package com.studiojozu.medicheck.type.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.BooleanType;

/**
 * アラームの要否を管理するクラス
 */
public class NeedAlarmType extends BooleanType {
    public NeedAlarmType(@NonNull Object value) {
        super(value);
    }
}
