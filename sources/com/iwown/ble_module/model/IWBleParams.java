package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class IWBleParams {
    int Conn_Interval_Max = -1;
    int Conn_Interval_Min = -1;
    int Conn_Params_Flag = -1;
    int Conn_Sla_latency = -1;
    int Conn_Sup_Timeout = -1;
    int Peer_HandShake = -1;
    boolean newProtocol = false;

    public int getPeer_HandShake() {
        return this.Peer_HandShake;
    }

    public void setPeer_HandShake(int peer_HandShake) {
        this.Peer_HandShake = peer_HandShake;
    }

    public int getConn_Params_Flag() {
        return this.Conn_Params_Flag;
    }

    public void setConn_Params_Flag(int conn_Params_Flag) {
        this.Conn_Params_Flag = conn_Params_Flag;
    }

    public int getConn_Interval_Min() {
        return this.Conn_Interval_Min;
    }

    public void setConn_Interval_Min(int conn_Interval_Min) {
        this.Conn_Interval_Min = conn_Interval_Min;
    }

    public int getConn_Interval_Max() {
        return this.Conn_Interval_Max;
    }

    public void setConn_Interval_Max(int conn_Interval_Max) {
        this.Conn_Interval_Max = conn_Interval_Max;
    }

    public int getConn_Sla_latency() {
        return this.Conn_Sla_latency;
    }

    public void setConn_Sla_latency(int conn_Sla_latency) {
        this.Conn_Sla_latency = conn_Sla_latency;
    }

    public int getConn_Sup_Timeout() {
        return this.Conn_Sup_Timeout;
    }

    public void setConn_Sup_Timeout(int conn_Sup_Timeout) {
        this.Conn_Sup_Timeout = conn_Sup_Timeout;
    }

    public boolean isNewProtocol() {
        return this.newProtocol;
    }

    public void setNewProtocol(boolean newProtocol2) {
    }

    public void parseData(byte[] datas) {
        try {
            if (datas.length >= 10) {
                this.newProtocol = true;
                this.Peer_HandShake = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
                this.Conn_Params_Flag = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
                this.Conn_Interval_Min = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
                this.Conn_Interval_Max = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
                this.Conn_Sla_latency = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
                this.Conn_Sup_Timeout = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
    }
}
