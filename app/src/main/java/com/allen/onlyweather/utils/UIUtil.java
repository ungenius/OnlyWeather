package com.allen.onlyweather.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.allen.onlyweather.R;

/**
 * Created by Allen.
 */

public class UIUtil {

    public static Drawable getDrawable(Context context, int drawableId) {
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static int getColor(Context context, int colorId) {
        return ContextCompat.getColor(context, colorId);
    }

    public static int dipToPx(Context context, int dpId) {
        return (int) context.getResources().getDimension(dpId);
    }

    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
