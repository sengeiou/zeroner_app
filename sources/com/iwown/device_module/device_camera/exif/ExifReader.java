package com.iwown.device_module.device_camera.exif;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

class ExifReader {
    private static final String TAG = "ExifReader";
    private final ExifInterface mInterface;

    ExifReader(ExifInterface iRef) {
        this.mInterface = iRef;
    }

    /* access modifiers changed from: protected */
    public ExifData read(InputStream inputStream) throws ExifInvalidFormatException, IOException {
        ExifParser parser = ExifParser.parse(inputStream, this.mInterface);
        ExifData exifData = new ExifData(parser.getByteOrder());
        for (int event = parser.next(); event != 5; event = parser.next()) {
            switch (event) {
                case 0:
                    exifData.addIfdData(new IfdData(parser.getCurrentIfd()));
                    break;
                case 1:
                    ExifTag tag = parser.getTag();
                    if (tag.hasValue()) {
                        exifData.getIfdData(tag.getIfd()).setTag(tag);
                        break;
                    } else {
                        parser.registerForTagValue(tag);
                        break;
                    }
                case 2:
                    ExifTag tag2 = parser.getTag();
                    if (tag2.getDataType() == 7) {
                        parser.readFullTagValue(tag2);
                    }
                    exifData.getIfdData(tag2.getIfd()).setTag(tag2);
                    break;
                case 3:
                    byte[] buf = new byte[parser.getCompressedImageSize()];
                    if (buf.length != parser.read(buf)) {
                        Log.w(TAG, "Failed to read the compressed thumbnail");
                        break;
                    } else {
                        exifData.setCompressedThumbnail(buf);
                        break;
                    }
                case 4:
                    byte[] buf2 = new byte[parser.getStripSize()];
                    if (buf2.length != parser.read(buf2)) {
                        Log.w(TAG, "Failed to read the strip bytes");
                        break;
                    } else {
                        exifData.setStripBytes(parser.getStripIndex(), buf2);
                        break;
                    }
            }
        }
        return exifData;
    }
}
