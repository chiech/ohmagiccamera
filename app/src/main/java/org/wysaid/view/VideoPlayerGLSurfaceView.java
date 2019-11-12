package org.wysaid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.wysaid.common.Common;
import org.wysaid.nativePort.CGEFrameRenderer;
import org.wysaid.texUtils.TextureRenderer.Viewport;

public class VideoPlayerGLSurfaceView extends GLSurfaceView implements Renderer, OnFrameAvailableListener {
    static final /* synthetic */ boolean $assertionsDisabled = (!VideoPlayerGLSurfaceView.class.desiredAssertionStatus());
    public static final String LOG_TAG = "libCGE_java";
    private boolean mFitFullView = false;
    private CGEFrameRenderer mFrameRenderer;
    private long mFramesCount2 = 0;
    private boolean mIsUsingMask = false;
    private long mLastTimestamp2 = 0;
    private float mMaskAspectRatio = 1.0f;
    private OnCreateCallback mOnCreateCallback;
    PlayCompletionCallback mPlayCompletionCallback;
    private MediaPlayer mPlayer;
    PlayerInitializeCallback mPlayerInitCallback;
    PlayPreparedCallback mPreparedCallback;
    private Viewport mRenderViewport = new Viewport();
    private SurfaceTexture mSurfaceTexture;
    private long mTimeCount2 = 0;
    private float[] mTransformMatrix = new float[16];
    private int mVideoHeight = 1000;
    private int mVideoTextureID;
    private Uri mVideoUri;
    private int mVideoWidth = 1000;
    private int mViewHeight = 1000;
    private int mViewWidth = 1000;

    /* renamed from: org.wysaid.view.VideoPlayerGLSurfaceView$1 */
    class C16051 implements Runnable {
        C16051() {
        }

        public void run() {
            Log.i("libCGE_java", "setVideoUri...");
            if (VideoPlayerGLSurfaceView.this.mSurfaceTexture == null || VideoPlayerGLSurfaceView.this.mVideoTextureID == 0) {
                VideoPlayerGLSurfaceView.this.mVideoTextureID = Common.genSurfaceTextureID();
                VideoPlayerGLSurfaceView.this.mSurfaceTexture = new SurfaceTexture(VideoPlayerGLSurfaceView.this.mVideoTextureID);
                VideoPlayerGLSurfaceView.this.mSurfaceTexture.setOnFrameAvailableListener(VideoPlayerGLSurfaceView.this);
            }
            VideoPlayerGLSurfaceView.this._useUri();
        }
    }

    /* renamed from: org.wysaid.view.VideoPlayerGLSurfaceView$6 */
    class C16106 implements Runnable {
        C16106() {
        }

        public void run() {
            Log.i("libCGE_java", "Video player view release run...");
            if (VideoPlayerGLSurfaceView.this.mPlayer != null) {
                VideoPlayerGLSurfaceView.this.mPlayer.setSurface(null);
                if (VideoPlayerGLSurfaceView.this.mPlayer.isPlaying()) {
                    VideoPlayerGLSurfaceView.this.mPlayer.stop();
                }
                VideoPlayerGLSurfaceView.this.mPlayer.release();
                VideoPlayerGLSurfaceView.this.mPlayer = null;
            }
            if (VideoPlayerGLSurfaceView.this.mFrameRenderer != null) {
                VideoPlayerGLSurfaceView.this.mFrameRenderer.release();
                VideoPlayerGLSurfaceView.this.mFrameRenderer = null;
            }
            if (VideoPlayerGLSurfaceView.this.mSurfaceTexture != null) {
                VideoPlayerGLSurfaceView.this.mSurfaceTexture.release();
                VideoPlayerGLSurfaceView.this.mSurfaceTexture = null;
            }
            if (VideoPlayerGLSurfaceView.this.mVideoTextureID != 0) {
                GLES20.glDeleteTextures(1, new int[]{VideoPlayerGLSurfaceView.this.mVideoTextureID}, 0);
                VideoPlayerGLSurfaceView.this.mVideoTextureID = 0;
            }
            VideoPlayerGLSurfaceView.this.mIsUsingMask = false;
            VideoPlayerGLSurfaceView.this.mPreparedCallback = null;
            VideoPlayerGLSurfaceView.this.mPlayCompletionCallback = null;
            Log.i("libCGE_java", "Video player view release OK");
        }
    }

