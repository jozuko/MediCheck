package com.studiojozu.medicheck.type.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.DateType;
import com.studiojozu.medicheck.type.general.DatetimeType;
import com.studiojozu.medicheck.type.general.TimeType;

/**
 * 服用した日時を管理するクラス
 */
public class TookDatetime extends DatetimeType {

    public TookDatetime(@NonNull Object millisecond) {
        super(millisecond);
    }

    public TookDatetime(int year, int month, int date, int hourOfDay, int minute) {
        super(year, month, date, hourOfDay, minute);
    }

    public TookDatetime(@NonNull DateType dateModel, @NonNull TimeType timeModel) {
        super(dateModel, timeModel);
    }
}
