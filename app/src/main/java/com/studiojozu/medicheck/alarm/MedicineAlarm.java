package com.studiojozu.medicheck.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.repository.ColumnBase;
import com.studiojozu.medicheck.database.repository.MedicineRepository;
import com.studiojozu.medicheck.database.repository.ParsonMediRelationRepository;
import com.studiojozu.medicheck.database.repository.ParsonRepository;
import com.studiojozu.medicheck.database.repository.SchedulePlanDateTimeComparator;
import com.studiojozu.medicheck.database.repository.ScheduleRepository;
import com.studiojozu.medicheck.database.repository.SettingRepository;
import com.studiojozu.medicheck.database.repository.TimetableRepository;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.DateType;
import com.studiojozu.medicheck.type.IntType;
import com.studiojozu.medicheck.type.TimeType;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * アラームクラス
 */
class MedicineAlarm {

    /** 薬NotificationのID */
    private static final int NOTIFICATION_MEDICINE = 1;

    /** アプリケーションコンテキスト */
    @NonNull
    private final Context mContext;

    /** NotificationManagerインスタンス */
    @NonNull
    private final NotificationManager mNotificationManager;

    /** タイムテーブルRepository */
    @NonNull
    private final TimetableRepository mTimetableRepository;

    /** 設定Repository */
    @NonNull
    private final SettingRepository mSettingRepository;

    /** 飲む人-薬 RelationRepository */
    @NonNull
    private final ParsonMediRelationRepository mParsonMediRelationRepository;

    /** 薬Repository */
    @NonNull
    private final MedicineRepository mMedicineRepository;

    /** 飲む人Repository */
    @NonNull
    private final ParsonRepository mParsonRepository;

    /** 飲む人Repository */
    @NonNull
    private final ScheduleRepository mScheduleRepository;

    MedicineAlarm(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mTimetableRepository = new TimetableRepository();
        mSettingRepository = new SettingRepository();
        mParsonMediRelationRepository = new ParsonMediRelationRepository();
        mMedicineRepository = new MedicineRepository();
        mParsonRepository = new ParsonRepository();
        mScheduleRepository = new ScheduleRepository();
    }

    /**
     * データベースに登録されているスケジュールから、アラームが必要なスケジュールを抽出し、スケジュール設定する。
     */
    void showNotification() {
        // アラームが必要なスケジュールを取得する。
        List<Map<ColumnBase, ADbType>> needAlarmSchedules = getNeedAlarmSchedules();
        if (needAlarmSchedules.size() == 0) return;

        // アラームが必要なスケジュールを投薬予定時刻順に並べ替える
        TreeSet<Map<ColumnBase, ADbType>> targetSchedules = new TreeSet<>(new SchedulePlanDateTimeComparator(mContext));
        for (Map<ColumnBase, ADbType> scheduleRecord : needAlarmSchedules) {
            if (isNeedAlarm(scheduleRecord)) targetSchedules.add(scheduleRecord);
        }
        if (targetSchedules.size() == 0) return;

        // 通知を生成する
        Notification notification = createNotification(targetSchedules);
        mNotificationManager.cancelAll();
        mNotificationManager.notify(NOTIFICATION_MEDICINE, notification);
    }

    /**
     * アラームが必要で、まだ服用されていないスケジュールを取得する。
     *
     * @return アラームが必要なスケジュール一覧
     */
    @NonNull
    private List<Map<ColumnBase, ADbType>> getNeedAlarmSchedules() {
        return mScheduleRepository.getNeedAlerts(mContext);
    }

