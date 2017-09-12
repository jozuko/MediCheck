package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;

import java.util.Map;

abstract class ASqliteConverter<T> {

    @Nullable
    final Map<ColumnBase, ADbType> mDatabaseRecord;

    ASqliteConverter(@Nullable Map<ColumnBase, ADbType> databaseRecord) {
        mDatabaseRecord = databaseRecord;
    }

    abstract T createFromRecord();

    @Nullable
    ADbType getData(@NonNull ColumnBase columnBase) {
        if (mDatabaseRecord == null)
            return null;

        return mDatabaseRecord.get(columnBase);
    }
}