    /* renamed from: org.wysaid.view.VideoPlayerGLSurfaceView$7 */
    class C16117 implements Runnable {
        C16117() {
        }

        public void run() {
            if (VideoPlayerGLSurfaceView.this.mPlayCompletionCallback != null && !VideoPlayerGLSurfaceView.this.mPlayCompletionCallback.playFailed(VideoPlayerGLSurfaceView.this.mPlayer, 1, -1010)) {
                VideoPlayerGLSurfaceView.this.mPlayCompletionCallback.playComplete(VideoPlayerGLSurfaceView.this.mPlayer);
            }
        }
    }

    /* renamed from: org.wysaid.view.VideoPlayerGLSurfaceView$8 */
    class C16128 implements OnCompletionListener {
        C16128() {
        }

        public void onCompletion(MediaPlayer mp) {
            if (VideoPlayerGLSurfaceView.this.mPlayCompletionCallback != null) {
                VideoPlayerGLSurfaceView.this.mPlayCompletionCallback.playComplete(VideoPlayerGLSurfaceView.this.mPlayer);
            }
            Log.i("libCGE_java", "Video Play Over");
        }
    }

    /* renamed from: org.wysaid.view.VideoPlayerGLSurfaceView$9 */
    class C16149 implements OnPreparedListener {

        /* renamed from: org.wysaid.view.VideoPlayerGLSurfaceView$9$1 */
        class C16131 implements Runnable {
            C16131() {
            }

            public void run() {
                if (VideoPlayerGLSurfaceView.this.mFrameRenderer == null) {
                    VideoPlayerGLSurfaceView.this.mFrameRenderer = new CGEFrameRenderer();
                }
                if (VideoPlayerGLSurfaceView.this.mFrameRenderer.init(VideoPlayerGLSurfaceView.this.mVideoWidth, VideoPlayerGLSurfaceView.this.mVideoHeight, VideoPlayerGLSurfaceView.this.mVideoWidth, VideoPlayerGLSurfaceView.this.mVideoHeight)) {
                    VideoPlayerGLSurfaceView.this.mFrameRenderer.setSrcFlipScale(1.0f, -1.0f);
                    VideoPlayerGLSurfaceView.this.mFrameRenderer.setRenderFlipScale(1.0f, -1.0f);
                } else {
                    Log.e("libCGE_java", "Frame Recorder init failed!");
                }
                VideoPlayerGLSurfaceView.this.calcViewport();
            }
        }

        C16149() {
        }

        public void onPrepared(MediaPlayer mp) {
            VideoPlayerGLSurfaceView.this.mVideoWidth = mp.getVideoWidth();
            VideoPlayerGLSurfaceView.this.mVideoHeight = mp.getVideoHeight();
            VideoPlayerGLSurfaceView.this.queueEvent(new C16131());
            if (VideoPlayerGLSurfaceView.this.mPreparedCallback != null) {
                VideoPlayerGLSurfaceView.this.mPreparedCallback.playPrepared(VideoPlayerGLSurfaceView.this.mPlayer);
            } else {
                mp.start();
            }
            Log.i("libCGE_java", String.format("Video resolution 1: %d x %d", new Object[]{Integer.valueOf(VideoPlayerGLSurfaceView.this.mVideoWidth), Integer.valueOf(VideoPlayerGLSurfaceView.this.mVideoHeight)}));
        }
    }

    public interface OnCreateCallback {
        void createOK();
    }

    public interface PlayCompletionCallback {
        void playComplete(MediaPlayer mediaPlayer);

        boolean playFailed(MediaPlayer mediaPlayer, int i, int i2);
    }

    public interface PlayPreparedCallback {
        void playPrepared(MediaPlayer mediaPlayer);
    }

    public interface PlayerInitializeCallback {
        void initPlayer(MediaPlayer mediaPlayer);
    }

