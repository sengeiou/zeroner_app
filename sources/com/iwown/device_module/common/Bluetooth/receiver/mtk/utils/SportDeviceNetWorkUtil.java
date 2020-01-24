package com.iwown.device_module.common.Bluetooth.receiver.mtk.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.client.DeviceClient;
import com.iwown.device_module.common.network.data.req.SportBallSend;
import com.iwown.device_module.common.network.data.req.SportBallSend.BallSend;
import com.iwown.device_module.common.network.data.req.SportGpsSend;
import com.iwown.device_module.common.network.data.req.SportGpsSend.GpsSend;
import com.iwown.device_module.common.network.data.req.SportOtherSend;
import com.iwown.device_module.common.network.data.req.SportOtherSend.OtherSend;
import com.iwown.device_module.common.network.data.req.SportSwimSend;
import com.iwown.device_module.common.network.data.req.SportSwimSend.SwimSend;
import com.iwown.device_module.common.network.data.req.UpSportGpsUrl;
import com.iwown.device_module.common.network.data.req.UpSportGpsUrl.SportGpsUrl;
import com.iwown.device_module.common.network.data.resp.ReturnCode;
import com.iwown.device_module.common.network.data.resp.UpSDFileCode;
import com.iwown.device_module.common.sql.TB_sport_ball;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import com.iwown.device_module.common.sql.TB_sport_other;
import com.iwown.device_module.common.sql.TB_sport_swim;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.socks.library.KLog;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.litepal.crud.DataSupport;

public class SportDeviceNetWorkUtil {
    private static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(2, 2, 10, TimeUnit.SECONDS, new LinkedBlockingDeque());

