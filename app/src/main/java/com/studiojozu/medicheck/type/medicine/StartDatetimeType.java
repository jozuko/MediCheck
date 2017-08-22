package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.DateType;
import com.studiojozu.medicheck.type.general.DatetimeType;
import com.studiojozu.medicheck.type.general.TimeType;

/**
 * 服用開始日時を管理するクラス
 */
public class StartDatetimeType extends DatetimeType {

    public StartDatetimeType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public StartDatetimeType(int year, int month, int date, int hourOfDay, int minute) {
        super(year, month, date, hourOfDay, minute);
    }

    public StartDatetimeType(@NonNull DateType dateModel, @NonNull TimeType timeModel) {
        super(dateModel, timeModel);
    }
}
