package com.studiojozu.medicheck.port.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * ギャラリーに関する処理を行うクラス
 */
public class Gallery {

    @NonNull
    private final Context mContext;

    public Gallery(@NonNull Context context) {
        mContext = context;
    }

    /**
     * Galleryにイメージファイルを登録する
     *
     * @param imageFile イメージファイルのファイルインスタンス
     * @return ギャラリー登録先URI
     */
    public Uri register(@NonNull File imageFile) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.TITLE, imageFile.getName());

        ContentResolver contentResolver = mContext.getContentResolver();
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
