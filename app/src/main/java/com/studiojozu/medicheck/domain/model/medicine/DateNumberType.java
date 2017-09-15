package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.LongType;

/**
 * 服用日数を管理するクラス
 */
public class DateNumberType extends LongType<DateNumberType> {
    private static final long serialVersionUID = 4831886646728241799L;

    public DateNumberType() {
        super(0);
    }

    public DateNumberType(@NonNull Object value) {
        super(value);
    }

}
