package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import no.nordicsemi.android.dfu.DfuCallback.DfuGattCallback;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;

class DfuServiceProvider implements DfuCallback {
    private boolean mAborted;
    private BaseDfuImpl mImpl;
    private boolean mPaused;

    DfuServiceProvider() {
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public DfuService getServiceImpl(Intent intent, DfuBaseService service, BluetoothGatt gatt) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        BaseDfuImpl baseDfuImpl;
        try {
            this.mImpl = new ButtonlessDfuWithBondSharingImpl(intent, service);
            if (this.mImpl.isClientCompatible(intent, gatt)) {
                baseDfuImpl = this.mImpl;
                if (this.mImpl != null) {
                    if (this.mPaused) {
                        this.mImpl.pause();
                    }
                    if (this.mAborted) {
                        this.mImpl.abort();
                    }
                }
            } else {
                this.mImpl = new ButtonlessDfuWithoutBondSharingImpl(intent, service);
                if (this.mImpl.isClientCompatible(intent, gatt)) {
                    baseDfuImpl = this.mImpl;
                    if (this.mImpl != null) {
                        if (this.mPaused) {
                            this.mImpl.pause();
                        }
                        if (this.mAborted) {
                            this.mImpl.abort();
                        }
                    }
                } else {
                    this.mImpl = new SecureDfuImpl(intent, service);
                    if (this.mImpl.isClientCompatible(intent, gatt)) {
                        baseDfuImpl = this.mImpl;
                        if (this.mImpl != null) {
                            if (this.mPaused) {
                                this.mImpl.pause();
                            }
                            if (this.mAborted) {
                                this.mImpl.abort();
                            }
                        }
                    } else {
                        this.mImpl = new LegacyButtonlessDfuImpl(intent, service);
                        if (this.mImpl.isClientCompatible(intent, gatt)) {
                            baseDfuImpl = this.mImpl;
                            if (this.mImpl != null) {
                                if (this.mPaused) {
                                    this.mImpl.pause();
                                }
                                if (this.mAborted) {
                                    this.mImpl.abort();
                                }
                            }
                        } else {
                            this.mImpl = new LegacyDfuImpl(intent, service);
                            if (this.mImpl.isClientCompatible(intent, gatt)) {
                                baseDfuImpl = this.mImpl;
                                if (this.mImpl != null) {
                                    if (this.mPaused) {
                                        this.mImpl.pause();
                                    }
                                    if (this.mAborted) {
                                        this.mImpl.abort();
                                    }
                                }
                            } else {
                                if (intent.getBooleanExtra(DfuBaseService.EXTRA_UNSAFE_EXPERIMENTAL_BUTTONLESS_DFU, false)) {
                                    this.mImpl = new ExperimentalButtonlessDfuImpl(intent, service);
                                    if (this.mImpl.isClientCompatible(intent, gatt)) {
                                        baseDfuImpl = this.mImpl;
                                        if (this.mImpl != null) {
                                            if (this.mPaused) {
                                                this.mImpl.pause();
                                            }
                                            if (this.mAborted) {
                                                this.mImpl.abort();
                                            }
                                        }
                                    }
                                }
                                baseDfuImpl = null;
                                if (this.mImpl != null) {
                                    if (this.mPaused) {
                                        this.mImpl.pause();
                                    }
                                    if (this.mAborted) {
                                        this.mImpl.abort();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return baseDfuImpl;
        } catch (Throwable th) {
            if (this.mImpl != null) {
                if (this.mPaused) {
                    this.mImpl.pause();
                }
                if (this.mAborted) {
                    this.mImpl.abort();
                }
            }
            throw th;
        }
    }

    public DfuGattCallback getGattCallback() {
        if (this.mImpl != null) {
            return this.mImpl.getGattCallback();
        }
        return null;
    }

    public void onBondStateChanged(int state) {
        if (this.mImpl != null) {
            this.mImpl.onBondStateChanged(state);
        }
    }

    public void pause() {
        this.mPaused = true;
    }

    public void resume() {
        this.mPaused = false;
    }

    public void abort() {
        this.mAborted = true;
        if (this.mImpl != null) {
            this.mImpl.abort();
        }
    }
}
