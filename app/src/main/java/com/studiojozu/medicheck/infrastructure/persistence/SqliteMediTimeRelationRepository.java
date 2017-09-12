package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.MediTimeRelationRepository;
import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Medicine-TimetableのRelation
 * <ol>
 * <li>medicine_id 薬ID</li>
 * <li>is_one_shot 頓服？</li>
 * <li>timetable_id タイムテーブルID</li>
 * </ol>
 * medicine_id:timetable_id=1:N
 */
public class SqliteMediTimeRelationRepository extends ABaseRepository implements MediTimeRelationRepository {
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase(SqliteMedicineRepository.COLUMN_ID, PrimaryPattern.Primary);
    /** タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase(SqliteTimetableRepository.COLUMN_ID, PrimaryPattern.Primary);
    /** 頓服？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_IS_ONE_SHOT = new ColumnBase("medi_time_relation_is_one_shot", ColumnPattern.MEDICINE_IS_ONE_SHOT);
    static final String TABLE_NAME = "medi_time_relation";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_MEDICINE_ID);
        columns.add(COLUMN_TIMETABLE_ID);
        columns.add(COLUMN_IS_ONE_SHOT);
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

    public void remove(@NonNull Context context, @NonNull MedicineIdType medicineId) {
        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);

        delete(context, whereClause, whereList);
    }

    @Override
    public void add(@NonNull Context context, @NonNull MedicineIdType medicineId, @NonNull MedicineTimetableList timetableList) {

        // 薬に紐づくすべてのタイムテーブルを削除する
        remove(context, medicineId);

        // 頓服薬の場合の処理
        if (timetableList.isOneShotMedicine()) {
            saveOneShot(context, medicineId);
            return;
        }

        // タイムテーブルの場合の処理
        saveTimetable(context, medicineId, timetableList);
    }

    /**
     * 頓服薬として登録する
     */
    private void saveOneShot(@NonNull Context context, @NonNull MedicineIdType medicineId) {

        TimetableIdType timetableId = new TimetableIdType();
        IsOneShotType isOneShotType = new IsOneShotType(true);

        // 追加データの作成
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_MEDICINE_ID, medicineId);
        insertData.put(COLUMN_TIMETABLE_ID, timetableId);
        insertData.put(COLUMN_IS_ONE_SHOT, isOneShotType);

        // レコード追加
        insert(context, insertData);
    }

    /**
     * 頓服薬ではない薬として登録する
     */
    private void saveTimetable(@NonNull Context context, @NonNull MedicineIdType medicineId, @NonNull MedicineTimetableList timetableList) {
        IsOneShotType isOneShotType = new IsOneShotType(false);
        for (Timetable timetable : timetableList) {
            // 追加データの作成
            Map<ColumnBase, ADbType> insertData = new HashMap<>();
            insertData.put(COLUMN_MEDICINE_ID, medicineId);
            insertData.put(COLUMN_TIMETABLE_ID, timetable.getTimetableId());
            insertData.put(COLUMN_IS_ONE_SHOT, isOneShotType);

            // レコード追加
            insert(context, insertData);
        }
    }

}
