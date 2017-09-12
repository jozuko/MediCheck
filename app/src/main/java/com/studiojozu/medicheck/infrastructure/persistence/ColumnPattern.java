package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.DateNumberType;
import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameType;
import com.studiojozu.medicheck.domain.model.medicine.MedicinePhotoType;
import com.studiojozu.medicheck.domain.model.medicine.StartDatetimeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalType;
import com.studiojozu.medicheck.domain.model.medicine.TakeNumberType;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;
import com.studiojozu.medicheck.domain.model.person.PersonNameType;
import com.studiojozu.medicheck.domain.model.person.PersonPhotoType;
import com.studiojozu.medicheck.domain.model.schedule.IsTakeType;
import com.studiojozu.medicheck.domain.model.schedule.NeedAlarmType;
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType;
import com.studiojozu.medicheck.domain.model.schedule.TookDatetime;
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType;
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType;
import com.studiojozu.medicheck.domain.model.setting.TimetableDisplayOrderType;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType;
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType;
import com.studiojozu.medicheck.domain.model.setting.UseReminderType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * データベースのカラム型を表すクラス
 */
public enum ColumnPattern {
    MEDICINE_DATE_NUMBER(SqliteTypeNamePattern.Integer, DateNumberType.class),
    MEDICINE_IS_ONE_SHOT(SqliteTypeNamePattern.Integer, IsOneShotType.class),
    MEDICINE_ID(SqliteTypeNamePattern.Text, MedicineIdType.class),
    MEDICINE_NAME(SqliteTypeNamePattern.Text, MedicineNameType.class),
    MEDICINE_PHOTO(SqliteTypeNamePattern.Text, MedicinePhotoType.class),
    MEDICINE_START_DATETIME(SqliteTypeNamePattern.Integer, StartDatetimeType.class),
    MEDICINE_TAKE_INTERVAL_MODE(SqliteTypeNamePattern.Integer, TakeIntervalModeType.class),
    MEDICINE_TAKE_INTERVAL(SqliteTypeNamePattern.Integer, TakeIntervalType.class),
    MEDICINE_TAKE_NUMBER(SqliteTypeNamePattern.Integer, TakeNumberType.class),

    PARSON_ID(SqliteTypeNamePattern.Text, PersonIdType.class),
    PARSON_NAME(SqliteTypeNamePattern.Text, PersonNameType.class),
    PARSON_PHOTO(SqliteTypeNamePattern.Text, PersonPhotoType.class),

    SCHEDULE_IS_TAKE(SqliteTypeNamePattern.Integer, IsTakeType.class),
    SCHEDULE_NEED_ALARM(SqliteTypeNamePattern.Integer, NeedAlarmType.class),
    SCHEDULE_PLAN_DATE(SqliteTypeNamePattern.Integer, PlanDateType.class),
    SCHEDULE_TOOK_DATETIME(SqliteTypeNamePattern.Integer, TookDatetime.class),

    REMIND_INTERVAL(SqliteTypeNamePattern.Integer, RemindIntervalType.class),
    REMIND_TIMEOUT(SqliteTypeNamePattern.Integer, RemindTimeoutType.class),
    USE_REMIND(SqliteTypeNamePattern.Integer, UseReminderType.class),

    TIMETABLE_ID(SqliteTypeNamePattern.Text, TimetableIdType.class),
    TIMETABLE_NAME(SqliteTypeNamePattern.Text, TimetableNameType.class),
    TIMETABLE_TIME(SqliteTypeNamePattern.Integer, TimetableTimeType.class),
    TIMETABLE_DISPLAY_ORDER(SqliteTypeNamePattern.Integer, TimetableDisplayOrderType.class);

    @NonNull
    private final SqliteTypeNamePattern mSqliteTypeNamePattern;
    @NonNull
    private final Class<? extends ADbType> mTypeClass;

    ColumnPattern(@NonNull SqliteTypeNamePattern sqliteTypeNamePattern, @NonNull Class<? extends ADbType> typeClass) {
        mSqliteTypeNamePattern = sqliteTypeNamePattern;
        mTypeClass = typeClass;
    }

    @NonNull
    String getSqliteTypeNamePattern() {
        return mSqliteTypeNamePattern.getTypeName();
    }

    @NonNull
    public ADbType createNewInstance(Object argsValue) {
        try {
            Class[] types = {Object.class};
            Constructor constructor = mTypeClass.getConstructor(types);

            Object[] args = {argsValue};
            return (ADbType) constructor.newInstance(args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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

enum SqliteTypeNamePattern {
    Integer("integer"),
    Text("text"),
    Real("real"),
    Blob("blob");

    @NonNull
    private final String mTypeName;

    SqliteTypeNamePattern(@NonNull String typeName) {
        mTypeName = typeName;
    }

    @NonNull
    public String getTypeName() {
        return mTypeName;
    }
}

