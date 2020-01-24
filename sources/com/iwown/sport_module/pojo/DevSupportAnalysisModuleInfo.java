package com.iwown.sport_module.pojo;

public class DevSupportAnalysisModuleInfo {
    private String name_key = "";
    private int support_modules = 0;

    public DevSupportAnalysisModuleInfo(String name_key2, int support_modules2) {
        this.name_key = name_key2;
        this.support_modules = support_modules2;
    }

    public DevSupportAnalysisModuleInfo() {
    }

    public String getName_key() {
        return this.name_key;
    }

    public void setName_key(String name_key2) {
        this.name_key = name_key2;
    }

    public int getSupport_modules() {
        return this.support_modules;
    }

    public void setSupport_modules(int support_modules2) {
        this.support_modules = support_modules2;
    }
}
