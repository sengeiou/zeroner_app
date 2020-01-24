package com.iwown.sport_module.sql;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.ReturnCode;
import com.iwown.data_link.sport_data.V3_sport_data;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.send.Sport28Send;
import com.iwown.sport_module.net.send.Sport28Send.SportSend;
import com.iwown.sport_module.pojo.active.WalkData;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class SportSqlHelper {
    private static SportSqlHelper instance = null;

    public static SportSqlHelper getInstance() {
        if (instance == null) {
            synchronized (SportSqlHelper.class) {
                if (instance == null) {
                    instance = new SportSqlHelper();
                }
            }
        }
        return instance;
    }

    private SportSqlHelper() {
    }

    public void upLoadSportData() {
        KLog.i("进入上传运动历史");
        List<V3_sport_data> tbList = ModuleRouteSportService.getInstance().querySportUpLoadData(UserConfig.getInstance().getNewUID());
        if (tbList != null && tbList.size() > 0) {
            Sport28Send sport28Send = new Sport28Send();
            sport28Send.setUid(((V3_sport_data) tbList.get(0)).getUid());
            List<SportSend> sportList = new ArrayList<>();
            for (V3_sport_data tbData : tbList) {
                SportSend sportData = new SportSend();
                Detail_data detail_data = (Detail_data) new Gson().fromJson(tbData.getDetail_data(), Detail_data.class);
                DateUtil stDate = new DateUtil(tbData.getStart_uxtime(), true);
                DateUtil edDate = new DateUtil(tbData.getEnd_uxtime(), true);
                sportData.setStart_time(stDate.getYyyyMMdd_HHmmssDate());
                sportData.setEnd_time(edDate.getYyyyMMdd_HHmmssDate());
                sportData.setData_from(tbData.getData_from());
                sportData.setDistance(detail_data.getDistance());
                sportData.setCalorie(Util.doubleToFloat(2, tbData.getCalorie()));
                sportData.setSport_type(tbData.getSport_type());
                sportData.setDuration(detail_data.getActivity());
                sportData.setDone_times(detail_data.getCount());
                sportData.setStep(detail_data.getStep());
                sportList.add(sportData);
            }
            sport28Send.setData(sportList);
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode o) {
                    ModuleRouteSportService.getInstance().changeUpFlag(UserConfig.getInstance().getNewUID());
                    UserConfig.getInstance().setLast_up_sport_time(System.currentTimeMillis());
                    UserConfig.getInstance().save();
                }

                public void onFail(Throwable e) {
                }
            }).uploadSport28(sport28Send);
        }
    }

    public void upLoadWalkData() {
        List<V3_walk> walkList = ModuleRouteWalkService.getInstance().getUpWalkData(UserConfig.getInstance().getNewUID());
        KLog.d("licl", "upLoadWalk" + JsonTool.toJson(walkList));
        if (walkList != null && walkList.size() > 0) {
            List<WalkData> walkDatas = new ArrayList<>();
            for (V3_walk tbWalk : walkList) {
                WalkData data = new WalkData();
                data.setUid(UserConfig.getInstance().getNewUID());
                data.setCalorie(tbWalk.getCalorie());
                data.setData_from(tbWalk.getData_from());
                data.setDistance(tbWalk.getDistance());
                data.setRecord_date(tbWalk.getRecord_date());
                data.setStep(tbWalk.getStep());
                walkDatas.add(data);
            }
            NetFactory.getInstance().getClient(new MyCallback<RetCode>() {
                public void onSuccess(RetCode o) {
                    ModuleRouteWalkService.getInstance().changeUpFlag(UserConfig.getInstance().getNewUID());
                }

                public void onFail(Throwable e) {
                }
            }).up29Data(UserConfig.getInstance().getNewUID(), walkDatas);
        }
    }

    public int getActiveTimeExecludeWalk(long uid, int year, int month, int day, String data_from) {
        List<V3_sport_data> v3_sport_datas = ModuleRouteSportService.getInstance().getSport(uid, year, month, day, data_from);
        int actv_time = 0;
        if (!(v3_sport_datas == null || v3_sport_datas.size() == 0)) {
            for (V3_sport_data data : v3_sport_datas) {
                if (!(data.getSport_type() == 255 || data.getSport_type() == 1)) {
                    actv_time += ((Detail_data) JsonTool.fromJson(data.getDetail_data(), Detail_data.class)).getActivity();
                }
            }
        }
        return actv_time;
    }

    public static void addP1CDb() {
        if (((TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", "P1C").findFirst(TB_DevSupportsByName.class)) == null) {
            TB_DevSupportsByName name = new TB_DevSupportsByName();
            name.setDev_type(3);
            name.setName_key("P1C");
            name.setSupports(6);
            name.save();
        }
    }

    public static TB_DevSupportsByName querySupportsByName(String data_from) {
        return (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
    }

    public static boolean isP1(String data_from) {
        String new_str = getNameKeyString(data_from);
        if (new_str != null && new_str.contains("P1C")) {
            return true;
        }
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", new_str + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if (supportsByName.getDev_type() != 3) {
            return false;
        }
        return true;
    }

    public static boolean isR1(String data_from) {
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if (supportsByName.getDev_type() == 4) {
            return true;
        }
        return false;
    }

    public static boolean isIvWrist(String data_from) {
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if (supportsByName.getDev_type() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isZgWrist(String data_from) {
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if (supportsByName.getDev_type() == 2) {
            return true;
        }
        return false;
    }

    public static boolean isPhone(String data_from) {
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if (supportsByName.getDev_type() == 5) {
            return true;
        }
        return false;
    }

    public static boolean isP1(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null || supportsByName.getDev_type() != 3) {
            return false;
        }
        return true;
    }

    public static boolean isR1(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null || supportsByName.getDev_type() != 4) {
            return false;
        }
        return true;
    }

    public static boolean isIvWrist(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null) {
            return false;
        }
        if (supportsByName.getDev_type() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isZgWrist(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null || supportsByName.getDev_type() != 2) {
            return false;
        }
        return true;
    }

    public static boolean isPhone(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null || supportsByName.getDev_type() != 5) {
            return false;
        }
        return true;
    }

    @NonNull
    private static String getNameKeyString(String data_from) {
        String str = "";
        if (data_from.lastIndexOf(HelpFormatter.DEFAULT_OPT_PREFIX) == -1) {
            return data_from;
        }
        String new_str = data_from.substring(0, data_from.lastIndexOf(HelpFormatter.DEFAULT_OPT_PREFIX));
        KLog.d("licl", "new_str: " + new_str);
        return new_str;
    }

    public static boolean supportHR(String data_from) {
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if (((supportsByName.getSupports() >> 2) & 1) == 1) {
            return true;
        }
        return false;
    }

    public static boolean supportGps(String data_from) {
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if (((supportsByName.getSupports() >> 1) & 1) == 1) {
            return true;
        }
        return false;
    }

    public static boolean supportRunState(String data_from) {
        TB_DevSupportsByName supportsByName = (TB_DevSupportsByName) DataSupport.where("name_key=? COLLATE NOCASE", getNameKeyString(data_from) + "").findFirst(TB_DevSupportsByName.class);
        if (supportsByName == null) {
            return false;
        }
        if ((supportsByName.getSupports() & 1) == 1) {
            return true;
        }
        return false;
    }

    public static boolean supportHR(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null) {
            return false;
        }
        if (((supportsByName.getSupports() >> 2) & 1) == 1) {
            return true;
        }
        return false;
    }

    public static boolean supportGps(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null) {
            return false;
        }
        if (((supportsByName.getSupports() >> 1) & 1) == 1) {
            return true;
        }
        return false;
    }

    public static boolean supportRunState(TB_DevSupportsByName supportsByName) {
        if (supportsByName == null) {
            return false;
        }
        if ((supportsByName.getSupports() & 1) == 1) {
            return true;
        }
        return false;
    }
}
