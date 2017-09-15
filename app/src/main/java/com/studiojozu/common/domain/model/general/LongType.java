package com.studiojozu.common.domain.model.general;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;

import org.jetbrains.annotations.NotNull;

/**
 * longの型クラス
 */
public class LongType<C extends LongType<C>> extends ADbType<Long, C> implements Comparable<LongType> {

    private static final long serialVersionUID = 7610652992905703415L;
    @NonNull
    private final Long mValue;

    protected LongType() {
        this(0L);
    }

    protected LongType(@NotNull Object value) {
        if (value instanceof Long)
            mValue = (long) value;
        else if (value instanceof Integer)
            mValue = (long) (int) value;
        else
            mValue = -1L;
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
