package com.allen.onlyweather.user;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.onlyweather.R;
import com.allen.onlyweather.common.BaseActivity;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.scheduler.job.Scheduler;
import com.allen.onlyweather.utils.PreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Allen.
 */

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.update_schedule)
    TextView mUpdateSchedule;
    @BindView(R.id.new_version_tip)
    ImageView mNewVersionTip;
    @BindView(R.id.title)
    Toolbar mTitle;

    private AlertDialog.Builder mScheduleDialog;
    private String[] mScheduleKeys;

    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
    }

    public static void navigationActivity(Context packageContext) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        packageContext.startActivity(intent);
    }

    @Override
    public void initViews() {
        setSupportActionBar(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);
        mScheduleKeys = getResources().getStringArray(R.array.schedule);
        mScheduleDialog = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        mScheduleDialog.setTitle(R.string.update_allow);
        int scheduleWhich = PreferencesUtil.get(Constants.POLLING_TIME, 0);
        mUpdateSchedule.setText(mScheduleKeys[scheduleWhich]);

    }

    @OnClick({R.id.update_allow, R.id.mark, R.id.suggestion, R.id.update_version})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_allow:
                int whichSchedule = PreferencesUtil.get(Constants.POLLING_TIME, 0);
                mScheduleDialog.setSingleChoiceItems(R.array.schedule, whichSchedule, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferencesUtil.put(Constants.POLLING_TIME, which);
                        mUpdateSchedule.setText(mScheduleKeys[which]);
                        dialog.dismiss();
                    }
                });
                mScheduleDialog.show();
                break;
            case R.id.mark:
                openAppMarket();
                break;
            case R.id.suggestion:
                ContactActivity.navigationActivity(this);
                break;
            case R.id.update_version:
                if (mNewVersionTip.getVisibility() == View.VISIBLE) {

                } else {
                    Toast.makeText(this, "已是最新版本", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openAppMarket() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException anf) {
            Toast.makeText(this,"未找到相关应用",Toast.LENGTH_SHORT).show();
        }

    }
}
