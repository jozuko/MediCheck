package com.studiojozu.medicheck.resource.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.studiojozu.medicheck.application.AlarmController;

/**
 * お薬アラームのリセットを行うためのBroadcastReceiver
 */
public class SystemInfoBroadcastReceiver extends BroadcastReceiver {
    /**
     * 下記アクションを受信し、アラームの再設定を行う。
     * <ol>
     * <li>"android.intent.action.BOOT_COMPLETED"</li>
     * <li>"android.intent.action.ACTION_MY_PACKAGE_REPLACED"</li>
     * <li>"android.intent.action.TIME_SET"</li>
     * <li>"android.intent.action.TIMEZONE_CHANGED"</li>
     * <li>"android.intent.action.DATE_CHANGED"</li>
     * </ol>
     *
     * @param context アプリケーションコンテキスト
     * @param intent  Broadcastの引数
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED) ||
                action.equals(Intent.ACTION_MY_PACKAGE_REPLACED) ||
                action.equals(Intent.ACTION_TIME_CHANGED) ||
                action.equals(Intent.ACTION_TIMEZONE_CHANGED) ||
                action.equals(Intent.ACTION_DATE_CHANGED)) {

            AlarmController alarmController = new AlarmController(context);
            alarmController.resetAlarm();
        }
    }
}
