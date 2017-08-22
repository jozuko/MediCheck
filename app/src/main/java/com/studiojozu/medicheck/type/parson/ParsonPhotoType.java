package com.studiojozu.medicheck.type.parson;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.PhotoType;

/**
 * 薬の写真を管理するクラス
 */
public class ParsonPhotoType extends PhotoType {
    public ParsonPhotoType() {
        super("");
    }

    public ParsonPhotoType(@NonNull Object value) {
        super(value);
    }
}
