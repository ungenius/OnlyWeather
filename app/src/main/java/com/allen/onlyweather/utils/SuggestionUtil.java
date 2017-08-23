package com.allen.onlyweather.utils;

import com.allen.onlyweather.weather.entity.LifeIndex;
import com.allen.onlyweather.weather.entity.Suggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen.
 */

public class SuggestionUtil {
    public static List<LifeIndex> suggestionToList(Suggestion suggestion) {
        List<LifeIndex> lifeIndexList = new ArrayList<>();
        lifeIndexList.add(new LifeIndex("空气", suggestion.air.brf, suggestion.air.txt));
        lifeIndexList.add(new LifeIndex("舒适度", suggestion.comf.brf, suggestion.comf.txt));
        lifeIndexList.add(new LifeIndex("洗车", suggestion.cw.brf, suggestion.cw.txt));
        lifeIndexList.add(new LifeIndex("穿衣", suggestion.drsg.brf, suggestion.drsg.txt));
        lifeIndexList.add(new LifeIndex("感冒", suggestion.flu.brf, suggestion.flu.txt));
        lifeIndexList.add(new LifeIndex("运动", suggestion.sport.brf, suggestion.sport.txt));
        lifeIndexList.add(new LifeIndex("旅游", suggestion.trav.brf, suggestion.trav.txt));
        lifeIndexList.add(new LifeIndex("紫外线", suggestion.uv.brf, suggestion.uv.txt));
        return lifeIndexList;
    }
}
