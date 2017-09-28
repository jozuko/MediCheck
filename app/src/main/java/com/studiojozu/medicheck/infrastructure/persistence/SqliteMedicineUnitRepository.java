package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * medicine-unit
 */
public class SqliteMedicineUnitRepository extends ABaseRepository implements MedicineUnitRepository {
    /** ID */
    static final ColumnBase COLUMN_ID = new ColumnBase("medicine_unit_id", ColumnPattern.MEDICINE_UNIT_ID, PrimaryPattern.Primary);
    /** 表示文字列 */
    static final ColumnBase COLUMN_VALUE = new ColumnBase("medicine_unit_value", ColumnPattern.MEDICINE_UNIT_VALUE);
    /** 表示順 */
    static final ColumnBase COLUMN_DISPLAY_ORDER = new ColumnBase("timetable_display_order", ColumnPattern.MEDICINE_UNIT_DISPLAY_ORDER);
    static final String TABLE_NAME = "medicine_unit";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_VALUE);
        columns.add(COLUMN_DISPLAY_ORDER);
        COLUMNS = new Columns(columns);
    }

    @NonNull
    private final Map<MedicineUnitIdType, MedicineUnit> mDataMap = new HashMap<>();

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase database) {
        if (database == null) return;

        int order = 1;
        if (Locale.getDefault().equals(Locale.JAPAN) || Locale.getDefault().equals(Locale.JAPANESE)) {
            Map<ColumnBase, ADbType> insertData = new HashMap<>();
            insertData.put(COLUMN_ID, COLUMN_ID.mColumnType.createNewInstance(null));
            insertData.put(COLUMN_VALUE, COLUMN_VALUE.mColumnType.createNewInstance(context.getResources().getString(R.string.medicine_unit_1)));
            insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order));
            insert(database, insertData);
        } else {
            Map<ColumnBase, ADbType> insertData = new HashMap<>();
            insertData.put(COLUMN_ID, COLUMN_ID.mColumnType.createNewInstance(null));
            insertData.put(COLUMN_VALUE, COLUMN_VALUE.mColumnType.createNewInstance(context.getResources().getString(R.string.medicine_unit_1)));
            insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(order));
            insert(database, insertData);
        }
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable SQLiteDatabase database, int oldVersion, int newVersion) {
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

    private void clearDataMap() {
        mDataMap.clear();
    }

    private synchronized void refreshAllUnits(@NonNull Context context) {
        clearDataMap();

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, null, null, null);
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            MedicineUnit medicineUnit = new SqliteMedicineUnitConverter(record).createFromRecord();
            if (medicineUnit == null)
                continue;

            mDataMap.put(medicineUnit.getMedicineUnitId(), medicineUnit);
        }
    }

    private void getAllUnits(@NonNull Context context) {
        if (mDataMap.size() == 0)
            refreshAllUnits(context);
    }

    @Override
    @Nullable
    public MedicineUnit findMedicineUnitById(@NonNull Context context, @NonNull MedicineUnitIdType medicineUnitId) {
        getAllUnits(context);

        if (mDataMap.containsKey(medicineUnitId))
            return mDataMap.get(medicineUnitId);
        return null;
    }

    @Override
    @NonNull
    public Collection<MedicineUnit> findAll(@NonNull Context context) {
        getAllUnits(context);
        return mDataMap.values();
    }

    @Override
    public void add(@NonNull Context context, @NonNull MedicineUnit medicineUnit) {
        getAllUnits(context);

        if (mDataMap.containsKey(medicineUnit.getMedicineUnitId()))
            update(context, medicineUnit);

        insert(context, medicineUnit);
        refreshAllUnits(context);
    }

    private void insert(@NonNull Context context, @NonNull MedicineUnit medicineUnit) {

        // 表示順を最後に設定
        medicineUnit.setMedicineUnitDisplayOrder(new MedicineUnitDisplayOrderType(mDataMap.size() + 1));

        // 追加データの作成
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_ID, medicineUnit.getMedicineUnitId());
        insertData.put(COLUMN_VALUE, medicineUnit.getMedicineUnitValue());
        insertData.put(COLUMN_DISPLAY_ORDER, medicineUnit.getMedicineUnitDisplayOrder());

        // レコード追加
        insert(context, insertData);
    }

    private void update(@NonNull Context context, @NonNull MedicineUnit medicineUnit) {
        // 更新データの作成
        Map<ColumnBase, ADbType> updateData = new HashMap<>();
        updateData.put(COLUMN_VALUE, medicineUnit.getMedicineUnitValue());
        updateData.put(COLUMN_DISPLAY_ORDER, medicineUnit.getMedicineUnitDisplayOrder());

        // 検索条件の作成
        String whereClause = COLUMN_ID.getEqualsCondition();
        ArrayList<ADbType> whereArgs = new ArrayList<>();
        whereArgs.add(medicineUnit.getMedicineUnitId());

        // レコード更新
        update(context, updateData, whereClause, whereArgs);
    }
}
