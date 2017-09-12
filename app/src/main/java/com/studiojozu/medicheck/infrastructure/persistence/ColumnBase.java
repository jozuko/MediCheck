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

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern columnPattern) {
        mColumnName = columnName;
        mColumnType = columnPattern;
        mNullType = NullPattern.NotNull;
        mPrimaryType = PrimaryPattern.Primary;
    }

    ColumnBase(@NonNull String columnName, @NonNull ColumnPattern columnPattern, @NonNull PrimaryPattern primaryPattern) {
        mColumnName = columnName;
        mColumnType = columnPattern;
        mNullType = NullPattern.NotNull;
        mPrimaryType = primaryPattern;
    }

    ColumnBase(@NonNull ColumnBase baseColumn, @NonNull PrimaryPattern primaryPattern) {
        mColumnName = baseColumn.mColumnName;
        mColumnType = baseColumn.mColumnType;
        mNullType = NullPattern.NotNull;
        mPrimaryType = primaryPattern;
    }

    String getEqualsCondition() {
        return mColumnName + "=?";
    }
}
