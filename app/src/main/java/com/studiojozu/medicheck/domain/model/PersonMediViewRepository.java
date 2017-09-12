package com.studiojozu.medicheck.domain.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;

import java.util.Set;

public interface PersonMediViewRepository {

    @Nullable
    Person findPersonByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineIdType);

    @Nullable
    Set<Medicine> findMedicineByPersonId(@NonNull Context context, @NonNull PersonIdType personIdType);

    boolean existByPersonIdMedicineId(@NonNull Context context, @NonNull PersonIdType personIdType, @NonNull MedicineIdType medicineIdType);

    int getPersonNumberByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineIdType);

    int getMedicineNumberByPersonId(@NonNull Context context, @NonNull PersonIdType personIdType);
}
