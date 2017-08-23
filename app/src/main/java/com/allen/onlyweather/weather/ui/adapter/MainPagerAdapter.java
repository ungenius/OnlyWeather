package com.allen.onlyweather.weather.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allen.onlyweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private int[] tabIconIds = new int[]{R.drawable.tab_weather_drawable, R.drawable.tab_city_drawable};
    private Context context;


    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public View getTabView(int position, ViewGroup parent) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.tab_view, parent, false);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        icon.setImageResource(tabIconIds[position]);
        return view;
    }
}
