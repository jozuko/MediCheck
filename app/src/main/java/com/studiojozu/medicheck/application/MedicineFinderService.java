package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineRepository;
import com.studiojozu.medicheck.infrastructure.adapter.PersistenceAdapter;

import java.util.Set;

public class MedicineFinderService {

    @NonNull
    private final Context mContext;
    @NonNull
    private final MedicineRepository mMedicineRepository = PersistenceAdapter.getMedicineRepository();

    public MedicineFinderService(@NonNull Context context) {
        mContext = context;
    }

    public boolean existMedicine() {
        Set<Medicine> medicineSet = mMedicineRepository.findAll(mContext);
        if (medicineSet == null)
            return false;

        return (medicineSet.size() > 0);
    }
}
