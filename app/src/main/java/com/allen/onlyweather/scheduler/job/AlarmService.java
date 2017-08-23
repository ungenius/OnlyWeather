package com.allen.onlyweather.scheduler.job;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.model.WeatherModel;
import com.silencedut.router.Router;

/**
 * Created by Allen.
 */

public class AlarmService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.instance().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ModelManager.getModel(WeatherModel.class).updateDefaultWeather();
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Router.instance().unregister(this);
    }
}
