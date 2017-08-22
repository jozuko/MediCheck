package com.studiojozu.medicheck.database.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.TimeType;

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
public class TimetableRepository extends ABaseRepository {
    /** タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnPattern.INT, AutoIncrementPattern.AutoIncrement);
    /** 服用タイミング名 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnPattern.TEXT);
    /** 予定時刻 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIME = new ColumnBase("time", ColumnPattern.TIME);

    static {
        TABLE_NAME = "timetable";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_TIME);
        COLUMNS = new Columns(columns);
    }

    @Nullable
    private SparseArray<Map<ColumnBase, ADbType>> mDataArray;

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        if (db == null) return;

        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_morning)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(7, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_noon)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(12, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_night)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(19, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_sleep)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(22, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_breakfast)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(6, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_lunch)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(11, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_dinner)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(18, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_after_breakfast)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(7, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_after_lunch)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(12, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_after_dinner)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(19, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_between_meals_morning)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(10, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_between_meals_afternoon)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimeType(16, 0).getDbValue()));
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
     * すべてのタイムテーブルを取得する
     * すでにフィールドに取得している場合は、フィールド値をクリアし、最新データを取得する
     *
     * @param context アプリケーションコンテキスト
     */
    private synchronized void refreashAllTimetables(@NonNull Context context) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            if (mDataArray == null) mDataArray = new SparseArray<>();
            mDataArray.clear();

            List<Map<ColumnBase, ADbType>> entities = find(readonlyDatabase, null, null);
            for (Map<ColumnBase, ADbType> recordData : entities) {
                Integer id = (Integer) recordData.get(TimetableRepository.COLUMN_ID).getDbValue();
                mDataArray.put(id, recordData);
            }
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * すべてのタイムテーブルを取得する.
     * すでにフィールドに取得している場合は、再取得しない。
     *
     * @param context アプリケーションコンテキスト
     */
    private void getAllTimetables(@NonNull Context context) {
        if (mDataArray == null) refreashAllTimetables(context);
    }

    /**
     * パラメータのタイムテーブルIDが示すレコードを取得する
     *
     * @param context     アプリケーションコンテキスト
     * @param timetableId タイムテーブルID
     * @return タイムテーブルの1レコード分のデータ
     */
    @Nullable
    public Map<ColumnBase, ADbType> findTimetable(@NonNull Context context, int timetableId) {
        getAllTimetables(context);

        if (mDataArray == null) return null;
        if (mDataArray.indexOfKey(timetableId) < 0) return null;

        return mDataArray.get(timetableId);
    }

}
