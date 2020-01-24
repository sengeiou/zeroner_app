package com.iwown.device_module.device_camera.exif;

import com.iwown.device_module.device_camera.exif.ExifInterface.ColorSpace;
import java.io.BufferedOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

class ExifOutputStream extends FilterOutputStream {
    private static final boolean DEBUG = false;
    private static final int EXIF_HEADER = 1165519206;
    private static final int MAX_EXIF_SIZE = 65535;
    private static final int STATE_FRAME_HEADER = 1;
    private static final int STATE_JPEG_DATA = 2;
    private static final int STATE_SOI = 0;
    private static final int STREAMBUFFER_SIZE = 65536;
    private static final String TAG = "ExifOutputStream";
    private static final short TAG_SIZE = 12;
    private static final short TIFF_BIG_ENDIAN = 19789;
    private static final short TIFF_HEADER = 42;
    private static final short TIFF_HEADER_SIZE = 8;
    private static final short TIFF_LITTLE_ENDIAN = 18761;
    private final ByteBuffer mBuffer = ByteBuffer.allocate(4);
    private int mByteToCopy;
    private int mByteToSkip;
    private ExifData mExifData;
    private final ExifInterface mInterface;
    private final byte[] mSingleByteArray = new byte[1];
    private int mState = 0;

    protected ExifOutputStream(OutputStream ou, ExifInterface iRef) {
        super(new BufferedOutputStream(ou, 65536));
        this.mInterface = iRef;
    }

    /* access modifiers changed from: protected */
    public void setExifData(ExifData exifData) {
        this.mExifData = exifData;
    }

    /* access modifiers changed from: protected */
    public ExifData getExifData() {
        return this.mExifData;
    }

    private int requestByteToBuffer(int requestByteCount, byte[] buffer, int offset, int length) {
        int byteToRead;
        int byteNeeded = requestByteCount - this.mBuffer.position();
        if (length > byteNeeded) {
            byteToRead = byteNeeded;
        } else {
            byteToRead = length;
        }
        this.mBuffer.put(buffer, offset, byteToRead);
        return byteToRead;
    }

    public void write(byte[] buffer, int offset, int length) throws IOException {
        int byteToProcess;
        while (true) {
            if ((this.mByteToSkip > 0 || this.mByteToCopy > 0 || this.mState != 2) && length > 0) {
                if (this.mByteToSkip > 0) {
                    int byteToProcess2 = length > this.mByteToSkip ? this.mByteToSkip : length;
                    length -= byteToProcess2;
                    this.mByteToSkip -= byteToProcess2;
                    offset += byteToProcess2;
                }
                if (this.mByteToCopy > 0) {
                    if (length > this.mByteToCopy) {
                        byteToProcess = this.mByteToCopy;
                    } else {
                        byteToProcess = length;
                    }
                    this.out.write(buffer, offset, byteToProcess);
                    length -= byteToProcess;
                    this.mByteToCopy -= byteToProcess;
                    offset += byteToProcess;
                }
                if (length != 0) {
                    switch (this.mState) {
                        case 0:
                            int byteRead = requestByteToBuffer(2, buffer, offset, length);
                            offset += byteRead;
                            length -= byteRead;
                            if (this.mBuffer.position() >= 2) {
                                this.mBuffer.rewind();
                                if (this.mBuffer.getShort() == -40) {
                                    this.out.write(this.mBuffer.array(), 0, 2);
                                    this.mState = 1;
                                    this.mBuffer.rewind();
                                    writeExifData();
                                    break;
                                } else {
                                    throw new IOException("Not a valid jpeg image, cannot write exif");
                                }
                            } else {
                                return;
                            }
                        case 1:
                            int byteRead2 = requestByteToBuffer(4, buffer, offset, length);
                            offset += byteRead2;
                            length -= byteRead2;
                            if (this.mBuffer.position() == 2 && this.mBuffer.getShort() == -39) {
                                this.out.write(this.mBuffer.array(), 0, 2);
                                this.mBuffer.rewind();
                            }
                            if (this.mBuffer.position() >= 4) {
                                this.mBuffer.rewind();
                                short marker = this.mBuffer.getShort();
                                if (marker == -31) {
                                    this.mByteToSkip = (this.mBuffer.getShort() & ColorSpace.UNCALIBRATED) - 2;
                                    this.mState = 2;
                                } else if (!JpegHeader.isSofMarker(marker)) {
                                    this.out.write(this.mBuffer.array(), 0, 4);
                                    this.mByteToCopy = (this.mBuffer.getShort() & ColorSpace.UNCALIBRATED) - 2;
                                } else {
                                    this.out.write(this.mBuffer.array(), 0, 4);
                                    this.mState = 2;
                                }
                                this.mBuffer.rewind();
                                break;
                            } else {
                                return;
                            }
                    }
                } else {
                    return;
                }
            }
        }
        if (length > 0) {
            this.out.write(buffer, offset, length);
        }
    }

