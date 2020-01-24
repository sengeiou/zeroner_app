package no.nordicsemi.android.dfu;

import android.os.SystemClock;
import android.support.annotation.NonNull;

class DfuProgressInfo {
    private int bytesReceived;
    private int bytesSent;
    private int currentPart;
    private int imageSizeInBytes;
    private int initialBytesSent;
    private int lastBytesSent;
    private long lastProgressTime;
    private final ProgressListener mListener;
    private int maxObjectSizeInBytes;
    private int progress;
    private long timeStart;
    private int totalParts;

    interface ProgressListener {
        void updateProgressNotification();
    }

    DfuProgressInfo(@NonNull ProgressListener listener) {
        this.mListener = listener;
    }

    /* access modifiers changed from: 0000 */
    public DfuProgressInfo init(int imageSizeInBytes2, int currentPart2, int totalParts2) {
        this.imageSizeInBytes = imageSizeInBytes2;
        this.maxObjectSizeInBytes = Integer.MAX_VALUE;
        this.currentPart = currentPart2;
        this.totalParts = totalParts2;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public DfuProgressInfo setTotalPart(int totalParts2) {
        this.totalParts = totalParts2;
        return this;
    }

    public void setProgress(int progress2) {
        this.progress = progress2;
        this.mListener.updateProgressNotification();
    }

    /* access modifiers changed from: 0000 */
    public void setBytesSent(int bytesSent2) {
        if (this.timeStart == 0) {
            this.timeStart = SystemClock.elapsedRealtime();
            this.initialBytesSent = bytesSent2;
        }
        this.bytesSent = bytesSent2;
        this.progress = (int) ((100.0f * ((float) bytesSent2)) / ((float) this.imageSizeInBytes));
        this.mListener.updateProgressNotification();
    }

    /* access modifiers changed from: 0000 */
    public void addBytesSent(int increment) {
        setBytesSent(this.bytesSent + increment);
    }

    /* access modifiers changed from: 0000 */
    public void setBytesReceived(int bytesReceived2) {
        this.bytesReceived = bytesReceived2;
    }

    /* access modifiers changed from: 0000 */
    public void setMaxObjectSizeInBytes(int bytes) {
        this.maxObjectSizeInBytes = bytes;
    }

    /* access modifiers changed from: 0000 */
    public boolean isComplete() {
        return this.bytesSent == this.imageSizeInBytes;
    }

    /* access modifiers changed from: 0000 */
    public boolean isObjectComplete() {
        return this.bytesSent % this.maxObjectSizeInBytes == 0;
    }

    /* access modifiers changed from: 0000 */
    public int getAvailableObjectSizeIsBytes() {
        return Math.min(this.imageSizeInBytes - this.bytesSent, this.maxObjectSizeInBytes - (this.bytesSent % this.maxObjectSizeInBytes));
    }

    /* access modifiers changed from: 0000 */
    public int getProgress() {
        return this.progress;
    }

    /* access modifiers changed from: 0000 */
    public int getBytesSent() {
        return this.bytesSent;
    }

    /* access modifiers changed from: 0000 */
    public int getBytesReceived() {
        return this.bytesReceived;
    }

    /* access modifiers changed from: 0000 */
    public int getImageSizeInBytes() {
        return this.imageSizeInBytes;
    }

    /* access modifiers changed from: 0000 */
    public float getSpeed() {
        long now = SystemClock.elapsedRealtime();
        float speed = now - this.timeStart != 0 ? ((float) (this.bytesSent - this.lastBytesSent)) / ((float) (now - this.lastProgressTime)) : 0.0f;
        this.lastProgressTime = now;
        this.lastBytesSent = this.bytesSent;
        return speed;
    }

    /* access modifiers changed from: 0000 */
    public float getAverageSpeed() {
        long now = SystemClock.elapsedRealtime();
        if (now - this.timeStart != 0) {
            return ((float) (this.bytesSent - this.initialBytesSent)) / ((float) (now - this.timeStart));
        }
        return 0.0f;
    }

    /* access modifiers changed from: 0000 */
    public int getCurrentPart() {
        return this.currentPart;
    }

    /* access modifiers changed from: 0000 */
    public int getTotalParts() {
        return this.totalParts;
    }

    /* access modifiers changed from: 0000 */
    public boolean isLastPart() {
        return this.currentPart == this.totalParts;
    }
}
