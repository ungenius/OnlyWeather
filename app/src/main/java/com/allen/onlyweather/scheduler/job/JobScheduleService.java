package com.allen.onlyweather.scheduler.job;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.model.WeatherModel;
import com.silencedut.router.Router;

/**
 * Created by Allen.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobScheduleService extends JobService {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.instance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Router.instance().unregister(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        ModelManager.getModel(WeatherModel.class).updateDefaultWeather();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }
}
