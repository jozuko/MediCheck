package com.studiojozu.medicheck.entity;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.medicine.DateNumberType;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.medicine.MedicineNameType;
import com.studiojozu.medicheck.type.medicine.MedicinePhotoType;
import com.studiojozu.medicheck.type.medicine.StartDatetimeType;
import com.studiojozu.medicheck.type.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.type.medicine.TakeIntervalType;
import com.studiojozu.medicheck.type.medicine.TakeNumberType;

import java.util.Calendar;

/**
 *
 */
public class Medicine {
    /** 薬ID */
    @NonNull
    private final MedicineIdType mMedicineId;
    /** 薬の名前 */
    @NonNull
    private MedicineNameType mMedicineName;
    /** 服用数 */
    @NonNull
    private TakeNumberType mTakeNumber;
    /** 服用日数 */
    @NonNull
    private DateNumberType mDateNumber;
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

    /**
     * DB登録前のインスタンスを生成するためのコンストラクタ
     *
     * @param medicineName      薬の名前
     * @param takeNumber        服用数
     * @param dateNumber        不空用日数
     * @param startDatetime     服用開始日時
     * @param takeInterval      服用間隔
     * @param takeIntervalType  服用間隔タイプ(日or月)
     * @param medicinePhotoPath 写真パス
     */
    public Medicine(String medicineName, int takeNumber, int dateNumber, Calendar startDatetime, int takeInterval, int takeIntervalType, String medicinePhotoPath) {
        mMedicineId = new MedicineIdType();
        mMedicineName = new MedicineNameType(medicineName);
        mTakeNumber = new TakeNumberType(takeNumber);
        mDateNumber = new DateNumberType(dateNumber);
        mStartDatetime = new StartDatetimeType(startDatetime);
        mTakeInterval = new TakeIntervalType(takeInterval);
        mTakeIntervalMode = new TakeIntervalModeType(takeIntervalType);
        mMedicinePhoto = new MedicinePhotoType(medicinePhotoPath);
    }

    /**
     * DB登録済み（IDがわかっている）インスタンスを生成するためのコンストラクタ
     *
     * @param medicineId        薬のID
     * @param medicineName      薬の名前
     * @param takeNumber        服用数
     * @param dateNumber        不空用日数
     * @param startDatetime     服用開始日時
     * @param takeInterval      服用間隔
     * @param takeIntervalType  服用間隔タイプ(日or月)
     * @param medicinePhotoPath 写真パス
     */
    public Medicine(MedicineIdType medicineId, MedicineNameType medicineName, TakeNumberType takeNumber, DateNumberType dateNumber, StartDatetimeType startDatetime, TakeIntervalType takeInterval, TakeIntervalModeType takeIntervalType, MedicinePhotoType medicinePhotoPath) {
        mMedicineId = new MedicineIdType(medicineId.getDbValue());
        mMedicineName = new MedicineNameType(medicineName);
        mTakeNumber = new TakeNumberType(takeNumber);
        mDateNumber = new DateNumberType(dateNumber);
        mStartDatetime = new StartDatetimeType(startDatetime);
        mTakeInterval = new TakeIntervalType(takeInterval);
        mTakeIntervalMode = new TakeIntervalModeType(takeIntervalType);
        mMedicinePhoto = new MedicinePhotoType(medicinePhotoPath);
    }

}
