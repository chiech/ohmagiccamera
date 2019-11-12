package mobo.andro.apps.ohmagiccamera.textmodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerColorAdapter;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerImageAdapter;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerItemClickListener;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerItemClickListener.OnItemClickListener;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class TextActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {
    private AssetsGrid adapter;
    private AutoFitEditText autoFitEditText;
    private int bgAlpha = 255;
    private int bgColor = 0;
    private String bgDrawable = "0";
    private RelativeLayout bg_rel;
    private ImageButton btn_back;
    private ImageButton btn_ok;
    private Bundle bundle;
    View clickedView;
    private RelativeLayout color_rel;
    boolean firsttime = true;
    private String fontName = "";
    private RelativeLayout font_grid_rel;
    private GridView font_gridview;
    String hex = "";
    String hex1 = "";
    private TextView hint_txt;
    private ImageView ic_kb;
    int[] imageId = new int[]{R.drawable.btxt0, R.drawable.btxt1, R.drawable.btxt2, R.drawable.btxt3, R.drawable.btxt4, R.drawable.btxt5, R.drawable.btxt6, R.drawable.btxt7, R.drawable.btxt8, R.drawable.btxt9, R.drawable.btxt10, R.drawable.btxt11, R.drawable.btxt12, R.drawable.btxt13, R.drawable.btxt14, R.drawable.btxt15, R.drawable.btxt16, R.drawable.btxt17, R.drawable.btxt18, R.drawable.btxt19, R.drawable.btxt20, R.drawable.btxt21, R.drawable.btxt22, R.drawable.btxt23, R.drawable.btxt24};
    private InputMethodManager imm;
    private boolean isKbOpened = true;
    private ImageView lay_back_img;
    private RelativeLayout lay_below;
    private RelativeLayout lay_txtbg;
    private RelativeLayout lay_txtcolor;
    private RelativeLayout lay_txtfont;
    private RelativeLayout lay_txtshadow;
    private RelativeLayout laykeyboard;
    String[] pallete = new String[]{"#ffffff", "#000000", "#383838", "#717070", "#bcbbbb", "#ffa800", "#ffcc00", "#ffe824", "#fcee74", "#b50000", "#ed0000", "#fd3e3e", "#ffabab", "#125301", "#2e8e15", "#59db36", "#b8ffa5", "#0244ec", "#0281ec", "#00b4ff", "#00deff"};
    int processValue = 100;
    RecyclerColorAdapter recyclr_Color1;
    RecyclerColorAdapter recyclr_Color2;
    RecyclerColorAdapter recyclr_Color3;
    RecyclerImageAdapter recyclr_Image;
    private SeekBar seekBar;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private int shadowColor = Color.parseColor("#7641b6");
    private int shadowProg = 5;
    private RelativeLayout shadow_rel;
    private int tAlpha = 100;
    private int tColor = Color.parseColor("#4149b6");
    private String text = "";
    private Typeface ttf;
    int value = 0;
    int value2 = 0;

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$1 */
    class C08881 implements Runnable {
        C08881() {
        }

        public void run() {
            TextActivity.this.initUIData();
            TextActivity.this.laykeyboard.performClick();
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$2 */
    class C08892 implements OnGlobalLayoutListener {
        C08892() {
        }

        public void onGlobalLayout() {
            if (TextActivity.this.isKeyboardShown(TextActivity.this.autoFitEditText.getRootView())) {
                TextActivity.this.lay_below.setVisibility(View.INVISIBLE);
                TextActivity.this.setSelected(R.id.laykeyboard);
                TextActivity.this.firsttime = false;
            } else if (!TextActivity.this.firsttime) {
                TextActivity.this.setSelected(TextActivity.this.clickedView.getId());
                TextActivity.this.clickedView.performClick();
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$3 */
    class C08903 implements TextWatcher {
        C08903() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                TextActivity.this.hint_txt.setVisibility(View.VISIBLE);
            } else {
                TextActivity.this.hint_txt.setVisibility(View.GONE);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$4 */
    class C08924 implements OnClickListener {

        /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$4$1 */
        class C08911 implements OnAmbilWarnaListener {
            C08911() {
            }

            public void onOk(AmbilWarnaDialog dialog, int color) {
                TextActivity.this.bgDrawable = "0";
                TextActivity.this.bgColor = color;
                TextActivity.this.lay_back_img.setImageBitmap(null);
                TextActivity.this.lay_back_img.setBackgroundColor(TextActivity.this.bgColor);
                TextActivity.this.lay_back_img.setVisibility(View.VISIBLE);
                TextActivity.this.recyclr_Image.setSelected(500);
                TextActivity.this.recyclr_Color3.setSelected(500);
            }

            public void onCancel(AmbilWarnaDialog dialog) {
            }
        }

        C08924() {
        }

        public void onClick(View v) {
            new AmbilWarnaDialog(TextActivity.this, TextActivity.this.bgColor, new C08911()).show();
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$5 */
    class C08945 implements OnClickListener {

        /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$5$1 */
        class C08931 implements OnAmbilWarnaListener {
            C08931() {
            }

            public void onOk(AmbilWarnaDialog dialog, int color) {
                TextActivity.this.recyclr_Color2.setSelected(500);
                TextActivity.this.updateColor(color, 2);
            }

            public void onCancel(AmbilWarnaDialog dialog) {
            }
        }

        C08945() {
        }

        public void onClick(View v) {
            new AmbilWarnaDialog(TextActivity.this, TextActivity.this.shadowColor, new C08931()).show();
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$6 */
    class C08966 implements OnClickListener {

        /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$6$1 */
        class C08951 implements OnAmbilWarnaListener {
            C08951() {
            }

            public void onOk(AmbilWarnaDialog dialog, int color) {
                TextActivity.this.recyclr_Color1.setSelected(500);
                TextActivity.this.updateColor(color, 1);
            }

            public void onCancel(AmbilWarnaDialog dialog) {
            }
        }

        C08966() {
        }

        public void onClick(View v) {
            new AmbilWarnaDialog(TextActivity.this, TextActivity.this.tColor, new C08951()).show();
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$7 */
    class C08977 implements OnItemClickListener {
        C08977() {
        }

        public void onItemClick(View view, int position) {
            TextActivity.this.lay_back_img.setVisibility(View.VISIBLE);
            String mDrawableName = "btxt" + String.valueOf(position);
            int resID = TextActivity.this.getResources().getIdentifier(mDrawableName, "drawable", TextActivity.this.getPackageName());
            TextActivity.this.bgDrawable = mDrawableName;
            TextActivity.this.bgColor = 0;
            TextActivity.this.lay_back_img.setImageBitmap(TextActivity.this.getTiledBitmap(TextActivity.this, resID, TextActivity.this.autoFitEditText.getWidth(), TextActivity.this.autoFitEditText.getHeight()));
            TextActivity.this.recyclr_Color3.setSelected(500);
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$8 */
    class C08988 implements OnItemClickListener {
        C08988() {
        }

        public void onItemClick(View view, int position) {
            TextActivity.this.updateColor(Color.parseColor(TextActivity.this.pallete[position]), 1);
        }
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.TextActivity$9 */
    class C08999 implements OnItemClickListener {
        C08999() {
        }

        public void onItemClick(View view, int position) {
            TextActivity.this.updateColor(Color.parseColor(TextActivity.this.pallete[position]), 2);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        this.ttf = Typeface.createFromAsset(getAssets(), "Aspergit.otf");
        this.imm = (InputMethodManager) getSystemService("input_method");
        initUI();
        initViewPager();
        this.lay_back_img.post(new C08881());
        ((TextView) findViewById(R.id.headertext)).setTypeface(this.ttf);
        this.autoFitEditText.getViewTreeObserver().addOnGlobalLayoutListener(new C08892());
    }

    private boolean isKeyboardShown(View rootView) {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        return ((float) (rootView.getBottom() - r.bottom)) > 128.0f * rootView.getResources().getDisplayMetrics().density;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initUI() {
        this.font_gridview = (GridView) findViewById(R.id.font_gridview);
        this.autoFitEditText = (AutoFitEditText) findViewById(R.id.auto_fit_edit_text);
        this.lay_back_img = (ImageView) findViewById(R.id.lay_back_txt);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
        this.btn_ok = (ImageButton) findViewById(R.id.btn_ok);
        this.hint_txt = (TextView) findViewById(R.id.hint_txt);
        this.lay_below = (RelativeLayout) findViewById(R.id.lay_below);
        this.laykeyboard = (RelativeLayout) findViewById(R.id.laykeyboard);
        this.lay_txtfont = (RelativeLayout) findViewById(R.id.lay_txtfont);
        this.lay_txtcolor = (RelativeLayout) findViewById(R.id.lay_txtcolor);
        this.lay_txtshadow = (RelativeLayout) findViewById(R.id.lay_txtshadow);
        this.lay_txtbg = (RelativeLayout) findViewById(R.id.lay_txtbg);
        this.font_grid_rel = (RelativeLayout) findViewById(R.id.font_grid_rel);
        this.color_rel = (RelativeLayout) findViewById(R.id.color_rel);
        this.shadow_rel = (RelativeLayout) findViewById(R.id.shadow_rel);
        this.bg_rel = (RelativeLayout) findViewById(R.id.bg_rel);
        this.ic_kb = (ImageView) findViewById(R.id.ic_kb);
        this.clickedView = this.lay_txtfont;
        this.ic_kb.setOnClickListener(this);
        this.seekBar = (SeekBar) findViewById(R.id.seekBar1);
        this.seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        this.seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        this.seekBar.setProgress(this.processValue);
        this.autoFitEditText.addTextChangedListener(new C08903());
        findViewById(R.id.txt_bg_none).setOnClickListener(this);
        findViewById(R.id.color_picker3).setOnClickListener(new C08924());
        findViewById(R.id.color_picker2).setOnClickListener(new C08945());
        findViewById(R.id.color_picker1).setOnClickListener(new C08966());
        showBottomRecycler();
        showBottomRecycler1();
        showBottomRecycler2();
        showBottomRecycler3();
        this.btn_back.setOnClickListener(this);
        this.btn_ok.setOnClickListener(this);
        this.laykeyboard.setOnClickListener(this);
        this.lay_txtfont.setOnClickListener(this);
        this.lay_txtcolor.setOnClickListener(this);
        this.lay_txtshadow.setOnClickListener(this);
        this.lay_txtbg.setOnClickListener(this);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.seekBar2.setOnSeekBarChangeListener(this);
        this.seekBar3.setOnSeekBarChangeListener(this);
        this.seekBar2.setProgress(5);
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.autoFitEditText, 0);
    }

    void showBottomRecycler() {
        this.recyclr_Image = new RecyclerImageAdapter(this, this.imageId);
        RecyclerView bottomRecyclerEdit = (RecyclerView) findViewById(R.id.backgdImage_recylr);
        bottomRecyclerEdit.setLayoutManager(new LinearLayoutManager(this, 0, false));
        bottomRecyclerEdit.setHasFixedSize(true);
        bottomRecyclerEdit.setAdapter(this.recyclr_Image);
        bottomRecyclerEdit.addOnItemTouchListener(new RecyclerItemClickListener(this, new C08977()));
    }

    void showBottomRecycler1() {
        this.recyclr_Color1 = new RecyclerColorAdapter(this, this.pallete);
        RecyclerView bottomRecyclerEdit = (RecyclerView) findViewById(R.id.color_recylr);
        bottomRecyclerEdit.setLayoutManager(new LinearLayoutManager(this, 0, false));
        bottomRecyclerEdit.setHasFixedSize(true);
        bottomRecyclerEdit.setAdapter(this.recyclr_Color1);
        bottomRecyclerEdit.addOnItemTouchListener(new RecyclerItemClickListener(this, new C08988()));
    }

    void showBottomRecycler2() {
        this.recyclr_Color2 = new RecyclerColorAdapter(this, this.pallete);
        RecyclerView bottomRecyclerEdit = (RecyclerView) findViewById(R.id.shadow_recylr);
        bottomRecyclerEdit.setLayoutManager(new LinearLayoutManager(this, 0, false));
        bottomRecyclerEdit.setHasFixedSize(true);
        bottomRecyclerEdit.setAdapter(this.recyclr_Color2);
        bottomRecyclerEdit.addOnItemTouchListener(new RecyclerItemClickListener(this, new C08999()));
    }

    void showBottomRecycler3() {
        this.recyclr_Color3 = new RecyclerColorAdapter(this, this.pallete);
        RecyclerView bottomRecyclerEdit = (RecyclerView) findViewById(R.id.backgdColor_recylr);
        bottomRecyclerEdit.setLayoutManager(new LinearLayoutManager(this, 0, false));
        bottomRecyclerEdit.setHasFixedSize(true);
        bottomRecyclerEdit.setAdapter(this.recyclr_Color3);
        bottomRecyclerEdit.addOnItemTouchListener(new RecyclerItemClickListener(this, new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                TextActivity.this.bgDrawable = "0";
                TextActivity.this.bgColor = Color.parseColor(TextActivity.this.pallete[position]);
                TextActivity.this.lay_back_img.setImageBitmap(null);
                TextActivity.this.lay_back_img.setBackgroundColor(TextActivity.this.bgColor);
                TextActivity.this.lay_back_img.setVisibility(View.VISIBLE);
                TextActivity.this.recyclr_Image.setSelected(500);
            }
        }));
    }

    private void initViewPager() {
        this.adapter = new AssetsGrid(this, getResources().getStringArray(R.array.fonts_array));
        this.font_gridview = (GridView) findViewById(R.id.font_gridview);
        this.font_gridview.setAdapter(this.adapter);
        this.font_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TextActivity.this.fontName = (String) TextActivity.this.adapter.getItem(position);
                Editable text = TextActivity.this.autoFitEditText.getText();
                TextActivity.this.autoFitEditText.setTypeface(Typeface.createFromAsset(TextActivity.this.getAssets(), TextActivity.this.fontName));
                TextActivity.this.autoFitEditText.setText(text);
                TextActivity.this.autoFitEditText.invalidate();
                TextActivity.this.adapter.setSelected(position);
            }
        });
    }

    private void initUIData() {
        this.bundle = getIntent().getExtras();
        if (this.bundle != null) {
            int i;
            this.text = this.bundle.getString("text", "");
            this.fontName = this.bundle.getString("fontName", "");
            this.tColor = this.bundle.getInt("tColor", Color.parseColor("#4149b6"));
            this.tAlpha = this.bundle.getInt("tAlpha", 100);
            this.shadowColor = this.bundle.getInt("shadowColor", Color.parseColor("#7641b6"));
            this.shadowProg = this.bundle.getInt("shadowProg", 5);
            this.bgDrawable = this.bundle.getString("bgDrawable", "0");
            this.bgColor = this.bundle.getInt("bgColor", 0);
            this.bgAlpha = this.bundle.getInt("bgAlpha", 255);
            this.autoFitEditText.setText(this.text);
            this.seekBar.setProgress(this.tAlpha);
            this.seekBar2.setProgress(this.shadowProg);
            updateColor(this.tColor, 1);
            updateColor(this.shadowColor, 2);
            String hexColorT = String.format("#%06X", new Object[]{Integer.valueOf(this.tColor & ViewCompat.MEASURED_SIZE_MASK)});
            for (i = 0; i < this.pallete.length; i++) {
                if (this.pallete[i].equals(hexColorT.toLowerCase())) {
                    this.recyclr_Color1.setSelected(i);
                }
            }
            if (!this.bgDrawable.equals("0")) {
                this.lay_back_img.setImageBitmap(getTiledBitmap(this, getResources().getIdentifier(this.bgDrawable, "drawable", getPackageName()), this.autoFitEditText.getWidth(), this.autoFitEditText.getHeight()));
                this.lay_back_img.setVisibility(View.VISIBLE);
                this.lay_back_img.postInvalidate();
                this.lay_back_img.requestLayout();
                this.recyclr_Image.setSelected(Integer.parseInt(this.bgDrawable.replace("btxt", "")));
            }
            if (this.bgColor != 0) {
                this.lay_back_img.setBackgroundColor(this.bgColor);
                this.lay_back_img.setVisibility(View.VISIBLE);
                String hexBgColor = String.format("#%06X", new Object[]{Integer.valueOf(this.bgColor & ViewCompat.MEASURED_SIZE_MASK)});
                for (i = 0; i < this.pallete.length; i++) {
                    if (this.pallete[i].equals(hexBgColor.toLowerCase())) {
                        this.recyclr_Color3.setSelected(i);
                    }
                }
            }
            this.seekBar3.setProgress(this.bgAlpha);
            try {
                this.autoFitEditText.setTypeface(Typeface.createFromAsset(getAssets(), this.fontName));
                String[] font_Arr = getResources().getStringArray(R.array.fonts_array);
                for (i = 0; i < font_Arr.length; i++) {
                    if (font_Arr[i].equals(this.fontName)) {
                        this.adapter.setSelected(i);
                    }
                }
            } catch (Exception e) {
            }
            String hexColorShado = String.format("#%06X", new Object[]{Integer.valueOf(this.shadowColor & ViewCompat.MEASURED_SIZE_MASK)});
            for (i = 0; i < this.pallete.length; i++) {
                if (this.pallete[i].equals(hexColorShado.toLowerCase())) {
                    this.recyclr_Color2.setSelected(i);
                }
            }
        }
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_back) {
            finish();
        } else if (i == R.id.btn_ok) {
            if (this.autoFitEditText.getText().toString().trim().length() > 0) {
                Intent intent = new Intent();
                intent.putExtras(getInfoBundle());
                setResult(-1, intent);
                finish();
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.textlib_warn_text), Toast.LENGTH_SHORT).show();
        } else if (i == R.id.laykeyboard || i == R.id.ic_kb) {
            this.isKbOpened = true;
            this.firsttime = true;
            setSelected(v.getId());
            this.imm.showSoftInput(this.autoFitEditText, 0);
        } else if (i == R.id.lay_txtfont) {
            setSelected(v.getId());
            this.clickedView = v;
            this.font_grid_rel.setVisibility(View.VISIBLE);
            this.color_rel.setVisibility(View.GONE);
            this.shadow_rel.setVisibility(View.GONE);
            this.bg_rel.setVisibility(View.GONE);
            this.lay_below.setVisibility(View.VISIBLE);
            this.imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        } else if (i == R.id.lay_txtcolor) {
            setSelected(v.getId());
            this.clickedView = v;
            this.font_grid_rel.setVisibility(View.GONE);
            this.color_rel.setVisibility(View.VISIBLE);
            this.shadow_rel.setVisibility(View.GONE);
            this.bg_rel.setVisibility(View.GONE);
            this.lay_below.setVisibility(View.VISIBLE);
            this.imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        } else if (i == R.id.lay_txtshadow) {
            setSelected(v.getId());
            this.clickedView = v;
            this.font_grid_rel.setVisibility(View.GONE);
            this.color_rel.setVisibility(View.GONE);
            this.shadow_rel.setVisibility(View.VISIBLE);
            this.bg_rel.setVisibility(View.GONE);
            this.lay_below.setVisibility(View.VISIBLE);
            this.imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        } else if (i == R.id.lay_txtbg) {
            setSelected(v.getId());
            this.clickedView = v;
            this.font_grid_rel.setVisibility(View.GONE);
            this.color_rel.setVisibility(View.GONE);
            this.shadow_rel.setVisibility(View.GONE);
            this.bg_rel.setVisibility(View.VISIBLE);
            this.lay_below.setVisibility(View.VISIBLE);
            this.imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        } else if (i == R.id.txt_bg_none) {
            this.lay_back_img.setVisibility(View.GONE);
            this.bgDrawable = "0";
            this.bgColor = 0;
            this.recyclr_Image.setSelected(500);
            this.recyclr_Color3.setSelected(500);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.processValue = progress;
        this.value = progress;
        int i = seekBar.getId();
        if (i == R.id.seekBar1) {
            this.autoFitEditText.setAlpha(((float) seekBar.getProgress()) / ((float) seekBar.getMax()));
        } else if (i == R.id.seekBar2) {
            if (this.hex1.equals("")) {
                this.autoFitEditText.setShadowLayer((float) progress, 0.0f, 0.0f, Color.parseColor("#fdab52"));
            } else {
                this.autoFitEditText.setShadowLayer((float) progress, 0.0f, 0.0f, Color.parseColor("#" + this.hex1));
            }
        } else if (i == R.id.seekBar3) {
            this.lay_back_img.setAlpha(((float) progress) / 255.0f);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private Bundle getInfoBundle() {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        this.text = this.autoFitEditText.getText().toString().trim().replace("\n", " ");
        this.bundle.putString("text", this.text);
        this.bundle.putString("fontName", this.fontName);
        this.bundle.putInt("tColor", this.tColor);
        this.bundle.putInt("tAlpha", this.seekBar.getProgress());
        this.bundle.putInt("shadowColor", this.shadowColor);
        this.bundle.putInt("shadowProg", this.seekBar2.getProgress());
        this.bundle.putString("bgDrawable", this.bgDrawable);
        this.bundle.putInt("bgColor", this.bgColor);
        this.bundle.putInt("bgAlpha", this.seekBar3.getProgress());
        return this.bundle;
    }

    private void updateColor(int color, int i) {
        if (i == 1) {
            this.tColor = color;
            this.hex = Integer.toHexString(color);
            this.autoFitEditText.setTextColor(Color.parseColor("#" + this.hex));
        } else if (i == 2) {
            this.shadowColor = color;
            int progress = this.seekBar2.getProgress();
            this.hex1 = Integer.toHexString(color);
            this.autoFitEditText.setShadowLayer((float) progress, 0.0f, 0.0f, Color.parseColor("#" + this.hex1));
        }
    }

    public void setSelected(int id) {
        if (id == R.id.laykeyboard) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb1);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (id == R.id.lay_txtfont) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text1);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (id == R.id.lay_txtcolor) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor1);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (id == R.id.lay_txtshadow) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow1);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (id == R.id.lay_txtbg) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg1);
        }
    }

    private Bitmap getTiledBitmap(Context ctx, int resId, int width, int height) {
        Rect rect = new Rect(0, 0, width, height);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(ctx.getResources(), resId, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        new Canvas(b).drawRect(rect, paint);
        return b;
    }
}
