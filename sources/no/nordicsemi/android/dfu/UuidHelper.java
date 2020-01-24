package no.nordicsemi.android.dfu;

import android.content.Intent;
import android.os.ParcelUuid;
import android.os.Parcelable;

class UuidHelper {
    UuidHelper() {
    }

    static void assignCustomUuids(Intent intent) {
        Parcelable[] uuids = intent.getParcelableArrayExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_LEGACY_DFU);
        if (uuids == null || uuids.length != 4) {
            LegacyDfuImpl.DFU_SERVICE_UUID = LegacyDfuImpl.DEFAULT_DFU_SERVICE_UUID;
            LegacyDfuImpl.DFU_CONTROL_POINT_UUID = LegacyDfuImpl.DEFAULT_DFU_CONTROL_POINT_UUID;
            LegacyDfuImpl.DFU_PACKET_UUID = LegacyDfuImpl.DEFAULT_DFU_PACKET_UUID;
            LegacyDfuImpl.DFU_VERSION_UUID = LegacyDfuImpl.DEFAULT_DFU_VERSION_UUID;
            LegacyButtonlessDfuImpl.DFU_SERVICE_UUID = LegacyDfuImpl.DEFAULT_DFU_SERVICE_UUID;
            LegacyButtonlessDfuImpl.DFU_CONTROL_POINT_UUID = LegacyDfuImpl.DEFAULT_DFU_CONTROL_POINT_UUID;
            LegacyButtonlessDfuImpl.DFU_VERSION_UUID = LegacyDfuImpl.DEFAULT_DFU_VERSION_UUID;
        } else {
            LegacyDfuImpl.DFU_SERVICE_UUID = uuids[0] != null ? ((ParcelUuid) uuids[0]).getUuid() : LegacyDfuImpl.DEFAULT_DFU_SERVICE_UUID;
            LegacyDfuImpl.DFU_CONTROL_POINT_UUID = uuids[1] != null ? ((ParcelUuid) uuids[1]).getUuid() : LegacyDfuImpl.DEFAULT_DFU_CONTROL_POINT_UUID;
            LegacyDfuImpl.DFU_PACKET_UUID = uuids[2] != null ? ((ParcelUuid) uuids[2]).getUuid() : LegacyDfuImpl.DEFAULT_DFU_PACKET_UUID;
            LegacyDfuImpl.DFU_VERSION_UUID = uuids[3] != null ? ((ParcelUuid) uuids[3]).getUuid() : LegacyDfuImpl.DEFAULT_DFU_VERSION_UUID;
            LegacyButtonlessDfuImpl.DFU_SERVICE_UUID = LegacyDfuImpl.DFU_SERVICE_UUID;
            LegacyButtonlessDfuImpl.DFU_CONTROL_POINT_UUID = LegacyDfuImpl.DFU_CONTROL_POINT_UUID;
            LegacyButtonlessDfuImpl.DFU_VERSION_UUID = LegacyDfuImpl.DFU_VERSION_UUID;
        }
        Parcelable[] uuids2 = intent.getParcelableArrayExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_SECURE_DFU);
        if (uuids2 == null || uuids2.length != 3) {
            SecureDfuImpl.DFU_SERVICE_UUID = SecureDfuImpl.DEFAULT_DFU_SERVICE_UUID;
            SecureDfuImpl.DFU_CONTROL_POINT_UUID = SecureDfuImpl.DEFAULT_DFU_CONTROL_POINT_UUID;
            SecureDfuImpl.DFU_PACKET_UUID = SecureDfuImpl.DEFAULT_DFU_PACKET_UUID;
        } else {
            SecureDfuImpl.DFU_SERVICE_UUID = uuids2[0] != null ? ((ParcelUuid) uuids2[0]).getUuid() : SecureDfuImpl.DEFAULT_DFU_SERVICE_UUID;
            SecureDfuImpl.DFU_CONTROL_POINT_UUID = uuids2[1] != null ? ((ParcelUuid) uuids2[1]).getUuid() : SecureDfuImpl.DEFAULT_DFU_CONTROL_POINT_UUID;
            SecureDfuImpl.DFU_PACKET_UUID = uuids2[2] != null ? ((ParcelUuid) uuids2[2]).getUuid() : SecureDfuImpl.DEFAULT_DFU_PACKET_UUID;
        }
        Parcelable[] uuids3 = intent.getParcelableArrayExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_EXPERIMENTAL_BUTTONLESS_DFU);
        if (uuids3 == null || uuids3.length != 2) {
            ExperimentalButtonlessDfuImpl.EXPERIMENTAL_BUTTONLESS_DFU_SERVICE_UUID = ExperimentalButtonlessDfuImpl.DEFAULT_EXPERIMENTAL_BUTTONLESS_DFU_SERVICE_UUID;
            ExperimentalButtonlessDfuImpl.EXPERIMENTAL_BUTTONLESS_DFU_UUID = ExperimentalButtonlessDfuImpl.DEFAULT_EXPERIMENTAL_BUTTONLESS_DFU_UUID;
        } else {
            ExperimentalButtonlessDfuImpl.EXPERIMENTAL_BUTTONLESS_DFU_SERVICE_UUID = uuids3[0] != null ? ((ParcelUuid) uuids3[0]).getUuid() : ExperimentalButtonlessDfuImpl.DEFAULT_EXPERIMENTAL_BUTTONLESS_DFU_SERVICE_UUID;
            ExperimentalButtonlessDfuImpl.EXPERIMENTAL_BUTTONLESS_DFU_UUID = uuids3[1] != null ? ((ParcelUuid) uuids3[1]).getUuid() : ExperimentalButtonlessDfuImpl.DEFAULT_EXPERIMENTAL_BUTTONLESS_DFU_UUID;
        }
        Parcelable[] uuids4 = intent.getParcelableArrayExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITHOUT_BOND_SHARING);
        if (uuids4 == null || uuids4.length != 2) {
            ButtonlessDfuWithoutBondSharingImpl.BUTTONLESS_DFU_SERVICE_UUID = ButtonlessDfuWithoutBondSharingImpl.DEFAULT_BUTTONLESS_DFU_SERVICE_UUID;
            ButtonlessDfuWithoutBondSharingImpl.BUTTONLESS_DFU_UUID = ButtonlessDfuWithoutBondSharingImpl.DEFAULT_BUTTONLESS_DFU_UUID;
        } else {
            ButtonlessDfuWithoutBondSharingImpl.BUTTONLESS_DFU_SERVICE_UUID = uuids4[0] != null ? ((ParcelUuid) uuids4[0]).getUuid() : ButtonlessDfuWithoutBondSharingImpl.DEFAULT_BUTTONLESS_DFU_SERVICE_UUID;
            ButtonlessDfuWithoutBondSharingImpl.BUTTONLESS_DFU_UUID = uuids4[1] != null ? ((ParcelUuid) uuids4[1]).getUuid() : ButtonlessDfuWithoutBondSharingImpl.DEFAULT_BUTTONLESS_DFU_UUID;
        }
        Parcelable[] uuids5 = intent.getParcelableArrayExtra(DfuBaseService.EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITH_BOND_SHARING);
        if (uuids5 == null || uuids5.length != 2) {
            ButtonlessDfuWithBondSharingImpl.BUTTONLESS_DFU_SERVICE_UUID = ButtonlessDfuWithBondSharingImpl.DEFAULT_BUTTONLESS_DFU_SERVICE_UUID;
            ButtonlessDfuWithBondSharingImpl.BUTTONLESS_DFU_UUID = ButtonlessDfuWithBondSharingImpl.DEFAULT_BUTTONLESS_DFU_UUID;
            return;
        }
        ButtonlessDfuWithBondSharingImpl.BUTTONLESS_DFU_SERVICE_UUID = uuids5[0] != null ? ((ParcelUuid) uuids5[0]).getUuid() : ButtonlessDfuWithBondSharingImpl.DEFAULT_BUTTONLESS_DFU_SERVICE_UUID;
        ButtonlessDfuWithBondSharingImpl.BUTTONLESS_DFU_UUID = uuids5[1] != null ? ((ParcelUuid) uuids5[1]).getUuid() : ButtonlessDfuWithBondSharingImpl.DEFAULT_BUTTONLESS_DFU_UUID;
    }
}
