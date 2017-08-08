package com.studiojozu.medicheck.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.activity.uicomponent.BitmapViewComponent;
import com.studiojozu.medicheck.activity.uicomponent.Size;

import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmapを扱う型クラス
 */
public class BitmapRead {
    @NonNull
    private final Context _context;
    @NonNull
    private final Uri _uri;

    public BitmapRead(@NonNull Context context, @NonNull Uri uri) {
        _context = context.getApplicationContext();
        _uri = uri;
    }

    /**
     * パレメータに指定されたbitmapViewComponentに対して、{@link #_uri}の指すbitmapを表示する。
     * Activity#onCreate()で呼び出すと必ず何も表示できない。
     * Activity#onCreate()以降のライフサイクルでよびだすか、Activity#onWindowFocusChanged(boolean)で実施すること
     *
     * @param bitmapViewComponent Bitmapを表示するView
     * @throws IOException 読み込み時例外
     */
    private void setImageView(@NonNull BitmapViewComponent bitmapViewComponent) throws IOException {

        BitmapFactory.Options originalSizeOptions = getOriginalSize();
        Size viewSize = bitmapViewComponent.getImageSize();

        int rate = viewSize.calcurateResizeRate(originalSizeOptions.outWidth, originalSizeOptions.outHeight);
        if (rate == 0) return;

        Bitmap bitmap = decodeResizeBitmapFromFile(rate);
        bitmapViewComponent.setBitmap(bitmap);
    }

    /**
     * {@link #_uri}で指定されたBitmapのオリジナルサイズを取得する
     *
     * @return Bitmapのオリジナルサイズ
     * @throws IOException 読み込み例外
     */
    private BitmapFactory.Options getOriginalSize() throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = _context.getContentResolver().openInputStream(_uri);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, /*outPadding*/null, options);

            return options;
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    /**
     * パラメータで指定したサイズ分の1に縮小したBitmapを読み込む
     *
     * @param resizeRate 縮小サイズ（1/〇の〇を指定）
     * @return 縮小したBitmapイメージ
     * @throws IOException 読み込み例外
     */
    private Bitmap decodeResizeBitmapFromFile(int resizeRate) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = _context.getContentResolver().openInputStream(_uri);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = resizeRate;
            return BitmapFactory.decodeStream(inputStream, /*outPadding*/null, options);
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }
}
