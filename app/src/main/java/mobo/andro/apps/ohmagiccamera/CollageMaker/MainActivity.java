package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.explorer.winklib.interfaces.IReportListener;
import com.explorer.winklib.presenter.BusinessLogic;
import com.google.android.gms.ads.AdView;

import org.jetbrains.annotations.NotNull;
import org.wysaid.nativePort.CGENativeLibrary.LoadImageCallback;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import mobo.andro.apps.ohmagiccamera.Camera.CameraDemoActivity;
import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Gallery;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.Dimensions;
import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.DataReportUtils;
import mobo.andro.apps.ohmagiccamera.R;

import static mobo.andro.apps.ohmagiccamera.Camera.CameraDemoActivity.getRealScreenSize;

public class MainActivity extends Activity implements OnClickListener, IReportListener {
    public static int screenHeight;
    public static int screenWidth;
    private boolean isOpenFisrtTime = false;
    private AdView mAdView;
    public LoadImageCallback loadImageCallback = new C03702();

    DataReportUtils dataReport = DataReportUtils.getInstance();

    @Override
    public void report(@NotNull String s) {
        dataReport.report(s);
        Log.d("ssss",s);

    }

    class C03702 implements LoadImageCallback {
        C03702() { }

        public Bitmap loadImage(String name, Object arg) {
            try {
                return BitmapFactory.decodeStream(MainActivity.this.getAssets().open(name));
            } catch (IOException e) {
                return null;
            }
        }

        public void loadImageOK(Bitmap bmp, Object arg) {
            bmp.recycle();
        }
    }
    int currentPage = 0;
    Integer[] imageId = new Integer[]{
            Integer.valueOf(R.drawable.model_3),
            Integer.valueOf(R.drawable.model_4),
            Integer.valueOf(R.drawable.model_5),
            Integer.valueOf(R.drawable.model_1),
            Integer.valueOf(R.drawable.model_2)
    };
    Timer timer;
    private ViewPager viewPager;
    class C02512 implements Runnable {
        C02512() {
        }

        public void run() {
            if (MainActivity.this.currentPage == 5) {
                MainActivity.this.currentPage = 0;
            }
            ViewPager access$100 = MainActivity.this.viewPager;
            MainActivity mainActivity = MainActivity.this;
            int i = mainActivity.currentPage;
            mainActivity.currentPage = i + 1;
            access$100.setCurrentItem(i, true);
        }
    }
    int height,width;
    int action_height;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        getWindow().setFlags(1024, 1024);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height =displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        action_height=height-(height*10/100+width);

//        MobileAds.initialize(this);
//        mAdView = findViewById(R.id.ad_main);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        Context context=MainActivity.this;
        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(MainActivity.this,adContainer);
        CustomAds.facebookAdInterstitial(MainActivity.this);

        ImageView rate=(ImageView)findViewById(R.id.rate);
        LinearLayout.LayoutParams rate_param= new LinearLayout.LayoutParams(height*5/100,height*5/100);
        rate.setLayoutParams(rate_param);


        LinearLayout parent_=(LinearLayout)findViewById(R.id.parent_);
        Point p=getRealScreenSize(MainActivity.this);
//        height=p.y;
//        width=p.x;

        TextView heading_text=(TextView)findViewById(R.id.heading_text);
        Typeface ttf_heading = Typeface.createFromAsset(getAssets(), "font36.ttf");
        heading_text.setTypeface(ttf_heading);


        LinearLayout heading=(LinearLayout)findViewById(R.id.heading);
        LinearLayout parent_cardview=(LinearLayout)findViewById(R.id.parent_cardView);
        LinearLayout action=(LinearLayout)findViewById(R.id.action);
        LinearLayout.LayoutParams params_heading = new LinearLayout.LayoutParams(width,height*10/100);
        LinearLayout.LayoutParams params_parent_cardview = new LinearLayout.LayoutParams(width,width);
        LinearLayout.LayoutParams params_action = new LinearLayout.LayoutParams(width,action_height);
        heading.setLayoutParams(params_heading);
        parent_cardview.setLayoutParams(params_parent_cardview);
        action.setLayoutParams(params_action);

        CardView cardView=(CardView)findViewById(R.id.cardView);
        LinearLayout.LayoutParams params_cardView = new LinearLayout.LayoutParams((width*90/100),(width*90/100));
        cardView.setLayoutParams(params_cardView);
        this.viewPager = (ViewPager) findViewById(R.id.imageviewPager);

        LinearLayout.LayoutParams params_view = new LinearLayout.LayoutParams((width*90/100),(width*90/100));
        this.viewPager.setLayoutParams(params_view);
        this.viewPager.setAdapter(new CustomAdapter(this, this.imageId));
        final Handler handler = new Handler();
        final Runnable Update = new C02512();
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {public void run(){handler.post(Update);}}, 500, 3000);


