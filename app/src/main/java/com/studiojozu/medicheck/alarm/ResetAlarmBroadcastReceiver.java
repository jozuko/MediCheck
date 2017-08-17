package com.studiojozu.medicheck.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * お薬アラームのリセットを行うためのBroadcastReceiver
 */
public class ResetAlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_RESET_ALARM = "com.studiojozu.medicheck.alarm.reset_medicine_alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmController alarmController = new AlarmController(context);
        alarmController.resetAlarm();
    }
}
