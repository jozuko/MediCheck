package com.studiojozu.medicheck.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * アラーム管理クラス
 */
public class AlarmController {

    private final int REQUEST_CODE_MEDICINE_ALARM = 1;

    @NonNull
    private final AlarmManager mAlarmManager;

    @NonNull
    private final Context mContext;

    public AlarmController(Context context) {
        mContext = context.getApplicationContext();
        mAlarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

    void resetAlarm() {
        clearAlarm();
        setAlarm();
    }

    private void clearAlarm() {
        PendingIntent pendingIntent = createAlarmIntent();
        pendingIntent.cancel();
        mAlarmManager.cancel(pendingIntent);
    }

    private void setAlarm() {
        PendingIntent pendingIntent = createAlarmIntent();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MINUTE, 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            return;
        }
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private PendingIntent createAlarmIntent() {
        Intent intent = new Intent(mContext, AlarmBroadcastReceiver.class);
        return PendingIntent.getBroadcast(mContext, REQUEST_CODE_MEDICINE_ALARM, intent, 0);
    }
}
