package mobo.andro.apps.ohmagiccamera.Camera;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.wysaid.camera.CameraInstance;
import org.wysaid.myUtils.FileUtil;
import org.wysaid.myUtils.ImageUtil;
import org.wysaid.myUtils.MsgUtil;
import org.wysaid.nativePort.CGENativeLibrary;
import org.wysaid.view.CameraRecordGLSurfaceView;

import java.lang.reflect.InvocationTargetException;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Gallery;
import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.textmodule.adapter.RecyclerItemClickListener;

public class CameraDemoActivity extends Activity
{
    public static String lastVideoPathFileName = FileUtil.getPath() + "/lastVideoPath.txt";
    static String mCurrentConfig;
    boolean isValid = true;
    String recordFilename;
    private CameraRecordGLSurfaceView mCameraView;
    public final static String LOG_TAG = CameraRecordGLSurfaceView.LOG_TAG;
    public static CameraDemoActivity mCurrentInstance = null;
    public static CameraDemoActivity getInstance() {
        return mCurrentInstance;
    }
    private void showText(final String s) {
        mCameraView.post(new Runnable() {
            @Override
            public void run() {
                MsgUtil.toastMsg(CameraDemoActivity.this, s);
            }
        });
    }
    public static class MyButtons extends Button {

        public String filterConfig;

