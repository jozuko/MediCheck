package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日付を表す型クラス
 */
public class DateType extends ADbType<Long> implements Comparable<DateType> {

    @NonNull
    private final Calendar mValue;

    public DateType(Calendar value) {
        this(value.getTimeInMillis());
    }

    public DateType(int year, int month, int day) {
        mValue = Calendar.getInstance();
        mValue.set(year, month - 1, day, 0, 0, 0);
        mValue.set(Calendar.MILLISECOND, 0);
    }

    public DateType(long millisecond) {
        mValue = Calendar.getInstance();
        mValue.setTimeInMillis(millisecond);
        mValue.set(Calendar.HOUR_OF_DAY, 0);
        mValue.set(Calendar.MINUTE, 0);
        mValue.set(Calendar.SECOND, 0);
        mValue.set(Calendar.MILLISECOND, 0);
    }

    @Override
    public Long getDbValue() {
        return mValue.getTimeInMillis();
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull DateType dateModel) {
        return getDbValue().compareTo(dateModel.getDbValue());
    }

    /**
     * 画面表示用文字列を返却する
     *
     * @return 画面表示用文字列
     */
    public String getFormatValue() {
        DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        return format.format(mValue.getTime());
    }
}
