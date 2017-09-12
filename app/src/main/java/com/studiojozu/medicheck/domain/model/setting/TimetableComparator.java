package com.studiojozu.medicheck.domain.model.setting;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 *
 */
public class TimetableComparator implements Comparator<Timetable> {

    @NonNull
    private final ComparePattern mComparePattern;

    public TimetableComparator(@NonNull ComparePattern comparePattern) {
        mComparePattern = comparePattern;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int compare(@Nullable Timetable timetable1, @Nullable Timetable timetable2) {
        if (timetable1 == null && timetable2 == null) return 0;
        if (timetable1 == null && timetable2 != null) return -1;
        if (timetable1 != null && timetable2 == null) return 1;

        if (mComparePattern == ComparePattern.Time)
            return compareTimePriority(timetable1, timetable2);

        return compareDisplayOrderPriority(timetable1, timetable2);
    }

    private int compareTimePriority(@NotNull Timetable timetable1, @NotNull Timetable timetable2) {
        int timeResult = compareTime(timetable1, timetable2);
        if (timeResult != 0) return timeResult;

        int displayOrderResult = compareDisplayOrder(timetable1, timetable2);
        if (displayOrderResult != 0) return displayOrderResult;

        int nameResult = compareName(timetable1, timetable2);
        if (nameResult != 0) return nameResult;

        return compareId(timetable1, timetable2);
    }

    private int compareDisplayOrderPriority(@NotNull Timetable timetable1, @NotNull Timetable timetable2) {
        int displayOrderResult = compareDisplayOrder(timetable1, timetable2);
        if (displayOrderResult != 0) return displayOrderResult;

        int timeResult = compareTime(timetable1, timetable2);
        if (timeResult != 0) return timeResult;

        int nameResult = compareName(timetable1, timetable2);
        if (nameResult != 0) return nameResult;

        return compareId(timetable1, timetable2);
    }

    private int compareTime(@NotNull Timetable timetable1, @NotNull Timetable timetable2) {
        return timetable1.getTimetableTime().compareTo(timetable2.getTimetableTime());
    }

    private int compareDisplayOrder(@NotNull Timetable timetable1, @NotNull Timetable timetable2) {
        return timetable1.getTimetableDisplayOrderType().compareTo(timetable2.getTimetableDisplayOrderType());
    }

    private int compareName(@NotNull Timetable timetable1, @NotNull Timetable timetable2) {
        return timetable1.getTimetableName().compareTo(timetable2.getTimetableName());
    }

    private int compareId(@NotNull Timetable timetable1, @NotNull Timetable timetable2) {
        return timetable1.getTimetableId().compareTo(timetable2.getTimetableId());
    }

    public enum ComparePattern {
        Time,
        DisplayOrder
    }
}
