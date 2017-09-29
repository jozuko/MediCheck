package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * 選択ダイアログ
 */
public class SelectorDialogView extends ADialogView<ListView> implements ListView.OnItemClickListener {

    private static final FrameLayout.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    @Nullable
    private ListView.OnItemClickListener mClientOnSelectedListener = null;

    public SelectorDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, new ListView(context));

        mDialogTargetView.setOnItemClickListener(this);
        initTargetView(LAYOUT_PARAMS, false, false);
    }

    public void setListViewAdapter(@NonNull BaseAdapter adapter) {
        mDialogTargetView.setAdapter(adapter);
    }

    public void setOnItemSelectedListener(@Nullable final ListView.OnItemClickListener listener) {
        mClientOnSelectedListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mClientOnSelectedListener != null) {
            mClientOnSelectedListener.onItemClick(adapterView, view, position, id);
        }
        closeDialog();
    }

    public void showSelectorDialog(@StringRes int titleResourceId, boolean useOkButton, boolean useCancelButton) {
        setDialogTitle(titleResourceId);
        setUseOkButton(useOkButton);
        setUseCancelButton(useCancelButton);

        showDialog();
    }
}
