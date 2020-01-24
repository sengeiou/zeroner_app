package com.google.protobuf;

import com.alibaba.android.arouter.utils.Consts;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.common.base.Ascii;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.ExtensionRegistry.ExtensionInfo;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.UnknownFieldSet.Field;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.internal.LongCompanionObject;
import org.apache.commons.cli.HelpFormatter;

public final class TextFormat {
    private static final Parser PARSER = Parser.newBuilder().build();
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(TextFormat.class.getName());

    public static class InvalidEscapeSequenceException extends IOException {
        private static final long serialVersionUID = -8164033650142593304L;

        InvalidEscapeSequenceException(String description) {
            super(description);
        }
    }

    public static class ParseException extends IOException {
        private static final long serialVersionUID = 3196188060225107702L;
        private final int column;
        private final int line;

        public ParseException(String message) {
            this(-1, -1, message);
        }

        public ParseException(int line2, int column2, String message) {
            super(Integer.toString(line2) + ":" + column2 + ": " + message);
            this.line = line2;
            this.column = column2;
        }

        public int getLine() {
            return this.line;
        }

        public int getColumn() {
            return this.column;
        }
    }

    public static class Parser {
        private static final int BUFFER_SIZE = 4096;
        private final boolean allowUnknownEnumValues;
        private final boolean allowUnknownFields;
        private com.google.protobuf.TextFormatParseInfoTree.Builder parseInfoTreeBuilder;
        private final SingularOverwritePolicy singularOverwritePolicy;

        public static class Builder {
            private boolean allowUnknownEnumValues = false;
            private boolean allowUnknownFields = false;
            private com.google.protobuf.TextFormatParseInfoTree.Builder parseInfoTreeBuilder = null;
            private SingularOverwritePolicy singularOverwritePolicy = SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES;

            public Builder setSingularOverwritePolicy(SingularOverwritePolicy p) {
                this.singularOverwritePolicy = p;
                return this;
            }

            public Builder setParseInfoTreeBuilder(com.google.protobuf.TextFormatParseInfoTree.Builder parseInfoTreeBuilder2) {
                this.parseInfoTreeBuilder = parseInfoTreeBuilder2;
                return this;
            }

            public Parser build() {
                return new Parser(this.allowUnknownFields, this.allowUnknownEnumValues, this.singularOverwritePolicy, this.parseInfoTreeBuilder);
            }
        }

        public enum SingularOverwritePolicy {
            ALLOW_SINGULAR_OVERWRITES,
            FORBID_SINGULAR_OVERWRITES
        }

