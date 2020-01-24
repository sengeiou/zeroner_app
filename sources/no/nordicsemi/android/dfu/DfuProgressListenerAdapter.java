package no.nordicsemi.android.dfu;

public class DfuProgressListenerAdapter implements DfuProgressListener {
    public void onDeviceConnecting(String deviceAddress) {
    }

    public void onDeviceConnected(String deviceAddress) {
    }

    public void onDfuProcessStarting(String deviceAddress) {
    }

    public void onDfuProcessStarted(String deviceAddress) {
    }

    public void onEnablingDfuMode(String deviceAddress) {
    }

    public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
    }

    public void onFirmwareValidating(String deviceAddress) {
    }

    public void onDeviceDisconnecting(String deviceAddress) {
    }

    public void onDeviceDisconnected(String deviceAddress) {
    }

    public void onDfuCompleted(String deviceAddress) {
    }

    public void onDfuAborted(String deviceAddress) {
    }

    public void onError(String deviceAddress, int error, int errorType, String message) {
    }
}
