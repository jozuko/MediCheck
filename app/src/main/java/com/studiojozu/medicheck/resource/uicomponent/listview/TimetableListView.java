package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.resource.uicomponent.ACustomView;

public class TimetableListView extends ACustomView<TimetableListView> {


    public TimetableListView(@NonNull Context context) {
        this(context, null);
    }

    public TimetableListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutResource() {
        return R.layout.timetable_list;
    }

    @Nullable
    @Override
    protected int[] styleableResources() {
        return null;
    }


}
