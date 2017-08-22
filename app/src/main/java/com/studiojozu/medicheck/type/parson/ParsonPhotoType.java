package com.studiojozu.medicheck.type.parson;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.PhotoType;

/**
 * 薬の写真を管理するクラス
 */
public class ParsonPhotoType extends PhotoType implements Cloneable {
    public ParsonPhotoType() {
        super("");
    }

    public ParsonPhotoType(@NonNull Object value) {
        super(value);
    }

    @Override
    public ParsonPhotoType clone() {
        try {
            return (ParsonPhotoType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