        public MyButtons(Context context, String config) {
            super(context);
            filterConfig = config;
        }
    }
    class RecordListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if (!isValid) {
                Log.e(LOG_TAG, "Please wait for the call...");
                return;
            }
            isValid = false;
            if (!mCameraView.isRecording())
            {
                video_click_action.setImageResource(R.drawable.video_btn_shutter_pause);
                Log.i(LOG_TAG, "Start recording...");
                recordFilename = ImageUtil.getPath() + "/rec_" + System.currentTimeMillis() + ".mp4";
                mCameraView.startRecording(recordFilename, new CameraRecordGLSurfaceView.StartRecordingCallback() {
                    @Override
                    public void startRecordingOver(boolean success) {
                        if (success) {
                            showText("Start recording OK");
                            FileUtil.saveTextContent(recordFilename, lastVideoPathFileName);
                        } else {
                            showText("Start recording failed");
                        }
                        isValid = true;
                    }
                });
            }
            else {
                video_click_action.setImageResource(R.drawable.video_btn_shutter_dot);
                showText("Recorded as: " + recordFilename);
                Log.i(LOG_TAG, "End recording...");
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + recordFilename)));
                mCameraView.endRecording(new CameraRecordGLSurfaceView.EndRecordingCallback() {
                    @Override
                    public void endRecordingOK() {
                        Log.i(LOG_TAG, "End recording OK");
                        isValid = true;
                    }
                });
          }

        }
    }
    static int filterPos=0;
    boolean foucs = false;
    private FilterAdapter mAdapter;
    private RecyclerView mFilterListView;
    FontTextView textViewFilter;
    int height,width;
    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
        }

        return size;
    }
    ImageView flash_button,timer_button,video_or_photo,aspect_ratio,camera_switch,photo_click,video_click,video_click_action,gallery,filter;
    public static int is_video_or_photo=0;
    RelativeLayout video_click_parent;
    int bottom_hardware_height;
    SeekBar seekBar;
    LinearLayout fragment_aspect;
    int is_filter_open = 1;
    boolean is_camera_front=true;
    public void open_filter() {
        is_filter_open = 1;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", mFilterLayout.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                //     findViewById(R.id.save).setClickable(false);
                mFilterLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();
    }
    public void close_filter(){
        is_filter_open=0;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", 0 ,  mFilterLayout.getHeight());
        animator.setDuration(200L);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mFilterLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mFilterLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
    LinearLayout mFilterLayout;
    static int timer=0;
    static int flash=0;
    static int selected_aspect_ratio=0;
    boolean countCheck = false;
    TextView video_photo_text;
    LinearLayout buffer_frame;
    LinearLayout myGLSurfaceViewParent;
    public void countDownTimerPhoto(int num) {
        final int i = num;
        new CountDownTimer((long) num, 1000) {
            public void onTick(long millisUntilFinished) {
                showText1(0, "" + (millisUntilFinished / 1000));
            }
            public void onFinish() {
                if (i == 0) {
                    countCheck = true;
                } else {
                    countCheck = false;
                }
                showText("Taking Picture...");

                mCameraView.takePicture(new CameraRecordGLSurfaceView.TakePictureCallback() {
                    @Override
                    public void takePictureOK(Bitmap bmp) {
                        if (bmp != null) {
                            String s = ImageUtil.saveBitmap(bmp);
                            bmp.recycle();
                            showText("Take picture success!");
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + s)));
                        } else
                            showText("Take picture failed!");
                    }
                }, null, mCurrentConfig, 1.0f, true);
            }
        }.start();
    }
    RelativeLayout.LayoutParams param_full,param_3_4,param_1_1;
    public void executeAsyncTask_aspect_change(final int position) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                if(position==0)
                {
                    mCameraView.setPictureSize(1920,1440, false);
                    mCameraView.setFitFullView(true);
                    mCameraView.onPause();
                    mCameraView.onResume();

                }
                if(position==1)
                {
                    mCameraView.setPictureSize(1920,1440, false);
                    mCameraView.setFitFullView(false);
                    mCameraView.onPause();
                    mCameraView.onResume();
                }
                if(position==2)
                {
                    mCameraView.setPictureSize(1088,1088, false);
                    mCameraView.setFitFullView(false);
                    mCameraView.onPause();
                    mCameraView.onResume();
                }
                String msg = null;
                return msg;
            }
            @Override
            protected void onPostExecute(String msg)
            {
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(buffer_frame, "alpha",  1f, 0f);
                fadeOut.setDuration(1000);
                final AnimatorSet mAnimationSet = new AnimatorSet();
                mAnimationSet.play(fadeOut);
                mAnimationSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });
                mAnimationSet.start();
                }
        };
        if(Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            task.execute();
        }
    }

    public void update_ui(final int position) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(5000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                buffer_frame.setVisibility(View.VISIBLE);

/*                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(buffer_frame, "alpha",  1f, 0f);
                fadeOut.setDuration(4000);
                final AnimatorSet mAnimationSet = new AnimatorSet();
                mAnimationSet.play(fadeOut);
                mAnimationSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });
                mAnimationSet.start();
*/
            };
        };
        thread.start();
    }
    public void countDownTimerAspect(int num) {
        final int i = num;
        new CountDownTimer((long) num, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
            }
        }.start();
    }
    int left_h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_demo);
        getWindow().setFlags(1024, 1024);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height =displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        Point p=getRealScreenSize(CameraDemoActivity.this);
        Log.e("camsize"," : "+p.x+" : "+p.y);
        bottom_hardware_height=p.y-height;
        height=p.y;
        width=p.x;
        is_video_or_photo=0;
        selected_aspect_ratio=0;
        is_camera_front=true;

        left_h=(height-((width/3)*4)-bottom_hardware_height);
        myGLSurfaceViewParent=(LinearLayout)findViewById(R.id.myGLSurfaceViewParent);
        buffer_frame=(LinearLayout)findViewById(R.id.frame);
        LinearLayout bottom_layer=(LinearLayout)findViewById(R.id.bottom_layer);
