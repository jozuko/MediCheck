package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.ReadonlyDatabase;
import com.studiojozu.medicheck.database.helper.WritableDatabase;
import com.studiojozu.medicheck.database.type.BooleanModel;
import com.studiojozu.medicheck.database.type.ADbType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Schedule
 * <ol>
 * <li>medicine_id 服用薬ID</li>
 * <li>plan_date 服用予定日付</li>
 * <li>timetable_id 服用予定タイムテーブルID</li>
 * <li>need_alert Alertいる？</li>
 * <li>is_take 服用した？</li>
 * <li>alert_datetime Alertする日時(リマインダ時間ずつ増加する)</li>
 * <li>take_datetime 服用した日時</li>
 * </ol>
 */
public class ScheduleEntity extends ABaseEntity {

    /**
     * 薬ID
     */
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnType.INT, PrimayType.Primary);
    /**
     * 服用予定日付
     */
    public static final ColumnBase COLUMN_PLAN_DATE = new ColumnBase("plan_date", ColumnType.DATE, PrimayType.Primary);
    /**
     * 服用予定タイムテーブルID
     */
    public static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase("timetable_id", ColumnType.INT, PrimayType.Primary);
    /**
     * Alertいる？
     */
    public static final ColumnBase COLUMN_NEED_ALERT = new ColumnBase("need_alert", ColumnType.BOOL);
    /**
     * 服用した？
     */
    public static final ColumnBase COLUMN_IS_TAKE = new ColumnBase("is_take", ColumnType.BOOL);
    /**
     * 服用した日時
     */
    public static final ColumnBase COLUMN_TAKE_DATETIME = new ColumnBase("take_datetime", ColumnType.DATETIME);

    static {
        TABLE_NAME = "schedule";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_MEDICINE_ID);
        columns.add(COLUMN_PLAN_DATE);
        columns.add(COLUMN_TIMETABLE_ID);
        columns.add(COLUMN_NEED_ALERT);
        columns.add(COLUMN_IS_TAKE);
        columns.add(COLUMN_TAKE_DATETIME);
        COLUMNS = new Columns(columns);
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

    /**
     * need_alert=true, is_take=falseの条件を満たすレコードを返却する
     *
     * @param context アプリケーションコンテキスト
     * @return need_alert=true, is_take=falseの条件を満たすレコード一覧
     */
    public List<Map<ColumnBase, ADbType>> getNeedAlerts(Context context) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(new BooleanModel(true));
            whereList.add(new BooleanModel(false));

            return findEntities(readonlyDatabase, COLUMN_NEED_ALERT.getEqualsCondition() + " and " + COLUMN_IS_TAKE.getEqualsCondition(), whereList);
        } finally {
            readonlyDatabase.close();
        }
    }
}
