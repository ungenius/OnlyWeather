package com.allen.onlyweather.city.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.city.adapter.AddData;
import com.allen.onlyweather.city.adapter.AddHolder;
import com.allen.onlyweather.city.adapter.CityWeatherAdapter;
import com.allen.onlyweather.city.adapter.FollowedCityData;
import com.allen.onlyweather.city.adapter.FollowedCityHolder;
import com.allen.onlyweather.city.ui.presenter.FollowedCityPresenter;
import com.allen.onlyweather.city.ui.presenter.FollowedCityView;
import com.allen.onlyweather.common.BaseFragment;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.utils.PreferencesUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Allen.
 */

public class CityFragment extends BaseFragment implements FollowedCityView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private CityWeatherAdapter mSubscribeCityAdapter;
    private AddData mAddData = new AddData();
    private FollowedCityPresenter mFollowedCityPresenter;
    private boolean mIsVisibleToUser;


    public static CityFragment newInstance() {
        CityFragment cityFragment;
        cityFragment = new CityFragment();
        return cityFragment;
    }

    @Override
    public void initBeforeView() {
        mFollowedCityPresenter = new FollowedCityPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_city;
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setBackgroundResource(R.color.main_background);

        mSubscribeCityAdapter = new CityWeatherAdapter(getContext());

        mSubscribeCityAdapter.registerHolder(FollowedCityHolder.class, R.layout.item_followed_city);
        mSubscribeCityAdapter.registerHolder(AddHolder.class, R.layout.item_add_city);
        mRecyclerView.setAdapter(mSubscribeCityAdapter);
        mFollowedCityPresenter.getFollowedWeather();
    }

    @Override
    public void onAllFollowedCities(List<FollowedCityData> followedCityDatas) {
        mSubscribeCityAdapter.clear();
        mSubscribeCityAdapter.setData(followedCityDatas);
        mSubscribeCityAdapter.addData(mAddData);
    }

    @Override
    public void onFollowedCity(FollowedCityData followedCityData) {
        if (followedCityData == null) {
            mSubscribeCityAdapter.notifyDataSetChanged();
            return;
        }

        mSubscribeCityAdapter.getData().remove(mAddData);
        mSubscribeCityAdapter.addData(followedCityData);
        mSubscribeCityAdapter.addData(mAddData);
        if (!mIsVisibleToUser) {
            return;
        }

        if (!PreferencesUtil.get(Constants.CITYS_TIPS_SHOW, false) && mFollowedCityPresenter.followedCitiesNumber() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
            builder.setMessage(R.string.city_guide_info);
            builder.setNegativeButton(R.string.known, null);
            builder.setPositiveButton(R.string.tip_not_show, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferencesUtil.put(Constants.CITYS_TIPS_SHOW, true);
                }
            });
            builder.show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
    }
}
