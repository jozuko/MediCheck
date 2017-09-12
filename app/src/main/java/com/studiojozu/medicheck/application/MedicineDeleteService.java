package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.MediTimeRelationRepository;
import com.studiojozu.medicheck.domain.model.PersonMediRelationRepository;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineRepository;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleRepository;
import com.studiojozu.medicheck.infrastructure.adapter.IPersistenceTransaction;
import com.studiojozu.medicheck.infrastructure.adapter.PersistenceAdapter;

/**
 *
 */
public class MedicineDeleteService {

    @NonNull
    private final Context mContext;
    @NonNull
    private final MedicineRepository mMedicineRepository;
    @NonNull
    private final PersonMediRelationRepository mPersonMediRelationRepository;
    @NonNull
    private final IPersistenceTransaction mPersistenceTransaction;
    @NonNull
    private final MediTimeRelationRepository mMediTimeRelationRepository;
    @NonNull
    private final ScheduleRepository mScheduleRepository;

    public MedicineDeleteService(@NonNull Context context) {
        mContext = context;
        mPersistenceTransaction = PersistenceAdapter.getPersistenceTransaction(mContext);
        mMedicineRepository = PersistenceAdapter.getMedicineRepository();
        mPersonMediRelationRepository = PersistenceAdapter.getPersonMediRelationRepository();
        mMediTimeRelationRepository = PersistenceAdapter.getMediTimeRelationRepository();
        mScheduleRepository = PersistenceAdapter.getScheduleRepository();
    }

    public void deleteMedicine(@NonNull MedicineIdType medicineId) {
        try {
            mPersistenceTransaction.beginTransaction();

            mMedicineRepository.remove(mContext, medicineId);
            mPersonMediRelationRepository.remove(mContext, medicineId);
            mMediTimeRelationRepository.remove(mContext, medicineId);
            mScheduleRepository.removeIgnoreHistory(mContext, medicineId);

            mPersistenceTransaction.commit();
        } catch (Exception e) {
            mPersistenceTransaction.rollback();
        }

    }

}
