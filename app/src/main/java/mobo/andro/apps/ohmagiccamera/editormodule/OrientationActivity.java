package mobo.andro.apps.ohmagiccamera.editormodule;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.R;

public class OrientationActivity extends Activity {
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    Button done;
    LinearLayout footer;
    RelativeLayout header;
    TextView headertext;
    Button horizontalflip;
    ImageView image;
    Button leftrotate;
    RelativeLayout rel;
    Button rightrotate;
    Typeface ttf;
    Button verticalflip;

    /* renamed from: mobo.andro.apps.camera.editormodule.OrientationActivity$1 */
    class C08521 implements OnClickListener {
        C08521() {
        }

        public void onClick(View v) {
            OrientationActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OrientationActivity$2 */
    class C08532 implements OnClickListener {
        C08532() {
        }

        public void onClick(View v) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90.0f);
            OrientationActivity.this.bitmap = Bitmap.createBitmap(OrientationActivity.this.bitmap, 0, 0, OrientationActivity.this.bitmap.getWidth(), OrientationActivity.this.bitmap.getHeight(), matrix, true);
            OrientationActivity.this.image.setImageBitmap(OrientationActivity.this.bitmap);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OrientationActivity$3 */
    class C08543 implements OnClickListener {
        C08543() {
        }

        public void onClick(View v) {
            Matrix matrix = new Matrix();
            matrix.postRotate(270.0f);
            OrientationActivity.this.bitmap = Bitmap.createBitmap(OrientationActivity.this.bitmap, 0, 0, OrientationActivity.this.bitmap.getWidth(), OrientationActivity.this.bitmap.getHeight(), matrix, true);
            OrientationActivity.this.image.setImageBitmap(OrientationActivity.this.bitmap);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OrientationActivity$4 */
    class C08554 implements OnClickListener {
        C08554() {
        }

        public void onClick(View v) {
            Matrix matrix = new Matrix();
            matrix.preScale(1.0f, -1.0f);
            OrientationActivity.this.bitmap = Bitmap.createBitmap(OrientationActivity.this.bitmap, 0, 0, OrientationActivity.this.bitmap.getWidth(), OrientationActivity.this.bitmap.getHeight(), matrix, true);
            OrientationActivity.this.image.setImageBitmap(OrientationActivity.this.bitmap);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OrientationActivity$5 */
    class C08565 implements OnClickListener {
        C08565() {
        }

        public void onClick(View v) {
            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);
            OrientationActivity.this.bitmap = Bitmap.createBitmap(OrientationActivity.this.bitmap, 0, 0, OrientationActivity.this.bitmap.getWidth(), OrientationActivity.this.bitmap.getHeight(), matrix, true);
            OrientationActivity.this.image.setImageBitmap(OrientationActivity.this.bitmap);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OrientationActivity$6 */
    class C08576 implements OnClickListener {
        C08576() {
        }

        public void onClick(View v) {
            PhotoEditor.edBitmap = OrientationActivity.this.bitmap;
            OrientationActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_orientation);
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.footer = (LinearLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.image = (ImageView) findViewById(R.id.image);
        this.leftrotate = (Button) findViewById(R.id.leftrotate);
        this.rightrotate = (Button) findViewById(R.id.rightrotate);
        this.verticalflip = (Button) findViewById(R.id.verticalflip);
        this.horizontalflip = (Button) findViewById(R.id.horizontalflip);
        this.done = (Button) findViewById(R.id.done);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_down);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.bitmap = PhotoEditor.edBitmap;
        this.image.setImageBitmap(this.bitmap);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.leftrotate.setTypeface(this.ttf);
        this.rightrotate.setTypeface(this.ttf);
        this.verticalflip.setTypeface(this.ttf);
        this.horizontalflip.setTypeface(this.ttf);
        findViewById(R.id.btn_bck).setOnClickListener(new C08521());
        this.leftrotate.setOnClickListener(new C08532());
        this.rightrotate.setOnClickListener(new C08543());
        this.verticalflip.setOnClickListener(new C08554());
        this.horizontalflip.setOnClickListener(new C08565());
        this.done.setOnClickListener(new C08576());
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
