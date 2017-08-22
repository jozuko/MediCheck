package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.IdType;

/**
 * 薬IDを管理するクラス
 */
public class MedicineIdType extends IdType implements Cloneable {
    public MedicineIdType() {
        super(UNDEFINED_ID);
    }

    public MedicineIdType(@NonNull Object value) {
        super(value);
    }

    @Override
    public MedicineIdType clone() {
        try {
            return (MedicineIdType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
