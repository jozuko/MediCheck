package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.DateNumberType;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameType;
import com.studiojozu.medicheck.domain.model.medicine.MedicinePhotoType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList;
import com.studiojozu.medicheck.domain.model.medicine.StartDatetimeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalType;
import com.studiojozu.medicheck.domain.model.medicine.TakeNumberType;

import java.util.Map;

/**
 * DBのレコードから{@link com.studiojozu.medicheck.domain.model.medicine.Medicine}を生成する
 */
class SqliteMedicineConverter extends ASqliteConverter<Medicine> {

    SqliteMedicineConverter(@Nullable Map<ColumnBase, ADbType> databaseRecord) {
        super(databaseRecord);
    }

    @Override
    @Nullable
    Medicine createFromRecord() {
        if (mDatabaseRecord == null) return new Medicine();

        return new Medicine(
                getMedicineIdType(),
                getMedicineNameType(),
                getTakeNumberType(),
                getDateNumberType(),
                getStartDatetimeType(),
                getTakeIntervalType(),
                getTakeIntervalModeType(),
                getMedicinePhotoType(),
                new MedicineTimetableList()
        );
    }

    private MedicineIdType getMedicineIdType() {
        MedicineIdType medicineIdType = (MedicineIdType) getData(SqliteMedicineRepository.COLUMN_ID);
        if (medicineIdType != null) return medicineIdType;
        return new MedicineIdType();
    }

    private MedicineNameType getMedicineNameType() {
        MedicineNameType medicineNameType = (MedicineNameType) getData(SqliteMedicineRepository.COLUMN_NAME);
        if (medicineNameType != null) return medicineNameType;
        return new MedicineNameType();
    }

    private TakeNumberType getTakeNumberType() {
        TakeNumberType takeNumberType = (TakeNumberType) getData(SqliteMedicineRepository.COLUMN_TAKE_NUMBER);
        if (takeNumberType != null) return takeNumberType;
        return new TakeNumberType();
    }

    private DateNumberType getDateNumberType() {
        DateNumberType dateNumberType = (DateNumberType) getData(SqliteMedicineRepository.COLUMN_DATE_NUMBER);
        if (dateNumberType != null) return dateNumberType;
        return new DateNumberType();
    }

    private StartDatetimeType getStartDatetimeType() {
        StartDatetimeType startDatetimeType = (StartDatetimeType) getData(SqliteMedicineRepository.COLUMN_START_DATETIME);
        if (startDatetimeType != null) return startDatetimeType;
        return new StartDatetimeType();
    }

    private TakeIntervalType getTakeIntervalType() {
        TakeIntervalType takeIntervalType = (TakeIntervalType) getData(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL);
        if (takeIntervalType != null) return takeIntervalType;
        return new TakeIntervalType();
    }

    private TakeIntervalModeType getTakeIntervalModeType() {
        TakeIntervalModeType takeIntervalModeType = (TakeIntervalModeType) getData(SqliteMedicineRepository.COLUMN_TAKE_INTERVAL_MODE);
        if (takeIntervalModeType != null) return takeIntervalModeType;
        return new TakeIntervalModeType();
    }

    private MedicinePhotoType getMedicinePhotoType() {
        MedicinePhotoType medicinePhotoType = (MedicinePhotoType) getData(SqliteMedicineRepository.COLUMN_PHOTO);
        if (medicinePhotoType != null) return medicinePhotoType;
        return new MedicinePhotoType();
    }
}
