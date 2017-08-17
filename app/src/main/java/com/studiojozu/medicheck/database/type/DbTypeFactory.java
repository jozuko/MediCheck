package com.studiojozu.medicheck.database.type;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.database.entity.ABaseEntity;

/**
 * {@link ADbType}の実態クラスを生成するFactory
 */
public class DbTypeFactory {

    /**
     * {@link com.studiojozu.medicheck.database.entity.ABaseEntity.ColumnType} を元にして、{@link ADbType}の実態クラスを生成する
     *
     * @param columnType カラムタイプ
     * @param value      値
     * @return {@link ADbType}の実態クラスのインスタンス
     */
    @NonNull
    public static ADbType createInstance(ABaseEntity.ColumnType columnType, Object value) {
        if (columnType == ABaseEntity.ColumnType.BOOL) return new BooleanModel((Boolean) value);
        if (columnType == ABaseEntity.ColumnType.DATE) return new DateModel((Long) value);
        if (columnType == ABaseEntity.ColumnType.TIME) return new TimeModel((Long) value);
        if (columnType == ABaseEntity.ColumnType.DATETIME) return new DateTimeModel((Long) value);
        if (columnType == ABaseEntity.ColumnType.INT) return new IntModel((Integer) value);
        if (columnType == ABaseEntity.ColumnType.TEXT) return new TextModel((String) value);
        if (columnType == ABaseEntity.ColumnType.REMIND_INTERVAL) return new RemindIntervalModel((int) value);
        if (columnType == ABaseEntity.ColumnType.REMIND_TIMEOUT) return new RemindTimeoutModel((int) value);
        if (columnType == ABaseEntity.ColumnType.INTERVAL) return new IntervalModel((int) value);

        throw new IllegalArgumentException("unknown type:" + columnType.toString());
    }
}
