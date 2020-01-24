package no.nordicsemi.android.dialog;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.device_firmware_upgrade.service.NewDfuService;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import no.nordicsemi.android.dialog.data.File;
import no.nordicsemi.android.dialog.data.Statics;

public abstract class BluetoothManager {
    public static final int END_SIGNAL = -33554432;
    public static final int REBOOT_SIGNAL = -50331648;
    static final String TAG = "BluetoothManager";
    int CS_GPIO = 5;
    int I2CDeviceAddress;
    int MISO_GPIO = 2;
    int MOSI_GPIO = 1;
    int SCK_GPIO = 0;
    int SCL_GPIO;
    int SDA_GPIO;
    NewDfuService activity;
    int blockCounter = 0;
    public Queue characteristicsQueue;
    int chunkCounter = -1;
    Context context;
    BluetoothDevice device;
    boolean endSignalSent = false;
    HashMap errors;
    File file;
    String fileName;
    boolean finished = false;
    boolean hasError = false;
    int imageBank;
    boolean lastBlock = false;
    boolean lastBlockSent = false;
    public Handler mHandler = new Handler(Looper.getMainLooper());
    int memoryType = 3;
    int patchBaseAddress;
    boolean preparedForLastBlock = false;
    boolean rebootsignalSent = false;
    protected int step;
    public int type;

    /* access modifiers changed from: protected */
    public abstract int getSpotaMemDev();

    public abstract void processStep(Intent intent);

    public BluetoothManager(Context context2) {
        this.context = context2;
        initErrorMap();
        this.characteristicsQueue = new ArrayDeque();
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean getError() {
        return this.hasError;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file2) throws IOException {
        this.file = file2;
        this.file.setType(this.type);
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName2) {
        this.fileName = fileName2;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    public void setDevice(BluetoothDevice device2) {
        this.device = device2;
    }

    public void setMemoryType(int memoryType2) {
        this.memoryType = memoryType2;
    }

    public void setPatchBaseAddress(int patchBaseAddress2) {
        this.patchBaseAddress = patchBaseAddress2;
    }

    public void setImageBank(int imageBank2) {
        this.imageBank = imageBank2;
    }

    public void setMISO_GPIO(int MISO_GPIO2) {
        this.MISO_GPIO = MISO_GPIO2;
    }

    public void setMOSI_GPIO(int MOSI_GPIO2) {
        this.MOSI_GPIO = MOSI_GPIO2;
    }

    public void setCS_GPIO(int CS_GPIO2) {
        this.CS_GPIO = CS_GPIO2;
    }

    public void setSCK_GPIO(int SCK_GPIO2) {
        this.SCK_GPIO = SCK_GPIO2;
    }

    public void setSCL_GPIO(int SCL_GPIO2) {
        this.SCL_GPIO = SCL_GPIO2;
    }

    public void setSDA_GPIO(int SDA_GPIO2) {
        this.SDA_GPIO = SDA_GPIO2;
    }

    public void setI2CDeviceAddress(int I2CDeviceAddress2) {
        this.I2CDeviceAddress = I2CDeviceAddress2;
    }

    public void enableNotifications() {
        Log.d(TAG, "- enableNotifications");
        KLog.d("- Enable notifications for SPOTA_SERV_STATUS characteristic");
        for (BluetoothGattService service : BluetoothGattSingleton.getGatt().getServices()) {
            KLog.d("  Found service: " + service.getUuid().toString());
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                KLog.d("  Found characteristic: " + characteristic.getUuid().toString());
                if (characteristic.getUuid().equals(Statics.SPOTA_SERV_STATUS_UUID)) {
                    KLog.d("*** Found SUOTA service");
                    BluetoothGattSingleton.getGatt().setCharacteristicNotification(characteristic, true);
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Statics.SPOTA_DESCRIPTOR_UUID);
                    if (descriptor != null) {
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                        BluetoothGattSingleton.getGatt().writeDescriptor(descriptor);
                    } else {
                        L.file("descriptor==null", 6);
                    }
                }
            }
        }
    }

