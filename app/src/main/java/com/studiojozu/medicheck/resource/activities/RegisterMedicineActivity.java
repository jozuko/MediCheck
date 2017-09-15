package com.studiojozu.medicheck.resource.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameValidator;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.resource.uicomponent.dialog.InputDialogView;
import com.studiojozu.medicheck.resource.uicomponent.template.TemplateHeaderView;

public class RegisterMedicineActivity extends APersonSelectActivity {

    @Nullable
    private TextView mMedicineNameTextView = null;
    @Nullable
    private TextView mStartDayTextView = null;
    @Nullable
    private TextView mTimetablesTextView = null;
    @Nullable
    private CheckedTextView mOneShotCheckTextView = null;
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

        initHeaderParent();
        initPersonSelect();

        getAllView();
        setClickListener();
    }

    private void getAllView() {
        mMedicineNameTextView = findViewById(R.id.register_medicine_name_text);
        mStartDayTextView = findViewById(R.id.register_start_day_text);
        mTimetablesTextView = findViewById(R.id.register_timetables_text);
        mOneShotCheckTextView = findViewById(R.id.register_one_shot_text);
        mTakeNumberTextView = findViewById(R.id.register_take_number_text);
        mIntervalTextView = findViewById(R.id.register_interval_text);
        mDateNumberTextView = findViewById(R.id.register_date_number_text);
        mUseCameraButton = findViewById(R.id.register_use_camera_button);
        mUseGalleryButton = findViewById(R.id.register_use_gallery_button);
        mAlarmCheckTextView = findViewById(R.id.register_alarm_text);
        mRegisterButton = findViewById(R.id.register_button);
    }

    private void setClickListener() {
        setMedicineNameTextViewClickListener();
        setStartDayTextViewClickListener();
        setTimetablesTextViewClickListener();
        setOneShotCheckTextViewClickListener();
        setTakeNumberTextViewClickListener();
        setIntervalTextViewClickListener();
        setDateNumberTextViewClickListener();
        setUseCameraButtonClickListener();
        setUseGalleryButtonClickListener();
        setAlarmCheckTextViewClickListener();
        setRegisterButtonClickListener();
    }

    private void setMedicineNameTextViewClickListener() {
        if (mMedicineNameTextView == null) return;
        mMedicineNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(R.string.input_title_medicine_name, new MedicineNameValidator(), new InputDialogView.OnCompletedCorrectInputListener() {
                    @Override
                    public void onCompleted(String data) {
                        mMedicineNameTextView.setText(data);
                    }
                });
            }
        });
    }

    private void setStartDayTextViewClickListener() {
        if (mStartDayTextView == null) return;
        mStartDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 開始日クリック処理
            }
        });
    }

    private void setTimetablesTextViewClickListener() {
        if (mTimetablesTextView == null) return;
        mTimetablesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Timetableクリック処理
            }
        });
    }

    private void setOneShotCheckTextViewClickListener() {
        if (mOneShotCheckTextView == null || mTimetablesTextView == null) return;
        mOneShotCheckTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOneShotCheckTextView.setChecked(!mOneShotCheckTextView.isChecked());
                mTimetablesTextView.setEnabled(!mOneShotCheckTextView.isChecked());
            }
        });
    }

    private void setTakeNumberTextViewClickListener() {
        if (mTakeNumberTextView == null) return;
        mTakeNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 服用数クリック処理
            }
        });
    }

    private void setIntervalTextViewClickListener() {
        if (mIntervalTextView == null) return;
        mIntervalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 服用間隔クリック処理
            }
        });
    }

    private void setDateNumberTextViewClickListener() {
        if (mDateNumberTextView == null) return;
        mDateNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 服用日数クリック処理
            }
        });
    }

    private void setUseCameraButtonClickListener() {
        if (mUseCameraButton == null) return;
        mUseCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO カメラ使用クリック処理
            }
        });
    }

    private void setUseGalleryButtonClickListener() {
        if (mUseGalleryButton == null) return;
        mUseGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO ギャラリー使用クリック処理
            }
        });
    }

    private void setAlarmCheckTextViewClickListener() {
        if (mAlarmCheckTextView == null) return;
        mAlarmCheckTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlarmCheckTextView.setChecked(!mAlarmCheckTextView.isChecked());
            }
        });
    }

    private void setRegisterButtonClickListener() {
        if (mRegisterButton == null) return;
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 登録ボタンクリック処理
            }
        });
    }

    private void initHeaderParent() {
        TemplateHeaderView templateHeaderView = findViewById(R.id.header_view);
        templateHeaderView.setParentActivity(this);
        templateHeaderView.setOnFinishingListener(new TemplateHeaderView.OnFinishingListener() {
            @Override
            public boolean onFinishing() {
                // TODO do something...
                return true;
            }
        });
    }

    @Nullable
    @Override
    OnSelectedPersonListener createOnSelectedPersonListener() {
        return new OnSelectedPersonListener() {
            @Override
            public void onSelectedPerson(@NonNull Person person) {
                Toast.makeText(getApplicationContext(), ((TextView) findViewById(R.id.person_select)).getText(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
