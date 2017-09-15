package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.BooleanType;

/**
 * アラームの要否を管理するクラス
 */
public class NeedAlarmType extends BooleanType<NeedAlarmType> {
    private static final long serialVersionUID = -1817659640674585986L;

    public NeedAlarmType() {
        super(true);
    }

    public NeedAlarmType(@NonNull Object value) {
        super(value);
    }
}
