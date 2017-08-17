package com.studiojozu.medicheck.activity.uicomponent;

/**
 *
 */
public class Size {
    private int mWidth;
    private int mHeight;

    public Size(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public int calcurateResizeRate(int width, int height) {
        if (mWidth == 0 || mHeight == 0) return 0;
        if (width == 0 || height == 0) return 0;

        // 縮小不要の場合は1を返却する
        if (width < mWidth && height < mHeight) return 1;

        // 縦横それぞれ何倍なのかを取得する
        int widthRate = Math.round((float) width / (float) mWidth);
        int heightRate = Math.round((float) height / (float) mHeight);

        // 大きい方を採用する
        if (widthRate < heightRate) return heightRate;
        return widthRate;
    }
}
