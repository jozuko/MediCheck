package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * booleanの型クラス
 */
public class BooleanType extends ADbType<Integer> implements Comparable<BooleanType> {

    private static final int TRUE_VALUE = 1;
    private static final int FALSE_VALUE = 0;

    private final boolean mValue;

    public BooleanType(boolean value) {
        mValue = value;
    }

    public BooleanType(int value) {
        mValue = (value == TRUE_VALUE);
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
