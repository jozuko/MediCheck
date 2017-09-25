package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;

/**
 * 日付選択ダイアログ
 */
public class DatePickerDialogView extends ADialogView<DatePicker> {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    @Nullable
    private View.OnClickListener mClientOnCancelClickListener = null;

    public DatePickerDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, new DatePicker(context));

        LAYOUT_PARAMS.gravity = Gravity.CENTER;
        initTargetView(LAYOUT_PARAMS, true, true);
    }

    public void showDatePickerDialog(int year, int month, int dayOfMonth, final OnDateSelectedListener onDateSelectedListener) {
        mDialogTargetView.init(year, month, dayOfMonth, null);

        setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDateSelectedListener != null)
                    onDateSelectedListener.onDateSelected(mDialogTargetView.getYear(), mDialogTargetView.getMonth(), mDialogTargetView.getDayOfMonth());

                closeDialog();
            }
        });

        showDialog();
    }

    public interface OnDateSelectedListener {
        void onDateSelected(final int year, final int monthOfYear, final int dayOfMonth);
    }
}
