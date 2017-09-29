package com.studiojozu.medicheck.application;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BitmapWriteServiceTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.studiojozu.medicheck", appContext.getPackageName());
    }

    @Test
    public void saveToNewPngFileAutoRecycle() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.i("DEBUG", appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        Log.i("DEBUG", appContext.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath());
    }
}
