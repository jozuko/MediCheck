package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineViewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SqliteMedicineViewRepository extends ABaseRepository implements MedicineViewRepository {
    static final String TABLE_NAME = "medicine_view";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(SqliteMedicineRepository.COLUMN_ID);
        columns.add(SqliteMedicineRepository.COLUMN_NAME);
        columns.add(SqliteMedicineRepository.COLUMN_TAKE_NUMBER);
        columns.add(SqliteMedicineRepository.COLUMN_DATE_NUMBER);
        columns.add(SqliteMedicineRepository.COLUMN_START_DATETIME);
        columns.add(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL);
        columns.add(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL_MODE);
        columns.add(SqliteMedicineRepository.COLUMN_PHOTO);
        columns.add(SqliteMedicineRepository.COLUMN_NEED_ALARM);
        columns.add(SqliteMedicineRepository.COLUMN_DELETE_FLG);

        columns.add(SqliteMedicineUnitRepository.COLUMN_ID);
        columns.add(SqliteMedicineUnitRepository.COLUMN_VALUE);
        COLUMNS = new Columns(columns);
    }

    private final SqliteMedicineRepository mSqliteMedicineRepository = new SqliteMedicineRepository();
    private final SqliteMedicineUnitRepository mSqliteMedicineUnitRepository = new SqliteMedicineUnitRepository();

    @Override
    protected String getCreateTableSQL() {
        StringBuilder builder = new StringBuilder();

        builder.append("create view ").append(TABLE_NAME);
        builder.append(" as ");
        builder.append("select");

        builder.append(" ").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_ID.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_ID.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_NAME.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_NAME.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_TAKE_NUMBER.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_TAKE_NUMBER.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_DATE_NUMBER.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_DATE_NUMBER.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_START_DATETIME.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_START_DATETIME.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL_MODE.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL_MODE.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_PHOTO.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_PHOTO.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_NEED_ALARM.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_NEED_ALARM.mColumnName);
        builder.append(",").append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_DELETE_FLG.mColumnName).append(" as ").append(SqliteMedicineRepository.COLUMN_DELETE_FLG.mColumnName);

        builder.append(",").append(SqliteMedicineUnitRepository.TABLE_NAME).append(".").append(SqliteMedicineUnitRepository.COLUMN_ID.mColumnName).append(" as ").append(SqliteMedicineUnitRepository.COLUMN_ID.mColumnName);
        builder.append(",").append(SqliteMedicineUnitRepository.TABLE_NAME).append(".").append(SqliteMedicineUnitRepository.COLUMN_VALUE.mColumnName).append(" as ").append(SqliteMedicineUnitRepository.COLUMN_VALUE.mColumnName);

        builder.append(" from ");
        builder.append(SqliteMedicineRepository.TABLE_NAME).append(",");
        builder.append(SqliteMedicineUnitRepository.TABLE_NAME);

        builder.append(" where ");
        builder.append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineUnitRepository.COLUMN_ID.mColumnName).append("=");
        builder.append(SqliteMedicineUnitRepository.TABLE_NAME).append(".").append(SqliteMedicineUnitRepository.COLUMN_ID.mColumnName);

        return builder.toString();
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

        List<Map<ColumnBase, ADbType>> dataList = find(context, SqliteMedicineRepository.COLUMN_ID.getEqualsCondition(), whereList, null);

        if (dataList.size() == 0)
            return null;

        return new SqliteMedicineViewConverter(dataList.get(0)).createFromRecord();
    }

    @Override
    @Nullable
    public Set<Medicine> findAll(@NonNull Context context) {
        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, null, null, null);

        if (databaseRecords.size() == 0)
            return null;

        Set<Medicine> medicineSet = new TreeSet<>();
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            Medicine medicine = new SqliteMedicineViewConverter(record).createFromRecord();
            medicineSet.add(medicine);
        }

        return medicineSet;
    }

    @Override
    public void add(@NonNull Context context, @NonNull Medicine medicine) {
        mSqliteMedicineRepository.add(context, medicine);
        mSqliteMedicineUnitRepository.add(context, medicine.getMedicineUnit());
    }

    @Override
    public void remove(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        mSqliteMedicineRepository.remove(context, medicineIdType);
    }
}
