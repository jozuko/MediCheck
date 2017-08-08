package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * Parson
 * <ol>
 * <li>name 名前</li>
 * <li>photo 写真</li>
 * </ol>
 */
public class ParsonEntity extends ABaseEntity {

    private static final String TABLE_NAME = "parson";

    private static final String CREATE_TABLE_SQL
            = "create table " + TABLE_NAME
            + " ("
            + " _id   integer not null autoincrement"   // ID
            + ",name  text    not null"   // 名前
            + ",photo text    not null"   // Photo URI string
            + ",primary key(_id)"
            + ");";

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        db.execSQL("insert into " + TABLE_NAME + " (name, photo) values ('" + context.getResources().getString(R.string.parson_self) + "','')");
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
