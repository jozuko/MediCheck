package com.studiojozu.medicheck.resource.uicomponent.calendar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * カレンダーViewにアクセスするインターフェース定義
 */
public interface ICalendarAccess {

    /**
     * カレンダーを任意の年月で表示する
     *
     * @param displayMonthCalendar 表示する年月
     */
    void showCalendar(@NonNull Calendar displayMonthCalendar);

    /**
     * 選択された際に呼び出すListenerを設定する。
     *
     * @param listener 選択時Listener
     */
    void setOnSelectedDayListener(@Nullable CalendarDayView.OnSelectedDayListener listener);
}
