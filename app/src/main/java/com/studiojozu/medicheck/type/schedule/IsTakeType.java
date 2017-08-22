package com.studiojozu.medicheck.type.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.BooleanType;

/**
 * アラームの要否を管理するクラス
 */
public class IsTakeType extends BooleanType implements Cloneable {
    public IsTakeType(@NonNull Object value) {
        super(value);
    }

    @Override
    public IsTakeType clone() {
        try {
            return (IsTakeType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
