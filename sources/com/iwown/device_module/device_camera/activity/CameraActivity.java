package com.iwown.device_module.device_camera.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.device_camera.CameraUtil;
import com.iwown.device_module.device_camera.activity.DemoCameraFragment.Contract;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.socks.library.KLog;
import java.util.Timer;
import java.util.TimerTask;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class CameraActivity extends AppCompatActivity implements Contract {
    private static final String STATE_LOCK_TO_LANDSCAPE = "lock_to_landscape";
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final String STATE_SINGLE_SHOT = "single_shot";
    /* access modifiers changed from: private */
    public static boolean flag = false;
    private String TAG;
    /* access modifiers changed from: private */
    public DemoCameraFragment current;
    private DemoCameraFragment ffc;
    private boolean hasTwoCameras;
    /* access modifiers changed from: private */
    public boolean isLockedToLandscape;
    private boolean isSelf;
    /* access modifiers changed from: private */
    public BroadcastReceiver mReceiver;
    private boolean singleShot;
    private DemoCameraFragment std;

    public CameraActivity() {
        this.hasTwoCameras = Camera.getNumberOfCameras() > 1;
        this.current = null;
        this.std = DemoCameraFragment.newInstance(false);
        this.ffc = DemoCameraFragment.newInstance(true);
        this.TAG = getClass().getSimpleName();
        this.isSelf = false;
        this.isLockedToLandscape = false;
        this.singleShot = false;
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (!intent.getAction().equals(KeyCodeAction.Action_Seleie_Data)) {
                    return;
                }
                if (!CameraActivity.this.current.isCamera()) {
                    Toast.makeText(CameraActivity.this, CameraActivity.this.getString(R.string.device_module_camera_no_open), 1).show();
                } else if (CameraActivity.this.current.isTakephotoFlag() || CameraActivity.flag) {
                    Toast.makeText(context, R.string.device_module_camera_picture, 0).show();
                } else {
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            CameraActivity.flag = false;
                        }
                    }, BootloaderScanner.TIMEOUT);
                    if (!CameraActivity.flag) {
                        CameraActivity.flag = true;
                        try {
                            CameraActivity.this.current.takeSimplePicture();
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_mirror_setting);
        PermissionsUtils.handleCAMER(this, new PermissinCallBack() {
            public void callBackOk() {
                CameraActivity.this.initView();
                IntentFilter filter = new IntentFilter();
                filter.addAction(KeyCodeAction.Action_Seleie_Data);
                CameraActivity.this.registerReceiver(CameraActivity.this.mReceiver, filter);
                CameraUtil.sendCaneraCommand(true);
            }

            public void callBackFial() {
                Toast.makeText(CameraActivity.this, CameraActivity.this.getString(R.string.device_module_camera_no_open), 1).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void initView() {
        if (!this.hasTwoCameras) {
            KLog.e(this.TAG, "手机没有双摄像头");
            return;
        }
        this.current = this.std;
        this.isSelf = false;
        getFragmentManager().beginTransaction().replace(R.id.container, this.current).commit();
    }

    public boolean isSingleShotMode() {
        return this.singleShot;
    }

    public void setSingleShotMode(boolean mode) {
        this.singleShot = mode;
    }

    public void onLensTurnBtnClick(View view) {
        this.isSelf = !this.isSelf;
        if (this.isSelf) {
            this.current = this.ffc;
        } else {
            this.current = this.std;
        }
        getFragmentManager().beginTransaction().replace(R.id.container, this.current).commit();
        findViewById(16908290).post(new Runnable() {
            public void run() {
                CameraActivity.this.current.lockToLandscape(CameraActivity.this.isLockedToLandscape);
            }
        });
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SINGLE_SHOT, isSingleShotMode());
        outState.putBoolean(STATE_LOCK_TO_LANDSCAPE, this.isLockedToLandscape);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        setSingleShotMode(savedInstanceState.getBoolean(STATE_SINGLE_SHOT));
        this.isLockedToLandscape = savedInstanceState.getBoolean(STATE_LOCK_TO_LANDSCAPE);
        if (this.current != null) {
            this.current.lockToLandscape(this.isLockedToLandscape);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 27 || this.current == null || this.current.isSingleShotProcessing()) {
            if (keyCode == 4) {
                CameraUtil.sendCaneraCommand(false);
            }
            return super.onKeyDown(keyCode, event);
        }
        this.current.takePicture();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }
}
