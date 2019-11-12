package mobo.andro.apps.ohmagiccamera.editormodule;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.editormodule.GPUImageFilterTools.FilterAdjuster;

public class CBSSActivity extends Activity implements OnSeekBarChangeListener {
    Bitmap bitmap;
    Boolean brbool = Boolean.valueOf(true);
    SeekBar brseek;
    TextView brtext;
    Boolean cobool = Boolean.valueOf(true);
    Button compare;
    SeekBar coseek;
    TextView cotext;
    Button done;
    TextView headertext;
    GPUImageView image;
    ImageView image1;
    private GPUImageFilter mFilter;
    private FilterAdjuster mFilterAdjuster;
    RelativeLayout rel;
    Boolean sabool = Boolean.valueOf(true);
    SeekBar saseek;
    TextView satext;
    Boolean shbool = Boolean.valueOf(true);
    SeekBar shseek;
    TextView shtext;
    Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.editormodule.CBSSActivity$1 */
    class C08201 implements OnClickListener {
        C08201() {
        }

        public void onClick(View v) {
            CBSSActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CBSSActivity$2 */
    class C08212 implements OnClickListener {
        C08212() {
        }

        public void onClick(View v) {
            try {
                PhotoEditor.edBitmap = CBSSActivity.this.image.capture();
                CBSSActivity.this.finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CBSSActivity$3 */
    class C08223 implements OnTouchListener {
        C08223() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    CBSSActivity.this.image1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    CBSSActivity.this.image1.setVisibility(View.INVISIBLE);
                    break;
            }
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_cbss);
        this.image = (GPUImageView) findViewById(R.id.gpuimage);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.coseek = (SeekBar) findViewById(R.id.coseek);
        this.brseek = (SeekBar) findViewById(R.id.brseek);
        this.saseek = (SeekBar) findViewById(R.id.saseek);
        this.shseek = (SeekBar) findViewById(R.id.shseek);
        this.done = (Button) findViewById(R.id.done);
        this.compare = (Button) findViewById(R.id.compare);
        this.image1 = (ImageView) findViewById(R.id.image1);
        this.coseek.setOnSeekBarChangeListener(this);
        this.brseek.setOnSeekBarChangeListener(this);
        this.saseek.setOnSeekBarChangeListener(this);
        this.shseek.setOnSeekBarChangeListener(this);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.cotext = (TextView) findViewById(R.id.cotext);
        this.brtext = (TextView) findViewById(R.id.brtext);
        this.satext = (TextView) findViewById(R.id.satext);
        this.shtext = (TextView) findViewById(R.id.shtext);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.compare.setTypeface(this.ttf);
        this.cotext.setTypeface(this.ttf);
        this.brtext.setTypeface(this.ttf);
        this.satext.setTypeface(this.ttf);
        this.shtext.setTypeface(this.ttf);
        this.bitmap = PhotoEditor.edBitmap;
        this.image.setRatio(((float) this.bitmap.getWidth()) / ((float) this.bitmap.getHeight()));
        this.image.setImage(this.bitmap);
        this.image1.setImageBitmap(this.bitmap);
        this.image1.setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_bck).setOnClickListener(new C08201());
        this.done.setOnClickListener(new C08212());
        this.compare.setOnTouchListener(new C08223());
    }

    private void switchFilterTo(GPUImageFilter filter) {
        if (this.mFilter == null || !(filter == null || this.mFilter.getClass().equals(filter.getClass()))) {
            this.mFilter = filter;
            this.image.setFilter(this.mFilter);
            this.mFilterAdjuster = new FilterAdjuster(this.mFilter);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        GPUImageFilter filter1 = GPUImageFilterTools.createFilterForType(this, GPUImageFilterTools.FilterType.CONTRAST);
        GPUImageFilter filter2 = GPUImageFilterTools.createFilterForType(this, GPUImageFilterTools.FilterType.BRIGHTNESS);
        GPUImageFilter filter3 = GPUImageFilterTools.createFilterForType(this, GPUImageFilterTools.FilterType.SATURATION);
        GPUImageFilter filter4 = GPUImageFilterTools.createFilterForType(this, GPUImageFilterTools.FilterType.SHARPEN);
        new FilterAdjuster(filter1).adjust(this.coseek.getProgress());
        new FilterAdjuster(filter2).adjust(this.brseek.getProgress() + 25);
        new FilterAdjuster(filter3).adjust(this.saseek.getProgress());
        new FilterAdjuster(filter4).adjust(this.shseek.getProgress());
        GPUImageFilterGroup group = new GPUImageFilterGroup();
        group.addFilter(filter1);
        group.addFilter(filter2);
        group.addFilter(filter3);
        group.addFilter(filter4);
        this.image.setFilter(group);
        this.image.requestRender();
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
