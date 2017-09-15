package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.BooleanType;

/**
 * Reminderを使用するかを管理するクラス
 */
public class UseReminderType extends BooleanType<UseReminderType> {

    private static final long serialVersionUID = 7662891586709926954L;

    public UseReminderType() {
        this(true);
    }

    public UseReminderType(@NonNull Object value) {
        super(value);
    }
}
