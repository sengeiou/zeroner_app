package com.airbnb.lottie.parser;

class GradientFillParser {
    private GradientFillParser() {
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.GradientFill parse(android.util.JsonReader r14, com.airbnb.lottie.LottieComposition r15) throws java.io.IOException {
        /*
            r8 = 0
            r9 = 0
            r11 = -1
            r12 = 1
            r1 = 0
            r4 = 0
            r5 = 0
            r2 = 0
            r6 = 0
            r7 = 0
            r3 = 0
        L_0x000b:
            boolean r0 = r14.hasNext()
            if (r0 == 0) goto L_0x00e4
            java.lang.String r0 = r14.nextName()
            int r13 = r0.hashCode()
            switch(r13) {
                case 101: goto L_0x005b;
                case 103: goto L_0x002f;
                case 111: goto L_0x003a;
                case 114: goto L_0x0066;
                case 115: goto L_0x0050;
                case 116: goto L_0x0045;
                case 3519: goto L_0x0024;
                default: goto L_0x001c;
            }
        L_0x001c:
            r0 = r11
        L_0x001d:
            switch(r0) {
                case 0: goto L_0x0071;
                case 1: goto L_0x0076;
                case 2: goto L_0x00b8;
                case 3: goto L_0x00be;
                case 4: goto L_0x00cb;
                case 5: goto L_0x00d1;
                case 6: goto L_0x00d7;
                default: goto L_0x0020;
            }
        L_0x0020:
            r14.skipValue()
            goto L_0x000b
        L_0x0024:
            java.lang.String r13 = "nm"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x001c
            r0 = r9
            goto L_0x001d
        L_0x002f:
            java.lang.String r13 = "g"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x001c
            r0 = r12
            goto L_0x001d
        L_0x003a:
            java.lang.String r13 = "o"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x001c
            r0 = 2
            goto L_0x001d
        L_0x0045:
            java.lang.String r13 = "t"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x001c
            r0 = 3
            goto L_0x001d
        L_0x0050:
            java.lang.String r13 = "s"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x001c
            r0 = 4
            goto L_0x001d
        L_0x005b:
            java.lang.String r13 = "e"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x001c
            r0 = 5
            goto L_0x001d
        L_0x0066:
            java.lang.String r13 = "r"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x001c
            r0 = 6
            goto L_0x001d
        L_0x0071:
            java.lang.String r1 = r14.nextString()
            goto L_0x000b
        L_0x0076:
            r10 = -1
            r14.beginObject()
        L_0x007a:
            boolean r0 = r14.hasNext()
            if (r0 == 0) goto L_0x00b3
            java.lang.String r0 = r14.nextName()
            int r13 = r0.hashCode()
            switch(r13) {
                case 107: goto L_0x009e;
                case 112: goto L_0x0093;
                default: goto L_0x008b;
            }
        L_0x008b:
            r0 = r11
        L_0x008c:
            switch(r0) {
                case 0: goto L_0x00a9;
                case 1: goto L_0x00ae;
                default: goto L_0x008f;
            }
        L_0x008f:
            r14.skipValue()
            goto L_0x007a
        L_0x0093:
            java.lang.String r13 = "p"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x008b
            r0 = r9
            goto L_0x008c
        L_0x009e:
            java.lang.String r13 = "k"
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x008b
            r0 = r12
            goto L_0x008c
        L_0x00a9:
            int r10 = r14.nextInt()
            goto L_0x007a
        L_0x00ae:
            com.airbnb.lottie.model.animatable.AnimatableGradientColorValue r4 = com.airbnb.lottie.parser.AnimatableValueParser.parseGradientColor(r14, r15, r10)
            goto L_0x007a
        L_0x00b3:
            r14.endObject()
            goto L_0x000b
        L_0x00b8:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r5 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r14, r15)
            goto L_0x000b
        L_0x00be:
            int r0 = r14.nextInt()
            if (r0 != r12) goto L_0x00c8
            com.airbnb.lottie.model.content.GradientType r2 = com.airbnb.lottie.model.content.GradientType.Linear
        L_0x00c6:
            goto L_0x000b
        L_0x00c8:
            com.airbnb.lottie.model.content.GradientType r2 = com.airbnb.lottie.model.content.GradientType.Radial
            goto L_0x00c6
        L_0x00cb:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r6 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r14, r15)
            goto L_0x000b
        L_0x00d1:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r7 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r14, r15)
            goto L_0x000b
        L_0x00d7:
            int r0 = r14.nextInt()
            if (r0 != r12) goto L_0x00e1
            android.graphics.Path$FillType r3 = android.graphics.Path.FillType.WINDING
        L_0x00df:
            goto L_0x000b
        L_0x00e1:
            android.graphics.Path$FillType r3 = android.graphics.Path.FillType.EVEN_ODD
            goto L_0x00df
        L_0x00e4:
            com.airbnb.lottie.model.content.GradientFill r0 = new com.airbnb.lottie.model.content.GradientFill
            r9 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.GradientFillParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.GradientFill");
    }
}
