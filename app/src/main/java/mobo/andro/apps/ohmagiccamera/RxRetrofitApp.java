package mobo.andro.apps.ohmagiccamera;

import android.app.Application;

public class RxRetrofitApp {
    private static Application application;
    private static boolean debug;


    public static void init(Application app) {
        init(app, true);
    }

    public static void init(Application app, boolean debug) {
        setApplication(app);
        setDebug(debug);
        initDb(app);
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }


    private static void initDb(Application app) {
//        AbAppUtil.importDatabase(app, "lib", R.raw.lib);
    }
}
