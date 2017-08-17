package com.studiojozu.medicheck.database.table;

import android.content.Context;
import android.support.annotation.NonNull;

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
    private final TimetableTable mTimetableTable;

    public SchedulePlanDateTimeComparator(@NonNull Context context) {
        mContext = context;
        mTimetableTable = new TimetableTable();
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
     * @param scheduleRecord1 {@link ScheduleTable}の1レコード分のデータ1
     * @param scheduleRecord2 {@link ScheduleTable}の1レコード分のデータ2
     * @return 投与予定日付の比較結果
     */
    private int compareDate(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        DateType dateModel1 = (DateType) scheduleRecord1.get(ScheduleTable.COLUMN_PLAN_DATE);
        DateType dateModel2 = (DateType) scheduleRecord2.get(ScheduleTable.COLUMN_PLAN_DATE);
        return dateModel1.compareTo(dateModel2);
    }

    /**
     * パラメータで指定されたレコードのタイムテーブルが示す時刻を比較する
     *
     * @param scheduleRecord1 {@link ScheduleTable}の1レコード分のデータ1
     * @param scheduleRecord2 {@link ScheduleTable}の1レコード分のデータ2
     * @return タイムテーブルが示す時刻の比較結果
     */
    private int compareTime(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        TimeType timeModel1 = getTimeModel(scheduleRecord1);
        TimeType timeModel2 = getTimeModel(scheduleRecord2);
        return timeModel1.compareTo(timeModel2);
    }

    /**
     * パラメータで指定されたレコードの薬IDを比較する
     *
     * @param scheduleRecord1 {@link ScheduleTable}の1レコード分のデータ1
     * @param scheduleRecord2 {@link ScheduleTable}の1レコード分のデータ2
     * @return 薬IDの比較結果
     */
    private int compareMedicineId(Map<ColumnBase, ADbType> scheduleRecord1, Map<ColumnBase, ADbType> scheduleRecord2) {
        IntType medicineId1 = (IntType) scheduleRecord1.get(ScheduleTable.COLUMN_MEDICINE_ID);
        IntType medicineId2 = (IntType) scheduleRecord2.get(ScheduleTable.COLUMN_MEDICINE_ID);
        return medicineId1.compareTo(medicineId2);
    }

    /**
     * {@link ScheduleTable}が持つタイムテーブルIDがさすタイムテーブルの時刻を取得する
     *
     * @param scheduleRecord {@link ScheduleTable}の1レコード分のデータ
     * @return タイムテーブルの時刻
     */
    private TimeType getTimeModel(Map<ColumnBase, ADbType> scheduleRecord) {
        int timetableId = (Integer) scheduleRecord.get(ScheduleTable.COLUMN_TIMETABLE_ID).getDbValue();
        Map<ColumnBase, ADbType> timeTable1 = mTimetableTable.findTimetable(mContext, timetableId);
        return (TimeType) timeTable1.get(TimetableTable.COLUMN_TIME);
    }
}
