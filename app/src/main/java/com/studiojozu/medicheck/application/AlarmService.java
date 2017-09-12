package com.studiojozu.medicheck.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.resource.alarm.AlarmBroadcastReceiver;

import java.util.Calendar;

/**
 * アラーム管理クラス
 */
public class AlarmService {

    /** AlarmManagerに登録するAlarmのRequestCode */
    private final int REQUEST_CODE_MEDICINE_ALARM = 1;

    /** AlarmManagerインスタンス. */
    @NonNull
    private final AlarmManager mAlarmManager;

    /** アプリケーションコンテキスト */
    @NonNull
    private final Context mContext;

    public AlarmService(Context context) {
        mContext = context.getApplicationContext();
        mAlarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * アラームを再設定する。
     */
    public void resetAlarm() {
        clearAlarm();
        setAlarm();
    }

    /**
     * RequestCodeが{@link #REQUEST_CODE_MEDICINE_ALARM}であるすべてのアラームを削除する。
     */
    private void clearAlarm() {
        PendingIntent pendingIntent = createAlarmIntent();
        pendingIntent.cancel();
        mAlarmManager.cancel(pendingIntent);
    }

    /**
     * 1分後にAlarmを設定する。
     * Marshmallow以上の端末では、Dozeを無視して通知できるように設定する。
     */
    private void setAlarm() {
        PendingIntent pendingIntent = createAlarmIntent();
        Calendar calendar = getNowPlusOneMinute();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            return;
        }
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * アラームの通知先Intentを生成する
     *
     * @return アラームの通知先Intent
     */
    private PendingIntent createAlarmIntent() {
        Intent intent = new Intent(mContext, AlarmBroadcastReceiver.class);
        return PendingIntent.getBroadcast(mContext, REQUEST_CODE_MEDICINE_ALARM, intent, 0);
    }

    /**
     * 現在時刻+1分のCalendarインスタンスを生成する。
     * その際秒以下の値は0にセットする。
     *
     * @return 現在時刻+1分のCalendarインスタンス
     */
    private Calendar getNowPlusOneMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }
}
