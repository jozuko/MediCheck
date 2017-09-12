package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.IdType;

/**
 * TimetableIDを管理するクラス
 */
public class TimetableIdType extends IdType implements Cloneable {
    public TimetableIdType() {
        super();
    }

    public TimetableIdType(@NonNull Object value) {
        super(value);
    }

    @Override
    public TimetableIdType clone() {
        try {
            return (TimetableIdType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
