package mobo.andro.apps.ohmagiccamera.editormodule;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.R;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class BorderActivity extends Activity {
    Bitmap bit;
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    int color = -4738125;
    Button color_button;
    Button compare;
    Button done;
    RelativeLayout footer;
    RelativeLayout header;
    TextView headertext;
    ImageView image;
    RelativeLayout rel;
    SeekBar seek;
    int size = 5;
    private Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.editormodule.BorderActivity$1 */
    class C08141 implements OnClickListener {
        C08141() {
        }

        public void onClick(View v) {
            BorderActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.BorderActivity$2 */
    class C08162 implements OnClickListener {

        /* renamed from: mobo.andro.apps.camera.editormodule.BorderActivity$2$1 */
        class C08151 implements OnAmbilWarnaListener {
            C08151() {
            }

            public void onOk(AmbilWarnaDialog dialog, int cr) {
                BorderActivity.this.color = cr;
                BorderActivity.this.bit = BorderActivity.this.addBorder(BorderActivity.this.bitmap, BorderActivity.this.size, BorderActivity.this.color);
                BorderActivity.this.image.setImageBitmap(BorderActivity.this.bit);
            }

            public void onCancel(AmbilWarnaDialog dialog) {
            }
        }

        C08162() {
        }

        public void onClick(View v) {
            new AmbilWarnaDialog(BorderActivity.this, BorderActivity.this.color, new C08151()).show();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.BorderActivity$3 */
    class C08173 implements OnSeekBarChangeListener {
        C08173() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            BorderActivity.this.size = progress;
            BorderActivity.this.bit = BorderActivity.this.addBorder(BorderActivity.this.bitmap, BorderActivity.this.size, BorderActivity.this.color);
            BorderActivity.this.image.setImageBitmap(BorderActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.BorderActivity$4 */
    class C08184 implements OnClickListener {
        C08184() {
        }

        public void onClick(View v) {
            PhotoEditor.edBitmap = BorderActivity.this.bit;
            BorderActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.BorderActivity$5 */
    class C08195 implements OnTouchListener {
        C08195() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    BorderActivity.this.image.setImageBitmap(BorderActivity.this.bitmap);
                    break;
                case 1:
                    BorderActivity.this.image.setImageBitmap(BorderActivity.this.bit);
                    break;
            }
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_border);
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.image = (ImageView) findViewById(R.id.image);
        this.done = (Button) findViewById(R.id.done);
        this.color_button = (Button) findViewById(R.id.color_button);
        this.seek = (SeekBar) findViewById(R.id.seek);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.compare = (Button) findViewById(R.id.compare);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_down);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.bitmap = PhotoEditor.edBitmap;
        this.bit = PhotoEditor.edBitmap;
        this.bit = addBorder(this.bitmap, this.size, this.color);
        this.image.setImageBitmap(this.bit);
        this.seek.setMax(100);
        this.seek.setProgress(this.size);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.compare.setTypeface(this.ttf);
        this.color_button.setTypeface(this.ttf);
        findViewById(R.id.btn_bck).setOnClickListener(new C08141());
        this.color_button.setOnClickListener(new C08162());
        this.seek.setOnSeekBarChangeListener(new C08173());
        this.done.setOnClickListener(new C08184());
        this.compare.setOnTouchListener(new C08195());
    }

    private Bitmap addBorder(Bitmap bmp, int borderSize, int color) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + (borderSize * 2), bmp.getHeight() + (borderSize * 2), bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(color);
        canvas.drawBitmap(bmp, (float) borderSize, (float) borderSize, null);
        return bmpWithBorder;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
