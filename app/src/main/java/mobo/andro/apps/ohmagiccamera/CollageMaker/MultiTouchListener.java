package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import androidx.core.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

import mobo.andro.apps.ohmagiccamera.CollageMaker.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class MultiTouchListener implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Bitmap bitmap;
    boolean bt = false;
    private boolean disContinueHandleTransparecy = true;
    GestureDetector gd = null;
    private boolean handleTransparecy = false;
    public boolean isRotateEnabled = true;
    public boolean isRotationEnabled = false;
    public boolean isScaleEnabled = true;
    public boolean isTranslateEnabled = true;
    private TouchCallbackListener listener = null;
    private int mActivePointerId = -1;
    private float mPrevX;
    private float mPrevY;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 8.0f;
    public float minimumScale = 0.5f;

    public interface TouchCallbackListener {
        void onTouchCallback(View view);

        void onTouchUpCallback(View view);
    }

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
            float angle;
            float f = 0.0f;
            TransformInfo info = new TransformInfo();
            info.deltaScale = MultiTouchListener.this.isScaleEnabled ? detector.getScaleFactor() : 1.0f;
            if (MultiTouchListener.this.isRotateEnabled) {
                angle = Vector2D.getAngle(this.mPrevSpanVector, detector.getCurrentSpanVector());
            } else {
                angle = 0.0f;
            }
            info.deltaAngle = angle;
            if (MultiTouchListener.this.isTranslateEnabled) {
                angle = detector.getFocusX() - this.mPivotX;
            } else {
                angle = 0.0f;
            }
            info.deltaX = angle;
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

    public MultiTouchListener setHandleTransparecy(boolean handleTransparecy) {
        this.handleTransparecy = handleTransparecy;
        return this;
    }

    public MultiTouchListener setGestureListener(GestureDetector gd) {
        this.gd = gd;
        return this;
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
        computeRenderOffset(view, info.pivotX, info.pivotY);
        adjustTranslation(view, info.deltaX, info.deltaY);
        float scale = Math.max(info.minimumScale, Math.min(info.maximumScale, view.getScaleX() * info.deltaScale));
        view.setScaleX(scale);
        view.setScaleY(scale);
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
            if (event.getAction() == 2) {
                Log.i("MOVE_TESTs", "ACTION_MOVE");
                if (this.bt) {
                    return true;
                }
            }
            if (event.getAction() == 1) {
                Log.i("MOVE_TESTs", "ACTION_UP");
                if (this.bt) {
                    this.bt = false;
                    if (this.bitmap != null) {
                        this.bitmap.recycle();
                    }
                    return true;
                }
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
                Log.i("MOVE_TESTs", "View Width/height " + view.getWidth() + " / " + view.getHeight());
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
        this.mScaleGestureDetector.onTouchEvent(view, event);
        if (this.handleTransparecy && this.disContinueHandleTransparecy) {
            if (handleTransparency(view, event)) {
                return false;
            }
            this.disContinueHandleTransparecy = false;
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
                this.mPrevX = event.getX();
                this.mPrevY = event.getY();
                this.mActivePointerId = event.getPointerId(0);
                break;
            case 1:
                this.mActivePointerId = -1;
                this.disContinueHandleTransparecy = true;
                if (this.listener != null) {
                    this.listener.onTouchUpCallback(view);
                    break;
                }
                break;
            case 2:
                pointerIndex = event.findPointerIndex(this.mActivePointerId);
                if (pointerIndex != -1) {
                    float currX = event.getX(pointerIndex);
                    float currY = event.getY(pointerIndex);
                    if ((view instanceof GLFilterImageView) && event.getPointerCount() == 1) {
                        FrameLayout parent = (FrameLayout) view.getParent();
                        int width = parent.getWidth();
                        int height = parent.getHeight();
                        int[] posXY = new int[2];
                        parent.getLocationOnScreen(posXY);
                        int pX = posXY[0];
                        int pY = posXY[1];
                        if (!new Rect(pX, pY, pX + width, pY + height).contains((int) event.getRawX(), (int) event.getRawY())) {
                            view.startDrag(null, new DragShadowBuilder(view), view, 0);
                        }
                    }
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, currX - this.mPrevX, currY - this.mPrevY);
                        break;
                    }
                }
                break;
            case 3:
                this.mActivePointerId = -1;
                break;
            case 6:
                pointerIndex = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & action) >> 8;
                if (event.getPointerId(pointerIndex) == this.mActivePointerId) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    this.mPrevX = event.getX(newPointerIndex);
                    this.mPrevY = event.getY(newPointerIndex);
                    this.mActivePointerId = event.getPointerId(newPointerIndex);
                    break;
                }
                break;
        }
        if (this.gd != null) {
            this.gd.onTouchEvent(event);
        }
        view.requestLayout();
        view.postInvalidate();
        return true;
    }
}
