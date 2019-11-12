package mobo.andro.apps.ohmagiccamera.CollageMaker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import org.wysaid.view.ImageGLSurfaceView;
import org.wysaid.view.ImageGLSurfaceView.OnSurfaceCreatedCallback;

import java.io.IOException;

import mobo.andro.apps.ohmagiccamera.CollageMaker.CollageInterface;
import mobo.andro.apps.ohmagiccamera.CollageMaker.GLFilterImageView;
import mobo.andro.apps.ohmagiccamera.CollageMaker.ImageGalleryActivity;
import mobo.andro.apps.ohmagiccamera.CollageMaker.ImageUtils;
import mobo.andro.apps.ohmagiccamera.CollageMaker.MainActivity;
import mobo.andro.apps.ohmagiccamera.CollageMaker.MultiTouchListener;
import mobo.andro.apps.ohmagiccamera.CollageMaker.MultiTouchListener.TouchCallbackListener;
import mobo.andro.apps.ohmagiccamera.CollageMaker.RoundCornerFrameLayout;
import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.editormodule.PhotoEditor;

public class CollageFourtyFive extends Fragment implements CollageInterface, OnTouchListener, OnClickListener, TouchCallbackListener {
    private int curPadding = 0;
    private int curRadius = 0;
    private View div1;
    private View div2;
    private View div3;
    private View div4;
    private View div5;
    private View div6;
    private View div7;
    private View div8;
    private View div9;
    int extraCount = 1;
    private FrameLayout fl1;
    private FrameLayout fl10;
    private FrameLayout fl2;
    private FrameLayout fl3;
    private FrameLayout fl4;
    private FrameLayout fl5;
    private FrameLayout fl6;
    private FrameLayout fl7;
    private FrameLayout fl8;
    private FrameLayout fl9;
    private View focusedView;
    private boolean forShowOffOnly;
    private GestureDetector gdetector;
    private GLFilterImageView img1;
    private GLFilterImageView img10;
    private GLFilterImageView img2;
    private GLFilterImageView img3;
    private GLFilterImageView img4;
    private GLFilterImageView img5;
    private GLFilterImageView img6;
    private GLFilterImageView img7;
    private GLFilterImageView img8;
    private GLFilterImageView img9;
    private PopupWindow mPopupWindow;
    private RelativeLayout rl1;
    private RelativeLayout rl10;
    private RelativeLayout rl11;
    private RelativeLayout rl12;
    private RelativeLayout rl13;
    private RelativeLayout rl14;
    private RelativeLayout rl15;
    private RelativeLayout rl16;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private RelativeLayout rl4;
    private RelativeLayout rl5;
    private RelativeLayout rl6;
    private RelativeLayout rl7;
    private RelativeLayout rl8;
    private RelativeLayout rl9;
    private float tX = 0.0f;
    private float tY = 0.0f;
    private View view;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageFourtyFive$3 */
    class C04953 implements Runnable {
        C04953() {
        }

