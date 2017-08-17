package com.studiojozu.medicheck.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * 投薬アラームを受信して通知を表示するBroadcastReceiver
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_RESET_ALARM = "com.studiojozu.medicheck.alarm.medicine_alarm";

    @NonNull
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context.getApplicationContext();

        resetAlarm();

        MedicineAlarm medicineAlarm = new MedicineAlarm(mContext);
        medicineAlarm.showNotification();
    }

    private void resetAlarm() {
        AlarmController alarmController = new AlarmController(mContext);
        alarmController.resetAlarm();
    }
}
