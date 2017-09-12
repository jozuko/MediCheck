package com.studiojozu.medicheck.domain.model.person;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.PhotoType;

/**
 * 薬の写真を管理するクラス
 */
public class PersonPhotoType extends PhotoType implements Cloneable {
    public PersonPhotoType() {
        super("");
    }

    public PersonPhotoType(@NonNull Object value) {
        super(value);
    }

    @Override
    public PersonPhotoType clone() {
        try {
            return (PersonPhotoType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
