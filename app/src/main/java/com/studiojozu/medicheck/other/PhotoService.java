package com.studiojozu.medicheck.other;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.image.Gallery;
import com.studiojozu.medicheck.log.Log;

import java.io.File;
import java.io.IOException;

/**
 * 写真に関するクラス
 */
public class PhotoService {

    public static final int REQUEST_CAMERA_IMAGE = 0;
    public static final int REQUEST_GALLERY_IMAGE = 1;

    @NonNull
    private final Activity mActivity;
    @NonNull
    private final Log mLog;
    @Nullable
    private Uri mUri = null;

    public PhotoService(@NonNull Activity activity) {
        mActivity = activity;
        mLog = new Log(PhotoService.class);
    }

    public PhotoService(@NonNull Activity activity, String uriString) {
        this(activity);
        mUri = Uri.parse(uriString);
    }

    /**
     * 写真をカメラ撮影により取得する。
     * <p>
     * このメソッド呼出し後、カメラアプリが起動する。
     * カメラアプリ終了後、コンストラクタで指定したActivityの{@link Activity#onActivityResult(int, int, Intent)}が呼び出される。
     * そのActivityの{@link Activity#onActivityResult(int, int, Intent)}で、{@link #onResponse(int, int, Intent)}を呼び出すように記述すると、撮影したイメージがファイル保存される。
     * 保存されたファイルパスは、{@link #onResponse(int, int, Intent)}を呼び出したのと同じインスタンスで{@link #getFilepath()}を使用して取得する。
     */
    public void captureFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.startActivityForResult(intent, REQUEST_CAMERA_IMAGE);
    }

    /**
     * 写真をギャラリーより取得する。
     * <p>
     * このメソッド呼出し後、ギャラリーアプリが起動する。
     * ギャラリーアプリ終了後、コンストラクタで指定したActivityの{@link Activity#onActivityResult(int, int, Intent)}が呼び出される。
     * そのActivityの{@link Activity#onActivityResult(int, int, Intent)}で、{@link #onResponse(int, int, Intent)}を呼び出すように記述すると、選択したイメージがファイル保存される。
     * 保存されたファイルパスは、{@link #onResponse(int, int, Intent)}を呼び出したのと同じインスタンスで{@link #getFilepath()}を使用して取得する。
     */
    public void captureFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, REQUEST_GALLERY_IMAGE);
    }

    /**
     * {@link #captureFromCamera()} もしくは {@link #captureFromGallery()}を呼び出すことで発火するActivityの{@link Activity#onActivityResult(int, int, Intent)}で呼び出されることを想定したメソッド。
     * <p>
     * {@link #captureFromCamera()} もしくは {@link #captureFromGallery()}で起動したアプリ終了後、コンストラクタで指定したActivityの{@link Activity#onActivityResult(int, int, Intent)}が呼び出される。
     * そのActivityの{@link Activity#onActivityResult(int, int, Intent)}で、{@link #onResponse(int, int, Intent)}を呼び出すように記述する
     *
     * @param requestCode 要求コード
     * @param resultCode  結果コード
     * @param data        選択した写真データ
     * @return 処理成否
     */
    public boolean onResponse(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) return false;
        if (requestCode == REQUEST_CAMERA_IMAGE) return onResponseCameraImage(resultCode, data);
        if (requestCode == REQUEST_GALLERY_IMAGE) return onResponseGalleryImage(resultCode, data);

        return false;
    }

    /**
     * ギャラリーアプリからのレスポンスを処理するためのメソッド
     *
     * @param resultCode 結果コード
     * @param data       選択した写真データ
     * @return 処理成否
     */
    private boolean onResponseGalleryImage(int resultCode, @NonNull Intent data) {
        if (resultCode != Activity.RESULT_OK) return false;

        mUri = data.getData();
        return true;
    }

    /**
     * カメラアプリからのレスポンスを処理するためのメソッド
     *
     * @param resultCode 結果コード
     * @param data       選択した写真データ
     * @return 処理成否
     */
    private boolean onResponseCameraImage(int resultCode, @NonNull Intent data) {
        if (resultCode != Activity.RESULT_OK) return false;

        try {
            Bitmap bitmapData = (Bitmap) data.getExtras().get("data");
            mUri = new Gallery(mActivity).register(bitmapData);
            return (mUri != null);
        } catch (IOException e) {
            mLog.e(e);
            return false;
        }
    }

    /**
     * インスタンスが保持しているイメージファイルのパスを返却する。
     *
     * @return イメージファイルのパス
     */
    @Nullable
    public File getFilepath() {
        if (mUri == null) return null;
        return new File(mUri.getPath());
    }

}
