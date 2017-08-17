package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 通知の繰り返し間隔を表す型クラス
 */
public class RemindIntervalType extends ADbType<Integer> implements Comparable<RemindIntervalType> {

    @NonNull
    private final RemindIntervalPattern mValue;

    public RemindIntervalType(@NonNull RemindIntervalPattern intervalType) {
        mValue = intervalType;
    }

    public RemindIntervalType(int intervalMinutes) {
        mValue = RemindIntervalPattern.typeOfIntervalMinute(intervalMinutes);
    }

    @Override
    public Integer getDbValue() {
        return RemindIntervalPattern.valueOfIntervalMinutes(mValue);
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull RemindIntervalType target) {
        return mValue.compareTo(target.mValue);
    }

    public enum RemindIntervalPattern {
        MINUTE_1(1),
        MINUTE_5(5),
        MINUTE_10(10),
        MINUTE_15(15),
        MINUTE_30(30),
        HOUR_1(60);

        private final int mIntervalMinutes;

        RemindIntervalPattern(int intervalMinutes) {
            mIntervalMinutes = intervalMinutes;
        }

        static int valueOfIntervalMinutes(RemindIntervalPattern type) {
            for (RemindIntervalPattern remindIntervalType : values()) {
                if (remindIntervalType == type)
                    return remindIntervalType.mIntervalMinutes;
            }
            return 0;
        }

        @NonNull
        static RemindIntervalPattern typeOfIntervalMinute(int intervalMinutes) {
            for (RemindIntervalPattern remindIntervalType : values()) {
                if (remindIntervalType.mIntervalMinutes == intervalMinutes)
                    return remindIntervalType;
            }
            return RemindIntervalPattern.MINUTE_5;
        }
    }
}
