package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.BooleanType;

public class MedicineNeedAlarmType extends BooleanType<MedicineNeedAlarmType> {
    private static final long serialVersionUID = 3912664133628844098L;

    public MedicineNeedAlarmType() {
        super(true);
    }

    public MedicineNeedAlarmType(boolean value) {
        super(value);
    }

    public MedicineNeedAlarmType(@NonNull Object value) {
        super(value);
    }
}
