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

import mobo.andro.apps.ohmagiccamera.R;

public class OverlaysActivity extends Activity {
    int alpha = 80;
    Bitmap bb;
    Bitmap bit;
    Bitmap bitmap;
    Bitmap bl;
    Animation bottomDown;
    Animation bottomUp;
    Button compare;
    Button done;
    RelativeLayout footer;
    RelativeLayout header;
    TextView headertext;
    ImageView image;
    Button o1;
    Button o10;
    Button o11;
    Button o12;
    Button o13;
    Button o14;
    Button o15;
    Button o16;
    Button o17;
    Button o18;
    Button o19;
    Button o2;
    Button o20;
    Button o21;
    Button o22;
    Button o23;
    Button o24;
    Button o25;
    Button o26;
    Button o27;
    Button o28;
    Button o29;
    Button o3;
    Button o30;
    Button o4;
    Button o5;
    Button o6;
    Button o7;
    Button o8;
    Button o9;
    Bitmap oi;
    Bitmap ol;
    RelativeLayout rel;
    SeekBar seek;
    Typeface ttf;

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$1 */
    class C08581 implements OnClickListener {
        C08581() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$2 */
    class C08592 implements OnClickListener {
        C08592() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o1);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$3 */
    class C08603 implements OnClickListener {
        C08603() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o2);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$4 */
    class C08614 implements OnClickListener {
        C08614() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o3);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$5 */
    class C08625 implements OnClickListener {
        C08625() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o4);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$6 */
    class C08636 implements OnClickListener {
        C08636() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o5);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$7 */
    class C08647 implements OnClickListener {
        C08647() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o6);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$8 */
    class C08658 implements OnClickListener {
        C08658() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o7);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: mobo.andro.apps.camera.editormodule.OverlaysActivity$9 */
    class C08669 implements OnClickListener {
        C08669() {
        }

        public void onClick(View v) {
            OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o8);
            OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
            OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
        }
    }

    class ThumbnailLoader extends AsyncTask<Void, Void, Void> {
        Bitmap bb;
        Bitmap bl1;
        Bitmap bl10;
        Bitmap bl11;
        Bitmap bl12;
        Bitmap bl13;
        Bitmap bl14;
        Bitmap bl15;
        Bitmap bl16;
        Bitmap bl17;
        Bitmap bl18;
        Bitmap bl19;
        Bitmap bl2;
        Bitmap bl20;
        Bitmap bl21;
        Bitmap bl22;
        Bitmap bl23;
        Bitmap bl24;
        Bitmap bl25;
        Bitmap bl26;
        Bitmap bl27;
        Bitmap bl28;
        Bitmap bl29;
        Bitmap bl3;
        Bitmap bl30;
        Bitmap bl4;
        Bitmap bl5;
        Bitmap bl6;
        Bitmap bl7;
        Bitmap bl8;
        Bitmap bl9;
        Bitmap ol1;
        Bitmap ol10;
        Bitmap ol11;
        Bitmap ol12;
        Bitmap ol13;
        Bitmap ol14;
        Bitmap ol15;
        Bitmap ol16;
        Bitmap ol17;
        Bitmap ol18;
        Bitmap ol19;
        Bitmap ol2;
        Bitmap ol20;
        Bitmap ol21;
        Bitmap ol22;
        Bitmap ol23;
        Bitmap ol24;
        Bitmap ol25;
        Bitmap ol26;
        Bitmap ol27;
        Bitmap ol28;
        Bitmap ol29;
        Bitmap ol3;
        Bitmap ol30;
        Bitmap ol4;
        Bitmap ol5;
        Bitmap ol6;
        Bitmap ol7;
        Bitmap ol8;
        Bitmap ol9;

        ThumbnailLoader() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.bb = Bitmap.createScaledBitmap(OverlaysActivity.this.bitmap, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
        }

