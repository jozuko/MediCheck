package com.studiojozu.common.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.studiojozu.medicheck.R;

import org.jetbrains.annotations.Contract;

import java.math.BigDecimal;

public abstract class ANumericDecimalValidator implements IValidator {
    private final BigDecimal mMin;
    private final BigDecimal mMax;
    private final boolean mAllowMinValue;
    private final boolean mAllowMaxValue;

    protected ANumericDecimalValidator(BigDecimal min, BigDecimal max, boolean allowMinValue, boolean allowMaxValue) {
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

        if (!checkRange(new BigDecimal(data)))
            return R.string.validation_out_of_range;

        return -1;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private boolean isNumeric(@NonNull String data) {
        try {
            new BigDecimal(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Contract(pure = true)
    private boolean checkRange(BigDecimal data) {
        return (compareMinValue(data) && compareMaxValue(data));
    }

    @Contract(pure = true)
    private boolean compareMinValue(BigDecimal data) {
        if (mAllowMinValue)
            return (data.compareTo(mMin) >= 0);
        return (data.compareTo(mMin) > 0);
    }

    @Contract(pure = true)
    private boolean compareMaxValue(BigDecimal data) {
        if (mAllowMaxValue)
            return (data.compareTo(mMax) <= 0);
        return (data.compareTo(mMax) < 0);
    }
}
