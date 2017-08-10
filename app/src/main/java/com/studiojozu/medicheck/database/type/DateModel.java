package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日付を表す型クラス
 */
public class DateModel implements IDbType<Long> {

    @NonNull
    private final Calendar _value;

    public DateModel(Calendar value) {
        this(value.getTimeInMillis());
    }

    public DateModel(int year, int month, int day) {
        _value = Calendar.getInstance();
        _value.set(year, month - 1, day, 0, 0, 0);
        _value.set(Calendar.MILLISECOND, 0);
    }

    public DateModel(long millisecond) {
        _value = Calendar.getInstance();
        _value.setTimeInMillis(millisecond);
        _value.set(Calendar.HOUR_OF_DAY, 0);
        _value.set(Calendar.MINUTE, 0);
        _value.set(Calendar.SECOND, 0);
        _value.set(Calendar.MILLISECOND, 0);
    }

    public String getFormatValue() {
        DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
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
