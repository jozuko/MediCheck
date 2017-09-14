package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.studiojozu.medicheck.R;

import java.util.List;

public class MultiSelectArrayAdapter extends ASelectArrayAdapter<MultiSelectItem> {

    private final boolean mUseCheckBox;

    public MultiSelectArrayAdapter(@NonNull Context context, @NonNull List<MultiSelectItem> itemList, boolean useSubText, boolean useCheckBox) {
        super(context, itemList, useSubText);
        mUseCheckBox = useCheckBox;
    }

    public MultiSelectArrayAdapter(@NonNull Context context, @NonNull List<MultiSelectItem> itemList, boolean useSubText, boolean useCheckBox, @DrawableRes int defaultImageResourceId) {
        super(context, itemList, useSubText, defaultImageResourceId);
        mUseCheckBox = useCheckBox;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);

        setCheckedVisibility(itemView, (mUseCheckBox ? View.VISIBLE : View.GONE));
        if (mUseCheckBox) {
            setItemViewOnClickListener(itemView, getListItem(position));
        }

        return itemView;
    }

    private void showChecked(@NonNull View itemView, @NonNull MultiSelectItem item) {
        getCheckedView(itemView).setImageResource(item.isChecked() ? R.mipmap.ic_check_on : R.mipmap.ic_check_off);
    }

    private void setItemViewOnClickListener(@NonNull View itemView, @NonNull final MultiSelectItem item) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setChecked(!item.isChecked());
                showChecked(view, item);
            }
        });
    }
}
