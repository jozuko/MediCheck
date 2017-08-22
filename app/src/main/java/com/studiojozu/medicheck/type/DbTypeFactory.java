package com.studiojozu.medicheck.type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.repository.ColumnPattern;
import com.studiojozu.medicheck.type.medicine.DateNumberType;
import com.studiojozu.medicheck.type.medicine.IsOneShotType;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.medicine.MedicineNameType;
import com.studiojozu.medicheck.type.medicine.MedicinePhotoType;
import com.studiojozu.medicheck.type.medicine.StartDatetimeType;
import com.studiojozu.medicheck.type.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.type.medicine.TakeIntervalType;
import com.studiojozu.medicheck.type.medicine.TakeNumberType;
import com.studiojozu.medicheck.type.parson.ParsonIdType;
import com.studiojozu.medicheck.type.parson.ParsonNameType;
import com.studiojozu.medicheck.type.parson.ParsonPhotoType;
import com.studiojozu.medicheck.type.schedule.IsTakeType;
import com.studiojozu.medicheck.type.schedule.NeedAlarmType;
import com.studiojozu.medicheck.type.schedule.PlanDateType;
import com.studiojozu.medicheck.type.schedule.TookDatetime;
import com.studiojozu.medicheck.type.setting.RemindIntervalType;
import com.studiojozu.medicheck.type.setting.RemindTimeoutType;
import com.studiojozu.medicheck.type.setting.UseReminderType;
import com.studiojozu.medicheck.type.timetable.TimetableIdType;
import com.studiojozu.medicheck.type.timetable.TimetableNameType;
import com.studiojozu.medicheck.type.timetable.TimetableTimeType;

import org.jetbrains.annotations.NotNull;

/**
 * {@link ADbType}の実態クラスを生成するFactory
 */
public class DbTypeFactory {

    /**
     * {@link ColumnPattern} を元にして、{@link ADbType}の実態クラスを生成する
     *
     * @param columnType カラムタイプ
     * @param value      値
     * @return {@link ADbType}の実態クラスのインスタンス
     */
    @NonNull
    public static ADbType createInstance(@NotNull ColumnPattern columnType, @NotNull Object value) {
        // Parsonテーブルのカラムインスタンスを生成
        ADbType parsonType = createParsonInstance(columnType, value);
        if (parsonType != null) return parsonType;

        // Medicineテーブルのカラムインスタンスを生成
        ADbType medicineType = createMedicineInstance(columnType, value);
        if (medicineType != null) return medicineType;

        ADbType scheduleType = createScheduleInstance(columnType, value);
        if (scheduleType != null) return scheduleType;

        // Timetableテーブルのカラムインスタンスを生成
        ADbType timetableType = createTimetableInstance(columnType, value);
        if (timetableType != null) return timetableType;

        // Settingテーブルのカラムインスタンスを生成
        ADbType settingType = createSettingInstance(columnType, value);
        if (settingType != null) return settingType;

        throw new IllegalArgumentException("unknown type:" + columnType.toString());
    }

    @Nullable
    private static ADbType createParsonInstance(@NotNull ColumnPattern columnType, @NotNull Object value) {
        if (columnType == ColumnPattern.PARSON_ID) return new ParsonIdType(value);
        if (columnType == ColumnPattern.PARSON_NAME) return new ParsonNameType(value);
        if (columnType == ColumnPattern.PARSON_PHOTO) return new ParsonPhotoType(value);

        return null;
    }

    @Nullable
    private static ADbType createMedicineInstance(@NotNull ColumnPattern columnType, @NotNull Object value) {
        if (columnType == ColumnPattern.MEDICINE_DATE_NUMBER) return new DateNumberType(value);
        if (columnType == ColumnPattern.MEDICINE_IS_ONE_SHOT) return new IsOneShotType(value);
        if (columnType == ColumnPattern.MEDICINE_ID) return new MedicineIdType(value);
        if (columnType == ColumnPattern.MEDICINE_NAME) return new MedicineNameType(value);
        if (columnType == ColumnPattern.MEDICINE_PHOTO) return new MedicinePhotoType(value);
        if (columnType == ColumnPattern.MEDICINE_START_DATETIME) return new StartDatetimeType(value);
        if (columnType == ColumnPattern.MEDICINE_TAKE_INTERVAL_MODE) return new TakeIntervalModeType(value);
        if (columnType == ColumnPattern.MEDICINE_TAKE_INTERVAL) return new TakeIntervalType(value);
        if (columnType == ColumnPattern.MEDICINE_TAKE_NUMBER) return new TakeNumberType(value);

        return null;
    }

    @Nullable
    private static ADbType createScheduleInstance(@NotNull ColumnPattern columnType, @NotNull Object value) {
        if (columnType == ColumnPattern.SCHEDULE_IS_TAKE) return new IsTakeType(value);
        if (columnType == ColumnPattern.SCHEDULE_NEED_ALARM) return new NeedAlarmType(value);
        if (columnType == ColumnPattern.SCHEDULE_PLAN_DATE) return new PlanDateType(value);
        if (columnType == ColumnPattern.SCHEDULE_TOOK_DATETIME) return new TookDatetime(value);

        return null;
    }

    @Nullable
    private static ADbType createTimetableInstance(@NotNull ColumnPattern columnType, @NotNull Object value) {
        if (columnType == ColumnPattern.TIMETABLE_ID) return new TimetableIdType(value);
        if (columnType == ColumnPattern.TIMETABLE_NAME) return new TimetableNameType(value);
        if (columnType == ColumnPattern.TIMETABLE_TIME) return new TimetableTimeType(value);

        return null;
    }

    @Nullable
    private static ADbType createSettingInstance(@NotNull ColumnPattern columnType, @NotNull Object value) {
        if (columnType == ColumnPattern.REMIND_INTERVAL) return new RemindIntervalType(value);
        if (columnType == ColumnPattern.REMIND_TIMEOUT) return new RemindTimeoutType(value);
        if (columnType == ColumnPattern.USE_REMIND) return new UseReminderType(value);

        return null;
    }
}
