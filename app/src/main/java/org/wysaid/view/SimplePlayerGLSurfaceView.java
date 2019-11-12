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
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.wysaid.common.Common;
import org.wysaid.texUtils.TextureRenderer;
import org.wysaid.texUtils.TextureRenderer.Viewport;
import org.wysaid.texUtils.TextureRendererDrawOrigin;
import org.wysaid.texUtils.TextureRendererMask;

public class SimplePlayerGLSurfaceView extends GLSurfaceView implements Renderer, OnFrameAvailableListener {
    static final /* synthetic */ boolean $assertionsDisabled = (!SimplePlayerGLSurfaceView.class.desiredAssertionStatus());
    public static final String LOG_TAG = "libCGE_java";
    private TextureRenderer mDrawer;
    private float mDrawerFlipScaleX = 1.0f;
    private float mDrawerFlipScaleY = 1.0f;
    private boolean mFitFullView = false;
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

    /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$1 */
    class C15951 implements Runnable {
        C15951() {
        }

        public void run() {
            Log.i("libCGE_java", "setVideoUri...");
            if (SimplePlayerGLSurfaceView.this.mSurfaceTexture == null || SimplePlayerGLSurfaceView.this.mVideoTextureID == 0) {
                SimplePlayerGLSurfaceView.this.mVideoTextureID = Common.genSurfaceTextureID();
                SimplePlayerGLSurfaceView.this.mSurfaceTexture = new SurfaceTexture(SimplePlayerGLSurfaceView.this.mVideoTextureID);
                SimplePlayerGLSurfaceView.this.mSurfaceTexture.setOnFrameAvailableListener(SimplePlayerGLSurfaceView.this);
            }
            SimplePlayerGLSurfaceView.this._useUri();
        }
    }

    /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$4 */
    class C15984 implements Runnable {
        C15984() {
        }

        public void run() {
            Log.i("libCGE_java", "Video player view release run...");
            if (SimplePlayerGLSurfaceView.this.mPlayer != null) {
                SimplePlayerGLSurfaceView.this.mPlayer.setSurface(null);
                if (SimplePlayerGLSurfaceView.this.mPlayer.isPlaying()) {
                    SimplePlayerGLSurfaceView.this.mPlayer.stop();
                }
                SimplePlayerGLSurfaceView.this.mPlayer.release();
                SimplePlayerGLSurfaceView.this.mPlayer = null;
            }
            if (SimplePlayerGLSurfaceView.this.mDrawer != null) {
                SimplePlayerGLSurfaceView.this.mDrawer.release();
                SimplePlayerGLSurfaceView.this.mDrawer = null;
            }
            if (SimplePlayerGLSurfaceView.this.mSurfaceTexture != null) {
                SimplePlayerGLSurfaceView.this.mSurfaceTexture.release();
                SimplePlayerGLSurfaceView.this.mSurfaceTexture = null;
            }
            if (SimplePlayerGLSurfaceView.this.mVideoTextureID != 0) {
                GLES20.glDeleteTextures(1, new int[]{SimplePlayerGLSurfaceView.this.mVideoTextureID}, 0);
                SimplePlayerGLSurfaceView.this.mVideoTextureID = 0;
            }
            SimplePlayerGLSurfaceView.this.mIsUsingMask = false;
            SimplePlayerGLSurfaceView.this.mPreparedCallback = null;
            SimplePlayerGLSurfaceView.this.mPlayCompletionCallback = null;
            Log.i("libCGE_java", "Video player view release OK");
        }
    }

    /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$5 */
    class C15995 implements Runnable {
        C15995() {
        }

        public void run() {
            if (SimplePlayerGLSurfaceView.this.mPlayCompletionCallback != null && !SimplePlayerGLSurfaceView.this.mPlayCompletionCallback.playFailed(SimplePlayerGLSurfaceView.this.mPlayer, 1, -1010)) {
                SimplePlayerGLSurfaceView.this.mPlayCompletionCallback.playComplete(SimplePlayerGLSurfaceView.this.mPlayer);
            }
        }
    }

    /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$6 */
    class C16006 implements OnCompletionListener {
        C16006() {
        }

        public void onCompletion(MediaPlayer mp) {
            if (SimplePlayerGLSurfaceView.this.mPlayCompletionCallback != null) {
                SimplePlayerGLSurfaceView.this.mPlayCompletionCallback.playComplete(SimplePlayerGLSurfaceView.this.mPlayer);
            }
            Log.i("libCGE_java", "Video Play Over");
        }
    }

    /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$7 */
    class C16027 implements OnPreparedListener {

        /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$7$1 */
        class C16011 implements Runnable {
            C16011() {
            }

            public void run() {
                SimplePlayerGLSurfaceView.this.calcViewport();
            }
        }

        C16027() {
        }

