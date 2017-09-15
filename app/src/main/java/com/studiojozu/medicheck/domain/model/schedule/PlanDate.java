package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;

/**
 * 服用予定日時と日付、タイムテーブルIDを持つクラス
 */
public class PlanDate implements Cloneable {

    /** 服用予定日時 */
    @NonNull
    private DatetimeType mPlanDatetime;

    /** 服用予定日付 */
    @NonNull
    private PlanDateType mPlanDate;

    /** タイムテーブルID */
    @NonNull
    private TimetableIdType mTimetableId;

    public PlanDate(@NonNull DatetimeType planDatetime) {
        this(planDatetime, new PlanDateType(), new TimetableIdType());
    }

    public PlanDate(@NonNull DatetimeType planDatetime, @NonNull PlanDateType planDate, @NonNull TimetableIdType timetableId) {
        mPlanDatetime = planDatetime.clone();
        mPlanDate = planDate.clone();
        mTimetableId = timetableId.clone();
    }

    @NonNull
    public DatetimeType getPlanDatetime() {
        return mPlanDatetime;
    }

    @NonNull
    public PlanDateType getPlanDate() {
        return mPlanDate;
    }

    @NonNull
    public TimetableIdType getTimetableId() {
        return mTimetableId;
    }

    @Override
    public PlanDate clone() {
        try {
            return (PlanDate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    /**
     * フィールドが保持する日時に、パラメータのdayを日数として追加した値を返却する。
     *
     * @param days 加算する日数
     * @return パラメータ値加算後の値を保持するインスタンス
     */
    public PlanDate addDay(int days) {
        mPlanDatetime = mPlanDatetime.addDay(days);
        mPlanDate = mPlanDate.addDay(days);

        return clone();
    }
}
