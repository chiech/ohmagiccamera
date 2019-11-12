package mobo.andro.apps.ohmagiccamera.editormodule;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import java.util.LinkedList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDarkenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDirectionalSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDivideBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHardLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLuminosityBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNormalBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageScreenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSoftLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSourceOverBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSubtractBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTwoInputFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWeakPixelInclusionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;
import mobo.andro.apps.ohmagiccamera.R;

public class GPUImageFilterTools {

    public static class FilterAdjuster {
        private final Adjuster<? extends GPUImageFilter> adjuster;

        private abstract class Adjuster<T extends GPUImageFilter> {
            private T filter;

            public abstract void adjust(int i);

            private Adjuster() {
            }

            public Adjuster<T> filter(GPUImageFilter filter) {
                this.filter = (T)filter;
                return this;
            }

            public T getFilter() {
                return this.filter;
            }

            protected float range(int percentage, float start, float end) {
                return (((end - start) * ((float) percentage)) / 100.0f) + start;
            }

            protected int range(int percentage, int start, int end) {
                return (((end - start) * percentage) / 100) + start;
            }
        }

        private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
            private BrightnessAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageBrightnessFilter) getFilter()).setBrightness(range(percentage, -1.0f, 1.0f));
            }
        }

        private class BulgeDistortionAdjuster extends Adjuster<GPUImageBulgeDistortionFilter> {
            private BulgeDistortionAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageBulgeDistortionFilter) getFilter()).setRadius(range(percentage, 0.0f, 1.0f));
                ((GPUImageBulgeDistortionFilter) getFilter()).setScale(range(percentage, -1.0f, 1.0f));
            }
        }

        private class ColorBalanceAdjuster extends Adjuster<GPUImageColorBalanceFilter> {
            private ColorBalanceAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageColorBalanceFilter) getFilter()).setMidtones(new float[]{range(percentage, 0.0f, 1.0f), range(percentage / 2, 0.0f, 1.0f), range(percentage / 3, 0.0f, 1.0f)});
            }
        }

        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            private ContrastAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageContrastFilter) getFilter()).setContrast(range(percentage, 0.0f, 2.0f));
            }
        }

        private class CrosshatchBlurAdjuster extends Adjuster<GPUImageCrosshatchFilter> {
            private CrosshatchBlurAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageCrosshatchFilter) getFilter()).setCrossHatchSpacing(range(percentage, 0.0f, 0.06f));
                ((GPUImageCrosshatchFilter) getFilter()).setLineWidth(range(percentage, 0.0f, 0.006f));
            }
        }

        private class DissolveBlendAdjuster extends Adjuster<GPUImageDissolveBlendFilter> {
            private DissolveBlendAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageDissolveBlendFilter) getFilter()).setMix(range(percentage, 0.0f, 1.0f));
            }
        }

        private class EmbossAdjuster extends Adjuster<GPUImageEmbossFilter> {
            private EmbossAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageEmbossFilter) getFilter()).setIntensity(range(percentage, 0.0f, 4.0f));
            }
        }

        private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
            private ExposureAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageExposureFilter) getFilter()).setExposure(range(percentage, -10.0f, 10.0f));
            }
        }

        private class GPU3x3TextureAdjuster extends Adjuster<GPUImage3x3TextureSamplingFilter> {
            private GPU3x3TextureAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImage3x3TextureSamplingFilter) getFilter()).setLineSize(range(percentage, 0.0f, 5.0f));
            }
        }

        private class GammaAdjuster extends Adjuster<GPUImageGammaFilter> {
            private GammaAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageGammaFilter) getFilter()).setGamma(range(percentage, 0.0f, 3.0f));
            }
        }

        private class GaussianBlurAdjuster extends Adjuster<GPUImageGaussianBlurFilter> {
            private GaussianBlurAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageGaussianBlurFilter) getFilter()).setBlurSize(range(percentage, 0.0f, 1.0f));
            }
        }

        private class GlassSphereAdjuster extends Adjuster<GPUImageGlassSphereFilter> {
            private GlassSphereAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageGlassSphereFilter) getFilter()).setRadius(range(percentage, 0.0f, 1.0f));
            }
        }

        private class HazeAdjuster extends Adjuster<GPUImageHazeFilter> {
            private HazeAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageHazeFilter) getFilter()).setDistance(range(percentage, -0.3f, 0.3f));
                ((GPUImageHazeFilter) getFilter()).setSlope(range(percentage, -0.3f, 0.3f));
            }
        }

        private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
            private HighlightShadowAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageHighlightShadowFilter) getFilter()).setShadows(range(percentage, 0.0f, 1.0f));
                ((GPUImageHighlightShadowFilter) getFilter()).setHighlights(range(percentage, 0.0f, 1.0f));
            }
        }

        private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
            private HueAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageHueFilter) getFilter()).setHue(range(percentage, 0.0f, 360.0f));
            }
        }

        private class LevelsMinMidAdjuster extends Adjuster<GPUImageLevelsFilter> {
            private LevelsMinMidAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageLevelsFilter) getFilter()).setMin(0.0f, range(percentage, 0.0f, 1.0f), 1.0f);
            }
        }

        private class MonochromeAdjuster extends Adjuster<GPUImageMonochromeFilter> {
            private MonochromeAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageMonochromeFilter) getFilter()).setIntensity(range(percentage, 0.0f, 1.0f));
            }
        }

        private class OpacityAdjuster extends Adjuster<GPUImageOpacityFilter> {
            private OpacityAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageOpacityFilter) getFilter()).setOpacity(range(percentage, 0.0f, 1.0f));
            }
        }

        private class PixelationAdjuster extends Adjuster<GPUImagePixelationFilter> {
            private PixelationAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImagePixelationFilter) getFilter()).setPixel(range(percentage, 1.0f, 100.0f));
            }
        }

        private class PosterizeAdjuster extends Adjuster<GPUImagePosterizeFilter> {
            private PosterizeAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImagePosterizeFilter) getFilter()).setColorLevels(range(percentage, 1, 50));
            }
        }

        private class RGBAdjuster extends Adjuster<GPUImageRGBFilter> {
            private RGBAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageRGBFilter) getFilter()).setRed(range(percentage, 0.0f, 1.0f));
            }
        }

        private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
            private SaturationAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageSaturationFilter) getFilter()).setSaturation(range(percentage, 0.0f, 2.0f));
            }
        }

        private class SepiaAdjuster extends Adjuster<GPUImageSepiaFilter> {
            private SepiaAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageSepiaFilter) getFilter()).setIntensity(range(percentage, 0.0f, 2.0f));
            }
        }

        private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
            private SharpnessAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageSharpenFilter) getFilter()).setSharpness(range(percentage, -4.0f, 4.0f));
            }
        }

        private class SobelAdjuster extends Adjuster<GPUImageSobelEdgeDetection> {
            private SobelAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageSobelEdgeDetection) getFilter()).setLineSize(range(percentage, 0.0f, 5.0f));
            }
        }

        private class SphereRefractionAdjuster extends Adjuster<GPUImageSphereRefractionFilter> {
            private SphereRefractionAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageSphereRefractionFilter) getFilter()).setRadius(range(percentage, 0.0f, 1.0f));
            }
        }

        private class SwirlAdjuster extends Adjuster<GPUImageSwirlFilter> {
            private SwirlAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageSwirlFilter) getFilter()).setAngle(range(percentage, 0.0f, 2.0f));
            }
        }

        private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
            private VignetteAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageVignetteFilter) getFilter()).setVignetteStart(range(percentage, 0.0f, 1.0f));
            }
        }

        private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
            private WhiteBalanceAdjuster() {
                super();
            }

            public void adjust(int percentage) {
                ((GPUImageWhiteBalanceFilter) getFilter()).setTemperature(range(percentage, 2000.0f, 8000.0f));
            }
        }

        public FilterAdjuster(GPUImageFilter filter) {
            if (filter instanceof GPUImageSharpenFilter) {
                this.adjuster = new SharpnessAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSepiaFilter) {
                this.adjuster = new SepiaAdjuster().filter(filter);
            } else if (filter instanceof GPUImageContrastFilter) {
                this.adjuster = new ContrastAdjuster().filter(filter);
            } else if (filter instanceof GPUImageGammaFilter) {
                this.adjuster = new GammaAdjuster().filter(filter);
            } else if (filter instanceof GPUImageBrightnessFilter) {
                this.adjuster = new BrightnessAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSobelEdgeDetection) {
                this.adjuster = new SobelAdjuster().filter(filter);
            } else if (filter instanceof GPUImageEmbossFilter) {
                this.adjuster = new EmbossAdjuster().filter(filter);
            } else if (filter instanceof GPUImage3x3TextureSamplingFilter) {
                this.adjuster = new GPU3x3TextureAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHueFilter) {
                this.adjuster = new HueAdjuster().filter(filter);
            } else if (filter instanceof GPUImagePosterizeFilter) {
                this.adjuster = new PosterizeAdjuster().filter(filter);
            } else if (filter instanceof GPUImagePixelationFilter) {
                this.adjuster = new PixelationAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSaturationFilter) {
                this.adjuster = new SaturationAdjuster().filter(filter);
            } else if (filter instanceof GPUImageExposureFilter) {
                this.adjuster = new ExposureAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHighlightShadowFilter) {
                this.adjuster = new HighlightShadowAdjuster().filter(filter);
            } else if (filter instanceof GPUImageMonochromeFilter) {
                this.adjuster = new MonochromeAdjuster().filter(filter);
            } else if (filter instanceof GPUImageOpacityFilter) {
                this.adjuster = new OpacityAdjuster().filter(filter);
            } else if (filter instanceof GPUImageRGBFilter) {
                this.adjuster = new RGBAdjuster().filter(filter);
            } else if (filter instanceof GPUImageWhiteBalanceFilter) {
                this.adjuster = new WhiteBalanceAdjuster().filter(filter);
            } else if (filter instanceof GPUImageVignetteFilter) {
                this.adjuster = new VignetteAdjuster().filter(filter);
            } else if (filter instanceof GPUImageDissolveBlendFilter) {
                this.adjuster = new DissolveBlendAdjuster().filter(filter);
            } else if (filter instanceof GPUImageGaussianBlurFilter) {
                this.adjuster = new GaussianBlurAdjuster().filter(filter);
            } else if (filter instanceof GPUImageCrosshatchFilter) {
                this.adjuster = new CrosshatchBlurAdjuster().filter(filter);
            } else if (filter instanceof GPUImageBulgeDistortionFilter) {
                this.adjuster = new BulgeDistortionAdjuster().filter(filter);
            } else if (filter instanceof GPUImageGlassSphereFilter) {
                this.adjuster = new GlassSphereAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHazeFilter) {
                this.adjuster = new HazeAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSphereRefractionFilter) {
                this.adjuster = new SphereRefractionAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSwirlFilter) {
                this.adjuster = new SwirlAdjuster().filter(filter);
            } else if (filter instanceof GPUImageColorBalanceFilter) {
                this.adjuster = new ColorBalanceAdjuster().filter(filter);
            } else if (filter instanceof GPUImageLevelsFilter) {
                this.adjuster = new LevelsMinMidAdjuster().filter(filter);
            } else {
                this.adjuster = null;
            }
        }

        public boolean canAdjust() {
            return this.adjuster != null;
        }

        public void adjust(int percentage) {
            if (this.adjuster != null) {
                this.adjuster.adjust(percentage);
            }
        }
    }

    private static class FilterList {
        public List<FilterType> filters;
        public List<String> names;

        private FilterList() {
            this.names = new LinkedList();
            this.filters = new LinkedList();
        }

        public void addFilter(String name, FilterType filter) {
            this.names.add(name);
            this.filters.add(filter);
        }
    }

    enum FilterType {
        CONTRAST,
        GRAYSCALE,
        SHARPEN,
        SEPIA,
        SOBEL_EDGE_DETECTION,
        THREE_X_THREE_CONVOLUTION,
        FILTER_GROUP,
        EMBOSS,
        POSTERIZE,
        GAMMA,
        BRIGHTNESS,
        INVERT,
        HUE,
        PIXELATION,
        SATURATION,
        EXPOSURE,
        HIGHLIGHT_SHADOW,
        MONOCHROME,
        OPACITY,
        RGB,
        WHITE_BALANCE,
        VIGNETTE,
        TONE_CURVE,
        BLEND_COLOR_BURN,
        BLEND_COLOR_DODGE,
        BLEND_DARKEN,
        BLEND_DIFFERENCE,
        BLEND_DISSOLVE,
        BLEND_EXCLUSION,
        BLEND_SOURCE_OVER,
        BLEND_HARD_LIGHT,
        BLEND_LIGHTEN,
        BLEND_ADD,
        BLEND_DIVIDE,
        BLEND_MULTIPLY,
        BLEND_OVERLAY,
        BLEND_SCREEN,
        BLEND_ALPHA,
        BLEND_COLOR,
        BLEND_HUE,
        BLEND_SATURATION,
        BLEND_LUMINOSITY,
        BLEND_LINEAR_BURN,
        BLEND_SOFT_LIGHT,
        BLEND_SUBTRACT,
        BLEND_CHROMA_KEY,
        BLEND_NORMAL,
        LOOKUP_AMATORKA,
        GAUSSIAN_BLUR,
        CROSSHATCH,
        BOX_BLUR,
        CGA_COLORSPACE,
        DILATION,
        KUWAHARA,
        RGB_DILATION,
        SKETCH,
        TOON,
        SMOOTH_TOON,
        BULGE_DISTORTION,
        GLASS_SPHERE,
        HAZE,
        LAPLACIAN,
        NON_MAXIMUM_SUPPRESSION,
        SPHERE_REFRACTION,
        SWIRL,
        WEAK_PIXEL_INCLUSION,
        FALSE_COLOR,
        COLOR_BALANCE,
        LEVELS_FILTER_MIN
    }

    public interface OnGpuImageFilterChosenListener {
        void onGpuImageFilterChosenListener(GPUImageFilter gPUImageFilter);
    }

    public static void showDialog(final Context context, final OnGpuImageFilterChosenListener listener) {
        final FilterList filters = new FilterList();
        filters.addFilter("Contrast", FilterType.CONTRAST);
        filters.addFilter("Invert", FilterType.INVERT);
        filters.addFilter("Pixelation", FilterType.PIXELATION);
        filters.addFilter("Hue", FilterType.HUE);
        filters.addFilter("Gamma", FilterType.GAMMA);
        filters.addFilter("Brightness", FilterType.BRIGHTNESS);
        filters.addFilter("Sepia", FilterType.SEPIA);
        filters.addFilter("Grayscale", FilterType.GRAYSCALE);
        filters.addFilter("Sharpness", FilterType.SHARPEN);
        filters.addFilter("Sobel Edge Detection", FilterType.SOBEL_EDGE_DETECTION);
        filters.addFilter("3x3 Convolution", FilterType.THREE_X_THREE_CONVOLUTION);
        filters.addFilter("Emboss", FilterType.EMBOSS);
        filters.addFilter("Posterize", FilterType.POSTERIZE);
        filters.addFilter("Grouped filters", FilterType.FILTER_GROUP);
        filters.addFilter("Saturation", FilterType.SATURATION);
        filters.addFilter("Exposure", FilterType.EXPOSURE);
        filters.addFilter("Highlight Shadow", FilterType.HIGHLIGHT_SHADOW);
        filters.addFilter("Monochrome", FilterType.MONOCHROME);
        filters.addFilter("Opacity", FilterType.OPACITY);
        filters.addFilter("RGB", FilterType.RGB);
        filters.addFilter("White Balance", FilterType.WHITE_BALANCE);
        filters.addFilter("Vignette", FilterType.VIGNETTE);
        filters.addFilter("ToneCurve", FilterType.TONE_CURVE);
        filters.addFilter("Blend (Difference)", FilterType.BLEND_DIFFERENCE);
        filters.addFilter("Blend (Source Over)", FilterType.BLEND_SOURCE_OVER);
        filters.addFilter("Blend (Color Burn)", FilterType.BLEND_COLOR_BURN);
        filters.addFilter("Blend (Color Dodge)", FilterType.BLEND_COLOR_DODGE);
        filters.addFilter("Blend (Darken)", FilterType.BLEND_DARKEN);
        filters.addFilter("Blend (Dissolve)", FilterType.BLEND_DISSOLVE);
        filters.addFilter("Blend (Exclusion)", FilterType.BLEND_EXCLUSION);
        filters.addFilter("Blend (Hard Light)", FilterType.BLEND_HARD_LIGHT);
        filters.addFilter("Blend (Lighten)", FilterType.BLEND_LIGHTEN);
        filters.addFilter("Blend (Add)", FilterType.BLEND_ADD);
        filters.addFilter("Blend (Divide)", FilterType.BLEND_DIVIDE);
        filters.addFilter("Blend (Multiply)", FilterType.BLEND_MULTIPLY);
        filters.addFilter("Blend (Overlay)", FilterType.BLEND_OVERLAY);
        filters.addFilter("Blend (Screen)", FilterType.BLEND_SCREEN);
        filters.addFilter("Blend (Alpha)", FilterType.BLEND_ALPHA);
        filters.addFilter("Blend (Color)", FilterType.BLEND_COLOR);
        filters.addFilter("Blend (Hue)", FilterType.BLEND_HUE);
        filters.addFilter("Blend (Saturation)", FilterType.BLEND_SATURATION);
        filters.addFilter("Blend (Luminosity)", FilterType.BLEND_LUMINOSITY);
        filters.addFilter("Blend (Linear Burn)", FilterType.BLEND_LINEAR_BURN);
        filters.addFilter("Blend (Soft Light)", FilterType.BLEND_SOFT_LIGHT);
        filters.addFilter("Blend (Subtract)", FilterType.BLEND_SUBTRACT);
        filters.addFilter("Blend (Chroma Key)", FilterType.BLEND_CHROMA_KEY);
        filters.addFilter("Blend (Normal)", FilterType.BLEND_NORMAL);
        filters.addFilter("Lookup (Amatorka)", FilterType.LOOKUP_AMATORKA);
        filters.addFilter("Gaussian Blur", FilterType.GAUSSIAN_BLUR);
        filters.addFilter("Crosshatch", FilterType.CROSSHATCH);
        filters.addFilter("Box Blur", FilterType.BOX_BLUR);
        filters.addFilter("CGA Color Space", FilterType.CGA_COLORSPACE);
        filters.addFilter("Dilation", FilterType.DILATION);
        filters.addFilter("Kuwahara", FilterType.KUWAHARA);
        filters.addFilter("RGB Dilation", FilterType.RGB_DILATION);
        filters.addFilter("Sketch", FilterType.SKETCH);
        filters.addFilter("Toon", FilterType.TOON);
        filters.addFilter("Smooth Toon", FilterType.SMOOTH_TOON);
        filters.addFilter("Bulge Distortion", FilterType.BULGE_DISTORTION);
        filters.addFilter("Glass Sphere", FilterType.GLASS_SPHERE);
        filters.addFilter("Haze", FilterType.HAZE);
        filters.addFilter("Laplacian", FilterType.LAPLACIAN);
        filters.addFilter("Non Maximum Suppression", FilterType.NON_MAXIMUM_SUPPRESSION);
        filters.addFilter("Sphere Refraction", FilterType.SPHERE_REFRACTION);
        filters.addFilter("Swirl", FilterType.SWIRL);
        filters.addFilter("Weak Pixel Inclusion", FilterType.WEAK_PIXEL_INCLUSION);
        filters.addFilter("False Color", FilterType.FALSE_COLOR);
        filters.addFilter("Color Balance", FilterType.COLOR_BALANCE);
        filters.addFilter("Levels Min (Mid Adjust)", FilterType.LEVELS_FILTER_MIN);
        Builder builder = new Builder(context);
        builder.setTitle("Choose a filter");
        builder.setItems((CharSequence[]) filters.names.toArray(new String[filters.names.size()]), new OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                listener.onGpuImageFilterChosenListener(GPUImageFilterTools.createFilterForType(context, (FilterType) filters.filters.get(item)));
            }
        });
        builder.create().show();
    }

    static GPUImageFilter createFilterForType(Context context, FilterType type) {
        switch (type) {
            case CONTRAST:
                return new GPUImageContrastFilter(2.0f);
            case GAMMA:
                return new GPUImageGammaFilter(2.0f);
            case INVERT:
                return new GPUImageColorInvertFilter();
            case PIXELATION:
                return new GPUImagePixelationFilter();
            case HUE:
                return new GPUImageHueFilter(90.0f);
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter(1.5f);
            case GRAYSCALE:
                return new GPUImageGrayscaleFilter();
            case SEPIA:
                return new GPUImageSepiaFilter();
            case SHARPEN:
                GPUImageFilter sharpness = new GPUImageSharpenFilter();
     //           sharpness.setSharpness(2.0f);
                return sharpness;
            case SOBEL_EDGE_DETECTION:
                return new GPUImageSobelEdgeDetection();
            case THREE_X_THREE_CONVOLUTION:
                GPUImageFilter convolution = new GPUImage3x3ConvolutionFilter();
     //           convolution.setConvolutionKernel(new float[]{-1.0f, 0.0f, 1.0f, -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f});
                return convolution;
            case EMBOSS:
                return new GPUImageEmbossFilter();
            case POSTERIZE:
                return new GPUImagePosterizeFilter();
            case FILTER_GROUP:
                List<GPUImageFilter> filters = new LinkedList();
                filters.add(new GPUImageContrastFilter());
                filters.add(new GPUImageDirectionalSobelEdgeDetectionFilter());
                filters.add(new GPUImageGrayscaleFilter());
                return new GPUImageFilterGroup(filters);
            case SATURATION:
                return new GPUImageSaturationFilter(1.0f);
            case EXPOSURE:
                return new GPUImageExposureFilter(0.0f);
            case HIGHLIGHT_SHADOW:
                return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case MONOCHROME:
                return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case OPACITY:
                return new GPUImageOpacityFilter(1.0f);
            case RGB:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);
            case WHITE_BALANCE:
                return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
            case VIGNETTE:
                PointF centerPoint = new PointF();
                centerPoint.x = 0.5f;
                centerPoint.y = 0.5f;
                return new GPUImageVignetteFilter(centerPoint, new float[]{0.0f, 0.0f, 0.0f}, 0.3f, 0.75f);
            case TONE_CURVE:
                GPUImageFilter toneCurveFilter = new GPUImageToneCurveFilter();
 //               toneCurveFilter.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.tone_cuver_sample));
                return toneCurveFilter;
            case BLEND_DIFFERENCE:
                return createBlendFilter(context, GPUImageDifferenceBlendFilter.class);
            case BLEND_SOURCE_OVER:
                return createBlendFilter(context, GPUImageSourceOverBlendFilter.class);
            case BLEND_COLOR_BURN:
                return createBlendFilter(context, GPUImageColorBurnBlendFilter.class);
            case BLEND_COLOR_DODGE:
                return createBlendFilter(context, GPUImageColorDodgeBlendFilter.class);
            case BLEND_DARKEN:
                return createBlendFilter(context, GPUImageDarkenBlendFilter.class);
            case BLEND_DISSOLVE:
                return createBlendFilter(context, GPUImageDissolveBlendFilter.class);
            case BLEND_EXCLUSION:
                return createBlendFilter(context, GPUImageExclusionBlendFilter.class);
            case BLEND_HARD_LIGHT:
                return createBlendFilter(context, GPUImageHardLightBlendFilter.class);
            case BLEND_LIGHTEN:
                return createBlendFilter(context, GPUImageLightenBlendFilter.class);
            case BLEND_ADD:
                return createBlendFilter(context, GPUImageAddBlendFilter.class);
            case BLEND_DIVIDE:
                return createBlendFilter(context, GPUImageDivideBlendFilter.class);
            case BLEND_MULTIPLY:
                return createBlendFilter(context, GPUImageMultiplyBlendFilter.class);
            case BLEND_OVERLAY:
                return createBlendFilter(context, GPUImageOverlayBlendFilter.class);
            case BLEND_SCREEN:
                return createBlendFilter(context, GPUImageScreenBlendFilter.class);
            case BLEND_ALPHA:
                return createBlendFilter(context, GPUImageAlphaBlendFilter.class);
            case BLEND_COLOR:
                return createBlendFilter(context, GPUImageColorBlendFilter.class);
            case BLEND_HUE:
                return createBlendFilter(context, GPUImageHueBlendFilter.class);
            case BLEND_SATURATION:
                return createBlendFilter(context, GPUImageSaturationBlendFilter.class);
            case BLEND_LUMINOSITY:
                return createBlendFilter(context, GPUImageLuminosityBlendFilter.class);
            case BLEND_LINEAR_BURN:
                return createBlendFilter(context, GPUImageLinearBurnBlendFilter.class);
            case BLEND_SOFT_LIGHT:
                return createBlendFilter(context, GPUImageSoftLightBlendFilter.class);
            case BLEND_SUBTRACT:
                return createBlendFilter(context, GPUImageSubtractBlendFilter.class);
            case BLEND_CHROMA_KEY:
                return createBlendFilter(context, GPUImageChromaKeyBlendFilter.class);
            case BLEND_NORMAL:
                return createBlendFilter(context, GPUImageNormalBlendFilter.class);
            case LOOKUP_AMATORKA:
                GPUImageFilter amatorka = new GPUImageLookupFilter();
//                amatorka.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.lookup_amatorka));
                return amatorka;
            case GAUSSIAN_BLUR:
                return new GPUImageGaussianBlurFilter();
            case CROSSHATCH:
                return new GPUImageCrosshatchFilter();
            case BOX_BLUR:
                return new GPUImageBoxBlurFilter();
            case CGA_COLORSPACE:
                return new GPUImageCGAColorspaceFilter();
            case DILATION:
                return new GPUImageDilationFilter();
            case KUWAHARA:
                return new GPUImageKuwaharaFilter();
            case RGB_DILATION:
                return new GPUImageRGBDilationFilter();
            case SKETCH:
                return new GPUImageSketchFilter();
            case TOON:
                return new GPUImageToonFilter();
            case SMOOTH_TOON:
                return new GPUImageSmoothToonFilter();
            case BULGE_DISTORTION:
                return new GPUImageBulgeDistortionFilter();
            case GLASS_SPHERE:
                return new GPUImageGlassSphereFilter();
            case HAZE:
                return new GPUImageHazeFilter();
            case LAPLACIAN:
                return new GPUImageLaplacianFilter();
            case NON_MAXIMUM_SUPPRESSION:
                return new GPUImageNonMaximumSuppressionFilter();
            case SPHERE_REFRACTION:
                return new GPUImageSphereRefractionFilter();
            case SWIRL:
                return new GPUImageSwirlFilter();
            case WEAK_PIXEL_INCLUSION:
                return new GPUImageWeakPixelInclusionFilter();
            case FALSE_COLOR:
                return new GPUImageFalseColorFilter();
            case COLOR_BALANCE:
                return new GPUImageColorBalanceFilter();
            case LEVELS_FILTER_MIN:
                GPUImageFilter levelsFilter = new GPUImageLevelsFilter();
//                levelsFilter.setMin(0.0f, 3.0f, 1.0f);
                return levelsFilter;
            default:
                throw new IllegalStateException("No filter of that type!");
        }
    }

    private static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> filterClass) {
        try {
            GPUImageTwoInputFilter filter = (GPUImageTwoInputFilter) filterClass.newInstance();
            filter.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.playstore));
            return filter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
