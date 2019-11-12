package org.wysaid.myUtils;

import android.content.Context;
import android.widget.Toast;
import java.lang.ref.WeakReference;

public class MsgUtil {
    static WeakReference<Context> mContext;
    static Toast mToast;

    public static Toast getCurrentToast() {
        return mToast;
    }

    public static void setCurrentToast(Context context, Toast toast) {
        mContext = new WeakReference(context);
        mToast = toast;
    }

    public static void clear() {
        mContext = null;
        mToast = null;
    }

    public static void toastMsg(Context context, String msg) {
        toastMsg(context, msg, 1);
    }

    public static void toastMsg(Context context, String msg, int duration) {
        if (mContext == null || mContext.get() != context) {
            if (context == null) {
                mContext = null;
                return;
            }
            mContext = new WeakReference(context);
            mToast = Toast.makeText((Context) mContext.get(), "", duration);
            mToast.setDuration(duration);
        }
        if (mContext.get() != null && mToast != null) {
            mToast.setText(msg);
            mToast.show();
        }
    }

    public static boolean isDisplaying() {
        return (mToast == null || mToast.getView() == null || mToast.getView().getWindowVisibility() != 0) ? false : true;
    }
}
