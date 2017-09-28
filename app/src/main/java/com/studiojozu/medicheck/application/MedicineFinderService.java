package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitComparator;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitRepository;
import com.studiojozu.medicheck.domain.model.medicine.MedicineViewRepository;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableComparator;
import com.studiojozu.medicheck.domain.model.setting.TimetableRepository;
import com.studiojozu.medicheck.infrastructure.InfrastructureRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MedicineFinderService {

    @NonNull
    private final Context mContext;
    @NonNull
    private final MedicineViewRepository mMedicineViewRepository = InfrastructureRegistry.getMedicineRepository();

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
    public Medicine findByIdNothingDefault(@Nullable MedicineIdType medicineIdType) {
        if (medicineIdType == null)
            return createDefaultMedicine(null);

        Medicine medicine = mMedicineViewRepository.findMedicineById(mContext, medicineIdType);
        if (medicine == null)
            return createDefaultMedicine(medicineIdType);

        return medicine;
    }

    @NonNull
    private Medicine createDefaultMedicine(@Nullable MedicineIdType medicineIdType) {
        Medicine medicine;
        if (medicineIdType == null)
            medicine = new Medicine();
        else
            medicine = new Medicine(medicineIdType);

        medicine.setMedicineName("");
        medicine.setTakeNumber(1);
        medicine.setMedicineUnit(getDefaultMedicineUnit());
        medicine.setDateNumber(7);
        medicine.setStartDatetime(System.currentTimeMillis());
        medicine.setTakeInterval(0, TakeIntervalModeType.DateIntervalPattern.DAYS);
        medicine.setMedicinePhoto("");
        medicine.setTimetableList(getDefaultTimetable());
        medicine.setOneShowMedicine(false);
        medicine.setNeedAlarm(true);

        return medicine;
    }

    private MedicineUnit getDefaultMedicineUnit() {
        MedicineUnitRepository medicineUnitRepository = InfrastructureRegistry.getMedicineUnitRepository();
        List<MedicineUnit> medicineUnitList = new ArrayList<>(medicineUnitRepository.findAll(mContext));
        Collections.sort(medicineUnitList, new MedicineUnitComparator());

        return (medicineUnitList.size() > 0 ? medicineUnitList.get(0) : new MedicineUnit());
    }

    private List<Timetable> getDefaultTimetable() {
        TimetableRepository timetableRepository = InfrastructureRegistry.getTimetableRepository();
        List<Timetable> timetableList = new ArrayList<>(timetableRepository.findAll(mContext));
        Collections.sort(timetableList, new TimetableComparator(TimetableComparator.ComparePattern.DisplayOrder));

        List<Timetable> medicineTimetableList1 = new ArrayList<>();
        if (timetableList.size() > 0)
            medicineTimetableList1.add(timetableList.get(0));

        if (timetableList.size() > 1)
            medicineTimetableList1.add(timetableList.get(1));

        return medicineTimetableList1;
    }


}
