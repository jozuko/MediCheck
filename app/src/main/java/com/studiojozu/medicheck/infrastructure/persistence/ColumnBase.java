package com.studiojozu.medicheck.infrastructure.persistence;

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
    final PrimaryPattern mPrimaryType;
    @NonNull
    final AutoIncrementPattern mAutoIncrementType;

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern columnPattern, @NonNull AutoIncrementPattern autoIncrementPattern) {
        mColumnName = columnName;
        mColumnType = columnPattern;
        mNullType = NullPattern.NotNull;
        mPrimaryType = PrimaryPattern.Primary;
        mAutoIncrementType = autoIncrementPattern;
    }

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern columnPattern, @NonNull PrimaryPattern primaryPattern) {
        mColumnName = columnName;
        mColumnType = columnPattern;
        mNullType = NullPattern.NotNull;
        mPrimaryType = primaryPattern;
        mAutoIncrementType = AutoIncrementPattern.NotAutoIncrement;
    }

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern type) {
        mColumnName = columnName;
        mColumnType = type;
        mNullType = NullPattern.NotNull;
        mPrimaryType = PrimaryPattern.NotPrimary;
        mAutoIncrementType = AutoIncrementPattern.NotAutoIncrement;
    }

    String getEqualsCondition() {
        return mColumnName + "=?";
    }
}
