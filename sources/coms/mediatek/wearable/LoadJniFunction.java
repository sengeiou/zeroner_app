package coms.mediatek.wearable;

class LoadJniFunction {
    private static LoadJniFunction a;

    static {
        System.loadLibrary("Command_iwown");
    }

    private LoadJniFunction() {
    }

    public static synchronized LoadJniFunction a() {
        LoadJniFunction loadJniFunction;
        synchronized (LoadJniFunction.class) {
            if (a == null) {
                a = new LoadJniFunction();
            }
            loadJniFunction = a;
        }
        return loadJniFunction;
    }

    public int a(byte[] bArr, int i) {
        return getCmdTypeFromJni(bArr, i);
    }

    public byte[] a(int i, String str) {
        return getDataCmdFromJni(i, str);
    }

    public int b(byte[] bArr, int i) {
        return getDataLenthFromJni(bArr, i);
    }

    public byte[] b() {
        return getEPOUrlFromJni();
    }

    public native int getCmdTypeFromJni(byte[] bArr, int i);

    public native byte[] getDataCmdFromJni(int i, String str);

    public native int getDataLenthFromJni(byte[] bArr, int i);

    public native byte[] getEPOUrlFromJni();
}
