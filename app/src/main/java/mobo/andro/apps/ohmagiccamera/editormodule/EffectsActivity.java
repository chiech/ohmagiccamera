package mobo.andro.apps.ohmagiccamera.editormodule;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
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

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.editormodule.GPUImageFilterTools.FilterAdjuster;

public class EffectsActivity extends Activity {
    Bitmap bb;
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    Button compare;
    Button done;
    Button e1;
    Button e10;
    Button e11;
    Button e12;
    Button e13;
    Button e14;
    Button e15;
    Button e16;
    Button e17;
    Button e18;
    Button e19;
    Button e2;
    Button e20;
    Button e21;
    Button e22;
    Button e23;
    Button e24;
    Button e25;
    Button e3;
    Button e4;
    Button e5;
    Button e6;
    Button e7;
    Button e8;
    Button e9;
    RelativeLayout footer;
    GPUImage gpuimage;
    RelativeLayout header;
    TextView headertext;
    GPUImageView image;
    ImageView image1;
    private GPUImageFilter mFilter;
    private FilterAdjuster mFilterAdjuster;
    RelativeLayout rel;
    SeekBar seek;
    Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$1 */
    class C08321 implements OnClickListener {
        C08321() {
        }

        public void onClick(View v) {
            EffectsActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$2 */
    class C08332 implements OnClickListener {
        C08332() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.SEPIA));
            EffectsActivity.this.image.requestRender();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$3 */
    class C08343 implements OnClickListener {
        C08343() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.HUE));
            EffectsActivity.this.image.requestRender();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$4 */
    class C08354 implements OnClickListener {
        C08354() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.PIXELATION));
            EffectsActivity.this.image.requestRender();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$5 */
    class C08365 implements OnClickListener {
        C08365() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.GRAYSCALE));
            EffectsActivity.this.image.requestRender();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$6 */
    class C08376 implements OnClickListener {
        C08376() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.GAMMA));
            EffectsActivity.this.image.requestRender();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$7 */
    class C08387 implements OnClickListener {
        C08387() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.POSTERIZE));
            EffectsActivity.this.image.requestRender();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$8 */
    class C08398 implements OnClickListener {
        C08398() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.EMBOSS));
            EffectsActivity.this.image.requestRender();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.EffectsActivity$9 */
    class C08409 implements OnClickListener {
        C08409() {
        }

        public void onClick(View v) {
            EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.HIGHLIGHT_SHADOW));
            EffectsActivity.this.image.requestRender();
        }
    }

    class ThumbnailLoader extends AsyncTask<Void, Void, Void> {
        Bitmap bb1;
        Bitmap bb10;
        Bitmap bb11;
        Bitmap bb12;
        Bitmap bb13;
        Bitmap bb14;
        Bitmap bb15;
        Bitmap bb16;
        Bitmap bb17;
        Bitmap bb18;
        Bitmap bb19;
        Bitmap bb2;
        Bitmap bb20;
        Bitmap bb21;
        Bitmap bb22;
        Bitmap bb23;
        Bitmap bb24;
        Bitmap bb25;
        Bitmap bb3;
        Bitmap bb4;
        Bitmap bb5;
        Bitmap bb6;
        Bitmap bb7;
        Bitmap bb8;
        Bitmap bb9;

        ThumbnailLoader() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(EffectsActivity.this.bitmap, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.bb25 = createScaledBitmap;
            this.bb24 = createScaledBitmap;
            this.bb23 = createScaledBitmap;
            this.bb22 = createScaledBitmap;
            this.bb21 = createScaledBitmap;
            this.bb20 = createScaledBitmap;
            this.bb19 = createScaledBitmap;
            this.bb18 = createScaledBitmap;
            this.bb17 = createScaledBitmap;
            this.bb16 = createScaledBitmap;
            this.bb15 = createScaledBitmap;
            this.bb14 = createScaledBitmap;
            this.bb13 = createScaledBitmap;
            this.bb12 = createScaledBitmap;
            this.bb11 = createScaledBitmap;
            this.bb10 = createScaledBitmap;
            this.bb9 = createScaledBitmap;
            this.bb8 = createScaledBitmap;
            this.bb7 = createScaledBitmap;
            this.bb6 = createScaledBitmap;
            this.bb5 = createScaledBitmap;
            this.bb4 = createScaledBitmap;
            this.bb3 = createScaledBitmap;
            this.bb2 = createScaledBitmap;
            this.bb1 = createScaledBitmap;
            EffectsActivity.this.gpuimage = new GPUImage(EffectsActivity.this.getApplicationContext());
        }

        protected Void doInBackground(Void... params) {
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.SEPIA));
            this.bb1 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb1);
            this.bb1 = EffectsActivity.this.addBorder(this.bb1, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.HUE));
            this.bb2 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb2);
            this.bb2 = EffectsActivity.this.addBorder(this.bb2, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.PIXELATION));
            this.bb3 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb3);
            this.bb3 = EffectsActivity.this.addBorder(this.bb3, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.GRAYSCALE));
            this.bb4 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb4);
            this.bb4 = EffectsActivity.this.addBorder(this.bb4, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.GAMMA));
            this.bb5 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb5);
            this.bb5 = EffectsActivity.this.addBorder(this.bb5, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.POSTERIZE));
            this.bb6 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb6);
            this.bb6 = EffectsActivity.this.addBorder(this.bb6, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.EMBOSS));
            this.bb7 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb7);
            this.bb7 = EffectsActivity.this.addBorder(this.bb7, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.HIGHLIGHT_SHADOW));
            this.bb8 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb8);
            this.bb8 = EffectsActivity.this.addBorder(this.bb8, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.RGB));
            this.bb9 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb9);
            this.bb9 = EffectsActivity.this.addBorder(this.bb9, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.MONOCHROME));
            this.bb10 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb10);
            this.bb10 = EffectsActivity.this.addBorder(this.bb10, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.WHITE_BALANCE));
            this.bb11 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb11);
            this.bb11 = EffectsActivity.this.addBorder(this.bb11, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.VIGNETTE));
            this.bb12 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb12);
            this.bb12 = EffectsActivity.this.addBorder(this.bb12, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.TONE_CURVE));
            this.bb13 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb13);
            this.bb13 = EffectsActivity.this.addBorder(this.bb13, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.LOOKUP_AMATORKA));
            this.bb14 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb14);
            this.bb14 = EffectsActivity.this.addBorder(this.bb14, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.GAUSSIAN_BLUR));
            this.bb15 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb15);
            this.bb15 = EffectsActivity.this.addBorder(this.bb15, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.DILATION));
            this.bb16 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb16);
            this.bb16 = EffectsActivity.this.addBorder(this.bb16, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.KUWAHARA));
            this.bb17 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb17);
            this.bb17 = EffectsActivity.this.addBorder(this.bb17, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.RGB_DILATION));
            this.bb18 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb18);
            this.bb18 = EffectsActivity.this.addBorder(this.bb18, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.TOON));
            this.bb19 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb19);
            this.bb19 = EffectsActivity.this.addBorder(this.bb19, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.BULGE_DISTORTION));
            this.bb20 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb20);
            this.bb20 = EffectsActivity.this.addBorder(this.bb20, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.EXPOSURE));
            this.bb21 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb21);
            this.bb21 = EffectsActivity.this.addBorder(this.bb21, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.SWIRL));
            this.bb22 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb22);
            this.bb22 = EffectsActivity.this.addBorder(this.bb22, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.FALSE_COLOR));
            this.bb23 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb23);
            this.bb23 = EffectsActivity.this.addBorder(this.bb23, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.COLOR_BALANCE));
            this.bb24 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb24);
            this.bb24 = EffectsActivity.this.addBorder(this.bb24, 3, -1);
            EffectsActivity.this.gpuimage.setFilter(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.LEVELS_FILTER_MIN));
            this.bb25 = EffectsActivity.this.gpuimage.getBitmapWithFilterApplied(this.bb25);
            this.bb25 = EffectsActivity.this.addBorder(this.bb25, 3, -1);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (VERSION.SDK_INT < 16) {
                EffectsActivity.this.e1.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb1));
                EffectsActivity.this.e2.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb2));
                EffectsActivity.this.e3.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb3));
                EffectsActivity.this.e4.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb4));
                EffectsActivity.this.e5.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb5));
                EffectsActivity.this.e6.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb6));
                EffectsActivity.this.e7.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb7));
                EffectsActivity.this.e8.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb8));
                EffectsActivity.this.e9.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb9));
                EffectsActivity.this.e10.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb10));
                EffectsActivity.this.e11.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb11));
                EffectsActivity.this.e12.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb12));
                EffectsActivity.this.e13.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb13));
                EffectsActivity.this.e14.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb14));
                EffectsActivity.this.e15.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb15));
                EffectsActivity.this.e16.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb16));
                EffectsActivity.this.e17.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb17));
                EffectsActivity.this.e18.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb18));
                EffectsActivity.this.e19.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb19));
                EffectsActivity.this.e20.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb20));
                EffectsActivity.this.e21.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb21));
                EffectsActivity.this.e22.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb22));
                EffectsActivity.this.e23.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb23));
                EffectsActivity.this.e24.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb24));
                EffectsActivity.this.e25.setBackgroundDrawable(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb25));
                return;
            }
            EffectsActivity.this.e1.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb1));
            EffectsActivity.this.e2.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb2));
            EffectsActivity.this.e3.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb3));
            EffectsActivity.this.e4.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb4));
            EffectsActivity.this.e5.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb5));
            EffectsActivity.this.e6.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb6));
            EffectsActivity.this.e7.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb7));
            EffectsActivity.this.e8.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb8));
            EffectsActivity.this.e9.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb9));
            EffectsActivity.this.e10.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb10));
            EffectsActivity.this.e11.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb11));
            EffectsActivity.this.e12.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb12));
            EffectsActivity.this.e13.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb13));
            EffectsActivity.this.e14.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb14));
            EffectsActivity.this.e15.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb15));
            EffectsActivity.this.e16.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb16));
            EffectsActivity.this.e17.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb17));
            EffectsActivity.this.e18.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb18));
            EffectsActivity.this.e19.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb19));
            EffectsActivity.this.e20.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb20));
            EffectsActivity.this.e21.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb21));
            EffectsActivity.this.e22.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb22));
            EffectsActivity.this.e23.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb23));
            EffectsActivity.this.e24.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb24));
            EffectsActivity.this.e25.setBackground(new BitmapDrawable(EffectsActivity.this.getResources(), this.bb25));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_effects);
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.image = (GPUImageView) findViewById(R.id.gpuimage);
        this.done = (Button) findViewById(R.id.done);
        this.seek = (SeekBar) findViewById(R.id.seek);
        this.seek.setProgress(50);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.compare = (Button) findViewById(R.id.compare);
        this.image1 = (ImageView) findViewById(R.id.image1);
        this.e1 = (Button) findViewById(R.id.e1);
        this.e2 = (Button) findViewById(R.id.e2);
        this.e3 = (Button) findViewById(R.id.e3);
        this.e4 = (Button) findViewById(R.id.e4);
        this.e5 = (Button) findViewById(R.id.e5);
        this.e6 = (Button) findViewById(R.id.e6);
        this.e7 = (Button) findViewById(R.id.e7);
        this.e8 = (Button) findViewById(R.id.e8);
        this.e9 = (Button) findViewById(R.id.e9);
        this.e10 = (Button) findViewById(R.id.e10);
        this.e11 = (Button) findViewById(R.id.e11);
        this.e12 = (Button) findViewById(R.id.e12);
        this.e13 = (Button) findViewById(R.id.e13);
        this.e14 = (Button) findViewById(R.id.e14);
        this.e15 = (Button) findViewById(R.id.e15);
        this.e16 = (Button) findViewById(R.id.e16);
        this.e17 = (Button) findViewById(R.id.e17);
        this.e18 = (Button) findViewById(R.id.e18);
        this.e19 = (Button) findViewById(R.id.e19);
        this.e20 = (Button) findViewById(R.id.e20);
        this.e21 = (Button) findViewById(R.id.e21);
        this.e22 = (Button) findViewById(R.id.e22);
        this.e23 = (Button) findViewById(R.id.e23);
        this.e24 = (Button) findViewById(R.id.e24);
        this.e25 = (Button) findViewById(R.id.e25);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.bitmap = PhotoEditor.edBitmap;
        this.image.setRatio(((float) this.bitmap.getWidth()) / ((float) this.bitmap.getHeight()));
        this.image.setImage(this.bitmap);
        this.image1.setImageBitmap(this.bitmap);
        this.image1.setVisibility(View.INVISIBLE);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.compare.setTypeface(this.ttf);
        new ThumbnailLoader().execute(new Void[0]);
        findViewById(R.id.btn_bck).setOnClickListener(new C08321());
        this.e1.setOnClickListener(new C08332());
        this.e2.setOnClickListener(new C08343());
        this.e3.setOnClickListener(new C08354());
        this.e4.setOnClickListener(new C08365());
        this.e5.setOnClickListener(new C08376());
        this.e6.setOnClickListener(new C08387());
        this.e7.setOnClickListener(new C08398());
        this.e8.setOnClickListener(new C08409());
        this.e9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.RGB));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.MONOCHROME));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.WHITE_BALANCE));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e12.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.VIGNETTE));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e13.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.TONE_CURVE));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.LOOKUP_AMATORKA));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.GAUSSIAN_BLUR));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e16.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.DILATION));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e17.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.KUWAHARA));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e18.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.RGB_DILATION));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e19.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.TOON));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e20.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.BULGE_DISTORTION));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e21.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.EXPOSURE));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e22.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.SWIRL));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e23.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.FALSE_COLOR));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e24.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.COLOR_BALANCE));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.e25.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EffectsActivity.this.switchFilterTo(GPUImageFilterTools.createFilterForType(EffectsActivity.this, GPUImageFilterTools.FilterType.LEVELS_FILTER_MIN));
                EffectsActivity.this.image.requestRender();
            }
        });
        this.seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (EffectsActivity.this.mFilterAdjuster != null) {
                    EffectsActivity.this.mFilterAdjuster.adjust(progress);
                }
                EffectsActivity.this.image.requestRender();
            }
        });
        this.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    PhotoEditor.edBitmap = EffectsActivity.this.image.capture();
                    EffectsActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.compare.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        EffectsActivity.this.image1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        EffectsActivity.this.image1.setVisibility(View.INVISIBLE);
                        break;
                }
                return true;
            }
        });
    }

    private void switchFilterTo(GPUImageFilter filter) {
        if (this.mFilter == null || !(filter == null || this.mFilter.getClass().equals(filter.getClass()))) {
            this.mFilter = filter;
            this.image.setFilter(this.mFilter);
            this.mFilterAdjuster = new FilterAdjuster(this.mFilter);
            this.seek.setVisibility(this.mFilterAdjuster.canAdjust() ? 0 : 8);
            this.seek.setProgress(50);
            this.mFilterAdjuster.adjust(this.seek.getProgress());
            this.image.requestRender();
        }
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
