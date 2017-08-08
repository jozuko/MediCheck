package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.model.TimeModel;

/**
 * Timetable
 * <ol>
 * <li>name 服用タイミング名</li>
 * <li>time 時刻</li>
 * </ol>
 */
public class TimetableEntity extends ABaseEntity {

    private static final String TABLE_NAME = "timetable";

    private static final String CREATE_TABLE_SQL
            = "create table " + TABLE_NAME
            + " ("
            + " _id  integer not null autoincrement"    // ID
            + ",name text    not null" // 服用タイミング名
            + ",time integer not null" // 時刻(DateTime-long値)
            + ",primary key(_id)"
            + ");";

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_morning) + "'," + new TimeModel(7, 0).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_noon) + "', " + new TimeModel(12, 0).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_night) + "', " + new TimeModel(19, 0).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_before_sleep) + "', " + new TimeModel(22, 0).getDbValue() + ")");

        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_before_breakfast) + "', " + new TimeModel(6, 30).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_before_lunch) + "', " + new TimeModel(11, 30).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_before_dinner) + "', " + new TimeModel(18, 30).getDbValue() + ")");

        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_after_breakfast) + "', " + new TimeModel(7, 30).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_after_lunch) + "', " + new TimeModel(12, 30).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_after_dinner) + "', " + new TimeModel(19, 30).getDbValue() + ")");

        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_between_meals_morning) + "', " + new TimeModel(10, 00).getDbValue() + ")");
        db.execSQL("insert into " + TABLE_NAME + " (name, time) values ('" + context.getResources().getString(R.string.timing_between_meals_afternoon) + "', " + new TimeModel(16, 00).getDbValue() + ")");
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
