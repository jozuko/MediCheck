package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * intの型クラス
 */
public class IntModel extends ADbType<Integer> implements Comparable<IntModel> {
    private final int _value;

    public IntModel() {
        _value = 0;
    }

    public IntModel(int value) {
        _value = value;
    }

    @Override
    public Integer getDbValue() {
        return _value;
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull IntModel intModel) {
        return (getDbValue().compareTo(intModel.getDbValue()));
    }
}
