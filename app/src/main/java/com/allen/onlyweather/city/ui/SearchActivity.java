package com.allen.onlyweather.city.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.R;
import com.allen.onlyweather.city.adapter.CityInfoData;
import com.allen.onlyweather.city.adapter.CityViewHolder;
import com.allen.onlyweather.city.adapter.HeaderData;
import com.allen.onlyweather.city.adapter.HeaderViewHolder;
import com.allen.onlyweather.city.ui.presenter.SearchCityView;
import com.allen.onlyweather.city.ui.presenter.SearchPresenter;
import com.allen.onlyweather.common.BaseActivity;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.customview.SideLetterBar;
import com.allen.onlyweather.db.CityInfo;
import com.allen.onlyweather.model.CityModel;
import com.allen.onlyweather.model.WeatherModel;
import com.allen.onlyweather.utils.Check;
import com.allen.onlyweather.utils.PreferencesUtil;
import com.allen.onlyweather.weather.entity.Weather;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Allen.
 */

public class SearchActivity extends BaseActivity implements SearchCityView{
    @BindView(R.id.rv_city_list)
    RecyclerView mAllCitiesRecyclerView;
    @BindView(R.id.tv_letter_overlay)
    TextView mTvLetterOverlay;
    @BindView(R.id.slb_side_bar)
    SideLetterBar mSide;
    @BindView(R.id.et_search_city)
    EditText mSearchTextView;
    @BindView(R.id.ib_empty_btn)
    ImageButton mActionEmptyBtn;
    @BindView(R.id.search_result_view)
    RecyclerView mSearchResultView;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;

    private SearchPresenter mSearchPresenter;
    private BaseRecyclerAdapter mSearchResultAdapter;

    public static void navigationActivity(Context from) {
        Intent intent = new Intent(from, SearchActivity.class);
        from.startActivity(intent);
    }

    public static void navigationFromApplication(Context from) {
        Intent intent = new Intent(from, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initBeforeView() {
        mSearchPresenter = new SearchPresenter(this);
        mSearchPresenter.getAllCities();
    }

    @Override
    public void initViews() {
        initSearcherView();
    }

    private void initSearcherView() {
        setCursorDrawable(R.drawable.color_cursor_white);
        mSearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });

        mSearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CharSequence text = mSearchTextView.getText();
                boolean hasText = !TextUtils.isEmpty(text);
                if (hasText) {
                    mActionEmptyBtn.setVisibility(View.VISIBLE);
                } else {
                    mActionEmptyBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    mActionEmptyBtn.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    mSearchResultView.setVisibility(View.GONE);
                } else {
                    mActionEmptyBtn.setVisibility(View.VISIBLE);
                    mSearchResultView.setVisibility(View.VISIBLE);
                    mSearchPresenter.matchedCities(keyword);
                }
            }
        });

    }

    private int getLetterPosition(String letter, List<CityInfoData> allCityInfoDatas) {
        int position = 0;
        for (CityInfoData data : allCityInfoDatas) {
            if (data.getInitial().equals(letter)) {
                position = allCityInfoDatas.indexOf(data);
            }
        }
        return position;
    }

    @Override
    public void onMatched(List<CityInfoData> cityInfoDatas) {
        if (Check.isNull(cityInfoDatas)) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mSearchResultAdapter.setData(cityInfoDatas);
        }
    }

    @Override
    public void onAllCities(final List<CityInfoData> allCityInfoDatas) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAllCitiesRecyclerView.setLayoutManager(linearLayoutManager);
        BaseRecyclerAdapter citiesAdapter = new BaseRecyclerAdapter(this);
        citiesAdapter.registerHolder(HeaderViewHolder.class, new HeaderData());
        citiesAdapter.registerHolder(CityViewHolder.class, allCityInfoDatas);
        mAllCitiesRecyclerView.setAdapter(citiesAdapter);

        LinearLayoutManager resultLayoutManager = new LinearLayoutManager(this);
        mSearchResultView.setLayoutManager(resultLayoutManager);
        mSearchResultAdapter = new BaseRecyclerAdapter(this);
        mSearchResultAdapter.registerHolder(CityViewHolder.class, R.layout.item_city);
        mSearchResultView.setAdapter(mSearchResultAdapter);

        mSide.setOverlay(mTvLetterOverlay);
        mSide.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                linearLayoutManager.scrollToPositionWithOffset(getLetterPosition(letter, allCityInfoDatas), 0);
            }
        });


    }

    //设置游标样式
    private void setCursorDrawable(int drawable) {
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mSearchTextView, drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ib_action_back, R.id.ib_empty_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_action_back:
                if (PreferencesUtil.get(Constants.DEFAULT_CITY, Constants.DEFAULT_STR).equals(Constants.DEFAULT_STR)) {
                    Toast.makeText(this, R.string.no_default_not_select, Toast.LENGTH_SHORT).show();
                    ModelManager.getModel(CityModel.class).setDefaultId(Constants.DEFAULT_CITY_ID);
                    ModelManager.getModel(WeatherModel.class).updateDefaultWeather();
                }
                finish();
                break;
            case R.id.ib_empty_btn:
                mSearchTextView.setText("");
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
