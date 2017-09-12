package com.studiojozu.medicheck.resource.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.resource.uicomponent.calendar.CalendarDayView;
import com.studiojozu.medicheck.resource.uicomponent.view.TemplateHeaderView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 今日のお薬を一覧表示する。
 */
public class TodayMedicineActivity extends AActivity implements View.OnClickListener {

    /** 表示日付 */
    private Calendar mDisplayDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_today_medicine);

        initHeaderParent();

        mDisplayDate = Calendar.getInstance();
        mDisplayDate.setTimeInMillis(System.currentTimeMillis());

        setClickListener();
    }

    private void initHeaderParent() {
        TemplateHeaderView templateHeaderView = findViewById(R.id.header_view);
        templateHeaderView.setParentActivity(this);
        templateHeaderView.setOnFinishingListener(new TemplateHeaderView.OnFinishingListener() {
            @Override
            public boolean onFinishing() {
                // TODO do something...
                return true;
            }
        });
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
}
