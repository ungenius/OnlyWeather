package com.allen.onlyweather.weather.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseAdapterData;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.utils.Check;
import com.allen.onlyweather.utils.SuggestionUtil;
import com.allen.onlyweather.weather.callbacks.WeatherCallbacks;
import com.allen.onlyweather.weather.entity.LifeIndex;
import com.silencedut.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Allen.
 */

public class SuggestionViewHolder extends BaseViewHolder<SuggestionData> implements WeatherCallbacks.LifeAdvice {

    private static final int[] LIFE_INDEXES_ICONIDS = {R.mipmap.air, R.mipmap.comfort , R.mipmap.wash_car, R.mipmap.clothing, R.mipmap.health, R.mipmap.sport_mode, R.mipmap.shopping, R.mipmap.protection};

    private BaseRecyclerAdapter mLifeAdapter;

    @BindView(R.id.life_index)
    RecyclerView rv_lifeIndex;
    @BindView(R.id.life_advice)
    TextView lifeAdvice;

    public SuggestionViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        Router.instance().register(this);
    }

    @Override
    public void initViews() {
        rv_lifeIndex.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mLifeAdapter = new BaseRecyclerAdapter(getContext());
        rv_lifeIndex.setAdapter(mLifeAdapter);
        mLifeAdapter.registerHolder(LifeItemViewHolder.class, R.layout.item_suggestion_item);

    }

    @Override
    public int getContentViewId() {
        return R.layout.item_suggestion;
    }

    @Override
    public void updateItem(SuggestionData data, int position) {
        List<LifeIndex> lifeIndexList = SuggestionUtil.suggestionToList(data.suggestion);
        List<LifeItemData> lifeItemDatas = new ArrayList<>();
        for (int i = 0; i < lifeIndexList.size(); i++) {
            lifeItemDatas.add(new LifeItemData(lifeIndexList.get(i), LIFE_INDEXES_ICONIDS[i]));
        }
        mLifeAdapter.setData(lifeItemDatas);
    }


    @OnClick(R.id.life_advice)
    void onClick() {
        Router.instance().getReceiver(WeatherCallbacks.LifeAdvice.class).lifeAdvice(getContext().getString(R.string.lifeIndexes), getContext().getString(R.string.lifeIndexes));
        lifeAdvice.setVisibility(View.GONE);
    }

    @Override
    public void lifeAdvice(String index, String advice) {
        lifeAdvice.setText(advice);
        lifeAdvice.setVisibility(View.VISIBLE);
    }


    private static final class LifeItemData implements BaseAdapterData {

        public LifeIndex lifeIndex;
        public int lifeIndexIconId = R.mipmap.sport_mode;

        public LifeItemData(LifeIndex lifeIndex, int lifeIndexIconId) {
            this.lifeIndex = lifeIndex;
            this.lifeIndexIconId = lifeIndexIconId;
        }

        public LifeIndex getLifeIndex() {
            return lifeIndex;
        }

        @Override
        public int getItemViewType() {
            return R.layout.item_suggestion_item;
        }
    }

    static final class LifeItemViewHolder extends BaseViewHolder<LifeItemData> {

        @BindView(R.id.life_index_icon)
        ImageView lifeIndexIcon;
        @BindView(R.id.life_type)
        TextView lifeType;
        @BindView(R.id.life_level)
        TextView lifeLevel;
        LifeIndex lifeIndex;

        public LifeItemViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
            super(itemView, baseRecyclerAdapter);
        }

        @Override
        public int getContentViewId() {
            return R.layout.item_suggestion_item;
        }

        @Override
        public void updateItem(LifeItemData data, int position) {
            lifeIndex = data.lifeIndex;
            if (Check.isNull(lifeIndex)) {
                return;
            }
            lifeType.setText(lifeIndex.name);
            lifeLevel.setText(lifeIndex.brf);
            lifeIndexIcon.setImageResource(data.lifeIndexIconId);

        }

        @OnClick(R.id.life_index_content)
        void onClick() {
            Router.instance().getReceiver(WeatherCallbacks.LifeAdvice.class).lifeAdvice(lifeIndex.getName(), lifeIndex.getTxt());

        }
    }





}