    public static void uploadNggService(final long uid) {
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                SportDeviceNetWorkUtil.uploadAllTbGpsSegment(uid);
                SportDeviceNetWorkUtil.uploadAllTbBallSegment(uid);
                SportDeviceNetWorkUtil.uploadAllTbOtherSegment(uid);
                SportDeviceNetWorkUtil.uploadAllTbSwimSegment(uid);
                SportDeviceNetWorkUtil.uploadGpsServiceUrl(uid);
            }
        });
    }

    public static void uploadGps(long uid, long startTime, long endTime, String dataFrom, boolean hasHr, int sportType) {
        String path1 = Environment.getExternalStorageDirectory() + LogPath.GPS_PATH + uid + "_gps_" + startTime + "_" + dataFrom;
        String txtFile = path1 + ".txt";
        final String zipFile = path1 + ".zip";
        if (new File(txtFile).exists()) {
            String st = new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate();
            String ed = new DateUtil(endTime, true).getYyyyMMdd_HHmmssDate();
            if (ZipUtil.zip(txtFile, zipFile)) {
                final long j = uid;
                final long j2 = startTime;
                final String str = dataFrom;
                final boolean z = hasHr;
                final long j3 = endTime;
                final int i = sportType;
                NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                    public void onSuccess(UpSDFileCode upSDFileCode) {
                        FileUtils.deleteFile(new File(zipFile));
                        if (upSDFileCode != null) {
                            try {
                                if (upSDFileCode.getRetCode() == 0) {
                                    DataUtil.upGpsSportOneUrl(upSDFileCode.getUrl(), 0, j, j2, str, 0);
                                    if (z) {
                                        SportDeviceNetWorkUtil.uploadHr(true, j, j2, j3, str, i);
                                    }
                                }
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                        FileUtils.deleteFile(new File(zipFile));
                    }
                }).upSportGpsFile(uid, st, ed, dataFrom, new File(zipFile));
            }
        }
    }

    public static void uploadHr(boolean isGpsSeg, long uid, long startTime, long endTime, String dataFrom, int sportType) {
        final int dataType;
        if (isGpsSeg) {
            dataType = 0;
        } else {
            dataType = DataUtil.getSportDataTYpe(sportType);
        }
        String newDataFrom = dataFrom;
        if (dataFrom.startsWith("%")) {
            newDataFrom = dataFrom.replace("%", "");
        }
        String path1 = Environment.getExternalStorageDirectory() + LogPath.HR_PATH + uid + "_hr_" + startTime + "_" + newDataFrom;
        if (dataType == 3) {
            path1 = Environment.getExternalStorageDirectory() + LogPath.SWIM_PATH + uid + "_" + startTime + "_" + newDataFrom;
        }
        String txtFile = path1 + ".txt";
        final String zipFile = path1 + ".zip";
        String st = new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate();
        String ed = new DateUtil(endTime, true).getYyyyMMdd_HHmmssDate();
        if (new File(txtFile).exists() && ZipUtil.zip(txtFile, zipFile)) {
            final long j = uid;
            final long j2 = startTime;
            final String str = dataFrom;
            NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                public void onSuccess(UpSDFileCode upSDFileCode) {
                    FileUtils.deleteFile(new File(zipFile));
                    if (upSDFileCode != null) {
                        try {
                            if (upSDFileCode.getRetCode() == 0) {
                                KLog.e("no2855-> 心率上传的url: " + upSDFileCode.getUrl() + " 我去: " + dataType);
                                DataUtil.upGpsSportOneUrl(upSDFileCode.getUrl(), 1, j, j2, str, dataType);
                                if (dataType == 0) {
                                    SportDeviceNetWorkUtil.uploadTbGpsSegment(j, j2, str, true);
                                } else if (dataType == 1) {
                                    SportDeviceNetWorkUtil.uploadTbBallSegment(j, j2, str);
                                } else if (dataType == 2) {
                                    SportDeviceNetWorkUtil.uploadTbOtherSegment(j, j2, str);
                                } else if (dataType == 3) {
                                    SportDeviceNetWorkUtil.uploadTbSwimSegment(j, j2, str);
                                }
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                    FileUtils.deleteFile(new File(zipFile));
                }
            }).upSportHrFile(uid, st, ed, newDataFrom, new File(zipFile));
        }
    }

    public static void updateR1(boolean isGpsSeg, long uid, long startTime, long endTime, String dataFrom, int sportType) {
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + LogPath.R1_PATH + uid + "_r1_" + startTime + "_" + dataFrom + ".txt";
        final String outPath = Environment.getExternalStorageDirectory().getAbsoluteFile() + LogPath.R1_PATH + uid + "_r1_" + startTime + "_" + dataFrom + ".zip";
        KLog.e("yanxi....准备上传R1数据...." + dataFrom);
        if (ZipUtil.zip(path, outPath)) {
            KLog.e("yanxi....本地有文件...上传R1步态分析数据...." + dataFrom);
            DateUtil dateUtil = new DateUtil(startTime, true);
            final String st = dateUtil.getYyyyMMdd_HHmmssDate();
            DateUtil dateUtil2 = new DateUtil(endTime, true);
            String ed = dateUtil2.getYyyyMMdd_HHmmssDate();
            final String str = dataFrom;
            final long j = uid;
            final long j2 = startTime;
            DeviceClient client = NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>(0) {
                public void onSuccess(UpSDFileCode upSDFileCode) {
                    FileUtils.deleteFile(new File(outPath));
                    KLog.e("yanxi ;r1----上传成功--" + st + "---" + str);
                    if (upSDFileCode.getRetCode() == 0) {
                        try {
                            DataUtil.upGpsSportOneUrl(upSDFileCode.getUrl(), 2, j, j2, "%" + str, 0);
                            SportDeviceNetWorkUtil.uploadTbGpsSegment(j, j2, "%" + str, true);
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                    KLog.e("r1----上传失败" + st + "---" + str);
                }
            });
            File file = new File(outPath);
            client.upSportR1File(uid, st, ed, dataFrom, file);
            return;
        }
        KLog.e("yanxi....没有符合条件的数据,无法上传...");
    }

    public static void uploadTbGpsSegment(long uid, long startTime, String dataFrom, boolean isCheck) {
        final TB_sport_gps_segment gps_segment = (TB_sport_gps_segment) DataSupport.where("uid=? and start_time=? and data_from like ?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_gps_segment.class);
        if (gps_segment != null) {
            boolean isCanUp = !"1".equals(gps_segment.getGps_url()) && !"1".equals(gps_segment.getHeart_url()) && !"1".equals(gps_segment.getR1_url());
            KLog.e("no2855---->上传gpsSegment: " + gps_segment.getGps_url() + " == " + gps_segment.getHeart_url() + " == " + gps_segment.getR1_url());
            KLog.e("no2855---->上传gpsSegment canUP: " + isCheck + " == " + isCanUp);
            if (isCanUp) {
                SportGpsSend sportGpsSend = new SportGpsSend();
                sportGpsSend.setUid(uid);
                List<GpsSend> gpsSends = new ArrayList<>();
                GpsSend gpsSend = new GpsSend();
                gpsSend.setData_from(gps_segment.getData_from());
                gpsSend.setSource_type(2);
                DateUtil st = new DateUtil(gps_segment.getStart_time(), true);
                DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
                gpsSend.setStart_time(st.getYyyyMMdd_HHmmssDate());
                gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
                gpsSend.setSport_type(gps_segment.getSport_type());
                gpsSend.setStep(gps_segment.getStep());
                gpsSend.setCalorie(gps_segment.getCalorie());
                gpsSend.setDuration(gps_segment.getDuration());
                gpsSend.setDistance(gps_segment.getDistance());
                gpsSend.setAvg_pace(0);
                gpsSend.setCadence(0);
                if (!"1".equals(gps_segment.getGps_url())) {
                    gpsSend.setGps_data_url(gps_segment.getGps_url());
                }
                if (!"1".equals(gps_segment.getHeart_url())) {
                    gpsSend.setHr_data_url(gps_segment.getHeart_url());
                }
                if (!"1".equals(gps_segment.getR1_url())) {
                    gpsSend.setHeadset_data_url(gps_segment.getR1_url());
                }
                gpsSends.add(gpsSend);
                sportGpsSend.setData(gpsSends);
                NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                    public void onSuccess(ReturnCode returnCode) {
                        if (returnCode != null) {
                            try {
                                if (returnCode.getReturnCode() == 0) {
                                    gps_segment.setUpload(1);
                                    gps_segment.update(gps_segment.getId());
                                }
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                    }
                }).upSportGpsSegment(uid, sportGpsSend);
            }
        }
    }

    public static void uploadTbBallSegment(long uid, long startTime, String dataFrom) {
        final TB_sport_ball gps_segment = (TB_sport_ball) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_ball.class);
        if (gps_segment != null && !TextUtils.isEmpty(gps_segment.getHeart_url()) && !"1".equals(gps_segment.getHeart_url())) {
            SportBallSend sportGpsSend = new SportBallSend();
            sportGpsSend.setUid(uid);
            List<BallSend> gpsSends = new ArrayList<>();
            BallSend gpsSend = new BallSend();
            gpsSend.setData_from(gps_segment.getData_from());
            DateUtil st = new DateUtil(gps_segment.getStart_time(), true);
            DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
            gpsSend.setStart_time(st.getYyyyMMdd_HHmmssDate());
            gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
            gpsSend.setSport_type(gps_segment.getSport_type());
            gpsSend.setCalorie(gps_segment.getCalorie());
            gpsSend.setDuration(gps_segment.getDuration());
            gpsSend.setHr_data_url(gps_segment.getHeart_url());
            gpsSends.add(gpsSend);
            sportGpsSend.setData(gpsSends);
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode returnCode) {
                    if (returnCode != null) {
                        try {
                            if (returnCode.getReturnCode() == 0) {
                                gps_segment.setUpload_type(1);
                                gps_segment.update(gps_segment.getId());
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                }
            }).upSportBallSegment(uid, sportGpsSend);
        }
    }

    public static void uploadTbOtherSegment(long uid, long startTime, String dataFrom) {
        final TB_sport_other gps_segment = (TB_sport_other) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_other.class);
        if (gps_segment != null && !TextUtils.isEmpty(gps_segment.getHeart_url()) && !"1".equals(gps_segment.getHeart_url())) {
            SportOtherSend sportGpsSend = new SportOtherSend();
            sportGpsSend.setUid(uid);
            List<OtherSend> gpsSends = new ArrayList<>();
            OtherSend gpsSend = new OtherSend();
            gpsSend.setData_from(gps_segment.getData_from());
            DateUtil st = new DateUtil(gps_segment.getStart_time(), true);
            DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
            gpsSend.setStart_time(st.getYyyyMMdd_HHmmssDate());
            gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
            gpsSend.setSport_type(gps_segment.getSport_type());
            gpsSend.setCalorie(gps_segment.getCalorie());
            gpsSend.setDone_times(gps_segment.getDone_times());
            gpsSend.setDuration(gps_segment.getDuration());
            gpsSend.setHr_data_url(gps_segment.getHeart_url());
            gpsSends.add(gpsSend);
            sportGpsSend.setData(gpsSends);
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode returnCode) {
                    if (returnCode != null) {
                        try {
                            if (returnCode.getReturnCode() == 0) {
                                gps_segment.setUpload_type(1);
                                gps_segment.update(gps_segment.getId());
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                }
            }).upSportOtherSegment(uid, sportGpsSend);
        }
    }

    public static void uploadTbSwimSegment(long uid, long startTime, String dataFrom) {
        final TB_sport_swim gps_segment = (TB_sport_swim) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_swim.class);
        if (gps_segment != null && !TextUtils.isEmpty(gps_segment.getHeart_url()) && !"1".equals(gps_segment.getHeart_url())) {
            SportSwimSend sportGpsSend = new SportSwimSend();
            List<SwimSend> gpsSends = new ArrayList<>();
            SwimSend gpsSend = new SwimSend();
            gpsSend.setData_from(gps_segment.getData_from());
            DateUtil st = new DateUtil(gps_segment.getStart_time(), true);
            DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
            gpsSend.setStart_time(st.getYyyyMMdd_HHmmssDate());
            gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
            gpsSend.setUid(uid);
            gpsSend.setCalorie(gps_segment.getCalorie());
            gpsSend.setDistance(gps_segment.getDistance());
            gpsSend.setStroke_times(gps_segment.getDone_times());
            gpsSend.setDuration(gps_segment.getDuration());
            gpsSend.setSwim_laps(gps_segment.getLaps());
            gpsSend.setSwim_detail_data(gps_segment.getHeart_url());
            gpsSends.add(gpsSend);
            sportGpsSend.setData(gpsSends);
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode returnCode) {
                    if (returnCode != null) {
                        try {
                            if (returnCode.getReturnCode() == 0) {
                                gps_segment.setUpload_type(1);
                                gps_segment.update(gps_segment.getId());
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                }
            }).upSportSwimSegment(uid, sportGpsSend);
        }
    }

    public static void uploadAllTbGpsSegment(long uid) {
        List<TB_sport_gps_segment> gpsSegmentList = DataSupport.where("uid=? and upload=0", uid + "").find(TB_sport_gps_segment.class);
        if (gpsSegmentList != null && gpsSegmentList.size() > 0) {
            SportGpsSend sportGpsSend = new SportGpsSend();
            sportGpsSend.setUid(uid);
            ArrayList arrayList = new ArrayList();
            LinkedList<TB_sport_gps_segment> noUpGpsList = new LinkedList<>();
            LinkedList<TB_sport_gps_segment> canUpGpsList = new LinkedList<>();
            for (TB_sport_gps_segment gps_segment : gpsSegmentList) {
                if (!"1".equals(gps_segment.getGps_url()) && !"1".equals(gps_segment.getHeart_url()) && !"1".equals(gps_segment.getR1_url())) {
                    KLog.d("no2855---->上传信息: " + gps_segment.getStart_time());
                    GpsSend gpsSend = new GpsSend();
                    gpsSend.setData_from(gps_segment.getData_from());
                    gpsSend.setSource_type(2);
                    DateUtil dateUtil = new DateUtil(gps_segment.getStart_time(), true);
                    DateUtil dateUtil2 = new DateUtil(gps_segment.getEnd_time(), true);
                    gpsSend.setStart_time(dateUtil.getYyyyMMdd_HHmmssDate());
                    gpsSend.setEnd_time(dateUtil2.getYyyyMMdd_HHmmssDate());
                    gpsSend.setSport_type(gps_segment.getSport_type());
                    gpsSend.setStep(gps_segment.getStep());
                    gpsSend.setCalorie(gps_segment.getCalorie());
                    gpsSend.setDuration(gps_segment.getDuration());
                    gpsSend.setDistance(gps_segment.getDistance());
                    gpsSend.setAvg_pace(0);
                    gpsSend.setCadence(0);
                    if (!"1".equals(gps_segment.getGps_url())) {
                        gpsSend.setGps_data_url(gps_segment.getGps_url());
                    }
                    if (!"1".equals(gps_segment.getHeart_url())) {
                        gpsSend.setHr_data_url(gps_segment.getHeart_url());
                    }
                    if (!"1".equals(gps_segment.getR1_url())) {
                        gpsSend.setHeadset_data_url(gps_segment.getR1_url());
                    }
                    arrayList.add(gpsSend);
                    canUpGpsList.add(gps_segment);
                } else {
                    noUpGpsList.add(gps_segment);
                }
            }
            if (arrayList.size() > 0) {
                sportGpsSend.setData(arrayList);
                final LinkedList linkedList = canUpGpsList;
                NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                    public void onSuccess(ReturnCode returnCode) {
                        if (returnCode != null) {
                            try {
                                if (returnCode.getReturnCode() == 0) {
                                    Iterator it = linkedList.iterator();
                                    while (it.hasNext()) {
                                        TB_sport_gps_segment segment = (TB_sport_gps_segment) it.next();
                                        segment.setUpload(1);
                                        segment.update(segment.getId());
                                    }
                                }
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                    }
                }).upSportGpsSegment(uid, sportGpsSend);
            }
            if (noUpGpsList.size() > 0) {
                Iterator it = noUpGpsList.iterator();
                while (it.hasNext()) {
                    TB_sport_gps_segment segment = (TB_sport_gps_segment) it.next();
                    KLog.e("no2855---->存在需要上传的: " + segment.getStart_time() + " - " + segment.getData_from());
                    if ("1".equals(segment.getGps_url())) {
                        boolean hasHr = "1".equals(segment.getHeart_url());
                        KLog.e("no2855--> 心率存在: " + hasHr);
                        uploadGps(uid, segment.getStart_time(), segment.getEnd_time(), segment.getData_from(), hasHr, segment.getSport_type());
                    } else if ("1".equals(segment.getHeart_url())) {
                        uploadHr(true, segment.getUid(), segment.getStart_time(), segment.getEnd_time(), segment.getData_from(), segment.getSport_type());
                    } else if ("1".equals(segment.getR1_url())) {
                        updateR1(true, segment.getUid(), segment.getStart_time(), segment.getEnd_time(), segment.getData_from(), 0);
                    }
                }
            }
        }
    }

    public static void uploadAllTbBallSegment(long uid) {
        List<TB_sport_ball> sport_balls = DataSupport.where("uid=? and upload_type=0", uid + "").find(TB_sport_ball.class);
        if (sport_balls != null && sport_balls.size() > 0) {
            SportBallSend sportGpsSend = new SportBallSend();
            sportGpsSend.setUid(uid);
            ArrayList arrayList = new ArrayList();
            LinkedList<TB_sport_ball> noUpGpsList = new LinkedList<>();
            final LinkedList<TB_sport_ball> canUpGpsList = new LinkedList<>();
            for (TB_sport_ball gps_segment : sport_balls) {
                if (!"1".equals(gps_segment.getHeart_url())) {
                    BallSend gpsSend = new BallSend();
                    gpsSend.setData_from(gps_segment.getData_from());
                    DateUtil dateUtil = new DateUtil(gps_segment.getStart_time(), true);
                    DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
                    gpsSend.setStart_time(dateUtil.getYyyyMMdd_HHmmssDate());
                    gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
                    gpsSend.setSport_type(gps_segment.getSport_type());
                    gpsSend.setCalorie(gps_segment.getCalorie());
                    gpsSend.setDuration(gps_segment.getDuration());
                    if (!TextUtils.isEmpty(gps_segment.getHeart_url())) {
                        gpsSend.setHr_data_url(gps_segment.getHeart_url());
                        arrayList.add(gpsSend);
                        canUpGpsList.add(gps_segment);
                    }
                } else {
                    noUpGpsList.add(gps_segment);
                }
            }
            if (arrayList.size() > 0) {
                sportGpsSend.setData(arrayList);
                NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                    public void onSuccess(ReturnCode returnCode) {
                        if (returnCode != null) {
                            try {
                                if (returnCode.getReturnCode() == 0) {
                                    Iterator it = canUpGpsList.iterator();
                                    while (it.hasNext()) {
                                        TB_sport_ball gps_segment = (TB_sport_ball) it.next();
                                        gps_segment.setUpload_type(1);
                                        gps_segment.update(gps_segment.getId());
                                    }
                                }
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                    }
                }).upSportBallSegment(uid, sportGpsSend);
            }
            if (noUpGpsList.size() > 0) {
                Iterator it = noUpGpsList.iterator();
                while (it.hasNext()) {
                    TB_sport_ball segment = (TB_sport_ball) it.next();
                    uploadHr(false, segment.getUid(), segment.getStart_time(), segment.getEnd_time(), segment.getData_from(), segment.getSport_type());
                }
            }
        }
    }

    public static void uploadAllTbOtherSegment(long uid) {
        List<TB_sport_other> sport_balls = DataSupport.where("uid=? and upload_type=0", uid + "").find(TB_sport_other.class);
        if (sport_balls != null && sport_balls.size() > 0) {
            SportOtherSend sportGpsSend = new SportOtherSend();
            sportGpsSend.setUid(uid);
            ArrayList arrayList = new ArrayList();
            LinkedList<TB_sport_other> noUpGpsList = new LinkedList<>();
            final LinkedList<TB_sport_other> canUpGpsList = new LinkedList<>();
            for (TB_sport_other gps_segment : sport_balls) {
                if (!"1".equals(gps_segment.getHeart_url())) {
                    OtherSend gpsSend = new OtherSend();
                    gpsSend.setData_from(gps_segment.getData_from());
                    DateUtil dateUtil = new DateUtil(gps_segment.getStart_time(), true);
                    DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
                    gpsSend.setStart_time(dateUtil.getYyyyMMdd_HHmmssDate());
                    gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
                    gpsSend.setSport_type(gps_segment.getSport_type());
                    gpsSend.setCalorie(gps_segment.getCalorie());
                    gpsSend.setDuration(gps_segment.getDuration());
                    gpsSend.setDone_times(gps_segment.getDone_times());
                    if (!TextUtils.isEmpty(gps_segment.getHeart_url())) {
                        gpsSend.setHr_data_url(gps_segment.getHeart_url());
                        arrayList.add(gpsSend);
                        canUpGpsList.add(gps_segment);
                    }
                } else {
                    noUpGpsList.add(gps_segment);
                }
            }
            if (arrayList.size() > 0) {
                sportGpsSend.setData(arrayList);
                NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                    public void onSuccess(ReturnCode returnCode) {
                        if (returnCode != null) {
                            try {
                                if (returnCode.getReturnCode() == 0) {
                                    Iterator it = canUpGpsList.iterator();
                                    while (it.hasNext()) {
                                        TB_sport_other gps_segment = (TB_sport_other) it.next();
                                        gps_segment.setUpload_type(1);
                                        gps_segment.update(gps_segment.getId());
                                    }
                                }
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                    }
                }).upSportOtherSegment(uid, sportGpsSend);
            }
            if (noUpGpsList.size() > 0) {
                Iterator it = noUpGpsList.iterator();
                while (it.hasNext()) {
                    TB_sport_other segment = (TB_sport_other) it.next();
                    uploadHr(false, segment.getUid(), segment.getStart_time(), segment.getEnd_time(), segment.getData_from(), segment.getSport_type());
                }
            }
        }
    }

    public static void uploadAllTbSwimSegment(long uid) {
        List<TB_sport_swim> sport_swims = DataSupport.where("uid=? and upload_type=0", uid + "").find(TB_sport_swim.class);
        if (sport_swims != null && sport_swims.size() > 0) {
            SportSwimSend sportGpsSend = new SportSwimSend();
            ArrayList arrayList = new ArrayList();
            LinkedList<TB_sport_swim> noUpGpsList = new LinkedList<>();
            final LinkedList<TB_sport_swim> canUpGpsList = new LinkedList<>();
            for (TB_sport_swim gps_segment : sport_swims) {
                if (!(!"1".equals(gps_segment.getHeart_url()))) {
                    noUpGpsList.add(gps_segment);
                } else if (!TextUtils.isEmpty(gps_segment.getHeart_url())) {
                    SwimSend gpsSend = new SwimSend();
                    gpsSend.setData_from(gps_segment.getData_from());
                    DateUtil dateUtil = new DateUtil(gps_segment.getStart_time(), true);
                    DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
                    gpsSend.setStart_time(dateUtil.getYyyyMMdd_HHmmssDate());
                    gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
                    gpsSend.setSwim_laps(gps_segment.getLaps());
                    gpsSend.setUid(gps_segment.getUid());
                    gpsSend.setDistance(gps_segment.getDistance());
                    gpsSend.setStroke_times(gps_segment.getDone_times());
                    gpsSend.setCalorie(gps_segment.getCalorie());
                    gpsSend.setDuration(gps_segment.getDuration());
                    gpsSend.setSwim_detail_data(gps_segment.getHeart_url());
                    arrayList.add(gpsSend);
                    canUpGpsList.add(gps_segment);
                }
            }
            if (arrayList.size() > 0) {
                sportGpsSend.setData(arrayList);
                NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                    public void onSuccess(ReturnCode returnCode) {
                        if (returnCode != null) {
                            try {
                                if (returnCode.getReturnCode() == 0) {
                                    Iterator it = canUpGpsList.iterator();
                                    while (it.hasNext()) {
                                        TB_sport_swim gps_segment = (TB_sport_swim) it.next();
                                        gps_segment.setUpload_type(1);
                                        gps_segment.update(gps_segment.getId());
                                    }
                                }
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                    }
                }).upSportSwimSegment(uid, sportGpsSend);
            }
            if (noUpGpsList.size() > 0) {
                Iterator it = noUpGpsList.iterator();
                while (it.hasNext()) {
                    TB_sport_swim segment = (TB_sport_swim) it.next();
                    KLog.e("no2855-->swim no up: " + segment.getId());
                    uploadHr(false, segment.getUid(), segment.getStart_time(), segment.getEnd_time(), segment.getData_from(), segment.getSport_type());
                }
            }
        }
    }

    public static void uploadAll1Service(long uid) {
    }

    public static void uploadGpsServiceUrl(long uid) {
        final List<TB_sport_gps_segment> tbSportGpsSegments = DataSupport.where("uid=? and url_type>0 and upload=1", uid + "").find(TB_sport_gps_segment.class);
        if (tbSportGpsSegments != null && tbSportGpsSegments.size() > 0) {
            UpSportGpsUrl upSportGpsUrl = new UpSportGpsUrl();
            upSportGpsUrl.getClass();
            SportGpsUrl sportGpsUrl = new SportGpsUrl();
            List<SportGpsUrl> sportGpsUrlList = new ArrayList<>();
            upSportGpsUrl.setUid(uid);
            for (TB_sport_gps_segment tbSportGpsSegment : tbSportGpsSegments) {
                sportGpsUrl.setData_from(tbSportGpsSegment.getData_from());
                sportGpsUrl.setStart_time(new DateUtil(tbSportGpsSegment.getStart_time(), true).getY_M_D_H_M_S());
                if (!"1".equals(tbSportGpsSegment.getGps_url())) {
                    sportGpsUrl.setGps_data_url(tbSportGpsSegment.getGps_url());
                }
                if (!"1".equals(tbSportGpsSegment.getHeart_url())) {
                    sportGpsUrl.setHr_data_url(tbSportGpsSegment.getHeart_url());
                }
                if (!"1".equals(tbSportGpsSegment.getR1_url())) {
                    sportGpsUrl.setHeadset_data_url(tbSportGpsSegment.getR1_url());
                }
                sportGpsUrl.setSource_type(tbSportGpsSegment.getSource_type());
                sportGpsUrlList.add(sportGpsUrl);
            }
            if (sportGpsUrlList.size() > 0) {
                upSportGpsUrl.setData(sportGpsUrlList);
                NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                    public void onSuccess(ReturnCode returnCode) {
                        if (returnCode != null) {
                            try {
                                if (returnCode.getReturnCode() == 0) {
                                    for (TB_sport_gps_segment tbSportGpsSegment : tbSportGpsSegments) {
                                        tbSportGpsSegment.setToDefault("url_type");
                                        tbSportGpsSegment.update(tbSportGpsSegment.getId());
                                    }
                                }
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                        KLog.d("yanxi...tbSportGpsSegment 失败");
                    }
                }).updateSportGpsUrl(upSportGpsUrl);
            }
        }
        uploadGpsOneUrlServer(uid);
    }

    public static void uploadGpsServiceUrl(final TB_sport_gps_segment tbSportGpsSegment) {
        UpSportGpsUrl upSportGpsUrl = new UpSportGpsUrl();
        upSportGpsUrl.getClass();
        SportGpsUrl sportGpsUrl = new SportGpsUrl();
        List<SportGpsUrl> sportGpsUrlList = new ArrayList<>();
        upSportGpsUrl.setUid(tbSportGpsSegment.getUid());
        sportGpsUrl.setData_from(tbSportGpsSegment.getData_from());
        sportGpsUrl.setStart_time(new DateUtil(tbSportGpsSegment.getStart_time(), true).getY_M_D_H_M_S());
        if (!"1".equals(tbSportGpsSegment.getGps_url())) {
            sportGpsUrl.setGps_data_url(tbSportGpsSegment.getGps_url());
        }
        if (!"1".equals(tbSportGpsSegment.getHeart_url())) {
            sportGpsUrl.setHr_data_url(tbSportGpsSegment.getHeart_url());
        }
        if (!"1".equals(tbSportGpsSegment.getR1_url())) {
            sportGpsUrl.setHeadset_data_url(tbSportGpsSegment.getR1_url());
        }
        sportGpsUrl.setSource_type(tbSportGpsSegment.getSource_type());
        sportGpsUrlList.add(sportGpsUrl);
        if (sportGpsUrlList.size() > 0) {
            upSportGpsUrl.setData(sportGpsUrlList);
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode returnCode) {
                    if (returnCode != null) {
                        try {
                            if (returnCode.getReturnCode() == 0) {
                                tbSportGpsSegment.setToDefault("url_type");
                                tbSportGpsSegment.update(tbSportGpsSegment.getId());
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                    KLog.d("yanxi...tbSportGpsSegment 失败");
                }
            }).updateSportGpsUrl(upSportGpsUrl);
        }
    }

    public static void uploadGpsOneUrlServer(long uid) {
        List<TB_sport_gps_segment> tbSportGpsSegments = DataSupport.where("uid=? and gps_url=1 and upload=1", uid + "").find(TB_sport_gps_segment.class);
        if (tbSportGpsSegments != null && tbSportGpsSegments.size() > 0) {
            for (TB_sport_gps_segment tbSportGpsSegment : tbSportGpsSegments) {
                uploadMyGps(tbSportGpsSegment);
            }
        }
        List<TB_sport_gps_segment> tbSportHrSegments = DataSupport.where("uid=? and heart_url=1 and upload=1", uid + "").find(TB_sport_gps_segment.class);
        if (tbSportGpsSegments != null && tbSportGpsSegments.size() > 0) {
            for (TB_sport_gps_segment tbSportGpsSegment2 : tbSportHrSegments) {
                uploadMyHr(tbSportGpsSegment2);
            }
        }
    }

    public static void uploadMyGps(final TB_sport_gps_segment gpsSegment) {
        String path1 = Environment.getExternalStorageDirectory() + LogPath.GPS_PATH + gpsSegment.getUid() + "_gps_" + gpsSegment.getStart_time() + "_" + gpsSegment.getData_from();
        String txtFile = path1 + ".txt";
        final String zipFile = path1 + ".zip";
        if (!new File(txtFile).exists()) {
            DataUtil.writeBlueOneGps2SD(gpsSegment);
        }
        if (new File(txtFile).exists()) {
            String st = new DateUtil(gpsSegment.getStart_time(), true).getYyyyMMdd_HHmmssDate();
            String ed = new DateUtil(gpsSegment.getEnd_time(), true).getYyyyMMdd_HHmmssDate();
            if (ZipUtil.zip(txtFile, zipFile)) {
                NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                    public void onSuccess(UpSDFileCode upSDFileCode) {
                        FileUtils.deleteFile(new File(zipFile));
                        if (upSDFileCode != null) {
                            try {
                                if (upSDFileCode.getRetCode() == 0) {
                                    DataUtil.upGpsSportOneUrl(upSDFileCode.getUrl(), 0, gpsSegment.getUid(), gpsSegment.getStart_time(), gpsSegment.getData_from(), 0);
                                }
                                SportDeviceNetWorkUtil.uploadGpsServiceUrl((TB_sport_gps_segment) DataSupport.find(TB_sport_gps_segment.class, gpsSegment.getId()));
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                        FileUtils.deleteFile(new File(zipFile));
                    }
                }).upSportGpsFile(gpsSegment.getUid(), st, ed, gpsSegment.getData_from(), new File(zipFile));
            }
        }
    }

    public static void uploadMyHr(final TB_sport_gps_segment gpsSegment) {
        String path1 = Environment.getExternalStorageDirectory() + LogPath.HR_PATH + gpsSegment.getUid() + "_hr_" + gpsSegment.getStart_time() + "_" + gpsSegment.getData_from();
        String txtFile = path1 + ".txt";
        final String zipFile = path1 + ".zip";
        if (!new File(txtFile).exists()) {
            DataUtil.saveHr2File((TB_heartrate_data) DataSupport.where("uid=? and data_from=? and start_time=?", gpsSegment.getUid() + "", gpsSegment.getData_from(), gpsSegment.getStart_time() + "").findFirst(TB_heartrate_data.class), null, false, false);
        }
        if (new File(txtFile).exists()) {
            String st = new DateUtil(gpsSegment.getStart_time(), true).getYyyyMMdd_HHmmssDate();
            String ed = new DateUtil(gpsSegment.getEnd_time(), true).getYyyyMMdd_HHmmssDate();
            if (ZipUtil.zip(txtFile, zipFile)) {
                NetFactory.getInstance().getClient(new MyCallback<UpSDFileCode>() {
                    public void onSuccess(UpSDFileCode upSDFileCode) {
                        FileUtils.deleteFile(new File(zipFile));
                        if (upSDFileCode != null) {
                            try {
                                if (upSDFileCode.getRetCode() == 0) {
                                    DataUtil.upGpsSportOneUrl(upSDFileCode.getUrl(), 1, gpsSegment.getUid(), gpsSegment.getStart_time(), gpsSegment.getData_from(), 0);
                                }
                                SportDeviceNetWorkUtil.uploadGpsServiceUrl((TB_sport_gps_segment) DataSupport.find(TB_sport_gps_segment.class, gpsSegment.getId()));
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }

                    public void onFail(Throwable e) {
                        FileUtils.deleteFile(new File(zipFile));
                    }
                }).upSportGpsFile(gpsSegment.getUid(), st, ed, gpsSegment.getData_from(), new File(zipFile));
            }
        }
    }
}
