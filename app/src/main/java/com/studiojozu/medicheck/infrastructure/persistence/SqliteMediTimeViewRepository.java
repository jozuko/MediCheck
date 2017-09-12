package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.MediTimeViewRepository;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableDisplayOrderType;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType;
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Medicine-Timetable-MediTimeRelationの結合View
 */
public class SqliteMediTimeViewRepository extends ABaseRepository implements MediTimeViewRepository {

    private static final String TABLE_NAME = "medi_time_view";
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
        columns.add(SqliteMediTimeRelationRepository.COLUMN_IS_ONE_SHOT);
        columns.add(SqliteTimetableRepository.COLUMN_ID);
        columns.add(SqliteTimetableRepository.COLUMN_NAME);
        columns.add(SqliteTimetableRepository.COLUMN_TIME);
        columns.add(SqliteTimetableRepository.COLUMN_DISPLAY_ORDER);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected String getCreateTableSQL() {
        StringBuilder builder = new StringBuilder();

        builder.append("create view ").append(TABLE_NAME);
        builder.append(" as ");
        builder.append("select ").append(COLUMNS.getAllColumnNameSplitComma());

        builder.append(" from ");
        builder.append(SqliteMediTimeRelationRepository.TABLE_NAME).append(",");
        builder.append(SqliteMedicineRepository.TABLE_NAME).append(",");
        builder.append(SqliteTimetableRepository.TABLE_NAME);

        builder.append(" where ");
        builder.append(SqliteMediTimeRelationRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_ID.mColumnName).append("=");
        builder.append(SqliteMedicineRepository.TABLE_NAME).append(".").append(SqliteMedicineRepository.COLUMN_ID.mColumnName);

        builder.append(" and ");
        builder.append(SqliteMediTimeRelationRepository.TABLE_NAME).append(".").append(SqliteTimetableRepository.COLUMN_ID.mColumnName).append("=");
        builder.append(SqliteTimetableRepository.TABLE_NAME).append(".").append(SqliteTimetableRepository.COLUMN_ID.mColumnName);

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
    @NonNull
    public MedicineTimetableList findTimetableListByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineIdType);

        String orderBy = SqliteTimetableRepository.COLUMN_DISPLAY_ORDER.mColumnName;

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, SqliteMedicineRepository.COLUMN_ID.getEqualsCondition(), whereList, orderBy);
        if (databaseRecords.size() == 0)
            return new MedicineTimetableList();

        ArrayList<Timetable> timetableList = new ArrayList<>();
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            TimetableIdType timetableIdType = (TimetableIdType) record.get(SqliteTimetableRepository.COLUMN_ID);
            TimetableNameType timetableNameType = (TimetableNameType) record.get(SqliteTimetableRepository.COLUMN_NAME);
            TimetableTimeType timetableTimeType = (TimetableTimeType) record.get(SqliteTimetableRepository.COLUMN_TIME);
            TimetableDisplayOrderType timetableDisplayOrderType = (TimetableDisplayOrderType) record.get(SqliteTimetableRepository.COLUMN_DISPLAY_ORDER);

            Timetable timetable = new Timetable(timetableIdType, timetableNameType, timetableTimeType, timetableDisplayOrderType);
            timetableList.add(timetable);
        }

        return new MedicineTimetableList(timetableList);
    }
}
