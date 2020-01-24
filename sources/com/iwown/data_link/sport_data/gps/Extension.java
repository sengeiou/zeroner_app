package com.iwown.data_link.sport_data.gps;

import java.util.HashMap;

public class Extension {
    protected HashMap<String, Object> extensionData;

    public HashMap<String, Object> getExtensionData() {
        return this.extensionData;
    }

    public void setExtensionData(HashMap<String, Object> paramHashMap) {
        this.extensionData = paramHashMap;
    }

    public void addExtensionData(String paramString, Object paramObject) {
        if (this.extensionData == null) {
            this.extensionData = new HashMap<>();
        }
        this.extensionData.put(paramString, paramObject);
    }

    public Object getExtensionData(String paramString) {
        if (this.extensionData != null) {
            return this.extensionData.get(paramString);
        }
        return null;
    }

    public int getExtensionsParsed() {
        if (this.extensionData != null) {
            return this.extensionData.size();
        }
        return 0;
    }
}
