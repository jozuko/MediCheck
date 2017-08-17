package com.studiojozu.medicheck.activity.uicomponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Bitmapイメージを表示するImageView
 */
public class BitmapViewComponent {
    @NonNull
    private final Context mContext;
    @NonNull
    private final ImageView mImageView;
    @NonNull
    private final Uri mUri;
    @Nullable
    private Bitmap mBitmap;

    public BitmapViewComponent(@NonNull Context context, @NonNull ImageView imageView, @NonNull Uri uri) {
        mContext = context;
        mImageView = imageView;
        mUri = uri;
    }

    /**
     * Activity#onCreate()で呼び出すと必ず0が返却される。
     * Activity#onCreate()以降のライフサイクルでよびだすか、Activity#onWindowFocusChanged(boolean)で実施すること
     *
     * @return ImageViewの幅と高さ
     */
    public Size getImageSize() {
        return new Size(mImageView.getWidth(), mImageView.getHeight());
    }

    /**
     * ImageViewに画像を表示する
     *
     * @param bitmap 表示するBitmapイメージ
     */
    public void setBitmap(@NonNull Bitmap bitmap) {
        recycle();

        mBitmap = bitmap;
        mImageView.setImageBitmap(mBitmap);
    }

    /**
     * リサイクル処理
     * setBitmap()を呼び出したライフサイクルと対になる箇所で呼び出すこと
     */
    public void recycle() {
        if (mBitmap == null) return;

        mImageView.setImageDrawable(null);
        mBitmap.recycle();
        mBitmap = null;
    }
}
