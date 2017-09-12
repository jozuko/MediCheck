package com.studiojozu.medicheck.resource.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.resource.uicomponent.calendar.CalendarDayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 今日のお薬を一覧表示する。
 */
public class TodayMedicineActivity extends AActivity implements View.OnClickListener, TemplateHeaderView.TemplateHeaderIncludeActivity {

    /** 表示日付 */
    private Calendar mDisplayDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_today_medicine);

        initTemplateHeaderView();

        mDisplayDate = Calendar.getInstance();
        mDisplayDate.setTimeInMillis(System.currentTimeMillis());

        setClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setClickListener() {
        findViewById(R.id.calendar_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        onClickCalendarButton(id);
    }

    private void onClickCalendarButton(int id) {
        if (id != R.id.calendar_button) return;

        showCalendarDialog(mDisplayDate, null, new CalendarDayView.OnSelectedDayListener() {
            @Override
            public void onSelected(Calendar selectedDateCalendar) {
                mDisplayDate = selectedDateCalendar;
                Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy/MM/dd").format(mDisplayDate.getTime()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initTemplateHeaderView() {
        new TemplateHeaderView(this, R.mipmap.ic_take_person, R.string.button_today_medicine);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public boolean beforeFinish() {
        return true;
    }

}
