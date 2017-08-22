package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.IdType;

/**
 * 薬IDを管理するクラス
 */
public class MedicineIdType extends IdType {
    public MedicineIdType() {
        super(UNDEFINED_ID);
    }

    public MedicineIdType(@NonNull Object value) {
        super(value);
    }
}
