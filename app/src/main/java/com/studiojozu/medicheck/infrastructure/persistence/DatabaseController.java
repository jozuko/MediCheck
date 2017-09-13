package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
abstract class DatabaseController {

    private void execSQL(@NonNull Context context, @Nullable String sql) {
        if (sql == null) return;
        if (sql.isEmpty()) return;

        openWritableDatabase(context).execSQL(sql);
    }

    @NonNull
    private ReadonlyDatabase openReadonlyDatabase(@NonNull Context context) {
        return DbOpenHelper.getInstance(context).openReadonlyDatabase();
    }

    @NonNull
    private WritableDatabase openWritableDatabase(@NonNull Context context) {
        return DbOpenHelper.getInstance(context).openWritableDatabase();
    }

    private void closeDatabase(@NonNull Context context) {
        DbOpenHelper.getInstance(context).closeDatabase();
    }

    long insert(@NonNull Context context, @NonNull String tableName, @NonNull Map<ColumnBase, ADbType> dataMap) {
        try {
            ContentValues insertData = new ContentValues();
            for (ColumnBase column : dataMap.keySet()) {
                dataMap.get(column).setContentValue(column.mColumnName, insertData);
            }
            return openWritableDatabase(context).insert(tableName, insertData);
        } finally {
            closeDatabase(context);
        }
    }

    long insert(@NonNull WritableDatabase database, @NonNull String tableName, @NonNull Map<ColumnBase, ADbType> dataMap) {
        ContentValues insertData = new ContentValues();
        for (ColumnBase column : dataMap.keySet()) {
            dataMap.get(column).setContentValue(column.mColumnName, insertData);
        }
        return database.insert(tableName, insertData);
    }

    long update(@NonNull Context context, @NonNull String tableName, @NonNull Map<ColumnBase, ADbType> dataMap, String whereClause, ArrayList<ADbType> whereArgs) {
        try {
            ContentValues values = new ContentValues();
            for (ColumnBase column : dataMap.keySet()) {
                dataMap.get(column).setContentValue(column.mColumnName, values);
            }
            return openWritableDatabase(context).update(tableName, values, whereClause, createWhereArgs(whereArgs));
        } finally {
            closeDatabase(context);
        }
    }

    void delete(@NonNull Context context, @NonNull String tableName, String whereClause, ArrayList<ADbType> whereArgs) {
        try {
            openWritableDatabase(context).delete(tableName, whereClause, createWhereArgs(whereArgs));
        } finally {
            closeDatabase(context);
        }
    }

    void deleteAll(@NonNull Context context, @NonNull String tableName) {
        try {
            execSQL(context, "delete from " + tableName);
        } finally {
            closeDatabase(context);
        }
    }

    @NonNull
    ArrayList<Map<ColumnBase, ADbType>> find(@NonNull Context context, @NonNull String tableName, @NonNull Columns columns, @Nullable String whereClause, @Nullable ArrayList<ADbType> whereArgs, @Nullable String orderBy) {
        final String sql = "select * from " + tableName + " "
                + (whereClause != null ? "where " + whereClause + " " : "")
                + (orderBy != null ? "order by " + orderBy + " " : "");

        return rawQuery(context, columns, sql, whereArgs);
    }

    @NonNull
    private ArrayList<Map<ColumnBase, ADbType>> rawQuery(@NonNull Context context, @NonNull Columns columns, @NonNull String sql, @Nullable ArrayList<ADbType> whereArgs) {
        Cursor cursor = null;
        try {
            cursor = openReadonlyDatabase(context).rawQuery(sql, createWhereArgs(whereArgs));
            return columns.putAllDataList(cursor);
        } finally {
            if (cursor != null) cursor.close();
            closeDatabase(context);
        }
    }

    /**
     * where句のパラメータの型変換を行う
     *
     * @param whereArgs where句のパラメータ
     * @return where句のパラメータのString配列
     */
    @Nullable
    @Contract("null -> null")
    private String[] createWhereArgs(@Nullable ArrayList<ADbType> whereArgs) {
        if (whereArgs == null || whereArgs.isEmpty()) return null;

        ArrayList<String> args = new ArrayList<>();
        for (ADbType model : whereArgs) {
            args.add(model.getDbWhereValue());
        }

        return args.toArray(new String[0]);
    }
}
