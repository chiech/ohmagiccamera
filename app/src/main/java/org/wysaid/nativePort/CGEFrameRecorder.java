package org.wysaid.nativePort;

import java.nio.ShortBuffer;

public class CGEFrameRecorder extends CGEFrameRenderer {
    private native long nativeCreateRecorder();

    private native boolean nativeEndRecording(long j, boolean z);

    private native double nativeGetAudioStreamtime(long j);

    private native double nativeGetTimestamp(long j);

    private native double nativeGetVideoStreamtime(long j);

    private native void nativeIsGlobalFilterEnabled(long j);

    private native boolean nativeIsRecordingStarted(long j);

    private native void nativePauseRecording(long j);

    private native void nativeRecordAudioFrame(long j, ShortBuffer shortBuffer, int i);

    private native void nativeRecordImageFrame(long j);

    private native void nativeSetBeautifyFilter(long j);

    private native void nativeSetGlobalFilter(long j, String str);

    private native void nativeSetGlobalFilterIntensity(long j, float f);

    private native void nativeSetTempDir(long j, String str);

    private native boolean nativeStartRecording(long j, int i, String str, int i2);

    static {
        NativeLibraryLoader.load();
    }

    public CGEFrameRecorder() {
        super(0);
        this.mNativeAddress = nativeCreateRecorder();
    }

    public boolean startRecording(int fps, String filename) {
        return startRecording(fps, 1650000, filename);
    }

    public boolean startRecording(int fps, int bitRate, String filename) {
        if (this.mNativeAddress != 0) {
            return nativeStartRecording(this.mNativeAddress, fps, filename, bitRate);
        }
        return false;
    }

    public boolean isRecordingStarted() {
        if (this.mNativeAddress != 0) {
            return nativeIsRecordingStarted(this.mNativeAddress);
        }
        return false;
    }

    public boolean endRecording(boolean shouldSave) {
        if (this.mNativeAddress != 0) {
            return nativeEndRecording(this.mNativeAddress, shouldSave);
        }
        return false;
    }

    public void pauseRecording() {
        if (this.mNativeAddress != 0) {
            nativePauseRecording(this.mNativeAddress);
        }
    }

    public double getTimestamp() {
        if (this.mNativeAddress != 0) {
            return nativeGetTimestamp(this.mNativeAddress);
        }
        return 0.0d;
    }

    public double getVideoStreamtime() {
        if (this.mNativeAddress != 0) {
            return nativeGetVideoStreamtime(this.mNativeAddress);
        }
        return 0.0d;
    }

    public double getAudioStreamtime() {
        if (this.mNativeAddress != 0) {
            return nativeGetAudioStreamtime(this.mNativeAddress);
        }
        return 0.0d;
    }

    public void setTempDir(String dir) {
        if (this.mNativeAddress != 0) {
            nativeSetTempDir(this.mNativeAddress, dir);
        }
    }

    public void recordImageFrame() {
        if (this.mNativeAddress != 0) {
            nativeRecordImageFrame(this.mNativeAddress);
        }
    }

    public void recordAudioFrame(ShortBuffer audioBuffer, int bufferLen) {
        if (this.mNativeAddress != 0) {
            nativeRecordAudioFrame(this.mNativeAddress, audioBuffer, bufferLen);
        }
    }

    public void setGlobalFilter(String config) {
        if (this.mNativeAddress != 0) {
            nativeSetGlobalFilter(this.mNativeAddress, config);
        }
    }

    public void setBeautifyFilter() {
        if (this.mNativeAddress != 0) {
            nativeSetBeautifyFilter(this.mNativeAddress);
        }
    }

    public void setGlobalFilterIntensity(float intensity) {
        if (this.mNativeAddress != 0) {
            nativeSetGlobalFilterIntensity(this.mNativeAddress, intensity);
        }
    }

    public void isGlobalFilterEnabled() {
        if (this.mNativeAddress != 0) {
            nativeIsGlobalFilterEnabled(this.mNativeAddress);
        }
    }
}
