package com.studiojozu.medicheck.domain.model.person;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * 飲む人を画面から受信し、DBに保存する。また、DBのデータを画面表示用に加工する。
 */
public class Person implements Serializable {
    private static final long serialVersionUID = -4752297475972711988L;

    /** 飲む人ID */
    @NonNull
    private final PersonIdType mPersonId;

    /** 飲む人の名前 */
    @NotNull
    private PersonNameType mPersonName;

    /** 写真 */
    @NotNull
    private PersonPhotoType mPhotoPath;

    /** 表示順 */
    @NotNull
    private PersonDisplayOrderType mPersonDisplayOrder;

    public Person() {
        mPersonId = new PersonIdType();
        mPersonName = new PersonNameType();
        mPhotoPath = new PersonPhotoType();
        mPersonDisplayOrder = new PersonDisplayOrderType(-1);
    }

    public Person(String name, String photoPath) {
        mPersonId = new PersonIdType();
        mPersonName = new PersonNameType(name);
        mPhotoPath = new PersonPhotoType(photoPath);
        mPersonDisplayOrder = new PersonDisplayOrderType(-1);
    }

    public Person(@NonNull PersonIdType personIdType, @NonNull PersonNameType personName, @NonNull PersonPhotoType personPhoto, @NonNull PersonDisplayOrderType personDisplayOrder) {
        mPersonId = personIdType;
        mPersonName = personName;
        mPhotoPath = personPhoto;
        mPersonDisplayOrder = personDisplayOrder;
    }

    @NonNull
    public String getDisplayPersonName() {
        return mPersonName.getDbValue();
    }

    @Nullable
    public Uri getPhotoUri() {
        return mPhotoPath.getPhotoUri();
    }

    @NonNull
    public PersonIdType getPersonId() {
        return mPersonId;
    }

    @NonNull
    public PersonNameType getPersonName() {
        return mPersonName;
    }

    @NotNull
    public PersonPhotoType getPersonPhoto() {
        return mPhotoPath;
    }

    @NotNull
    public PersonDisplayOrderType getPersonDisplayOrder() {
        return mPersonDisplayOrder;
    }

    public void setDisplayOrder(int personDisplayOrder) {
        mPersonDisplayOrder = new PersonDisplayOrderType(personDisplayOrder);
    }
}
