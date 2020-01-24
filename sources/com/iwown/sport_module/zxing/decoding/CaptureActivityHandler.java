package com.iwown.sport_module.zxing.decoding;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.iwown.sport_module.R;
import com.iwown.sport_module.zxing.activity.CaptureActivity;
import com.iwown.sport_module.zxing.camera.CameraManager;
import com.iwown.sport_module.zxing.view.ViewfinderResultPointCallback;
import com.iwown.sport_module.zxing.view.ViewfinderView;
import java.util.Vector;

public final class CaptureActivityHandler extends Handler {
    private static final String TAG = CaptureActivityHandler.class.getSimpleName();
    private final Activity activity;
    private DecodeCallback decodeCallback;
    private final DecodeThread decodeThread;
    private State state = State.SUCCESS;

    public interface DecodeCallback {
        void drawViewfinder();

        void handleDecode(Result result, Bitmap bitmap);
    }

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CaptureActivityHandler(CaptureActivity activity2, Vector<BarcodeFormat> decodeFormats, String characterSet, ViewfinderView viewfinderView, DecodeCallback decodeCallback2) {
        this.activity = activity2;
        this.decodeCallback = decodeCallback2;
        this.decodeThread = new DecodeThread(activity2, decodeFormats, characterSet, new ViewfinderResultPointCallback(viewfinderView));
        this.decodeThread.start();
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    public void handleMessage(Message message) {
        Bitmap barcode;
        if (message.what == R.id.auto_focus) {
            if (this.state == State.PREVIEW) {
                CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
            }
        } else if (message.what == R.id.restart_preview) {
            Log.d(TAG, "Got restart preview message");
            restartPreviewAndDecode();
        } else if (message.what == R.id.decode_succeeded) {
            Log.d(TAG, "Got decode succeeded message");
            this.state = State.SUCCESS;
            Bundle bundle = message.getData();
            if (bundle == null) {
                barcode = null;
            } else {
                barcode = (Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);
            }
            this.decodeCallback.handleDecode((Result) message.obj, barcode);
        } else if (message.what == R.id.decode_failed) {
            this.state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
        } else if (message.what == R.id.return_scan_result) {
            Log.d(TAG, "Got return scan result message");
            this.activity.setResult(-1, (Intent) message.obj);
            this.activity.finish();
        } else if (message.what == R.id.launch_product_query) {
            Log.d(TAG, "Got product query message");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String) message.obj));
            intent.addFlags(524288);
            this.activity.startActivity(intent);
        }
    }

    public void quitSynchronously() {
        this.state = State.DONE;
        CameraManager.get().stopPreview();
        Message.obtain(this.decodeThread.getHandler(), R.id.quit).sendToTarget();
        try {
            this.decodeThread.join();
        } catch (InterruptedException e) {
        }
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    public void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
            CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
            this.decodeCallback.drawViewfinder();
        }
    }
}
