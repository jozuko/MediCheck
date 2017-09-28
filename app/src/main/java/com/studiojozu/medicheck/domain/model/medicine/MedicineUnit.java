package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class MedicineUnit implements Serializable {

    private static final long serialVersionUID = 1450309381235440899L;
    @NonNull
    private final MedicineUnitIdType mMedicineUnitId;
    @NonNull
    private MedicineUnitValueType mMedicineUnitValue;
    @NonNull
    private MedicineUnitDisplayOrderType mMedicineUnitDisplayOrder;

    public MedicineUnit() {
        mMedicineUnitId = new MedicineUnitIdType();
        mMedicineUnitValue = new MedicineUnitValueType();
        mMedicineUnitDisplayOrder = new MedicineUnitDisplayOrderType();
    }

    public MedicineUnit(@NonNull MedicineUnitValueType medicineUnitValue, @NonNull MedicineUnitDisplayOrderType medicineUnitDisplayOrder) {
        this(new MedicineUnitIdType(), medicineUnitValue, medicineUnitDisplayOrder);
    }

    public MedicineUnit(@NonNull MedicineUnitIdType medicineUnitId, @NonNull MedicineUnitValueType medicineUnitValue, @NonNull MedicineUnitDisplayOrderType medicineUnitDisplayOrder) {
        mMedicineUnitId = medicineUnitId;
        mMedicineUnitValue = medicineUnitValue;
        mMedicineUnitDisplayOrder = medicineUnitDisplayOrder;
    }

    @NonNull
    public MedicineUnitIdType getMedicineUnitId() {
        return mMedicineUnitId;
    }

    @NonNull
    public MedicineUnitValueType getMedicineUnitValue() {
        return mMedicineUnitValue;
    }

    public void setMedicineUnitValue(@NonNull String unitValue) {
        mMedicineUnitValue = new MedicineUnitValueType(unitValue);
    }

    @NonNull
    public MedicineUnitDisplayOrderType getMedicineUnitDisplayOrder() {
        return mMedicineUnitDisplayOrder;
    }

    public void setMedicineUnitDisplayOrder(@NonNull MedicineUnitDisplayOrderType medicineUnitDisplayOrder) {
        mMedicineUnitDisplayOrder = medicineUnitDisplayOrder;
    }

    @NonNull
    public String getDisplayValue() {
        return mMedicineUnitValue.getDisplayValue();
    }
}
