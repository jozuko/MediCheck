package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.LongType;

/**
 * 服用日数を管理するクラス
 */
public class DateNumberType extends LongType implements Cloneable {
    public DateNumberType() {
        super(0);
    }

    public DateNumberType(@NonNull Object value) {
        super(value);
    }

    @Override
    public DateNumberType clone() {
        try {
            return (DateNumberType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
