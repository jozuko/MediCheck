package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * 薬テーブル
 * <ol>
 * <li>name 薬名</li>
 * <li>take_number 服用数</li>
 * <li>photo 薬の写真URI</li>
 * </ol>
 */
public class MedicineEntity extends ABaseEntity {

    private static final String TABLE_NAME = "medichine";

    private static final String CREATE_TABLE_SQL
            = "create table " + TABLE_NAME
            + " ("
            + " _id         integer not null autoincrement"   // ID
            + ",name        text    not null"   // 名前
            + ",take_number integer not null"   // 服用数
            + ",photo       text    not null"   // 薬の写真
            + ",primary key(_id)"
            + ");";

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        // do nothing.
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        // do nothing.
    }
}
