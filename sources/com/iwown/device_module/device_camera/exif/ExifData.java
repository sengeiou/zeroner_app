package com.iwown.device_module.device_camera.exif;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.CharEncoding;

class ExifData {
    private static final String TAG = "ExifData";
    private static final byte[] USER_COMMENT_ASCII = {65, 83, 67, 73, 73, 0, 0, 0};
    private static final byte[] USER_COMMENT_JIS = {74, 73, 83, 0, 0, 0, 0, 0};
    private static final byte[] USER_COMMENT_UNICODE = {85, 78, 73, 67, 79, 68, 69, 0};
    private final ByteOrder mByteOrder;
    private final IfdData[] mIfdDatas = new IfdData[5];
    private final ArrayList<byte[]> mStripBytes = new ArrayList<>();
    private byte[] mThumbnail;

    ExifData(ByteOrder order) {
        this.mByteOrder = order;
    }

    /* access modifiers changed from: protected */
    public byte[] getCompressedThumbnail() {
        return this.mThumbnail;
    }

    /* access modifiers changed from: protected */
    public void setCompressedThumbnail(byte[] thumbnail) {
        this.mThumbnail = thumbnail;
    }

    /* access modifiers changed from: protected */
    public boolean hasCompressedThumbnail() {
        return this.mThumbnail != null;
    }

    /* access modifiers changed from: protected */
    public void setStripBytes(int index, byte[] strip) {
        if (index < this.mStripBytes.size()) {
            this.mStripBytes.set(index, strip);
            return;
        }
        for (int i = this.mStripBytes.size(); i < index; i++) {
            this.mStripBytes.add(null);
        }
        this.mStripBytes.add(strip);
    }

    /* access modifiers changed from: protected */
    public int getStripCount() {
        return this.mStripBytes.size();
    }

    /* access modifiers changed from: protected */
    public byte[] getStrip(int index) {
        return (byte[]) this.mStripBytes.get(index);
    }

    /* access modifiers changed from: protected */
    public boolean hasUncompressedStrip() {
        return this.mStripBytes.size() != 0;
    }

    /* access modifiers changed from: protected */
    public ByteOrder getByteOrder() {
        return this.mByteOrder;
    }

    /* access modifiers changed from: protected */
    public IfdData getIfdData(int ifdId) {
        if (ExifTag.isValidIfd(ifdId)) {
            return this.mIfdDatas[ifdId];
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void addIfdData(IfdData data) {
        this.mIfdDatas[data.getId()] = data;
    }

    /* access modifiers changed from: protected */
    public IfdData getOrCreateIfdData(int ifdId) {
        IfdData ifdData = this.mIfdDatas[ifdId];
        if (ifdData != null) {
            return ifdData;
        }
        IfdData ifdData2 = new IfdData(ifdId);
        this.mIfdDatas[ifdId] = ifdData2;
        return ifdData2;
    }

    /* access modifiers changed from: protected */
    public ExifTag getTag(short tag, int ifd) {
        IfdData ifdData = this.mIfdDatas[ifd];
        if (ifdData == null) {
            return null;
        }
        return ifdData.getTag(tag);
    }

    /* access modifiers changed from: protected */
    public ExifTag addTag(ExifTag tag) {
        if (tag != null) {
            return addTag(tag, tag.getIfd());
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public ExifTag addTag(ExifTag tag, int ifdId) {
        if (tag == null || !ExifTag.isValidIfd(ifdId)) {
            return null;
        }
        return getOrCreateIfdData(ifdId).setTag(tag);
    }

    /* access modifiers changed from: protected */
    public void clearThumbnailAndStrips() {
        this.mThumbnail = null;
        this.mStripBytes.clear();
    }

    /* access modifiers changed from: protected */
    public void removeThumbnailData() {
        clearThumbnailAndStrips();
        this.mIfdDatas[1] = null;
    }

    /* access modifiers changed from: protected */
    public void removeTag(short tagId, int ifdId) {
        IfdData ifdData = this.mIfdDatas[ifdId];
        if (ifdData != null) {
            ifdData.removeTag(tagId);
        }
    }

    /* access modifiers changed from: protected */
    public String getUserComment() {
        IfdData ifdData = this.mIfdDatas[0];
        if (ifdData == null) {
            return null;
        }
        ExifTag tag = ifdData.getTag(ExifInterface.getTrueTagKey(ExifInterface.TAG_USER_COMMENT));
        if (tag == null || tag.getComponentCount() < 8) {
            return null;
        }
        byte[] buf = new byte[tag.getComponentCount()];
        tag.getBytes(buf);
        byte[] code = new byte[8];
        System.arraycopy(buf, 0, code, 0, 8);
        try {
            if (Arrays.equals(code, USER_COMMENT_ASCII)) {
                return new String(buf, 8, buf.length - 8, CharEncoding.US_ASCII);
            }
            if (Arrays.equals(code, USER_COMMENT_JIS)) {
                return new String(buf, 8, buf.length - 8, "EUC-JP");
            }
            if (Arrays.equals(code, USER_COMMENT_UNICODE)) {
                return new String(buf, 8, buf.length - 8, CharEncoding.UTF_16);
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "Failed to decode the user comment");
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public List<ExifTag> getAllTags() {
        IfdData[] ifdDataArr;
        ArrayList<ExifTag> ret = new ArrayList<>();
        for (IfdData d : this.mIfdDatas) {
            if (d != null) {
                ExifTag[] tags = d.getAllTags();
                if (tags != null) {
                    for (ExifTag t : tags) {
                        ret.add(t);
                    }
                }
            }
        }
        if (ret.size() == 0) {
            return null;
        }
        return ret;
    }

    /* access modifiers changed from: protected */
    public List<ExifTag> getAllTagsForIfd(int ifd) {
        IfdData d = this.mIfdDatas[ifd];
        if (d == null) {
            return null;
        }
        ExifTag[] tags = d.getAllTags();
        if (tags == null) {
            return null;
        }
        ArrayList<ExifTag> ret = new ArrayList<>(tags.length);
        for (ExifTag t : tags) {
            ret.add(t);
        }
        if (ret.size() == 0) {
            return null;
        }
        return ret;
    }

    /* access modifiers changed from: protected */
    public List<ExifTag> getAllTagsForTagId(short tag) {
        IfdData[] ifdDataArr;
        ArrayList<ExifTag> ret = new ArrayList<>();
        for (IfdData d : this.mIfdDatas) {
            if (d != null) {
                ExifTag t = d.getTag(tag);
                if (t != null) {
                    ret.add(t);
                }
            }
        }
        if (ret.size() == 0) {
            return null;
        }
        return ret;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ExifData)) {
            return false;
        }
        ExifData data = (ExifData) obj;
        if (data.mByteOrder != this.mByteOrder || data.mStripBytes.size() != this.mStripBytes.size() || !Arrays.equals(data.mThumbnail, this.mThumbnail)) {
            return false;
        }
        for (int i = 0; i < this.mStripBytes.size(); i++) {
            if (!Arrays.equals((byte[]) data.mStripBytes.get(i), (byte[]) this.mStripBytes.get(i))) {
                return false;
            }
        }
        for (int i2 = 0; i2 < 5; i2++) {
            IfdData ifd1 = data.getIfdData(i2);
            IfdData ifd2 = getIfdData(i2);
            if (ifd1 != ifd2 && ifd1 != null && !ifd1.equals(ifd2)) {
                return false;
            }
        }
        return true;
    }
}
