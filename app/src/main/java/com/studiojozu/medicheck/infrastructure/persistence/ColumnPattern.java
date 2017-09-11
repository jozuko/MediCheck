package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.NonNull;

/**
 * データベースのカラム型を表すクラス
 */
public enum ColumnPattern {
    MEDICINE_DATE_NUMBER("integer"),
    MEDICINE_IS_ONE_SHOT("integer"),
    MEDICINE_ID("integer"),
    MEDICINE_NAME("text"),
    MEDICINE_PHOTO("text"),
    MEDICINE_START_DATETIME("integer"),
    MEDICINE_TAKE_INTERVAL_MODE("integer"),
    MEDICINE_TAKE_INTERVAL("integer"),
    MEDICINE_TAKE_NUMBER("integer"),

    PARSON_ID("integer"),
    PARSON_NAME("text"),
    PARSON_PHOTO("text"),

    SCHEDULE_IS_TAKE("integer"),
    SCHEDULE_NEED_ALARM("integer"),
    SCHEDULE_PLAN_DATE("integer"),
    SCHEDULE_TOOK_DATETIME("integer"),

    REMIND_INTERVAL("integer"),
    REMIND_TIMEOUT("integer"),
    USE_REMIND("integer"),

    TIMETABLE_ID("integer"),
    TIMETABLE_NAME("text"),
    TIMETABLE_TIME("integer");

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