    public interface SetMaskBitmapCallback {
        void setMaskOK(CGEFrameRenderer cGEFrameRenderer);
    }

    public interface TakeShotCallback {
        void takeShotOK(Bitmap bitmap);
    }

    public boolean isUsingMask() {
        return this.mIsUsingMask;
    }

    public int getViewWidth() {
        return this.mViewWidth;
    }

    public int getViewheight() {
        return this.mViewHeight;
    }

    public void setFitFullView(boolean fit) {
        this.mFitFullView = fit;
        if (this.mFrameRenderer != null) {
            calcViewport();
        }
    }

    public void setPlayerInitializeCallback(PlayerInitializeCallback callback) {
        this.mPlayerInitCallback = callback;
    }

    public synchronized void setVideoUri(Uri uri, PlayPreparedCallback preparedCallback, PlayCompletionCallback completionCallback) {
        this.mVideoUri = uri;
        this.mPreparedCallback = preparedCallback;
        this.mPlayCompletionCallback = completionCallback;
        if (this.mFrameRenderer != null) {
            queueEvent(new C16051());
        }
    }

    public synchronized void setFilterWithConfig(final String config) {
        queueEvent(new Runnable() {
            public void run() {
                if (VideoPlayerGLSurfaceView.this.mFrameRenderer != null) {
                    VideoPlayerGLSurfaceView.this.mFrameRenderer.setFilterWidthConfig(config);
                } else {
                    Log.e("libCGE_java", "setFilterWithConfig after release!!");
                }
            }
        });
    }

    public void setFilterIntensity(final float intensity) {
        queueEvent(new Runnable() {
            public void run() {
                if (VideoPlayerGLSurfaceView.this.mFrameRenderer != null) {
                    VideoPlayerGLSurfaceView.this.mFrameRenderer.setFilterIntensity(intensity);
                } else {
                    Log.e("libCGE_java", "setFilterIntensity after release!!");
                }
            }
        });
    }

    public void setMaskBitmap(Bitmap bmp, boolean shouldRecycle) {
        setMaskBitmap(bmp, shouldRecycle, null);
    }

    public void setMaskBitmap(final Bitmap bmp, final boolean shouldRecycle, final SetMaskBitmapCallback callback) {
        queueEvent(new Runnable() {
            public void run() {
                if (VideoPlayerGLSurfaceView.this.mFrameRenderer == null) {
                    Log.e("libCGE_java", "setMaskBitmap after release!!");
                } else if (bmp == null) {
                    VideoPlayerGLSurfaceView.this.mFrameRenderer.setMaskTexture(0, 1.0f);
                    VideoPlayerGLSurfaceView.this.mIsUsingMask = false;
                    VideoPlayerGLSurfaceView.this.calcViewport();
                } else {
                    VideoPlayerGLSurfaceView.this.mFrameRenderer.setMaskTexture(Common.genNormalTextureID(bmp, 9728, 33071), ((float) bmp.getWidth()) / ((float) bmp.getHeight()));
                    VideoPlayerGLSurfaceView.this.mIsUsingMask = true;
                    VideoPlayerGLSurfaceView.this.mMaskAspectRatio = ((float) bmp.getWidth()) / ((float) bmp.getHeight());
                    if (callback != null) {
                        callback.setMaskOK(VideoPlayerGLSurfaceView.this.mFrameRenderer);
                    }
                    if (shouldRecycle) {
                        bmp.recycle();
                    }
                    VideoPlayerGLSurfaceView.this.calcViewport();
                }
            }
        });
    }

    public synchronized MediaPlayer getPlayer() {
        if (this.mPlayer == null) {
            Log.e("libCGE_java", "Player is not initialized!");
        }
        return this.mPlayer;
    }

    public void setOnCreateCallback(final OnCreateCallback callback) {
        if (!$assertionsDisabled && callback == null) {
            throw new AssertionError("无意义操作!");
        } else if (this.mFrameRenderer == null) {
            this.mOnCreateCallback = callback;
        } else {
            queueEvent(new Runnable() {
                public void run() {
                    callback.createOK();
                }
            });
        }
    }

