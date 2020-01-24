package no.nordicsemi.android.dfu.internal;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import no.nordicsemi.android.dfu.internal.manifest.FileInfo;
import no.nordicsemi.android.dfu.internal.manifest.Manifest;
import no.nordicsemi.android.dfu.internal.manifest.ManifestFile;
import no.nordicsemi.android.dfu.internal.manifest.SoftDeviceBootloaderFileInfo;

public class ArchiveInputStream extends ZipInputStream {
    private static final String APPLICATION_BIN = "application.bin";
    private static final String APPLICATION_HEX = "application.hex";
    private static final String APPLICATION_INIT = "application.dat";
    private static final String BOOTLOADER_BIN = "bootloader.bin";
    private static final String BOOTLOADER_HEX = "bootloader.hex";
    private static final String MANIFEST = "manifest.json";
    private static final String SOFTDEVICE_BIN = "softdevice.bin";
    private static final String SOFTDEVICE_HEX = "softdevice.hex";
    private static final String SYSTEM_INIT = "system.dat";
    private byte[] applicationBytes;
    private byte[] applicationInitBytes;
    private int applicationSize;
    private byte[] bootloaderBytes;
    private int bootloaderSize;
    private int bytesRead = 0;
    private int bytesReadFromCurrentSource = 0;
    private int bytesReadFromMarkedSource;
    private CRC32 crc32 = new CRC32();
    private byte[] currentSource;
    private Map<String, byte[]> entries = new HashMap();
    private Manifest manifest;
    private byte[] markedSource;
    private byte[] softDeviceAndBootloaderBytes;
    private byte[] softDeviceBytes;
    private int softDeviceSize;
    private byte[] systemInitBytes;

    public ArchiveInputStream(InputStream stream, int mbrSize, int types) throws IOException {
        super(stream);
        try {
            parseZip(mbrSize);
            if (this.manifest != null) {
                boolean valid = false;
                if (this.manifest.getApplicationInfo() != null && (types == 0 || (types & 4) > 0)) {
                    FileInfo application = this.manifest.getApplicationInfo();
                    this.applicationBytes = (byte[]) this.entries.get(application.getBinFileName());
                    this.applicationInitBytes = (byte[]) this.entries.get(application.getDatFileName());
                    if (this.applicationBytes == null) {
                        throw new IOException("Application file " + application.getBinFileName() + " not found.");
                    }
                    this.applicationSize = this.applicationBytes.length;
                    this.currentSource = this.applicationBytes;
                    valid = true;
                }
                if (this.manifest.getBootloaderInfo() != null && (types == 0 || (types & 2) > 0)) {
                    if (this.systemInitBytes != null) {
                        throw new IOException("Manifest: softdevice and bootloader specified. Use softdevice_bootloader instead.");
                    }
                    FileInfo bootloader = this.manifest.getBootloaderInfo();
                    this.bootloaderBytes = (byte[]) this.entries.get(bootloader.getBinFileName());
                    this.systemInitBytes = (byte[]) this.entries.get(bootloader.getDatFileName());
                    if (this.bootloaderBytes == null) {
                        throw new IOException("Bootloader file " + bootloader.getBinFileName() + " not found.");
                    }
                    this.bootloaderSize = this.bootloaderBytes.length;
                    this.currentSource = this.bootloaderBytes;
                    valid = true;
                }
                if (this.manifest.getSoftdeviceInfo() != null && (types == 0 || (types & 1) > 0)) {
                    FileInfo softdevice = this.manifest.getSoftdeviceInfo();
                    this.softDeviceBytes = (byte[]) this.entries.get(softdevice.getBinFileName());
                    this.systemInitBytes = (byte[]) this.entries.get(softdevice.getDatFileName());
                    if (this.softDeviceBytes == null) {
                        throw new IOException("SoftDevice file " + softdevice.getBinFileName() + " not found.");
                    }
                    this.softDeviceSize = this.softDeviceBytes.length;
                    this.currentSource = this.softDeviceBytes;
                    valid = true;
                }
                if (this.manifest.getSoftdeviceBootloaderInfo() != null && (types == 0 || ((types & 1) > 0 && (types & 2) > 0))) {
                    if (this.systemInitBytes != null) {
                        throw new IOException("Manifest: The softdevice_bootloader may not be used together with softdevice or bootloader.");
                    }
                    SoftDeviceBootloaderFileInfo system = this.manifest.getSoftdeviceBootloaderInfo();
                    this.softDeviceAndBootloaderBytes = (byte[]) this.entries.get(system.getBinFileName());
                    this.systemInitBytes = (byte[]) this.entries.get(system.getDatFileName());
                    if (this.softDeviceAndBootloaderBytes == null) {
                        throw new IOException("File " + system.getBinFileName() + " not found.");
                    }
                    this.softDeviceSize = system.getSoftdeviceSize();
                    this.bootloaderSize = system.getBootloaderSize();
                    this.currentSource = this.softDeviceAndBootloaderBytes;
                    valid = true;
                }
                if (!valid) {
                    throw new IOException("Manifest file must specify at least one file.");
                }
            } else {
                boolean valid2 = false;
                if (types == 0 || (types & 4) > 0) {
                    this.applicationBytes = (byte[]) this.entries.get(APPLICATION_HEX);
                    if (this.applicationBytes == null) {
                        this.applicationBytes = (byte[]) this.entries.get(APPLICATION_BIN);
                    }
                    if (this.applicationBytes != null) {
                        this.applicationSize = this.applicationBytes.length;
                        this.applicationInitBytes = (byte[]) this.entries.get(APPLICATION_INIT);
                        this.currentSource = this.applicationBytes;
                        valid2 = true;
                    }
                }
                if (types == 0 || (types & 2) > 0) {
                    this.bootloaderBytes = (byte[]) this.entries.get(BOOTLOADER_HEX);
                    if (this.bootloaderBytes == null) {
                        this.bootloaderBytes = (byte[]) this.entries.get(BOOTLOADER_BIN);
                    }
                    if (this.bootloaderBytes != null) {
                        this.bootloaderSize = this.bootloaderBytes.length;
                        this.systemInitBytes = (byte[]) this.entries.get(SYSTEM_INIT);
                        this.currentSource = this.bootloaderBytes;
                        valid2 = true;
                    }
                }
                if (types == 0 || (types & 1) > 0) {
                    this.softDeviceBytes = (byte[]) this.entries.get(SOFTDEVICE_HEX);
                    if (this.softDeviceBytes == null) {
                        this.softDeviceBytes = (byte[]) this.entries.get(SOFTDEVICE_BIN);
                    }
                    if (this.softDeviceBytes != null) {
                        this.softDeviceSize = this.softDeviceBytes.length;
                        this.systemInitBytes = (byte[]) this.entries.get(SYSTEM_INIT);
                        this.currentSource = this.softDeviceBytes;
                        valid2 = true;
                    }
                }
                if (!valid2) {
                    throw new IOException("The ZIP file must contain an Application, a Soft Device and/or a Bootloader.");
                }
            }
            mark(0);
        } finally {
            super.close();
        }
    }

