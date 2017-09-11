package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.PhotoType;

/**
 * 薬の写真を管理するクラス
 */
public class MedicinePhotoType extends PhotoType implements Cloneable {
    public MedicinePhotoType() {
        super("");
    }

    public MedicinePhotoType(@NonNull Object value) {
        super(value);
    }

    @Override
    public MedicinePhotoType clone() {
        try {
            return (MedicinePhotoType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