    public void write(int oneByte) throws IOException {
        this.mSingleByteArray[0] = (byte) (oneByte & 255);
        write(this.mSingleByteArray);
    }

    public void write(byte[] buffer) throws IOException {
        write(buffer, 0, buffer.length);
    }

    private void writeExifData() throws IOException {
        if (this.mExifData != null) {
            ArrayList<ExifTag> nullTags = stripNullValueTags(this.mExifData);
            createRequiredIfdAndTag();
            int exifSize = calculateAllOffset();
            if (exifSize + 8 > 65535) {
                throw new IOException("Exif header is too large (>64Kb)");
            }
            OrderedDataOutputStream dataOutputStream = new OrderedDataOutputStream(this.out);
            dataOutputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
            dataOutputStream.writeShort(-31);
            dataOutputStream.writeShort((short) (exifSize + 8));
            dataOutputStream.writeInt(EXIF_HEADER);
            dataOutputStream.writeShort(0);
            if (this.mExifData.getByteOrder() == ByteOrder.BIG_ENDIAN) {
                dataOutputStream.writeShort(TIFF_BIG_ENDIAN);
            } else {
                dataOutputStream.writeShort(TIFF_LITTLE_ENDIAN);
            }
            dataOutputStream.setByteOrder(this.mExifData.getByteOrder());
            dataOutputStream.writeShort(TIFF_HEADER);
            dataOutputStream.writeInt(8);
            writeAllTags(dataOutputStream);
            writeThumbnail(dataOutputStream);
            Iterator it = nullTags.iterator();
            while (it.hasNext()) {
                this.mExifData.addTag((ExifTag) it.next());
            }
        }
    }

    private ArrayList<ExifTag> stripNullValueTags(ExifData data) {
        ArrayList<ExifTag> nullTags = new ArrayList<>();
        for (ExifTag t : data.getAllTags()) {
            if (t.getValue() == null && !ExifInterface.isOffsetTag(t.getTagId())) {
                data.removeTag(t.getTagId(), t.getIfd());
                nullTags.add(t);
            }
        }
        return nullTags;
    }

    private void writeThumbnail(OrderedDataOutputStream dataOutputStream) throws IOException {
        if (this.mExifData.hasCompressedThumbnail()) {
            dataOutputStream.write(this.mExifData.getCompressedThumbnail());
        } else if (this.mExifData.hasUncompressedStrip()) {
            for (int i = 0; i < this.mExifData.getStripCount(); i++) {
                dataOutputStream.write(this.mExifData.getStrip(i));
            }
        }
    }

    private void writeAllTags(OrderedDataOutputStream dataOutputStream) throws IOException {
        writeIfd(this.mExifData.getIfdData(0), dataOutputStream);
        writeIfd(this.mExifData.getIfdData(2), dataOutputStream);
        IfdData interoperabilityIfd = this.mExifData.getIfdData(3);
        if (interoperabilityIfd != null) {
            writeIfd(interoperabilityIfd, dataOutputStream);
        }
        IfdData gpsIfd = this.mExifData.getIfdData(4);
        if (gpsIfd != null) {
            writeIfd(gpsIfd, dataOutputStream);
        }
        if (this.mExifData.getIfdData(1) != null) {
            writeIfd(this.mExifData.getIfdData(1), dataOutputStream);
        }
    }

