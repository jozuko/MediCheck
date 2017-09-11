package com.studiojozu.medicheck.infrastructure.persistence;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.common.port.adapter.DbTypeFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1テーブル分のカラム定義をまとめたオブジェクト
 */
class Columns {

    @NonNull
    private final List<ColumnBase> mColumns;

    Columns(@NonNull List<ColumnBase> columns) {
        mColumns = columns;
    }

    /**
     * CreateTableで使用するカラム定義文字列を取得する
     *
     * @return カラム定義文字列
     */
    @NonNull
    String getColumnDefinition() {
        StringBuilder builder = new StringBuilder();
        for (ColumnBase column : mColumns) {
            if (builder.length() > 0) builder.append(",");

            builder.append(column.mColumnName).append(" ");
            builder.append(column.mColumnType.getTypeName()).append(" ");

            if (column.mAutoIncrementType == AutoIncrementPattern.AutoIncrement) {
                builder.append("primary key autoincrement");
                continue;
            }

            builder.append((column.mNullType == NullPattern.NotNull ? "not null" : "")).append(" ");
        }

        return builder.toString();
    }

    /**
     * CreateTableで使用するPrimaryKey生成文を取得する
     *
     * @return PrimaryKey生成文
     */
    @NonNull
    String getCreatePrimarySql() {
        StringBuilder builder = new StringBuilder();
        for (ColumnBase column : mColumns) {
            if (column.mAutoIncrementType == AutoIncrementPattern.AutoIncrement) return "";
            if (column.mPrimaryType != PrimaryPattern.Primary) continue;

            if (builder.length() > 0) builder.append(",");
            builder.append(column.mColumnName);
        }

        if (builder.length() == 0) return "";
        return ",primary key(" + builder.toString() + ")";
    }

    /**
     * Cursorを次に進めて、1レコード分のデータを取得する
     *
     * @param cursor DBのQueryCursor
     * @return 1レコード分のデータ
     */
    @NonNull
    private Map<ColumnBase, ADbType> putAllData(@NonNull Cursor cursor) {

        Map<ColumnBase, ADbType> dataMap = new HashMap<>();
        if (cursor.isClosed()) return dataMap;

        for (ColumnBase column : mColumns) {
            if (column.mColumnType.getTypeName().equalsIgnoreCase("integer"))
                dataMap.put(column, DbTypeFactory.createInstance(column.mColumnType, cursor.getLong(cursor.getColumnIndex(column.mColumnName))));

            if (column.mColumnType.getTypeName().equalsIgnoreCase("text"))
                dataMap.put(column, DbTypeFactory.createInstance(column.mColumnType, cursor.getString(cursor.getColumnIndex(column.mColumnName))));

            if (column.mColumnType.getTypeName().equalsIgnoreCase("real"))
                dataMap.put(column, DbTypeFactory.createInstance(column.mColumnType, cursor.getDouble(cursor.getColumnIndex(column.mColumnName))));

            if (column.mColumnType.getTypeName().equalsIgnoreCase("blob"))
                dataMap.put(column, DbTypeFactory.createInstance(column.mColumnType, cursor.getBlob(cursor.getColumnIndex(column.mColumnName))));
        }

        return dataMap;
    }

    /**
     * Cursorにある全レコード分のデータを取得する
     *
     * @param cursor DBのQueryCursor
     * @return 全レコード分のデータ
     */
    @NonNull
    ArrayList<Map<ColumnBase, ADbType>> putAllDataList(@NonNull Cursor cursor) {

        ArrayList<Map<ColumnBase, ADbType>> entities = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            Map<ColumnBase, ADbType> dataMap = putAllData(cursor);
            entities.add(dataMap);
        }
        return entities;
    }
}
