package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.IntModel;

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
public class MedicineEntity extends ABaseEntity {
    /**
     * ID
     */
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnType.INT, AutoIncrementType.AutoIncrement);
    /**
     * 名前
     */
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnType.TEXT);
    /**
     * 服用数
     */
    public static final ColumnBase COLUMN_TAKE_NUMBER = new ColumnBase("take_number", ColumnType.INT);
    /**
     * 服用日数
     */
    public static final ColumnBase COLUMN_DATE_NUMBER = new ColumnBase("date_number", ColumnType.INT);
    /**
     * 服用開始日時
     */
    public static final ColumnBase COLUMN_START_DATETIME = new ColumnBase("start_datetime", ColumnType.DATETIME);
    /**
     * 服用間隔
     */
    public static final ColumnBase COLUMN_INTERVAL = new ColumnBase("interval", ColumnType.INTERVAL);
    /**
     * 服用間隔タイプ
     */
    public static final ColumnBase COLUMN_INTERVAL_TYPE = new ColumnBase("interval_type", ColumnType.INTERVAL_TYPE);
    /**
     * 薬の写真ファイルパス
     */
    public static final ColumnBase COLUMN_PHOTO = new ColumnBase("photo", ColumnType.TEXT);

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
    public Map<ColumnBase, ADbType> findById(@NonNull Context context, @NonNull IntModel medicineId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(medicineId);

            List<Map<ColumnBase, ADbType>> datas = findEntities(readonlyDatabase, COLUMN_ID.getEqualsCondition(), whereList);
            if (datas == null || datas.size() == 0) return null;
            return datas.get(0);
        } finally {
            readonlyDatabase.close();
        }
    }
}
