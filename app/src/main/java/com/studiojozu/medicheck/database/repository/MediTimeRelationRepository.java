package com.studiojozu.medicheck.database.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
public class MediTimeRelationRepository extends ABaseRepository {
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnPattern.ID, PrimaryPattern.Primary);
    /** タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase("timetable_id", ColumnPattern.ID, PrimaryPattern.Primary);
    /** 頓服？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_IS_ONE_SHOT = new ColumnBase("is_one_shot", ColumnPattern.BOOL);

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
