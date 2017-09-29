package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studiojozu.common.domain.model.IValidator;

/**
 * 入力ダイアログ
 */
public class InputDialogView extends ADialogView<TextView> {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public InputDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, new EditText(context));

        initTargetView(LAYOUT_PARAMS, true, true);
    }

    public void showInputDialog(@StringRes int titleResourceId, @NonNull InputType inputType, @NonNull String defaultValue, @Nullable final IValidator inputValidation, @Nullable final OnCompletedCorrectInputListener listener) {
        setDialogTitle(titleResourceId);
        mDialogTargetView.setInputType(inputType.getInputType());
        mDialogTargetView.setText(defaultValue);
        setOnOkButton(inputValidation, listener);

        showDialog();
        showSoftwareKeyboard();
    }

    private void setOnOkButton(@Nullable final IValidator inputValidation, @Nullable final OnCompletedCorrectInputListener listener) {
        setOkButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = mDialogTargetView.getText().toString();

                // check validation
                int validationResult = validateData(inputValidation, data);
                if (showValidationResult(validationResult)) return;

                // 呼び出し元にコールバック
                if (listener != null)
                    listener.onCompleted(data);

                closeDialog();
            }
        });
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

    public enum InputType {
        /** 改行なし文字列 */
        TEXT_SINGLE_LINE(android.text.InputType.TYPE_CLASS_TEXT),
        /** 改行あり文字列 */
        TEXT_MULTI_LINE(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE),
        /** 整数数値 符号なし */
        NUMBER(android.text.InputType.TYPE_CLASS_NUMBER),
        /** 小数数値 */
        NUMBER_DECIMAL(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);

        private int mInputType;

        InputType(int inputType) {
            mInputType = inputType;
        }

        private int getInputType() {
            return mInputType;
        }

    }

    public interface OnCompletedCorrectInputListener {
        void onCompleted(String data);
    }
}
