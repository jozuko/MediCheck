package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.TextType;

/**
 * 服用数を管理するクラス
 */
public class TakeNumberType extends TextType<TakeNumberType> {
    private static final long serialVersionUID = 6806353498301151954L;

    public TakeNumberType() {
        super("0");
    }

    public TakeNumberType(@NonNull Object value) {
        super(value);
    }
}
