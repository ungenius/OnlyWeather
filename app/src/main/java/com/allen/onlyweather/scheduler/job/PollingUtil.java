package com.allen.onlyweather.scheduler.job;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.utils.PreferencesUtil;

/**
 * Created by Allen.
 */

public class PollingUtil  {
    public static void startService(Context context, boolean allowPoll) {
        Class cls = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP ? JobScheduleService.class : AlarmService.class;
        context.startService(new Intent(context, cls));
        if (!allowPoll) {
            return;
        }

    }

    public static void startPolling(Context context) {
        Scheduler scheduler;
        long seconds = Constants.getSchedule(PreferencesUtil.get(Constants.POLLING_TIME, 0));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            scheduler = new JobWork();
            scheduler.startPolling(context, seconds, JobScheduleService.class, JobScheduleService.class.getSimpleName());
        } else {
            scheduler = new AlarmWork();
            scheduler.startPolling(context, seconds, AlarmService.class, AlarmService.class.getSimpleName());
        }
    }

    //停止轮询
    public static void stopPolling(Context context) {
        Scheduler scheduler;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            scheduler = new JobWork();
            scheduler.stopPolling(context, JobScheduleService.class, JobScheduleService.class.getSimpleName());
        } else {
            scheduler = new AlarmWork();
            scheduler.stopPolling(context, AlarmService.class, AlarmService.class.getSimpleName());
        }
    }
}
