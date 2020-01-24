package com.iwown.data_link.utils;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AssetsUtils {
    public static String getFromAssets(Context context, String fileName) {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(fileName)));
            String str = "";
            String Result = "";
            while (true) {
                String line = bufReader.readLine();
                if (line == null) {
                    return Result;
                }
                Result = Result + line;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }
}
