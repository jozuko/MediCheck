package com.studiojozu.medicheck.activity;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.service.ParsonService;

/**
 *
 */
class TemplateParsonSelectView {

    @NonNull
    private final TemplateParsonSelectIncludeActivity mTemplateParsonSelectIncludeActivity;
    @NonNull
    private final Activity mParentActivity;
    @NonNull
    private final ParsonService mParsonService;


    TemplateParsonSelectView(@NonNull TemplateParsonSelectIncludeActivity parentActivity) {
        mTemplateParsonSelectIncludeActivity = parentActivity;
        mParentActivity = mTemplateParsonSelectIncludeActivity.getActivity();
        mParsonService = new ParsonService();
    }

    void init() {
    }

    void recycle() {
    }

    interface TemplateParsonSelectIncludeActivity {
        void initTemplateParsonSelectView();

        Activity getActivity();
    }
}
