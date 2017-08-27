package com.studiojozu.medicheck.type.timetable;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.DatetimeType;
import com.studiojozu.medicheck.type.general.TimeType;

import java.util.Calendar;

/**
 * タイムテーブルの時刻を管理するクラス
 */
public class TimetableTimeType extends TimeType implements Cloneable {
    public TimetableTimeType() {
        super(Calendar.getInstance().getTimeInMillis());
    }

    public TimetableTimeType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public TimetableTimeType(int hourOfDay, int minute) {
        super(hourOfDay, minute);
    }

    public DatetimeType replaceHourMinute(@NonNull DatetimeType datetimeType) {
        return datetimeType.setHourMinute(mValue.get(Calendar.HOUR_OF_DAY), mValue.get(Calendar.MINUTE));
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
