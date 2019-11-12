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

public class CollageThree extends Fragment implements CollageInterface, OnTouchListener, OnClickListener, TouchCallbackListener {
    private int curPadding = 50;
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
    private float tY = 0.0f;
    private View view;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageThree$3 */
    class C07113 implements Runnable {
        C07113() {
        }

        public void run() {
            CollageThree.this.setGridPadding(CollageThree.this.curPadding);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageThree$4 */
    class C07124 extends SimpleOnGestureListener {
        C07124() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (CollageThree.this.mPopupWindow != null) {
                if (CollageThree.this.mPopupWindow.isShowing()) {
                    CollageThree.this.mPopupWindow.dismiss();
                }
                int rawX = (int) e.getRawX();
                int rawY = (int) e.getRawY();
                RelativeLayout main_rel = (RelativeLayout) CollageThree.this.view.findViewById(R.id.main_rel);
                int[] posXY = new int[2];
                main_rel.getLocationOnScreen(posXY);
                CollageThree.this.mPopupWindow.showAtLocation(main_rel, 51, rawX + posXY[0], rawY + posXY[1]);
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

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageThree$5 */
    class C07135 implements OnClickListener {
        C07135() {
        }

        public void onClick(View view) {
            CollageThree.this.mPopupWindow.dismiss();
            CollageThree.this.onGalleryButtonClick(FragmentsManager.REQUEST_GALLERY_ONDOUBLECLICK);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageThree$6 */
    class C07146 implements OnClickListener {
        C07146() {
        }

        public void onClick(View view) {
            CollageThree.this.mPopupWindow.dismiss();
            Bitmap bitmap = CollageThree.this.getStoredBitmap(CollageThree.this.focusedView);
            PhotoEditor.bitmap = bitmap.copy(bitmap.getConfig(), true);
            Intent intentEditor = new Intent(CollageThree.this.getActivity(), PhotoEditor.class);
            intentEditor.setAction("android.intent.action.MAIN");
            CollageThree.this.startActivityForResult(intentEditor, FragmentsManager.REQUEST_EDITOR);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageThree$7 */
    class C07157 implements OnClickListener {
        C07157() {
        }

        public void onClick(View view) {
            CollageThree.this.mPopupWindow.dismiss();
        }
    }

    public static CollageThree newInstance(boolean b) {
        CollageThree fragment = new CollageThree();
        Bundle args = new Bundle();
        args.putBoolean("forShowOffOnly", b);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collage_3, container, false);
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
                CollageThree.this.optimizeLayout(view);
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

                /* renamed from: mobo.andro.apps.camera.CollageMaker.fragments.CollageThree$2$1 */
                class C07091 implements Runnable {
                    C07091() {
                    }

                    public void run() {
                        img.setImageGLSurfaceView(mainImageView);
                        img.setGLBitmap(bitmap);
                        img.setFilterConfig(CollageThree.this.img1.getmCurConfig());
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
                        CollageThree.this.getActivity().runOnUiThread(new C07091());
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
        RelativeLayout glview_container = (RelativeLayout) view.findViewById(R.id.glview_container);
        glview_container.getLayoutParams().width = MainActivity.screenWidth;
        glview_container.getLayoutParams().height = MainActivity.screenWidth;
        glview_container.invalidate();
        ImageUtils.optimizeView(this.rl1, 0.0f, 0.0f, Dimensions.MATCH_PARENT, Dimensions.S_2P);
        ImageUtils.optimizeView(this.rl2, 0.0f, (float) Dimensions.S_2P, Dimensions.MATCH_PARENT, Dimensions.S_2P);
        view.post(new C07113());
    }

    public void setCornerRadius(int radius) {
        this.curRadius = radius;
        ((RoundCornerFrameLayout) this.fl1).setCornerRadius(radius);
        ((RoundCornerFrameLayout) this.fl2).setCornerRadius(radius);
    }

    public void setGridPadding(int padding) {
        this.curPadding = ImageUtils.dpToPx(getActivity(), padding);
        LayoutParams lp1 = (LayoutParams) this.fl1.getLayoutParams();
        lp1.setMargins(this.curPadding, this.curPadding, this.curPadding, this.curPadding / 2);
        this.fl1.setLayoutParams(lp1);
        LayoutParams lp2 = (LayoutParams) this.fl2.getLayoutParams();
        lp2.setMargins(this.curPadding, this.curPadding / 2, this.curPadding, this.curPadding);
        this.fl2.setLayoutParams(lp2);
        if (padding < 20) {
            this.curPadding = ImageUtils.dpToPx(getActivity(), 20);
        }
        this.div1.getLayoutParams().height = this.curPadding;
        this.div1.setY((float) (this.rl1.getHeight() - (this.curPadding / 2)));
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
                this.tY = motionEvent.getY();
                view.bringToFront();
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.divColor));
                break;
            case 1:
                view.setBackgroundColor(0);
                break;
            case 2:
                float y = motionEvent.getY();
                int dy = (int) (this.tY - y);
                Log.i("movetest", "tY : " + this.tY + "  y : " + y + "  dy : " + dy);
                view.bringToFront();
                if (view.getId() == R.id.div1 && (view.getY() - ((float) dy)) + ((float) view.getHeight()) <= this.rl2.getY() + ((float) this.rl2.getHeight()) && view.getY() - ((float) dy) >= 0.0f) {
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
        this.gdetector = new GestureDetector(getActivity(), new C07124());
    }

    private void initPopupWindow() {
        View customView = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.popup_dialog, null);
        this.mPopupWindow = new PopupWindow(customView, -2, -2);
        if (VERSION.SDK_INT >= 21) {
            this.mPopupWindow.setElevation(5.0f);
        }
        TextView delete = (TextView) customView.findViewById(R.id.delete);
        TextView cancel = (TextView) customView.findViewById(R.id.cancel);
        ((TextView) customView.findViewById(R.id.open)).setOnClickListener(new C07135());
        delete.setOnClickListener(new C07146());
        cancel.setOnClickListener(new C07157());
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
