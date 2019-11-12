package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Fragments.OneFragment;
import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Fragments.TwoFragment;
import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class Gallery extends AppCompatActivity {
    private ViewPager viewPager;
    public static int selectionTitle;
    public static String title;
    public static int maxSelection;
    public static int mode;
    TextView headertext;
    int height,width;

    public void onPause()
    {
        super.onPause();
        CustomAds.dismissInterstitialGoogle(Gallery.this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gallery_activity_gallery);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(Gallery.this,adContainer);
        CustomAds.facebookAdInterstitial(Gallery.this);


        ImageView btn_close=(ImageView)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height =displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        headertext=(TextView)findViewById(R.id.headertext);
        final ImageView btn_switch=(ImageView)findViewById(R.id.btn_switch);
        LinearLayout.LayoutParams btn_switch_param=new LinearLayout.LayoutParams(height*5/100,height*5/100);
        btn_switch.setLayoutParams(btn_switch_param);
        btn_switch.setImageResource(R.drawable.camera_btn_video_b);
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(viewPager.getCurrentItem()==0)
                {
                    headertext.setText("All Videos");
                    btn_switch.setImageResource(R.drawable.camera_btn_video_b);
                    viewPager.setCurrentItem(1);
                }
                else
                {
                    headertext.setText("All Photos");
                    btn_switch.setImageResource(R.drawable.camera_btn_camera_b);
                    viewPager.setCurrentItem(0);
                }

            }
        });

        try {

        }
        catch (Exception e){}

        title=getIntent().getExtras().getString("title");
        maxSelection=getIntent().getExtras().getInt("maxSelection");
        if (maxSelection==0) maxSelection = Integer.MAX_VALUE;
        mode=getIntent().getExtras().getInt("mode");
        setTitle(title);
        selectionTitle=0;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        OpenGallery.selected.clear();
        OpenGallery.imagesSelected.clear();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                if(position==0)
                {
                    headertext.setText("All Images");
                    btn_switch.setImageResource(R.drawable.camera_btn_video_b);

                }
                else
                {
                    headertext.setText("All Videos");
                    btn_switch.setImageResource(R.drawable.camera_btn_camera_b);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {}
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(selectionTitle>0){
            setTitle(String.valueOf(selectionTitle));
        }
    }

    //This method set up the tab view for images and videos
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(mode==1 || mode==2) {
            adapter.addFragment(new OneFragment(), "Images");
        }
        if(mode==1||mode==3)
        adapter.addFragment(new TwoFragment(), "Videos");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra("result",OpenGallery.imagesSelected);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}