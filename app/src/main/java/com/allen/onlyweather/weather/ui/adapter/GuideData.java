package com.allen.onlyweather.weather.ui.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;

/**
 * Created by Allen.
 */

public class GuideData implements BaseAdapterData {
    public String guide;
    public int guideIcon;

    public GuideData(String guide) {
        this.guide = guide;
    }

    public void setGuideIcon(int guideIcon) {
        this.guideIcon = guideIcon;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_guide;
    }
}
