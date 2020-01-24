package no.nordicsemi.android.dfu.internal.exception;

public class UnknownResponseException extends Exception {
    private static final char[] HEX_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final long serialVersionUID = -8716125467309979289L;
    private final int mExpectedOpCode;
    private final int mExpectedReturnCode;
    private final byte[] mResponse;

    public UnknownResponseException(String message, byte[] response, int expectedReturnCode, int expectedOpCode) {
        super(message);
        if (response == null) {
            response = new byte[0];
        }
        this.mResponse = response;
        this.mExpectedReturnCode = expectedReturnCode;
        this.mExpectedOpCode = expectedOpCode;
    }

    public String getMessage() {
        return String.format("%s (response: %s, expected: 0x%02X%02X..)", new Object[]{super.getMessage(), bytesToHex(this.mResponse, 0, this.mResponse.length), Integer.valueOf(this.mExpectedReturnCode), Integer.valueOf(this.mExpectedOpCode)});
    }

    public static String bytesToHex(byte[] bytes, int start, int length) {
        if (bytes == null || bytes.length <= start || length <= 0) {
            return "";
        }
        int maxLength = Math.min(length, bytes.length - start);
        char[] hexChars = new char[(maxLength * 2)];
        for (int j = 0; j < maxLength; j++) {
            int v = bytes[start + j] & 255;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[(j * 2) + 1] = HEX_ARRAY[v & 15];
        }
        return "0x" + new String(hexChars);
    }
}
