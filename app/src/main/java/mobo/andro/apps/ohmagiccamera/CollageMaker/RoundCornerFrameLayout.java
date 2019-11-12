package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class RoundCornerFrameLayout extends FrameLayout implements OnDragListener {
    private static final float CORNER_RADIUS = 0.0f;
    private float cornerRadius;
    private Bitmap maskBitmap;
    private Paint maskPaint;
    private Paint paint;

    public RoundCornerFrameLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundCornerFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RoundCornerFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.cornerRadius = TypedValue.applyDimension(0, 0.0f, context.getResources().getDisplayMetrics());
        this.paint = new Paint(1);
        this.maskPaint = new Paint(3);
        this.maskPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        setWillNotDraw(false);
        setOnDragListener(this);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = (float) radius;
        if (this.maskBitmap != null) {
            this.maskBitmap = createMask(this.maskBitmap.getWidth(), this.maskBitmap.getHeight());
            invalidate();
        }
    }

    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (width > 0 && width <= 1600 && height > 0 && height <= 1600) {
            Bitmap offscreenBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas offscreenCanvas = new Canvas(offscreenBitmap);
            super.draw(offscreenCanvas);
            if (this.maskBitmap == null) {
                this.maskBitmap = createMask(width, height);
            }
            offscreenCanvas.drawBitmap(this.maskBitmap, 0.0f, 0.0f, this.maskPaint);
            canvas.drawBitmap(offscreenBitmap, 0.0f, 0.0f, this.paint);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.maskBitmap != null && w > 0 && h > 0) {
            synchronized (this.maskBitmap) {
                this.maskBitmap = createMask(w, h);
                invalidate();
            }
        }
    }

    private Bitmap createMask(int width, int height) {
        Throwable e;
        Bitmap mask = null;
        try {
            mask = Bitmap.createBitmap(width, height, Config.ALPHA_8);
            Canvas canvas = new Canvas(mask);
            Paint paint = new Paint(1);
            paint.setColor(-1);
            canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) width, (float) height), this.cornerRadius, this.cornerRadius, paint);
            return mask;
        } catch (Exception e2) {
            e = e2;
        } catch (OutOfMemoryError e3) {
            e = e3;
        }
        e.printStackTrace();
        return mask;
    }

    public boolean onDrag(View layoutview, DragEvent dragevent) {
        if (dragevent.getAction() == 3) {
            View view = (View) dragevent.getLocalState();
            ViewGroup owner = (ViewGroup) view.getParent();
            ViewGroup container = (FrameLayout) layoutview;
            if (owner != container) {
                owner.removeView(view);
                View view1 = container.getChildAt(0);
                float r = view.getRotation();
                float r1 = view1.getRotation();
                container.removeAllViews();
                view.setTranslationX(0.0f);
                view.setTranslationY(0.0f);
                view1.setTranslationX(0.0f);
                view1.setTranslationY(0.0f);
                new LayoutParams(-2, -2).gravity = 17;
                new LayoutParams(-2, -2).gravity = 17;
                container.addView(view);
                owner.addView(view1);
            }
        }
        return true;
    }
}
