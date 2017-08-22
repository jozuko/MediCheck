package com.studiojozu.medicheck.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * お薬アラームのリセットを行うためのBroadcastReceiver
 */
public class ResetAlarmBroadcastReceiver extends BroadcastReceiver {
    /**
     * 下記アクションを受信し、アラームの再設定を行う。
     * <ol>
     * <li>"android.intent.action.BOOT_COMPLETED"</li>
     * <li>"android.intent.action.ACTION_MY_PACKAGE_REPLACED"</li>
     * <li>"android.intent.action.TIME_SET"</li>
     * <li>"android.intent.action.TIMEZONE_CHANGED"</li>
     * <li>"android.intent.action.DATE_CHANGED"</li>
     * <li>"com.studiojozu.medicheck.alarm.reset_medicine_alarm"</li>
     * </ol>
     *
     * @param context アプリケーションコンテキスト
     * @param intent  Broadcastの引数
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmController alarmController = new AlarmController(context);
        alarmController.resetAlarm();
    }
}
