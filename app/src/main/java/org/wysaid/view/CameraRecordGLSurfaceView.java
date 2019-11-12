package org.wysaid.view;

import android.content.Context;
import android.media.AudioRecord;
import android.util.AttributeSet;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import org.wysaid.view.CameraGLSurfaceView.ReleaseOKCallback;

public class CameraRecordGLSurfaceView extends CameraGLSurfaceView {
    private AudioRecordRunnable mAudioRecordRunnable;
    private Thread mAudioThread;
    private final Object mRecordStateLock = new Object();
    private boolean mShouldRecord = false;

    class AudioRecordRunnable implements Runnable {
        private static final int sampleRate = 44100;
        ShortBuffer audioBuffer;
        ByteBuffer audioBufferRef;
        public AudioRecord audioRecord;
        int bufferReadResult;
        int bufferSize;
        public volatile boolean isInitialized;
        StartRecordingCallback recordingCallback;

        private AudioRecordRunnable(StartRecordingCallback callback) {
            this.recordingCallback = callback;
            try {
                this.bufferSize = AudioRecord.getMinBufferSize(sampleRate, 16, 2);
                Log.i("libCGE_java", "audio min buffer size: " + this.bufferSize);
                this.audioRecord = new AudioRecord(1, sampleRate, 16, 2, this.bufferSize);
                this.audioBufferRef = ByteBuffer.allocateDirect(this.bufferSize * 2).order(ByteOrder.nativeOrder());
                this.audioBuffer = this.audioBufferRef.asShortBuffer();
            } catch (Exception e) {
                if (this.audioRecord != null) {
                    this.audioRecord.release();
                    this.audioRecord = null;
                }
            }
            if (this.audioRecord == null && this.recordingCallback != null) {
                this.recordingCallback.startRecordingOver(false);
                this.recordingCallback = null;
            }
        }

        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            this.isInitialized = false;

            if (this.audioRecord == null) {
                recordingCallback.startRecordingOver(false);
                recordingCallback = null;
                return;
            }

            while (this.audioRecord.getState() == 0) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException localInterruptedException) {
                    localInterruptedException.printStackTrace();
                }
            }
            this.isInitialized = true;

            try {
                this.audioRecord.startRecording();
            } catch (Exception e) {
                if (recordingCallback != null) {
                    recordingCallback.startRecordingOver(false);
                    recordingCallback = null;
                }
                return;
            }

            if (this.audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                if (recordingCallback != null) {
                    recordingCallback.startRecordingOver(false);
                    recordingCallback = null;
                }
                return;
            }

            if (recordingCallback != null) {
                recordingCallback.startRecordingOver(true);
                recordingCallback = null;
            }


            while (true) {
                synchronized (mRecordStateLock) {
                    if (!mShouldRecord) //&& mFrameRecorder.getVideoStreamtime() <= mFrameRecorder.getAudioStreamtime()
                        break;
                }

                audioBufferRef.position(0);
                bufferReadResult = this.audioRecord.read(audioBufferRef, bufferSize * 2);
                if (mShouldRecord && bufferReadResult > 0 && mFrameRecorder != null &&
                        mFrameRecorder.getTimestamp() > mFrameRecorder.getAudioStreamtime()) {
//                    Log.e(LOG_TAG, "buffer Result: " + bufferReadResult);
                    audioBuffer.position(0);
//                    audioBuffer.put(audioData).position(0);
                    mFrameRecorder.recordAudioFrame(audioBuffer, bufferReadResult / 2);
                }
            }
            this.audioRecord.stop();
            this.audioRecord.release();
            Log.i(LOG_TAG, "Audio thread end!");
        }
    }

    public interface EndRecordingCallback {
        void endRecordingOK();
    }

    public interface StartRecordingCallback {
        void startRecordingOver(boolean z);
    }

    public CameraRecordGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public synchronized boolean isRecording() {
        return this.mShouldRecord;
    }

    public void startRecording(String filename) {
        startRecording(filename, null);
    }

    public void startRecording(final String filename, final StartRecordingCallback recordingCallback) {
        queueEvent(new Runnable() {
            public void run() {
                if (CameraRecordGLSurfaceView.this.mFrameRecorder == null) {
                    Log.e("libCGE_java", "Error: startRecording after release!!");
                    if (recordingCallback != null) {
                        recordingCallback.startRecordingOver(false);
                    }
                } else if (CameraRecordGLSurfaceView.this.mFrameRecorder.startRecording(30, filename)) {
                    Log.i("libCGE_java", "glSurfaceView recording, file: " + filename);
                    synchronized (CameraRecordGLSurfaceView.this.mRecordStateLock) {
                        CameraRecordGLSurfaceView.this.mShouldRecord = true;
                        CameraRecordGLSurfaceView.this.mAudioRecordRunnable = new AudioRecordRunnable(recordingCallback);
                        if (CameraRecordGLSurfaceView.this.mAudioRecordRunnable.audioRecord != null) {
                            CameraRecordGLSurfaceView.this.mAudioThread = new Thread(CameraRecordGLSurfaceView.this.mAudioRecordRunnable);
                            CameraRecordGLSurfaceView.this.mAudioThread.start();
                        }
                    }
                } else {
                    Log.e("libCGE_java", "start recording failed!");
                    if (recordingCallback != null) {
                        recordingCallback.startRecordingOver(false);
                    }
                }
            }
        });
    }

    public void endRecording() {
        endRecording(null, true);
    }

    public void endRecording(EndRecordingCallback callback) {
        endRecording(callback, true);
    }

    public void endRecording(final EndRecordingCallback callback, final boolean shouldSave) {
        Log.i("libCGE_java", "notify quit...");
        synchronized (this.mRecordStateLock) {
            this.mShouldRecord = false;
        }
        if (this.mFrameRecorder == null) {
            Log.e("libCGE_java", "Error: endRecording after release!!");
            return;
        }
        joinAudioRecording();
        queueEvent(new Runnable() {
            public void run() {
                if (CameraRecordGLSurfaceView.this.mFrameRecorder != null) {
                    CameraRecordGLSurfaceView.this.mFrameRecorder.endRecording(shouldSave);
                }
                if (callback != null) {
                    callback.endRecordingOK();
                }
            }
        });
    }

    public synchronized void release(ReleaseOKCallback callback) {
        synchronized (this.mRecordStateLock) {
            this.mShouldRecord = false;
        }
        joinAudioRecording();
        super.release(callback);
    }

    public void stopPreview() {
        synchronized (this.mRecordStateLock) {
            if (this.mShouldRecord) {
                Log.e("libCGE_java", "The camera is recording! cannot stop!");
                return;
            }
            super.stopPreview();
        }
    }

    public void joinAudioRecording() {
        if (this.mAudioThread != null) {
            try {
                this.mAudioThread.join();
                this.mAudioThread = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
