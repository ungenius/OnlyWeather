package com.allen.onlyweather;

import android.app.Application;
import android.view.ScaleGestureDetector;

import com.allen.onlyweather.db.DBManage;
import com.allen.onlyweather.scheduler.TaskScheduler;
import com.allen.onlyweather.utils.LogHelper;
import com.google.gson.Gson;

/**
 * Created by Allen.
 */

public class MyApplication extends Application {

    private static Application sApplication;
    private static Gson sGson = new Gson();



    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        TaskScheduler.init();
        DBManage.getInstance().copyCitiesToDb();

        if (BuildConfig.DEBUG) {
            LogHelper.debugInit();
        } else {
            LogHelper.releaseInit();
        }
    }

    public static Application getContext() {
        return sApplication;
    }

    public static Gson getGson() {
        return sGson;

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ModelManager.unSubscribeAll();
    }
}
