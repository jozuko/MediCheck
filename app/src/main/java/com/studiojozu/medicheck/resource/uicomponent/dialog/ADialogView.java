package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studiojozu.medicheck.R;

/**
 * ダイアログViewの親クラス
 */
public abstract class ADialogView<T extends View> extends LinearLayout implements View.OnClickListener {

    @NonNull
    final Context mContext;
    @NonNull
    final T mDialogTargetView;
    @NonNull
    private final InputMethodManager mInputMethodManager;
    @NonNull
    private final ViewGroup mParentView;
    @NonNull
    private final FrameLayout mDialogMainView;
    @NonNull
    private final LinearLayout mDialogButtonLayout;
    @NonNull
    private final Button mCancelButton;
    @NonNull
    private final Button mOKButton;
    @Nullable
    private ViewGroup.LayoutParams mLayoutParams = null;
    @Nullable
    private View.OnClickListener mOnCancelButtonClickListener = null;
    @Nullable
    private View.OnClickListener mOnOkButtonClickListener = null;
    @Nullable
    private OnCloseListener mOnCloseListener = null;

    public ADialogView(@NonNull Context context, @Nullable AttributeSet attrs, @NonNull T dialogTargetView) {
        super(context, attrs);
        mContext = context;
        mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog, this);
        mParentView = dialogLayout.findViewById(R.id.dialog_parent_layout);
        mDialogMainView = dialogLayout.findViewById(R.id.dialog_main_layout);
        mCancelButton = dialogLayout.findViewById(R.id.dialog_cancel_button);
        mOKButton = dialogLayout.findViewById(R.id.dialog_ok_button);
        mDialogButtonLayout = dialogLayout.findViewById(R.id.dialog_button_layout);
        mDialogTargetView = dialogTargetView;


        setClickListener();
        closeDialog();
    }

    private void setClickListener() {
        mParentView.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mOKButton.setOnClickListener(this);
    }

    void initTargetView(@NonNull ViewGroup.LayoutParams layoutParams, boolean needCancel, boolean needOk) {
        mLayoutParams = layoutParams;

        addChildView();
        showCancelButton(needCancel);
        showOkButton(needOk);
    }

    public void setOnCancelButtonClickListener(@Nullable View.OnClickListener listener) {
        mOnCancelButtonClickListener = listener;
    }

    public void setOnOkButtonClickListener(@Nullable View.OnClickListener listener) {
        mOnOkButtonClickListener = listener;
    }

    public void setOnCloseListener(@Nullable OnCloseListener listener) {
        mOnCloseListener = listener;
    }

    void setDialogTitle(@StringRes int resourceId) {
        TextView titleText = findViewById(R.id.dialog_title_text);
        titleText.setText(resourceId);
        titleText.setVisibility(VISIBLE);
    }

    void setDialogMessage(@StringRes int resourceId) {
        TextView messageText = findViewById(R.id.dialog_message_text);
        messageText.setText(resourceId);
        messageText.setVisibility(VISIBLE);
    }

    void setDialogMessageColor(@ColorInt int color) {
        TextView messageText = findViewById(R.id.dialog_message_text);
        messageText.setTextColor(color);
    }

    private void addChildView() {
        mDialogMainView.removeAllViews();

        if (mLayoutParams == null)
            mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mDialogMainView.addView(mDialogTargetView, mLayoutParams);
        mDialogMainView.setVisibility(VISIBLE);
    }

    void showCancelButton(boolean needCancel) {
        if (needCancel) {
            mCancelButton.setVisibility(VISIBLE);
            return;
        }
        mCancelButton.setVisibility(GONE);
    }

    void showOkButton(boolean needOk) {
        if (needOk) {
            mOKButton.setVisibility(VISIBLE);
            return;
        }
        mOKButton.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        onClickNoContentArea(id);
        onClickCancelButton(id);
        onClickOkButton(id);
    }

    private void onClickNoContentArea(int id) {
        if (id != R.id.dialog_parent_layout) return;
        cancelDialog();
    }

    private void onClickCancelButton(int id) {
        if (id != R.id.dialog_cancel_button) return;
        cancelDialog();
    }

    private void onClickOkButton(int id) {
        if (id != R.id.dialog_ok_button) return;

        if (mOnOkButtonClickListener == null) {
            closeDialog();
            return;
        }

        mOnOkButtonClickListener.onClick(mOKButton);
    }

    public void cancelDialog() {
        if (mOnCancelButtonClickListener == null) {
            closeDialog();
            return;
        }

        mOnCancelButtonClickListener.onClick(mCancelButton);
    }

    public void showDialog() {
        if (mCancelButton.getVisibility() != VISIBLE && mOKButton.getVisibility() != VISIBLE)
            mDialogButtonLayout.setVisibility(GONE);

        setVisibility(VISIBLE);
    }

    void closeDialog() {
        if (mOnCloseListener != null)
            mOnCloseListener.onClose();

        hideSoftwareKeyboard();
        setVisibility(GONE);
    }

    public boolean isShown() {
        return (getVisibility() == VISIBLE);
    }

    void showSoftwareKeyboard() {
        mDialogTargetView.requestFocus();
        mInputMethodManager.showSoftInput(mDialogTargetView, 0);
    }

    void hideSoftwareKeyboard() {
        mInputMethodManager.hideSoftInputFromWindow(mDialogTargetView.getWindowToken(), 0);
    }

    public interface OnCloseListener {
        void onClose();
    }
}
