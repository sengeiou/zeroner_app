package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.tencent.connect.common.Constants;

public abstract class AbstractExpandedDecoder {
    private final GeneralAppIdDecoder generalDecoder;
    private final BitArray information;

    public abstract String parseInformation() throws NotFoundException, FormatException;

    AbstractExpandedDecoder(BitArray information2) {
        this.information = information2;
        this.generalDecoder = new GeneralAppIdDecoder(information2);
    }

    /* access modifiers changed from: protected */
    public final BitArray getInformation() {
        return this.information;
    }

    /* access modifiers changed from: protected */
    public final GeneralAppIdDecoder getGeneralDecoder() {
        return this.generalDecoder;
    }

    public static AbstractExpandedDecoder createDecoder(BitArray information2) {
        if (information2.get(1)) {
            return new AI01AndOtherAIs(information2);
        }
        if (!information2.get(2)) {
            return new AnyAIDecoder(information2);
        }
        switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(information2, 1, 4)) {
            case 4:
                return new AI013103decoder(information2);
            case 5:
                return new AI01320xDecoder(information2);
            default:
                switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(information2, 1, 5)) {
                    case 12:
                        return new AI01392xDecoder(information2);
                    case 13:
                        return new AI01393xDecoder(information2);
                    default:
                        switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(information2, 1, 7)) {
                            case 56:
                                return new AI013x0x1xDecoder(information2, "310", Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                            case 57:
                                return new AI013x0x1xDecoder(information2, "320", Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                            case 58:
                                return new AI013x0x1xDecoder(information2, "310", Constants.VIA_REPORT_TYPE_JOININ_GROUP);
                            case 59:
                                return new AI013x0x1xDecoder(information2, "320", Constants.VIA_REPORT_TYPE_JOININ_GROUP);
                            case 60:
                                return new AI013x0x1xDecoder(information2, "310", Constants.VIA_REPORT_TYPE_WPA_STATE);
                            case 61:
                                return new AI013x0x1xDecoder(information2, "320", Constants.VIA_REPORT_TYPE_WPA_STATE);
                            case 62:
                                return new AI013x0x1xDecoder(information2, "310", Constants.VIA_REPORT_TYPE_START_GROUP);
                            case 63:
                                return new AI013x0x1xDecoder(information2, "320", Constants.VIA_REPORT_TYPE_START_GROUP);
                            default:
                                throw new IllegalStateException("unknown decoder: " + information2);
                        }
                }
        }
    }
}
