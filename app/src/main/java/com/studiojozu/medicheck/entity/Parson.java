package com.studiojozu.medicheck.entity;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.repository.ParsonRepository;
import com.studiojozu.medicheck.type.parson.ParsonIdType;
import com.studiojozu.medicheck.type.parson.ParsonNameType;
import com.studiojozu.medicheck.type.parson.ParsonPhotoType;

import org.jetbrains.annotations.NotNull;

/**
 * 飲む人を画面から受信し、DBに保存する。また、DBのデータを画面表示用に加工する。
 */
public class Parson {

    /** 飲む人ID */
    @NonNull
    private final ParsonIdType mParsonId;

    /** 飲む人の名前 */
    @NotNull
    private ParsonNameType mParsonNameType;

    /** 写真 */
    @NotNull
    private ParsonPhotoType mPhotoPath;

    /**
     * DB登録前のインスタンスを生成するためのコンストラクタ
     *
     * @param name      飲む人の名前
     * @param photoPath 飲む人の写真パス
     */
    public Parson(String name, String photoPath) {
        mParsonId = new ParsonIdType();
        mParsonNameType = new ParsonNameType(name);
        mPhotoPath = new ParsonPhotoType(photoPath);
    }

    /**
     * DB登録済み（IDがわかっている）インスタンスを生成するためのコンストラクタ
     *
     * @param parsonIdType 飲む人ID
     * @param parsonName   飲む人名前
     * @param parsonPhoto  飲む人写真パス
     */
    public Parson(@NonNull ParsonIdType parsonIdType, @NonNull ParsonNameType parsonName, @NonNull ParsonPhotoType parsonPhoto) {
        mParsonId = parsonIdType.clone();
        mParsonNameType = parsonName.clone();
        mPhotoPath = parsonPhoto.clone();
    }

    /**
     * 飲む人の名前を変更する。
     *
     * @param name 飲む人の名前
     */
    public void setParsonName(@NotNull String name) {
        mParsonNameType = new ParsonNameType(name);
    }

    /**
     * 飲む人の写真パスを変更する。
     *
     * @param photoPath 飲む人の写真パス
     */
    public void setPhotoPath(@NonNull String photoPath) {
        mPhotoPath = new ParsonPhotoType(photoPath);
    }

    public String getDisplayParsonName() {
        return mParsonNameType.getDbValue();
    }

    public Uri getPhotoUri() {
        return mPhotoPath.getPhotoUri();
    }

    /**
     * フィールドに指定した値でストレージへの保存を行う
     *
     * @param context アプリケーションコンテキスト
     */
    public void save(@NonNull Context context) {
        ParsonRepository parsonRepository = new ParsonRepository();
        parsonRepository.save(context.getApplicationContext(), mParsonId, mParsonNameType, mPhotoPath);
    }

    /**
     * {@link #mParsonId}に一致する飲む人とそれに付随する薬、スケジュールも削除する。
     *
     * @param context アプリケーションコンテキスト
     */
    public void delete(@NonNull Context context) {
        ParsonRepository parsonRepository = new ParsonRepository();
        parsonRepository.delete(context.getApplicationContext(), mParsonId);
    }

}
