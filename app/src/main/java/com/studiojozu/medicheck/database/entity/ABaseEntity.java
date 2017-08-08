package com.studiojozu.medicheck.database.entity;

import android.database.Cursor;

import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * テーブル定義の基底クラス
 * <p>
 * Created by jozuko on 2017/04/25.
 */
public abstract class ABaseEntity {

    protected abstract String getCreateTableSQL();

    protected abstract void updateDefaultData(WritableDatabase db);

    protected abstract String getUpgradeSQL(int oldVersion, int newVersion);

    protected abstract void updateUpgradeData(WritableDatabase db, int oldVersion, int newVersion);

    /**
     * CREATE TABLEを行う
     *
     * @param db 書き込み可能データベース接続
     */
    void createTable(WritableDatabase db) {
        execSQL(db, getCreateTableSQL());
        updateDefaultData(db);
    }

    /**
     * バージョンが上がった際のUPDATE TABLEを行う
     *
     * @param db         書き込み可能データベース接続
     * @param oldVersion 旧バージョンNo
     * @param newVersion 新バージョンNo
     */
    void upgradeTable(WritableDatabase db, int oldVersion, int newVersion) {
        if (!isNewVersion(oldVersion, newVersion)) return;

        execSQL(db, getUpgradeSQL(oldVersion, newVersion));
        updateUpgradeData(db, oldVersion, newVersion);
    }

    /**
     * カラムの値が1の場合にtrueを返却する
     *
     * @param cursor     カーソル
     * @param columnName カラム名
     * @return カラムの値が1の場合にtrue
     */
    boolean getBoolean(Cursor cursor, String columnName) {
        int temp = cursor.getInt(cursor.getColumnIndex(columnName));
        return (temp == 1);
    }

    private boolean isNewVersion(int oldVersion, int newVersion) {
        if (newVersion == 1) return false;
        return (oldVersion < newVersion);
    }

    private void execSQL(WritableDatabase db, String sql) {
        if (sql != null && sql.getBytes().length > 0) db.execSQL(sql);
    }
}
