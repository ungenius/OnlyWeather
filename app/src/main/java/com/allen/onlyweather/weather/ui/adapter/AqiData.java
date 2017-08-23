package com.allen.onlyweather.weather.ui.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;
import com.allen.onlyweather.weather.entity.AQI;
import com.allen.onlyweather.weather.entity.Suggestion;

/**
 * Created by Allen.
 */

public class AqiData implements BaseAdapterData {

    public AQI aqi;
    public Suggestion suggestion;

    public AqiData(AQI aqi, Suggestion suggestion) {
        this.aqi = aqi;
        this.suggestion = suggestion;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_aqi;
    }
}
