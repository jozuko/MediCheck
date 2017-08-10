package com.studiojozu.medicheck.database.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.ADatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.IDbType;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * テーブル定義の基底クラス
 * <p>
 * Created by jozuko on 2017/04/25.
 */
public abstract class ABaseEntity {

    public enum ColumnType {
        TEXT("text"),
        INT("integer"),
        DATE("integer"),
        TIME("integer"),
        DATETIME("integer"),
        BOOL("integer"),
        REMIND_INTERVAL("integer"),
        REMIND_TIMEOUT("integer");

        @NonNull
        private final String _typeName;

        ColumnType(@NonNull String typeName) {
            _typeName = typeName;
        }

        String getTypeName() {
            return _typeName;
        }
    }

    enum NullType {
        NotNull,
        Nullable;
    }

    enum PrimayType {
        Primary,
        NotPrimary
    }

    enum AutoIncrementType {
        AutoIncrement,
        NotAutoIncrement
    }

    static String TABLE_NAME;
    static Columns COLUMNS;

    private String getCreateTableSQL(){
        return "create table " + TABLE_NAME
                + " ("
                + COLUMNS.getColumnDefinition()
                + COLUMNS.getCreatePrimarySql()
                + ");";
    }

    protected abstract void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db);

    protected abstract String getUpgradeSQL(int oldVersion, int newVersion);

    protected abstract void updateUpgradeData(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion);

    /**
     * CREATE TABLEを行う
     *
     * @param context アプリケーションコンテキスト
     * @param db      書き込み可能データベース接続
     */
    void createTable(@NonNull Context context, @Nullable WritableDatabase db) {
        execSQL(db, getCreateTableSQL());
        updateDefaultData(context, db);
    }

    /**
     * バージョンが上がった際のUPDATE TABLEを行う
     *
     * @param context    アプリケーションコンテキスト
     * @param db         書き込み可能データベース接続
     * @param oldVersion 旧バージョンNo
     * @param newVersion 新バージョンNo
     */
    void upgradeTable(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        if (!isNewVersion(oldVersion, newVersion)) return;

        execSQL(db, getUpgradeSQL(oldVersion, newVersion));
        updateUpgradeData(context, db, oldVersion, newVersion);
    }

    @Contract(pure = true)
    private boolean isNewVersion(int oldVersion, int newVersion) {
        if (newVersion == 1) return false;
        return (oldVersion < newVersion);
    }

    private void execSQL(@Nullable WritableDatabase db, @Nullable String sql) {
        if (db == null) return;
        if (sql == null) return;
        if (sql.isEmpty()) return;

        db.execSQL(sql);
    }

    void insert(@NonNull WritableDatabase db, @NonNull Map<ColumnBase, IDbType> dataMap){
        ContentValues insertData = new ContentValues();
        for(ColumnBase column : dataMap.keySet()){
            dataMap.get(column).setContentValue(column._columnName, insertData);
        }

        db.save(TABLE_NAME, insertData);
    }

    List<Map<ColumnBase, IDbType>> findEntities(ADatabase database, String where, ArrayList<IDbType> whereArgs) {
        Cursor cursor = null;
        try {
            final String sql = "select * from " + TABLE_NAME + " " + where;
            cursor = database.rawQuery(sql, createWhereArgs(whereArgs));
            return COLUMNS.putAllDatas(cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Nullable
    @Contract("null -> null")
    private String[] createWhereArgs(@Nullable ArrayList<IDbType> whereArgs) {
        if (whereArgs == null || whereArgs.isEmpty()) return null;

        ArrayList<String> args = new ArrayList<>();
        for (IDbType model : whereArgs) {
            args.add(model.getDbWhereValue());
        }

        return args.toArray(new String[0]);
    }}
