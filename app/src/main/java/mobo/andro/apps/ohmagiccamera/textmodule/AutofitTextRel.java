package mobo.andro.apps.ohmagiccamera.textmodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.textmodule.listener.MultiTouchListener;
import mobo.andro.apps.ohmagiccamera.textmodule.listener.MultiTouchListener.TouchCallbackListener;

public class AutofitTextRel extends RelativeLayout implements TouchCallbackListener {
    public static final String TAG = "TextStickerView";
    double angle = 0.0d;
    private ImageView background_iv;
    int baseh;
    int basew;
    int basex;
    int basey;
    private int bgAlpha = 255;
    private int bgColor = 0;
    private String bgDrawable = "0";
    private ImageView border_iv;
    float cX = 0.0f;
    float cY = 0.0f;
    double centerX;
    double centerY;
    private Context context;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    private String fontName = "";
    private GestureDetector gd = null;
    private int he;
    private boolean isBorderVisible = false;
    private TouchEventListener listener = null;
    private OnTouchListener mTouchListener1 = new C08823();
    int margl;
    int margt;
    double onTouchAngle = 0.0d;
    private OnTouchListener rTouchListener = new C08812();
    private ImageView rotate_iv;
    private float rotation;
    /* renamed from: s */
    private int f19s;
    Animation scale;
    private ImageView scale_iv;
    private int shadowColor = 0;
    private int shadowProg = 0;
    private int tAlpha = 100;
    double tAngle = 0.0d;
    private int tColor = ViewCompat.MEASURED_STATE_MASK;
    private String text = "";
    private AutoResizeTextView text_iv;
    double vAngle = 0.0d;
    private int wi;
    Animation zoomInScale;
    Animation zoomOutScale;

    public interface TouchEventListener {
        void onDelete(View view);

        void onDoubleTap(View view);

        void onEdit(View view, String str);

        void onTouchDown(View view);

        void onTouchUp(View view);
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.AutofitTextRel$1 */
    class C08801 implements OnClickListener {
        C08801() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) AutofitTextRel.this.getParent();
            AutofitTextRel.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    parent.removeView(AutofitTextRel.this);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            AutofitTextRel.this.text_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.background_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.setBorderVisibility(false);
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDelete(AutofitTextRel.this);
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.AutofitTextRel$2 */
    class C08812 implements OnTouchListener {
        C08812() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    AutofitTextRel.this.tAngle = (Math.atan2((double) (AutofitTextRel.this.cY - event.getRawY()), (double) (AutofitTextRel.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel.this.dAngle = AutofitTextRel.this.vAngle - AutofitTextRel.this.tAngle;
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onEdit(AutofitTextRel.this, "gone");
                        break;
                    }
                    break;
                case 1:
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onEdit(AutofitTextRel.this, "view");
                        break;
                    }
                    break;
                case 2:
                    AutofitTextRel.this.angle = (Math.atan2((double) (AutofitTextRel.this.cY - event.getRawY()), (double) (AutofitTextRel.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                    break;
            }
            return true;
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.AutofitTextRel$3 */
    class C08823 implements OnTouchListener {
        C08823() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
            switch (event.getAction()) {
                case 0:
                    AutofitTextRel.this.invalidate();
                    AutofitTextRel.this.basex = j;
                    AutofitTextRel.this.basey = i;
                    AutofitTextRel.this.basew = AutofitTextRel.this.getWidth();
                    AutofitTextRel.this.baseh = AutofitTextRel.this.getHeight();
                    AutofitTextRel.this.getLocationOnScreen(new int[2]);
                    AutofitTextRel.this.margl = layoutParams.leftMargin;
                    AutofitTextRel.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    AutofitTextRel.this.wi = AutofitTextRel.this.getLayoutParams().width;
                    AutofitTextRel.this.he = AutofitTextRel.this.getLayoutParams().height;
                    break;
                case 2:
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - AutofitTextRel.this.basey), (double) (j - AutofitTextRel.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= AutofitTextRel.this.basex;
                    int k = i - AutofitTextRel.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - AutofitTextRel.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - AutofitTextRel.this.getRotation()))));
                    k = (i * 2) + AutofitTextRel.this.basew;
                    int m = (j * 2) + AutofitTextRel.this.baseh;
                    if (k > AutofitTextRel.this.f19s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = AutofitTextRel.this.margl - i;
                    }
                    if (m > AutofitTextRel.this.f19s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = AutofitTextRel.this.margt - j;
                    }
                    AutofitTextRel.this.setLayoutParams(layoutParams);
                    if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                        AutofitTextRel.this.wi = AutofitTextRel.this.getLayoutParams().width;
                        AutofitTextRel.this.he = AutofitTextRel.this.getLayoutParams().height;
                        AutofitTextRel.this.setBgDrawable(AutofitTextRel.this.bgDrawable);
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.AutofitTextRel$4 */
    class C08834 extends SimpleOnGestureListener {
        C08834() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDoubleTap(AutofitTextRel.this);
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return true;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public AutofitTextRel setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }

    public AutofitTextRel(Context context) {
        super(context);
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context ctx) {
        this.context = ctx;
        this.text_iv = new AutoResizeTextView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.background_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.f19s = dpToPx(this.context, 25);
        this.wi = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.he = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.scale_iv.setImageResource(R.drawable.textlib_scale);
        this.background_iv.setImageResource(0);
        this.delete_iv.setImageResource(R.drawable.textlib_clear);
        this.rotate_iv.setImageResource(R.drawable.rotate);
        LayoutParams lp = new LayoutParams(this.wi, this.he);
        LayoutParams slp = new LayoutParams(this.f19s, this.f19s);
        slp.addRule(12);
        slp.addRule(11);
        slp.setMargins(5, 5, 5, 5);
        LayoutParams tlp = new LayoutParams(-1, -1);
        tlp.setMargins(5, 5, 5, 5);
        tlp.addRule(17);
        LayoutParams dlp = new LayoutParams(this.f19s, this.f19s);
        dlp.addRule(10);
        dlp.addRule(9);
        dlp.setMargins(5, 5, 5, 5);
        LayoutParams blp = new LayoutParams(-1, -1);
        LayoutParams bglp = new LayoutParams(-1, -1);
        LayoutParams slp1 = new LayoutParams(this.f19s, this.f19s);
        slp1.addRule(12);
        slp1.addRule(9);
        slp1.setMargins(5, 5, 5, 5);
        setLayoutParams(lp);
        setBackgroundResource(R.drawable.textlib_border_gray);
        addView(this.background_iv);
        this.background_iv.setLayoutParams(bglp);
        this.background_iv.setScaleType(ScaleType.FIT_XY);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(blp);
        this.border_iv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(200.0f);
        this.text_iv.setLayoutParams(tlp);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(10.0f);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(dlp);
        this.delete_iv.setOnClickListener(new C08801());
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(slp1);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(slp);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in);
        initGD();
        setDefaultTouchListener();
    }

