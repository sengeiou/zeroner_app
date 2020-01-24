package com.iwown.device_module.device_setting.wifi_scale.data;

import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.holtek.libHTBodyfat.HTPeopleGeneral;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.weight.WeightData;
import com.iwown.data_link.weight.WifiScaleRWResp;
import com.iwown.data_link.weight.WifiScaleReq;
import com.iwown.data_link.weight.archive.AutoWeight;
import com.iwown.data_link.weight.archive.DataSet;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.T_Weight;
import com.iwown.device_module.common.sql.weight.TB_Weight;
import com.iwown.device_module.common.sql.weight.TB_WeightUser;
import com.iwown.device_module.common.sql.weight.TB_rawWeightData;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_setting.wifi_scale.bean.WifiScaleSetting;
import com.iwown.device_module.device_setting.wifi_scale.eventbus.EventbusFinish;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.device_module.device_setting.wifi_scale.util.WifiScaleSettingPresenter;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class WifiScaleData {
    private static WifiScaleData instance;
    WifiScaleSettingPresenter presenter = new WifiScaleSettingPresenter();

    private WifiScaleData() {
    }

    public static WifiScaleData getInstance() {
        if (instance == null) {
            instance = new WifiScaleData();
        }
        return instance;
    }

    public int getRwaWeightCounts(String mac) {
        return DataSupport.where("macaddr=?", mac).count(TB_rawWeightData.class);
    }

    public void saveRawWeight(WifiScaleRWResp resp) {
        float fWeight;
        if (resp.getData() == null) {
            EventbusFinish event = new EventbusFinish(9);
            event.setData(Integer.valueOf(0));
            EventBus.getDefault().post(event);
            DataSupport.deleteAll(TB_rawWeightData.class, new String[0]);
        } else if (resp.getRetCode() == 0) {
            List<WeightData> data = resp.getData();
            EventbusFinish event2 = new EventbusFinish(9);
            event2.setData(Integer.valueOf(data.size()));
            EventBus.getDefault().post(event2);
            DataSupport.deleteAll(TB_rawWeightData.class, new String[0]);
            for (int i = 0; i < data.size(); i++) {
                TB_rawWeightData rawData = new TB_rawWeightData();
                rawData.setMacaddr(((WeightData) data.get(i)).getMacaddr());
                rawData.setImpedance(((WeightData) data.get(i)).getImpedance());
                rawData.setUnit(((WeightData) data.get(i)).getUnit());
                rawData.setWeightid(((WeightData) data.get(i)).getWeightid());
                rawData.setWeightime(new DateUtil(((WeightData) data.get(i)).getWeightime(), true).getYyyyMMdd_HHmmssDate());
                rawData.setWeight(((WeightData) data.get(i)).getWeight());
                rawData.save();
                if (((WeightData) data.get(i)).getUnit() == 3) {
                    fWeight = ((WeightData) data.get(i)).getWeight() / 2.0f;
                } else if (((WeightData) data.get(i)).getUnit() == 1) {
                    fWeight = ((WeightData) data.get(i)).getWeight() * 0.4535924f;
                } else {
                    fWeight = ((WeightData) data.get(i)).getWeight();
                }
                WifiScaleSetting setting = this.presenter.wifiScaleSettingStatue();
                if (setting != null) {
                    if (setting.isAutomaticArchive()) {
                        calcWeight(fWeight, ((WeightData) data.get(i)).getWeightid(), data.size());
                    }
                    setting.setUnArchivedData(data.size());
                    this.presenter.saveWifiScaleStatue(setting);
                }
            }
        }
    }

    public void calcWeight(float weight, int weightId, int size) {
        ArrayList arrayList = new ArrayList();
        new ArrayList();
        List<TB_WeightUser> list = DataSupport.findAll(TB_WeightUser.class, new long[0]);
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(Long.valueOf(((TB_WeightUser) list.get(i)).getUid()));
        }
        if (arrayList.size() != 0) {
            HashMap hashMap = new HashMap();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                List<TB_Weight> tWeights = DataSupport.where("uid=?", String.valueOf(arrayList.get(i2))).order("timeStamp desc").limit(30).find(TB_Weight.class);
                ArrayList arrayList2 = new ArrayList();
                for (int j = 0; j < tWeights.size(); j++) {
                    DataSet d1 = new DataSet();
                    if (((TB_Weight) tWeights.get(j)).getWeight() != 0.0f) {
                        d1.setWeight((double) ((TB_Weight) tWeights.get(j)).getWeight());
                        d1.setBmi(((TB_Weight) tWeights.get(j)).getHtBMI());
                        d1.setBodyFat(((TB_Weight) tWeights.get(j)).getHtBodyfatPercentage());
                        d1.setBoneWeight(((TB_Weight) tWeights.get(j)).getHtBoneKg());
                        d1.setCalorie((double) ((TB_Weight) tWeights.get(j)).getHtBMR());
                        d1.setMuscule(((TB_Weight) tWeights.get(j)).getHtMuscleKg());
                        d1.setImpedance((double) ((TB_Weight) tWeights.get(j)).getBodyImpedance());
                        d1.setWater(((TB_Weight) tWeights.get(j)).getHtWaterPercentage());
                        d1.setVisceralFat((double) ((TB_Weight) tWeights.get(j)).getHtVFAL());
                        arrayList2.add(d1);
                    }
                }
                if (arrayList2.size() != 0) {
                    hashMap.put(arrayList.get(i2), arrayList2);
                }
            }
            HashMap hashMap2 = new HashMap();
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                TB_WeightUser personal = (TB_WeightUser) DataSupport.where("uid=?", String.valueOf(arrayList.get(i3))).findFirst(TB_WeightUser.class);
                if (personal != null) {
                    float height = personal.getHeight();
                    Calendar calendar = null;
                    int year = 0;
                    try {
                        Date date = DateUtil.String2Date("yyyy-MM-dd", personal.getBirthday());
                        calendar = Calendar.getInstance();
                        year = calendar.get(1);
                        calendar.setTime(date);
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    int age = (year - calendar.get(1)) + 1;
                    int sex = personal.getGender();
                    KLog.i("hzy", age + "age" + height + "height");
                    TB_rawWeightData data = null;
                    float fWeight = 0.0f;
                    try {
                        data = (TB_rawWeightData) DataSupport.where("weightid=?", String.valueOf(weightId)).findLast(TB_rawWeightData.class);
                        fWeight = 0.0f;
                        if (data.getUnit() == 3) {
                            fWeight = data.getWeight() / 2.0f;
                        } else if (data.getUnit() == 1) {
                            fWeight = data.getWeight() * 0.4535924f;
                        } else {
                            fWeight = data.getWeight();
                        }
                    } catch (Exception e2) {
                        ThrowableExtension.printStackTrace(e2);
                    }
                    float f1 = new BigDecimal((double) fWeight).setScale(1, 4).floatValue();
                    if (data.getImpedance() > 0) {
                        HTPeopleGeneral bodyfat = new HTPeopleGeneral((double) fWeight, (double) height, sex, age, data.getImpedance());
                        int bodyfatParameters = bodyfat.getBodyfatParameters();
                        KLog.i("阻抗:" + bodyfat.htZTwoLegs + "Ω  BMI:" + String.format("%.1f", new Object[]{Double.valueOf(bodyfat.htBMI)}) + "  BMR:" + bodyfat.htBMR + "  内脏脂肪:" + bodyfat.htVFAL + "  骨量:" + String.format("%.1fkg", new Object[]{Double.valueOf(bodyfat.htBoneKg)}) + "  脂肪率:" + String.format("%.1f%%", new Object[]{Double.valueOf(bodyfat.htBodyfatPercentage)}) + "  水分:" + String.format("%.1f%%", new Object[]{Double.valueOf(bodyfat.htWaterPercentage)}) + "  肌肉:" + String.format("%.1fkg", new Object[]{Double.valueOf(bodyfat.htMuscleKg)}) + "  体年龄:" + bodyfat.htBodyAge + "  蛋白质:" + String.format("%.1f%%", new Object[]{Double.valueOf(bodyfat.htProteinPercentage)}) + "\r\n");
                        DataSet dataSet = new DataSet();
                        if (f1 != 0.0f) {
                            dataSet.setWeight((double) f1);
                            dataSet.setVisceralFat((double) bodyfat.htVFAL);
                            dataSet.setImpedance(bodyfat.htZTwoLegs);
                            dataSet.setWater(bodyfat.htWaterPercentage);
                            dataSet.setMuscule(bodyfat.htMuscleKg);
                            dataSet.setBoneWeight(bodyfat.htBoneKg);
                            dataSet.setBmi(bodyfat.htBMI);
                            dataSet.setCalorie((double) bodyfat.htBMR);
                            dataSet.setBodyFat(bodyfat.htBodyfatPercentage);
                            hashMap2.put(arrayList.get(i3), dataSet);
                        }
                    } else {
                        DataSet dataSet2 = new DataSet();
                        if (f1 != 0.0f) {
                            dataSet2.setWeight((double) f1);
                            dataSet2.setVisceralFat(Utils.DOUBLE_EPSILON);
                            dataSet2.setImpedance(Utils.DOUBLE_EPSILON);
                            dataSet2.setWater(Utils.DOUBLE_EPSILON);
                            dataSet2.setMuscule(Utils.DOUBLE_EPSILON);
                            dataSet2.setBoneWeight(Utils.DOUBLE_EPSILON);
                            dataSet2.setBmi(Utils.DOUBLE_EPSILON);
                            dataSet2.setCalorie(Utils.DOUBLE_EPSILON);
                            dataSet2.setBodyFat(Utils.DOUBLE_EPSILON);
                            hashMap2.put(arrayList.get(i3), dataSet2);
                        }
                    }
                }
            }
            AutoWeight aw = new AutoWeight();
            KLog.i("weight:" + weight + "=============" + weightId + "==================================================" + new Gson().toJson((Object) hashMap));
            KLog.e(JsonUtils.toJson(hashMap));
            KLog.e(JsonUtils.toJson(hashMap2));
            Long uid = aw.getMinDis(hashMap, hashMap2);
            if (uid == null) {
                KLog.i("null:===================================================================================" + uid);
                return;
            }
            KLog.i("老张返回的UID:===================================================================================" + uid);
            WifiScaleReq reqBody = new WifiScaleReq();
            reqBody.setUid(uid.longValue());
            reqBody.setWeightid(weightId);
            NetFactory.getInstance().getClient(new MyCallback() {
                public void onSuccess(Object o) {
                }

                public void onFail(Throwable e) {
                }
            }).archiveData(reqBody, size);
        }
    }

    public void saveArchiveData(WifiScaleReq body, int retCode) {
        if (retCode == 0) {
            TB_WeightUser personal = (TB_WeightUser) DataSupport.where("uid=?", String.valueOf(body.getUid())).findFirst(TB_WeightUser.class);
            if (personal != null) {
                float height = personal.getHeight();
                Date date = DateUtil.String2Date("yyyy-MM-dd", personal.getBirthday());
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(1);
                calendar.setTime(date);
                int age = (year - calendar.get(1)) + 1;
                int sex = personal.getGender();
                KLog.i("hzy", age + "age" + height + "height");
                TB_rawWeightData data = null;
                float fWeight = 0.0f;
                try {
                    data = (TB_rawWeightData) DataSupport.where("weightid=?", String.valueOf(body.getWeightid())).findLast(TB_rawWeightData.class);
                    fWeight = 0.0f;
                    if (data.getUnit() == 3) {
                        fWeight = data.getWeight() / 2.0f;
                    } else if (data.getUnit() == 1) {
                        fWeight = data.getWeight() * 0.4535924f;
                    } else {
                        fWeight = data.getWeight();
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                TB_Weight weight = new TB_Weight();
                weight.setUid(body.getUid());
                weight.setAddress(data.getMacaddr());
                float f1 = new BigDecimal((double) fWeight).setScale(1, 4).floatValue();
                weight.setWeight(f1);
                weight.setsWeight(String.valueOf(f1));
                weight.setWeightUnit(data.getUnit());
                weight.setDate(data.getWeightime());
                weight.setTimeStamp(DateUtil.String2Date("yyyy-MM-dd HH:mm:ss", data.getWeightime()).getTime());
                if (data.getImpedance() > 0) {
                    HTPeopleGeneral bodyfat = new HTPeopleGeneral((double) fWeight, (double) height, sex, age, data.getImpedance());
                    int bodyfatParameters = bodyfat.getBodyfatParameters();
                    KLog.i("阻抗:" + bodyfat.htZTwoLegs + "Ω  BMI:" + String.format("%.1f", new Object[]{Double.valueOf(bodyfat.htBMI)}) + "  BMR:" + bodyfat.htBMR + "  内脏脂肪:" + bodyfat.htVFAL + "  骨量:" + String.format("%.1fkg", new Object[]{Double.valueOf(bodyfat.htBoneKg)}) + "  脂肪率:" + String.format("%.1f%%", new Object[]{Double.valueOf(bodyfat.htBodyfatPercentage)}) + "  水分:" + String.format("%.1f%%", new Object[]{Double.valueOf(bodyfat.htWaterPercentage)}) + "  肌肉:" + String.format("%.1fkg", new Object[]{Double.valueOf(bodyfat.htMuscleKg)}) + "  体年龄:" + bodyfat.htBodyAge + "  蛋白质:" + String.format("%.1f%%", new Object[]{Double.valueOf(bodyfat.htProteinPercentage)}) + "\r\n");
                    weight.setHtBMI(bodyfat.htBMI);
                    weight.setHtBodyfatPercentage(bodyfat.htBodyfatPercentage);
                    weight.setHtBoneKg(bodyfat.htBoneKg);
                    weight.setHtWaterPercentage(bodyfat.htWaterPercentage);
                    weight.setHtMuscleKg(bodyfat.htMuscleKg);
                    weight.setHtVFAL(bodyfat.htVFAL);
                    weight.setHtBMR(bodyfat.htBMR);
                    weight.setBodyImpedance((int) bodyfat.htZTwoLegs);
                } else {
                    TB_Weight tb_weights = (TB_Weight) DataSupport.where("uid=? ", ContextUtil.getUID() + "").order("timeStamp desc").findFirst(TB_Weight.class);
                    weight.setHtBMI(Utils.DOUBLE_EPSILON);
                    if (tb_weights != null) {
                        weight.setHtBMI(tb_weights.getHtBMI());
                    }
                    weight.setHtBodyfatPercentage(Utils.DOUBLE_EPSILON);
                    weight.setHtBoneKg(Utils.DOUBLE_EPSILON);
                    weight.setHtWaterPercentage(Utils.DOUBLE_EPSILON);
                    weight.setHtMuscleKg(Utils.DOUBLE_EPSILON);
                    weight.setHtVFAL(0);
                    weight.setHtBMR(0);
                    weight.setBodyImpedance(0);
                }
                weight.saveOrUpdate("uid=? and timestamp=?", String.valueOf(body.getUid()), String.valueOf(date.getTime()));
                uploadScaleData(weight);
                EventBus.getDefault().post(new EventbusFinish(12));
                EventbusFinish eventbusFinish = new EventbusFinish(15);
                eventbusFinish.setData(String.valueOf(f1));
                EventBus.getDefault().post(eventbusFinish);
            }
            int deleteAll = DataSupport.deleteAll(TB_rawWeightData.class, "weightid=?", String.valueOf(body.getWeightid()));
            int index = DataSupport.where("macaddr=? ", S2WifiUtils.wifiScaleMac(ContextUtil.getUID())).count(TB_rawWeightData.class);
            WifiScaleSetting setting = this.presenter.wifiScaleSettingStatue();
            if (setting != null) {
                setting.setUnArchivedData(index);
                this.presenter.saveWifiScaleStatue(setting);
            }
            EventbusFinish ev1 = new EventbusFinish();
            ev1.setAction(8);
            ev1.setData(Long.valueOf(body.getUid()));
            EventBus.getDefault().post(ev1);
            HealthDataEventBus.updateHealthWeightEvent();
        } else if (retCode == 70002) {
            DataSupport.deleteAll(TB_rawWeightData.class, "weightid=?", String.valueOf(body.getWeightid()));
            EventBus.getDefault().post(new EventbusFinish(4));
        }
    }

    public void uploadScaleData(TB_Weight weight) {
        List<T_Weight> body = new ArrayList<>();
        T_Weight t_weight = new T_Weight();
        t_weight.setUid(weight.getUid());
        t_weight.setWeight(weight.getWeight());
        t_weight.setBmi((float) weight.getHtBMI());
        t_weight.setWater((float) weight.getHtWaterPercentage());
        t_weight.setBodyfat((float) weight.getHtBodyfatPercentage());
        t_weight.setBone_weight((float) weight.getHtBoneKg());
        t_weight.setMuscule((float) weight.getHtMuscleKg());
        t_weight.setCalorie((float) weight.getHtBMR());
        t_weight.setVisceral_fat(weight.getHtVFAL());
        t_weight.setRecord_date(weight.getTimeStamp() / 1000);
        t_weight.setData_from(weight.getAddress());
        t_weight.setImpedance((float) weight.getBodyImpedance());
        body.add(t_weight);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
            }

            public void onFail(Throwable e) {
            }
        }).upScaleData(weight.getUid(), body);
    }

    public int userCount() {
        return DataSupport.count(TB_WeightUser.class);
    }
}
