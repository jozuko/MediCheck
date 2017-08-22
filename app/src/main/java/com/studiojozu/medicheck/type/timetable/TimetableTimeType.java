package com.studiojozu.medicheck.type.timetable;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.TimeType;

/**
 * タイムテーブルの時刻を管理するクラス
 */
public class TimetableTimeType extends TimeType {
    public TimetableTimeType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public TimetableTimeType(int hourOfDay, int minute) {
        super(hourOfDay, minute);
    }
}
