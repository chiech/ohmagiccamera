package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;

import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class ShareImageActivity extends Activity implements OnClickListener {
    private static final String MyPREFERENCES = "CollageMakerPref";
    boolean ch = false;
    private boolean fromGallery = false;
    private boolean fromGridCollageActivity = false;
    private boolean hasRated = false;
    private ImageView imageView;
//    InterstitialAd mInterstitialAd;
    private Uri phototUri = null;
    private RelativeLayout remove_watermark_lay;
    SharedPreferences sharedpreferences;
    private Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.CollageMaker.ShareImageActivity$2 */
    class listenerone implements OnDismissListener {
        listenerone() {
        }

        public void onDismiss(DialogInterface dialog) {
//            if (ShareImageActivity.this.mInterstitialAd.isLoaded()) {
//                ShareImageActivity.this.mInterstitialAd.show();
//            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.ShareImageActivity$3 */
    class listenertwo implements DialogInterface.OnClickListener {
        listenertwo() {
        }

        public void onClick(DialogInterface dialog, int which) {
            String url = "https://play.google.com/store/apps/details?id=" + ShareImageActivity.this.getPackageName();
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            ShareImageActivity.this.startActivity(i);
            dialog.cancel();
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.ShareImageActivity$4 */
    class listenerthree implements DialogInterface.OnClickListener {

        /* renamed from: mobo.andro.apps.camera.CollageMaker.ShareImageActivity$4$1 */
        class C03811 implements DialogInterface.OnClickListener {
            C03811() {
            }

            public void onClick(DialogInterface dialog, int which) {
                Intent emailIntent = new Intent("android.intent.action.SENDTO");
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{"picartexpert@gmail.com"});
                emailIntent.putExtra("android.intent.extra.SUBJECT", ShareImageActivity.this.getResources().getString(R.string.app_name));
                emailIntent.putExtra("android.intent.extra.TEXT", ShareImageActivity.this.getResources().getString(R.string.email_msg));
                try {
                    ShareImageActivity.this.startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(ShareImageActivity.this, ShareImageActivity.this.getResources().getString(R.string.email_error), Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        }

        /* renamed from: mobo.andro.apps.camera.CollageMaker.ShareImageActivity$4$2 */
        class C03822 implements DialogInterface.OnClickListener {
            C03822() {
            }

            public void onClick(DialogInterface dialog, int which) {
//                if (ShareImageActivity.this.mInterstitialAd.isLoaded()) {
//                    ShareImageActivity.this.mInterstitialAd.show();
//                }
                dialog.cancel();
            }
        }

        listenerthree() {
        }

        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alertDialog = new Builder(ShareImageActivity.this, 16974126).create();
            alertDialog.setMessage(ShareImageActivity.this.getResources().getString(R.string.sugg_msg));
            alertDialog.setButton(ShareImageActivity.this.getResources().getString(R.string.yes1), new C03811());
            alertDialog.setButton2(ShareImageActivity.this.getResources().getString(R.string.no1), new C03822());
            alertDialog.show();
            dialog.cancel();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_share_image);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(ShareImageActivity.this,adContainer);

        this.sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
        this.hasRated = this.sharedpreferences.getBoolean("hasRated", false);
        initUI();
        ((TextView) findViewById(R.id.headertext)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_share)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_rate)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_more)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_remove_watermark)).setTypeface(this.ttf);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initUI() {
        this.remove_watermark_lay = (RelativeLayout) findViewById(R.id.remove_watermark_lay);
        this.imageView = (ImageView) findViewById(R.id.image);
        this.phototUri = Uri.parse(getIntent().getStringExtra("uri"));
        this.imageView.setImageURI(this.phototUri);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);
        findViewById(R.id.btn_rate).setOnClickListener(this);
        findViewById(R.id.btn_more).setOnClickListener(this);
        findViewById(R.id.btn_remove_watermark).setOnClickListener(this);
        this.fromGridCollageActivity = getIntent().getBooleanExtra("fromGridCollageActivity", false);
        this.fromGallery = getIntent().getBooleanExtra("fromGallery", false);
        if (this.fromGallery) {
            this.remove_watermark_lay.setVisibility(View.GONE);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                return;
            case R.id.btn_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.addFlags(536870912);
//                intent.addFlags(67108864);
                startActivity(intent);
                finish();
                return;
            case R.id.btn_share:
                final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", getString(R.string.plzwait), true);
                ringProgressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Intent intent = new Intent("android.intent.action.SEND");
                            File file = new File(ShareImageActivity.this.phototUri.getPath());
                            intent.setType("image/*");
                            intent.putExtra("android.intent.extra.SUBJECT", ShareImageActivity.this.getResources().getString(R.string.app_name));
                            intent.putExtra("android.intent.extra.TEXT", (ShareImageActivity.this.getResources().getString(R.string.sharetext) + " " + ShareImageActivity.this.getResources().getString(R.string.app_name) + ". " + ShareImageActivity.this.getResources().getString(R.string.sharetext1) + "\n\n") + "https://play.google.com/store/apps/details?id=" + ShareImageActivity.this.getPackageName());
                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                            ShareImageActivity.this.startActivity(Intent.createChooser(intent, ShareImageActivity.this.getString(R.string.shareusing).toString()));
                        } catch (Exception e) {
                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();
                ringProgressDialog.setOnDismissListener(new listenerone());
                return;
            case R.id.btn_rate:
                AlertDialog alertDialog = new Builder(this, 16974126).create();
                alertDialog.setTitle(getResources().getString(R.string.rate_us));
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setMessage(getResources().getString(R.string.rate_msg) + "\n\n" + getResources().getString(R.string.rate_msg1));
                alertDialog.setButton(getResources().getString(R.string.yes1), new listenertwo());
                alertDialog.setButton2(getResources().getString(R.string.no1), new listenerthree());
                alertDialog.show();
                return;
            case R.id.btn_more:
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Simply+Entertaining"));
                startActivity(i);
                return;
            case R.id.btn_remove_watermark:
                removeWatermork();
                return;
            default:
                return;
        }
    }


    private void saveBitmap() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.save_image_), true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    File pictureFile = new File(ShareImageActivity.this.phototUri.getPath());
                    try {
                        if (!pictureFile.exists()) {
                            pictureFile.createNewFile();
                        }
                        FileOutputStream ostream = new FileOutputStream(pictureFile);
                        if (!ShareImageActivity.this.fromGridCollageActivity || CollageEditorActivity.resultBitmap == null) {
                            FreeCollageActivity.resultBitmap.compress(CompressFormat.PNG, 100, ostream);
                        } else {
                            CollageEditorActivity.resultBitmap.compress(CompressFormat.PNG, 100, ostream);
                        }
                        ostream.flush();
                        ostream.close();
                        ShareImageActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(pictureFile)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(1000);
                } catch (Exception e2) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();
        ringProgressDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(ShareImageActivity.this.getApplicationContext(), ShareImageActivity.this.getString(R.string.saved).toString() + " " + ShareImageActivity.this.phototUri.getPath(), 0).show();
                if (ShareImageActivity.this.fromGridCollageActivity) {
                    ShareImageActivity.this.imageView.setImageBitmap(CollageEditorActivity.resultBitmap);
                } else {
                    ShareImageActivity.this.imageView.setImageBitmap(FreeCollageActivity.resultBitmap);
                }
            }
        });
    }


//    private boolean isNetworkAvailable() {
//        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    protected void onResume() {
        super.onResume();
    }

    public void removeWatermork() {
        final Dialog dialog = new Dialog(this, 16974128);
        dialog.setContentView(R.layout.remove_watermark_dialog);
        ((TextView) dialog.findViewById(R.id.headertext)).setTypeface(this.ttf);
        ((TextView) dialog.findViewById(R.id.remove_watermark_msg)).setTypeface(this.ttf);
        TextView youhave = (TextView) dialog.findViewById(R.id.youhave);
        youhave.setTypeface(this.ttf);
        Button no_thanks = (Button) dialog.findViewById(R.id.no_thanks);
        no_thanks.setTypeface(this.ttf);
        no_thanks.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button watch_ad = (Button) dialog.findViewById(R.id.watch_ad);
        watch_ad.setTypeface(this.ttf);
        watch_ad.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ShareImageActivity.this.saveBitmap();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
