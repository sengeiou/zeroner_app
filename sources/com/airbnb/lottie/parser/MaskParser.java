package com.airbnb.lottie.parser;

class MaskParser {
    private MaskParser() {
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.Mask parse(android.util.JsonReader r11, com.airbnb.lottie.LottieComposition r12) throws java.io.IOException {
        /*
            r8 = 2
            r7 = 1
            r5 = 0
            r6 = -1
            r0 = 0
            r1 = 0
            r3 = 0
            r11.beginObject()
        L_0x000a:
            boolean r4 = r11.hasNext()
            if (r4 == 0) goto L_0x00b0
            java.lang.String r2 = r11.nextName()
            int r4 = r2.hashCode()
            switch(r4) {
                case 111: goto L_0x0039;
                case 3588: goto L_0x002e;
                case 3357091: goto L_0x0023;
                default: goto L_0x001b;
            }
        L_0x001b:
            r4 = r6
        L_0x001c:
            switch(r4) {
                case 0: goto L_0x0044;
                case 1: goto L_0x00a4;
                case 2: goto L_0x00aa;
                default: goto L_0x001f;
            }
        L_0x001f:
            r11.skipValue()
            goto L_0x000a
        L_0x0023:
            java.lang.String r4 = "mode"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x001b
            r4 = r5
            goto L_0x001c
        L_0x002e:
            java.lang.String r4 = "pt"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x001b
            r4 = r7
            goto L_0x001c
        L_0x0039:
            java.lang.String r4 = "o"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x001b
            r4 = r8
            goto L_0x001c
        L_0x0044:
            java.lang.String r4 = r11.nextString()
            int r9 = r4.hashCode()
            switch(r9) {
                case 97: goto L_0x0077;
                case 105: goto L_0x008d;
                case 115: goto L_0x0082;
                default: goto L_0x004f;
            }
        L_0x004f:
            r4 = r6
        L_0x0050:
            switch(r4) {
                case 0: goto L_0x0098;
                case 1: goto L_0x009c;
                case 2: goto L_0x00a0;
                default: goto L_0x0053;
            }
        L_0x0053:
            java.lang.String r4 = "LOTTIE"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Unknown mask mode "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r2)
            java.lang.String r10 = ". Defaulting to Add."
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.w(r4, r9)
            com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeAdd
            goto L_0x000a
        L_0x0077:
            java.lang.String r9 = "a"
            boolean r4 = r4.equals(r9)
            if (r4 == 0) goto L_0x004f
            r4 = r5
            goto L_0x0050
        L_0x0082:
            java.lang.String r9 = "s"
            boolean r4 = r4.equals(r9)
            if (r4 == 0) goto L_0x004f
            r4 = r7
            goto L_0x0050
        L_0x008d:
            java.lang.String r9 = "i"
            boolean r4 = r4.equals(r9)
            if (r4 == 0) goto L_0x004f
            r4 = r8
            goto L_0x0050
        L_0x0098:
            com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeAdd
            goto L_0x000a
        L_0x009c:
            com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeSubtract
            goto L_0x000a
        L_0x00a0:
            com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeIntersect
            goto L_0x000a
        L_0x00a4:
            com.airbnb.lottie.model.animatable.AnimatableShapeValue r1 = com.airbnb.lottie.parser.AnimatableValueParser.parseShapeData(r11, r12)
            goto L_0x000a
        L_0x00aa:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r3 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r11, r12)
            goto L_0x000a
        L_0x00b0:
            r11.endObject()
            com.airbnb.lottie.model.content.Mask r4 = new com.airbnb.lottie.model.content.Mask
            r4.<init>(r0, r1, r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.MaskParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.Mask");
    }
}
