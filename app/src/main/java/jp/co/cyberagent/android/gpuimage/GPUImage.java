package jp.co.cyberagent.android.gpuimage;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.view.WindowManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class GPUImage {
    private final Context mContext;
    private Bitmap mCurrentBitmap;
    private GPUImageFilter mFilter;
    private GLSurfaceView mGlSurfaceView;
    private final GPUImageRenderer mRenderer;
    private ScaleType mScaleType = ScaleType.CENTER_CROP;

    /* renamed from: jp.co.cyberagent.android.gpuimage.GPUImage$1 */
    class C15401 implements Runnable {
        C15401() {
        }

        public void run() {
            synchronized (GPUImage.this.mFilter) {
                GPUImage.this.mFilter.destroy();
                GPUImage.this.mFilter.notify();
            }
        }
    }

    private abstract class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
        private final GPUImage mGPUImage;
        private int mOutputHeight;
        private int mOutputWidth;

        protected abstract Bitmap decode(Options options);

        protected abstract int getImageOrientation() throws IOException;

        public LoadImageTask(GPUImage gpuImage) {
            this.mGPUImage = gpuImage;
        }

        protected Bitmap doInBackground(Void... params) {
            if (GPUImage.this.mRenderer != null && GPUImage.this.mRenderer.getFrameWidth() == 0) {
                try {
                    synchronized (GPUImage.this.mRenderer.mSurfaceChangedWaiter) {
                        GPUImage.this.mRenderer.mSurfaceChangedWaiter.wait(3000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.mOutputWidth = GPUImage.this.getOutputWidth();
            this.mOutputHeight = GPUImage.this.getOutputHeight();
            return loadResizedImage();
        }

        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            this.mGPUImage.deleteImage();
            this.mGPUImage.setImage(bitmap);
        }

        private Bitmap loadResizedImage() {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            decode(options);
            int scale = 1;
            while (true) {
                boolean z;
                if (options.outWidth / scale > this.mOutputWidth) {
                    z = true;
                } else {
                    z = false;
                }
                if (!checkSize(z, options.outHeight / scale > this.mOutputHeight)) {
                    break;
                }
                scale++;
            }
            scale--;
            if (scale < 1) {
                scale = 1;
            }
            options = new Options();
            options.inSampleSize = scale;
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inTempStorage = new byte[32768];
            Bitmap bitmap = decode(options);
            if (bitmap == null) {
                return null;
            }
            return scaleBitmap(rotateImage(bitmap));
        }

        private Bitmap scaleBitmap(Bitmap bitmap) {
            int[] newSize = getScaleSize(bitmap.getWidth(), bitmap.getHeight());
            Bitmap workBitmap = Bitmap.createScaledBitmap(bitmap, newSize[0], newSize[1], true);
            if (workBitmap != bitmap) {
                bitmap.recycle();
                bitmap = workBitmap;
                System.gc();
            }
            if (GPUImage.this.mScaleType != ScaleType.CENTER_CROP) {
                return bitmap;
            }
            int diffWidth = newSize[0] - this.mOutputWidth;
            int diffHeight = newSize[1] - this.mOutputHeight;
            workBitmap = Bitmap.createBitmap(bitmap, diffWidth / 2, diffHeight / 2, newSize[0] - diffWidth, newSize[1] - diffHeight);
            if (workBitmap == bitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return workBitmap;
        }

        private int[] getScaleSize(int width, int height) {
            float newHeight;
            float newWidth;
            float withRatio = ((float) width) / ((float) this.mOutputWidth);
            float heightRatio = ((float) height) / ((float) this.mOutputHeight);
            boolean adjustWidth = GPUImage.this.mScaleType == ScaleType.CENTER_CROP ? withRatio > heightRatio : withRatio < heightRatio;
            if (adjustWidth) {
                newHeight = (float) this.mOutputHeight;
                newWidth = (newHeight / ((float) height)) * ((float) width);
            } else {
                newWidth = (float) this.mOutputWidth;
                newHeight = (newWidth / ((float) width)) * ((float) height);
            }
            return new int[]{Math.round(newWidth), Math.round(newHeight)};
        }

        private boolean checkSize(boolean widthBigger, boolean heightBigger) {
            boolean z = false;
            if (GPUImage.this.mScaleType != ScaleType.CENTER_CROP) {
                if (widthBigger || heightBigger) {
                    z = true;
                }
                return z;
            } else if (widthBigger && heightBigger) {
                return true;
            } else {
                return false;
            }
        }

        private Bitmap rotateImage(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            Bitmap rotatedBitmap = bitmap;
            try {
                int orientation = getImageOrientation();
                if (orientation == 0) {
                    return rotatedBitmap;
                }
                Matrix matrix = new Matrix();
                matrix.postRotate((float) orientation);
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return rotatedBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return rotatedBitmap;
            }
        }
    }

    private class LoadImageFileTask extends LoadImageTask {
        private final File mImageFile;

        public LoadImageFileTask(GPUImage gpuImage, File file) {
            super(gpuImage);
            this.mImageFile = file;
        }

        protected Bitmap decode(Options options) {
            return BitmapFactory.decodeFile(this.mImageFile.getAbsolutePath(), options);
        }

        protected int getImageOrientation() throws IOException {
            switch (new ExifInterface(this.mImageFile.getAbsolutePath()).getAttributeInt("Orientation", 1)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        }
    }

    private class LoadImageUriTask extends LoadImageTask {
        private final Uri mUri;

        public LoadImageUriTask(GPUImage gpuImage, Uri uri) {
            super(gpuImage);
            this.mUri = uri;
        }

        protected Bitmap decode(Options options) {
            try {
                InputStream inputStream;
                if (this.mUri.getScheme().startsWith("http") || this.mUri.getScheme().startsWith("https")) {
                    inputStream = new URL(this.mUri.toString()).openStream();
                } else {
                    inputStream = GPUImage.this.mContext.getContentResolver().openInputStream(this.mUri);
                }
                return BitmapFactory.decodeStream(inputStream, null, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected int getImageOrientation() throws IOException {
            Cursor cursor = GPUImage.this.mContext.getContentResolver().query(this.mUri, new String[]{"orientation"}, null, null, null);
            if (cursor == null || cursor.getCount() != 1) {
                return 0;
            }
            cursor.moveToFirst();
            int orientation = cursor.getInt(0);
            cursor.close();
            return orientation;
        }
    }

    public interface OnPictureSavedListener {
        void onPictureSaved(Uri uri);
    }

    public interface ResponseListener<T> {
        void response(T t);
    }

    @Deprecated
    private class SaveTask extends AsyncTask<Void, Void, Void> {
        private final Bitmap mBitmap;
        private final String mFileName;
        private final String mFolderName;
        private final Handler mHandler = new Handler();
        private final OnPictureSavedListener mListener;

        /* renamed from: jp.co.cyberagent.android.gpuimage.GPUImage$SaveTask$1 */
        class C15421 implements OnScanCompletedListener {
            C15421() {
            }

            public void onScanCompleted(String path, final Uri uri) {
                if (SaveTask.this.mListener != null) {
                    SaveTask.this.mHandler.post(new Runnable() {
                        public void run() {
                            SaveTask.this.mListener.onPictureSaved(uri);
                        }
                    });
                }
            }
        }

        public SaveTask(Bitmap bitmap, String folderName, String fileName, OnPictureSavedListener listener) {
            this.mBitmap = bitmap;
            this.mFolderName = folderName;
            this.mFileName = fileName;
            this.mListener = listener;
        }

        protected Void doInBackground(Void... params) {
            saveImage(this.mFolderName, this.mFileName, GPUImage.this.getBitmapWithFilterApplied(this.mBitmap));
            return null;
        }

        private void saveImage(String folderName, String fileName, Bitmap image) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName + "/" + fileName);
            try {
                file.getParentFile().mkdirs();
                image.compress(CompressFormat.JPEG, 80, new FileOutputStream(file));
                MediaScannerConnection.scanFile(GPUImage.this.mContext, new String[]{file.toString()}, null, new C15421());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public enum ScaleType {
        CENTER_INSIDE,
        CENTER_CROP
    }

    public GPUImage(Context context) {
        if (supportsOpenGLES2(context)) {
            this.mContext = context;
            this.mFilter = new GPUImageFilter();
            this.mRenderer = new GPUImageRenderer(this.mFilter);
            return;
        }
        throw new IllegalStateException("OpenGL ES 2.0 is not supported on this phone.");
    }

    private boolean supportsOpenGLES2(Context context) {
        return ((ActivityManager) context.getSystemService("activity")).getDeviceConfigurationInfo().reqGlEsVersion >= 131072;
    }

    public void setGLSurfaceView(GLSurfaceView view) {
        this.mGlSurfaceView = view;
        this.mGlSurfaceView.setEGLContextClientVersion(2);
        this.mGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.mGlSurfaceView.getHolder().setFormat(1);
        this.mGlSurfaceView.setRenderer(this.mRenderer);
        this.mGlSurfaceView.setRenderMode(0);
        this.mGlSurfaceView.requestRender();
    }

    public void setBackgroundColor(float red, float green, float blue) {
        this.mRenderer.setBackgroundColor(red, green, blue);
    }

    public void requestRender() {
        if (this.mGlSurfaceView != null) {
            this.mGlSurfaceView.requestRender();
        }
    }

    public void setUpCamera(Camera camera) {
        setUpCamera(camera, 0, false, false);
    }

    public void setUpCamera(Camera camera, int degrees, boolean flipHorizontal, boolean flipVertical) {
        this.mGlSurfaceView.setRenderMode(1);
        if (VERSION.SDK_INT > 10) {
            setUpCameraGingerbread(camera);
        } else {
            camera.setPreviewCallback(this.mRenderer);
            camera.startPreview();
        }
        Rotation rotation = Rotation.NORMAL;
        switch (degrees) {
            case 90:
                rotation = Rotation.ROTATION_90;
                break;
            case 180:
                rotation = Rotation.ROTATION_180;
                break;
            case 270:
                rotation = Rotation.ROTATION_270;
                break;
        }
        this.mRenderer.setRotationCamera(rotation, flipHorizontal, flipVertical);
    }

    @TargetApi(11)
    private void setUpCameraGingerbread(Camera camera) {
        this.mRenderer.setUpSurfaceTexture(camera);
    }

    public void setFilter(GPUImageFilter filter) {
        this.mFilter = filter;
        this.mRenderer.setFilter(this.mFilter);
        requestRender();
    }

    public void setImage(Bitmap bitmap) {
        this.mCurrentBitmap = bitmap;
        this.mRenderer.setImageBitmap(bitmap, false);
        requestRender();
    }

    public void setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
        this.mRenderer.setScaleType(scaleType);
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void setRotation(Rotation rotation) {
        this.mRenderer.setRotation(rotation);
    }

    public void setRotation(Rotation rotation, boolean flipHorizontal, boolean flipVertical) {
        this.mRenderer.setRotation(rotation, flipHorizontal, flipVertical);
    }

    public void deleteImage() {
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void setImage(Uri uri) {
        new LoadImageUriTask(this, uri).execute(new Void[0]);
    }

    public void setImage(File file) {
        new LoadImageFileTask(this, file).execute(new Void[0]);
    }

    private String getPath(Uri uri) {
        Cursor cursor = this.mContext.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        int pathIndex = cursor.getColumnIndexOrThrow("_data");
        String path = null;
        if (cursor.moveToFirst()) {
            path = cursor.getString(pathIndex);
        }
        cursor.close();
        return path;
    }

    public Bitmap getBitmapWithFilterApplied() {
        return getBitmapWithFilterApplied(this.mCurrentBitmap);
    }

    public Bitmap getBitmapWithFilterApplied(Bitmap bitmap) {
        if (this.mGlSurfaceView != null) {
            this.mRenderer.deleteImage();
            this.mRenderer.runOnDraw(new C15401());
            synchronized (this.mFilter) {
                requestRender();
                try {
                    this.mFilter.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        GPUImageRenderer renderer = new GPUImageRenderer(this.mFilter);
        renderer.setRotation(Rotation.NORMAL, this.mRenderer.isFlippedHorizontally(), this.mRenderer.isFlippedVertically());
        renderer.setScaleType(this.mScaleType);
        PixelBuffer buffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        buffer.setRenderer(renderer);
        renderer.setImageBitmap(bitmap, false);
        Bitmap result = buffer.getBitmap();
        this.mFilter.destroy();
        renderer.deleteImage();
        buffer.destroy();
        this.mRenderer.setFilter(this.mFilter);
        if (this.mCurrentBitmap != null) {
            this.mRenderer.setImageBitmap(this.mCurrentBitmap, false);
        }
        requestRender();
        return result;
    }

    public static void getBitmapForMultipleFilters(Bitmap bitmap, List<GPUImageFilter> filters, ResponseListener<Bitmap> listener) {
        if (!filters.isEmpty()) {
            GPUImageRenderer renderer = new GPUImageRenderer((GPUImageFilter) filters.get(0));
            renderer.setImageBitmap(bitmap, false);
            PixelBuffer buffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
            buffer.setRenderer(renderer);
            for (GPUImageFilter filter : filters) {
                renderer.setFilter(filter);
                listener.response(buffer.getBitmap());
                filter.destroy();
            }
            renderer.deleteImage();
            buffer.destroy();
        }
    }

    @Deprecated
    public void saveToPictures(String folderName, String fileName, OnPictureSavedListener listener) {
        saveToPictures(this.mCurrentBitmap, folderName, fileName, listener);
    }

    @Deprecated
    public void saveToPictures(Bitmap bitmap, String folderName, String fileName, OnPictureSavedListener listener) {
        new SaveTask(bitmap, folderName, fileName, listener).execute(new Void[0]);
    }

    void runOnGLThread(Runnable runnable) {
        this.mRenderer.runOnDrawEnd(runnable);
    }

    private int getOutputWidth() {
        if (this.mRenderer != null && this.mRenderer.getFrameWidth() != 0) {
            return this.mRenderer.getFrameWidth();
        }
        if (this.mCurrentBitmap != null) {
            return this.mCurrentBitmap.getWidth();
        }
        return ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    private int getOutputHeight() {
        if (this.mRenderer != null && this.mRenderer.getFrameHeight() != 0) {
            return this.mRenderer.getFrameHeight();
        }
        if (this.mCurrentBitmap != null) {
            return this.mCurrentBitmap.getHeight();
        }
        return ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getHeight();
    }
}
