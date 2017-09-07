package com.studiojozu.medicheck.activity.uicomponent.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.studiojozu.medicheck.R;

/**
 * ダイアログViewの親クラス
 */
public abstract class ADialogView<T extends View> extends LinearLayout implements View.OnClickListener {

    @NonNull
    private final Context mContext;
    @NonNull
    private final ViewGroup mParentView;
    @NonNull
    private final ScrollView mDialogMainView;
    @NonNull
    private final Button mCancelButton;
    @NonNull
    private final Button mOKButton;
    @Nullable
    private T mDialogTargetView = null;
    @Nullable
    private ScrollView.LayoutParams mLayoutParams = null;
    @Nullable
    private View.OnClickListener mOnCancelButtonClickListener = null;
    @Nullable
    private View.OnClickListener mOnOkButtonClickListener = null;

    public ADialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        View dialogLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog, this);
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

    protected void initTargetView(@NonNull T dialogTargetView, @NonNull ScrollView.LayoutParams layoutParams, boolean needCancel, boolean needOk) {
        mDialogTargetView = dialogTargetView;
        mLayoutParams = layoutParams;

        addChildView();
        showCancelButton(needCancel);
        showOkButton(needOk);
    }

    protected void setOnCancelButtonClickListener(@Nullable View.OnClickListener listener) {
        mOnCancelButtonClickListener = listener;
    }

    protected void setOnOkButtonClickListener(@Nullable View.OnClickListener listener) {
        mOnOkButtonClickListener = listener;
    }

    private void addChildView() {
        mDialogMainView.removeAllViews();

        if (mDialogTargetView == null) return;
        if (mLayoutParams == null) mLayoutParams = new ScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mDialogMainView.addView(mDialogTargetView, mLayoutParams);
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
        if (mOnOkButtonClickListener != null)
            mOnOkButtonClickListener.onClick(mOKButton);
    }

    private void cancelDialog() {
        if (mOnCancelButtonClickListener != null)
            mOnCancelButtonClickListener.onClick(mCancelButton);

        closeDialog();
    }

    public void showDialog() {
        this.setVisibility(VISIBLE);
    }

    private void closeDialog() {
        this.setVisibility(GONE);
    }

}
