package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class MedicineNameType extends TextType<MedicineNameType> {
    private static final long serialVersionUID = 7689473074804188066L;

    public MedicineNameType() {
        super("");
    }

    public MedicineNameType(@Nullable Object value) {
        super(value);
    }
}
