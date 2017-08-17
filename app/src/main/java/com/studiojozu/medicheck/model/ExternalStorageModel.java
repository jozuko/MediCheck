package com.studiojozu.medicheck.model;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;

/**
 * 外部ストレージを扱う型クラス
 */
public class ExternalStorageModel {

    @NonNull
    private final Context mContext;

    public ExternalStorageModel(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 外部ストレージがマウントされているかをチェックする
     *
     * @return マウントされている場合はtrueを返却する
     */
    public static boolean isReadyExternalStorage() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    /**
     * 外部ストレージ上に新規PNGファイルを作成する。
     * ファイル名は現在時刻のlong値とするため、論理上重複はないが、万が一あった場合は削除して、作成する。
     *
     * @return 新規作成したファイルインスタンス
     * @throws IOException ファイルIO系例外
     */
    public File createNewImageFile() throws IOException {
        File imageFile = new File(getImageDir(), String.format("%d.png", System.currentTimeMillis()));
        if (imageFile.exists()) imageFile.delete();

        imageFile.createNewFile();
        return imageFile;
    }

    /**
     * 外部ストレージのカメラ画像保存先ディレクトリパスを取得する。
     *
     * @return カメラ画像保存先ディレクトリパス
     */
    private File getImageDir() {
        File rootDir = mContext.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if (!rootDir.exists()) rootDir.mkdirs();

        return rootDir;
    }

}
