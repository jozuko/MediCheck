package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.IdType;

/**
 * TimetableIDを管理するクラス
 */
public class TimetableIdType extends IdType<TimetableIdType> {
    private static final long serialVersionUID = 2295047399748442857L;

    public TimetableIdType() {
        super();
    }

    public TimetableIdType(@NonNull Object value) {
        super(value);
    }
}
