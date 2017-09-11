package com.studiojozu.common.domain.model.general;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * 写真パスを管理するクラス
 */
public class PhotoType extends TextType {

    protected PhotoType() {
        super("");
    }

    protected PhotoType(@Nullable Object value) {
        super(value);
    }

    public Uri getPhotoUri() {
        if (getDbValue().equals("")) return null;

        File photoPath = new File(getDbValue());
        if (!photoPath.exists()) return null;
        if (!photoPath.canRead()) return null;

        return Uri.fromFile(photoPath);
    }
}
