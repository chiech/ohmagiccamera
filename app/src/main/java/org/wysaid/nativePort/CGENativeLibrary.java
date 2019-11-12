package org.wysaid.nativePort;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.wysaid.common.Common;

public class CGENativeLibrary {
    static Object callbackArg;
    static LoadImageCallback loadImageCallback;

    public interface LoadImageCallback {
        Bitmap loadImage(String str, Object obj);

        void loadImageOK(Bitmap bitmap, Object obj);
    }

    public enum BlendFilterType {
        BLEND_NORMAL,
        BLEND_KEEP_RATIO,
        BLEND_TILE
    }

    public enum TextureBlendMode {
        CGE_BLEND_MIX,
        CGE_BLEND_DISSOLVE,
        CGE_BLEND_DARKEN,
        CGE_BLEND_MULTIPLY,
        CGE_BLEND_COLORBURN,
        CGE_BLEND_LINEARBURN,
        CGE_BLEND_DARKER_COLOR,
        CGE_BLEND_LIGHTEN,
        CGE_BLEND_SCREEN,
        CGE_BLEND_COLORDODGE,
        CGE_BLEND_LINEARDODGE,
        CGE_BLEND_LIGHTERCOLOR,
        CGE_BLEND_OVERLAY,
        CGE_BLEND_SOFTLIGHT,
        CGE_BLEND_HARDLIGHT,
        CGE_BLEND_VIVIDLIGHT,
        CGE_BLEND_LINEARLIGHT,
        CGE_BLEND_PINLIGHT,
        CGE_BLEND_HARDMIX,
        CGE_BLEND_DIFFERENCE,
        CGE_BLEND_EXCLUDE,
        CGE_BLEND_SUBTRACT,
        CGE_BLEND_DIVIDE,
        CGE_BLEND_HUE,
        CGE_BLEND_SATURATION,
        CGE_BLEND_COLOR,
        CGE_BLEND_LUMINOSITY,
        CGE_BLEND_ADD,
        CGE_BLEND_ADDREV,
        CGE_BLEND_COLORBW,
        CGE_BLEND_TYPE_MAX_NUM
    }

    public static class TextureResult {
        int height;
        int texID;
        int width;
    }

    public static native long cgeCreateBlendFilter(int i, int i2, int i3, int i4, int i5, float f);

    public static native long cgeCreateCustomNativeFilter(int i, float f, boolean z);

    public static native long cgeCreateFilterWithConfig(String str, float f);

    public static native void cgeDeleteFilterWithAddress(long j);

    public static native Bitmap cgeFilterImageWithCustomFilter(Bitmap bitmap, int i, float f, boolean z, boolean z2);

    public static native Bitmap cgeFilterImage_MultipleEffects(Bitmap bitmap, String str, float f);

    public static native void cgeFilterImage_MultipleEffectsWriteBack(Bitmap bitmap, String str, float f);

    public static native int cgeGetCustomFilterNum();

    static {
        NativeLibraryLoader.load();
    }

    public static void setLoadImageCallback(LoadImageCallback callback, Object arg) {
        loadImageCallback = callback;
        callbackArg = arg;
    }

    public static TextureResult loadTextureByName(String sourceName) {
        if (loadImageCallback == null) {
            Log.i("libCGE_java", "The loading callback is not set!");
            return null;
        }
        Bitmap bmp = loadImageCallback.loadImage(sourceName, callbackArg);
        if (bmp == null) {
            return null;
        }
        TextureResult result = loadTextureByBitmap(bmp);
        loadImageCallback.loadImageOK(bmp, callbackArg);
        return result;
    }

    public static TextureResult loadTextureByBitmap(Bitmap bmp) {
        if (bmp == null) {
            return null;
        }
        TextureResult result = new TextureResult();
        result.texID = Common.genNormalTextureID(bmp);
        result.width = bmp.getWidth();
        result.height = bmp.getHeight();
        return result;
    }

    public static TextureResult loadTextureByFile(String fileName) {
        Bitmap bmp = BitmapFactory.decodeFile(fileName);
        TextureResult result = loadTextureByBitmap(bmp);
        bmp.recycle();
        return result;
    }

    public static Bitmap filterImage_MultipleEffects(Bitmap bmp, String config, float intensity) {
        return (config == null || config.length() == 0) ? bmp : cgeFilterImage_MultipleEffects(bmp, config, intensity);
    }

    public static void filterImage_MultipleEffectsWriteBack(Bitmap bmp, String config, float intensity) {
        if (config != null && config.length() != 0) {
            cgeFilterImage_MultipleEffectsWriteBack(bmp, config, intensity);
        }
    }

    public static long createBlendFilter(TextureBlendMode blendMode, int texID, int texWidth, int texHeight, BlendFilterType blendFilterType, float intensity) {
        return cgeCreateBlendFilter(blendMode.ordinal(), texID, texWidth, texHeight, blendFilterType.ordinal(), intensity);
    }
}
