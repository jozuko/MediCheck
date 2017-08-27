package com.studiojozu.medicheck.entity;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.timetable.TimetableIdType;
import com.studiojozu.medicheck.type.timetable.TimetableTimeType;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * タイムテーブルの時刻をキーとして昇順に返却するイテレータ.
 */
class TimetableTimeList implements Iterable<Timetable>, Iterator<Timetable> {
    @NonNull
    private final TreeSet<Timetable> mTimetableTreeSet;

    private Iterator<Timetable> mIterator;

    TimetableTimeList(@NonNull List<Timetable> timetables) {
        mTimetableTreeSet = new TreeSet<>(new TimetableTimeComparator());
        mTimetableTreeSet.addAll(timetables);
    }

    @Override
    public Iterator<Timetable> iterator() {
        mIterator = mTimetableTreeSet.iterator();
        return this;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public Timetable next() {
        return mIterator.next();
    }

    /**
     * タイムテーブル一覧の内、一番最後の時刻のIDとtimetableIdが同一であるかを返却する。
     *
     * @param timetableId 最終時刻のタイムテーブルIDと比較するタイムテーブルID
     * @return 一番最後の時刻のIDとtimetableIdが同一である場合trueを返却する
     */
    boolean isFinalTime(@NonNull TimetableIdType timetableId) {

        TimetableTimeType finalTime = mTimetableTreeSet.last().getTimetableTime();

        for (Timetable timetable : mTimetableTreeSet) {
            if (!timetable.getTimetableTime().equals(finalTime)) continue;

            if (timetableId.equals(timetable.getTimetableId())) return true;
        }

        return false;
    }

    /**
     * {@link Timetable}を時刻-IDの順に昇順に並べるComparator
     */
    private static class TimetableTimeComparator implements Comparator<Timetable> {
        @Override
        public int compare(Timetable timetable1, Timetable timetable2) {

            TimetableTimeType time1 = timetable1.getTimetableTime();
            TimetableTimeType time2 = timetable2.getTimetableTime();
            int compareResultTime = time1.compareTo(time2);
            if (compareResultTime != 0) return (compareResultTime);

            TimetableIdType id1 = timetable1.getTimetableId();
            TimetableIdType id2 = timetable2.getTimetableId();
            return id1.compareTo(id2);
        }
    }
}
