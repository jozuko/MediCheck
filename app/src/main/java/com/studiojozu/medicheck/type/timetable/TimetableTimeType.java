package com.studiojozu.medicheck.type.timetable;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.TimeType;

/**
 * タイムテーブルの時刻を管理するクラス
 */
public class TimetableTimeType extends TimeType implements Cloneable {
    public TimetableTimeType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public TimetableTimeType(int hourOfDay, int minute) {
        super(hourOfDay, minute);
    }

    @Override
    public TimetableTimeType clone() {
        try {
            return (TimetableTimeType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
