package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class MedicineNameType extends TextType implements Cloneable {

    public MedicineNameType() {
        super("");
    }

    public MedicineNameType(@Nullable Object value) {
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
