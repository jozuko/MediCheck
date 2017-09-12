package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.domain.model.setting.TimetableDisplayOrderType;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType;
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType;

import java.util.Map;

/**
 * DBのレコードから{@link Timetable}を生成する
 */
class SqliteTimetableConverter extends ASqliteConverter<Timetable> {

    SqliteTimetableConverter(@Nullable Map<ColumnBase, ADbType> databaseRecord) {
        super(databaseRecord);
    }

    @Override
    @Nullable
    Timetable createFromRecord() {
        if (mDatabaseRecord == null) return null;

        return new Timetable(
                getTimetableIdType(),
                getTimetableNameType(),
                getTimetableTimeType(),
                getTimetableDisplayOrderType()
        );
    }

    private TimetableIdType getTimetableIdType() {
        TimetableIdType timetableIdType = (TimetableIdType) getData(SqliteTimetableRepository.COLUMN_ID);
        if (timetableIdType != null) return timetableIdType;
        return new TimetableIdType();
    }

    private TimetableNameType getTimetableNameType() {
        TimetableNameType timetableNameType = (TimetableNameType) getData(SqliteTimetableRepository.COLUMN_NAME);
        if (timetableNameType != null) return timetableNameType;
        return new TimetableNameType();
    }

    private TimetableTimeType getTimetableTimeType() {
        TimetableTimeType timetableNameType = (TimetableTimeType) getData(SqliteTimetableRepository.COLUMN_TIME);
        if (timetableNameType != null) return timetableNameType;
        return new TimetableTimeType();
    }

    private TimetableDisplayOrderType getTimetableDisplayOrderType() {
        TimetableDisplayOrderType timetableDisplayOrderType = (TimetableDisplayOrderType) getData(SqliteTimetableRepository.COLUMN_DISPLAY_ORDER);
        if (timetableDisplayOrderType != null) return timetableDisplayOrderType;
        return new TimetableDisplayOrderType();
    }
}
