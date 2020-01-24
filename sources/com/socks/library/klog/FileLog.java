package com.socks.library.klog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class FileLog {
    private static final String FILE_FORMAT = ".log";
    private static final String FILE_PREFIX = "KLog_";

    public static void printFile(String tag, File targetDirectory, @Nullable String fileName, String headString, String msg) {
        if (fileName == null) {
            fileName = getFileName();
        }
        if (save(targetDirectory, fileName, msg)) {
            Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
        } else {
            Log.e(tag, headString + "save log fails !");
        }
    }

    private static boolean save(File dic, @NonNull String fileName, String msg) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(dic, fileName));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg);
            outputStreamWriter.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        } catch (UnsupportedEncodingException e2) {
            ThrowableExtension.printStackTrace(e2);
            return false;
        } catch (IOException e3) {
            ThrowableExtension.printStackTrace(e3);
            return false;
        } catch (Exception e4) {
            ThrowableExtension.printStackTrace(e4);
            return false;
        }
    }

    private static String getFileName() {
        return FILE_PREFIX + Long.toString(System.currentTimeMillis() + ((long) new Random().nextInt(10000))).substring(4) + FILE_FORMAT;
    }
}
