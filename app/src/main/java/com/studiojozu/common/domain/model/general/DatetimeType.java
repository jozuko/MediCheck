package com.studiojozu.common.domain.model.general;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 時間を表す型クラス
 */
public class DatetimeType extends ADbType<Long> implements Cloneable, Comparable<DatetimeType> {

    @NonNull
    private final Calendar mValue;

    public DatetimeType(@NonNull Object millisecond) {
        long timeInMillis;
        if (millisecond instanceof Calendar)
            timeInMillis = ((Calendar) millisecond).getTimeInMillis();
        else if ((millisecond instanceof Long))
            timeInMillis = (long) millisecond;
        else
            throw new IllegalArgumentException("unknown type.");

        mValue = Calendar.getInstance();
        mValue.setTimeInMillis(timeInMillis);
        mValue.set(Calendar.SECOND, 0);
        mValue.set(Calendar.MILLISECOND, 0);
    }

    protected DatetimeType(int year, int month, int date, int hourOfDay, int minute) {
        mValue = Calendar.getInstance();
        mValue.set(year, month - 1, date, hourOfDay, minute, 0);
        mValue.set(Calendar.MILLISECOND, 0);
    }

    public DatetimeType(@NonNull DateType dateModel, @NonNull TimeType timeModel) {
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
    public int compareTo(@NonNull DatetimeType target) {
        return (getDbValue().compareTo(target.getDbValue()));
    }

    @Override
    public DatetimeType clone() {
        try {
            return (DatetimeType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    /**
     * フィールドが保持する日時に、パラメータのminuteを分として追加した値を返却する。
     *
     * @param minute 加算する分
     * @return パラメータ値加算後の値を保持するインスタンス
     */
    @NonNull
    public DatetimeType addMinute(int minute) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MINUTE, minute);

        return new DatetimeType(calendar.getTimeInMillis());
    }

    /**
     * フィールドが保持する日時に、パラメータのdayを日数として追加した値を返却する。
     *
     * @param days 加算する日数
     * @return パラメータ値加算後の値を保持するインスタンス
     */
    @NonNull
    public DatetimeType addDay(int days) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return new DatetimeType(calendar.getTimeInMillis());
    }

    /**
     * フィールドが保持する日時に、パラメータのmonthsを月数として追加した値を返却する。
     *
     * @param months 加算する月数
     * @return パラメータ値加算後の値を保持するインスタンス
     */
    @NonNull
    public DatetimeType addMonth(int months) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MONTH, months);

        return new DatetimeType(calendar.getTimeInMillis());
    }

    /**
     * フィールドが保持する日時とパラメータの示す日時の差分を分単位で返却する
     *
     * @param target 差分を求める日時
     * @return 差分
     */
    public long diffMinutes(@NonNull DatetimeType target) {
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTimeInMillis(target.getDbValue());

        long diff = getDbValue() - target.getDbValue();
        return (diff / (60 * 1000));
    }

    /**
     * クローンを生成し、時と分のみを置き換える
     *
     * @param hourOfDay 時
     * @param minute    分
     * @return 時分を置き換えたクローンインスタンス
     */
    public DatetimeType setHourMinute(int hourOfDay, int minute) {
        DatetimeType datetimeType = clone();
        datetimeType.mValue.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetimeType.mValue.set(Calendar.MINUTE, minute);

        return datetimeType;
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
