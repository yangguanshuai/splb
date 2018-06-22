package com.mylibrary;

/**
 * 全局app
 */

public class MyApplication {
    private static android.app.Application application;
    private static boolean debug ;


    public static void init(android.app.Application app){
        setApplication(app);
        setDebug(true);
    }

    public static void init(android.app.Application app, boolean debug){
        setApplication(app);
        setDebug(debug);
    }

    public static android.app.Application getApplication() {
        return application;
    }

    private static void setApplication(android.app.Application application) {
        MyApplication.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        MyApplication.debug = debug;
    }
}
