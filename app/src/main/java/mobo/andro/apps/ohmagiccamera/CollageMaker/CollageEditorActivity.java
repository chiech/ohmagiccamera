package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.irisstudio.demo.view.ComponentInfo;
import com.irisstudio.demo.view.ResizableStickerView;
import com.irisstudio.demo.view.ResizableStickerView.TouchEventListener;
import com.irisstudio.demo.view.TabHost;
import com.irisstudio.demo.view.TabHost.OnTabClickListener;

import org.wysaid.myUtils.ImageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.ShowImage;
import mobo.andro.apps.ohmagiccamera.CollageMaker.MultiTouchListener.TouchCallbackListener;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.BackgroundsAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.CollageIconAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.EffectsAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.SingleIconAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.StickerResAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.adapters.TextFontAdapter;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageEight;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageEighteen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageEleven;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageExtraOne;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageExtraTwo;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFifteen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFifty;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFive;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFour;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourteen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourty;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtyEight;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtyFive;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtyFour;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtyNine;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtyOne;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtySeven;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtySix;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtyThree;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageFourtyTwo;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageNine;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageNineteen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageOne;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageSeven;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageSeventeen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageSix;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageSixteen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirteen;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirty;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtyEight;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtyFive;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtyFour;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtyNine;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtyOne;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtySeven;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtySix;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtyThree;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThirtyTwo;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageThree;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwelve;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwenty;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentyEight;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentyFive;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentyFour;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentyNine;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentyOne;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentySeven;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentySix;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentyThreee;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwentyTwo;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.CollageTwo;
import mobo.andro.apps.ohmagiccamera.CollageMaker.fragments.Dimensions;
import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.textmodule.AutofitTextRel;
import mobo.andro.apps.ohmagiccamera.textmodule.TextActivity;
import mobo.andro.apps.ohmagiccamera.textmodule.TextInfo;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerColorAdapter;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerItemClickListener;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerItemClickListener.OnItemClickListener;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class CollageEditorActivity extends Activity implements OnClickListener, OnSeekBarChangeListener, TouchCallbackListener, TouchEventListener, AutofitTextRel.TouchEventListener {
    public static Bitmap resultBitmap;
    int TEXT_ACTIVITY = 234;
    private AutofitTextRel autofitTextRel = null;
    private int[] bgIconArr = new int[]{R.drawable.t_1, R.drawable.t_2, R.drawable.t_3, R.drawable.t_4, R.drawable.t_5, R.drawable.t_6, R.drawable.t_7, R.drawable.t_8, R.drawable.t_9, R.drawable.t_10, R.drawable.t_11, R.drawable.t_12, R.drawable.t_13, R.drawable.t_14, R.drawable.t_15, R.drawable.t_16, R.drawable.t_17, R.drawable.t_18, R.drawable.t_19, R.drawable.t_20, R.drawable.t_21, R.drawable.t_22, R.drawable.t_23, R.drawable.t_24, R.drawable.t_25, R.drawable.t_26, R.drawable.t_27, R.drawable.t_28, R.drawable.t_29, R.drawable.t_30, R.drawable.t_31, R.drawable.t_32};
    private int[] bgNameArr = new int[]{R.string.bg1, R.string.bg2, R.string.bg3, R.string.bg4, R.string.bg5, R.string.bg6, R.string.bg7, R.string.bg8, R.string.bg9, R.string.bg10, R.string.bg11, R.string.bg12, R.string.bg13, R.string.bg14, R.string.bg15, R.string.bg16, R.string.bg17, R.string.bg18, R.string.bg19, R.string.bg20, R.string.bg21, R.string.bg22, R.string.bg23, R.string.bg24, R.string.bg25, R.string.bg26, R.string.bg27, R.string.bg28, R.string.bg29, R.string.bg30, R.string.bg31, R.string.bg32};
    private BackgroundsAdapter bg_adapter;
    private ImageView bg_image;
    private RecyclerView bg_recycler;
    private RelativeLayout bg_res_lay;
    private SeekBar bg_seekBar;
    private int[] birthdayArr = new int[]{R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12, R.drawable.b13, R.drawable.b14, R.drawable.b15, R.drawable.b16, R.drawable.b17, R.drawable.b18, R.drawable.b19, R.drawable.b20, R.drawable.b21, R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25, R.drawable.b26};
    private int[] cartoonArr = new int[]{R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9, R.drawable.c10, R.drawable.c11, R.drawable.c12, R.drawable.c13, R.drawable.c14, R.drawable.c15, R.drawable.c16, R.drawable.c17, R.drawable.c18, R.drawable.c19, R.drawable.c20, R.drawable.c21, R.drawable.c22, R.drawable.c23, R.drawable.c24, R.drawable.c25, R.drawable.c26, R.drawable.c27, R.drawable.c28, R.drawable.c29, R.drawable.c30, R.drawable.c31, R.drawable.c32, R.drawable.c33};
    private RelativeLayout center_rel;
    private int[] collageIconArr = new int[]{R.drawable.grid_rect_0, R.drawable.grid_rect_1, R.drawable.grid_rect_2, R.drawable.grid_rect_3, R.drawable.grid_rect_4, R.drawable.grid_rect_5, R.drawable.grid_rect_6, R.drawable.grid_rect_7, R.drawable.grid_rect_8, R.drawable.grid_rect_9, R.drawable.grid_rect_10, R.drawable.grid_rect_11, R.drawable.grid_rect_12, R.drawable.grid_rect_13, R.drawable.grid_rect_14, R.drawable.grid_rect_15, R.drawable.grid_rect_16, R.drawable.grid_rect_17, R.drawable.grid_rect_18, R.drawable.grid_rect_19, R.drawable.grid_rect_20, R.drawable.grid_rect_21, R.drawable.grid_rect_22, R.drawable.grid_rect_23, R.drawable.grid_rect_24, R.drawable.grid_rect_25, R.drawable.grid_rect_26, R.drawable.grid_rect_27, R.drawable.grid_rect_28, R.drawable.grid_rect_29, R.drawable.grid_rect_41, R.drawable.grid_rect_30, R.drawable.grid_rect_31, R.drawable.grid_rect_32, R.drawable.grid_rect_33, R.drawable.grid_rect_34, R.drawable.grid_rect_35, R.drawable.grid_rect_36, R.drawable.grid_rect_37, R.drawable.grid_rect_38, R.drawable.grid_rect_39, R.drawable.grid_rect_40, R.drawable.grid_rect_421, R.drawable.grid_rect_422, R.drawable.grid_rect_43, R.drawable.grid_rect_44, R.drawable.grid_rect_45, R.drawable.grid_rect_46, R.drawable.grid_rect_47, R.drawable.grid_rect_48, R.drawable.grid_rect_49, R.drawable.grid_rect_50};
    private String[] collageNameArr = new String[]{CollageOne.class.getName(), CollageTwo.class.getName(), CollageThree.class.getName(), CollageFour.class.getName(), CollageFive.class.getName(), CollageSix.class.getName(), CollageSeven.class.getName(), CollageEight.class.getName(), CollageNine.class.getName(), CollageTen.class.getName(), CollageEleven.class.getName(), CollageTwelve.class.getName(), CollageThirteen.class.getName(), CollageFourteen.class.getName(), CollageSeventeen.class.getName(), CollageEighteen.class.getName(), CollageNineteen.class.getName(), CollageTwenty.class.getName(), CollageFifty.class.getName(), CollageFifteen.class.getName(), CollageSixteen.class.getName(), CollageTwentyOne.class.getName(), CollageTwentyTwo.class.getName(), CollageTwentyThreee.class.getName(), CollageTwentyFour.class.getName(), CollageTwentyFive.class.getName(), CollageTwentySix.class.getName(), CollageTwentySeven.class.getName(), CollageTwentyEight.class.getName(), CollageTwentyNine.class.getName(), CollageThirty.class.getName(), CollageThirtyOne.class.getName(), CollageThirtyTwo.class.getName(), CollageFourty.class.getName(), CollageFourtyOne.class.getName(), CollageThirtyThree.class.getName(), CollageThirtyFour.class.getName(), CollageThirtyFive.class.getName(), CollageThirtySix.class.getName(), CollageThirtySeven.class.getName(), CollageThirtyEight.class.getName(), CollageThirtyNine.class.getName(), CollageExtraOne.class.getName(), CollageExtraTwo.class.getName(), CollageFourtyTwo.class.getName(), CollageFourtyThree.class.getName(), CollageFourtyEight.class.getName(), CollageFourtyNine.class.getName(), CollageFourtyFour.class.getName(), CollageFourtyFive.class.getName(), CollageFourtySix.class.getName(), CollageFourtySeven.class.getName()};
    private CollageIconAdapter collage_adapter;
    private RecyclerView collage_recycler;
    private RelativeLayout collage_res_lay;
    private RelativeLayout color_container;
    private int curColor = -7829368;
    private CollageInterface curFragment = null;
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
    private int[] footerIconArr = new int[]{R.drawable.ic_collage, R.drawable.ic_filters, R.drawable.ic_patterns, R.drawable.ic_text, R.drawable.ic_sticker};
    private int[] footerNameArr = new int[]{R.string.collage, R.string.filters, R.string.patterns, R.string.text, R.string.stickers};
    private SingleIconAdapter footer_adapter;
    private RecyclerView footer_recycler;
    private int[] heartsArr = new int[]{R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h8, R.drawable.h9, R.drawable.h10, R.drawable.h11, R.drawable.h12, R.drawable.h13, R.drawable.h14, R.drawable.h15, R.drawable.h16, R.drawable.h17, R.drawable.h18, R.drawable.h19, R.drawable.h20, R.drawable.h21, R.drawable.h22, R.drawable.h23, R.drawable.h24, R.drawable.h25, R.drawable.h26, R.drawable.h27, R.drawable.h28, R.drawable.h29, R.drawable.h30, R.drawable.h31, R.drawable.h32, R.drawable.h33, R.drawable.h34, R.drawable.h35, R.drawable.h36, R.drawable.h37, R.drawable.h38, R.drawable.h39, R.drawable.h40, R.drawable.h41, R.drawable.h42, R.drawable.h43, R.drawable.h44, R.drawable.h45, R.drawable.h46, R.drawable.h47, R.drawable.h48, R.drawable.h49, R.drawable.h50};
    private boolean isUnlocked = true;
    private Bitmap logo;
    private LinearLayout logo_ll;
    private int[] loveArr = new int[]{R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l5, R.drawable.l6, R.drawable.l7, R.drawable.l8, R.drawable.l9, R.drawable.l10, R.drawable.l11, R.drawable.l12, R.drawable.l13, R.drawable.l14, R.drawable.l15, R.drawable.l16, R.drawable.l17, R.drawable.l18, R.drawable.l19, R.drawable.l20, R.drawable.l21, R.drawable.l22, R.drawable.l23, R.drawable.l24, R.drawable.l25, R.drawable.l26, R.drawable.l27, R.drawable.l28, R.drawable.l29, R.drawable.l30, R.drawable.l31, R.drawable.l32, R.drawable.l33, R.drawable.l34, R.drawable.l35, R.drawable.l36, R.drawable.l37};
    private int mainRelHeight;
    private int mainRelWidth;
    private String[] pallete = new String[]{"#ffffff", "#000000", "#383838", "#717070", "#bcbbbb", "#ffa800", "#ffcc00", "#ffe824", "#fcee74", "#b50000", "#ed0000", "#fd3e3e", "#ffabab", "#125301", "#2e8e15", "#59db36", "#b8ffa5", "#0244ec", "#0281ec", "#00b4ff", "#00deff"};
    private int[] partyArr = new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14, R.drawable.p15, R.drawable.p16, R.drawable.p17, R.drawable.p18, R.drawable.p19, R.drawable.p20};
    RecyclerColorAdapter recyclr_Color;
    private SeekBar seekbar_padding;
    private SeekBar seekbar_radius;
    private StickerResAdapter sticker_Adapter;
    private RelativeLayout sticker_container;
    private RecyclerView sticker_recyclerview;
    private TabHost tabhost;
    private int tempPos = 0;
    private TextFontAdapter textfont_adapter;
    private int[] textsArr = new int[]{R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8, R.drawable.t9, R.drawable.t10, R.drawable.t11, R.drawable.t12, R.drawable.t13, R.drawable.t14, R.drawable.t15, R.drawable.t16, R.drawable.t17, R.drawable.t18, R.drawable.t19, R.drawable.t20, R.drawable.t21, R.drawable.t22, R.drawable.t23, R.drawable.t24, R.drawable.t25, R.drawable.t26, R.drawable.t27, R.drawable.t28, R.drawable.t29, R.drawable.t30, R.drawable.t31, R.drawable.t32, R.drawable.t33, R.drawable.t34, R.drawable.t35, R.drawable.t36, R.drawable.t37, R.drawable.t38, R.drawable.t39, R.drawable.t40, R.drawable.t41, R.drawable.t42, R.drawable.t43, R.drawable.t44, R.drawable.t45, R.drawable.t46, R.drawable.t47, R.drawable.t48, R.drawable.t49, R.drawable.t50};
    private RelativeLayout txt_sticker_rel;

    class C03282 implements Runnable {
        C03282() {
        }

        public void run() {
            CollageEditorActivity.this.loadCollageLayout(CollageEditorActivity.this.getIntent().getStringExtra("className"));
            int bgIndex = CollageEditorActivity.this.isUnlocked ? ((int) (Math.random() * 10.0d)) + 0 : ((int) (Math.random() * 10.0d)) + 0;
            CollageEditorActivity.this.curTileId = CollageEditorActivity.this.bgIconArr[bgIndex];
            CollageEditorActivity.this.bg_image.setImageBitmap(ImageUtils.getTiledBitmap(CollageEditorActivity.this, CollageEditorActivity.this.curTileId, Dimensions.S_1P, Dimensions.S_1P, CollageEditorActivity.this.bg_seekBar.getProgress()));
            CollageEditorActivity.this.bg_adapter.setSelected(bgIndex);
            CollageEditorActivity.this.bg_recycler.scrollToPosition(bgIndex);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CollageEditorActivity$3 */
    class C03293 implements OnTouchListener {
        C03293() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                CollageEditorActivity.this.removeImageViewControll();
            }
            return false;
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CollageEditorActivity$4 */
    class C03304 implements OnTouchListener {
        C03304() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                CollageEditorActivity.this.removeImageViewControll();
            }
            return false;
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CollageEditorActivity$5 */
    class C03315 implements OnItemClickListener {
        C03315() {
        }

        public void onItemClick(View view, int position) {
            CollageEditorActivity.this.autofitTextRel.setTextColor(Color.parseColor(CollageEditorActivity.this.pallete[position]));
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CollageEditorActivity$6 */
    class C03326 implements OnTabClickListener {
        C03326() {
        }

        public void onTabClick(int position, String tabName) {
            CollageEditorActivity.this.tabhost.setTabSelected(position);
            if (position == 0) {
                CollageEditorActivity.this.setStickerAdapter(CollageEditorActivity.this.birthdayArr);
            }
            if (position == 1) {
                CollageEditorActivity.this.setStickerAdapter(CollageEditorActivity.this.cartoonArr);
            }
            if (position == 2) {
                CollageEditorActivity.this.setStickerAdapter(CollageEditorActivity.this.festivals);
            }
            if (position == 3) {
                CollageEditorActivity.this.setStickerAdapter(CollageEditorActivity.this.heartsArr);
            }
            if (position == 4) {
                CollageEditorActivity.this.setStickerAdapter(CollageEditorActivity.this.loveArr);
            }
            if (position == 5) {
                CollageEditorActivity.this.setStickerAdapter(CollageEditorActivity.this.partyArr);
            }
            if (position == 6) {
                CollageEditorActivity.this.setStickerAdapter(CollageEditorActivity.this.textsArr);
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CollageEditorActivity$7 */
    class C03337 implements StickerResAdapter.OnItemClickListener {
        C03337() {
        }

        public void onImageClick(int pos, int resId) {
            CollageEditorActivity.this.addSticker(resId);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CollageEditorActivity$8 */
    class C03348 implements SingleIconAdapter.OnItemClickListener {
        C03348() {
        }

        public void onImageClick(int pos, int resId) {
            CollageEditorActivity.this.footer_adapter.setSelected(pos);
            CollageEditorActivity.this.collage_res_lay.setVisibility(View.GONE);
            CollageEditorActivity.this.effect_res_lay.setVisibility(View.GONE);
            CollageEditorActivity.this.font_recycler.setVisibility(View.GONE);
            CollageEditorActivity.this.bg_res_lay.setVisibility(View.GONE);
            CollageEditorActivity.this.sticker_container.setVisibility(View.GONE);
            if (pos == 0) {
                CollageEditorActivity.this.collage_res_lay.setVisibility(View.VISIBLE);
            } else if (pos == 1) {
                CollageEditorActivity.this.effect_res_lay.setVisibility(View.VISIBLE);
            } else if (pos == 2) {
                CollageEditorActivity.this.bg_res_lay.setVisibility(View.VISIBLE);
            } else if (pos == 3) {
                CollageEditorActivity.this.font_recycler.setVisibility(View.VISIBLE);
            } else if (pos == 4) {
                CollageEditorActivity.this.sticker_container.setVisibility(View.VISIBLE);
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CollageEditorActivity$9 */
    class C03359 implements EffectsAdapter.OnItemClickListener {
        C03359() {
        }

        public void onImageClick(int pos, int resId) {
            CollageEditorActivity.this.effects_adapter.setSelected(pos);
            CollageEditorActivity.this.curFragment.setFilterConfig(Constants.EFFECT_CONFIGS[pos]);
            int selected_position_progress = CollageEditorActivity.this.effects_adapter.getSelected_position_progress(pos);
            CollageEditorActivity.this.effect_seekBar.setOnSeekBarChangeListener(null);
            CollageEditorActivity.this.effect_seekBar.setProgress(selected_position_progress);
            if (pos != 0) {
                CollageEditorActivity.this.effect_seekBar.setOnSeekBarChangeListener(CollageEditorActivity.this);
            }
        }

        public void onImageReClick(int pos, int resId) {
        }
    }


    public void onPause()
    {
        super.onPause();
        CustomAds.dismissInterstitialGoogle(CollageEditorActivity.this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_collage_editor);
        getWindow().setFlags(1024, 1024);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(CollageEditorActivity.this,adContainer);
        CustomAds.facebookAdInterstitial(CollageEditorActivity.this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.mainRelWidth = displaymetrics.widthPixels;
        this.mainRelHeight = displaymetrics.heightPixels;
        this.fontNameArr = getResources().getStringArray(R.array.fonts_array);
        initHeightWidth(this.mainRelWidth);
        initUI();
        initFooterLayout();
        initCollageRecyclerLayout();
        initEffectRecyclerLayout();
        initTextFontRecyclerLayout();
        initBackgroundsRecyclerLayout();
        initStickerRecyclerView();
        initStickerUI();
        this.center_rel.setLayoutParams(new LayoutParams(this.mainRelWidth, this.mainRelWidth));
        this.center_rel.post(new C03282());
    }

    private void initHeightWidth(int screenWidth) {
        Dimensions.MATCH_PARENT = -1;
        Dimensions.WRAP_CONTENT = -2;
        Dimensions.S_1P = screenWidth;
        Dimensions.S_2P = screenWidth / 2;
        Dimensions.S_3P = screenWidth / 3;
        Dimensions.S_4P = screenWidth / 4;
        Dimensions.S_5P = screenWidth / 5;
    }

    private void initUI() {
        this.logo_ll = (LinearLayout) findViewById(R.id.logo_ll);
        this.txt_sticker_rel = (RelativeLayout) findViewById(R.id.txt_sticker_rel);
        this.center_rel = (RelativeLayout) findViewById(R.id.center_rel);
        this.collage_res_lay = (RelativeLayout) findViewById(R.id.collage_res_lay);
        this.effect_res_lay = (RelativeLayout) findViewById(R.id.effect_res_lay);
        this.bg_res_lay = (RelativeLayout) findViewById(R.id.bg_res_lay);
        this.color_container = (RelativeLayout) findViewById(R.id.color_container);
        this.sticker_container = (RelativeLayout) findViewById(R.id.sticker_container);
        this.bg_image = (ImageView) findViewById(R.id.bg_image);
        this.bg_seekBar = (SeekBar) findViewById(R.id.bg_seekBar);
        this.effect_seekBar = (SeekBar) findViewById(R.id.effect_seekBar);
        this.seekbar_padding = (SeekBar) findViewById(R.id.seekbar_padding);
        this.seekbar_radius = (SeekBar) findViewById(R.id.seekbar_radius);
        this.bg_seekBar.setOnSeekBarChangeListener(this);
        this.effect_seekBar.setOnSeekBarChangeListener(this);
        this.seekbar_padding.setOnSeekBarChangeListener(this);
        this.seekbar_radius.setOnSeekBarChangeListener(this);
        this.txt_sticker_rel.setOnTouchListener(new C03293());
        findViewById(R.id.main_rel).setOnTouchListener(new C03304());
        this.recyclr_Color = new RecyclerColorAdapter(this, this.pallete);
        RecyclerView bottomRecyclerEdit = (RecyclerView) findViewById(R.id.color_recylr);
        bottomRecyclerEdit.setLayoutManager(new LinearLayoutManager(this, 0, false));
        bottomRecyclerEdit.setHasFixedSize(true);
        bottomRecyclerEdit.setAdapter(this.recyclr_Color);
        bottomRecyclerEdit.addOnItemTouchListener(new RecyclerItemClickListener(this, new C03315()));
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
        this.tabhost.setOnTabClickListener(new C03326());
        this.tabhost.setTabSelected(0);
        setStickerAdapter(this.birthdayArr);
    }

    private void setStickerAdapter(int[] arr) {
        this.sticker_Adapter = new StickerResAdapter(this, arr);
        this.sticker_recyclerview.setAdapter(this.sticker_Adapter);
        this.sticker_Adapter.setListner(new C03337());
    }

    private void initFooterLayout() {
        this.footer_recycler = (RecyclerView) findViewById(R.id.footer_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.footer_recycler.setLayoutManager(llm);
        this.footer_adapter = new SingleIconAdapter(this, this.footerIconArr, this.footerNameArr);
        this.footer_recycler.setAdapter(this.footer_adapter);
        this.footer_adapter.setOnItemClickListner(new C03348());
        this.footer_adapter.setSelected(0);
    }

    private void initEffectRecyclerLayout() {
        this.effects_recycler = (RecyclerView) findViewById(R.id.effects_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.effects_recycler.setLayoutManager(llm);
        this.effects_adapter = new EffectsAdapter(this, this.effectIconArr);
        this.effects_recycler.setAdapter(this.effects_adapter);
        this.effects_adapter.setOnItemClickListner(new C03359());
        this.effects_adapter.setSelected(0);
    }

    private void initCollageRecyclerLayout() {
        this.collage_recycler = (RecyclerView) findViewById(R.id.collage_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(0);
        this.collage_recycler.setLayoutManager(llm);
        this.collage_adapter = new CollageIconAdapter(this, this.collageIconArr);
        this.collage_recycler.setAdapter(this.collage_adapter);
        this.collage_adapter.setOnItemClickListner(new CollageIconAdapter.OnItemClickListener() {
            public void onImageClick(int pos, int resId) {
                CollageEditorActivity.this.collage_adapter.setSelected(pos);
                CollageEditorActivity.this.loadCollageLayout(CollageEditorActivity.this.collageNameArr[pos]);
            }
        });
        this.collage_adapter.setSelected(getSelectedPositionByName(getIntent().getStringExtra("className")));
        this.collage_recycler.scrollToPosition(getSelectedPositionByName(getIntent().getStringExtra("className")));
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
                CollageEditorActivity.this.textfont_adapter.setSelected(pos);
                CollageEditorActivity.this.initOrAddNewText(fontName);
            }

            public void onImageReClick(int pos, String fontName) {
                CollageEditorActivity.this.color_container.setVisibility(View.VISIBLE);
                CollageEditorActivity.this.tempPos = CollageEditorActivity.this.recyclr_Color.getSelected();
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
                CollageEditorActivity.this.bg_image.setBackgroundColor(0);
                CollageEditorActivity.this.bg_adapter.setSelected(pos);
                CollageEditorActivity.this.curTileId = resId;
                CollageEditorActivity.this.bg_image.setImageBitmap(ImageUtils.getTiledBitmap(CollageEditorActivity.this, CollageEditorActivity.this.curTileId, Dimensions.S_1P, Dimensions.S_1P, CollageEditorActivity.this.bg_seekBar.getProgress()));
                CollageEditorActivity.this.bg_seekBar.setOnSeekBarChangeListener(CollageEditorActivity.this);
            }

            public void onImageReClick(int pos, int resId) {
            }
        });
        this.bg_adapter.setSelected(0);
    }

    private void showColorDialog() {
        new AmbilWarnaDialog(this, this.curColor, new OnAmbilWarnaListener() {
            public void onOk(AmbilWarnaDialog dialog, int color) {
                CollageEditorActivity.this.bg_seekBar.setOnSeekBarChangeListener(null);
                CollageEditorActivity.this.bg_image.setImageBitmap(null);
                CollageEditorActivity.this.curColor = color;
                CollageEditorActivity.this.bg_image.setBackgroundColor(color);
                CollageEditorActivity.this.bg_adapter.setSelected(500);
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

    private void loadCollageLayout(String className) {
        try {
            Fragment fragment = (Fragment) Class.forName(className).getDeclaredMethod("newInstance", new Class[]{Boolean.TYPE}).invoke(null, new Object[]{Boolean.valueOf(false)});
            this.curFragment = (CollageInterface) fragment;
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        } catch (Exception e5) {
            e5.printStackTrace();
        }
    }

    private int getSelectedPositionByName(String name) {
        for (int i = 0; i <= this.collageNameArr.length; i++) {
            if (name.equals(this.collageNameArr[i])) {
                return i;
            }
        }
        return 0;
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
        }
    }

    public void onBackPressed() {
        int style;
        if (VERSION.SDK_INT >= 14) {
            style = 16974126;
        } else {
            style = 16973835;
        }
        new Builder(this, style).setTitle(getResources().getString(R.string.alert)).setIcon(R.mipmap.ic_launcher).setMessage(getResources().getString(R.string.exit_cut_page)).setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CollageEditorActivity.this.finish();
            }
        }).setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
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
                for (int i = 0; i < this.pallete.length; i++) {
                    if (this.pallete[i].equals(hexColorT.toLowerCase())) {
                        this.recyclr_Color.setSelected(i);
                    }
                }
                if (this.editMode) {
                    ((AutofitTextRel) this.txt_sticker_rel.getChildAt(this.txt_sticker_rel.getChildCount() - 1)).setTextInfo(textInfo);
                    this.editMode = false;
                    return;
                }
                removeImageViewControll();
                AutofitTextRel rl = new AutofitTextRel(this);
                this.txt_sticker_rel.addView(rl);
                rl.setTextInfo(textInfo);
                rl.setOnTouchCallbackListener(this);
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
                    Bitmap tbitmap = CollageEditorActivity.resultBitmap;//ImageUtils.mergelogo(CollageEditorActivity.resultBitmap, CollageEditorActivity.this.logo);
                    CollageEditorActivity.this.logo.recycle();
//                    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/" + CollageEditorActivity.this.getResources().getString(R.string.folder_name));
                    File pictureFileDir = new File(ImageUtil.getPath());
                    if (pictureFileDir.exists() || pictureFileDir.mkdirs()) {
                        CollageEditorActivity.this.filename = pictureFileDir.getPath() + File.separator + ("Photo_" + System.currentTimeMillis() + ".png");
                        File pictureFile = new File(CollageEditorActivity.this.filename);
                        try {
                            if (!pictureFile.exists()) {
                                pictureFile.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(pictureFile);
                            tbitmap.compress(CompressFormat.PNG, 100, ostream);
                            ostream.flush();
                            ostream.close();
                            tbitmap.recycle();
                            CollageEditorActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(pictureFile)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(1000);
                        ringProgressDialog.dismiss();
                        return;
                    }
                    Log.d("", "Can't create directory to save image.");
                    Toast.makeText(CollageEditorActivity.this.getApplicationContext(), CollageEditorActivity.this.getResources().getString(R.string.create_dir_err), Toast.LENGTH_LONG).show();
                } catch (Exception e2) {
                }
            }
        }).start();
        ringProgressDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog)
            {
                ShowImage.selected_image_path=CollageEditorActivity.this.filename;
                Intent intent=new Intent(CollageEditorActivity.this,ShowImage.class);
                startActivity(intent);
/*
                Toast.makeText(CollageEditorActivity.this.getApplicationContext(), CollageEditorActivity.this.getString(R.string.saved).toString() + " " + CollageEditorActivity.this.filename, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CollageEditorActivity.this, ShareImageActivity.class);
                intent.putExtra("uri", CollageEditorActivity.this.filename);
                intent.putExtra("fromGridCollageActivity", true);
                CollageEditorActivity.this.startActivity(intent);
                if (CollageEditorActivity.this.mInterstitialAd.isLoaded()) {
                    CollageEditorActivity.this.mInterstitialAd.show();
                }
            */}
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
        if (this.curFragment == null) {
            return;
        }
        if (seekBar.getId() == R.id.effect_seekBar) {
            this.curFragment.setFilterProgress(((float) i) / ((float) seekBar.getMax()));
            this.effects_adapter.setSelected_position_progress(i);
        } else if (seekBar.getId() == R.id.seekbar_padding) {
            this.curFragment.setGridPadding(i);
        } else if (seekBar.getId() == R.id.seekbar_radius) {
            this.curFragment.setCornerRadius(i);
        } else if (seekBar.getId() == R.id.bg_seekBar) {
            this.bg_image.setImageBitmap(ImageUtils.getTiledBitmap(this, this.curTileId, Dimensions.S_1P, Dimensions.S_1P, i));
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void onTouchCallback(View v) {
    }

    public void onTouchUpCallback(View v) {
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
    public void onEdit(View v, String uri) { }

}
