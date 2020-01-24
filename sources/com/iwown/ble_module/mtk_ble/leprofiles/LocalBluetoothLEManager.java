package com.iwown.ble_module.mtk_ble.leprofiles;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.leprofiles.basclient.BatteryChangeListener;
import com.iwown.ble_module.mtk_ble.leprofiles.fmpclient.FmpClientStatusRegister;
import com.iwown.ble_module.mtk_ble.leprofiles.fmpclient.FmpGattClient;
import com.iwown.ble_module.mtk_ble.leprofiles.fmpserver.FmpGattServer;
import coms.mediatek.wearable.leprofiles.LeServer;
import coms.mediatek.wearable.leprofiles.LeServerManager;
import java.util.ArrayList;

public class LocalBluetoothLEManager {
    private static final String TAG = "LocalBluetoothLEManager";
    private static LocalBluetoothLEManager sInstance = null;
    private MTKBle mBasClient = null;
    private Context mContext;
    private FmpGattClient mFmpClient = null;
    private LeServer mFmpServer = null;

    public static synchronized LocalBluetoothLEManager getInstance() {
        LocalBluetoothLEManager localBluetoothLEManager;
        synchronized (LocalBluetoothLEManager.class) {
            if (sInstance == null) {
                sInstance = new LocalBluetoothLEManager();
            }
            localBluetoothLEManager = sInstance;
        }
        return localBluetoothLEManager;
    }

    public void init(Context context) {
        this.mContext = context;
        if (VERSION.SDK_INT >= 18) {
            Log.d(TAG, "supported profiles = " + 7);
            initFwk(this.mContext, 7);
        }
    }

    private LocalBluetoothLEManager() {
    }

    private void initFwk(Context context, int profiles) {
        ArrayList<LeServer> leServerList = new ArrayList<>();
        if ((profiles & 2) > 0) {
            this.mFmpServer = FmpGattServer.getInstance(context);
            leServerList.add(this.mFmpServer);
        }
        LeServerManager.addLeServers(context, leServerList);
        if ((profiles & 1) > 0) {
            this.mFmpClient = FmpGattClient.getInstance();
        }
        if ((profiles & 4) > 0) {
            this.mBasClient = MTKBle.getInstance();
        }
    }

    public void findTargetDevice(int level) {
        if (this.mFmpClient != null) {
            this.mFmpClient.findTarget(level);
            if (level == 2 || level == 1) {
                FmpClientStatusRegister.getInstance().setFindMeStatus(2);
            } else {
                FmpClientStatusRegister.getInstance().setFindMeStatus(1);
            }
        }
    }

    public void registerBatteryLevelListener(BatteryChangeListener listener) {
        if (this.mBasClient != null) {
            this.mBasClient.registerBatteryChangeListener(listener);
        }
    }

    public void unregisterBatteryLevelListener() {
        if (this.mBasClient != null) {
            this.mBasClient.unregisterBatteryChangeListener();
        }
    }
}
