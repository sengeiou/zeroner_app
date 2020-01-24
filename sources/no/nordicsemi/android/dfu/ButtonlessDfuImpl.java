package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.RemoteDfuException;
import no.nordicsemi.android.dfu.internal.exception.UnknownResponseException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;
import no.nordicsemi.android.error.SecureDfuError;

abstract class ButtonlessDfuImpl extends BaseButtonlessDfuImpl {
    private static final int DFU_STATUS_SUCCESS = 1;
    private static final byte[] OP_CODE_ENTER_BOOTLOADER = {1};
    private static final int OP_CODE_ENTER_BOOTLOADER_KEY = 1;
    private static final int OP_CODE_RESPONSE_CODE_KEY = 32;

    /* access modifiers changed from: protected */
    public abstract BluetoothGattCharacteristic getButtonlessDfuCharacteristic();

    /* access modifiers changed from: protected */
    public abstract int getResponseType();

    /* access modifiers changed from: protected */
    public abstract boolean shouldScanForBootloader();

    ButtonlessDfuImpl(Intent intent, DfuBaseService service) {
        super(intent, service);
    }

    public void performDfu(Intent intent) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        byte[] response;
        this.mProgressInfo.setProgress(-2);
        this.mService.waitFor(1000);
        BluetoothGatt gatt = this.mGatt;
        this.mService.sendLogBroadcast(15, "Application with buttonless update found");
        this.mService.sendLogBroadcast(1, "Jumping to the DFU Bootloader...");
        BluetoothGattCharacteristic characteristic = getButtonlessDfuCharacteristic();
        int type = getResponseType();
        enableCCCD(characteristic, getResponseType());
        this.mService.sendLogBroadcast(10, (type == 2 ? "Indications" : "Notifications") + " enabled");
        this.mService.waitFor(1000);
        try {
            this.mProgressInfo.setProgress(-3);
            logi("Sending Enter Bootloader (Op Code = 1)");
            writeOpCode(characteristic, OP_CODE_ENTER_BOOTLOADER, true);
            this.mService.sendLogBroadcast(10, "Enter bootloader sent (Op Code = 1)");
            try {
                response = readNotificationResponse();
            } catch (DeviceDisconnectedException e) {
                response = this.mReceivedData;
            }
            if (response != null) {
                int status = getStatusCode(response, 1);
                logi("Response received (Op Code = " + response[1] + ", Status = " + status + ")");
                this.mService.sendLogBroadcast(10, "Response received (Op Code = " + response[1] + ", Status = " + status + ")");
                if (status != 1) {
                    throw new RemoteDfuException("Device returned error after sending Enter Bootloader", status);
                }
                this.mService.waitUntilDisconnected();
            } else {
                logi("Device disconnected before receiving notification");
            }
            this.mService.sendLogBroadcast(5, "Disconnected by the remote device");
            finalize(intent, false, shouldScanForBootloader());
        } catch (UnknownResponseException e2) {
            loge(e2.getMessage());
            this.mService.sendLogBroadcast(20, e2.getMessage());
            this.mService.terminateConnection(gatt, DfuBaseService.ERROR_INVALID_RESPONSE);
        } catch (RemoteDfuException e3) {
            int error = e3.getErrorNumber() | 2048;
            loge(e3.getMessage());
            this.mService.sendLogBroadcast(20, String.format("Remote DFU error: %s", new Object[]{SecureDfuError.parseButtonlessError(error)}));
            this.mService.terminateConnection(gatt, error | 8192);
        }
    }

    private int getStatusCode(byte[] response, int request) throws UnknownResponseException {
        if (response != null && response.length >= 3 && response[0] == 32 && response[1] == request && (response[2] == 1 || response[2] == 2 || response[2] == 4)) {
            return response[2];
        }
        throw new UnknownResponseException("Invalid response received", response, 32, request);
    }
}
