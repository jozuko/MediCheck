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
    private final ViewGroup mMainView;
    @NonNull
    private final FrameLayout mTargetLayout;
    @NonNull
    private final LinearLayout mButtonLayout;
    @NonNull
    private final LinearLayout mHeaderLayout;
    @NonNull
    private final TextView mTitleTextView;
    @NonNull
    private final TextView mMessageTextView;
    @NonNull
    private final Button mCancelButton;
    @NonNull
    private final Button mOkButton;
    @Nullable
    private ViewGroup.LayoutParams mLayoutParams = null;
    @Nullable
    private View.OnClickListener mClientCancelButtonOnClickListener = null;
    @Nullable
    private View.OnClickListener mClientOkButtonOnClickListener = null;
    @Nullable
    private OnCloseListener mClientOnCloseListener = null;

    public ADialogView(@NonNull Context context, @Nullable AttributeSet attrs, @NonNull T dialogTargetView) {
        super(context, attrs);
        mContext = context;
        mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog, this);
        mMainView = dialogLayout.findViewById(R.id.dialog_main_layout);
        mTargetLayout = dialogLayout.findViewById(R.id.dialog_target_layout);
        mHeaderLayout = dialogLayout.findViewById(R.id.dialog_header_layout);
        mButtonLayout = dialogLayout.findViewById(R.id.dialog_button_layout);
        mTitleTextView = dialogLayout.findViewById(R.id.dialog_title_text);
        mMessageTextView = dialogLayout.findViewById(R.id.dialog_message_text);
        mCancelButton = dialogLayout.findViewById(R.id.dialog_cancel_button);
        mOkButton = dialogLayout.findViewById(R.id.dialog_ok_button);

        mDialogTargetView = dialogTargetView;

        setDefaultVisibility();
        setClickListener();
        closeDialog();
    }

    private void setDefaultVisibility() {
        mHeaderLayout.setVisibility(View.GONE);
        mTitleTextView.setVisibility(View.GONE);
        mMessageTextView.setVisibility(View.GONE);
        mTargetLayout.setVisibility(View.GONE);
        mButtonLayout.setVisibility(View.GONE);
        mCancelButton.setVisibility(View.GONE);
        mOkButton.setVisibility(View.GONE);
    }

    private void setClickListener() {
        mMainView.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);
    }

    void initTargetView(@NonNull ViewGroup.LayoutParams layoutParams, boolean needCancel, boolean needOk) {
        mLayoutParams = layoutParams;

        addChildView();
        setUseCancelButton(needCancel);
        setUseOkButton(needOk);
    }

    public void setCancelButtonOnClickListener(@Nullable View.OnClickListener listener) {
        mClientCancelButtonOnClickListener = listener;
    }

    public void setOkButtonOnClickListener(@Nullable View.OnClickListener listener) {
        mClientOkButtonOnClickListener = listener;
    }

    public void setOnCloseListener(@Nullable OnCloseListener listener) {
        mClientOnCloseListener = listener;
    }

    void setDialogTitle(@StringRes int resourceId) {
        if (resourceId <= 0) return;
        mTitleTextView.setText(resourceId);
        showTitleTextView();
    }

    void setDialogMessage(@StringRes int resourceId) {
        if (resourceId <= 0) return;
        mMessageTextView.setText(resourceId);
        setMessageVisibility();
    }

    private void setDialogMessageVisibility() {
        mHeaderLayout.setVisibility(View.VISIBLE);
    }

    private void showTitleTextView() {
        mTitleTextView.setVisibility(View.VISIBLE);
        setDialogMessageVisibility();
    }

    private void setMessageVisibility() {
        mMessageTextView.setVisibility(View.VISIBLE);
        setDialogMessageVisibility();
    }

    void setDialogMessageColor(@ColorInt int color) {
        mMessageTextView.setTextColor(color);
    }

    private void addChildView() {
        mTargetLayout.removeAllViews();

        if (mLayoutParams == null)
            mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mTargetLayout.addView(mDialogTargetView, mLayoutParams);
        mTargetLayout.setVisibility(View.VISIBLE);
    }

    void setUseCancelButton(boolean useCancelButton) {
        mCancelButton.setVisibility(useCancelButton ? View.VISIBLE : View.GONE);
        setVisibilityButtonLayout();
    }

    void setUseOkButton(boolean useOkButton) {
        mOkButton.setVisibility(useOkButton ? View.VISIBLE : View.GONE);
        setVisibilityButtonLayout();
    }

    private void setVisibilityButtonLayout() {
        if (mOkButton.getVisibility() == View.VISIBLE) {
            mButtonLayout.setVisibility(View.VISIBLE);
            return;
        }
        if (mCancelButton.getVisibility() == View.VISIBLE) {
            mButtonLayout.setVisibility(View.VISIBLE);
            return;
        }
        mButtonLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        onClickNoContentArea(id);
        onClickCancelButton(id);
        onClickOkButton(id);
    }

    private void onClickNoContentArea(int id) {
        if (id != R.id.dialog_main_layout) return;
        cancelDialog();
    }

    private void onClickCancelButton(int id) {
        if (id != R.id.dialog_cancel_button) return;
        cancelDialog();
    }

    private void onClickOkButton(int id) {
        if (id != R.id.dialog_ok_button) return;
        if (mClientOkButtonOnClickListener == null) {
            closeDialog();
            return;
        }

        mClientOkButtonOnClickListener.onClick(mOkButton);
    }

    public void cancelDialog() {
        if (mClientCancelButtonOnClickListener == null) {
            closeDialog();
            return;
        }

        mClientCancelButtonOnClickListener.onClick(mCancelButton);
    }

    public void showDialog() {
        this.setVisibility(VISIBLE);
    }

    void closeDialog() {
        if (mClientOnCloseListener != null)
            mClientOnCloseListener.onClose();

        hideSoftwareKeyboard();
        this.setVisibility(GONE);
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
