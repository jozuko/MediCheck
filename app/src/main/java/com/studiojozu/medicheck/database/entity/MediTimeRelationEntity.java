package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * Medicine-TimetableのRelation
 * <ol>
 * <li>medicine_id 薬ID</li>
 * <li>is_one_shot 頓服？</li>
 * <li>timetable_id タイムテーブルID</li>
 * </ol>
 * medicine_id:timetable_id=1:N
 */
public class MediTimeRelationEntity extends ABaseEntity {

    private static final String TABLE_NAME = "medi_time_relation";

    private static final String CREATE_TABLE_SQL
            = "create table " + TABLE_NAME
            + " ("
            + " medicine_id  integer not null"                  // 薬ID
            + ",is_one_shot  integer not null"                  // 頓服？
            + ",timetable_id integer not null"                  // タイムテーブルID
            + ",primary key(medicine_id, timetable_id)"
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
