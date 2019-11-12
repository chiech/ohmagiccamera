package org.wysaid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.wysaid.nativePort.CGEImageHandler;
import org.wysaid.texUtils.TextureRenderer.Viewport;

public class ImageGLSurfaceView extends GLSurfaceView implements Renderer {
    public static final String LOG_TAG = "libCGE_java";
    protected DisplayMode mDisplayMode = DisplayMode.DISPLAY_SCALE_TO_FILL;
    protected float mFilterIntensity = 1.0f;
    protected CGEImageHandler mImageHandler;
    protected int mImageHeight;
    protected int mImageWidth;
    protected Viewport mRenderViewport = new Viewport();
    protected int mSettingIntensityCount = 1;
    protected final Object mSettingIntensityLock = new Object();
    protected OnSurfaceCreatedCallback mSurfaceCreatedCallback;
    protected int mViewHeight;
    protected int mViewWidth;

    public interface QueryResultBitmapCallback {
        void get(Bitmap bitmap);
    }

    public interface OnSurfaceCreatedCallback {
        void surfaceCreated();
    }

    /* renamed from: org.wysaid.view.ImageGLSurfaceView$3 */
    class C15903 implements Runnable {
        C15903() {
        }

        public void run() {
            if (ImageGLSurfaceView.this.mImageHandler == null) {
                Log.e("libCGE_java", "set intensity after release!!");
            } else {
                ImageGLSurfaceView.this.mImageHandler.setFilterIntensity(ImageGLSurfaceView.this.mFilterIntensity, true);
                ImageGLSurfaceView.this.requestRender();
            }
            synchronized (ImageGLSurfaceView.this.mSettingIntensityLock) {
                ImageGLSurfaceView imageGLSurfaceView = ImageGLSurfaceView.this;
                imageGLSurfaceView.mSettingIntensityCount++;
            }
        }
    }

    /* renamed from: org.wysaid.view.ImageGLSurfaceView$6 */
    class C15936 implements Runnable {
        C15936() {
        }

        public void run() {
            Log.i("libCGE_java", "ImageGLSurfaceView release...");
            if (ImageGLSurfaceView.this.mImageHandler != null) {
                ImageGLSurfaceView.this.mImageHandler.release();
                ImageGLSurfaceView.this.mImageHandler = null;
            }
        }
    }

    public enum DisplayMode {
        DISPLAY_SCALE_TO_FILL,
        DISPLAY_ASPECT_FILL,
        DISPLAY_ASPECT_FIT
    }

    public CGEImageHandler getImageHandler() {
        return this.mImageHandler;
    }

    public int getImageWidth() {
        return this.mImageWidth;
    }

    public int getImageheight() {
        return this.mImageHeight;
    }

    public DisplayMode getDisplayMode() {
        return this.mDisplayMode;
    }

    public void setDisplayMode(DisplayMode displayMode) {
        this.mDisplayMode = displayMode;
        calcViewport();
        requestRender();
    }

    public void setFilterWithConfig(final String config) {
        if (this.mImageHandler != null) {
            queueEvent(new Runnable() {
                public void run() {
                    if (ImageGLSurfaceView.this.mImageHandler == null) {
                        Log.e("libCGE_java", "set config after release!!");
                        return;
                    }
                    ImageGLSurfaceView.this.mImageHandler.setFilterWithConfig(config);
                    ImageGLSurfaceView.this.requestRender();
                }
            });
        }
    }

    public void setFilterIntensityForIndex(float intensity, int index) {
        setFilterIntensityForIndex(intensity, index, true);
    }

    public void processFilters() {
        this.mImageHandler.processFilters();
    }

    public void setFilterIntensityForIndex(float intensity, final int index, final boolean shouldProcess) {
        if (this.mImageHandler != null) {
            this.mFilterIntensity = intensity;
            synchronized (this.mSettingIntensityLock) {
                if (this.mSettingIntensityCount <= 0) {
                    Log.i("libCGE_java", "Too fast, skipping...");
                    return;
                }
                this.mSettingIntensityCount--;
                queueEvent(new Runnable() {
                    public void run() {
                        if (ImageGLSurfaceView.this.mImageHandler == null) {
                            Log.e("libCGE_java", "set intensity after release!!");
                        } else {
                            ImageGLSurfaceView.this.mImageHandler.setFilterIntensityAtIndex(ImageGLSurfaceView.this.mFilterIntensity, index, shouldProcess);
                            if (shouldProcess) {
                                ImageGLSurfaceView.this.requestRender();
                            }
                        }
                        synchronized (ImageGLSurfaceView.this.mSettingIntensityLock) {
                            ImageGLSurfaceView imageGLSurfaceView = ImageGLSurfaceView.this;
                            imageGLSurfaceView.mSettingIntensityCount++;
                        }
                    }
                });
            }
        }
    }

