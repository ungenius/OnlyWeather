package com.allen.onlyweather.city.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseAdapterData;

/**
 * Created by Allen.
 */

public class CityInfoData implements BaseAdapterData {

    private String mInitial = Constants.DEFAULT_STR;
    private String cityName;
    private String cityNamePinyin;
    private String cityId;

    public CityInfoData(String cityName, String cityNamePinyin, String cityId) {
        this.cityName = cityName;
        this.cityNamePinyin = cityNamePinyin;
        this.cityId = cityId;
    }

    public void setInitial(String initial) {
        mInitial = initial;
    }

    public String getInitial() {
        return mInitial;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityNamePinyin() {
        return cityNamePinyin;
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_city;
    }
}