    /**
     * スケジュールがアラーム対象であるかをチェックする。
     * 対象条件（OR条件）
     * ・服用予定日時＋服用時分が、現在日時＋現在時分と一致する
     * ・服用予定日時＋服用時分が、現在日時＋現在時分を超過しており、リマンダタイミングと一致する
     *
     * @param scheduleRecord スケジュール
     * @return アラーム対象の場合trueを返却する
     */
    private boolean isNeedAlarm(Map<ColumnBase, ADbType> scheduleRecord) {

        // 現在日時を取得する
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        // 年月日時分が一致する場合は、trueを返却して終了する
        DateType alarmDate = (DateType) scheduleRecord.get(ScheduleRepository.COLUMN_PLAN_DATE);
        TimeType alarmTime = getScheduleTime((Integer) scheduleRecord.get(ScheduleRepository.COLUMN_TIMETABLE_ID).getDbValue());
        if (alarmTime == null) return false;
        if (alarmDate.equalsDate(now) && (alarmTime.equalsTime(now))) return true;

        // リマインダが不要ならAlertも不要
        if (!mSettingRepository.isUseRemind(mContext)) return false;

        // リマインダタイムアウトならAlertは不要
        if (mSettingRepository.isRemindTimeout(mContext, now, alarmDate, alarmTime)) return false;

        // リマインドタイミングならAlertする
        return (mSettingRepository.isRemindTiming(mContext, now, alarmDate, alarmTime));
    }

    /**
     * タイムテーブルRepositoryからIDに一致する時分を取得する
     *
     * @param timetableId タイムテーブルID
     * @return タイムテーブルに登録されている時分
     */
    @Nullable
    private TimeType getScheduleTime(int timetableId) {
        Map<ColumnBase, ADbType> timetable = mTimetableRepository.findTimetable(mContext, timetableId);
        if (timetable == null) return null;

        return (TimeType) timetable.get(TimetableRepository.COLUMN_TIME);
    }

    /**
     * 直ちに通知を行うNotificationを生成する。
     *
     * @param targetSchedules アラーム対象スケジュール
     * @return Notificationインスタンス
     */
    @Nullable
    @SuppressWarnings("deprecation")
    private Notification createNotification(TreeSet<Map<ColumnBase, ADbType>> targetSchedules) {

        String notificationMessage = getNotificationMessage(targetSchedules);
        if (notificationMessage.length() == 0) return null;

        Notification.Builder notificationBuilder = new Notification.Builder(mContext);

        notificationBuilder.setSmallIcon(R.mipmap.notification_icon);
        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.notification_action_icon);
        notificationBuilder.setLargeIcon(largeIcon);

        notificationBuilder.setTicker(mContext.getResources().getString(R.string.notification_medicine_title));
        notificationBuilder.setContentTitle(mContext.getResources().getString(R.string.notification_medicine_title));
        notificationBuilder.setContentText(notificationMessage);

        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        notificationBuilder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return notificationBuilder.getNotification();
        }
        return notificationBuilder.build();
    }

    /**
     * Notificationに表示するメッセージを作成する
     *
     * @param targetSchedules アラームが必要なスケジュール
     * @return Notificationに表示するメッセージ
     */
    @NonNull
    private String getNotificationMessage(TreeSet<Map<ColumnBase, ADbType>> targetSchedules) {

        StringBuilder builder = new StringBuilder();
        for (Map<ColumnBase, ADbType> targetSchedule : targetSchedules) {
            TreeSet<IntType> parsonIds = mParsonMediRelationRepository.findParsonIdsByMedicineId(mContext, (IntType) targetSchedule.get(ScheduleRepository.COLUMN_MEDICINE_ID));
            if (parsonIds == null) continue;

            Map<ColumnBase, ADbType> medicines = mMedicineRepository.findById(mContext, (IntType) targetSchedule.get(ScheduleRepository.COLUMN_MEDICINE_ID));
            if (medicines == null) continue;

            builder.append(createMedicineLine(parsonIds, medicines));
        }

        return builder.toString();
    }

    /**
     * 飲む人＋薬名の行文字列を作成する。
     *
     * @param parsonIds 飲む人ID
     * @param medicines 薬一覧
     * @return 飲む人＋薬名の行文字列
     */
    @NonNull
    private String createMedicineLine(@NonNull TreeSet<IntType> parsonIds, Map<ColumnBase, ADbType> medicines) {
        StringBuilder builder = new StringBuilder();

        for (IntType parsonId : parsonIds) {
            Map<ColumnBase, ADbType> parson = mParsonRepository.findById(mContext, parsonId);
            if (parson == null) continue;

            builder.append(parson.get(ParsonRepository.COLUMN_NAME).getDbValue());
            builder.append(" ");
            builder.append(medicines.get(MedicineRepository.COLUMN_NAME).getDbValue());
            builder.append("\n");
        }

        return builder.toString();
    }
}
