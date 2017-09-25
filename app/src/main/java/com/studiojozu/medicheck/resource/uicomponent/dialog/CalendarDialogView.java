package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.studiojozu.medicheck.resource.uicomponent.calendar.CalendarDayView;
import com.studiojozu.medicheck.resource.uicomponent.calendar.CalendarView;
import com.studiojozu.medicheck.resource.uicomponent.calendar.ICalendarAccess;

import java.util.Calendar;

/**
 *
 */
public class CalendarDialogView extends ADialogView<CalendarView> implements ICalendarAccess, CalendarDayView.OnSelectedDayListener {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    @Nullable
    private CalendarDayView.OnSelectedDayListener mOnSelectedDayListener = null;

    public CalendarDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, new CalendarView(context));

        mDialogTargetView.setClientOnSelectedDayListener(this);
        initTargetView(LAYOUT_PARAMS, true, false);
    }

    /**
     * カレンダーを任意の年月で表示する
     *
     * @param displayMonthCalendar 表示する年月
     */
    @Override
    public void showCalendar(@NonNull Calendar displayMonthCalendar) {
        mDialogTargetView.showCalendar(displayMonthCalendar);
        showDialog();
    }

    /**
     * 選択された際に呼び出すListenerを設定する。
     *
     * @param listener 選択時Listener
     */
    public void setClientOnSelectedDayListener(@Nullable CalendarDayView.OnSelectedDayListener listener) {
        mOnSelectedDayListener = listener;
    }

    @Override
    public void onSelected(Calendar selectedDateCalendar) {
        if (mOnSelectedDayListener != null)
            mOnSelectedDayListener.onSelected(selectedDateCalendar);

        cancelDialog();
    }
}
