package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType;
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType;
import com.studiojozu.medicheck.domain.model.setting.Setting;
import com.studiojozu.medicheck.domain.model.setting.SettingRepository;
import com.studiojozu.medicheck.domain.model.setting.UseReminderType;

import java.util.ArrayList;
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
public class SqliteSettingRepository extends ABaseRepository implements SettingRepository {
    /** 繰り返し通知を使用する？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_USE_REMINDER = new ColumnBase("use_reminder", ColumnPattern.USE_REMIND);
    /** 繰り返し通知間隔 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_REMIND_INTERVAL = new ColumnBase("remind_interval", ColumnPattern.REMIND_INTERVAL);
    /** 繰り返し通知最大時間(これ以上は通知しない) */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_REMIND_TIMEOUT = new ColumnBase("remind_timeout", ColumnPattern.REMIND_TIMEOUT);
    private static final String TABLE_NAME = "setting";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_USE_REMINDER);
        columns.add(COLUMN_REMIND_INTERVAL);
        columns.add(COLUMN_REMIND_TIMEOUT);
        COLUMNS = new Columns(columns);
    }

    @Nullable
    private Setting mCurrentSetting = null;

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase db) {
        if (db == null) return;
        Map<ColumnBase, ADbType> insertData = new HashMap<>();

        insertData.put(COLUMN_USE_REMINDER, COLUMN_USE_REMINDER.mColumnType.createNewInstance(true));
        insertData.put(COLUMN_REMIND_INTERVAL, COLUMN_REMIND_INTERVAL.mColumnType.createNewInstance(RemindIntervalType.RemindIntervalPattern.MINUTE_5));
        insertData.put(COLUMN_REMIND_TIMEOUT, COLUMN_REMIND_TIMEOUT.mColumnType.createNewInstance(RemindTimeoutType.RemindTimeoutPattern.HOUR_24));
        insert(db, insertData);
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing.
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Columns getColumns() {
        return COLUMNS;
    }

    /**
     * フィールドに保持している設定テーブルの値をいったん破棄し、再取得する。
     *
     * @param context アプリケーションコンテキスト
     */
    private void refreshSetting(@NonNull Context context) {
        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, null, null, null);
        if (databaseRecords.size() == 0) {
            mCurrentSetting = new SqliteSettingConverter(null).createFromRecord();
            return;
        }

        mCurrentSetting = new SqliteSettingConverter(databaseRecords.get(0)).createFromRecord();
    }

    @Override
    @NonNull
    public Setting getSetting(@NonNull Context context) {
        if (mCurrentSetting != null)
            return mCurrentSetting;

        refreshSetting(context);
        return mCurrentSetting;
    }

    @Override
    public void save(@NonNull Context context, @NonNull UseReminderType useReminder, @NonNull RemindIntervalType remindIntervalType, @NonNull RemindTimeoutType remindTimeout) {
        // レコード全削除
        deleteAll(context);

        // 必要なレコードを挿入
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_USE_REMINDER, useReminder);
        insertData.put(COLUMN_REMIND_INTERVAL, remindIntervalType);
        insertData.put(COLUMN_REMIND_TIMEOUT, remindTimeout);
        insert(context, insertData);

        // フィールドに保存している値をクリアする
        mCurrentSetting = new Setting(useReminder, remindIntervalType, remindTimeout);
    }
}
