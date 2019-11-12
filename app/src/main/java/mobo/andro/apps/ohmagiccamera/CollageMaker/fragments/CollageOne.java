package mobo.andro.apps.ohmagiccamera.CollageMaker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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

public class CollageOne extends Fragment implements CollageInterface, OnClickListener, TouchCallbackListener {
    private int curPadding = 10;
    private int curRadius = 0;
    int extraCount = 1;
    private FrameLayout fl1;
    private View focusedView;
    private boolean forShowOffOnly = false;
    private GestureDetector gdetector;
    private GLFilterImageView img1;
    private PopupWindow mPopupWindow = null;
    private RelativeLayout rl1;
    private View view;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageOne$3 */
    class C05753 implements Runnable {
        C05753() {
        }

        public void run() {
            CollageOne.this.setGridPadding(CollageOne.this.curPadding);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageOne$4 */
    class C05764 extends SimpleOnGestureListener {
        C05764() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (CollageOne.this.mPopupWindow != null) {
                if (CollageOne.this.mPopupWindow.isShowing()) {
                    CollageOne.this.mPopupWindow.dismiss();
                }
                int rawX = (int) e.getRawX();
                int rawY = (int) e.getRawY();
                RelativeLayout main_rel = (RelativeLayout) CollageOne.this.view.findViewById(R .id.main_rel);
                int[] posXY = new int[2];
                main_rel.getLocationOnScreen(posXY);
                CollageOne.this.mPopupWindow.showAtLocation(main_rel, 51, rawX + posXY[0], rawY + posXY[1]);
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

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageOne$5 */
    class C05775 implements OnClickListener {
        C05775() {
        }

        public void onClick(View view) {
            CollageOne.this.mPopupWindow.dismiss();
            CollageOne.this.onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONDOUBLECLICK);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageOne$6 */
    class C05786 implements OnClickListener {
        C05786() {
        }

        public void onClick(View view) {
            CollageOne.this.mPopupWindow.dismiss();
            Bitmap bitmap = CollageOne.this.getStoredBitmap(CollageOne.this.focusedView);
            PhotoEditor.bitmap = bitmap.copy(bitmap.getConfig(), true);
            Intent intentEditor = new Intent(CollageOne.this.getActivity(), PhotoEditor.class);
            intentEditor.setAction("android.intent.action.MAIN");
            CollageOne.this.startActivityForResult(intentEditor, FragmentsManager.REQUEST_EDITOR);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageOne$7 */
    class C05797 implements OnClickListener {
        C05797() {
        }

        public void onClick(View view) {
            CollageOne.this.mPopupWindow.dismiss();
        }
    }

    public static CollageOne newInstance(boolean b) {
        CollageOne fragment = new CollageOne();
        Bundle args = new Bundle();
        args.putBoolean("forShowOffOnly", b);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collage_1, container, false);
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        this.curPadding = getActivity().getResources().getInteger(R.integer.default_padding);
        this.forShowOffOnly = getArguments().getBoolean("forShowOffOnly");
        this.rl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        this.fl1 = (FrameLayout) view.findViewById(R.id.fl1);
        this.img1 = (GLFilterImageView) view.findViewById(R.id.img1);
        initGd();
        if (!this.forShowOffOnly) {
            this.img1.setOnClickListener(this);
        }
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            public void run() {
                CollageOne.this.optimizeLayout(view);
            }
        });
        if (ImageGalleryActivity.selectedBitmaps.size() > 0) {
            setImageBitmap((Bitmap) ImageGalleryActivity.selectedBitmaps.get(0), this.img1);
            createGLViewForImage(view, this.img1, (Bitmap) ImageGalleryActivity.selectedBitmaps.get(0));
        }
        initPopupWindow();
    }

    private void createGLViewForImage(View view, final GLFilterImageView img, final Bitmap bitmap) {
        if (!this.forShowOffOnly) {
            final ImageGLSurfaceView mainImageView = new ImageGLSurfaceView(getActivity());
            mainImageView.setId(((int) System.currentTimeMillis()) + this.extraCount);
            this.extraCount++;
            mainImageView.setSurfaceCreatedCallback(new OnSurfaceCreatedCallback() {

                /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageOne$2$1 */
                class C05731 implements Runnable {
                    C05731() {
                    }

                    public void run() {
                        img.setImageGLSurfaceView(mainImageView);
                        img.setGLBitmap(bitmap);
                        img.setFilterConfig(CollageOne.this.img1.getmCurConfig());
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
                        CollageOne.this.getActivity().runOnUiThread(new C05731());
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
        ImageUtils.optimizeView(this.rl1, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.MATCH_PARENT);
        view.post(new C05753());
    }

    public void setCornerRadius(int radius) {
        this.curRadius = radius;
        ((RoundCornerFrameLayout) this.fl1).setCornerRadius(radius);
    }

    public void setGridPadding(int padding) {
        this.curPadding = ImageUtils.dpToPx(getActivity(), padding);
        LayoutParams lp1 = (LayoutParams) this.fl1.getLayoutParams();
        lp1.setMargins(this.curPadding, this.curPadding, this.curPadding, this.curPadding);
        this.fl1.setLayoutParams(lp1);
    }

    public void setEffectProgress(int progress, int maxProgress) {
        this.img1.setEffectProgress(progress, maxProgress);
    }

    public int setEffectConfigIndex(int index) {
        return this.img1.setActiveConfigIndex(index, 100);
    }

    public void setFilterConfig(String config) {
        this.img1.setFilterConfig(config);
    }

    public void setFilterProgress(float filterProgress) {
        this.img1.setFilterProgress(filterProgress);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img1:
                this.focusedView = this.img1;
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
        this.gdetector = new GestureDetector(getActivity(), new C05764());
    }

    private void initPopupWindow() {
        View customView = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_dialog, null);
        this.mPopupWindow = new PopupWindow(customView, -2, -2);
        if (VERSION.SDK_INT >= 21) {
            this.mPopupWindow.setElevation(5.0f);
        }
        TextView delete = (TextView) customView.findViewById(R.id.delete);
        TextView cancel = (TextView) customView.findViewById(R.id.cancel);
        ((TextView) customView.findViewById(R.id.open)).setOnClickListener(new C05775());
        delete.setOnClickListener(new C05786());
        cancel.setOnClickListener(new C05797());
    }

    private void storeBitmap(View view, Bitmap bitmap) {
        if (view == this.img1) {
            ImageGalleryActivity.selectedBitmaps.set(0, bitmap);
        }
    }

    private Bitmap getStoredBitmap(View view) {
        Bitmap bitmap = null;
        if (view == this.img1) {
            bitmap = (Bitmap) ImageGalleryActivity.selectedBitmaps.get(0);
        }
        return bitmap.copy(bitmap.getConfig(), true);
    }
}
