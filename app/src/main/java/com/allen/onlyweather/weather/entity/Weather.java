package com.allen.onlyweather.weather.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Allen.
 */

public class Weather {

    public String status;

    public AQI aqi;
    public Basic basic;

    @SerializedName("daily_forecast")
    public List<DailyForecast> dailyForecastList;
    @SerializedName("hourly_forecast")
    public List<HourlyForecast> hourlyForecastList;
    public Now now;
    public Suggestion suggestion;

}
