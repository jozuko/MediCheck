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
    private final ViewGroup mParentView;
    @NonNull
    private final FrameLayout mDialogMainView;
    @NonNull
    private final Button mCancelButton;
    @NonNull
    private final Button mOKButton;
    @Nullable
    private T mDialogTargetView = null;
    @Nullable
    private ViewGroup.LayoutParams mLayoutParams = null;
    @Nullable
    private View.OnClickListener mOnCancelButtonClickListener = null;
    @Nullable
    private View.OnClickListener mOnOkButtonClickListener = null;
    @Nullable
    private OnCloseListener mOnCloseListener = null;

    public ADialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog, this);
        mParentView = dialogLayout.findViewById(R.id.dialog_parent_layout);
        mDialogMainView = dialogLayout.findViewById(R.id.dialog_main_layout);
        mCancelButton = dialogLayout.findViewById(R.id.dialog_cancel_button);
        mOKButton = dialogLayout.findViewById(R.id.dialog_ok_button);

        setClickListener();
        closeDialog();
    }

    private void setClickListener() {
        mParentView.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mOKButton.setOnClickListener(this);
    }

    void initTargetView(@Nullable T dialogTargetView, @NonNull ViewGroup.LayoutParams layoutParams, boolean needCancel, boolean needOk) {
        mDialogTargetView = dialogTargetView;
        mLayoutParams = layoutParams;

        addChildView();
        showCancelButton(needCancel);
        showOkButton(needOk);
    }

    void setOnCancelButtonClickListener(@Nullable View.OnClickListener listener) {
        mOnCancelButtonClickListener = listener;
    }

    void setOnOkButtonClickListener(@Nullable View.OnClickListener listener) {
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

        if (mDialogTargetView == null) return;
        if (mLayoutParams == null) mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mDialogMainView.addView(mDialogTargetView, mLayoutParams);
        mDialogMainView.setVisibility(VISIBLE);
    }

    private void showCancelButton(boolean needCancel) {
        if (needCancel) {
            mCancelButton.setVisibility(VISIBLE);
            return;
        }
        mCancelButton.setVisibility(GONE);
    }

    private void showOkButton(boolean needOk) {
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
        setVisibility(VISIBLE);
    }

    void closeDialog() {
        if (mOnCloseListener != null)
            mOnCloseListener.onClose();

        setVisibility(GONE);
    }

    public boolean isShown() {
        return (getVisibility() == VISIBLE);
    }

    public interface OnCloseListener {
        void onClose();
    }
}
