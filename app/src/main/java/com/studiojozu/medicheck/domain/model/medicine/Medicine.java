package com.studiojozu.medicheck.domain.model.medicine;

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
        mDateNumber = new DateNumberType();
        mStartDatetime = new StartDatetimeType();
        mTakeInterval = new TakeIntervalType();
        mTakeIntervalMode = new TakeIntervalModeType();
        mMedicinePhoto = new MedicinePhotoType();
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
        mDateNumber = medicine.mDateNumber;
        mStartDatetime = medicine.mStartDatetime;
        mTakeInterval = medicine.mTakeInterval;
        mTakeIntervalMode = medicine.mTakeIntervalMode;
        mMedicinePhoto = medicine.mMedicinePhoto;
        mTimetableList = medicine.mTimetableList;
    }

    /**
     * DB登録前のインスタンスを生成するためのコンストラクタ.
     *
     * @param medicineName     薬名
     * @param takeNumber       服用数
     * @param dateNumber       服用日数
     * @param startDatetime    服用開始日
     * @param takeInterval     服用間隔(数値)
     * @param takeIntervalMode 服用間隔(日or月)
     * @param medicinePhoto    写真パス
     * @param timetableList    タイムテーブル一覧
     */
    public Medicine(@NonNull MedicineNameType medicineName,
                    @NonNull TakeNumberType takeNumber,
                    @NonNull DateNumberType dateNumber,
                    @NonNull StartDatetimeType startDatetime,
                    @NonNull TakeIntervalType takeInterval,
                    @NonNull TakeIntervalModeType takeIntervalMode,
                    @NonNull MedicinePhotoType medicinePhoto,
                    @NonNull MedicineTimetableList timetableList) {
        this(new MedicineIdType(),
                medicineName,
                takeNumber,
                dateNumber,
                startDatetime,
                takeInterval,
                takeIntervalMode,
                medicinePhoto,
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
     * @param timetableList    タイムテーブル一覧
     */
    public Medicine(@NonNull MedicineIdType medicineId,
                    @NonNull MedicineNameType medicineName,
                    @NonNull TakeNumberType takeNumber,
                    @NonNull DateNumberType dateNumber,
                    @NonNull StartDatetimeType startDatetime,
                    @NonNull TakeIntervalType takeInterval,
                    @NonNull TakeIntervalModeType takeIntervalMode,
                    @NonNull MedicinePhotoType medicinePhoto,
                    @NonNull MedicineTimetableList timetableList) {
        mMedicineId = medicineId;
        mMedicineName = medicineName;
        mTakeNumber = takeNumber;
        mDateNumber = dateNumber;
        mStartDatetime = startDatetime;
        mTakeInterval = takeInterval;
        mTakeIntervalMode = takeIntervalMode;
        mMedicinePhoto = medicinePhoto;
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
    public DateNumberType getDateNumber() {
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
}
