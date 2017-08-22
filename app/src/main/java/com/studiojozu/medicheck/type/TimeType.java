package com.studiojozu.medicheck.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 時間を表す型クラス
 */
public class TimeType extends ADbType<Long> implements Comparable<TimeType> {

    @NonNull
    private final Calendar mValue;

    public TimeType(Calendar value) {
        this(value.getTimeInMillis());
    }

    public TimeType(int hourOfDay, int minute) {
        this(Calendar.getInstance());
        mValue.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mValue.set(Calendar.MINUTE, minute);
    }

    public TimeType(long millisecond) {
        mValue = Calendar.getInstance();
        mValue.setTimeInMillis(millisecond);
        mValue.set(Calendar.YEAR, 2000);
        mValue.set(Calendar.MONTH, 0);
        mValue.set(Calendar.DAY_OF_MONTH, 1);
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
    public int compareTo(@NonNull TimeType timeModel) {
        return (getDbValue().compareTo(timeModel.getDbValue()));
    }

    /**
     * フィールド値と引数の時分を比較する。
     *
     * @param target 比較する時分
     * @return 一致する場合はtrueを返却する
     */
    public boolean equalsTime(@NonNull Calendar target) {
        if (mValue.get(Calendar.HOUR_OF_DAY) != target.get(Calendar.HOUR_OF_DAY)) return false;
        return mValue.get(Calendar.MINUTE) == target.get(Calendar.MINUTE);

    }

    /**
     * 画面表示用文字列を返却する
     *
     * @return 画面表示用文字列
     */
    public String getFormatValue() {
        DateFormat format = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        return format.format(mValue.getTime());
    }
}