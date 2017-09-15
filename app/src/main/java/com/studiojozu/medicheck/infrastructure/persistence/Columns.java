package com.studiojozu.medicheck.infrastructure.persistence;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;

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
            builder.append(column.mColumnType.getSqliteTypeNamePattern()).append(" ");
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

        for (ColumnBase columnBase : mColumns) {
            Object value = getValue(columnBase, cursor);
            dataMap.put(columnBase, columnBase.mColumnType.createNewInstance(value));
        }

        return dataMap;
    }

    @Nullable
    private Object getValue(@NonNull ColumnBase columnBase, @NonNull Cursor cursor) {
        if (columnBase.mColumnType.getSqliteTypeNamePattern().equalsIgnoreCase("integer"))
            return cursor.getLong(cursor.getColumnIndex(columnBase.mColumnName));

        if (columnBase.mColumnType.getSqliteTypeNamePattern().equalsIgnoreCase("text"))
            return cursor.getString(cursor.getColumnIndex(columnBase.mColumnName));

        if (columnBase.mColumnType.getSqliteTypeNamePattern().equalsIgnoreCase("real"))
            return cursor.getDouble(cursor.getColumnIndex(columnBase.mColumnName));

        if (columnBase.mColumnType.getSqliteTypeNamePattern().equalsIgnoreCase("blob"))
            return cursor.getBlob(cursor.getColumnIndex(columnBase.mColumnName));

        return null;
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
