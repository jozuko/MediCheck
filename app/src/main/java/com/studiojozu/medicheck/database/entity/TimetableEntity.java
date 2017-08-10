package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.IDbType;
import com.studiojozu.medicheck.database.type.RemindIntervalModel;
import com.studiojozu.medicheck.database.type.RemindTimeoutModel;
import com.studiojozu.medicheck.database.type.TimeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Timetable
 * <ol>
 * <li>name 服用タイミング名</li>
 * <li>time 時刻</li>
 * </ol>
 */
public class TimetableEntity extends ABaseEntity {
    /**
     * ID
     */
    private static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnType.INT, AutoIncrementType.AutoIncrement);
    /**
     * 服用タイミング名
     */
    private static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnType.TEXT);
    /**
     * 予定時刻
     */
    private static final ColumnBase COLUMN_TIME = new ColumnBase("time", ColumnType.TIME);

    static {
        TABLE_NAME = "timetable";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_TIME);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        Map<ColumnBase, IDbType> insertData = new HashMap<>();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_morning)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(7, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_noon)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(12, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_night)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(19, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_before_sleep)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(22, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_before_breakfast)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(6, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_before_lunch)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(11, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_before_dinner)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(18, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_after_breakfast)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(7, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_after_lunch)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(12, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_after_dinner)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(19, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_between_meals_morning)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(10, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.timing_between_meals_afternoon)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME._type, new TimeModel(16, 0).getDbValue()));
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
