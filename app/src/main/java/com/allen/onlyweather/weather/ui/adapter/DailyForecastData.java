package com.allen.onlyweather.weather.ui.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;
import com.allen.onlyweather.weather.entity.DailyForecast;

/**
 * Created by Allen.
 */

public class DailyForecastData implements BaseAdapterData {

    public DailyForecast dailyForecast;

    public DailyForecastData(DailyForecast dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_daily_forecast;
    }
}
