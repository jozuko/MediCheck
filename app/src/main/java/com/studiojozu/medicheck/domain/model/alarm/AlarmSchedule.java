package com.studiojozu.medicheck.domain.model.alarm;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType;
import com.studiojozu.medicheck.domain.model.schedule.Schedule;
import com.studiojozu.medicheck.domain.model.setting.Setting;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType;

import java.util.Calendar;

public class AlarmSchedule {
    @NonNull
    private final Schedule mSchedule;
    @NonNull
    private final Timetable mTimetable;
    @NonNull
    private final Person mPerson;
    @NonNull
    private final Medicine mMedicine;

    public AlarmSchedule(@NonNull Schedule schedule, @NonNull Timetable timetable, @NonNull Medicine medicine, @NonNull Person person) {
        mSchedule = schedule;
        mTimetable = timetable;
        mMedicine = medicine;
        mPerson = person;
    }

    @NonNull
    public Timetable getTimetable() {
        return mTimetable;
    }

    public String getMedicineName() {
        return mMedicine.getMedicineName().getDbValue();
    }

    public String getPersonName() {
        return mPerson.getPersonName().getDbValue();
    }

    public boolean isNeedAlarm(@NonNull Calendar now, @NonNull Setting setting) {
        if (isSameDateTime(now)) return true;

        // リマインダが不要ならAlertも不要
        if (!setting.useReminder()) return false;

        // リマインダタイムアウトならAlertは不要
        PlanDateType alarmDate = getPlanDateType();
        TimetableTimeType alarmTime = getTimetableTimeType();
        if (setting.isRemindTimeout(now, alarmDate, alarmTime)) return false;

        // リマインドタイミングならAlertする
        return (setting.isRemindTiming(now, alarmDate, alarmTime));
    }

    private boolean isSameDateTime(@NonNull Calendar compareTarget) {
        DateType alarmDate = mSchedule.getPlanDate();
        TimetableTimeType alarmTime = mTimetable.getTimetableTime();
        return (alarmDate.equalsDate(compareTarget) && (alarmTime.equalsTime(compareTarget)));
    }

    @NonNull
    PlanDateType getPlanDateType() {
        return mSchedule.getPlanDate();
    }

    @NonNull
    TimetableTimeType getTimetableTimeType() {
        return mTimetable.getTimetableTime();
    }
}
