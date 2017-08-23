package com.allen.onlyweather.city.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.R;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.model.CityModel;
import com.allen.onlyweather.model.WeatherModel;
import com.allen.onlyweather.utils.UIUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Allen.
 */

public class FollowedCityHolder extends BaseViewHolder<FollowedCityData> {
    public static final int[] BLUR_IMAGE = {R.mipmap.blur0, R.mipmap.blur1, R.mipmap.blur2, R.mipmap.blur3, R.mipmap.blur4, R.mipmap.blur5};

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.city_temp)
    TextView mCityTemp;
    @BindView(R.id.city_name)
    TextView mCityName;
    @BindView(R.id.city_status)
    TextView mCityStatus;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.checked)
    ImageView mChecked;
    @BindView(R.id.delete)
    ImageView mDelete;
    @BindView(R.id.hover)
    View mHover;

    private CityWeatherAdapter mCityWeatherAdapter;
    private FollowedCityData mFollowedCityData;
    private CityModel mCityModel;
    private Drawable mDrawableLocation;


    public FollowedCityHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        mCityWeatherAdapter = (CityWeatherAdapter) baseRecyclerAdapter;
        mCityModel = ModelManager.getModel(CityModel.class);
    }

    @Override
    public void initViews() {
        super.initViews();
        mDrawableLocation = UIUtil.getDrawable(getContext(), R.mipmap.location);
        mDrawableLocation.setBounds(0, 0, UIUtil.dipToPx(getContext(), R.dimen.common_location_size), UIUtil.dipToPx(getContext(), R.dimen.common_location_size));
    }

    @Override
    public void updateItem(FollowedCityData data, int position) {
        if (data == null) {
            return;
        }
        mFollowedCityData = data;
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);
        mImage.setImageResource(data.backgroundId);
        mCityTemp.setText(data.temp);
        mCityName.setText(data.cityName);
        mCityStatus.setText(data.weatherStatus);

        Drawable drawableLeft = UIUtil.getDrawable(getContext(), Constants.getIconId(data.weatherStatus));
        drawableLeft.setBounds(0, 0, UIUtil.dipToPx(getContext(), R.dimen.common_icon_size_small), UIUtil.dipToPx(getContext(), R.dimen.common_icon_size_small));
        mCityStatus.setCompoundDrawables(drawableLeft, null, null, null);

        mDelete.setVisibility(mCityWeatherAdapter.mIsDeleting ? View.VISIBLE : View.GONE);
        mHover.setVisibility(mCityWeatherAdapter.mIsDeleting ? View.VISIBLE : View.GONE);

        if (data.cityId.equals(mCityModel.getLocationCityId())) {
            mDelete.setVisibility(View.GONE);
            mHover.setVisibility(View.GONE);
            mCityName.setCompoundDrawables(mDrawableLocation, null, null, null);
        } else {
            mCityName.setCompoundDrawables(null, null, null, null);
        }

        boolean isDefault = data.cityId.equals(mCityModel.getDefaultId());
        mChecked.setVisibility(isDefault ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_followed_city;
    }

    @OnLongClick(R.id.content)
    boolean showDelete() {
        updateAdapter(true);
        return true;
    }

    private void updateAdapter(boolean deleting) {
        mCityWeatherAdapter.setDeleteAction(deleting);
        mCityWeatherAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.content, R.id.delete})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.content:
                if (!mCityWeatherAdapter.mIsDeleting) {
                    mCityModel.setDefaultId(mFollowedCityData.cityId);
                    ModelManager.getModel(WeatherModel.class).updateWeather(mFollowedCityData.cityId);

                }

                updateAdapter(false);
                break;
            case R.id.delete:
                mCityModel.unFollowedCity(mFollowedCityData.cityId);
                mCityWeatherAdapter.getData().remove(getLayoutPosition());
                updateAdapter(true);
                break;
        }
    }
}