        public void onPrepared(MediaPlayer mp) {
            SimplePlayerGLSurfaceView.this.mVideoWidth = mp.getVideoWidth();
            SimplePlayerGLSurfaceView.this.mVideoHeight = mp.getVideoHeight();
            SimplePlayerGLSurfaceView.this.queueEvent(new C16011());
            if (SimplePlayerGLSurfaceView.this.mPreparedCallback != null) {
                SimplePlayerGLSurfaceView.this.mPreparedCallback.playPrepared(SimplePlayerGLSurfaceView.this.mPlayer);
            } else {
                mp.start();
            }
            Log.i("libCGE_java", String.format("Video resolution 1: %d x %d", new Object[]{Integer.valueOf(SimplePlayerGLSurfaceView.this.mVideoWidth), Integer.valueOf(SimplePlayerGLSurfaceView.this.mVideoHeight)}));
        }
    }

    /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$8 */
    class C16038 implements OnErrorListener {
        C16038() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (SimplePlayerGLSurfaceView.this.mPlayCompletionCallback != null) {
                return SimplePlayerGLSurfaceView.this.mPlayCompletionCallback.playFailed(mp, what, extra);
            }
            return false;
        }
    }

    /* renamed from: org.wysaid.view.SimplePlayerGLSurfaceView$9 */
    class C16049 implements Runnable {
        C16049() {
        }

        public void run() {
            if (SimplePlayerGLSurfaceView.this.mPlayCompletionCallback != null && !SimplePlayerGLSurfaceView.this.mPlayCompletionCallback.playFailed(SimplePlayerGLSurfaceView.this.mPlayer, 1, -1010)) {
                SimplePlayerGLSurfaceView.this.mPlayCompletionCallback.playComplete(SimplePlayerGLSurfaceView.this.mPlayer);
            }
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
        void setMaskOK(TextureRendererMask textureRendererMask);

        void unsetMaskOK(TextureRenderer textureRenderer);
    }

    public interface TakeShotCallback {
        void takeShotOK(Bitmap bitmap);
    }

    public void setTextureRenderer(TextureRenderer drawer) {
        if (this.mDrawer == null) {
            Log.e("libCGE_java", "Invalid Drawer!");
        } else if (this.mDrawer != drawer) {
            this.mDrawer.release();
            this.mDrawer = drawer;
            calcViewport();
        }
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
        if (this.mDrawer != null) {
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
        if (this.mDrawer != null) {
            queueEvent(new C15951());
        }
    }

    public void setMaskBitmap(Bitmap bmp, boolean shouldRecycle) {
        setMaskBitmap(bmp, shouldRecycle, null);
    }

    public synchronized void setMaskBitmap(final Bitmap bmp, final boolean shouldRecycle, final SetMaskBitmapCallback callback) {
        if (this.mDrawer == null) {
            Log.e("libCGE_java", "setMaskBitmap after release!");
        } else {
            queueEvent(new Runnable() {
                public void run() {
                    if (bmp == null) {
                        Log.i("libCGE_java", "Cancel Mask Bitmap!");
                        SimplePlayerGLSurfaceView.this.setMaskTexture(0, 1.0f);
                        if (callback != null) {
                            callback.unsetMaskOK(SimplePlayerGLSurfaceView.this.mDrawer);
                            return;
                        }
                        return;
                    }
                    Log.i("libCGE_java", "Use Mask Bitmap!");
                    int[] texID = new int[]{0};
                    GLES20.glGenTextures(1, texID, 0);
                    GLES20.glBindTexture(3553, texID[0]);
                    GLUtils.texImage2D(3553, 0, bmp, 0);
                    GLES20.glTexParameteri(3553, 10241, 9728);
                    GLES20.glTexParameteri(3553, 10240, 9728);
                    GLES20.glTexParameteri(3553, 10242, 33071);
                    GLES20.glTexParameteri(3553, 10243, 33071);
                    SimplePlayerGLSurfaceView.this.setMaskTexture(texID[0], ((float) bmp.getWidth()) / ((float) bmp.getHeight()));
                    if (callback != null && (SimplePlayerGLSurfaceView.this.mDrawer instanceof TextureRendererMask)) {
                        callback.setMaskOK((TextureRendererMask) SimplePlayerGLSurfaceView.this.mDrawer);
                    }
                    if (shouldRecycle) {
                        bmp.recycle();
                    }
                }
            });
        }
    }

    public synchronized void setMaskTexture(int texID, float aspectRatio) {
        Log.i("libCGE_java", "setMaskTexture... ");
        if (texID == 0) {
            if (this.mDrawer instanceof TextureRendererMask) {
                this.mDrawer.release();
                this.mDrawer = TextureRendererDrawOrigin.create(true);
            }
            this.mIsUsingMask = false;
        } else {
            if (!(this.mDrawer instanceof TextureRendererMask)) {
                this.mDrawer.release();
                TextureRendererMask drawer = TextureRendererMask.create(true);
                if ($assertionsDisabled || drawer != null) {
                    drawer.setMaskTexture(texID);
                    this.mDrawer = drawer;
                } else {
                    throw new AssertionError("Drawer Create Failed!");
                }
            }
            this.mIsUsingMask = true;
        }
        this.mMaskAspectRatio = aspectRatio;
        calcViewport();
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
        } else if (this.mDrawer == null) {
            this.mOnCreateCallback = callback;
        } else {
            queueEvent(new Runnable() {
                public void run() {
                    callback.createOK();
                }
            });
        }
    }

    public SimplePlayerGLSurfaceView(Context context, AttributeSet attrs) {
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
        this.mDrawer = TextureRendererDrawOrigin.create(true);
        if (this.mDrawer == null) {
            Log.e("libCGE_java", "Create Drawer Failed!");
            return;
        }
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
            queueEvent(new C15984());
        }
    }

    public void onPause() {
        Log.i("libCGE_java", "surfaceview onPause ...");
        super.onPause();
    }

    public void onDrawFrame(GL10 gl) {
        if (this.mSurfaceTexture != null) {
            this.mSurfaceTexture.updateTexImage();
            if (this.mPlayer.isPlaying()) {
                GLES20.glBindFramebuffer(36160, 0);
                GLES20.glClear(16384);
                GLES20.glViewport(0, 0, this.mViewWidth, this.mViewHeight);
                this.mSurfaceTexture.getTransformMatrix(this.mTransformMatrix);
                this.mDrawer.setTransform(this.mTransformMatrix);
                this.mDrawer.renderTexture(this.mVideoTextureID, this.mRenderViewport);
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
            flushMaskAspectRatio();
            scaling = this.mMaskAspectRatio;
        } else {
            this.mDrawer.setFlipscale(this.mDrawerFlipScaleX, this.mDrawerFlipScaleY);
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
            this.mPlayer.setOnCompletionListener(new C16006());
            this.mPlayer.setOnPreparedListener(new C16027());
            this.mPlayer.setOnErrorListener(new C16038());
            try {
                this.mPlayer.prepareAsync();
            } catch (Exception e) {
                Log.i("libCGE_java", String.format("Error handled: %s, play failure handler would be called!", new Object[]{e.toString()}));
                if (this.mPlayCompletionCallback != null) {
                    post(new C16049());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e("libCGE_java", "useUri failed");
            if (this.mPlayCompletionCallback != null) {
                post(new C15995());
            }
        }
    }

    private void flushMaskAspectRatio() {
        float s = (((float) this.mVideoWidth) / ((float) this.mVideoHeight)) / this.mMaskAspectRatio;
        if (s > 1.0f) {
            this.mDrawer.setFlipscale(this.mDrawerFlipScaleX / s, this.mDrawerFlipScaleY);
        } else {
            this.mDrawer.setFlipscale(this.mDrawerFlipScaleX, this.mDrawerFlipScaleY * s);
        }
    }

    public synchronized void takeShot(final TakeShotCallback callback) {
        if (!$assertionsDisabled && callback == null) {
            throw new AssertionError("callback must not be null!");
        } else if (this.mDrawer == null) {
            Log.e("libCGE_java", "Drawer not initialized!");
            callback.takeShotOK(null);
        } else {
            queueEvent(new Runnable() {
                public void run() {
                    IntBuffer buffer = IntBuffer.allocate(SimplePlayerGLSurfaceView.this.mRenderViewport.width * SimplePlayerGLSurfaceView.this.mRenderViewport.height);
                    GLES20.glReadPixels(SimplePlayerGLSurfaceView.this.mRenderViewport.f34x, SimplePlayerGLSurfaceView.this.mRenderViewport.f35y, SimplePlayerGLSurfaceView.this.mRenderViewport.width, SimplePlayerGLSurfaceView.this.mRenderViewport.height, 6408, 5121, buffer);
                    Bitmap bmp = Bitmap.createBitmap(SimplePlayerGLSurfaceView.this.mRenderViewport.width, SimplePlayerGLSurfaceView.this.mRenderViewport.height, Config.ARGB_8888);
                    bmp.copyPixelsFromBuffer(buffer);
                    Bitmap bmp2 = Bitmap.createBitmap(SimplePlayerGLSurfaceView.this.mRenderViewport.width, SimplePlayerGLSurfaceView.this.mRenderViewport.height, Config.ARGB_8888);
                    Canvas canvas = new Canvas(bmp2);
                    Matrix mat = new Matrix();
                    mat.setTranslate(0.0f, ((float) (-SimplePlayerGLSurfaceView.this.mRenderViewport.height)) / 2.0f);
                    mat.postScale(1.0f, -1.0f);
                    mat.postTranslate(0.0f, ((float) SimplePlayerGLSurfaceView.this.mRenderViewport.height) / 2.0f);
                    canvas.drawBitmap(bmp, mat, null);
                    bmp.recycle();
                    callback.takeShotOK(bmp2);
                }
            });
        }
    }
}
