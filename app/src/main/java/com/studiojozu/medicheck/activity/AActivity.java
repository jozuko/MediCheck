package com.studiojozu.medicheck.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.activity.uicomponent.calendar.CalendarDayView;
import com.studiojozu.medicheck.activity.uicomponent.calendar.CalendarDialogView;

import java.util.Calendar;

/**
 *
 */
public class AActivity extends Activity {

    @Nullable
    private CalendarDialogView mCalendarDialogView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.view_main);

        ViewGroup mainLayout = findViewById(R.id.main_layout);
        View view = LayoutInflater.from(getApplicationContext()).inflate(layoutResID, null);
        mainLayout.addView(view);

        getCalendarDialogView();
    }

    private void getCalendarDialogView() {
        mCalendarDialogView = findViewById(R.id.main_calendar_dialog);
    }

    protected void showCalendarDialog(Calendar displayMonthCalendar, View.OnClickListener cancelListener, CalendarDayView.OnSelectedDayListener selectedDayListener) {
        if (mCalendarDialogView == null) return;

        mCalendarDialogView.setOnCancelButtonClickListener(cancelListener);
        mCalendarDialogView.setOnSelectedDayListener(selectedDayListener);
        mCalendarDialogView.showCalendar(displayMonthCalendar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && closeDialog()) return false;
        return super.onKeyDown(keyCode, event);
    }

    private boolean closeDialog() {
        if (mCalendarDialogView != null && mCalendarDialogView.isShown()) {
            mCalendarDialogView.cancelDialog();
            return true;
        }

        return false;
    }

}
