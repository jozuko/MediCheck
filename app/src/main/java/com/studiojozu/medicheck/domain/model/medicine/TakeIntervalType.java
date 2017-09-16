package com.studiojozu.medicheck.domain.model.medicine;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DatetimeType;
import com.studiojozu.common.domain.model.general.LongType;
import com.studiojozu.medicheck.R;

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

    @NonNull
    @Override
    public String getDisplayValue() {
        throw new RuntimeException("you need to call getDisplayValue(TakeIntervalModeType).");
    }

    @NonNull
    public String getDisplayValue(@NonNull Resources resources, @NonNull TakeIntervalModeType takeIntervalModeType) {
        if (takeIntervalModeType.isDays())
            return getDaysDisplayValue(resources);
        return getMonthDisplayValue(resources);
    }

    @NonNull
    private String getDaysDisplayValue(@NonNull Resources resources) {
        if(getDbValue() == 0)
            return resources.getString(R.string.interval_every_day);

        if(getDbValue() == 1)
            return resources.getString(R.string.interval_every_other_day);

        return resources.getString(R.string.interval_every_few_days, getDbValue());
    }

    @NonNull
    private String getMonthDisplayValue(@NonNull Resources resources) {
        String daysValue = null;

        if(getDbValue() == 1)
            daysValue = resources.getString(R.string.day_1);
        if(getDbValue() == 2)
            daysValue = resources.getString(R.string.day_2);
        if(getDbValue() == 3)
            daysValue = resources.getString(R.string.day_3);
        if(getDbValue() > 3)
            daysValue = resources.getString(R.string.day_4_over, getDbValue());

        if(daysValue == null)
            return "";
        return resources.getString(R.string.interval_every_month, daysValue);
    }


}
