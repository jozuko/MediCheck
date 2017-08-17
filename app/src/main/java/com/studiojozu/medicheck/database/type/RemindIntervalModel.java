package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 通知の繰り返し間隔を表す型クラス
 */
public class RemindIntervalModel extends ADbType<Integer> {

    public enum RemindIntervalType {
        MINUTE_1(1),
        MINUTE_5(5),
        MINUTE_10(10),
        MINUTE_15(15),
        MINUTE_30(30),
        HOUR_1(60);

        private final int _intervalMinutes;

        RemindIntervalType(int intervalMinutes) {
            _intervalMinutes = intervalMinutes;
        }

        static int valueOfIntervalMinutes(RemindIntervalType type) {
            for (RemindIntervalType remindIntervalType : values()) {
                if (remindIntervalType == type)
                    return remindIntervalType._intervalMinutes;
            }
            return 0;
        }

        @NonNull
        static RemindIntervalType typeOfIntervalMinute(int intervalMinutes) {
            for (RemindIntervalType remindIntervalType : values()) {
                if (remindIntervalType._intervalMinutes == intervalMinutes)
                    return remindIntervalType;
            }
            return RemindIntervalType.MINUTE_5;
        }
    }

    @NonNull
    private final RemindIntervalType _value;

    public RemindIntervalModel(@NonNull RemindIntervalType intervalType) {
        _value = intervalType;
    }

    public RemindIntervalModel(int intervalMinutes) {
        _value = RemindIntervalType.typeOfIntervalMinute(intervalMinutes);
    }

    @Override
    public Integer getDbValue() {
        return _value._intervalMinutes;
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }
}
