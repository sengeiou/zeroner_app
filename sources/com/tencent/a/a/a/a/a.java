package com.tencent.a.a.a.a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

final class a {
    static List<String> a(File file) {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                arrayList.add(readLine.trim());
            } else {
                fileReader.close();
                bufferedReader.close();
                return arrayList;
            }
        }
    }

    static File d(String str) {
        File file = new File(str);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                d(file.getParentFile().getAbsolutePath());
            }
            file.mkdir();
        }
        return file;
    }
}
