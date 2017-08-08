package com.studiojozu.medicheck.other;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.image.Gallery;
import com.studiojozu.medicheck.log.Log;

import java.io.IOException;

/**
 * 写真に関するクラス
 */
public class PhotoService {

    public static final int REQUEST_CAMERA_IMAGE = 0;
    public static final int REQUEST_GALLERY_IMAGE = 1;

    @NonNull
    private final Activity _activity;
    @NonNull
    private final Log _log;
    @Nullable
    private Uri _uri = null;

    public PhotoService(@NonNull Activity activity) {
        _activity = activity;
        _log = new Log(PhotoService.class);
    }

    public PhotoService(@NonNull Activity activity, String uriString) {
        this(activity);
        _uri = Uri.parse(uriString);
    }

    public void captureFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        _activity.startActivityForResult(intent, REQUEST_CAMERA_IMAGE);
    }

    public void captureFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        _activity.startActivityForResult(intent, REQUEST_GALLERY_IMAGE);
    }

    public boolean onResponse(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) return false;
        if (requestCode == REQUEST_CAMERA_IMAGE) return onResponseCameraImage(resultCode, data);
        if (requestCode == REQUEST_GALLERY_IMAGE) return onResponseGalleryImage(resultCode, data);

        return false;
    }

    private boolean onResponseGalleryImage(int resultCode, @NonNull Intent data) {
        if (resultCode != Activity.RESULT_OK) return false;

        _uri = data.getData();
        return true;
    }

    private boolean onResponseCameraImage(int resultCode, @NonNull Intent data) {
        if (resultCode != Activity.RESULT_OK) return false;

        try {
            Bitmap bitmapData = (Bitmap) data.getExtras().get("data");
            _uri = new Gallery(_activity).register(bitmapData);
            return (_uri != null);
        }
        catch(IOException e) {
            _log.e(e);
            return false;
        }
    }

}
