package com.studiojozu.medicheck.resource.uicomponent.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.resource.uicomponent.ACustomView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * カレンダーView
 */
public class CalendarView extends ACustomView<CalendarView> implements View.OnClickListener, ICalendarAccess {

    /** 日付行を格納するLayout */
    @NonNull
    private final LinearLayout mWeekRowLayout;
    /** 年・月を表示するTextView */
    @NonNull
    private final TextView mYearMonthLabel;
    @NonNull
    private final List<CalendarDayView> mCalendarDayViewList = new ArrayList<>();
    /** 表示する年月を表すCalendarインスタンス */
    @Nullable
    private Calendar mDisplayYearMonthCalendar = null;
    @Nullable
    private CalendarDayView.OnSelectedDayListener mClientOnSelectedDayListener = null;

    /**
     * ダイアログとしてカレンダーを生成するときに使用するコンストラクタ
     *
     * @param context アプリケーションコンテキスト
     */
    public CalendarView(@NonNull Context context) {
        this(context, null);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mWeekRowLayout = mCustomView.findViewById(R.id.week_row_layout);
        mYearMonthLabel = mCustomView.findViewById(R.id.year_month_label);
        setEventListener();
        showCalendarFromAttrs(attrs);
    }

    @Override
    protected int layoutResource() {
        return R.layout.calendar;
    }

    @Nullable
    @Override
    protected int[] styleableResources() {
        return R.styleable.calendar_view;
    }

    private void showCalendarFromAttrs(@Nullable AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray typedArray = getTypedArray(attrs);
        try {
            createDisplayYearMonthCalendar(typedArray);
            createCalendar();
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }
    }

    /**
     * layout.xmlの引数から表示するカレンダーインスタンスを生成する。
     *
     * @param typedArray layout.xmlの引数
     */
    private void createDisplayYearMonthCalendar(TypedArray typedArray) {
        int year = getAttributeInteger(typedArray, R.styleable.calendar_view_year);
        if (year == RESOURCE_DEFAULT_INTEGER) return;

        int month = getAttributeInteger(typedArray, R.styleable.calendar_view_month);
        if (month == RESOURCE_DEFAULT_INTEGER) return;

        mDisplayYearMonthCalendar = Calendar.getInstance();
        mDisplayYearMonthCalendar.set(year, month - 1, 1);
    }

    /**
     * Viewに対し、イベントリスナーを設定する。
     */
    private void setEventListener() {
        mCustomView.findViewById(R.id.previous_month_button).setOnClickListener(this);
        mCustomView.findViewById(R.id.next_month_button).setOnClickListener(this);
    }

    /**
     * カレンダーを任意の年月で表示する
     *
     * @param displayMonthCalendar 表示する年月
     */
    @Override
    public void showCalendar(@NonNull Calendar displayMonthCalendar) {
        mDisplayYearMonthCalendar = (Calendar) displayMonthCalendar.clone();
        mDisplayYearMonthCalendar.set(Calendar.DATE, 1);

        showDisplayYearMonth();
        createCalendar();
    }

    /**
     * 日付を表すViewが選択されたときのイベントを処理するリスナーを設定する
     *
     * @param listener 日付を表すViewが選択されたときに発火するリスナー
     */
    public void setClientOnSelectedDayListener(@Nullable CalendarDayView.OnSelectedDayListener listener) {
        mClientOnSelectedDayListener = listener;

        for (CalendarDayView calendarDayView : mCalendarDayViewList) {
            calendarDayView.setClientOnSelectedDayListener(mClientOnSelectedDayListener);
        }
    }

