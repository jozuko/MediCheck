package com.studiojozu.medicheck.domain.model.medicine.comparator;

import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;

import java.util.Comparator;

public class MedicineUnitComparator implements Comparator<MedicineUnit> {

    @SuppressWarnings("ConstantConditions")
    @Override
    public int compare(@Nullable MedicineUnit medicineUnit1, @Nullable MedicineUnit medicineUnit2) {
        if (medicineUnit1 == null && medicineUnit2 == null) return 0;
        if (medicineUnit1 == null && medicineUnit2 != null) return -1;
        if (medicineUnit1 != null && medicineUnit2 == null) return 1;

        return medicineUnit1.getDisplayValue().compareTo(medicineUnit2.getDisplayValue());
    }
}
