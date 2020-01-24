package com.dmcbig.mediapicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.Toast;
import com.dmcbig.mediapicker.entity.Media;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TakePhotoActivity extends Activity {
    Uri NuriForFile;
    File mTmpFile = null;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {
            this.mTmpFile = createImageFile();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (VERSION.SDK_INT >= 24) {
            this.NuriForFile = FileProvider.getUriForFile(this, getPackageName() + ".dmc", this.mTmpFile);
            intent.putExtra("output", this.NuriForFile);
            startActivityForResult(intent, 100);
        } else if (this.mTmpFile == null || !this.mTmpFile.exists()) {
            Toast.makeText(this, "take error", 0).show();
            finish();
        } else {
            intent.putExtra("output", Uri.fromFile(this.mTmpFile));
            startActivityForResult(intent, 101);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<Media> medias = new ArrayList<>();
        if (requestCode == 100 || (requestCode == 101 && resultCode == -1 && this.mTmpFile.length() > 0)) {
            medias.add(new Media(this.mTmpFile.getPath(), this.mTmpFile.getName(), 0, 1, this.mTmpFile.length(), 0, ""));
        }
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT, medias);
        setResult(PickerConfig.RESULT_CODE, intent);
        finish();
    }

    private File createImageFile() throws IOException {
        return File.createTempFile("JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }
}
