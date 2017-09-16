package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.TimeType;

import java.util.Calendar;

/**
 * タイムテーブルの時刻を管理するクラス
 */
public class TimetableTimeType extends TimeType<TimetableTimeType> {
    private static final long serialVersionUID = 5169596478420128156L;

    public TimetableTimeType() {
        super(System.currentTimeMillis());
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
}
