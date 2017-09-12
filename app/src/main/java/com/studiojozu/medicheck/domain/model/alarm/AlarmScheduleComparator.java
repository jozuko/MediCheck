package com.studiojozu.medicheck.domain.model.alarm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.setting.TimetableComparator;

import java.util.Comparator;

/**
 * 投与予定日時でソートするComparator
 */
public class AlarmScheduleComparator implements Comparator<AlarmSchedule> {

    @SuppressWarnings("ConstantConditions")
    @Override
    public int compare(@Nullable AlarmSchedule alarmSchedule1, @Nullable AlarmSchedule alarmSchedule2) {
        if (alarmSchedule1 == null && alarmSchedule2 == null) return 0;
        if (alarmSchedule1 == null && alarmSchedule2 != null) return -1;
        if (alarmSchedule1 != null && alarmSchedule2 == null) return 1;

        int compareDate = compareDate(alarmSchedule1, alarmSchedule2);
        if (compareDate != 0) return compareDate;

        return compareTime(alarmSchedule1, alarmSchedule2);
    }

    private int compareDate(@NonNull AlarmSchedule alarmSchedule1, @NonNull AlarmSchedule alarmSchedule2) {
        return alarmSchedule1.getPlanDateType().compareTo(alarmSchedule2.getPlanDateType());
    }

    private int compareTime(@NonNull AlarmSchedule alarmSchedule1, @NonNull AlarmSchedule alarmSchedule2) {
        return new TimetableComparator(TimetableComparator.ComparePattern.Time).compare(alarmSchedule1.getTimetable(), alarmSchedule2.getTimetable());
    }
}
