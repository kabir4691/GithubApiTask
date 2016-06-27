package com.kabir.githubapitask.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kabir on 27-06-2016.
 */
public class AppSingleton extends Application {

    static AppSingleton singleton = null;

    public static synchronized AppSingleton getInstance() {
        return singleton;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        singleton = null;
    }
}