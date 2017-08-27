package com.studiojozu.medicheck.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.entity.Timetable;
import com.studiojozu.medicheck.entity.TimetableList;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.medicine.IsOneShotType;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.timetable.TimetableIdType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Medicine-TimetableのRelation
 * <ol>
 * <li>medicine_id 薬ID</li>
 * <li>is_one_shot 頓服？</li>
 * <li>timetable_id タイムテーブルID</li>
 * </ol>
 * medicine_id:timetable_id=1:N
 */
public class MediTimeRelationRepository extends ABaseRepository {
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnPattern.MEDICINE_ID, PrimaryPattern.Primary);
    /** タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase("timetable_id", ColumnPattern.TIMETABLE_ID, PrimaryPattern.Primary);
    /** 頓服？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_IS_ONE_SHOT = new ColumnBase("is_one_shot", ColumnPattern.MEDICINE_IS_ONE_SHOT);

    static {
        TABLE_NAME = "medi_time_relation";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_MEDICINE_ID);
        columns.add(COLUMN_TIMETABLE_ID);
        columns.add(COLUMN_IS_ONE_SHOT);
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
     * パラメータの薬IDに一致するすべてのタイムテーブルID一覧を取得する
     *
     * @param context    アプリケーションコンテキスト
     * @param medicineId 薬ID
     * @return パラメータの薬IDに一致するすべてのタイムテーブルID一覧
     */
    @Nullable
    public TreeSet<TimetableIdType> findTimetableIdsByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            return findTimetableIdsByMedicineId(readonlyDatabase, medicineId);
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * パラメータの人IDに一致するすべての薬IDを取得する
     *
     * @param database   読み込み可能データベースインスタンス
     * @param medicineId 人ID
     * @return パラメータの人IDに一致するすべての薬ID
     */
    @Nullable
    private TreeSet<TimetableIdType> findTimetableIdsByMedicineId(@NonNull ADatabase database, @NonNull MedicineIdType medicineId) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        List<Map<ColumnBase, ADbType>> relations = find(database, COLUMN_MEDICINE_ID.getEqualsCondition(), whereList);
        if (relations.size() == 0) return null;

        TreeSet<TimetableIdType> timetableIds = new TreeSet<>();
        for (Map<ColumnBase, ADbType> relation : relations) {
            timetableIds.add((TimetableIdType) relation.get(COLUMN_TIMETABLE_ID));
        }

        return timetableIds;
    }

    /**
     * 薬IDをキーとして、テーブルから該当レコードを削除する。
     *
     * @param database   Writableなデータベースインスタンス
     * @param medicineId 薬ID
     */
    void delete(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId) {
        // Medicine-TimetableのRelationテーブルから該当IDを削除
        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        delete(database, whereClause, whereList);
    }

    /**
     * 薬-タイムテーブルに保存する。
     * 一度すべてを削除し、新規追加する。
     *
     * @param database      書き込み可能データベースインスタンス
     * @param medicineId    薬ID
     * @param timetableList タイムテーブル一覧
     */
    void save(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId, @NonNull TimetableList timetableList) {

        // データの削除
        delete(database, medicineId);

        if (timetableList.isOneShot()) {
            saveOneShot(database, medicineId);
            return;
        }

        saveTimetable(database, medicineId, timetableList);
    }

    /**
     * 頓服薬として登録する
     */
    private void saveOneShot(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId) {

        TimetableIdType timetableId = new TimetableIdType();
        IsOneShotType isOneShotType = new IsOneShotType(true);

        // 追加データの作成
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_MEDICINE_ID, medicineId);
        insertData.put(COLUMN_TIMETABLE_ID, timetableId);
        insertData.put(COLUMN_IS_ONE_SHOT, isOneShotType);

        // レコード追加
        insert(database, insertData);
    }

    /**
     * 頓服薬ではない薬として登録する
     */
    private void saveTimetable(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId, @NonNull TimetableList timetableList) {
        IsOneShotType isOneShotType = new IsOneShotType(false);

        for (Timetable timetable : timetableList) {
            // 追加データの作成
            Map<ColumnBase, ADbType> insertData = new HashMap<>();
            insertData.put(COLUMN_MEDICINE_ID, medicineId);
            insertData.put(COLUMN_TIMETABLE_ID, timetable.getTimetableId());
            insertData.put(COLUMN_IS_ONE_SHOT, isOneShotType);

            // レコード追加
            insert(database, insertData);
        }
    }
}
