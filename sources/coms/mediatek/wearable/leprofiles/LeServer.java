package coms.mediatek.wearable.leprofiles;

import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import java.util.List;

public interface LeServer {
    BluetoothGattServerCallback getGattServerCallback();

    List<BluetoothGattService> getHardCodeProfileServices();

    void setBluetoothGattServer(BluetoothGattServer bluetoothGattServer);
}
