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
    private final AlarmManager _alarmManager;

    @NonNull
    private final Context _context;

    public AlarmController(Context context) {
        _context = context.getApplicationContext();
        _alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

    void resetAlarm() {
        clearAlarm();
        setAlarm();
    }

    private void clearAlarm() {
        PendingIntent pendingIntent = createAlarmIntent();
        pendingIntent.cancel();
        _alarmManager.cancel(pendingIntent);
    }

    private void setAlarm() {
        PendingIntent pendingIntent = createAlarmIntent();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MINUTE, 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            return;
        }
        _alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private PendingIntent createAlarmIntent() {
        Intent intent = new Intent(_context, AlarmBroadcastReceiver.class);
        return PendingIntent.getBroadcast(_context, REQUEST_CODE_MEDICINE_ALARM, intent, 0);
    }
}
