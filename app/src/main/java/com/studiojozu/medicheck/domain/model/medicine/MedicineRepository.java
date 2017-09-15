package com.studiojozu.medicheck.domain.model.medicine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Set;

public interface MedicineRepository {
    @Nullable
    Medicine findMedicineById(@NonNull Context context, @NonNull MedicineIdType medicineIdType);

    @Nullable
    Set<Medicine> findAll(@NonNull Context context);

    void add(@NonNull Context context, @NonNull Medicine medicine);

    void remove(@NonNull Context context, @NonNull MedicineIdType medicineIdType);
}
