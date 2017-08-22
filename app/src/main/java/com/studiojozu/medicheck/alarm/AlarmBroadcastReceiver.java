package com.studiojozu.medicheck.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * 投薬アラームを受信して通知を表示するBroadcastReceiver
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    /** アプリケーションコンテキスト */
    @Nullable
    private Context mContext = null;

    /**
     * "com.studiojozu.medicheck.alarm.medicine_alarm"を受信し、Notificationを表示する。
     *
     * @param context アプリケーションコンテキスト
     * @param intent  Broadcast引数
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context.getApplicationContext();

        resetAlarm();
        showNotification();
    }

    /**
     * アラームのリセットを行う。
     */
    private void resetAlarm() {
        if (mContext == null) return;
        new AlarmController(mContext).resetAlarm();
    }

    /**
     * Notificationを表示する
     */
    private void showNotification() {
        if (mContext == null) return;
        new MedicineAlarm(mContext).showNotification();
    }
}
