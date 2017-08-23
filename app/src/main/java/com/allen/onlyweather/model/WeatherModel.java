package com.allen.onlyweather.model;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.MyApplication;
import com.allen.onlyweather.common.BaseModel;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.model.callbacks.ModelCallback;
import com.allen.onlyweather.network.AppHttpClient;
import com.allen.onlyweather.network.api.WeatherApi;
import com.allen.onlyweather.utils.LogHelper;
import com.allen.onlyweather.utils.PreferencesUtil;
import com.allen.onlyweather.weather.entity.HeWeather;
import com.allen.onlyweather.weather.entity.Weather;
import com.allen.onlyweather.weather.presenter.MainView;
import com.google.gson.Gson;
import com.silencedut.router.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Allen.
 */

public class WeatherModel extends BaseModel {

    private Weather mCachedWeather = null;
    private WeatherApi mWeatherApi;

    public void onCreate() {
        mWeatherApi = AppHttpClient.getInstance().getService(WeatherApi.class);
        mCachedWeather = initWeather();

    }

    public Weather getCachedWeather() {
        return mCachedWeather;
    }

    private Weather initWeather() {
        Weather weather = null;
        String mainPageCache = PreferencesUtil.get(Constants.MAIN_PAGE_WEATHER, Constants.DEFAULT_STR);
        if (!mainPageCache.equals(Constants.DEFAULT_STR)) {
            weather = MyApplication.getGson().fromJson(mainPageCache, Weather.class);
        }
        return weather;
    }

    public void updateDefaultWeather() {

        String defaultCity = ModelManager.getModel(CityModel.class).getDefaultId();
        updateWeather(defaultCity);
    }


    public void updateWeather(final String cityId) {
        Router.instance().getReceiver(MainView.class).onRefreshing(true);
        Call<HeWeather> weatherCall = mWeatherApi.getWeather(cityId, Constants.WEATHER_KEY);
        weatherCall.enqueue(new Callback<HeWeather>() {
            @Override
            public void onResponse(Call<HeWeather> call, Response<HeWeather> response) {
                try {
                    HeWeather heWeather = response.body();
                    Weather weather = heWeather.weatherList.get(0);
                    if (response.isSuccessful() && weather != null) {
                        String cache = MyApplication.getGson().toJson(weather);
                        PreferencesUtil.put(Constants.MAIN_PAGE_WEATHER, cache);
                        mCachedWeather = weather;
                        onWeather(weather);
                        ModelManager.getModel(CityModel.class).setDefaultId(cityId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HeWeather> call, Throwable t) {
                LogHelper.e(t, call.toString() + t.getMessage());
                onWeather(null);
            }
        });
    }


    private void onWeather(Weather weather) {
        Router.instance().getReceiver(ModelCallback.WeatherResult.class).onWeather(weather);
    }
}
