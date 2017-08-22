package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.LongType;

/**
 * 服用数を管理するクラス
 */
public class TakeNumberType extends LongType {
    public TakeNumberType() {
        super(0);
    }

    public TakeNumberType(@NonNull Object value) {
        super(value);
    }
}
