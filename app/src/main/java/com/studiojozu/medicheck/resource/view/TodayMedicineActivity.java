package com.studiojozu.medicheck.resource.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.resource.uicomponent.calendar.CalendarDayView;
import com.studiojozu.medicheck.resource.uicomponent.listview.ImageSingleSelectItem;
import com.studiojozu.medicheck.resource.uicomponent.template.TemplateHeaderView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
        mDisplayDate = nowCalendar();
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
        findViewById(R.id.person_select).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        onClickCalendarButton(id);
        onClickSelectPerson(id);
    }

    private void onClickCalendarButton(@IdRes int id) {
        if (id != R.id.calendar_button) return;

        showCalendarDialog(mDisplayDate, new CalendarDayView.OnSelectedDayListener() {
            @Override
            public void onSelected(Calendar selectedDateCalendar) {
                mDisplayDate = selectedDateCalendar;
                Toast.makeText(getApplicationContext(), new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(mDisplayDate.getTime()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickSelectPerson(@IdRes int id) {
        if (id != R.id.person_select) return;

        showPersonSelectorDialog(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mSelectPersonAdapter == null) return;

                List<ImageSingleSelectItem> itemList = mSelectPersonAdapter.getItemList();
                if (itemList.size() == 0) return;

                ImageSingleSelectItem item = itemList.get(position);
                Person person = (Person) item.getTag();
                if (person == null) {
                    Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
                } else {
                    ((TextView) findViewById(R.id.person_select)).setText(person.getDisplayPersonName());
                }
            }
        });
    }

    private Calendar nowCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return calendar;
    }
}
