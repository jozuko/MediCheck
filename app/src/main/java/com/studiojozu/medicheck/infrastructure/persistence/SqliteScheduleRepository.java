package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.schedule.IsTakeType;
import com.studiojozu.medicheck.domain.model.schedule.NeedAlarmType;
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType;
import com.studiojozu.medicheck.domain.model.schedule.Schedule;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleList;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleRepository;
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType;

import java.util.ArrayList;
import java.util.HashMap;
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
public class SqliteScheduleRepository extends ABaseRepository implements ScheduleRepository {
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase(SqliteMedicineRepository.COLUMN_ID, PrimaryPattern.Primary);
    /** 服用予定日付 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PLAN_DATE = new ColumnBase("plan_date", ColumnPattern.SCHEDULE_PLAN_DATE, PrimaryPattern.Primary);
    /** 服用予定タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase(SqliteTimetableRepository.COLUMN_ID, PrimaryPattern.Primary);
    /** Alertいる？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NEED_ALERT = new ColumnBase("need_alert", ColumnPattern.SCHEDULE_NEED_ALARM);
    /** 服用した？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_IS_TAKE = new ColumnBase("is_take", ColumnPattern.SCHEDULE_IS_TAKE);
    /** 服用した日時 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TOOK_DATETIME = new ColumnBase("took_datetime", ColumnPattern.SCHEDULE_TOOK_DATETIME);
    private static final String TABLE_NAME = "schedule";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_MEDICINE_ID);
        columns.add(COLUMN_PLAN_DATE);
        columns.add(COLUMN_TIMETABLE_ID);
        columns.add(COLUMN_NEED_ALERT);
        columns.add(COLUMN_IS_TAKE);
        columns.add(COLUMN_TOOK_DATETIME);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase db) {
        // do nothing.
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing.
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Columns getColumns() {
        return COLUMNS;
    }

    @Override
    @NonNull
    public List<Schedule> getNeedAlerts(@NonNull Context context) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(new NeedAlarmType(true));
        whereList.add(new IsTakeType(false));

        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, COLUMN_NEED_ALERT.getEqualsCondition() + " and " + COLUMN_IS_TAKE.getEqualsCondition(), whereList, null);

        List<Schedule> scheduleList = new ArrayList<>();
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            Schedule schedule = new SqliteScheduleConverter(record).createFromRecord();
            scheduleList.add(schedule);
        }

        return scheduleList;
    }

    @Override
    public void addAll(@NonNull Context context, @NonNull MedicineIdType medicineId, @NonNull ScheduleList scheduleList) {
        // データの削除
        removeNotTookMedicineByMedicineId(context, medicineId);

        // スケジュールを保存する
        addAll(context, scheduleList);
    }

    @Override
    public void removeIgnoreHistory(@NonNull Context context, @NonNull MedicineIdType medicineId) {
        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);

        delete(context, whereClause, whereList);
    }

    private void removeNotTookMedicineByMedicineId(@NonNull Context context, @NonNull MedicineIdType medicineId) {
        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition() + " and " + COLUMN_IS_TAKE.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        whereList.add(new IsTakeType(true));

        delete(context, whereClause, whereList);
    }

    private void addAll(@NonNull Context context, @NonNull ScheduleList scheduleList) {
        for (Schedule schedule : scheduleList) {
            // PrimaryKeyが一致するレコードが登録済みの場合はスキップする
            if (existUniqueData(context, schedule.getMedicineId(), schedule.getPlanDate(), schedule.getTimetableId()))
                continue;

            // 追加データの作成
            Map<ColumnBase, ADbType> insertData = new HashMap<>();
            insertData.put(COLUMN_MEDICINE_ID, schedule.getMedicineId());
            insertData.put(COLUMN_PLAN_DATE, schedule.getPlanDate());
            insertData.put(COLUMN_TIMETABLE_ID, schedule.getTimetableId());
            insertData.put(COLUMN_NEED_ALERT, schedule.getNeedAlarm());
            insertData.put(COLUMN_IS_TAKE, schedule.getIsTake());
            insertData.put(COLUMN_TOOK_DATETIME, schedule.getTookDatetime());

            // レコード追加
            insert(context, insertData);
        }
    }

    private boolean existUniqueData(@NonNull Context context, @NonNull MedicineIdType medicineId, @NonNull PlanDateType planDateType, @NonNull TimetableIdType timetableId) {
        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition() + " and " + COLUMN_PLAN_DATE.getEqualsCondition() + " and " + COLUMN_TIMETABLE_ID.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        whereList.add(planDateType);
        whereList.add(timetableId);

        List<Map<ColumnBase, ADbType>> recordList = find(context, whereClause, whereList, null);
        return (recordList.size() != 0);
    }
}
