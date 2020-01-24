package coms.mediatek.ctrl.epo;

public interface EpoDownloadChangeListener {
    void notifyConnectionChanged(int i);

    void notifyDownloadResult(int i);

    void notifyProgressChanged(float f);
}
