package com.allen.onlyweather.weather.ui.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;

/**
 * Created by Allen.
 */

public class SuggestionGuideData implements BaseAdapterData {

    public String title;

    public SuggestionGuideData(String title) {
        this.title = title;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_index_guide;
    }
}
