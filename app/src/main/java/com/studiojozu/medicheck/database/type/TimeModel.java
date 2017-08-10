package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 時間を表す型クラス
 */
public class TimeModel implements IDbType<Long> {

    @NonNull
    private final Calendar _value;

    public TimeModel(Calendar value) {
        this(value.getTimeInMillis());
    }

    public TimeModel(int hourOfDay, int minute) {
        this(Calendar.getInstance());
        _value.set(Calendar.HOUR_OF_DAY, hourOfDay);
        _value.set(Calendar.MINUTE, minute);
    }

    public TimeModel(long millisecond) {
        _value = Calendar.getInstance();
        _value.setTimeInMillis(millisecond);
        _value.set(Calendar.YEAR, 2000);
        _value.set(Calendar.MONTH, 0);
        _value.set(Calendar.DAY_OF_MONTH, 1);
        _value.set(Calendar.SECOND, 0);
        _value.set(Calendar.MILLISECOND, 0);
    }

    public String getFormatValue() {
        DateFormat format = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        return format.format(_value.getTime());
    }

    public Long getDbValue() {
        return _value.getTimeInMillis();
    }

    @Override
    public String getDbWhereValue() {
        return String.valueOf(getDbValue());
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

}
