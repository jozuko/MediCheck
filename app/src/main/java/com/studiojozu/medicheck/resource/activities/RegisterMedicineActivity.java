package com.studiojozu.medicheck.resource.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.application.MedicineFinderService;
import com.studiojozu.medicheck.application.MedicineUnitRegisterService;
import com.studiojozu.medicheck.application.MedicineUnitSelectService;
import com.studiojozu.medicheck.domain.model.medicine.Medicine;
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType;
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameValidator;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit;
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueValidator;
import com.studiojozu.medicheck.domain.model.medicine.StartDatetimeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;
import com.studiojozu.medicheck.domain.model.medicine.TakeNumberValidator;
import com.studiojozu.medicheck.domain.model.setting.Timetable;
import com.studiojozu.medicheck.resource.uicomponent.dialog.DatePickerDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.InputDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.TakeIntervalInputDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.TimePickerDialogView;
import com.studiojozu.medicheck.resource.uicomponent.dialog.TimetableSelectorDialogView;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectArrayAdapter;
import com.studiojozu.medicheck.resource.uicomponent.listview.SingleSelectItem;
import com.studiojozu.medicheck.resource.uicomponent.template.TemplateHeaderView;

import java.io.Serializable;
import java.util.List;

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
    private TextView mTakeNumberTextView = null;
    @Nullable
    private TextView mMedicineUnitTextView = null;
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
    private TimetableSelectorDialogView mTimetableSelectorDialogView = null;
    @Nullable
    private TakeIntervalInputDialogView mTakeIntervalInputDialogView = null;

    @Nullable
    private MedicineFinderService mMedicineFinderService = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_register_medicine);

        getTimetableSelectorDialogView();
        getTakeIntervalInputDialogView();

        initHeaderParent();
        initPersonSelect();

        setClickListener();
        showMedicineInformation();
    }

    private void setClickListener() {
        setMedicineNameTextViewClickListener();
        setStartDayTextViewClickListener();
        setTimetablesTextViewClickListener();
        setTakeNumberTextViewClickListener();
        setMedicineUnitTextViewClickListener();
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
    private TextView getTakeNumberTextView() {
        if (mTakeNumberTextView == null)
            mTakeNumberTextView = findViewById(R.id.register_take_number_text);
        return mTakeNumberTextView;
    }

    @NonNull
    private TextView getMedicineUnitTextView() {
        if (mMedicineUnitTextView == null)
            mMedicineUnitTextView = findViewById(R.id.register_medicine_unit_text);
        return mMedicineUnitTextView;
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
        final Medicine medicine = getActivityParameterMedicine();

        getMedicineNameTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(R.string.input_title_medicine_name, InputDialogView.InputType.TEXT_SINGLE_LINE, medicine.getDisplayMedicineName(), new MedicineNameValidator(), new InputDialogView.OnCompletedCorrectInputListener() {
                    @Override
                    public void onCompleted(String data) {
                        medicine.setMedicineName(data);
                        showDisplayMedicineName();
                    }
                });
            }
        });
    }

    private void setStartDayTextViewClickListener() {
        getStartDayTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectStartDatetime();
            }
        });
    }

    private void selectStartDatetime() {
        final Medicine medicine = getActivityParameterMedicine();
        final StartDatetimeType startDatetime = medicine.getStartDatetime();

        showDatePickerDialog(
                startDatetime.getYear(),
                startDatetime.getMonth(),
                startDatetime.getDayOfMonth(),
                new DatePickerDialogView.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(final int year, final int monthOfYear, final int dayOfMonth) {
                        showTimePickerDialog(startDatetime.getHourOfDay(), startDatetime.getMinute(), new TimePickerDialogView.OnTimeSelectedListener() {
                            @Override
                            public void onTimeChanged(final int hourOfDay, final int minute) {
                                medicine.setStartDatetime(year, monthOfYear + 1, dayOfMonth, hourOfDay, minute);
                                showDisplayStartDay();
                            }
                        });
                    }
                });
    }

    private void setTimetablesTextViewClickListener() {
        getTimetablesTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimetableSelectorDialog();
            }
        });
    }

    private void setTakeNumberTextViewClickListener() {
        final Medicine medicine = getActivityParameterMedicine();

        getTakeNumberTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(R.string.input_title_medicine_take_number, InputDialogView.InputType.NUMBER_DECIMAL, medicine.getDisplayTakeNumber(), new TakeNumberValidator(), new InputDialogView.OnCompletedCorrectInputListener() {
                    @Override
                    public void onCompleted(String data) {
                        medicine.setTakeNumber(data);
                        showDisplayTakeNumber();
                    }
                });
            }
        });
    }

    private void setMedicineUnitTextViewClickListener() {
        getMedicineUnitTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMedicineUnitListDialog();
            }
        });
    }

    private void showMedicineUnitListDialog() {
        final MedicineUnitSelectService medicineUnitSelectService = new MedicineUnitSelectService(getApplicationContext(), true);
        final SingleSelectArrayAdapter medicineUnitListViewAdapter = medicineUnitSelectService.getSelectAdapter();

        showSingleSelectorDialog(medicineUnitListViewAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                List<SingleSelectItem> itemList = medicineUnitListViewAdapter.getItemList();
                if (itemList.size() == 0) return;

                SingleSelectItem item = itemList.get(position);
                MedicineUnit medicineUnit = (MedicineUnit) item.getTag();
                if (medicineUnit == null)
                    medicineUnit = new MedicineUnit();

                if (medicineUnit.getDisplayValue().equals("")) {
                    showAddNewMedicineUnitDialog(medicineUnit);
                    return;
                }

                getActivityParameterMedicine().setMedicineUnit(medicineUnit);
                showDisplayMedicineUnit();
            }
        }, null);
    }

    private void showAddNewMedicineUnitDialog(@NonNull final MedicineUnit medicineUnit) {
        showInputDialog(R.string.input_title_medicine_unit, InputDialogView.InputType.TEXT_SINGLE_LINE, "", new MedicineUnitValueValidator(), new InputDialogView.OnCompletedCorrectInputListener() {
            @Override
            public void onCompleted(String data) {
                medicineUnit.setMedicineUnitValue(data);

                MedicineUnitRegisterService medicineUnitRegisterService = new MedicineUnitRegisterService(getApplicationContext());
                medicineUnitRegisterService.register(medicineUnit);

                getActivityParameterMedicine().setMedicineUnit(medicineUnit);
                showDisplayMedicineUnit();
            }
        });
    }

    private void setIntervalTextViewClickListener() {
        getIntervalTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTakeIntervalInputDialog();
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
                Medicine medicine = getActivityParameterMedicine();
                medicine.setNeedAlarm(!medicine.isNeedAlarm());
                showDisplayNeedAlarm();
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
        return null;
    }

    private void showMedicineInformation() {
        showDisplayMedicineName();
        showDisplayStartDay();
        showDisplayTimetable();
        showDisplayTakeNumber();
        showDisplayMedicineUnit();
        showDisplayInterval();
        showDisplayDateNumber();
        showDisplayNeedAlarm();
    }

    private void showDisplayMedicineName() {
        Medicine medicine = getActivityParameterMedicine();
        getMedicineNameTextView().setText(medicine.getDisplayMedicineName());
    }

    private void showDisplayStartDay() {
        Medicine medicine = getActivityParameterMedicine();
        getStartDayTextView().setText(medicine.getDisplayStartDatetime());
    }

    private void showDisplayTimetable() {
        Medicine medicine = getActivityParameterMedicine();
        boolean isOneShot = medicine.isOneShowMedicine();

        if (isOneShot) {
            getTimetablesTextView().setText(R.string.button_one_shot);
            return;
        }
        getTimetablesTextView().setText(medicine.getDisplayTimetableList());
    }

    private void showDisplayTakeNumber() {
        Medicine medicine = getActivityParameterMedicine();
        getTakeNumberTextView().setText(medicine.getDisplayTakeNumber());
    }

    private void showDisplayMedicineUnit() {
        Medicine medicine = getActivityParameterMedicine();
        getMedicineUnitTextView().setText(medicine.getDisplayMedicineUnit());
    }

    private void showDisplayInterval() {
        Medicine medicine = getActivityParameterMedicine();
        getIntervalTextView().setText(medicine.getDisplayTakeInterval(getResources()));
    }

    private void showDisplayDateNumber() {
        Medicine medicine = getActivityParameterMedicine();
        getDateNumberTextView().setText(medicine.getDisplayDateNumber());
    }

    private void showDisplayNeedAlarm() {
        Medicine medicine = getActivityParameterMedicine();
        getAlarmCheckTextView().setChecked(medicine.isNeedAlarm());
    }

    private Medicine getActivityParameterMedicine() {
        if (mMedicine == null) {
            MedicineIdType medicineIdType = getActivityParameterMedicineId();
            mMedicine = getMedicineFinderService().findByIdNothingDefault(medicineIdType);
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
        if (serializable instanceof MedicineIdType)
            return (MedicineIdType) serializable;
        return new MedicineIdType();
    }

    private void getTimetableSelectorDialogView() {
        mTimetableSelectorDialogView = findViewById(R.id.timetable_dialog);
    }

    private void getTakeIntervalInputDialogView() {
        mTakeIntervalInputDialogView = findViewById(R.id.take_interval_input_dialog);
    }

    private void showTimetableSelectorDialog() {
        if (mTimetableSelectorDialogView == null) return;
        final Medicine medicine = getActivityParameterMedicine();

        mTimetableSelectorDialogView.setOnSelectedListener(new TimetableSelectorDialogView.OnSelectedListener() {
            @Override
            public void onSelected(boolean isOneShot, @NonNull List<Timetable> timetableList) {
                medicine.setOneShowMedicine(isOneShot);
                medicine.setTimetableList(isOneShot ? null : timetableList);
                showDisplayTimetable();
            }
        });
        mTimetableSelectorDialogView.showDialog(medicine);
    }

    private void showTakeIntervalInputDialog() {
        if (mTakeIntervalInputDialogView == null) return;
        final Medicine medicine = getActivityParameterMedicine();

        mTakeIntervalInputDialogView.setOnCompletedCorrectInputListener(new TakeIntervalInputDialogView.OnCompletedCorrectInputListener() {
            @Override
            public void onCompleted(int interval, TakeIntervalModeType.DateIntervalPattern dateIntervalPattern) {
                medicine.setTakeInterval(interval, dateIntervalPattern);
                showDisplayInterval();
            }
        });
        mTakeIntervalInputDialogView.showDialog(medicine);
    }
}
