package com.studiojozu.medicheck.domain.model.person;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.PhotoType;

/**
 * 薬の写真を管理するクラス
 */
public class PersonPhotoType extends PhotoType<PersonPhotoType> {
    private static final long serialVersionUID = -8138608804947506518L;

    public PersonPhotoType() {
        super("");
    }

    public PersonPhotoType(@NonNull Object value) {
        super(value);
    }
}
