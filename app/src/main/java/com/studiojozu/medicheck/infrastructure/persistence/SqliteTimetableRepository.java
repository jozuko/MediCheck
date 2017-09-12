package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;
import com.studiojozu.medicheck.domain.model.setting.TimetableRepository;
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType;

import java.util.ArrayList;
import java.util.Collection;
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
public class SqliteTimetableRepository extends ABaseRepository implements TimetableRepository {
    /** タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_ID = new ColumnBase("timetable_id", ColumnPattern.TIMETABLE_ID, PrimaryPattern.Primary);
    /** 服用タイミング名 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NAME = new ColumnBase("timetable_name", ColumnPattern.TIMETABLE_NAME);
    /** 予定時刻 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIME = new ColumnBase("timetable_time", ColumnPattern.TIMETABLE_TIME);
    /** 予定時刻 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_DISPLAY_ORDER = new ColumnBase("timetable_display_order", ColumnPattern.TIMETABLE_DISPLAY_ORDER);
    static final String TABLE_NAME = "timetable";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_TIME);
        columns.add(COLUMN_DISPLAY_ORDER);
        COLUMNS = new Columns(columns);
    }

    @NonNull
    private final Map<TimetableIdType, Timetable> mDataMap = new HashMap<>();

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase database) {
        if (database == null) return;

        int order = 1;

        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_morning)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(7, 0).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_noon)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(12, 0).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_night)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(19, 0).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_before_sleep)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(22, 0).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_before_breakfast)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(6, 30).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_before_lunch)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(11, 30).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_before_dinner)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(18, 30).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_after_breakfast)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(7, 30).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_after_lunch)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(12, 30).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_after_dinner)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(19, 30).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_between_meals_morning)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(10, 0).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order++));
        insert(database, insertData);

        insertData.clear();
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.timing_between_meals_afternoon)));
        insertData.put(COLUMN_TIME, COLUMN_TIME.mColumnType.createNewInstance(new TimetableTimeType(16, 0).getDbValue()));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order));
        insert(database, insertData);
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable SQLiteDatabase database, int oldVersion, int newVersion) {
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

    private void clearDataMap() {
        mDataMap.clear();
    }

    /**
     * すべてのタイムテーブルを取得する
     * すでにフィールドに取得している場合は、フィールド値をクリアし、最新データを取得する
     *
     * @param context アプリケーションコンテキスト
     */
    private synchronized void refreshAllTimetables(@NonNull Context context) {
        clearDataMap();

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, null, null, null);
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            Timetable timetable = new SqliteTimetableConverter(record).createFromRecord();
            if (timetable == null)
                continue;

            mDataMap.put(timetable.getTimetableId(), timetable);
        }
    }

    /**
     * すべてのタイムテーブルを取得する.
     * すでにフィールドに取得している場合は、再取得しない。
     *
     * @param context アプリケーションコンテキスト
     */
    private void getAllTimetables(@NonNull Context context) {
        if (mDataMap.size() == 0)
            refreshAllTimetables(context);
    }

    @Override
    @Nullable
    public Timetable findTimetableById(@NonNull Context context, TimetableIdType timetableId) {
        getAllTimetables(context);

        if (mDataMap.containsKey(timetableId))
            return mDataMap.get(timetableId);
        return null;
    }

    @Override
    @NonNull
    public Collection<Timetable> findAll(@NonNull Context context) {
        getAllTimetables(context);
        return mDataMap.values();
    }
}
