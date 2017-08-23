package com.allen.onlyweather.city.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;
import com.allen.onlyweather.weather.entity.Weather;

/**
 * Created by Allen.
 */

public class FollowedCityData implements BaseAdapterData {

    String cityId;
    String cityName;
    String temp;
    String weatherStatus;
    int backgroundId;

    public FollowedCityData(Weather weather, int backgroundId) {
        if (weather != null) {
            this.cityId = weather.basic.id;
            this.cityName = weather.basic.city;
            this.temp = weather.now.tmp;
            this.weatherStatus = weather.now.cond.txt;
        }
        this.backgroundId = backgroundId;
    }


    @Override
    public int getItemViewType() {
        return R.layout.item_followed_city;
    }
}
