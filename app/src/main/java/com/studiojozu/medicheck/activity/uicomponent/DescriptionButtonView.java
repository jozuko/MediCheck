package com.studiojozu.medicheck.activity.uicomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studiojozu.medicheck.R;

import org.jetbrains.annotations.Contract;

/**
 * 説明文がついたボタンView
 */
public class DescriptionButtonView extends FrameLayout {

    /**
     * 引数をLayoutに反映するコンストラクタ
     *
     * @param context アプリケーションコンテキスト
     * @param attrs   引数
     */
    public DescriptionButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View descriptionButtonView = getMainLayout(context);
        TypedArray typedArray = getTypedArray(context, attrs);
        try {
            setButtonText(descriptionButtonView, typedArray);
            setButtonIcon(descriptionButtonView, typedArray);
            setDescriptionText(descriptionButtonView, typedArray);
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
     * ボタンにテキストを表示する
     *
     * @param mainLayout メインView
     * @param typedArray layout.xmlで指定した引数
     */
    private void setButtonText(@NonNull View mainLayout, @Nullable TypedArray typedArray) {
        String text = getString(typedArray, R.styleable.description_button_view_text);
        getButtonInstance(mainLayout).setText(text);
    }

    /**
     * ボタン左端にアイコンを表示する
     *
     * @param mainLayout メインView
     * @param typedArray layout.xmlで指定した引数
     */
    private void setButtonIcon(@NonNull View mainLayout, @Nullable TypedArray typedArray) {
        int drawableLeftResourceId = getReference(typedArray, R.styleable.description_button_view_drawableLeft);
        if (drawableLeftResourceId < 0) return;

        getButtonInstance(mainLayout).setCompoundDrawablesWithIntrinsicBounds(drawableLeftResourceId, 0, 0, 0);
    }

    /**
     * 説明文にテキストを表示する
     *
     * @param mainLayout メインView
     * @param typedArray layout.xmlで指定した引数
     */
    private void setDescriptionText(@NonNull View mainLayout, @Nullable TypedArray typedArray) {
        String text = getString(typedArray, R.styleable.description_button_view_description);
        getDescriptionText(mainLayout).setText(text);
    }

    /**
     * Buttonのインスタンスを取得する
     *
     * @param mainLayout メインView
     * @return Buttonインスタンス
     */
    private Button getButtonInstance(@NonNull View mainLayout) {
        return (Button) mainLayout.findViewById(R.id.description_button);
    }

    /**
     * 説明文を表示するTextViewを取得する
     *
     * @param mainLayout メインView
     * @return 説明文を表示するTextView
     */
    private TextView getDescriptionText(@NonNull View mainLayout) {
        return (TextView) mainLayout.findViewById(R.id.description_text);
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

}
