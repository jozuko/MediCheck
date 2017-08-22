package com.studiojozu.medicheck.database.repository;

import android.support.annotation.NonNull;

/**
 * データベースのカラム型を表すクラス
 */
public enum ColumnPattern {
    TEXT("text"),
    INT("integer"),
    DATE("integer"),
    TIME("integer"),
    DATETIME("integer"),
    BOOL("integer"),
    REMIND_INTERVAL("integer"),
    REMIND_TIMEOUT("integer"),
    INTERVAL("integer"),
    INTERVAL_TYPE("integer");

    @NonNull
    private final String mTypeName;

    ColumnPattern(@NonNull String typeName) {
        mTypeName = typeName;
    }

    @NonNull
    String getTypeName() {
        return mTypeName;
    }
}

/**
 * データベースのカラムのnot null属性を表すクラス
 */
enum NullPattern {
    NotNull,
    Nullable
}

/**
 * データベースのカラムのprimary key属性を表すクラス
 */
enum PrimaryPattern {
    Primary,
    NotPrimary
}

/**
 * データベースのカラムのauto increment属性を表すクラス
 */
enum AutoIncrementPattern {
    AutoIncrement,
    NotAutoIncrement
}

