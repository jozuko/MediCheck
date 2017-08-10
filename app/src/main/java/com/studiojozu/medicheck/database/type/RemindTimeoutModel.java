package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 通知の繰り返しタイムアウトを表す型クラス
 */
public class RemindTimeoutModel implements IDbType<Integer> {

    public enum RemindTimeoutType {
        MINUTE_1(1),
        MINUTE_5(5),
        MINUTE_10(10),
        MINUTE_15(15),
        MINUTE_30(30),
        HOUR_1(60),
        HOUR_2(60 * 2),
        HOUR_6(60 * 6),
        HOUR_9(60 * 9),
        HOUR_12(60 * 12),
        HOUR_24(60 * 24);

        private final int _timeoutMinutes;

        RemindTimeoutType(int timeoutMinutes) {
            _timeoutMinutes = timeoutMinutes;
        }

        static int valueOfTimeoutMinutes(RemindTimeoutType type) {
            for (RemindTimeoutType remindTimeType : values()) {
                if (remindTimeType == type)
                    return remindTimeType._timeoutMinutes;
            }
            return 0;
        }

        static RemindTimeoutType typeOfTimeoutMinute(int timeoutMinutes) {
            for (RemindTimeoutType remindTimeType : values()) {
                if (remindTimeType._timeoutMinutes == timeoutMinutes)
                    return remindTimeType;
            }
            return RemindTimeoutType.HOUR_24;
        }
    }

    @NonNull
    private final RemindTimeoutType _value;

    public RemindTimeoutModel(@NonNull RemindTimeoutType timeoutType) {
        _value = timeoutType;
    }

    public RemindTimeoutModel(int timeoutMinute) {
        _value = RemindTimeoutType.typeOfTimeoutMinute(timeoutMinute);
    }

    public Integer getDbValue() {
        return _value._timeoutMinutes;
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
