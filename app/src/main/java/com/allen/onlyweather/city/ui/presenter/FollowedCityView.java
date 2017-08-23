package com.allen.onlyweather.city.ui.presenter;

import com.allen.onlyweather.city.adapter.FollowedCityData;
import com.allen.onlyweather.common.BaseView;

import java.util.List;

/**
 * Created by Allen.
 */

public interface FollowedCityView extends BaseView {
    void onAllFollowedCities(List<FollowedCityData> followedCityDatas);

    void onFollowedCity(FollowedCityData followedCityData);
}
