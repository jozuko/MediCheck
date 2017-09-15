package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.BooleanType;

/**
 * アラームの要否を管理するクラス
 */
public class IsTakeType extends BooleanType<IsTakeType> {
    private static final long serialVersionUID = -8175393566197539808L;

    public IsTakeType() {
        super(false);
    }

    public IsTakeType(@NonNull Object value) {
        super(value);
    }
}
