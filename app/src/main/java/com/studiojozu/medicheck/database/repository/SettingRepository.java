package com.studiojozu.medicheck.database.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.DateTimeType;
import com.studiojozu.medicheck.database.type.DateType;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.RemindIntervalType;
import com.studiojozu.medicheck.database.type.RemindTimeoutType;
import com.studiojozu.medicheck.database.type.TimeType;

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
public class SettingRepository extends ABaseRepository {
    /** 繰り返し通知を使用する？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_USE_REMINDER = new ColumnBase("use_reminder", ColumnPattern.BOOL);
    /** 繰り返し通知間隔 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_REMIND_INTERVAL = new ColumnBase("remind_interval", ColumnPattern.REMIND_INTERVAL);
    /** 繰り返し通知最大時間(これ以上は通知しない) */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_REMIND_TIMEOUT = new ColumnBase("remind_timeout", ColumnPattern.REMIND_TIMEOUT);

    static {
        TABLE_NAME = "setting";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_USE_REMINDER);
        columns.add(COLUMN_REMIND_INTERVAL);
        columns.add(COLUMN_REMIND_TIMEOUT);
        COLUMNS = new Columns(columns);
    }

    @Nullable
    private Map<ColumnBase, ADbType> mCurrentSetting;

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        if(db == null) return;
        Map<ColumnBase, ADbType> insertData = new HashMap<>();

        insertData.put(COLUMN_USE_REMINDER, DbTypeFactory.createInstance(COLUMN_USE_REMINDER.mColumnType, true));
        insertData.put(COLUMN_REMIND_INTERVAL, DbTypeFactory.createInstance(COLUMN_REMIND_INTERVAL.mColumnType, RemindIntervalType.RemindIntervalPattern.MINUTE_5));
        insertData.put(COLUMN_REMIND_TIMEOUT, DbTypeFactory.createInstance(COLUMN_REMIND_TIMEOUT.mColumnType, RemindTimeoutType.RemindTimeoutPattern.HOUR_24));
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

    /**
     * フィールドに保持している設定テーブルの値をいったん破棄し、再取得する。
     *
     * @param context アプリケーションコンテキスト
     */
    private void refreashSetting(@NonNull Context context) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            List<Map<ColumnBase, ADbType>> entities = find(readonlyDatabase, null, null);
            mCurrentSetting = entities.get(0);
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * フィールドに保持している設定テーブルの値がある場合は、それを返却し、
     * ない場合は、設定テーブルから取得する。
     *
     * @param context アプリケーションコンテキスト
     */
    private void getSetting(@NonNull Context context) {
        if (mCurrentSetting == null) refreashSetting(context);
    }

    /**
     * リマインド機能を使用するか？
     *
     * @param context アプリケーションコンテキスト
     * @return リマインド機能を使用する場合はtrueを返却する。
     */
    public boolean isUseRemind(@NonNull Context context) {
        getSetting(context);
        if(mCurrentSetting == null) return false;
        return (Boolean) mCurrentSetting.get(COLUMN_USE_REMINDER).getDbValue();
    }

    /**
     * パラメータnowに指名した時刻が、リマインド機能の限界時間を超えているか？
     *
     * @param context      アプリケーションコンテキスト
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド機能の限界時間を超えている場合はtrueを返却する
     */
    public boolean isRemindTimeout(@NonNull Context context, @NonNull Calendar now, @NonNull DateType scheduleDate, @NonNull TimeType scheduleTime) {
        getSetting(context);
        if(mCurrentSetting == null) return true;

        return ((RemindTimeoutType) mCurrentSetting.get(COLUMN_REMIND_TIMEOUT)).isTimeout(new DateTimeType(now), scheduleDate, scheduleTime);
    }

    /**
     * パラメータnowに指名した時刻が、リマインド時刻であるか？
     *
     * @param context      アプリケーションコンテキスト
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド時刻である場合はtrueを返却する
     */
    public boolean isRemindTiming(@NonNull Context context, @NonNull Calendar now, @NonNull DateType scheduleDate, @NonNull TimeType scheduleTime) {
        getSetting(context);
        if(mCurrentSetting == null) return false;

        DateTimeType reminderDateTime = new DateTimeType(scheduleDate, scheduleTime);
        DateTimeType currentDateTime = new DateTimeType(now);
        long diffMinutes = reminderDateTime.diffMinutes(currentDateTime);

        RemindIntervalType intervalModel = (RemindIntervalType) mCurrentSetting.get(COLUMN_REMIND_INTERVAL);

        return (diffMinutes % intervalModel.getDbValue() == 0);
    }
}
