package com.allen.onlyweather.city.ui.presenter;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.city.adapter.FollowedCityData;
import com.allen.onlyweather.city.adapter.FollowedCityHolder;
import com.allen.onlyweather.common.BasePresenter;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.model.CityModel;
import com.allen.onlyweather.model.callbacks.ModelCallback;
import com.allen.onlyweather.network.AppHttpClient;
import com.allen.onlyweather.network.api.WeatherApi;
import com.allen.onlyweather.scheduler.TaskScheduler;
import com.allen.onlyweather.utils.PreferencesUtil;
import com.allen.onlyweather.weather.entity.HeWeather;
import com.allen.onlyweather.weather.entity.Weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Allen.
 */

public class FollowedCityPresenter extends BasePresenter<FollowedCityView> implements ModelCallback.WeatherResult {

    private WeatherApi mWeatherApiService;
    private CityModel mCityModel;


    public FollowedCityPresenter(FollowedCityView presentView) {
        super(presentView);
        mWeatherApiService = AppHttpClient.getInstance().getService(WeatherApi.class);
        mCityModel = ModelManager.getModel(CityModel.class);
    }

    public int followedCitiesNumber() {
        Set<String> defaultFollowed = new HashSet<>();
        defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, defaultFollowed);
        return defaultFollowed.size();
    }

    public void getFollowedWeather() {
        final List<FollowedCityData> followedCityDatas = new ArrayList<>();

        TaskScheduler.execute(new Runnable() {
            @Override
            public void run() {
                int length = 0;
                final Set<String> defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, new HashSet<String>());
                for (String cityId : defaultFollowed) {
                    Call<HeWeather> heWeatherCall = mWeatherApiService.getWeather(cityId, Constants.WEATHER_KEY);
                    try {
                        Response<HeWeather> response = heWeatherCall.execute();
                        HeWeather heWeather = response.body();
                        if (response.isSuccessful() && heWeather != null) {
                            parseHeWeather(heWeather, cityId, length, followedCityDatas);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    length++;
                }
                if (followedCityDatas.size() == 0) {
                    return;
                }
                TaskScheduler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mPresentView.onAllFollowedCities(followedCityDatas);
                    }
                });
            }
        });

    }

    private List<FollowedCityData> parseHeWeather(HeWeather heWeather, String cityId, int length, List<FollowedCityData> followedCityDatas) {
        if (heWeather != null) {
            if (mCityModel.getLocationCityId().equals(cityId)) {
                followedCityDatas.add(0, new FollowedCityData(heWeather.weatherList.get(0), FollowedCityHolder.BLUR_IMAGE[length % FollowedCityHolder.BLUR_IMAGE.length]));
            } else {
                followedCityDatas.add(new FollowedCityData(heWeather.weatherList.get(0), FollowedCityHolder.BLUR_IMAGE[length % FollowedCityHolder.BLUR_IMAGE.length]));
            }
        }
        return followedCityDatas;
    }

    @Override
    public void onWeather(Weather weather) {
        if (weather == null) {
            return;
        }
        String cityId = weather.basic.id;
        Set<String> defaultFollowed = new HashSet<>();

        defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, defaultFollowed);
        boolean cityExisted = defaultFollowed.contains(cityId);
        if (!cityExisted) {
            defaultFollowed.add(cityId);
            PreferencesUtil.put(Constants.FOLLOWED_CITIES, defaultFollowed);
            FollowedCityData followedCityData = new FollowedCityData(weather, FollowedCityHolder.BLUR_IMAGE[followedCitiesNumber() % FollowedCityHolder.BLUR_IMAGE.length]);
            mPresentView.onFollowedCity(followedCityData);
        } else {
            mPresentView.onFollowedCity(null);
        }
    }
}
