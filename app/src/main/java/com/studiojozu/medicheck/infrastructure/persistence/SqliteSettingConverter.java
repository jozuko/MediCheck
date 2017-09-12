package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType;
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType;
import com.studiojozu.medicheck.domain.model.setting.Setting;
import com.studiojozu.medicheck.domain.model.setting.UseReminderType;

import java.util.Map;

/**
 * DBのレコードから{@link Setting}を生成する
 */
class SqliteSettingConverter extends ASqliteConverter<Setting> {

    SqliteSettingConverter(@Nullable Map<ColumnBase, ADbType> databaseRecord) {
        super(databaseRecord);
    }

    @Override
    @NonNull
    Setting createFromRecord() {
        if (mDatabaseRecord == null)
            return new Setting(new UseReminderType(), new RemindIntervalType(), new RemindTimeoutType());

        return new Setting(
                getUseReminder(),
                getRemindInterval(),
                getRemindTimeout()
        );
    }

    private UseReminderType getUseReminder() {
        UseReminderType useReminderType = (UseReminderType) getData(SqliteSettingRepository.COLUMN_USE_REMINDER);
        if (useReminderType != null) return useReminderType;
        return new UseReminderType();
    }

    private RemindIntervalType getRemindInterval() {
        RemindIntervalType remindIntervalType = (RemindIntervalType) getData(SqliteSettingRepository.COLUMN_REMIND_INTERVAL);
        if (remindIntervalType != null) return remindIntervalType;
        return new RemindIntervalType();
    }

    private RemindTimeoutType getRemindTimeout() {
        RemindTimeoutType remindTimeoutType = (RemindTimeoutType) getData(SqliteSettingRepository.COLUMN_REMIND_TIMEOUT);
        if (remindTimeoutType != null) return remindTimeoutType;
        return new RemindTimeoutType();
    }
}
