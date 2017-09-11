package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class TimetableNameType extends TextType implements Cloneable {

    public TimetableNameType() {
        super("");
    }

    public TimetableNameType(@Nullable Object value) {
        super(value);
    }

    @Override
    public TimetableNameType clone() {
        try {
            return (TimetableNameType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
