package com.iwown.sport_module.gps.data;

import java.util.LinkedList;

public class GpsVoiceLink {
    private LinkedList<Integer> heartList = new LinkedList<>();
    private int heartNum;
    private int linkNum = 0;
    private int maxLink = 15;
    private int playHeartNum;
    private int voiceTime = 0;

    public GpsVoiceLink(int maxLink2, int heartNum2, int playHeartNum2) {
        this.maxLink = maxLink2;
        this.heartNum = heartNum2;
        this.playHeartNum = playHeartNum2;
    }

    public LinkedList<Integer> getHeartList() {
        return this.heartList;
    }

    public int getLinkNum() {
        return this.linkNum;
    }

    public void setLinkNum(int linkNum2) {
        this.linkNum = linkNum2;
    }

    public int getMaxLink() {
        return this.maxLink;
    }

    public void setMaxLink(int maxLink2) {
        this.maxLink = maxLink2;
    }

    public void setHeartList(LinkedList<Integer> heartList2) {
        this.heartList = heartList2;
    }

    public void addLinkList(int number) {
        if (this.linkNum < this.maxLink) {
            this.heartList.add(Integer.valueOf(number));
            this.linkNum++;
            return;
        }
        this.heartList.removeFirst();
        this.heartList.add(Integer.valueOf(number));
    }

    public int getListSize() {
        return this.heartList.size();
    }

    public boolean isFull() {
        if (this.heartList.size() >= this.maxLink) {
            return true;
        }
        return false;
    }

    public int getHeartNum() {
        return this.heartNum;
    }

    public void setHeartNum(int heartNum2) {
        this.heartNum = heartNum2;
    }

    public int getPlayHeartNum() {
        return this.playHeartNum;
    }

    public void setPlayHeartNum(int playHeartNum2) {
        this.playHeartNum = playHeartNum2;
    }

    public void clearList() {
        this.heartList.clear();
    }

    public int getVoiceTime() {
        return this.voiceTime;
    }

    public void setVoiceTime(int voiceTime2) {
        this.voiceTime = voiceTime2;
    }
}
