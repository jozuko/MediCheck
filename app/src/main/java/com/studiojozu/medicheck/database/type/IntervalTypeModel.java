package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 通知の繰り返し間隔を表す型クラス
 */
public class IntervalTypeModel extends ADbType<Integer> {

    public enum DateIntervalType {
        DAYS,
        MONTH;

        static DateIntervalType typeOf(int dbValue) {
            for (DateIntervalType dateIntervalType : values()) {
                if (dateIntervalType.ordinal() == dbValue)
                    return dateIntervalType;
            }
            return DateIntervalType.DAYS;
        }

    }

    @NonNull
    private final DateIntervalType _value;

    public IntervalTypeModel(@NonNull DateIntervalType intervalType) {
        _value = intervalType;
    }

    public IntervalTypeModel(int dbValue) {
        _value = DateIntervalType.typeOf(dbValue);
    }

    @Override
    public Integer getDbValue() {
        return _value.ordinal();
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }
}
