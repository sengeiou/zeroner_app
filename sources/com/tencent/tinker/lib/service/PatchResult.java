package com.tencent.tinker.lib.service;

import java.io.Serializable;

public class PatchResult implements Serializable {
    public long costTime;
    public Throwable e;
    public boolean isSuccess;
    public String patchVersion;
    public String rawPatchFilePath;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\nPatchResult: \n");
        sb.append("isSuccess:" + this.isSuccess + "\n");
        sb.append("rawPatchFilePath:" + this.rawPatchFilePath + "\n");
        sb.append("costTime:" + this.costTime + "\n");
        if (this.patchVersion != null) {
            sb.append("patchVersion:" + this.patchVersion + "\n");
        }
        if (this.e != null) {
            sb.append("Throwable:" + this.e.getMessage() + "\n");
        }
        return sb.toString();
    }
}
