package com.studiojozu.medicheck.database.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.IntType;

import java.util.ArrayList;
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
    public static final ColumnBase COLUMN_PARSON_ID = new ColumnBase("parson_id", ColumnPattern.ID, PrimaryPattern.Primary);
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnPattern.ID, PrimaryPattern.Primary);

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

    /**
     * パラメータの薬IDに一致するすべての人ID（想定1件）を取得する
     *
     * @param context    アプリケーションコンテキスト
     * @param medicineId 薬ID
     * @return パラメータの薬IDに一致するすべての人ID（想定1件）
     */
    @Nullable
    public TreeSet<IntType> findParsonIdsByMedicineId(@NonNull Context context, @NonNull IntType medicineId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(medicineId);

            List<Map<ColumnBase, ADbType>> relations = find(readonlyDatabase, COLUMN_MEDICINE_ID.getEqualsCondition(), whereList);
            if (relations.size() == 0) return null;

            TreeSet<IntType> parsonIds = new TreeSet<>();
            for (Map<ColumnBase, ADbType> relation : relations) {
                parsonIds.add((IntType) relation.get(COLUMN_PARSON_ID));
            }

            return parsonIds;
        } finally {
            readonlyDatabase.close();
        }
    }
}
