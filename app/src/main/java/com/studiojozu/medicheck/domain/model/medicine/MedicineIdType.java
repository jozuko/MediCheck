package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.IdType;

/**
 * 薬IDを管理するクラス
 */
public class MedicineIdType extends IdType implements Cloneable {
    public MedicineIdType() {
        super();
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
