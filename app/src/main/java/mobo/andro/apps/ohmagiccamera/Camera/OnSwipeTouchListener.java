package mobo.andro.apps.ohmagiccamera.Camera;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {
    private final GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private final class GestureListener extends SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        private GestureListener() {
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) <= 100.0f || Math.abs(velocityX) <= 100.0f) {
                        return false;
                    }
                    if (diffX > 0.0f) {
                        OnSwipeTouchListener.this.onSwipeRight();
                    } else {
                        OnSwipeTouchListener.this.onSwipeLeft();
                    }
                    return true;
                } else if (Math.abs(diffY) <= 100.0f || Math.abs(velocityY) <= 100.0f) {
                    return false;
                } else {
                    if (diffY > 0.0f) {
                        OnSwipeTouchListener.this.onSwipeBottom();
                    } else {
                        OnSwipeTouchListener.this.onSwipeTop();
                    }
                    return true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            OnSwipeTouchListener.this.onTouch(e);
            return true;
        }
    }

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            OnSwipeTouchListener.this.onTouchDouble(detector);
            return true;
        }
    }

    public OnSwipeTouchListener(Context ctx) {
        this.gestureDetector = new GestureDetector(ctx, new GestureListener());
        this.scaleGestureDetector = new ScaleGestureDetector(ctx, new ScaleListener());
    }

    public boolean onTouch(View v, MotionEvent event) {
        Log.d("Sunny", "  MotionEvent   " + event.getPointerCount());
        if (event.getPointerCount() == 1) {
            return this.gestureDetector.onTouchEvent(event);
        }
        if (event.getPointerCount() == 2) {
            return this.scaleGestureDetector.onTouchEvent(event);
        }
        return this.gestureDetector.onTouchEvent(event);
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

    public void onTouch(MotionEvent e) {
    }

    public void onTouchDouble(ScaleGestureDetector detector) {
    }
}
