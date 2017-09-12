package com.studiojozu.medicheck.domain.model.setting;

import android.content.Context;
import android.support.annotation.NonNull;

public interface SettingRepository {
    @NonNull
    Setting getSetting(@NonNull Context context);

    void save(@NonNull Context context, @NonNull UseReminderType useReminder, @NonNull RemindIntervalType remindIntervalType, @NonNull RemindTimeoutType remindTimeout);
}
