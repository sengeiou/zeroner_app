package com.facebook.stetho.dumpapp.plugins;

import android.content.Context;
import android.os.Environment;
import com.facebook.stetho.dumpapp.ArgsHelper;
import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumpUsageException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FilesDumperPlugin implements DumperPlugin {
    private static final String NAME = "files";
    private final Context mContext;

    public FilesDumperPlugin(Context context) {
        this.mContext = context;
    }

    public String getName() {
        return NAME;
    }

    public void dump(DumperContext dumpContext) throws DumpException {
        Iterator<String> args = dumpContext.getArgsAsList().iterator();
        String command = ArgsHelper.nextOptionalArg(args, "");
        if ("ls".equals(command)) {
            doLs(dumpContext.getStdout());
        } else if ("tree".equals(command)) {
            doTree(dumpContext.getStdout());
        } else if ("download".equals(command)) {
            doDownload(dumpContext.getStdout(), args);
        } else {
            doUsage(dumpContext.getStdout());
            if (!"".equals(command)) {
                throw new DumpUsageException("Unknown command: " + command);
            }
        }
    }

    private void doLs(PrintStream writer) throws DumpUsageException {
        File baseDir = getBaseDir(this.mContext);
        if (baseDir.isDirectory()) {
            printDirectoryText(baseDir, "", writer);
        }
    }

    private void doTree(PrintStream writer) throws DumpUsageException {
        printDirectoryVisual(getBaseDir(this.mContext), 0, writer);
    }

    private static File getBaseDir(Context context) {
        return context.getFilesDir().getParentFile();
    }

    private static void printDirectoryText(File dir, String path, PrintStream writer) {
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                printDirectoryText(file, path + file.getName() + "/", writer);
            } else {
                writer.println(path + file.getName());
            }
        }
    }

    private static void printDirectoryVisual(File dir, int depth, PrintStream writer) {
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            printHeaderVisual(depth, writer);
            writer.print("+---");
            writer.print(file.getName());
            writer.println();
            if (file.isDirectory()) {
                printDirectoryVisual(file, depth + 1, writer);
            }
        }
    }

    private static void printHeaderVisual(int depth, PrintStream writer) {
        for (int i = 0; i < depth; i++) {
            writer.print("|   ");
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.io.PrintStream, code=java.io.OutputStream, for r12v0, types: [java.io.PrintStream, java.io.OutputStream] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0073=Splitter:B:25:0x0073, B:32:0x007e=Splitter:B:32:0x007e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doDownload(java.io.OutputStream r12, java.util.Iterator<java.lang.String> r13) throws com.facebook.stetho.dumpapp.DumpUsageException {
        /*
            r11 = this;
            r8 = 1
            r9 = 0
            java.lang.String r7 = "Must specify output file or '-'"
            java.lang.String r3 = com.facebook.stetho.dumpapp.ArgsHelper.nextArg(r13, r7)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
        L_0x000e:
            boolean r7 = r13.hasNext()
            if (r7 == 0) goto L_0x0024
            android.content.Context r10 = r11.mContext
            java.lang.Object r7 = r13.next()
            java.lang.String r7 = (java.lang.String) r7
            java.io.File r7 = resolvePossibleAppStoragePath(r10, r7)
            r5.add(r7)
            goto L_0x000e
        L_0x0024:
            java.lang.String r7 = "-"
            boolean r7 = r7.equals(r3)     // Catch:{ IOException -> 0x0074 }
            if (r7 == 0) goto L_0x0057
            r4 = r12
        L_0x002e:
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ IOException -> 0x0074 }
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0074 }
            r7.<init>(r4)     // Catch:{ IOException -> 0x0074 }
            r2.<init>(r7)     // Catch:{ IOException -> 0x0074 }
            r6 = 0
            r7 = 2048(0x800, float:2.87E-42)
            byte[] r0 = new byte[r7]     // Catch:{ all -> 0x006f }
            int r7 = r5.size()     // Catch:{ all -> 0x006f }
            if (r7 <= 0) goto L_0x0061
            int r7 = r5.size()     // Catch:{ all -> 0x006f }
            java.io.File[] r7 = new java.io.File[r7]     // Catch:{ all -> 0x006f }
            java.lang.Object[] r7 = r5.toArray(r7)     // Catch:{ all -> 0x006f }
            java.io.File[] r7 = (java.io.File[]) r7     // Catch:{ all -> 0x006f }
            r11.addFiles(r2, r0, r7)     // Catch:{ all -> 0x006f }
        L_0x0052:
            r6 = 1
            r2.close()     // Catch:{ IOException -> 0x007b }
        L_0x0056:
            return
        L_0x0057:
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0074 }
            java.io.File r7 = resolvePossibleSdcardPath(r3)     // Catch:{ IOException -> 0x0074 }
            r4.<init>(r7)     // Catch:{ IOException -> 0x0074 }
            goto L_0x002e
        L_0x0061:
            android.content.Context r7 = r11.mContext     // Catch:{ all -> 0x006f }
            java.io.File r7 = getBaseDir(r7)     // Catch:{ all -> 0x006f }
            java.io.File[] r7 = r7.listFiles()     // Catch:{ all -> 0x006f }
            r11.addFiles(r2, r0, r7)     // Catch:{ all -> 0x006f }
            goto L_0x0052
        L_0x006f:
            r7 = move-exception
            r2.close()     // Catch:{ IOException -> 0x0086 }
        L_0x0073:
            throw r7     // Catch:{ IOException -> 0x0074 }
        L_0x0074:
            r1 = move-exception
            java.lang.RuntimeException r7 = new java.lang.RuntimeException
            r7.<init>(r1)
            throw r7
        L_0x007b:
            r1 = move-exception
            if (r6 != 0) goto L_0x0084
        L_0x007e:
            com.facebook.stetho.common.Util.close(r4, r8)     // Catch:{ IOException -> 0x0074 }
            if (r6 == 0) goto L_0x0056
            throw r1     // Catch:{ IOException -> 0x0074 }
        L_0x0084:
            r8 = r9
            goto L_0x007e
        L_0x0086:
            r1 = move-exception
            if (r6 != 0) goto L_0x008f
        L_0x0089:
            com.facebook.stetho.common.Util.close(r4, r8)     // Catch:{ IOException -> 0x0074 }
            if (r6 == 0) goto L_0x0073
            throw r1     // Catch:{ IOException -> 0x0074 }
        L_0x008f:
            r8 = r9
            goto L_0x0089
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.stetho.dumpapp.plugins.FilesDumperPlugin.doDownload(java.io.PrintStream, java.util.Iterator):void");
    }

    private void addFiles(ZipOutputStream output, byte[] buf, File[] files) throws IOException {
        for (File file : files) {
            if (file.isDirectory()) {
                addFiles(output, buf, file.listFiles());
            } else {
                output.putNextEntry(new ZipEntry(relativizePath(getBaseDir(this.mContext).getParentFile(), file)));
                FileInputStream input = new FileInputStream(file);
                try {
                    copy(input, output, buf);
                } finally {
                    input.close();
                }
            }
        }
    }

    private static void copy(InputStream in, OutputStream out, byte[] buf) throws IOException {
        while (true) {
            int n = in.read(buf);
            if (n >= 0) {
                out.write(buf, 0, n);
            } else {
                return;
            }
        }
    }

    private static String relativizePath(File base, File path) {
        String baseStr = base.getAbsolutePath();
        String pathStr = path.getAbsolutePath();
        if (pathStr.startsWith(baseStr)) {
            return pathStr.substring(baseStr.length() + 1);
        }
        return pathStr;
    }

    private static File resolvePossibleAppStoragePath(Context context, String path) {
        if (path.startsWith("/")) {
            return new File(path);
        }
        return new File(getBaseDir(context), path);
    }

    private static File resolvePossibleSdcardPath(String path) {
        if (path.startsWith("/")) {
            return new File(path);
        }
        return new File(Environment.getExternalStorageDirectory(), path);
    }

    private void doUsage(PrintStream writer) {
        String str = "dumpapp files";
        String blankPrefix = "       dumpapp files ";
        writer.println("Usage: dumpapp files " + "<command> [command-options]");
        writer.println(blankPrefix + "ls");
        writer.println(blankPrefix + "tree");
        writer.println(blankPrefix + "download <output.zip> [<path>...]");
        writer.println();
        writer.println("dumpapp files ls: List files similar to the ls command");
        writer.println();
        writer.println("dumpapp files tree: List files similar to the tree command");
        writer.println();
        writer.println("dumpapp files download: Fetch internal application storage");
        writer.println("    <output.zip>: Output location or '-' for stdout");
        writer.println("    <path>: Fetch only those paths named (directories fetch recursively)");
    }
}
