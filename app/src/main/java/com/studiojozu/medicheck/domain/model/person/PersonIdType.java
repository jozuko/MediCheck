package com.studiojozu.medicheck.domain.model.person;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.IdType;

/**
 * 飲む人IDを管理するクラス
 */
public class PersonIdType extends IdType<PersonIdType> {
    private static final long serialVersionUID = 1526728745548403076L;

    public PersonIdType() {
        super();
    }

    public PersonIdType(@NonNull Object value) {
        super(value);
    }
}
