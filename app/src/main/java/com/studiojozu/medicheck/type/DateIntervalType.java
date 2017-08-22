package com.studiojozu.medicheck.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 通知の繰り返し間隔を表す型クラス
 */
public class DateIntervalType extends ADbType<Integer> implements Comparable<DateIntervalType> {

    @NonNull
    private final DateIntervalPattern mValue;

    public DateIntervalType(@NonNull DateIntervalPattern intervalType) {
        mValue = intervalType;
    }

    public DateIntervalType(int dbValue) {
        mValue = DateIntervalPattern.typeOf(dbValue);
    }

    @Override
    public Integer getDbValue() {
        return mValue.ordinal();
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull DateIntervalType target) {
        return getDbValue().compareTo(target.getDbValue());
    }

    public enum DateIntervalPattern {
        DAYS,
        MONTH;

        static DateIntervalPattern typeOf(int dbValue) {
            for (DateIntervalPattern dateIntervalType : values()) {
                if (dateIntervalType.ordinal() == dbValue)
                    return dateIntervalType;
            }
            return DateIntervalPattern.DAYS;
        }
    }
}
