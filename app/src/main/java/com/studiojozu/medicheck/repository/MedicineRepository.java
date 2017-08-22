package com.studiojozu.medicheck.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;

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
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnPattern.MEDICINE_ID, AutoIncrementPattern.AutoIncrement);
    /** 名前 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnPattern.MEDICINE_NAME);
    /** 服用数 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TAKE_NUMBER = new ColumnBase("take_number", ColumnPattern.MEDICINE_TAKE_NUMBER);
    /** 服用日数 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_DATE_NUMBER = new ColumnBase("date_number", ColumnPattern.MEDICINE_DATE_NUMBER);
    /** 服用開始日時 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_START_DATETIME = new ColumnBase("start_datetime", ColumnPattern.MEDICINE_START_DATETIME);
    /** 服用間隔 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TAKE_INTERVAL = new ColumnBase("interval", ColumnPattern.MEDICINE_TAKE_INTERVAL);
    /** 服用間隔タイプ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TAKE_INTERVAL_MODE = new ColumnBase("interval_mode", ColumnPattern.MEDICINE_TAKE_INTERVAL_MODE);
    /** 薬の写真ファイルパス */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PHOTO = new ColumnBase("photo", ColumnPattern.MEDICINE_PHOTO);

    static {
        TABLE_NAME = "medicine";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_TAKE_NUMBER);
        columns.add(COLUMN_DATE_NUMBER);
        columns.add(COLUMN_START_DATETIME);
        columns.add(COLUMN_TAKE_INTERVAL);
        columns.add(COLUMN_TAKE_INTERVAL_MODE);
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
    public Map<ColumnBase, ADbType> findById(@NonNull Context context, @NonNull MedicineIdType medicineId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            return findById(readonlyDatabase, medicineId);
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * 薬IDに一致するレコードを取得する
     *
     * @param database   Readableなデータベースインスタンス
     * @param medicineId 薬ID
     * @return 薬IDに一致するレコード
     */
    @Nullable
    public Map<ColumnBase, ADbType> findById(@NonNull ADatabase database, @NonNull MedicineIdType medicineId) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);

        List<Map<ColumnBase, ADbType>> dataList = find(database, COLUMN_ID.getEqualsCondition(), whereList);
        if (dataList.size() == 0) return null;
        return dataList.get(0);
    }

    /**
     * 薬IDをキーとして、テーブルから該当レコードを削除する。
     *
     * @param database   Writableなデータベースインスタンス
     * @param medicineId 薬ID
     */
    void delete(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId) {
        // タイムテーブル一覧の情報を取得する
        Map<ColumnBase, ADbType> medicine = findById(database, medicineId);
        if (medicine == null || medicine.size() == 0) return;

        // 薬テーブルから該当IDを削除
        String whereClause = COLUMN_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        delete(database, whereClause, whereList);

        // 飲む人-薬 Relationテーブルから該当IDを削除
        new ParsonMediRelationRepository().deleteByMedicineId(database, medicineId);

        // 薬-Timetable Relationテーブルから該当IDを削除
        new MediTimeRelationRepository().delete(database, medicineId);

        // Scheduleテーブルから該当IDを削除
        new ScheduleRepository().deleteIgnoreHistory(database, medicineId);
    }
}
