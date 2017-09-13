package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.PersonMediViewRepository;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Medicine-Timetable-MediTimeRelationの結合View
 */
public class SqlitePersonMediViewRepository extends ABaseRepository implements PersonMediViewRepository {

    private static final String TABLE_NAME = "person_medi_view";
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
        columns.add(SqlitePersonRepository.COLUMN_ID);
        columns.add(SqlitePersonRepository.COLUMN_NAME);
        columns.add(SqlitePersonRepository.COLUMN_PHOTO);
        COLUMNS = new Columns(columns);
    }

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

        builder.append(",").append(SqlitePersonRepository.TABLE_NAME).append(".").append(SqlitePersonRepository.COLUMN_ID.mColumnName).append(" as ").append(SqlitePersonRepository.COLUMN_ID.mColumnName);
        builder.append(",").append(SqlitePersonRepository.TABLE_NAME).append(".").append(SqlitePersonRepository.COLUMN_NAME.mColumnName).append(" as ").append(SqlitePersonRepository.COLUMN_NAME.mColumnName);
        builder.append(",").append(SqlitePersonRepository.TABLE_NAME).append(".").append(SqlitePersonRepository.COLUMN_PHOTO.mColumnName).append(" as ").append(SqlitePersonRepository.COLUMN_PHOTO.mColumnName);

        builder.append(" from ");
        builder.append(SqlitePersonMediRelationRepository.TABLE_NAME).append(",");
        builder.append(SqliteMedicineRepository.TABLE_NAME).append(",");
        builder.append(SqlitePersonRepository.TABLE_NAME);

        builder.append(" where ");
        builder.append(SqlitePersonMediRelationRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_ID.mColumnName).append("=");
        builder.append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_ID.mColumnName);

        builder.append(" and ");
        builder.append(SqlitePersonMediRelationRepository.TABLE_NAME).append(".").append(SqlitePersonRepository.COLUMN_ID.mColumnName).append("=");
        builder.append(SqlitePersonRepository.TABLE_NAME).append(".").append(SqlitePersonRepository.COLUMN_ID.mColumnName);

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

    @Nullable
    @Override
    public Person findPersonByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineIdType);

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, SqliteMedicineRepository.COLUMN_ID.getEqualsCondition(), whereList, null);
        if (databaseRecords.size() == 0)
            return null;

        return new SqlitePersonConverter(databaseRecords.get(0)).createFromRecord();
    }

    @Nullable
    @Override
    public Set<Medicine> findMedicineByPersonId(@NonNull Context context, @NonNull PersonIdType personIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, SqlitePersonRepository.COLUMN_ID.getEqualsCondition(), whereList, null);
        if (databaseRecords.size() == 0)
            return null;

        Set<Medicine> medicineSet = new HashSet<>();
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            medicineSet.add(new SqliteMedicineConverter(record).createFromRecord());
        }

        return medicineSet;
    }

    @Override
    public boolean existByPersonIdMedicineId(@NonNull Context context, @NonNull PersonIdType personIdType, @NonNull MedicineIdType medicineIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);
        whereList.add(medicineIdType);

        String where = SqlitePersonRepository.COLUMN_ID.getEqualsCondition() + " and " + SqliteMedicineRepository.COLUMN_ID.getEqualsCondition();

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, where, whereList, null);
        return (databaseRecords.size() > 0);
    }

    @Override
    public int getPersonNumberByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineIdType);

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, SqliteMedicineRepository.COLUMN_ID.getEqualsCondition(), whereList, null);
        return databaseRecords.size();
    }

    @Override
    public int getMedicineNumberByPersonId(@NonNull Context context, @NonNull PersonIdType personIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, SqlitePersonRepository.COLUMN_ID.getEqualsCondition(), whereList, null);
        return databaseRecords.size();
    }
}
