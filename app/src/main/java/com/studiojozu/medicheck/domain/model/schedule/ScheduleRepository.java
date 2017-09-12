package com.studiojozu.medicheck.domain.model.schedule;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;

import java.util.List;

public interface ScheduleRepository {
    List<Schedule> getNeedAlerts(@NonNull Context context);

    void addAll(@NonNull Context context, @NonNull MedicineIdType medicineId, @NonNull ScheduleList scheduleList);

    void removeIgnoreHistory(@NonNull Context context, @NonNull MedicineIdType medicineId);
}
