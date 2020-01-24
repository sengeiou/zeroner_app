package no.nordicsemi.android.dfu;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import com.iwown.device_module.R;
import java.security.InvalidParameterException;
import java.util.UUID;

public class DfuServiceInitiator {
    public static final int DEFAULT_PRN_VALUE = 8;
    public static final int SCOPE_APPLICATION = 3542;
    public static final int SCOPE_SYSTEM_COMPONENTS = 7578;
    private Parcelable[] buttonlessDfuWithBondSharingUuids;
    private Parcelable[] buttonlessDfuWithoutBondSharingUuids;
    private final String deviceAddress;
    private String deviceName;
    private boolean disableNotification;
    private boolean enableUnsafeExperimentalButtonlessDfu = false;
    private Parcelable[] experimentalButtonlessDfuUuids;
    private String filePath;
    private int fileResId;
    private int fileType = -1;
    private Uri fileUri;
    private boolean forceDfu = false;
    private String initFilePath;
    private int initFileResId;
    private Uri initFileUri;
    private boolean keepBond;
    private Parcelable[] legacyDfuUuids;
    private String mimeType;
    private int numberOfPackets = 12;
    private Boolean packetReceiptNotificationsEnabled;
    private boolean restoreBond;
    private Parcelable[] secureDfuUuids;

    public DfuServiceInitiator(String deviceAddress2) {
        this.deviceAddress = deviceAddress2;
    }

    public DfuServiceInitiator setDeviceName(String name) {
        this.deviceName = name;
        return this;
    }

    public DfuServiceInitiator setDisableNotification(boolean disableNotification2) {
        this.disableNotification = disableNotification2;
        return this;
    }

    public DfuServiceInitiator setKeepBond(boolean keepBond2) {
        this.keepBond = keepBond2;
        return this;
    }

    public DfuServiceInitiator setRestoreBond(boolean restoreBond2) {
        this.restoreBond = restoreBond2;
        return this;
    }

    public DfuServiceInitiator setPacketsReceiptNotificationsEnabled(boolean enabled) {
        this.packetReceiptNotificationsEnabled = Boolean.valueOf(enabled);
        return this;
    }

    public DfuServiceInitiator setPacketsReceiptNotificationsValue(int number) {
        if (number <= 0) {
            number = 8;
        }
        this.numberOfPackets = number;
        return this;
    }

    public DfuServiceInitiator setForceDfu(boolean force) {
        this.forceDfu = force;
        return this;
    }

    public DfuServiceInitiator setScope(int scope) {
        if (!DfuBaseService.MIME_TYPE_ZIP.equals(this.mimeType)) {
            throw new UnsupportedOperationException("Scope can be set only for a ZIP file");
        }
        if (scope == 3542) {
            this.fileType = 4;
        } else if (scope == 7578) {
            this.fileType = 3;
        } else {
            throw new UnsupportedOperationException("Unknown scope");
        }
        return this;
    }

    public DfuServiceInitiator setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(boolean enable) {
        this.enableUnsafeExperimentalButtonlessDfu = enable;
        return this;
    }

    public DfuServiceInitiator setCustomUuidsForLegacyDfu(UUID dfuServiceUuid, UUID dfuControlPointUuid, UUID dfuPacketUuid, UUID dfuVersionUuid) {
        ParcelUuid parcelUuid;
        ParcelUuid parcelUuid2;
        ParcelUuid parcelUuid3 = null;
        ParcelUuid[] uuids = new ParcelUuid[4];
        uuids[0] = dfuServiceUuid != null ? new ParcelUuid(dfuServiceUuid) : null;
        if (dfuControlPointUuid != null) {
            parcelUuid = new ParcelUuid(dfuControlPointUuid);
        } else {
            parcelUuid = null;
        }
        uuids[1] = parcelUuid;
        if (dfuPacketUuid != null) {
            parcelUuid2 = new ParcelUuid(dfuPacketUuid);
        } else {
            parcelUuid2 = null;
        }
        uuids[2] = parcelUuid2;
        if (dfuVersionUuid != null) {
            parcelUuid3 = new ParcelUuid(dfuVersionUuid);
        }
        uuids[3] = parcelUuid3;
        this.legacyDfuUuids = uuids;
        return this;
    }

    public DfuServiceInitiator setCustomUuidsForSecureDfu(UUID dfuServiceUuid, UUID dfuControlPointUuid, UUID dfuPacketUuid) {
        ParcelUuid parcelUuid;
        ParcelUuid parcelUuid2;
        ParcelUuid parcelUuid3 = null;
        ParcelUuid[] uuids = new ParcelUuid[3];
        if (dfuServiceUuid != null) {
            parcelUuid = new ParcelUuid(dfuServiceUuid);
        } else {
            parcelUuid = null;
        }
        uuids[0] = parcelUuid;
        if (dfuControlPointUuid != null) {
            parcelUuid2 = new ParcelUuid(dfuControlPointUuid);
        } else {
            parcelUuid2 = null;
        }
        uuids[1] = parcelUuid2;
        if (dfuPacketUuid != null) {
            parcelUuid3 = new ParcelUuid(dfuPacketUuid);
        }
        uuids[2] = parcelUuid3;
        this.secureDfuUuids = uuids;
        return this;
    }

