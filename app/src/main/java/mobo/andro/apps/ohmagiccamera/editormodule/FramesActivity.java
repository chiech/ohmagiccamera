package mobo.andro.apps.ohmagiccamera.editormodule;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.R;

public class FramesActivity extends Activity {
    Bitmap bit;
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    Button compare;
    Button done;
    Button f1;
    Button f10;
    Button f11;
    Button f12;
    Button f13;
    Button f14;
    Button f15;
    Button f16;
    Button f17;
    Button f18;
    Button f19;
    Button f2;
    Button f20;
    Button f21;
    Button f22;
    Button f23;
    Button f24;
    Button f25;
    Button f26;
    Button f27;
    Button f28;
    Button f29;
    Button f3;
    Button f30;
    Button f4;
    Button f5;
    Button f6;
    Button f7;
    Button f8;
    Button f9;
    RelativeLayout footer;
    RelativeLayout header;
    TextView headertext;
    ImageView image;
    RelativeLayout rel;
    SeekBar seek;
    Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$1 */
    class C08411 implements OnClickListener {
        C08411() {
        }

        public void onClick(View v) {
            FramesActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$2 */
    class C08422 implements OnClickListener {
        C08422() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f1);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$3 */
    class C08433 implements OnClickListener {
        C08433() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f2);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$4 */
    class C08444 implements OnClickListener {
        C08444() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f3);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$5 */
    class C08455 implements OnClickListener {
        C08455() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f4);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$6 */
    class C08466 implements OnClickListener {
        C08466() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f5);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$7 */
    class C08477 implements OnClickListener {
        C08477() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f6);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$8 */
    class C08488 implements OnClickListener {
        C08488() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f7);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.FramesActivity$9 */
    class C08499 implements OnClickListener {
        C08499() {
        }

        public void onClick(View v) {
            Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f8);
            FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
            FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_frames);
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.image = (ImageView) findViewById(R.id.image);
        this.done = (Button) findViewById(R.id.done);
        this.seek = (SeekBar) findViewById(R.id.seek);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.compare = (Button) findViewById(R.id.compare);
        this.f1 = (Button) findViewById(R.id.f1);
        this.f2 = (Button) findViewById(R.id.f2);
        this.f3 = (Button) findViewById(R.id.f3);
        this.f4 = (Button) findViewById(R.id.f4);
        this.f5 = (Button) findViewById(R.id.f5);
        this.f6 = (Button) findViewById(R.id.f6);
        this.f7 = (Button) findViewById(R.id.f7);
        this.f8 = (Button) findViewById(R.id.f8);
        this.f9 = (Button) findViewById(R.id.f9);
        this.f10 = (Button) findViewById(R.id.f10);
        this.f11 = (Button) findViewById(R.id.f11);
        this.f12 = (Button) findViewById(R.id.f12);
        this.f13 = (Button) findViewById(R.id.f13);
        this.f14 = (Button) findViewById(R.id.f14);
        this.f15 = (Button) findViewById(R.id.f15);
        this.f16 = (Button) findViewById(R.id.f16);
        this.f17 = (Button) findViewById(R.id.f17);
        this.f18 = (Button) findViewById(R.id.f18);
        this.f19 = (Button) findViewById(R.id.f19);
        this.f20 = (Button) findViewById(R.id.f20);
        this.f21 = (Button) findViewById(R.id.f21);
        this.f22 = (Button) findViewById(R.id.f22);
        this.f23 = (Button) findViewById(R.id.f23);
        this.f24 = (Button) findViewById(R.id.f24);
        this.f25 = (Button) findViewById(R.id.f25);
        this.f26 = (Button) findViewById(R.id.f26);
        this.f27 = (Button) findViewById(R.id.f27);
        this.f28 = (Button) findViewById(R.id.f28);
        this.f29 = (Button) findViewById(R.id.f29);
        this.f30 = (Button) findViewById(R.id.f30);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_down);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.bitmap = PhotoEditor.edBitmap;
        this.bit = PhotoEditor.edBitmap;
        this.image.setImageBitmap(this.bitmap);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.compare.setTypeface(this.ttf);
        findViewById(R.id.btn_bck).setOnClickListener(new C08411());
        this.f1.setOnClickListener(new C08422());
        this.f2.setOnClickListener(new C08433());
        this.f3.setOnClickListener(new C08444());
        this.f4.setOnClickListener(new C08455());
        this.f5.setOnClickListener(new C08466());
        this.f6.setOnClickListener(new C08477());
        this.f7.setOnClickListener(new C08488());
        this.f8.setOnClickListener(new C08499());
        this.f9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f9);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f10);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f11);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f12.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f12);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f13.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f13);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f14);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f15);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f16.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f16);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f17.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f17);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f18.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f18);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f19.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f19);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f20.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f20);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f21.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f21);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f22.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f22);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f23.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f23);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f24.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f24);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f25.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f25);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f26.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f26);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f27.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f27);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f28.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f28);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f29.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f29);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.f30.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bitmap oi = BitmapFactory.decodeResource(FramesActivity.this.getResources(), R.drawable.editor_f30);
                FramesActivity.this.bit = FramesActivity.this.merge(FramesActivity.this.bitmap, oi);
                FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
            }
        });
        this.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhotoEditor.edBitmap = FramesActivity.this.bit;
                FramesActivity.this.finish();
            }
        });
        this.compare.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        FramesActivity.this.image.setImageBitmap(FramesActivity.this.bitmap);
                        break;
                    case 1:
                        FramesActivity.this.image.setImageBitmap(FramesActivity.this.bit);
                        break;
                }
                return true;
            }
        });
    }

    private Bitmap merge(Bitmap bitmap1, Bitmap a1) {
        Bitmap bit = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Config.ARGB_8888);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new BitmapDrawable(bitmap1), new BitmapDrawable(a1)});
        Canvas c = new Canvas(bit);
        layerDrawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        layerDrawable.draw(c);
        return bit;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
