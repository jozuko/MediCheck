package com.studiojozu.medicheck.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 時間を表す型クラス
 */
public class DateTimeType extends ADbType<Long> implements Comparable<DateTimeType> {

    @NonNull
    private final Calendar mValue;

    public DateTimeType(Calendar value) {
        this(value.getTimeInMillis());
    }

    public DateTimeType(int year, int month, int date, int hourOfDay, int minute) {
        mValue = Calendar.getInstance();
        mValue.set(year, month - 1, date, hourOfDay, minute, 0);
        mValue.set(Calendar.MILLISECOND, 0);
    }

    public DateTimeType(long millisecond) {
        mValue = Calendar.getInstance();
        mValue.setTimeInMillis(millisecond);
        mValue.set(Calendar.SECOND, 0);
        mValue.set(Calendar.MILLISECOND, 0);
    }

    public DateTimeType(@NonNull DateType dateModel, @NonNull TimeType timeModel) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTimeInMillis(dateModel.getDbValue());

        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(timeModel.getDbValue());

        mValue = Calendar.getInstance();
        mValue.set(dateCalendar.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.DAY_OF_MONTH), timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), 0);
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
    public int compareTo(@NonNull DateTimeType target) {
        return (getDbValue().compareTo(target.getDbValue()));
    }

    /**
     * フィールドが保持する日時に、パラメータのminuteを分として追加した値を返却する。
     *
     * @param minute 加算する分
     * @return パラメータ値加算後の値を保持するインスタンス
     */
    @NonNull
    public DateTimeType add(int minute) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MINUTE, minute);

        return new DateTimeType(calendar);
    }

    /**
     * フィールドが保持する日時とパラメータの示す日時の差分を分単位で返却する
     *
     * @param target 差分を求める日時
     * @return 差分
     */
    public long diffMinutes(@NonNull DateTimeType target) {
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTimeInMillis(target.getDbValue());

        long diff = getDbValue() - target.getDbValue();
        return (diff / (60 * 1000));
    }

    /**
     * 画面表示用文字列を返却する
     *
     * @return 画面表示用文字列
     */
    public String getFormatValue() {
        DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return format.format(mValue.getTime());
    }
}
