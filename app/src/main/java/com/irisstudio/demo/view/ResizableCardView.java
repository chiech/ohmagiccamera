package com.irisstudio.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.irisstudio.demo.listener.MultiTouchListener;
import com.irisstudio.demo.listener.MultiTouchListener.TouchCallbackListener;
import org.wysaid.nativePort.CGENativeLibrary;

import mobo.andro.apps.ohmagiccamera.R;

public class ResizableCardView extends RelativeLayout implements TouchCallbackListener {
    private static final int SELF_SIZE_DP = 30;
    public static final String TAG = "ResizableStickerView";
    private int alphaProg = 0;
    double angle = 0.0d;
    int baseh;
    int basew;
    int basex;
    int basey;
    private Bitmap bitmap;
    private ImageView border_iv;
    float cX = 0.0f;
    float cY = 0.0f;
    private double centerX;
    private double centerY;
    private Context context;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    private int drawableId;
    private ImageView edit_iv;
    private ImageView flip_iv;
    private int he;
    private int hueProg = 0;
    private boolean isBorderVisible = false;
    private boolean isColorFilterEnable = false;
    private boolean isSticker = true;
    private boolean isStrickerEditEnable = false;
    private TouchEventListener listener = null;
    private String mCurConfig = "";
    private float mCurConfigProg = 1.0f;
    private OnTouchListener mTouchListener = new C14773();
    private OnTouchListener mTouchListener1 = new C14795();
    public ImageView main_iv;
    int margl;
    int margt;
    private OnTouchListener rTouchListener = new C14784();
    private Uri resUri = null;
    private float rotation;
    /* renamed from: s */
    private int f21s;
    Animation scale;
    private ImageView scale_iv;
    private double scale_orgHeight = -1.0d;
    private double scale_orgWidth = -1.0d;
    private float scale_orgX = -1.0f;
    private float scale_orgY = -1.0f;
    private int stickerColorFiter = 0;
    double tAngle = 0.0d;
    private float this_orgX = -1.0f;
    private float this_orgY = -1.0f;
    double vAngle = 0.0d;
    private int wi;
    private float yRotation;
    Animation zoomInScale;
    Animation zoomOutScale;

    public interface TouchEventListener {
        void onDelete(View view);

        void onEdit(View view, String str);

        void onTouchDown(View view);

        void onTouchUp(View view);
    }

    /* renamed from: com.irisstudio.demo.view.ResizableCardView$1 */
    class C14741 implements OnClickListener {
        C14741() {
        }

        public void onClick(View v) {
            float f = -180.0f;
            ImageView imageView = ResizableCardView.this.main_iv;
            if (ResizableCardView.this.main_iv.getRotationY() == -180.0f) {
                f = 0.0f;
            }
            imageView.setRotationY(f);
            ResizableCardView.this.main_iv.invalidate();
            ResizableCardView.this.requestLayout();
        }
    }

