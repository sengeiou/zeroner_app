package com.iwown.device_module.device_message_push.bean;

public class SmsInfo {
    public String _id = "";
    public int action = 0;
    public String date = "";
    public String person = "";
    public String read = "";
    public String smsAddress = "";
    public String smsBody = "";
    public String thread_id = "";

    public String toString() {
        return "SmsInfo{_id='" + this._id + '\'' + ", thread_id='" + this.thread_id + '\'' + ", smsAddress='" + this.smsAddress + '\'' + ", smsBody='" + this.smsBody + '\'' + ", read='" + this.read + '\'' + ", date='" + this.date + '\'' + ", person='" + this.person + '\'' + ", action=" + this.action + '}';
    }
}
