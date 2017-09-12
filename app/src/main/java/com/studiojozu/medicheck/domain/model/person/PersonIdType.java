package com.studiojozu.medicheck.domain.model.person;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.IdType;

/**
 * 飲む人IDを管理するクラス
 */
public class PersonIdType extends IdType implements Cloneable {
    public PersonIdType() {
        super();
    }

    public PersonIdType(@NonNull Object value) {
        super(value);
    }

    @Override
    public PersonIdType clone() {
        try {
            return (PersonIdType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
