package com.studiojozu.medicheck.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.port.adapter.ExternalStorageModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Bitmapイメージをファイルに出力するクラス
 */
public class BitmapWriteService {
    @NonNull
    private final Context mContext;

    /**
     * Bitmapイメージ
     * recycleの際に,nullを設定できるようにNullableとしている
     */
    @Nullable
    private Bitmap mBitmap = null;

    /**
     * パラメータをフィールドに設定するコンストラクタ
     *
     * @param context アプリケーションコンテキスト
     * @param bitmap  Bitmapイメージ
     */
    public BitmapWriteService(@NonNull Context context, @NonNull Bitmap bitmap) {
        mContext = context;
        mBitmap = bitmap;
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
        if (mBitmap == null) return null;
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
        if (mBitmap == null) return null;

        FileOutputStream fileOutputStream = null;
        try {
            File imageFile = new ExternalStorageModel(mContext).createNewImageFile();
            fileOutputStream = new FileOutputStream(imageFile);

            if (mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream))
                return imageFile;

            return null;

        } finally {
            if (fileOutputStream != null) fileOutputStream.close();
        }
    }

    /**
     * Bitmapイメージをメモリから解放する
     */
    private void recycle() {
        if (mBitmap != null) mBitmap.recycle();
        mBitmap = null;
    }
}