//        CGENativeLibrary.setLoadImageCallback(this.loadImageCallback, null);

        initHeightWidth();
        if (VERSION.SDK_INT >= 23 && !(checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0 && checkSelfPermission("android.permission.CAMERA") == 0 && checkSelfPermission("android.permission.RECORD_AUDIO") == 0)) { perdg(); }
        findViewById(R.id.btn_gridcollage).setOnClickListener(this);
        findViewById(R.id.btn_freecollage).setOnClickListener(this);
        findViewById(R.id.btn_gallery).setOnClickListener(this);

        findViewById(R.id.btn_mypic).setOnClickListener(this);
        findViewById(R.id.btn_mypic).setOnClickListener(this);
        Typeface ttf = Typeface.createFromAsset(getAssets(), "BEBAS.ttf");
        ((TextView) findViewById(R.id.txt_gridcollage)).setTypeface(ttf);
        ((TextView) findViewById(R.id.txt_freecollage)).setTypeface(ttf);
        ((TextView) findViewById(R.id.txt_mypic)).setTypeface(ttf);

        ImageView camera=(ImageView)findViewById(R.id.camera);
        LinearLayout.LayoutParams params_camera=new  LinearLayout.LayoutParams(action_height*30/100,action_height*30/100);
        camera.setLayoutParams(params_camera);

        ImageView grid_collage=(ImageView)findViewById(R.id.grid_collage);
        ImageView free_collage=(ImageView)findViewById(R.id.free_collage);
        ImageView my_studio=(ImageView)findViewById(R.id.my_studio);

        LinearLayout.LayoutParams params_action_element=new  LinearLayout.LayoutParams(action_height*16/100,action_height*16/100);
        grid_collage.setLayoutParams(params_action_element);
        free_collage.setLayoutParams(params_action_element);
        my_studio.setLayoutParams(params_action_element);

        rate.setOnClickListener(this);
        camera.setOnClickListener(this);
        grid_collage.setOnClickListener(this);
        free_collage.setOnClickListener(this);
        my_studio.setOnClickListener(this);


    }



    private void perdg() {
        final Dialog dialog = new Dialog(this, 16974128);
        dialog.setContentView(R.layout.permissionsdialog);
        dialog.setTitle(getResources().getString(R.string.permission).toString());
        dialog.setCancelable(false);
        ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (VERSION.SDK_INT >= 23) {
                    MainActivity.this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA","android.permission.RECORD_AUDIO"}, 922);
                }
                dialog.dismiss();
            }
        });
        if (this.isOpenFisrtTime) {
            Button setting = (Button) dialog.findViewById(R.id.settings);
            setting.setVisibility(View.VISIBLE);
            setting.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                    intent.addFlags(268435456);
                    MainActivity.this.startActivityForResult(intent, 922);
                    dialog.dismiss();
                }
            });
        } else {
            this.isOpenFisrtTime = true;
        }
        dialog.show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 922 && VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0|| checkSelfPermission("android.permission.CAMERA") != 0 || checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
                perdg();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 922 && VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0 || checkSelfPermission("android.permission.CAMERA") != 0 || checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
                perdg();
            }
        }
    }

    private void initHeightWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;
        Dimensions.MATCH_PARENT = -1;
        Dimensions.WRAP_CONTENT = -2;
        Dimensions.S_1P = screenWidth;
        Dimensions.S_2P = screenWidth / 2;
        Dimensions.S_3P = screenWidth / 3;
        Dimensions.S_4P = screenWidth / 4;
        Dimensions.S_5P = screenWidth / 5;
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rate:
                rate_pressed(MainActivity.this);
//                Toast.makeText(this, "Rate clicked", Toast.LENGTH_SHORT).show();
                return;
            case R.id.my_studio:
                Intent intent= new Intent(MainActivity.this, Gallery.class);
                intent.putExtra("title","Select media");
                intent.putExtra("mode",1);
                intent.putExtra("maxSelection",3);
                startActivity(intent);
                return;
            case R.id.camera:
                startActivity(new Intent(this, CameraDemoActivity.class));
                return;
            case R.id.grid_collage:
                startActivity(new Intent(this, ImageGalleryActivity.class));
                return;
            case R.id.free_collage:
                Intent collageIntent = new Intent(this, ImageGalleryActivity.class);
                collageIntent.putExtra("isOpenedForFreeCollage", true);
                collageIntent.putExtra("needToClear", true);
                startActivity(collageIntent);
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        int style;
        if (VERSION.SDK_INT >= 14) { style = 16974126; } else { style = 16973835; }
        new AlertDialog.Builder(this, style).setTitle(getResources().getString(R.string.app_name)).setIcon(R.mipmap.ic_launcher).setMessage(getResources().getString(R.string.exit_app)).setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                MainActivity.this.finish();
            }
        }).setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager)context. getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void onResume() {
        super.onResume();
        //记得注销
        BusinessLogic.Companion.getInstance(this).setOperator("52003");
        BusinessLogic.Companion.getInstance(this).verify(this,this);
    }

    public static void rate_pressed(Context context) {

        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK|
                Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }

    }

    public static void more_pressed(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=pub:"+"\""+context.getString(R.string.more_link)+"\""));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void onPause()
    {
        super.onPause();
        CustomAds.dismissInterstitialGoogle(MainActivity.this);
    }


}