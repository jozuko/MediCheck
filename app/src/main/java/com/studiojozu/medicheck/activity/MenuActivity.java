package com.studiojozu.medicheck.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.studiojozu.medicheck.R;

/**
 * トップメニューを表示するActivity
 */
public class MenuActivity extends Activity implements View.OnClickListener {

    /** メニューボタン ResourceId */
    private static final int[] MENU_BUTTON_RESOURCE_ID = {
            R.id.menu_button_today_medicine,
            R.id.menu_button_history_medicine,
            R.id.menu_button_list_medicine,
            R.id.menu_button_register_medicine,
            R.id.menu_button_setting};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_menu);

        setClickListenerAllButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * メニューボタンにClickListenerを設定する。
     */
    private void setClickListenerAllButton() {
        for (int resourceId : MENU_BUTTON_RESOURCE_ID) {
            setClickListener(resourceId);
        }
    }

    /**
     * ClickListenerを設定する
     *
     * @param resourceId ClickListenerを設定するリソースID
     */
    private void setClickListener(int resourceId) {
        findViewById(resourceId).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == null) return;

        onClickTodayMedicineButton(view);
        onClickHistoryMedicineButton(view);
        onClickListMedicineButton(view);
        onClickRegisterMedicineButton(view);
        onClickSettingButton(view);
    }

    /**
     * 今日のお薬ボタン Clickイベント処理
     */
    private void onClickTodayMedicineButton(@NonNull View view) {
        if (view.getId() != R.id.menu_button_today_medicine) return;
        startNextActivity(TodayMedicineActivity.class, R.id.menu_button_today_medicine);
    }

    /**
     * 服用履歴ボタン Clickイベント処理
     */
    private void onClickHistoryMedicineButton(@NonNull View view) {
        if (view.getId() != R.id.menu_button_history_medicine) return;

        // TODO 服用履歴画面遷移
    }

    /**
     * お薬一覧ボタン Clickイベント処理
     */
    private void onClickListMedicineButton(@NonNull View view) {
        if (view.getId() != R.id.menu_button_list_medicine) return;

        // TODO お薬一覧画面遷移
    }

    /**
     * お薬登録ボタン Clickイベント処理
     */
    private void onClickRegisterMedicineButton(@NonNull View view) {
        if (view.getId() != R.id.menu_button_register_medicine) return;
        startNextActivity(RegisterMedicineActivity.class, R.id.menu_button_register_medicine);
    }

    /**
     * 設定ボタン Clickイベント処理
     */
    private void onClickSettingButton(@NonNull View view) {
        if (view.getId() != R.id.menu_button_setting) return;

        // TODO 設定画面遷移
    }

    /**
     * 画面を遷移する。
     *
     * @param nextActivityClass 次画面のActivityクラス
     * @param requestCode       リクエストコード
     */
    private void startNextActivity(Class nextActivityClass, int requestCode) {
        Intent intent = new Intent(this, nextActivityClass);
        startActivityForResult(intent, requestCode);
    }


}

