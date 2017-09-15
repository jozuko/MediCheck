package com.studiojozu.medicheck.resource.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.application.AlarmScheduleService;
import com.studiojozu.medicheck.domain.model.alarm.AlarmSchedule;

import java.util.List;

/**
 * アラームクラス
 */
class MedicineAlarm {

    private static final int NOTIFICATION_MEDICINE = 1;
    @NonNull
    private final Context mContext;
    @NonNull
    private final NotificationManager mNotificationManager;
    @NonNull
    private final AlarmScheduleService mAlarmScheduleService = new AlarmScheduleService();


    MedicineAlarm(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * データベースに登録されているスケジュールから、アラームが必要なスケジュールを抽出し、スケジュール設定する。
     */
    void showNotification() {
        List<AlarmSchedule> alarmTargetSchedules = mAlarmScheduleService.getNeedAlarmSchedules(mContext);
        if (alarmTargetSchedules.size() == 0) return;

        // 通知を生成する
        Notification notification = createNotification(alarmTargetSchedules);
        mNotificationManager.cancelAll();
        mNotificationManager.notify(NOTIFICATION_MEDICINE, notification);
    }

    /**
     * 直ちに通知を行うNotificationを生成する。
     *
     * @param targetSchedules アラーム対象スケジュール
     * @return Notificationインスタンス
     */
    @Nullable
    @SuppressWarnings("deprecation")
    private Notification createNotification(@NonNull List<AlarmSchedule> targetSchedules) {

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
    private String getNotificationMessage(@NonNull List<AlarmSchedule> targetSchedules) {

        StringBuilder builder = new StringBuilder();
        for (AlarmSchedule targetSchedule : targetSchedules) {
            builder.append(targetSchedule.getPersonName());
            builder.append(" ");
            builder.append(targetSchedule.getMedicineName());
            builder.append("\n");
        }

        return builder.toString();
    }
}
