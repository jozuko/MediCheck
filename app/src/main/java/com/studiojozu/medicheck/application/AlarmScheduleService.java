package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.PersonMediViewRepository;
import com.studiojozu.medicheck.domain.model.alarm.AlarmSchedule;
import com.studiojozu.medicheck.domain.model.alarm.AlarmScheduleComparator;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineRepository;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.schedule.Schedule;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleRepository;
import com.studiojozu.medicheck.domain.model.setting.Setting;
import com.studiojozu.medicheck.domain.model.setting.SettingRepository;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableRepository;
import com.studiojozu.medicheck.infrastructure.adapter.PersistenceAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Alarmを設定するための情報を提供するサービス
 */
public class AlarmScheduleService {
    @NonNull
    private final TimetableRepository mTimetableRepository = PersistenceAdapter.getTimetableRepository();
    @NonNull
    private final SettingRepository mSettingRepository = PersistenceAdapter.getSettingRepository();
    @NonNull
    private final ScheduleRepository mScheduleRepository = PersistenceAdapter.getScheduleRepository();
    @NonNull
    private final PersonMediViewRepository mPersonMediViewRepository = PersistenceAdapter.getPersonMediViewRepository();
    @NonNull
    private final MedicineRepository mMedicineRepository = PersistenceAdapter.getMedicineRepository();

    @NonNull
    public List<AlarmSchedule> getNeedAlarmSchedules(@NonNull Context context) {
        List<Schedule> needAlarmSchedules = new ArrayList<>(mScheduleRepository.getNeedAlerts(context));
        Setting setting = mSettingRepository.getSetting(context);
        Calendar now = now();

        Set<AlarmSchedule> alarmTargetSchedules = new TreeSet<>(new AlarmScheduleComparator());
        for (Schedule schedule : needAlarmSchedules) {
            AlarmSchedule alarmSchedule = createAlarmSchedule(context, schedule);
            if (alarmSchedule == null) continue;
            if (!alarmSchedule.isNeedAlarm(now, setting)) continue;

            alarmTargetSchedules.add(alarmSchedule);
        }

        return new ArrayList<>(alarmTargetSchedules);
    }

    private AlarmSchedule createAlarmSchedule(@NonNull Context context, @NonNull Schedule schedule) {
        Timetable timetable = mTimetableRepository.findTimetableById(context, schedule.getTimetableId());
        if (timetable == null) return null;

        Medicine medicine = mMedicineRepository.findMedicineById(context, schedule.getMedicineId());
        if (medicine == null) return null;

        Person person = mPersonMediViewRepository.findPersonByMedicineId(context, schedule.getMedicineId());
        if (person == null) return null;

        return new AlarmSchedule(schedule, timetable, medicine, person);
    }

    private Calendar now() {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        return now;
    }
}
