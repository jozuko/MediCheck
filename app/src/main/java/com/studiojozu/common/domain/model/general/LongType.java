package com.studiojozu.common.domain.model.general;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;

import org.jetbrains.annotations.NotNull;

/**
 * longの型クラス
 */
public class LongType extends ADbType<Long> implements Comparable<LongType> {

    @NonNull
    private final Long mValue;

    protected LongType() {
        this(0L);
    }

    protected LongType(@NotNull Object value) {
        mValue = (long) value;
    }

    @Override
    public Long getDbValue() {
        return mValue;
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull LongType intModel) {
        return (getDbValue().compareTo(intModel.getDbValue()));
    }
}
