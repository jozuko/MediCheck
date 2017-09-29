package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepository;
import com.studiojozu.medicheck.infrastructure.InfrastructureRegistry;
import com.studiojozu.medicheck.infrastructure.adapter.IPersistenceTransaction;

public class MedicineUnitRegisterService {

    @NonNull
    private final Context mContext;
    @NonNull
    private final MedicineUnitRepository mMedicineUnitRepository = InfrastructureRegistry.getMedicineUnitRepository();
    @NonNull
    private final IPersistenceTransaction mPersistenceTransaction;

    public MedicineUnitRegisterService(@NonNull Context context) {
        mContext = context;
        mPersistenceTransaction = InfrastructureRegistry.getPersistenceTransaction(context);
    }

    public void register(@NonNull MedicineUnit medicineUnit) {
        try {
            mPersistenceTransaction.beginTransaction();
            mMedicineUnitRepository.add(mContext, medicineUnit);
            mPersistenceTransaction.commit();
        } catch (Exception e) {
            mPersistenceTransaction.rollback();
        }
    }
}