        private Parser(boolean allowUnknownFields2, boolean allowUnknownEnumValues2, SingularOverwritePolicy singularOverwritePolicy2, com.google.protobuf.TextFormatParseInfoTree.Builder parseInfoTreeBuilder2) {
            this.allowUnknownFields = allowUnknownFields2;
            this.allowUnknownEnumValues = allowUnknownEnumValues2;
            this.singularOverwritePolicy = singularOverwritePolicy2;
            this.parseInfoTreeBuilder = parseInfoTreeBuilder2;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public void merge(Readable input, com.google.protobuf.Message.Builder builder) throws IOException {
            merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(CharSequence input, com.google.protobuf.Message.Builder builder) throws ParseException {
            merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(Readable input, ExtensionRegistry extensionRegistry, com.google.protobuf.Message.Builder builder) throws IOException {
            merge((CharSequence) toStringBuilder(input), extensionRegistry, builder);
        }

        private static StringBuilder toStringBuilder(Readable input) throws IOException {
            StringBuilder text = new StringBuilder();
            CharBuffer buffer = CharBuffer.allocate(4096);
            while (true) {
                int n = input.read(buffer);
                if (n == -1) {
                    return text;
                }
                buffer.flip();
                text.append(buffer, 0, n);
            }
        }

        private void checkUnknownFields(List<String> unknownFields) throws ParseException {
            if (!unknownFields.isEmpty()) {
                StringBuilder msg = new StringBuilder("Input contains unknown fields and/or extensions:");
                for (String field : unknownFields) {
                    msg.append(10).append(field);
                }
                if (this.allowUnknownFields) {
                    TextFormat.logger.warning(msg.toString());
                } else {
                    String[] lineColumn = ((String) unknownFields.get(0)).split(":");
                    throw new ParseException(Integer.valueOf(lineColumn[0]).intValue(), Integer.valueOf(lineColumn[1]).intValue(), msg.toString());
                }
            }
        }

        public void merge(CharSequence input, ExtensionRegistry extensionRegistry, com.google.protobuf.Message.Builder builder) throws ParseException {
            Tokenizer tokenizer = new Tokenizer(input);
            BuilderAdapter target = new BuilderAdapter(builder);
            List<String> unknownFields = new ArrayList<>();
            while (!tokenizer.atEnd()) {
                mergeField(tokenizer, extensionRegistry, target, unknownFields);
            }
            checkUnknownFields(unknownFields);
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MergeTarget target, List<String> unknownFields) throws ParseException {
            mergeField(tokenizer, extensionRegistry, target, this.parseInfoTreeBuilder, unknownFields);
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MergeTarget target, com.google.protobuf.TextFormatParseInfoTree.Builder parseTreeBuilder, List<String> unknownFields) throws ParseException {
            FieldDescriptor field;
            FieldDescriptor field2 = null;
            int startLine = tokenizer.getLine();
            int startColumn = tokenizer.getColumn();
            Descriptor type = target.getDescriptorForType();
            ExtensionInfo extension = null;
            if (tokenizer.tryConsume("[")) {
                StringBuilder sb = new StringBuilder(tokenizer.consumeIdentifier());
                while (true) {
                    if (!tokenizer.tryConsume(Consts.DOT)) {
                        break;
                    }
                    sb.append('.');
                    sb.append(tokenizer.consumeIdentifier());
                }
                extension = target.findExtensionByName(extensionRegistry, sb.toString());
                if (extension == null) {
                    unknownFields.add((tokenizer.getPreviousLine() + 1) + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + ".[" + sb + "]");
                } else if (extension.descriptor.getContainingType() != type) {
                    throw tokenizer.parseExceptionPreviousToken("Extension \"" + sb + "\" does not extend message type \"" + type.getFullName() + "\".");
                } else {
                    field2 = extension.descriptor;
                }
                tokenizer.consume("]");
            } else {
                String name = tokenizer.consumeIdentifier();
                field = type.findFieldByName(name);
                if (field == null) {
                    field = type.findFieldByName(name.toLowerCase(Locale.US));
                    if (!(field == null || field.getType() == Type.GROUP)) {
                        field = null;
                    }
                }
                if (field != null && field.getType() == Type.GROUP && !field.getMessageType().getName().equals(name)) {
                    field = null;
                }
                if (field == null) {
                    unknownFields.add((tokenizer.getPreviousLine() + 1) + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + Consts.DOT + name);
                }
            }
            if (field == null) {
                if (tokenizer.tryConsume(":")) {
                    if (!tokenizer.lookingAt("{")) {
                        if (!tokenizer.lookingAt("<")) {
                            skipFieldValue(tokenizer);
                            return;
                        }
                    }
                }
                skipFieldMessage(tokenizer);
                return;
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                tokenizer.tryConsume(":");
                if (parseTreeBuilder != null) {
                    consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder.getBuilderForSubMessageField(field), unknownFields);
                } else {
                    consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
                }
            } else {
                tokenizer.consume(":");
                consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
            }
            if (parseTreeBuilder != null) {
                parseTreeBuilder.setLocation(field, TextFormatParseLocation.create(startLine, startColumn));
            }
            if (!tokenizer.tryConsume(";")) {
                tokenizer.tryConsume(",");
            }
        }

        private void consumeFieldValues(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MergeTarget target, FieldDescriptor field, ExtensionInfo extension, com.google.protobuf.TextFormatParseInfoTree.Builder parseTreeBuilder, List<String> unknownFields) throws ParseException {
            if (!field.isRepeated() || !tokenizer.tryConsume("[")) {
                consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
            } else if (!tokenizer.tryConsume("]")) {
                while (true) {
                    consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
                    if (!tokenizer.tryConsume("]")) {
                        tokenizer.consume(",");
                    } else {
                        return;
                    }
                }
            }
        }

        private void consumeFieldValue(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MergeTarget target, FieldDescriptor field, ExtensionInfo extension, com.google.protobuf.TextFormatParseInfoTree.Builder parseTreeBuilder, List<String> unknownFields) throws ParseException {
            String endToken;
            Object value = null;
            if (field.getJavaType() == JavaType.MESSAGE) {
                if (tokenizer.tryConsume("<")) {
                    endToken = ">";
                } else {
                    tokenizer.consume("{");
                    endToken = "}";
                }
                MergeTarget subField = target.newMergeTargetForField(field, extension == null ? null : extension.defaultInstance);
                while (!tokenizer.tryConsume(endToken)) {
                    if (tokenizer.atEnd()) {
                        throw tokenizer.parseException("Expected \"" + endToken + "\".");
                    }
                    mergeField(tokenizer, extensionRegistry, subField, parseTreeBuilder, unknownFields);
                }
                value = subField.finish();
            } else {
                switch (field.getType()) {
                    case INT32:
                    case SINT32:
                    case SFIXED32:
                        value = Integer.valueOf(tokenizer.consumeInt32());
                        break;
                    case INT64:
                    case SINT64:
                    case SFIXED64:
                        value = Long.valueOf(tokenizer.consumeInt64());
                        break;
                    case BOOL:
                        value = Boolean.valueOf(tokenizer.consumeBoolean());
                        break;
                    case FLOAT:
                        value = Float.valueOf(tokenizer.consumeFloat());
                        break;
                    case DOUBLE:
                        value = Double.valueOf(tokenizer.consumeDouble());
                        break;
                    case UINT32:
                    case FIXED32:
                        value = Integer.valueOf(tokenizer.consumeUInt32());
                        break;
                    case UINT64:
                    case FIXED64:
                        value = Long.valueOf(tokenizer.consumeUInt64());
                        break;
                    case STRING:
                        value = tokenizer.consumeString();
                        break;
                    case BYTES:
                        value = tokenizer.consumeByteString();
                        break;
                    case ENUM:
                        EnumDescriptor enumType = field.getEnumType();
                        if (tokenizer.lookingAtInteger()) {
                            int number = tokenizer.consumeInt32();
                            value = enumType.findValueByNumber(number);
                            if (value == null) {
                                String unknownValueMsg = "Enum type \"" + enumType.getFullName() + "\" has no value with number " + number + '.';
                                if (this.allowUnknownEnumValues) {
                                    TextFormat.logger.warning(unknownValueMsg);
                                    return;
                                }
                                throw tokenizer.parseExceptionPreviousToken("Enum type \"" + enumType.getFullName() + "\" has no value with number " + number + '.');
                            }
                        } else {
                            String id = tokenizer.consumeIdentifier();
                            value = enumType.findValueByName(id);
                            if (value == null) {
                                String unknownValueMsg2 = "Enum type \"" + enumType.getFullName() + "\" has no value named \"" + id + "\".";
                                if (this.allowUnknownEnumValues) {
                                    TextFormat.logger.warning(unknownValueMsg2);
                                    return;
                                }
                                throw tokenizer.parseExceptionPreviousToken(unknownValueMsg2);
                            }
                        }
                        break;
                    case MESSAGE:
                    case GROUP:
                        throw new RuntimeException("Can't get here.");
                }
            }
            if (field.isRepeated()) {
                target.addRepeatedField(field, value);
            } else if (this.singularOverwritePolicy != SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES || !target.hasField(field)) {
                if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && field.getContainingOneof() != null) {
                    if (target.hasOneof(field.getContainingOneof())) {
                        OneofDescriptor oneof = field.getContainingOneof();
                        throw tokenizer.parseExceptionPreviousToken("Field \"" + field.getFullName() + "\" is specified along with field \"" + target.getOneofFieldDescriptor(oneof).getFullName() + "\", another member of oneof \"" + oneof.getName() + "\".");
                    }
                }
                target.setField(field, value);
            } else {
                throw tokenizer.parseExceptionPreviousToken("Non-repeated field \"" + field.getFullName() + "\" cannot be overwritten.");
            }
        }

        private void skipField(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsume("[")) {
                do {
                    tokenizer.consumeIdentifier();
                } while (tokenizer.tryConsume(Consts.DOT));
                tokenizer.consume("]");
            } else {
                tokenizer.consumeIdentifier();
            }
            if (!tokenizer.tryConsume(":") || tokenizer.lookingAt("<") || tokenizer.lookingAt("{")) {
                skipFieldMessage(tokenizer);
            } else {
                skipFieldValue(tokenizer);
            }
            if (!tokenizer.tryConsume(";")) {
                tokenizer.tryConsume(",");
            }
        }