    private void writeIfd(IfdData ifd, OrderedDataOutputStream dataOutputStream) throws IOException {
        ExifTag[] tags = ifd.getAllTags();
        dataOutputStream.writeShort((short) tags.length);
        for (ExifTag tag : tags) {
            dataOutputStream.writeShort(tag.getTagId());
            dataOutputStream.writeShort(tag.getDataType());
            dataOutputStream.writeInt(tag.getComponentCount());
            if (tag.getDataSize() > 4) {
                dataOutputStream.writeInt(tag.getOffset());
            } else {
                writeTagValue(tag, dataOutputStream);
                int n = 4 - tag.getDataSize();
                for (int i = 0; i < n; i++) {
                    dataOutputStream.write(0);
                }
            }
        }
        dataOutputStream.writeInt(ifd.getOffsetToNextIfd());
        for (ExifTag tag2 : tags) {
            if (tag2.getDataSize() > 4) {
                writeTagValue(tag2, dataOutputStream);
            }
        }
    }

    private int calculateOffsetOfIfd(IfdData ifd, int offset) {
        ExifTag[] tags;
        int offset2 = offset + (ifd.getTagCount() * 12) + 2 + 4;
        for (ExifTag tag : ifd.getAllTags()) {
            if (tag.getDataSize() > 4) {
                tag.setOffset(offset2);
                offset2 += tag.getDataSize();
            }
        }
        return offset2;
    }