    public DfuServiceInitiator setCustomUuidsForExperimentalButtonlessDfu(UUID buttonlessDfuServiceUuid, UUID buttonlessDfuControlPointUuid) {
        ParcelUuid parcelUuid;
        ParcelUuid parcelUuid2 = null;
        ParcelUuid[] uuids = new ParcelUuid[2];
        if (buttonlessDfuServiceUuid != null) {
            parcelUuid = new ParcelUuid(buttonlessDfuServiceUuid);
        } else {
            parcelUuid = null;
        }
        uuids[0] = parcelUuid;
        if (buttonlessDfuControlPointUuid != null) {
            parcelUuid2 = new ParcelUuid(buttonlessDfuControlPointUuid);
        }
        uuids[1] = parcelUuid2;
        this.experimentalButtonlessDfuUuids = uuids;
        return this;
    }

    public DfuServiceInitiator setCustomUuidsForButtonlessDfuWithBondSharing(UUID buttonlessDfuServiceUuid, UUID buttonlessDfuControlPointUuid) {
        ParcelUuid parcelUuid;
        ParcelUuid parcelUuid2 = null;
        ParcelUuid[] uuids = new ParcelUuid[2];
        if (buttonlessDfuServiceUuid != null) {
            parcelUuid = new ParcelUuid(buttonlessDfuServiceUuid);
        } else {
            parcelUuid = null;
        }
        uuids[0] = parcelUuid;
        if (buttonlessDfuControlPointUuid != null) {
            parcelUuid2 = new ParcelUuid(buttonlessDfuControlPointUuid);
        }
        uuids[1] = parcelUuid2;
        this.buttonlessDfuWithBondSharingUuids = uuids;
        return this;
    }

    public DfuServiceInitiator setCustomUuidsForButtonlessDfuWithoutBondSharing(UUID buttonlessDfuServiceUuid, UUID buttonlessDfuControlPointUuid) {
        ParcelUuid parcelUuid;
        ParcelUuid parcelUuid2 = null;
        ParcelUuid[] uuids = new ParcelUuid[2];
        if (buttonlessDfuServiceUuid != null) {
            parcelUuid = new ParcelUuid(buttonlessDfuServiceUuid);
        } else {
            parcelUuid = null;
        }
        uuids[0] = parcelUuid;
        if (buttonlessDfuControlPointUuid != null) {
            parcelUuid2 = new ParcelUuid(buttonlessDfuControlPointUuid);
        }
        uuids[1] = parcelUuid2;
        this.buttonlessDfuWithoutBondSharingUuids = uuids;
        return this;
    }

    public DfuServiceInitiator setZip(Uri uri) {
        return init(uri, null, 0, 0, DfuBaseService.MIME_TYPE_ZIP);
    }

    public DfuServiceInitiator setZip(String path) {
        return init(null, path, 0, 0, DfuBaseService.MIME_TYPE_ZIP);
    }

    public DfuServiceInitiator setZip(int rawResId) {
        return init(null, null, rawResId, 0, DfuBaseService.MIME_TYPE_ZIP);
    }

    public DfuServiceInitiator setZip(Uri uri, String path) {
        return init(uri, path, 0, 0, DfuBaseService.MIME_TYPE_ZIP);
    }

    @Deprecated
    public DfuServiceInitiator setBinOrHex(int fileType2, Uri uri) {
        if (fileType2 == 0) {
            throw new UnsupportedOperationException("You must specify the file type");
        }
        return init(uri, null, 0, fileType2, DfuBaseService.MIME_TYPE_OCTET_STREAM);
    }

    @Deprecated
    public DfuServiceInitiator setBinOrHex(int fileType2, String path) {
        if (fileType2 == 0) {
            throw new UnsupportedOperationException("You must specify the file type");
        }
        return init(null, path, 0, fileType2, DfuBaseService.MIME_TYPE_OCTET_STREAM);
    }

    @Deprecated
    public DfuServiceInitiator setBinOrHex(int fileType2, Uri uri, String path) {
        if (fileType2 == 0) {
            throw new UnsupportedOperationException("You must specify the file type");
        }
        return init(uri, path, 0, fileType2, DfuBaseService.MIME_TYPE_OCTET_STREAM);
    }

    @Deprecated
    public DfuServiceInitiator setBinOrHex(int fileType2, int rawResId) {
        if (fileType2 == 0) {
            throw new UnsupportedOperationException("You must specify the file type");
        }
        return init(null, null, rawResId, fileType2, DfuBaseService.MIME_TYPE_OCTET_STREAM);
    }

