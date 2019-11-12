package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;
import java.util.Date;

import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class ShowImage extends Activity
{
    private ZoomageView show_image;
    private int position = 0;
    private MediaController mediaController;
    public static String selected_image_path;
    ImageView info,delete,share;
    int height,width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height =displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(ShowImage.this,adContainer);
        CustomAds.facebookAdInterstitial(ShowImage.this);


        info=(ImageView)findViewById(R.id.info);
        delete=(ImageView)findViewById(R.id.delete);
        share=(ImageView)findViewById(R.id.share);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width*10/100,width*10/100);
        info.setLayoutParams(params);
        delete.setLayoutParams(params);
        share.setLayoutParams(params);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                File file = new File(selected_image_path);
                Date lastModDate = new Date(file.lastModified());
                String[] str = lastModDate.toString().split(" ");

                Log.e("selected"," : "+lastModDate);
                Toast.makeText(ShowImage.this, ""+str, Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String str[]=selected_image_path.split("/");
                new AlertDialog.Builder(ShowImage.this)
                        .setTitle(str[str.length-1])
                        .setMessage("Delete this file?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                File ff=new File(selected_image_path);
                                ff.delete();
                                finish();
                                Toast.makeText(ShowImage.this, "Image Deleted", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                Toast.makeText(ShowImage.this, "No", Toast.LENGTH_SHORT).show();
                            }})
                        .show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(selected_image_path);
                sharingIntent.setType("*/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            }
        });


        show_image=(ZoomageView)findViewById(R.id.show_image);
        try {
            show_image.setImageURI(Uri.parse(selected_image_path));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void onPause()
    {
        super.onPause();
        CustomAds.dismissInterstitialGoogle(ShowImage.this);
    }

}