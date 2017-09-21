package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TimePicker;

/**
 * 時刻選択ダイアログ
 */
public class TimePickerDialogView extends ADialogView<TimePicker> {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    @NonNull
    private final TimePicker mTimePicker;

    public TimePickerDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTimePicker = createTimePicker(context);
        LAYOUT_PARAMS.gravity = Gravity.CENTER;
        initTargetView(mTimePicker, LAYOUT_PARAMS, true, true);
    }

    @NonNull
    private TimePicker createTimePicker(@NonNull Context context) {
        return new TimePicker(context);
    }

    @SuppressWarnings("deprecation")
    public void showTimePickerDialog(int hourOfDay, int minute, @Nullable final OnTimeSelectedListener onTimeSelectedListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mTimePicker.setCurrentHour(hourOfDay);
            mTimePicker.setCurrentMinute(minute);
        } else {
            mTimePicker.setHour(hourOfDay);
            mTimePicker.setMinute(minute);
        }

        mTimePicker.setIs24HourView(true);

        setOnOkButtonClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTimeSelectedListener != null) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        onTimeSelectedListener.onTimeChanged(mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
                    } else {
                        onTimeSelectedListener.onTimeChanged(mTimePicker.getHour(), mTimePicker.getMinute());
                    }
                }

                closeDialog();
            }
        });

        showDialog();
    }

    public interface OnTimeSelectedListener {
        void onTimeChanged(final int hourOfDay, final int minute);
    }
}
