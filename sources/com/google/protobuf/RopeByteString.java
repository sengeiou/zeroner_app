package com.google.protobuf;

import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

final class RopeByteString extends ByteString {
    /* access modifiers changed from: private */
    public static final int[] minLengthByDepth;
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final ByteString left;
    private final int leftLength;
    /* access modifiers changed from: private */
    public final ByteString right;
    private final int totalLength;
    private final int treeDepth;

    private static class Balancer {
        private final Stack<ByteString> prefixesStack;

        private Balancer() {
            this.prefixesStack = new Stack<>();
        }

        /* access modifiers changed from: private */
        public ByteString balance(ByteString left, ByteString right) {
            doBalance(left);
            doBalance(right);
            ByteString partialString = (ByteString) this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                partialString = new RopeByteString((ByteString) this.prefixesStack.pop(), partialString);
            }
            return partialString;
        }

        private void doBalance(ByteString root) {
            if (root.isBalanced()) {
                insert(root);
            } else if (root instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString) root;
                doBalance(rbs.left);
                doBalance(rbs.right);
            } else {
                throw new IllegalArgumentException("Has a new type of ByteString been created? Found " + root.getClass());
            }
        }

        private void insert(ByteString byteString) {
            ByteString newTree;
            int depthBin = getDepthBinForLength(byteString.size());
            int binEnd = RopeByteString.minLengthByDepth[depthBin + 1];
            if (this.prefixesStack.isEmpty() || ((ByteString) this.prefixesStack.peek()).size() >= binEnd) {
                this.prefixesStack.push(byteString);
                return;
            }
            int binStart = RopeByteString.minLengthByDepth[depthBin];
            ByteString newTree2 = (ByteString) this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty() && ((ByteString) this.prefixesStack.peek()).size() < binStart) {
                newTree2 = new RopeByteString((ByteString) this.prefixesStack.pop(), newTree2);
            }
            ByteString newTree3 = new RopeByteString(newTree2, byteString);
            while (true) {
                newTree = newTree3;
                if (this.prefixesStack.isEmpty()) {
                    break;
                }
                if (((ByteString) this.prefixesStack.peek()).size() >= RopeByteString.minLengthByDepth[getDepthBinForLength(newTree.size()) + 1]) {
                    break;
                }
                newTree3 = new RopeByteString((ByteString) this.prefixesStack.pop(), newTree);
            }
            this.prefixesStack.push(newTree);
        }

        private int getDepthBinForLength(int length) {
            int depth = Arrays.binarySearch(RopeByteString.minLengthByDepth, length);
            if (depth < 0) {
                return (-(depth + 1)) - 1;
            }
            return depth;
        }
    }

    private static class PieceIterator implements Iterator<LeafByteString> {
        private final Stack<RopeByteString> breadCrumbs;
        private LeafByteString next;

        private PieceIterator(ByteString root) {
            this.breadCrumbs = new Stack<>();
            this.next = getLeafByLeft(root);
        }

        private LeafByteString getLeafByLeft(ByteString root) {
            ByteString pos = root;
            while (pos instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString) pos;
                this.breadCrumbs.push(rbs);
                pos = rbs.left;
            }
            return (LeafByteString) pos;
        }

        private LeafByteString getNextNonEmptyLeaf() {
            while (!this.breadCrumbs.isEmpty()) {
                LeafByteString result = getLeafByLeft(((RopeByteString) this.breadCrumbs.pop()).right);
                if (!result.isEmpty()) {
                    return result;
                }
            }
            return null;
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public LeafByteString next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            LeafByteString result = this.next;
            this.next = getNextNonEmptyLeaf();
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class RopeInputStream extends InputStream {
        private LeafByteString currentPiece;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int currentPieceSize;
        private int mark;
        private PieceIterator pieceIterator;

        public RopeInputStream() {
            initialize();
        }

        public int read(byte[] b, int offset, int length) {
            if (b == null) {
                throw new NullPointerException();
            } else if (offset >= 0 && length >= 0 && length <= b.length - offset) {
                return readSkipInternal(b, offset, length);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public long skip(long length) {
            if (length < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (length > 2147483647L) {
                length = 2147483647L;
            }
            return (long) readSkipInternal(null, 0, (int) length);
        }

        private int readSkipInternal(byte[] b, int offset, int length) {
            int bytesRemaining = length;
            while (true) {
                if (bytesRemaining <= 0) {
                    break;
                }
                advanceIfCurrentPieceFullyRead();
                if (this.currentPiece != null) {
                    int count = Math.min(this.currentPieceSize - this.currentPieceIndex, bytesRemaining);
                    if (b != null) {
                        this.currentPiece.copyTo(b, this.currentPieceIndex, offset, count);
                        offset += count;
                    }
                    this.currentPieceIndex += count;
                    bytesRemaining -= count;
                } else if (bytesRemaining == length) {
                    return -1;
                }
            }
            return length - bytesRemaining;
        }

        public int read() throws IOException {
            advanceIfCurrentPieceFullyRead();
            if (this.currentPiece == null) {
                return -1;
            }
            LeafByteString leafByteString = this.currentPiece;
            int i = this.currentPieceIndex;
            this.currentPieceIndex = i + 1;
            return leafByteString.byteAt(i) & UnsignedBytes.MAX_VALUE;
        }

        public int available() throws IOException {
            return RopeByteString.this.size() - (this.currentPieceOffsetInRope + this.currentPieceIndex);
        }

        public boolean markSupported() {
            return true;
        }

        public void mark(int readAheadLimit) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }

        public synchronized void reset() {
            initialize();
            readSkipInternal(null, 0, this.mark);
        }

        private void initialize() {
            this.pieceIterator = new PieceIterator(RopeByteString.this);
            this.currentPiece = this.pieceIterator.next();
            this.currentPieceSize = this.currentPiece.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }

        private void advanceIfCurrentPieceFullyRead() {
            if (this.currentPiece != null && this.currentPieceIndex == this.currentPieceSize) {
                this.currentPieceOffsetInRope += this.currentPieceSize;
                this.currentPieceIndex = 0;
                if (this.pieceIterator.hasNext()) {
                    this.currentPiece = this.pieceIterator.next();
                    this.currentPieceSize = this.currentPiece.size();
                    return;
                }
                this.currentPiece = null;
                this.currentPieceSize = 0;
            }
        }
    }

    static {
        List<Integer> numbers = new ArrayList<>();
        int f1 = 1;
        int f2 = 1;
        while (f2 > 0) {
            numbers.add(Integer.valueOf(f2));
            int temp = f1 + f2;
            f1 = f2;
            f2 = temp;
        }
        numbers.add(Integer.valueOf(Integer.MAX_VALUE));
        minLengthByDepth = new int[numbers.size()];
        for (int i = 0; i < minLengthByDepth.length; i++) {
            minLengthByDepth[i] = ((Integer) numbers.get(i)).intValue();
        }
    }

    private RopeByteString(ByteString left2, ByteString right2) {
        this.left = left2;
        this.right = right2;
        this.leftLength = left2.size();
        this.totalLength = this.leftLength + right2.size();
        this.treeDepth = Math.max(left2.getTreeDepth(), right2.getTreeDepth()) + 1;
    }

    static ByteString concatenate(ByteString left2, ByteString right2) {
        if (right2.size() == 0) {
            return left2;
        }
        if (left2.size() == 0) {
            return right2;
        }
        int newLength = left2.size() + right2.size();
        if (newLength < 128) {
            return concatenateBytes(left2, right2);
        }
        if (left2 instanceof RopeByteString) {
            RopeByteString leftRope = (RopeByteString) left2;
            if (leftRope.right.size() + right2.size() < 128) {
                return new RopeByteString(leftRope.left, concatenateBytes(leftRope.right, right2));
            } else if (leftRope.left.getTreeDepth() > leftRope.right.getTreeDepth() && leftRope.getTreeDepth() > right2.getTreeDepth()) {
                return new RopeByteString(leftRope.left, new RopeByteString(leftRope.right, right2));
            }
        }
        if (newLength >= minLengthByDepth[Math.max(left2.getTreeDepth(), right2.getTreeDepth()) + 1]) {
            return new RopeByteString(left2, right2);
        }
        return new Balancer().balance(left2, right2);
    }

    private static ByteString concatenateBytes(ByteString left2, ByteString right2) {
        int leftSize = left2.size();
        int rightSize = right2.size();
        byte[] bytes = new byte[(leftSize + rightSize)];
        left2.copyTo(bytes, 0, 0, leftSize);
        right2.copyTo(bytes, 0, leftSize, rightSize);
        return ByteString.wrap(bytes);
    }

    static RopeByteString newInstanceForTest(ByteString left2, ByteString right2) {
        return new RopeByteString(left2, right2);
    }

    public byte byteAt(int index) {
        checkIndex(index, this.totalLength);
        if (index < this.leftLength) {
            return this.left.byteAt(index);
        }
        return this.right.byteAt(index - this.leftLength);
    }

    public int size() {
        return this.totalLength;
    }

    /* access modifiers changed from: protected */
    public int getTreeDepth() {
        return this.treeDepth;
    }

    /* access modifiers changed from: protected */
    public boolean isBalanced() {
        return this.totalLength >= minLengthByDepth[this.treeDepth];
    }

    /* Debug info: failed to restart local var, previous not found, register: 6 */
    public ByteString substring(int beginIndex, int endIndex) {
        int length = checkRange(beginIndex, endIndex, this.totalLength);
        if (length == 0) {
            return ByteString.EMPTY;
        }
        if (length == this.totalLength) {
            return this;
        }
        if (endIndex <= this.leftLength) {
            return this.left.substring(beginIndex, endIndex);
        }
        if (beginIndex >= this.leftLength) {
            return this.right.substring(beginIndex - this.leftLength, endIndex - this.leftLength);
        }
        return new RopeByteString(this.left.substring(beginIndex), this.right.substring(0, endIndex - this.leftLength));
    }

    /* access modifiers changed from: protected */
    public void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        if (sourceOffset + numberToCopy <= this.leftLength) {
            this.left.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
        } else if (sourceOffset >= this.leftLength) {
            this.right.copyToInternal(target, sourceOffset - this.leftLength, targetOffset, numberToCopy);
        } else {
            int leftLength2 = this.leftLength - sourceOffset;
            this.left.copyToInternal(target, sourceOffset, targetOffset, leftLength2);
            this.right.copyToInternal(target, 0, targetOffset + leftLength2, numberToCopy - leftLength2);
        }
    }

    public void copyTo(ByteBuffer target) {
        this.left.copyTo(target);
        this.right.copyTo(target);
    }

    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    public List<ByteBuffer> asReadOnlyByteBufferList() {
        List<ByteBuffer> result = new ArrayList<>();
        PieceIterator pieces = new PieceIterator(this);
        while (pieces.hasNext()) {
            result.add(pieces.next().asReadOnlyByteBuffer());
        }
        return result;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }

    /* access modifiers changed from: 0000 */
    public void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
        if (sourceOffset + numberToWrite <= this.leftLength) {
            this.left.writeToInternal(out, sourceOffset, numberToWrite);
        } else if (sourceOffset >= this.leftLength) {
            this.right.writeToInternal(out, sourceOffset - this.leftLength, numberToWrite);
        } else {
            int numberToWriteInLeft = this.leftLength - sourceOffset;
            this.left.writeToInternal(out, sourceOffset, numberToWriteInLeft);
            this.right.writeToInternal(out, 0, numberToWrite - numberToWriteInLeft);
        }
    }

    /* access modifiers changed from: 0000 */
    public void writeTo(ByteOutput output) throws IOException {
        this.left.writeTo(output);
        this.right.writeTo(output);
    }

    /* access modifiers changed from: protected */
    public String toStringInternal(Charset charset) {
        return new String(toByteArray(), charset);
    }

    public boolean isValidUtf8() {
        if (this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(0, 0, this.leftLength), 0, this.right.size()) == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int partialIsValidUtf8(int state, int offset, int length) {
        if (offset + length <= this.leftLength) {
            return this.left.partialIsValidUtf8(state, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialIsValidUtf8(state, offset - this.leftLength, length);
        }
        int leftLength2 = this.leftLength - offset;
        return this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(state, offset, leftLength2), 0, length - leftLength2);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        ByteString otherByteString = (ByteString) other;
        if (this.totalLength != otherByteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        int thisHash = peekCachedHashCode();
        int thatHash = otherByteString.peekCachedHashCode();
        if (thisHash == 0 || thatHash == 0 || thisHash == thatHash) {
            return equalsFragments(otherByteString);
        }
        return false;
    }

    private boolean equalsFragments(ByteString other) {
        boolean stillEqual;
        int thisOffset = 0;
        Iterator<LeafByteString> thisIter = new PieceIterator<>(this);
        LeafByteString thisString = (LeafByteString) thisIter.next();
        int thatOffset = 0;
        Iterator<LeafByteString> thatIter = new PieceIterator<>(other);
        LeafByteString thatString = (LeafByteString) thatIter.next();
        int pos = 0;
        while (true) {
            int thisRemaining = thisString.size() - thisOffset;
            int thatRemaining = thatString.size() - thatOffset;
            int bytesToCompare = Math.min(thisRemaining, thatRemaining);
            if (thisOffset == 0) {
                stillEqual = thisString.equalsRange(thatString, thatOffset, bytesToCompare);
            } else {
                stillEqual = thatString.equalsRange(thisString, thisOffset, bytesToCompare);
            }
            if (!stillEqual) {
                return false;
            }
            pos += bytesToCompare;
            if (pos < this.totalLength) {
                if (bytesToCompare == thisRemaining) {
                    thisOffset = 0;
                    thisString = (LeafByteString) thisIter.next();
                } else {
                    thisOffset += bytesToCompare;
                }
                if (bytesToCompare == thatRemaining) {
                    thatOffset = 0;
                    thatString = (LeafByteString) thatIter.next();
                } else {
                    thatOffset += bytesToCompare;
                }
            } else if (pos == this.totalLength) {
                return true;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /* access modifiers changed from: protected */
    public int partialHash(int h, int offset, int length) {
        if (offset + length <= this.leftLength) {
            return this.left.partialHash(h, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialHash(h, offset - this.leftLength, length);
        }
        int leftLength2 = this.leftLength - offset;
        return this.right.partialHash(this.left.partialHash(h, offset, leftLength2), 0, length - leftLength2);
    }

    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance((InputStream) new RopeInputStream());
    }

    public InputStream newInput() {
        return new RopeInputStream();
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return ByteString.wrap(toByteArray());
    }

    private void readObject(ObjectInputStream in) throws IOException {
        throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }
}
