package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineDateNumberType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * スケジュールの一覧を管理するクラス
 */
public class ScheduleList implements Cloneable, Iterator<Schedule>, Iterable<Schedule>, Serializable {

    private static final long serialVersionUID = -4901152259481847837L;
    private ArrayList<Schedule> mScheduleList;
    private transient Iterator<Schedule> mScheduleIterator;

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

    public void createScheduleList(@NonNull Medicine medicine) {
        // 服用回数を計算する（タイムテーブルの回数×服用日数）
        int medicineNumber = calculateMedicineNumber(medicine.getTimetableList(), medicine.getDateNumber());

        // タイムテーブルを作成する
        createScheduleList(medicine, medicineNumber);
    }

    /**
     * 服用回数を計算する（タイムテーブルの回数×服用日数）
     *
     * @param timetableList タイムテーブル一覧
     * @param dateNumber    服用日数
     * @return （タイムテーブルの回数×服用日数）頓服の場合は0を返却する
     */
    private int calculateMedicineNumber(@NonNull MedicineTimetableList timetableList, @NonNull MedicineDateNumberType dateNumber) {
        if (timetableList.isOneShotMedicine()) return 0;
        return timetableList.getCount() * dateNumber.getDbValue().intValue();
    }

    /**
     * スケジュールを作成する。
     *
     * @param medicine       薬
     * @param medicineNumber 服用回数
     */
    private void createScheduleList(@NonNull Medicine medicine, int medicineNumber) {
        clearScheduleList();

        // 服用予定日時を初期化する
        PlanDate planDate = null;

        for (int medicine_i = 0; medicine_i < medicineNumber; medicine_i++) {
            // 服用予定算出基準日時を取得する
            DatetimeType standardDatetime = getNextStartDatetime(medicine, planDate);

            // 服用予定日時を取得する
            planDate = medicine.getTimetableList().getPlanDate(standardDatetime);

            // 服用予定日時を一覧に追加する
            Schedule schedule = new Schedule(medicine.getMedicineId(), planDate.getPlanDate(), planDate.getTimetableId());
            mScheduleList.add(schedule);
        }
    }

    private void clearScheduleList() {
        if (mScheduleList == null) mScheduleList = new ArrayList<>();
        mScheduleList.clear();
    }

    @Contract("_, null -> !null")
    private DatetimeType getNextStartDatetime(@NonNull Medicine medicine, @Nullable PlanDate planDate) {

        // 予定時刻が初期値の場合は予定日時を返却する
        if (planDate == null)
            return medicine.getStartDatetime();

        // 予定日時で使用しているTimetableIdがTimetableListの最終時刻ではなかった場合は予定日時を返却する
        if (!medicine.getTimetableList().isFinalTime(planDate.getTimetableId()))
            return planDate.getPlanDatetime();

        // 予定日時にInterval日数(or月数)を加算した日(時分秒は0初期化)を返却する
        return medicine.getTakeInterval().getNextDatetime(planDate.getPlanDatetime(), medicine.getTakeIntervalMode());
    }
}
