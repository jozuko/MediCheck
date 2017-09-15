package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.TextType;

/**
 * 飲む人の名前を管理するクラス
 */
public class TimetableNameType extends TextType<TimetableNameType> {

    private static final long serialVersionUID = 8465585151530155440L;

    public TimetableNameType() {
        super("");
    }

    public TimetableNameType(@Nullable Object value) {
        super(value);
    }
}
