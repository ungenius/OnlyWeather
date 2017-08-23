package com.allen.onlyweather.weather.presenter;

import com.allen.onlyweather.common.BaseView;
import com.allen.onlyweather.weather.entity.Basic;
import com.allen.onlyweather.weather.entity.Now;
import com.allen.onlyweather.weather.ui.adapter.AqiData;
import com.allen.onlyweather.weather.ui.adapter.DailyForecastData;
import com.allen.onlyweather.weather.ui.adapter.HourlyForecastData;
import com.allen.onlyweather.weather.ui.adapter.SuggestionData;

import java.util.List;

/**
 * Created by Allen.
 */

public interface MainView extends BaseView {
//    void onWeatherInfo(Weather weather);
    void onRefreshing(boolean refreshing);

    void onBasicInfo(Basic basicData, Now now, List<HourlyForecastData> hourlyForecastDatas, boolean isLocationCity);

    void onWeatherInfo(AqiData aqiData, List<DailyForecastData> dailyForecastDatas, SuggestionData suggestionData);



}
