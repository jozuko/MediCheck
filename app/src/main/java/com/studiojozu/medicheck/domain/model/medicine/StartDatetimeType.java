package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;
import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.TimeType;

import java.util.Calendar;

/**
 * 服用開始日時を管理するクラス
 */
public class StartDatetimeType extends DatetimeType implements Cloneable {

    public StartDatetimeType() {
        super(Calendar.getInstance().getTimeInMillis());
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
    public StartDatetimeType clone() {
        return (StartDatetimeType) super.clone();
    }
}
