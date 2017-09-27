package com.studiojozu.medicheck.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableComparator;
import com.studiojozu.medicheck.domain.model.setting.TimetableRepository;
import com.studiojozu.medicheck.infrastructure.InfrastructureRegistry;
import com.studiojozu.medicheck.resource.uicomponent.listview.MultiSelectArrayAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.MultiSelectItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimetableSelectService {
    @NonNull
    private final Context mContext;
    @NonNull
    private final TimetableRepository mTimetableRepository = InfrastructureRegistry.getTimetableRepository();

    public TimetableSelectService(@NonNull Context context) {
        mContext = context;
    }

    @NonNull
    public MultiSelectArrayAdapter<Timetable> getTimetableSelectAdapter(@NonNull MedicineTimetableList medicineTimetableList) {
        List<MultiSelectItem> itemList = getTimetableSelectItemList(medicineTimetableList);
        return new MultiSelectArrayAdapter<>(mContext, itemList, false, true);
    }

    private List<MultiSelectItem> getTimetableSelectItemList(@NonNull MedicineTimetableList medicineTimetableList) {
        List<MultiSelectItem> itemList = new ArrayList<>();
        addTimetable(medicineTimetableList, itemList);
        return itemList;
    }

    @SuppressWarnings("unchecked")
    private void addTimetable(@NonNull MedicineTimetableList medicineTimetableList, @NonNull List<MultiSelectItem> listItem) {
        List<Timetable> timetableList = getAllTimetablesOrderByDisplayOrder(mContext);

        for (Timetable timetable : timetableList) {
            MultiSelectItem<Timetable> item = new MultiSelectItem.Builder<Timetable>(timetable.getTimetableNameWithTime())
                    .setTag(timetable)
                    .setChecked(medicineTimetableList.contain(timetable))
                    .build();
            listItem.add(item);
        }
    }

    private ArrayList<Timetable> getAllTimetablesOrderByDisplayOrder(@NonNull Context context) {
        ArrayList<Timetable> timetableList = new ArrayList<>(mTimetableRepository.findAll(context));
        Collections.sort(timetableList, new TimetableComparator(TimetableComparator.ComparePattern.DisplayOrder));

        return timetableList;
    }
}
