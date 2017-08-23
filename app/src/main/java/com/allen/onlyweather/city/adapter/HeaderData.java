package com.allen.onlyweather.city.adapter;


import android.support.v4.util.Pair;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen.
 * 热门城市
 */

public class HeaderData implements BaseAdapterData {

    private List<Pair<String, String>> mHotCities;

    public HeaderData() {
        mHotCities = new ArrayList<>();
        mHotCities.add(new Pair<>("北京", "CN101010100"));
        mHotCities.add(new Pair<>("上海", "CN101020100"));
        mHotCities.add(new Pair<>("广州", "CN101280101"));
        mHotCities.add(new Pair<>("深圳", "CN101280601"));
        mHotCities.add(new Pair<>("杭州", "CN101210101"));
        mHotCities.add(new Pair<>("南京", "CN101190101"));
        mHotCities.add(new Pair<>("武汉", "CN101200101"));
        mHotCities.add(new Pair<>("重庆", "CN101040100"));
    }

    public List<Pair<String, String>> getCities() {
        return mHotCities;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_city_select_header;
    }
}
