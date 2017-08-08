package com.studiojozu.medicheck.image;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * ギャラリーに関する処理を行うクラス
 */
public class Gallery {

    private final Context _context;

    public Gallery(@NonNull Context context) {
        _context = context;
    }

    /**
     * Bitmapをギャラリーに追加する
     *
     * @param bitmap Bitmapを保持するクラス
     * @return 保存先URI
     * @throws IOException ファイル保存時の例外
     */
    @Nullable
    public Uri register(@Nullable Bitmap bitmap) throws IOException {
        if (bitmap == null) return null;

        File imageFile = new BitmapWrite(_context, bitmap).saveToNewPngFileAutoRecycle();
        registerGallery(imageFile, "image/png");
        return Uri.fromFile(imageFile);
    }

    /**
     * Galleryにイメージファイルを登録する
     *
     * @param imageFile
     */
    private void registerGallery(@NonNull File imageFile, @NonNull String mimeType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put("_data", imageFile.getAbsolutePath());

        ContentResolver contentResolver = _context.getContentResolver();
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

}
