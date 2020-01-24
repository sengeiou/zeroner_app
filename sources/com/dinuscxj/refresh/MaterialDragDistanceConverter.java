package com.dinuscxj.refresh;

public class MaterialDragDistanceConverter implements IDragDistanceConverter {
    public float convert(float scrollDistance, float refreshDistance) {
        float dragPercent = Math.min(1.0f, Math.abs(scrollDistance / refreshDistance));
        float slingshotDist = refreshDistance;
        float tensionSlingshotPercent = Math.max(0.0f, Math.min(Math.abs(scrollDistance) - slingshotDist, 2.0f * slingshotDist) / slingshotDist);
        return (float) ((int) ((slingshotDist * dragPercent) + (slingshotDist * ((float) (((double) (tensionSlingshotPercent / 4.0f)) - Math.pow((double) (tensionSlingshotPercent / 4.0f), 2.0d))) * 2.0f * 2.0f)));
    }
}
