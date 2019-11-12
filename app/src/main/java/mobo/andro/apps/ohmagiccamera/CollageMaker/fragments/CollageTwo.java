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

public class CollageTwo extends Fragment implements CollageInterface, OnTouchListener, OnClickListener, TouchCallbackListener {
    private int curPadding = 10;
    private int curRadius = 0;
    private View div1;
    int extraCount = 1;
    private FrameLayout fl1;
    private FrameLayout fl2;
    private View focusedView;
    private boolean forShowOffOnly;
    private GestureDetector gdetector;
    private GLFilterImageView img1;
    private GLFilterImageView img2;
    private PopupWindow mPopupWindow;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private float tX = 0.0f;
    private View view;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageTwo$3 */
    class C08073 implements Runnable {
        C08073() {
        }

        public void run() {
            CollageTwo.this.setGridPadding(CollageTwo.this.curPadding);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageTwo$4 */
    class C08084 extends SimpleOnGestureListener {
        C08084() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (CollageTwo.this.mPopupWindow != null) {
                if (CollageTwo.this.mPopupWindow.isShowing()) {
                    CollageTwo.this.mPopupWindow.dismiss();
                }
                int rawX = (int) e.getRawX();
                int rawY = (int) e.getRawY();
                RelativeLayout main_rel = (RelativeLayout) CollageTwo.this.view.findViewById(R.id.main_rel);
                int[] posXY = new int[2];
                main_rel.getLocationOnScreen(posXY);
                CollageTwo.this.mPopupWindow.showAtLocation(main_rel, 51, rawX + posXY[0], rawY + posXY[1]);
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

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageTwo$5 */
    class C08095 implements OnClickListener {
        C08095() {
        }

        public void onClick(View view) {
            CollageTwo.this.mPopupWindow.dismiss();
            CollageTwo.this.onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONDOUBLECLICK);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageTwo$6 */
    class C08106 implements OnClickListener {
        C08106() {
        }

        public void onClick(View view) {
            CollageTwo.this.mPopupWindow.dismiss();
            Bitmap bitmap = CollageTwo.this.getStoredBitmap(CollageTwo.this.focusedView);
            PhotoEditor.bitmap = bitmap.copy(bitmap.getConfig(), true);
            Intent intentEditor = new Intent(CollageTwo.this.getActivity(), PhotoEditor.class);
            intentEditor.setAction("android.intent.action.MAIN");
            CollageTwo.this.startActivityForResult(intentEditor, FragmentsManager.REQUEST_EDITOR);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageTwo$7 */
    class C08117 implements OnClickListener {
        C08117() {
        }

        public void onClick(View view) {
            CollageTwo.this.mPopupWindow.dismiss();
        }
    }

    public static CollageTwo newInstance(boolean b) {
        CollageTwo fragment = new CollageTwo();
        Bundle args = new Bundle();
        args.putBoolean("forShowOffOnly", b);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collage_2, container, false);
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        this.curPadding = getActivity().getResources().getInteger(R.integer.default_padding);
        this.forShowOffOnly = getArguments().getBoolean("forShowOffOnly");
        this.rl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        this.rl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        this.fl1 = (FrameLayout) view.findViewById(R.id.fl1);
        this.fl2 = (FrameLayout) view.findViewById(R.id.fl2);
        this.img1 = (GLFilterImageView) view.findViewById(R.id.img1);
        this.img2 = (GLFilterImageView) view.findViewById(R.id.img2);
        this.div1 = view.findViewById(R.id.div1);
        if (!this.forShowOffOnly) {
            this.div1.setOnTouchListener(this);
            this.img1.setOnClickListener(this);
            this.img2.setOnClickListener(this);
            ImageUtils.blinkMe(getActivity(), this.div1);
        }
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            public void run() {
                CollageTwo.this.optimizeLayout(view);
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
        initPopupWindow();
    }

    private void createGLViewForImage(View view, final GLFilterImageView img, final Bitmap bitmap) {
        if (!this.forShowOffOnly) {
            final ImageGLSurfaceView mainImageView = new ImageGLSurfaceView(getActivity());
            mainImageView.setId(((int) System.currentTimeMillis()) + this.extraCount);
            this.extraCount++;
            mainImageView.setSurfaceCreatedCallback(new OnSurfaceCreatedCallback() {

                /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageTwo$2$1 */
                class C08051 implements Runnable {
                    C08051() {
                    }

                    public void run() {
                        img.setImageGLSurfaceView(mainImageView);
                        img.setGLBitmap(bitmap);
                        img.setFilterConfig(CollageTwo.this.img1.getmCurConfig());
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
                        CollageTwo.this.getActivity().runOnUiThread(new C08051());
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
        ImageUtils.optimizeView(this.rl1, 0.0f, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        ImageUtils.optimizeView(this.rl2, (float) Dimensions.S_2P, 0.0f, Dimensions.S_2P, Dimensions.MATCH_PARENT);
        view.post(new C08073());
    }

    public void setCornerRadius(int radius) {
        this.curRadius = radius;
        ((RoundCornerFrameLayout) this.fl1).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl2).setCornerRadius(radius);
    }

    public void setGridPadding(int padding) {
        this.curPadding = ImageUtils.dpToPx(getActivity(), padding);
        LayoutParams lp1 = (LayoutParams) this.fl1.getLayoutParams();
        lp1.setMargins(this.curPadding, this.curPadding, this.curPadding / 2, this.curPadding);
        this.fl1.setLayoutParams(lp1);
        LayoutParams lp2 = (LayoutParams) this.fl2.getLayoutParams();
        lp2.setMargins(this.curPadding / 2, this.curPadding, this.curPadding, this.curPadding);
        this.fl2.setLayoutParams(lp2);
        if (padding < 20) {
            this.curPadding = ImageUtils.dpToPx(getActivity(), 20);
        }
        this.div1.getLayoutParams().width = this.curPadding;
        this.div1.setX((float) (this.rl1.getWidth() - (this.curPadding / 2)));
    }

    public void setEffectProgress(int progress, int maxProgress) {
    }

    public int setEffectConfigIndex(int index) {
        return index;
    }

    public void setFilterConfig(String config) {
        this.img1.setFilterConfig(config);
        this.img2.setFilterConfig(config);
    }

    public void setFilterProgress(float filterProgress) {
        this.img1.setFilterProgress(filterProgress);
        this.img2.setFilterProgress(filterProgress);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                view.bringToFront();
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.divColor));
                this.tX = motionEvent.getX();
                break;
            case 1:
                view.setBackgroundColor(0);
                break;
            case 2:
                float x = motionEvent.getX();
                int dx = (int) (this.tX - x);
                Log.i("movetest", "tX : " + this.tX + "  x : " + x + "  dx : " + dx);
                if (view.getId() == R.id.div1 && (view.getX() - ((float) dx)) + ((float) view.getWidth()) <= this.rl2.getX() + ((float) this.rl2.getWidth()) && view.getX() - ((float) dx) >= 0.0f) {
                    this.rl1.getLayoutParams().width -= dx;
                    if (this.rl1.getLayoutParams().width >= 0) {
                        this.rl1.setVisibility(View.VISIBLE);
                    } else {
                        this.rl1.setVisibility(View.GONE);
                    }
                    this.rl2.getLayoutParams().width += dx;
                    if (this.rl2.getLayoutParams().width >= 0) {
                        this.rl2.setVisibility(View.VISIBLE);
                    } else {
                        this.rl2.setVisibility(View.GONE);
                    }
                    this.rl2.setX(this.rl2.getX() - ((float) dx));
                    this.rl2.requestLayout();
                    this.rl2.postInvalidate();
                    view.setX(view.getX() - ((float) dx));
                    view.requestLayout();
                    view.postInvalidate();
                    break;
                }
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
        this.gdetector = new GestureDetector(getActivity(), new C08084());
    }

    private void initPopupWindow() {
        View customView = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_dialog, null);
        this.mPopupWindow = new PopupWindow(customView, -2, -2);
        if (VERSION.SDK_INT >= 21) {
            this.mPopupWindow.setElevation(5.0f);
        }
        TextView delete = (TextView) customView.findViewById(R.id.delete);
        TextView cancel = (TextView) customView.findViewById(R.id.cancel);
        ((TextView) customView.findViewById(R.id.open)).setOnClickListener(new C08095());
        delete.setOnClickListener(new C08106());
        cancel.setOnClickListener(new C08117());
    }

    private void storeBitmap(View view, Bitmap bitmap) {
        if (view == this.img1) {
            ImageGalleryActivity.selectedBitmaps.set(0, bitmap);
        }
        if (view == this.img2) {
            ImageGalleryActivity.selectedBitmaps.set(1, bitmap);
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
        return bitmap.copy(bitmap.getConfig(), true);
    }
}
