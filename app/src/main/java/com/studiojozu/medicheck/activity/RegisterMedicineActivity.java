package com.studiojozu.medicheck.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.activity.uicomponent.dialog.InputDialogView;
import com.studiojozu.medicheck.activity.validator.medicine.MedicineNameValidator;

/**
 * 今日のお薬を一覧表示する。
 */
public class RegisterMedicineActivity extends AActivity implements View.OnClickListener, TemplateHeaderView.TemplateHeaderIncludeActivity, TemplateParsonSelectView.TemplateParsonSelectIncludeActivity {

    @Nullable
    private TemplateParsonSelectView mTemplateParsonSelectView = null;
    @Nullable
    private TextView mMedicineNameTextView = null;
    @Nullable
    private TextView mStartDayTextView = null;
    @Nullable
    private TextView mTimetablesTextView = null;
    @Nullable
    private TextView mTakeNumberTextView = null;
    @Nullable
    private TextView mIntervalTextView = null;
    @Nullable
    private TextView mDateNumberTextView = null;
    @Nullable
    private Button mUseCameraButton = null;
    @Nullable
    private Button mUseGalleryButton = null;
    @Nullable
    private CheckedTextView mAlarmCheckTextView = null;
    @Nullable
    private Button mRegisterButton = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_register_medicine);

        initTemplateHeaderView();
        initTemplateParsonSelectView();
        getAllView();

        setClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        try {
            if (mTemplateParsonSelectView != null)
                mTemplateParsonSelectView.recycle();
        } finally {
            super.onDestroy();
        }
    }

    private void getAllView() {
        mMedicineNameTextView = findViewById(R.id.register_medicine_name_text);
        mStartDayTextView = findViewById(R.id.register_start_day_text);
        mTimetablesTextView = findViewById(R.id.register_timetables_text);
        mTakeNumberTextView = findViewById(R.id.register_take_number_text);
        mIntervalTextView = findViewById(R.id.register_interval_text);
        mDateNumberTextView = findViewById(R.id.register_date_number_text);
        mUseCameraButton = findViewById(R.id.register_use_camera_button);
        mUseGalleryButton = findViewById(R.id.register_use_gallery_button);
        mAlarmCheckTextView = findViewById(R.id.register_alarm_text);
        mRegisterButton = findViewById(R.id.register_button);
    }

    private void setClickListener() {
        if (mMedicineNameTextView != null) mMedicineNameTextView.setOnClickListener(this);
        if (mStartDayTextView != null) mStartDayTextView.setOnClickListener(this);
        if (mTimetablesTextView != null) mTimetablesTextView.setOnClickListener(this);
        if (mTakeNumberTextView != null) mTakeNumberTextView.setOnClickListener(this);
        if (mIntervalTextView != null) mIntervalTextView.setOnClickListener(this);
        if (mDateNumberTextView != null) mDateNumberTextView.setOnClickListener(this);
        if (mUseCameraButton != null) mUseCameraButton.setOnClickListener(this);
        if (mUseGalleryButton != null) mUseGalleryButton.setOnClickListener(this);
        if (mAlarmCheckTextView != null) mAlarmCheckTextView.setOnClickListener(this);
        if (mRegisterButton != null) mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View view) {
        onClickMedicineNameText(view);
        onClickStartDayText(view);
        onClickTimetablesText(view);
        onClickTakeNumberText(view);
        onClickIntervalText(view);
        onClickDateNumberText(view);
        onClickUseCameraButton(view);
        onClickUseGalleryButton(view);
        onClickAlarmText(view);
        onClickRegisterButton(view);
    }

    private void onClickMedicineNameText(@NonNull View view) {
        if (!view.equals(mMedicineNameTextView)) return;

        showInputDialog(R.string.input_title_medicine_name, new MedicineNameValidator(), new InputDialogView.OnCompletedCorrectInputListener() {
            @Override
            public void onCompleted(String data) {
                mMedicineNameTextView.setText(data);
            }
        });
    }

    private void onClickStartDayText(@NonNull View view) {
        if (!view.equals(mStartDayTextView)) return;
        // TODO 開始日クリック処理
    }

    private void onClickTimetablesText(@NonNull View view) {
        if (!view.equals(mTimetablesTextView)) return;
        // TODO Timetableクリック処理
    }

    private void onClickTakeNumberText(@NonNull View view) {
        if (!view.equals(mTakeNumberTextView)) return;
        // TODO 服用数クリック処理
    }

    private void onClickIntervalText(@NonNull View view) {
        if (!view.equals(mIntervalTextView)) return;
        // TODO 服用間隔クリック処理
    }

    private void onClickDateNumberText(@NonNull View view) {
        if (!view.equals(mDateNumberTextView)) return;
        // TODO 服用日数クリック処理
    }

    private void onClickUseCameraButton(@NonNull View view) {
        if (!view.equals(mUseCameraButton)) return;
        // TODO カメラ使用クリック処理
    }

    private void onClickUseGalleryButton(@NonNull View view) {
        if (!view.equals(mUseGalleryButton)) return;
        // TODO ギャラリー使用クリック処理
    }

    private void onClickAlarmText(@NonNull View view) {
        if (!view.equals(mAlarmCheckTextView)) return;

        mAlarmCheckTextView.setChecked(!mAlarmCheckTextView.isChecked());
    }

    private void onClickRegisterButton(@NonNull View view) {
        if (!view.equals(mRegisterButton)) return;
        // TODO 登録ボタンクリック処理
    }

    @Override
    public void initTemplateHeaderView() {
        new TemplateHeaderView(this, R.mipmap.ic_register_medicine, R.string.button_register_medicine);
    }

    @Override
    public void initTemplateParsonSelectView() {
        mTemplateParsonSelectView = new TemplateParsonSelectView(this);
        mTemplateParsonSelectView.init();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public boolean beforeFinish() {
        // TODO 入力内容がキャンセルされますが、よろしいですか？アラートダイアログを表示したい。

        return true;
    }
}
