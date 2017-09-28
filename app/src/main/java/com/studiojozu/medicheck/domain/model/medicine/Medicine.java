package com.studiojozu.medicheck.domain.model.medicine;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.setting.Timetable;

import java.io.Serializable;
import java.util.List;

/**
 * 薬を管理するクラス
 */
public class Medicine implements Serializable {
    private static final long serialVersionUID = -3626443464441488492L;

    /** 薬ID */
    @NonNull
    private final MedicineIdType mMedicineId;
    /** タイムテーブルの一覧 */
    @NonNull
    private final MedicineTimetableList mTimetableList;
    /** 薬の名前 */
    @NonNull
    private MedicineNameType mMedicineName;
    /** 服用数 */
    @NonNull
    private TakeNumberType mTakeNumber;
    /** 薬の単位 */
    @NonNull
    private MedicineUnit mMedicineUnit;
    /** 服用日数 */
    @NonNull
    private MedicineDateNumberType mDateNumber;
    /** 服用開始日時 */
    @NonNull
    private StartDatetimeType mStartDatetime;
    /** 服用間隔 */
    @NonNull
    private TakeIntervalType mTakeInterval;
    /** 服用間隔タイプ */
    @NonNull
    private TakeIntervalModeType mTakeIntervalMode;
    /** 薬の写真パス */
    @NonNull
    private MedicinePhotoType mMedicinePhoto;
    /** アラーム要否フラグ */
    @NonNull
    private MedicineNeedAlarmType mNeedAlarmType;
    /** 削除フラグ */
    @NonNull
    private DeleteFlagType mDeleteFlag;

    public Medicine() {
        this(new MedicineIdType());
    }

    public Medicine(@NonNull MedicineIdType medicineId) {
        mMedicineId = medicineId;
        mMedicineName = new MedicineNameType();
        mTakeNumber = new TakeNumberType();
        mMedicineUnit = new MedicineUnit();
        mDateNumber = new MedicineDateNumberType();
        mStartDatetime = new StartDatetimeType();
        mTakeInterval = new TakeIntervalType();
        mTakeIntervalMode = new TakeIntervalModeType();
        mMedicinePhoto = new MedicinePhotoType();
        mNeedAlarmType = new MedicineNeedAlarmType();
        mDeleteFlag = new DeleteFlagType();
        mTimetableList = new MedicineTimetableList();
    }

    public Medicine(@NonNull MedicineIdType medicineId,
                    @NonNull MedicineNameType medicineName,
                    @NonNull TakeNumberType takeNumber,
                    @NonNull MedicineUnit medicineUnit,
                    @NonNull MedicineDateNumberType dateNumber,
                    @NonNull StartDatetimeType startDatetime,
                    @NonNull TakeIntervalType takeInterval,
                    @NonNull TakeIntervalModeType takeIntervalMode,
                    @NonNull MedicinePhotoType medicinePhoto,
                    @NonNull MedicineNeedAlarmType needAlarmType,
                    @NonNull DeleteFlagType deleteFlag,
                    @NonNull MedicineTimetableList timetableList) {
        mMedicineId = medicineId;
        mMedicineName = medicineName;
        mTakeNumber = takeNumber;
        mMedicineUnit = medicineUnit;
        mDateNumber = dateNumber;
        mStartDatetime = startDatetime;
        mTakeInterval = takeInterval;
        mTakeIntervalMode = takeIntervalMode;
        mMedicinePhoto = medicinePhoto;
        mNeedAlarmType = needAlarmType;
        mDeleteFlag = deleteFlag;
        mTimetableList = timetableList;
    }

    @NonNull
    public MedicineIdType getMedicineId() {
        return mMedicineId;
    }

    @NonNull
    public MedicineNameType getMedicineName() {
        return mMedicineName;
    }

    public void setMedicineName(@NonNull String medicineName) {
        mMedicineName = new MedicineNameType(medicineName);
    }

