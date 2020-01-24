package no.nordicsemi.android.dialog;

import android.content.Context;
import android.content.Intent;
import com.iwown.device_module.device_firmware_upgrade.service.NewDfuService;
import com.socks.library.KLog;

public class SuotaManager extends BluetoothManager {
    public static final int MEMORY_TYPE_EXTERNAL_I2C = 18;
    public static final int MEMORY_TYPE_EXTERNAL_SPI = 19;
    static final String TAG = "SuotaManager";
    public static final int TYPE = 1;

    public SuotaManager(Context context) {
        super(context);
        this.activity = (NewDfuService) context;
        this.type = 1;
    }

    public void processStep(Intent intent) {
        int newStep = intent.getIntExtra("step", -1);
        int error = intent.getIntExtra("error", -1);
        int memDevValue = intent.getIntExtra("memDevValue", -1);
        if (error >= 0) {
            onError(error);
        } else if (memDevValue >= 0) {
            processMemDevValue(memDevValue);
        }
        if (newStep >= 0) {
            this.step = newStep;
        } else {
            int intExtra = intent.getIntExtra("characteristic", -1);
            String stringExtra = intent.getStringExtra("value");
            readNextCharacteristic();
        }
        KLog.e(TAG, "step " + this.step);
        switch (this.step) {
            case 0:
                this.activity.startUp();
                this.step = -1;
                return;
            case 1:
                enableNotifications();
                return;
            case 2:
                setSpotaMemDev();
                return;
            case 3:
                setSpotaGpioMap();
                return;
            case 4:
                setPatchLength();
                return;
            case 5:
                if (!this.lastBlock) {
                    sendBlock();
                    return;
                } else if (!this.preparedForLastBlock) {
                    setPatchLength();
                    return;
                } else if (!this.lastBlockSent) {
                    sendBlock();
                    return;
                } else if (!this.endSignalSent) {
                    sendEndSignal();
                    return;
                } else {
                    onSuccess();
                    return;
                }
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public int getSpotaMemDev() {
        int memTypeBase = -1;
        switch (this.memoryType) {
            case 3:
                memTypeBase = 19;
                break;
            case 4:
                memTypeBase = 18;
                break;
        }
        return (memTypeBase << 24) | this.imageBank;
    }

    private void processMemDevValue(int memDevValue) {
        String stringValue = String.format("%#10x", new Object[]{Integer.valueOf(memDevValue)});
        KLog.e(TAG, "processMemDevValue() step: " + this.step + ", value: " + stringValue);
        switch (this.step) {
            case 2:
                if (memDevValue == 1) {
                    KLog.d("Set SPOTA_MEM_DEV: 0x1");
                    goToStep(3);
                    return;
                }
                onError(0);
                return;
            default:
                return;
        }
    }
}
