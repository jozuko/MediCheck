package com.studiojozu.medicheck.other;

import android.app.AlarmManager;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * アラーム管理クラス
 */
public class AlarmInformation {

    @NonNull
    private final AlarmManager _AlarmManager;

    public AlarmInformation(Context context) {
        _AlarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

}
