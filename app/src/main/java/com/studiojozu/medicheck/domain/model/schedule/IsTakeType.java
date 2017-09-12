package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.BooleanType;

/**
 * アラームの要否を管理するクラス
 */
public class IsTakeType extends BooleanType implements Cloneable {
    public IsTakeType() {
        super(false);
    }

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
