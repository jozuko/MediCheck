package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class MedicineNameType extends TextType implements Cloneable {

    public MedicineNameType() {
        super("");
    }

    public MedicineNameType(@NonNull Object value) {
        super(value);
    }

    @Override
    public MedicineNameType clone() {
        try {
            return (MedicineNameType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
