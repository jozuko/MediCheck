package com.studiojozu.common.domain.model.general;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;

/**
 * booleanの型クラス
 */
public abstract class BooleanType extends ADbType<Integer> implements Comparable<BooleanType> {

    private static final int TRUE_VALUE = 1;
    private static final int FALSE_VALUE = 0;

    private final boolean mValue;

    protected BooleanType(@NonNull Object value) {
        if (value instanceof Boolean)
            mValue = (boolean) value;
        else if ((value instanceof Long) || (value instanceof Integer))
            mValue = ((int) value == TRUE_VALUE);
        else
            throw new IllegalArgumentException("unknown type.");

    }

    @Override
    public Integer getDbValue() {
        return (mValue ? TRUE_VALUE : FALSE_VALUE);
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull BooleanType target) {
        return getDbValue().compareTo(target.getDbValue());
    }

    public boolean isTrue() {
        return mValue;
    }
}
