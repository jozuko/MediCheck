package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.DbTypeFactory;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.IntModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnType.INT, AutoIncrementType.AutoIncrement);
    /**
     * 名前
     */
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnType.TEXT);
    /**
     * 写真
     */
    public static final ColumnBase COLUMN_PHOTO = new ColumnBase("photo", ColumnType.TEXT);

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
        Map<ColumnBase, ADbType> insertData = new HashMap<>();

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

    /**
     * 飲む人IDに一致するレコードを取得する
     *
     * @param context  アプリケーションコンテキスト
     * @param parsonId 飲む人ID
     * @return 飲む人IDに一致するレコード
     */
    @Nullable
    public Map<ColumnBase, ADbType> findById(@NonNull Context context, @NonNull IntModel parsonId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(parsonId);

            List<Map<ColumnBase, ADbType>> datas = findEntities(readonlyDatabase, COLUMN_ID.getEqualsCondition(), whereList);
            if (datas == null || datas.size() == 0) return null;
            return datas.get(0);
        } finally {
            readonlyDatabase.close();
        }
    }
}
