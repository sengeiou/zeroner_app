package com.iwown.device_module.device_camera.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.device_camera.CameraUtil;
import com.iwown.device_module.device_camera.camera.CameraFragment;
import com.iwown.device_module.device_camera.camera.CameraHost.FailureReason;
import com.iwown.device_module.device_camera.camera.CameraUtils;
import com.iwown.device_module.device_camera.camera.PictureTransaction;
import com.iwown.device_module.device_camera.camera.SimpleCameraHost;
import com.iwown.device_module.device_camera.camera.SimpleCameraHost.Builder;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;

public class DemoCameraFragment extends CameraFragment implements OnSeekBarChangeListener, OnClickListener {
    private static final String KEY_USE_FFC = "com.commonsware.cwac.camera.demo.USE_FFC";
    private TextView autoAdjust;
    private MenuItem autoFocusItem = null;
    /* access modifiers changed from: private */
    public boolean can_take_photo = true;
    private MenuItem flashItem = null;
    String flashMode = null;
    private boolean hasTwoCameras;
    /* access modifiers changed from: private */
    public boolean isCamera = true;
    /* access modifiers changed from: private */
    public long lastFaceToast = 0;
    private CameraActivity mActivity;
    ImageView mBackBtn;
    FrameLayout mCamera;
    ImageView mLensTurnBtn;
    ImageView mShutterBtn;
    private MenuItem recordItem = null;
    View results;
    private MenuItem singleShotItem = null;
    /* access modifiers changed from: private */
    public boolean singleShotProcessing = false;
    private MenuItem stopRecordItem = null;
    /* access modifiers changed from: private */
    public boolean takePhotoFlag = false;
    private MenuItem takePictureItem = null;

    public interface Contract {
        boolean isSingleShotMode();

        void onLensTurnBtnClick(View view);

        void setSingleShotMode(boolean z);
    }

    class DemoCameraHost extends SimpleCameraHost implements FaceDetectionListener {
        private BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(KeyCodeAction.Action_Seleie_Data)) {
                }
            }
        };
        boolean supportsFaces = false;

        public DemoCameraHost(Context _ctxt) {
            super(_ctxt);
        }

        public boolean useFrontFacingCamera() {
            if (DemoCameraFragment.this.getArguments() == null) {
                return false;
            }
            return DemoCameraFragment.this.getArguments().getBoolean(DemoCameraFragment.KEY_USE_FFC);
        }

        public boolean useSingleShotMode() {
            return false;
        }

        public void saveImage(PictureTransaction xact, byte[] image) {
            DemoCameraFragment.this.takePhotoFlag = false;
            if (useSingleShotMode()) {
                DemoCameraFragment.this.singleShotProcessing = false;
            } else {
                super.saveImage(xact, image);
            }
        }

        public void autoFocusAvailable() {
            if (this.supportsFaces) {
                DemoCameraFragment.this.startFaceDetection();
            }
        }

        public void autoFocusUnavailable() {
            DemoCameraFragment.this.stopFaceDetection();
            if (this.supportsFaces) {
            }
        }

        public void onCameraFail(FailureReason reason) {
            super.onCameraFail(reason);
            DemoCameraFragment.this.isCamera = false;
            Toast.makeText(DemoCameraFragment.this.getActivity(), DemoCameraFragment.this.getString(R.string.device_module_camera_no_open), 1).show();
        }

        public Parameters adjustPreviewParameters(Parameters parameters) {
            DemoCameraFragment.this.flashMode = CameraUtils.findBestFlashModeMatch(parameters, "red-eye", "auto", "on");
            if (parameters.getMaxNumDetectedFaces() > 0) {
                this.supportsFaces = true;
            }
            return super.adjustPreviewParameters(parameters);
        }

        public void onFaceDetection(Face[] faces, Camera camera) {
            if (faces.length > 0) {
                long now = SystemClock.elapsedRealtime();
                if (now > DemoCameraFragment.this.lastFaceToast + 10000) {
                    DemoCameraFragment.this.lastFaceToast = now;
                }
            }
        }

        @TargetApi(16)
        public void onAutoFocus(boolean success, Camera camera) {
            super.onAutoFocus(success, camera);
            DemoCameraFragment.this.can_take_photo = true;
        }
    }

    public DemoCameraFragment() {
        boolean z = true;
        if (Camera.getNumberOfCameras() <= 1) {
            z = false;
        }
        this.hasTwoCameras = z;
    }

    public boolean isCamera() {
        return this.isCamera;
    }

    public static DemoCameraFragment newInstance(boolean useFFC) {
        DemoCameraFragment f = new DemoCameraFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_USE_FFC, useFFC);
        f.setArguments(args);
        return f;
    }

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
        setCameraHost(new Builder((SimpleCameraHost) new DemoCameraHost(getActivity())).useFullBleedPreview(true).build());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cameraView = super.onCreateView(inflater, container, savedInstanceState);
        this.results = inflater.inflate(R.layout.device_module_camera_fragment, container, false);
        ((ViewGroup) this.results.findViewById(R.id.camera)).addView(cameraView, 0);
        if (!this.hasTwoCameras) {
            this.mLensTurnBtn.setVisibility(8);
        }
        PermissionsUtils.handleCAMER(getActivity(), new PermissinCallBack() {
            public void callBackOk() {
            }

            public void callBackFial() {
                Toast.makeText(DemoCameraFragment.this.getActivity(), DemoCameraFragment.this.getString(R.string.device_module_camera_no_open), 1).show();
            }
        });
        this.mBackBtn = (ImageView) this.results.findViewById(R.id.back_btn);
        this.mCamera = (FrameLayout) this.results.findViewById(R.id.camera);
        this.mShutterBtn = (ImageView) this.results.findViewById(R.id.shutter_btn);
        this.mLensTurnBtn = (ImageView) this.results.findViewById(R.id.lens_turn_btn);
        this.mBackBtn.setOnClickListener(this);
        this.mCamera.setOnClickListener(this);
        this.mShutterBtn.setOnClickListener(this);
        this.mLensTurnBtn.setOnClickListener(this);
        initEvent();
        return this.results;
    }

    private void initEvent() {
        this.mCamera.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    DemoCameraFragment.this.can_take_photo = false;
                    DemoCameraFragment.this.autoFocus();
                }
                return false;
            }
        });
    }

    public void onResume() {
        this.mActivity = (CameraActivity) getActivity();
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        getActivity().invalidateOptionsMenu();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isSingleShotProcessing() {
        return this.singleShotProcessing;
    }

    public boolean isTakephotoFlag() {
        return this.takePhotoFlag;
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    /* access modifiers changed from: 0000 */
    public void setRecordingItemVisibility() {
        if (this.recordItem != null && getDisplayOrientation() != 0 && getDisplayOrientation() != 180) {
            this.recordItem.setVisible(false);
        }
    }

    /* access modifiers changed from: 0000 */
    public Contract getContract() {
        return (Contract) getActivity();
    }

    public void takeSimplePicture() {
        this.takePhotoFlag = true;
        takePicture(new PictureTransaction(getCameraHost()));
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.back_btn) {
            CameraUtil.sendCaneraCommand(false);
            this.mActivity.finish();
        } else if (i == R.id.shutter_btn) {
            if (this.isCamera) {
                takeSimplePicture();
            } else {
                Toast.makeText(getActivity(), getString(R.string.device_module_camera_no_open), 1).show();
            }
        } else if (i == R.id.lens_turn_btn) {
            getContract().onLensTurnBtnClick(this.mLensTurnBtn);
        }
    }
}
