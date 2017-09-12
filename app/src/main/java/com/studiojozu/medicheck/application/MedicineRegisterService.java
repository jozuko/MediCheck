package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.MediTimeRelationRepository;
import com.studiojozu.medicheck.domain.model.PersonMediRelationRepository;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineRepository;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleList;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleRepository;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleService;
import com.studiojozu.medicheck.infrastructure.adapter.IPersistenceTransaction;
import com.studiojozu.medicheck.infrastructure.adapter.PersistenceAdapter;

public class MedicineRegisterService {

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

    public MedicineRegisterService(@NonNull Context context) {
        mContext = context;
        mPersistenceTransaction = PersistenceAdapter.getPersistenceTransaction(mContext);
        mMedicineRepository = PersistenceAdapter.getMedicineRepository();
        mPersonMediRelationRepository = PersistenceAdapter.getPersonMediRelationRepository();
        mMediTimeRelationRepository = PersistenceAdapter.getMediTimeRelationRepository();
        mScheduleRepository = PersistenceAdapter.getScheduleRepository();
    }

    public void registerMedicine(PersonIdType personIdType, @NonNull Medicine medicine) {
        try {
            mPersistenceTransaction.beginTransaction();

            mMedicineRepository.add(mContext, personIdType, medicine);
            mMediTimeRelationRepository.add(mContext, medicine.getMedicineId(), medicine.getTimetableList());
            mPersonMediRelationRepository.add(mContext, personIdType, medicine.getMedicineId());

            ScheduleList scheduleList = new ScheduleService().createScheduleList(medicine);
            mScheduleRepository.addAll(mContext, medicine.getMedicineId(), scheduleList);

            mPersistenceTransaction.commit();
        } catch (Exception e) {
            mPersistenceTransaction.rollback();
        }
    }
}