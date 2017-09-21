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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studiojozu.common.domain.model.IValidator;

/**
 * 入力ダイアログ
 */
public class InputDialogView extends ADialogView<TextView> {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    @NonNull
    private final InputMethodManager mInputMethodManager;
    @NonNull
    private final EditText mEditText;

    public InputDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        mEditText = new EditText(context);
        initTargetView(mEditText, LAYOUT_PARAMS, true, true);
    }

    public void showInputDialog(@StringRes int titleResourceId, @Nullable final IValidator inputValidation, @Nullable final OnCompletedCorrectInputListener listener) {
        if (titleResourceId >= 0)
            setDialogTitle(titleResourceId);

        setOnOkButton(inputValidation, listener);
        setOnCancelButton();

        showDialog();
        showSoftwareKeyboard();
    }

    private void setOnOkButton(@Nullable final IValidator inputValidation, @Nullable final OnCompletedCorrectInputListener listener) {
        setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = mEditText.getText().toString();

                // check validation
                int validationResult = validateData(inputValidation, data);
                if (showValidationResult(validationResult)) return;

                // 呼び出し元にコールバック
                if (listener != null)
                    listener.onCompleted(data);

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

    private void showSoftwareKeyboard() {
        mEditText.requestFocus();
        mInputMethodManager.showSoftInput(mEditText, 0);
    }

    @StringRes
    private int validateData(@Nullable final IValidator validator, @NonNull String data) {
        if (validator == null) return -1;
        return validator.validate(data);
    }

    private boolean showValidationResult(@StringRes int resourceId) {
        if (resourceId < 0) return false;

        setDialogMessage(resourceId);
        setDialogMessageColor(Color.RED);
        return true;
    }

    public interface OnCompletedCorrectInputListener {
        void onCompleted(String data);
    }
}
