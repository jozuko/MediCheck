package com.studiojozu.medicheck.domain.model.person;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class PersonNameType extends TextType implements Cloneable {

    public PersonNameType() {
        super("");
    }

    public PersonNameType(@Nullable Object value) {
        super(value);
    }

    @Override
    public PersonNameType clone() {
        try {
            return (PersonNameType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
