package com.studiojozu.medicheck.domain.model.medicine;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 薬を管理するクラス
 */
public class Medicine implements Serializable {
    private static final long serialVersionUID = -3626443464441488492L;

    /** 薬ID */
    @NonNull
    private final MedicineIdType mMedicineId;
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
    private DeleteFlagType mDeleteFlagType;
    /**
     * タイムテーブルの一覧
     * タイムテーブルのサイズが0の場合は、頓服扱いとなる。
     */
    @NonNull
    private MedicineTimetableList mTimetableList;

    /**
     * DB登録前のインスタンスを生成するためのコンストラクタ.
     * フィールドにはデフォルト値を登録する
     */
    public Medicine() {
        mMedicineId = new MedicineIdType();
        mMedicineName = new MedicineNameType();
        mTakeNumber = new TakeNumberType();
        mMedicineUnit = new MedicineUnit();
        mDateNumber = new MedicineDateNumberType();
        mStartDatetime = new StartDatetimeType();
        mTakeInterval = new TakeIntervalType();
        mTakeIntervalMode = new TakeIntervalModeType();
        mMedicinePhoto = new MedicinePhotoType();
        mNeedAlarmType = new MedicineNeedAlarmType();
        mDeleteFlagType = new DeleteFlagType();
        mTimetableList = new MedicineTimetableList();
    }

    /**
     * DB登録済み（IDがわかっている）インスタンスを生成するためのコンストラクタ
     *
     * @param medicineId 薬のID
     * @param medicine   ID以外を複製する際に使用する
     */
    public Medicine(long medicineId, @NonNull Medicine medicine) {
        mMedicineId = new MedicineIdType(medicineId);
        mMedicineName = medicine.mMedicineName;
        mTakeNumber = medicine.mTakeNumber;
        mMedicineUnit = medicine.mMedicineUnit;
        mDateNumber = medicine.mDateNumber;
        mStartDatetime = medicine.mStartDatetime;
        mTakeInterval = medicine.mTakeInterval;
        mTakeIntervalMode = medicine.mTakeIntervalMode;
        mMedicinePhoto = medicine.mMedicinePhoto;
        mNeedAlarmType = medicine.mNeedAlarmType;
        mDeleteFlagType = medicine.mDeleteFlagType;
        mTimetableList = medicine.mTimetableList;
    }

    /**
     * DB登録前のインスタンスを生成するためのコンストラクタ.
     *
     * @param medicineName     薬名
     * @param takeNumber       服用数
     * @param medicineUnit     服用数 単位
     * @param dateNumber       服用日数
     * @param startDatetime    服用開始日
     * @param takeInterval     服用間隔(数値)
     * @param takeIntervalMode 服用間隔(日or月)
     * @param medicinePhoto    写真パス
     * @param needAlarmType    アラーム要否
     * @param deleteFlagType   削除フラグ
     * @param timetableList    タイムテーブル一覧
     */
    public Medicine(@NonNull MedicineNameType medicineName,
                    @NonNull TakeNumberType takeNumber,
                    @NonNull MedicineUnit medicineUnit,
                    @NonNull MedicineDateNumberType dateNumber,
                    @NonNull StartDatetimeType startDatetime,
                    @NonNull TakeIntervalType takeInterval,
                    @NonNull TakeIntervalModeType takeIntervalMode,
                    @NonNull MedicinePhotoType medicinePhoto,
                    @NonNull MedicineNeedAlarmType needAlarmType,
                    @NonNull DeleteFlagType deleteFlagType,
                    @NonNull MedicineTimetableList timetableList) {
        this(new MedicineIdType(),
                medicineName,
                takeNumber,
                medicineUnit,
                dateNumber,
                startDatetime,
                takeInterval,
                takeIntervalMode,
                medicinePhoto,
                needAlarmType,
                deleteFlagType,
                timetableList);
    }

    /**
     * DB登録済み（IDがわかっている）インスタンスを生成するためのコンストラクタ
     *
     * @param medicineId       薬ID
     * @param medicineName     薬名
     * @param takeNumber       服用数
     * @param dateNumber       服用日数
     * @param startDatetime    服用開始日
     * @param takeInterval     服用間隔(数値)
     * @param takeIntervalMode 服用間隔(日or月)
     * @param medicinePhoto    写真パス
     * @param needAlarmType    アラーム要否
     * @param deleteFlagType   削除フラグ
     * @param timetableList    タイムテーブル一覧
     */
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
                    @NonNull DeleteFlagType deleteFlagType,
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
        mDeleteFlagType = deleteFlagType;
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

    @NonNull
    public TakeNumberType getTakeNumber() {
        return mTakeNumber;
    }

    @NonNull
    public MedicineDateNumberType getDateNumber() {
        return mDateNumber;
    }

    @NonNull
    public StartDatetimeType getStartDatetime() {
        return mStartDatetime;
    }

    @NonNull
    public TakeIntervalType getTakeInterval() {
        return mTakeInterval;
    }

    @NonNull
    public TakeIntervalModeType getTakeIntervalMode() {
        return mTakeIntervalMode;
    }

    @NonNull
    public MedicinePhotoType getMedicinePhoto() {
        return mMedicinePhoto;
    }

    @NonNull
    public MedicineTimetableList getTimetableList() {
        return mTimetableList;
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
        return mDeleteFlagType;
    }

    @NonNull
    public MedicineUnit getMedicineUnit() {
        return mMedicineUnit;
    }

    @NonNull
    public MedicineUnitIdType getMedicineUnitId() {
        return mMedicineUnit.getMedicineUnitId();
    }
}
