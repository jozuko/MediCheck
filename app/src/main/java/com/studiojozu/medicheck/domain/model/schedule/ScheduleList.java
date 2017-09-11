package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.medicheck.domain.model.medicine.DateNumberType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.StartDatetimeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalType;
import com.studiojozu.medicheck.domain.model.setting.TimetableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * スケジュールの一覧を管理するクラス
 */
public class ScheduleList implements Cloneable, Iterator<Schedule>, Iterable<Schedule> {

    private List<Schedule> mScheduleList;
    private Iterator<Schedule> mScheduleIterator;

    @Override
    public ScheduleList clone() {
        try {
            return (ScheduleList) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    @Override
    public Iterator<Schedule> iterator() {
        ScheduleList scheduleList = clone();
        scheduleList.mScheduleIterator = scheduleList.createScheduleIterator();
        return scheduleList;
    }

    @Override
    public boolean hasNext() {
        return mScheduleIterator.hasNext();
    }

    @Override
    public Schedule next() {
        return mScheduleIterator.next();
    }

    private Iterator<Schedule> createScheduleIterator() {
        if (mScheduleList == null) return new ArrayList<Schedule>().iterator();
        return mScheduleList.iterator();
    }

    /**
     * 薬の情報からスケジュールを作成する。
     *
     * @param medicineId       薬ID
     * @param startDatetime    開始日時
     * @param takeInterval     服用間隔
     * @param takeIntervalMode 服用間隔タイプ(日or月)
     * @param timetableList    タイムテーブル一覧
     * @param dateNumber       服用日数
     */
    public void createScheduleList(@NonNull MedicineIdType medicineId, @NonNull StartDatetimeType startDatetime, @NonNull TakeIntervalType takeInterval, @NonNull TakeIntervalModeType takeIntervalMode, @NonNull TimetableList timetableList, @NonNull DateNumberType dateNumber) {
        // 服用回数を計算する（タイムテーブルの回数×服用日数）
        int medicineNumber = calculateMedicineNumber(timetableList, dateNumber);

        // タイムテーブルを作成する
        createScheduleList(medicineId, startDatetime, takeInterval, takeIntervalMode, timetableList, medicineNumber);
    }

    /**
     * 服用回数を計算する（タイムテーブルの回数×服用日数）
     *
     * @param timetableList タイムテーブル一覧
     * @param dateNumber    服用日数
     * @return （タイムテーブルの回数×服用日数）頓服の場合は0を返却する
     */
    private int calculateMedicineNumber(@NonNull TimetableList timetableList, @NonNull DateNumberType dateNumber) {
        if (timetableList.isOneShot()) return 0;
        return timetableList.getCount() * dateNumber.getDbValue().intValue();
    }

    /**
     * スケジュールを作成する。
     *
     * @param medicineId       薬ID
     * @param startDatetime    開始日時
     * @param takeInterval     服用間隔
     * @param takeIntervalMode 服用間隔タイプ(日or月)
     * @param timetableList    タイムテーブル一覧
     * @param medicineNumber   服用回数
     */
    private void createScheduleList(@NonNull MedicineIdType medicineId, @NonNull StartDatetimeType startDatetime, @NonNull TakeIntervalType takeInterval, @NonNull TakeIntervalModeType takeIntervalMode, @NonNull TimetableList timetableList, int medicineNumber) {
        clearScheduleList();

        // 服用予定日時を初期化する
        PlanDate planDate = new PlanDate(startDatetime);

        for (int medicine_i = 0; medicine_i < medicineNumber; medicine_i++) {
            // 服用予定算出基準日時を取得する
            DatetimeType standardDatetime = getNextStartDatetime(timetableList, planDate, takeInterval, takeIntervalMode);

            // 服用予定日時を取得する
            planDate = timetableList.getPlanDate(standardDatetime);

            // 服用予定日時を一覧に追加する
            Schedule schedule = new Schedule(medicineId, planDate.getPlanDate(), planDate.getTimetableId());
            mScheduleList.add(schedule);
        }
    }

    private void clearScheduleList() {
        if (mScheduleList == null) mScheduleList = new ArrayList<>();
        mScheduleList.clear();
    }

    private DatetimeType getNextStartDatetime(@NonNull TimetableList timetableList, @NonNull PlanDate planDate, @NonNull TakeIntervalType takeInterval, @NonNull TakeIntervalModeType takeIntervalMode) {

        // 予定時刻が初期値の場合は予定日時を返却する
        if (planDate.isUndefinedTimetableId())
            return planDate.getPlanDatetime();

        // 予定日時で使用しているTimetableIdがTimetableListの最終時刻ではなかった場合は予定日時を返却する
        if (!timetableList.isFinalTime(planDate.getTimetableId()))
            return planDate.getPlanDatetime();

        // 予定日時にInterval日数(or月数)を加算した日(時分秒は0初期化)を返却する
        return takeInterval.getNextDatetime(planDate.getPlanDatetime(), takeIntervalMode);
    }
}
