package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;

import java.util.Calendar;

/**
 * 服用予定日を管理するクラス
 */
public class PlanDateType extends DateType<PlanDateType> {

    private static final long serialVersionUID = 9088775597942158497L;

    public PlanDateType() {
        this(Calendar.getInstance());
    }

    public PlanDateType(@NonNull Object millisecond) {
        super(millisecond);
    }

    public PlanDateType(int year, int month, int day) {
        super(year, month, day);
    }

    /**
     * フィールドが保持する日時に、パラメータのdayを日数として追加した値を返却する。
     *
     * @param days 加算する日数
     * @return パラメータ値加算後の値を保持するインスタンス
     */
    @NonNull
    @Override
    public PlanDateType addDay(int days) {
        Calendar calendar = (Calendar) mValue.clone();
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return new PlanDateType(calendar.getTimeInMillis());
    }
}
