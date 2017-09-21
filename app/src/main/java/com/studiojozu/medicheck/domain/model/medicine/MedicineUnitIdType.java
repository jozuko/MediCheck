package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.IdType;

/**
 * 薬IDを管理するクラス
 */
public class MedicineUnitIdType extends IdType<MedicineUnitIdType> {

    private static final long serialVersionUID = -4014725377594558521L;

    public MedicineUnitIdType() {
        super();
    }

    public MedicineUnitIdType(@NonNull Object value) {
        super(value);
    }
}
