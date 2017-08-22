package com.studiojozu.medicheck.type.setting;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.DateTimeType;
import com.studiojozu.medicheck.type.DateType;
import com.studiojozu.medicheck.type.TimeType;

import java.util.Map;
import java.util.TreeMap;

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

    /**
     * 選択肢のIDとなる分とそれに対応する表示文字列をMapで返却する
     *
     * @param context アプリケーションコンテキスト
     * @return 選択肢のIDとなる分とそれに対応する表示文字列のMap
     */
    @NonNull
    public static TreeMap<Integer, String> getAllValues(@NonNull Context context) {
        TreeMap<Integer, String> values = new TreeMap<>();
        for(RemindTimeoutPattern remindTimeoutPattern : RemindTimeoutPattern.values()) {
            values.put(remindTimeoutPattern.mTimeoutMinutes, getDisplayValue(context, remindTimeoutPattern.mTimeoutMinutes));
        }

        return values;
    }

    @NonNull
    private static String getDisplayValue(@NonNull Context context, int targetMinutes) {
        if(targetMinutes < 60) return targetMinutes + context.getResources().getString(R.string.label_minutes);
        if(targetMinutes % 60 == 0) return (targetMinutes % 60) + context.getResources().getString(R.string.label_hours);

        int hours = targetMinutes / 60;
        int minutes = targetMinutes % 60;

        return hours + context.getResources().getString(R.string.label_hours) + minutes + context.getResources().getString(R.string.label_minutes);
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
