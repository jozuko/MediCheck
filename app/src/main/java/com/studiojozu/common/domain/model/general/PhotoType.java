package com.studiojozu.common.domain.model.general;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * 写真パスを管理するクラス
 */
public abstract class PhotoType<C extends PhotoType<C>> extends TextType<C> {

    private static final long serialVersionUID = 4343775991858053805L;

    protected PhotoType() {
        super("");
    }

    protected PhotoType(@Nullable Object value) {
        super(value);
    }

    @Nullable
    public Uri getPhotoUri() {
        if (getDbValue().equals("")) return null;

        File photoPath = new File(getDbValue());
        if (!photoPath.exists()) return null;
        if (!photoPath.canRead()) return null;

        return Uri.fromFile(photoPath);
    }
}