    @NonNull
    public TakeNumberType getTakeNumber() {
        return mTakeNumber;
    }

    public void setTakeNumber(@NonNull String takeNumber) {
        mTakeNumber = new TakeNumberType(takeNumber);
    }

    @NonNull
    public MedicineDateNumberType getDateNumber() {
        return mDateNumber;
    }

    public void setDateNumber(int dateNumber) {
        mDateNumber = new MedicineDateNumberType(dateNumber);
    }

    @NonNull
    public StartDatetimeType getStartDatetime() {
        return mStartDatetime;
    }

    public void setStartDatetime(long timeInMillis) {
        mStartDatetime = new StartDatetimeType(timeInMillis);
    }

    @NonNull
    public TakeIntervalType getTakeInterval() {
        return mTakeInterval;
    }

    public void setTakeInterval(int takeInterval, @NonNull TakeIntervalModeType.DateIntervalPattern dateIntervalPattern) {
        mTakeInterval = new TakeIntervalType(takeInterval);
        mTakeIntervalMode = new TakeIntervalModeType(dateIntervalPattern);
    }

    @NonNull
    public TakeIntervalModeType getTakeIntervalMode() {
        return mTakeIntervalMode;
    }

    @NonNull
    public MedicinePhotoType getMedicinePhoto() {
        return mMedicinePhoto;
    }

    public void setMedicinePhoto(@NonNull String photoFilePath) {
        mMedicinePhoto = new MedicinePhotoType(photoFilePath);
    }

    @NonNull
    public MedicineTimetableList getTimetableList() {
        return mTimetableList;
    }

    public void setTimetableList(@Nullable List<Timetable> timetableList) {
        mTimetableList.setTimetableList(timetableList);
    }

    @NonNull
    public String getDisplayMedicineName() {
        return mMedicineName.getDisplayValue();
    }

    @NonNull
    public String getDisplayTakeNumber() {
        return mTakeNumber.getDisplayValue();
    }

    @NonNull
    public String getDisplayMedicineUnit() {
        return mMedicineUnit.getDisplayValue();
    }

    @NonNull
    public String getDisplayDateNumber() {
        return mDateNumber.getDisplayValue();
    }

    @NonNull
    public String getDisplayStartDatetime() {
        return mStartDatetime.getDisplayValue();
    }

    @NonNull
    public String getDisplayTakeInterval(@NonNull Resources resources) {
        return mTakeInterval.getDisplayValue(resources, mTakeIntervalMode);
    }

    @NonNull
    public String getDisplayTimetableList() {
        return mTimetableList.getDisplayValue();
    }

    @NonNull
    public MedicineNeedAlarmType getNeedAlarmType() {
        return mNeedAlarmType;
    }

    @NonNull
    public DeleteFlagType getDeleteFlagType() {
        return mDeleteFlag;
    }

    @NonNull
    public MedicineUnit getMedicineUnit() {
        return mMedicineUnit;
    }

    public void setMedicineUnit(@NonNull MedicineUnit medicineUnit) {
        mMedicineUnit = medicineUnit;
    }

    @NonNull
    public MedicineUnitIdType getMedicineUnitId() {
        return mMedicineUnit.getMedicineUnitId();
    }

    public boolean isOneShowMedicine() {
        return mTimetableList.isOneShotMedicine();
    }

    public void setOneShowMedicine(boolean isOneShotMedicine) {
        mTimetableList.setOneShotMedicine(isOneShotMedicine);
    }

    public boolean isNeedAlarm() {
        return mNeedAlarmType.isTrue();
    }

    public void setNeedAlarm(boolean needAlarm) {
        mNeedAlarmType = new MedicineNeedAlarmType(needAlarm);
    }

    public void setStartDatetime(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        mStartDatetime = new StartDatetimeType(year, month, dayOfMonth, hourOfDay, minute);
    }

    public void setDeleteFlag(boolean delete) {
        mDeleteFlag = new DeleteFlagType(delete);
    }
}
