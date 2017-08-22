package com.studiojozu.medicheck.type.parson;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class ParsonNameType extends TextType {

    public ParsonNameType() {
        super("");
    }

    public ParsonNameType(@NonNull Object value) {
        super(value);
    }
}
