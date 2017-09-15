package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.LongType;

/**
 * 日付の間隔の型クラス.
 * 〇日おき、毎月〇日の〇を表す
 */
public class TakeIntervalType extends LongType<TakeIntervalType> {
    private static final long serialVersionUID = 1399242371014805824L;

    public TakeIntervalType() {
        super(0);
    }

    public TakeIntervalType(@NonNull Object value) {
        super(value);
    }

    /**
     * Interval分の日数（or 月数）を加算する
     *
     * @param datetime         日時
     * @param takeIntervalMode 日数か月数を表す
     * @return パラメータの日時にInterval分の日数（or 月数）を加算した値
     */
    public DatetimeType getNextDatetime(@NonNull DatetimeType datetime, @NonNull TakeIntervalModeType takeIntervalMode) {
        if (takeIntervalMode.isDays()) {
            return datetime.addDay(getDbValue().intValue());
        }
        return datetime.addMonth(getDbValue().intValue());
    }
}
