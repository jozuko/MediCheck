package com.studiojozu.medicheck.database.entity;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.ADbType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * カラム定義クラス
 */
public class Columns {

    @NonNull
    private final List<ColumnBase> _columns;

    Columns(@NonNull List<ColumnBase> columns) {
        _columns = columns;
    }

    /**
     * CreateTableで使用するカラム定義文字列を取得する
     *
     * @return カラム定義文字列
     */
    String getColumnDefinition() {
        StringBuilder builder = new StringBuilder();
        for (ColumnBase column : _columns) {
            if (builder.length() > 0) builder.append(",");

            builder.append(column._columnName).append(" ");
            builder.append(column._type.getTypeName()).append(" ");
            builder.append((column._nullType == ABaseEntity.NullType.NotNull ? " not null" : "")).append(" ");
            builder.append((column._autoIncrementType == ABaseEntity.AutoIncrementType.AutoIncrement ? " autoincrement" : ""));
        }

        return builder.toString();
    }

    /**
     * CreateTableで使用するPrimaryKey生成文を取得する
     *
     * @return PrimaryKey生成文
     */
    String getCreatePrimarySql() {
        StringBuilder builder = new StringBuilder();
        for (ColumnBase column : _columns) {
            if (column._primayType != ABaseEntity.PrimayType.Primary) continue;

            if (builder.length() > 0) builder.append(",");
            builder.append(column._columnName);
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
    Map<ColumnBase, ADbType> putAllData(@NonNull Cursor cursor) {

        Map<ColumnBase, ADbType> dataMap = new HashMap<>();
        if (cursor.moveToNext()) {
            for (ColumnBase column : _columns) {
                dataMap.put(column, DbTypeFactory.createInstance(column._type, cursor.getInt(cursor.getColumnIndex(column._columnName))));
            }
        }
        return dataMap;
    }

    /**
     * Cursorにある全レコード分のデータを取得する
     *
     * @param cursor DBのQueryCursor
     * @return 全レコード分のデータ
     */
    List<Map<ColumnBase, ADbType>> putAllDatas(@NonNull Cursor cursor) {

        List<Map<ColumnBase, ADbType>> entities = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            Map<ColumnBase, ADbType> dataMap = new HashMap<>();

            for (ColumnBase column : _columns) {
                dataMap.put(column, DbTypeFactory.createInstance(column._type, cursor.getInt(cursor.getColumnIndex(column._columnName))));
            }

            entities.add(dataMap);
        }
        return entities;
    }
}
