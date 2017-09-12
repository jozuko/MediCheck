package com.studiojozu.medicheck.domain.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;

public interface PersonMediRelationRepository {
    void remove(@NonNull Context context, @NonNull MedicineIdType medicineIdType);

    void add(@NonNull Context context, @NonNull PersonIdType personId, @NonNull MedicineIdType medicineIdType);
}
