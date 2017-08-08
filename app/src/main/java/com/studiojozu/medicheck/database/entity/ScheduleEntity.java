package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * Schedule
 * <ol>
 * <li>medicine_id 服用薬ID</li>
 * <li>plan_date 服用予定日付</li>
 * <li>timetable_id 服用予定タイムテーブルID</li>
 * <li>need_alert Alertいる？</li>
 * <li>is_take 服用した？</li>
 * <li>alert_datetime Alertする日時(リマインダ時間ずつ増加する)</li>
 * <li>take_datetime 服用した日時</li>
 * </ol>
 */
public class ScheduleEntity extends ABaseEntity {

    private static final String TABLE_NAME = "schedule";

    private static final String CREATE_TABLE_SQL
            = "create table " + TABLE_NAME
            + " ("
            + " medicine_id   integer not null"   // 服用薬ID
            + ",plan_date     integer not null"   // 服用予定日付
            + ",timetable_id  integer not null"   // 服用予定タイムテーブルID
            + ",need_alert    integer not null"   // Alertいる？
            + ",is_take       integer not null"   // 服用した？
            + ",take_datetime integer not null"   // 服用した日時
            + ",primary key(medicine_id, plan_date, timetable_id)"
            + ");";

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        // do nothing.
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
