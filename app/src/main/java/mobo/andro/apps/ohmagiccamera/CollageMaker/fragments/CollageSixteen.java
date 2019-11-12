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

public class CollageSixteen extends Fragment implements CollageInterface, OnTouchListener, OnClickListener, TouchCallbackListener {
    private int curPadding = 0;
    private int curRadius = 0;
    private View div1;
    private View div2;
    private View div3;
    private View div4;
    int extraCount = 1;
    private FrameLayout fl1;
    private FrameLayout fl2;
    private FrameLayout fl3;
    private FrameLayout fl4;
    private FrameLayout fl5;
    private View focusedView;
    private boolean forShowOffOnly;
    private GestureDetector gdetector;
    private GLFilterImageView img1;
    private GLFilterImageView img2;
    private GLFilterImageView img3;
    private GLFilterImageView img4;
    private GLFilterImageView img5;
    private PopupWindow mPopupWindow;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private RelativeLayout rl4;
    private RelativeLayout rl5;
    private RelativeLayout rl6;
    private RelativeLayout rl7;
    private float tX = 0.0f;
    private float tY = 0.0f;
    private View view;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSixteen$3 */
    class C06073 implements Runnable {
        C06073() {
        }

        public void run() {
            CollageSixteen.this.setGridPadding(CollageSixteen.this.curPadding);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSixteen$4 */
    class C06084 extends SimpleOnGestureListener {
        C06084() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (CollageSixteen.this.mPopupWindow != null) {
                if (CollageSixteen.this.mPopupWindow.isShowing()) {
                    CollageSixteen.this.mPopupWindow.dismiss();
                }
                int rawX = (int) e.getRawX();
                int rawY = (int) e.getRawY();
                RelativeLayout main_rel = (RelativeLayout) CollageSixteen.this.view.findViewById(R.id.main_rel);
                int[] posXY = new int[2];
                main_rel.getLocationOnScreen(posXY);
                CollageSixteen.this.mPopupWindow.showAtLocation(main_rel, 51, rawX + posXY[0], rawY + posXY[1]);
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

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSixteen$5 */
    class C06095 implements OnClickListener {
        C06095() {
        }

        public void onClick(View view) {
            CollageSixteen.this.mPopupWindow.dismiss();
            CollageSixteen.this.onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONDOUBLECLICK);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSixteen$6 */
    class C06106 implements OnClickListener {
        C06106() {
        }

        public void onClick(View view) {
            CollageSixteen.this.mPopupWindow.dismiss();
            Bitmap bitmap = CollageSixteen.this.getStoredBitmap(CollageSixteen.this.focusedView);
            PhotoEditor.bitmap = bitmap.copy(bitmap.getConfig(), true);
            Intent intentEditor = new Intent(CollageSixteen.this.getActivity(), PhotoEditor.class);
            intentEditor.setAction("android.intent.action.MAIN");
            CollageSixteen.this.startActivityForResult(intentEditor, FragmentsManager.REQUEST_EDITOR);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSixteen$7 */
    class C06117 implements OnClickListener {
        C06117() {
        }

        public void onClick(View view) {
            CollageSixteen.this.mPopupWindow.dismiss();
        }
    }

    public static CollageSixteen newInstance(boolean b) {
        CollageSixteen fragment = new CollageSixteen();
        Bundle args = new Bundle();
        args.putBoolean("forShowOffOnly", b);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collage_16, container, false);
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
        this.fl1 = (FrameLayout) view.findViewById(R.id.fl1);
        this.fl2 = (FrameLayout) view.findViewById(R.id.fl2);
        this.fl3 = (FrameLayout) view.findViewById(R.id.fl3);
        this.fl4 = (FrameLayout) view.findViewById(R.id.fl4);
        this.fl5 = (FrameLayout) view.findViewById(R.id.fl5);
        this.img1 = (GLFilterImageView) view.findViewById(R.id.img1);
        this.img2 = (GLFilterImageView) view.findViewById(R.id.img2);
        this.img3 = (GLFilterImageView) view.findViewById(R.id.img3);
        this.img4 = (GLFilterImageView) view.findViewById(R.id.img4);
        this.img5 = (GLFilterImageView) view.findViewById(R.id.img5);
        this.div1 = view.findViewById(R.id.div1);
        this.div2 = view.findViewById(R.id.div2);
        this.div3 = view.findViewById(R.id.div3);
        this.div4 = view.findViewById(R.id.div4);
        if (!this.forShowOffOnly) {
            this.div1.setOnTouchListener(this);
            this.div2.setOnTouchListener(this);
            this.div3.setOnTouchListener(this);
            this.div4.setOnTouchListener(this);
            this.img1.setOnClickListener(this);
            this.img2.setOnClickListener(this);
            this.img3.setOnClickListener(this);
            this.img4.setOnClickListener(this);
            this.img5.setOnClickListener(this);
            ImageUtils.blinkMe(getActivity(), this.div1);
        }
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            public void run() {
                CollageSixteen.this.optimizeLayout(view);
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
        initPopupWindow();
    }

    private void createGLViewForImage(View view, final GLFilterImageView img, final Bitmap bitmap) {
        if (!this.forShowOffOnly) {
            final ImageGLSurfaceView mainImageView = new ImageGLSurfaceView(getActivity());
            mainImageView.setId(((int) System.currentTimeMillis()) + this.extraCount);
            this.extraCount++;
            mainImageView.setSurfaceCreatedCallback(new OnSurfaceCreatedCallback() {

                /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSixteen$2$1 */
                class C06051 implements Runnable {
                    C06051() {
                    }

                    public void run() {
                        img.setImageGLSurfaceView(mainImageView);
                        img.setGLBitmap(bitmap);
                        img.setFilterConfig(CollageSixteen.this.img1.getmCurConfig());
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
                        CollageSixteen.this.getActivity().runOnUiThread(new C06051());
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
        ImageUtils.optimizeView(this.rl1, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.S_3P);
        ImageUtils.optimizeView(this.rl2, 0.0f, (float) Dimensions.S_3P, Dimensions.MATCH_PARENT, Dimensions.S_3P);
        ImageUtils.optimizeView(this.rl3, 0.0f, (float) (Dimensions.S_3P * 2), Dimensions.MATCH_PARENT, Dimensions.S_3P);
        ImageUtils.optimizeView(this.rl4, 0.0f, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl5, (float) Dimensions.S_2P, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl6, 0.0f, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl7, (float) Dimensions.S_2P, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        view.post(new C06073());
    }

    public void setCornerRadius(int radius) {
        this.curRadius = radius;
        ((RoundCornerFrameLayout) this.fl1).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl2).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl3).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl4).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl5).setCornerRadius(radius);
    }

    public void setGridPadding(int padding) {
        this.curPadding = ImageUtils.dpToPx(getActivity(), padding);
        LayoutParams lp1 = (LayoutParams) this.fl1.getLayoutParams();
        lp1.setMargins(this.curPadding, this.curPadding, this.curPadding / 2, this.curPadding / 2);
        LayoutParams lp2 = (LayoutParams) this.fl2.getLayoutParams();
        lp2.setMargins(this.curPadding / 2, this.curPadding, this.curPadding, this.curPadding / 2);
        LayoutParams lp3 = (LayoutParams) this.fl3.getLayoutParams();
        lp3.setMargins(this.curPadding, this.curPadding / 2, this.curPadding, this.curPadding / 2);
        LayoutParams lp4 = (LayoutParams) this.fl4.getLayoutParams();
        lp4.setMargins(this.curPadding, this.curPadding / 2, this.curPadding / 2, this.curPadding);
        LayoutParams lp5 = (LayoutParams) this.fl5.getLayoutParams();
        lp5.setMargins(this.curPadding / 2, this.curPadding / 2, this.curPadding, this.curPadding);
        this.fl1.setLayoutParams(lp1);
        this.fl2.setLayoutParams(lp2);
        this.fl3.setLayoutParams(lp3);
        this.fl4.setLayoutParams(lp4);
        this.fl5.setLayoutParams(lp5);
        if (padding < 20) {
            this.curPadding = ImageUtils.dpToPx(getActivity(), 20);
        }
        this.div1.getLayoutParams().height = this.curPadding;
        this.div2.getLayoutParams().height = this.curPadding;
        this.div3.getLayoutParams().width = this.curPadding;
        this.div4.getLayoutParams().width = this.curPadding;
        this.div1.setY((float) (this.rl1.getHeight() - (this.curPadding / 2)));
        this.div2.setY((this.rl2.getY() + ((float) this.rl2.getHeight())) - ((float) (this.curPadding / 2)));
        this.div3.setX((float) (this.rl4.getWidth() - (this.curPadding / 2)));
        this.div4.setX((float) (this.rl6.getWidth() - (this.curPadding / 2)));
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
    }

    public void setFilterProgress(float filterProgress) {
        this.img1.setFilterProgress(filterProgress);
        this.img2.setFilterProgress(filterProgress);
        this.img3.setFilterProgress(filterProgress);
        this.img4.setFilterProgress(filterProgress);
        this.img5.setFilterProgress(filterProgress);
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
                float y = motionEvent.getY();
                int dx = (int) (this.tX - motionEvent.getX());
                int dy = (int) (this.tY - y);
                Log.i("movetest", "tY : " + this.tY + "  y : " + y + "  dy : " + dy);
                view.bringToFront();
                if (view.getId() != R.id.div1 || (view.getY() - ((float) dy)) + ((float) view.getHeight()) > this.div2.getY() + ((float) this.div2.getHeight()) || view.getY() - ((float) dy) < 0.0f) {
                    if (view.getId() != R.id.div2 || (view.getY() - ((float) dy)) + ((float) view.getHeight()) > this.rl3.getY() + ((float) this.rl3.getHeight()) || view.getY() - ((float) dy) < this.div1.getY()) {
                        if (view.getId() != R.id.div3 || (view.getX() - ((float) dx)) + ((float) view.getWidth()) > this.rl5.getX() + ((float) this.rl5.getWidth()) || view.getX() - ((float) dx) < 0.0f) {
                            if (view.getId() == R.id.div4 && (view.getX() - ((float) dx)) + ((float) view.getWidth()) <= this.rl7.getX() + ((float) this.rl7.getWidth()) && view.getX() - ((float) dx) >= 0.0f) {
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
                                Log.i("movetest", "rl1 : " + this.rl1.getLayoutParams().width + "  rl2 : " + this.rl2.getLayoutParams().width);
                                this.rl7.setX(this.rl7.getX() - ((float) dx));
                                this.rl7.requestLayout();
                                this.rl7.postInvalidate();
                                view.setX(view.getX() - ((float) dx));
                                view.requestLayout();
                                view.postInvalidate();
                                break;
                            }
                        }
                        this.rl4.getLayoutParams().width -= dx;
                        if (this.rl4.getLayoutParams().width >= 0) {
                            this.rl4.setVisibility(View.VISIBLE);
                        } else {
                            this.rl4.setVisibility(View.GONE);
                        }
                        this.rl5.getLayoutParams().width += dx;
                        if (this.rl5.getLayoutParams().width >= 0) {
                            this.rl5.setVisibility(View.VISIBLE);
                        } else {
                            this.rl5.setVisibility(View.GONE);
                        }
                        Log.i("movetest", "rl1 : " + this.rl1.getLayoutParams().width + "  rl2 : " + this.rl2.getLayoutParams().width);
                        this.rl5.setX(this.rl5.getX() - ((float) dx));
                        this.rl5.requestLayout();
                        this.rl5.postInvalidate();
                        view.setX(view.getX() - ((float) dx));
                        view.requestLayout();
                        view.postInvalidate();
                        break;
                    }
                    this.rl2.getLayoutParams().height -= dy;
                    if (this.rl2.getLayoutParams().height >= 0) {
                        this.rl2.setVisibility(View.VISIBLE);
                    } else {
                        this.rl2.setVisibility(View.GONE);
                    }
                    this.rl3.getLayoutParams().height += dy;
                    if (this.rl3.getLayoutParams().height >= 0) {
                        this.rl3.setVisibility(View.VISIBLE);
                    } else {
                        this.rl3.setVisibility(View.GONE);
                    }
                    Log.i("movetest", "rl1 : " + this.rl1.getLayoutParams().height + "  rl2 : " + this.rl2.getLayoutParams().height);
                    this.rl3.setY(this.rl3.getY() - ((float) dy));
                    view.setY(view.getY() - ((float) dy));
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
                Log.i("movetest", "rl1 : " + this.rl1.getLayoutParams().height + "  rl2 : " + this.rl2.getLayoutParams().height);
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
        this.gdetector = new GestureDetector(getActivity(), new C06084());
    }

    private void initPopupWindow() {
        View customView = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_dialog, null);
        this.mPopupWindow = new PopupWindow(customView, -2, -2);
        if (VERSION.SDK_INT >= 21) {
            this.mPopupWindow.setElevation(5.0f);
        }
        TextView delete = (TextView) customView.findViewById(R.id.delete);
        TextView cancel = (TextView) customView.findViewById(R.id.cancel);
        ((TextView) customView.findViewById(R.id.open)).setOnClickListener(new C06095());
        delete.setOnClickListener(new C06106());
        cancel.setOnClickListener(new C06117());
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
        return bitmap.copy(bitmap.getConfig(), true);
    }
}
