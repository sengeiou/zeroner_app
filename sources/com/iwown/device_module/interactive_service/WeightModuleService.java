package com.iwown.device_module.interactive_service;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.weight.IWeightService;
import com.iwown.data_link.weight.ScaleBodyFat;
import com.iwown.data_link.weight.ScaleDataResp;
import com.iwown.data_link.weight.WeightUser;
import com.iwown.data_link.weight.WifiScaleRWResp;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.sql.weight.TB_S2WifiConfig;
import com.iwown.device_module.common.sql.weight.TB_Weight;
import com.iwown.device_module.common.sql.weight.TB_WeightUser;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_setting.wifi_scale.data.WifiScaleData;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.toast.ToastUtils;
import com.socks.library.KLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

@Route(path = "/device/weight_service")
public class WeightModuleService implements IWeightService {
    public void saveNetWeight(ScaleDataResp scaleDataResp) {
        List<ScaleBodyFat> content = scaleDataResp.getContent();
        for (int i = 0; i < content.size(); i++) {
            long uid = ((ScaleBodyFat) content.get(i)).getUid();
            TB_Weight weight = new TB_Weight();
            weight.setUid(((ScaleBodyFat) content.get(i)).getUid());
            weight.setAddress(((ScaleBodyFat) content.get(i)).getData_from());
            float f1 = new BigDecimal((double) ((ScaleBodyFat) content.get(i)).getWeight()).setScale(1, 4).floatValue();
            weight.setWeight(f1);
            weight.setsWeight(String.valueOf(f1));
            weight.setTimeStamp(((ScaleBodyFat) content.get(i)).getRecord_date() * 1000);
            weight.setHtBMI((double) ((ScaleBodyFat) content.get(i)).getBmi());
            weight.setHtBodyfatPercentage((double) ((ScaleBodyFat) content.get(i)).getBodyfat());
            weight.setHtBoneKg((double) ((ScaleBodyFat) content.get(i)).getBone_weight());
            weight.setHtWaterPercentage((double) ((ScaleBodyFat) content.get(i)).getWater());
            weight.setHtMuscleKg((double) ((ScaleBodyFat) content.get(i)).getMuscule());
            weight.setHtVFAL(((ScaleBodyFat) content.get(i)).getVisceral_fat());
            weight.setHtBMR((int) ((ScaleBodyFat) content.get(i)).getCalorie());
            weight.setBodyImpedance((int) ((ScaleBodyFat) content.get(i)).getImpedance());
            weight.setDate(new DateUtil(((ScaleBodyFat) content.get(i)).getRecord_date(), true).getY_M_D_H_M_S());
            weight.saveOrUpdate("uid=? and timestamp=?", String.valueOf(uid), String.valueOf(weight.getTimeStamp()));
        }
    }

    public List<ScaleBodyFat> getLocalDataSizeDay(long uid, int daySize) {
        DateUtil dateUtil = new DateUtil();
        dateUtil.setHour(23);
        dateUtil.setMinute(59);
        dateUtil.setSecond(59);
        long timestamp_end = dateUtil.getTimestamp();
        dateUtil.setHour(0);
        dateUtil.setMinute(0);
        dateUtil.setSecond(0);
        dateUtil.addDay(-daySize);
        KLog.e(uid + "    " + dateUtil.getTimestamp() + "  " + timestamp_end);
        List<TB_Weight> tb_weights = DataSupport.where("uid=? ", uid + "").order("timeStamp desc").limit(800).find(TB_Weight.class);
        List<ScaleBodyFat> scaleBodyFatList = new ArrayList<>();
        for (TB_Weight tb_weight : tb_weights) {
            ScaleBodyFat scaleBodyFat = new ScaleBodyFat();
            scaleBodyFat.setBmi((float) tb_weight.getHtBMI());
            scaleBodyFat.setBodyfat((float) tb_weight.getHtBodyfatPercentage());
            scaleBodyFat.setMuscule((float) tb_weight.getHtMuscleKg());
            scaleBodyFat.setWater((float) tb_weight.getHtWaterPercentage());
            scaleBodyFat.setBone_weight((float) tb_weight.getHtBoneKg());
            scaleBodyFat.setVisceral_fat(tb_weight.getHtVFAL());
            scaleBodyFat.setCalorie((float) tb_weight.getHtBMR());
            scaleBodyFat.setUid(tb_weight.getUid());
            scaleBodyFat.setData_from(tb_weight.getAddress());
            scaleBodyFat.setRecord_date(tb_weight.getTimeStamp() / 1000);
            scaleBodyFat.setWeight(tb_weight.getWeight());
            scaleBodyFatList.add(scaleBodyFat);
        }
        KLog.e("scaleBodyFatList " + scaleBodyFatList);
        return scaleBodyFatList;
    }

    public void saveS2WifiConfig(long uid, String config_wifi_name, String config_wifi_pwd) {
        TB_S2WifiConfig last = (TB_S2WifiConfig) DataSupport.where("uid=? ", uid + "").findLast(TB_S2WifiConfig.class);
        if (last == null) {
            ToastUtils.showShortToast((CharSequence) "error s2wifi mac is null");
            return;
        }
        last.setConfig_wifi_name(config_wifi_name);
        last.setConfig_wifi_pwd(config_wifi_pwd);
        last.save();
    }

    public void updateMac(long uid, String mac) {
        TB_S2WifiConfig last = (TB_S2WifiConfig) DataSupport.where("uid=? ", uid + "").findLast(TB_S2WifiConfig.class);
        if (last == null) {
            last = new TB_S2WifiConfig();
        }
        last.setUid(uid);
        last.setMac(mac);
        last.save();
        NetFactory.getInstance().getClient(null).scaleSetUnit(2, S2WifiUtils.wifiScaleMac(ContextUtil.getUID()));
    }

    public void saveNetRWWeight(WifiScaleRWResp wifiScaleRWResp) {
        WifiScaleData.getInstance().saveRawWeight(wifiScaleRWResp);
    }

    public int getRawWeightCounts(String mac) {
        return WifiScaleData.getInstance().getRwaWeightCounts(mac);
    }

    public String getScaleMac(long newUID) {
        return S2WifiUtils.wifiScaleMac(ContextUtil.getUID());
    }

    public List<WeightUser> getWeightUsers(long newUID) {
        List<TB_WeightUser> all = DataSupport.findAll(TB_WeightUser.class, new long[0]);
        List<WeightUser> weightUsers = new ArrayList<>();
        for (TB_WeightUser user : all) {
            WeightUser weightUser = new WeightUser();
            weightUser.setName(user.getName());
            weightUser.setUid(user.getUid());
            weightUser.setHeight(user.getHeight());
            int age = 25;
            try {
                String[] split = user.getBirthday().split(HelpFormatter.DEFAULT_OPT_PREFIX);
                age = DateUtil.getAgeByBirthday(new Date(new DateUtil(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])).getTimestamp()));
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            weightUser.setAge(age);
            weightUser.setMale(user.getGender() != 2);
            weightUsers.add(weightUser);
        }
        return weightUsers;
    }

    public void getNetWeightUsers(long newUID) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
            }

            public void onFail(Throwable e) {
            }
        }).getNoAccountList();
    }

    public void init(Context context) {
    }
}
