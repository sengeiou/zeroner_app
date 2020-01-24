package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"}, d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, k = 5, mv = {1, 1, 8}, xi = 1, xs = "kotlin/text/StringsKt")
/* compiled from: Indent.kt */
class StringsKt__IndentKt {
    @NotNull
    public static /* bridge */ /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return StringsKt.trimMargin(str, str2);
    }

    @NotNull
    public static final String trimMargin(@NotNull String $receiver, @NotNull String marginPrefix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        return StringsKt.replaceIndentByMargin($receiver, "", marginPrefix);
    }

    @NotNull
    public static /* bridge */ /* synthetic */ String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = "|";
        }
        return StringsKt.replaceIndentByMargin(str, str2, str3);
    }

    @NotNull
    public static final String replaceIndentByMargin(@NotNull String $receiver, @NotNull String newIndent, @NotNull String marginPrefix) {
        String str;
        String it$iv$iv$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        if (!(!StringsKt.isBlank(marginPrefix))) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        }
        List lines = StringsKt.lines($receiver);
        int length = $receiver.length() + (newIndent.length() * lines.size());
        Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex$iv = CollectionsKt.getLastIndex(lines);
        Iterable<String> $receiver$iv$iv = lines;
        Collection destination$iv$iv$iv = new ArrayList();
        int index$iv$iv$iv$iv = 0;
        for (String line : $receiver$iv$iv) {
            int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
            int index$iv = index$iv$iv$iv$iv;
            if ((index$iv == 0 || index$iv == lastIndex$iv) && StringsKt.isBlank(line)) {
                it$iv$iv$iv = null;
            } else {
                CharSequence $receiver$iv = line;
                int firstNonWhitespaceIndex = 0;
                int length2 = $receiver$iv.length();
                while (true) {
                    if (firstNonWhitespaceIndex >= length2) {
                        firstNonWhitespaceIndex = -1;
                        break;
                    }
                    if (!CharsKt.isWhitespace($receiver$iv.charAt(firstNonWhitespaceIndex))) {
                        break;
                    }
                    firstNonWhitespaceIndex++;
                }
                if (firstNonWhitespaceIndex == -1) {
                    str = null;
                } else if (StringsKt.startsWith$default(line, marginPrefix, firstNonWhitespaceIndex, false, 4, null)) {
                    int length3 = marginPrefix.length() + firstNonWhitespaceIndex;
                    if (line == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    str = line.substring(length3);
                    Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).substring(startIndex)");
                } else {
                    str = null;
                }
                if (str != null) {
                    String str2 = (String) indentAddFunction$iv.invoke(str);
                    if (str2 != null) {
                        it$iv$iv$iv = str2;
                    }
                }
                it$iv$iv$iv = line;
            }
            if (it$iv$iv$iv != null) {
                destination$iv$iv$iv.add(it$iv$iv$iv);
            }
            index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
        }
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder(length), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }

    @NotNull
    public static final String trimIndent(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.replaceIndent($receiver, "");
    }

    @NotNull
    public static /* bridge */ /* synthetic */ String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return StringsKt.replaceIndent(str, str2);
    }

    @NotNull
    public static final String replaceIndent(@NotNull String $receiver, @NotNull String newIndent) {
        String it$iv$iv$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        List lines = StringsKt.lines($receiver);
        Iterable iterable = lines;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : iterable) {
            if (!StringsKt.isBlank((String) element$iv$iv)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable<String> iterable2 = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable2, 10));
        for (String indentWidth$StringsKt__IndentKt : iterable2) {
            destination$iv$iv2.add(Integer.valueOf(indentWidth$StringsKt__IndentKt(indentWidth$StringsKt__IndentKt)));
        }
        Integer num = (Integer) CollectionsKt.min((Iterable<? extends T>) (List) destination$iv$iv2);
        int minCommonIndent = num != null ? num.intValue() : 0;
        int length = $receiver.length() + (newIndent.length() * lines.size());
        Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex$iv = CollectionsKt.getLastIndex(lines);
        Iterable<String> $receiver$iv$iv = lines;
        Collection destination$iv$iv$iv = new ArrayList();
        int index$iv$iv$iv$iv = 0;
        for (String line : $receiver$iv$iv) {
            int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
            int index$iv = index$iv$iv$iv$iv;
            if ((index$iv == 0 || index$iv == lastIndex$iv) && StringsKt.isBlank(line)) {
                it$iv$iv$iv = null;
            } else {
                String drop = StringsKt.drop(line, minCommonIndent);
                if (drop != null) {
                    String str = (String) indentAddFunction$iv.invoke(drop);
                    if (str != null) {
                        it$iv$iv$iv = str;
                    }
                }
                it$iv$iv$iv = line;
            }
            if (it$iv$iv$iv != null) {
                destination$iv$iv$iv.add(it$iv$iv$iv);
            }
            index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
        }
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder(length), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }

    @NotNull
    public static /* bridge */ /* synthetic */ String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return StringsKt.prependIndent(str, str2);
    }

    @NotNull
    public static final String prependIndent(@NotNull String $receiver, @NotNull String indent) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indent, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence($receiver), new StringsKt__IndentKt$prependIndent$1(indent)), "\n", null, null, 0, null, null, 62, null);
    }

    private static final int indentWidth$StringsKt__IndentKt(@NotNull String $receiver) {
        int it;
        CharSequence $receiver$iv = $receiver;
        int length = $receiver$iv.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                it = -1;
                break;
            }
            if (!CharsKt.isWhitespace($receiver$iv.charAt(i))) {
                it = i;
                break;
            }
            i++;
        }
        return it == -1 ? $receiver.length() : it;
    }

    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String indent) {
        if (indent.length() == 0) {
            return StringsKt__IndentKt$getIndentFunction$1.INSTANCE;
        }
        return new StringsKt__IndentKt$getIndentFunction$2<>(indent);
    }

    private static final String reindent$StringsKt__IndentKt(@NotNull List<String> $receiver, int resultSizeEstimate, Function1<? super String, String> indentAddFunction, Function1<? super String, String> indentCutFunction) {
        String it$iv$iv;
        int lastIndex = CollectionsKt.getLastIndex($receiver);
        Iterable<String> $receiver$iv = $receiver;
        Collection destination$iv$iv = new ArrayList();
        int index$iv$iv$iv = 0;
        for (String str : $receiver$iv) {
            int index$iv$iv$iv2 = index$iv$iv$iv + 1;
            int index = index$iv$iv$iv;
            if ((index == 0 || index == lastIndex) && StringsKt.isBlank(str)) {
                it$iv$iv = null;
            } else {
                String str2 = (String) indentCutFunction.invoke(str);
                if (str2 != null) {
                    String str3 = (String) indentAddFunction.invoke(str2);
                    if (str3 != null) {
                        it$iv$iv = str3;
                    }
                }
                it$iv$iv = str;
            }
            if (it$iv$iv != null) {
                destination$iv$iv.add(it$iv$iv);
            }
            index$iv$iv$iv = index$iv$iv$iv2;
        }
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv, new StringBuilder(resultSizeEstimate), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }
}
