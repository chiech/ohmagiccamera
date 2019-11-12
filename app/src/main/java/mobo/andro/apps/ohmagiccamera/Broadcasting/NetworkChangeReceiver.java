package mobo.andro.apps.ohmagiccamera.Broadcasting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        Log.e("networkchange","on Recieve");
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo wifi = connMgr.getNetworkInfo(1);
        NetworkInfo mobile = connMgr.getNetworkInfo(0);
        if (wifi.isAvailable() || mobile.isAvailable())
        {
            context.startService(new Intent(context, NotifyService.class));
        }
    }
}