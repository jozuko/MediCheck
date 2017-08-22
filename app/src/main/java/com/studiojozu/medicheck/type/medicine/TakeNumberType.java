package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.LongType;

/**
 * 服用数を管理するクラス
 */
public class TakeNumberType extends LongType implements Cloneable {
    public TakeNumberType() {
        super(0);
    }

    public TakeNumberType(@NonNull Object value) {
        super(value);
    }

    @Override
    public TakeNumberType clone() {
        try {
            return (TakeNumberType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
