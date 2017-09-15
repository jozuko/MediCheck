package com.studiojozu.medicheck.resource.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.application.MedicineFinderService;
import com.studiojozu.medicheck.resource.uicomponent.template.TemplateHeaderView;

public class RegisterMedicineChoiceActivity extends AActivity {

    private static final int REQUEST_CODE_SKIP_THIS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (skipThisActivity()) {
            return;
        }


        setContentView(R.layout.view_register_medicine_choice);
        initHeaderParent();
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

    private boolean skipThisActivity() {
        MedicineFinderService mMedicineFinderService = new MedicineFinderService(getApplicationContext());
        if (!mMedicineFinderService.existMedicine()) {
            Intent intent = new Intent(this, RegisterMedicineActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SKIP_THIS);
            return true;
        }

        return false;
    }
}
