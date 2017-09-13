package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.resource.uicomponent.BitmapViewComponent;

import org.jetbrains.annotations.Contract;

import java.util.List;

public class ImageSingleSelectArrayAdapter extends ArrayAdapter<ImageSingleSelectItem> {
    @LayoutRes
    private final static int ITEM_LAYOUT = R.layout.list_item_image_single_select;
    @NonNull
    private final Context mContext;
    @NonNull
    private final List<ImageSingleSelectItem> mItemList;
    @DrawableRes
    private final int mDefaultImageResourceId;
    @NonNull
    private final LayoutInflater mLayoutInflater;

    public ImageSingleSelectArrayAdapter(@NonNull Context context, @NonNull List<ImageSingleSelectItem> itemList, @DrawableRes int defaultImageResourceId) {
        super(context, ITEM_LAYOUT, itemList);

        mContext = context;
        mItemList = itemList;
        mDefaultImageResourceId = defaultImageResourceId;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = getItemView(convertView);
        ImageSingleSelectItem item = getListItem(position);

        showImage(getIconView(itemView), item);
        showText(getTextView(itemView), item);

        return itemView;
    }

    public void recycle() {
        for (ImageSingleSelectItem item : mItemList) {
            item.recycleBitmapViewComponent();
        }
    }

    @Contract("!null -> !null")
    private View getItemView(@Nullable View convertView) {
        if (convertView != null)
            return convertView;
        return mLayoutInflater.inflate(ITEM_LAYOUT, null);
    }

    private ImageSingleSelectItem getListItem(int position) {
        return mItemList.get(position);
    }

    private void showImage(@NonNull ImageView imageView, @NonNull ImageSingleSelectItem item) {
        int imageResourceId = item.getImageResourceId();
        if (imageResourceId <= 0) {
            imageResourceId = mDefaultImageResourceId;
        }

        BitmapViewComponent bitmapViewComponent = new BitmapViewComponent(mContext, imageView, imageResourceId);
        bitmapViewComponent.showBitmap(item.getImageFileUri());
        item.setBitmapViewComponent(bitmapViewComponent);
    }

    private void showText(@NonNull TextView textView, @NonNull ImageSingleSelectItem item) {
        textView.setText(item.getText());
    }

    private ImageView getIconView(@NonNull View itemView) {
        return itemView.findViewById(R.id.icon);
    }

    private TextView getTextView(@NonNull View itemView) {
        return itemView.findViewById(R.id.text);
    }

    @NonNull
    public List<ImageSingleSelectItem> getItemList() {
        return mItemList;
    }
}
