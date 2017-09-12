package com.studiojozu.medicheck.resource.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.studiojozu.medicheck.application.AlarmService;

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
        AlarmService alarmController = new AlarmService(context);
        alarmController.resetAlarm();
    }
}
