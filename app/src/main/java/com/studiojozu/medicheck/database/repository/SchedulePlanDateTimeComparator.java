package com.studiojozu.medicheck.database.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.DateType;
import com.studiojozu.medicheck.database.type.IntType;
import com.studiojozu.medicheck.database.type.TimeType;

import java.util.Comparator;
import java.util.Map;

/**
 * 投与予定日時でソートするComparator
 */
public class SchedulePlanDateTimeComparator implements Comparator<Map<ColumnBase, ADbType>> {

    @NonNull
    private final Context mContext;
    @NonNull
    private final TimetableRepository mTimetableRepository;

    public SchedulePlanDateTimeComparator(@NonNull Context context) {
        mContext = context;
        mTimetableRepository = new TimetableRepository();
    }

    @Override
    public int compare(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        int compareDate = compareDate(scheduleRecord1, scheduleRecord2);
        if (compareDate != 0) return compareDate;

        int compareTime = compareTime(scheduleRecord1, scheduleRecord2);
        if (compareTime != 0) return compareTime;

        return compareMedicineId(scheduleRecord1, scheduleRecord2);
    }

    /**
     * パラメータで指定されたレコードの投与予定日付を比較する
     *
     * @param scheduleRecord1 {@link ScheduleRepository}の1レコード分のデータ1
     * @param scheduleRecord2 {@link ScheduleRepository}の1レコード分のデータ2
     * @return 投与予定日付の比較結果
     */
    private int compareDate(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        DateType dateModel1 = (DateType) scheduleRecord1.get(ScheduleRepository.COLUMN_PLAN_DATE);
        DateType dateModel2 = (DateType) scheduleRecord2.get(ScheduleRepository.COLUMN_PLAN_DATE);
        return dateModel1.compareTo(dateModel2);
    }

    /**
     * パラメータで指定されたレコードのタイムテーブルが示す時刻を比較する
     *
     * @param scheduleRecord1 {@link ScheduleRepository}の1レコード分のデータ1
     * @param scheduleRecord2 {@link ScheduleRepository}の1レコード分のデータ2
     * @return タイムテーブルが示す時刻の比較結果
     */
    private int compareTime(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        TimeType timeModel1 = getTimeModel(scheduleRecord1);
        TimeType timeModel2 = getTimeModel(scheduleRecord2);

        if(timeModel1 == null){
            if(timeModel2 == null) return 0;
            return -1;
        }
        if(timeModel2 == null) return 1;

        return timeModel1.compareTo(timeModel2);
    }

    /**
     * パラメータで指定されたレコードの薬IDを比較する
     *
     * @param scheduleRecord1 {@link ScheduleRepository}の1レコード分のデータ1
     * @param scheduleRecord2 {@link ScheduleRepository}の1レコード分のデータ2
     * @return 薬IDの比較結果
     */
    private int compareMedicineId(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        IntType medicineId1 = (IntType) scheduleRecord1.get(ScheduleRepository.COLUMN_MEDICINE_ID);
        IntType medicineId2 = (IntType) scheduleRecord2.get(ScheduleRepository.COLUMN_MEDICINE_ID);
        return medicineId1.compareTo(medicineId2);
    }

    /**
     * {@link ScheduleRepository}が持つタイムテーブルIDがさすタイムテーブルの時刻を取得する
     *
     * @param scheduleRecord {@link ScheduleRepository}の1レコード分のデータ
     * @return タイムテーブルの時刻
     */
    @Nullable
    private TimeType getTimeModel(@NonNull Map<ColumnBase, ADbType> scheduleRecord) {
        int timetableId = (Integer) scheduleRecord.get(ScheduleRepository.COLUMN_TIMETABLE_ID).getDbValue();
        Map<ColumnBase, ADbType> timeRepository1 = mTimetableRepository.findTimetable(mContext, timetableId);
        if(timeRepository1 == null) return null;
        return (TimeType) timeRepository1.get(TimetableRepository.COLUMN_TIME);
    }
}
