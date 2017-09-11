package com.studiojozu.medicheck.domain.model.medicine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.parson.ParsonIdType;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleList;
import com.studiojozu.medicheck.domain.model.setting.TimetableList;
import com.studiojozu.medicheck.infrastructure.persistence.MedicineRepository;

/**
 * 薬を管理するクラス
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
     * タイムテーブルの一覧
     * タイムテーブルのサイズが0の場合は、頓服扱いとなる。
     */
    @NonNull
    private TimetableList mTimetableList;

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
        mTimetableList = new TimetableList();
    }

    /**
     * DB登録済み（IDがわかっている）インスタンスを生成するためのコンストラクタ
     *
     * @param medicineId 薬のID
     * @param medicine   ID以外を複製する際に使用する
     */
    public Medicine(long medicineId, @NonNull Medicine medicine) {
        mMedicineId = new MedicineIdType(medicineId);
        mMedicineName = medicine.mMedicineName.clone();
        mTakeNumber = medicine.mTakeNumber.clone();
        mDateNumber = medicine.mDateNumber.clone();
        mStartDatetime = medicine.mStartDatetime.clone();
        mTakeInterval = medicine.mTakeInterval.clone();
        mTakeIntervalMode = medicine.mTakeIntervalMode.clone();
        mMedicinePhoto = medicine.mMedicinePhoto.clone();
        mTimetableList = medicine.mTimetableList.clone();
    }

    public void save(@NonNull Context context, @NonNull ParsonIdType parsonId) {
        // 薬を登録する
        MedicineRepository medicineRepository = new MedicineRepository();
        medicineRepository.save(context, parsonId, this, mTimetableList);
    }

    /**
     * 薬情報を元にスケジュールを生成する
     *
     * @return 生成したスケジュール一覧インスタンス
     */
    public ScheduleList createScheduleList() {
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.createScheduleList(mMedicineId, mStartDatetime, mTakeInterval, mTakeIntervalMode, mTimetableList, mDateNumber);

        return scheduleList;
    }

    @NonNull
    public MedicineIdType getMedicineId() {
        return mMedicineId.clone();
    }

    @NonNull
    public MedicineNameType getMedicineName() {
        return mMedicineName.clone();
    }

    @NonNull
    public TakeNumberType getTakeNumber() {
        return mTakeNumber.clone();
    }

    @NonNull
    public DateNumberType getDateNumber() {
        return mDateNumber.clone();
    }

    @NonNull
    public StartDatetimeType getStartDatetime() {
        return mStartDatetime.clone();
    }

    @NonNull
    public TakeIntervalType getTakeInterval() {
        return mTakeInterval.clone();
    }

    @NonNull
    public TakeIntervalModeType getTakeIntervalMode() {
        return mTakeIntervalMode.clone();
    }

    @NonNull
    public MedicinePhotoType getMedicinePhoto() {
        return mMedicinePhoto.clone();
    }

    @NonNull
    public TimetableList getTimetableList() {
        return mTimetableList.clone();
    }
}
