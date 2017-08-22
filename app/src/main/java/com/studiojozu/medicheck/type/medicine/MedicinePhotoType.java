package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.PhotoType;

/**
 * 薬の写真を管理するクラス
 */
public class MedicinePhotoType extends PhotoType {
    public MedicinePhotoType() {
        super("");
    }

    public MedicinePhotoType(@NonNull Object value) {
        super(value);
    }
}
