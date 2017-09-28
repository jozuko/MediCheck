package com.studiojozu.medicheck.resource.uicomponent.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.resource.uicomponent.ACustomView;

public class TakeIntervalInputView extends ACustomView<TakeIntervalInputView> {

    @NonNull
    private final CheckBox mEveryDayCheckBox;
    @NonNull
    private final CheckBox mEveryFewDaysCheckBox;
    @NonNull
    private final CheckBox mEveryMonthCheckBox;
    @NonNull
    private final EditText mEveryFewDaysTextView;
    @NonNull
    private final EditText mEveryMonthTextView;

    /**
     * ダイアログとしてカレンダーを生成するときに使用するコンストラクタ
     *
     * @param context アプリケーションコンテキスト
     */
    public TakeIntervalInputView(@NonNull Context context) {
        this(context, null);
    }

    public TakeIntervalInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mEveryDayCheckBox = mCustomView.findViewById(R.id.interval_every_day_checkbox);
        mEveryFewDaysCheckBox = mCustomView.findViewById(R.id.interval_every_few_days_checkbox);
        mEveryMonthCheckBox = mCustomView.findViewById(R.id.interval_every_month_checkbox);
        mEveryFewDaysTextView = mCustomView.findViewById(R.id.interval_every_few_days_text_view);
        mEveryMonthTextView = mCustomView.findViewById(R.id.interval_every_month_text_view);

        setEventListener();
        showDefaultValue();
    }

    private void showDefaultValue() {
        mEveryDayCheckBox.callOnClick();
        mEveryFewDaysTextView.setText("1");
        mEveryMonthTextView.setText("1");
    }

    public void showMedicineData(@NonNull Medicine medicine) {
        long interval = medicine.getTakeInterval().getDbValue();
        if (medicine.getTakeIntervalMode().isDays()) {
            if (interval < 1) {
                mEveryDayCheckBox.callOnClick();
                return;
            }
            mEveryFewDaysCheckBox.callOnClick();
            mEveryFewDaysTextView.setText(String.valueOf(interval));
            return;
        }
        mEveryMonthCheckBox.callOnClick();
        mEveryMonthTextView.setText(String.valueOf(interval));
    }

    @Override
    protected int layoutResource() {
        return R.layout.take_interval_input;
    }

    @Nullable
    @Override
    protected int[] styleableResources() {
        return null;
    }

    private void setEventListener() {
        setCheckBoxClickListener();
        setEveryFewDaysTouchListener();
        setEveryMonthTouchListener();
    }

    private void setCheckBoxClickListener() {
        View.OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(view instanceof CheckBox)) return;

                mEveryDayCheckBox.setChecked(view.equals(mEveryDayCheckBox));
                mEveryFewDaysCheckBox.setChecked(view.equals(mEveryFewDaysCheckBox));
                mEveryMonthCheckBox.setChecked(view.equals(mEveryMonthCheckBox));
            }
        };

        mEveryDayCheckBox.setOnClickListener(onClickListener);
        mEveryFewDaysCheckBox.setOnClickListener(onClickListener);
        mEveryMonthCheckBox.setOnClickListener(onClickListener);
    }

    @NonNull
    public TakeIntervalModeType.DateIntervalPattern getIntervalPattern() {
        if (mEveryDayCheckBox.isChecked() || mEveryFewDaysCheckBox.isChecked()) return TakeIntervalModeType.DateIntervalPattern.DAYS;
        return TakeIntervalModeType.DateIntervalPattern.MONTH;
    }

    @NonNull
    public String getInterval() {
        if (mEveryDayCheckBox.isChecked()) return "0";
        if (mEveryFewDaysCheckBox.isChecked()) return mEveryFewDaysTextView.getText().toString();
        return mEveryMonthTextView.getText().toString();
    }

    private void setEveryFewDaysTouchListener() {
        mEveryFewDaysTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    mEveryFewDaysCheckBox.callOnClick();
                return false;
            }
        });
    }

    private void setEveryMonthTouchListener() {
        mEveryMonthTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    mEveryMonthCheckBox.callOnClick();
                return false;
            }
        });
    }
}
