package com.studiojozu.medicheck.type.timetable;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class TimetableNameType extends TextType implements Cloneable {

    public TimetableNameType() {
        super("");
    }

    public TimetableNameType(@NonNull Object value) {
        super(value);
    }

    @Override
    public TimetableNameType clone() {
        try {
            return (TimetableNameType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
