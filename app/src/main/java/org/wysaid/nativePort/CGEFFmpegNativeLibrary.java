package org.wysaid.nativePort;

import android.graphics.Bitmap;
import org.wysaid.nativePort.CGENativeLibrary.TextureBlendMode;

public class CGEFFmpegNativeLibrary {
    public static native void avRegisterAll();

    private static native boolean nativeGenerateVideoWithFilter(String str, String str2, String str3, float f, Bitmap bitmap, int i, float f2, boolean z);

    static {
        NativeLibraryLoader.load();
    }

    public static boolean generateVideoWithFilter(String outputFilename, String inputFilename, String filterConfig, float filterIntensity, Bitmap blendImage, TextureBlendMode blendMode, float blendIntensity, boolean mute) {
        return nativeGenerateVideoWithFilter(outputFilename, inputFilename, filterConfig, filterIntensity, blendImage, blendMode == null ? 0 : blendMode.ordinal(), blendIntensity, mute);
    }
}
