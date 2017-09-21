package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.LongType;

/**
 * 服用日数を管理するクラス
 */
public class MedicineDateNumberType extends LongType<MedicineDateNumberType> {
    private static final long serialVersionUID = 4831886646728241799L;

    public MedicineDateNumberType() {
        super(0);
    }

    public MedicineDateNumberType(@NonNull Object value) {
        super(value);
    }

}
