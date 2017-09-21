package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;

import java.io.Serializable;

/**
 * Schedule Entity
 */
public class Schedule implements Cloneable, Serializable {

    private static final long serialVersionUID = 8903872909322659419L;
    /** 薬ID */
    @NonNull
    private final MedicineIdType mMedicineId;
    /** 予定日付 */
    @NonNull
    private final PlanDateType mPlanDate;
    /** タイムテーブルID */
    @NonNull
    private final TimetableIdType mTimetableId;
    /** アラーム要否 */
    @NonNull
    private ScheduleNeedAlarmType mNeedAlarm;
    /** 服用済みか否か */
    @NonNull
    private IsTakeType mIsTake;
    /** 服用した日時 */
    @NonNull
    private TookDatetime mTookDatetime;

    public Schedule(@NonNull MedicineIdType medicineId, @NonNull PlanDateType planDate, @NonNull TimetableIdType timetableId) {
        mMedicineId = medicineId;
        mPlanDate = planDate;
        mTimetableId = timetableId;
        mNeedAlarm = new ScheduleNeedAlarmType();
        mIsTake = new IsTakeType();
        mTookDatetime = new TookDatetime();
    }

    public Schedule(@NonNull MedicineIdType medicineId,
                    @NonNull PlanDateType planDate,
                    @NonNull TimetableIdType timetableId,
                    @NonNull ScheduleNeedAlarmType needAlarm,
                    @NonNull IsTakeType isTake,
                    @NonNull TookDatetime tookDatetime) {
        mMedicineId = medicineId;
        mPlanDate = planDate;
        mTimetableId = timetableId;
        mNeedAlarm = needAlarm;
        mIsTake = isTake;
        mTookDatetime = tookDatetime;
    }

    @Override
    public Schedule clone() {
        try {
            return (Schedule) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    @NonNull
    public MedicineIdType getMedicineId() {
        return mMedicineId;
    }

    @NonNull
    public PlanDateType getPlanDate() {
        return mPlanDate;
    }

    @NonNull
    public TimetableIdType getTimetableId() {
        return mTimetableId;
    }

    @NonNull
    public ScheduleNeedAlarmType getNeedAlarm() {
        return mNeedAlarm;
    }

    @NonNull
    public IsTakeType getIsTake() {
        return mIsTake;
    }

    @NonNull
    public TookDatetime getTookDatetime() {
        return mTookDatetime;
    }
}
