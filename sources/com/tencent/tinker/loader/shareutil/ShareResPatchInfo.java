package com.tencent.tinker.loader.shareutil;

import com.alibaba.android.arouter.utils.Consts;
import com.tencent.tinker.loader.TinkerRuntimeException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

public class ShareResPatchInfo {
    public ArrayList<String> addRes = new ArrayList<>();
    public String arscBaseCrc = null;
    public ArrayList<String> deleteRes = new ArrayList<>();
    public HashMap<String, LargeModeInfo> largeModMap = new HashMap<>();
    public ArrayList<String> largeModRes = new ArrayList<>();
    public ArrayList<String> modRes = new ArrayList<>();
    public HashSet<Pattern> patterns = new HashSet<>();
    public String resArscMd5 = null;
    public HashMap<String, File> storeRes = new HashMap<>();

    public static class LargeModeInfo {
        public long crc;
        public File file = null;
        public String md5 = null;
    }

    public static void parseAllResPatchInfo(String meta, ShareResPatchInfo info) {
        if (meta != null && meta.length() != 0) {
            String[] lines = meta.split("\n");
            int i = 0;
            while (i < lines.length) {
                String line = lines[i];
                if (line != null && line.length() > 0) {
                    if (line.startsWith(ShareConstants.RES_TITLE)) {
                        String[] kv = line.split(",", 3);
                        info.arscBaseCrc = kv[1];
                        info.resArscMd5 = kv[2];
                    } else if (line.startsWith(ShareConstants.RES_PATTERN_TITLE)) {
                        for (int size = Integer.parseInt(line.split(":", 2)[1]); size > 0; size--) {
                            info.patterns.add(convertToPatternString(lines[i + 1]));
                            i++;
                        }
                    } else if (line.startsWith(ShareConstants.RES_ADD_TITLE)) {
                        for (int size2 = Integer.parseInt(line.split(":", 2)[1]); size2 > 0; size2--) {
                            info.addRes.add(lines[i + 1]);
                            i++;
                        }
                    } else if (line.startsWith(ShareConstants.RES_MOD_TITLE)) {
                        for (int size3 = Integer.parseInt(line.split(":", 2)[1]); size3 > 0; size3--) {
                            info.modRes.add(lines[i + 1]);
                            i++;
                        }
                    } else if (line.startsWith(ShareConstants.RES_LARGE_MOD_TITLE)) {
                        for (int size4 = Integer.parseInt(line.split(":", 2)[1]); size4 > 0; size4--) {
                            String[] data = lines[i + 1].split(",", 3);
                            String name = data[0];
                            LargeModeInfo largeModeInfo = new LargeModeInfo();
                            largeModeInfo.md5 = data[1];
                            largeModeInfo.crc = Long.parseLong(data[2]);
                            info.largeModRes.add(name);
                            info.largeModMap.put(name, largeModeInfo);
                            i++;
                        }
                    } else if (line.startsWith(ShareConstants.RES_DEL_TITLE)) {
                        for (int size5 = Integer.parseInt(line.split(":", 2)[1]); size5 > 0; size5--) {
                            info.deleteRes.add(lines[i + 1]);
                            i++;
                        }
                    } else if (line.startsWith(ShareConstants.RES_STORE_TITLE)) {
                        for (int size6 = Integer.parseInt(line.split(":", 2)[1]); size6 > 0; size6--) {
                            info.storeRes.put(lines[i + 1], null);
                            i++;
                        }
                    }
                }
                i++;
            }
        }
    }

    public static boolean checkFileInPattern(HashSet<Pattern> patterns2, String key) {
        if (!patterns2.isEmpty()) {
            Iterator<Pattern> it = patterns2.iterator();
            while (it.hasNext()) {
                if (((Pattern) it.next()).matcher(key).matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkResPatchInfo(ShareResPatchInfo info) {
        if (info == null) {
            return false;
        }
        String md5 = info.resArscMd5;
        if (md5 == null || md5.length() != 32) {
            return false;
        }
        return true;
    }

    private static Pattern convertToPatternString(String input) {
        if (input.contains(Consts.DOT)) {
            input = input.replaceAll("\\.", "\\\\.");
        }
        if (input.contains("?")) {
            input = input.replaceAll("\\?", "\\.");
        }
        if (input.contains("*")) {
            input = input.replace("*", ".*");
        }
        return Pattern.compile(input);
    }

    public static void parseResPatchInfoFirstLine(String meta, ShareResPatchInfo info) {
        if (meta != null && meta.length() != 0) {
            String firstLine = meta.split("\n")[0];
            if (firstLine == null || firstLine.length() <= 0) {
                throw new TinkerRuntimeException("res meta Corrupted:" + meta);
            }
            String[] kv = firstLine.split(",", 3);
            info.arscBaseCrc = kv[1];
            info.resArscMd5 = kv[2];
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("resArscMd5:" + this.resArscMd5 + "\n");
        sb.append("arscBaseCrc:" + this.arscBaseCrc + "\n");
        Iterator it = this.patterns.iterator();
        while (it.hasNext()) {
            sb.append(ShareConstants.RES_PATTERN_TITLE + ((Pattern) it.next()) + "\n");
        }
        Iterator it2 = this.addRes.iterator();
        while (it2.hasNext()) {
            sb.append("addedSet:" + ((String) it2.next()) + "\n");
        }
        Iterator it3 = this.modRes.iterator();
        while (it3.hasNext()) {
            sb.append("modifiedSet:" + ((String) it3.next()) + "\n");
        }
        Iterator it4 = this.largeModRes.iterator();
        while (it4.hasNext()) {
            sb.append("largeModifiedSet:" + ((String) it4.next()) + "\n");
        }
        Iterator it5 = this.deleteRes.iterator();
        while (it5.hasNext()) {
            sb.append("deletedSet:" + ((String) it5.next()) + "\n");
        }
        for (String store : this.storeRes.keySet()) {
            sb.append("storeSet:" + store + "\n");
        }
        return sb.toString();
    }
}
