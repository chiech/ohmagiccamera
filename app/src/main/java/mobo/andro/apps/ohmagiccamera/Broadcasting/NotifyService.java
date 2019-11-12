package mobo.andro.apps.ohmagiccamera.Broadcasting;

import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;

import mobo.andro.apps.ohmagiccamera.R;
import mobo.andro.apps.ohmagiccamera.SplashActivity;

public class NotifyService extends Service
{
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    public void onCreate() {
        super.onCreate();
        Builder notification = new Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("b886 Beauty Camera Magic Effect").setContentText("click to save moments and make photo collage.").setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class).setFlags(67108864), 0));
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (VERSION.SDK_INT < 16) {
            notificationManager.notify(1, notification.getNotification());
        } else {
            notificationManager.notify(1, notification.build());
        }
    }
    public void onDestroy() {
        super.onDestroy();
    }
}