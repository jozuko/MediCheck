package com.studiojozu.medicheck.domain.model.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;

/**
 *
 */
public interface TimetableRepository {
    @Nullable
    Timetable findTimetableById(@NonNull Context context, TimetableIdType timetableId);

    @NonNull
    Collection<Timetable> findAll(@NonNull Context context);
}
