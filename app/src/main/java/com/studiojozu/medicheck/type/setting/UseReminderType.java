package com.studiojozu.medicheck.type.setting;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.BooleanType;

/**
 * Reminderを使用するかを管理するクラス
 */
public class UseReminderType extends BooleanType implements Cloneable {

    public UseReminderType(@NonNull Object value) {
        super(value);
    }

    @Override
    public UseReminderType clone() {
        try {
            return (UseReminderType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
