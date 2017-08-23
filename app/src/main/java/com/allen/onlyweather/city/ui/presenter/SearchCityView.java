package com.allen.onlyweather.city.ui.presenter;

import com.allen.onlyweather.city.adapter.CityInfoData;
import com.allen.onlyweather.common.BaseView;

import java.util.List;

/**
 * Created by Allen.
 */

public interface SearchCityView extends BaseView {

    void onMatched(List<CityInfoData> cityInfoDatas);

    void onAllCities(List<CityInfoData> allCityInfoDatas);

}
