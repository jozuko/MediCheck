package com.studiojozu.medicheck.type.medicine;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.ADbType;

/**
 * 薬の服用間隔（日or月）タイプを管理するクラス
 */
public class TakeIntervalModeType extends ADbType<Integer> implements Comparable<TakeIntervalModeType> {

    @NonNull
    private final DateIntervalPattern mValue;

    public TakeIntervalModeType(@NonNull Object dbValue) {
        if (dbValue instanceof DateIntervalPattern)
            mValue = (DateIntervalPattern) dbValue;
        else if ((dbValue instanceof Long) || (dbValue instanceof Integer))
            mValue = DateIntervalPattern.typeOf((int) dbValue);
        else
            throw new IllegalArgumentException("unknown type.");
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
    public int compareTo(@NonNull TakeIntervalModeType target) {
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
