package com.studiojozu.medicheck.domain.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList;

public interface MediTimeViewRepository {
    @NonNull
    MedicineTimetableList findTimetableListByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineIdType);
}
