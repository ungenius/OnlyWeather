package com.allen.onlyweather.weather.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.R;
import com.allen.onlyweather.city.ui.SearchActivity;
import com.allen.onlyweather.common.BasePresenter;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.model.CityModel;
import com.allen.onlyweather.model.WeatherModel;
import com.allen.onlyweather.model.callbacks.ModelCallback;
import com.allen.onlyweather.scheduler.TaskScheduler;
import com.allen.onlyweather.utils.Check;
import com.allen.onlyweather.utils.LogHelper;
import com.allen.onlyweather.utils.PreferencesUtil;
import com.allen.onlyweather.weather.entity.Basic;
import com.allen.onlyweather.weather.entity.DailyForecast;
import com.allen.onlyweather.weather.entity.HourlyForecast;
import com.allen.onlyweather.weather.entity.Now;
import com.allen.onlyweather.weather.entity.Suggestion;
import com.allen.onlyweather.weather.entity.Weather;
import com.allen.onlyweather.weather.ui.adapter.AqiData;
import com.allen.onlyweather.weather.ui.adapter.DailyForecastData;
import com.allen.onlyweather.weather.ui.adapter.HourlyForecastData;
import com.allen.onlyweather.weather.ui.adapter.SuggestionData;
import com.silencedut.router.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen.
 */

public class WeatherPresenter extends BasePresenter<MainView> implements ModelCallback.WeatherResult, ModelCallback.LocationResult{

    private MainView mMainView;
    private WeatherModel mWeatherModel;
    private CityModel mCityModel;



    public WeatherPresenter(MainView presentView) {
        super(presentView);
        Router.instance().register(this);
        mMainView = presentView;
        mWeatherModel = ModelManager.getModel(WeatherModel.class);
        mCityModel = ModelManager.getModel(CityModel.class);
        mCityModel.startLocation();
        TaskScheduler.execute(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                    for (String permission : mPermissionList) {
                        if (ActivityCompat.checkSelfPermission(mMainView.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) mMainView.getContext(), new String[]{permission}, 1);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onLocationComplete(String cityId, boolean success) {
        if (!success && mCityModel.noDefaultCity()) {
            Toast.makeText(getContext(), R.string.add_city_hand_mode, Toast.LENGTH_SHORT).show();
            SearchActivity.navigationActivity(mMainView.getContext());
            return;
        }
        if (mCityModel.noDefaultCity() || !mCityModel.getDefaultId().equals(cityId)) {
            getWeather(cityId);
        }
    }

    public void getDefaultWeather() {
        Weather weather = mWeatherModel.getCachedWeather();
        if (!Check.isNull(weather)) {
            onWeather(weather);
        }
        updateDefaultWeather();
    }

    public void updateDefaultWeather() {
        String defaultCity = mCityModel.getDefaultId();
        getWeather(defaultCity);

    }

    private void getWeather(String cityId) {
        mMainView.onRefreshing(true);
        mWeatherModel.updateWeather(cityId);
    }


    @Override
    public void onWeather(Weather weather) {
        if (weather == null) {
            mMainView.onRefreshing(false);
        } else {
            Basic basicData = weather.basic;
            AqiData aqiData = new AqiData(weather.aqi, weather.suggestion);
            Now nowData = weather.now;
            boolean isLocationCity = weather.basic.id.equals(PreferencesUtil.get(Constants.LOCATION, Constants.DEFAULT_STR));
            SuggestionData suggestionData = new SuggestionData(weather.suggestion);
            List<HourlyForecastData> hourlyForecastDataList = new ArrayList<>();
            for (HourlyForecast hourlyForecast : weather.hourlyForecastList) {
                hourlyForecastDataList.add(new HourlyForecastData(hourlyForecast));
            }

            List<DailyForecastData> dailyForecastDatas = new ArrayList<>();
            for (DailyForecast dailyForecast : weather.dailyForecastList) {
                dailyForecastDatas.add(new DailyForecastData(dailyForecast));
            }

            mMainView.onBasicInfo(basicData, nowData, hourlyForecastDataList, isLocationCity);
            mMainView.onWeatherInfo(aqiData, dailyForecastDatas, suggestionData);
        }
    }


}
