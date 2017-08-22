package com.studiojozu.medicheck.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * intの型クラス
 */
public class IntType extends ADbType<Integer> implements Comparable<IntType> {
    private final int mValue;

    public IntType() {
        mValue = 0;
    }

    public IntType(int value) {
        mValue = value;
    }

    @Override
    public Integer getDbValue() {
        return mValue;
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull IntType intModel) {
        return (getDbValue().compareTo(intModel.getDbValue()));
    }
}
