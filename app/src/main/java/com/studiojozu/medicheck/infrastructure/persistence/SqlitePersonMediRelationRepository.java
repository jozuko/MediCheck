package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.PersonMediRelationRepository;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Person-MedicineのRelation
 * <ol>
 * <li>person_id 飲む人ID</li>
 * <li>medicine_id 薬ID</li>
 * </ol>
 * person_id:medicine_id=1:N
 */
public class SqlitePersonMediRelationRepository extends ABaseRepository implements PersonMediRelationRepository {
    /** 飲む人ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PARSON_ID = new ColumnBase(SqlitePersonRepository.COLUMN_ID, PrimaryPattern.Primary);
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase(SqliteMedicineRepository.COLUMN_ID, PrimaryPattern.Primary);
    static final String TABLE_NAME = "person_medi_relation";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_PARSON_ID);
        columns.add(COLUMN_MEDICINE_ID);
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
    public void add(@NonNull Context context, @NonNull PersonIdType personId, @NonNull MedicineIdType medicineIdType) {
        // 薬と人が一致するレコードが登録されていれば、何もせずに終了する
        SqlitePersonMediViewRepository viewRepository = new SqlitePersonMediViewRepository();
        if (viewRepository.existByPersonIdMedicineId(context, personId, medicineIdType)) return;

        // 新規データ追加
        insert(context, personId, medicineIdType);
    }

    private void insert(@NonNull Context context, @NonNull PersonIdType personId, @NonNull MedicineIdType medicineId) {
        // 追加データの作成
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_PARSON_ID, personId);
        insertData.put(COLUMN_MEDICINE_ID, medicineId);

        // レコード追加
        insert(context, insertData);
    }

    @Override
    public void remove(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        SqlitePersonMediViewRepository viewRepository = new SqlitePersonMediViewRepository();

        // ユーザ数が0なら何もしないで終了
        int personNumber = viewRepository.getPersonNumberByMedicineId(context, medicineIdType);
        if (personNumber <= 0) return;

        // 飲む人-薬Relationテーブルから該当IDを削除
        String whereClause = COLUMN_PARSON_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineIdType);
        delete(context, whereClause, whereList);
    }

    void remove(@NonNull Context context, @NonNull PersonIdType personIdType) {
        SqlitePersonMediViewRepository viewRepository = new SqlitePersonMediViewRepository();

        // 薬の数が0なら何もしないで終了
        int medicineNumber = viewRepository.getMedicineNumberByPersonId(context, personIdType);
        if (medicineNumber <= 0) return;

        // 飲む人-薬Relationテーブルから該当IDを削除
        String whereClause = COLUMN_PARSON_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);
        delete(context, whereClause, whereList);
    }
}
