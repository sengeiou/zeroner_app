package com.iwown.device_module.device_camera.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaScannerConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.cameraview.CameraView;
import com.google.android.cameraview.CameraView.Callback;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.device_camera.CameraUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.socks.library.KLog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity2 extends AppCompatActivity {
    /* access modifiers changed from: private */
    public static final String[] SCAN_TYPES = {"image/jpeg"};
    private ImageView backBtn;
    /* access modifiers changed from: private */
    public CameraView camera2;
    private ImageView lensTurnBtn;
    private Handler mBackgroundHandler;
    private Callback mCallback = new Callback() {
        public void onCameraOpened(CameraView cameraView) {
            KLog.d("onCameraOpened");
        }

        public void onCameraClosed(CameraView cameraView) {
            KLog.d("onCameraClosed");
        }

        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            KLog.d("onPictureTaken " + data.length);
            CameraActivity2.this.getBackgroundHandler().post(new Runnable() {
                public void run() {
                    File photo = CameraActivity2.this.getPhotoPath();
                    if (photo.exists()) {
                        photo.delete();
                    }
                    try {
                        FileOutputStream fos = new FileOutputStream(photo.getPath());
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        bos.write(data);
                        bos.flush();
                        fos.getFD().sync();
                        bos.close();
                        MediaScannerConnection.scanFile(CameraActivity2.this, new String[]{photo.getPath()}, CameraActivity2.SCAN_TYPES, null);
                    } catch (IOException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            });
        }
    };
    private OnClickListener mOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            int i = 0;
            int id = v.getId();
            if (id == R.id.take_picture) {
                if (CameraActivity2.this.camera2 != null) {
                    CameraActivity2.this.camera2.takePicture();
                }
            } else if (id == R.id.back_btn) {
                CameraUtil.sendCaneraCommand(false);
                CameraActivity2.this.finish();
            } else if (id != R.id.camera_2 && id == R.id.lens_turn_btn && CameraActivity2.this.camera2 != null) {
                int facing = CameraActivity2.this.camera2.getFacing();
                CameraView access$200 = CameraActivity2.this.camera2;
                if (facing != 1) {
                    i = 1;
                }
                access$200.setFacing(i);
            }
        }
    };
    /* access modifiers changed from: private */
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(KeyCodeAction.Action_Seleie_Data)) {
                return;
            }
            if (new DateUtil().getUnixTimestamp() - CameraActivity2.this.time > 5) {
                try {
                    CameraActivity2.this.time = new DateUtil().getUnixTimestamp();
                    CameraActivity2.this.camera2.takePicture();
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            } else {
                Toast.makeText(context, R.string.device_module_camera_picture, 0).show();
            }
        }
    };
    private File photoDirectory = null;
    private ImageView takePicture;
    /* access modifiers changed from: private */
    public long time;
    private View view;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = LayoutInflater.from(this).inflate(R.layout.device_module_activity_mirror_setting_2, null);
        setContentView(this.view);
        PermissionsUtils.handleCAMER(this, new PermissinCallBack() {
            public void callBackOk() {
                CameraActivity2.this.initView();
                IntentFilter filter = new IntentFilter();
                filter.addAction(KeyCodeAction.Action_Seleie_Data);
                CameraActivity2.this.registerReceiver(CameraActivity2.this.mReceiver, filter);
                CameraUtil.sendCaneraCommand(true);
            }

            public void callBackFial() {
                Toast.makeText(CameraActivity2.this, CameraActivity2.this.getString(R.string.device_module_camera_no_open), 1).show();
            }
        });
        initView();
    }

    /* access modifiers changed from: private */
    public void initView() {
        this.camera2 = (CameraView) findViewById(R.id.camera_2);
        this.backBtn = (ImageView) findViewById(R.id.back_btn);
        this.takePicture = (ImageView) findViewById(R.id.take_picture);
        this.lensTurnBtn = (ImageView) findViewById(R.id.lens_turn_btn);
        this.camera2.setOnClickListener(this.mOnClickListener);
        this.backBtn.setOnClickListener(this.mOnClickListener);
        this.takePicture.setOnClickListener(this.mOnClickListener);
        this.lensTurnBtn.setOnClickListener(this.mOnClickListener);
        if (this.camera2 != null) {
            this.camera2.addCallback(this.mCallback);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        PermissionsUtils.handleCAMER(this, new PermissinCallBack() {
            public void callBackOk() {
                CameraActivity2.this.camera2.start();
            }

            public void callBackFial() {
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.camera2.stop();
        super.onPause();
    }

    /* access modifiers changed from: private */
    public Handler getBackgroundHandler() {
        if (this.mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            this.mBackgroundHandler = new Handler(thread.getLooper());
        }
        return this.mBackgroundHandler;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            CameraUtil.sendCaneraCommand(false);
        }
        return super.onKeyDown(keyCode, event);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mReceiver);
        if (this.mBackgroundHandler != null) {
            if (VERSION.SDK_INT >= 18) {
                this.mBackgroundHandler.getLooper().quitSafely();
            } else {
                this.mBackgroundHandler.getLooper().quit();
            }
            this.mBackgroundHandler = null;
        }
    }

    /* access modifiers changed from: protected */
    public File getPhotoPath() {
        File dir = getPhotoDirectory();
        dir.mkdirs();
        return new File(dir, getPhotoFilename());
    }

    /* access modifiers changed from: protected */
    public String getPhotoFilename() {
        return "Photo_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".jpg";
    }

    /* access modifiers changed from: protected */
    public File getPhotoDirectory() {
        if (this.photoDirectory == null) {
            initPhotoDirectory();
        }
        return this.photoDirectory;
    }

    private void initPhotoDirectory() {
        this.photoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }
}
