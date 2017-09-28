package com.studiojozu.common.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.studiojozu.medicheck.R;

import org.jetbrains.annotations.Contract;

public abstract class ANumericValidator implements IValidator {
    private final long mMin;
    private final long mMax;
    private final boolean mAllowMinValue;
    private final boolean mAllowMaxValue;

    protected ANumericValidator(int min, int max, boolean allowMinValue, boolean allowMaxValue) {
        mMin = min;
        mMax = max;
        mAllowMinValue = allowMinValue;
        mAllowMaxValue = allowMaxValue;
    }

    @Override
    @StringRes
    public int validate(@Nullable String data) {
        if (data == null)
            return -1;

        if (!isNumeric(data))
            return R.string.validation_numeric;

        if (!checkRange(Long.parseLong(data)))
            return R.string.validation_out_of_range;

        return -1;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private boolean isNumeric(@NonNull String data) {
        try {
            Long.parseLong(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Contract(pure = true)
    private boolean checkRange(long data) {
        return (compareMinValue(data) && compareMaxValue(data));
    }

    @Contract(pure = true)
    private boolean compareMinValue(long data) {
        if (mAllowMinValue)
            return (data >= mMin);
        return (data > mMin);
    }

    @Contract(pure = true)
    private boolean compareMaxValue(long data) {
        if (mAllowMaxValue)
            return (data <= mMax);
        return (data < mMax);
    }
}
