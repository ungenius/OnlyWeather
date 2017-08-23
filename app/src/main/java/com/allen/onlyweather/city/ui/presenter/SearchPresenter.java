package com.allen.onlyweather.city.ui.presenter;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.city.adapter.CityInfoData;
import com.allen.onlyweather.common.BasePresenter;
import com.allen.onlyweather.model.CityModel;
import com.allen.onlyweather.scheduler.TaskCallback;

import java.util.List;

/**
 * Created by Allen.
 */

public class SearchPresenter extends BasePresenter<SearchCityView>{

    private SearchCityView mSearchCityView;
    private CityModel mCityModel;


    public SearchPresenter(SearchCityView presentView) {
        super(presentView);
        mSearchCityView = presentView;
        mCityModel = ModelManager.getModel(CityModel.class);
    }


    public void getAllCities() {
        mCityModel.getAllCities(new TaskCallback.Success<List<CityInfoData>>() {
            @Override
            public void onSuccess(List<CityInfoData> response) {
                mSearchCityView.onAllCities(response);
            }
        });

    }

    public void matchedCities(final String key) {
        mCityModel.matchCities(key, new TaskCallback.Success<List<CityInfoData>>() {
            @Override
            public void onSuccess(List<CityInfoData> response) {
                mSearchCityView.onMatched(response);
            }
        });
    }
}
