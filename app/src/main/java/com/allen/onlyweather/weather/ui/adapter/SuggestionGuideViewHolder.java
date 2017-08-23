package com.allen.onlyweather.weather.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.weather.callbacks.WeatherCallbacks;
import com.silencedut.router.Router;

import butterknife.BindView;

/**
 * Created by Allen.
 */

public class SuggestionGuideViewHolder extends BaseViewHolder<SuggestionGuideData> implements WeatherCallbacks.LifeAdvice {

    @BindView(R.id.guide_title)
    TextView guideTitle;

    public SuggestionGuideViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        Router.instance().register(this);
    }

    @Override
    public void updateItem(SuggestionGuideData data, int position) {
        guideTitle.setText(data.title);
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_index_guide;
    }

    @Override
    public void lifeAdvice(String index, String advice) {
        guideTitle.setText(index);
    }
}
