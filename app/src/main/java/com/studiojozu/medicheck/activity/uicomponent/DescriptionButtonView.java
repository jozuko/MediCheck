package com.studiojozu.medicheck.activity.uicomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.log.Log;

import org.jetbrains.annotations.Contract;

/**
 * 説明文がついたボタンView
 */
public class DescriptionButtonView extends FrameLayout implements View.OnClickListener {

    /** このクラスのインスタンス */
    @NonNull
    private final View mDescriptionButtonView;

    /**
     * 引数をLayoutに反映するコンストラクタ
     *
     * @param context アプリケーションコンテキスト
     * @param attrs   引数
     */
    public DescriptionButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDescriptionButtonView = getMainLayout(context);
        setOnClickListener(this);

        TypedArray typedArray = getTypedArray(context, attrs);
        try {
            setButtonText(typedArray);
            setButtonIcon(typedArray);
            setDescriptionText(typedArray);
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }
    }

    /**
     * このViewの一番親となるLayoutを取得する
     *
     * @param context アプリケーションコンテキスト
     * @return このViewの一番親となるLayout
     */
    private View getMainLayout(@NonNull Context context) {
        return LayoutInflater.from(context).inflate(R.layout.description_button, this);
    }

    /**
     * AttributeSetからこのViewで使用可能なTypedArrayを取得する
     *
     * @param context アプリケーションコンテキスト
     * @param attrs   layout.xmlで指定したAttributeSet
     * @return このViewで使用可能なTypedArray
     */
    @Contract("_, null -> null")
    private TypedArray getTypedArray(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) return null;
        return context.obtainStyledAttributes(attrs, R.styleable.description_button_view);
    }

    /**
     * ClickListerを登録する
     *
     * @param listener ClickListener
     */
    public void setOnClickListener(View.OnClickListener listener) {
        getMainLayout().setOnClickListener(listener);
    }

    /**
     * ボタンにテキストを表示する
     *
     * @param typedArray layout.xmlで指定した引数
     */
    private void setButtonText(@Nullable TypedArray typedArray) {
        String text = getString(typedArray, R.styleable.description_button_view_text);
        getTextInstance().setText(text);
    }

    /**
     * 左端にアイコンを表示する
     *
     * @param typedArray layout.xmlで指定した引数
     */
    private void setButtonIcon(@Nullable TypedArray typedArray) {
        int drawableLeftResourceId = getReference(typedArray, R.styleable.description_button_view_drawableLeft);
        if (drawableLeftResourceId < 0) return;

        getIconInstance().setImageResource(drawableLeftResourceId);
    }

    /**
     * 説明文にテキストを表示する
     *
     * @param typedArray layout.xmlで指定した引数
     */
    private void setDescriptionText(@Nullable TypedArray typedArray) {
        String text = getString(typedArray, R.styleable.description_button_view_description);
        getMessageInstance().setText(text);
    }

    /**
     * メインレイアウトを取得する
     *
     * @return メインレイアウト
     */
    @NonNull
    private ViewGroup getMainLayout() {
        return (ViewGroup) mDescriptionButtonView.findViewById(R.id.description_button_layout);
    }

    /**
     * 画像のインスタンスを取得する
     *
     * @return Buttonインスタンス
     */
    @NonNull
    private ImageView getIconInstance() {
        return (ImageView) mDescriptionButtonView.findViewById(R.id.description_button_icon);
    }

    /**
     * タイトルのインスタンスを取得する
     *
     * @return Buttonインスタンス
     */
    @NonNull
    private TextView getTextInstance() {
        return (TextView) mDescriptionButtonView.findViewById(R.id.description_button_text);
    }

    /**
     * 説明文を表示するTextViewを取得する
     *
     * @return 説明文を表示するTextView
     */
    @NonNull
    private TextView getMessageInstance() {
        return (TextView) mDescriptionButtonView.findViewById(R.id.description_button_message);
    }

    /**
     * このViewで使用可能なtypedArrayからStringを取得する
     *
     * @param typedArray  使用可能なtypedArray
     * @param styleableId attrs.xmlに指定したID
     * @return typedArrayにから取得したString値
     */
    @Contract("null, _ -> !null")
    private String getString(@Nullable TypedArray typedArray, int styleableId) {
        if (typedArray == null) return "";
        return typedArray.getString(styleableId);
    }

    /**
     * このViewで使用可能なtypedArrayからリソースIDを取得する
     *
     * @param typedArray  使用可能なtypedArray
     * @param styleableId attrs.xmlに指定したID
     * @return typedArrayにから取得したリソースID
     */
    private int getReference(@Nullable TypedArray typedArray, int styleableId) {
        if (typedArray == null) return -1;
        return typedArray.getResourceId(styleableId, -1);
    }

    @Override
    public void onClick(View view) {
        new Log(this.getClass()).i("Main FrameLayout clicked.");
    }
}
