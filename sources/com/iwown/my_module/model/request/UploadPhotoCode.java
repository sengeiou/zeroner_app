package com.iwown.my_module.model.request;

import com.iwown.data_link.base.RetCode;

public class UploadPhotoCode extends RetCode {
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }
}
