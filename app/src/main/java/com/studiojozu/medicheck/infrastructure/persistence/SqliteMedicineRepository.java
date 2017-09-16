package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 薬テーブル
 * <ol>
 * <li>name 薬名</li>
 * <li>take_number 服用数</li>
 * <li>photo 薬の写真URI</li>
 * </ol>
 */
public class SqliteMedicineRepository extends ABaseRepository implements MedicineRepository {

    /** ID */
    static final ColumnBase COLUMN_ID = new ColumnBase("medicine_id", ColumnPattern.MEDICINE_ID, PrimaryPattern.Primary);
    /** 名前 */
    static final ColumnBase COLUMN_NAME = new ColumnBase("medicine_name", ColumnPattern.MEDICINE_NAME);
    /** 服用数 */
    static final ColumnBase COLUMN_TAKE_NUMBER = new ColumnBase("medicine_take_number", ColumnPattern.MEDICINE_TAKE_NUMBER);
    /** 服用日数 */
    static final ColumnBase COLUMN_DATE_NUMBER = new ColumnBase("medicine_date_number", ColumnPattern.MEDICINE_DATE_NUMBER);
    /** 服用開始日時 */
    static final ColumnBase COLUMN_START_DATETIME = new ColumnBase("medicine_start_datetime", ColumnPattern.MEDICINE_START_DATETIME);
    /** 服用間隔 */
    static final ColumnBase COLUMN_TAKE_INTERVAL = new ColumnBase("medicine_interval", ColumnPattern.MEDICINE_TAKE_INTERVAL);
    /** 服用間隔タイプ */
    static final ColumnBase COLUMN_TAKE_INTERVAL_MODE = new ColumnBase("medicine_interval_mode", ColumnPattern.MEDICINE_TAKE_INTERVAL_MODE);
    /** 薬の写真ファイルパス */
    static final ColumnBase COLUMN_PHOTO = new ColumnBase("medicine_photo", ColumnPattern.MEDICINE_PHOTO);
    static final String TABLE_NAME = "medicine";
    private static final Columns COLUMNS;

    static {
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
    protected void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase db) {
        // do nothing.
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable SQLiteDatabase db, int oldVersion, int newVersion) {
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

    @Override
    @Nullable
    public Medicine findMedicineById(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineIdType);
        List<Map<ColumnBase, ADbType>> dataList = find(context, COLUMN_ID.getEqualsCondition(), whereList, null);

        if (dataList.size() == 0)
            return null;

        return new SqliteMedicineConverter(dataList.get(0)).createFromRecord();
    }

    @Override
    @Nullable
    public Set<Medicine> findAll(@NonNull Context context) {
        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, null, null, null);

        if (databaseRecords.size() == 0)
            return null;

        Set<Medicine> medicineSet = new TreeSet<>();
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            Medicine medicine = new SqliteMedicineConverter(record).createFromRecord();
            medicineSet.add(medicine);
        }

        return medicineSet;
    }

    @Override
    public void add(@NonNull Context context, @NonNull Medicine medicine) {
        if (isNewRecord(context, medicine.getMedicineId())) {
            insertMedicine(context, medicine);
            return;
        }

        updateMedicine(context, medicine);
    }

    @Override
    public void remove(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        if (isNewRecord(context, medicineIdType)) return;

        String whereClause = COLUMN_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineIdType);
        delete(context, whereClause, whereList);
    }

    private boolean isNewRecord(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        Medicine medicine = findMedicineById(context, medicineIdType);
        return (medicine == null);
    }

    private void insertMedicine(@NonNull Context context, @NonNull Medicine medicine) {
        // 追加データの作成
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_ID, medicine.getMedicineId());
        insertData.put(COLUMN_NAME, medicine.getMedicineName());
        insertData.put(COLUMN_TAKE_NUMBER, medicine.getTakeNumber());
        insertData.put(COLUMN_DATE_NUMBER, medicine.getDateNumber());
        insertData.put(COLUMN_START_DATETIME, medicine.getStartDatetime());
        insertData.put(COLUMN_TAKE_INTERVAL, medicine.getTakeInterval());
        insertData.put(COLUMN_TAKE_INTERVAL_MODE, medicine.getTakeIntervalMode());
        insertData.put(COLUMN_PHOTO, medicine.getMedicinePhoto());

        // レコード追加
        insert(context, insertData);
    }

    private void updateMedicine(@NonNull Context context, @NonNull Medicine medicine) {

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
        update(context, updateData, whereClause, whereArgs);
    }
}