    /* renamed from: com.irisstudio.demo.view.ResizableCardView$2 */
    class C14762 implements OnClickListener {
        C14762() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) ResizableCardView.this.getParent();
            ResizableCardView.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    parent.removeView(ResizableCardView.this);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            ResizableCardView.this.main_iv.startAnimation(ResizableCardView.this.zoomInScale);
            ResizableCardView.this.setBorderVisibility(false);
            if (ResizableCardView.this.listener != null) {
                ResizableCardView.this.listener.onDelete(ResizableCardView.this);
            }
        }
    }

    /* renamed from: com.irisstudio.demo.view.ResizableCardView$3 */
    class C14773 implements OnTouchListener {
        C14773() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    ResizableCardView.this.this_orgX = ResizableCardView.this.getX();
                    ResizableCardView.this.this_orgY = ResizableCardView.this.getY();
                    ResizableCardView.this.scale_orgX = event.getRawX();
                    ResizableCardView.this.scale_orgY = event.getRawY();
                    ResizableCardView.this.scale_orgWidth = (double) ResizableCardView.this.getLayoutParams().width;
                    ResizableCardView.this.scale_orgHeight = (double) ResizableCardView.this.getLayoutParams().height;
                    ResizableCardView.this.centerX = (double) ((((View) ResizableCardView.this.getParent()).getX() + ResizableCardView.this.getX()) + (((float) ResizableCardView.this.getWidth()) / 2.0f));
                    int result = 0;
                    int resourceId = ResizableCardView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        result = ResizableCardView.this.getResources().getDimensionPixelSize(resourceId);
                    }
                    double statusBarHeight = (double) result;
                    ResizableCardView.this.centerY = (((double) (((View) ResizableCardView.this.getParent()).getY() + ResizableCardView.this.getY())) + statusBarHeight) + ((double) (((float) ResizableCardView.this.getHeight()) / 2.0f));
                    break;
                case 1:
                    ResizableCardView.this.wi = ResizableCardView.this.getLayoutParams().width;
                    ResizableCardView.this.he = ResizableCardView.this.getLayoutParams().height;
                    break;
                case 2:
                    double angle_diff = (Math.abs(Math.atan2((double) (event.getRawY() - ResizableCardView.this.scale_orgY), (double) (event.getRawX() - ResizableCardView.this.scale_orgX)) - Math.atan2(((double) ResizableCardView.this.scale_orgY) - ResizableCardView.this.centerY, ((double) ResizableCardView.this.scale_orgX) - ResizableCardView.this.centerX)) * 180.0d) / 3.141592653589793d;
                    Log.v("ResizableStickerView", "angle_diff: " + angle_diff);
                    double length1 = ResizableCardView.this.getLength(ResizableCardView.this.centerX, ResizableCardView.this.centerY, (double) ResizableCardView.this.scale_orgX, (double) ResizableCardView.this.scale_orgY);
                    double length2 = ResizableCardView.this.getLength(ResizableCardView.this.centerX, ResizableCardView.this.centerY, (double) event.getRawX(), (double) event.getRawY());
                    int size = ResizableCardView.this.dpToPx(ResizableCardView.this.getContext(), 30);
                    double offset;
                    LayoutParams layoutParams;
                    if (length2 > length1 && (angle_diff < 25.0d || Math.abs(angle_diff - 180.0d) < 25.0d)) {
                        offset = (double) Math.round(Math.max((double) Math.abs(event.getRawX() - ResizableCardView.this.scale_orgX), (double) Math.abs(event.getRawY() - ResizableCardView.this.scale_orgY)));
                        layoutParams = (LayoutParams)ResizableCardView.this.getLayoutParams();
                        layoutParams.width = (int) (((double) layoutParams.width) + offset);
                        layoutParams = (LayoutParams)ResizableCardView.this.getLayoutParams();
                        layoutParams.height = (int) (((double) layoutParams.height) + offset);
                    } else if (length2 < length1 && ((angle_diff < 25.0d || Math.abs(angle_diff - 180.0d) < 25.0d) && ResizableCardView.this.getLayoutParams().width > size / 2 && ResizableCardView.this.getLayoutParams().height > size / 2)) {
                        offset = (double) Math.round(Math.max((double) Math.abs(event.getRawX() - ResizableCardView.this.scale_orgX), (double) Math.abs(event.getRawY() - ResizableCardView.this.scale_orgY)));
                        layoutParams = (LayoutParams)ResizableCardView.this.getLayoutParams();
                        layoutParams.width = (int) (((double) layoutParams.width) - offset);
                        layoutParams = (LayoutParams)ResizableCardView.this.getLayoutParams();
                        layoutParams.height = (int) (((double) layoutParams.height) - offset);
                    }
                    ResizableCardView.this.scale_orgX = event.getRawX();
                    ResizableCardView.this.scale_orgY = event.getRawY();
                    ResizableCardView.this.postInvalidate();
                    ResizableCardView.this.requestLayout();
                    break;
            }
            return true;
        }
    }

    /* renamed from: com.irisstudio.demo.view.ResizableCardView$4 */
    class C14784 implements OnTouchListener {
        C14784() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableCardView.this.cX = rect.exactCenterX();
                    ResizableCardView.this.cY = rect.exactCenterY();
                    ResizableCardView.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    ResizableCardView.this.tAngle = (Math.atan2((double) (ResizableCardView.this.cY - event.getRawY()), (double) (ResizableCardView.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ResizableCardView.this.dAngle = ResizableCardView.this.vAngle - ResizableCardView.this.tAngle;
                    if (ResizableCardView.this.listener != null) {
                        ResizableCardView.this.listener.onEdit(ResizableCardView.this, "gone");
                        break;
                    }
                    break;
                case 1:
                    if (ResizableCardView.this.listener != null) {
                        ResizableCardView.this.listener.onEdit(ResizableCardView.this, "view");
                        break;
                    }
                    break;
                case 2:
                    ResizableCardView.this.angle = (Math.atan2((double) (ResizableCardView.this.cY - event.getRawY()), (double) (ResizableCardView.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (ResizableCardView.this.angle + ResizableCardView.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                    break;
            }
            return true;
        }
    }

    /* renamed from: com.irisstudio.demo.view.ResizableCardView$5 */
    class C14795 implements OnTouchListener {
        C14795() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ResizableCardView.this.getLayoutParams();
            switch (event.getAction()) {
                case 0:
                    ResizableCardView.this.invalidate();
                    ResizableCardView.this.basex = j;
                    ResizableCardView.this.basey = i;
                    ResizableCardView.this.basew = ResizableCardView.this.getWidth();
                    ResizableCardView.this.baseh = ResizableCardView.this.getHeight();
                    ResizableCardView.this.getLocationOnScreen(new int[2]);
                    ResizableCardView.this.margl = layoutParams.leftMargin;
                    ResizableCardView.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    ResizableCardView.this.wi = ResizableCardView.this.getLayoutParams().width;
                    ResizableCardView.this.he = ResizableCardView.this.getLayoutParams().height;
                    break;
                case 2:
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - ResizableCardView.this.basey), (double) (j - ResizableCardView.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= ResizableCardView.this.basex;
                    int k = i - ResizableCardView.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - ResizableCardView.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - ResizableCardView.this.getRotation()))));
                    k = (i * 2) + ResizableCardView.this.basew;
                    int m = (j * 2) + ResizableCardView.this.baseh;
                    if (k > ResizableCardView.this.f21s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = ResizableCardView.this.margl - i;
                    }
                    if (m > ResizableCardView.this.f21s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = ResizableCardView.this.margt - j;
                    }
                    ResizableCardView.this.setLayoutParams(layoutParams);
                    ResizableCardView.this.performLongClick();
                    break;
            }
            return true;
        }
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.main_iv.setImageBitmap(CGENativeLibrary.cgeFilterImage_MultipleEffects(bitmap, this.mCurConfig, this.mCurConfigProg));
    }

    public String getmCurConfig() {
        return this.mCurConfig;
    }

    public void setmCurConfig(String mCurConfig) {
        this.mCurConfig = mCurConfig;
        this.mCurConfigProg = 0.5f;
        this.main_iv.setImageBitmap(CGENativeLibrary.cgeFilterImage_MultipleEffects(this.bitmap, mCurConfig, this.mCurConfigProg));
    }

    public float getmCurConfigProg() {
        return this.mCurConfigProg;
    }

    public void setmCurConfigProg(float mCurConfigProg) {
        this.mCurConfigProg = mCurConfigProg;
        this.main_iv.setImageBitmap(CGENativeLibrary.cgeFilterImage_MultipleEffects(this.bitmap, this.mCurConfig, mCurConfigProg));
    }

    public ResizableCardView setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }

    public ResizableCardView(Context context) {
        super(context);
        init(context);
    }

    public ResizableCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResizableCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context ctx) {
        this.context = ctx;
        this.main_iv = new ImageView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.flip_iv = new ImageView(this.context);
        this.edit_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.f21s = dpToPx(this.context, 25);
        this.wi = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.he = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.border_iv.setImageResource(R.drawable.sticker_border_gray);
        this.flip_iv.setImageResource(R.drawable.sticker_flip);
        this.edit_iv.setImageResource(R.drawable.rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(this.wi, this.he);
        RelativeLayout.LayoutParams mlp = new RelativeLayout.LayoutParams(-1, -1);
        mlp.setMargins(5, 5, 5, 5);
        mlp.addRule(17);
        RelativeLayout.LayoutParams slp = new RelativeLayout.LayoutParams(this.f21s, this.f21s);
        slp.addRule(12);
        slp.addRule(11);
        slp.setMargins(5, 5, 5, 5);
        RelativeLayout.LayoutParams flp = new RelativeLayout.LayoutParams(this.f21s, this.f21s);
        flp.addRule(10);
        flp.addRule(11);
        flp.setMargins(5, 5, 5, 5);
        RelativeLayout.LayoutParams elp = new RelativeLayout.LayoutParams(this.f21s, this.f21s);
        elp.addRule(12);
        elp.addRule(9);
        elp.setMargins(5, 5, 5, 5);
        RelativeLayout.LayoutParams dlp = new RelativeLayout.LayoutParams(this.f21s, this.f21s);
        dlp.addRule(10);
        dlp.addRule(9);
        dlp.setMargins(5, 5, 5, 5);
        RelativeLayout.LayoutParams blp = new RelativeLayout.LayoutParams(-1, -1);
        setLayoutParams(lp);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(blp);
        this.border_iv.setScaleType(ScaleType.FIT_XY);
        this.border_iv.setTag("border_iv");
        addView(this.main_iv);
        this.main_iv.setLayoutParams(mlp);
        this.main_iv.setTag("main_iv");
        addView(this.flip_iv);
        this.flip_iv.setLayoutParams(flp);
        this.flip_iv.setOnClickListener(new C14741());
        addView(this.edit_iv);
        this.edit_iv.setLayoutParams(elp);
        this.edit_iv.setOnTouchListener(this.rTouchListener);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(dlp);
        this.delete_iv.setOnClickListener(new C14762());
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(slp);
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.scale_iv.setTag("scale_iv");
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in);
        setDefaultTouchListener();
    }

    public void setDefaultTouchListener() {
        setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this));
    }

    public void setBorderVisibility(boolean ch) {
        this.isBorderVisible = ch;
        if (!ch) {
            this.border_iv.setVisibility(8);
            this.scale_iv.setVisibility(8);
            this.flip_iv.setVisibility(8);
            this.edit_iv.setVisibility(8);
            this.delete_iv.setVisibility(8);
            setBackgroundResource(0);
            if (this.isColorFilterEnable) {
                this.main_iv.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.border_iv.getVisibility() != 0) {
            this.border_iv.setVisibility(0);
            this.scale_iv.setVisibility(0);
            if (this.isSticker) {
                this.flip_iv.setVisibility(0);
            }
            this.edit_iv.setVisibility(0);
            this.delete_iv.setVisibility(0);
            setBackgroundResource(R.drawable.sticker_border_gray);
            this.main_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisbilty() {
        return this.isBorderVisible;
    }

    public int getHueProg() {
        return this.hueProg;
    }

    public void setHueProg(int hueProg) {
        this.hueProg = hueProg;
        this.main_iv.setColorFilter(ColorFilterGenerator.adjustHue((float) hueProg));
    }

    public int getAlphaProg() {
        return this.alphaProg;
    }

    public void setAlphaProg(int alphaProg) {
        this.alphaProg = alphaProg;
        this.main_iv.setImageAlpha(255 - alphaProg);
    }

    public int getStickerColorFiter() {
        return this.stickerColorFiter;
    }

    public void setStickerColorFiter(int stickerColorFiter) {
        this.stickerColorFiter = stickerColorFiter;
    }

    public void setBgDrawable(int redId) {
        this.main_iv.setImageResource(redId);
        this.drawableId = redId;
        this.main_iv.startAnimation(this.zoomOutScale);
    }

    public void setMainImageUri(Uri uri) {
        this.resUri = uri;
        this.main_iv.setImageURI(this.resUri);
    }

    public Uri getMainImageUri() {
        return this.resUri;
    }

    public void setMainImageBitmap(Bitmap bit) {
        this.main_iv.setImageBitmap(bit);
    }

    public void setComponentInfo(ComponentInfo ci) {
        this.wi = ci.getWIDTH();
        this.he = ci.getHEIGHT();
        this.drawableId = ci.getRES_ID();
        this.resUri = ci.getRES_URI();
        this.rotation = ci.getROTATION();
        this.yRotation = ci.getY_ROTATION();
        this.bitmap = ci.getBITMAP();
        this.mCurConfig = ci.getCURCONFIG();
        this.mCurConfigProg = ci.getCURCONFIGPROG();
        setX(ci.getPOS_X());
        setY(ci.getPOS_Y());
        setBitmap(this.bitmap);
        setRotation(this.rotation);
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
        if (ci.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(8);
            this.isSticker = false;
        }
        if (ci.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(0);
            this.isSticker = true;
        }
        this.main_iv.setRotationY(this.yRotation);
    }

    public void optimize(float wr, float hr) {
        setX(getX() * wr);
        setY(getY() * hr);
        getLayoutParams().width = (int) (((float) this.wi) * wr);
        getLayoutParams().height = (int) (((float) this.he) * hr);
    }

    public ComponentInfo getComponentInfo() {
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X(getX());
        ci.setPOS_Y(getY());
        ci.setWIDTH(this.wi);
        ci.setHEIGHT(this.he);
        ci.setRES_ID(this.drawableId);
        ci.setRES_URI(this.resUri);
        ci.setROTATION(getRotation());
        ci.setY_ROTATION(this.main_iv.getRotationY());
        return ci;
    }

    public int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (f * Resources.getSystem().getDisplayMetrics().density);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2.0d) + Math.pow(x2 - x1, 2.0d));
    }

    public void enableColorFilter(boolean b) {
        this.isColorFilterEnable = b;
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
