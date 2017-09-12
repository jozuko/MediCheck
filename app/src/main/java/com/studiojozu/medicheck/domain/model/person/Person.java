package com.studiojozu.medicheck.domain.model.person;

import android.net.Uri;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

/**
 * 飲む人を画面から受信し、DBに保存する。また、DBのデータを画面表示用に加工する。
 */
public class Person {

    /** 飲む人ID */
    @NonNull
    private final PersonIdType mPersonId;

    /** 飲む人の名前 */
    @NotNull
    private PersonNameType mPersonName;

    /** 写真 */
    @NotNull
    private PersonPhotoType mPhotoPath;

    /**
     * DB登録前のインスタンスを生成するためのコンストラクタ
     *
     * @param name      飲む人の名前
     * @param photoPath 飲む人の写真パス
     */
    public Person(String name, String photoPath) {
        mPersonId = new PersonIdType();
        mPersonName = new PersonNameType(name);
        mPhotoPath = new PersonPhotoType(photoPath);
    }

    /**
     * DB登録済み（IDがわかっている）インスタンスを生成するためのコンストラクタ
     *
     * @param personIdType 飲む人ID
     * @param personName   飲む人名前
     * @param personPhoto  飲む人写真パス
     */
    public Person(@NonNull PersonIdType personIdType, @NonNull PersonNameType personName, @NonNull PersonPhotoType personPhoto) {
        mPersonId = personIdType.clone();
        mPersonName = personName.clone();
        mPhotoPath = personPhoto.clone();
    }

    /**
     * 飲む人の写真パスを変更する。
     *
     * @param photoPath 飲む人の写真パス
     */
    public void setPhotoPath(@NonNull String photoPath) {
        mPhotoPath = new PersonPhotoType(photoPath);
    }

    public String getDisplayPersonName() {
        return mPersonName.getDbValue();
    }

    public Uri getPhotoUri() {
        return mPhotoPath.getPhotoUri();
    }

    public PersonIdType getPersonId() {
        return mPersonId.clone();
    }

    public PersonNameType getPersonName() {
        return mPersonName.clone();
    }

    /**
     * 飲む人の名前を変更する。
     *
     * @param name 飲む人の名前
     */
    public void setPersonName(@NotNull String name) {
        mPersonName = new PersonNameType(name);
    }

    public PersonPhotoType getPersonPhoto() {
        return mPhotoPath.clone();
    }
}
