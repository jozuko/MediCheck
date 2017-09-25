package com.studiojozu.medicheck.domain.model.setting;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

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
        else if (timeoutMinute instanceof Long)
            mValue = RemindTimeoutPattern.typeOfTimeoutMinute(((Long) timeoutMinute).intValue());
        else if (timeoutMinute instanceof Integer)
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

    @NonNull
    @Override
    public String getDisplayValue() {
        throw new RuntimeException("you need to call getDisplayValue(TakeIntervalModeType).");
    }

    @NonNull
    public String getDisplayValue(@NonNull Resources resources) {
        int stringRes = RemindTimeoutPattern.valueOfStringRes(mValue);
        if (stringRes == 0)
            return "";

        return resources.getString(stringRes, RemindTimeoutPattern.valueOfDisplayValue(mValue));
    }

    public enum RemindTimeoutPattern {
        MINUTE_1(1, 1, R.string.interval_minute),
        MINUTE_5(5, 5, R.string.interval_minutes),
        MINUTE_10(10, 10, R.string.interval_minutes),
        MINUTE_15(15, 15, R.string.interval_minutes),
        MINUTE_30(30, 30, R.string.interval_minutes),
        HOUR_1(60, 1, R.string.interval_hour),
        HOUR_2(60 * 2, 2, R.string.interval_hours),
        HOUR_6(60 * 6, 6, R.string.interval_hours),
        HOUR_9(60 * 9, 9, R.string.interval_hours),
        HOUR_12(60 * 12, 12, R.string.interval_hours),
        HOUR_24(60 * 24, 24, R.string.interval_hours);

        private final int mTimeoutMinutes;
        private final int mDisplayValue;
        @StringRes
        private final int mStringRes;

        RemindTimeoutPattern(int timeoutMinutes, int displayValue, @StringRes int stringRes) {
            mTimeoutMinutes = timeoutMinutes;
            mDisplayValue = displayValue;
            mStringRes = stringRes;
        }

        static int valueOfTimeoutMinutes(RemindTimeoutPattern type) {
            for (RemindTimeoutPattern remindTimeoutPattern : values()) {
                if (remindTimeoutPattern == type)
                    return remindTimeoutPattern.mTimeoutMinutes;
            }
            return 0;
        }

        @NonNull
        static String valueOfDisplayValue(RemindTimeoutPattern type) {
            for (RemindTimeoutPattern remindTimeoutPattern : values()) {
                if (remindTimeoutPattern == type)
                    return String.valueOf(remindTimeoutPattern.mDisplayValue);
            }
            return "0";
        }

        @StringRes
        static int valueOfStringRes(RemindTimeoutPattern type) {
            for (RemindTimeoutPattern remindTimeoutPattern : values()) {
                if (remindTimeoutPattern == type)
                    return remindTimeoutPattern.mStringRes;
            }
            return 0;
        }

        static RemindTimeoutPattern typeOfTimeoutMinute(int timeoutMinutes) {
            for (RemindTimeoutPattern remindTimeoutPattern : values()) {
                if (remindTimeoutPattern.mTimeoutMinutes == timeoutMinutes)
                    return remindTimeoutPattern;
            }
            return RemindTimeoutPattern.HOUR_24;
        }
    }
}
