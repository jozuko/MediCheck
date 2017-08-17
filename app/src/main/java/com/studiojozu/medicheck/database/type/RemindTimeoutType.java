package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 通知の繰り返しタイムアウトを表す型クラス
 */
public class RemindTimeoutType extends ADbType<Integer> implements Comparable<RemindTimeoutType> {

    @NonNull
    private final RemindTimeoutPattern mValue;

    public RemindTimeoutType(@NonNull RemindTimeoutPattern timeoutType) {
        mValue = timeoutType;
    }

    public RemindTimeoutType(int timeoutMinute) {
        mValue = RemindTimeoutPattern.typeOfTimeoutMinute(timeoutMinute);
    }

    @Override
    public Integer getDbValue() {
        return RemindTimeoutPattern.valueOfTimeoutMinutes(mValue);
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull RemindTimeoutType target) {
        return mValue.compareTo(target.mValue);
    }

    /**
     * パラメータnowに指名した時刻が、リマインド機能の限界時間を超えているか？
     *
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド機能の限界時間を超えている場合はtrueを返却する
     */
    public boolean isTimeout(@NonNull DateTimeType now, @NonNull DateType scheduleDate, @NonNull TimeType scheduleTime) {
        DateTimeType reminderDateTime = new DateTimeType(scheduleDate, scheduleTime).add(getDbValue());
        return (reminderDateTime.compareTo(now) < 0);
    }

    public enum RemindTimeoutPattern {
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

        private final int mTimeoutMinutes;

        RemindTimeoutPattern(int timeoutMinutes) {
            mTimeoutMinutes = timeoutMinutes;
        }

        static int valueOfTimeoutMinutes(RemindTimeoutPattern type) {
            for (RemindTimeoutPattern remindTimeType : values()) {
                if (remindTimeType == type)
                    return remindTimeType.mTimeoutMinutes;
            }
            return 0;
        }

        static RemindTimeoutPattern typeOfTimeoutMinute(int timeoutMinutes) {
            for (RemindTimeoutPattern remindTimeType : values()) {
                if (remindTimeType.mTimeoutMinutes == timeoutMinutes)
                    return remindTimeType;
            }
            return RemindTimeoutPattern.HOUR_24;
        }
    }
}
