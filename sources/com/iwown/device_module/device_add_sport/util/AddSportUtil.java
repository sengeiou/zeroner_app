package com.iwown.device_module.device_add_sport.util;

import android.annotation.SuppressLint;
import com.iwown.device_module.R;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.HashMap;
import java.util.Map;

public class AddSportUtil {
    @SuppressLint({"UseSparseArrays"})
    public static int getSporyImgOrName(int k, int sportType) {
        Map<Integer, Integer[]> sportMap = new HashMap<>();
        sportMap.put(Integer.valueOf(1), new Integer[]{Integer.valueOf(R.string.sport_module_walking), Integer.valueOf(R.mipmap.walk_off_3x), Integer.valueOf(R.mipmap.walk_on_3x)});
        sportMap.put(Integer.valueOf(2), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_situp), Integer.valueOf(R.mipmap.situps_off_3x), Integer.valueOf(R.mipmap.situps_on_3x)});
        sportMap.put(Integer.valueOf(3), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_pushup), Integer.valueOf(R.mipmap.pushups_off_3x), Integer.valueOf(R.mipmap.pushups_on_3x)});
        sportMap.put(Integer.valueOf(4), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_jump), Integer.valueOf(R.mipmap.rope_skipping_off_3x), Integer.valueOf(R.mipmap.rope_skipping_on_3x)});
        sportMap.put(Integer.valueOf(5), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_mountaineering), Integer.valueOf(R.mipmap.climbing_off_3x), Integer.valueOf(R.mipmap.climbing_on_3x)});
        sportMap.put(Integer.valueOf(6), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_pullup), Integer.valueOf(R.mipmap.pullups_off_3x), Integer.valueOf(R.mipmap.pullups_on_3x)});
        sportMap.put(Integer.valueOf(7), new Integer[]{Integer.valueOf(R.string.sport_module_running), Integer.valueOf(R.mipmap.run_off_3x), Integer.valueOf(R.mipmap.run_on_3x)});
        sportMap.put(Integer.valueOf(8), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_hi_low), Integer.valueOf(R.mipmap.hi_low_off_3x), Integer.valueOf(R.mipmap.hi_low_on_3x)});
        sportMap.put(Integer.valueOf(128), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_badminton), Integer.valueOf(R.mipmap.badminton_off_3x), Integer.valueOf(R.mipmap.badminton_on_3x)});
        sportMap.put(Integer.valueOf(129), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_basketball), Integer.valueOf(R.mipmap.basketball_off_3x), Integer.valueOf(R.mipmap.basketball_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_FLOAT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_football), Integer.valueOf(R.mipmap.football_off_3x), Integer.valueOf(R.mipmap.football_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_DOUBLE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_swimming), Integer.valueOf(R.mipmap.swimming_off_3x), Integer.valueOf(R.mipmap.swimming_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.LONG_TO_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_volleyball), Integer.valueOf(R.mipmap.volleyball_off_3x), Integer.valueOf(R.mipmap.volleyball_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.LONG_TO_FLOAT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_pingpong), Integer.valueOf(R.mipmap.table_tennis_off_3x), Integer.valueOf(R.mipmap.table_tennis_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.LONG_TO_DOUBLE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_bowling), Integer.valueOf(R.mipmap.bowling_off_3x), Integer.valueOf(R.mipmap.bowling_on_3x)});
        sportMap.put(Integer.valueOf(135), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_tennis), Integer.valueOf(R.mipmap.tennis_off_3x), Integer.valueOf(R.mipmap.tennis_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.FLOAT_TO_LONG), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_cycling), Integer.valueOf(R.mipmap.bike_off_3x), Integer.valueOf(R.mipmap.bike_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.FLOAT_TO_DOUBLE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_skee), Integer.valueOf(R.mipmap.ski_off_3x), Integer.valueOf(R.mipmap.ski_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_skate), Integer.valueOf(R.mipmap.skate_off_3x), Integer.valueOf(R.mipmap.skate_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_LONG), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_climbing), Integer.valueOf(R.mipmap.rock_climbing_off_3x), Integer.valueOf(R.mipmap.rock_climbing_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_FLOAT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_gymnasium), Integer.valueOf(R.mipmap.fitness_off_3x), Integer.valueOf(R.mipmap.fitness_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_BYTE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_dance), Integer.valueOf(R.mipmap.dance_off_3x), Integer.valueOf(R.mipmap.dance_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_CHAR), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_slap), Integer.valueOf(R.mipmap.plank_off_3x), Integer.valueOf(R.mipmap.plank_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_SHORT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_bodymechanics), Integer.valueOf(R.mipmap.aerobics_off_3x), Integer.valueOf(R.mipmap.aerobics_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.ADD_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_yoga), Integer.valueOf(R.mipmap.yoga_off_3x), Integer.valueOf(R.mipmap.yoga_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.SUB_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_shuttlecock), Integer.valueOf(R.mipmap.shuttlecock_off_3x), Integer.valueOf(R.mipmap.shuttlecock_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.MUL_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_ball_game), Integer.valueOf(R.mipmap.ball_game_off), Integer.valueOf(R.mipmap.ball_games_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.DIV_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_speed_walking), Integer.valueOf(R.mipmap.walk_off_3x), Integer.valueOf(R.mipmap.walk_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.REM_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_golf), Integer.valueOf(R.mipmap.golf_off_3x), Integer.valueOf(R.mipmap.golf_on_3x)});
        sportMap.put(Integer.valueOf(Opcodes.AND_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_canoeing), Integer.valueOf(R.mipmap.canoeing_off_3x), Integer.valueOf(R.mipmap.canoeing_on_3x)});
        sportMap.put(Integer.valueOf(150), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_bodymechanics), Integer.valueOf(R.mipmap.aerobics_off_3x), Integer.valueOf(R.mipmap.aerobics_on_3x)});
        sportMap.put(Integer.valueOf(4096), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_treadmill), Integer.valueOf(R.mipmap.treadmill_off_3x), Integer.valueOf(R.mipmap.treadmill_on_3x)});
        sportMap.put(Integer.valueOf(4097), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_spinning), Integer.valueOf(R.mipmap.spinning_off_3x), Integer.valueOf(R.mipmap.spinning_on_3x)});
        for (Integer integer : sportMap.keySet()) {
            if (integer.intValue() == sportType) {
                return ((Integer[]) sportMap.get(integer))[k].intValue();
            }
        }
        return -1;
    }
}
