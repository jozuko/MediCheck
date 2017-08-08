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
    private final Context _context;

    public ExternalStorageModel(Context context) {
        _context = context.getApplicationContext();
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

    public File createNewImageFile() throws IOException {
        File imageFile = new File(getImageDir(), String.format("%d.png", System.currentTimeMillis()));
        imageFile.createNewFile();

        return imageFile;
    }

    private File getImageDir() {
        File rootDir = _context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if (!rootDir.exists()) rootDir.mkdirs();

        return rootDir;
    }

}
