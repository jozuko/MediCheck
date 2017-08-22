package com.studiojozu.medicheck.other;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.database.repository.SettingRepository;
import com.studiojozu.medicheck.type.setting.RemindIntervalType;
import com.studiojozu.medicheck.type.setting.RemindTimeoutType;
import com.studiojozu.medicheck.type.setting.UseReminderType;

import java.util.TreeMap;

/**
 * 設定を画面が受信し、DBに保存する。また、DBのデータを画面表示用に加工する。
 */
public class Setting {

    /** リマンダ機能を使用するか？ */
    @NonNull
    private UseReminderType mUseReminder = new UseReminderType(true);
    /** リマンダ機能のインターバル時間 */
    @NonNull
    private RemindIntervalType mRemindInterval = new RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5);
    /** リマンダ機能のタイムアウト時間 */
    @NonNull
    private RemindTimeoutType mRemindTimeout = new RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24);

    /**
     * リマンダ機能を使用するかを設定する
     *
     * @param useReminder リマンダ機能を使用する場合はtrueを設定する
     */
    public void setUseReminder(boolean useReminder) {
        mUseReminder = new UseReminderType(useReminder);
    }

    /**
     * リマンダ機能のタイムアウト時間を設定する
     *
     * @param timeoutMinute タイムアウト時間（分）.
     *                      選択できる時間は、{@link #getRemindTimeoutMap(Context)} で取得する
     */
    public void setRemindTimeout(int timeoutMinute) {
        mRemindTimeout = new RemindTimeoutType(timeoutMinute);
    }

    /**
     * リマンダ機能のインターバルを設定する
     *
     * @param intervalMinute リマンダ機能のインターバル(分).
     *                       選択できる時間は、{@link #getRemindIntervalMap(Context)} で取得する
     */
    public void setRemindInterval(int intervalMinute) {
        mRemindInterval = new RemindIntervalType(intervalMinute);
    }

    /**
     * フィールドに指定した値でストレージへの保存を行う
     */
    public void save(@NonNull Context context) {
        SettingRepository settingRepository = new SettingRepository();
        settingRepository.save(context.getApplicationContext(), mUseReminder, mRemindInterval, mRemindTimeout);
    }

    /**
     * リマインド機能のタイムアウト時間として、選択可能な時間(分)と対応する文言を返却する。
     *
     * @return リマインド機能のタイムアウト時間一覧
     */
    @NonNull
    public TreeMap<Integer, String> getRemindTimeoutMap(@NonNull Context context) {
        return RemindTimeoutType.getAllValues(context.getApplicationContext());
    }

    /**
     * リマインド機能のインターバルとして、選択可能な時間(分)と対応する文言を返却する。
     *
     * @return リマインド機能のインターバル一覧
     */
    public TreeMap<Integer, String> getRemindIntervalMap(@NonNull Context context) {
        return RemindIntervalType.getAllValues(context.getApplicationContext());
    }


}
