package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.database.type.DateModel;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.TimeModel;

import java.util.Comparator;
import java.util.Map;

/**
 * 投与予定日時でソートするComparator
 */
public class SchedulePlanDateTimeComparator implements Comparator<Map<ColumnBase, ADbType>> {

    @NonNull
    private final Context _context;
    @NonNull
    private final TimetableEntity _timetableEntity;

    public SchedulePlanDateTimeComparator(Context context){
        _context = context;
        _timetableEntity = new TimetableEntity();
    }

    @Override
    public int compare(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        int compareDate = compareDate(scheduleRecord1, scheduleRecord2);
        if(compareDate != 0) return compareDate;

        int compareTime = compareTime(scheduleRecord1, scheduleRecord2);
        if(compareTime != 0) return compareTime;

        return compareMedicineId(scheduleRecord1, scheduleRecord2);
    }

    private int compareDate(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        DateModel dateModel1 = (DateModel)scheduleRecord1.get(ScheduleEntity.COLUMN_PLAN_DATE);
        DateModel dateModel2 = (DateModel)scheduleRecord2.get(ScheduleEntity.COLUMN_PLAN_DATE);
        return dateModel1.compareTo(dateModel2);
    }

    private int compareTime(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2){
        TimeModel timeModel1 = getTimeModel(scheduleRecord1);
        TimeModel timeModel2 = getTimeModel(scheduleRecord2);
        return timeModel1.compareTo(timeModel2);
    }

    private int compareMedicineId(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2){
        Integer medicineId1 = (Integer) scheduleRecord1.get(ScheduleEntity.COLUMN_MEDICINE_ID).getDbValue();
        Integer medicineId2 = (Integer) scheduleRecord2.get(ScheduleEntity.COLUMN_MEDICINE_ID).getDbValue();
        return medicineId1.compareTo(medicineId2);
    }

    private TimeModel getTimeModel(Map<ColumnBase, ADbType> scheduleRecord){
        int timetableId1 = (Integer) scheduleRecord.get(ScheduleEntity.COLUMN_TIMETABLE_ID).getDbValue();
        Map<ColumnBase, ADbType> timeTable1 = _timetableEntity.findTimetable(_context, timetableId1);
        return (TimeModel)timeTable1.get(TimetableEntity.COLUMN_TIME);
    }
}
