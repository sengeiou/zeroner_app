package okhttp3.internal.http2;

import android.support.v4.view.PointerIconCompat;
import com.google.common.base.Ascii;
import com.tencent.bugly.beta.tinker.TinkerReport;
import java.io.ByteArrayOutputStream;
import okio.ByteString;

class Huffman {
    private static final int[] CODES = {8184, 8388568, 268435426, 268435427, 268435428, 268435429, 268435430, 268435431, 268435432, 16777194, 1073741820, 268435433, 268435434, 1073741821, 268435435, 268435436, 268435437, 268435438, 268435439, 268435440, 268435441, 268435442, 1073741822, 268435443, 268435444, 268435445, 268435446, 268435447, 268435448, 268435449, 268435450, 268435451, 20, PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, 4090, 8185, 21, 248, 2042, PointerIconCompat.TYPE_ZOOM_IN, PointerIconCompat.TYPE_ZOOM_OUT, 249, 2043, 250, 22, 23, 24, 0, 1, 2, 25, 26, 27, 28, 29, 30, 31, 92, TinkerReport.KEY_LOADED_UNCAUGHT_EXCEPTION, 32764, 32, 4091, PointerIconCompat.TYPE_GRAB, 8186, 33, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, TinkerReport.KEY_LOADED_EXCEPTION_DEX, 115, TinkerReport.KEY_LOADED_EXCEPTION_DEX_CHECK, 8187, 524272, 8188, 16380, 34, 32765, 3, 35, 4, 36, 5, 37, 38, 39, 6, 116, 117, 40, 41, 42, 7, 43, 118, 44, 8, 9, 45, 119, 120, TinkerReport.KEY_APPLIED_DEXOPT_OTHER, 122, 123, 32766, 2044, 16381, 8189, 268435452, 1048550, 4194258, 1048551, 1048552, 4194259, 4194260, 4194261, 8388569, 4194262, 8388570, 8388571, 8388572, 8388573, 8388574, 16777195, 8388575, 16777196, 16777197, 4194263, 8388576, 16777198, 8388577, 8388578, 8388579, 8388580, 2097116, 4194264, 8388581, 4194265, 8388582, 8388583, 16777199, 4194266, 2097117, 1048553, 4194267, 4194268, 8388584, 8388585, 2097118, 8388586, 4194269, 4194270, 16777200, 2097119, 4194271, 8388587, 8388588, 2097120, 2097121, 4194272, 2097122, 8388589, 4194273, 8388590, 8388591, 1048554, 4194274, 4194275, 4194276, 8388592, 4194277, 4194278, 8388593, 67108832, 67108833, 1048555, 524273, 4194279, 8388594, 4194280, 33554412, 67108834, 67108835, 67108836, 134217694, 134217695, 67108837, 16777201, 33554413, 524274, 2097123, 67108838, 134217696, 134217697, 67108839, 134217698, 16777202, 2097124, 2097125, 67108840, 67108841, 268435453, 134217699, 134217700, 134217701, 1048556, 16777203, 1048557, 2097126, 4194281, 2097127, 2097128, 8388595, 4194282, 4194283, 33554414, 33554415, 16777204, 16777205, 67108842, 8388596, 67108843, 134217702, 67108844, 67108845, 134217703, 134217704, 134217705, 134217706, 134217707, 268435454, 134217708, 134217709, 134217710, 134217711, 134217712, 67108846};
    private static final byte[] CODE_LENGTHS = {Ascii.CR, Ascii.ETB, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.CAN, Ascii.RS, Ascii.FS, Ascii.FS, Ascii.RS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.RS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, Ascii.FS, 6, 10, 10, Ascii.FF, Ascii.CR, 6, 8, Ascii.VT, 10, 10, 8, Ascii.VT, 8, 6, 6, 6, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, Ascii.SI, 6, Ascii.FF, 10, Ascii.CR, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 7, 8, Ascii.CR, 19, Ascii.CR, Ascii.SO, 6, Ascii.SI, 5, 6, 5, 6, 5, 6, 6, 6, 5, 7, 7, 6, 6, 6, 5, 6, 7, 6, 5, 5, 6, 7, 7, 7, 7, 7, Ascii.SI, Ascii.VT, Ascii.SO, Ascii.CR, Ascii.FS, Ascii.DC4, Ascii.SYN, Ascii.DC4, Ascii.DC4, Ascii.SYN, Ascii.SYN, Ascii.SYN, Ascii.ETB, Ascii.SYN, Ascii.ETB, Ascii.ETB, Ascii.ETB, Ascii.ETB, Ascii.ETB, Ascii.CAN, Ascii.ETB, Ascii.CAN, Ascii.CAN, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.ETB, Ascii.ETB, Ascii.ETB, Ascii.ETB, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.SYN, Ascii.ETB, Ascii.ETB, Ascii.CAN, Ascii.SYN, Ascii.NAK, Ascii.DC4, Ascii.SYN, Ascii.SYN, Ascii.ETB, Ascii.ETB, Ascii.NAK, Ascii.ETB, Ascii.SYN, Ascii.SYN, Ascii.CAN, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.ETB, Ascii.NAK, Ascii.NAK, Ascii.SYN, Ascii.NAK, Ascii.ETB, Ascii.SYN, Ascii.ETB, Ascii.ETB, Ascii.DC4, Ascii.SYN, Ascii.SYN, Ascii.SYN, Ascii.ETB, Ascii.SYN, Ascii.SYN, Ascii.ETB, Ascii.SUB, Ascii.SUB, Ascii.DC4, 19, Ascii.SYN, Ascii.ETB, Ascii.SYN, Ascii.EM, Ascii.SUB, Ascii.SUB, Ascii.SUB, Ascii.ESC, Ascii.ESC, Ascii.SUB, Ascii.CAN, Ascii.EM, 19, Ascii.NAK, Ascii.SUB, Ascii.ESC, Ascii.ESC, Ascii.SUB, Ascii.ESC, Ascii.CAN, Ascii.NAK, Ascii.NAK, Ascii.SUB, Ascii.SUB, Ascii.FS, Ascii.ESC, Ascii.ESC, Ascii.ESC, Ascii.DC4, Ascii.CAN, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.NAK, Ascii.NAK, Ascii.ETB, Ascii.SYN, Ascii.SYN, Ascii.EM, Ascii.EM, Ascii.CAN, Ascii.CAN, Ascii.SUB, Ascii.ETB, Ascii.SUB, Ascii.ESC, Ascii.SUB, Ascii.SUB, Ascii.ESC, Ascii.ESC, Ascii.ESC, Ascii.ESC, Ascii.ESC, Ascii.FS, Ascii.ESC, Ascii.ESC, Ascii.ESC, Ascii.ESC, Ascii.ESC, Ascii.SUB};
    private static final Huffman INSTANCE = new Huffman();
    private final Node root = new Node();

