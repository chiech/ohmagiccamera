package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Vector;

import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.Dimensions;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.FragmentsManager;
import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class ImageGalleryActivity extends Activity
{
    public static Vector<Bitmap> selectedBitmaps = new Vector();
    public static Vector<Uri> selectedUris = new Vector();
    private LinearLayout collage_fragment_container;
    private boolean forAddMoreIMages = false;
    private CustomGalleryFragment galleryFragment;
    private FrameLayout gallery_fragment_container;
    private boolean isOpenedForFreeCollage = false;
    private HorizontalScrollView layout_scroll;
    private RelativeLayout loading_lay;
    private AVLoadingIndicatorView loading_view;
    private int mainRelHeight;
    private int mainRelWidth;
    private int maxSelectionSize = 10;
    private boolean needToClear = true;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.ImageGalleryActivity$1 */
    class C03651 implements OnTouchListener {
        C03651() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.ImageGalleryActivity$2 */
    class C03662 implements Runnable {
        C03662() {
        }

        public void run() {
            ImageGalleryActivity.this.initHeightWidth(ImageGalleryActivity.this.collage_fragment_container.getHeight());
        }
    }

    public class AddUriAsync extends AsyncTask<Uri, Object, Boolean> {
        protected void onPreExecute() {
            super.onPreExecute();
            ImageGalleryActivity.this.loading_lay.setVisibility(View.VISIBLE);
            ImageGalleryActivity.this.loading_view.show();
        }

        protected Boolean doInBackground(Uri... uris) {
            Throwable e;
            Uri uri = uris[0];
            if (ImageGalleryActivity.selectedUris.contains(uri)) {
                int indx = ImageGalleryActivity.selectedUris.indexOf(uri);
                ImageGalleryActivity.selectedUris.remove(indx);
                ImageGalleryActivity.selectedBitmaps.remove(indx);
            } else {
                try {
                    if (ImageGalleryActivity.selectedUris.size() >= ImageGalleryActivity.this.maxSelectionSize) {
                        return Boolean.valueOf(false);
                    }
                    Bitmap bitmap = ImageUtils.getResampleImageBitmap(uri, ImageGalleryActivity.this, ImageGalleryActivity.this.mainRelWidth);
                    if (bitmap == null) {
                        return Boolean.valueOf(false);
                    }
                    ImageGalleryActivity.selectedUris.add(uri);
                    ImageGalleryActivity.selectedBitmaps.add(bitmap);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return Boolean.valueOf(false);
                } catch (OutOfMemoryError e3) {
                    e = e3;
                    e.printStackTrace();
                    return Boolean.valueOf(false);
                }
            }
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            ImageGalleryActivity.this.galleryFragment.updateAdapterView();
            if (aBoolean.booleanValue()) {
                if (!ImageGalleryActivity.this.isOpenedForFreeCollage) {
                    if (ImageGalleryActivity.selectedUris.size() == 0) {
                        ImageGalleryActivity.this.collage_fragment_container.removeAllViews();
                    } else {
                        int margin = ImageUtils.dpToPx(ImageGalleryActivity.this, 10);
                        Fragment[] fragmentsByCount = FragmentsManager.getFragmentsByCount(ImageGalleryActivity.selectedUris.size());
                        ImageGalleryActivity.this.collage_fragment_container.removeAllViews();
                        long l = System.currentTimeMillis();
                        for (int i = 0; i < fragmentsByCount.length; i++) {
                            int id = i + 1;
                            try {
                                final Fragment fragment = fragmentsByCount[i];
                                FrameLayout frameLayout = new FrameLayout(ImageGalleryActivity.this);
                                frameLayout.setId(id);
                                LayoutParams lp = new LayoutParams(ImageGalleryActivity.this.collage_fragment_container.getHeight(), ImageGalleryActivity.this.collage_fragment_container.getHeight());
                                lp.setMargins(margin, 0, margin, 0);
                                frameLayout.setLayoutParams(lp);
                                frameLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
                                ImageGalleryActivity.this.collage_fragment_container.addView(frameLayout);
                                ImageGalleryActivity.this.getFragmentManager().beginTransaction().replace(id, fragment).commit();
                                frameLayout.setOnClickListener(new OnClickListener() {
                                    public void onClick(View view) {
                                        String className = fragment.getClass().getName();
                                        Intent collageIntent = new Intent(ImageGalleryActivity.this, CollageEditorActivity.class);
                                        collageIntent.putExtra("className", className);
                                        ImageGalleryActivity.this.startActivityForResult(collageIntent, 1234);
                                    }
                                });
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                ImageGalleryActivity.this.loading_lay.setVisibility(View.GONE);
                ImageGalleryActivity.this.loading_view.hide();
                return;
            }
            Toast.makeText(ImageGalleryActivity.this, ImageGalleryActivity.this.getResources().getString(R.string.selection_warning), Toast.LENGTH_SHORT).show();
            ImageGalleryActivity.this.loading_lay.setVisibility(View.GONE);
            ImageGalleryActivity.this.loading_view.hide();
        }
    }

    public void onPause()
    {
        super.onPause();
        CustomAds.dismissInterstitialGoogle(ImageGalleryActivity.this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_image_gallery);


        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(ImageGalleryActivity.this,adContainer);
        CustomAds.facebookAdInterstitial(ImageGalleryActivity.this);


        getWindow().setFlags(1024, 1024);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.mainRelWidth = displaymetrics.widthPixels;
        this.mainRelHeight = displaymetrics.heightPixels;
        this.isOpenedForFreeCollage = getIntent().getBooleanExtra("isOpenedForFreeCollage", false);
        this.needToClear = getIntent().getBooleanExtra("needToClear", true);
        this.forAddMoreIMages = getIntent().getBooleanExtra("forAddMoreIMages", false);
        this.maxSelectionSize = getIntent().getIntExtra("maxSelectionSize", 10);
        if (this.needToClear) {
            selectedUris = new Vector();
            selectedBitmaps = new Vector();
        }
        this.loading_view = (AVLoadingIndicatorView) findViewById(R.id.loading_view);
        this.loading_lay = (RelativeLayout) findViewById(R.id.loading_lay);
        this.collage_fragment_container = (LinearLayout) findViewById(R.id.collage_fragment_container);
        this.gallery_fragment_container = (FrameLayout) findViewById(R.id.gallery_fragment_container);
        this.layout_scroll = (HorizontalScrollView) findViewById(R.id.layout_scroll);
        this.loading_lay.setOnTouchListener(new C03651());
        this.galleryFragment = new CustomGalleryFragment();
        Bundle args = new Bundle();
        args.putBoolean("isOpenedForFreeCollage", this.isOpenedForFreeCollage);
        args.putBoolean("forAddMoreIMages", this.forAddMoreIMages);
        this.galleryFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.gallery_fragment_container, this.galleryFragment);
        fragmentTransaction.commit();
        if (this.isOpenedForFreeCollage) {
            this.collage_fragment_container.setVisibility(View.GONE);
            this.gallery_fragment_container.setLayoutParams(new LayoutParams(-1, -2, 10.0f));
            this.gallery_fragment_container.requestLayout();
            this.gallery_fragment_container.postInvalidate();
            return;
        }
        this.collage_fragment_container.post(new C03662());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!this.isOpenedForFreeCollage) {
            initHeightWidth(this.collage_fragment_container.getHeight());
            this.collage_fragment_container.removeAllViews();
        }
        selectedUris = new Vector();
        selectedBitmaps = new Vector();
        this.galleryFragment = new CustomGalleryFragment();
        Bundle args = new Bundle();
        args.putBoolean("isOpenedForFreeCollage", this.isOpenedForFreeCollage);
        args.putBoolean("forAddMoreIMages", this.forAddMoreIMages);
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
        new AddUriAsync().execute(new Uri[]{uri});
    }

    public boolean containsImage(Uri uri) {
        return selectedUris.contains(uri);
    }
}
