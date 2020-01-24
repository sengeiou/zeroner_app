package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.model.content.MergePaths;
import com.airbnb.lottie.model.content.MergePaths.MergePathsMode;
import java.io.IOException;

class MergePathsParser {
    private MergePathsParser() {
    }

    static MergePaths parse(JsonReader reader) throws IOException {
        String name = null;
        MergePathsMode mode = null;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 3488:
                    if (nextName.equals("mm")) {
                        c = 1;
                        break;
                    }
                    break;
                case 3519:
                    if (nextName.equals("nm")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    mode = MergePathsMode.forId(reader.nextInt());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new MergePaths(name, mode);
    }
}
