package com.iwown.my_module.feedback.data;

import android.text.SpannableString;

public class TalkFirstEntity {
    private String date;
    private int megType;
    private SpannableString message;

    public int getMegType() {
        return this.megType;
    }

    public void setMegType(int megType2) {
        this.megType = megType2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public SpannableString getMessage() {
        return this.message;
    }

    public void setMessage(SpannableString message2) {
        this.message = message2;
    }

    public TalkFirstEntity() {
    }

    public TalkFirstEntity(String date2, SpannableString text, int megType2) {
        this.date = date2;
        this.message = text;
        this.megType = megType2;
    }
}
