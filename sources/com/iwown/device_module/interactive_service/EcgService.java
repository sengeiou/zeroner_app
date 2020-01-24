package com.iwown.device_module.interactive_service;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.ecg.EcgUploadNet;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.IEcgService;
import com.iwown.device_module.common.BaseActionUtils.FilePath;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.resp.UpSDFileCode;
import com.iwown.device_module.common.sql.TB_64_data;
import com.iwown.device_module.common.sql.TB_64_index_table;
import com.iwown.device_module.common.sql.TB_ecg_view_data;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_hours;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.socks.library.KLog;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

@Route(path = "/device/ecg_service")
public class EcgService implements IEcgService {
    public void init(Context context) {
    }

    public void braceletToView(long newUID, String device) {
        List<TB_64_index_table> list = DataSupport.where("uid=? and data_from=?", String.valueOf(newUID), device).find(TB_64_index_table.class);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                List<Integer> datas = new ArrayList<>();
                TB_64_index_table tb_64_index_table = (TB_64_index_table) list.get(i);
                int startSeq = tb_64_index_table.getSeq_start();
                int endSeq = tb_64_index_table.getSeq_end();
                TB_ecg_view_data data = (TB_ecg_view_data) DataSupport.where("uid=? and data_from=? and date=? ", String.valueOf(newUID), device, tb_64_index_table.getDate()).findFirst(TB_ecg_view_data.class);
                ArrayList<Integer> ecgItemDatas = new ArrayList<>();
                if (!(data == null || data.getDataArray() == null)) {
                    ecgItemDatas = JsonUtils.getListJson(data.getDataArray(), Integer.class);
                }
                if (data == null || ecgItemDatas == null || ecgItemDatas.size() <= 2500 || data.getHeartrate() == 0) {
                    List<TB_64_data> tb_64_data = new ArrayList<>();
                    if (startSeq <= endSeq) {
                        tb_64_data = DataSupport.where("uid=? and data_from=? and time >= ? and seq >=? and seq <=?", String.valueOf(newUID), device, (tb_64_index_table.getUnixTime() - 30) + "", startSeq + "", endSeq + "").find(TB_64_data.class);
                    } else {
                        List<TB_64_data> t1 = DataSupport.where("uid=? and data_from=? and time >= ? and seq >=? and seq <=?", String.valueOf(newUID), device, (tb_64_index_table.getUnixTime() - 30) + "", startSeq + "", "1279").find(TB_64_data.class);
                        List<TB_64_data> t2 = DataSupport.where("uid=? and data_from=? and time >= ? and seq >=? and seq <=?", String.valueOf(newUID), device, (tb_64_index_table.getUnixTime() - 30) + "", "0", endSeq + "").find(TB_64_data.class);
                        tb_64_data.addAll(t1);
                        tb_64_data.addAll(t2);
                    }
                    if (tb_64_data != null && tb_64_data.size() > 0) {
                        for (int j = 0; j < tb_64_data.size(); j++) {
                            String ecgPoint = ((TB_64_data) tb_64_data.get(j)).getEcg();
                            if (!TextUtils.isEmpty(ecgPoint)) {
                                datas.addAll(JsonUtils.getListJson(ecgPoint, Integer.class));
                            }
                        }
                    }
                    DateUtil d = new DateUtil(tb_64_index_table.getUnixTime(), true);
                    TB_v3_heartRate_data_hours heartHour = (TB_v3_heartRate_data_hours) DataSupport.where("uid=? and data_from=? and year =? and month=? and day=? and hours =?", String.valueOf(newUID), device, d.getYear() + "", d.getMonth() + "", d.getDay() + "", d.getHour() + "").findFirst(TB_v3_heartRate_data_hours.class);
                    int heart = 0;
                    if (heartHour != null && !TextUtils.isEmpty(heartHour.getDetail_data())) {
                        ArrayList listJson = JsonUtils.getListJson(heartHour.getDetail_data(), Integer.class);
                        if (listJson != null && listJson.size() > d.getMinute()) {
                            if (BluetoothOperation.isProtoBuf()) {
                                try {
                                    heart = ((Integer) listJson.get(new DateUtil(d.getUnixTimestamp() + 90, true).getMinute())).intValue();
                                } catch (Exception e) {
                                    ThrowableExtension.printStackTrace(e);
                                }
                            } else {
                                heart = ((Integer) listJson.get(d.getMinute())).intValue();
                            }
                        }
                    }
                    TB_ecg_view_data tb_ecg_view_data = (TB_ecg_view_data) DataSupport.where("uid=? and data_from=? and date=?", String.valueOf(newUID), device, tb_64_index_table.getDate()).findFirst(TB_ecg_view_data.class);
                    if (tb_ecg_view_data == null) {
                        tb_ecg_view_data = new TB_ecg_view_data();
                    }
                    if (tb_ecg_view_data.getHeartrate() == 0 && heart > 0) {
                        tb_ecg_view_data.set_uploaded(0);
                        tb_ecg_view_data.setToDefault("_uploaded");
                    }
                    tb_ecg_view_data.setHeartrate(heart);
                    tb_ecg_view_data.setUid(newUID);
                    tb_ecg_view_data.setData_from(device);
                    if (datas.size() > 0) {
                        tb_ecg_view_data.setDataArray(JsonUtils.toJson(datas));
                    }
                    tb_ecg_view_data.setDate(tb_64_index_table.getDate());
                    tb_ecg_view_data.setUnixTime(tb_64_index_table.getUnixTime());
                    tb_ecg_view_data.save();
                    if (datas.size() != 0) {
                        ecgCmdSaveToFile(newUID, device, tb_64_index_table.getUnixTime(), tb_64_index_table.getDate(), JsonUtils.toJson(datas));
                    }
                }
            }
        }
    }

    private void ecgToFile(long uid, String data_from, String date, long time) {
        String fileName = uid + HelpFormatter.DEFAULT_OPT_PREFIX + data_from + HelpFormatter.DEFAULT_OPT_PREFIX + time;
        String path = Environment.getExternalStorageDirectory() + FilePath.ECG_Data_Path + fileName + ".txt";
        String zipPath = Environment.getExternalStorageDirectory() + FilePath.ECG_Data_Path + fileName + ".zip";
        if (ZipUtil.zip(path, zipPath)) {
            final long j = uid;
            final String str = data_from;
            final String str2 = date;
            NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                public void onSuccess(UpSDFileCode upSDFileCode) {
                    if (upSDFileCode.getRetCode() == 0 && !TextUtils.isEmpty(upSDFileCode.getUrl())) {
                        EcgService.this.upDateEcgUrl(j, str, str2, upSDFileCode.getUrl());
                    }
                }

                public void onFail(Throwable e) {
                    KLog.i("-----------------------" + e.toString());
                }
            }).upSd_ecg_File(uid, date, date, data_from, new File(zipPath));
        }
    }

    private synchronized void ecgCmdSaveToFile(long uid, String data_from, long time, String date, String dataArray) {
        String baseUrl = FilePath.ECG_Data_Path;
        String fileName = uid + HelpFormatter.DEFAULT_OPT_PREFIX + data_from + HelpFormatter.DEFAULT_OPT_PREFIX + time + ".txt";
        if (FileUtils.checkFileExists(baseUrl + fileName)) {
            FileUtils.deleteFile(baseUrl + fileName);
        }
        write2SDFromString_1(FilePath.ECG_Data_Path, fileName, dataArray);
        if (!TextUtils.isEmpty(dataArray)) {
            ecgToFile(uid, data_from, date, time);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f A[SYNTHETIC, Splitter:B:16:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b A[SYNTHETIC, Splitter:B:22:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromString_1(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r1 = 0
            r2 = 0
            creatSDDir(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r4.<init>()     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0039 }
            java.io.File r1 = createSDFile(r4)     // Catch:{ Exception -> 0x0039 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0039 }
            r4 = 1
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0039 }
            r3.write(r7)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            java.lang.String r4 = "\r\n"
            r3.write(r4)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            r3.flush()     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            if (r3 == 0) goto L_0x0031
            r3.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0031:
            r2 = r3
        L_0x0032:
            return r1
        L_0x0033:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r2 = r3
            goto L_0x0032
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ Exception -> 0x0043 }
            goto L_0x0032
        L_0x0043:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0032
        L_0x0048:
            r4 = move-exception
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004e:
            throw r4
        L_0x004f:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004e
        L_0x0054:
            r4 = move-exception
            r2 = r3
            goto L_0x0049
        L_0x0057:
            r0 = move-exception
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.interactive_service.EcgService.write2SDFromString_1(java.lang.String, java.lang.String, java.lang.String):java.io.File");
    }

    public static File createSDFile(String fileName) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
            KLog.e("---create file : " + Environment.getExternalStorageDirectory() + "/" + fileName);
        }
        return file;
    }

    public static File creatSDDir(String dirName) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public List<Integer> ecgChartViewDataByTime(long newUID, String device, long time) {
        List<Integer> data = new ArrayList<>();
        TB_ecg_view_data tb_ecg_view_data = (TB_ecg_view_data) DataSupport.where("uid=? and data_from =? and unixTime =?", String.valueOf(newUID), device, time + "").findFirst(TB_ecg_view_data.class);
        if (tb_ecg_view_data != null) {
            String ecgData = tb_ecg_view_data.getDataArray();
            if (!TextUtils.isEmpty(ecgData)) {
                data.addAll(JsonUtils.getListJson(ecgData, Integer.class));
            }
        }
        return data;
    }

    public List<EcgViewDataBean> ecgViewDataFromDB(long newUID, String device, long time) {
        List<EcgViewDataBean> data = new ArrayList<>();
        try {
            List<TB_ecg_view_data> dataList = DataSupport.where("uid=?  and unixTime >=?  and  unixTime<= ?", String.valueOf(newUID), time + "", ((86400 + time) - 1) + "").order("unixTime desc").find(TB_ecg_view_data.class);
            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    EcgViewDataBean dataBean = new EcgViewDataBean();
                    dataBean.setUid(((TB_ecg_view_data) dataList.get(i)).getUid());
                    dataBean.setData_from(((TB_ecg_view_data) dataList.get(i)).getData_from());
                    dataBean.setDataArray(((TB_ecg_view_data) dataList.get(i)).getDataArray());
                    dataBean.setDate(((TB_ecg_view_data) dataList.get(i)).getDate());
                    dataBean.setHeartrate(((TB_ecg_view_data) dataList.get(i)).getHeartrate());
                    dataBean.setNote(((TB_ecg_view_data) dataList.get(i)).getNote());
                    dataBean.setUrl(((TB_ecg_view_data) dataList.get(i)).getUrl());
                    dataBean.setUnixTime(((TB_ecg_view_data) dataList.get(i)).getUnixTime());
                    dataBean.setAi_result(((TB_ecg_view_data) dataList.get(i)).getAi_result());
                    data.add(dataBean);
                    if (TextUtils.isEmpty(((TB_ecg_view_data) dataList.get(i)).getUrl())) {
                        ecgToFile(((TB_ecg_view_data) dataList.get(i)).getUid(), ((TB_ecg_view_data) dataList.get(i)).getData_from(), ((TB_ecg_view_data) dataList.get(i)).getDate(), ((TB_ecg_view_data) dataList.get(i)).getUnixTime());
                    }
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return data;
    }

    public EcgViewDataBean ecgViewDataByTime(long newUID, String device, long time) {
        TB_ecg_view_data data = (TB_ecg_view_data) DataSupport.where("uid=? and data_from =? and unixTime =?", String.valueOf(newUID), device, time + "").findFirst(TB_ecg_view_data.class);
        EcgViewDataBean dataBean = new EcgViewDataBean();
        dataBean.setUid(data.getUid());
        dataBean.setData_from(data.getData_from());
        dataBean.setDataArray(data.getDataArray());
        dataBean.setDate(data.getDate());
        dataBean.setHeartrate(data.getHeartrate());
        dataBean.setNote(data.getNote());
        dataBean.setUrl(data.getUrl());
        dataBean.setUnixTime(data.getUnixTime());
        dataBean.setAi_result(data.getAi_result());
        return dataBean;
    }

    public void saveEcgNote(EcgViewDataBean bean) {
        if (bean != null) {
            TB_ecg_view_data data = (TB_ecg_view_data) DataSupport.where("uid=? and data_from =? and unixTime =?", String.valueOf(bean.getUid()), bean.getData_from(), bean.getUnixTime() + "").findFirst(TB_ecg_view_data.class);
            if (data != null) {
                data.setNote(bean.getNote());
                data.saveOrUpdate("uid=? and data_from =? and unixTime =?", String.valueOf(bean.getUid()), bean.getData_from(), bean.getUnixTime() + "");
            }
        }
    }

    public List<EcgUploadNet> getUnUploadedData(long uid) {
        List<EcgUploadNet> netList = new ArrayList<>();
        List<TB_ecg_view_data> list = DataSupport.where("uid=? and _uploaded=0", uid + "").find(TB_ecg_view_data.class);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TB_ecg_view_data td = (TB_ecg_view_data) list.get(i);
                EcgUploadNet en = new EcgUploadNet();
                en.setUid(td.getUid());
                en.setData_from(td.getData_from());
                en.setHr(td.getHeartrate());
                en.setNote(td.getNote());
                en.setStart_time(td.getDate());
                en.setUrl(td.getUrl());
                if (td.getUrl() != null) {
                    netList.add(en);
                }
            }
        }
        KLog.i("----------------------" + netList.size());
        return netList;
    }

    public void updateDataAlreadyUploaded(List<EcgUploadNet> data) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                EcgUploadNet en = (EcgUploadNet) data.get(i);
                TB_ecg_view_data d = new TB_ecg_view_data();
                d.set_uploaded(1);
                d.updateAll("uid=? and date=? and data_from=?", en.getUid() + "", en.getStart_time(), en.getData_from());
            }
        }
    }

    public void upDateEcgUrl(long uid, String data_from, String date, String url) {
        TB_ecg_view_data d = new TB_ecg_view_data();
        d.setUrl(url);
        d.updateAll("uid=? and date=? and data_from=?", uid + "", date, data_from);
    }

    public void updateEcgAIResult(long uid, String data_from, String date, List<String> ai_result, int uploaded) {
        TB_ecg_view_data d = new TB_ecg_view_data();
        d.setAi_result(JsonUtils.toJson(ai_result));
        d.set_uploaded(uploaded);
        if (uploaded == 0) {
            d.setToDefault("_uploaded");
        }
        d.updateAll("uid=? and date=? and data_from=?", uid + "", date, data_from);
    }

    public void updateEcgByData(long uid, String dataFrom, String date, String data) {
        TB_ecg_view_data d = new TB_ecg_view_data();
        if (!TextUtils.isEmpty(data)) {
            ArrayList<Integer> list = JsonUtils.getListJson(data, Integer.class);
            if (list != null && list.size() > 0) {
                d.setDataArray(JsonUtils.toJson(list));
            }
        }
        d.updateAll("uid=? and date=? and data_from=?", uid + "", date, dataFrom);
    }

    public void saveEcgData(EcgViewDataBean data) {
        if (data != null) {
            TB_ecg_view_data ted = (TB_ecg_view_data) DataSupport.where("uid=? and data_from =? and date =?", String.valueOf(data.getUid()), data.getData_from(), data.getDate() + "").findFirst(TB_ecg_view_data.class);
            if (ted == null) {
                ted = new TB_ecg_view_data();
            }
            ted.setUrl(data.getUrl());
            ted.set_uploaded(1);
            ted.setUid(data.getUid());
            ted.setUnixTime(data.getUnixTime());
            ted.setDate(data.getDate());
            ted.setNote(data.getNote());
            ted.setData_from(data.getData_from());
            ted.setHeartrate(data.getHeartrate());
            ted.setDataArray(data.getDataArray());
            ted.setAi_result(data.getAi_result());
            ted.saveOrUpdate("uid=? and data_from =? and date =?", String.valueOf(data.getUid()), data.getData_from(), data.getDate() + "");
        }
    }

    public List<EcgViewDataBean> ecgViewDataFromDbByUid(long newUID) {
        List<EcgViewDataBean> ecgViewDataBeans = new ArrayList<>();
        List<TB_ecg_view_data> list = DataSupport.where("uid= ?", newUID + "").find(TB_ecg_view_data.class);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TB_ecg_view_data data = (TB_ecg_view_data) list.get(i);
                EcgViewDataBean dataBean = new EcgViewDataBean();
                dataBean.setUid(data.getUid());
                dataBean.setData_from(data.getData_from());
                dataBean.setDataArray(data.getDataArray());
                dataBean.setDate(data.getDate());
                dataBean.setHeartrate(data.getHeartrate());
                dataBean.setNote(data.getNote());
                dataBean.setUrl(data.getUrl());
                dataBean.setUnixTime(data.getUnixTime());
                ecgViewDataBeans.add(dataBean);
            }
        }
        return ecgViewDataBeans;
    }

    public void loadEcgCalendarView(long uid, DateUtil dateUtil) {
        new ArrayList();
        List<TB_ecg_view_data> tb_ecg_view_data = DataSupport.where("uid=? and unixTime =?", uid + "", dateUtil.getUnixTimestamp() + "").order("unixTime desc").find(TB_ecg_view_data.class);
    }

    public int checkHasDataByUid(long uid, DateUtil dateUtil) {
        List<TB_ecg_view_data> list = DataSupport.where("uid=? and unixTime>= ? and  unixTime<=?", uid + "", dateUtil.getZeroTime() + "", (dateUtil.getZeroTime() + 86400) + "").find(TB_ecg_view_data.class);
        if (list == null || list.size() <= 0) {
            return 0;
        }
        return 1;
    }
}
