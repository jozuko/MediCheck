package com.studiojozu.common.domain.model.general;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日付を表す型クラス
 */
public abstract class DateType<C extends DateType<C>> extends ADbType<Long, C> implements Comparable<DateType> {

    @NonNull
    protected final Calendar mValue;

    protected DateType(@NonNull Object millisecond) {
        if (millisecond instanceof Long) {
            mValue = Calendar.getInstance();
            mValue.setTimeInMillis((Long) millisecond);
        } else if (millisecond instanceof DatetimeType) {
            DatetimeType datetimeType = (DatetimeType) millisecond;
            mValue = Calendar.getInstance();
            mValue.setTimeInMillis(datetimeType.getDbValue());
        } else {
            throw new IllegalArgumentException("unknown type.");
        }

        mValue.set(Calendar.HOUR_OF_DAY, 0);
        mValue.set(Calendar.MINUTE, 0);
        mValue.set(Calendar.SECOND, 0);
        mValue.set(Calendar.MILLISECOND, 0);
    }

    protected DateType(int year, int month, int day) {
        mValue = Calendar.getInstance();
        mValue.set(year, month - 1, day, 0, 0, 0);
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
     * フィールド値と引数の年月日を比較する。
     *
     * @param target 比較する年月日
     * @return 一致する場合はtrueを返却する
     */
    public boolean equalsDate(@NonNull Calendar target) {
        if (mValue.get(Calendar.YEAR) != target.get(Calendar.YEAR)) return false;
        if (mValue.get(Calendar.MONTH) != target.get(Calendar.MONTH)) return false;
        return mValue.get(Calendar.DAY_OF_MONTH) == target.get(Calendar.DAY_OF_MONTH);

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

    public abstract C addDay(int days);
}
