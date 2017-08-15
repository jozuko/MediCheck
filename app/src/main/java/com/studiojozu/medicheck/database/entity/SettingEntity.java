package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.DateModel;
import com.studiojozu.medicheck.database.type.DateTimeModel;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.IDbType;
import com.studiojozu.medicheck.database.type.RemindIntervalModel;
import com.studiojozu.medicheck.database.type.RemindTimeoutModel;
import com.studiojozu.medicheck.database.type.TimeModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
    public static final ColumnBase COLUMN_USE_REMINDER = new ColumnBase("use_reminder", ColumnType.BOOL);
    /**
     * 繰り返し通知間隔
     */
    public static final ColumnBase COLUMN_REMIND_INTERVAL = new ColumnBase("remind_interval", ColumnType.REMIND_INTERVAL);
    /**
     * 繰り返し通知最大時間(これ以上は通知しない)
     */
    public static final ColumnBase COLUMN_REMIND_TIMEOUT = new ColumnBase("remind_timeout", ColumnType.REMIND_TIMEOUT);

    static {
        TABLE_NAME = "setting";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_USE_REMINDER);
        columns.add(COLUMN_REMIND_INTERVAL);
        columns.add(COLUMN_REMIND_TIMEOUT);
        COLUMNS = new Columns(columns);
    }

    @Nullable
    private Map<ColumnBase, IDbType> _currentSetting;

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

    private void refreashSetting(@NonNull Context context) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            List<Map<ColumnBase, IDbType>> entities = findEntities(readonlyDatabase, null, null);
            _currentSetting = entities.get(0);
        } finally {
            readonlyDatabase.close();
        }
    }

    private void getSetting(@NonNull Context context) {
        if(_currentSetting == null) refreashSetting(context);
    }

    public boolean isUseRemind(@NonNull Context context){
        getSetting(context);
        return (Boolean) _currentSetting.get(COLUMN_USE_REMINDER).getDbValue();
    }

    public boolean isRemindTimeout(@NonNull Context context, @NonNull Calendar now, @NonNull DateModel scheduleDate, @NonNull TimeModel scheduleTime){
        getSetting(context);
        return ((RemindTimeoutModel)_currentSetting.get(COLUMN_REMIND_TIMEOUT)).isTimeout(new DateTimeModel(now), scheduleDate, scheduleTime);
    }

    public boolean isRemindTiming(@NonNull Context context, @NonNull Calendar now, @NonNull DateModel scheduleDate, @NonNull TimeModel scheduleTime){
        getSetting(context);

        DateTimeModel reminderDateTime = new DateTimeModel(scheduleDate, scheduleTime);
        DateTimeModel currentDateTime = new DateTimeModel(now);
        long diffMinutes = reminderDateTime.diffMinutes(currentDateTime);

        RemindIntervalModel intervalModel = (RemindIntervalModel)_currentSetting.get(COLUMN_REMIND_INTERVAL);

        return (diffMinutes % intervalModel.getDbValue() == 0);
    }
}
