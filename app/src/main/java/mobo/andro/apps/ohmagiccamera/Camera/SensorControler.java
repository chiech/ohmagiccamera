package mobo.andro.apps.ohmagiccamera.Camera;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Calendar;

public class SensorControler implements SensorEventListener {
    public static final int DELEY_DURATION = 500;
    public static final int STATUS_MOVE = 2;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_STATIC = 1;
    public static final String TAG = "SensorControler";
    private static SensorControler mInstance;
    private int STATUE = 0;
    boolean canFocus = false;
    boolean canFocusIn = false;
    private int foucsing = 1;
    boolean isFocusing = false;
    private long lastStaticStamp = 0;
    Calendar mCalendar;
    private CameraFocusListener mCameraFocusListener;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private int mX;
    private int mY;
    private int mZ;

    public interface CameraFocusListener {
        void onFocus();
    }

    private SensorControler(Context context) {
        this.mSensorManager = (SensorManager) context.getSystemService("sensor");
        this.mSensor = this.mSensorManager.getDefaultSensor(1);
    }

    public static SensorControler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SensorControler(context);
        }
        return mInstance;
    }

    public void setCameraFocusListener(CameraFocusListener mCameraFocusListener) {
        this.mCameraFocusListener = mCameraFocusListener;
    }

    public void onStart() {
        restParams();
        this.canFocus = true;
        this.mSensorManager.registerListener(this, this.mSensor, 3);
    }

    public void onStop() {
        this.mSensorManager.unregisterListener(this, this.mSensor);
        this.canFocus = false;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor != null) {
            if (this.isFocusing) {
                restParams();
            } else if (event.sensor.getType() == 1) {
                int x = (int) event.values[0];
                int y = (int) event.values[1];
                int z = (int) event.values[2];
                this.mCalendar = Calendar.getInstance();
                long stamp = this.mCalendar.getTimeInMillis();
                int second = this.mCalendar.get(13);
                if (this.STATUE != 0) {
                    int px = Math.abs(this.mX - x);
                    int py = Math.abs(this.mY - y);
                    int pz = Math.abs(this.mZ - z);
                    if (Math.sqrt((double) (((px * px) + (py * py)) + (pz * pz))) > 1.4d) {
                        this.STATUE = 2;
                    } else {
                        if (this.STATUE == 2) {
                            this.lastStaticStamp = stamp;
                            this.canFocusIn = true;
                        }
                        if (this.canFocusIn && stamp - this.lastStaticStamp > 500 && !this.isFocusing) {
                            this.canFocusIn = false;
                            if (this.mCameraFocusListener != null) {
                                this.mCameraFocusListener.onFocus();
                            }
                        }
                        this.STATUE = 1;
                    }
                } else {
                    this.lastStaticStamp = stamp;
                    this.STATUE = 1;
                }
                this.mX = x;
                this.mY = y;
                this.mZ = z;
            }
        }
    }

    private void restParams() {
        this.STATUE = 0;
        this.canFocusIn = false;
        this.mX = 0;
        this.mY = 0;
        this.mZ = 0;
    }

    public boolean isFocusLocked() {
        if (!this.canFocus || this.foucsing > 0) {
            return false;
        }
        return true;
    }

    public void lockFocus() {
        this.isFocusing = true;
        this.foucsing--;
        Log.i(TAG, "lockFocus");
    }

    public void unlockFocus() {
        this.isFocusing = false;
        this.foucsing++;
        Log.i(TAG, "unlockFocus");
    }

    public void restFoucs() {
        this.foucsing = 1;
    }
}