//        LinearLayout.LayoutParams params_bottom_layer=new LinearLayout.LayoutParams(width,(width*20/100)+height*20/100);
        LinearLayout.LayoutParams params_bottom_layer=new LinearLayout.LayoutParams(width,left_h);
        params_bottom_layer.setMargins(0,0,0,bottom_hardware_height);
        bottom_layer.setLayoutParams(params_bottom_layer);

        LinearLayout bottom_layout_base=(LinearLayout)findViewById(R.id.bottom_layout_base);
        LinearLayout.LayoutParams params_bottom_layer_base=new LinearLayout.LayoutParams(width,left_h*46/100);
        bottom_layout_base.setLayoutParams(params_bottom_layer_base);

        LinearLayout filter_layout_base=(LinearLayout)findViewById(R.id.filter_layout_base);
        LinearLayout.LayoutParams params_filter_layout_base=new LinearLayout.LayoutParams(width,left_h*46/100);
        filter_layout_base.setLayoutParams(params_filter_layout_base);

        LinearLayout video_photo_text_base=(LinearLayout)findViewById(R.id.video_photo_text_base);
        LinearLayout.LayoutParams params_photo_text_base=new LinearLayout.LayoutParams(width,left_h*8/100);
        video_photo_text_base.setLayoutParams(params_photo_text_base);

        video_photo_text=(TextView)findViewById(R.id.video_photo_text);
        video_photo_text.setText("Photo");

        timer=0;
        flash=0;

        mFilterLayout=(LinearLayout)findViewById(R.id.layout_filter_layer);
        fragment_aspect=(LinearLayout)findViewById(R.id.fragment_aspect);

        flash_button=(ImageView)findViewById(R.id.flash_button);
        timer_button=(ImageView)findViewById(R.id.timer_button);
        video_or_photo=(ImageView)findViewById(R.id.video_or_photo);
        aspect_ratio=(ImageView)findViewById(R.id.aspect_ratio);
        camera_switch=(ImageView)findViewById(R.id.camera_switch);
        photo_click=(ImageView)findViewById(R.id.photo_click);
        video_click=(ImageView)findViewById(R.id.video_click);
        video_click_action=(ImageView)findViewById(R.id.video_click_action);
        gallery=(ImageView)findViewById(R.id.gallery);
        filter=(ImageView)findViewById(R.id.filter);
        video_click_parent=(RelativeLayout)findViewById(R.id.video_click_parent);

        flash_button.setImageResource(R.drawable.camera_btn_flash);
        timer_button.setImageResource(R.drawable.camera_btn_timer);
        video_or_photo.setImageResource(R.drawable.camera_btn_video);
        aspect_ratio.setImageResource(R.drawable.camera_btn_ratio_full);
        camera_switch.setImageResource(R.drawable.camera_btn_switch_w);
        photo_click.setImageResource(R.drawable.camera_btn_shutter);
        video_click.setImageResource(R.drawable.video_btn_shutter);
        video_click_action.setImageResource(R.drawable.video_btn_shutter_dot);
        filter.setImageResource(R.drawable.camera_btn_filter_w);
        gallery.setImageResource(R.drawable.camera_btn_album_w);

        LinearLayout.LayoutParams layoutParams_upper_element=new LinearLayout.LayoutParams(width*10/100,width*10/100);
        flash_button.setLayoutParams(layoutParams_upper_element);
        timer_button.setLayoutParams(layoutParams_upper_element);
        aspect_ratio.setLayoutParams(layoutParams_upper_element);
        camera_switch.setLayoutParams(layoutParams_upper_element);

        LinearLayout.LayoutParams layoutParams_lower_element=new LinearLayout.LayoutParams(width*11/100,width*11/100);
        gallery.setLayoutParams(layoutParams_lower_element);
        filter.setLayoutParams(layoutParams_lower_element);
        video_or_photo.setLayoutParams(layoutParams_lower_element);

