package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.PhotoType;

/**
 * 薬の写真を管理するクラス
 */
public class MedicinePhotoType extends PhotoType<MedicinePhotoType> {
    private static final long serialVersionUID = 2920557811262773058L;

    public MedicinePhotoType() {
        super("");
    }

    public MedicinePhotoType(@NonNull Object value) {
        super(value);
    }
}
