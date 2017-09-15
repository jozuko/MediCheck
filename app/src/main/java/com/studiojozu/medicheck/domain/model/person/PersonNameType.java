package com.studiojozu.medicheck.domain.model.person;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class PersonNameType extends TextType<PersonNameType> {

    private static final long serialVersionUID = 5123729176127085413L;

    public PersonNameType() {
        super("");
    }

    public PersonNameType(@Nullable Object value) {
        super(value);
    }
}
