package com.studiojozu.medicheck.model;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 時間を表す型クラス
 */
public class TimeModel {

    @NonNull
    private final Calendar _value;

    public TimeModel(Calendar value) {
        _value = Calendar.getInstance();
        _value.set(2000, 1, 1, _value.get(Calendar.HOUR_OF_DAY), _value.get(Calendar.MINUTE), 0);
        _value.set(Calendar.MILLISECOND, 0);
    }

    public TimeModel(int hourOfDay, int minute) {
        this(Calendar.getInstance());
        _value.set(Calendar.HOUR_OF_DAY, hourOfDay);
        _value.set(Calendar.MINUTE, minute);
    }

    public String getTimeFormatValue() {
        DateFormat format = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        return format.format(_value.getTime());
    }

    public long getDbValue() {
        return _value.getTimeInMillis();
    }

}
