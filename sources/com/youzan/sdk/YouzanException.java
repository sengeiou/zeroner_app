package com.youzan.sdk;

public final class YouzanException extends Exception {

    /* renamed from: ˊ reason: contains not printable characters */
    private final String f14;

    /* renamed from: ˋ reason: contains not printable characters */
    private int f15;

    public YouzanException(int code, String msg) {
        super(msg);
        this.f14 = msg;
        this.f15 = code;
    }

    public YouzanException(String msg) {
        super(msg);
        this.f14 = msg;
    }

    public int getCode() {
        return this.f15;
    }

    public String getMsg() {
        return this.f14;
    }
}
