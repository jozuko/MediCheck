package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Map;

/**
 * テーブル定義の基底クラス
 * <p>
 * Created by jozuko on 2017/04/25.
 */
abstract class ABaseRepository extends DatabaseController {

    abstract void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase db);

    abstract String getUpgradeSQL(int oldVersion, int newVersion);

    abstract void updateUpgradeData(@NonNull Context context, @Nullable SQLiteDatabase db, int oldVersion, int newVersion);

    abstract String getTableName();

    abstract Columns getColumns();

    String getCreateTableSQL() {
        return "create table " + getTableName()
                + " ("
                + getColumns().getColumnDefinition()
                + getColumns().getCreatePrimarySql()
                + ");";
    }

    /**
     * CREATE TABLEを行う
     *
     * @param context アプリケーションコンテキスト
     * @param db      書き込み可能データベース接続
     */
    void createTable(@NonNull Context context, @Nullable SQLiteDatabase db) {
        if (db != null)
            db.execSQL(getCreateTableSQL());
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
    void upgradeTable(@NonNull Context context, @Nullable SQLiteDatabase db, int oldVersion, int newVersion) {
        if (!isNewVersion(oldVersion, newVersion)) return;

        if (db != null)
            db.execSQL(getUpgradeSQL(oldVersion, newVersion));

        updateUpgradeData(context, db, oldVersion, newVersion);
    }

    /**
     * DBのバージョンがUPしているかをチェックする
     *
     * @param oldVersion 旧バージョンコード
     * @param newVersion 新バージョンコード
     * @return バージョンUPしている場合はtrueを返却する
     */
    @Contract(pure = true)
    private boolean isNewVersion(int oldVersion, int newVersion) {
        return newVersion != 1 && (oldVersion < newVersion);
    }

    long insert(@NonNull Context context, @NonNull Map<ColumnBase, ADbType> dataMap) {
        return insert(context, getTableName(), dataMap);
    }

    long insert(@NonNull SQLiteDatabase database, @NonNull Map<ColumnBase, ADbType> dataMap) {
        return insert(new WritableDatabase(database), getTableName(), dataMap);
    }

    long update(@NonNull Context context, @NonNull Map<ColumnBase, ADbType> dataMap, String whereClause, ArrayList<ADbType> whereArgs) {
        return update(context, getTableName(), dataMap, whereClause, whereArgs);
    }

    void delete(@NonNull Context context, String whereClause, ArrayList<ADbType> whereArgs) {
        delete(context, getTableName(), whereClause, whereArgs);
    }

    void deleteAll(@NonNull Context context) {
        deleteAll(context, getTableName());
    }

    @NonNull
    ArrayList<Map<ColumnBase, ADbType>> find(@NonNull Context context, @Nullable String whereClause, @Nullable ArrayList<ADbType> whereArgs, @Nullable String orderBy) {
        return find(context, getTableName(), getColumns(), whereClause, whereArgs, orderBy);
    }
}
