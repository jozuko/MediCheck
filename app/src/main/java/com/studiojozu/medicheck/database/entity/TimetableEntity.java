package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.TimeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnType.INT, AutoIncrementType.AutoIncrement);
    /**
     * 服用タイミング名
     */
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnType.TEXT);
    /**
     * 予定時刻
     */
    public static final ColumnBase COLUMN_TIME = new ColumnBase("time", ColumnType.TIME);

    static {
        TABLE_NAME = "timetable";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_TIME);
        COLUMNS = new Columns(columns);
    }

    @Nullable
    private Map<Integer, Map<ColumnBase, ADbType>> _dataMap;

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
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

    /**
     * すべてのタイムテーブルEntityを取得する
     * すでにフィールドに取得している場合は、フィールド値をクリアし、最新データを取得する
     *
     * @param context アプリケーションコンテキスト
     * @return タイムテーブルEntityレコード一覧
     */
    private synchronized void refreashAllTimetables(@NonNull Context context) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            if (_dataMap == null) _dataMap = new HashMap<>();
            _dataMap.clear();

            List<Map<ColumnBase, ADbType>> entities = findEntities(readonlyDatabase, null, null);
            for (Map<ColumnBase, ADbType> recordData : entities) {
                Integer id = (Integer) recordData.get(TimetableEntity.COLUMN_ID).getDbValue();
                _dataMap.put(id, recordData);
            }
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * すべてのタイムテーブルEntityを取得する.
     * すでにフィールドに取得している場合は、再取得しない。
     *
     * @param context アプリケーションコンテキスト
     */
    private void getAllTimetables(@NonNull Context context) {
        if (_dataMap == null) refreashAllTimetables(context);
    }

    public Map<ColumnBase, ADbType> findTimetable(@NonNull Context context, int timetableId) {
        getAllTimetables(context);
        return _dataMap.get(timetableId);
    }

}