        private void skipFieldMessage(Tokenizer tokenizer) throws ParseException {
            String delimiter;
            if (tokenizer.tryConsume("<")) {
                delimiter = ">";
            } else {
                tokenizer.consume("{");
                delimiter = "}";
            }
            while (!tokenizer.lookingAt(">") && !tokenizer.lookingAt("}")) {
                skipField(tokenizer);
            }
            tokenizer.consume(delimiter);
        }

        private void skipFieldValue(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsumeString()) {
                do {
                } while (tokenizer.tryConsumeString());
            } else if (!tokenizer.tryConsumeIdentifier() && !tokenizer.tryConsumeInt64() && !tokenizer.tryConsumeUInt64() && !tokenizer.tryConsumeDouble() && !tokenizer.tryConsumeFloat()) {
                throw tokenizer.parseException("Invalid field value: " + tokenizer.currentToken);
            }
        }
    }

    private static final class Printer {
        static final Printer DEFAULT = new Printer(true);
        static final Printer UNICODE = new Printer(false);
        private final boolean escapeNonAscii;

        private Printer(boolean escapeNonAscii2) {
            this.escapeNonAscii = escapeNonAscii2;
        }

        /* access modifiers changed from: private */
        public void print(MessageOrBuilder message, TextGenerator generator) throws IOException {
            for (Entry<FieldDescriptor, Object> field : message.getAllFields().entrySet()) {
                printField((FieldDescriptor) field.getKey(), field.getValue(), generator);
            }
            printUnknownFields(message.getUnknownFields(), generator);
        }

        /* access modifiers changed from: private */
        public void printField(FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            if (field.isRepeated()) {
                for (Object element : (List) value) {
                    printSingleField(field, element, generator);
                }
                return;
            }
            printSingleField(field, value, generator);
        }

        private void printSingleField(FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            if (field.isExtension()) {
                generator.print("[");
                if (!field.getContainingType().getOptions().getMessageSetWireFormat() || field.getType() != Type.MESSAGE || !field.isOptional() || field.getExtensionScope() != field.getMessageType()) {
                    generator.print(field.getFullName());
                } else {
                    generator.print(field.getMessageType().getFullName());
                }
                generator.print("]");
            } else if (field.getType() == Type.GROUP) {
                generator.print(field.getMessageType().getName());
            } else {
                generator.print(field.getName());
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                generator.print(" {");
                generator.eol();
                generator.indent();
            } else {
                generator.print(": ");
            }
            printFieldValue(field, value, generator);
            if (field.getJavaType() == JavaType.MESSAGE) {
                generator.outdent();
                generator.print("}");
            }
            generator.eol();
        }

        /* access modifiers changed from: private */
        public void printFieldValue(FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            String replace;
            switch (field.getType()) {
                case INT32:
                case SINT32:
                case SFIXED32:
                    generator.print(((Integer) value).toString());
                    return;
                case INT64:
                case SINT64:
                case SFIXED64:
                    generator.print(((Long) value).toString());
                    return;
                case BOOL:
                    generator.print(((Boolean) value).toString());
                    return;
                case FLOAT:
                    generator.print(((Float) value).toString());
                    return;
                case DOUBLE:
                    generator.print(((Double) value).toString());
                    return;
                case UINT32:
                case FIXED32:
                    generator.print(TextFormat.unsignedToString(((Integer) value).intValue()));
                    return;
                case UINT64:
                case FIXED64:
                    generator.print(TextFormat.unsignedToString(((Long) value).longValue()));
                    return;
                case STRING:
                    generator.print("\"");
                    if (this.escapeNonAscii) {
                        replace = TextFormatEscaper.escapeText((String) value);
                    } else {
                        replace = TextFormat.escapeDoubleQuotesAndBackslashes((String) value).replace("\n", "\\n");
                    }
                    generator.print(replace);
                    generator.print("\"");
                    return;
                case BYTES:
                    generator.print("\"");
                    if (value instanceof ByteString) {
                        generator.print(TextFormat.escapeBytes((ByteString) value));
                    } else {
                        generator.print(TextFormat.escapeBytes((byte[]) (byte[]) value));
                    }
                    generator.print("\"");
                    return;
                case ENUM:
                    generator.print(((EnumValueDescriptor) value).getName());
                    return;
                case MESSAGE:
                case GROUP:
                    print((Message) value, generator);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: private */
        public void printUnknownFields(UnknownFieldSet unknownFields, TextGenerator generator) throws IOException {
            for (Entry<Integer, Field> entry : unknownFields.asMap().entrySet()) {
                int number = ((Integer) entry.getKey()).intValue();
                Field field = (Field) entry.getValue();
                printUnknownField(number, 0, field.getVarintList(), generator);
                printUnknownField(number, 5, field.getFixed32List(), generator);
                printUnknownField(number, 1, field.getFixed64List(), generator);
                printUnknownField(number, 2, field.getLengthDelimitedList(), generator);
                for (UnknownFieldSet value : field.getGroupList()) {
                    generator.print(((Integer) entry.getKey()).toString());
                    generator.print(" {");
                    generator.eol();
                    generator.indent();
                    printUnknownFields(value, generator);
                    generator.outdent();
                    generator.print("}");
                    generator.eol();
                }
            }
        }

        private void printUnknownField(int number, int wireType, List<?> values, TextGenerator generator) throws IOException {
            for (Object value : values) {
                generator.print(String.valueOf(number));
                generator.print(": ");
                TextFormat.printUnknownFieldValue(wireType, value, generator);
                generator.eol();
            }
        }
    }

    private static final class TextGenerator {
        private boolean atStartOfLine;
        private final StringBuilder indent;
        private final Appendable output;
        private final boolean singleLineMode;

        private TextGenerator(Appendable output2, boolean singleLineMode2) {
            this.indent = new StringBuilder();
            this.atStartOfLine = false;
            this.output = output2;
            this.singleLineMode = singleLineMode2;
        }

        public void indent() {
            this.indent.append("  ");
        }

        public void outdent() {
            int length = this.indent.length();
            if (length == 0) {
                throw new IllegalArgumentException(" Outdent() without matching Indent().");
            }
            this.indent.setLength(length - 2);
        }

        public void print(CharSequence text) throws IOException {
            if (this.atStartOfLine) {
                this.atStartOfLine = false;
                this.output.append(this.singleLineMode ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : this.indent);
            }
            this.output.append(text);
        }

        public void eol() throws IOException {
            if (!this.singleLineMode) {
                this.output.append("\n");
            }
            this.atStartOfLine = true;
        }
    }

    private static final class Tokenizer {
        private static final Pattern DOUBLE_INFINITY = Pattern.compile("-?inf(inity)?", 2);
        private static final Pattern FLOAT_INFINITY = Pattern.compile("-?inf(inity)?f?", 2);
        private static final Pattern FLOAT_NAN = Pattern.compile("nanf?", 2);
        private static final Pattern TOKEN = Pattern.compile("[a-zA-Z_][0-9a-zA-Z_+-]*+|[.]?[0-9+-][0-9a-zA-Z_.+-]*+|\"([^\"\n\\\\]|\\\\.)*+(\"|\\\\?$)|'([^'\n\\\\]|\\\\.)*+('|\\\\?$)", 8);
        private static final Pattern WHITESPACE = Pattern.compile("(\\s|(#.*$))++", 8);
        private int column;
        /* access modifiers changed from: private */
        public String currentToken;
        private int line;
        private final Matcher matcher;
        private int pos;
        private int previousColumn;
        private int previousLine;
        private final CharSequence text;

        private Tokenizer(CharSequence text2) {
            this.pos = 0;
            this.line = 0;
            this.column = 0;
            this.previousLine = 0;
            this.previousColumn = 0;
            this.text = text2;
            this.matcher = WHITESPACE.matcher(text2);
            skipWhitespace();
            nextToken();
        }

        /* access modifiers changed from: 0000 */
        public int getPreviousLine() {
            return this.previousLine;
        }

        /* access modifiers changed from: 0000 */
        public int getPreviousColumn() {
            return this.previousColumn;
        }

        /* access modifiers changed from: 0000 */
        public int getLine() {
            return this.line;
        }

        /* access modifiers changed from: 0000 */
        public int getColumn() {
            return this.column;
        }

        public boolean atEnd() {
            return this.currentToken.length() == 0;
        }

        public void nextToken() {
            this.previousLine = this.line;
            this.previousColumn = this.column;
            while (this.pos < this.matcher.regionStart()) {
                if (this.text.charAt(this.pos) == 10) {
                    this.line++;
                    this.column = 0;
                } else {
                    this.column++;
                }
                this.pos++;
            }
            if (this.matcher.regionStart() == this.matcher.regionEnd()) {
                this.currentToken = "";
                return;
            }
            this.matcher.usePattern(TOKEN);
            if (this.matcher.lookingAt()) {
                this.currentToken = this.matcher.group();
                this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
            } else {
                this.currentToken = String.valueOf(this.text.charAt(this.pos));
                this.matcher.region(this.pos + 1, this.matcher.regionEnd());
            }
            skipWhitespace();
        }

        private void skipWhitespace() {
            this.matcher.usePattern(WHITESPACE);
            if (this.matcher.lookingAt()) {
                this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
            }
        }

        public boolean tryConsume(String token) {
            if (!this.currentToken.equals(token)) {
                return false;
            }
            nextToken();
            return true;
        }

        public void consume(String token) throws ParseException {
            if (!tryConsume(token)) {
                throw parseException("Expected \"" + token + "\".");
            }
        }

        public boolean lookingAtInteger() {
            if (this.currentToken.length() == 0) {
                return false;
            }
            char c = this.currentToken.charAt(0);
            if (('0' <= c && c <= '9') || c == '-' || c == '+') {
                return true;
            }
            return false;
        }

        public boolean lookingAt(String text2) {
            return this.currentToken.equals(text2);
        }

        public String consumeIdentifier() throws ParseException {
            for (int i = 0; i < this.currentToken.length(); i++) {
                char c = this.currentToken.charAt(i);
                if (('a' > c || c > 'z') && (('A' > c || c > 'Z') && !(('0' <= c && c <= '9') || c == '_' || c == '.'))) {
                    throw parseException("Expected identifier. Found '" + this.currentToken + "'");
                }
            }
            String result = this.currentToken;
            nextToken();
            return result;
        }

        public boolean tryConsumeIdentifier() {
            try {
                consumeIdentifier();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public int consumeInt32() throws ParseException {
            try {
                int result = TextFormat.parseInt32(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public int consumeUInt32() throws ParseException {
            try {
                int result = TextFormat.parseUInt32(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public long consumeInt64() throws ParseException {
            try {
                long result = TextFormat.parseInt64(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeInt64() {
            try {
                consumeInt64();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public long consumeUInt64() throws ParseException {
            try {
                long result = TextFormat.parseUInt64(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeUInt64() {
            try {
                consumeUInt64();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public double consumeDouble() throws ParseException {
            if (DOUBLE_INFINITY.matcher(this.currentToken).matches()) {
                boolean negative = this.currentToken.startsWith(HelpFormatter.DEFAULT_OPT_PREFIX);
                nextToken();
                return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else if (this.currentToken.equalsIgnoreCase("nan")) {
                nextToken();
                return Double.NaN;
            } else {
                try {
                    double result = Double.parseDouble(this.currentToken);
                    nextToken();
                    return result;
                } catch (NumberFormatException e) {
                    throw floatParseException(e);
                }
            }
        }

        public boolean tryConsumeDouble() {
            try {
                consumeDouble();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public float consumeFloat() throws ParseException {
            if (FLOAT_INFINITY.matcher(this.currentToken).matches()) {
                boolean negative = this.currentToken.startsWith(HelpFormatter.DEFAULT_OPT_PREFIX);
                nextToken();
                return negative ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            } else if (FLOAT_NAN.matcher(this.currentToken).matches()) {
                nextToken();
                return Float.NaN;
            } else {
                try {
                    float result = Float.parseFloat(this.currentToken);
                    nextToken();
                    return result;
                } catch (NumberFormatException e) {
                    throw floatParseException(e);
                }
            }
        }

        public boolean tryConsumeFloat() {
            try {
                consumeFloat();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public boolean consumeBoolean() throws ParseException {
            if (this.currentToken.equals("true") || this.currentToken.equals("True") || this.currentToken.equals("t") || this.currentToken.equals("1")) {
                nextToken();
                return true;
            } else if (this.currentToken.equals("false") || this.currentToken.equals("False") || this.currentToken.equals("f") || this.currentToken.equals("0")) {
                nextToken();
                return false;
            } else {
                throw parseException("Expected \"true\" or \"false\". Found \"" + this.currentToken + "\".");
            }
        }

        public String consumeString() throws ParseException {
            return consumeByteString().toStringUtf8();
        }

        public boolean tryConsumeString() {
            try {
                consumeString();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public ByteString consumeByteString() throws ParseException {
            List<ByteString> list = new ArrayList<>();
            consumeByteString(list);
            while (true) {
                if (!this.currentToken.startsWith("'") && !this.currentToken.startsWith("\"")) {
                    return ByteString.copyFrom((Iterable<ByteString>) list);
                }
                consumeByteString(list);
            }
        }

        private void consumeByteString(List<ByteString> list) throws ParseException {
            char quote = 0;
            if (this.currentToken.length() > 0) {
                quote = this.currentToken.charAt(0);
            }
            if (quote != '\"' && quote != '\'') {
                throw parseException("Expected string.");
            } else if (this.currentToken.length() < 2 || this.currentToken.charAt(this.currentToken.length() - 1) != quote) {
                throw parseException("String missing ending quote.");
            } else {
                try {
                    ByteString result = TextFormat.unescapeBytes(this.currentToken.substring(1, this.currentToken.length() - 1));
                    nextToken();
                    list.add(result);
                } catch (InvalidEscapeSequenceException e) {
                    throw parseException(e.getMessage());
                }
            }
        }

        public ParseException parseException(String description) {
            return new ParseException(this.line + 1, this.column + 1, description);
        }

        public ParseException parseExceptionPreviousToken(String description) {
            return new ParseException(this.previousLine + 1, this.previousColumn + 1, description);
        }

        private ParseException integerParseException(NumberFormatException e) {
            return parseException("Couldn't parse integer: " + e.getMessage());
        }

        private ParseException floatParseException(NumberFormatException e) {
            return parseException("Couldn't parse number: " + e.getMessage());
        }

        public UnknownFieldParseException unknownFieldParseExceptionPreviousToken(String unknownField, String description) {
            return new UnknownFieldParseException(this.previousLine + 1, this.previousColumn + 1, unknownField, description);
        }
    }

    public static class UnknownFieldParseException extends ParseException {
        private final String unknownField;

        public UnknownFieldParseException(String message) {
            this(-1, -1, "", message);
        }

        public UnknownFieldParseException(int line, int column, String unknownField2, String message) {
            super(line, column, message);
            this.unknownField = unknownField2;
        }

        public String getUnknownField() {
            return this.unknownField;
        }
    }

    private TextFormat() {
    }

    public static void print(MessageOrBuilder message, Appendable output) throws IOException {
        Printer.DEFAULT.print(message, multiLineOutput(output));
    }

    public static void print(UnknownFieldSet fields, Appendable output) throws IOException {
        Printer.DEFAULT.printUnknownFields(fields, multiLineOutput(output));
    }

    public static void printUnicode(MessageOrBuilder message, Appendable output) throws IOException {
        Printer.UNICODE.print(message, multiLineOutput(output));
    }

    public static void printUnicode(UnknownFieldSet fields, Appendable output) throws IOException {
        Printer.UNICODE.printUnknownFields(fields, multiLineOutput(output));
    }

    public static String shortDebugString(MessageOrBuilder message) {
        try {
            StringBuilder text = new StringBuilder();
            Printer.DEFAULT.print(message, singleLineOutput(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String shortDebugString(FieldDescriptor field, Object value) {
        try {
            StringBuilder text = new StringBuilder();
            Printer.DEFAULT.printField(field, value, singleLineOutput(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String shortDebugString(UnknownFieldSet fields) {
        try {
            StringBuilder text = new StringBuilder();
            Printer.DEFAULT.printUnknownFields(fields, singleLineOutput(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(MessageOrBuilder message) {
        try {
            StringBuilder text = new StringBuilder();
            print(message, (Appendable) text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(UnknownFieldSet fields) {
        try {
            StringBuilder text = new StringBuilder();
            print(fields, (Appendable) text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(MessageOrBuilder message) {
        try {
            StringBuilder text = new StringBuilder();
            Printer.UNICODE.print(message, multiLineOutput(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(UnknownFieldSet fields) {
        try {
            StringBuilder text = new StringBuilder();
            Printer.UNICODE.printUnknownFields(fields, multiLineOutput(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printField(FieldDescriptor field, Object value, Appendable output) throws IOException {
        Printer.DEFAULT.printField(field, value, multiLineOutput(output));
    }

    public static String printFieldToString(FieldDescriptor field, Object value) {
        try {
            StringBuilder text = new StringBuilder();
            printField(field, value, text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printUnicodeFieldValue(FieldDescriptor field, Object value, Appendable output) throws IOException {
        Printer.UNICODE.printFieldValue(field, value, multiLineOutput(output));
    }

    public static void printFieldValue(FieldDescriptor field, Object value, Appendable output) throws IOException {
        Printer.DEFAULT.printFieldValue(field, value, multiLineOutput(output));
    }

    public static void printUnknownFieldValue(int tag, Object value, Appendable output) throws IOException {
        printUnknownFieldValue(tag, value, multiLineOutput(output));
    }

    /* access modifiers changed from: private */
    public static void printUnknownFieldValue(int tag, Object value, TextGenerator generator) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                generator.print(unsignedToString(((Long) value).longValue()));
                return;
            case 1:
                generator.print(String.format(null, "0x%016x", new Object[]{(Long) value}));
                return;
            case 2:
                try {
                    UnknownFieldSet message = UnknownFieldSet.parseFrom((ByteString) value);
                    generator.print("{");
                    generator.eol();
                    generator.indent();
                    Printer.DEFAULT.printUnknownFields(message, generator);
                    generator.outdent();
                    generator.print("}");
                    return;
                } catch (InvalidProtocolBufferException e) {
                    generator.print("\"");
                    generator.print(escapeBytes((ByteString) value));
                    generator.print("\"");
                    return;
                }
            case 3:
                Printer.DEFAULT.printUnknownFields((UnknownFieldSet) value, generator);
                return;
            case 5:
                generator.print(String.format(null, "0x%08x", new Object[]{(Integer) value}));
                return;
            default:
                throw new IllegalArgumentException("Bad tag: " + tag);
        }
    }

    public static String unsignedToString(int value) {
        if (value >= 0) {
            return Integer.toString(value);
        }
        return Long.toString(((long) value) & 4294967295L);
    }

    public static String unsignedToString(long value) {
        if (value >= 0) {
            return Long.toString(value);
        }
        return BigInteger.valueOf(LongCompanionObject.MAX_VALUE & value).setBit(63).toString();
    }

    private static TextGenerator multiLineOutput(Appendable output) {
        return new TextGenerator(output, false);
    }

    private static TextGenerator singleLineOutput(Appendable output) {
        return new TextGenerator(output, true);
    }

    public static Parser getParser() {
        return PARSER;
    }

    public static void merge(Readable input, Builder builder) throws IOException {
        PARSER.merge(input, builder);
    }

    public static void merge(CharSequence input, Builder builder) throws ParseException {
        PARSER.merge(input, builder);
    }

    public static <T extends Message> T parse(CharSequence input, Class<T> protoClass) throws ParseException {
        Builder builder = ((Message) Internal.getDefaultInstance(protoClass)).newBuilderForType();
        merge(input, builder);
        return builder.build();
    }

    public static void merge(Readable input, ExtensionRegistry extensionRegistry, Builder builder) throws IOException {
        PARSER.merge(input, extensionRegistry, builder);
    }

    public static void merge(CharSequence input, ExtensionRegistry extensionRegistry, Builder builder) throws ParseException {
        PARSER.merge(input, extensionRegistry, builder);
    }

    public static <T extends Message> T parse(CharSequence input, ExtensionRegistry extensionRegistry, Class<T> protoClass) throws ParseException {
        Builder builder = ((Message) Internal.getDefaultInstance(protoClass)).newBuilderForType();
        merge(input, extensionRegistry, builder);
        return builder.build();
    }

    public static String escapeBytes(ByteString input) {
        return TextFormatEscaper.escapeBytes(input);
    }

    public static String escapeBytes(byte[] input) {
        return TextFormatEscaper.escapeBytes(input);
    }

    public static ByteString unescapeBytes(CharSequence charString) throws InvalidEscapeSequenceException {
        int i;
        int pos;
        ByteString input = ByteString.copyFromUtf8(charString.toString());
        byte[] result = new byte[input.size()];
        int pos2 = 0;
        int i2 = 0;
        while (i < input.size()) {
            byte c = input.byteAt(i);
            if (c != 92) {
                pos = pos2 + 1;
                result[pos2] = c;
            } else if (i + 1 < input.size()) {
                i++;
                byte c2 = input.byteAt(i);
                if (isOctal(c2)) {
                    int code = digitValue(c2);
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        i++;
                        code = (code * 8) + digitValue(input.byteAt(i));
                    }
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        i++;
                        code = (code * 8) + digitValue(input.byteAt(i));
                    }
                    pos = pos2 + 1;
                    result[pos2] = (byte) code;
                } else {
                    switch (c2) {
                        case 34:
                            pos = pos2 + 1;
                            result[pos2] = 34;
                            break;
                        case 39:
                            pos = pos2 + 1;
                            result[pos2] = 39;
                            break;
                        case 92:
                            pos = pos2 + 1;
                            result[pos2] = 92;
                            break;
                        case 97:
                            pos = pos2 + 1;
                            result[pos2] = 7;
                            break;
                        case 98:
                            pos = pos2 + 1;
                            result[pos2] = 8;
                            break;
                        case 102:
                            pos = pos2 + 1;
                            result[pos2] = Ascii.FF;
                            break;
                        case 110:
                            pos = pos2 + 1;
                            result[pos2] = 10;
                            break;
                        case 114:
                            pos = pos2 + 1;
                            result[pos2] = Ascii.CR;
                            break;
                        case 116:
                            pos = pos2 + 1;
                            result[pos2] = 9;
                            break;
                        case 118:
                            pos = pos2 + 1;
                            result[pos2] = Ascii.VT;
                            break;
                        case 120:
                            if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                i++;
                                int code2 = digitValue(input.byteAt(i));
                                if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                    i++;
                                    code2 = (code2 * 16) + digitValue(input.byteAt(i));
                                }
                                pos = pos2 + 1;
                                result[pos2] = (byte) code2;
                                break;
                            } else {
                                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\x' with no digits");
                            }
                        default:
                            throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\" + ((char) c2) + '\'');
                    }
                }
            } else {
                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\' at end of string.");
            }
            pos2 = pos;
            i2 = i + 1;
        }
        if (result.length == pos2) {
            return ByteString.wrap(result);
        }
        return ByteString.copyFrom(result, 0, pos2);
    }

    static String escapeText(String input) {
        return escapeBytes(ByteString.copyFromUtf8(input));
    }

    public static String escapeDoubleQuotesAndBackslashes(String input) {
        return TextFormatEscaper.escapeDoubleQuotesAndBackslashes(input);
    }

    static String unescapeText(String input) throws InvalidEscapeSequenceException {
        return unescapeBytes(input).toStringUtf8();
    }

    private static boolean isOctal(byte c) {
        return 48 <= c && c <= 55;
    }

    private static boolean isHex(byte c) {
        return (48 <= c && c <= 57) || (97 <= c && c <= 102) || (65 <= c && c <= 70);
    }

    private static int digitValue(byte c) {
        if (48 <= c && c <= 57) {
            return c - 48;
        }
        if (97 > c || c > 122) {
            return (c - 65) + 10;
        }
        return (c - 97) + 10;
    }

    static int parseInt32(String text) throws NumberFormatException {
        return (int) parseInteger(text, true, false);
    }

    static int parseUInt32(String text) throws NumberFormatException {
        return (int) parseInteger(text, false, false);
    }

    static long parseInt64(String text) throws NumberFormatException {
        return parseInteger(text, true, true);
    }

    static long parseUInt64(String text) throws NumberFormatException {
        return parseInteger(text, false, true);
    }

    private static long parseInteger(String text, boolean isSigned, boolean isLong) throws NumberFormatException {
        int pos = 0;
        boolean negative = false;
        if (text.startsWith(HelpFormatter.DEFAULT_OPT_PREFIX, 0)) {
            if (!isSigned) {
                throw new NumberFormatException("Number must be positive: " + text);
            }
            pos = 0 + 1;
            negative = true;
        }
        int radix = 10;
        if (text.startsWith("0x", pos)) {
            pos += 2;
            radix = 16;
        } else if (text.startsWith("0", pos)) {
            radix = 8;
        }
        String numberText = text.substring(pos);
        if (numberText.length() < 16) {
            long result = Long.parseLong(numberText, radix);
            if (negative) {
                result = -result;
            }
            if (isLong) {
                return result;
            }
            if (isSigned) {
                if (result <= 2147483647L && result >= -2147483648L) {
                    return result;
                }
                throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
            } else if (result < 4294967296L && result >= 0) {
                return result;
            } else {
                throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
            }
        } else {
            BigInteger bigValue = new BigInteger(numberText, radix);
            if (negative) {
                bigValue = bigValue.negate();
            }
            if (!isLong) {
                if (isSigned) {
                    if (bigValue.bitLength() > 31) {
                        throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
                    }
                } else if (bigValue.bitLength() > 32) {
                    throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
                }
            } else if (isSigned) {
                if (bigValue.bitLength() > 63) {
                    throw new NumberFormatException("Number out of range for 64-bit signed integer: " + text);
                }
            } else if (bigValue.bitLength() > 64) {
                throw new NumberFormatException("Number out of range for 64-bit unsigned integer: " + text);
            }
            return bigValue.longValue();
        }
    }
}
