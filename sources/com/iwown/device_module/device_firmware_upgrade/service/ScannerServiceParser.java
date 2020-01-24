package com.iwown.device_module.device_firmware_upgrade.service;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class ScannerServiceParser {
    private static final int COMPLETE_LOCAL_NAME = 9;
    private static final int FLAGS_BIT = 1;
    private static final byte LE_GENERAL_DISCOVERABLE_MODE = 2;
    private static final byte LE_LIMITED_DISCOVERABLE_MODE = 1;
    private static final int SERVICES_COMPLETE_LIST_128_BIT = 7;
    private static final int SERVICES_COMPLETE_LIST_16_BIT = 3;
    private static final int SERVICES_COMPLETE_LIST_32_BIT = 5;
    private static final int SERVICES_MORE_AVAILABLE_128_BIT = 6;
    private static final int SERVICES_MORE_AVAILABLE_16_BIT = 2;
    private static final int SERVICES_MORE_AVAILABLE_32_BIT = 4;
    private static final int SHORTENED_LOCAL_NAME = 8;
    private static final String TAG = "ScannerServiceParser";

    public static boolean decodeDeviceAdvData(byte[] data, UUID requiredUUID) {
        boolean valid;
        boolean valid2;
        boolean valid3;
        boolean valid4;
        String uuid = requiredUUID != null ? requiredUUID.toString() : null;
        if (data == null) {
            return false;
        }
        boolean connectable = false;
        if (uuid == null) {
            valid = true;
        } else {
            valid = false;
        }
        int packetLength = data.length;
        int index = 0;
        while (index < packetLength) {
            byte fieldLength = data[index];
            if (fieldLength != 0) {
                int index2 = index + 1;
                byte fieldName = data[index2];
                if (uuid != null) {
                    if (fieldName == 2 || fieldName == 3) {
                        for (int i = index2 + 1; i < (index2 + fieldLength) - 1; i += 2) {
                            if (valid2 || decodeService16BitUUID(uuid, data, i, 2)) {
                                valid3 = true;
                            } else {
                                valid3 = false;
                            }
                        }
                    } else if (fieldName == 4 || fieldName == 5) {
                        for (int i2 = index2 + 1; i2 < (index2 + fieldLength) - 1; i2 += 4) {
                            if (valid2 || decodeService32BitUUID(uuid, data, i2, 4)) {
                                valid4 = true;
                            } else {
                                valid4 = false;
                            }
                        }
                    } else if (fieldName == 6 || fieldName == 7) {
                        for (int i3 = index2 + 1; i3 < (index2 + fieldLength) - 1; i3 += 16) {
                            if (valid2 || decodeService128BitUUID(uuid, data, i3, 16)) {
                                valid2 = true;
                            } else {
                                valid2 = false;
                            }
                        }
                    }
                }
                if (fieldName == 1) {
                    if ((data[index2 + 1] & 3) > 0) {
                        connectable = true;
                    } else {
                        connectable = false;
                    }
                }
                index = index2 + (fieldLength - 1) + 1;
            } else if (!connectable || !valid2) {
                return false;
            } else {
                return true;
            }
        }
        if (!connectable || !valid2) {
            return false;
        }
        return true;
    }

    public static String decodeDeviceName(byte[] data) {
        int packetLength = data.length;
        int index = 0;
        while (index < packetLength) {
            byte fieldLength = data[index];
            if (fieldLength == 0) {
                return null;
            }
            int index2 = index + 1;
            byte fieldName = data[index2];
            if (fieldName == 9 || fieldName == 8) {
                return decodeLocalName(data, index2 + 1, fieldLength - 1);
            }
            index = index2 + (fieldLength - 1) + 1;
        }
        return null;
    }

    public static String decodeLocalName(byte[] data, int start, int length) {
        try {
            return new String(data, start, length, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unable to convert the complete local name to UTF-8", e);
            return null;
        } catch (IndexOutOfBoundsException e2) {
            Log.e(TAG, "Error when reading complete local name", e2);
            return null;
        }
    }

    private static boolean decodeService16BitUUID(String uuid, byte[] data, int startPosition, int serviceDataLength) {
        return Integer.toHexString(decodeUuid16(data, startPosition)).equals(uuid.substring(4, 8));
    }

    private static boolean decodeService32BitUUID(String uuid, byte[] data, int startPosition, int serviceDataLength) {
        return Integer.toHexString(decodeUuid16(data, (startPosition + serviceDataLength) - 4)).equals(uuid.substring(4, 8));
    }

    private static boolean decodeService128BitUUID(String uuid, byte[] data, int startPosition, int serviceDataLength) {
        return Integer.toHexString(decodeUuid16(data, (startPosition + serviceDataLength) - 4)).equals(uuid.substring(4, 8));
    }

    private static int decodeUuid16(byte[] data, int start) {
        return ((data[start + 1] & 255) << 8) | ((data[start] & 255) << 0);
    }
}
