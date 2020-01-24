package com.tencent.tinker.loader.shareutil;

import java.util.ArrayList;

public class ShareBsDiffPatchInfo {
    public String md5;
    public String name;
    public String patchMd5;
    public String path;
    public String rawCrc;

    public ShareBsDiffPatchInfo(String name2, String md52, String path2, String raw, String patch) {
        this.name = name2;
        this.md5 = md52;
        this.rawCrc = raw;
        this.patchMd5 = patch;
        this.path = path2;
    }

    public static void parseDiffPatchInfo(String meta, ArrayList<ShareBsDiffPatchInfo> diffList) {
        String[] lines;
        if (meta != null && meta.length() != 0) {
            for (String line : meta.split("\n")) {
                if (line != null && line.length() > 0) {
                    String[] kv = line.split(",", 5);
                    if (kv != null && kv.length >= 5) {
                        diffList.add(new ShareBsDiffPatchInfo(kv[0].trim(), kv[2].trim(), kv[1].trim(), kv[3].trim(), kv[4].trim()));
                    }
                }
            }
        }
    }

    public static boolean checkDiffPatchInfo(ShareBsDiffPatchInfo info) {
        if (info == null) {
            return false;
        }
        String name2 = info.name;
        String md52 = info.md5;
        if (name2 == null || name2.length() <= 0 || md52 == null || md52.length() != 32) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name);
        sb.append(",");
        sb.append(this.path);
        sb.append(",");
        sb.append(this.md5);
        sb.append(",");
        sb.append(this.rawCrc);
        sb.append(",");
        sb.append(this.patchMd5);
        return sb.toString();
    }
}
