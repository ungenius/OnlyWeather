package com.allen.onlyweather.common;

import android.content.Intent;

import com.allen.onlyweather.R;
import com.allen.onlyweather.utils.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen.
 */

public class Constants {

    public static final String MAIN_PAGE_WEATHER = "MAIN_PAGE_WEATHER";

    public static final String DEFAULT_STR = "$";
    public static final String WEATHER_KEY = "1f5b07fbdd174936ad0f4d600440a3ed";
    public static final String DEFAULT_CITY = "DEFAULT_CITY";
    public static final String FOLLOWED_CITIES = "FOLLOWED_CITIES";
    public static final String LOCATION = "LOCATION";
    public static final String CITYS_TIPS_SHOW = "CITYS_TIPS_SHOW";

    public static final String DEFAULT_CITY_ID = "CN101110101";//西安CityId
    public static final String POLLING_TIME = "POLLING_TIME";

    private static final long[] SCHEDULES = {30 * 60, 60 * 60, 3 * 60 * 60, 0};

    private static Map<String, Integer> sWeatherIcons = new HashMap<>();
    private static final String[] SUNNY = {"晴", "多云"};
    private static final String[] WEATHERS = {"阴", "晴", "多云", "大雨", "雨", "雪", "风", "雾霾", "雨夹雪"};
    private static final int[] ICONS_ID = {R.mipmap.weather_clouds, R.mipmap.weather_sunny, R.mipmap.weather_few_clouds, R.mipmap.weather_big_rain, R.mipmap.weather_rain, R.mipmap.weather_snow, R.mipmap.weather_wind, R.mipmap.weather_haze, R.mipmap.weather_rain_snow};

    static {
        for (int index = 0; index < WEATHERS.length; index++) {
            sWeatherIcons.put(WEATHERS[index], ICONS_ID[index]);
        }
    }
    public static long getSchedule(int which) {
        return SCHEDULES[which];
    }


    public static int getIconId(String weather) {

        if (Check.isEmpty(weather)) {
            return R.mipmap.weather_none_available;
        }

        if (sWeatherIcons.get(weather) != null) {
            return sWeatherIcons.get(weather);
        }

        for (String weatherKey : sWeatherIcons.keySet()) {
            if (weatherKey.contains(weather) || weather.contains(weatherKey)) {
                return sWeatherIcons.get(weatherKey);
            }
        }

        return R.mipmap.weather_none_available;
    }

    public static boolean sunny(String weather) {
        for (String weatherKey : SUNNY) {
            if (weather.contains(weather) || weather.contains(weather)) {
                return true;
            }
        }
        return false;
    }

}
