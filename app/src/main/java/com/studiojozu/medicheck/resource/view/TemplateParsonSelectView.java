package com.studiojozu.medicheck.resource.view;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 *
 */
class TemplatePersonSelectView {

    @NonNull
    private final TemplatePersonSelectIncludeActivity mTemplatePersonSelectIncludeActivity;
    @NonNull
    private final Activity mParentActivity;


    TemplatePersonSelectView(@NonNull TemplatePersonSelectIncludeActivity parentActivity) {
        mTemplatePersonSelectIncludeActivity = parentActivity;
        mParentActivity = mTemplatePersonSelectIncludeActivity.getActivity();
    }

    void init() {
    }

    void recycle() {
    }

    interface TemplatePersonSelectIncludeActivity {
        void initTemplatePersonSelectView();

        Activity getActivity();
    }
}
