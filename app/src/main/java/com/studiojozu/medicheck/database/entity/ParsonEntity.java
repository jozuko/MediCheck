package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.IDbType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Parson
 * <ol>
 * <li>name 名前</li>
 * <li>photo 写真</li>
 * </ol>
 */
public class ParsonEntity extends ABaseEntity {
    /**
     * ID
     */
    private static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnType.INT, AutoIncrementType.AutoIncrement);
    /**
     * 名前
     */
    private static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnType.TEXT);
    /**
     * 写真
     */
    private static final ColumnBase COLUMN_PHOTO = new ColumnBase("photo", ColumnType.TEXT);

    static {
        TABLE_NAME = "parson";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_PHOTO);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        Map<ColumnBase, IDbType> insertData = new HashMap<>();

        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME._type, context.getResources().getString(R.string.parson_self)));
        insertData.put(COLUMN_PHOTO, DbTypeFactory.createInstance(COLUMN_NAME._type, ""));
        insert(db, insertData);
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
