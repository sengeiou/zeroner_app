package com.github.mikephil.charting.utils;

import android.content.res.Resources;
import android.graphics.Color;
import com.tencent.bugly.beta.tinker.TinkerReport;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.List;

public class ColorTemplate {
    public static final int[] COLORFUL_COLORS = {Color.rgb(Opcodes.OR_LONG_2ADDR, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, Opcodes.SUB_FLOAT_2ADDR, 0), Color.rgb(106, 150, 31), Color.rgb(Opcodes.DIV_INT_2ADDR, 100, 53)};
    public static final int COLOR_NONE = 1122867;
    public static final int COLOR_SKIP = 1122868;
    public static final int[] JOYFUL_COLORS = {Color.rgb(Opcodes.RSUB_INT_LIT8, 80, Opcodes.DOUBLE_TO_INT), Color.rgb(TinkerReport.KEY_LOADED_EXCEPTION_RESOURCE, Opcodes.AND_INT, 7), Color.rgb(TinkerReport.KEY_LOADED_EXCEPTION_RESOURCE, 247, 120), Color.rgb(106, Opcodes.SUB_FLOAT, Opcodes.LONG_TO_DOUBLE), Color.rgb(53, Opcodes.XOR_LONG_2ADDR, 209)};
    public static final int[] LIBERTY_COLORS = {Color.rgb(207, 248, 246), Color.rgb(Opcodes.REM_INT, Opcodes.REM_INT_LIT16, Opcodes.REM_INT_LIT16), Color.rgb(Opcodes.FLOAT_TO_LONG, 180, Opcodes.ADD_LONG_2ADDR), Color.rgb(118, Opcodes.DIV_DOUBLE, Opcodes.REM_DOUBLE), Color.rgb(42, 109, Opcodes.INT_TO_FLOAT)};
    public static final int[] MATERIAL_COLORS = {rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db")};
    public static final int[] PASTEL_COLORS = {Color.rgb(64, 89, 128), Color.rgb(Opcodes.AND_INT, Opcodes.USHR_LONG, 124), Color.rgb(Opcodes.RSUB_INT_LIT8, 184, Opcodes.XOR_LONG), Color.rgb(Opcodes.REM_LONG_2ADDR, Opcodes.LONG_TO_DOUBLE, Opcodes.LONG_TO_DOUBLE), Color.rgb(Opcodes.DIV_INT_2ADDR, 48, 80)};
    public static final int[] VORDIPLOM_COLORS = {Color.rgb(Opcodes.AND_LONG_2ADDR, 255, Opcodes.DOUBLE_TO_FLOAT), Color.rgb(255, 247, Opcodes.DOUBLE_TO_FLOAT), Color.rgb(255, 208, Opcodes.DOUBLE_TO_FLOAT), Color.rgb(Opcodes.DOUBLE_TO_FLOAT, 234, 255), Color.rgb(255, Opcodes.DOUBLE_TO_FLOAT, 157)};

    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        return Color.rgb((color >> 16) & 255, (color >> 8) & 255, (color >> 0) & 255);
    }

    public static int getHoloBlue() {
        return Color.rgb(51, 181, 229);
    }

    public static int colorWithAlpha(int color, int alpha) {
        return (16777215 & color) | ((alpha & 255) << 24);
    }

    public static List<Integer> createColors(Resources r, int[] colors) {
        List<Integer> result = new ArrayList<>();
        for (int i : colors) {
            result.add(Integer.valueOf(r.getColor(i)));
        }
        return result;
    }

    public static List<Integer> createColors(int[] colors) {
        List<Integer> result = new ArrayList<>();
        for (int i : colors) {
            result.add(Integer.valueOf(i));
        }
        return result;
    }
}
