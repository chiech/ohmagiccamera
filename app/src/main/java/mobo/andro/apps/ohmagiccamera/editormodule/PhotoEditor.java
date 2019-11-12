package mobo.andro.apps.ohmagiccamera.editormodule;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnScrollChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class PhotoEditor extends Activity {
    public static Bitmap bitmap;
    static Bitmap edBitmap;
    public static Bitmap resultBitmap;
    RelativeLayout allrel;
    Animation blink;
    Button border;
    Animation bottomDown;
    Animation bottomUp;
    Button compare;
    Button crop;
    Button done;
    Button effects;
    Button enhancer;
    RelativeLayout footer;
    RelativeLayout forcalrel;
    Button frames;
    RelativeLayout header;
    TextView headertext;
    ImageView image;
    ImageView img_blink;
    LinearLayout linearlayout;
    Button orientation;
    Button overlays;
    RelativeLayout rel;
    HorizontalScrollView scrollView;
    Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$2 */
    class C08682 implements OnClickListener {
        C08682() {
        }

        public void onClick(View v) {
            PhotoEditor.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$3 */
    class C08693 implements OnScrollChangeListener {
        C08693() {
        }

        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (PhotoEditor.this.scrollView.getMaxScrollAmount() < scrollX) {
                PhotoEditor.this.img_blink.clearAnimation();
                PhotoEditor.this.img_blink.setVisibility(View.GONE);
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$4 */
    class C08704 implements OnScrollChangedListener {
        C08704() {
        }

        public void onScrollChanged() {
            PhotoEditor.this.img_blink.clearAnimation();
            PhotoEditor.this.img_blink.setVisibility(View.GONE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$5 */
    class C08715 implements OnClickListener {
        C08715() {
        }

        public void onClick(View v) {
            PhotoEditor.this.startActivity(new Intent(PhotoEditor.this, CBSSActivity.class));
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$6 */
    class C08726 implements OnClickListener {
        C08726() {
        }

        public void onClick(View v) {

            try {
                Log.e("crop_activity"," : open");
                Intent intent = new Intent(PhotoEditor.this, CropActivity.class);
                intent.putExtra("forcal", PhotoEditor.this.forcalrel.getHeight());
                PhotoEditor.this.startActivity(intent);
            }
            catch (Exception e){
                Log.e("crop_activity"," : "+e);
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$7 */
    class C08737 implements OnClickListener {
        C08737() {
        }

        public void onClick(View v) {
            PhotoEditor.this.startActivity(new Intent(PhotoEditor.this, OrientationActivity.class));
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$8 */
    class C08748 implements OnClickListener {
        C08748() {
        }

        public void onClick(View v) {
            PhotoEditor.this.startActivity(new Intent(PhotoEditor.this, EffectsActivity.class));
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.PhotoEditor$9 */
    class C08759 implements OnClickListener {
        C08759() {
        }

        public void onClick(View v) {
            PhotoEditor.this.startActivity(new Intent(PhotoEditor.this, OverlaysActivity.class));
        }
    }

    public void onPause()
    {
        super.onPause();
        CustomAds.dismissInterstitialGoogle(PhotoEditor.this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_photo_editor);

        CustomAds.facebookAdInterstitial(PhotoEditor.this);
        this.allrel = (RelativeLayout) findViewById(R.id.allrel);
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.forcalrel = (RelativeLayout) findViewById(R.id.forcalrel);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.enhancer = (Button) findViewById(R.id.enhancer);
        this.crop = (Button) findViewById(R.id.crop);
        this.orientation = (Button) findViewById(R.id.orientation);
        this.effects = (Button) findViewById(R.id.effects);
        this.overlays = (Button) findViewById(R.id.overlays);
        this.frames = (Button) findViewById(R.id.frames);
        this.border = (Button) findViewById(R.id.border);
        this.done = (Button) findViewById(R.id.done);
        this.compare = (Button) findViewById(R.id.compare);
        this.image = (ImageView) findViewById(R.id.image);
        this.scrollView = (HorizontalScrollView) findViewById(R.id.scrollview);
        this.linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        this.img_blink = (ImageView) findViewById(R.id.img_blink);
        this.img_blink.setColorFilter(-1);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_down);
        this.blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_blink);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.img_blink.startAnimation(this.blink);
        if (bitmap != null) {
            edBitmap = bitmap;
        } else {
            int style;
            final Activity activity = this;
            if (VERSION.SDK_INT >= 14) {
                style = 16974126;
            } else {
                style = 16973835;
            }
            AlertDialog errDialog = new Builder(this, style).setTitle(getResources().getString(R.string.editor_dialog_title)).setMessage(getResources().getString(R.string.editor_dialog_msg)).setCancelable(false).setPositiveButton(getResources().getString(R.string.editor_ok), new DialogInterface.OnClickListener() {
                @TargetApi(23)
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            }).create();
            int EditorDialogAnimation_ = 2131296480;
            errDialog.getWindow().getAttributes().windowAnimations =EditorDialogAnimation_;
            errDialog.show();
        }
        this.image.setImageBitmap(bitmap);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.done.setTypeface(this.ttf);
        this.compare.setTypeface(this.ttf);
        this.enhancer.setTypeface(this.ttf);
        this.crop.setTypeface(this.ttf);
        this.orientation.setTypeface(this.ttf);
        this.effects.setTypeface(this.ttf);
        this.overlays.setTypeface(this.ttf);
        this.border.setTypeface(this.ttf);
        this.frames.setTypeface(this.ttf);
        findViewById(R.id.btn_bck).setOnClickListener(new C08682());
        if (VERSION.SDK_INT >= 23) {
            this.scrollView.setOnScrollChangeListener(new C08693());
        } else {
            this.scrollView.getViewTreeObserver().addOnScrollChangedListener(new C08704());
        }
        this.enhancer.setOnClickListener(new C08715());
//        this.crop.setOnClickListener(new C08726());
        this.orientation.setOnClickListener(new C08737());
        this.effects.setOnClickListener(new C08748());
        this.overlays.setOnClickListener(new C08759());

        this.frames.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhotoEditor.this.startActivity(new Intent(PhotoEditor.this, FramesActivity.class));
            }
        });
        this.border.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhotoEditor.this.startActivity(new Intent(PhotoEditor.this, BorderActivity.class));
            }
        });
        this.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (PhotoEditor.edBitmap != null) {
                    PhotoEditor.resultBitmap = PhotoEditor.edBitmap.copy(PhotoEditor.edBitmap.getConfig(), true);
                    PhotoEditor.this.setResult(-1);
                }
                PhotoEditor.this.finish();
            }
        });
        this.compare.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        PhotoEditor.this.image.setImageBitmap(PhotoEditor.bitmap);
                        break;
                    case 1:
                        PhotoEditor.this.image.setImageBitmap(PhotoEditor.edBitmap);
                        break;
                }
                return true;
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
        this.image.setImageBitmap(edBitmap);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        if (edBitmap != null) {
            edBitmap.recycle();
            edBitmap = null;
        }
    }
}
