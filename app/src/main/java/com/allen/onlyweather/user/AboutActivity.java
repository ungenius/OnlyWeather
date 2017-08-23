package com.allen.onlyweather.user;

import android.content.Context;
import android.content.Intent;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.BaseActivity;

/**
 * Created by Allen.
 */

public class AboutActivity extends BaseActivity {

    public static void navigationActivity(Context from) {
        Intent intent = new Intent(from, AboutActivity.class);
        from.startActivity(intent);
    }
    @Override
    public int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews() {

    }
}