    public VideoPlayerGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("libCGE_java", "MyGLSurfaceView Construct...");
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 8, 0);
        getHolder().setFormat(1);
        setRenderer(this);
        setRenderMode(0);
        setZOrderOnTop(true);
        Log.i("libCGE_java", "MyGLSurfaceView Construct OK...");
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("libCGE_java", "video player onSurfaceCreated...");
        GLES20.glDisable(2929);
        GLES20.glDisable(2960);
        if (this.mOnCreateCallback != null) {
            this.mOnCreateCallback.createOK();
        }
        if (this.mVideoUri == null) {
            return;
        }
        if (this.mSurfaceTexture == null || this.mVideoTextureID == 0) {
            this.mVideoTextureID = Common.genSurfaceTextureID();
            this.mSurfaceTexture = new SurfaceTexture(this.mVideoTextureID);
            this.mSurfaceTexture.setOnFrameAvailableListener(this);
            _useUri();
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mViewWidth = width;
        this.mViewHeight = height;
        calcViewport();
    }

    public void release() {
        Log.i("libCGE_java", "Video player view release...");
        if (this.mPlayer != null) {
            queueEvent(new C16106());
        }
    }

    public void onPause() {
        Log.i("libCGE_java", "surfaceview onPause ...");
        super.onPause();
    }

    public void onDrawFrame(GL10 gl) {
        if (this.mSurfaceTexture != null && this.mFrameRenderer != null) {
            this.mSurfaceTexture.updateTexImage();
            if (this.mPlayer.isPlaying()) {
                this.mSurfaceTexture.getTransformMatrix(this.mTransformMatrix);
                this.mFrameRenderer.update(this.mVideoTextureID, this.mTransformMatrix);
                this.mFrameRenderer.runProc();
                GLES20.glBindFramebuffer(36160, 0);
                GLES20.glClear(16384);
                GLES20.glEnable(3042);
                this.mFrameRenderer.render(this.mRenderViewport.f34x, this.mRenderViewport.f35y, this.mRenderViewport.width, this.mRenderViewport.height);
                GLES20.glDisable(3042);
            }
        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
        if (this.mLastTimestamp2 == 0) {
            this.mLastTimestamp2 = System.currentTimeMillis();
        }
        long currentTimestamp = System.currentTimeMillis();
        this.mFramesCount2++;
        this.mTimeCount2 += currentTimestamp - this.mLastTimestamp2;
        this.mLastTimestamp2 = currentTimestamp;
        if (((double) this.mTimeCount2) >= 1000.0d) {
            Log.i("libCGE_java", String.format("播放帧率: %d", new Object[]{Long.valueOf(this.mFramesCount2)}));
            this.mTimeCount2 = (long) (((double) this.mTimeCount2) - 1000.0d);
            this.mFramesCount2 = 0;
        }
    }

    private void calcViewport() {
        float scaling;
        int w;
        int h;
        if (this.mIsUsingMask) {
            scaling = this.mMaskAspectRatio;
        } else {
            scaling = ((float) this.mVideoWidth) / ((float) this.mVideoHeight);
        }
        float s = scaling / (((float) this.mViewWidth) / ((float) this.mViewHeight));
        if (this.mFitFullView) {
            if (((double) s) > 1.0d) {
                w = (int) (((float) this.mViewHeight) * scaling);
                h = this.mViewHeight;
            } else {
                w = this.mViewWidth;
                h = (int) (((float) this.mViewWidth) / scaling);
            }
        } else if (((double) s) > 1.0d) {
            w = this.mViewWidth;
            h = (int) (((float) this.mViewWidth) / scaling);
        } else {
            h = this.mViewHeight;
            w = (int) (((float) this.mViewHeight) * scaling);
        }
        this.mRenderViewport.width = w;
        this.mRenderViewport.height = h;
        this.mRenderViewport.f34x = (this.mViewWidth - this.mRenderViewport.width) / 2;
        this.mRenderViewport.f35y = (this.mViewHeight - this.mRenderViewport.height) / 2;
        Log.i("libCGE_java", String.format("View port: %d, %d, %d, %d", new Object[]{Integer.valueOf(this.mRenderViewport.f34x), Integer.valueOf(this.mRenderViewport.f35y), Integer.valueOf(this.mRenderViewport.width), Integer.valueOf(this.mRenderViewport.height)}));
    }

    private void _useUri() {
        if (this.mPlayer != null) {
            this.mPlayer.stop();
            this.mPlayer.reset();
        } else {
            this.mPlayer = new MediaPlayer();
        }
        try {
            this.mPlayer.setDataSource(getContext(), this.mVideoUri);
            this.mPlayer.setSurface(new Surface(this.mSurfaceTexture));
            if (this.mPlayerInitCallback != null) {
                this.mPlayerInitCallback.initPlayer(this.mPlayer);
            }
            this.mPlayer.setOnCompletionListener(new C16128());
            this.mPlayer.setOnPreparedListener(new C16149());
            this.mPlayer.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (VideoPlayerGLSurfaceView.this.mPlayCompletionCallback != null) {
                        return VideoPlayerGLSurfaceView.this.mPlayCompletionCallback.playFailed(mp, what, extra);
                    }
                    return false;
                }
            });
            try {
                this.mPlayer.prepareAsync();
            } catch (Exception e) {
                Log.i("libCGE_java", String.format("Error handled: %s, play failure handler would be called!", new Object[]{e.toString()}));
                if (this.mPlayCompletionCallback != null) {
                    post(new Runnable() {
                        public void run() {
                            if (VideoPlayerGLSurfaceView.this.mPlayCompletionCallback != null && !VideoPlayerGLSurfaceView.this.mPlayCompletionCallback.playFailed(VideoPlayerGLSurfaceView.this.mPlayer, 1, -1010)) {
                                VideoPlayerGLSurfaceView.this.mPlayCompletionCallback.playComplete(VideoPlayerGLSurfaceView.this.mPlayer);
                            }
                        }
                    });
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e("libCGE_java", "useUri failed");
            if (this.mPlayCompletionCallback != null) {
                post(new C16117());
            }
        }
    }

    public synchronized void takeShot(final TakeShotCallback callback) {
        if (!$assertionsDisabled && callback == null) {
            throw new AssertionError("callback must not be null!");
        } else if (this.mFrameRenderer == null) {
            Log.e("libCGE_java", "Drawer not initialized!");
            callback.takeShotOK(null);
        } else {
            queueEvent(new Runnable() {
                public void run() {
                    IntBuffer buffer = IntBuffer.allocate(VideoPlayerGLSurfaceView.this.mRenderViewport.width * VideoPlayerGLSurfaceView.this.mRenderViewport.height);
                    GLES20.glReadPixels(VideoPlayerGLSurfaceView.this.mRenderViewport.f34x, VideoPlayerGLSurfaceView.this.mRenderViewport.f35y, VideoPlayerGLSurfaceView.this.mRenderViewport.width, VideoPlayerGLSurfaceView.this.mRenderViewport.height, 6408, 5121, buffer);
                    Bitmap bmp = Bitmap.createBitmap(VideoPlayerGLSurfaceView.this.mRenderViewport.width, VideoPlayerGLSurfaceView.this.mRenderViewport.height, Config.ARGB_8888);
                    bmp.copyPixelsFromBuffer(buffer);
                    Bitmap bmp2 = Bitmap.createBitmap(VideoPlayerGLSurfaceView.this.mRenderViewport.width, VideoPlayerGLSurfaceView.this.mRenderViewport.height, Config.ARGB_8888);
                    Canvas canvas = new Canvas(bmp2);
                    Matrix mat = new Matrix();
                    mat.setTranslate(0.0f, ((float) (-VideoPlayerGLSurfaceView.this.mRenderViewport.height)) / 2.0f);
                    mat.postScale(1.0f, -1.0f);
                    mat.postTranslate(0.0f, ((float) VideoPlayerGLSurfaceView.this.mRenderViewport.height) / 2.0f);
                    canvas.drawBitmap(bmp, mat, null);
                    bmp.recycle();
                    callback.takeShotOK(bmp2);
                }
            });
        }
    }
}
