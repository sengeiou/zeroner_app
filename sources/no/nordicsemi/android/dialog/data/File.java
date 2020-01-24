package no.nordicsemi.android.dialog.data;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class File {
    private static String filesDir = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Suota");
    private byte[][][] blocks;
    private byte[] bytes;
    private int bytesAvailable;
    private int chunksPerBlockCount;
    private byte crc;
    private int fileBlockSize = 0;
    private InputStream inputStream;
    private int numberOfBlocks = -1;
    private int totalChunkCount;
    private int type;

    public File(InputStream inputStream2) throws IOException {
        this.inputStream = inputStream2;
        this.bytesAvailable = this.inputStream.available();
    }

    public void setType(int type2) throws IOException {
        this.type = type2;
        if (type2 == 1) {
            this.bytes = new byte[(this.bytesAvailable + 1)];
            this.inputStream.read(this.bytes);
            this.crc = getCrc();
            this.bytes[this.bytesAvailable] = this.crc;
            return;
        }
        this.bytes = new byte[this.bytesAvailable];
        this.inputStream.read(this.bytes);
    }

    public int getFileBlockSize() {
        return this.fileBlockSize;
    }

    public int getNumberOfBytes() {
        return this.bytes.length;
    }

    public void setFileBlockSize(int fileBlockSize2) {
        this.fileBlockSize = fileBlockSize2;
        this.chunksPerBlockCount = (int) Math.ceil(((double) fileBlockSize2) / 20.0d);
        this.numberOfBlocks = (int) Math.ceil(((double) this.bytes.length) / ((double) this.fileBlockSize));
        initBlocks();
    }

    private void initBlocksSuota() {
        int totalChunkCounter = 0;
        this.blocks = new byte[this.numberOfBlocks][][];
        int byteOffset = 0;
        for (int i = 0; i < this.numberOfBlocks; i++) {
            int blockSize = this.fileBlockSize;
            if (i + 1 == this.numberOfBlocks) {
                blockSize = this.bytes.length % this.fileBlockSize;
            }
            int chunkNumber = 0;
            this.blocks[i] = new byte[((int) Math.ceil(((double) blockSize) / 20.0d))][];
            for (int j = 0; j < blockSize; j += 20) {
                int chunkSize = 20;
                if (byteOffset + 20 > this.bytes.length) {
                    chunkSize = this.bytes.length - byteOffset;
                } else if (j + 20 > blockSize) {
                    chunkSize = this.fileBlockSize % 20;
                }
                Log.d("chunk", "total bytes: " + this.bytes.length + ", offset: " + byteOffset + ", block: " + i + ", chunk: " + (chunkNumber + 1) + ", blocksize: " + blockSize + ", chunksize: " + chunkSize);
                this.blocks[i][chunkNumber] = Arrays.copyOfRange(this.bytes, byteOffset, byteOffset + chunkSize);
                byteOffset += chunkSize;
                chunkNumber++;
                totalChunkCounter++;
            }
        }
        this.totalChunkCount = totalChunkCounter;
    }

    private void initBlocksSpota() {
        this.numberOfBlocks = 1;
        this.fileBlockSize = this.bytes.length;
        this.totalChunkCount = (int) Math.ceil(((double) this.bytes.length) / 20.0d);
        this.blocks = (byte[][][]) Array.newInstance(byte[].class, new int[]{this.numberOfBlocks, this.totalChunkCount});
        int byteOffset = 0;
        int chunkSize = 20;
        for (int i = 0; i < this.totalChunkCount; i++) {
            if (byteOffset + 20 > this.bytes.length) {
                chunkSize = this.bytes.length - byteOffset;
            }
            this.blocks[0][i] = Arrays.copyOfRange(this.bytes, byteOffset, byteOffset + chunkSize);
            byteOffset += 20;
        }
    }

    private void initBlocks() {
        if (this.type == 1) {
            initBlocksSuota();
        } else if (this.type == 2) {
            initBlocksSpota();
        }
    }

    public byte[][] getBlock(int index) {
        return this.blocks[index];
    }

    public void close() {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public int getNumberOfBlocks() {
        return this.numberOfBlocks;
    }

    public int getChunksPerBlockCount() {
        return this.chunksPerBlockCount;
    }

    public int getTotalChunkCount() {
        return this.totalChunkCount;
    }

    private byte getCrc() throws IOException {
        byte crc_code = 0;
        for (int i = 0; i < this.bytesAvailable; i++) {
            crc_code = (byte) (crc_code ^ Byte.valueOf(this.bytes[i]).intValue());
        }
        Log.d("crc", "crc: " + String.format("%#10x", new Object[]{Byte.valueOf(crc_code)}));
        return crc_code;
    }

    public static File getByFileName(String filename) throws IOException {
        return new File(new FileInputStream(filesDir + "/" + filename));
    }

    public static Map list() {
        java.io.File[] file = new java.io.File(filesDir).listFiles();
        Log.d("Files", "Size: " + file.length);
        Map map = new HashMap();
        for (int i = 0; i < file.length; i++) {
            Log.d("Files", "FileName:" + file[i].getName());
            map.put(file[i].getName(), file[i].getName());
        }
        return map;
    }

    public static void createFileDirectories(Context c) {
        new java.io.File(filesDir).mkdirs();
    }
}
