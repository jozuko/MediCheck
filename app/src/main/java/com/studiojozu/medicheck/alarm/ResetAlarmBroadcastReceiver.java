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
