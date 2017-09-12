package com.studiojozu.medicheck.domain.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList;

public interface MediTimeRelationRepository {
    void remove(@NonNull Context context, @NonNull MedicineIdType medicineId);

    void add(@NonNull Context context, @NonNull MedicineIdType medicineId, @NonNull MedicineTimetableList timetableList);
}
