package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;
import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.TimeType;

import java.util.Calendar;

/**
 * 服用開始日時を管理するクラス
 */
public class StartDatetimeType extends DatetimeType<StartDatetimeType> {
    private static final long serialVersionUID = 7612988577243783674L;

    public StartDatetimeType() {
        super(System.currentTimeMillis());
    }

    public StartDatetimeType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public StartDatetimeType(int year, int month, int date, int hourOfDay, int minute) {
        super(year, month, date, hourOfDay, minute);
    }

    public StartDatetimeType(@NonNull DateType dateModel, @NonNull TimeType timeModel) {
        super(dateModel, timeModel);
    }

    @Override
    @NonNull
    public StartDatetimeType addMinute(int minute) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MINUTE, minute);

        return new StartDatetimeType(calendar.getTimeInMillis());
    }

    @Override
    @NonNull
    public StartDatetimeType addDay(int days) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return new StartDatetimeType(calendar.getTimeInMillis());
    }

    @Override
    @NonNull
    public StartDatetimeType addMonth(int months) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MONTH, months);

        return new StartDatetimeType(calendar.getTimeInMillis());
    }
}
