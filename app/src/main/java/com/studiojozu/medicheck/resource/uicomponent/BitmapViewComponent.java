package com.studiojozu.medicheck.resource.uicomponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.studiojozu.common.log.Log;
import com.studiojozu.medicheck.application.BitmapReadService;

import org.jetbrains.annotations.Contract;

import java.io.IOException;

/**
 * Bitmapイメージを表示するImageView
 */
public class BitmapViewComponent {
    @NonNull
    private final Context mContext;
    private final int mDefaultResourceId;
    @NonNull
    private final ImageView mImageView;
    @NonNull
    private Log mLog = new Log(BitmapViewComponent.class);
    @Nullable
    private Bitmap mBitmap;

    public BitmapViewComponent(@NonNull Context context, @NonNull ImageView imageView, int defaultResourceId) {
        mContext = context;
        mDefaultResourceId = defaultResourceId;
        mImageView = imageView;
        mImageView.setImageResource(defaultResourceId);
    }

    /**
     * Activity#onCreate()で呼び出すと必ず0が返却される。
     * Activity#onCreate()以降のライフサイクルでよびだすか、Activity#onWindowFocusChanged(boolean)で実施すること
     *
     * @return ImageViewの幅と高さ
     */
    @NonNull
    private Size getImageSize() {
        return new Size(mImageView.getWidth(), mImageView.getHeight());
    }

    /**
     * ImageViewに画像を表示する。
     * ImageViewのサイズに合わせたBitmapイメージを読み込むためには、onCreate以降のライフサイクルで実行する必要がある。
     *
     * @param uri 表示するBitmapのパス
     */
    public void showBitmap(@Nullable Uri uri) {
        recycle();

        mBitmap = getBitmap(uri);
        if (mBitmap == null) {
            showDefaultResource();
            return;
        }

        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageBitmap(mBitmap);
    }

    /**
     * {@link #mDefaultResourceId}に指定されたリソースを表示する。
     * {@link #mDefaultResourceId}が0未満の場合は、イメージViewのVisibilityをGONEにする。
     */
    private void showDefaultResource() {
        if (mDefaultResourceId < 0) {
            mImageView.setVisibility(View.GONE);
            return;
        }

        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageResource(mDefaultResourceId);
    }

    /**
     * URIからBitmapを取得する
     *
     * @param uri Bitmapのパス
     * @return Bitmapインスタンス
     */
    @Contract("null -> null")
    @Nullable
    private Bitmap getBitmap(@Nullable Uri uri) {
        if (uri == null) return null;
        BitmapReadService bitmapRead = new BitmapReadService(mContext, uri);
        Size imageViewSize = getImageSize();
        try {
            return bitmapRead.readResizedBitmap(imageViewSize);
        } catch (IOException e) {
            mLog.e(e);
            return null;
        }
    }

    /**
     * リサイクル処理
     * {@link #showBitmap(Uri)}を呼び出したライフサイクルと対になる箇所で呼び出すこと
     */
    public void recycle() {
        if (mBitmap == null) return;

        mImageView.setImageDrawable(null);
        mBitmap.recycle();
        mBitmap = null;
    }
}
