package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.medicheck.domain.model.schedule.PlanDate;
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType;

import org.jetbrains.annotations.NotNull;

/**
 * タイムテーブルのデータを管理するクラス
 */
public class Timetable implements Cloneable {

    /** タイムテーブルID */
    @NonNull
    private final TimetableIdType mTimetableId;
    /** タイムテーブルの名前 */
    @NonNull
    private TimetableNameType mTimetableName;
    /** タイムテーブルの時刻 */
    @NonNull
    private TimetableTimeType mTimetableTime;

    /**
     * DB未登録データ用のコンストラクタ
     */
    public Timetable() {
        mTimetableId = new TimetableIdType();
        mTimetableName = new TimetableNameType();
        mTimetableTime = new TimetableTimeType();
    }

    /**
     * DB登録済みのデータ用のコンストラクタ
     *
     * @param timetableId   タイムテーブルID
     * @param timetableName タイムテーブルの名前
     * @param timetableTime タイムテーブルの時刻
     */
    public Timetable(@NonNull TimetableIdType timetableId, @NonNull TimetableNameType timetableName, @NonNull TimetableTimeType timetableTime) {
        mTimetableId = timetableId;
        mTimetableName = timetableName;
        mTimetableTime = timetableTime;
    }

    /**
     * タイムテーブル名を変更する
     *
     * @param timetableName 新しく使用するタイムテーブルの名前
     */
    public void setTimetableName(@NotNull String timetableName) {
        mTimetableName = new TimetableNameType(timetableName);
    }

    /**
     * タイムテーブルの時刻を変更する
     *
     * @param hourOfDay 新しく使用するタイムテーブルの時刻(時)
     * @param minute    新しく使用するタイムテーブルの時刻(分)
     */
    public void setTimetableTime(int hourOfDay, int minute) {
        mTimetableTime = new TimetableTimeType(hourOfDay, minute);
    }

    /**
     * 日付を表すパラメータを使用して予定日時を作成する
     *
     * @param datetimeType 日付
     * @return 予定日時（パラメータの日付 + {@link #mTimetableTime}の時分）
     */
    public PlanDate getPlanDateTime(DatetimeType datetimeType) {
        DatetimeType planDatetime = mTimetableTime.replaceHourMinute(datetimeType);
        PlanDateType planDate = new PlanDateType(datetimeType);
        return new PlanDate(planDatetime, planDate, mTimetableId);
    }

    /**
     * タイムテーブルのIDを返却する
     *
     * @return タイムテーブルIDのクローン
     */
    public TimetableIdType getTimetableId() {
        return mTimetableId.clone();
    }

    /**
     * タイムテーブルの時刻を返却する
     *
     * @return タイムテーブル時刻のクローン
     */
    public TimetableTimeType getTimetableTime() {
        return mTimetableTime.clone();
    }

    @Override
    public Timetable clone() {
        try {
            return (Timetable) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
