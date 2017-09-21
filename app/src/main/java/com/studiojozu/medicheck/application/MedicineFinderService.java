package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineViewRepository;
import com.studiojozu.medicheck.infrastructure.adapter.PersistenceAdapter;

import java.util.Set;

public class MedicineFinderService {

    @NonNull
    private final Context mContext;
    @NonNull
    private final MedicineViewRepository mMedicineViewRepository = PersistenceAdapter.getMedicineRepository();

    public MedicineFinderService(@NonNull Context context) {
        mContext = context;
    }

    public boolean existMedicine() {
        Set<Medicine> medicineSet = mMedicineViewRepository.findAll(mContext);
        if (medicineSet == null)
            return false;

        return (medicineSet.size() > 0);
    }

    @NonNull
    public Medicine findById(@Nullable MedicineIdType medicineIdType) {
        if (medicineIdType == null)
            return new Medicine();

        Medicine medicine = mMedicineViewRepository.findMedicineById(mContext, medicineIdType);
        if (medicine == null)
            return new Medicine();

        return medicine;
    }
}
