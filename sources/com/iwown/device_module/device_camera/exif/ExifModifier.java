package com.iwown.device_module.device_camera.exif;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

class ExifModifier {
    public static final boolean DEBUG = false;
    public static final String TAG = "ExifModifier";
    private final ByteBuffer mByteBuffer;
    private final ExifInterface mInterface;
    private int mOffsetBase;
    private final List<TagOffset> mTagOffsets = new ArrayList();
    private final ExifData mTagToModified;

    private static class TagOffset {
        final int mOffset;
        final ExifTag mTag;

        TagOffset(ExifTag tag, int offset) {
            this.mTag = tag;
            this.mOffset = offset;
        }
    }

    protected ExifModifier(ByteBuffer byteBuffer, ExifInterface iRef) throws IOException, ExifInvalidFormatException {
        this.mByteBuffer = byteBuffer;
        this.mOffsetBase = byteBuffer.position();
        this.mInterface = iRef;
        InputStream is = null;
        try {
            InputStream is2 = new ByteBufferInputStream(byteBuffer);
            try {
                ExifParser parser = ExifParser.parse(is2, this.mInterface);
                this.mTagToModified = new ExifData(parser.getByteOrder());
                this.mOffsetBase += parser.getTiffStartPosition();
                this.mByteBuffer.position(0);
                ExifInterface.closeSilently(is2);
            } catch (Throwable th) {
                th = th;
                is = is2;
                ExifInterface.closeSilently(is);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            ExifInterface.closeSilently(is);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public ByteOrder getByteOrder() {
        return this.mTagToModified.getByteOrder();
    }

    /* access modifiers changed from: protected */
    public boolean commit() throws IOException, ExifInvalidFormatException {
        InputStream is = null;
        try {
            InputStream is2 = new ByteBufferInputStream(this.mByteBuffer);
            int flag = 0;
            try {
                IfdData[] ifdDatas = {this.mTagToModified.getIfdData(0), this.mTagToModified.getIfdData(1), this.mTagToModified.getIfdData(2), this.mTagToModified.getIfdData(3), this.mTagToModified.getIfdData(4)};
                if (ifdDatas[0] != null) {
                    flag = 0 | 1;
                }
                if (ifdDatas[1] != null) {
                    flag |= 2;
                }
                if (ifdDatas[2] != null) {
                    flag |= 4;
                }
                if (ifdDatas[4] != null) {
                    flag |= 8;
                }
                if (ifdDatas[3] != null) {
                    flag |= 16;
                }
                ExifParser parser = ExifParser.parse(is2, flag, this.mInterface);
                IfdData currIfd = null;
                for (int event = parser.next(); event != 5; event = parser.next()) {
                    switch (event) {
                        case 0:
                            currIfd = ifdDatas[parser.getCurrentIfd()];
                            if (currIfd != null) {
                                break;
                            } else {
                                parser.skipRemainingTagsInCurrentIfd();
                                break;
                            }
                        case 1:
                            ExifTag oldTag = parser.getTag();
                            ExifTag newTag = currIfd.getTag(oldTag.getTagId());
                            if (newTag != null) {
                                if (newTag.getComponentCount() == oldTag.getComponentCount() && newTag.getDataType() == oldTag.getDataType()) {
                                    this.mTagOffsets.add(new TagOffset(newTag, oldTag.getOffset()));
                                    currIfd.removeTag(oldTag.getTagId());
                                    if (currIfd.getTagCount() != 0) {
                                        break;
                                    } else {
                                        parser.skipRemainingTagsInCurrentIfd();
                                        break;
                                    }
                                } else {
                                    ExifInterface.closeSilently(is2);
                                    return false;
                                }
                            } else {
                                continue;
                            }
                            break;
                    }
                }
                int length = ifdDatas.length;
                int i = 0;
                while (i < length) {
                    IfdData ifd = ifdDatas[i];
                    if (ifd == null || ifd.getTagCount() <= 0) {
                        i++;
                    } else {
                        ExifInterface.closeSilently(is2);
                        return false;
                    }
                }
                modify();
                ExifInterface.closeSilently(is2);
                return true;
            } catch (Throwable th) {
                th = th;
                is = is2;
                ExifInterface.closeSilently(is);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            ExifInterface.closeSilently(is);
            throw th;
        }
    }

    private void modify() {
        this.mByteBuffer.order(getByteOrder());
        for (TagOffset tagOffset : this.mTagOffsets) {
            writeTagValue(tagOffset.mTag, tagOffset.mOffset);
        }
    }

    private void writeTagValue(ExifTag tag, int offset) {
        this.mByteBuffer.position(this.mOffsetBase + offset);
        switch (tag.getDataType()) {
            case 1:
            case 7:
                byte[] buf = new byte[tag.getComponentCount()];
                tag.getBytes(buf);
                this.mByteBuffer.put(buf);
                return;
            case 2:
                byte[] buf2 = tag.getStringByte();
                if (buf2.length == tag.getComponentCount()) {
                    buf2[buf2.length - 1] = 0;
                    this.mByteBuffer.put(buf2);
                    return;
                }
                this.mByteBuffer.put(buf2);
                this.mByteBuffer.put(0);
                return;
            case 3:
                int n = tag.getComponentCount();
                for (int i = 0; i < n; i++) {
                    this.mByteBuffer.putShort((short) ((int) tag.getValueAt(i)));
                }
                return;
            case 4:
            case 9:
                int n2 = tag.getComponentCount();
                for (int i2 = 0; i2 < n2; i2++) {
                    this.mByteBuffer.putInt((int) tag.getValueAt(i2));
                }
                return;
            case 5:
            case 10:
                int n3 = tag.getComponentCount();
                for (int i3 = 0; i3 < n3; i3++) {
                    Rational v = tag.getRational(i3);
                    this.mByteBuffer.putInt((int) v.getNumerator());
                    this.mByteBuffer.putInt((int) v.getDenominator());
                }
                return;
            default:
                return;
        }
    }

    public void modifyTag(ExifTag tag) {
        this.mTagToModified.addTag(tag);
    }
}
