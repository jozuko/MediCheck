package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType;

import java.util.Map;

/**
 * DBのレコードから{@link com.studiojozu.medicheck.domain.model.medicine.MedicineUnit}を生成する
 */
class SqliteMedicineUnitConverter extends ASqliteConverter<MedicineUnit> {

    SqliteMedicineUnitConverter(@Nullable Map<ColumnBase, ADbType> databaseRecord) {
        super(databaseRecord);
    }

    @Override
    @Nullable
    MedicineUnit createFromRecord() {
        if (mDatabaseRecord == null) return null;

        return new MedicineUnit(
                getMedicineUnitIdType(),
                getMedicineUnitValueType(),
                getMedicineUnitDisplayOrderType()
        );
    }

    private MedicineUnitIdType getMedicineUnitIdType() {
        MedicineUnitIdType medicineUnitIdType = (MedicineUnitIdType) getData(SqliteMedicineUnitRepository.COLUMN_ID);
        if (medicineUnitIdType != null) return medicineUnitIdType;
        return new MedicineUnitIdType();
    }

    private MedicineUnitValueType getMedicineUnitValueType() {
        MedicineUnitValueType medicineUnitValueType = (MedicineUnitValueType) getData(SqliteMedicineUnitRepository.COLUMN_VALUE);
        if (medicineUnitValueType != null) return medicineUnitValueType;
        return new MedicineUnitValueType();
    }

    private MedicineUnitDisplayOrderType getMedicineUnitDisplayOrderType() {
        MedicineUnitDisplayOrderType medicineUnitDisplayOrderType = (MedicineUnitDisplayOrderType) getData(SqliteMedicineUnitRepository.COLUMN_DISPLAY_ORDER);
        if (medicineUnitDisplayOrderType != null) return medicineUnitDisplayOrderType;
        return new MedicineUnitDisplayOrderType();
    }
}
