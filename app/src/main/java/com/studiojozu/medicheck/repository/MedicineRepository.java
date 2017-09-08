package com.studiojozu.medicheck.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.entity.Medicine;
import com.studiojozu.medicheck.entity.ScheduleList;
import com.studiojozu.medicheck.entity.TimetableList;
import com.studiojozu.medicheck.exception.DatabaseException;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.parson.ParsonIdType;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String TABLE_NAME;
    private static final Columns COLUMNS;

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

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Columns getColumns() {
        return COLUMNS;
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
    private Map<ColumnBase, ADbType> findById(@NonNull ADatabase database, @NonNull MedicineIdType medicineId) {
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

    /**
     * DBのテーブルに値を追加、もしくは、既存レコードの更新を行う。
     *
     * @param context       アプリケーションコンテキスト
     * @param parsonId      飲む人ID
     * @param medicine      薬の情報
     * @param timetableList タイムテーブルの一覧
     */
    public void save(@NonNull Context context, @NonNull ParsonIdType parsonId, @NonNull Medicine medicine, @NonNull TimetableList timetableList) {

        WritableDatabase database = new WritableDatabase(context);
        try {
            database.beginTransaction();

            // 薬テーブルへの登録
            Medicine targetMedicine = registerMedicine(database, medicine);

            // 薬-タイムテーブルへの追加
            new MediTimeRelationRepository().save(database, targetMedicine.getMedicineId(), timetableList);

            // 飲む人-薬テーブルへの追加
            new ParsonMediRelationRepository().save(database, parsonId, targetMedicine.getMedicineId());

            // スケジュールへの追加
            ScheduleList scheduleList = targetMedicine.createScheduleList();
            new ScheduleRepository().save(database, targetMedicine.getMedicineId(), scheduleList);

            database.commitTransaction();
        } catch (Exception e) {
            database.rollbackTransaction();
            throw e;
        } finally {
            database.close();
        }
    }

    private Medicine registerMedicine(@NonNull WritableDatabase database, @NonNull Medicine medicine) {
        if (isNewRecord(database, medicine)) {
            return insertMedicine(database, medicine);
        }
        return updateMedicine(database, medicine);
    }

    private boolean isNewRecord(@NonNull WritableDatabase database, @NonNull Medicine medicine) {
        if (medicine.getMedicineId().isUndefined()) return true;

        Map<ColumnBase, ADbType> medicineRecord = findById(database, medicine.getMedicineId());
        return (medicineRecord == null || medicineRecord.size() == 0);
    }

    /**
     * 薬テーブルにデータを追加する。
     *
     * @param database Writableなデータベースインスタンス
     * @param medicine 薬の情報
     * @return 新規追加した薬ID
     */
    private Medicine insertMedicine(@NonNull WritableDatabase database, @NonNull Medicine medicine) {
        // 追加データの作成
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_NAME, medicine.getMedicineName());
        insertData.put(COLUMN_TAKE_NUMBER, medicine.getTakeNumber());
        insertData.put(COLUMN_DATE_NUMBER, medicine.getDateNumber());
        insertData.put(COLUMN_START_DATETIME, medicine.getStartDatetime());
        insertData.put(COLUMN_TAKE_INTERVAL, medicine.getTakeInterval());
        insertData.put(COLUMN_TAKE_INTERVAL_MODE, medicine.getTakeIntervalMode());
        insertData.put(COLUMN_PHOTO, medicine.getMedicinePhoto());

        // レコード追加
        long id = insert(database, insertData);

        // IDの取得
        if (id < 0) throw new DatabaseException("cannot insert medicine.");

        // インスタンスを生成する
        return new Medicine(id, medicine);
    }

    /**
     * 薬テーブルにデータを追加する。
     *
     * @param database Writableなデータベースインスタンス
     * @param medicine 薬の情報
     * @return 新規追加した薬ID
     */
    private Medicine updateMedicine(@NonNull WritableDatabase database, @NonNull Medicine medicine) {

        // 更新データの作成
        Map<ColumnBase, ADbType> updateData = new HashMap<>();
        updateData.put(COLUMN_NAME, medicine.getMedicineName());
        updateData.put(COLUMN_TAKE_NUMBER, medicine.getTakeNumber());
        updateData.put(COLUMN_DATE_NUMBER, medicine.getDateNumber());
        updateData.put(COLUMN_START_DATETIME, medicine.getStartDatetime());
        updateData.put(COLUMN_TAKE_INTERVAL, medicine.getTakeInterval());
        updateData.put(COLUMN_TAKE_INTERVAL_MODE, medicine.getTakeIntervalMode());
        updateData.put(COLUMN_PHOTO, medicine.getMedicinePhoto());

        // 検索条件の作成
        String whereClause = COLUMN_ID.getEqualsCondition();
        ArrayList<ADbType> whereArgs = new ArrayList<>();
        whereArgs.add(medicine.getMedicineId());

        // レコード更新
        update(database, updateData, whereClause, whereArgs);

        // インスタンスを返却する
        return medicine;
    }

}
