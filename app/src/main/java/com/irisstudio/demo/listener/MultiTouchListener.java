package com.irisstudio.demo.listener;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import androidx.core.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.irisstudio.demo.listener.ScaleGestureDetector.SimpleOnScaleGestureListener;
import com.irisstudio.demo.view.ResizableCardView;
import com.irisstudio.demo.view.ResizableStickerView;

public class MultiTouchListener implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Bitmap bitmap = null;
    boolean bt = false;
    public boolean isRotateEnabled = true;
    public boolean isRotationEnabled = false;
    public boolean isTranslateEnabled = true;
    private TouchCallbackListener listener = null;
    private int mActivePointerId = -1;
    private float mPrevX;
    private float mPrevY;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 8.0f;
    public float minimumScale = 0.5f;

    private class ScaleGestureListener extends SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
        }

        public boolean onScaleBegin(View view, ScaleGestureDetector detector) {
            this.mPivotX = detector.getFocusX();
            this.mPivotY = detector.getFocusY();
            this.mPrevSpanVector.set(detector.getCurrentSpanVector());
            return true;
        }

        public boolean onScale(View view, ScaleGestureDetector detector) {
            float focusX;
            float f = 0.0f;
            TransformInfo info = new TransformInfo();
            info.deltaAngle = MultiTouchListener.this.isRotateEnabled ? Vector2D.getAngle(this.mPrevSpanVector, detector.getCurrentSpanVector()) : 0.0f;
            if (MultiTouchListener.this.isTranslateEnabled) {
                focusX = detector.getFocusX() - this.mPivotX;
            } else {
                focusX = 0.0f;
            }
            info.deltaX = focusX;
            if (MultiTouchListener.this.isTranslateEnabled) {
                f = detector.getFocusY() - this.mPivotY;
            }
            info.deltaY = f;
            info.pivotX = this.mPivotX;
            info.pivotY = this.mPivotY;
            info.minimumScale = MultiTouchListener.this.minimumScale;
            info.maximumScale = MultiTouchListener.this.maximumScale;
            MultiTouchListener.this.move(view, info);
            return false;
        }
    }

    public interface TouchCallbackListener {
        void onTouchCallback(View view);

        void onTouchUpCallback(View view);
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    public MultiTouchListener setOnTouchCallbackListener(TouchCallbackListener l) {
        this.listener = l;
        return this;
    }

    public MultiTouchListener enableRotation(boolean b) {
        this.isRotationEnabled = b;
        return this;
    }

    public MultiTouchListener setMinScale(float f) {
        this.minimumScale = f;
        return this;
    }

    private void move(View view, TransformInfo info) {
        if (this.isRotationEnabled) {
            view.setRotation(adjustAngle(view.getRotation() + info.deltaAngle));
        }
    }

    private static float adjustAngle(float degrees) {
        if (degrees > 180.0f) {
            return degrees - 360.0f;
        }
        if (degrees < -180.0f) {
            return degrees + 360.0f;
        }
        return degrees;
    }

    private static void adjustTranslation(View view, float deltaX, float deltaY) {
        float[] deltaVector = new float[]{deltaX, deltaY};
        view.getMatrix().mapVectors(deltaVector);
        view.setTranslationX(view.getTranslationX() + deltaVector[0]);
        view.setTranslationY(view.getTranslationY() + deltaVector[1]);
    }

    private static void computeRenderOffset(View view, float pivotX, float pivotY) {
        if (view.getPivotX() != pivotX || view.getPivotY() != pivotY) {
            float[] prevPoint = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(prevPoint);
            view.setPivotX(pivotX);
            view.setPivotY(pivotY);
            float[] currPoint = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(currPoint);
            float offsetY = currPoint[1] - prevPoint[1];
            view.setTranslationX(view.getTranslationX() - (currPoint[0] - prevPoint[0]));
            view.setTranslationY(view.getTranslationY() - offsetY);
        }
    }

    public boolean handleTransparency(View view, MotionEvent event) {
        try {
            if (((ResizableStickerView) view).getBorderVisbilty()) {
                return false;
            }
            if (event.getAction() == 2 && this.bt) {
                return true;
            }
            if (event.getAction() == 1 && this.bt) {
                this.bt = false;
                if (this.bitmap != null) {
                    this.bitmap.recycle();
                }
                return true;
            }
            int[] posXY = new int[2];
            view.getLocationOnScreen(posXY);
            int rx = (int) (event.getRawX() - ((float) posXY[0]));
            int ry = (int) (event.getRawY() - ((float) posXY[1]));
            float r = view.getRotation();
            Matrix mat = new Matrix();
            mat.postRotate(-r);
            float[] point = new float[]{(float) rx, (float) ry};
            mat.mapPoints(point);
            rx = (int) point[0];
            ry = (int) point[1];
            if (event.getAction() == 0) {
                this.bt = false;
                view.setDrawingCacheEnabled(true);
                this.bitmap = Bitmap.createBitmap(view.getDrawingCache());
                rx = (int) (((float) rx) * (((float) this.bitmap.getWidth()) / (((float) this.bitmap.getWidth()) * view.getScaleX())));
                ry = (int) (((float) ry) * (((float) this.bitmap.getHeight()) / (((float) this.bitmap.getHeight()) * view.getScaleX())));
                view.setDrawingCacheEnabled(false);
            }
            if (rx < 0 || ry < 0 || rx > this.bitmap.getWidth() || ry > this.bitmap.getHeight()) {
                return false;
            }
            boolean b = this.bitmap.getPixel(rx, ry) == 0;
            if (event.getAction() != 0) {
                return b;
            }
            this.bt = b;
            return b;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        int newPointerIndex = 0;
        this.mScaleGestureDetector.onTouchEvent(view, event);
        if (handleTransparency(view, event)) {
            return false;
        }
        if (!this.isTranslateEnabled) {
            return true;
        }
        int action = event.getAction();
        int pointerIndex;
        switch (event.getActionMasked() & action) {
            case 0:
                if (this.listener != null) {
                    this.listener.onTouchCallback(view);
                }
                view.bringToFront();
                if (view instanceof ResizableStickerView) {
                    ((ResizableStickerView) view).setBorderVisibility(true);
                }
                if (view instanceof ResizableCardView) {
                    ((ResizableCardView) view).setBorderVisibility(true);
                }
                this.mPrevX = event.getX();
                this.mPrevY = event.getY();
                this.mActivePointerId = event.getPointerId(0);
                return true;
            case 1:
                this.mActivePointerId = -1;
                if (this.listener != null) {
                    this.listener.onTouchUpCallback(view);
                }
                float rotation = view.getRotation();
                if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                    if (rotation > 0.0f) {
                        rotation = 90.0f;
                    } else {
                        rotation = -90.0f;
                    }
                }
                if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                    if (rotation > 0.0f) {
                        rotation = 0.0f;
                    } else {
                        rotation = -0.0f;
                    }
                }
                if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                    if (rotation > 0.0f) {
                        rotation = 180.0f;
                    } else {
                        rotation = -180.0f;
                    }
                }
                view.setRotation(rotation);
                Log.i("testing", "Final Rotation : " + rotation);
                return true;
            case 2:
                pointerIndex = event.findPointerIndex(this.mActivePointerId);
                if (pointerIndex == -1) {
                    return true;
                }
                float currX = event.getX(pointerIndex);
                float currY = event.getY(pointerIndex);
                if (this.mScaleGestureDetector.isInProgress()) {
                    return true;
                }
                adjustTranslation(view, currX - this.mPrevX, currY - this.mPrevY);
                return true;
            case 3:
                this.mActivePointerId = -1;
                return true;
            case 6:
                pointerIndex = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & action) >> 8;
                if (event.getPointerId(pointerIndex) != this.mActivePointerId) {
                    return true;
                }
                if (pointerIndex == 0) {
                    newPointerIndex = 1;
                }
                this.mPrevX = event.getX(newPointerIndex);
                this.mPrevY = event.getY(newPointerIndex);
                this.mActivePointerId = event.getPointerId(newPointerIndex);
                return true;
            default:
                return true;
        }
    }
}
