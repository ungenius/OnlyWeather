package com.allen.onlyweather.scheduler.job;

import android.content.Context;

/**
 * Created by Allen.
 */

public interface Scheduler {
    void startPolling(Context context, long seconds, Class<?> cls, String action);

    void stopPolling(Context context, Class<?> cls, String action);
}
