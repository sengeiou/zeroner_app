package com.iwown.my_module.model.request;

public class ChangePasswordRequest {
    private String new_password;
    private String old_password;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getOld_password() {
        return this.old_password;
    }

    public void setOld_password(String old_password2) {
        this.old_password = old_password2;
    }

    public String getNew_password() {
        return this.new_password;
    }

    public void setNew_password(String new_password2) {
        this.new_password = new_password2;
    }
}
