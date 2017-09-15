package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.IdType;

/**
 * 薬IDを管理するクラス
 */
public class MedicineIdType extends IdType<MedicineIdType> {
    private static final long serialVersionUID = 6915190177094626179L;

    public MedicineIdType() {
        super();
    }

    public MedicineIdType(@NonNull Object value) {
        super(value);
    }
}
