package com.allen.onlyweather.common;

import android.content.Context;

import com.allen.onlyweather.MyApplication;
import com.silencedut.router.Router;

/**
 * Created by Allen.
 */

public abstract class BasePresenter<T extends BaseView> {

    protected T mPresentView;
    private Context mContext = MyApplication.getContext();

    public Context getContext() {
        return mContext;
    }

    public BasePresenter(T presenterView) {
        attachView(presenterView);
    }

    private void attachView(T presenterView) {
        mPresentView = presenterView;
        Router.instance().register(this);
    }

    public void onDetchView() {
        mPresentView = null;
        Router.instance().unregister(this);
    }


}
