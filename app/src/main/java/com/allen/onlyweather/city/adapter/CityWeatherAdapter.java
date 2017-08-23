package com.allen.onlyweather.city.adapter;

import android.content.Context;

import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;

/**
 * Created by Allen.
 */

public class CityWeatherAdapter extends BaseRecyclerAdapter {

    boolean mIsDeleting;

    public CityWeatherAdapter(Context context) {
        super(context);
    }

    public void setDeleteAction(boolean deleting) {
        this.mIsDeleting = deleting;
    }
}
