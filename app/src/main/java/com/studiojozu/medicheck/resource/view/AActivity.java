package com.studiojozu.medicheck.resource.view;

import android.app.Activity;
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
import com.studiojozu.medicheck.application.PersonSelectService;
import com.studiojozu.medicheck.resource.uicomponent.calendar.CalendarDayView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.ADialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.CalendarDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.InputDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.SelectorDialogView;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectArrayAdapter;

import org.jetbrains.annotations.Contract;

import java.util.Calendar;

/**
 *
 */
public abstract class AActivity extends Activity {

    @Nullable
    SingleSelectArrayAdapter mSelectPersonAdapter = null;
    @Nullable
    private CalendarDialogView mCalendarDialogView = null;
    @Nullable
    private InputDialogView mInputDialogView = null;
    @Nullable
    private SelectorDialogView mSelectorDialogView = null;

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

    void showCalendarDialog(Calendar displayMonthCalendar, CalendarDayView.OnSelectedDayListener selectedDayListener) {
        if (mCalendarDialogView == null) return;

        mCalendarDialogView.setClientOnSelectedDayListener(selectedDayListener);
        mCalendarDialogView.showCalendar(displayMonthCalendar);
    }

    void showInputDialog(@StringRes int titleResourceId, @Nullable final IValidator validator, @Nullable final InputDialogView.OnCompletedCorrectInputListener listener) {
        if (mInputDialogView == null) return;

        mInputDialogView.showInputDialog(titleResourceId, validator, listener);
    }

    void showPersonSelectorDialog(@Nullable ListView.OnItemClickListener itemClickListener) {
        initSelectPerson();
        if (mSelectPersonAdapter == null) return;

        showSelectorDialog(mSelectPersonAdapter, itemClickListener, new ADialogView.OnCloseListener() {
            @Override
            public void onClose() {
                finalSelectPerson();
            }
        });
    }

    private void showSelectorDialog(@NonNull BaseAdapter adapter, @Nullable ListView.OnItemClickListener itemClickListener, @Nullable ADialogView.OnCloseListener closeListener) {
        if (mSelectorDialogView == null) return;

        mSelectorDialogView.setListViewAdapter(adapter);
        mSelectorDialogView.setOnItemSelectedListener(itemClickListener);
        mSelectorDialogView.setOnCloseListener(closeListener);
        mSelectorDialogView.showDialog();
    }

    @Contract("null -> false")
    private boolean closeDialog(@Nullable ADialogView dialogView) {
        if (dialogView == null) return false;
        if (!dialogView.isShown()) return false;

        dialogView.cancelDialog();
        return true;
    }

    private boolean closeDialog() {
        if (closeDialog(mCalendarDialogView)) return true;
        return (closeDialog(mInputDialogView));
    }

    private void initSelectPerson() {
        PersonSelectService personSelectService = new PersonSelectService(getApplicationContext(), true);
        mSelectPersonAdapter = personSelectService.getPersonSelectAdapter();
    }

    private void finalSelectPerson() {
        if (mSelectPersonAdapter == null)
            return;

        mSelectPersonAdapter.recycle();
    }

}
