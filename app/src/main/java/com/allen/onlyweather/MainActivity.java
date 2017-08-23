package com.allen.onlyweather;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.onlyweather.city.ui.CityFragment;
import com.allen.onlyweather.common.BaseActivity;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.scheduler.job.PollingUtil;
import com.allen.onlyweather.user.AboutActivity;
import com.allen.onlyweather.user.SettingsActivity;
import com.allen.onlyweather.utils.LogHelper;
import com.allen.onlyweather.utils.PreferencesUtil;
import com.allen.onlyweather.utils.TimeUtil;
import com.allen.onlyweather.utils.UIUtil;
import com.allen.onlyweather.weather.entity.Basic;
import com.allen.onlyweather.weather.entity.Now;
import com.allen.onlyweather.weather.presenter.MainView;
import com.allen.onlyweather.weather.presenter.WeatherPresenter;
import com.allen.onlyweather.weather.ui.WeatherFragment;
import com.allen.onlyweather.weather.ui.adapter.AqiData;
import com.allen.onlyweather.weather.ui.adapter.AqiViewHolder;
import com.allen.onlyweather.weather.ui.adapter.DailyForecastData;
import com.allen.onlyweather.weather.ui.adapter.DailyForecastViewHolder;
import com.allen.onlyweather.weather.ui.adapter.GuideData;
import com.allen.onlyweather.weather.ui.adapter.GuideViewHolder;
import com.allen.onlyweather.weather.ui.adapter.HourlyForecastData;
import com.allen.onlyweather.weather.ui.adapter.HourlyWeatherHolder;
import com.allen.onlyweather.weather.ui.adapter.MainPagerAdapter;
import com.allen.onlyweather.weather.ui.adapter.SuggestionData;
import com.allen.onlyweather.weather.ui.adapter.SuggestionGuideData;
import com.allen.onlyweather.weather.ui.adapter.SuggestionGuideViewHolder;
import com.allen.onlyweather.weather.ui.adapter.SuggestionViewHolder;
import com.silencedut.router.Router;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements
        AppBarLayout.OnOffsetChangedListener , MainView, NavigationView.OnNavigationItemSelectedListener {

    private static final int ROTATION_DURATION = 1000;
    private static final int POSTTIME_DURATION = 500;
    private static final float DEFAULT_PERCENTAGE = 0.8f;

    private float percentageOfShowTitle = DEFAULT_PERCENTAGE;
    private float mWeatherInfoContainerLeft;
    private BaseRecyclerAdapter mHourlyForecastAdapter;
    private String mTemperature;
    private String mWeatherStatus;
    protected float mTitlePercentage;
    private ObjectAnimator mActionRotate;
    private Drawable mDrawableLocation;
    private ValueAnimator mSucceedAnimator;


    @BindView(R.id.iv_main_bg)
    ImageView iv_main_bg;
    @BindView(R.id.tb_main_toolbar)
    Toolbar tb_main_toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout app_bar_layout;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.iv_refresh_status)
    ImageView iv_refresh_status;
    @BindView(R.id.rv_main_hours_forecast)
    RecyclerView rv_main_hours_forecast;
    @BindView(R.id.iv_title_icon)
    ImageView iv_title_icon;
    @BindView(R.id.tv_title_temp)
    TextView tv_title_temp;
    @BindView(R.id.dl_main_drawer)
    DrawerLayout dl_main_drawer;
    @BindView(R.id.nv_main_nav)
    NavigationView nv_main_nav;
    @BindView(R.id.tv_main_post_time)
    TextView tv_main_post_time;
    @BindView(R.id.float_action)
    FloatingActionButton float_action;
    @BindView(R.id.tv_main_location)
    TextView tv_main_location;
    @BindView(R.id.main_temp)
    TextView main_temp;
    @BindView(R.id.main_info)
    TextView main_info;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    private boolean mIsInit = true;
    private WeatherPresenter mWeatherPresenter;
    private WeatherFragment weatherFragment;


    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initBeforeView() {
        mWeatherPresenter = new WeatherPresenter(this);
    }

    @Override
    public void initViews() {
        if (TimeUtil.isNight()) {
            iv_main_bg.setImageResource(R.mipmap.bg_night);
        }
        setSupportActionBar(tb_main_toolbar);
        getSupportActionBar().setTitle("");
//        tb_main_toolbar.setNavigationIcon(R.drawable.ic_action_name);
        app_bar_layout.addOnOffsetChangedListener(this);
        ll_container.post(new Runnable() {
            @Override
            public void run() {
                mWeatherInfoContainerLeft = ll_container.getX();
                percentageOfShowTitle = (ll_container.getBottom()) * 1.0f /app_bar_layout.getTotalScrollRange();
                if (percentageOfShowTitle == 0) {
                    mWeatherInfoContainerLeft = DEFAULT_PERCENTAGE;
                }
            }
        });

        mActionRotate = ObjectAnimator.ofFloat(iv_refresh_status, "rotation", 0, 360);
        mActionRotate.setDuration(ROTATION_DURATION);
        mActionRotate.setRepeatCount(ValueAnimator.INFINITE);
        mDrawableLocation = UIUtil.getDrawable(this, R.mipmap.location);
        mDrawableLocation.setBounds(0, 0, UIUtil.dipToPx(this, R.dimen.common_location_size), UIUtil.dipToPx(this, R.dimen.common_location_size));

        mSucceedAnimator = ObjectAnimator.ofFloat(tv_main_post_time, "scaleX", 1, 0, 1);
        mSucceedAnimator.setStartDelay(ROTATION_DURATION);
        initDrawer();
        setupViewPager();
        setUpHoursForecast();
        int scheduleWhich = PreferencesUtil.get(Constants.POLLING_TIME, 0);
        PollingUtil.startService(this, scheduleWhich != 3);

    }

    private void initDrawer() {
        if (nv_main_nav != null) {
            nv_main_nav.setNavigationItemSelectedListener(this);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl_main_drawer, tb_main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            dl_main_drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

    }

    private void setupViewPager() {
        MainPagerAdapter adapter = new MainPagerAdapter(this, getSupportFragmentManager());
        weatherFragment = WeatherFragment.newInstance();
        adapter.addFragment(weatherFragment);
        Fragment cityFragment = CityFragment.newInstance();
        adapter.addFragment(cityFragment);


        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setCustomView(adapter.getTabView(0, mTabLayout));
        mTabLayout.getTabAt(1).setCustomView(adapter.getTabView(1, mTabLayout));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);
    }

    private void setUpHoursForecast() {
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(this);
        horizontalLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_main_hours_forecast.setLayoutManager(horizontalLinearLayoutManager);
        mHourlyForecastAdapter = new BaseRecyclerAdapter(this);

        mHourlyForecastAdapter.registerHolder(HourlyWeatherHolder.class, R.layout.item_hour_forecast);
        rv_main_hours_forecast.setAdapter(mHourlyForecastAdapter);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        mTitlePercentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        handleInfoAnimate(mTitlePercentage);

    }

    private void handleInfoAnimate(float percentage) {
        tb_main_toolbar.getBackground().mutate().setAlpha((int) (255 * percentage));
        ll_container.setAlpha(1 - percentage);
        ll_container.setScaleX(1 - percentage);
        ll_container.setScaleY(1 - percentage);
        rv_main_hours_forecast.setAlpha(1 - percentage);
        if (mWeatherInfoContainerLeft > 0) {
            ll_container.setX(mWeatherInfoContainerLeft * (1 + percentage));
        }
        if (!(percentage < percentageOfShowTitle)) {
            iv_title_icon.setImageResource(Constants.getIconId(mWeatherStatus));
            if (tv_title_temp.getVisibility() == View.GONE) {
                tv_title_temp.setVisibility(View.VISIBLE);
                tv_title_temp.setText(mTemperature);
            }
        } else {
            if (tv_title_temp.getVisibility() == View.VISIBLE) {
                iv_title_icon.setImageDrawable(null);
                tv_title_temp.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onRefreshing(boolean refreshing) {
        if (refreshing) {
            tv_main_post_time.setText(R.string.refreshing);
            iv_refresh_status.setVisibility(View.VISIBLE);
            mActionRotate.start();
            float_action.hide();
        } else {
            tv_main_post_time.setText(R.string.refreshing_fail);
            stopRefreshing();
        }
    }

    private void stopRefreshing() {
        mActionRotate.end();
        iv_refresh_status.setVisibility(View.GONE);
        float_action.show();
    }

    @Override
    public void onBasicInfo(Basic basicData, Now nowData, List<HourlyForecastData> hourlyForecastDatas, boolean isLocationCity) {
        tv_main_location.setCompoundDrawables(isLocationCity ? mDrawableLocation : null, null, null, null);
        tv_main_location.setText(basicData.city);
        updateSucceed(String.format(getString(R.string.post), TimeUtil.getTimeTips(basicData.update.loc)));
        mTemperature = String.format("%s°", nowData.tmp);
        mWeatherStatus = nowData.cond.txt;
        main_temp.setText(mTemperature);
        main_info.setText(mWeatherStatus);

        if (TimeUtil.isNight()) {
            if (Constants.sunny(mWeatherStatus)) {
                iv_main_bg.setImageResource(R.mipmap.bg_night);
            } else {
                iv_main_bg.setImageResource(R.mipmap.bg_night_dark);
            }
        } else {
            iv_main_bg.setImageResource(R.mipmap.bg_day);
        }
        mHourlyForecastAdapter.setData(hourlyForecastDatas);
    }

    private void updateSucceed(final String postTime) {
        mSucceedAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                stopRefreshing();
                tv_main_post_time.setText(R.string.refreshing);
            }
        });

        mSucceedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                if (fraction >= 0.5f) {
                    tv_main_post_time.setText(postTime);
                }
            }
        });
        mSucceedAnimator.start();
    }

    @Override
    public void onWeatherInfo(AqiData aqiData, List<DailyForecastData> dailyForecastDatas, SuggestionData suggestionData) {
        weatherFragment.onWeatherInfo(aqiData, dailyForecastDatas, suggestionData);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                dl_main_drawer.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                SettingsActivity.navigationActivity(this);
                break;
        }
        dl_main_drawer.closeDrawers();
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && mIsInit) {
            mIsInit = false;
            mWeatherPresenter.getDefaultWeather();
        }
    }

    @OnClick(R.id.float_action)
    public void onClick() {

        mWeatherPresenter.updateDefaultWeather();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSucceedAnimator.removeAllListeners();
        mActionRotate.removeAllListeners();
        mSucceedAnimator.removeAllUpdateListeners();
        mActionRotate.removeAllUpdateListeners();
    }

    @Override
    public Context getContext() {
        return this;
    }


}
