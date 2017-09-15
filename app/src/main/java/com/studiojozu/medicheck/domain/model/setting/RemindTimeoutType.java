package com.studiojozu.medicheck.domain.model.setting;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.common.domain.model.general.DateType;
import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.TimeType;
import com.studiojozu.medicheck.R;

import java.util.TreeMap;

/**
 * 通知の繰り返しタイムアウトを表す型クラス
 */
public class RemindTimeoutType extends ADbType<Integer, RemindTimeoutType> implements Comparable<RemindTimeoutType> {

    private static final long serialVersionUID = 2910275487868116214L;
    @NonNull
    private final RemindTimeoutPattern mValue;

    public RemindTimeoutType() {
        this(RemindTimeoutType.RemindTimeoutPattern.HOUR_24);
    }

    public RemindTimeoutType(@NonNull Object timeoutMinute) {
        if (timeoutMinute instanceof RemindTimeoutPattern)
            mValue = (RemindTimeoutPattern) timeoutMinute;
        else if ((timeoutMinute instanceof Long) || (timeoutMinute instanceof Integer))
            mValue = RemindTimeoutPattern.typeOfTimeoutMinute((int) timeoutMinute);
        else
            throw new IllegalArgumentException("unknown type");
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
        for (RemindTimeoutPattern remindTimeoutPattern : RemindTimeoutPattern.values()) {
            values.put(remindTimeoutPattern.mTimeoutMinutes, getDisplayValue(context, remindTimeoutPattern.mTimeoutMinutes));
        }

        return values;
    }

    @NonNull
    private static String getDisplayValue(@NonNull Context context, int targetMinutes) {
        if (targetMinutes < 60)
            return targetMinutes + context.getResources().getString(R.string.label_minutes);
        if (targetMinutes % 60 == 0)
            return (targetMinutes % 60) + context.getResources().getString(R.string.label_hours);

        int hours = targetMinutes / 60;
        int minutes = targetMinutes % 60;

        return hours + context.getResources().getString(R.string.label_hours) + minutes + context.getResources().getString(R.string.label_minutes);
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
    public boolean isTimeout(@NonNull DatetimeType now, @NonNull DateType scheduleDate, @NonNull TimeType scheduleTime) {
        ReminderDatetimeType reminderDateTime = new ReminderDatetimeType(scheduleDate, scheduleTime).addMinute(getDbValue());
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
