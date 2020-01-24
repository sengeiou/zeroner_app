package com.iwown.device_module.device_message_push;

public class CallMessage {
    private String last_msg = "";
    private long last_receive_time;

    public long getLast_receive_time() {
        return this.last_receive_time;
    }

    public void setLast_receive_time(long last_receive_time2) {
        this.last_receive_time = last_receive_time2;
    }

    public String getLast_msg() {
        return this.last_msg;
    }

    public void setLast_msg(String last_msg2) {
        this.last_msg = last_msg2;
    }
}