    public void setSpotaMemDev() {
        BluetoothGattCharacteristic characteristic = BluetoothGattSingleton.getGatt().getService(Statics.SPOTA_SERVICE_UUID).getCharacteristic(Statics.SPOTA_MEM_DEV_UUID);
        int memType = getSpotaMemDev();
        characteristic.setValue(memType, 20, 0);
        BluetoothGattSingleton.getGatt().writeCharacteristic(characteristic);
        Log.d(TAG, "setSpotaMemDev: " + String.format("%#10x", new Object[]{Integer.valueOf(memType)}));
        Log.d(TAG, "Set SPOTA_MEM_DEV: " + String.format("%#10x", new Object[]{Integer.valueOf(memType)}));
    }

    private int getMemParamsSPI() {
        return (this.MISO_GPIO << 24) | (this.MOSI_GPIO << 16) | (this.CS_GPIO << 8) | this.SCK_GPIO;
    }

    private int getMemParamsI2C() {
        return (this.I2CDeviceAddress << 16) | (this.SCL_GPIO << 8) | this.SDA_GPIO;
    }

    public void setSpotaGpioMap() {
        int memInfoData = 0;
        boolean valid = false;
        switch (this.memoryType) {
            case 3:
                memInfoData = getMemParamsSPI();
                valid = true;
                break;
            case 4:
                memInfoData = getMemParamsI2C();
                valid = true;
                break;
        }
        if (valid) {
            KLog.d(TAG, "setSpotaGpioMap: " + String.format("%#10x", new Object[]{Integer.valueOf(memInfoData)}));
            KLog.d(TAG, "Set SPOTA_GPIO_MAP: " + String.format("%#10x", new Object[]{Integer.valueOf(memInfoData)}));
            BluetoothGattCharacteristic characteristic = BluetoothGattSingleton.getGatt().getService(Statics.SPOTA_SERVICE_UUID).getCharacteristic(Statics.SPOTA_GPIO_MAP_UUID);
            characteristic.setValue(memInfoData, 20, 0);
            BluetoothGattSingleton.getGatt().writeCharacteristic(characteristic);
            this.activity.updateProgressNotification(-2, 0, 0);
            return;
        }
        KLog.d("Set SPOTA_GPIO_MAP: Memory type not set.");
    }

    public void setPatchLength() {
        int blocksize = this.file.getFileBlockSize();
        if (this.lastBlock) {
            blocksize = this.file.getNumberOfBytes() % this.file.getFileBlockSize();
            this.preparedForLastBlock = true;
        }
        KLog.d(TAG, "setPatchLength: " + blocksize + " - " + String.format("%#4x", new Object[]{Integer.valueOf(blocksize)}));
        KLog.d(TAG, "Set SPOTA_PATCH_LENGTH: " + blocksize);
        BluetoothGattCharacteristic characteristic = BluetoothGattSingleton.getGatt().getService(Statics.SPOTA_SERVICE_UUID).getCharacteristic(Statics.SPOTA_PATCH_LEN_UUID);
        characteristic.setValue(blocksize, 18, 0);
        BluetoothGattSingleton.getGatt().writeCharacteristic(characteristic);
    }

