package com.studiojozu.medicheck.type.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.DateType;

/**
 * 服用予定日を管理するクラス
 */
public class PlanDateType extends DateType {

    public PlanDateType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public PlanDateType(int year, int month, int day) {
        super(year, month, day);
    }
}
