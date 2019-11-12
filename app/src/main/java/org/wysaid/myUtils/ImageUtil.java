package org.wysaid.myUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil extends FileUtil {

    public static class FaceRects {
        public Face[] faces;
        public int numOfFaces;
    }

    public static String saveBitmap(Bitmap bmp) {
        String path = FileUtil.getPath();
        return saveBitmap(bmp, path + "/" + System.currentTimeMillis() + ".jpg");
    }

    public static String saveBitmap(Bitmap bmp, String filename) {
        Log.i("libCGE_java", "saving Bitmap : " + filename);
        try {
            BufferedOutputStream bufferOutStream = new BufferedOutputStream(new FileOutputStream(filename));
            bmp.compress(CompressFormat.JPEG, 100, bufferOutStream);
            bufferOutStream.flush();
            bufferOutStream.close();
            Log.i("libCGE_java", "Bitmap " + filename + " saved!");
            return filename;
        } catch (IOException e) {
            Log.e("libCGE_java", "Err when saving bitmap...");
            e.printStackTrace();
            return null;
        }
    }

    public static FaceRects findFaceByBitmap(Bitmap bmp) {
        return findFaceByBitmap(bmp, 1);
    }

    public static FaceRects findFaceByBitmap(Bitmap bmp, int maxFaces) {
        if (bmp == null) {
            Log.e("libCGE_java", "Invalid Bitmap for Face Detection!");
            return null;
        }
        Bitmap newBitmap = bmp;
        if (newBitmap.getConfig() != Config.RGB_565) {
            newBitmap = newBitmap.copy(Config.RGB_565, false);
        }
        FaceRects rects = new FaceRects();
        rects.faces = new Face[maxFaces];
        try {
            rects.numOfFaces = new FaceDetector(newBitmap.getWidth(), newBitmap.getHeight(), maxFaces).findFaces(newBitmap, rects.faces);
            if (newBitmap == bmp) {
                return rects;
            }
            newBitmap.recycle();
            return rects;
        } catch (Exception e) {
            Log.e("libCGE_java", "findFaceByBitmap error: " + e.getMessage());
            return null;
        }
    }
}
