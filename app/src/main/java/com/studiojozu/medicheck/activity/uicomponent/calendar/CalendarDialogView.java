package com.studiojozu.medicheck.activity.uicomponent.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.studiojozu.medicheck.activity.uicomponent.dialog.ADialogView;

import java.util.Calendar;

/**
 *
 */
public class CalendarDialogView extends ADialogView<CalendarView> implements ICalendarAccess {

    private static final ScrollView.LayoutParams LAYOUT_PARAMS;

    static {
        LAYOUT_PARAMS = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    private final CalendarView mCalendarView;

    public CalendarDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCalendarView = new CalendarView(context);

        initTargetView(mCalendarView, LAYOUT_PARAMS, true, true);
        showCalendar(Calendar.getInstance());
    }

    /**
     * カレンダーを任意の年月で表示する
     *
     * @param displayMonthCalendar 表示する年月
     */
    @Override
    public void showCalendar(@NonNull Calendar displayMonthCalendar) {
        mCalendarView.showCalendar(displayMonthCalendar);
    }

    /**
     * 選択された際に呼び出すListenerを設定する。
     *
     * @param listener 選択時Listener
     */
    @Override
    public void setOnSelectedDayListener(@Nullable CalendarDayView.OnSelectedDayListener listener) {
        mCalendarView.setOnSelectedDayListener(listener);
    }
}
