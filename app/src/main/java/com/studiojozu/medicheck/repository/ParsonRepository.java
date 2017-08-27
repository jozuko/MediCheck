package com.studiojozu.medicheck.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.DbTypeFactory;
import com.studiojozu.medicheck.type.general.PhotoType;
import com.studiojozu.medicheck.type.parson.ParsonIdType;
import com.studiojozu.medicheck.type.parson.ParsonNameType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 薬を飲む人テーブル
 * <ol>
 * <li>name 名前</li>
 * <li>photo 写真</li>
 * </ol>
 */
public class ParsonRepository extends ABaseRepository {
    /** 飲む人ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_ID = new ColumnBase("_id", ColumnPattern.PARSON_ID, AutoIncrementPattern.AutoIncrement);
    /** 名前 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NAME = new ColumnBase("name", ColumnPattern.PARSON_NAME);
    /** 写真 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PHOTO = new ColumnBase("photo", ColumnPattern.PARSON_PHOTO);

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
        if (db == null) return;
        Map<ColumnBase, ADbType> insertData = new HashMap<>();

        insertData.put(COLUMN_NAME, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, context.getResources().getString(R.string.parson_self)));
        insertData.put(COLUMN_PHOTO, DbTypeFactory.createInstance(COLUMN_NAME.mColumnType, ""));
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
    public Map<ColumnBase, ADbType> findById(@NonNull Context context, @NonNull ParsonIdType parsonId) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(parsonId);

            List<Map<ColumnBase, ADbType>> dataList = find(readonlyDatabase, COLUMN_ID.getEqualsCondition(), whereList);
            if (dataList.size() == 0) return null;
            return dataList.get(0);
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * DBのテーブルに値を追加、もしくは、既存レコードの更新を行う。
     *
     * @param context    アプリケーションコンテキスト
     * @param id         飲む人ID
     * @param parsonName 飲む人の名前
     * @param photoPath  飲む人の写真
     */
    public void save(@NonNull Context context, @NonNull ParsonIdType id, @NonNull ParsonNameType parsonName, @NonNull PhotoType photoPath) {
        Map<ColumnBase, ADbType> parsons = findById(context, id);

        if (parsons == null || parsons.size() == 0) {
            insert(context, parsonName, photoPath);
            return;
        }

        update(context, id, parsonName, photoPath);
    }

    /**
     * DBへの行追加を行う
     *
     * @param context    アプリケーションコンテキスト
     * @param parsonName 飲む人の名前
     * @param photoPath  飲む人の写真
     */
    private void insert(@NonNull Context context, @NonNull ParsonNameType parsonName, @NonNull PhotoType photoPath) {
        WritableDatabase database = new WritableDatabase(context);
        try {
            database.beginTransaction();

            Map<ColumnBase, ADbType> insertData = new HashMap<>();
            insertData.put(COLUMN_NAME, parsonName);
            insertData.put(COLUMN_PHOTO, photoPath);
            insert(database, insertData);

            database.commitTransaction();
        } catch (Exception e) {
            database.rollbackTransaction();
            throw e;
        } finally {
            database.close();
        }
    }

    /**
     * DBへの行更新を行う
     *
     * @param context    アプリケーションコンテキスト
     * @param id         飲む人ID
     * @param parsonName 飲む人の名前
     * @param photoPath  飲む人の写真
     */
    private void update(@NonNull Context context, @NonNull ParsonIdType id, @NonNull ParsonNameType parsonName, @NonNull PhotoType photoPath) {
        WritableDatabase database = new WritableDatabase(context);
        try {
            database.beginTransaction();

            Map<ColumnBase, ADbType> values = new HashMap<>();
            values.put(COLUMN_NAME, parsonName);
            values.put(COLUMN_PHOTO, photoPath);

            String whereClause = COLUMN_ID.getEqualsCondition();

            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(id);

            update(database, values, whereClause, whereList);
            database.commitTransaction();
        } catch (Exception e) {
            database.rollbackTransaction();
            throw e;
        } finally {
            database.close();
        }
    }

    public void delete(@NonNull Context context, @NonNull ParsonIdType id) {
        // 飲む人の情報を取得する
        Map<ColumnBase, ADbType> parsons = findById(context, id);
        if (parsons == null || parsons.size() == 0) return;

        WritableDatabase database = new WritableDatabase(context);
        try {
            database.beginTransaction();

            // 飲む人テーブルから該当IDを削除
            String whereClause = COLUMN_ID.getEqualsCondition();
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(id);
            delete(database, whereClause, whereList);

            // 飲む人-薬Relationテーブルから該当IDを削除
            new ParsonMediRelationRepository().deleteByParsonId(database, id);

            database.commitTransaction();
        } catch (Exception e) {
            database.rollbackTransaction();
            throw e;
        } finally {
            database.close();
        }
    }

}
