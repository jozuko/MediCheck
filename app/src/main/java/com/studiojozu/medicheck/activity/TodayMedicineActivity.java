package com.studiojozu.medicheck.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.activity.uicomponent.ImageSpinnerAdapter;

/**
 * 今日のお薬を一覧表示する。
 */
public class TodayMedicineActivity extends Activity {

    /** 飲む人Spinnerに使用するAdapter */
    @Nullable
    private ImageSpinnerAdapter mImageSpinnerAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_today_medicine);

        createImageSpinnerAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        try {
            if (mImageSpinnerAdapter != null) mImageSpinnerAdapter.recycle();
        } finally {
            super.onDestroy();
        }
    }

    private void createImageSpinnerAdapter() {
        mImageSpinnerAdapter = new ImageSpinnerAdapter(this.getApplicationContext(), R.mipmap.parson_no_image);
    }

}
