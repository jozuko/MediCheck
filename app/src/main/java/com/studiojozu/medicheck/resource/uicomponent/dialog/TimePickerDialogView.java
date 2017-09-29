package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import org.jetbrains.annotations.Contract;

/**
 * 時刻選択ダイアログ
 */
public class TimePickerDialogView extends ADialogView<TimePicker> {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public TimePickerDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, new TimePicker(context));

        LAYOUT_PARAMS.gravity = Gravity.CENTER;
        initTargetView(LAYOUT_PARAMS, true, true);
    }

    public void showTimePickerDialog(@StringRes int titleResourceId, int hourOfDay, int minute, @Nullable final OnTimeSelectedListener onTimeSelectedListener) {
        setDialogTitle(titleResourceId);
        setTime(hourOfDay, minute);
        setOkButtonOnClickListener(createOkButtonClickListener(onTimeSelectedListener));
        showDialog();
    }

    @SuppressWarnings("deprecation")
    private void setTime(int hourOfDay, int minute) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mDialogTargetView.setCurrentHour(hourOfDay);
            mDialogTargetView.setCurrentMinute(minute);
        } else {
            mDialogTargetView.setHour(hourOfDay);
            mDialogTargetView.setMinute(minute);
        }

        mDialogTargetView.setIs24HourView(true);
    }

    @Contract("_ -> !null")
    @SuppressWarnings("deprecation")
    private View.OnClickListener createOkButtonClickListener(@Nullable final OnTimeSelectedListener onTimeSelectedListener) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTimeSelectedListener == null) {
                    closeDialog();
                    return;
                }

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    onTimeSelectedListener.onTimeChanged(mDialogTargetView.getCurrentHour(), mDialogTargetView.getCurrentMinute());
                } else {
                    onTimeSelectedListener.onTimeChanged(mDialogTargetView.getHour(), mDialogTargetView.getMinute());
                }

                closeDialog();
            }
        };
    }

    public interface OnTimeSelectedListener {
        void onTimeChanged(final int hourOfDay, final int minute);
    }
}
