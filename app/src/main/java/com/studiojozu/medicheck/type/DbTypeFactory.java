package com.studiojozu.medicheck.type;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.database.repository.ColumnPattern;
import com.studiojozu.medicheck.type.setting.RemindIntervalType;
import com.studiojozu.medicheck.type.setting.RemindTimeoutType;
import com.studiojozu.medicheck.type.setting.UseReminderType;

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
    public static ADbType createInstance(ColumnPattern columnType, Object value) {
        if (columnType == ColumnPattern.ID) return new IdType((int) value);

        if (columnType == ColumnPattern.DATE) return new DateType((Long) value);
        if (columnType == ColumnPattern.TIME) return new TimeType((Long) value);
        if (columnType == ColumnPattern.DATETIME) return new DateTimeType((Long) value);

        if (columnType == ColumnPattern.USE_REMIND) return new UseReminderType((int) value);
        if (columnType == ColumnPattern.REMIND_INTERVAL) return new RemindIntervalType((int) value);
        if (columnType == ColumnPattern.REMIND_TIMEOUT) return new RemindTimeoutType((int) value);

        if (columnType == ColumnPattern.INTERVAL) return new IntervalType((int) value);
        if (columnType == ColumnPattern.INTERVAL_TYPE) return new DateIntervalType((int) value);

        if (columnType == ColumnPattern.BOOL) return new BooleanType((Boolean) value);
        if (columnType == ColumnPattern.INT) return new IntType((Integer) value);
        if (columnType == ColumnPattern.TEXT) return new TextType((String) value);

        throw new IllegalArgumentException("unknown type:" + columnType.toString());
    }
}
