package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.irisstudio.demo.view.ComponentInfo;
import com.irisstudio.demo.view.ResizableCardView;
import com.irisstudio.demo.view.ResizableStickerView;
import com.irisstudio.demo.view.TabHost;
import com.irisstudio.demo.view.TabHost.OnTabClickListener;

import org.wysaid.myUtils.ImageUtil;

import java.io.File;
import java.io.FileOutputStream;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.ShowImage;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.BackgroundsAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.EffectsAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.SingleIconAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.StickerResAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.TextFontAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.Dimensions;
import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.textmodule.AutofitTextRel;
import mobo.andro.apps.ohmagiccamera.textmodule.AutofitTextRel.TouchEventListener;
import mobo.andro.apps.ohmagiccamera.textmodule.TextActivity;
import mobo.andro.apps.ohmagiccamera.textmodule.TextInfo;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerColorAdapter;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerItemClickListener;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerItemClickListener.OnItemClickListener;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class FreeCollageActivity extends Activity implements OnSeekBarChangeListener, TouchEventListener, ResizableStickerView.TouchEventListener, ResizableCardView.TouchEventListener, OnClickListener {
    public static Bitmap resultBitmap;
    private int MORE_IMAGES = 296;
    private int TEXT_ACTIVITY = 234;
    private AutofitTextRel autofitTextRel = null;
    private int[] bgIconArr = new int[]{R.drawable.t_1, R.drawable.t_2, R.drawable.t_3, R.drawable.t_4, R.drawable.t_5, R.drawable.t_6, R.drawable.t_7, R.drawable.t_8, R.drawable.t_9, R.drawable.t_10, R.drawable.t_11, R.drawable.t_12, R.drawable.t_13, R.drawable.t_14, R.drawable.t_15, R.drawable.t_16, R.drawable.t_17, R.drawable.t_18, R.drawable.t_19, R.drawable.t_20, R.drawable.t_21, R.drawable.t_22, R.drawable.t_23, R.drawable.t_24, R.drawable.t_25, R.drawable.t_26, R.drawable.t_27, R.drawable.t_28, R.drawable.t_29, R.drawable.t_30, R.drawable.t_31, R.drawable.t_32};
    private int[] bgNameArr = new int[]{R.string.bg1, R.string.bg2, R.string.bg3, R.string.bg4, R.string.bg5, R.string.bg6, R.string.bg7, R.string.bg8, R.string.bg9, R.string.bg10, R.string.bg11, R.string.bg12, R.string.bg13, R.string.bg14, R.string.bg15, R.string.bg16, R.string.bg17, R.string.bg18, R.string.bg19, R.string.bg20, R.string.bg21, R.string.bg22, R.string.bg23, R.string.bg24, R.string.bg25, R.string.bg26, R.string.bg27, R.string.bg28, R.string.bg29, R.string.bg30, R.string.bg31, R.string.bg32};
    private BackgroundsAdapter bg_adapter;
    private ImageView bg_image;
    private RecyclerView bg_recycler;
    private RelativeLayout bg_res_lay;
    private SeekBar bg_seekbar;
    private int[] birthdayArr = new int[]{R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12, R.drawable.b13, R.drawable.b14, R.drawable.b15, R.drawable.b16, R.drawable.b17, R.drawable.b18, R.drawable.b19, R.drawable.b20, R.drawable.b21, R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25, R.drawable.b26};
    private int[] cartoonArr = new int[]{R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9, R.drawable.c10, R.drawable.c11, R.drawable.c12, R.drawable.c13, R.drawable.c14, R.drawable.c15, R.drawable.c16, R.drawable.c17, R.drawable.c18, R.drawable.c19, R.drawable.c20, R.drawable.c21, R.drawable.c22, R.drawable.c23, R.drawable.c24, R.drawable.c25, R.drawable.c26, R.drawable.c27, R.drawable.c28, R.drawable.c29, R.drawable.c30, R.drawable.c31, R.drawable.c32, R.drawable.c33};
    private RelativeLayout center_rel;
    private RelativeLayout color_container;
    private int curColor = -7829368;
    private String curConfig = "";
    private float curConfigProg = 1.0f;
    private int curTileId = R.drawable.t_1;
    private boolean editMode = false;
    private int[] effectIconArr = new int[]{R.drawable.filter1, R.drawable.filter37, R.drawable.filter2, R.drawable.filter3, R.drawable.filter3, R.drawable.filter4, R.drawable.filter5, R.drawable.filter6, R.drawable.filter7, R.drawable.filter8, R.drawable.filter9, R.drawable.filter10, R.drawable.filter11, R.drawable.filter12, R.drawable.filter13, R.drawable.filter14, R.drawable.filter15, R.drawable.filter16, R.drawable.filter17, R.drawable.filter18, R.drawable.filter19, R.drawable.filter20, R.drawable.filter21, R.drawable.filter22, R.drawable.filter23, R.drawable.filter24, R.drawable.filter25, R.drawable.filter26, R.drawable.filter27, R.drawable.filter28, R.drawable.filter29, R.drawable.filter30, R.drawable.filter31, R.drawable.filter32, R.drawable.filter33, R.drawable.filter34, R.drawable.filter35, R.drawable.filter36, R.drawable.filter38, R.drawable.filter39, R.drawable.filter40, R.drawable.filter41, R.drawable.filter42, R.drawable.filter43, R.drawable.filter44, R.drawable.filter45, R.drawable.filter46, R.drawable.filter47, R.drawable.filter48, R.drawable.filter49, R.drawable.filter50, R.drawable.filter51, R.drawable.filter52, R.drawable.filter53, R.drawable.filter54, R.drawable.filter55};
    private RelativeLayout effect_res_lay;
    private SeekBar effect_seekBar;
    private EffectsAdapter effects_adapter;
    private RecyclerView effects_recycler;
    private int[] festivals = new int[]{R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5, R.drawable.f6, R.drawable.f7, R.drawable.f8, R.drawable.f9, R.drawable.f10, R.drawable.f11, R.drawable.f12};
    private String filename;
    private View focusedView = null;
    private String[] fontNameArr = new String[]{"font1.ttf", "font2.ttf", "font3.ttf", "font4.ttf", "font5.ttf", "font6.ttf", "font7.ttf", "font8.ttf"};
    private RecyclerView font_recycler;
    private int[] footerIconArr = new int[]{R.drawable.ic_more_img, R.drawable.ic_filters, R.drawable.ic_patterns, R.drawable.ic_text, R.drawable.ic_sticker};
    private int[] footerNameArr = new int[]{R.string.images, R.string.filters, R.string.patterns, R.string.text, R.string.stickers};
    private SingleIconAdapter footer_adapter;
    private RecyclerView footer_recycler;
    private int[] heartsArr = new int[]{R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h8, R.drawable.h9, R.drawable.h10, R.drawable.h11, R.drawable.h12, R.drawable.h13, R.drawable.h14, R.drawable.h15, R.drawable.h16, R.drawable.h17, R.drawable.h18, R.drawable.h19, R.drawable.h20, R.drawable.h21, R.drawable.h22, R.drawable.h23, R.drawable.h24, R.drawable.h25, R.drawable.h26, R.drawable.h27, R.drawable.h28, R.drawable.h29, R.drawable.h30, R.drawable.h31, R.drawable.h32, R.drawable.h33, R.drawable.h34, R.drawable.h35, R.drawable.h36, R.drawable.h37, R.drawable.h38, R.drawable.h39, R.drawable.h40, R.drawable.h41, R.drawable.h42, R.drawable.h43, R.drawable.h44, R.drawable.h45, R.drawable.h46, R.drawable.h47, R.drawable.h48, R.drawable.h49, R.drawable.h50};
    private boolean isUnlocked = true;
    private Bitmap logo;
    private LinearLayout logo_ll;
    private int[] loveArr = new int[]{R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l5, R.drawable.l6, R.drawable.l7, R.drawable.l8, R.drawable.l9, R.drawable.l10, R.drawable.l11, R.drawable.l12, R.drawable.l13, R.drawable.l14, R.drawable.l15, R.drawable.l16, R.drawable.l17, R.drawable.l18, R.drawable.l19, R.drawable.l20, R.drawable.l21, R.drawable.l22, R.drawable.l23, R.drawable.l24, R.drawable.l25, R.drawable.l26, R.drawable.l27, R.drawable.l28, R.drawable.l29, R.drawable.l30, R.drawable.l31, R.drawable.l32, R.drawable.l33, R.drawable.l34, R.drawable.l35, R.drawable.l36, R.drawable.l37};
    private int mainRelHeight;
    private int mainRelWidth;
    private int maxSelectionSize = 10;
    String[] pallete = new String[]{"#ffffff", "#000000", "#383838", "#717070", "#bcbbbb", "#ffa800", "#ffcc00", "#ffe824", "#fcee74", "#b50000", "#ed0000", "#fd3e3e", "#ffabab", "#125301", "#2e8e15", "#59db36", "#b8ffa5", "#0244ec", "#0281ec", "#00b4ff", "#00deff"};
    private int[] partyArr = new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14, R.drawable.p15, R.drawable.p16, R.drawable.p17, R.drawable.p18, R.drawable.p19, R.drawable.p20};
    RecyclerColorAdapter recyclr_Color;
    private RelativeLayout res_container;
    private StickerResAdapter sticker_Adapter;
    private RelativeLayout sticker_container;
    private RecyclerView sticker_recyclerview;
    private TabHost tabhost;
    private int tempPos = 0;
    private TextFontAdapter textfont_adapter;
    private int[] textsArr = new int[]{R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8, R.drawable.t9, R.drawable.t10, R.drawable.t11, R.drawable.t12, R.drawable.t13, R.drawable.t14, R.drawable.t15, R.drawable.t16, R.drawable.t17, R.drawable.t18, R.drawable.t19, R.drawable.t20, R.drawable.t21, R.drawable.t22, R.drawable.t23, R.drawable.t24, R.drawable.t25, R.drawable.t26, R.drawable.t27, R.drawable.t28, R.drawable.t29, R.drawable.t30, R.drawable.t31, R.drawable.t32, R.drawable.t33, R.drawable.t34, R.drawable.t35, R.drawable.t36, R.drawable.t37, R.drawable.t38, R.drawable.t39, R.drawable.t40, R.drawable.t41, R.drawable.t42, R.drawable.t43, R.drawable.t44, R.drawable.t45, R.drawable.t46, R.drawable.t47, R.drawable.t48, R.drawable.t49, R.drawable.t50};
    private RelativeLayout txt_sticker_rel;
    private Animation zoomOutScale;

    class C03432 implements Runnable {
        C03432() {
        }

        public void run() {
            for (int i = 0; i < ImageGalleryActivity.selectedBitmaps.size(); i++) {
                FreeCollageActivity.this.addCardView((Bitmap) ImageGalleryActivity.selectedBitmaps.get(i));
            }
            int bgIndex = FreeCollageActivity.this.isUnlocked ? ((int) (Math.random() * 10.0d)) + 0 : ((int) (Math.random() * 10.0d)) + 0;
            FreeCollageActivity.this.curTileId = FreeCollageActivity.this.bgIconArr[bgIndex];
            FreeCollageActivity.this.bg_image.setImageBitmap(ImageUtils.getTiledBitmap(FreeCollageActivity.this, FreeCollageActivity.this.curTileId, Dimensions.S_1P, Dimensions.S_1P, FreeCollageActivity.this.bg_seekbar.getProgress()));
            FreeCollageActivity.this.bg_adapter.setSelected(bgIndex);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.FreeCollageActivity$3 */
    class C03443 implements OnTouchListener {
        C03443() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                FreeCollageActivity.this.removeImageViewControll();
            }
            return false;
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.FreeCollageActivity$4 */
    class C03454 implements OnTouchListener {
        C03454() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                FreeCollageActivity.this.removeImageViewControll();
            }
            return false;
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.FreeCollageActivity$5 */
    class C03465 implements OnItemClickListener {
        C03465() {
        }

        public void onItemClick(View view, int position) {
            FreeCollageActivity.this.autofitTextRel.setTextColor(Color.parseColor(FreeCollageActivity.this.pallete[position]));
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.FreeCollageActivity$6 */
    class C03476 implements OnTabClickListener {
        C03476() {
        }

        public void onTabClick(int position, String tabName) {
            FreeCollageActivity.this.tabhost.setTabSelected(position);
            if (position == 0) {
                FreeCollageActivity.this.setStickerAdapter(FreeCollageActivity.this.birthdayArr);
            }
            if (position == 1) {
                FreeCollageActivity.this.setStickerAdapter(FreeCollageActivity.this.cartoonArr);
            }
            if (position == 2) {
                FreeCollageActivity.this.setStickerAdapter(FreeCollageActivity.this.festivals);
            }
            if (position == 3) {
                FreeCollageActivity.this.setStickerAdapter(FreeCollageActivity.this.heartsArr);
            }
            if (position == 4) {
                FreeCollageActivity.this.setStickerAdapter(FreeCollageActivity.this.loveArr);
            }
            if (position == 5) {
                FreeCollageActivity.this.setStickerAdapter(FreeCollageActivity.this.partyArr);
            }
            if (position == 6) {
                FreeCollageActivity.this.setStickerAdapter(FreeCollageActivity.this.textsArr);
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.FreeCollageActivity$7 */
    class C03487 implements StickerResAdapter.OnItemClickListener {
        C03487() {
        }

        public void onImageClick(int pos, int resId) {
            FreeCollageActivity.this.addSticker(resId);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.FreeCollageActivity$8 */
    class C03498 implements SingleIconAdapter.OnItemClickListener {
        C03498() {
        }

        public void onImageClick(int pos, int resId) {
            if (pos == 0) {
                int count = FreeCollageActivity.this.getCardViewCount();
                if (count < FreeCollageActivity.this.maxSelectionSize) {
                    Intent collageIntent = new Intent(FreeCollageActivity.this, ImageGalleryActivity.class);
                    collageIntent.putExtra("isOpenedForFreeCollage", true);
                    collageIntent.putExtra("needToClear", true);
                    collageIntent.putExtra("forAddMoreIMages", true);
                    collageIntent.putExtra("maxSelectionSize", FreeCollageActivity.this.maxSelectionSize - count);
                    FreeCollageActivity.this.startActivityForResult(collageIntent, FreeCollageActivity.this.MORE_IMAGES);
                    return;
                }
                Toast.makeText(FreeCollageActivity.this, FreeCollageActivity.this.getResources().getString(R.string.selection_warning), 0).show();
                return;
            }
            FreeCollageActivity.this.footer_adapter.setSelected(pos);
            FreeCollageActivity.this.res_container.setVisibility(View.VISIBLE);
            FreeCollageActivity.this.effect_res_lay.setVisibility(View.GONE);
            FreeCollageActivity.this.font_recycler.setVisibility(View.GONE);
            FreeCollageActivity.this.bg_res_lay.setVisibility(View.GONE);
            FreeCollageActivity.this.sticker_container.setVisibility(View.GONE);
            if (pos == 1) {
                FreeCollageActivity.this.effect_res_lay.setVisibility(View.VISIBLE);
            } else if (pos == 2) {
                FreeCollageActivity.this.bg_res_lay.setVisibility(View.VISIBLE);
            } else if (pos == 3) {
                FreeCollageActivity.this.font_recycler.setVisibility(View.VISIBLE);
            } else if (pos == 4) {
                FreeCollageActivity.this.sticker_container.setVisibility(View.VISIBLE);
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.FreeCollageActivity$9 */
    class C03509 implements EffectsAdapter.OnItemClickListener {
        C03509() {
        }

        public void onImageClick(int pos, int resId) {
            FreeCollageActivity.this.effects_adapter.setSelected(pos);
            FreeCollageActivity.this.changeFilterConfig(Constants.EFFECT_CONFIGS[pos]);
            int selected_position_progress = FreeCollageActivity.this.effects_adapter.getSelected_position_progress(pos);
            FreeCollageActivity.this.effect_seekBar.setOnSeekBarChangeListener(null);
            FreeCollageActivity.this.effect_seekBar.setProgress(selected_position_progress);
            if (pos != 0) {
                FreeCollageActivity.this.effect_seekBar.setOnSeekBarChangeListener(FreeCollageActivity.this);
            }
        }

        public void onImageReClick(int pos, int resId) {
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_free_collage);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(FreeCollageActivity.this,adContainer);

        getWindow().setFlags(1024, 1024);
        this.zoomOutScale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_zoom_out);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.mainRelWidth = displaymetrics.widthPixels;
        this.mainRelHeight = displaymetrics.heightPixels;
        this.fontNameArr = getResources().getStringArray(R.array.fonts_array);
        initUI();
        initFooterLayout();
        initEffectRecyclerLayout();
        initTextFontRecyclerLayout();
        initBackgroundsRecyclerLayout();
        initStickerRecyclerView();
        initStickerUI();
        this.center_rel.setLayoutParams(new LayoutParams(this.mainRelWidth, this.mainRelWidth));
        this.center_rel.post(new C03432());
    }

    private void initUI() {
        this.logo_ll = (LinearLayout) findViewById(R.id.logo_ll);
        this.res_container = (RelativeLayout) findViewById(R.id.res_container);
        this.txt_sticker_rel = (RelativeLayout) findViewById(R.id.txt_sticker_rel);
        this.center_rel = (RelativeLayout) findViewById(R.id.center_rel);
        this.effect_res_lay = (RelativeLayout) findViewById(R.id.effect_res_lay);
        this.bg_res_lay = (RelativeLayout) findViewById(R.id.bg_res_lay);
        this.color_container = (RelativeLayout) findViewById(R.id.color_container);
        this.sticker_container = (RelativeLayout) findViewById(R.id.sticker_container);
        this.bg_image = (ImageView) findViewById(R.id.bg_image);
        this.bg_seekbar = (SeekBar) findViewById(R.id.bg_seekBar);
        this.effect_seekBar = (SeekBar) findViewById(R.id.effect_seekBar);
        this.effect_seekBar.setOnSeekBarChangeListener(this);
        this.bg_seekbar.setOnSeekBarChangeListener(this);
        this.txt_sticker_rel.setOnTouchListener(new C03443());
        findViewById(R.id.main_rel).setOnTouchListener(new C03454());
        this.recyclr_Color = new RecyclerColorAdapter(this, this.pallete);
        RecyclerView bottomRecyclerEdit = (RecyclerView) findViewById(R.id.color_recylr);
        bottomRecyclerEdit.setLayoutManager(new LinearLayoutManager(this, 0, false));
        bottomRecyclerEdit.setHasFixedSize(true);
        bottomRecyclerEdit.setAdapter(this.recyclr_Color);
        bottomRecyclerEdit.addOnItemTouchListener(new RecyclerItemClickListener(this, new C03465()));
    }

    private void initStickerUI() {
        this.sticker_recyclerview = (RecyclerView) findViewById(R.id.sticker_recyclerview);
        this.sticker_recyclerview.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.sticker_recyclerview.setLayoutManager(llm);
        this.tabhost = (TabHost) findViewById(R.id.tabhost);
        this.tabhost.addTab(getResources().getString(R.string.birthday));
        this.tabhost.addTab(getResources().getString(R.string.cartoons));
        this.tabhost.addTab(getResources().getString(R.string.festivals));
        this.tabhost.addTab(getResources().getString(R.string.hearts));
        this.tabhost.addTab(getResources().getString(R.string.love));
        this.tabhost.addTab(getResources().getString(R.string.party));
        this.tabhost.addTab(getResources().getString(R.string.texts));
        this.tabhost.setOnTabClickListener(new C03476());
        this.tabhost.setTabSelected(0);
        setStickerAdapter(this.birthdayArr);
    }

    private void setStickerAdapter(int[] arr) {
        this.sticker_Adapter = new StickerResAdapter(this, arr);
        this.sticker_recyclerview.setAdapter(this.sticker_Adapter);
        this.sticker_Adapter.setListner(new C03487());
    }

    private void initFooterLayout() {
        this.footer_recycler = (RecyclerView) findViewById(R.id.footer_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.footer_recycler.setLayoutManager(llm);
        this.footer_adapter = new SingleIconAdapter(this, this.footerIconArr, this.footerNameArr);
        this.footer_recycler.setAdapter(this.footer_adapter);
        this.footer_adapter.setOnItemClickListner(new C03498());
        this.footer_adapter.setSelected(1);
    }

    private void initEffectRecyclerLayout() {
        this.effects_recycler = (RecyclerView) findViewById(R.id.effects_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.effects_recycler.setLayoutManager(llm);
        this.effects_adapter = new EffectsAdapter(this, this.effectIconArr);
        this.effects_recycler.setAdapter(this.effects_adapter);
        this.effects_adapter.setOnItemClickListner(new C03509());
        this.effects_adapter.setSelected(0);
    }

    private void initTextFontRecyclerLayout() {
        this.font_recycler = (RecyclerView) findViewById(R.id.font_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.font_recycler.setLayoutManager(llm);
        this.textfont_adapter = new TextFontAdapter(this, this.fontNameArr);
        this.font_recycler.setAdapter(this.textfont_adapter);
        this.textfont_adapter.setOnItemClickListner(new TextFontAdapter.OnItemClickListener() {
            public void onImageClick(int pos, String fontName) {
                FreeCollageActivity.this.textfont_adapter.setSelected(pos);
                FreeCollageActivity.this.initOrAddNewText(fontName);
            }

            public void onImageReClick(int pos, String fontName) {
                FreeCollageActivity.this.color_container.setVisibility(View.VISIBLE);
                FreeCollageActivity.this.tempPos = FreeCollageActivity.this.recyclr_Color.getSelected();
            }
        });
    }

    private void initBackgroundsRecyclerLayout() {
        this.bg_recycler = (RecyclerView) findViewById(R.id.bg_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.bg_recycler.setLayoutManager(llm);
        this.bg_adapter = new BackgroundsAdapter(this, this.bgIconArr, this.bgNameArr);
        this.bg_recycler.setAdapter(this.bg_adapter);
        this.bg_adapter.setOnItemClickListner(new BackgroundsAdapter.OnItemClickListener() {
            public void onImageClick(int pos, int resId) {
                FreeCollageActivity.this.bg_image.setBackgroundColor(0);
                FreeCollageActivity.this.bg_adapter.setSelected(pos);
                FreeCollageActivity.this.curTileId = resId;
                FreeCollageActivity.this.bg_image.setImageBitmap(ImageUtils.getTiledBitmap(FreeCollageActivity.this, FreeCollageActivity.this.curTileId, Dimensions.S_1P, Dimensions.S_1P, FreeCollageActivity.this.bg_seekbar.getProgress()));
                FreeCollageActivity.this.bg_seekbar.setOnSeekBarChangeListener(FreeCollageActivity.this);
            }

            public void onImageReClick(int pos, int resId) {
            }
        });
        this.bg_adapter.setSelected(0);
    }

    private void showColorDialog() {
        new AmbilWarnaDialog(this, this.curColor, new OnAmbilWarnaListener() {
            public void onOk(AmbilWarnaDialog dialog, int color) {
                FreeCollageActivity.this.bg_seekbar.setOnSeekBarChangeListener(null);
                FreeCollageActivity.this.bg_image.setImageBitmap(null);
                FreeCollageActivity.this.curColor = color;
                FreeCollageActivity.this.bg_image.setBackgroundColor(color);
                FreeCollageActivity.this.bg_adapter.setSelected(500);
            }

            public void onCancel(AmbilWarnaDialog dialog) {
            }
        }).show();
    }

    private void initStickerRecyclerView() {
        this.sticker_recyclerview = (RecyclerView) findViewById(R.id.sticker_recyclerview);
        this.sticker_recyclerview.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.sticker_recyclerview.setLayoutManager(llm);
    }

    public void initOrAddNewText(String fontName) {
        if (this.autofitTextRel == null) {
            removeImageViewControll();
            TextInfo textInfo = new TextInfo();
            textInfo.setPOS_X((float) ((this.txt_sticker_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 100)));
            textInfo.setPOS_Y((float) ((this.txt_sticker_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 100)));
            textInfo.setWIDTH(ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            textInfo.setHEIGHT(ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            textInfo.setTEXT(getResources().getString(R.string.double_tap));
            textInfo.setFONT_NAME(fontName);
            textInfo.setTEXT_COLOR(-1);
            textInfo.setTEXT_ALPHA(100);
            textInfo.setSHADOW_COLOR(ViewCompat.MEASURED_STATE_MASK);
            textInfo.setSHADOW_PROG(5);
            textInfo.setBG_COLOR(0);
            textInfo.setBG_DRAWABLE("0");
            textInfo.setBG_ALPHA(255);
            textInfo.setROTATION(0.0f);
            this.autofitTextRel = new AutofitTextRel(this);
            this.txt_sticker_rel.addView(this.autofitTextRel);
            this.autofitTextRel.setTextInfo(textInfo);
            this.autofitTextRel.setOnTouchCallbackListener(this);
            this.recyclr_Color.setSelected(0);
            return;
        }
        this.autofitTextRel.setTextFont(fontName);
    }

    private void addSticker(int resId) {
        removeImageViewControll();
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X((float) ((this.txt_sticker_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 70)));
        ci.setPOS_Y((float) ((this.txt_sticker_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 70)));
        ci.setWIDTH(ImageUtils.dpToPx(this, 140));
        ci.setHEIGHT(ImageUtils.dpToPx(this, 140));
        ci.setROTATION(0.0f);
        ci.setRES_ID(resId);
        ci.setTYPE("STICKER");
        ResizableStickerView riv = new ResizableStickerView(this);
        riv.setComponentInfo(ci);
        this.txt_sticker_rel.addView(riv);
        riv.setOnTouchCallbackListener(this);
    }

    private void addCardView(Bitmap bitmap) {
        removeImageViewControll();
        int reqSize = ImageUtils.dpToPx(this, 180);
        int[] resizeDim = ImageUtils.getResizeDim((float) bitmap.getWidth(), (float) bitmap.getHeight(), reqSize, reqSize);
        int x = (-(resizeDim[0] / 2)) + ((int) (Math.random() * ((double) (this.txt_sticker_rel.getWidth() - (resizeDim[0] / 2)))));
        int y = (-(resizeDim[1] / 2)) + ((int) (Math.random() * ((double) (this.txt_sticker_rel.getHeight() - (resizeDim[1] / 2)))));
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X((float) x);
        ci.setPOS_Y((float) y);
        ci.setWIDTH(resizeDim[0]);
        ci.setHEIGHT(resizeDim[1]);
        ci.setROTATION(0.0f);
        ci.setBITMAP(bitmap);
        ci.setCURCONFIG(this.curConfig);
        ci.setCURCONFIGPROG(this.curConfigProg);
        ci.setTYPE("STICKER");
        ResizableCardView riv = new ResizableCardView(this);
        riv.setComponentInfo(ci);
        this.txt_sticker_rel.addView(riv);
        riv.setOnTouchCallbackListener(this);
    }

    public int getCardViewCount() {
        int childCount = this.txt_sticker_rel.getChildCount();
        int count = 0;
        for (int i = 0; i < childCount; i++) {
            if (this.txt_sticker_rel.getChildAt(i) instanceof ResizableCardView) {
                count++;
            }
        }
        return count;
    }

    public void removeImageViewControll() {
        int childCount = this.txt_sticker_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_sticker_rel.getChildAt(i);
            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);
            }
            if (view instanceof ResizableStickerView) {
                ((ResizableStickerView) view).setBorderVisibility(false);
            }
            if (view instanceof ResizableCardView) {
                ((ResizableCardView) view).setBorderVisibility(false);
            }
        }
    }

    public void changeFilterConfig(String config) {
        int childCount = this.txt_sticker_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_sticker_rel.getChildAt(i);
            if (view instanceof ResizableCardView) {
                ((ResizableCardView) view).setmCurConfig(config);
            }
        }
    }

    public void changeFilterProgress(float prog) {
        int childCount = this.txt_sticker_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_sticker_rel.getChildAt(i);
            if (view instanceof ResizableCardView) {
                ((ResizableCardView) view).setmCurConfigProg(prog);
            }
        }
    }

    public void onBackPressed()
    {
        int style;
        if (VERSION.SDK_INT >= 14) {
            style = 16974126;
        } else {
            style = 16973835;
        }
        new Builder(this, style).setTitle(getResources().getString(R.string.alert)).setIcon(R.mipmap.ic_launcher).setMessage(getResources().getString(R.string.exit_cut_page)).setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FreeCollageActivity.this.finish();
            }
        }).setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            int i;
            if (requestCode == this.TEXT_ACTIVITY) {
                Bundle bundle = data.getExtras();
                TextInfo textInfo = new TextInfo();
                textInfo.setPOS_X((float) bundle.getInt("X", 0));
                textInfo.setPOS_Y((float) bundle.getInt("Y", 0));
                textInfo.setWIDTH(bundle.getInt("wi", ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION)));
                textInfo.setHEIGHT(bundle.getInt("he", ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION)));
                textInfo.setTEXT(bundle.getString("text", ""));
                textInfo.setFONT_NAME(bundle.getString("fontName", ""));
                textInfo.setTEXT_COLOR(bundle.getInt("tColor", Color.parseColor("#4149b6")));
                textInfo.setTEXT_ALPHA(bundle.getInt("tAlpha", 100));
                textInfo.setSHADOW_COLOR(bundle.getInt("shadowColor", Color.parseColor("#7641b6")));
                textInfo.setSHADOW_PROG(bundle.getInt("shadowProg", 5));
                textInfo.setBG_COLOR(bundle.getInt("bgColor", 0));
                textInfo.setBG_DRAWABLE(bundle.getString("bgDrawable", "0"));
                textInfo.setBG_ALPHA(bundle.getInt("bgAlpha", 255));
                textInfo.setROTATION(bundle.getFloat("rotation", 0.0f));
                String hexColorT = String.format("#%06X", new Object[]{Integer.valueOf(ViewCompat.MEASURED_SIZE_MASK & bundle.getInt("tColor", Color.parseColor("#4149b6")))});
                for (i = 0; i < this.pallete.length; i++) {
                    if (this.pallete[i].equals(hexColorT.toLowerCase())) {
                        this.recyclr_Color.setSelected(i);
                    }
                }
                if (this.editMode) {
                    ((AutofitTextRel) this.txt_sticker_rel.getChildAt(this.txt_sticker_rel.getChildCount() - 1)).setTextInfo(textInfo);
                    this.editMode = false;
                } else {
                    removeImageViewControll();
                    AutofitTextRel rl = new AutofitTextRel(this);
                    this.txt_sticker_rel.addView(rl);
                    rl.setTextInfo(textInfo);
                    rl.setOnTouchCallbackListener(this);
                }
            }
            if (requestCode == this.MORE_IMAGES) {
                for (i = 0; i < ImageGalleryActivity.selectedBitmaps.size(); i++) {
                    addCardView((Bitmap) ImageGalleryActivity.selectedBitmaps.get(i));
                }
            }
        } else if (requestCode == this.TEXT_ACTIVITY) {
            this.editMode = false;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_cp:
                showColorDialog();
                return;
            case R.id.btn_cancel_color:
                this.autofitTextRel.setTextColor(Color.parseColor(this.pallete[this.tempPos]));
                this.recyclr_Color.setSelected(this.tempPos);
                this.color_container.setVisibility(View.GONE);
                return;
            case R.id.btn_done_color:
                this.color_container.setVisibility(View.GONE);
                return;
            case R.id.btn_save:
                removeImageViewControll();
                resultBitmap = viewToBitmap(this.center_rel);
                this.logo_ll.setVisibility(View.VISIBLE);
                this.logo_ll.setDrawingCacheEnabled(true);
                this.logo = Bitmap.createBitmap(this.logo_ll.getDrawingCache());
                this.logo_ll.setDrawingCacheEnabled(false);
                this.logo_ll.setVisibility(View.INVISIBLE);
                saveImage();
                return;
            default:
                return;
        }
    }

    private void saveImage() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", getString(R.string.save_image_), true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Bitmap tbitmap = FreeCollageActivity.resultBitmap;//ImageUtils.mergelogo(FreeCollageActivity.resultBitmap, FreeCollageActivity.this.logo);
                    FreeCollageActivity.this.logo.recycle();
//                    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/" + FreeCollageActivity.this.getResources().getString(R.string.folder_name));
                    File pictureFileDir = new File(ImageUtil.getPath());

                    if (pictureFileDir.exists() || pictureFileDir.mkdirs()) {
                        FreeCollageActivity.this.filename = pictureFileDir.getPath() + File.separator + ("Photo_" + System.currentTimeMillis() + ".png");
                        File pictureFile = new File(FreeCollageActivity.this.filename);
                        try {
                            if (!pictureFile.exists()) {
                                pictureFile.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(pictureFile);
                            tbitmap.compress(CompressFormat.PNG, 100, ostream);
                            ostream.flush();
                            ostream.close();
                            tbitmap.recycle();
                            FreeCollageActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(pictureFile)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(1000);
                        ringProgressDialog.dismiss();
                        return;
                    }
                    Log.d("", "Can't create directory to save image.");
                    Toast.makeText(FreeCollageActivity.this.getApplicationContext(), FreeCollageActivity.this.getResources().getString(R.string.create_dir_err), 1).show();
                } catch (Exception e2) {
                }
            }
        }).start();
        ringProgressDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {

                ShowImage.selected_image_path=FreeCollageActivity.this.filename;
                Intent intent=new Intent(FreeCollageActivity.this,ShowImage.class);
                startActivity(intent);

                /*Toast.makeText(FreeCollageActivity.this.getApplicationContext(), FreeCollageActivity.this.getString(R.string.saved).toString() + " " + FreeCollageActivity.this.filename, 0).show();
                Intent intent = new Intent(FreeCollageActivity.this, ShareImageActivity.class);
                intent.putExtra("uri", FreeCollageActivity.this.filename);
                FreeCollageActivity.this.startActivity(intent);
                if (FreeCollageActivity.this.mInterstitialAd.isLoaded()) {
                    FreeCollageActivity.this.mInterstitialAd.show();
                }
*/            }
        });
    }

    private Bitmap viewToBitmap(RelativeLayout frameLayout) {
        Bitmap b = null;
        try {
            b = Bitmap.createBitmap(frameLayout.getWidth(), frameLayout.getHeight(), Config.ARGB_8888);
            frameLayout.draw(new Canvas(b));
            return b;
        } finally {
            frameLayout.destroyDrawingCache();
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar.getId() == R.id.effect_seekBar) {
            changeFilterProgress(((float) i) / ((float) seekBar.getMax()));
            this.effects_adapter.setSelected_position_progress(i);
        } else if (seekBar.getId() == R.id.bg_seekBar) {
            this.bg_image.setImageBitmap(ImageUtils.getTiledBitmap(this, this.curTileId, Dimensions.S_1P, Dimensions.S_1P, this.bg_seekbar.getProgress()));
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void onTouchDown(View v) {
        if (v != this.focusedView) {
            removeImageViewControll();
            this.focusedView = v;
        }
    }

    public void onTouchUp(View v) {
    }

    public void onDoubleTap(View v) {
        this.editMode = true;
        TextInfo t = ((AutofitTextRel) this.txt_sticker_rel.getChildAt(this.txt_sticker_rel.getChildCount() - 1)).getTextInfo();
        Intent i = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("X", (int) t.getPOS_X());
        bundle.putInt("Y", (int) t.getPOS_Y());
        bundle.putInt("wi", t.getWIDTH());
        bundle.putInt("he", t.getHEIGHT());
        bundle.putString("text", t.getTEXT());
        bundle.putString("fontName", t.getFONT_NAME());
        bundle.putInt("tColor", t.getTEXT_COLOR());
        bundle.putInt("tAlpha", t.getTEXT_ALPHA());
        bundle.putInt("shadowColor", t.getSHADOW_COLOR());
        bundle.putInt("shadowProg", t.getSHADOW_PROG());
        bundle.putString("bgDrawable", t.getBG_DRAWABLE());
        bundle.putInt("bgColor", t.getBG_COLOR());
        bundle.putInt("bgAlpha", t.getBG_ALPHA());
        bundle.putFloat("rotation", t.getROTATION());
        i.putExtras(bundle);
        startActivityForResult(i, this.TEXT_ACTIVITY);
    }

    public void onDelete(View v) {
        if (v instanceof AutofitTextRel) {
            this.autofitTextRel = null;
            this.color_container.setVisibility(View.GONE);
            this.textfont_adapter.setSelected(500);
        }
    }

    public void onEdit(View v, String uri) {
    }

}
