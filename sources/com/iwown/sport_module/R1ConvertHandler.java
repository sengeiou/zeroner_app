package com.iwown.sport_module;

import android.os.Environment;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.heart.HeartData;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.R1DataBean;
import com.iwown.data_link.sport_data.R1_68_data;
import com.iwown.data_link.sport_data.gps.HrUpData;
import com.iwown.data_link.sport_data.gps.HrUpData.Hr51;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.file.FileIOUtils;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.sport_module.contract.R1DataPresenter;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.pojo.R1UpdateBean;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.view.run.DlineDataBean;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class R1ConvertHandler {
    public static void tb68ToConvertHistory(R1DataBean r1DataBean) {
        KLog.e("TB68DATA", "开始同步68history数据.............");
        if (r1DataBean != null && "R1TableConvert".equals(r1DataBean.getTag())) {
            try {
                List<R1_68_data> r1_68_datas = ModuleRouteSportService.getInstance().get68Data(0, 0, 0, UserConfig.getInstance().getDevice(), UserConfig.getInstance().getNewUID());
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                List<Integer> heartLists = new ArrayList<>();
                if (r1_68_datas != null && r1_68_datas.size() > 0) {
                    long startTime = 0;
                    float distance = 0.0f;
                    float calorie = 0.0f;
                    int touchdown_b = 0;
                    boolean isSport = false;
                    boolean isDiscard = false;
                    for (R1_68_data r1_68_data : r1_68_datas) {
                        TB_location_history history = new TB_location_history();
                        if (r1_68_data.getState_type() == 1) {
                            isDiscard = false;
                            startTime = r1_68_data.getTime();
                            distance += r1_68_data.getDistance();
                            calorie += r1_68_data.getCalorie();
                        } else if (r1_68_data.getState_type() == 4) {
                            isSport = true;
                            distance += r1_68_data.getDistance();
                            calorie += r1_68_data.getCalorie();
                            arrayList.add(new DlineDataBean(r1_68_data.getTime() / 1000, (float) r1_68_data.getRateOfStride_avg()));
                            arrayList2.add(new DlineDataBean(r1_68_data.getTime() / 1000, (float) r1_68_data.getTouchDown_avg()));
                            arrayList3.add(new DlineDataBean(r1_68_data.getTime() / 1000, Util.doubleToFloat(1, (double) r1_68_data.getFlight_avg())));
                            if (r1_68_data.getDistance() > 0.0f) {
                                arrayList4.add(new DlineDataBean(r1_68_data.getTime() / 1000, Util.doubleToFloat(2, (double) (1.0f / (r1_68_data.getDistance() / 1000.0f)))));
                            } else {
                                arrayList4.add(new DlineDataBean(r1_68_data.getTime() / 1000, 0.0f));
                            }
                            heartLists.add(Integer.valueOf(r1_68_data.getAvg_hr()));
                            touchdown_b += r1_68_data.getTouchDownPower_balance();
                        } else if (r1_68_data.getState_type() == 0) {
                            heartLists.add(Integer.valueOf(r1_68_data.getAvg_hr()));
                        } else if (r1_68_data.getState_type() == 7 || r1_68_data.getState_type() == 5) {
                            isDiscard = true;
                        } else if (r1_68_data.getState_type() == 2 || r1_68_data.getState_type() == 6) {
                            long endTime = r1_68_data.getTime();
                            distance += r1_68_data.getDistance();
                            calorie += r1_68_data.getCalorie();
                            if (endTime < startTime || !isSport || startTime == 0) {
                                arrayList.clear();
                                arrayList2.clear();
                                arrayList3.clear();
                                arrayList4.clear();
                                heartLists.clear();
                            } else {
                                isSport = false;
                                if (((endTime - startTime) / 1000 >= 300 || distance > 500.0f) && calorie != 0.0f && !isDiscard) {
                                    history.setTime((int) ((endTime - startTime) / 1000));
                                    history.setDistance(distance);
                                    history.setCalorie(calorie);
                                    history.setTime_id(startTime / 1000);
                                    history.setEnd_time(endTime / 1000);
                                    history.setIs_upload(0);
                                    history.setData_type(2);
                                    history.setData_from(r1_68_data.getData_from());
                                    history.setSport_type(3);
                                    history.setUid(UserConfig.getInstance().getNewUID());
                                    String hearts = JsonTool.toJson(heartLists);
                                    String steps = JsonTool.toJson(arrayList);
                                    String earths = JsonTool.toJson(arrayList2);
                                    String skys = JsonTool.toJson(arrayList3);
                                    String speeds = JsonTool.toJson(arrayList4);
                                    history.setAvg_hr(hearts);
                                    history.setRateOfStride_avg(steps);
                                    history.setTouchDown_avg(earths);
                                    history.setFlight_avg(skys);
                                    history.setSpeedList(speeds);
                                    if (arrayList3.size() == 0) {
                                        history.setTouchDownPower_balance(touchdown_b);
                                    } else {
                                        history.setTouchDownPower_balance(touchdown_b / arrayList3.size());
                                    }
                                    saveR1Data(history);
                                    R1DataPresenter presenter = new R1DataPresenter();
                                    com.iwown.sport_module.pojo.R1DataBean r1DataBean1 = presenter.initHistoryData(history);
                                    HeartData heartData = presenter.initHrData(r1DataBean1, history.getData_from(), heartLists, history.getTime_id() * 1000, history.getEnd_time() * 1000);
                                    saveAndR1Data(r1DataBean1, history.getUid(), history.getTime_id(), history.getEnd_time(), history.getData_from());
                                    saveAndHrData(heartData, history.getUid(), history.getTime_id(), history.getEnd_time(), history.getData_from());
                                }
                                startTime = 0;
                                distance = 0.0f;
                                calorie = 0.0f;
                                touchdown_b = 0;
                                arrayList.clear();
                                arrayList2.clear();
                                arrayList3.clear();
                                arrayList4.clear();
                                heartLists.clear();
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static void gpsAndR1Disp(R1DataBean r1DataBean) {
        if (r1DataBean != null && "R1TableConvert".equals(r1DataBean.getTag())) {
            List<CopySportGps> his = ModuleRouteSportService.getInstance().getR1AndGpsSegment(UserConfig.getInstance().getNewUID(), "Android&" + UserConfig.getInstance().getDevice());
            ArrayList arrayList = new ArrayList();
            List<DlineDataBean> earthLists = new ArrayList<>();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (int i = 0; i < his.size(); i++) {
                long startTime = 0;
                float distance = 0.0f;
                float calorie = 0.0f;
                int touchdown_b = 0;
                boolean isHasStartEnd = false;
                List<R1_68_data> r1_68_datas = ModuleRouteSportService.getInstance().get68Data(UserConfig.getInstance().getDevice(), UserConfig.getInstance().getNewUID(), ((CopySportGps) his.get(i)).getStart_time() * 1000, ((CopySportGps) his.get(i)).getEnd_time() * 1000);
                for (int j = 0; j < r1_68_datas.size(); j++) {
                    TB_location_history history = new TB_location_history();
                    R1_68_data r1_68_data = (R1_68_data) r1_68_datas.get(j);
                    if (j == 0) {
                        startTime = r1_68_data.getTime();
                    }
                    if (r1_68_data.getState_type() == 4) {
                        distance += r1_68_data.getDistance();
                        calorie += r1_68_data.getCalorie();
                        arrayList.add(new DlineDataBean(r1_68_data.getTime() / 1000, (float) r1_68_data.getRateOfStride_avg()));
                        earthLists.add(new DlineDataBean(r1_68_data.getTime() / 1000, (float) r1_68_data.getTouchDown_avg()));
                        arrayList2.add(new DlineDataBean(r1_68_data.getTime() / 1000, Util.doubleToFloat(1, (double) r1_68_data.getFlight_avg())));
                        if (r1_68_data.getDistance() > 0.0f) {
                            arrayList3.add(new DlineDataBean(r1_68_data.getTime() / 1000, Util.doubleToFloat(2, (double) (1.0f / (r1_68_data.getDistance() / 1000.0f)))));
                        } else {
                            arrayList3.add(new DlineDataBean(r1_68_data.getTime() / 1000, 0.0f));
                        }
                        arrayList4.add(Integer.valueOf(r1_68_data.getAvg_hr()));
                        touchdown_b += r1_68_data.getTouchDownPower_balance();
                    } else if (r1_68_data.getState_type() == 0) {
                        arrayList4.add(Integer.valueOf(r1_68_data.getAvg_hr()));
                    } else if (r1_68_data.getState_type() == 1) {
                        isHasStartEnd = true;
                        ModuleRouteSportService.getInstance().update68Data(r1_68_data.getUid(), r1_68_data.getTime(), 5);
                    } else if (r1_68_data.getState_type() == 2) {
                        isHasStartEnd = true;
                        ModuleRouteSportService.getInstance().update68Data(r1_68_data.getUid(), r1_68_data.getTime(), 6);
                    }
                    if (j == r1_68_datas.size() - 1) {
                        if (!isHasStartEnd) {
                            ModuleRouteSportService.getInstance().update68Data(r1_68_data.getUid(), r1_68_data.getTime(), 7);
                        }
                        isHasStartEnd = false;
                        long endTime = r1_68_data.getTime();
                        float distance2 = distance + r1_68_data.getDistance();
                        float calorie2 = calorie + r1_68_data.getCalorie();
                        if (((endTime - startTime) / 1000 >= 300 || distance2 > 500.0f) && calorie2 != 0.0f) {
                            history.setTime((int) ((endTime - startTime) / 1000));
                            history.setDistance(distance2);
                            history.setCalorie(calorie2);
                            history.setTime_id(startTime / 1000);
                            history.setEnd_time(endTime / 1000);
                            history.setIs_upload(0);
                            history.setData_type(2);
                            history.setData_from(r1_68_data.getData_from());
                            history.setSport_type(3);
                            history.setUid(UserConfig.getInstance().getNewUID());
                            String hearts = JsonTool.toJson(arrayList4);
                            String steps = JsonTool.toJson(arrayList);
                            String earths = JsonTool.toJson(earthLists);
                            String skys = JsonTool.toJson(arrayList2);
                            String speeds = JsonTool.toJson(arrayList3);
                            history.setAvg_hr(hearts);
                            history.setRateOfStride_avg(steps);
                            history.setTouchDown_avg(earths);
                            history.setFlight_avg(skys);
                            history.setSpeedList(speeds);
                            if (arrayList2.size() == 0) {
                                history.setTouchDownPower_balance(touchdown_b);
                            } else {
                                history.setTouchDownPower_balance(touchdown_b / arrayList2.size());
                            }
                            R1DataPresenter presenter = new R1DataPresenter();
                            com.iwown.sport_module.pojo.R1DataBean r1DataBean1 = presenter.initHistoryData(history);
                            ModuleRouteSportService.getInstance().updateR1AndGpsSegment(history.getUid(), ((CopySportGps) his.get(i)).getStart_time(), r1_68_data.getData_from());
                            saveAndR1Data(r1DataBean1, r1_68_data.getUid(), ((CopySportGps) his.get(i)).getStart_time(), history.getEnd_time(), r1_68_data.getData_from());
                            saveAndHrData(presenter.initHrData(r1DataBean1, r1_68_data.getData_from(), arrayList4, history.getTime_id() * 1000, history.getEnd_time() * 1000), history.getUid(), ((CopySportGps) his.get(i)).getStart_time(), ((CopySportGps) his.get(i)).getEnd_time(), r1_68_data.getData_from());
                            startTime = 0;
                            distance = 0.0f;
                            calorie = 0.0f;
                            touchdown_b = 0;
                            arrayList.clear();
                            earthLists.clear();
                            arrayList2.clear();
                            arrayList3.clear();
                            arrayList4.clear();
                        } else {
                            startTime = 0;
                            distance = 0.0f;
                            calorie = 0.0f;
                            touchdown_b = 0;
                            arrayList.clear();
                            earthLists.clear();
                            arrayList2.clear();
                            arrayList3.clear();
                            arrayList4.clear();
                        }
                    }
                }
            }
        }
    }

    private static void saveAndR1Data(com.iwown.sport_module.pojo.R1DataBean r1DataBean, long uid, long time_id, long end_time, String dataFrom) {
        R1UpdateBean bean = new R1UpdateBean();
        bean.setAvgRate(r1DataBean.getRate_avg());
        bean.setAvgGround(r1DataBean.getEarth_time_avg());
        bean.setAvgFlight(r1DataBean.getSky_time_avg());
        bean.setAvgVert(r1DataBean.getVertical_avg());
        bean.setBanlance(r1DataBean.getEarth_balance());
        bean.setPace(r1DataBean.parse(r1DataBean.getSpeedLists()));
        bean.setRate(r1DataBean.parse(r1DataBean.getStepRateLists()));
        bean.setVert(r1DataBean.parse(r1DataBean.getVerticalLists()));
        bean.setGround(r1DataBean.parse(r1DataBean.getEarthTimeLists()));
        String udateInfos = JsonTool.toJson(bean);
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + LogPath.R1_PATH + uid + "_r1_" + time_id + "_" + dataFrom + ".txt";
        KLog.e("yanxi", "-----writer1");
        if (FileIOUtils.writeFileFromString(path, udateInfos, false)) {
            ModuleRouteSportService.getInstance().uploadR1File(true, uid, time_id, end_time, dataFrom, 0);
        }
    }

    private static void saveAndHrData(HeartData data, long uid, long time_id, long endTime, String dataFrom) {
        if (data != null && data.getMins().length == 6) {
            HrUpData hrUpData = new HrUpData();
            hrUpData.setAg(UserConfig.getInstance().getAge());
            Hr51 hr51 = new Hr51();
            hr51.setR0(data.getMins()[0]);
            hr51.setR1(data.getMins()[1]);
            hr51.setR2(data.getMins()[2]);
            hr51.setR3(data.getMins()[3]);
            hr51.setR4(data.getMins()[4]);
            hr51.setR5(data.getMins()[5]);
            hrUpData.setH1(hr51);
            hrUpData.setH3(data.getHeInt());
            String udateInfos = JsonTool.toJson(hrUpData);
            KLog.e("yanxi", "-----writehr");
            if (FileIOUtils.writeFileFromString(Environment.getExternalStorageDirectory().getAbsoluteFile() + LogPath.HR_PATH + uid + "_hr_" + time_id + "_" + dataFrom + ".txt", udateInfos, false)) {
                ModuleRouteSportService.getInstance().uploadHrFile(true, uid, time_id, endTime, "%" + dataFrom, 0);
            }
        }
    }

    private static void saveR1Data(TB_location_history history) {
        CopySportGps sportGps = new CopySportGps();
        sportGps.setDuration(history.getTime());
        sportGps.setDistance(history.getDistance());
        sportGps.setCalorie(history.getCalorie());
        sportGps.setStart_time(history.getTime_id());
        sportGps.setEnd_time(history.getEnd_time());
        sportGps.setData_from(history.getData_from());
        sportGps.setUid(history.getUid());
        sportGps.setSport_type(0);
        sportGps.setStep(0);
        sportGps.setSource_type(2);
        ModuleRouteSportService.getInstance().saveGps2SportTB(sportGps);
    }
}
