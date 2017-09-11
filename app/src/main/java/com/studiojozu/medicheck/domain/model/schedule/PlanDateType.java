package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;

import java.util.Calendar;

/**
 * 服用予定日を管理するクラス
 */
public class PlanDateType extends DateType implements Cloneable {

    public PlanDateType() {
        this(Calendar.getInstance());
    }

    public PlanDateType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public PlanDateType(int year, int month, int day) {
        super(year, month, day);
    }

    @Override
    public PlanDateType clone() {
        try {
            return (PlanDateType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
