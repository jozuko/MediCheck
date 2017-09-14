package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SingleSelectArrayAdapter extends ASelectArrayAdapter<SingleSelectItem> {

    public SingleSelectArrayAdapter(@NonNull Context context, @NonNull List<SingleSelectItem> itemList, boolean useSubText) {
        super(context, itemList, useSubText);
    }

    public SingleSelectArrayAdapter(@NonNull Context context, @NonNull List<SingleSelectItem> itemList, boolean useSubText, @DrawableRes int defaultImageResourceId) {
        super(context, itemList, useSubText, defaultImageResourceId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);
        setCheckedVisibility(itemView, View.GONE);
        return itemView;
    }
}
