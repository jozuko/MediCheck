package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.LongType;

/**
 * 服用日数を管理するクラス
 */
public class DateNumberType extends LongType {
    public DateNumberType() {
        super(0);
    }

    public DateNumberType(@NonNull Object value) {
        super(value);
    }
}
