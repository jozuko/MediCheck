package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.schedule.IsTakeType;
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType;
import com.studiojozu.medicheck.domain.model.schedule.Schedule;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType;
import com.studiojozu.medicheck.domain.model.schedule.TookDatetime;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;

import java.util.Map;

/**
 * DBのレコードから{@link Schedule}を生成する
 */
class SqliteScheduleConverter extends ASqliteConverter<Schedule> {

    SqliteScheduleConverter(@Nullable Map<ColumnBase, ADbType> databaseRecord) {
        super(databaseRecord);
    }

    @Override
    @Nullable
    Schedule createFromRecord() {
        if (mDatabaseRecord == null) return null;

        return new Schedule(
                getMedicineIdType(),
                getPlanDateType(),
                getTimetableIdType(),
                getNeedAlarmType(),
                getIsTakeType(),
                getTookDatetime()
        );
    }

    private MedicineIdType getMedicineIdType() {
        MedicineIdType medicineIdType = (MedicineIdType) getData(SqliteScheduleRepository.COLUMN_MEDICINE_ID);
        if (medicineIdType != null) return medicineIdType;
        return new MedicineIdType();
    }

    private PlanDateType getPlanDateType() {
        PlanDateType planDateType = (PlanDateType) getData(SqliteScheduleRepository.COLUMN_PLAN_DATE);
        if (planDateType != null) return planDateType;
        return new PlanDateType();
    }

    private TimetableIdType getTimetableIdType() {
        TimetableIdType timetableIdType = (TimetableIdType) getData(SqliteScheduleRepository.COLUMN_TIMETABLE_ID);
        if (timetableIdType != null) return timetableIdType;
        return new TimetableIdType();
    }

    private ScheduleNeedAlarmType getNeedAlarmType() {
        ScheduleNeedAlarmType scheduleNeedAlarmType = (ScheduleNeedAlarmType) getData(SqliteScheduleRepository.COLUMN_NEED_ALERT);
        if (scheduleNeedAlarmType != null) return scheduleNeedAlarmType;
        return new ScheduleNeedAlarmType();
    }

    private IsTakeType getIsTakeType() {
        IsTakeType isTakeType = (IsTakeType) getData(SqliteScheduleRepository.COLUMN_IS_TAKE);
        if (isTakeType != null) return isTakeType;
        return new IsTakeType();
    }

    private TookDatetime getTookDatetime() {
        TookDatetime tookDatetime = (TookDatetime) getData(SqliteScheduleRepository.COLUMN_TOOK_DATETIME);
        if (tookDatetime != null) return tookDatetime;
        return new TookDatetime();
    }
}
