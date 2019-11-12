package mobo.andro.apps.ohmagiccamera.Camera.Gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.Vector;

import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.Dimensions;
import mobo.andro.apps.ohmagiccamera.R;

public class ImageGalleryActivity extends Activity {
    public static Vector<Bitmap> selectedBitmaps = new Vector();
    public static Vector<Uri> selectedUris = new Vector();
    private boolean forAddMoreIMages = false;
    private CustomGalleryFragment galleryFragment;
    private FrameLayout gallery_fragment_container;
    private boolean isOpenedForFreeCollage = true;
    private HorizontalScrollView layout_scroll;
    private RelativeLayout loading_lay;
    private AVLoadingIndicatorView loading_view;
    private int mainRelHeight;
    private int mainRelWidth;
    private int maxSelectionSize = 10;
    private boolean needToClear = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.camera_activity_image_gallery);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.mainRelWidth = displaymetrics.widthPixels;
        this.mainRelHeight = displaymetrics.heightPixels;
        getWindow().setFlags(1024, 1024);
        this.needToClear = getIntent().getBooleanExtra("needToClear", true);
        this.forAddMoreIMages = getIntent().getBooleanExtra("forAddMoreIMages", false);
        this.maxSelectionSize = getIntent().getIntExtra("maxSelectionSize", 10);
        if (this.needToClear) {
            selectedUris = new Vector();
            selectedBitmaps = new Vector();
        }
        this.loading_view = (AVLoadingIndicatorView) findViewById(R.id.loading_view);
        this.loading_lay = (RelativeLayout) findViewById(R.id.loading_lay);
        this.gallery_fragment_container = (FrameLayout) findViewById(R.id.gallery_fragment_container);
        this.layout_scroll = (HorizontalScrollView) findViewById(R.id.layout_scroll);
        this.galleryFragment = new CustomGalleryFragment();
        Bundle args = new Bundle();
        this.galleryFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.gallery_fragment_container, this.galleryFragment);
        fragmentTransaction.commit();
        if (this.isOpenedForFreeCollage) {
            this.gallery_fragment_container.setLayoutParams(new LayoutParams(-1, -2, 10.0f));
            this.gallery_fragment_container.requestLayout();
            this.gallery_fragment_container.postInvalidate();
            return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!this.isOpenedForFreeCollage) {
        }
        selectedUris = new Vector();
        selectedBitmaps = new Vector();
        this.galleryFragment = new CustomGalleryFragment();
        Bundle args = new Bundle();
        this.galleryFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.gallery_fragment_container, this.galleryFragment);
        fragmentTransaction.commit();
    }

    private void initHeightWidth(int screenWidth) {
        Dimensions.MATCH_PARENT = -1;
        Dimensions.WRAP_CONTENT = -2;
        Dimensions.S_1P = screenWidth;
        Dimensions.S_2P = screenWidth / 2;
        Dimensions.S_3P = screenWidth / 3;
        Dimensions.S_4P = screenWidth / 4;
        Dimensions.S_5P = screenWidth / 5;
    }

    @SuppressLint({"ResourceType"})
    public void addImage(Uri uri) {
    }

    public boolean containsImage(Uri uri) {
        return selectedUris.contains(uri);
    }
}
