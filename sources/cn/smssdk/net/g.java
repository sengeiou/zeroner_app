package cn.smssdk.net;

import android.text.TextUtils;
import android.util.Base64;
import cn.smssdk.utils.SMSLog;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.Hashon;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

public class g {
    private static Hashon a = new Hashon();
    private static String b = null;
    private static String c = "c0639567f182bd26b1ef4bc13bba7a4d12cbb891302e2bf5da59da50e9b418621f45c6f528972f6b7410ea38f8eb3369f39c7fc35246b8dddd595b5698155b53";
    private static String d = "35b2181b4f1eca4e19542e86e2439f5cdd1c9253fc4b760c372ba4fabdf750c3a04ec9dfada98428d75a9ed9e3078652e5d07b10467bd9328f3a66be21064621";
    private static int e = 128;

    public static void a(String str, String str2, int i) {
        c = str;
        d = str2;
        e = i;
    }

    public static void a(String str) {
        b = str;
    }

    public static boolean a() {
        return !TextUtils.isEmpty(b);
    }

    public static byte[] b(String str) {
        try {
            return Data.AES128Encode(Data.rawMD5(b.getBytes()), str);
        } catch (Throwable th) {
            SMSLog.getInstance().d(th);
            return null;
        }
    }

    public static byte[] a(byte[] bArr) {
        try {
            return Data.AES128Decode(Data.rawMD5(b.getBytes()), bArr);
        } catch (Throwable th) {
            SMSLog.getInstance().d(th);
            return null;
        }
    }

    public static byte[] a(HashMap<String, Object> hashMap, boolean z, int i) {
        byte[] b2;
        String fromHashMap = a.fromHashMap(hashMap);
        SMSLog.getInstance().d("data before encode: " + fromHashMap, new Object[0]);
        byte[] bytes = fromHashMap.getBytes();
        if (z) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bytes);
            gZIPOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        }
        if (i != 1) {
            b2 = b(bytes);
        } else if (TextUtils.isEmpty(b)) {
            return null;
        } else {
            b2 = Data.AES128Encode(Data.rawMD5(b.getBytes()), bytes);
        }
        SMSLog.getInstance().i("after encode data size = " + b2.length, new Object[0]);
        return b2;
    }

    public static String a(byte[] bArr, int i) {
        byte[] c2;
        if (i != 1) {
            c2 = c(bArr);
        } else if (TextUtils.isEmpty(b)) {
            return null;
        } else {
            c2 = Data.AES128Decode(Data.rawMD5(b.getBytes()), bArr);
        }
        String str = new String(c2, "utf-8");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        throw new Throwable("[decode]Response is empty");
    }

    public static String a(String str, Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        return Base64.encodeToString(Data.AES128Encode(Data.rawMD5(str.getBytes()), Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2).getBytes()), 2);
    }

    public static Object a(String str, String str2) {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(new String(Data.AES128Decode(Data.rawMD5(str.getBytes()), Base64.decode(str2, 2)), "UTF-8"), 2)));
        String str3 = (String) objectInputStream.readObject();
        objectInputStream.close();
        return str3;
    }

    public static String c(String str) {
        return Data.byteToHex(Data.AES128Encode(Data.rawMD5("sms.mob.com.sdk.2.0.0".getBytes()), str.getBytes()));
    }

    public static String d(String str) {
        byte[] AES128Decode = Data.AES128Decode(Data.rawMD5("sms.mob.com.sdk.2.0.0".getBytes()), e(str));
        if (AES128Decode == null) {
            return null;
        }
        return new String(AES128Decode, "utf-8");
    }

    public static boolean b() {
        c = "c0639567f182bd26b1ef4bc13bba7a4d12cbb891302e2bf5da59da50e9b418621f45c6f528972f6b7410ea38f8eb3369f39c7fc35246b8dddd595b5698155b53";
        d = "35b2181b4f1eca4e19542e86e2439f5cdd1c9253fc4b760c372ba4fabdf750c3a04ec9dfada98428d75a9ed9e3078652e5d07b10467bd9328f3a66be21064621";
        e = 128;
        return true;
    }

    public static byte[] e(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length % 2 == 1) {
            return null;
        }
        int i = length / 2;
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            try {
                bArr[i2] = (byte) Integer.parseInt(str.substring(i2 * 2, (i2 * 2) + 2), 16);
                i2++;
            } catch (Throwable th) {
                return null;
            }
        }
        return bArr;
    }

    private static byte[] b(byte[] bArr) {
        return a(bArr, e, new BigInteger(c, 16), new BigInteger(d, 16));
    }

    private static byte[] a(byte[] bArr, int i, BigInteger bigInteger, BigInteger bigInteger2) {
        int i2 = i / 8;
        int i3 = i2 - 11;
        int i4 = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        while (bArr.length > i4) {
            int min = Math.min(bArr.length - i4, i3);
            a(bArr, i4, min, bigInteger, bigInteger2, i2, dataOutputStream);
            i4 += min;
        }
        dataOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private static void a(byte[] bArr, int i, int i2, BigInteger bigInteger, BigInteger bigInteger2, int i3, DataOutputStream dataOutputStream) {
        if (!(bArr.length == i2 && i == 0)) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        BigInteger bigInteger3 = new BigInteger(b(bArr, i3));
        if (bigInteger3.compareTo(bigInteger2) > 0) {
            throw new Throwable("the message must be smaller than the modulus");
        }
        byte[] byteArray = bigInteger3.modPow(bigInteger, bigInteger2).toByteArray();
        dataOutputStream.writeInt(byteArray.length);
        dataOutputStream.write(byteArray);
    }

    private static byte[] b(byte[] bArr, int i) {
        if (bArr.length > i - 1) {
            throw new Throwable("Message too large");
        }
        byte[] bArr2 = new byte[i];
        bArr2[0] = 1;
        int length = bArr.length;
        bArr2[1] = (byte) (length >> 24);
        bArr2[2] = (byte) (length >> 16);
        bArr2[3] = (byte) (length >> 8);
        bArr2[4] = (byte) length;
        System.arraycopy(bArr, 0, bArr2, i - length, length);
        return bArr2;
    }

    private static byte[] c(byte[] bArr) {
        return b(bArr, e, new BigInteger(c, 16), new BigInteger(d, 16));
    }

    private static byte[] b(byte[] bArr, int i, BigInteger bigInteger, BigInteger bigInteger2) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (dataInputStream.available() > 0) {
            a(bigInteger, bigInteger2, dataInputStream.readInt(), dataInputStream, byteArrayOutputStream);
        }
        dataInputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private static void a(BigInteger bigInteger, BigInteger bigInteger2, int i, DataInputStream dataInputStream, ByteArrayOutputStream byteArrayOutputStream) {
        byte[] bArr = new byte[i];
        dataInputStream.readFully(bArr);
        byteArrayOutputStream.write(d(new BigInteger(bArr).modPow(bigInteger, bigInteger2).toByteArray()));
    }

    private static byte[] d(byte[] bArr) {
        if (bArr[0] != 1) {
            throw new Throwable("Not RSA Block");
        }
        int i = ((bArr[1] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) + ((bArr[2] & UnsignedBytes.MAX_VALUE) << 16) + ((bArr[3] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[4] & UnsignedBytes.MAX_VALUE);
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, bArr.length - i, bArr2, 0, i);
        return bArr2;
    }
}
