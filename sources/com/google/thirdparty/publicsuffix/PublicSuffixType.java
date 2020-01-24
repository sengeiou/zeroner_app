package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
enum PublicSuffixType {
    PRIVATE(':', ','),
    ICANN('!', '?');
    
    private final char innerNodeCode;
    private final char leafNodeCode;

    private PublicSuffixType(char innerNodeCode2, char leafNodeCode2) {
        this.innerNodeCode = innerNodeCode2;
        this.leafNodeCode = leafNodeCode2;
    }

    /* access modifiers changed from: 0000 */
    public char getLeafNodeCode() {
        return this.leafNodeCode;
    }

    /* access modifiers changed from: 0000 */
    public char getInnerNodeCode() {
        return this.innerNodeCode;
    }

    static PublicSuffixType fromCode(char code) {
        PublicSuffixType[] arr$;
        for (PublicSuffixType value : values()) {
            if (value.getInnerNodeCode() == code || value.getLeafNodeCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum corresponding to given code: " + code);
    }

    static PublicSuffixType fromIsPrivate(boolean isPrivate) {
        return isPrivate ? PRIVATE : ICANN;
    }
}
