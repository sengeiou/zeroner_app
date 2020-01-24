package com.google.api.client.util.store;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Logger;

public class FileDataStoreFactory extends AbstractDataStoreFactory {
    private static final Logger LOGGER = Logger.getLogger(FileDataStoreFactory.class.getName());
    private final File dataDirectory;

    static class FileDataStore<V extends Serializable> extends AbstractMemoryDataStore<V> {
        private final File dataFile;

        FileDataStore(FileDataStoreFactory dataStore, File dataDirectory, String id) throws IOException {
            super(dataStore, id);
            this.dataFile = new File(dataDirectory, id);
            if (IOUtils.isSymbolicLink(this.dataFile)) {
                String valueOf = String.valueOf(String.valueOf(this.dataFile));
                throw new IOException(new StringBuilder(valueOf.length() + 31).append("unable to use a symbolic link: ").append(valueOf).toString());
            } else if (this.dataFile.createNewFile()) {
                this.keyValueMap = Maps.newHashMap();
                save();
            } else {
                this.keyValueMap = (HashMap) IOUtils.deserialize((InputStream) new FileInputStream(this.dataFile));
            }
        }

        /* access modifiers changed from: 0000 */
        public void save() throws IOException {
            IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
        }

        public FileDataStoreFactory getDataStoreFactory() {
            return (FileDataStoreFactory) super.getDataStoreFactory();
        }
    }

    public FileDataStoreFactory(File dataDirectory2) throws IOException {
        File dataDirectory3 = dataDirectory2.getCanonicalFile();
        this.dataDirectory = dataDirectory3;
        if (IOUtils.isSymbolicLink(dataDirectory3)) {
            String valueOf = String.valueOf(String.valueOf(dataDirectory3));
            throw new IOException(new StringBuilder(valueOf.length() + 31).append("unable to use a symbolic link: ").append(valueOf).toString());
        } else if (dataDirectory3.exists() || dataDirectory3.mkdirs()) {
            setPermissionsToOwnerOnly(dataDirectory3);
        } else {
            String valueOf2 = String.valueOf(String.valueOf(dataDirectory3));
            throw new IOException(new StringBuilder(valueOf2.length() + 28).append("unable to create directory: ").append(valueOf2).toString());
        }
    }

    public final File getDataDirectory() {
        return this.dataDirectory;
    }

    /* access modifiers changed from: protected */
    public <V extends Serializable> DataStore<V> createDataStore(String id) throws IOException {
        return new FileDataStore(this, this.dataDirectory, id);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x009d, code lost:
        if (((java.lang.Boolean) r2.invoke(r10, new java.lang.Object[]{java.lang.Boolean.valueOf(false), java.lang.Boolean.valueOf(false)})).booleanValue() == false) goto L_0x009f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void setPermissionsToOwnerOnly(java.io.File r10) throws java.io.IOException {
        /*
            java.lang.Class<java.io.File> r5 = java.io.File.class
            java.lang.String r6 = "setReadable"
            r7 = 2
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r8 = 0
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r7[r8] = r9     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r8 = 1
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r7[r8] = r9     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.reflect.Method r3 = r5.getMethod(r6, r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Class<java.io.File> r5 = java.io.File.class
            java.lang.String r6 = "setWritable"
            r7 = 2
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r8 = 0
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r7[r8] = r9     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r8 = 1
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r7[r8] = r9     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.reflect.Method r4 = r5.getMethod(r6, r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Class<java.io.File> r5 = java.io.File.class
            java.lang.String r6 = "setExecutable"
            r7 = 2
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r8 = 0
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r7[r8] = r9     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r8 = 1
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r7[r8] = r9     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.reflect.Method r2 = r5.getMethod(r6, r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 0
            r7 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 1
            r7 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Object r5 = r3.invoke(r10, r5)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            boolean r5 = r5.booleanValue()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            if (r5 == 0) goto L_0x009f
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 0
            r7 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 1
            r7 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Object r5 = r4.invoke(r10, r5)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            boolean r5 = r5.booleanValue()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            if (r5 == 0) goto L_0x009f
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 0
            r7 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 1
            r7 = 0
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Object r5 = r2.invoke(r10, r5)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            boolean r5 = r5.booleanValue()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            if (r5 != 0) goto L_0x00c6
        L_0x009f:
            java.util.logging.Logger r5 = LOGGER     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r6 = java.lang.String.valueOf(r10)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            int r8 = r6.length()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            int r8 = r8 + 44
            r7.<init>(r8)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r8 = "unable to change permissions for everybody: "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.StringBuilder r6 = r7.append(r6)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r6 = r6.toString()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5.warning(r6)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
        L_0x00c6:
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 0
            r7 = 1
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 1
            r7 = 1
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Object r5 = r3.invoke(r10, r5)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            boolean r5 = r5.booleanValue()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            if (r5 == 0) goto L_0x0123
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 0
            r7 = 1
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 1
            r7 = 1
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Object r5 = r4.invoke(r10, r5)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            boolean r5 = r5.booleanValue()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            if (r5 == 0) goto L_0x0123
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 0
            r7 = 1
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r6 = 1
            r7 = 1
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5[r6] = r7     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Object r5 = r2.invoke(r10, r5)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            boolean r5 = r5.booleanValue()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            if (r5 != 0) goto L_0x014a
        L_0x0123:
            java.util.logging.Logger r5 = LOGGER     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r6 = java.lang.String.valueOf(r10)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            int r8 = r6.length()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            int r8 = r8 + 40
            r7.<init>(r8)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r8 = "unable to change permissions for owner: "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.StringBuilder r6 = r7.append(r6)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            java.lang.String r6 = r6.toString()     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
            r5.warning(r6)     // Catch:{ InvocationTargetException -> 0x014b, NoSuchMethodException -> 0x015b, SecurityException -> 0x018f, IllegalAccessException -> 0x018d, IllegalArgumentException -> 0x018b }
        L_0x014a:
            return
        L_0x014b:
            r1 = move-exception
            java.lang.Throwable r0 = r1.getCause()
            java.lang.Class<java.io.IOException> r5 = java.io.IOException.class
            com.google.api.client.util.Throwables.propagateIfPossible(r0, r5)
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            r5.<init>(r0)
            throw r5
        L_0x015b:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER
            java.lang.String r6 = java.lang.String.valueOf(r10)
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            int r8 = r6.length()
            int r8 = r8 + 93
            r7.<init>(r8)
            java.lang.String r8 = "Unable to set permissions for "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.String r7 = ", likely because you are running a version of Java prior to 1.6"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.warning(r6)
            goto L_0x014a
        L_0x018b:
            r5 = move-exception
            goto L_0x014a
        L_0x018d:
            r5 = move-exception
            goto L_0x014a
        L_0x018f:
            r5 = move-exception
            goto L_0x014a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.util.store.FileDataStoreFactory.setPermissionsToOwnerOnly(java.io.File):void");
    }
}
