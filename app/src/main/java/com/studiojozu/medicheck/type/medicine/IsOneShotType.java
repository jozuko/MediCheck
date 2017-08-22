package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.BooleanType;

/**
 * 頓服薬であるかを管理するクラス
 */
public class IsOneShotType extends BooleanType implements Cloneable {
    public IsOneShotType(boolean value) {
        super(value);
    }

    public IsOneShotType(@NonNull Object value) {
        super(value);
    }

    @Override
    public IsOneShotType clone() {
        try {
            return (IsOneShotType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
