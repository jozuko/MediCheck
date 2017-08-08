package com.studiojozu.medicheck.activity.uicomponent;

/**
 *
 */
public class Size {
    private int _width;
    private int _height;

    public Size(int width, int height) {
        _width = width;
        _height = height;
    }

    public int calcurateResizeRate(int width, int height) {
        if(_width == 0 || _height == 0) return 0;
        if(width == 0 || height == 0) return 0;

        // 縮小不要の場合は1を返却する
        if(width < _width && height < _height) return 1;

        // 縦横それぞれ何倍なのかを取得する
        int widthRate = Math.round((float)width / (float)_width);
        int heightRate = Math.round((float)height / (float)_height);

        // 大きい方を採用する
        if(widthRate < heightRate) return heightRate;
        return widthRate;
    }
}