//        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(left_h*46/100,left_h*46/100);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width*11/100,width*11/100);
//        photo_click.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams_inner_action=new LinearLayout.LayoutParams(width*5/100,width*5/100);
        video_click_action.setLayoutParams(layoutParams_inner_action);

        LinearLayout top_element_parent=(LinearLayout)findViewById(R.id.top_element_parent);
        LinearLayout.LayoutParams layoutParams_top_element_parent=new LinearLayout.LayoutParams(width,height*10/100);
        top_element_parent.setLayoutParams(layoutParams_top_element_parent);

        flash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(is_camera_front)
                {
                    Toast.makeText(CameraDemoActivity.this, "Flash is available at back camera.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(selected_aspect_ratio==2)
                    {
                        if(flash==0)
                        {
                            flash=1;
                            flash_button.setImageResource(R.drawable.camera_btn_flash_auto_b);
                            mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_AUTO);
                        }
                        else if(flash==1)
                        {
                            flash=2;
                            flash_button.setImageResource(R.drawable.camera_btn_flash_torch_b);
                            mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_TORCH);
                        }
                        else
                        {
                            flash=0;
                            flash_button.setImageResource(R.drawable.camera_btn_flash_b);
                            mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_OFF);

                        }

                    }
                    else
                    {
                        if(flash==0)
                        {
                            flash=1;
                            flash_button.setImageResource(R.drawable.camera_btn_flash_auto);
                            mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_AUTO);
                        }
                        else if(flash==1)
                        {
                            flash=2;
                            flash_button.setImageResource(R.drawable.camera_btn_flash_torch);
                            mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_TORCH);
                        }
                        else
                        {
                            flash=0;
                            flash_button.setImageResource(R.drawable.camera_btn_flash);
                            mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_OFF);

                        }
                    }
                }
            }
        });
        timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(selected_aspect_ratio==2)
                {
                    if(timer==0)
                    {
                        timer=3;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_3s_b);
                    }
                    else if(timer==3)
                    {
                        timer=5;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_5s_b);
                    }
                    else if(timer==5)
                    {
                        timer=10;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_10s_b);
                    }
                    else if(timer==10)
                    {
                        timer=15;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_15s_b);
                    }
                    else
                    {
                        timer=0;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_b);
                    }

                }
                else
                {
                    if(timer==0)
                    {
                        timer=3;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_3s);
                    }
                    else if(timer==3)
                    {
                        timer=5;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_5s);
                    }
                    else if(timer==5)
                    {
                        timer=10;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_10s);
                    }
                    else if(timer==10)
                    {
                        timer=15;
                        timer_button.setImageResource(R.drawable.camera_btn_timer_15s);
                    }
                    else
                    {
                        timer=0;
                        timer_button.setImageResource(R.drawable.camera_btn_timer);
                    }

                }

            }
        });

        video_or_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_video_or_photo==0)
                {
                    video_photo_text.setText("Video");
                    is_video_or_photo=1;

                    if(selected_aspect_ratio==0)
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_camera);
                    }
                    else
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_camera_b);
                    }
                    photo_click.setVisibility(View.GONE);
                    video_click_parent.setVisibility(View.VISIBLE);
                }
                else
                {
                    video_photo_text.setText("Photo");
                    is_video_or_photo=0;
                    if(selected_aspect_ratio==0)
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_video);
                    }
                    else
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_video_b);
                    }
                    video_click_parent.setVisibility(View.GONE);
                    photo_click.setVisibility(View.VISIBLE);
                }

            }
        });

        aspect_ratio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fragment_aspect.getVisibility()==View.GONE)
                {
                    fragment_aspect.setVisibility(View.VISIBLE);
                }
                else
                {
                    fragment_aspect.setVisibility(View.GONE);
                }
            }
        });

        camera_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(is_camera_front)
                {
                    is_camera_front=false;
                }
                else
                {
                    is_camera_front=true;
                }
                mCameraView.switchCamera();
            }
        });

        photo_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (timer == 0) {
                    countDownTimerPhoto(0);
                } else if (timer == 3) {
                    countDownTimerPhoto(3000);
                } else if (timer == 5) {
                    countDownTimerPhoto(5000);
                } else if (timer == 10) {
                    countDownTimerPhoto(10000);
                } else if (timer == 15) {
                    countDownTimerPhoto(15000);
                } else {
                    countDownTimerPhoto(0);
                }
            }
        });

        video_click_parent.setOnClickListener(new RecordListener());


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CameraDemoActivity.this.mAdapter.notifyDataSetChanged();
                if(is_filter_open==0)
                {
                    open_filter();
                    if (CameraDemoActivity.filterPos == 0) {
                        seekBar.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        seekBar.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    close_filter();
                    seekBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CameraDemoActivity.this, Gallery.class);
                intent.putExtra("title","Select media");
                intent.putExtra("mode",1);
                intent.putExtra("maxSelection",3);
                startActivity(intent);

//                Intent intent=new Intent(CameraDemoActivity.this, ImageGalleryActivity.class);
//                startActivity(intent);
            }
        });

        filterPos=0;
        textViewFilter = (FontTextView) findViewById(R.id.filterText);
        mFilterListView = (RecyclerView) findViewById(R.id.filter_listView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.aspect_ratio_listView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_off_set));
        Integer[] aspect_ratio_list={R.drawable.camera_btn_ratio_full,R.drawable.camera_btn_ratio_3x4,R.drawable.camera_btn_ratio_1x1_w};
        AspectRatioAdapter imageAdapter= new AspectRatioAdapter(CameraDemoActivity.this,aspect_ratio_list,width*10/100);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position)
            {
                selected_aspect_ratio=position;
                CameraDemoActivity.this.mAdapter.notifyDataSetChanged();
                if(position==0)
                {
                    if(timer==0)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer);
                    }
                    else if(timer==3)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_3s);
                    }
                    else if(timer==5)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_5s);
                    }
                    else if(timer==10)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_10s);
                    }
                    else
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_15s);
                    }

                    if(flash==0)
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash);
                    }
                    else if(flash==1)
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash_auto);
                    }
                    else
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash_torch);
                    }

                    camera_switch.setImageResource(R.drawable.camera_btn_switch_w);
                    seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.MULTIPLY);
                    seekBar.getThumb().setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_IN);
                    filter.setImageResource(R.drawable.camera_btn_filter_w);
                    gallery.setImageResource(R.drawable.camera_btn_album_w);
                    video_or_photo.setImageResource(R.drawable.camera_btn_video);
                    video_photo_text.setTextColor(getResources().getColor(R.color.color_white));
                    myGLSurfaceViewParent.setLayoutParams(param_full);
                    aspect_ratio.setImageResource(R.drawable.camera_btn_ratio_full);
                    fragment_aspect.setVisibility(View.GONE);
                }
                if(position==1)
                {
                    if(timer==0)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer);
                    }
                    else if(timer==3)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_3s);
                    }
                    else if(timer==5)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_5s);
                    }
                    else if(timer==10)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_10s);
                    }
                    else
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_15s);
                    }
                    if(flash==0)
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash);
                    }
                    else if(flash==1)
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash_auto);
                    }
                    else
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash_torch);
                    }
                    seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.MULTIPLY);
                    seekBar.getThumb().setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_IN);
                    filter.setImageResource(R.drawable.camera_btn_filter_b);
                    gallery.setImageResource(R.drawable.camera_btn_album_b);
                    if(is_video_or_photo==0)
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_video_b);
                    }
                    else
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_camera_b);
                    }
                    camera_switch.setImageResource(R.drawable.camera_btn_switch_w);
                    video_photo_text.setTextColor(getResources().getColor(R.color.black_new));
                    myGLSurfaceViewParent.setLayoutParams(param_3_4);
                    aspect_ratio.setImageResource(R.drawable.camera_btn_ratio_3x4);
                    fragment_aspect.setVisibility(View.GONE);
                }
                if(position==2)
                {
                    if(timer==0)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_b);
                    }
                    else if(timer==3)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_3s_b);
                    }
                    else if(timer==5)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_5s_b);
                    }
                    else if(timer==10)
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_10s_b);
                    }
                    else
                    {
                        timer_button.setImageResource(R.drawable.camera_btn_timer_15s_b);
                    }
                    if(flash==0)
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash_b);
                    }
                    else if(flash==1)
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash_auto_b);
                    }
                    else
                    {
                        flash_button.setImageResource(R.drawable.camera_btn_flash_torch_b);
                    }

                    seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.bg_color), PorterDuff.Mode.MULTIPLY);
                    seekBar.getThumb().setColorFilter(getResources().getColor(R.color.bg_color), PorterDuff.Mode.MULTIPLY);

                    filter.setImageResource(R.drawable.camera_btn_filter_b);
                    gallery.setImageResource(R.drawable.camera_btn_album_b);
                    if(is_video_or_photo==0)
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_video_b);
                    }
                    else
                    {
                        video_or_photo.setImageResource(R.drawable.camera_btn_camera_b);
                    }
                    camera_switch.setImageResource(R.drawable.camera_btn_switch_b);
                    video_photo_text.setTextColor(getResources().getColor(R.color.black_new));

                    ResizeAnimation animation=new ResizeAnimation(myGLSurfaceViewParent,width,(width/3)*4,width,width);
                    myGLSurfaceViewParent.setAnimation(animation);
                    myGLSurfaceViewParent.startAnimation(animation);

                    myGLSurfaceViewParent.setLayoutParams(param_1_1);
                    aspect_ratio.setImageResource(R.drawable.camera_btn_ratio_1x1);
                    fragment_aspect.setVisibility(View.GONE);
                }

                buffer_frame.setVisibility(View.VISIBLE);
                executeAsyncTask_aspect_change(position);
            }
            @Override
            public void onLongClick(View view, int position)
            {
                Toast.makeText(CameraDemoActivity.this, "Long press on position :"+position, Toast.LENGTH_LONG).show();
            }
        }));
        lastVideoPathFileName = FileUtil.getPathInPackage(CameraDemoActivity.this, true) + "/lastVideoPath.txt";
        Button takePicBtn = (Button) findViewById(R.id.takePicBtn);
        Button takeShotBtn = (Button) findViewById(R.id.takeShotBtn);
        Button recordBtn = (Button) findViewById(R.id.recordBtn);
        mCameraView = (CameraRecordGLSurfaceView) findViewById(R.id.myGLSurfaceView);
        param_full=new RelativeLayout.LayoutParams(width,height);
        param_3_4=new RelativeLayout.LayoutParams(width,((width/3)*4));
        param_1_1=new RelativeLayout.LayoutParams(width,width);
        param_1_1.setMargins(0,height*10/100,0,0);
        myGLSurfaceViewParent.setLayoutParams(param_full);
        mCameraView.presetCameraForward(false);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setVisibility(View.INVISIBLE);

        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Taking Picture..."+mCurrentConfig);

                mCameraView.takePicture(new CameraRecordGLSurfaceView.TakePictureCallback() {
                    @Override
                    public void takePictureOK(Bitmap bmp) {
                        if (bmp != null) {
                            String s = ImageUtil.saveBitmap(bmp);
                            bmp.recycle();
                            showText("Take picture success!");
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + s)));
                        } else
                            showText("Take picture failed!");
                    }
                }, null, mCurrentConfig, 1.0f, true);
            }
        });

        takeShotBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showText("Taking Shot...");
                mCameraView.takeShot(new CameraRecordGLSurfaceView.TakePictureCallback() {
                    @Override
                    public void takePictureOK(Bitmap bmp) {
                        if (bmp != null) {
                            String s = ImageUtil.saveBitmap(bmp);
                            bmp.recycle();
                            showText("Take Shot success!");
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + s)));
                        } else
                            showText("Take Shot failed!");
                    }
                });
            }
        });

        recordBtn.setOnClickListener(new RecordListener());

        LinearLayout layout = (LinearLayout) findViewById(R.id.menuLinearLayout);

        for (int i = 0; i != MainActivity.EFFECT_CONFIGS.length; ++i) {
            MyButtons button = new MyButtons(this, MainActivity.EFFECT_CONFIGS[i]);
            button.setClickable(true);
            button.setAllCaps(false);
            if (i == 0)
                button.setText("None");
            else
                button.setText("Filter" + i);

            button.setOnClickListener(mFilterSwitchListener);
            layout.addView(button);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float intensity = progress / 100.0f;
                mCameraView.setFilterIntensity(intensity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mCurrentInstance = this;

        Button switchBtn = (Button) findViewById(R.id.switchCameraBtn);
        switchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCameraView.switchCamera();
            }
        });

        Button flashBtn = (Button) findViewById(R.id.flashBtn);
        flashBtn.setOnClickListener(new View.OnClickListener() {
            int flashIndex = 0;
            String[] flashModes = {
                    Camera.Parameters.FLASH_MODE_AUTO,
                    Camera.Parameters.FLASH_MODE_ON,
                    Camera.Parameters.FLASH_MODE_OFF,
                    Camera.Parameters.FLASH_MODE_TORCH,
                    Camera.Parameters.FLASH_MODE_RED_EYE,
            };

            @Override
            public void onClick(View v) {
                mCameraView.setFlashLightMode(flashModes[flashIndex]);
                ++flashIndex;
                flashIndex %= flashModes.length;
            }
        });

        //Recording video size
        mCameraView.presetRecordingSize(480, 640);
