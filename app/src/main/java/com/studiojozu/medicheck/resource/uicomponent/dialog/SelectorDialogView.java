package com.studiojozu.medicheck.resource.uicomponent.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 選択ダイアログ
 */
public class SelectorDialogView extends ADialogView<ListView> implements ListView.OnItemClickListener {

    private static final ViewGroup.LayoutParams LAYOUT_PARAMS = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    @NonNull
    private final ListView mListView;
    @Nullable
    private ListView.OnItemClickListener mClientOnSelectedListener = null;

    public SelectorDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mListView = createListView(context);
        initTargetView(mListView, LAYOUT_PARAMS, false, false);
    }

    @NonNull
    private ListView createListView(@NonNull Context context) {
        ListView listView = new ListView(context);
        listView.setOnItemClickListener(this);

        return listView;
    }

    public void setListViewAdapter(@NonNull BaseAdapter adapter) {
        mListView.setAdapter(adapter);
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

    public void showSelectorDialog(boolean needOK, boolean needCancel) {
        showOkButton(needOK);
        showCancelButton(needCancel);

        showDialog();
    }
}
