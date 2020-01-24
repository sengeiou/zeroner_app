package com.tencent.tinker.loader.hotplug;

import java.util.HashMap;
import java.util.Map;

public class ActivityStubManager {
    private static final int[] NEXT_SINGLEINSTANCE_STUB_IDX_SLOTS = {0, 0};
    private static final int[] NEXT_SINGLETASK_STUB_IDX_SLOTS = {0, 0};
    private static final int[] NEXT_SINGLETOP_STUB_IDX_SLOTS = {0, 0};
    private static final int[] NEXT_STANDARD_STUB_IDX_SLOTS = {0, 0};
    private static final int NOTRANSPARENT_SLOT_INDEX = 0;
    private static final int[] SINGLEINSTANCE_STUB_COUNT_SLOTS = {10, 3};
    private static final int[] SINGLETASK_STUB_COUNT_SLOTS = {10, 3};
    private static final int[] SINGLETOP_STUB_COUNT_SLOTS = {10, 3};
    private static final int[] STANDARD_STUB_COUNT_SLOTS = {10, 3};
    private static final String TAG = "Tinker.ActivityStubManager";
    private static final int TRANSPARENT_SLOT_INDEX = 1;
    private static Map<String, String> sTargetToStubClassNameMap = new HashMap();

    public static String assignStub(String targetClassName, int launchMode, boolean isTransparent) {
        String stubNameFormat;
        int[] nextStubIdxSlots;
        int[] countSlots;
        int slotIdx;
        String stubClassName = (String) sTargetToStubClassNameMap.get(targetClassName);
        if (stubClassName != null) {
            return stubClassName;
        }
        switch (launchMode) {
            case 1:
                stubNameFormat = ActivityStubs.SINGLETOP_STUB_CLASSNAME_FORMAT;
                nextStubIdxSlots = NEXT_SINGLETOP_STUB_IDX_SLOTS;
                countSlots = SINGLETOP_STUB_COUNT_SLOTS;
                break;
            case 2:
                stubNameFormat = ActivityStubs.SINGLETASK_STUB_CLASSNAME_FORMAT;
                nextStubIdxSlots = NEXT_SINGLETASK_STUB_IDX_SLOTS;
                countSlots = SINGLETASK_STUB_COUNT_SLOTS;
                break;
            case 3:
                stubNameFormat = ActivityStubs.SINGLEINSTANCE_STUB_CLASSNAME_FORMAT;
                nextStubIdxSlots = NEXT_SINGLEINSTANCE_STUB_IDX_SLOTS;
                countSlots = SINGLEINSTANCE_STUB_COUNT_SLOTS;
                break;
            default:
                stubNameFormat = ActivityStubs.STARDARD_STUB_CLASSNAME_FORMAT;
                nextStubIdxSlots = NEXT_STANDARD_STUB_IDX_SLOTS;
                countSlots = STANDARD_STUB_COUNT_SLOTS;
                break;
        }
        if (isTransparent) {
            stubNameFormat = stubNameFormat + "_T";
            slotIdx = 1;
        } else {
            slotIdx = 0;
        }
        int stubIndex = nextStubIdxSlots[slotIdx];
        nextStubIdxSlots[slotIdx] = stubIndex + 1;
        if (stubIndex >= countSlots[slotIdx]) {
            stubIndex = 0;
            nextStubIdxSlots[slotIdx] = 0;
        }
        String stubClassName2 = String.format(stubNameFormat, new Object[]{Integer.valueOf(stubIndex)});
        sTargetToStubClassNameMap.put(targetClassName, stubClassName2);
        return stubClassName2;
    }

    private ActivityStubManager() {
        throw new UnsupportedOperationException();
    }
}
