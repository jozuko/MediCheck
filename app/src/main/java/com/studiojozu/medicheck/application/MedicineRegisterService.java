package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.MediTimeRelationRepository;
import com.studiojozu.medicheck.domain.model.PersonMediRelationRepository;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineViewRepository;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleList;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleRepository;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleService;
import com.studiojozu.medicheck.infrastructure.InfrastructureRegistry;
import com.studiojozu.medicheck.infrastructure.adapter.IPersistenceTransaction;

public class MedicineRegisterService {

    @NonNull
    private final Context mContext;
    @NonNull
    private final MedicineViewRepository mMedicineViewRepository;
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
        mPersistenceTransaction = InfrastructureRegistry.getPersistenceTransaction(mContext);
        mMedicineViewRepository = InfrastructureRegistry.getMedicineRepository();
        mPersonMediRelationRepository = InfrastructureRegistry.getPersonMediRelationRepository();
        mMediTimeRelationRepository = InfrastructureRegistry.getMediTimeRelationRepository();
        mScheduleRepository = InfrastructureRegistry.getScheduleRepository();
    }

    public void registerMedicine(PersonIdType personIdType, @NonNull Medicine medicine) {
        try {
            mPersistenceTransaction.beginTransaction();

            mMedicineViewRepository.add(mContext, medicine);
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
