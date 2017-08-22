package com.studiojozu.medicheck.type.parson;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.IdType;

/**
 * 飲む人IDを管理するクラス
 */
public class ParsonIdType extends IdType implements Cloneable {
    public ParsonIdType() {
        super(UNDEFINED_ID);
    }

    public ParsonIdType(@NonNull Object value) {
        super(value);
    }

    @Override
    public ParsonIdType clone() {
        try {
            return (ParsonIdType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
