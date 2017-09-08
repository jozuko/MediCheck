package com.studiojozu.medicheck.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.type.ADbType;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Map;

/**
 * テーブル定義の基底クラス
 * <p>
 * Created by jozuko on 2017/04/25.
 */
abstract class ABaseRepository {

    private String getCreateTableSQL() {
        return "create table " + getTableName()
                + " ("
                + getColumns().getColumnDefinition()
                + getColumns().getCreatePrimarySql()
                + ");";
    }

    protected abstract void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db);

    protected abstract String getUpgradeSQL(int oldVersion, int newVersion);

    protected abstract void updateUpgradeData(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion);

    protected abstract String getTableName();

    protected abstract Columns getColumns();


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

    /**
     * SQLを実行する
     *
     * @param db  読み込み可能 or 書き込み可能なデータベースインスタンス
     * @param sql 実行するSQL
     */
    private void execSQL(@Nullable WritableDatabase db, @Nullable String sql) {
        if (db == null) return;
        if (sql == null) return;
        if (sql.isEmpty()) return;

        db.execSQL(sql);
    }

    /**
     * データベースへのinsertを行う
     *
     * @param db      書き込み可能なデータベースインスタンス
     * @param dataMap カラム名と値を持つMap
     * @return 追加したレコードの_id値
     */
    long insert(@NonNull WritableDatabase db, @NonNull Map<ColumnBase, ADbType> dataMap) {
        ContentValues insertData = new ContentValues();
        for (ColumnBase column : dataMap.keySet()) {
            dataMap.get(column).setContentValue(column.mColumnName, insertData);
        }

        return db.insert(getTableName(), insertData);
    }

    /**
     * データベースへのupdateを行う
     *
     * @param db          書き込み可能なデータベースインスタンス
     * @param dataMap     カラム名と値を持つMap
     * @param whereClause where句
     * @param whereArgs   whereのパラメータ
     * @return 更新行数
     */
    long update(@NonNull WritableDatabase db, @NonNull Map<ColumnBase, ADbType> dataMap, String whereClause, ArrayList<ADbType> whereArgs) {
        ContentValues values = new ContentValues();
        for (ColumnBase column : dataMap.keySet()) {
            dataMap.get(column).setContentValue(column.mColumnName, values);
        }

        return db.update(getTableName(), values, whereClause, createWhereArgs(whereArgs));
    }

    /**
     * テーブルから条件に一致するレコードを削除する
     *
     * @param db          書き込み可能なデータベースインスタンス
     * @param whereClause where句
     * @param whereArgs   whereのパラメータ
     */
    void delete(@NonNull WritableDatabase db, String whereClause, ArrayList<ADbType> whereArgs) {
        db.delete(getTableName(), whereClause, createWhereArgs(whereArgs));
    }

    /**
     * 対象テーブルのレコードをすべて削除する
     *
     * @param db 書き込み可能なデータベースインスタンス
     */
    void deleteAll(@NonNull WritableDatabase db) {
        db.execSQL("deleteByParsonId from " + getTableName());
    }

    /**
     * データベースの検索を行うメソッド
     *
     * @param database    読み込み可能 or 書き込み可能なデータベースインスタンス
     * @param whereClause where句
     * @param whereArgs   whereのパラメータ
     * @return テーブル検索結果
     */
    @NonNull
    ArrayList<Map<ColumnBase, ADbType>> find(@NonNull ADatabase database, @Nullable String whereClause, @Nullable ArrayList<ADbType> whereArgs) {
        Cursor cursor = null;
        try {
            final String sql = "select * from " + getTableName() + " " + (whereClause != null ? whereClause : "");
            cursor = database.rawQuery(sql, createWhereArgs(whereArgs));
            return getColumns().putAllDataList(cursor);
        } finally {
            if (cursor != null) cursor.close();
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
    String[] createWhereArgs(@Nullable ArrayList<ADbType> whereArgs) {
        if (whereArgs == null || whereArgs.isEmpty()) return null;

        ArrayList<String> args = new ArrayList<>();
        for (ADbType model : whereArgs) {
            args.add(model.getDbWhereValue());
        }

        return args.toArray(new String[0]);
    }
}
