package com.studiojozu.medicheck.repository;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.DbTypeFactory;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.timetable.TimetableIdType;
import com.studiojozu.medicheck.type.timetable.TimetableTimeType;

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
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnPattern.TIMETABLE_ID, AutoIncrementPattern.AutoIncrement);
    /** 服用タイミング名 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnPattern.TIMETABLE_NAME);
    /** 予定時刻 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIME = new ColumnBase("time", ColumnPattern.TIMETABLE_TIME);
    private static final String TABLE_NAME;
    private static final Columns COLUMNS;

    static {
        TABLE_NAME = "timetable";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_TIME);
        COLUMNS = new Columns(columns);
    }

    @Nullable
    private Map<TimetableIdType, Map<ColumnBase, ADbType>> mDataMap;

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        if (db == null) return;

        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_morning)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(7, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_noon)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(12, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_night)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(19, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_sleep)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(22, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_breakfast)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(6, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_lunch)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(11, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_before_dinner)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(18, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_after_breakfast)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(7, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_after_lunch)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(12, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_after_dinner)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(19, 30).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_between_meals_morning)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(10, 0).getDbValue()));
        insert(db, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.timing_between_meals_afternoon)));
        insertData.put(COLUMN_TIME, DbTypeFactory.createInstance(COLUMN_TIME.mColumnType, new TimetableTimeType(16, 0).getDbValue()));
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

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Columns getColumns() {
        return COLUMNS;
    }

    /**
     * すべてのタイムテーブルを取得する
     * すでにフィールドに取得している場合は、フィールド値をクリアし、最新データを取得する
     *
     * @param context アプリケーションコンテキスト
     */
    private synchronized void refreshAllTimetables(@NonNull Context context) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            if (mDataMap == null) mDataMap = new HashMap<>();
            mDataMap.clear();

            List<Map<ColumnBase, ADbType>> entities = find(readonlyDatabase, null, null);
            for (Map<ColumnBase, ADbType> recordData : entities) {
                mDataMap.put((TimetableIdType) recordData.get(TimetableRepository.COLUMN_ID), recordData);
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
        if (mDataMap == null) refreshAllTimetables(context);
    }

    /**
     * パラメータのタイムテーブルIDが示すレコードを取得する
     *
     * @param context     アプリケーションコンテキスト
     * @param timetableId タイムテーブルID
     * @return タイムテーブルの1レコード分のデータ
     */
    @Nullable
    public Map<ColumnBase, ADbType> findTimetable(@NonNull Context context, TimetableIdType timetableId) {
        getAllTimetables(context);

        if (mDataMap == null) return null;
        if (mDataMap.containsKey(timetableId)) return mDataMap.get(timetableId);

        return null;
    }

    /**
     * 薬IDに一致するタイムテーブルのレコードをすべて取得する
     *
     * @param context    アプリケーションコンテキスト
     * @param medicineId 薬ID
     * @return 薬IDに一致するタイムテーブルのレコード一覧
     */
    public List<Map<ColumnBase, ADbType>> findByMedicineId(@NonNull Context context, MedicineIdType medicineId) {
        // SQL生成
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select");
        sqlBuilder.append(" t1.").append(COLUMN_ID.mColumnName).append(" as ").append(COLUMN_ID.mColumnName);
        sqlBuilder.append(",t1.").append(COLUMN_NAME.mColumnName).append(" as ").append(COLUMN_NAME.mColumnName);
        sqlBuilder.append(",t1.").append(COLUMN_TIME.mColumnName).append(" as ").append(COLUMN_TIME.mColumnName);
        sqlBuilder.append(" from ").append(TABLE_NAME).append(" t1");
        sqlBuilder.append(" inner join ").append(new MediTimeRelationRepository().getTableName()).append(" t2 ");
        sqlBuilder.append(" on t1.").append(COLUMN_ID.mColumnName).append("=t2.").append(MediTimeRelationRepository.COLUMN_TIMETABLE_ID.mColumnName);
        sqlBuilder.append(" where ").append(MediTimeRelationRepository.COLUMN_MEDICINE_ID.getEqualsCondition());

        // Where引数生成
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);

        ReadonlyDatabase database = new ReadonlyDatabase(context);
        try {
            Cursor cursor = null;
            try {
                final String sql = sqlBuilder.toString();
                cursor = database.rawQuery(sql, createWhereArgs(whereList));
            } finally {
                if (cursor != null) cursor.close();
            }

            return COLUMNS.putAllDataList(cursor);
        } finally {
            database.close();
        }
    }


}
