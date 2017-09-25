package com.studiojozu.medicheck.domain.model.setting;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.R;

import java.util.TreeMap;

/**
 * 通知の繰り返し間隔を表す型クラス
 */
public class RemindIntervalType extends ADbType<Integer, RemindIntervalType> implements Comparable<RemindIntervalType> {

    private static final long serialVersionUID = -2200953636883165844L;
    @NonNull
    private final RemindIntervalPattern mValue;

    public RemindIntervalType() {
        this(RemindIntervalType.RemindIntervalPattern.MINUTE_5);
    }

    public RemindIntervalType(@NonNull Object intervalMinutes) {
        if (intervalMinutes instanceof RemindIntervalPattern)
            mValue = (RemindIntervalPattern) intervalMinutes;
        else if (intervalMinutes instanceof Long)
            mValue = RemindIntervalPattern.typeOfIntervalMinute(((Long) intervalMinutes).intValue());
        else if (intervalMinutes instanceof Integer)
            mValue = RemindIntervalPattern.typeOfIntervalMinute((int) intervalMinutes);
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
        for (RemindIntervalPattern remindIntervalPattern : RemindIntervalPattern.values()) {
            values.put(remindIntervalPattern.mIntervalMinutes, getDisplayValue(context, remindIntervalPattern.mIntervalMinutes));
        }

        return values;
    }

    /**
     * 表示する文字列を生成する
     *
     * @param context       アプリケーションコンテキスト
     * @param targetMinutes 時間(分)
     * @return 表示文言
     */
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

    @NonNull
    @Override
    public String getDisplayValue() {
        throw new RuntimeException("you need to call getDisplayValue(TakeIntervalModeType).");
    }

    @NonNull
    public String getDisplayValue(@NonNull Resources resources) {
        int stringRes = RemindIntervalPattern.valueOfStringRes(mValue);
        if (stringRes == 0)
            return "";

        return resources.getString(stringRes, RemindIntervalPattern.valueOfDisplayValue(mValue));
    }

    public enum RemindIntervalPattern {
        MINUTE_1(1, 1, R.string.interval_minute),
        MINUTE_5(5, 5, R.string.interval_minutes),
        MINUTE_10(10, 10, R.string.interval_minutes),
        MINUTE_15(15, 15, R.string.interval_minutes),
        MINUTE_30(30, 30, R.string.interval_minutes),
        HOUR_1(60, 1, R.string.interval_hour);

        private final int mIntervalMinutes;
        private final int mDisplayValue;
        @StringRes
        private final int mStringRes;

        RemindIntervalPattern(int intervalMinutes, int displayValue, @StringRes int stringRes) {
            mIntervalMinutes = intervalMinutes;
            mDisplayValue = displayValue;
            mStringRes = stringRes;
        }

        static int valueOfIntervalMinutes(RemindIntervalPattern type) {
            for (RemindIntervalPattern remindIntervalType : values()) {
                if (remindIntervalType == type)
                    return remindIntervalType.mIntervalMinutes;
            }
            return 0;
        }

        static int valueOfDisplayValue(RemindIntervalPattern type) {
            for (RemindIntervalPattern remindIntervalType : values()) {
                if (remindIntervalType == type)
                    return remindIntervalType.mDisplayValue;
            }
            return 0;
        }

        @StringRes
        static int valueOfStringRes(RemindIntervalPattern type) {
            for (RemindIntervalPattern remindIntervalType : values()) {
                if (remindIntervalType == type)
                    return remindIntervalType.mStringRes;
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
