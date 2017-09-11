package com.studiojozu.medicheck.domain.model.setting;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.R;

import java.util.TreeMap;

/**
 * 通知の繰り返し間隔を表す型クラス
 */
public class RemindIntervalType extends ADbType<Integer> implements Comparable<RemindIntervalType>, Cloneable {

    @NonNull
    private final RemindIntervalPattern mValue;

    public RemindIntervalType(@NonNull Object intervalMinutes) {
        if (intervalMinutes instanceof RemindIntervalPattern)
            mValue = (RemindIntervalPattern) intervalMinutes;
        else if ((intervalMinutes instanceof Long) || (intervalMinutes instanceof Integer))
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

    @Override
    public RemindIntervalType clone() {
        try {
            return (RemindIntervalType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
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