    private static final class Node {
        final Node[] children;
        final int symbol;
        final int terminalBits;

        Node() {
            this.children = new Node[256];
            this.symbol = 0;
            this.terminalBits = 0;
        }

        Node(int symbol2, int bits) {
            this.children = null;
            this.symbol = symbol2;
            int b = bits & 7;
            if (b == 0) {
                b = 8;
            }
            this.terminalBits = b;
        }
    }

    public static Huffman get() {
        return INSTANCE;
    }

    private Huffman() {
        buildTree();
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(okio.ByteString r11, okio.BufferedSink r12) throws java.io.IOException {
        /*
            r10 = this;
            r2 = 0
            r5 = 0
            r4 = 0
        L_0x0004:
            int r7 = r11.size()
            if (r4 >= r7) goto L_0x002c
            byte r7 = r11.getByte(r4)
            r0 = r7 & 255(0xff, float:3.57E-43)
            int[] r7 = CODES
            r1 = r7[r0]
            byte[] r7 = CODE_LENGTHS
            byte r6 = r7[r0]
            long r2 = r2 << r6
            long r8 = (long) r1
            long r2 = r2 | r8
            int r5 = r5 + r6
        L_0x001c:
            r7 = 8
            if (r5 < r7) goto L_0x0029
            int r5 = r5 + -8
            long r8 = r2 >> r5
            int r7 = (int) r8
            r12.writeByte(r7)
            goto L_0x001c
        L_0x0029:
            int r4 = r4 + 1
            goto L_0x0004
        L_0x002c:
            if (r5 <= 0) goto L_0x003a
            int r7 = 8 - r5
            long r2 = r2 << r7
            r7 = 255(0xff, float:3.57E-43)
            int r7 = r7 >>> r5
            long r8 = (long) r7
            long r2 = r2 | r8
            int r7 = (int) r2
            r12.writeByte(r7)
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Huffman.encode(okio.ByteString, okio.BufferedSink):void");
    }

    /* access modifiers changed from: 0000 */
    public int encodedLength(ByteString bytes) {
        long len = 0;
        for (int i = 0; i < bytes.size(); i++) {
            len += (long) CODE_LENGTHS[bytes.getByte(i) & 255];
        }
        return (int) ((7 + len) >> 3);
    }

    /* access modifiers changed from: 0000 */
    public byte[] decode(byte[] buf) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Node node = this.root;
        int current = 0;
        int nbits = 0;
        for (byte b : buf) {
            current = (current << 8) | (b & 255);
            nbits += 8;
            while (nbits >= 8) {
                node = node.children[(current >>> (nbits - 8)) & 255];
                if (node.children == null) {
                    baos.write(node.symbol);
                    nbits -= node.terminalBits;
                    node = this.root;
                } else {
                    nbits -= 8;
                }
            }
        }
        while (nbits > 0) {
            Node node2 = node.children[(current << (8 - nbits)) & 255];
            if (node2.children != null || node2.terminalBits > nbits) {
                break;
            }
            baos.write(node2.symbol);
            nbits -= node2.terminalBits;
            node = this.root;
        }
        return baos.toByteArray();
    }

    private void buildTree() {
        for (int i = 0; i < CODE_LENGTHS.length; i++) {
            addCode(i, CODES[i], CODE_LENGTHS[i]);
        }
    }

    private void addCode(int sym, int code, byte len) {
        Node terminal = new Node(sym, len);
        Node current = this.root;
        while (len > 8) {
            len = (byte) (len - 8);
            int i = (code >>> len) & 255;
            if (current.children == null) {
                throw new IllegalStateException("invalid dictionary: prefix not unique");
            }
            if (current.children[i] == null) {
                current.children[i] = new Node();
            }
            current = current.children[i];
        }
        int shift = 8 - len;
        int start = (code << shift) & 255;
        int end = 1 << shift;
        for (int i2 = start; i2 < start + end; i2++) {
            current.children[i2] = terminal;
        }
    }
}
