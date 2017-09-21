package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.TextType;

public class MedicineUnitValueType extends TextType<MedicineUnitValueType> {

    private static final long serialVersionUID = 2054493100462525550L;

    public MedicineUnitValueType() {
        super("");
    }

    public MedicineUnitValueType(@Nullable Object value) {
        super(value);
    }
}