    public void setFilterIntensity(float intensity) {
        if (this.mImageHandler != null) {
            this.mFilterIntensity = intensity;
            synchronized (this.mSettingIntensityLock) {
                if (this.mSettingIntensityCount <= 0) {
                    Log.i("libCGE_java", "Too fast, skipping...");
                    return;
                }
                this.mSettingIntensityCount--;
                queueEvent(new C15903());
            }
        }
    }

    public void setImageBitmap(final Bitmap bmp) {
        if (bmp != null) {
            if (this.mImageHandler == null) {
                Log.e("libCGE_java", "Handler not initialized!");
                return;
            }
            this.mImageWidth = bmp.getWidth();
            this.mImageHeight = bmp.getHeight();
            queueEvent(new Runnable() {
                public void run() {
                    if (ImageGLSurfaceView.this.mImageHandler == null) {
                        Log.e("libCGE_java", "set image after release!!");
                    } else if (ImageGLSurfaceView.this.mImageHandler.initWithBitmap(bmp)) {
                        ImageGLSurfaceView.this.calcViewport();
                        ImageGLSurfaceView.this.requestRender();
                    } else {
                        Log.e("libCGE_java", "setImageBitmap: init handler failed!");
                    }
                }
            });
        }
    }

    public void getResultBitmap(final QueryResultBitmapCallback callback) {
        if (callback != null) {
            queueEvent(new Runnable() {
                public void run() {
                    callback.get(ImageGLSurfaceView.this.mImageHandler.getResultBitmap());
                }
            });
        }
    }

    public ImageGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 8, 0);
        getHolder().setFormat(1);
        setRenderer(this);
        setRenderMode(0);
        Log.i("libCGE_java", "ImageGLSurfaceView Construct...");
    }

    public ImageGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 8, 0);
        getHolder().setFormat(1);
        setRenderer(this);
        setRenderMode(0);
        Log.i("libCGE_java", "ImageGLSurfaceView Construct...");
    }

    public void setSurfaceCreatedCallback(OnSurfaceCreatedCallback callback) {
        this.mSurfaceCreatedCallback = callback;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("libCGE_java", "ImageGLSurfaceView onSurfaceCreated...");
        GLES20.glDisable(2929);
        GLES20.glDisable(2960);
        this.mImageHandler = new CGEImageHandler();
        this.mImageHandler.setDrawerFlipScale(1.0f, -1.0f);
        if (this.mSurfaceCreatedCallback != null) {
            this.mSurfaceCreatedCallback.surfaceCreated();
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mViewWidth = width;
        this.mViewHeight = height;
        calcViewport();
    }

    public void onDrawFrame(GL10 gl) {
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glClear(16384);
        if (this.mImageHandler != null) {
            GLES20.glViewport(this.mRenderViewport.f34x, this.mRenderViewport.f35y, this.mRenderViewport.width, this.mRenderViewport.height);
            this.mImageHandler.drawResult();
        }
    }

    public void release() {
        if (this.mImageHandler != null) {
            queueEvent(new C15936());
        }
    }

    protected void calcViewport() {
        if (this.mDisplayMode == DisplayMode.DISPLAY_SCALE_TO_FILL) {
            this.mRenderViewport.f34x = 0;
            this.mRenderViewport.f35y = 0;
            this.mRenderViewport.width = this.mViewWidth;
            this.mRenderViewport.height = this.mViewHeight;
            return;
        }
        int w;
        int h;
        float scaling = ((float) this.mImageWidth) / ((float) this.mImageHeight);
        float s = scaling / (((float) this.mViewWidth) / ((float) this.mViewHeight));
        switch (this.mDisplayMode) {
            case DISPLAY_ASPECT_FILL:
                if (((double) s) <= 1.0d) {
                    w = this.mViewWidth;
                    h = (int) (((float) this.mViewWidth) / scaling);
                    break;
                }
                w = (int) (((float) this.mViewHeight) * scaling);
                h = this.mViewHeight;
                break;
            case DISPLAY_ASPECT_FIT:
                if (((double) s) >= 1.0d) {
                    w = this.mViewWidth;
                    h = (int) (((float) this.mViewWidth) / scaling);
                    break;
                }
                w = (int) (((float) this.mViewHeight) * scaling);
                h = this.mViewHeight;
                break;
            default:
                Log.i("libCGE_java", "Error occured, please check the code...");
                return;
        }
        this.mRenderViewport.width = w;
        this.mRenderViewport.height = h;
        this.mRenderViewport.f34x = (this.mViewWidth - w) / 2;
        this.mRenderViewport.f35y = (this.mViewHeight - h) / 2;
        Log.i("libCGE_java", String.format("View port: %d, %d, %d, %d", new Object[]{Integer.valueOf(this.mRenderViewport.f34x), Integer.valueOf(this.mRenderViewport.f35y), Integer.valueOf(this.mRenderViewport.width), Integer.valueOf(this.mRenderViewport.height)}));
    }
}
