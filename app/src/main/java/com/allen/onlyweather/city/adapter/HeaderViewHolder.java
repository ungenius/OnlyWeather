package com.allen.onlyweather.city.adapter;

import android.app.Activity;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.R;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseAdapterData;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.model.CityModel;
import com.allen.onlyweather.model.WeatherModel;
import com.allen.onlyweather.model.callbacks.ModelCallback;
import com.allen.onlyweather.utils.Check;
import com.silencedut.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Allen.
 */

public class HeaderViewHolder extends BaseViewHolder<HeaderData> implements ModelCallback.LocationResult{

    @BindView(R.id.tv_located_city)
    TextView mTvLocatedCity;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private boolean mLocationSucceeded;
    private CityModel mCityModel;
    private WeatherModel mWeatherModel;
    private BaseRecyclerAdapter mHotCitiesAdapter;


    public HeaderViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        Router.instance().register(this);
    }

    @Override
    public void initBeforeView() {
        mCityModel = ModelManager.getModel(CityModel.class);
        mWeatherModel = ModelManager.getModel(WeatherModel.class);
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_city_select_header;
    }

    @Override
    public void updateItem(HeaderData data, int position) {
        if (Check.isNull(data)) {
            return;
        }
        mHotCitiesAdapter.clear();
        List<HotCity> hotCities = new ArrayList<>();
        for (Pair<String, String> city : data.getCities()) {
            hotCities.add(new HotCity(city.first, city.second));
        }
        mHotCitiesAdapter.registerHolder(HotCityHolder.class, hotCities);
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mHotCitiesAdapter = new BaseRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mHotCitiesAdapter);

        String locationName = mCityModel.locationCityName();
        showLocation(!locationName.equals(Constants.DEFAULT_STR));
    }

    private void showLocation(boolean success) {
        mLocationSucceeded = success;
        if (success) {
            mTvLocatedCity.setText(mCityModel.locationCityName());
        }else {
            mTvLocatedCity.setText(R.string.located_failed);
        }
    }


    @OnClick(R.id.location_layout)
    void locate() {
        if (mLocationSucceeded) {
            mWeatherModel.updateWeather(mCityModel.getLocationCityId());
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        } else {
            mCityModel.startLocation();
            mTvLocatedCity.setText(R.string.locating);
        }
    }

    @Override
    public void onLocationComplete(String cityId, boolean success) {
        showLocation(success);
    }


    static final class HotCity implements BaseAdapterData {

        private String mCityName;
        private String mCityId;

        public HotCity(String cityName, String cityId) {
            mCityName = cityName;
            mCityId = cityId;
        }

        @Override
        public int getItemViewType() {
            return R.layout.item_hot_city;
        }
    }

    static final class HotCityHolder extends BaseViewHolder<HotCity> {

        @BindView(R.id.tv_hot_city_name)
        TextView mTvHotCityName;
        HotCity mHotCity;

        public HotCityHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
            super(itemView, baseRecyclerAdapter);
        }

        @Override
        public int getContentViewId() {
            return R.layout.item_hot_city;
        }

        @Override
        public void updateItem(HotCity data, int position) {
            mTvHotCityName.setText(data.mCityName);
            mHotCity = data;
        }

        @OnClick(R.id.tv_hot_city_name)
        void navigationWeather() {
            ModelManager.getModel(WeatherModel.class).updateWeather(mHotCity.mCityId);
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        }
    }
}
