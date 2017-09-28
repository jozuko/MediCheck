package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalValidator;
import com.studiojozu.medicheck.resource.uicomponent.widget.TakeIntervalInputView;

public class TakeIntervalInputDialogView extends ADialogView<TakeIntervalInputView> {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    @NonNull
    private final InputMethodManager mInputMethodManager;
    @Nullable
    private OnCompletedCorrectInputListener mClientOnCompletedCorrectInputListener = null;

    public TakeIntervalInputDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, new TakeIntervalInputView(context));
        mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        initTargetView(LAYOUT_PARAMS, true, true);
    }

    public void showDialog(@NonNull Medicine medicine) {
        mDialogTargetView.showMedicineData(medicine);
        setOnOkButton();
        setOnCancelButton();
        super.showDialog();
    }

    private void setOnOkButton() {
        setOnOkButtonClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TakeIntervalModeType.DateIntervalPattern dateIntervalPattern = mDialogTargetView.getIntervalPattern();
                String intervalData = mDialogTargetView.getInterval();

                TakeIntervalValidator takeIntervalValidator = new TakeIntervalValidator(dateIntervalPattern);
                int stringRes = takeIntervalValidator.validate(intervalData);
                if (showValidationResult(stringRes)) return;

                if (mClientOnCompletedCorrectInputListener != null)
                    mClientOnCompletedCorrectInputListener.onCompleted(Integer.parseInt(intervalData), dateIntervalPattern);

                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                closeDialog();
            }
        });
    }

    private void setOnCancelButton() {
        setOnCancelButtonClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                closeDialog();
            }
        });
    }

    private boolean showValidationResult(@StringRes int resourceId) {
        if (resourceId < 0) return false;

        setDialogMessage(resourceId);
        setDialogMessageColor(Color.RED);
        return true;
    }

    public void setOnCompletedCorrectInputListener(TakeIntervalInputDialogView.OnCompletedCorrectInputListener listener) {
        mClientOnCompletedCorrectInputListener = listener;
    }

    public interface OnCompletedCorrectInputListener {
        void onCompleted(int interval, TakeIntervalModeType.DateIntervalPattern dateIntervalPattern);
    }
}
