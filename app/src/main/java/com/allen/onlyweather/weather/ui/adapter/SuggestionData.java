package com.allen.onlyweather.weather.ui.adapter;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;
import com.allen.onlyweather.weather.entity.Suggestion;

/**
 * Created by Allen.
 */

public class SuggestionData implements BaseAdapterData {

    public Suggestion suggestion;


    public SuggestionData(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_suggestion;
    }
}