    /**
     * 年月を表示する
     */
    private void showDisplayYearMonth() {
        if (mDisplayYearMonthCalendar == null) {
            mYearMonthLabel.setText("");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM", Locale.getDefault());
        mYearMonthLabel.setText(dateFormat.format(mDisplayYearMonthCalendar.getTime()));
    }

    /**
     * カレンダーグリッドを生成する
     */
    private void createCalendar() {

        mWeekRowLayout.removeAllViews();
        mCalendarDayViewList.clear();

        Calendar calendar = getCalendarFirstDay();
        if (calendar == null) return;

        LinearLayout weekRowLinearLayout = null;
        for (int i = 0; i < (7 * 6); i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || weekRowLinearLayout == null) {
                weekRowLinearLayout = createWeekRowLayout();
                mWeekRowLayout.addView(weekRowLinearLayout);
            }

            weekRowLinearLayout.addView(createCalendarDayView(calendar), createCalendarDayLayoutParams());
            calendar.add(Calendar.DATE, 1);
        }
    }

    /**
     * 1週間分の日付を並べるのに使用するLinearLayoutを生成する
     *
     * @return 生成したLinearLayout
     */
    private LinearLayout createWeekRowLayout() {
        LinearLayout weekRowLinearLayout = new LinearLayout(mContext);
        weekRowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        weekRowLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDimenPixel(R.dimen.calendar_row_height)));

        return weekRowLinearLayout;
    }

    /**
     * 引数の年・月が示すカレンダーの最初の日曜日を取得する
     *
     * @return 最初の日曜日
     */
    @Nullable
    private Calendar getCalendarFirstDay() {
        if (mDisplayYearMonthCalendar == null) return null;

        Calendar calendar = (Calendar) mDisplayYearMonthCalendar.clone();

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        return calendar;
    }

    /**
     * カレンダーの日付を表示するためのViewを生成する。
     *
     * @param displayDay 表示する日付
     * @return カレンダーの日付インスタンス
     */
    @Nullable
    private CalendarDayView createCalendarDayView(@NonNull Calendar displayDay) {
        if (mDisplayYearMonthCalendar == null) return null;

        CalendarDayView calendarDayView = new CalendarDayView(mContext, displayDay, mDisplayYearMonthCalendar.get(Calendar.MONTH) + 1);
        calendarDayView.setClientOnSelectedDayListener(mClientOnSelectedDayListener);
        mCalendarDayViewList.add(calendarDayView);

        return calendarDayView;
    }

    /**
     * 同じLinearLayoutの水平方向に並ぶViewを、均一幅で表示するためのLayoutParamを返却する
     *
     * @return 均一幅表示用LayoutParam
     */
    @NonNull
    private LinearLayout.LayoutParams createCalendarDayLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1.0f;

        return layoutParams;
    }

    /**
     * dpで指定した値をpixel値に変換する
     *
     * @param dimenResourceId dimens.xmlに指定した数値のname
     * @return pixel値
     */
    private int getDimenPixel(int dimenResourceId) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        final float dimenValue = mContext.getResources().getDimension(dimenResourceId);

        return (int) (dimenValue * scale + 0.5f);
    }

    @Override
    public void onClick(View view) {
        int clickedResourceId = view.getId();

        onClickPreviousMonthButton(clickedResourceId);
        onClickNextMonthButton(clickedResourceId);
    }

    private void onClickPreviousMonthButton(int clickedResourceId) {
        if (clickedResourceId != R.id.previous_month_button) return;
        if (mDisplayYearMonthCalendar == null) return;

        Calendar targetYearMonth = (Calendar) mDisplayYearMonthCalendar.clone();
        targetYearMonth.add(Calendar.MONTH, -1);

        showCalendar(targetYearMonth);
    }

    private void onClickNextMonthButton(int clickedResourceId) {
        if (clickedResourceId != R.id.next_month_button) return;
        if (mDisplayYearMonthCalendar == null) return;

        Calendar targetYearMonthCalendar = (Calendar) mDisplayYearMonthCalendar.clone();
        targetYearMonthCalendar.add(Calendar.MONTH, 1);

        showCalendar(targetYearMonthCalendar);
    }
}
