package com.iwown.sport_module.zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.lib_common.views.TitleBar.TextAction;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.weight.S2WifiUtils;
import com.iwown.sport_module.ui.weight.activity.MunalBindMacActivity;
import com.iwown.sport_module.zxing.camera.CameraManager;
import com.iwown.sport_module.zxing.decoding.CaptureActivityHandler;
import com.iwown.sport_module.zxing.decoding.CaptureActivityHandler.DecodeCallback;
import com.iwown.sport_module.zxing.decoding.InactivityTimer;
import com.iwown.sport_module.zxing.utils.PhotosGetCodeUtils;
import com.iwown.sport_module.zxing.view.ViewfinderView;
import com.socks.library.KLog;
import com.tapadoo.alerter.Alerter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@Route(path = "/sport/CaptureActivity")
public class CaptureActivity extends BaseActivity implements Callback, DecodeCallback {
    private static final float BEEP_VOLUME = 0.1f;
    public static final String Bundle_ResultMode = "resultmode";
    public static final String Intent_Type_QrCodeMode = "qrcode";
    public static final int QRCODE_REQUEST = 100;
    public static final String RELATION_USER = "RELATION_USER";
    public static final String RESULT_QRCODE_STRING = "RESULT_QRCODE_STRING";
    public static final int ResultMode_Error = 0;
    public static final String S1MAc = "S1MAC";
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private static final long VIBRATE_DURATION = 200;
    private final int Album_Code = 500;
    private int QrCodeMode_BlueTooth_ConnectActivity = 100;
    private int ResultMode_Relation_apply = 200;
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private Button btnBack;
    private String characterSet;
    protected Activity context;
    private Vector<BarcodeFormat> decodeFormats;
    private Handler delayRestartScanQrCode = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (CaptureActivity.this.handler != null) {
                CaptureActivity.this.handler.restartPreviewAndDecode();
            }
        }
    };
    /* access modifiers changed from: private */
    public CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private int qrMode = this.QrCodeMode_BlueTooth_ConnectActivity;
    protected SurfaceView surfaceView;
    private boolean vibrate;
    private ViewfinderView viewfinderView;

    /* access modifiers changed from: protected */
    public void init(Activity context2, SurfaceView surfaceView2, ViewfinderView viewfinderView2) {
        this.context = context2;
        this.surfaceView = surfaceView2;
        this.viewfinderView = viewfinderView2;
        CameraManager.init(getApplication());
        this.qrMode = getIntent().getIntExtra(Intent_Type_QrCodeMode, this.QrCodeMode_BlueTooth_ConnectActivity);
        this.hasSurface = false;
        this.inactivityTimer = new InactivityTimer(this);
        findViewById(R.id.bt_m_bind).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CaptureActivity.this.startActivity(new Intent(CaptureActivity.this, MunalBindMacActivity.class));
            }
        });
        viewfinderView2.setAngleColor(getResources().getColor(R.color.qr_code_center_roud_line));
        EventBus.getDefault().register(this);
        setLeftBackTo();
        setTitleBarBG(getResources().getColor(R.color.qr_code_top_bg2));
        getTitleBar().addAction(new TextAction(getString(R.string.s1_album)) {
            public void performAction(View view) {
                CaptureActivity.this.go2Album();
            }
        });
    }

    /* access modifiers changed from: private */
    public void go2Album() {
        Intent innerIntent = new Intent();
        if (VERSION.SDK_INT < 19) {
            innerIntent.setAction("android.intent.action.GET_CONTENT");
        } else {
            innerIntent.setAction("android.intent.action.OPEN_DOCUMENT");
        }
        innerIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(innerIntent, "选择二维码图片"), 500);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_capture);
        init(this, (SurfaceView) findViewById(R.id.svCameraScan), (ViewfinderView) findViewById(R.id.vfvCameraScan));
    }

    /* access modifiers changed from: protected */
    public int getColorRes(int colorRes) {
        return getResources().getColor(colorRes);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = this.surfaceView.getHolder();
        if (this.hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(3);
        }
        this.decodeFormats = null;
        this.characterSet = null;
        this.playBeep = true;
        if (((AudioManager) getSystemService("audio")).getRingerMode() != 2) {
            this.playBeep = false;
        }
        initBeepSound();
        this.vibrate = true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.handler != null) {
            this.handler.quitSynchronously();
            this.handler = null;
        }
        CameraManager.get().closeDriver();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.inactivityTimer.shutdown();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void handleDecode(Result result, Bitmap barcode) {
        this.inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        parseResult(result);
    }

    private void parseResult(Result result) {
        KLog.d("result :" + result);
        byte[] rawBytes = result.getRawBytes();
        StringBuilder sb = new StringBuilder();
        if (rawBytes != null) {
            int length = rawBytes.length;
            for (int i = 0; i < length; i++) {
                sb.append(rawBytes[i] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        KLog.d("rawBytes :" + sb.toString());
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(this, R.string.sport_module_qrcode_qrscan_fail, 0).show();
        } else {
            String regex = "^([A-F0-9]){12}$";
            if (TextUtils.isEmpty(resultString) || !resultString.matches(regex)) {
                int resultMode = 0;
                String content = "";
                try {
                    int index = resultString.indexOf(":");
                    String type = resultString.substring(0, index);
                    content = resultString.substring(index + 1, resultString.length());
                    if (TextUtils.equals(type, RELATION_USER)) {
                        if (!JsonUtils.isFound("user", content)) {
                            KLog.d("检测二维码是否是关联账号申请");
                            Toast.makeText(this, R.string.sport_module_qrcode_qrscan_format_err3, 0).show();
                            this.delayRestartScanQrCode.sendEmptyMessageDelayed(0, 1000);
                            return;
                        }
                        resultMode = this.ResultMode_Relation_apply;
                    } else if (TextUtils.equals(type, S1MAc)) {
                        String[] split = content.trim().split("\\s");
                        String content2 = split[0];
                        KLog.e("  " + Arrays.toString(split) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + content2);
                        String regex1 = "^([A-F0-9]){12}$";
                        if (TextUtils.isEmpty(content2) || !content2.matches(regex1)) {
                            Alerter.create(this).hideIcon().setDuration(500).setText("未能识别二维码").show();
                            return;
                        } else {
                            S2WifiUtils.banS2MAc(GlobalUserDataFetcher.getCurrentUid(this).longValue(), content2, this);
                            return;
                        }
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("result", content);
                bundle.putInt(Bundle_ResultMode, resultMode);
                resultIntent.putExtras(bundle);
                setResult(100, resultIntent);
            } else if (TextUtils.isEmpty(resultString)) {
                Alerter.create(this).hideIcon().setDuration(500).setText("未能识别二维码").show();
                return;
            } else {
                S2WifiUtils.banS2MAc(UserConfig.getInstance().getNewUID(), resultString, this);
            }
        }
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            if (this.handler == null) {
                this.handler = new CaptureActivityHandler(this, this.decodeFormats, this.characterSet, this.viewfinderView, this);
            }
        } catch (IOException e) {
        } catch (RuntimeException e2) {
        }
    }

    public void drawViewfinder() {
        this.viewfinderView.drawViewfinder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (!this.hasSurface) {
            this.hasSurface = true;
            initCamera(holder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.hasSurface = false;
    }

    public Handler getHandler() {
        return this.handler;
    }

    private void initBeepSound() {
        if (this.playBeep && this.mediaPlayer == null) {
            setVolumeControlStream(3);
            this.mediaPlayer = new MediaPlayer();
            this.mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setOnCompletionListener(this.beepListener);
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                this.mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                this.mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (this.playBeep && this.mediaPlayer != null) {
            this.mediaPlayer.start();
        }
        if (this.vibrate) {
            ((Vibrator) getSystemService("vibrator")).vibrate(VIBRATE_DURATION);
        }
    }

    private boolean checkQrBluetoothFormat(String qrCode) {
        KLog.d("qrCode:" + qrCode);
        return qrCode.matches("[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}");
    }

    private boolean checkQrClubApplyForAdd(String qrCode) {
        try {
            if (JsonUtils.isFound("club", qrCode)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    private boolean checkQrUrlFormat(String qrCode) {
        if (qrCode.length() < 8) {
            return false;
        }
        if (qrCode.substring(0, 7).toLowerCase().equals("http://") || qrCode.substring(0, 8).toLowerCase().equals("https://")) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 500:
                    try {
                        parseResult(PhotosGetCodeUtils.getCodeResult(this, data.getData()));
                        return;
                    } catch (NotFoundException e) {
                        Alerter.create(this).hideIcon().setDuration(500).setText("未能识别二维码").show();
                        ThrowableExtension.printStackTrace(e);
                        return;
                    } catch (Exception e2) {
                        ThrowableExtension.printStackTrace(e2);
                        Alerter.create(this).hideIcon().setDuration(500).setText("error " + e2.getMessage()).show();
                        return;
                    }
                default:
                    return;
            }
        }
    }

    @Subscribe
    public void finishActivity(EventCaptureFinsh eventCaptureFinsh) {
        finish();
    }
}
