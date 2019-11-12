package org.wysaid.nativePort;

public class NativeLibraryLoader {
    private static boolean mLibraryLoaded = false;

    public static void load() {
        if (!mLibraryLoaded) {
            mLibraryLoaded = true;
            System.loadLibrary("ffmpeg");
            System.loadLibrary("CGE");
            System.loadLibrary("CGEExt");
            CGEFFmpegNativeLibrary.avRegisterAll();
        }
    }
}
