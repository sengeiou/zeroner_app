package com.airbnb.lottie.parser;

class ContentModelParser {
    private ContentModelParser() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007b, code lost:
        if (r2.equals("gr") != false) goto L_0x004d;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.ContentModel parse(android.util.JsonReader r8, com.airbnb.lottie.LottieComposition r9) throws java.io.IOException {
        /*
            r5 = 1
            r3 = 0
            r4 = -1
            r2 = 0
            r8.beginObject()
            r0 = 2
        L_0x0008:
            boolean r6 = r8.hasNext()
            if (r6 == 0) goto L_0x003b
            java.lang.String r6 = r8.nextName()
            int r7 = r6.hashCode()
            switch(r7) {
                case 100: goto L_0x002c;
                case 3717: goto L_0x0021;
                default: goto L_0x0019;
            }
        L_0x0019:
            r6 = r4
        L_0x001a:
            switch(r6) {
                case 0: goto L_0x0037;
                case 1: goto L_0x003f;
                default: goto L_0x001d;
            }
        L_0x001d:
            r8.skipValue()
            goto L_0x0008
        L_0x0021:
            java.lang.String r7 = "ty"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0019
            r6 = r3
            goto L_0x001a
        L_0x002c:
            java.lang.String r7 = "d"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0019
            r6 = r5
            goto L_0x001a
        L_0x0037:
            java.lang.String r2 = r8.nextString()
        L_0x003b:
            if (r2 != 0) goto L_0x0044
            r1 = 0
        L_0x003e:
            return r1
        L_0x003f:
            int r0 = r8.nextInt()
            goto L_0x0008
        L_0x0044:
            r1 = 0
            int r6 = r2.hashCode()
            switch(r6) {
                case 3239: goto L_0x00c0;
                case 3270: goto L_0x0094;
                case 3295: goto L_0x009f;
                case 3307: goto L_0x0074;
                case 3308: goto L_0x0089;
                case 3488: goto L_0x00f2;
                case 3633: goto L_0x00cb;
                case 3646: goto L_0x00ff;
                case 3669: goto L_0x00b5;
                case 3679: goto L_0x00e5;
                case 3681: goto L_0x007e;
                case 3705: goto L_0x00d8;
                case 3710: goto L_0x00aa;
                default: goto L_0x004c;
            }
        L_0x004c:
            r3 = r4
        L_0x004d:
            switch(r3) {
                case 0: goto L_0x010c;
                case 1: goto L_0x0112;
                case 2: goto L_0x0118;
                case 3: goto L_0x011e;
                case 4: goto L_0x0124;
                case 5: goto L_0x012a;
                case 6: goto L_0x0130;
                case 7: goto L_0x0136;
                case 8: goto L_0x013c;
                case 9: goto L_0x0142;
                case 10: goto L_0x0148;
                case 11: goto L_0x014e;
                case 12: goto L_0x015a;
                default: goto L_0x0050;
            }
        L_0x0050:
            java.lang.String r3 = "LOTTIE"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Unknown shape type "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r4 = r4.toString()
            android.util.Log.w(r3, r4)
        L_0x006a:
            boolean r3 = r8.hasNext()
            if (r3 == 0) goto L_0x0160
            r8.skipValue()
            goto L_0x006a
        L_0x0074:
            java.lang.String r5 = "gr"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x004c
            goto L_0x004d
        L_0x007e:
            java.lang.String r3 = "st"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = r5
            goto L_0x004d
        L_0x0089:
            java.lang.String r3 = "gs"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 2
            goto L_0x004d
        L_0x0094:
            java.lang.String r3 = "fl"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 3
            goto L_0x004d
        L_0x009f:
            java.lang.String r3 = "gf"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 4
            goto L_0x004d
        L_0x00aa:
            java.lang.String r3 = "tr"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 5
            goto L_0x004d
        L_0x00b5:
            java.lang.String r3 = "sh"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 6
            goto L_0x004d
        L_0x00c0:
            java.lang.String r3 = "el"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 7
            goto L_0x004d
        L_0x00cb:
            java.lang.String r3 = "rc"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 8
            goto L_0x004d
        L_0x00d8:
            java.lang.String r3 = "tm"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 9
            goto L_0x004d
        L_0x00e5:
            java.lang.String r3 = "sr"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 10
            goto L_0x004d
        L_0x00f2:
            java.lang.String r3 = "mm"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 11
            goto L_0x004d
        L_0x00ff:
            java.lang.String r3 = "rp"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x004c
            r3 = 12
            goto L_0x004d
        L_0x010c:
            com.airbnb.lottie.model.content.ShapeGroup r1 = com.airbnb.lottie.parser.ShapeGroupParser.parse(r8, r9)
            goto L_0x006a
        L_0x0112:
            com.airbnb.lottie.model.content.ShapeStroke r1 = com.airbnb.lottie.parser.ShapeStrokeParser.parse(r8, r9)
            goto L_0x006a
        L_0x0118:
            com.airbnb.lottie.model.content.GradientStroke r1 = com.airbnb.lottie.parser.GradientStrokeParser.parse(r8, r9)
            goto L_0x006a
        L_0x011e:
            com.airbnb.lottie.model.content.ShapeFill r1 = com.airbnb.lottie.parser.ShapeFillParser.parse(r8, r9)
            goto L_0x006a
        L_0x0124:
            com.airbnb.lottie.model.content.GradientFill r1 = com.airbnb.lottie.parser.GradientFillParser.parse(r8, r9)
            goto L_0x006a
        L_0x012a:
            com.airbnb.lottie.model.animatable.AnimatableTransform r1 = com.airbnb.lottie.parser.AnimatableTransformParser.parse(r8, r9)
            goto L_0x006a
        L_0x0130:
            com.airbnb.lottie.model.content.ShapePath r1 = com.airbnb.lottie.parser.ShapePathParser.parse(r8, r9)
            goto L_0x006a
        L_0x0136:
            com.airbnb.lottie.model.content.CircleShape r1 = com.airbnb.lottie.parser.CircleShapeParser.parse(r8, r9, r0)
            goto L_0x006a
        L_0x013c:
            com.airbnb.lottie.model.content.RectangleShape r1 = com.airbnb.lottie.parser.RectangleShapeParser.parse(r8, r9)
            goto L_0x006a
        L_0x0142:
            com.airbnb.lottie.model.content.ShapeTrimPath r1 = com.airbnb.lottie.parser.ShapeTrimPathParser.parse(r8, r9)
            goto L_0x006a
        L_0x0148:
            com.airbnb.lottie.model.content.PolystarShape r1 = com.airbnb.lottie.parser.PolystarShapeParser.parse(r8, r9)
            goto L_0x006a
        L_0x014e:
            com.airbnb.lottie.model.content.MergePaths r1 = com.airbnb.lottie.parser.MergePathsParser.parse(r8)
            java.lang.String r3 = "Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove()."
            r9.addWarning(r3)
            goto L_0x006a
        L_0x015a:
            com.airbnb.lottie.model.content.Repeater r1 = com.airbnb.lottie.parser.RepeaterParser.parse(r8, r9)
            goto L_0x006a
        L_0x0160:
            r8.endObject()
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.ContentModelParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.ContentModel");
    }
}