        public void run() {
            CollageFourtyFive.this.setGridPadding(CollageFourtyFive.this.curPadding);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageFourtyFive$4 */
    class C04964 extends SimpleOnGestureListener {
        C04964() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (CollageFourtyFive.this.mPopupWindow != null) {
                if (CollageFourtyFive.this.mPopupWindow.isShowing()) {
                    CollageFourtyFive.this.mPopupWindow.dismiss();
                }
                int rawX = (int) e.getRawX();
                int rawY = (int) e.getRawY();
                RelativeLayout main_rel = (RelativeLayout) CollageFourtyFive.this.view.findViewById(R.id.main_rel);
                int[] posXY = new int[2];
                main_rel.getLocationOnScreen(posXY);
                CollageFourtyFive.this.mPopupWindow.showAtLocation(main_rel, 51, rawX + posXY[0], rawY + posXY[1]);
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

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageFourtyFive$5 */
    class C04975 implements OnClickListener {
        C04975() {
        }

        public void onClick(View view) {
            CollageFourtyFive.this.mPopupWindow.dismiss();
            CollageFourtyFive.this.onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONDOUBLECLICK);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageFourtyFive$6 */
    class C04986 implements OnClickListener {
        C04986() {
        }

        public void onClick(View view) {
            CollageFourtyFive.this.mPopupWindow.dismiss();
            Bitmap bitmap = CollageFourtyFive.this.getStoredBitmap(CollageFourtyFive.this.focusedView);
            PhotoEditor.bitmap = bitmap.copy(bitmap.getConfig(), true);
            Intent intentEditor = new Intent(CollageFourtyFive.this.getActivity(), PhotoEditor.class);
            intentEditor.setAction("android.intent.action.MAIN");
            CollageFourtyFive.this.startActivityForResult(intentEditor, FragmentsManager.REQUEST_EDITOR);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageFourtyFive$7 */
    class C04997 implements OnClickListener {
        C04997() {
        }

        public void onClick(View view) {
            CollageFourtyFive.this.mPopupWindow.dismiss();
        }
    }

    public static CollageFourtyFive newInstance(boolean b) {
        CollageFourtyFive fragment = new CollageFourtyFive();
        Bundle args = new Bundle();
        args.putBoolean("forShowOffOnly", b);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collage_45, container, false);
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        this.curPadding = getActivity().getResources().getInteger(R.integer.default_padding);
        this.forShowOffOnly = getArguments().getBoolean("forShowOffOnly");
        this.rl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        this.rl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        this.rl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        this.rl4 = (RelativeLayout) view.findViewById(R.id.rl4);
        this.rl5 = (RelativeLayout) view.findViewById(R.id.rl5);
        this.rl6 = (RelativeLayout) view.findViewById(R.id.rl6);
        this.rl7 = (RelativeLayout) view.findViewById(R.id.rl7);
        this.rl8 = (RelativeLayout) view.findViewById(R.id.rl8);
        this.rl9 = (RelativeLayout) view.findViewById(R.id.rl9);
        this.rl10 = (RelativeLayout) view.findViewById(R.id.rl10);
        this.rl11 = (RelativeLayout) view.findViewById(R.id.rl11);
        this.rl12 = (RelativeLayout) view.findViewById(R.id.rl12);
        this.rl13 = (RelativeLayout) view.findViewById(R.id.rl13);
        this.rl14 = (RelativeLayout) view.findViewById(R.id.rl14);
        this.rl15 = (RelativeLayout) view.findViewById(R.id.rl15);
        this.rl16 = (RelativeLayout) view.findViewById(R.id.rl16);
        this.fl1 = (FrameLayout) view.findViewById(R.id.fl1);
        this.fl2 = (FrameLayout) view.findViewById(R.id.fl2);
        this.fl3 = (FrameLayout) view.findViewById(R.id.fl3);
        this.fl4 = (FrameLayout) view.findViewById(R.id.fl4);
        this.fl5 = (FrameLayout) view.findViewById(R.id.fl5);
        this.fl6 = (FrameLayout) view.findViewById(R.id.fl6);
        this.fl7 = (FrameLayout) view.findViewById(R.id.fl7);
        this.fl8 = (FrameLayout) view.findViewById(R.id.fl8);
        this.fl9 = (FrameLayout) view.findViewById(R.id.fl9);
        this.fl10 = (FrameLayout) view.findViewById(R.id.fl10);
        this.img1 = (GLFilterImageView) view.findViewById(R.id.img1);
        this.img2 = (GLFilterImageView) view.findViewById(R.id.img2);
        this.img3 = (GLFilterImageView) view.findViewById(R.id.img3);
        this.img4 = (GLFilterImageView) view.findViewById(R.id.img4);
        this.img5 = (GLFilterImageView) view.findViewById(R.id.img5);
        this.img6 = (GLFilterImageView) view.findViewById(R.id.img6);
        this.img7 = (GLFilterImageView) view.findViewById(R.id.img7);
        this.img8 = (GLFilterImageView) view.findViewById(R.id.img8);
        this.img9 = (GLFilterImageView) view.findViewById(R.id.img9);
        this.img10 = (GLFilterImageView) view.findViewById(R.id.img10);
        this.div1 = view.findViewById(R.id.div1);
        this.div2 = view.findViewById(R.id.div2);
        this.div3 = view.findViewById(R.id.div3);
        this.div4 = view.findViewById(R.id.div4);
        this.div5 = view.findViewById(R.id.div5);
        this.div6 = view.findViewById(R.id.div6);
        this.div7 = view.findViewById(R.id.div7);
        this.div8 = view.findViewById(R.id.div8);
        this.div9 = view.findViewById(R.id.div9);
        if (!this.forShowOffOnly) {
            this.div1.setOnTouchListener(this);
            this.div2.setOnTouchListener(this);
            this.div3.setOnTouchListener(this);
            this.div4.setOnTouchListener(this);
            this.div5.setOnTouchListener(this);
            this.div6.setOnTouchListener(this);
            this.div7.setOnTouchListener(this);
            this.div8.setOnTouchListener(this);
            this.div9.setOnTouchListener(this);
            this.img1.setOnClickListener(this);
            this.img2.setOnClickListener(this);
            this.img3.setOnClickListener(this);
            this.img4.setOnClickListener(this);
            this.img5.setOnClickListener(this);
            this.img6.setOnClickListener(this);
            this.img7.setOnClickListener(this);
            this.img8.setOnClickListener(this);
            this.img9.setOnClickListener(this);
            this.img10.setOnClickListener(this);
            ImageUtils.blinkMe(getActivity(), this.div1);
        }
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            public void run() {
                CollageFourtyFive.this.optimizeLayout(view);
            }
        });
        initGd();
        if (ImageGalleryActivity.selectedBitmaps.size() > 0) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(0), this.img1);
            createGLViewForImage(view, this.img1, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(0));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 1) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(1), this.img2);
            createGLViewForImage(view, this.img2, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(1));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 2) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(2), this.img3);
            createGLViewForImage(view, this.img3, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(2));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 3) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(3), this.img4);
            createGLViewForImage(view, this.img4, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(3));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 4) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(4), this.img5);
            createGLViewForImage(view, this.img5, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(4));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 5) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(5), this.img6);
            createGLViewForImage(view, this.img6, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(5));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 6) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(6), this.img7);
            createGLViewForImage(view, this.img7, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(6));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 7) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(7), this.img8);
            createGLViewForImage(view, this.img8, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(7));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 8) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(8), this.img9);
            createGLViewForImage(view, this.img9, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(8));
        }
        if (ImageGalleryActivity.selectedBitmaps.size() > 9) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(9), this.img10);
            createGLViewForImage(view, this.img10, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(9));
        }
        initPopupWindow();
    }

    private void createGLViewForImage(View view, final GLFilterImageView img, final Bitmap bitmap) {
        if (!this.forShowOffOnly) {
            final ImageGLSurfaceView mainImageView = new ImageGLSurfaceView(getActivity());
            mainImageView.setId(((int) System.currentTimeMillis()) + this.extraCount);
            this.extraCount++;
            mainImageView.setSurfaceCreatedCallback(new OnSurfaceCreatedCallback() {

                /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageFourtyFive$2$1 */
                class C04931 implements Runnable {
                    C04931() {
                    }

                    public void run() {
                        img.setImageGLSurfaceView(mainImageView);
                        img.setGLBitmap(bitmap);
                        img.setFilterConfig(CollageFourtyFive.this.img1.getmCurConfig());
                        FrameLayout parent = (FrameLayout) img.getParent();
                        int w = parent.getWidth();
                        int h = parent.getHeight();
                        int[] resizeDim = ImageUtils.getResizeDim((float) bitmap.getWidth(), (float) bitmap.getHeight(), w, h);
                        img.setImageBitmap(bitmap);
                        img.getLayoutParams().width = resizeDim[0];
                        img.getLayoutParams().height = resizeDim[1];
                        float scale = ((float) w) / ((float) resizeDim[0]) > ((float) h) / ((float) resizeDim[1]) ? ((float) w) / ((float) resizeDim[0]) : ((float) h) / ((float) resizeDim[1]);
                        img.setScaleX(scale);
                        img.setScaleY(scale);
                    }
                }

                public void surfaceCreated() {
                    if (ImageGalleryActivity.selectedBitmaps.size() > 0) {
                        CollageFourtyFive.this.getActivity().runOnUiThread(new C04931());
                    }
                }
            });
            ((RelativeLayout) view.findViewById(R.id.glview_container)).addView(mainImageView);
        }
    }

    private void optimizeLayout(View view) {
        RelativeLayout main_rel = (RelativeLayout) view.findViewById(R.id.main_rel);
        main_rel.getLayoutParams().width = MainActivity.screenWidth;
        main_rel.getLayoutParams().height = MainActivity.screenWidth;
        main_rel.invalidate();
        ImageUtils.optimizeView(this.rl1, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.S_2P);
        ImageUtils.optimizeView(this.rl2, 0.0f, (float) Dimensions.S_2P, Dimensions.MATCH_PARENT, Dimensions.S_2P);
        ImageUtils.optimizeView(this.rl3, 0.0f, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl4, (float) Dimensions.S_2P, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl5, 0.0f, 0.0f, Dimensions.S_4P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl6, (float) Dimensions.S_4P, 0.0f, Dimensions.S_4P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl7, (float) (Dimensions.S_4P * 2), 0.0f, Dimensions.S_4P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl8, (float) (Dimensions.S_4P * 3), 0.0f, Dimensions.S_4P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl9, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        ImageUtils.optimizeView(this.rl10, 0.0f, (float) Dimensions.S_4P, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        ImageUtils.optimizeView(this.rl11, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        ImageUtils.optimizeView(this.rl12, 0.0f, (float) Dimensions.S_4P, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        ImageUtils.optimizeView(this.rl13, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        ImageUtils.optimizeView(this.rl14, 0.0f, (float) Dimensions.S_4P, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        ImageUtils.optimizeView(this.rl15, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        ImageUtils.optimizeView(this.rl16, 0.0f, (float) Dimensions.S_4P, Dimensions.MATCH_PARENT, Dimensions.S_4P);
        view.post(new C04953());
    }

    public void setCornerRadius(int radius) {
        this.curRadius = radius;
        ((RoundCornerFrameLayout) this.fl1).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl2).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl3).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl4).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl5).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl6).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl7).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl8).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl9).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl10).setCornerRadius(radius);
    }

    public void setGridPadding(int padding) {
        this.curPadding = ImageUtils.dpToPx(getActivity(), padding);
        LayoutParams lp1 = (LayoutParams) this.fl1.getLayoutParams();
        lp1.setMargins(this.curPadding, this.curPadding, this.curPadding / 2, this.curPadding / 2);
        LayoutParams lp2 = (LayoutParams) this.fl2.getLayoutParams();
        lp2.setMargins(this.curPadding, this.curPadding / 2, this.curPadding / 2, this.curPadding / 2);
        LayoutParams lp3 = (LayoutParams) this.fl3.getLayoutParams();
        lp3.setMargins(this.curPadding / 2, this.curPadding, this.curPadding / 2, this.curPadding / 2);
        LayoutParams lp4 = (LayoutParams) this.fl4.getLayoutParams();
        lp4.setMargins(this.curPadding / 2, this.curPadding / 2, this.curPadding / 2, this.curPadding / 2);
        LayoutParams lp5 = (LayoutParams) this.fl5.getLayoutParams();
        lp5.setMargins(this.curPadding / 2, this.curPadding, this.curPadding / 2, this.curPadding / 2);
        LayoutParams lp6 = (LayoutParams) this.fl6.getLayoutParams();
        lp6.setMargins(this.curPadding / 2, this.curPadding / 2, this.curPadding / 2, this.curPadding / 2);
        LayoutParams lp7 = (LayoutParams) this.fl7.getLayoutParams();
        lp7.setMargins(this.curPadding / 2, this.curPadding, this.curPadding, this.curPadding / 2);
        LayoutParams lp8 = (LayoutParams) this.fl8.getLayoutParams();
        lp8.setMargins(this.curPadding / 2, this.curPadding / 2, this.curPadding, this.curPadding / 2);
        LayoutParams lp9 = (LayoutParams) this.fl9.getLayoutParams();
        lp9.setMargins(this.curPadding, this.curPadding / 2, this.curPadding / 2, this.curPadding);
        LayoutParams lp10 = (LayoutParams) this.fl10.getLayoutParams();
        lp10.setMargins(this.curPadding / 2, this.curPadding / 2, this.curPadding, this.curPadding);
        this.fl1.setLayoutParams(lp1);
        this.fl2.setLayoutParams(lp2);
        this.fl3.setLayoutParams(lp3);
        this.fl4.setLayoutParams(lp4);
        this.fl5.setLayoutParams(lp5);
        this.fl6.setLayoutParams(lp6);
        this.fl7.setLayoutParams(lp7);
        this.fl8.setLayoutParams(lp8);
        this.fl9.setLayoutParams(lp9);
        this.fl10.setLayoutParams(lp10);
        if (padding < 20) {
            this.curPadding = ImageUtils.dpToPx(getActivity(), 20);
        }
        this.div1.getLayoutParams().height = this.curPadding;
        this.div2.getLayoutParams().width = this.curPadding;
        this.div3.getLayoutParams().width = this.curPadding;
        this.div4.getLayoutParams().width = this.curPadding;
        this.div5.getLayoutParams().width = this.curPadding;
        this.div6.getLayoutParams().height = this.curPadding;
        this.div7.getLayoutParams().height = this.curPadding;
        this.div8.getLayoutParams().height = this.curPadding;
        this.div9.getLayoutParams().height = this.curPadding;
        this.div1.setY((float) (this.rl1.getHeight() - (this.curPadding / 2)));
        this.div2.setX((float) (this.rl3.getWidth() - (this.curPadding / 2)));
        this.div3.setX((float) (this.rl5.getWidth() - (this.curPadding / 2)));
        this.div4.setX((this.rl6.getX() + ((float) this.rl6.getWidth())) - ((float) (this.curPadding / 2)));
        this.div5.setX((this.rl7.getX() + ((float) this.rl7.getWidth())) - ((float) (this.curPadding / 2)));
        this.div6.setY((float) (this.rl9.getHeight() - (this.curPadding / 2)));
        this.div7.setY((float) (this.rl11.getHeight() - (this.curPadding / 2)));
        this.div8.setY((float) (this.rl13.getHeight() - (this.curPadding / 2)));
        this.div9.setY((float) (this.rl15.getHeight() - (this.curPadding / 2)));
    }

    public void setEffectProgress(int progress, int maxProgress) {
    }

    public int setEffectConfigIndex(int index) {
        return index;
    }

    public void setFilterConfig(String config) {
        this.img1.setFilterConfig(config);
        this.img2.setFilterConfig(config);
        this.img3.setFilterConfig(config);
        this.img4.setFilterConfig(config);
        this.img5.setFilterConfig(config);
        this.img6.setFilterConfig(config);
        this.img7.setFilterConfig(config);
        this.img8.setFilterConfig(config);
        this.img9.setFilterConfig(config);
        this.img10.setFilterConfig(config);
    }

    public void setFilterProgress(float filterProgress) {
        this.img1.setFilterProgress(filterProgress);
        this.img2.setFilterProgress(filterProgress);
        this.img3.setFilterProgress(filterProgress);
        this.img4.setFilterProgress(filterProgress);
        this.img5.setFilterProgress(filterProgress);
        this.img6.setFilterProgress(filterProgress);
        this.img7.setFilterProgress(filterProgress);
        this.img8.setFilterProgress(filterProgress);
        this.img9.setFilterProgress(filterProgress);
        this.img10.setFilterProgress(filterProgress);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                view.bringToFront();
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.divColor));
                this.tY = motionEvent.getY();
                this.tX = motionEvent.getX();
                break;
            case 1:
                view.setBackgroundColor(0);
                break;
            case 2:
                float x = motionEvent.getX();
                int dy = (int) (this.tY - motionEvent.getY());
                int dx = (int) (this.tX - x);
                view.bringToFront();
                if (view.getId() != R.id.div1 || (view.getY() - ((float) dy)) + ((float) view.getHeight()) > this.rl2.getY() + ((float) this.rl2.getHeight()) || view.getY() - ((float) dy) < 0.0f) {
                    if (view.getId() != R.id.div2 || (view.getX() - ((float) dx)) + ((float) view.getWidth()) > this.rl4.getX() + ((float) this.rl4.getWidth()) || view.getX() - ((float) dx) < 0.0f) {
                        if (view.getId() != R.id.div3 || (view.getX() - ((float) dx)) + ((float) view.getWidth()) > this.div4.getX() + ((float) this.div4.getWidth()) || view.getX() - ((float) dx) < 0.0f) {
                            if (view.getId() != R.id.div4 || (view.getX() - ((float) dx)) + ((float) view.getWidth()) > this.div5.getX() + ((float) this.div5.getWidth()) || view.getX() - ((float) dx) < this.div3.getX()) {
                                if (view.getId() != R.id.div5 || (view.getX() - ((float) dx)) + ((float) view.getWidth()) > this.rl8.getX() + ((float) this.rl8.getWidth()) || view.getX() - ((float) dx) < this.div4.getX()) {
                                    if (view.getId() != R.id.div6 || (view.getY() - ((float) dy)) + ((float) view.getHeight()) > this.rl10.getY() + ((float) this.rl10.getHeight()) || view.getY() - ((float) dy) < 0.0f) {
                                        if (view.getId() != R.id.div7 || (view.getY() - ((float) dy)) + ((float) view.getHeight()) > this.rl12.getY() + ((float) this.rl12.getHeight()) || view.getY() - ((float) dy) < 0.0f) {
                                            if (view.getId() != R.id.div8 || (view.getY() - ((float) dy)) + ((float) view.getHeight()) > this.rl14.getY() + ((float) this.rl14.getHeight()) || view.getY() - ((float) dy) < 0.0f) {
                                                if (view.getId() == R.id.div9 && (view.getY() - ((float) dy)) + ((float) view.getHeight()) <= this.rl16.getY() + ((float) this.rl16.getHeight()) && view.getY() - ((float) dy) >= 0.0f) {
                                                    this.rl15.getLayoutParams().height -= dy;
                                                    if (this.rl15.getLayoutParams().height >= 0) {
                                                        this.rl15.setVisibility(View.VISIBLE);
                                                    } else {
                                                        this.rl15.setVisibility(View.GONE);
                                                    }
                                                    this.rl16.getLayoutParams().height += dy;
                                                    if (this.rl16.getLayoutParams().height >= 0) {
                                                        this.rl16.setVisibility(View.VISIBLE);
                                                    } else {
                                                        this.rl16.setVisibility(View.GONE);
                                                    }
                                                    this.rl16.setY(this.rl16.getY() - ((float) dy));
                                                    view.setY(view.getY() - ((float) dy));
                                                    view.requestLayout();
                                                    view.postInvalidate();
                                                    break;
                                                }
                                            }
                                            this.rl13.getLayoutParams().height -= dy;
                                            if (this.rl13.getLayoutParams().height >= 0) {
                                                this.rl13.setVisibility(View.VISIBLE);
                                            } else {
                                                this.rl13.setVisibility(View.GONE);
                                            }
                                            this.rl14.getLayoutParams().height += dy;
                                            if (this.rl14.getLayoutParams().height >= 0) {
                                                this.rl14.setVisibility(View.VISIBLE);
                                            } else {
                                                this.rl14.setVisibility(View.GONE);
                                            }
                                            this.rl14.setY(this.rl14.getY() - ((float) dy));
                                            view.setY(view.getY() - ((float) dy));
                                            view.requestLayout();
                                            view.postInvalidate();
                                            break;
                                        }
                                        this.rl11.getLayoutParams().height -= dy;
                                        if (this.rl11.getLayoutParams().height >= 0) {
                                            this.rl11.setVisibility(View.VISIBLE);
                                        } else {
                                            this.rl11.setVisibility(View.GONE);
                                        }
                                        this.rl12.getLayoutParams().height += dy;
                                        if (this.rl12.getLayoutParams().height >= 0) {
                                            this.rl12.setVisibility(View.VISIBLE);
                                        } else {
                                            this.rl12.setVisibility(View.GONE);
                                        }
                                        Log.i("movetest", "rl1 : " + this.rl1.getLayoutParams().height + "  rl2 : " + this.rl2.getLayoutParams().height);
                                        this.rl12.setY(this.rl12.getY() - ((float) dy));
                                        view.setY(view.getY() - ((float) dy));
                                        view.requestLayout();
                                        view.postInvalidate();
                                        break;
                                    }
                                    this.rl9.getLayoutParams().height -= dy;
                                    if (this.rl9.getLayoutParams().height >= 0) {
                                        this.rl9.setVisibility(View.VISIBLE);
                                    } else {
                                        this.rl9.setVisibility(View.GONE);
                                    }
                                    this.rl10.getLayoutParams().height += dy;
                                    if (this.rl10.getLayoutParams().height >= 0) {
                                        this.rl10.setVisibility(View.VISIBLE);
                                    } else {
                                        this.rl10.setVisibility(View.GONE);
                                    }
                                    this.rl10.setY(this.rl10.getY() - ((float) dy));
                                    view.setY(view.getY() - ((float) dy));
                                    view.requestLayout();
                                    view.postInvalidate();
                                    break;
                                }
                                this.rl7.getLayoutParams().width -= dx;
                                if (this.rl7.getLayoutParams().width >= 0) {
                                    this.rl7.setVisibility(View.VISIBLE);
                                } else {
                                    this.rl7.setVisibility(View.GONE);
                                }
                                this.rl8.getLayoutParams().width += dx;
                                if (this.rl8.getLayoutParams().width >= 0) {
                                    this.rl8.setVisibility(View.VISIBLE);
                                } else {
                                    this.rl8.setVisibility(View.GONE);
                                }
                                this.rl8.setX(this.rl8.getX() - ((float) dx));
                                view.setX(view.getX() - ((float) dx));
                                view.requestLayout();
                                view.postInvalidate();
                                break;
                            }
                            this.rl6.getLayoutParams().width -= dx;
                            if (this.rl6.getLayoutParams().width >= 0) {
                                this.rl6.setVisibility(View.VISIBLE);
                            } else {
                                this.rl6.setVisibility(View.GONE);
                            }
                            this.rl7.getLayoutParams().width += dx;
                            if (this.rl7.getLayoutParams().width >= 0) {
                                this.rl7.setVisibility(View.VISIBLE);
                            } else {
                                this.rl7.setVisibility(View.GONE);
                            }
                            this.rl7.setX(this.rl7.getX() - ((float) dx));
                            view.setX(view.getX() - ((float) dx));
                            view.requestLayout();
                            view.postInvalidate();
                            break;
                        }
                        this.rl5.getLayoutParams().width -= dx;
                        if (this.rl5.getLayoutParams().width >= 0) {
                            this.rl5.setVisibility(View.VISIBLE);
                        } else {
                            this.rl5.setVisibility(View.GONE);
                        }
                        this.rl6.getLayoutParams().width += dx;
                        if (this.rl6.getLayoutParams().width >= 0) {
                            this.rl6.setVisibility(View.VISIBLE);
                        } else {
                            this.rl6.setVisibility(View.GONE);
                        }
                        this.rl6.setX(this.rl6.getX() - ((float) dx));
                        view.setX(view.getX() - ((float) dx));
                        break;
                    }
                    this.rl3.getLayoutParams().width -= dx;
                    if (this.rl3.getLayoutParams().width >= 0) {
                        this.rl3.setVisibility(View.VISIBLE);
                    } else {
                        this.rl3.setVisibility(View.GONE);
                    }
                    this.rl4.getLayoutParams().width += dx;
                    if (this.rl4.getLayoutParams().width >= 0) {
                        this.rl4.setVisibility(View.VISIBLE);
                    } else {
                        this.rl4.setVisibility(View.GONE);
                    }
                    Log.i("movetest", "rl1 : " + this.rl1.getLayoutParams().width + "  rl2 : " + this.rl2.getLayoutParams().width);
                    this.rl4.setX(this.rl4.getX() - ((float) dx));
                    this.rl4.requestLayout();
                    this.rl4.postInvalidate();
                    view.setX(view.getX() - ((float) dx));
                    view.requestLayout();
                    view.postInvalidate();
                    break;
                }
                this.rl1.getLayoutParams().height -= dy;
                if (this.rl1.getLayoutParams().height >= 0) {
                    this.rl1.setVisibility(View.VISIBLE);
                } else {
                    this.rl1.setVisibility(View.GONE);
                }
                this.rl2.getLayoutParams().height += dy;
                if (this.rl2.getLayoutParams().height >= 0) {
                    this.rl2.setVisibility(View.VISIBLE);
                } else {
                    this.rl2.setVisibility(View.GONE);
                }
                if (this.rl1.getLayoutParams().height > this.rl9.getLayoutParams().height) {
                    this.rl10.getLayoutParams().height = this.rl1.getLayoutParams().height - this.rl9.getLayoutParams().height;
                }
                if (this.rl1.getLayoutParams().height > this.rl11.getLayoutParams().height) {
                    this.rl12.getLayoutParams().height = this.rl1.getLayoutParams().height - this.rl11.getLayoutParams().height;
                }
                if (this.rl1.getLayoutParams().height > this.rl13.getLayoutParams().height) {
                    this.rl14.getLayoutParams().height = this.rl1.getLayoutParams().height - this.rl13.getLayoutParams().height;
                }
                if (this.rl1.getLayoutParams().height > this.rl15.getLayoutParams().height) {
                    this.rl16.getLayoutParams().height = this.rl1.getLayoutParams().height - this.rl15.getLayoutParams().height;
                }
                this.rl2.setY(this.rl2.getY() - ((float) dy));
                view.setY(view.getY() - ((float) dy));
                view.requestLayout();
                view.postInvalidate();
                break;
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img1:
                this.focusedView = this.img1;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img2:
                this.focusedView = this.img2;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img3:
                this.focusedView = this.img3;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img4:
                this.focusedView = this.img4;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img5:
                this.focusedView = this.img5;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img6:
                this.focusedView = this.img6;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img7:
                this.focusedView = this.img7;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img8:
                this.focusedView = this.img8;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img9:
                this.focusedView = this.img9;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            case R.id.img10:
                this.focusedView = this.img10;
                onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONCLICK);
                return;
            default:
                return;
        }
    }

    private void onGalleryButtonClick(int reqCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), reqCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Bitmap bitmap;
            if (requestCode == FragmentsManager.REQUEST_GALLERY_ONCLICK) {
                try {
                    bitmap = ImageUtils.getResampleImageBitmap(data.getData(), getActivity(), MainActivity.screenWidth);
                    ImageGalleryActivity.selectedBitmaps.add(bitmap);
                    setImageBitmap(bitmap, (ImageView) this.focusedView);
                    createGLViewForImage(this.view, (GLFilterImageView) this.focusedView, bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == FragmentsManager.REQUEST_GALLERY_ONDOUBLECLICK) {
                try {
                    bitmap = ImageUtils.getResampleImageBitmap(data.getData(), getActivity(), MainActivity.screenWidth);
                    storeBitmap(this.focusedView, bitmap);
                    createGLViewForImage(this.view, (GLFilterImageView) this.focusedView, bitmap);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (requestCode == FragmentsManager.REQUEST_EDITOR) {
                bitmap = PhotoEditor.resultBitmap.copy(PhotoEditor.resultBitmap.getConfig(), true);
                storeBitmap(this.focusedView, bitmap);
                createGLViewForImage(this.view, (GLFilterImageView) this.focusedView, bitmap);
            }
        }
    }

    private void setImageBitmap(Bitmap bitmap, ImageView img) {
        img.setImageBitmap(bitmap);
        img.getLayoutParams().width = Dimensions.MATCH_PARENT;
        img.getLayoutParams().height = Dimensions.MATCH_PARENT;
        if (this.forShowOffOnly) {
            img.setScaleType(ScaleType.CENTER_CROP);
            return;
        }
        img.setScaleType(ScaleType.FIT_CENTER);
        img.setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gdetector));
    }

    public void onTouchCallback(View v) {
        this.focusedView = v;
    }

    public void onTouchUpCallback(View v) {
    }

    private void initGd() {
        this.gdetector = new GestureDetector(getActivity(), new C04964());
    }

    private void initPopupWindow() {
        View customView = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_dialog, null);
        this.mPopupWindow = new PopupWindow(customView, -2, -2);
        if (VERSION.SDK_INT >= 21) {
            this.mPopupWindow.setElevation(5.0f);
        }
        TextView delete = (TextView) customView.findViewById(R.id.delete);
        TextView cancel = (TextView) customView.findViewById(R.id.cancel);
        ((TextView) customView.findViewById(R.id.open)).setOnClickListener(new C04975());
        delete.setOnClickListener(new C04986());
        cancel.setOnClickListener(new C04997());
    }

    private void storeBitmap(View view, Bitmap bitmap) {
        if (view == this.img1) {
            ImageGalleryActivity.selectedBitmaps.set(0, bitmap);
        }
        if (view == this.img2) {
            ImageGalleryActivity.selectedBitmaps.set(1, bitmap);
        }
        if (view == this.img3) {
            ImageGalleryActivity.selectedBitmaps.set(2, bitmap);
        }
        if (view == this.img4) {
            ImageGalleryActivity.selectedBitmaps.set(3, bitmap);
        }
        if (view == this.img5) {
            ImageGalleryActivity.selectedBitmaps.set(4, bitmap);
        }
        if (view == this.img6) {
            ImageGalleryActivity.selectedBitmaps.set(5, bitmap);
        }
        if (view == this.img7) {
            ImageGalleryActivity.selectedBitmaps.set(6, bitmap);
        }
        if (view == this.img8) {
            ImageGalleryActivity.selectedBitmaps.set(7, bitmap);
        }
        if (view == this.img9) {
            ImageGalleryActivity.selectedBitmaps.set(8, bitmap);
        }
        if (view == this.img10) {
            ImageGalleryActivity.selectedBitmaps.set(9, bitmap);
        }
    }

    private Bitmap getStoredBitmap(View view) {
        Bitmap bitmap = null;
        if (view == this.img1) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(0);
        }
        if (view == this.img2) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(1);
        }
        if (view == this.img3) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(2);
        }
        if (view == this.img4) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(3);
        }
        if (view == this.img5) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(4);
        }
        if (view == this.img6) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(5);
        }
        if (view == this.img7) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(6);
        }
        if (view == this.img8) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(7);
        }
        if (view == this.img9) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(8);
        }
        if (view == this.img10) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(9);
        }
        return bitmap.copy(bitmap.getConfig(), true);
    }
}
