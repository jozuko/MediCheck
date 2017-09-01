package com.studiojozu.medicheck.activity.uicomponent;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * 画像表示Spinner用のAdapter
 */
public class ImageSpinnerAdapter extends BaseAdapter {

    @NonNull
    private final Context mContext;
    @NonNull
    private final LayoutInflater mLayoutInflater;
    @NonNull
    private final List<ValueHolder> mValueHolderList;
    @NonNull
    private final List<ViewHolder> mViewHolderList;

    private final int mDefaultImageResourceId;

    private static class ValueHolder {
        private String mText;
        private Uri mUri;

        private ValueHolder(String text, Uri uri) {
            mText = text;
            mUri = uri;
        }
    }

    private static class ViewHolder {
        private BitmapViewComponent mBitmapViewComponent;
        private TextView mTextView;

        private ViewHolder(TextView textView, BitmapViewComponent bitmapViewComponent) {
            mBitmapViewComponent = bitmapViewComponent;
            mTextView = textView;
        }

        private void showValues(ValueHolder valueHolder) {
            mBitmapViewComponent.showBitmap(valueHolder.mUri);
            mTextView.setText(valueHolder.mText);
        }
    }

    public ImageSpinnerAdapter(@NonNull Context context, int itemLayoutId) {
        this(context, itemLayoutId, -1);
    }

    public ImageSpinnerAdapter(@NonNull Context context, int itemLayoutId, int defaultImageResouceId) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDefaultImageResourceId = defaultImageResouceId;
        mViewHolderList = new ArrayList<>();
        mValueHolderList = new ArrayList<>();
    }

    public void add(String text, Uri uri) {
        mValueHolderList.add(new ValueHolder(text, uri));
    }

    /**
     * ActivityのonDestory()で必ずコールすること
     */
    public void onDestory() {
        for (ViewHolder viewHolder : mViewHolderList) {
            viewHolder.mBitmapViewComponent.onDestory();
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View itemView = getItemView(view);
        ViewHolder holder = getViewHolder(itemView);
        holder.showValues(mValueHolderList.get(position));

        return itemView;
    }

    /**
     * Spinnerのitemを表示するためのViewを取得する
     *
     * @param view Spinnerのitemを示すView
     * @return Spinnerのitemを表示するためのView。パラメータがnullの場合はxmlから取得する。
     */
    @Contract("!null -> !null")
    private View getItemView(@Nullable View view) {
        if (view != null) return view;
        return mLayoutInflater.inflate(R.layout.image_spinner_item, null);
    }

    /**
     * ViewHolerを取得する。
     * itemViewのTagにViewHolderが指定されている場合はそのTagを返却する。
     * 指定されていない場合は、新たに生成し、TAGへの追加と一覧への追加を行う。
     *
     * @param itemView SpinnerのアイテムView
     * @return SpinnerのアイテムViewに関連づくViewHolder
     */
    @NonNull
    private ViewHolder getViewHolder(@NonNull View itemView) {
        Object itemViewTag = itemView.getTag();

        if ((itemViewTag != null) && (itemViewTag instanceof ViewHolder))
            return (ViewHolder) itemViewTag;

        ImageView imageView = itemView.findViewById(R.id.image_spinner_item_icon);
        TextView textView = itemView.findViewById(R.id.image_spinner_item_text);

        BitmapViewComponent bitmapViewComponent = new BitmapViewComponent(mContext, imageView, mDefaultImageResourceId);
        ViewHolder viewHolder = new ViewHolder(textView, bitmapViewComponent);

        mViewHolderList.add(viewHolder);
        itemView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public int getCount() {
        return mValueHolderList.size();
    }

    @Override
    public Object getItem(int position) {
        return mValueHolderList.get(position).mText;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
