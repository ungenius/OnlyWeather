package com.allen.onlyweather.weather.ui.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;
import com.allen.onlyweather.weather.entity.HourlyForecast;

/**
 * Created by Allen.
 */

public class HourlyForecastData implements BaseAdapterData {
    public HourlyForecast hourlyForecastData;

    public HourlyForecastData(HourlyForecast hourlyForecast) {
        this.hourlyForecastData = hourlyForecast;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_hour_forecast;
    }
}
