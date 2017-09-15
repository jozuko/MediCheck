package com.studiojozu.medicheck.resource.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.studiojozu.medicheck.R;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_menu);

        setClickListenerAllButton();
    }

    /**
     * メニューボタンにClickListenerを設定する。
     */
    private void setClickListenerAllButton() {
        setHistoryMedicineButtonClickListener();
        setListMedicineButtonClickListener();
        setRegisterMedicineButtonClickListener();
        setTodayMedicineButtonClickListener();
        setSettingButtonClickListener();
    }

    /**
     * 今日のお薬ボタン Clickイベント処理
     */
    private void setTodayMedicineButtonClickListener() {
        findViewById(R.id.menu_button_today_medicine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextActivity(TodayMedicineActivity.class, R.id.menu_button_today_medicine);
            }
        });
    }

    /**
     * 服用履歴ボタン Clickイベント処理
     */
    private void setHistoryMedicineButtonClickListener() {
        findViewById(R.id.menu_button_history_medicine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 服用履歴画面遷移
            }
        });
    }

    /**
     * お薬一覧ボタン Clickイベント処理
     */
    private void setListMedicineButtonClickListener() {
        findViewById(R.id.menu_button_list_medicine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO お薬一覧画面遷移
            }
        });
    }

    /**
     * お薬登録ボタン Clickイベント処理
     */
    private void setRegisterMedicineButtonClickListener() {
        findViewById(R.id.menu_button_register_medicine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextActivity(RegisterMedicineActivity.class, R.id.menu_button_register_medicine);
            }
        });
    }

    /**
     * 設定ボタン Clickイベント処理
     */
    private void setSettingButtonClickListener() {
        findViewById(R.id.menu_button_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 設定画面遷移
            }
        });
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

