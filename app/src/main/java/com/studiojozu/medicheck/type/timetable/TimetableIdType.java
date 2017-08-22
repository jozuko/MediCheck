package com.studiojozu.medicheck.type.timetable;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.IdType;

/**
 * TimetableIDを管理するクラス
 */
public class TimetableIdType extends IdType implements Cloneable {
    public TimetableIdType() {
        super(UNDEFINED_ID);
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
