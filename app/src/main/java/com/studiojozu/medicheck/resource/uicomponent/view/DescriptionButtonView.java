package com.studiojozu.medicheck.resource.uicomponent.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.common.log.Log;
import com.studiojozu.medicheck.R;

import org.jetbrains.annotations.Contract;

/**
 * 説明文がついたボタンView
 */
public class DescriptionButtonView extends ACustomView<DescriptionButtonView> implements View.OnClickListener {

    @Nullable
    private View.OnClickListener mClientClickListener = null;

    public DescriptionButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getTypedArray(attrs);
        try {
            setButtonText(typedArray);
            setButtonIcon(typedArray);
            setDescriptionText(typedArray);
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        getLayoutGroup().setOnClickListener(this);
    }

    @Override
    int layoutResource() {
        return R.layout.description_button;
    }

    @Override
    int[] styleableResources() {
        return R.styleable.description_button_view;
    }

    public void setOnClickListener(@Nullable View.OnClickListener listener) {
        mClientClickListener = listener;
    }

    private void setButtonText(@Nullable TypedArray typedArray) {
        String text = getAttributeString(typedArray, R.styleable.description_button_view_text);
        getTextInstance().setText(text);
    }

    private void setButtonIcon(@Nullable TypedArray typedArray) {
        int drawableLeftResourceId = getAttributeDrawableResourceId(typedArray, R.styleable.description_button_view_drawableLeft);
        if (drawableLeftResourceId == UNKNOWN_RESOURCE_ID) return;

        getIconInstance().setImageResource(drawableLeftResourceId);
    }

    private void setDescriptionText(@Nullable TypedArray typedArray) {
        String text = getAttributeString(typedArray, R.styleable.description_button_view_description);
        getMessageInstance().setText(text);
    }

    @NonNull
    private ViewGroup getLayoutGroup() {
        return (ViewGroup) mCustomView.findViewById(R.id.description_button_layout);
    }

    @NonNull
    private ImageView getIconInstance() {
        return (ImageView) mCustomView.findViewById(R.id.description_button_icon);
    }

    @NonNull
    private TextView getTextInstance() {
        return (TextView) mCustomView.findViewById(R.id.description_button_text);
    }

    @NonNull
    private TextView getMessageInstance() {
        return (TextView) mCustomView.findViewById(R.id.description_button_message);
    }

    @Override
    public void onClick(View view) {
        if (mClientClickListener != null)
            mClientClickListener.onClick(this);
    }
}
