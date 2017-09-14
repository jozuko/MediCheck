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

import static android.view.View.GONE;

/**
 *
 */
public abstract class ASelectArrayAdapter<T extends ASelectItem> extends ArrayAdapter<T> {
    @LayoutRes
    private final static int ITEM_LAYOUT = R.layout.list_item_select;
    @NonNull
    final Context mContext;
    @NonNull
    final List<T> mItemList;
    @DrawableRes
    final int mDefaultImageResourceId;
    @NonNull
    final LayoutInflater mLayoutInflater;
    final boolean mUseSubText;

    public ASelectArrayAdapter(@NonNull Context context, @NonNull List<T> itemList, boolean useSubText) {
        super(context, ITEM_LAYOUT, itemList);

        mContext = context;
        mItemList = itemList;
        mDefaultImageResourceId = ASelectItem.NO_USE_ICON;
        mLayoutInflater = LayoutInflater.from(context);
        mUseSubText = useSubText;
    }

    public ASelectArrayAdapter(@NonNull Context context, @NonNull List<T> itemList, boolean useSubText, @DrawableRes int defaultImageResourceId) {
        super(context, ITEM_LAYOUT, itemList);

        mContext = context;
        mItemList = itemList;
        mDefaultImageResourceId = defaultImageResourceId;
        mLayoutInflater = LayoutInflater.from(context);
        mUseSubText = useSubText;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = getItemView(convertView);
        T item = getListItem(position);

        setIconVisibility(itemView);
        setSubTextVisibility(itemView);

        showImage(getIconView(itemView), item);
        showMainText(itemView, item);
        showSubText(itemView, item);

        return itemView;
    }

    public void recycle() {
        for (T item : mItemList) {
            item.recycleBitmapViewComponent();
        }
    }

    @Contract("!null -> !null")
    private View getItemView(@Nullable View convertView) {
        if (convertView != null)
            return convertView;
        return mLayoutInflater.inflate(ITEM_LAYOUT, null);
    }

    T getListItem(int position) {
        return mItemList.get(position);
    }

    private void setIconVisibility(@NonNull View itemView) {
        ImageView iconView = getIconView(itemView);

        if (mDefaultImageResourceId == ASelectItem.NO_USE_ICON) {
            iconView.setVisibility(GONE);
            return;
        }

        iconView.setVisibility(View.VISIBLE);
    }

    private void setSubTextVisibility(@NonNull View itemView) {
        TextView textView = getSubTextView(itemView);

        if (!mUseSubText) {
            textView.setVisibility(GONE);
            return;
        }

        textView.setVisibility(View.VISIBLE);
    }

    void setCheckedVisibility(@NonNull View itemView, int visibility) {
        getCheckedView(itemView).setVisibility(visibility);
    }

    private void showImage(@NonNull View itemView, @NonNull T item) {
        ImageView iconView = getIconView(itemView);
        if (iconView.getVisibility() == GONE) return;

        int imageResourceId = item.getImageResourceId();
        if (imageResourceId == ASelectItem.NO_USE_ICON) {
            imageResourceId = mDefaultImageResourceId;
        }

        BitmapViewComponent bitmapViewComponent = new BitmapViewComponent(mContext, iconView, imageResourceId);
        bitmapViewComponent.showBitmap(item.getImageFileUri());
        item.setBitmapViewComponent(bitmapViewComponent);
    }

    private void showMainText(@NonNull View itemView, @NonNull T item) {
        TextView textView = getMainTextView(itemView);
        showText(textView, item.getMainText());
    }

    private void showSubText(@NonNull View itemView, @NonNull T item) {
        TextView textView = getSubTextView(itemView);
        if (textView.getVisibility() == View.GONE) return;

        showText(textView, item.getSubText());
    }

    private void showText(@NonNull TextView textView, @Nullable String value) {
        textView.setText((value == null ? "" : value));
    }

    private ImageView getIconView(@NonNull View itemView) {
        return itemView.findViewById(R.id.icon);
    }

    private TextView getMainTextView(@NonNull View itemView) {
        return itemView.findViewById(R.id.main_text);
    }

    private TextView getSubTextView(@NonNull View itemView) {
        return itemView.findViewById(R.id.sub_text);
    }

    ImageView getCheckedView(@NonNull View view) {
        return view.findViewById(R.id.checked);
    }

    @NonNull
    public List<T> getItemList() {
        return mItemList;
    }

}
