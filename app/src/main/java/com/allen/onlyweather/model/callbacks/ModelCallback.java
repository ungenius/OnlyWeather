package com.allen.onlyweather.model.callbacks;

import com.allen.onlyweather.weather.entity.Weather;

/**
 * Created by Allen.
 */

public interface ModelCallback {
    interface WeatherResult{
        void onWeather(Weather weather);
    }

    interface LocationResult{
        void onLocationComplete(String cityId, boolean success);

    }
}