    public void setDefaultTouchListener() {
        setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
    }

    public void setBorderVisibility(boolean ch) {
        this.isBorderVisible = ch;
        if (!ch) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != 0) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.textlib_border_gray);
            this.text_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setText(String text) {
        this.text_iv.setText(text);
        this.text = text;
        this.text_iv.startAnimation(this.zoomOutScale);
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setTextFont(String fontName) {
        try {
            this.text_iv.setTypeface(Typeface.createFromAsset(this.context.getAssets(), fontName));
            this.fontName = fontName;
        } catch (Exception e) {
        }
    }

    public void setTextColor(int color) {
        this.text_iv.setTextColor(color);
        this.tColor = color;
    }

    public void setTextAlpha(int prog) {
        this.text_iv.setAlpha(((float) prog) / 100.0f);
        this.tAlpha = prog;
    }

    public void setTextShadowColor(int color) {
        this.shadowColor = color;
        this.text_iv.setShadowLayer((float) this.shadowProg, 0.0f, 0.0f, this.shadowColor);
    }

    public void setTextShadowProg(int prog) {
        this.shadowProg = prog;
        this.text_iv.setShadowLayer((float) this.shadowProg, 0.0f, 0.0f, this.shadowColor);
    }

    public void setBgDrawable(String did) {
        this.bgDrawable = did;
        this.bgColor = 0;
        this.background_iv.setImageBitmap(getTiledBitmap(this.context, getResources().getIdentifier(did, "drawable", this.context.getPackageName()), dpToPx(this.context, this.wi), dpToPx(this.context, this.he)));
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public void setBgColor(int c) {
        this.bgDrawable = "0";
        this.bgColor = c;
        this.background_iv.setImageResource(0);
        this.background_iv.setBackgroundColor(c);
    }

    public void setBgAlpha(int prog) {
        this.background_iv.setAlpha(((float) prog) / 255.0f);
        this.bgAlpha = prog;
    }

    public TextInfo getTextInfo() {
        TextInfo textInfo = new TextInfo();
        textInfo.setPOS_X(getX());
        textInfo.setPOS_Y(getY());
        textInfo.setWIDTH(this.wi);
        textInfo.setHEIGHT(this.he);
        textInfo.setTEXT(this.text);
        textInfo.setFONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setSHADOW_COLOR(this.shadowColor);
        textInfo.setSHADOW_PROG(this.shadowProg);
        textInfo.setBG_COLOR(this.bgColor);
        textInfo.setBG_DRAWABLE(this.bgDrawable);
        textInfo.setBG_ALPHA(this.bgAlpha);
        textInfo.setROTATION(getRotation());
        return textInfo;
    }

    public void setTextInfo(TextInfo textInfo) {
        this.wi = textInfo.getWIDTH();
        this.he = textInfo.getHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.getFONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.shadowColor = textInfo.getSHADOW_COLOR();
        this.shadowProg = textInfo.getSHADOW_PROG();
        this.bgColor = textInfo.getBG_COLOR();
        this.bgDrawable = textInfo.getBG_DRAWABLE();
        this.bgAlpha = textInfo.getBG_ALPHA();
        this.rotation = textInfo.getROTATION();
        setX(textInfo.getPOS_X());
        setY(textInfo.getPOS_Y());
        setText(this.text);
        setTextFont(this.fontName);
        setTextColor(this.tColor);
        setTextAlpha(this.tAlpha);
        setTextShadowColor(this.shadowColor);
        setTextShadowProg(this.shadowProg);
        if (this.bgColor != 0) {
            setBgColor(this.bgColor);
        } else {
            this.background_iv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.background_iv.setImageBitmap(null);
        } else {
            setBgDrawable(this.bgDrawable);
        }
        setBgAlpha(this.bgAlpha);
        setRotation(this.rotation);
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
    }

    public void optimize(float wr, float hr) {
        setX(getX() * wr);
        setY(getY() * hr);
        getLayoutParams().width = (int) (((float) this.wi) * wr);
        getLayoutParams().height = (int) (((float) this.he) * hr);
    }

    public int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (f * Resources.getSystem().getDisplayMetrics().density);
    }

    private Bitmap getTiledBitmap(Context ctx, int resId, int width, int height) {
        Rect rect = new Rect(0, 0, width, height);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(ctx.getResources(), resId, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        new Canvas(b).drawRect(rect, paint);
        return b;
    }

    private void initGD() {
        this.gd = new GestureDetector(this.context, new C08834());
    }

    public void onTouchCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchDown(v);
        }
    }

    public void onTouchUpCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchUp(v);
        }
    }
}
