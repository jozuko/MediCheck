package com.studiojozu.medicheck.type.timetable;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.IdType;

/**
 * TimetableIDを管理するクラス
 */
public class TimetableIdType extends IdType {
    public TimetableIdType() {
        super(UNDEFINED_ID);
    }

    public TimetableIdType(@NonNull Object value) {
        super(value);
    }
}
