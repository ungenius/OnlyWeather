package com.allen.onlyweather.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.silencedut.router.Router;

import butterknife.ButterKnife;

/**
 * Created by Allen.
 */

public abstract class BaseActivity extends AppCompatActivity implements UIInit {


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase);
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.instance().register(this);
        initBeforeView();
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initViews();
    }


    @Override
    public void initBeforeView() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Router.instance().unregister(this);
    }
}
