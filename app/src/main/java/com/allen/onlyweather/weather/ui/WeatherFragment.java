package com.allen.onlyweather.weather.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.onlyweather.MyApplication;
import com.allen.onlyweather.R;
import com.allen.onlyweather.common.BaseFragment;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.weather.ui.adapter.AqiData;
import com.allen.onlyweather.weather.ui.adapter.AqiViewHolder;
import com.allen.onlyweather.weather.ui.adapter.DailyForecastData;
import com.allen.onlyweather.weather.ui.adapter.DailyForecastViewHolder;
import com.allen.onlyweather.weather.ui.adapter.GuideData;
import com.allen.onlyweather.weather.ui.adapter.GuideViewHolder;
import com.allen.onlyweather.weather.ui.adapter.SuggestionData;
import com.allen.onlyweather.weather.ui.adapter.SuggestionGuideData;
import com.allen.onlyweather.weather.ui.adapter.SuggestionGuideViewHolder;
import com.allen.onlyweather.weather.ui.adapter.SuggestionViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Allen.
 */

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.rv_weather_info)
    RecyclerView weatherInfo;

    private BaseRecyclerAdapter mWeatherInfoAdapter;

    public static WeatherFragment newInstance() {
        WeatherFragment weatherFragment;
        weatherFragment = new WeatherFragment();
        return weatherFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.common_recyclerview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        weatherInfo.setLayoutManager(linearLayoutManager);
        weatherInfo.setBackgroundResource(R.color.dark_background);
        mWeatherInfoAdapter = new BaseRecyclerAdapter(mActivity);
    }

    @Override
    public void initBeforeView() {

    }

    public void onWeatherInfo(AqiData aqiData, List<DailyForecastData> dailyForecastDatas, SuggestionData suggestionData) {
        mWeatherInfoAdapter.clear();

        GuideData guideData = new GuideData(getString(R.string.future_weather));
        mWeatherInfoAdapter.registerHolder(GuideViewHolder.class, guideData);
        mWeatherInfoAdapter.registerHolder(DailyForecastViewHolder.class, dailyForecastDatas);
        guideData = new GuideData(getString(R.string.aqi_guide));
        mWeatherInfoAdapter.addData(guideData);
        mWeatherInfoAdapter.registerHolder(AqiViewHolder.class, aqiData);
        SuggestionGuideData suggestionGuideData = new SuggestionGuideData(getString(R.string.lifeIndexes));
        mWeatherInfoAdapter.registerHolder(SuggestionGuideViewHolder.class, suggestionGuideData);
        mWeatherInfoAdapter.registerHolder(SuggestionViewHolder.class, suggestionData);
        weatherInfo.setAdapter(mWeatherInfoAdapter);
    }
}
