package com.allen.onlyweather.weather.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.utils.Check;
import com.allen.onlyweather.weather.entity.HourlyForecast;

import butterknife.BindView;

/**
 * Created by Allen.
 */

public class HourlyWeatherHolder extends BaseViewHolder<HourlyForecastData> {

    @BindView(R.id.hour)
    TextView hour;
    @BindView(R.id.hour_temp)
    TextView hourTemp;
    @BindView(R.id.hour_icon)
    ImageView hourIcon;


    public HourlyWeatherHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_hour_forecast;
    }


    @Override
    public void updateItem(HourlyForecastData data, int position) {
        HourlyForecast hourlyForecastData = data.hourlyForecastData;
        if (Check.isNull(hourlyForecastData)) {
            return;
        }
        hour.setText(hourlyForecastData.date.substring(11, 16));
        hourTemp.setText(hourlyForecastData.tmp);
        hourIcon.setImageResource(Constants.getIconId(hourlyForecastData.cond.txt));

    }
}