    @Deprecated
    public DfuServiceInitiator setInitFile(Uri initFileUri2) {
        return init(initFileUri2, null, 0);
    }

    @Deprecated
    public DfuServiceInitiator setInitFile(String initFilePath2) {
        return init(null, initFilePath2, 0);
    }

    @Deprecated
    public DfuServiceInitiator setInitFile(int initFileResId2) {
        return init(null, null, initFileResId2);
    }

    @Deprecated
    public DfuServiceInitiator setInitFile(Uri initFileUri2, String initFilePath2) {
        return init(initFileUri2, initFilePath2, 0);
    }

    public DfuServiceController start(Context context, Class<? extends DfuBaseService> service) {
        if (this.fileType == -1) {
            throw new UnsupportedOperationException("You must specify the firmware file before starting the service");
        }
        Intent intent = new Intent(context, service);
        intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.deviceAddress);
        intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", this.deviceName);
        intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DISABLE_NOTIFICATION", this.disableNotification);
        intent.putExtra(DfuBaseService.EXTRA_FILE_MIME_TYPE, this.mimeType);
        intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE", this.fileType);
        intent.putExtra(DfuBaseService.EXTRA_FILE_URI, this.fileUri);
        intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", this.filePath);
        intent.putExtra(DfuBaseService.EXTRA_FILE_RES_ID, this.fileResId);
        intent.putExtra(DfuBaseService.EXTRA_INIT_FILE_URI, this.initFileUri);
        intent.putExtra(DfuBaseService.EXTRA_INIT_FILE_PATH, this.initFilePath);
        intent.putExtra(DfuBaseService.EXTRA_INIT_FILE_RES_ID, this.initFileResId);
        intent.putExtra(DfuBaseService.EXTRA_KEEP_BOND, this.keepBond);
        intent.putExtra(DfuBaseService.EXTRA_RESTORE_BOND, this.restoreBond);
        intent.putExtra(DfuBaseService.EXTRA_FORCE_DFU, this.forceDfu);
        intent.putExtra(DfuBaseService.EXTRA_UNSAFE_EXPERIMENTAL_BUTTONLESS_DFU, this.enableUnsafeExperimentalButtonlessDfu);
        if (this.packetReceiptNotificationsEnabled != null) {
            intent.putExtra(DfuBaseService.EXTRA_PACKET_RECEIPT_NOTIFICATIONS_ENABLED, this.packetReceiptNotificationsEnabled);
            intent.putExtra(DfuBaseService.EXTRA_PACKET_RECEIPT_NOTIFICATIONS_VALUE, this.numberOfPackets);
        }
        if (this.legacyDfuUuids != null) {
            intent.putExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_LEGACY_DFU, this.legacyDfuUuids);
        }
        if (this.secureDfuUuids != null) {
            intent.putExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_SECURE_DFU, this.secureDfuUuids);
        }
        if (this.experimentalButtonlessDfuUuids != null) {
            intent.putExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_EXPERIMENTAL_BUTTONLESS_DFU, this.experimentalButtonlessDfuUuids);
        }
        if (this.buttonlessDfuWithoutBondSharingUuids != null) {
            intent.putExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITHOUT_BOND_SHARING, this.buttonlessDfuWithoutBondSharingUuids);
        }
        if (this.buttonlessDfuWithBondSharingUuids != null) {
            intent.putExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITH_BOND_SHARING, this.buttonlessDfuWithBondSharingUuids);
        }
        if (VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        return new DfuServiceController(context);
    }

    private DfuServiceInitiator init(Uri initFileUri2, String initFilePath2, int initFileResId2) {
        if (DfuBaseService.MIME_TYPE_ZIP.equals(this.mimeType)) {
            throw new InvalidParameterException("Init file must be located inside the ZIP");
        }
        this.initFileUri = initFileUri2;
        this.initFilePath = initFilePath2;
        this.initFileResId = initFileResId2;
        return this;
    }

    private DfuServiceInitiator init(Uri fileUri2, String filePath2, int fileResId2, int fileType2, String mimeType2) {
        this.fileUri = fileUri2;
        this.filePath = filePath2;
        this.fileResId = fileResId2;
        this.fileType = fileType2;
        this.mimeType = mimeType2;
        if (DfuBaseService.MIME_TYPE_ZIP.equals(mimeType2)) {
            this.initFileUri = null;
            this.initFilePath = null;
            this.initFileResId = 0;
        }
        return this;
    }

    @RequiresApi(api = 26)
    public static void createDfuNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(DfuBaseService.NOTIFICATION_CHANNEL_DFU, context.getString(R.string.device_module_dfu_channel_name), 2);
        channel.setDescription(context.getString(R.string.device_module_dfu_channel_description));
        channel.setShowBadge(false);
        channel.setLockscreenVisibility(1);
        ((NotificationManager) context.getSystemService("notification")).createNotificationChannel(channel);
    }
}
