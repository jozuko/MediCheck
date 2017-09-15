package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;
import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.TimeType;

import java.util.Calendar;

class ReminderDatetimeType extends DatetimeType<ReminderDatetimeType> {

    ReminderDatetimeType(@NonNull Object millisecond) {
        super(millisecond);
    }

    ReminderDatetimeType(@NonNull DateType dateModel, @NonNull TimeType timeModel) {
        super(dateModel, timeModel);
    }

    @Override
    @NonNull
    public ReminderDatetimeType addMinute(int minute) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MINUTE, minute);

        return new ReminderDatetimeType(calendar.getTimeInMillis());
    }

    @Override
    @NonNull
    public ReminderDatetimeType addDay(int days) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return new ReminderDatetimeType(calendar.getTimeInMillis());
    }

    @Override
    @NonNull
    public ReminderDatetimeType addMonth(int months) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.MONTH, months);

        return new ReminderDatetimeType(calendar.getTimeInMillis());
    }
}
