package mobo.andro.apps.ohmagiccamera;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.explorer.winklib.presenter.BusinessLogic;

import mobo.andro.apps.ohmagiccamera.CollageMaker.MainActivity;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 2100;

    class C06891 implements Runnable {
        C06891() { }

        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(1024, 1024);
        TextView heading_text=(TextView)findViewById(R.id.heading_text);
        Typeface ttf_heading = Typeface.createFromAsset(getAssets(), "font36.ttf");
        heading_text.setTypeface(ttf_heading);

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(findViewById(R.id.icon), "rotationY", new float[]{0.0f, 180.0f});
        animation1.setDuration(2000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        animation1.start();
        new Handler().postDelayed(new C06891(), (long) SPLASH_TIME_OUT);
    }

    protected void onPause() {
        super.onPause();
    }
}
