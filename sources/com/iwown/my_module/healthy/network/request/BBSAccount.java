package com.iwown.my_module.healthy.network.request;

public class BBSAccount {
    private String email;
    private String password;
    private long uid;
    private String userName;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }
}
