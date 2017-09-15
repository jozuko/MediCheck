package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.BooleanType;

/**
 * 頓服薬であるかを管理するクラス
 */
public class IsOneShotType extends BooleanType<IsOneShotType> {
    private static final long serialVersionUID = 3912664133628844098L;

    public IsOneShotType(boolean value) {
        super(value);
    }

    public IsOneShotType(@NonNull Object value) {
        super(value);
    }
}
