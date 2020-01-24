package com.iwown.device_module.device_setting.heart.bean;

public class HeartGuidanceStatue {
    private boolean heart_guidance_switch;
    private int heart_guidance_type;
    private int maxHeart;
    private int minHeart;

    public boolean isHeart_guidance_switch() {
        return this.heart_guidance_switch;
    }

    public void setHeart_guidance_switch(boolean heart_guidance_switch2) {
        this.heart_guidance_switch = heart_guidance_switch2;
    }

    public int getHeart_guidance_type() {
        return this.heart_guidance_type;
    }

    public void setHeart_guidance_type(int heart_guidance_type2) {
        this.heart_guidance_type = heart_guidance_type2;
    }

    public int getMinHeart() {
        return this.minHeart;
    }

    public void setMinHeart(int minHeart2) {
        this.minHeart = minHeart2;
    }

    public int getMaxHeart() {
        return this.maxHeart;
    }

    public void setMaxHeart(int maxHeart2) {
        this.maxHeart = maxHeart2;
    }
}
