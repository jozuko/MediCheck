package com.studiojozu.medicheck.resource.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.application.MedicineFinderService;
import com.studiojozu.medicheck.resource.uicomponent.template.TemplateHeaderView;

public class RegisterMedicineChoiceActivity extends AActivity {

    private static final int REQUEST_CODE_SKIP_THIS = 1;
    private static final int REQUEST_CODE_NEW_MEDICINE = 2;
    private static final int REQUEST_CODE_COPY_MEDICINE = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (skipThisActivity()) {
            return;
        }


        setContentView(R.layout.view_register_medicine_choice);
        initHeaderParent();
        setClickListenerAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_BACK_TO_MENU) {
            setResult(resultCode);
            finish();
            return;
        }

        if (requestCode == REQUEST_CODE_SKIP_THIS) {
            setResult(RESULT_OK);
            finish();
        }
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

    private void setClickListenerAll() {
        setNewMedicineButton();
        setCopyMedicineButton();
    }

    private void setNewMedicineButton() {
        findViewById(R.id.register_new_medicine_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNewRegisterMedicine(REQUEST_CODE_NEW_MEDICINE);
            }
        });
    }

    private void setCopyMedicineButton() {
        findViewById(R.id.register_new_medicine_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCopyMedicine(REQUEST_CODE_COPY_MEDICINE);
            }
        });
    }

    private boolean skipThisActivity() {
        MedicineFinderService mMedicineFinderService = new MedicineFinderService(getApplicationContext());
        if (!mMedicineFinderService.existMedicine()) {
            moveToNewRegisterMedicine(REQUEST_CODE_SKIP_THIS);
            return true;
        }

        return false;
    }

    private void moveToNewRegisterMedicine(int requestCode) {
        Intent intent = new Intent(this, RegisterMedicineActivity.class);
        startActivityForResult(intent, requestCode);
    }

    private void moveToCopyMedicine(int requestCode) {
        // TODO call activity what copy medicine.
    }
}