        protected Void doInBackground(Void... params) {
            this.ol1 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o1);
            this.ol1 = Bitmap.createScaledBitmap(this.ol1, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol2 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o2);
            this.ol2 = Bitmap.createScaledBitmap(this.ol2, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol3 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o3);
            this.ol3 = Bitmap.createScaledBitmap(this.ol3, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol4 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o4);
            this.ol4 = Bitmap.createScaledBitmap(this.ol4, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol5 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o5);
            this.ol5 = Bitmap.createScaledBitmap(this.ol5, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol6 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o6);
            this.ol6 = Bitmap.createScaledBitmap(this.ol6, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol7 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o7);
            this.ol7 = Bitmap.createScaledBitmap(this.ol7, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol8 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o8);
            this.ol8 = Bitmap.createScaledBitmap(this.ol8, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol9 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o9);
            this.ol9 = Bitmap.createScaledBitmap(this.ol9, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol10 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o10);
            this.ol10 = Bitmap.createScaledBitmap(this.ol10, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol11 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o11);
            this.ol11 = Bitmap.createScaledBitmap(this.ol11, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol12 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o12);
            this.ol12 = Bitmap.createScaledBitmap(this.ol12, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol13 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o13);
            this.ol13 = Bitmap.createScaledBitmap(this.ol13, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol14 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o14);
            this.ol14 = Bitmap.createScaledBitmap(this.ol14, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol15 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o15);
            this.ol15 = Bitmap.createScaledBitmap(this.ol15, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol16 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o16);
            this.ol16 = Bitmap.createScaledBitmap(this.ol16, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol17 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o17);
            this.ol17 = Bitmap.createScaledBitmap(this.ol17, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol18 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o18);
            this.ol18 = Bitmap.createScaledBitmap(this.ol18, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol19 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o19);
            this.ol19 = Bitmap.createScaledBitmap(this.ol19, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol20 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o20);
            this.ol20 = Bitmap.createScaledBitmap(this.ol20, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol21 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o21);
            this.ol21 = Bitmap.createScaledBitmap(this.ol21, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol22 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o22);
            this.ol22 = Bitmap.createScaledBitmap(this.ol22, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol23 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o23);
            this.ol23 = Bitmap.createScaledBitmap(this.ol23, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol24 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o24);
            this.ol24 = Bitmap.createScaledBitmap(this.ol24, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol25 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o25);
            this.ol25 = Bitmap.createScaledBitmap(this.ol25, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol26 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o26);
            this.ol26 = Bitmap.createScaledBitmap(this.ol26, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol27 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o27);
            this.ol27 = Bitmap.createScaledBitmap(this.ol27, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol28 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o28);
            this.ol28 = Bitmap.createScaledBitmap(this.ol28, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol29 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o29);
            this.ol29 = Bitmap.createScaledBitmap(this.ol29, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.ol30 = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o30);
            this.ol30 = Bitmap.createScaledBitmap(this.ol30, Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION, false);
            this.bl1 = OverlaysActivity.this.merge(this.bb, this.ol1, OverlaysActivity.this.alpha);
            this.bl1 = OverlaysActivity.this.addBorder(this.bl1, 3, -1);
            this.bl2 = OverlaysActivity.this.merge(this.bb, this.ol2, OverlaysActivity.this.alpha);
            this.bl2 = OverlaysActivity.this.addBorder(this.bl2, 3, -1);
            this.bl3 = OverlaysActivity.this.merge(this.bb, this.ol3, OverlaysActivity.this.alpha);
            this.bl3 = OverlaysActivity.this.addBorder(this.bl3, 3, -1);
            this.bl4 = OverlaysActivity.this.merge(this.bb, this.ol4, OverlaysActivity.this.alpha);
            this.bl4 = OverlaysActivity.this.addBorder(this.bl4, 3, -1);
            this.bl5 = OverlaysActivity.this.merge(this.bb, this.ol5, OverlaysActivity.this.alpha);
            this.bl5 = OverlaysActivity.this.addBorder(this.bl5, 3, -1);
            this.bl6 = OverlaysActivity.this.merge(this.bb, this.ol6, OverlaysActivity.this.alpha);
            this.bl6 = OverlaysActivity.this.addBorder(this.bl6, 3, -1);
            this.bl7 = OverlaysActivity.this.merge(this.bb, this.ol7, OverlaysActivity.this.alpha);
            this.bl7 = OverlaysActivity.this.addBorder(this.bl7, 3, -1);
            this.bl8 = OverlaysActivity.this.merge(this.bb, this.ol8, OverlaysActivity.this.alpha);
            this.bl8 = OverlaysActivity.this.addBorder(this.bl8, 3, -1);
            this.bl9 = OverlaysActivity.this.merge(this.bb, this.ol9, OverlaysActivity.this.alpha);
            this.bl9 = OverlaysActivity.this.addBorder(this.bl9, 3, -1);
            this.bl10 = OverlaysActivity.this.merge(this.bb, this.ol10, OverlaysActivity.this.alpha);
            this.bl10 = OverlaysActivity.this.addBorder(this.bl10, 3, -1);
            this.bl11 = OverlaysActivity.this.merge(this.bb, this.ol11, OverlaysActivity.this.alpha);
            this.bl11 = OverlaysActivity.this.addBorder(this.bl11, 3, -1);
            this.bl12 = OverlaysActivity.this.merge(this.bb, this.ol12, OverlaysActivity.this.alpha);
            this.bl12 = OverlaysActivity.this.addBorder(this.bl12, 3, -1);
            this.bl13 = OverlaysActivity.this.merge(this.bb, this.ol13, OverlaysActivity.this.alpha);
            this.bl13 = OverlaysActivity.this.addBorder(this.bl13, 3, -1);
            this.bl14 = OverlaysActivity.this.merge(this.bb, this.ol14, OverlaysActivity.this.alpha);
            this.bl14 = OverlaysActivity.this.addBorder(this.bl14, 3, -1);
            this.bl15 = OverlaysActivity.this.merge(this.bb, this.ol15, OverlaysActivity.this.alpha);
            this.bl15 = OverlaysActivity.this.addBorder(this.bl15, 3, -1);
            this.bl16 = OverlaysActivity.this.merge(this.bb, this.ol16, OverlaysActivity.this.alpha);
            this.bl16 = OverlaysActivity.this.addBorder(this.bl16, 3, -1);
            this.bl17 = OverlaysActivity.this.merge(this.bb, this.ol17, OverlaysActivity.this.alpha);
            this.bl17 = OverlaysActivity.this.addBorder(this.bl17, 3, -1);
            this.bl18 = OverlaysActivity.this.merge(this.bb, this.ol18, OverlaysActivity.this.alpha);
            this.bl18 = OverlaysActivity.this.addBorder(this.bl18, 3, -1);
            this.bl19 = OverlaysActivity.this.merge(this.bb, this.ol19, OverlaysActivity.this.alpha);
            this.bl19 = OverlaysActivity.this.addBorder(this.bl19, 3, -1);
            this.bl20 = OverlaysActivity.this.merge(this.bb, this.ol20, OverlaysActivity.this.alpha);
            this.bl20 = OverlaysActivity.this.addBorder(this.bl20, 3, -1);
            this.bl21 = OverlaysActivity.this.merge(this.bb, this.ol21, OverlaysActivity.this.alpha);
            this.bl21 = OverlaysActivity.this.addBorder(this.bl21, 3, -1);
            this.bl22 = OverlaysActivity.this.merge(this.bb, this.ol22, OverlaysActivity.this.alpha);
            this.bl22 = OverlaysActivity.this.addBorder(this.bl22, 3, -1);
            this.bl23 = OverlaysActivity.this.merge(this.bb, this.ol23, OverlaysActivity.this.alpha);
            this.bl23 = OverlaysActivity.this.addBorder(this.bl23, 3, -1);
            this.bl24 = OverlaysActivity.this.merge(this.bb, this.ol24, OverlaysActivity.this.alpha);
            this.bl24 = OverlaysActivity.this.addBorder(this.bl24, 3, -1);
            this.bl25 = OverlaysActivity.this.merge(this.bb, this.ol25, OverlaysActivity.this.alpha);
            this.bl25 = OverlaysActivity.this.addBorder(this.bl25, 3, -1);
            this.bl26 = OverlaysActivity.this.merge(this.bb, this.ol26, OverlaysActivity.this.alpha);
            this.bl26 = OverlaysActivity.this.addBorder(this.bl26, 3, -1);
            this.bl27 = OverlaysActivity.this.merge(this.bb, this.ol27, OverlaysActivity.this.alpha);
            this.bl27 = OverlaysActivity.this.addBorder(this.bl27, 3, -1);
            this.bl28 = OverlaysActivity.this.merge(this.bb, this.ol28, OverlaysActivity.this.alpha);
            this.bl28 = OverlaysActivity.this.addBorder(this.bl28, 3, -1);
            this.bl29 = OverlaysActivity.this.merge(this.bb, this.ol29, OverlaysActivity.this.alpha);
            this.bl29 = OverlaysActivity.this.addBorder(this.bl29, 3, -1);
            this.bl30 = OverlaysActivity.this.merge(this.bb, this.ol30, OverlaysActivity.this.alpha);
            this.bl30 = OverlaysActivity.this.addBorder(this.bl30, 3, -1);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (VERSION.SDK_INT < 16) {
                OverlaysActivity.this.o1.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl1));
                OverlaysActivity.this.o2.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl2));
                OverlaysActivity.this.o3.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl3));
                OverlaysActivity.this.o4.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl4));
                OverlaysActivity.this.o5.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl5));
                OverlaysActivity.this.o6.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl6));
                OverlaysActivity.this.o7.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl7));
                OverlaysActivity.this.o8.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl8));
                OverlaysActivity.this.o9.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl9));
                OverlaysActivity.this.o10.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl10));
                OverlaysActivity.this.o11.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl11));
                OverlaysActivity.this.o12.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl12));
                OverlaysActivity.this.o13.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl13));
                OverlaysActivity.this.o14.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl14));
                OverlaysActivity.this.o15.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl15));
                OverlaysActivity.this.o16.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl16));
                OverlaysActivity.this.o17.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl17));
                OverlaysActivity.this.o18.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl18));
                OverlaysActivity.this.o19.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl19));
                OverlaysActivity.this.o20.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl20));
                OverlaysActivity.this.o21.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl21));
                OverlaysActivity.this.o22.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl22));
                OverlaysActivity.this.o23.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl23));
                OverlaysActivity.this.o24.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl24));
                OverlaysActivity.this.o25.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl25));
                OverlaysActivity.this.o26.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl26));
                OverlaysActivity.this.o27.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl27));
                OverlaysActivity.this.o28.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl28));
                OverlaysActivity.this.o29.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl29));
                OverlaysActivity.this.o30.setBackgroundDrawable(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl30));
                return;
            }
            OverlaysActivity.this.o1.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl1));
            OverlaysActivity.this.o2.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl2));
            OverlaysActivity.this.o3.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl3));
            OverlaysActivity.this.o4.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl4));
            OverlaysActivity.this.o5.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl5));
            OverlaysActivity.this.o6.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl6));
            OverlaysActivity.this.o7.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl7));
            OverlaysActivity.this.o8.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl8));
            OverlaysActivity.this.o9.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl9));
            OverlaysActivity.this.o10.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl10));
            OverlaysActivity.this.o11.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl11));
            OverlaysActivity.this.o12.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl12));
            OverlaysActivity.this.o13.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl13));
            OverlaysActivity.this.o14.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl14));
            OverlaysActivity.this.o15.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl15));
            OverlaysActivity.this.o16.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl16));
            OverlaysActivity.this.o17.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl17));
            OverlaysActivity.this.o18.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl18));
            OverlaysActivity.this.o19.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl19));
            OverlaysActivity.this.o20.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl20));
            OverlaysActivity.this.o21.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl21));
            OverlaysActivity.this.o22.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl22));
            OverlaysActivity.this.o23.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl23));
            OverlaysActivity.this.o24.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl24));
            OverlaysActivity.this.o25.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl25));
            OverlaysActivity.this.o26.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl26));
            OverlaysActivity.this.o27.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl27));
            OverlaysActivity.this.o28.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl28));
            OverlaysActivity.this.o29.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl29));
            OverlaysActivity.this.o30.setBackground(new BitmapDrawable(OverlaysActivity.this.getResources(), this.bl30));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_overlays);
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.image = (ImageView) findViewById(R.id.image);
        this.done = (Button) findViewById(R.id.done);
        this.seek = (SeekBar) findViewById(R.id.seek);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.compare = (Button) findViewById(R.id.compare);
        this.o1 = (Button) findViewById(R.id.o1);
        this.o2 = (Button) findViewById(R.id.o2);
        this.o3 = (Button) findViewById(R.id.o3);
        this.o4 = (Button) findViewById(R.id.o4);
        this.o5 = (Button) findViewById(R.id.o5);
        this.o6 = (Button) findViewById(R.id.o6);
        this.o7 = (Button) findViewById(R.id.o7);
        this.o8 = (Button) findViewById(R.id.o8);
        this.o9 = (Button) findViewById(R.id.o9);
        this.o10 = (Button) findViewById(R.id.o10);
        this.o11 = (Button) findViewById(R.id.o11);
        this.o12 = (Button) findViewById(R.id.o12);
        this.o13 = (Button) findViewById(R.id.o13);
        this.o14 = (Button) findViewById(R.id.o14);
        this.o15 = (Button) findViewById(R.id.o15);
        this.o16 = (Button) findViewById(R.id.o16);
        this.o17 = (Button) findViewById(R.id.o17);
        this.o18 = (Button) findViewById(R.id.o18);
        this.o19 = (Button) findViewById(R.id.o19);
        this.o20 = (Button) findViewById(R.id.o20);
        this.o21 = (Button) findViewById(R.id.o21);
        this.o22 = (Button) findViewById(R.id.o22);
        this.o23 = (Button) findViewById(R.id.o23);
        this.o24 = (Button) findViewById(R.id.o24);
        this.o25 = (Button) findViewById(R.id.o25);
        this.o26 = (Button) findViewById(R.id.o26);
        this.o27 = (Button) findViewById(R.id.o27);
        this.o28 = (Button) findViewById(R.id.o28);
        this.o29 = (Button) findViewById(R.id.o29);
        this.o30 = (Button) findViewById(R.id.o30);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.editor_bottom_down);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.bitmap = PhotoEditor.edBitmap;
        this.bit = PhotoEditor.edBitmap;
        this.image.setImageBitmap(this.bitmap);
        this.seek.setMax(255);
        this.seek.setProgress(80);
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headertext.setTypeface(this.ttf);
        this.compare.setTypeface(this.ttf);
        new ThumbnailLoader().execute(new Void[0]);
        findViewById(R.id.btn_bck).setOnClickListener(new C08581());
        this.o1.setOnClickListener(new C08592());
        this.o2.setOnClickListener(new C08603());
        this.o3.setOnClickListener(new C08614());
        this.o4.setOnClickListener(new C08625());
        this.o5.setOnClickListener(new C08636());
        this.o6.setOnClickListener(new C08647());
        this.o7.setOnClickListener(new C08658());
        this.o8.setOnClickListener(new C08669());
        this.o9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o9);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o10);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o11);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o12.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o12);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o13.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o13);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o14);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o15);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o16.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o16);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o17.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o17);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o18.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o18);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o19.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o19);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o20.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o20);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o21.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o21);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o22.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o22);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o23.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o23);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o24.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o24);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o25.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o25);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o26.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o26);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o27.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o27);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o28.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o28);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o29.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o29);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.o30.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OverlaysActivity.this.oi = BitmapFactory.decodeResource(OverlaysActivity.this.getResources(), R.drawable.editor_o30);
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                OverlaysActivity.this.seek.setVisibility(View.VISIBLE);
            }
        });
        this.seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                OverlaysActivity.this.alpha = progress;
                OverlaysActivity.this.bit = OverlaysActivity.this.merge(OverlaysActivity.this.bitmap, OverlaysActivity.this.oi, OverlaysActivity.this.alpha);
                OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
            }
        });
        this.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhotoEditor.edBitmap = OverlaysActivity.this.bit;
                OverlaysActivity.this.finish();
            }
        });
        this.compare.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bitmap);
                        break;
                    case 1:
                        OverlaysActivity.this.image.setImageBitmap(OverlaysActivity.this.bit);
                        break;
                }
                return true;
            }
        });
    }

    private Bitmap merge(Bitmap bitmap1, Bitmap a1, int alpha) {
        Bitmap bit = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Config.ARGB_8888);
        Drawable[] layers = new Drawable[]{new BitmapDrawable(bitmap1), new BitmapDrawable(a1)};
        layers[1].setAlpha(alpha);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        Canvas c = new Canvas(bit);
        layerDrawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        layerDrawable.draw(c);
        return bit;
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
