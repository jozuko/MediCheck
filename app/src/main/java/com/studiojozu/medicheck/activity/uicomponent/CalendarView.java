package com.studiojozu.medicheck.activity.uicomponent;

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

import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * カレンダーView
 * TODO need add click event for CalendarDayView
 */
public class CalendarView extends LinearLayout implements View.OnClickListener {

    /** アプリケーションコンテキスト */
    @NonNull
    private final Context mContext;
    /** 日付行を格納するLayout */
    @NonNull
    private final LinearLayout mWeekRowLayout;
    /** 年・月を表示するTextView */
    @NonNull
    private final TextView mYearMonthLabel;
    /** 表示する年月を表すCalendarインスタンス */
    @Nullable
    private Calendar mDisplayYearMonthCalendar = null;

    /**
     * カレンダーを生成するコンストラクタ
     *
     * @param context アプリケーションコンテキスト
     * @param attrs   layout.xmlに指定した引数
     */
    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View mainView = LayoutInflater.from(context).inflate(R.layout.calendar, this);

        mWeekRowLayout = mainView.findViewById(R.id.week_row_layout);
        mYearMonthLabel = mainView.findViewById(R.id.year_month_label);
        setEventListener(mainView);

        TypedArray typedArray = getTypedArray(context, attrs);
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
        int year = getYear(typedArray);
        if (year < 0) return;

        int month = getMonth(typedArray);
        if (month < 0) return;

        mDisplayYearMonthCalendar = Calendar.getInstance();
        mDisplayYearMonthCalendar.set(year, month - 1, 1);
    }

    /**
     * Viewに対し、イベントリスナーを設定する。
     *
     * @param mainView Viewを含んでいる親View
     */
    private void setEventListener(View mainView) {
        mainView.findViewById(R.id.previous_month_button).setOnClickListener(this);
        mainView.findViewById(R.id.next_month_button).setOnClickListener(this);
    }

    /**
     * カレンダーを任意の年月で表示する
     *
     * @param displayMonthCalendar 表示する年月
     */
    public void showCalendar(@NonNull Calendar displayMonthCalendar) {
        mDisplayYearMonthCalendar = (Calendar)displayMonthCalendar.clone();
        mDisplayYearMonthCalendar.set(Calendar.DATE, 1);

        showDisplayYearMonth();
        createCalendar();
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
     * AttributeSetからこのViewで使用可能なTypedArrayを取得する
     *
     * @param context アプリケーションコンテキスト
     * @param attrs   layout.xmlで指定したAttributeSet
     * @return このViewで使用可能なTypedArray
     */
    @Contract("_, null -> null")
    private TypedArray getTypedArray(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) return null;
        return context.obtainStyledAttributes(attrs, R.styleable.calendar_view);
    }

    /**
     * layout.xmlに指定した引数からyearに指定した値を取得する
     *
     * @param typedArray layout.xmlに指定した引数
     * @return yearの値
     */
    private int getYear(@Nullable TypedArray typedArray) {
        if (typedArray == null) return -1;
        return typedArray.getInt(R.styleable.calendar_view_year, -1);
    }

    /**
     * layout.xmlに指定した引数からmonthに指定した値を取得する
     *
     * @param typedArray layout.xmlに指定した引数
     * @return monthの値
     */
    private int getMonth(@Nullable TypedArray typedArray) {
        if (typedArray == null) return -1;
        return typedArray.getInt(R.styleable.calendar_view_month, -1);
    }

    /**
     * カレンダーグリッドを生成する
     */
    private void createCalendar() {

        mWeekRowLayout.removeAllViews();

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

        return new CalendarDayView(mContext, displayDay, mDisplayYearMonthCalendar.get(Calendar.MONTH) + 1);
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
