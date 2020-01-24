package coms.mediatek.ctrl.fota.common;

public interface IFotaOperatorCallback {
    void onConnectionStateChange(int i);

    void onCustomerInfoReceived(String str);

    void onFotaVersionReceived(FotaVersion fotaVersion);

    void onProgress(int i);

    void onStatusReceived(int i);
}
