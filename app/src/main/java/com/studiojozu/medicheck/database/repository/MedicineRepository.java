package com.studiojozu.medicheck.database.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.IntType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 薬テーブル
 * <ol>
 * <li>name 薬名</li>
 * <li>take_number 服用数</li>
 * <li>photo 薬の写真URI</li>
 * </ol>
 */
public class MedicineRepository extends ABaseRepository {
    /** ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnPattern.INT, AutoIncrementPattern.AutoIncrement);
    /** 名前 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnPattern.TEXT);
    /** 服用数 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TAKE_NUMBER = new ColumnBase("take_number", ColumnPattern.INT);
    /** 服用日数 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_DATE_NUMBER = new ColumnBase("date_number", ColumnPattern.INT);
    /** 服用開始日時 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_START_DATETIME = new ColumnBase("start_datetime", ColumnPattern.DATETIME);
    /** 服用間隔 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_INTERVAL = new ColumnBase("interval", ColumnPattern.INTERVAL);
    /** 服用間隔タイプ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_INTERVAL_TYPE = new ColumnBase("interval_type", ColumnPattern.INTERVAL_TYPE);
    /** 薬の写真ファイルパス */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PHOTO = new ColumnBase("photo", ColumnPattern.TEXT);

    static {
        TABLE_NAME = "medicine";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_TAKE_NUMBER);
        columns.add(COLUMN_DATE_NUMBER);
        columns.add(COLUMN_START_DATETIME);
        columns.add(COLUMN_INTERVAL);
        columns.add(COLUMN_INTERVAL_TYPE);
        columns.add(COLUMN_PHOTO);
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

    /**
     * 薬IDに一致するレコードを取得する
     *
     * @param context    アプリケーションコンテキスト
     * @param medicineId 薬ID
     * @return 薬IDに一致するレコード
     */
    @Nullable
    public Map<ColumnBase, ADbType> findById(@NonNull Context context, @NonNull IntType medicineId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(medicineId);

            List<Map<ColumnBase, ADbType>> datas = find(readonlyDatabase, COLUMN_ID.getEqualsCondition(), whereList);
            if (datas.size() == 0) return null;
            return datas.get(0);
        } finally {
            readonlyDatabase.close();
        }
    }
}
