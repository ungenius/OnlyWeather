package com.allen.onlyweather.weather.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Allen.
 */

public class GuideViewHolder extends BaseViewHolder<GuideData> {

    @BindView(R.id.tv_guide_title)
    TextView tv_guide_title;
    @BindView(R.id.iv_guide_icon)
    ImageView iv_guide_icon;

    public GuideViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_guide;
    }

    @Override
    public void updateItem(GuideData data, int position) {
        tv_guide_title.setText(data.guide);
        if (data.guideIcon != 0) {
            iv_guide_icon.setImageResource(data.guideIcon);
        }
    }
}
