package com.studiojozu.medicheck.database.table;

import android.support.annotation.NonNull;

/**
 * データベースのカラムを表すオブジェクト
 */
public class ColumnBase {
    @NonNull
    final String mColumnName;
    @NonNull
    final ColumnPattern mColumnType;
    @NonNull
    final NullPattern mNullType;
    @NonNull
    final PrimaryPattern mPrimayType;
    @NonNull
    final AutoIncrementPattern mAutoIncrementType;

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern type, AutoIncrementPattern autoIncrementType) {
        mColumnName = columnName;
        mColumnType = type;
        mNullType = NullPattern.NotNull;
        mPrimayType = PrimaryPattern.Primary;
        mAutoIncrementType = autoIncrementType;
    }

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern type, PrimaryPattern primayType) {
        mColumnName = columnName;
        mColumnType = type;
        mNullType = NullPattern.NotNull;
        mPrimayType = primayType;
        mAutoIncrementType = AutoIncrementPattern.NotAutoIncrement;
    }

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern type) {
        mColumnName = columnName;
        mColumnType = type;
        mNullType = NullPattern.NotNull;
        mPrimayType = PrimaryPattern.NotPrimary;
        mAutoIncrementType = AutoIncrementPattern.NotAutoIncrement;
    }

    String getEqualsCondition() {
        return mColumnName + "=?";
    }
}
