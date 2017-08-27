package com.studiojozu.medicheck.type.parson;

import android.support.annotation.Nullable;

import com.studiojozu.medicheck.type.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class ParsonNameType extends TextType implements Cloneable {

    public ParsonNameType() {
        super("");
    }

    public ParsonNameType(@Nullable Object value) {
        super(value);
    }

    @Override
    public ParsonNameType clone() {
        try {
            return (ParsonNameType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