//        mCameraView.presetRecordingSize(720, 1280);
        //Taking picture size.
        mCameraView.setPictureSize(2048, 2048, true); // > 4MP
        mCameraView.setZOrderOnTop(false);
        mCameraView.setZOrderMediaOverlay(true);

        mCameraView.setOnCreateCallback(new CameraRecordGLSurfaceView.OnCreateCallback() {
            @Override
            public void createOver(boolean z) {
                Log.i(LOG_TAG, "view onCreate");
            }
        });

        Button pauseBtn = (Button) findViewById(R.id.pauseBtn);
        Button resumeBtn = (Button) findViewById(R.id.resumeBtn);

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraView.stopPreview();
            }
        });

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraView.resumePreview();
            }
        });

        Button fitViewBtn = (Button) findViewById(R.id.fitViewBtn);
        fitViewBtn.setOnClickListener(new View.OnClickListener() {
            boolean shouldFit = false;
            @Override
            public void onClick(View v) {
                shouldFit = !shouldFit;
                mCameraView.setFitFullView(shouldFit);
            }
        });

        mCameraView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.i(LOG_TAG, String.format("Tap to focus: %g, %g", event.getX(), event.getY()));
                        final float focusX = event.getX() / mCameraView.getWidth();
                        final float focusY = event.getY() / mCameraView.getHeight();

                        mCameraView.focusAtPoint(focusX, focusY, new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                                if (success) {
                                    Log.e(LOG_TAG, String.format("Focus OK, pos: %g, %g", focusX, focusY));
                                } else {
                                    Log.e(LOG_TAG, String.format("Focus failed, pos: %g, %g", focusX, focusY));
                                    mCameraView.cameraInstance().setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                                }
                            }
                        });
                    }
                    break;
                    default:
                        break;
                }
                return true;
            }
        });



        if (this.mCameraView != null)
        {
            this.mCameraView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

                class C03802 implements Runnable {
                    C03802() {
                    }

                    public void run() {
                        mFilterListView.smoothScrollToPosition(filterPos);
                        mAdapter.setIntPos(filterPos);
                    }
                }
                class C03791 implements Runnable {
                    C03791() {
                    }

                    public void run() {
                        mFilterListView.smoothScrollToPosition(filterPos);
                        mAdapter.setIntPos(filterPos);
                    }
                }
                public void onSwipeRight() {
                    try {
                        mCameraView.setFilterIntensity(0.8f);
                        seekBar.setProgress(80);

                        if (CameraDemoActivity.filterPos <= 0) {
                            CameraDemoActivity.filterPos = MainActivity.EFFECT_CONFIGS.length - 1;
                        } else if (CameraDemoActivity.filterPos >= MainActivity.EFFECT_CONFIGS.length) {
                            CameraDemoActivity.filterPos = 0;
                        } else {
                            CameraDemoActivity.filterPos--;
                        }
                        if (CameraDemoActivity.this.foucs) {
                            CameraDemoActivity.this.mCameraView.setFilterWithConfig(MainActivity.EFFECT_CONFIGS[CameraDemoActivity.filterPos]);
                        } else {
                            CameraDemoActivity.this.mCameraView.setFilterWithConfig(MainActivity.camFilNormal[CameraDemoActivity.filterPos]);
                        }
                        if (CameraDemoActivity.filterPos == 0) {
                            seekBar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            seekBar.setVisibility(View.VISIBLE);
                        }
                        CameraDemoActivity.this.showText1(CameraDemoActivity.filterPos, null);
                        CameraDemoActivity.this.mAdapter.notifyDataSetChanged();
                        CameraDemoActivity.this.mFilterListView.post(new C03791());
                        mCurrentConfig=MainActivity.EFFECT_CONFIGS[CameraDemoActivity.filterPos];

                    } catch (Exception e) {
                    }
                }
                public void onSwipeLeft() {
                    try {
                        CameraDemoActivity.this.mCameraView.setFilterIntensity(0.8f);
                        seekBar.setProgress(80);
                        if (CameraDemoActivity.filterPos < 0) {
                            CameraDemoActivity.filterPos = 0;
                        } else if (CameraDemoActivity.filterPos >= MainActivity.EFFECT_CONFIGS.length - 1) {
                            CameraDemoActivity.filterPos = 0;
                        } else {
                            CameraDemoActivity.filterPos++;
                        }
                        if (CameraDemoActivity.this.foucs) {
                            CameraDemoActivity.this.mCameraView.setFilterWithConfig(MainActivity.EFFECT_CONFIGS[CameraDemoActivity.filterPos]);
                        } else {
                            CameraDemoActivity.this.mCameraView.setFilterWithConfig(MainActivity.camFilNormal[CameraDemoActivity.filterPos]);
                        }
                        if (CameraDemoActivity.filterPos == 0) {
                            seekBar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            seekBar.setVisibility(View.VISIBLE);
                        }

                        CameraDemoActivity.this.showText1(CameraDemoActivity.filterPos, null);
                        CameraDemoActivity.this.mAdapter.notifyDataSetChanged();
                        CameraDemoActivity.this.mFilterListView.post(new C03802());
                        mCurrentConfig=MainActivity.EFFECT_CONFIGS[CameraDemoActivity.filterPos];
                    } catch (Exception e) {
                    }
                }
                public void onSwipeTop() { }
                public void onSwipeBottom() { }
                public void onTouch(MotionEvent event) {}
                public void onTouchDouble(ScaleGestureDetector detector) { }
            });
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.mFilterListView.setLayoutManager(linearLayoutManager);
        this.mAdapter = new FilterAdapter(this, MainActivity.imageFilter2, 0,left_h*46/100);
        this.mFilterListView.setAdapter(this.mAdapter);
        this.mFilterListView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View view, final int position) {
//                freeMemory();
                filterPos = position;
                mCurrentConfig=MainActivity.EFFECT_CONFIGS[position];
                showText1(filterPos, null);
                try {
                    if (filterPos != 0) {
                        seekBar.setVisibility(View.VISIBLE);
                        seekBar.setProgress(80);
                    } else {
                        seekBar.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                }
                try {
                    mCameraView.post(new Runnable() {
                        public void run() {
                            try {
                                if (foucs) {
                                    mCameraView.setFilterWithConfig(MainActivity.EFFECT_CONFIGS[position]);
                                } else {
                                    mCameraView.setFilterWithConfig(MainActivity.camFilNormal[position]);
                                }
                                mCameraView.setFilterIntensity(0.8f);
                            } catch (Exception e) {
                            }
                        }
                    });
                } catch (Exception e2) {
                }
            }
        }));

        mCameraView.setPictureSize(1920,1440, false);
        mCameraView.setFitFullView(true);
    }

    private View.OnClickListener mFilterSwitchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            MyButtons btn = (MyButtons) v;
            mCameraView.setFilterWithConfig(btn.filterConfig);
            mCurrentConfig = btn.filterConfig;
        }
    };

    int customFilterIndex = 0;
    void showText1(final int msg, final String msg1) {
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(200);
        fadeIn.setStartOffset(100);
        final Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1000);
        fadeOut.setStartOffset(1000);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation arg0) {
                textViewFilter.startAnimation(fadeOut);
            }

            public void onAnimationRepeat(Animation arg0) {
            }

            public void onAnimationStart(Animation arg0) {
                textViewFilter.setVisibility(View.VISIBLE);
                if (msg1 == null) {
                    textViewFilter.setText(FilterAdapter.filterName1(msg));
                } else {
                    textViewFilter.setText(msg1);
                }
            }
        });
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation arg0) {
                textViewFilter.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation arg0) {
            }

            public void onAnimationStart(Animation arg0) {
            }
        });
        this.textViewFilter.startAnimation(fadeIn);
    }
    public void customFilterClicked(View view) {
        ++customFilterIndex;
        customFilterIndex %= CGENativeLibrary.cgeGetCustomFilterNum();
        mCameraView.queueEvent(new Runnable() {
            @Override
            public void run() {
                long customFilter = CGENativeLibrary.cgeCreateCustomNativeFilter(customFilterIndex, 1.0f, true);
                mCameraView.getRecorder().setNativeFilter(customFilter);
            }
        });
    }
    public void dynamicFilterClicked(View view) {

        mCameraView.setFilterWithConfig("#unpack @dynamic mf 10 0");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_demo, menu);
        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onPause() {
        super.onPause();
        try {
            video_click_action.setImageResource(R.drawable.video_btn_shutter_dot);
            if(mCameraView.isRecording())
            {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + recordFilename)));
                mCameraView.endRecording(new CameraRecordGLSurfaceView.EndRecordingCallback() {
                    @Override
                    public void endRecordingOK() {
                        Log.i(LOG_TAG, "End recording OK");
                        isValid = true;
                    }
                });
                recordFilename="";
            }
        }
        catch (Exception e){}
        CameraInstance.getInstance().stopCamera();
        Log.i(LOG_TAG, "activity onPause...");
        mCameraView.release(null);
        mCameraView.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        mCameraView.onResume();

        try {
            if (filterPos != 0) {
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setProgress(80);
            } else {
                seekBar.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
        try {
            mCameraView.post(new Runnable() {
                public void run() {
                    try {
                        if (foucs) {
                            mCameraView.setFilterWithConfig(MainActivity.EFFECT_CONFIGS[filterPos]);
                        } else {
                            mCameraView.setFilterWithConfig(MainActivity.camFilNormal[filterPos]);
                        }
                        mCameraView.setFilterIntensity(0.8f);
                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e2) {
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
