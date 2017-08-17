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
    private Context _context;

    @Override
    public void onReceive(Context context, Intent intent) {
        _context = context.getApplicationContext();

        resetAlarm();

        MedicineAlarm medicineAlarm = new MedicineAlarm(_context);
        medicineAlarm.showNotification();
    }

    private void resetAlarm(){
        AlarmController alarmController = new AlarmController(_context);
        alarmController.resetAlarm();
    }
}
