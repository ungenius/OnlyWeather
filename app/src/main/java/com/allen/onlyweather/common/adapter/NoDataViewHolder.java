package com.allen.onlyweather.common.adapter;

import android.view.View;

/**
 * Created by Allen.
 */

public class NoDataViewHolder extends BaseViewHolder {
    public NoDataViewHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initBeforeView() {

    }

    @Override
    public void updateItem(BaseAdapterData data, int position) {

    }
}
