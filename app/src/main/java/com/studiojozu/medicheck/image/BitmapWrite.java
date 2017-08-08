package com.studiojozu.medicheck.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.model.ExternalStorageModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Bitmapを扱う型クラス
 */
public class BitmapWrite {
    @NonNull
    private final Context _context;
    @Nullable
    private Bitmap _bitmap = null;

    public BitmapWrite(@NonNull Context context, @NonNull Bitmap bitmap) {
        _context = context;
        _bitmap = bitmap;
    }

    /**
     * BitmapをPNGファイルとして保存する。
     * 本メソッド完了時にBitmapはrecycleされる。
     *
     * @return 保存先ファイルパス
     * @throws IOException ファイル保存時の例外
     */
    @Nullable
    public File saveToNewPngFileAutoRecycle() throws IOException {
        if (_bitmap == null) return null;
        if (!ExternalStorageModel.isReadyExternalStorage()) return null;

        try {
            return saveToNewPngFile();
        } finally {
            recycle();
        }
    }

    /**
     * BitmapをPNGファイルとして保存する。
     * 本メソッド完了時にBitmapはrecycleされる。
     *
     * @return 保存先ファイルパス
     * @throws IOException ファイル保存時の例外
     */
    @Nullable
    private File saveToNewPngFile() throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            File imageFile = new ExternalStorageModel(_context).createNewImageFile();
            fileOutputStream = new FileOutputStream(imageFile);

            if (_bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream))
                return imageFile;

            return null;

        } finally {
            if (fileOutputStream != null) fileOutputStream.close();
        }
    }

    private void recycle() {
        if (_bitmap != null) _bitmap.recycle();
        _bitmap = null;
    }
}
