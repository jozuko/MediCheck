package com.studiojozu.medicheck.type.timetable;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class TimetableNameType extends TextType {

    public TimetableNameType() {
        super("");
    }

    public TimetableNameType(@NonNull Object value) {
        super(value);
    }
}
