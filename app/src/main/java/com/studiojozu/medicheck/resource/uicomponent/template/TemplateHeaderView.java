package com.studiojozu.medicheck.resource.uicomponent.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.resource.uicomponent.ACustomView;

public class TemplateHeaderView extends ACustomView<TemplateHeaderView> implements View.OnClickListener {

    @Nullable
    private OnFinishingListener mClientOnFinishingListener = null;

    public TemplateHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getTypedArray(attrs);
        try {
            setHeaderIcon(typedArray);
            setHeaderTitle(typedArray);
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        findViewById(R.id.header_back_to_menu_button).setOnClickListener(this);
    }

    @Override
    protected int layoutResource() {
        return R.layout.template_header;
    }

    @Nullable
    @Override
    protected int[] styleableResources() {
        return R.styleable.template_header_view;
    }

    private void setHeaderIcon(@Nullable TypedArray typedArray) {
        int drawableResourceId = getAttributeDrawableResourceId(typedArray, R.styleable.template_header_view_header_icon);
        if (drawableResourceId == UNKNOWN_RESOURCE_ID) return;

        getHeaderIconInstance().setImageResource(drawableResourceId);
    }

    private void setHeaderTitle(@Nullable TypedArray typedArray) {
        String value = getAttributeString(typedArray, R.styleable.template_header_view_header_title);
        getHeaderTitleInstance().setText(value);
    }

    private ImageView getHeaderIconInstance() {
        return mCustomView.findViewById(R.id.template_header_icon);
    }

    private TextView getHeaderTitleInstance() {
        return mCustomView.findViewById(R.id.template_header_title);
    }

    public void setOnFinishingListener(@Nullable OnFinishingListener listener) {
        mClientOnFinishingListener = listener;
    }

    @Override
    public void onClick(View view) {
        onClickBackToMenuButton(view.getId());
    }

    private void onClickBackToMenuButton(@IdRes int resourceId) {
        if (resourceId != R.id.header_back_to_menu_button) return;

        if (mClientOnFinishingListener != null && !mClientOnFinishingListener.onFinishing()) return;

        if (mActivity != null)
            mActivity.finish();
    }

    public interface OnFinishingListener {
        boolean onFinishing();
    }
}