    private void parseZip(int mbrSize) throws IOException {
        byte[] buffer = new byte[1024];
        String manifestData = null;
        while (true) {
            ZipEntry ze = getNextEntry();
            if (ze == null) {
                break;
            }
            String filename = ze.getName();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (true) {
                int count = super.read(buffer);
                if (count == -1) {
                    break;
                }
                baos.write(buffer, 0, count);
            }
            byte[] source = baos.toByteArray();
            if (filename.toLowerCase(Locale.US).endsWith("hex")) {
                HexInputStream is = new HexInputStream(source, mbrSize);
                source = new byte[is.available()];
                is.read(source);
                is.close();
            }
            if (MANIFEST.equals(filename)) {
                manifestData = new String(source, "UTF-8");
            } else {
                this.entries.put(filename, source);
            }
        }
        if (manifestData != null) {
            this.manifest = ((ManifestFile) new Gson().fromJson(manifestData, ManifestFile.class)).getManifest();
        }
    }

    public void close() throws IOException {
        this.softDeviceBytes = null;
        this.bootloaderBytes = null;
        this.softDeviceBytes = null;
        this.softDeviceAndBootloaderBytes = null;
        this.applicationSize = 0;
        this.bootloaderSize = 0;
        this.softDeviceSize = 0;
        this.currentSource = null;
        this.bytesReadFromCurrentSource = 0;
        this.bytesRead = 0;
        super.close();
    }

