package com.studiojozu.medicheck.resource.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.studiojozu.common.domain.model.IValidator;
import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.resource.uicomponent.calendar.CalendarDayView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.ADialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.CalendarDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.DatePickerDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.InputDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.SelectorDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.TimePickerDialogView;

import org.jetbrains.annotations.Contract;

import java.util.Calendar;

/**
 *
 */
public abstract class AMainActivity extends AActivity {

    @Nullable
    private CalendarDialogView mCalendarDialogView = null;
    @Nullable
    private InputDialogView mInputDialogView = null;
    @Nullable
    private SelectorDialogView mSelectorDialogView = null;
    @Nullable
    private DatePickerDialogView mDatePickerDialogView = null;
    @Nullable
    private TimePickerDialogView mTimePickerDialogView = null;

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
        getInputDialogView();
        getSelectorDialogView();
        getDatePickerDialogView();
        getTimePickerDialogView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && closeDialog()) return false;
        return super.onKeyDown(keyCode, event);
    }

    private void getCalendarDialogView() {
        mCalendarDialogView = findViewById(R.id.main_calendar_dialog);
    }

    private void getInputDialogView() {
        mInputDialogView = findViewById(R.id.main_input_dialog);
    }

    private void getSelectorDialogView() {
        mSelectorDialogView = findViewById(R.id.main_selector_dialog);
    }

    private void getDatePickerDialogView() {
        mDatePickerDialogView = findViewById(R.id.main_date_picker_dialog);
    }

    private void getTimePickerDialogView() {
        mTimePickerDialogView = findViewById(R.id.main_time_picker_dialog);
    }

    void showCalendarDialog(Calendar displayMonthCalendar, CalendarDayView.OnSelectedDayListener selectedDayListener) {
        if (mCalendarDialogView == null) return;

        mCalendarDialogView.setClientOnSelectedDayListener(selectedDayListener);
        mCalendarDialogView.showCalendar(displayMonthCalendar);
    }

    void showInputDialog(@StringRes int titleResourceId, @NonNull InputDialogView.InputType inputType, @NonNull String defaultValue, @Nullable final IValidator validator, @Nullable final InputDialogView.OnCompletedCorrectInputListener listener) {
        if (mInputDialogView == null) return;

        mInputDialogView.showInputDialog(titleResourceId, inputType, defaultValue, validator, listener);
    }

    void showSingleSelectorDialog(@StringRes int titleResourceId, @NonNull BaseAdapter adapter, @Nullable ListView.OnItemClickListener itemClickListener, @Nullable ADialogView.OnCloseListener closeListener) {
        if (mSelectorDialogView == null) return;

        mSelectorDialogView.setListViewAdapter(adapter);
        mSelectorDialogView.setOnItemSelectedListener(itemClickListener);
        mSelectorDialogView.setOnCloseListener(closeListener);
        mSelectorDialogView.showSelectorDialog(titleResourceId, false, false);
    }

    void showDatePickerDialog(@StringRes int titleResourceId, int year, int month, int dayOfMonth, @Nullable DatePickerDialogView.OnDateSelectedListener onDateSelectedListener) {
        if (mDatePickerDialogView == null) return;
        mDatePickerDialogView.showDatePickerDialog(titleResourceId, year, month, dayOfMonth, onDateSelectedListener);
    }

    void showTimePickerDialog(@StringRes int titleResourceId, int hourOfDay, int minute, @Nullable TimePickerDialogView.OnTimeSelectedListener onTimeSelectedListener) {
        if (mTimePickerDialogView == null) return;
        mTimePickerDialogView.showTimePickerDialog(titleResourceId, hourOfDay, minute, onTimeSelectedListener);
    }

    @Contract("null -> false")
    private boolean closeDialog(@Nullable ADialogView dialogView) {
        if (dialogView == null) return false;
        if (!dialogView.isShown()) return false;

        dialogView.cancelDialog();
        return true;
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean closeDialog() {
        if (closeDialog(mCalendarDialogView)) return true;
        if (closeDialog(mInputDialogView)) return true;
        if (closeDialog(mSelectorDialogView)) return true;
        if (closeDialog(mDatePickerDialogView)) return true;
        if (closeDialog(mTimePickerDialogView)) return true;

        return false;
    }
}
