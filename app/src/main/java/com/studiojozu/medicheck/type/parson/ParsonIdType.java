package com.studiojozu.medicheck.type.parson;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.IdType;

/**
 * 飲む人IDを管理するクラス
 */
public class ParsonIdType extends IdType {
    public ParsonIdType() {
        super(UNDEFINED_ID);
    }

    public ParsonIdType(@NonNull Object value) {
        super(value);
    }
}
