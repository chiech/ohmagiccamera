package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import org.wysaid.nativePort.CGEImageHandler;
import org.wysaid.view.ImageGLSurfaceView;
import org.wysaid.view.ImageGLSurfaceView.DisplayMode;
import org.wysaid.view.ImageGLSurfaceView.QueryResultBitmapCallback;

public class GLFilterImageView extends AppCompatImageView {
    private String BASIC_FILTER_CONFIG;
    private AdjustConfig mActiveConfig;
    AdjustConfig[] mAdjustConfigs;
    private String mCurConfig;
    private float mCurConfigProg;
    private ImageGLSurfaceView mImageView;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.GLFilterImageView$1 */
    class C03521 implements QueryResultBitmapCallback {
        C03521() {
        }

        public void get(final Bitmap bmp) {
            GLFilterImageView.this.post(new Runnable() {
                public void run() {
                    GLFilterImageView.this.setImageBitmap(bmp);
                    GLFilterImageView.this.requestLayout();
                    GLFilterImageView.this.postInvalidate();
                }
            });
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.GLFilterImageView$2 */
    class C03542 implements QueryResultBitmapCallback {
        C03542() {
        }

        public void get(final Bitmap bmp) {
            GLFilterImageView.this.post(new Runnable() {
                public void run() {
                    GLFilterImageView.this.setImageBitmap(bmp);
                    GLFilterImageView.this.requestLayout();
                    GLFilterImageView.this.postInvalidate();
                }
            });
        }
    }

    class AdjustConfig {
        public int index;
        public float intensity;
        public float maxValue;
        public float minValue;
        public float originValue;
        public float slierIntensity = 0.5f;

        /* renamed from: mobo.andro.apps.camera.CollageMaker.GLFilterImageView$AdjustConfig$1 */
        class C03561 implements QueryResultBitmapCallback {
            C03561() {
            }

            public void get(final Bitmap bmp) {
                GLFilterImageView.this.post(new Runnable() {
                    public void run() {
                        GLFilterImageView.this.setImageBitmap(bmp);
                        GLFilterImageView.this.requestLayout();
                        GLFilterImageView.this.postInvalidate();
                    }
                });
            }
        }

        public AdjustConfig(int _index, float _minValue, float _originValue, float _maxValue) {
            this.index = _index;
            this.minValue = _minValue;
            this.originValue = _originValue;
            this.maxValue = _maxValue;
            this.intensity = _originValue;
        }

        protected float calcIntensity(float _intensity) {
            if (_intensity <= 0.0f) {
                return this.minValue;
            }
            if (_intensity >= 1.0f) {
                return this.maxValue;
            }
            if (_intensity <= 0.5f) {
                return this.minValue + (((this.originValue - this.minValue) * _intensity) * 2.0f);
            }
            return this.maxValue + (((this.originValue - this.maxValue) * (1.0f - _intensity)) * 2.0f);
        }

        public void setIntensity(float _intensity, boolean shouldProcess) {
            if (GLFilterImageView.this.mImageView != null) {
                this.slierIntensity = _intensity;
                this.intensity = calcIntensity(_intensity);
                GLFilterImageView.this.mImageView.setFilterIntensity(this.intensity);
                GLFilterImageView.this.mImageView.getResultBitmap(new C03561());
            }
        }
    }

    public GLFilterImageView(Context context) {
        super(context);
        this.BASIC_FILTER_CONFIG = Constants.BASIC_FILTER_CONFIG;
        this.mActiveConfig = null;
        this.mCurConfig = "";
        this.mCurConfigProg = 1.0f;
        this.mAdjustConfigs = new AdjustConfig[]{new AdjustConfig(0, -1.0f, 0.0f, 1.0f), new AdjustConfig(1, 0.1f, 1.0f, 3.0f), new AdjustConfig(2, 0.0f, 1.0f, 3.0f), new AdjustConfig(3, 0.0f, 0.0f, 10.0f), new AdjustConfig(4, -1.0f, 0.0f, 1.0f)};
    }

    public GLFilterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.BASIC_FILTER_CONFIG = Constants.BASIC_FILTER_CONFIG;
        this.mActiveConfig = null;
        this.mCurConfig = "";
        this.mCurConfigProg = 1.0f;
        this.mAdjustConfigs = new AdjustConfig[]{new AdjustConfig(0, -1.0f, 0.0f, 1.0f), new AdjustConfig(1, 0.1f, 1.0f, 3.0f), new AdjustConfig(2, 0.0f, 1.0f, 3.0f), new AdjustConfig(3, 0.0f, 0.0f, 10.0f), new AdjustConfig(4, -1.0f, 0.0f, 1.0f)};
    }

    public GLFilterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.BASIC_FILTER_CONFIG = Constants.BASIC_FILTER_CONFIG;
        this.mActiveConfig = null;
        this.mCurConfig = "";
        this.mCurConfigProg = 1.0f;
        this.mAdjustConfigs = new AdjustConfig[]{new AdjustConfig(0, -1.0f, 0.0f, 1.0f), new AdjustConfig(1, 0.1f, 1.0f, 3.0f), new AdjustConfig(2, 0.0f, 1.0f, 3.0f), new AdjustConfig(3, 0.0f, 0.0f, 10.0f), new AdjustConfig(4, -1.0f, 0.0f, 1.0f)};
    }

    public void setImageGLSurfaceView(ImageGLSurfaceView glSurfaceView) {
        this.mImageView = glSurfaceView;
        this.mImageView.setDisplayMode(DisplayMode.DISPLAY_ASPECT_FIT);
        this.mImageView.setFilterWithConfig(this.BASIC_FILTER_CONFIG);
    }

    public void setGLBitmap(Bitmap bitmap) {
        this.mImageView.setImageBitmap(bitmap);
    }

    public String getmCurConfig() {
        return this.mCurConfig;
    }

    public float getmCurConfigProg() {
        return this.mCurConfigProg;
    }

    public int setActiveConfig(AdjustConfig config, int max) {
        this.mActiveConfig = config;
        return (int) (config.slierIntensity * ((float) max));
    }

    public int setActiveConfigIndex(int index, int max) {
        this.mActiveConfig = this.mAdjustConfigs[index];
        return (int) (this.mActiveConfig.slierIntensity * ((float) max));
    }

    public void setEffectProgress(int progress, int max) {
        if (this.mImageView != null && this.mActiveConfig != null) {
            this.mActiveConfig.setIntensity(((float) progress) / ((float) max), true);
        }
    }

    public void setFilterConfig(String config) {
        if (this.mImageView != null) {
            this.mCurConfig = config;
            this.mImageView.setFilterWithConfig(config);
            this.mCurConfigProg = 0.5f;
            this.mImageView.setFilterIntensity(this.mCurConfigProg);
            this.mImageView.getResultBitmap(new C03521());
        }
    }

    public void setFilterProgress(float progress) {
        if (this.mImageView != null) {
            this.mCurConfigProg = progress;
            this.mImageView.setFilterIntensity(progress);
            this.mImageView.getResultBitmap(new C03542());
        }
    }

    public void resetAllConfigs() {
        CGEImageHandler handler = this.mImageView.getImageHandler();
        for (AdjustConfig config : this.mAdjustConfigs) {
            handler.setFilterIntensityAtIndex(config.originValue, config.index, false);
        }
        handler.revertImage();
        handler.processFilters();
        this.mImageView.requestRender();
    }
}
