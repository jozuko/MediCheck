package com.studiojozu.medicheck.domain.model.medicine;

import android.content.Context;
import android.support.annotation.NonNull;

public interface MedicineRepository {
    Medicine findMedicineById(@NonNull Context context, @NonNull MedicineIdType medicineIdType);

    void add(@NonNull Context context, @NonNull Medicine medicine);

    void remove(@NonNull Context context, @NonNull MedicineIdType medicineIdType);
}
