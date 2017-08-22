package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.BooleanType;

/**
 * 頓服薬であるかを管理するクラス
 */
public class IsOneShotType extends BooleanType {
    public IsOneShotType(boolean value) {
        super(value);
    }

    public IsOneShotType(@NonNull Object value) {
        super(value);
    }
}
