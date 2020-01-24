package no.nordicsemi.android.dialog;

import android.content.Context;
import android.content.Intent;

public class SpotaManager extends BluetoothManager {
    public static final int MEMORY_TYPE_EXTERNAL_I2C = 2;
    public static final int MEMORY_TYPE_EXTERNAL_SPI = 3;
    public static final int MEMORY_TYPE_RETENTION_RAM = 1;
    public static final int MEMORY_TYPE_SYSTEM_RAM = 0;
    public static final int TYPE = 2;

    public SpotaManager(Context context) {
        super(context);
    }

    public void processStep(Intent intent) {
    }

    /* access modifiers changed from: protected */
    public int getSpotaMemDev() {
        return 0;
    }
}
