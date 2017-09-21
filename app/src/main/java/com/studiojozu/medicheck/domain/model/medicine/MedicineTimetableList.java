package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.medicheck.domain.model.schedule.PlanDate;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableComparator;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MedicineTimetableList implements Iterable<Timetable>, Iterator<Timetable>, Cloneable, Serializable {
    private static final long serialVersionUID = -6268967129299051940L;

    @NonNull
    private ArrayList<Timetable> mTimetables = new ArrayList<>();
    @Nullable
    private transient Iterator<Timetable> mTimetableIterator = null;

    public MedicineTimetableList() {
    }

    public MedicineTimetableList(@NonNull ArrayList<Timetable> timetables) {
        mTimetables = timetables;
    }

    @Override
    public MedicineTimetableList clone() {
        try {
            return (MedicineTimetableList) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    @Override
    public Iterator<Timetable> iterator() {
        MedicineTimetableList medicineTimetableList = clone();
        medicineTimetableList.mTimetableIterator = mTimetables.iterator();

        return medicineTimetableList;
    }

    @Override
    public boolean hasNext() {
        if (mTimetableIterator == null)
            return false;

        return mTimetableIterator.hasNext();
    }

    @Override
    public Timetable next() {
        if (mTimetableIterator == null)
            return null;

        return mTimetableIterator.next();
    }

    private void clearTimetableList() {
        mTimetables.clear();
    }

    private List<Timetable> getTimetableListOrderByTime() {
        @SuppressWarnings("unchecked")
        List<Timetable> timeList = (List<Timetable>) mTimetables.clone();

        Collections.sort(timeList, new TimetableComparator(TimetableComparator.ComparePattern.Time));
        return timeList;
    }

    private List<Timetable> getTimetableListOrderByDisplayOrder() {
        @SuppressWarnings("unchecked")
        List<Timetable> displayOrderList = (List<Timetable>) mTimetables.clone();

        Collections.sort(displayOrderList, new TimetableComparator(TimetableComparator.ComparePattern.DisplayOrder));
        return displayOrderList;
    }

    public boolean isOneShotMedicine() {
        return (mTimetables.size() == 0);
    }

    public int getCount() {
        return mTimetables.size();
    }

    /**
     * 服用予定日時を計算する
     *
     * @param startDatetime 服用予定日時を決めるうえでの起点となる日時(この日時以前とならない予定日時を求める)
     * @return 服用予定日時
     */
    public PlanDate getPlanDate(@NotNull DatetimeType startDatetime) {
        return getPlanDate(startDatetime, null);
    }

    /**
     * 服用予定日時を計算する
     *
     * @param startDatetime 服用予定日時を決めるうえでの起点となる日時(この日時以前とならない予定日時を求める)
     * @param planDate      服用予定日時に使用する日付を保持
     * @return 服用予定日時
     */
    private PlanDate getPlanDate(@NotNull DatetimeType startDatetime, @Nullable PlanDate planDate) {
        if (planDate == null)
            planDate = new PlanDate(startDatetime);

        List<Timetable> timetables = getTimetableListOrderByTime();
        for (Timetable timetable : timetables) {
            PlanDate nextPlanDate = timetable.getPlanDateTime(planDate.getPlanDatetime());
            if (nextPlanDate.getPlanDatetime().compareTo(startDatetime) > 0)
                return nextPlanDate;
        }

        return getPlanDate(startDatetime, planDate.addDay(1));
    }

    /**
     * タイムテーブル一覧の内、最終時刻を表すTimetableであるかをチェックする
     *
     * @param timetableId 比較するタイムテーブルID
     * @return 最終時刻を表すTimetableIDの場合はtrueを返却する
     */
    public boolean isFinalTime(@NonNull TimetableIdType timetableId) {
        List<Timetable> timetables = getTimetableListOrderByTime();
        if (timetables.size() == 0)
            return false;

        Timetable lastTimetable = timetables.get(timetables.size() - 1);
        return lastTimetable.getTimetableId().equals(timetableId);
    }

    public String getDisplayValue() {
        if (isOneShotMedicine()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Timetable timetable : mTimetables) {
            if (builder.length() != 0) builder.append(" / ");
            builder.append(getTimetableDisplayValue(timetable));
        }

        return builder.toString();
    }

    private String getTimetableDisplayValue(@NonNull Timetable timetable) {
        String name = timetable.getTimetableName().getDbValue();
        String time = timetable.getTimetableTime().getDisplayValue();
        return name + "(" + time + ")";
    }
}