    private void createRequiredIfdAndTag() throws IOException {
        IfdData ifd0 = this.mExifData.getIfdData(0);
        if (ifd0 == null) {
            ifd0 = new IfdData(0);
            this.mExifData.addIfdData(ifd0);
        }
        ExifTag exifOffsetTag = this.mInterface.buildUninitializedTag(ExifInterface.TAG_EXIF_IFD);
        if (exifOffsetTag == null) {
            throw new IOException("No definition for crucial exif tag: " + ExifInterface.TAG_EXIF_IFD);
        }
        ifd0.setTag(exifOffsetTag);
        IfdData exifIfd = this.mExifData.getIfdData(2);
        if (exifIfd == null) {
            exifIfd = new IfdData(2);
            this.mExifData.addIfdData(exifIfd);
        }
        if (this.mExifData.getIfdData(4) != null) {
            ExifTag gpsOffsetTag = this.mInterface.buildUninitializedTag(ExifInterface.TAG_GPS_IFD);
            if (gpsOffsetTag == null) {
                throw new IOException("No definition for crucial exif tag: " + ExifInterface.TAG_GPS_IFD);
            }
            ifd0.setTag(gpsOffsetTag);
        }
        if (this.mExifData.getIfdData(3) != null) {
            ExifTag interOffsetTag = this.mInterface.buildUninitializedTag(ExifInterface.TAG_INTEROPERABILITY_IFD);
            if (interOffsetTag == null) {
                throw new IOException("No definition for crucial exif tag: " + ExifInterface.TAG_INTEROPERABILITY_IFD);
            }
            exifIfd.setTag(interOffsetTag);
        }
        IfdData ifd1 = this.mExifData.getIfdData(1);
        if (this.mExifData.hasCompressedThumbnail()) {
            if (ifd1 == null) {
                ifd1 = new IfdData(1);
                this.mExifData.addIfdData(ifd1);
            }
            ExifTag offsetTag = this.mInterface.buildUninitializedTag(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT);
            if (offsetTag == null) {
                throw new IOException("No definition for crucial exif tag: " + ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT);
            }
            ifd1.setTag(offsetTag);
            ExifTag lengthTag = this.mInterface.buildUninitializedTag(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            if (lengthTag == null) {
                throw new IOException("No definition for crucial exif tag: " + ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            }
            lengthTag.setValue(this.mExifData.getCompressedThumbnail().length);
            ifd1.setTag(lengthTag);
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_STRIP_OFFSETS));
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_STRIP_BYTE_COUNTS));
        } else if (this.mExifData.hasUncompressedStrip()) {
            if (ifd1 == null) {
                ifd1 = new IfdData(1);
                this.mExifData.addIfdData(ifd1);
            }
            int stripCount = this.mExifData.getStripCount();
            ExifTag offsetTag2 = this.mInterface.buildUninitializedTag(ExifInterface.TAG_STRIP_OFFSETS);
            if (offsetTag2 == null) {
                throw new IOException("No definition for crucial exif tag: " + ExifInterface.TAG_STRIP_OFFSETS);
            }
            ExifTag lengthTag2 = this.mInterface.buildUninitializedTag(ExifInterface.TAG_STRIP_BYTE_COUNTS);
            if (lengthTag2 == null) {
                throw new IOException("No definition for crucial exif tag: " + ExifInterface.TAG_STRIP_BYTE_COUNTS);
            }
            long[] lengths = new long[stripCount];
            for (int i = 0; i < this.mExifData.getStripCount(); i++) {
                lengths[i] = (long) this.mExifData.getStrip(i).length;
            }
            lengthTag2.setValue(lengths);
            ifd1.setTag(offsetTag2);
            ifd1.setTag(lengthTag2);
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT));
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH));
        } else if (ifd1 != null) {
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_STRIP_OFFSETS));
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_STRIP_BYTE_COUNTS));
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT));
            ifd1.removeTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH));
        }
    }

    private int calculateAllOffset() {
        IfdData ifd0 = this.mExifData.getIfdData(0);
        int offset = calculateOffsetOfIfd(ifd0, 8);
        ifd0.getTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_EXIF_IFD)).setValue(offset);
        IfdData exifIfd = this.mExifData.getIfdData(2);
        int offset2 = calculateOffsetOfIfd(exifIfd, offset);
        IfdData interIfd = this.mExifData.getIfdData(3);
        if (interIfd != null) {
            exifIfd.getTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_INTEROPERABILITY_IFD)).setValue(offset2);
            offset2 = calculateOffsetOfIfd(interIfd, offset2);
        }
        IfdData gpsIfd = this.mExifData.getIfdData(4);
        if (gpsIfd != null) {
            ifd0.getTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_GPS_IFD)).setValue(offset2);
            offset2 = calculateOffsetOfIfd(gpsIfd, offset2);
        }
        IfdData ifd1 = this.mExifData.getIfdData(1);
        if (ifd1 != null) {
            ifd0.setOffsetToNextIfd(offset2);
            offset2 = calculateOffsetOfIfd(ifd1, offset2);
        }
        if (this.mExifData.hasCompressedThumbnail()) {
            ifd1.getTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT)).setValue(offset2);
            return offset2 + this.mExifData.getCompressedThumbnail().length;
        } else if (!this.mExifData.hasUncompressedStrip()) {
            return offset2;
        } else {
            long[] offsets = new long[this.mExifData.getStripCount()];
            for (int i = 0; i < this.mExifData.getStripCount(); i++) {
                offsets[i] = (long) offset2;
                offset2 += this.mExifData.getStrip(i).length;
            }
            ifd1.getTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_STRIP_OFFSETS)).setValue(offsets);
            return offset2;
        }
    }

    static void writeTagValue(ExifTag tag, OrderedDataOutputStream dataOutputStream) throws IOException {
        switch (tag.getDataType()) {
            case 1:
            case 7:
                byte[] buf = new byte[tag.getComponentCount()];
                tag.getBytes(buf);
                dataOutputStream.write(buf);
                return;
            case 2:
                byte[] buf2 = tag.getStringByte();
                if (buf2.length == tag.getComponentCount()) {
                    buf2[buf2.length - 1] = 0;
                    dataOutputStream.write(buf2);
                    return;
                }
                dataOutputStream.write(buf2);
                dataOutputStream.write(0);
                return;
            case 3:
                int n = tag.getComponentCount();
                for (int i = 0; i < n; i++) {
                    dataOutputStream.writeShort((short) ((int) tag.getValueAt(i)));
                }
                return;
            case 4:
            case 9:
                int n2 = tag.getComponentCount();
                for (int i2 = 0; i2 < n2; i2++) {
                    dataOutputStream.writeInt((int) tag.getValueAt(i2));
                }
                return;
            case 5:
            case 10:
                int n3 = tag.getComponentCount();
                for (int i3 = 0; i3 < n3; i3++) {
                    dataOutputStream.writeRational(tag.getRational(i3));
                }
                return;
            default:
                return;
        }
    }
}
