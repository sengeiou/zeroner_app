package com.iwown.ble_module.mtk_ble.leprofiles.fmpserver;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import coms.mediatek.wearableProfiles.WearableClientProfile;
import java.io.IOException;

public class FmpServerAlertService extends Service {
    private static final int ALERT_VOLUMN = 10;
    private static final boolean DBG = true;
    public static final String INTENT_STATE = "state";
    private static final String RINGTONE_NAME = "music/Alarm_Beep_03.ogg";
    private static final int STOP_RING = 1;
    /* access modifiers changed from: private */
    public static final String TAG = FmpServerAlertService.class.getSimpleName();
    private static final int UPDATE_ALERT = 0;
    private static final boolean VDBG = true;
    private static final long[] VIBRATE_PATTERN = {500, 500};
    /* access modifiers changed from: private */
    public AlertDialog mAlertDialog = null;
    OnAudioFocusChangeListener mAudioListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            Log.d(FmpServerAlertService.TAG, "onAudioFocusChange:" + focusChange);
            switch (focusChange) {
                case -2:
                case -1:
                    FmpServerAlertService.this.pauseRingAndVib();
                    return;
                case 0:
                case 2:
                    FmpServerAlertService.this.stopRingAndVib();
                    return;
                case 1:
                    FmpServerAlertService.this.replayRingAndVib();
                    return;
                default:
                    return;
            }
        }
    };
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d(FmpServerAlertService.TAG, "handleMessage: " + msg.what);
            switch (msg.what) {
                case 0:
                    switch (msg.arg1) {
                        case 0:
                            if (FmpServerAlertService.this.mAlertDialog.isShowing()) {
                                FmpServerAlertService.this.mAlertDialog.dismiss();
                            }
                            FmpServerAlertService.this.stopRingAndVib();
                            return;
                        case 1:
                        case 2:
                            if (!FmpServerAlertService.this.mAlertDialog.isShowing()) {
                                FmpServerAlertService.this.mAlertDialog.show();
                            }
                            FmpServerAlertService.this.applyRingAndVib();
                            return;
                        default:
                            Log.e(FmpServerAlertService.TAG, "Invalid level");
                            return;
                    }
                case 1:
                    FmpServerAlertService.this.stopRingAndVib();
                    return;
                default:
                    return;
            }
        }
    };
    private MediaPlayer mMediaPlayer = null;
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i(FmpServerAlertService.TAG, "PhoneStateListener, new state=" + state);
            if (state == 1) {
                FmpServerAlertService.this.updateAlertState(0);
            }
        }
    };
    private AssetFileDescriptor mRingtoneDescriptor;
    private TelephonyManager mTM = null;
    private Vibrator mVibrator = null;

    /* access modifiers changed from: private */
    public void updateAlertState(int state) {
        Message msg = this.mHandler.obtainMessage(0);
        msg.arg1 = state;
        msg.sendToTarget();
    }

    /* access modifiers changed from: private */
    public void stopRingVibrateOnly() {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate()");
        try {
            this.mRingtoneDescriptor = getAssets().openFd(RINGTONE_NAME);
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
        this.mTM = (TelephonyManager) getSystemService(UserConst.PHONE);
        this.mTM.listen(this.mPhoneStateListener, 32);
        initDialog();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            updateAlertState(intent.getIntExtra(INTENT_STATE, 0));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initDialog() {
        Log.d(TAG, "initDialog");
        this.mAlertDialog = new Builder(this).setTitle("").setCancelable(false).setMessage("").setPositiveButton("", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d(FmpServerAlertService.TAG, "Check clicked");
                switch (which) {
                }
                FmpServerAlertService.this.updateAlertState(0);
            }
        }).setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                Log.d(FmpServerAlertService.TAG, "onDismiss");
            }
        }).setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode != 25 && event.getKeyCode() != 24) {
                    return false;
                }
                FmpServerAlertService.this.stopRingVibrateOnly();
                return true;
            }
        }).create();
        this.mAlertDialog.getWindow().setType(WearableClientProfile.MSG_CHARAC_WRITE);
    }

    /* access modifiers changed from: private */
    public void pauseRingAndVib() {
        Log.d(TAG, "pauseRingAndVib");
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.pause();
        }
        if (this.mVibrator != null) {
            this.mVibrator = null;
        }
    }

    /* access modifiers changed from: private */
    public void replayRingAndVib() {
        Log.d(TAG, "replayRingAndVib");
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.start();
        }
        this.mVibrator = (Vibrator) getSystemService("vibrator");
    }

    /* access modifiers changed from: private */
    public void stopRingAndVib() {
        Log.d(TAG, "stopRingAndVib");
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            ((AudioManager) getSystemService("audio")).abandonAudioFocus(this.mAudioListener);
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        if (this.mVibrator != null) {
            this.mVibrator = null;
        }
    }

    /* access modifiers changed from: private */
    public void applyRingAndVib() {
        Log.d(TAG, "applyRingAndVib: ");
        stopRingAndVib();
        this.mMediaPlayer = new MediaPlayer();
        try {
            this.mMediaPlayer.setDataSource(this.mRingtoneDescriptor.getFileDescriptor(), this.mRingtoneDescriptor.getStartOffset(), this.mRingtoneDescriptor.getLength());
            this.mMediaPlayer.setLooping(true);
            AudioManager aM = (AudioManager) getSystemService("audio");
            aM.setStreamVolume(4, 10, 0);
            this.mMediaPlayer.setAudioStreamType(4);
            this.mMediaPlayer.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer player, int what, int extra) {
                    Log.d(FmpServerAlertService.TAG, "Media Player onError:" + what);
                    FmpServerAlertService.this.stopRingAndVib();
                    return false;
                }
            });
            this.mMediaPlayer.prepare();
            aM.requestAudioFocus(this.mAudioListener, 4, 2);
            this.mMediaPlayer.start();
        } catch (IllegalStateException e) {
            Log.e(TAG, "Media Player IllegalStateException");
            ThrowableExtension.printStackTrace(e);
        } catch (IOException e2) {
            Log.e(TAG, "Media Player IOException");
            ThrowableExtension.printStackTrace(e2);
        }
        this.mVibrator = (Vibrator) getSystemService("vibrator");
    }
}
