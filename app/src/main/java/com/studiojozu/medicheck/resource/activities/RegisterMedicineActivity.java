package com.studiojozu.medicheck.resource.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.application.MedicineFinderService;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameValidator;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.resource.uicomponent.dialog.InputDialogView;
import com.studiojozu.medicheck.resource.uicomponent.template.TemplateHeaderView;

import java.io.Serializable;

public class RegisterMedicineActivity extends APersonSelectActivity {

    final static String EXTRA_KEY_MEDICINE_ID = "medicine_id";

    @Nullable
    private Medicine mMedicine = null;
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
    private ImageView mPhotoImageView = null;
    @Nullable
    private Button mUseCameraButton = null;
    @Nullable
    private Button mUseGalleryButton = null;
    @Nullable
    private CheckedTextView mAlarmCheckTextView = null;
    @Nullable
    private Button mRegisterButton = null;

    @Nullable
    private MedicineFinderService mMedicineFinderService = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_register_medicine);

        initHeaderParent();
        initPersonSelect();

        setClickListener();
        showMedicineInformation();
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

    @NonNull
    private TextView getMedicineNameTextView() {
        if (mMedicineNameTextView == null)
            mMedicineNameTextView = findViewById(R.id.register_medicine_name_text);
        return mMedicineNameTextView;
    }

    @NonNull
    private TextView getStartDayTextView() {
        if (mStartDayTextView == null)
            mStartDayTextView = findViewById(R.id.register_start_day_text);
        return mStartDayTextView;
    }

    @NonNull
    private TextView getTimetablesTextView() {
        if (mTimetablesTextView == null)
            mTimetablesTextView = findViewById(R.id.register_timetables_text);
        return mTimetablesTextView;
    }

    @NonNull
    private CheckedTextView getOneShotCheckTextView() {
        if (mOneShotCheckTextView == null)
            mOneShotCheckTextView = findViewById(R.id.register_one_shot_text);
        return mOneShotCheckTextView;
    }

    @NonNull
    private TextView getTakeNumberTextView() {
        if (mTakeNumberTextView == null)
            mTakeNumberTextView = findViewById(R.id.register_take_number_text);
        return mTakeNumberTextView;
    }

    @NonNull
    private TextView getIntervalTextView() {
        if (mIntervalTextView == null)
            mIntervalTextView = findViewById(R.id.register_interval_text);
        return mIntervalTextView;
    }

    @NonNull
    private TextView getDateNumberTextView() {
        if (mDateNumberTextView == null)
            mDateNumberTextView = findViewById(R.id.register_date_number_text);
        return mDateNumberTextView;
    }

    private ImageView getPhotoImageView() {
        if (mPhotoImageView == null)
            mPhotoImageView = findViewById(R.id.register_medicine_photo_image);
        return mPhotoImageView;
    }

    @NonNull
    private Button getUseCameraButton() {
        if (mUseCameraButton == null)
            mUseCameraButton = findViewById(R.id.register_use_camera_button);
        return mUseCameraButton;
    }

    @NonNull
    private Button getUseGalleryButton() {
        if (mUseGalleryButton == null)
            mUseGalleryButton = findViewById(R.id.register_use_gallery_button);
        return mUseGalleryButton;
    }

    @NonNull
    private CheckedTextView getAlarmCheckTextView() {
        if (mAlarmCheckTextView == null)
            mAlarmCheckTextView = findViewById(R.id.register_alarm_text);
        return mAlarmCheckTextView;
    }

    @NonNull
    private Button getRegisterButton() {
        if (mRegisterButton == null)
            mRegisterButton = findViewById(R.id.register_button);
        return mRegisterButton;
    }

    @NonNull
    private MedicineFinderService getMedicineFinderService() {
        if (mMedicineFinderService == null)
            mMedicineFinderService = new MedicineFinderService(getApplicationContext());
        return mMedicineFinderService;
    }

    private void setMedicineNameTextViewClickListener() {
        getMedicineNameTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(R.string.input_title_medicine_name, new MedicineNameValidator(), new InputDialogView.OnCompletedCorrectInputListener() {
                    @Override
                    public void onCompleted(String data) {
                        getMedicineNameTextView().setText(data);
                    }
                });
            }
        });
    }

    private void setStartDayTextViewClickListener() {
        getStartDayTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 開始日クリック処理
            }
        });
    }

    private void setTimetablesTextViewClickListener() {
        getTimetablesTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Timetableクリック処理
            }
        });
    }

    private void setOneShotCheckTextViewClickListener() {
        getOneShotCheckTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOneShotCheckTextView().setChecked(!getOneShotCheckTextView().isChecked());
                getTimetablesTextView().setEnabled(!getOneShotCheckTextView().isChecked());
            }
        });
    }

    private void setTakeNumberTextViewClickListener() {
        getTakeNumberTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 服用数クリック処理
            }
        });
    }

    private void setIntervalTextViewClickListener() {
        getIntervalTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 服用間隔クリック処理
            }
        });
    }

    private void setDateNumberTextViewClickListener() {
        getDateNumberTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 服用日数クリック処理
            }
        });
    }

    private void setUseCameraButtonClickListener() {
        getUseCameraButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO カメラ使用クリック処理
            }
        });
    }

    private void setUseGalleryButtonClickListener() {
        getUseGalleryButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO ギャラリー使用クリック処理
            }
        });
    }

    private void setAlarmCheckTextViewClickListener() {
        getAlarmCheckTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlarmCheckTextView().setChecked(!getAlarmCheckTextView().isChecked());
            }
        });
    }

    private void setRegisterButtonClickListener() {
        getRegisterButton().setOnClickListener(new View.OnClickListener() {
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

    private void showMedicineInformation() {
        Medicine medicine = getActivityParameterMedicine();

        getMedicineNameTextView().setText(medicine.getDisplayMedicineName());
        getStartDayTextView().setText(medicine.getDisplayStartDatetime());
        getTimetablesTextView().setText(medicine.getDisplayTimetableList());
        getOneShotCheckTextView().setChecked(medicine.getTimetableList().isOneShotMedicine());
        getTakeNumberTextView().setText(medicine.getDisplayTakeNumber());
        getIntervalTextView().setText(medicine.getDisplayTakeInterval(getResources()));
        getDateNumberTextView().setText(medicine.getDisplayDateNumber());
    }

    private Medicine getActivityParameterMedicine() {
        if (mMedicine == null) {
            MedicineIdType medicineIdType = getActivityParameterMedicineId();
            mMedicine = getMedicineFinderService().findById(medicineIdType);
        }
        return mMedicine;
    }

    private MedicineIdType getActivityParameterMedicineId() {
        Intent activityParameter = getIntent();
        if (activityParameter == null)
            return new MedicineIdType();

        if (!activityParameter.hasExtra(EXTRA_KEY_MEDICINE_ID))
            return new MedicineIdType();

        Serializable serializable = activityParameter.getSerializableExtra(EXTRA_KEY_MEDICINE_ID);
        if (!(serializable instanceof Medicine))
            return new MedicineIdType();

        return (MedicineIdType) serializable;
    }
}
