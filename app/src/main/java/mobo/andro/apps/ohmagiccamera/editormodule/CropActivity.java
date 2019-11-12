package mobo.andro.apps.ohmagiccamera.editormodule;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.crop.CropImageView;

public class CropActivity extends Activity {
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    CropImageView cropimage;
    Button custom;
    Button done;
    RelativeLayout footer;
    RelativeLayout header;
    TextView headertext;
    Button ratio1;
    Button ratio10;
    Button ratio11;
    Button ratio12;
    Button ratio13;
    Button ratio14;
    Button ratio15;
    Button ratio2;
    Button ratio3;
    Button ratio4;
    Button ratio5;
    Button ratio6;
    Button ratio7;
    Button ratio8;
    Button ratio9;
    RelativeLayout rel;
    Button square;
    Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$1 */
    class C08231 implements OnClickListener {
        C08231() {
        }

        public void onClick(View v) {
            CropActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$2 */
    class C08242 implements OnClickListener {
        C08242() {
        }

        public void onClick(View v) {
            CropActivity.this.bitmap = CropActivity.this.cropimage.getCroppedImage();
            PhotoEditor.edBitmap = CropActivity.this.bitmap;
            CropActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$3 */
    class C08253 implements OnClickListener {
        C08253() {
        }

        public void onClick(View v) {
            CropActivity.this.cropimage.setFixedAspectRatio(false);
            CropActivity.this.setAllUnselected();
            CropActivity.this.custom.setBackgroundResource(R.drawable.editor_crop_buttons);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$4 */
    class C08264 implements OnClickListener {
        C08264() {
        }

        public void onClick(View v) {
            CropActivity.this.cropimage.setFixedAspectRatio(true);
            CropActivity.this.cropimage.setAspectRatio(1, 1);
            CropActivity.this.setAllUnselected();
            CropActivity.this.square.setBackgroundResource(R.drawable.editor_crop_buttons);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$5 */
    class C08275 implements OnClickListener {
        C08275() {
        }

        public void onClick(View v) {
            CropActivity.this.cropimage.setFixedAspectRatio(true);
            CropActivity.this.cropimage.setAspectRatio(1, 2);
            CropActivity.this.setAllUnselected();
            CropActivity.this.ratio1.setBackgroundResource(R.drawable.editor_crop_buttons);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$6 */
    class C08286 implements OnClickListener {
        C08286() {
        }

        public void onClick(View v) {
            CropActivity.this.cropimage.setFixedAspectRatio(true);
            CropActivity.this.cropimage.setAspectRatio(2, 1);
            CropActivity.this.setAllUnselected();
            CropActivity.this.ratio2.setBackgroundResource(R.drawable.editor_crop_buttons);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$7 */
    class C08297 implements OnClickListener {
        C08297() {
        }

        public void onClick(View v) {
            CropActivity.this.cropimage.setFixedAspectRatio(true);
            CropActivity.this.cropimage.setAspectRatio(2, 3);
            CropActivity.this.setAllUnselected();
            CropActivity.this.ratio3.setBackgroundResource(R.drawable.editor_crop_buttons);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$8 */
    class C08308 implements OnClickListener {
        C08308() {
        }

        public void onClick(View v) {
            CropActivity.this.cropimage.setFixedAspectRatio(true);
            CropActivity.this.cropimage.setAspectRatio(3, 2);
            CropActivity.this.setAllUnselected();
            CropActivity.this.ratio4.setBackgroundResource(R.drawable.editor_crop_buttons);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.CropActivity$9 */
    class C08319 implements OnClickListener {
        C08319() {
        }

        public void onClick(View v) {
            CropActivity.this.cropimage.setFixedAspectRatio(true);
            CropActivity.this.cropimage.setAspectRatio(3, 4);
            CropActivity.this.setAllUnselected();
            CropActivity.this.ratio5.setBackgroundResource(R.drawable.editor_crop_buttons);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_crop);
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.cropimage = (CropImageView) findViewById(R.id.cropimage);
        this.done = (Button) findViewById(R.id.done);
        this.custom = (Button) findViewById(R.id.cutom);
        this.square = (Button) findViewById(R.id.square);
        this.ratio1 = (Button) findViewById(R.id.ratio1);
        this.ratio2 = (Button) findViewById(R.id.ratio2);
        this.ratio3 = (Button) findViewById(R.id.ratio3);
        this.ratio4 = (Button) findViewById(R.id.ratio4);
        this.ratio5 = (Button) findViewById(R.id.ratio5);
        this.ratio6 = (Button) findViewById(R.id.ratio6);
        this.ratio7 = (Button) findViewById(R.id.ratio7);
        this.ratio8 = (Button) findViewById(R.id.ratio8);
        this.ratio9 = (Button) findViewById(R.id.ratio9);
        this.ratio10 = (Button) findViewById(R.id.ratio10);
        this.ratio11 = (Button) findViewById(R.id.ratio11);
        this.ratio12 = (Button) findViewById(R.id.ratio12);
        this.ratio13 = (Button) findViewById(R.id.ratio13);
        this.ratio14 = (Button) findViewById(R.id.ratio14);
        this.ratio15 = (Button) findViewById(R.id.ratio15);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_down);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.custom.setTypeface(this.ttf);
        this.square.setTypeface(this.ttf);
        this.bitmap = PhotoEditor.edBitmap;
        this.bitmap = resizeBitmap(this.bitmap, getIntent().getIntExtra("forcal", 102));
        this.cropimage.setImageBitmap(this.bitmap);
        findViewById(R.id.btn_bck).setOnClickListener(new C08231());
        this.done.setOnClickListener(new C08242());
        this.custom.setOnClickListener(new C08253());
        this.square.setOnClickListener(new C08264());
        this.ratio1.setOnClickListener(new C08275());
        this.ratio2.setOnClickListener(new C08286());
        this.ratio3.setOnClickListener(new C08297());
        this.ratio4.setOnClickListener(new C08308());
        this.ratio5.setOnClickListener(new C08319());
        this.ratio6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(3, 5);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio6.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(4, 3);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio7.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(4, 5);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio8.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(4, 7);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio9.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 3);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio10.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 4);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio11.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio12.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 6);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio12.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio13.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 7);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio13.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(9, 16);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio14.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
        this.ratio15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(16, 9);
                CropActivity.this.setAllUnselected();
                CropActivity.this.ratio15.setBackgroundResource(R.drawable.editor_crop_buttons);
            }
        });
    }

    private void setAllUnselected() {
        this.custom.setBackgroundResource(R.drawable.editor_compare_button);
        this.square.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio1.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio2.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio3.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio4.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio5.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio6.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio7.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio8.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio9.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio10.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio11.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio12.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio13.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio14.setBackgroundResource(R.drawable.editor_compare_button);
        this.ratio15.setBackgroundResource(R.drawable.editor_compare_button);
    }

    Bitmap resizeBitmap(Bitmap bit, int forcal) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float wr = (float) dm.widthPixels;
        float hr = ((float) dm.heightPixels) - ((float) forcal);
        float wd = (float) bit.getWidth();
        float he = (float) bit.getHeight();
        float rat1 = wd / he;
        float rat2 = he / wd;
        if (wd > wr) {
            wd = wr;
            he = wd * rat2;
        } else if (he > hr) {
            he = hr;
            wd = he * rat1;
        } else if (rat1 > 0.75f) {
            wd = wr;
            he = wd * rat2;
        } else if (rat2 > 1.5f) {
            he = hr;
            wd = he * rat1;
        } else {
            wd = wr;
            he = wd * rat2;
        }
        return Bitmap.createScaledBitmap(bit, (int) wd, (int) he, false);
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
