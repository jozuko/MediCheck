package com.studiojozu.medicheck.domain.model.medicine.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType;

import java.util.Collection;

public interface MedicineUnitRepository {
    @Nullable
    MedicineUnit findMedicineUnitById(@NonNull Context context, MedicineUnitIdType medicineUnitIdType);

    @NonNull
    Collection<MedicineUnit> findAll(@NonNull Context context);

    void add(@NonNull Context context, @NonNull MedicineUnit medicineUnit);

}
