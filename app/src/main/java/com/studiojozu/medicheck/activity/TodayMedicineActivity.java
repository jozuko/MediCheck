package com.studiojozu.medicheck.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.activity.uicomponent.ImageSpinnerAdapter;
import com.studiojozu.medicheck.activity.uicomponent.calendar.CalendarDayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 今日のお薬を一覧表示する。
 */
public class TodayMedicineActivity extends AActivity implements View.OnClickListener {

    /** 表示日付 */
    private Calendar mDisplayDate;

    /** 飲む人Spinnerに使用するAdapter */
    @Nullable
    private ImageSpinnerAdapter mImageSpinnerAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_today_medicine);

        mDisplayDate = Calendar.getInstance();
        mDisplayDate.setTimeInMillis(System.currentTimeMillis());

        createImageSpinnerAdapter();
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
        try {
            if (mImageSpinnerAdapter != null) mImageSpinnerAdapter.recycle();
        } finally {
            super.onDestroy();
        }
    }

    /**
     * Spinnerに使用するAdapterを作成する
     */
    private void createImageSpinnerAdapter() {
        mImageSpinnerAdapter = new ImageSpinnerAdapter(this.getApplicationContext(), R.mipmap.parson_no_image);
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
