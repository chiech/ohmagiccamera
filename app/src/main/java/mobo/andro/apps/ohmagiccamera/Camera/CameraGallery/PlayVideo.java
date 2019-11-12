package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
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
import android.widget.VideoView;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;

import java.io.File;

import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class PlayVideo extends Activity
{
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    public static String selected_video_path;
    ImageView info,delete,share;
    int height,width;

    public void onPause()
    {
        super.onPause();
        CustomAds.dismissInterstitialGoogle(PlayVideo.this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(PlayVideo.this,adContainer);
        CustomAds.facebookAdInterstitial(PlayVideo.this);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height =displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        info=(ImageView)findViewById(R.id.info);
        delete=(ImageView)findViewById(R.id.delete);
        share=(ImageView)findViewById(R.id.share);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width*10/100,width*10/100);
        info.setLayoutParams(params);
        delete.setLayoutParams(params);
        share.setLayoutParams(params);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str[]=selected_video_path.split("/");
                new AlertDialog.Builder(PlayVideo.this)
                        .setTitle(str[str.length-1])
                        .setMessage("Delete this file?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                File file=new File(selected_video_path);
                                file.delete();
                                finish();
                                Toast.makeText(PlayVideo.this, "Video Deleted", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }})
                        .show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(selected_video_path);
                sharingIntent.setType("*/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            }
        });

        videoView = (VideoView) findViewById(R.id.videoView);
        if (mediaController == null) {
            mediaController = new MediaController(PlayVideo.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
        }

        try {
            videoView.setVideoURI(Uri.parse(selected_video_path));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();

        videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }

}