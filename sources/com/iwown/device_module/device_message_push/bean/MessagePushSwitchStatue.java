package com.iwown.device_module.device_message_push.bean;

public class MessagePushSwitchStatue {
    private boolean call = true;
    private boolean gmail = true;
    private boolean kakaoTalk = true;
    private boolean line = true;
    private boolean messenger = true;
    private boolean qq = true;
    private boolean sina = true;
    private boolean skeype1 = true;
    private boolean skeype2 = true;
    private boolean sms = true;
    private boolean twitter = true;
    private boolean whatsapp = true;
    private boolean whchat = true;

    public boolean isCall() {
        return this.call;
    }

    public void setCall(boolean call2) {
        this.call = call2;
    }

    public boolean isSms() {
        return this.sms;
    }

    public void setSms(boolean sms2) {
        this.sms = sms2;
    }

    public boolean isSkeype1() {
        return this.skeype1;
    }

    public void setSkeype1(boolean skeype12) {
        this.skeype1 = skeype12;
    }

    public boolean isSkeype2() {
        return this.skeype2;
    }

    public void setSkeype2(boolean skeype22) {
        this.skeype2 = skeype22;
    }

    public boolean isWhatsapp() {
        return this.whatsapp;
    }

    public void setWhatsapp(boolean whatsapp2) {
        this.whatsapp = whatsapp2;
    }

    public boolean isLine() {
        return this.line;
    }

    public void setLine(boolean line2) {
        this.line = line2;
    }

    public boolean isGmail() {
        return this.gmail;
    }

    public void setGmail(boolean gmail2) {
        this.gmail = gmail2;
    }

    public boolean isTwitter() {
        return this.twitter;
    }

    public void setTwitter(boolean twitter2) {
        this.twitter = twitter2;
    }

    public boolean isMessenger() {
        return this.messenger;
    }

    public void setMessenger(boolean messenger2) {
        this.messenger = messenger2;
    }

    public boolean isKakaoTalk() {
        return this.kakaoTalk;
    }

    public void setKakaoTalk(boolean kakaoTalk2) {
        this.kakaoTalk = kakaoTalk2;
    }

    public boolean isQq() {
        return this.qq;
    }

    public void setQq(boolean qq2) {
        this.qq = qq2;
    }

    public boolean isWhchat() {
        return this.whchat;
    }

    public void setWhchat(boolean whchat2) {
        this.whchat = whchat2;
    }

    public boolean isSina() {
        return this.sina;
    }

    public void setSina(boolean sina2) {
        this.sina = sina2;
    }
}
