package com.studiojozu.medicheck.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.studiojozu.medicheck.R;

public class MenuActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
