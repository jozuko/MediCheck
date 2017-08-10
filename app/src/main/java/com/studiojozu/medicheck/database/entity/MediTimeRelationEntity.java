package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.WritableDatabase;

import java.util.ArrayList;

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
    /**
     * ID
     */
    private static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnType.INT, PrimayType.Primary);
    /**
     * タイムテーブルID
     */
    private static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase("timetable_id", ColumnType.INT, PrimayType.Primary);
    /**
     * 頓服？
     */
    private static final ColumnBase COLUMN_IS_ONE_SHOT = new ColumnBase("is_one_shot", ColumnType.BOOL);

    static {
        TABLE_NAME = "medi_time_relation";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_MEDICINE_ID);
        columns.add(COLUMN_TIMETABLE_ID);
        columns.add(COLUMN_IS_ONE_SHOT);
        COLUMNS = new Columns(columns);
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
