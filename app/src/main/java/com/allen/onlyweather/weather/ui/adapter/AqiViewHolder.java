package com.allen.onlyweather.weather.ui.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.adapter.BaseRecyclerAdapter;
import com.allen.onlyweather.common.adapter.BaseViewHolder;
import com.allen.onlyweather.common.customview.LevelView;
import com.allen.onlyweather.utils.Check;
import com.allen.onlyweather.utils.UIUtil;
import com.allen.onlyweather.weather.entity.AQI;
import com.allen.onlyweather.weather.entity.Suggestion;
import com.silencedut.expandablelayout.ExpandableLayout;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Allen.
 */

public class AqiViewHolder extends BaseViewHolder<AqiData> {

    private static final int EXPAND_DURATION = 300;

    private static final int[] COlORS_ID = {R.color.green500, R.color.yellow500, R.color.orange500, R.color.red400, R.color.purple500, R.color.red900};
    private static final int[] AQI_LEVELS = {50, 100, 150, 200, 300, 500};
    private static final int[] PM2_5_LEVELS = {35, 75, 115, 150, 250, 500};
    private static final int[] PM10_LEVELS = {50, 150, 250, 350, 420, 600};

    @BindView(R.id.aqi_view)
    LevelView aqiView;
    @BindView(R.id.aqi_value)
    TextView aqiValue;
    @BindView(R.id.aqi_quality)
    TextView aqiQuality;
    @BindView(R.id.date_case)
    LinearLayout dateCase;
    @BindView(R.id.expand_icon)
    ImageView expandIcon;
    @BindView(R.id.base_info)
    LinearLayout baseInfo;
    @BindView(R.id.pm2_5_view)
    LevelView pm2_5View;
    @BindView(R.id.pm2_5_value)
    TextView pm2_5Value;
    @BindView(R.id.pm10_view)
    LevelView pm10View;
    @BindView(R.id.pm10_value)
    TextView pm10Value;
    @BindView(R.id.aqi_advice)
    TextView aqiAdvice;
    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;
    @BindView(R.id.rank)
    TextView rank;

    private boolean isExpanded;
    private Animator iconDownAnimator;
    private Animator iconUpAnimator;

    public AqiViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(AqiData data, int position) {
        AQI aqi = data.aqi;
        Suggestion suggestion = data.suggestion;
        if (Check.isNull(aqi)) {
            return;
        }

        updateLevel(aqiView, aqiValue, Integer.valueOf(aqi.city.aqi));
        updateLevel(pm2_5View, pm2_5Value, Integer.valueOf(aqi.city.pm25));
        updateLevel(pm10View, pm10Value, Integer.valueOf(aqi.city.pm10));
        aqiQuality.setText(aqi.city.qlty);
        aqiAdvice.setText(suggestion.air.txt);

//        SpannableString rankSpannable = new SpannableString("空气质量超过全国86%的城市或地区");
//        rankSpannable.setSpan(new ForegroundColorSpan(UIUtil.getColor(getContext(), R.color.colorAccent)), 8, 11, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        rankSpannable.setSpan(new RelativeSizeSpan(1.3f), 8, 11, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        rank.setText(rankSpannable);
    }

    @Override
    public void initViews() {
        iconDownAnimator = generateAnimator(expandIcon, 0, 180);
        iconUpAnimator = generateAnimator(expandIcon, 180, 0);
        expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                isExpanded = b;
            }
        });

        aqiView.setColorLever(COlORS_ID, AQI_LEVELS);
        pm2_5View.setColorLever(COlORS_ID, PM2_5_LEVELS);
        pm10View.setColorLever(COlORS_ID, PM10_LEVELS);

    }

    @OnClick(R.id.expandable_layout)
    void animateIco() {
        if (isExpanded) {
            iconUpAnimator.start();
        } else {
            iconDownAnimator.start();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_aqi;
    }

    private Animator generateAnimator(View target, float start, float end) {
        return ObjectAnimator.ofFloat(target, "rotation", start, end).setDuration(EXPAND_DURATION);
    }

    private void updateLevel(LevelView levelView, TextView valueText, int value) {
        levelView.setCurrentValue(value);
        valueText.setText(String.valueOf(value));
        valueText.setTextColor(levelView.getSectionColor());
    }

}
