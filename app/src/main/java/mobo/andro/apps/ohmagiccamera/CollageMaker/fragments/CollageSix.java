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

public class CollageSix extends Fragment implements CollageInterface, OnTouchListener, OnClickListener, TouchCallbackListener {
    private int curPadding = 0;
    private int curRadius = 0;
    private View div1;
    private View div2;
    int extraCount = 1;
    private FrameLayout fl1;
    private FrameLayout fl2;
    private FrameLayout fl3;
    private View focusedView;
    private boolean forShowOffOnly;
    private GestureDetector gdetector;
    private GLFilterImageView img1;
    private GLFilterImageView img2;
    private GLFilterImageView img3;
    private PopupWindow mPopupWindow;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private RelativeLayout rl4;
    private float tX = 0.0f;
    private float tY = 0.0f;
    private View view;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSix$3 */
    class C05993 implements Runnable {
        C05993() {
        }

        public void run() {
            CollageSix.this.setGridPadding(CollageSix.this.curPadding);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSix$4 */
    class C06004 extends SimpleOnGestureListener {
        C06004() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (CollageSix.this.mPopupWindow != null) {
                if (CollageSix.this.mPopupWindow.isShowing()) {
                    CollageSix.this.mPopupWindow.dismiss();
                }
                int rawX = (int) e.getRawX();
                int rawY = (int) e.getRawY();
                RelativeLayout main_rel = (RelativeLayout) CollageSix.this.view.findViewById(R.id.main_rel);
                int[] posXY = new int[2];
                main_rel.getLocationOnScreen(posXY);
                CollageSix.this.mPopupWindow.showAtLocation(main_rel, 51, rawX + posXY[0], rawY + posXY[1]);
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

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSix$5 */
    class C06015 implements OnClickListener {
        C06015() {
        }

        public void onClick(View view) {
            CollageSix.this.mPopupWindow.dismiss();
            CollageSix.this.onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONDOUBLECLICK);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSix$6 */
    class C06026 implements OnClickListener {
        C06026() {
        }

        public void onClick(View view) {
            CollageSix.this.mPopupWindow.dismiss();
            Bitmap bitmap = CollageSix.this.getStoredBitmap(CollageSix.this.focusedView);
            PhotoEditor.bitmap = bitmap.copy(bitmap.getConfig(), true);
            Intent intentEditor = new Intent(CollageSix.this.getActivity(), PhotoEditor.class);
            intentEditor.setAction("android.intent.action.MAIN");
            CollageSix.this.startActivityForResult(intentEditor, FragmentsManager.REQUEST_EDITOR);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSix$7 */
    class C06037 implements OnClickListener {
        C06037() {
        }

        public void onClick(View view) {
            CollageSix.this.mPopupWindow.dismiss();
        }
    }

    public static CollageSix newInstance(boolean b) {
        CollageSix fragment = new CollageSix();
        Bundle args = new Bundle();
        args.putBoolean("forShowOffOnly", b);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collage_6, container, false);
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        this.curPadding = getActivity().getResources().getInteger(R.integer.default_padding);
        this.forShowOffOnly = getArguments().getBoolean("forShowOffOnly");
        this.rl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        this.rl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        this.rl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        this.rl4 = (RelativeLayout) view.findViewById(R.id.rl4);
        this.fl1 = (FrameLayout) view.findViewById(R.id.fl1);
        this.fl2 = (FrameLayout) view.findViewById(R.id.fl2);
        this.fl3 = (FrameLayout) view.findViewById(R.id.fl3);
        this.img1 = (GLFilterImageView) view.findViewById(R.id.img1);
        this.img2 = (GLFilterImageView) view.findViewById(R.id.img2);
        this.img3 = (GLFilterImageView) view.findViewById(R.id.img3);
        this.div1 = view.findViewById(R.id.div1);
        this.div2 = view.findViewById(R.id.div2);
        if (!this.forShowOffOnly) {
            this.div1.setOnTouchListener(this);
            this.div2.setOnTouchListener(this);
            this.img1.setOnClickListener(this);
            this.img2.setOnClickListener(this);
            this.img3.setOnClickListener(this);
            ImageUtils.blinkMe(getActivity(), this.div1);
        }
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            public void run() {
                CollageSix.this.optimizeLayout(view);
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
        initPopupWindow();
    }

    private void createGLViewForImage(View view, final GLFilterImageView img, final Bitmap bitmap) {
        if (!this.forShowOffOnly) {
            final ImageGLSurfaceView mainImageView = new ImageGLSurfaceView(getActivity());
            mainImageView.setId(((int) System.currentTimeMillis()) + this.extraCount);
            this.extraCount++;
            mainImageView.setSurfaceCreatedCallback(new OnSurfaceCreatedCallback() {

                /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageSix$2$1 */
                class C05971 implements Runnable {
                    C05971() {
                    }

                    public void run() {
                        img.setImageGLSurfaceView(mainImageView);
                        img.setGLBitmap(bitmap);
                        img.setFilterConfig(CollageSix.this.img1.getmCurConfig());
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
                        CollageSix.this.getActivity().runOnUiThread(new C05971());
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
        view.post(new C05993());
    }

    public void setCornerRadius(int radius) {
        this.curRadius = radius;
        ((RoundCornerFrameLayout) this.fl1).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl2).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl3).setCornerRadius(radius);
    }

    public void setGridPadding(int padding) {
        this.curPadding = ImageUtils.dpToPx(getActivity(), padding);
        LayoutParams lp1 = (LayoutParams) this.fl1.getLayoutParams();
        lp1.setMargins(this.curPadding, this.curPadding, this.curPadding / 2, this.curPadding / 2);
        this.fl1.setLayoutParams(lp1);
        LayoutParams lp2 = (LayoutParams) this.fl2.getLayoutParams();
        lp2.setMargins(this.curPadding / 2, this.curPadding, this.curPadding, this.curPadding / 2);
        this.fl2.setLayoutParams(lp2);
        LayoutParams lp3 = (LayoutParams) this.fl3.getLayoutParams();
        lp3.setMargins(this.curPadding, this.curPadding / 2, this.curPadding, this.curPadding);
        this.fl3.setLayoutParams(lp3);
        if (padding < 20) {
            this.curPadding = ImageUtils.dpToPx(getActivity(), 20);
        }
        this.div1.getLayoutParams().height = this.curPadding;
        this.div1.setY((float) (this.rl1.getHeight() - (this.curPadding / 2)));
        this.div2.getLayoutParams().width = this.curPadding;
        this.div2.setX((float) (this.rl3.getWidth() - (this.curPadding / 2)));
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
    }

    public void setFilterProgress(float filterProgress) {
        this.img1.setFilterProgress(filterProgress);
        this.img2.setFilterProgress(filterProgress);
        this.img3.setFilterProgress(filterProgress);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                view.bringToFront();
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.divColor));
                this.tX = motionEvent.getX();
                this.tY = motionEvent.getY();
                break;
            case 1:
                view.setBackgroundColor(0);
                break;
            case 2:
                float x = motionEvent.getX();
                int dx = (int) (this.tX - x);
                int dy = (int) (this.tY - motionEvent.getY());
                Log.i("movetest", "tX : " + this.tX + "  x : " + x + "  dx : " + dx);
                view.bringToFront();
                if (view.getId() != R.id.div1 || (view.getY() - ((float) dy)) + ((float) view.getHeight()) > this.rl2.getY() + ((float) this.rl2.getHeight()) || view.getY() - ((float) dy) < 0.0f) {
                    if (view.getId() == R.id.div2 && (view.getX() - ((float) dx)) + ((float) view.getWidth()) <= this.rl4.getX() + ((float) this.rl4.getWidth()) && view.getX() - ((float) dx) >= 0.0f) {
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
                        view.setX(view.getX() - ((float) dx));
                        view.requestLayout();
                        view.postInvalidate();
                        break;
                    }
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
        this.gdetector = new GestureDetector(getActivity(), new C06004());
    }

    private void initPopupWindow() {
        View customView = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_dialog, null);
        this.mPopupWindow = new PopupWindow(customView, -2, -2);
        if (VERSION.SDK_INT >= 21) {
            this.mPopupWindow.setElevation(5.0f);
        }
        TextView delete = (TextView) customView.findViewById(R.id.delete);
        TextView cancel = (TextView) customView.findViewById(R.id.cancel);
        ((TextView) customView.findViewById(R.id.open)).setOnClickListener(new C06015());
        delete.setOnClickListener(new C06026());
        cancel.setOnClickListener(new C06037());
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
        return bitmap.copy(bitmap.getConfig(), true);
    }
}
