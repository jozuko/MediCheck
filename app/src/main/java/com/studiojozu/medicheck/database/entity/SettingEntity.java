package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.IDbType;
import com.studiojozu.medicheck.database.type.RemindIntervalModel;
import com.studiojozu.medicheck.database.type.RemindTimeoutModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Setting
 * <ol>
 * <li>use_reminder 繰り返し通知を使用する？</li>
 * <li>remind_interval 繰り返し通知間隔</li>
 * <li>remind_timeout 繰り返し通知最大時間(これ以上は通知しない)</li>
 * </ol>
 */
public class SettingEntity extends ABaseEntity {
    /**
     * 繰り返し通知を使用する？
     */
    private static final ColumnBase COLUMN_USE_REMINDER = new ColumnBase("use_reminder", ColumnType.BOOL);
    /**
     * 繰り返し通知間隔
     */
    private static final ColumnBase COLUMN_REMIND_INTERVAL = new ColumnBase("remind_interval", ColumnType.REMIND_INTERVAL);
    /**
     * 繰り返し通知最大時間(これ以上は通知しない)
     */
    private static final ColumnBase COLUMN_REMIND_TIMEOUT = new ColumnBase("remind_timeout", ColumnType.REMIND_TIMEOUT);

    static {
        TABLE_NAME = "setting";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_USE_REMINDER);
        columns.add(COLUMN_REMIND_INTERVAL);
        columns.add(COLUMN_REMIND_TIMEOUT);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        Map<ColumnBase, IDbType> insertData = new HashMap<>();

        insertData.put(COLUMN_USE_REMINDER, DbTypeFactory.createInstance(COLUMN_USE_REMINDER._type, true));
        insertData.put(COLUMN_REMIND_INTERVAL, DbTypeFactory.createInstance(COLUMN_REMIND_INTERVAL._type, RemindIntervalModel.RemindIntervalType.MINUTE_5));
        insertData.put(COLUMN_REMIND_TIMEOUT, DbTypeFactory.createInstance(COLUMN_REMIND_TIMEOUT._type, RemindTimeoutModel.RemindTimeoutType.HOUR_24));
        insert(db, insertData);
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
