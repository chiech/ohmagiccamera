package mobo.andro.apps.ohmagiccamera.editormodule;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.os.Build.VERSION;

import java.io.FileOutputStream;

public class ImageUtils {
    private ImageUtils() {
    }

    public static void resampleImageAndSaveToNewLocation(String pathInput, String pathOutput) throws Exception {
        resampleImage(pathInput, 800).compress(CompressFormat.PNG, 100, new FileOutputStream(pathOutput));
    }

    @SuppressLint({"UseValueOf"})
    public static Bitmap resampleImage(String path, int maxDim) throws Exception {
        Options bfo = new Options();
        bfo.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bfo);
        Options optsDownSample = new Options();
        optsDownSample.inSampleSize = getClosestResampleSize(bfo.outWidth, bfo.outHeight, maxDim);
        Bitmap bmpt = BitmapFactory.decodeFile(path, optsDownSample);
        Matrix m = new Matrix();
        if (bmpt.getWidth() > maxDim || bmpt.getHeight() > maxDim) {
            Options optsScale = getResampling(bmpt.getWidth(), bmpt.getHeight(), maxDim);
            m.postScale(((float) optsScale.outWidth) / ((float) bmpt.getWidth()), ((float) optsScale.outHeight) / ((float) bmpt.getHeight()));
        }
        if (new Integer(VERSION.SDK).intValue() > 4) {
            int rotation = ExifUtils.getExifRotation(path);
            if (rotation != 0) {
                m.postRotate((float) rotation);
            }
        }
        return Bitmap.createBitmap(bmpt, 0, 0, bmpt.getWidth(), bmpt.getHeight(), m, true);
    }

    private static Options getResampling(int cx, int cy, int max) {
        float scaleVal;
        Options bfo = new Options();
        if (cx > cy) {
            scaleVal = ((float) max) / ((float) cx);
        } else if (cy > cx) {
            scaleVal = ((float) max) / ((float) cy);
        } else {
            scaleVal = ((float) max) / ((float) cx);
        }
        bfo.outWidth = (int) ((((float) cx) * scaleVal) + 0.5f);
        bfo.outHeight = (int) ((((float) cy) * scaleVal) + 0.5f);
        return bfo;
    }

    private static int getClosestResampleSize(int cx, int cy, int maxDim) {
        int max = Math.max(cx, cy);
        int resample = 1;
        while (resample < Integer.MAX_VALUE) {
            if (resample * maxDim > max) {
                resample--;
                break;
            }
            resample++;
        }
        return resample > 0 ? resample : 1;
    }

    public static Options getBitmapDims(String path) throws Exception {
        Options bfo = new Options();
        bfo.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bfo);
        return bfo;
    }
}
