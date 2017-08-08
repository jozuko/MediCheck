package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * Setting
 * <ol>
 * <li>use_reminder 繰り返し通知を使用する？</li>
 * <li>remind_interval 繰り返し通知間隔</li>
 * <li>remind_timeout 繰り返し通知最大時間(これ以上は通知しない)</li>
 * </ol>
 */
public class SettingEntity extends ABaseEntity {

    private static final String TABLE_NAME = "setting";

    private static final String CREATE_TABLE_SQL
            = "create table " + TABLE_NAME
            + " ("
            + ",use_reminder    integer not null"   // 繰り返し通知を使用する？
            + ",remind_interval integer not null"   // 繰り返し通知間隔
            + ",remind_timeout  integer not null"   // 繰り返し通知最大時間(これ以上は通知しない)
            + ");";

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        db.execSQL("insert into " + TABLE_NAME + " (use_reminder, remind_interval, remind_timeout) values (1, 1, 1)");
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        // do nothing.
    }
}
