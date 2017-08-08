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
    private final Context _context;
    @NonNull
    private final ImageView _imageView;
    @NonNull
    private final Uri _uri;
    @Nullable
    private Bitmap _bitmap;

    public BitmapViewComponent(@NonNull Context context, @NonNull ImageView imageView, @NonNull Uri uri) {
        _context = context;
        _imageView = imageView;
        _uri = uri;
    }

    /**
     * Activity#onCreate()で呼び出すと必ず0が返却される。
     * Activity#onCreate()以降のライフサイクルでよびだすか、Activity#onWindowFocusChanged(boolean)で実施すること
     *
     * @return ImageViewの幅と高さ
     */
    public Size getImageSize() {
        return new Size(_imageView.getWidth(), _imageView.getHeight());
    }

    /**
     * ImageViewに画像を表示する
     * @param bitmap 表示するBitmapイメージ
     */
    public void setBitmap(@NonNull Bitmap bitmap) {
        recycle();

        _bitmap = bitmap;
        _imageView.setImageBitmap(_bitmap);
    }

    /**
     * リサイクル処理
     * setBitmap()を呼び出したライフサイクルと対になる箇所で呼び出すこと
     */
    public void recycle() {
        if (_bitmap == null) return;

        _imageView.setImageDrawable(null);
        _bitmap.recycle();
        _bitmap = null;
    }
}