    public int read(@NonNull byte[] buffer) throws IOException {
        int size;
        int nextSize;
        int maxSize = this.currentSource.length - this.bytesReadFromCurrentSource;
        if (buffer.length <= maxSize) {
            size = buffer.length;
        } else {
            size = maxSize;
        }
        System.arraycopy(this.currentSource, this.bytesReadFromCurrentSource, buffer, 0, size);
        this.bytesReadFromCurrentSource += size;
        if (buffer.length > size) {
            if (startNextFile() == null) {
                this.bytesRead += size;
                this.crc32.update(buffer, 0, size);
                return size;
            }
            int maxSize2 = this.currentSource.length;
            if (buffer.length - size <= maxSize2) {
                nextSize = buffer.length - size;
            } else {
                nextSize = maxSize2;
            }
            System.arraycopy(this.currentSource, 0, buffer, size, nextSize);
            this.bytesReadFromCurrentSource += nextSize;
            size += nextSize;
        }
        this.bytesRead += size;
        this.crc32.update(buffer, 0, size);
        return size;
    }

    public boolean markSupported() {
        return true;
    }

    public void mark(int readlimit) {
        this.markedSource = this.currentSource;
        this.bytesReadFromMarkedSource = this.bytesReadFromCurrentSource;
    }

    public void reset() throws IOException {
        if (this.applicationBytes == null || (this.softDeviceBytes == null && this.bootloaderBytes == null && this.softDeviceAndBootloaderBytes == null)) {
            this.currentSource = this.markedSource;
            int i = this.bytesReadFromMarkedSource;
            this.bytesReadFromCurrentSource = i;
            this.bytesRead = i;
            this.crc32.reset();
            if (this.currentSource == this.bootloaderBytes && this.softDeviceBytes != null) {
                this.crc32.update(this.softDeviceBytes);
                this.bytesRead += this.softDeviceSize;
            }
            this.crc32.update(this.currentSource, 0, this.bytesReadFromCurrentSource);
            return;
        }
        throw new UnsupportedOperationException("Application must be sent in a separate connection.");
    }

    public int getBytesRead() {
        return this.bytesRead;
    }

    public long getCrc32() {
        return this.crc32.getValue();
    }

    public int getContentType() {
        byte type = 0;
        if (this.softDeviceAndBootloaderBytes != null) {
            type = (byte) 3;
        }
        if (this.softDeviceSize > 0) {
            type = (byte) (type | 1);
        }
        if (this.bootloaderSize > 0) {
            type = (byte) (type | 2);
        }
        if (this.applicationSize > 0) {
            return (byte) (type | 4);
        }
        return type;
    }

    public int setContentType(int type) {
        if (this.bytesRead > 0) {
            throw new UnsupportedOperationException("Content type must not be change after reading content");
        }
        int t = getContentType() & type;
        if ((t & 1) == 0) {
            this.softDeviceBytes = null;
            if (this.softDeviceAndBootloaderBytes != null) {
                this.softDeviceAndBootloaderBytes = null;
                this.bootloaderSize = 0;
            }
            this.softDeviceSize = 0;
        }
        if ((t & 2) == 0) {
            this.bootloaderBytes = null;
            if (this.softDeviceAndBootloaderBytes != null) {
                this.softDeviceAndBootloaderBytes = null;
                this.softDeviceSize = 0;
            }
            this.bootloaderSize = 0;
        }
        if ((t & 4) == 0) {
            this.applicationBytes = null;
            this.applicationSize = 0;
        }
        mark(0);
        return t;
    }

    private byte[] startNextFile() {
        byte[] ret;
        if (this.currentSource == this.softDeviceBytes && this.bootloaderBytes != null) {
            ret = this.bootloaderBytes;
            this.currentSource = ret;
        } else if (this.currentSource == this.applicationBytes || this.applicationBytes == null) {
            ret = null;
            this.currentSource = null;
        } else {
            ret = this.applicationBytes;
            this.currentSource = ret;
        }
        this.bytesReadFromCurrentSource = 0;
        return ret;
    }

    public int available() {
        if (this.softDeviceAndBootloaderBytes != null && this.softDeviceSize == 0 && this.bootloaderSize == 0) {
            return (this.softDeviceAndBootloaderBytes.length + this.applicationSize) - this.bytesRead;
        }
        return ((this.softDeviceSize + this.bootloaderSize) + this.applicationSize) - this.bytesRead;
    }

    public int softDeviceImageSize() {
        return this.softDeviceSize;
    }

    public int bootloaderImageSize() {
        return this.bootloaderSize;
    }

    public int applicationImageSize() {
        return this.applicationSize;
    }

    public byte[] getSystemInit() {
        return this.systemInitBytes;
    }

    public byte[] getApplicationInit() {
        return this.applicationInitBytes;
    }

    public boolean isSecureDfuRequired() {
        return this.manifest != null && this.manifest.isSecureDfuRequired();
    }
}
