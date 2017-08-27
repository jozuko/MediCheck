package com.studiojozu.medicheck.entity;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.schedule.IsTakeType;
import com.studiojozu.medicheck.type.schedule.NeedAlarmType;
import com.studiojozu.medicheck.type.schedule.PlanDateType;
import com.studiojozu.medicheck.type.schedule.TookDatetime;
import com.studiojozu.medicheck.type.timetable.TimetableIdType;

/**
 * 投薬予定を予定テーブルに登録する
 */
public class Schedule implements Cloneable {

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
    private NeedAlarmType mNeedAlarm;
    /** 服用済みか否か */
    @NonNull
    private IsTakeType mIsTake;
    /** 服用した日時 */
    @NonNull
    private TookDatetime mTookDatetime;

    public Schedule(@NonNull MedicineIdType medicineId, @NonNull PlanDateType planDate, @NonNull TimetableIdType timetableId) {
        mMedicineId = medicineId.clone();
        mPlanDate = planDate.clone();
        mTimetableId = timetableId.clone();
        mNeedAlarm = new NeedAlarmType(true);
        mIsTake = new IsTakeType(false);
        mTookDatetime = new TookDatetime();
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
    public NeedAlarmType getNeedAlarm() {
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
