package com.inuker.bluetooth.library.beacon;

import com.google.common.primitives.UnsignedBytes;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.iwown.device_module.device_camera.exif.ExifInterface.ColorSpace;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class BeaconParser {
    private byte[] bytes;
    private ByteBuffer mByteBuffer;

    public BeaconParser(BeaconItem item) {
        this(item.bytes);
    }

    public BeaconParser(byte[] bytes2) {
        this.bytes = bytes2;
        this.mByteBuffer = ByteBuffer.wrap(bytes2).order(ByteOrder.LITTLE_ENDIAN);
    }

    public void setPosition(int position) {
        this.mByteBuffer.position(position);
    }

    public int readByte() {
        return this.mByteBuffer.get() & UnsignedBytes.MAX_VALUE;
    }

    public int readShort() {
        return this.mByteBuffer.getShort() & ColorSpace.UNCALIBRATED;
    }

    public boolean getBit(int n, int index) {
        return ((1 << index) & n) != 0;
    }

    public static List<BeaconItem> parseBeacon(byte[] bytes2) {
        ArrayList<BeaconItem> items = new ArrayList<>();
        int i = 0;
        while (i < bytes2.length) {
            BeaconItem item = parse(bytes2, i);
            if (item == null) {
                break;
            }
            items.add(item);
            i += item.len + 1;
        }
        return items;
    }

    private static BeaconItem parse(byte[] bytes2, int startIndex) {
        BeaconItem item = null;
        if (bytes2.length - startIndex >= 2) {
            byte length = bytes2[startIndex];
            if (length > 0) {
                byte type = bytes2[startIndex + 1];
                int firstIndex = startIndex + 2;
                if (firstIndex < bytes2.length) {
                    item = new BeaconItem();
                    int endIndex = (firstIndex + length) - 2;
                    if (endIndex >= bytes2.length) {
                        endIndex = bytes2.length - 1;
                    }
                    item.type = type & UnsignedBytes.MAX_VALUE;
                    item.len = length;
                    item.bytes = ByteUtils.getBytes(bytes2, firstIndex, endIndex);
                }
            }
        }
        return item;
    }
}
