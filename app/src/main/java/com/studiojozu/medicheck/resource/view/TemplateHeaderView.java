package com.studiojozu.medicheck.resource.view;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;

/**
 * template_header.xmlをインクルードしたActivityから呼び出される
 */
class TemplateHeaderView implements View.OnClickListener {
    @NonNull
    private final TemplateHeaderIncludeActivity mTemplateHeaderIncludeActivity;
    @NonNull
    private final Activity mParentActivity;

    TemplateHeaderView(@NonNull TemplateHeaderIncludeActivity parentActivity, @DrawableRes int iconResourceId, @StringRes int titleResourceId) {
        mTemplateHeaderIncludeActivity = parentActivity;
        mParentActivity = mTemplateHeaderIncludeActivity.getActivity();

        setIcon(iconResourceId);
        setTitle(titleResourceId);
        setClickListener();
    }

    private void setIcon(@DrawableRes int iconResourceId) {
        ImageView iconImageView = mParentActivity.findViewById(R.id.header_icon);
        iconImageView.setImageResource(iconResourceId);
    }

    private void setTitle(@StringRes int titleResourceId) {
        TextView titleTextView = mParentActivity.findViewById(R.id.header_title);
        titleTextView.setText(titleResourceId);
    }

    private void setClickListener() {
        mParentActivity.findViewById(R.id.header_title_back_to_menu_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onClickBackToMenuButton(view.getId());
    }

    private void onClickBackToMenuButton(@IdRes int resourceId) {
        if (resourceId != R.id.header_title_back_to_menu_button) return;

        if (!mTemplateHeaderIncludeActivity.beforeFinish()) return;
        mParentActivity.finish();
    }

    interface TemplateHeaderIncludeActivity {
        void initTemplateHeaderView();

        Activity getActivity();

        boolean beforeFinish();
    }
}
