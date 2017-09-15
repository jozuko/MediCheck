package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;
import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.TimeType;

import java.util.Calendar;

/**
 * 服用した日時を管理するクラス
 */
public class TookDatetime extends DatetimeType<TookDatetime> {

    private static final long serialVersionUID = 4076300522326519800L;

    public TookDatetime() {
        this(Calendar.getInstance());
    }

    public TookDatetime(@NonNull Object millisecond) {
        super(millisecond);
    }

    public TookDatetime(int year, int month, int date, int hourOfDay, int minute) {
        super(year, month, date, hourOfDay, minute);
    }

    public TookDatetime(@NonNull DateType dateModel, @NonNull TimeType timeModel) {
        super(dateModel, timeModel);
    }

    @Override
    @NonNull
    public TookDatetime addMinute(int minute) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MINUTE, minute);

        return new TookDatetime(calendar.getTimeInMillis());
    }

    @Override
    @NonNull
    public TookDatetime addDay(int days) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return new TookDatetime(calendar.getTimeInMillis());
    }

    @Override
    @NonNull
    public TookDatetime addMonth(int months) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MONTH, months);

        return new TookDatetime(calendar.getTimeInMillis());
    }
}
