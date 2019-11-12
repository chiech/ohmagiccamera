package mobo.andro.apps.ohmagiccamera;

import android.os.Bundle;

//import com.facebook.appevents.AppEventsConstants;
//import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * firebase埋点统计 和facebook 事件统计
 * firebase utils
 *
 */
public class DataReportUtils {
    public static final String EVENT_NAME_ACTIVATED_APP = "fb_mobile_activate_app";
    public static final String EVENT_NAME_CONTACT = "fb_mobile_contact";

    private static volatile DataReportUtils intance;
    private static FirebaseAnalytics mFirebaseAnalytics;

    /**
     * Facebook 统计
     */
//    private static AppEventsLogger mLogger;

    public static DataReportUtils getInstance() {
        if (intance == null) {
            intance = new DataReportUtils();
        }
        return intance;
    }

    private DataReportUtils() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(RxRetrofitApp.getApplication());
//        mLogger = AppEventsLogger.newLogger(RxRetrofitApp.getApplication());
    }

    /**
     * 埋点
     *
     * @param key
     * @param value
     */
    public void report(String key, Bundle value) {
        if (value == null) {
            value = new Bundle();
        }
        value.putInt(key, 1);
        mFirebaseAnalytics.logEvent(key, value);
//        mLogger.logEvent(key, value);
    }

    /**
     * 埋点
     *
     * @param key
     */
    public void report(String key) {
        report(key, null);
    }

    public void setUserId(String userId) {
        if (BuildConfig.DEBUG) return;
        mFirebaseAnalytics.setUserId(userId);
//        AppEventsLogger.setUserID(userId);
    }

    /**
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logActivatedAppEvent () {
//        mLogger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP);
    }

    /**
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
//    public void logContactEvent () {
//        mLogger.logEvent(EVENT_NAME_CONTACT);
//    }
}