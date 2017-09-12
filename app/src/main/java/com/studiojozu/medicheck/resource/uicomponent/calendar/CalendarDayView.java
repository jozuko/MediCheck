package com.studiojozu.medicheck.resource.uicomponent.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;

import java.util.Calendar;

/**
 * カレンダーの日付を表示するViewの生成
 */
public class CalendarDayView extends FrameLayout implements View.OnClickListener {
    /** アプリケーションコンテキスト */
    @NonNull
    private final Context mContext;
    /** このViewに表示する日付を格納したCalendarインスタンス */
    @NonNull
    private final Calendar mDisplayDay;
    /** このViewが表示されるカレンダーが表示している月(1～12) */
    private final int mStandardMonth;
    /** このViewのメインレイアウトView */
    @NonNull
    private final FrameLayout mMainLayout;
    /** Image */
    @NonNull
    private final ImageView mTookIconImageView;
    /** 日付を表示するTextView */
    @NonNull
    private final TextView mDayTextView;
    @Nullable
    private OnSelectedDayListener mClientOnSelectedDayListener = null;

    /**
     * 本日日付のカレンダーの日付を表示するViewの生成
     *
     * @param context アプリケーションコンテキスト
     */
    public CalendarDayView(@NonNull Context context) {
        this(context, Calendar.getInstance(), Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    /**
     * カレンダーの日付を表示するViewの生成
     *
     * @param context       アプリケーションコンテキスト
     * @param displayDay    表示する日
     * @param standardMonth 基準月(1～12)
     */
    public CalendarDayView(@NonNull Context context, @NonNull Calendar displayDay, int standardMonth) {
        super(context);

        mContext = context;
        mDisplayDay = (Calendar) displayDay.clone();
        mStandardMonth = standardMonth;
        mMainLayout = getMainLayout();
        mDayTextView = getDayTextView();
        mTookIconImageView = getTookIconImageView();

        mMainLayout.setOnClickListener(this);

        showDate();
        showMedicineHistory();
    }

    /**
     * このViewのメインレイアウトViewを取得する
     *
     * @return このViewのメインレイアウトView
     */
    private FrameLayout getMainLayout() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return (FrameLayout) layoutInflater.inflate(R.layout.calendar_day, this);
    }

    /**
     * 日付を表示するTextViewを取得する
     *
     * @return 日付を表示するTextView
     */
    private TextView getDayTextView() {
        return mMainLayout.findViewById(R.id.calendar_day_text);
    }

    /**
     * 服用済みを示すImageViewを取得する
     *
     * @return 服用済みを示すImageView
     */
    private ImageView getTookIconImageView() {
        return mMainLayout.findViewById(R.id.calendar_day_icon);
    }

    /**
     * 日付を表示するTextViewに日付を表示する
     */
    private void showDate() {
        mDayTextView.setTextColor(getDateTextColor());
        mDayTextView.setText(String.valueOf(mDisplayDay.get(Calendar.DATE)));
    }

    /**
     * 表示している日付がカレンダーの月内のものであるかをチェックする。
     *
     * @return 同一月の日付であればtrueを返却する
     */
    private boolean isSameMonth() {
        return ((mDisplayDay.get(Calendar.MONTH) + 1) == mStandardMonth);
    }

    /**
     * 月に応じた日付を表示するTextViewの文字色を取得する。
     *
     * @return 月に応じた文字色
     */
    private int getDateTextColor() {
        if (isSameMonth())
            return ContextCompat.getColor(mContext, R.color.calendar_day_same_month);

        return ContextCompat.getColor(mContext, R.color.calendar_day_different_month);
    }

    /**
     * 服用履歴に応じた画像を表示する
     */
    private void showMedicineHistory() {
        // TODO get repository information... but first, I need to learn of 'DDD'. how do I get the information...
        mTookIconImageView.setImageResource(R.mipmap.ic_sakura_pink);
    }

    /**
     * 選択された際に呼び出すListenerを設定する。
     *
     * @param listener 選択時Listener
     */
    void setClientOnSelectedDayListener(@Nullable OnSelectedDayListener listener) {
        mClientOnSelectedDayListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mClientOnSelectedDayListener == null) return;

        mClientOnSelectedDayListener.onSelected((Calendar) mDisplayDay.clone());
    }

    /**
     * 日付を表すViewが選択されたときに発火するイベントリスナー
     */
    public interface OnSelectedDayListener {
        void onSelected(Calendar selectedDateCalendar);
    }
}
