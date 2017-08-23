package com.allen.onlyweather.city.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.R;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.model.CityModel;
import com.allen.onlyweather.model.WeatherModel;
import com.allen.onlyweather.weather.entity.Weather;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Allen.
 */

public class CityViewHolder extends BaseViewHolder<CityInfoData> {

    @BindView(R.id.tv_item_city_letter)
    TextView mTvItemCityLetter;
    @BindView(R.id.tv_item_city_name)
    TextView mTvItemCityName;

    private String mCityId;

    public CityViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);

    }

    @Override
    public int getContentViewId() {
        return R.layout.item_city;
    }

    @Override
    public void updateItem(CityInfoData data, int position) {
        mCityId = data.getCityId();
        mTvItemCityName.setText(data.getCityName());
        if (!Constants.DEFAULT_STR.equals(data.getInitial())) {
            mTvItemCityLetter.setVisibility(View.VISIBLE);
            mTvItemCityLetter.setText(data.getInitial());

        } else {
            mTvItemCityLetter.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_item_city_name)
    public void onClick() {
        ModelManager.getModel(WeatherModel.class).updateWeather(mCityId);
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }
}