    public float sendBlock() {
        final float progress = (((float) (this.blockCounter + 1)) / ((float) this.file.getNumberOfBlocks())) * 100.0f;
        if (!this.lastBlockSent) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothManager.this.sendProgressUpdate((int) progress, BluetoothManager.this.blockCounter, BluetoothManager.this.file.getNumberOfBlocks());
                }
            });
            KLog.d(TAG, "Sending block " + (this.blockCounter + 1) + " of " + this.file.getNumberOfBlocks());
            byte[][] block = this.file.getBlock(this.blockCounter);
            int i = this.chunkCounter + 1;
            this.chunkCounter = i;
            boolean lastChunk = false;
            if (this.chunkCounter == block.length - 1) {
                this.chunkCounter = -1;
                lastChunk = true;
            }
            byte[] chunk = block[i];
            final String message = "Sending chunk " + ((this.blockCounter * this.file.getChunksPerBlockCount()) + i + 1) + " of " + this.file.getTotalChunkCount() + " (with " + chunk.length + " bytes)";
            this.mHandler.post(new Runnable() {
                public void run() {
                    KLog.d(message);
                }
            });
            String systemLogMessage = "Sending block " + (this.blockCounter + 1) + ", chunk " + (i + 1) + ", blocksize: " + block.length + ", chunksize " + chunk.length;
            KLog.d(TAG, systemLogMessage);
            BluetoothGattCharacteristic characteristic = BluetoothGattSingleton.getGatt().getService(Statics.SPOTA_SERVICE_UUID).getCharacteristic(Statics.SPOTA_PATCH_DATA_UUID);
            characteristic.setValue(chunk);
            characteristic.setWriteType(1);
            boolean r = BluetoothGattSingleton.getGatt().writeCharacteristic(characteristic);
            KLog.d(TAG, "writeCharacteristic: " + r);
            if (lastChunk) {
                if (!this.lastBlock) {
                    this.blockCounter++;
                } else {
                    this.lastBlockSent = true;
                }
                if (this.blockCounter + 1 == this.file.getNumberOfBlocks()) {
                    this.lastBlock = true;
                }
                if (this.type == 2) {
                    this.lastBlockSent = true;
                }
            }
        }
        return progress;
    }

    public void sendEndSignal() {
        KLog.d("send SUOTA END command");
        BluetoothGattCharacteristic characteristic = BluetoothGattSingleton.getGatt().getService(Statics.SPOTA_SERVICE_UUID).getCharacteristic(Statics.SPOTA_MEM_DEV_UUID);
        characteristic.setValue(END_SIGNAL, 20, 0);
        BluetoothGattSingleton.getGatt().writeCharacteristic(characteristic);
        this.endSignalSent = true;
    }

    public void sendRebootSignal() {
        KLog.d("send SUOTA REBOOT command");
        BluetoothGattCharacteristic characteristic = BluetoothGattSingleton.getGatt().getService(Statics.SPOTA_SERVICE_UUID).getCharacteristic(Statics.SPOTA_MEM_DEV_UUID);
        characteristic.setValue(REBOOT_SIGNAL, 20, 0);
        BluetoothGattSingleton.getGatt().writeCharacteristic(characteristic);
        this.rebootsignalSent = true;
    }

    public void readNextCharacteristic() {
        if (this.characteristicsQueue.size() >= 1) {
            BluetoothGattSingleton.getGatt().readCharacteristic((BluetoothGattCharacteristic) this.characteristicsQueue.poll());
            Log.d(TAG, "readNextCharacteristic");
        }
    }

    /* access modifiers changed from: private */
    public void sendProgressUpdate(int progress, int blockCounter2, int numberOfBlocks) {
        this.activity.updateProgressNotification(progress, blockCounter2, numberOfBlocks);
    }

    public void disconnect() {
        try {
            BluetoothGattSingleton.getGatt().disconnect();
            BluetoothGattSingleton.getGatt().close();
            KLog.d("Disconnect from device");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.d("Error disconnecting from device");
        }
        try {
            if (this.file != null) {
                this.file.close();
            }
        } catch (Exception e2) {
        }
    }

    /* access modifiers changed from: protected */
    public void onSuccess() {
        this.finished = true;
        L.file("Upload completed", 6);
        this.activity.updateProgressNotification(-6, 0, 0);
        sendRebootSignal();
    }

    public void onError(int errorCode) {
        if (!this.hasError) {
            L.file("Error: " + ((String) this.errors.get(Integer.valueOf(errorCode))), 6);
            this.activity.terminateConnection(4098);
            disconnect();
            this.hasError = true;
        }
    }

    private void initErrorMap() {
        this.errors = new HashMap();
        this.errors.put(Integer.valueOf(3), "Forced exit of SPOTA service. See Table 1");
        this.errors.put(Integer.valueOf(4), "Patch Data CRC mismatch.");
        this.errors.put(Integer.valueOf(5), "Received patch Length not equal to PATCH_LEN characteristic value.");
        this.errors.put(Integer.valueOf(6), "External Memory Error. Writing to external device failed.");
        this.errors.put(Integer.valueOf(7), "Internal Memory Error. Not enough internal memory space for patch.");
        this.errors.put(Integer.valueOf(8), "Invalid memory device.");
        this.errors.put(Integer.valueOf(9), "Application error.");
        this.errors.put(Integer.valueOf(11), "Invalid image bank");
        this.errors.put(Integer.valueOf(12), "Invalid image header");
        this.errors.put(Integer.valueOf(13), "Invalid image size");
        this.errors.put(Integer.valueOf(14), "Invalid product header");
        this.errors.put(Integer.valueOf(15), "Same Image Error");
        this.errors.put(Integer.valueOf(16), " Failed to read from external memory device");
    }

    /* access modifiers changed from: protected */
    public void goToStep(int step2) {
        Intent i = new Intent();
        i.putExtra("step", step2);
        processStep(i);
    }
}
