package com.studiojozu.medicheck.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.repository.ColumnBase;
import com.studiojozu.medicheck.repository.TimetableRepository;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.general.DatetimeType;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.timetable.TimetableIdType;
import com.studiojozu.medicheck.type.timetable.TimetableNameType;
import com.studiojozu.medicheck.type.timetable.TimetableTimeType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * タイムテーブルの一覧を管理するクラス
 */
public class TimetableList implements Cloneable, Iterable<Timetable>, Iterator<Timetable> {

    /** {@link Timetable}の一覧 */
    @Nullable
    private List<Timetable> mTimetableList;

    /** {@link #iterator()}が呼び出された際に生成される */
    private Iterator<Timetable> mTimetableIterator;

    /**
     * 薬IDに一致するタイムテーブルのレコードを取得し、一覧に保存する。
     *
     * @param medicineId 薬ID
     */
    public void updateList(@NonNull Context context, @NonNull MedicineIdType medicineId) {
        if (mTimetableList == null) mTimetableList = new ArrayList<>();
        mTimetableList.clear();

        TimetableRepository timetableRepository = new TimetableRepository();
        List<Map<ColumnBase, ADbType>> timetableRecords = timetableRepository.findByMedicineId(context.getApplicationContext(), medicineId);
        if (timetableRecords == null) return;

        for (Map<ColumnBase, ADbType> timetableRecord : timetableRecords) {
            TimetableIdType timetableId = (TimetableIdType) timetableRecord.get(TimetableRepository.COLUMN_ID);
            TimetableNameType timetableName = (TimetableNameType) timetableRecord.get(TimetableRepository.COLUMN_NAME);
            TimetableTimeType timetableTime = (TimetableTimeType) timetableRecord.get(TimetableRepository.COLUMN_TIME);

            Timetable timetable = new Timetable(timetableId, timetableName, timetableTime);
            mTimetableList.add(timetable);
        }
    }

    @Override
    public TimetableList clone() {
        try {
            return (TimetableList) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    @Override
    public Iterator<Timetable> iterator() {
        TimetableList timetableList = clone();
        timetableList.mTimetableIterator = timetableList.createTimetableIterator();
        return timetableList;
    }

    @Override
    public boolean hasNext() {
        return mTimetableIterator.hasNext();
    }

    @Override
    public Timetable next() {
        return mTimetableIterator.next();
    }

    private Iterator<Timetable> createTimetableIterator() {
        if (mTimetableList == null) return new ArrayList<Timetable>().iterator();
        return mTimetableList.iterator();
    }

    /**
     * {@link Timetable} を時間-ID順に並べたリストインスタンスを返却する
     *
     * @return 時間-ID順に並べたリストのインスタンス
     */
    private TimetableTimeList getTimetableListOrderByTime() {
        if (mTimetableList == null)
            return new TimetableTimeList(new ArrayList<Timetable>());

        return new TimetableTimeList(mTimetableList);
    }

    /**
     * 頓服であるかを返却する
     *
     * @return 頓服の場合はtrueを返却する
     */
    public boolean isOneShot() {
        return (mTimetableList == null || mTimetableList.size() == 0);
    }

    /**
     * タイムテーブル一覧の件数を返却する
     *
     * @return タイムテーブルの件数
     */
    int getCount() {
        if (mTimetableList == null) return 0;
        return mTimetableList.size();
    }

    /**
     * 予定日時を計算する
     *
     * @param startDatetime 予定日時を決めるうえでの起点となる日時(この日時以前とならない予定日時を求める)
     * @return 予定日時
     */
    PlanDate getPlanDate(@NotNull DatetimeType startDatetime) {
        return getPlanDate(startDatetime, null);
    }

    /**
     * 予定日時を計算する
     *
     * @param startDatetime 予定日時を決めるうえでの起点となる日時(この日時以前とならない予定日時を求める)
     * @param planDate      予定日時に使用する日付を保持
     * @return 予定日時
     */
    private PlanDate getPlanDate(@NotNull DatetimeType startDatetime, @Nullable PlanDate planDate) {
        if (planDate == null)
            planDate = new PlanDate(startDatetime);

        TimetableTimeList timeList = getTimetableListOrderByTime();
        for (Timetable timetable : timeList) {
            PlanDate nextPlanDate = timetable.getPlanDateTime(planDate.getPlanDatetime());
            if (nextPlanDate.getPlanDatetime().compareTo(startDatetime) > 0)
                return nextPlanDate;
        }

        return getPlanDate(startDatetime, planDate.addDay(1));
    }

    /**
     * タイムテーブル一覧の内、一番最後の時刻のIDとtimetableIdが同一であるかを返却する。
     *
     * @param timetableId 最終時刻のタイムテーブルIDと比較するタイムテーブルID
     * @return 一番最後の時刻のIDとtimetableIdが同一である場合trueを返却する
     */
    boolean isFinalTime(@NonNull TimetableIdType timetableId) {
        TimetableTimeList timeList = getTimetableListOrderByTime();
        return timeList.isFinalTime(timetableId);
    }

}
