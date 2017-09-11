package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.parson.ParsonIdType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Parson-MedicineのRelation
 * <ol>
 * <li>parson_id 飲む人ID</li>
 * <li>medicine_id 薬ID</li>
 * </ol>
 * parson_id:medicine_id=1:N
 */
public class ParsonMediRelationRepository extends ABaseRepository {
    /** 飲む人ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PARSON_ID = new ColumnBase("parson_id", ColumnPattern.PARSON_ID, PrimaryPattern.Primary);
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnPattern.MEDICINE_ID, PrimaryPattern.Primary);
    private static final String TABLE_NAME;
    private static final Columns COLUMNS;

    static {
        TABLE_NAME = "parson_medi_relation";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_PARSON_ID);
        columns.add(COLUMN_MEDICINE_ID);
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
     * パラメータの薬IDに一致するすべての人ID（想定1件）を取得する
     *
     * @param context    アプリケーションコンテキスト
     * @param medicineId 薬ID
     * @return パラメータの薬IDに一致するすべての人ID（想定1件）
     */
    @Nullable
    public TreeSet<ParsonIdType> findParsonIdsByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            return findParsonIdsByMedicineId(readonlyDatabase, medicineId);
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * パラメータの薬IDに一致するすべての人ID（想定1件）を取得する
     *
     * @param database   Readableなデータベースインスタンス
     * @param medicineId 薬ID
     * @return パラメータの薬IDに一致するすべての人ID（想定1件）
     */
    @Nullable
    private TreeSet<ParsonIdType> findParsonIdsByMedicineId(@NonNull ADatabase database, @NonNull MedicineIdType medicineId) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);

        List<Map<ColumnBase, ADbType>> relations = find(database, COLUMN_MEDICINE_ID.getEqualsCondition(), whereList);
        if (relations.size() == 0) return null;

        TreeSet<ParsonIdType> parsonIds = new TreeSet<>();
        for (Map<ColumnBase, ADbType> relation : relations) {
            parsonIds.add((ParsonIdType) relation.get(COLUMN_PARSON_ID));
        }

        return parsonIds;
    }

    /**
     * パラメータの人IDに一致するすべての薬IDを取得する
     *
     * @param context  アプリケーションコンテキスト
     * @param parsonId 人ID
     * @return パラメータの人IDに一致するすべての薬ID
     */
    @Nullable
    public TreeSet<MedicineIdType> findMedicineIdsByParsonId(@NonNull Context context, @NonNull ParsonIdType parsonId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            return findMedicineIdsByParsonId(readonlyDatabase, parsonId);
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * パラメータの人IDに一致するすべての薬IDを取得する
     *
     * @param database 読み込み可能データベースインスタンス
     * @param parsonId 人ID
     * @return パラメータの人IDに一致するすべての薬ID
     */
    @Nullable
    private TreeSet<MedicineIdType> findMedicineIdsByParsonId(@NonNull ADatabase database, @NonNull ParsonIdType parsonId) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(parsonId);
        List<Map<ColumnBase, ADbType>> relations = find(database, COLUMN_PARSON_ID.getEqualsCondition(), whereList);
        if (relations.size() == 0) return null;

        TreeSet<MedicineIdType> medicineIds = new TreeSet<>();
        for (Map<ColumnBase, ADbType> relation : relations) {
            medicineIds.add((MedicineIdType) relation.get(COLUMN_MEDICINE_ID));
        }

        return medicineIds;
    }

    /**
     * 飲む人IDをキーとして、テーブルから該当レコードを削除する。
     *
     * @param database Writableなデータベースインスタンス
     * @param parsonId 飲む人ID
     */
    void deleteByParsonId(@NonNull WritableDatabase database, @NonNull ParsonIdType parsonId) {
        // 薬一覧の情報を取得する
        TreeSet<MedicineIdType> medicineIds = findMedicineIdsByParsonId(database, parsonId);
        if (medicineIds == null || medicineIds.size() == 0) return;

        // 飲む人-薬Relationテーブルから該当IDを削除
        String whereClause = COLUMN_PARSON_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(parsonId);
        delete(database, whereClause, whereList);

        // 薬テーブルから該当IDを削除
        for (MedicineIdType medicineId : medicineIds) {
            new MedicineRepository().delete(database, medicineId);
        }
    }

    /**
     * 薬IDをキーとして、テーブルから該当レコードを削除する。
     *
     * @param database   Writableなデータベースインスタンス
     * @param medicineId 薬ID
     */
    void deleteByMedicineId(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId) {
        // 薬一覧の情報を取得する
        TreeSet<ParsonIdType> parsonIds = findParsonIdsByMedicineId(database, medicineId);
        if (parsonIds == null || parsonIds.size() == 0) return;

        // 飲む人-薬Relationテーブルから該当IDを削除
        String whereClause = COLUMN_PARSON_ID.getEqualsCondition();
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        delete(database, whereClause, whereList);
    }

    void save(@NotNull WritableDatabase database, @NonNull ParsonIdType parsonId, @NonNull MedicineIdType medicineId) {
        // 薬と人が一致するレコードが登録されているかチェックする
        TreeSet<ParsonIdType> parsonIds = findParsonIdsByMedicineId(database, medicineId);

        // すでに登録されていれば何もせずに終了する
        if (parsonIds != null && parsonIds.contains(parsonId)) return;

        // 新規データ追加
        insert(database, parsonId, medicineId);
    }

    private void insert(@NotNull WritableDatabase database, @NonNull ParsonIdType parsonId, @NonNull MedicineIdType medicineId) {
        // 追加データの作成
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_PARSON_ID, parsonId);
        insertData.put(COLUMN_MEDICINE_ID, medicineId);

        // レコード追加
        insert(database, insertData);
    }
}
