package com.studiojozu.medicheck.port.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExternalStorageModelTest {

    @Test
    public void createNewImageFile() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ExternalStorageModel externalStorageModel = new ExternalStorageModel(appContext);
        File file = externalStorageModel.createNewImageFile();
        assertTrue(file.exists());
        assertTrue(file.canRead());
        assertTrue(file.canWrite());
    }
}
