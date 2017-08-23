package com.allen.onlyweather.city.adapter;

import android.view.View;
import android.widget.ImageView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.city.ui.SearchActivity;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Allen.
 */

public class AddHolder extends BaseViewHolder<AddData> {
    @BindView(R.id.image)
    ImageView mImage;


    public AddHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(AddData data, int position) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.item_add_city;
    }

    @OnClick(R.id.image)
    public void onClick() {
        SearchActivity.navigationActivity(getContext());
    }
}
