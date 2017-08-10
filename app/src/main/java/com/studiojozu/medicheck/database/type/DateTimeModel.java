package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 時間を表す型クラス
 */
public class DateTimeModel implements IDbType<Long> {

    @NonNull
    private final Calendar _value;

    public DateTimeModel(Calendar value) {
        this(value.getTimeInMillis());
    }

    public DateTimeModel(int year, int month, int date, int hourOfDay, int minute) {
        _value = Calendar.getInstance();
        _value.set(year, month - 1, date, hourOfDay, minute, 0);
        _value.set(Calendar.MILLISECOND, 0);
    }

    public DateTimeModel(long millisecond) {
        _value = Calendar.getInstance();
        _value.setTimeInMillis(millisecond);
        _value.set(Calendar.SECOND, 0);
        _value.set(Calendar.MILLISECOND, 0);
    }

    public String getTimeFormatValue() {
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
