package com.allen.onlyweather.weather.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.utils.Check;
import com.allen.onlyweather.utils.TimeUtil;
import com.allen.onlyweather.weather.entity.DailyForecast;

import butterknife.BindView;

/**
 * Created by Allen.
 */

public class DailyForecastViewHolder extends BaseViewHolder<DailyForecastData> {

    @BindView(R.id.tv_date_week)
    TextView tv_date_week;
    @BindView(R.id.weather_status_daily)
    TextView weather_status_daily;
    @BindView(R.id.weather_icon_daily)
    ImageView weather_icon_daily;
    @BindView(R.id.temp_daily)
    TextView temp_daily;

    public DailyForecastViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_daily_forecast;
    }

    @Override
    public void updateItem(DailyForecastData data, int position) {
        DailyForecast dailyForecast = data.dailyForecast;
        if (Check.isNull(dailyForecast)) {

            return;
        }
        weather_status_daily.setText(dailyForecast.cond.txt_d);
        weather_icon_daily.setImageResource(Constants.getIconId(dailyForecast.cond.txt_d));
        temp_daily.setText(String.format("%1s~%2s°", dailyForecast.tmp.min, dailyForecast.tmp.max));
        tv_date_week.setText(TimeUtil.getDateWeek(dailyForecast.date));

    }
}
