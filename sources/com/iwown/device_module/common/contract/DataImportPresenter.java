package com.iwown.device_module.common.contract;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.contract.DataImportContract.DataImportView;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.SportBallSend;
import com.iwown.device_module.common.network.data.req.SportBallSend.BallSend;
import com.iwown.device_module.common.network.data.req.SportGpsSend;
import com.iwown.device_module.common.network.data.req.SportGpsSend.GpsSend;
import com.iwown.device_module.common.network.data.req.SportOtherSend;
import com.iwown.device_module.common.network.data.req.SportOtherSend.OtherSend;
import com.iwown.device_module.common.network.data.resp.ReturnCode;
import com.iwown.device_module.common.network.data.resp.SportData;
import com.iwown.device_module.common.network.data.resp.SportDownCode;
import com.iwown.device_module.common.sql.TB_sport_ball;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import com.iwown.device_module.common.sql.TB_sport_other;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class DataImportPresenter implements com.iwown.device_module.common.contract.DataImportContract.DataImportPresenter {
    /* access modifiers changed from: private */
    public DataImportView dataImportView;
    private DateUtil dateUtil;
    /* access modifiers changed from: private */
    public int downNum = 0;
    /* access modifiers changed from: private */
    public boolean isClientOk = false;
    /* access modifiers changed from: private */
    public int times = 0;

    public DataImportPresenter(DataImportView dataImportView2, DateUtil dateUtil2) {
        this.dataImportView = dataImportView2;
        this.dateUtil = dateUtil2;
    }

    public void initEvent() {
        this.times = 0;
        this.isClientOk = false;
    }

    public void downloadTSport(long uid) {
        if (this.dateUtil == null) {
            this.dateUtil = new DateUtil();
        }
        this.downNum = 0;
        this.dateUtil.addDay(-10);
        downloadSport(uid, 10, this.dateUtil.getSyyyyMMddDate());
    }

    /* access modifiers changed from: private */
    public void againDown(long uid) {
        this.dateUtil.addDay(-10);
        downloadSport(uid, 10, this.dateUtil.getSyyyyMMddDate());
    }

    public void downloadSport(final long uid, int ds, String st) {
        this.dataImportView.onProgress(0);
        this.isClientOk = false;
        NetFactory.getInstance().getClient(new MyCallback<SportDownCode>() {
            public void onSuccess(SportDownCode sportDownCode) {
                KLog.e("no2855http下载28数据成功--");
                DataImportPresenter.this.isClientOk = true;
                if (sportDownCode != null && sportDownCode.getRetCode() == 0) {
                    DataImportPresenter.this.downNum = DataImportPresenter.this.downNum + 1;
                    if (DataImportPresenter.this.downNum < 3) {
                        DataImportPresenter.this.againDown(uid);
                        return;
                    }
                    DataImportPresenter.this.downloadTBSport(sportDownCode);
                    DataImportPresenter.this.dataImportView.loadSuccess();
                }
            }

            public void onFail(Throwable e) {
                if (DataImportPresenter.this.isClientOk) {
                }
            }
        }).get28Data(uid, ds, st);
    }

    public void sportToGpsSegment(long uid, long startTime, long endTime) {
        KLog.d("no2855-->准备开始入新表:启用阶段 ");
        List<TB_v3_sport_data> sportDatas = DataSupport.where("uid=? and start_uxtime>=? and start_uxtime<=?", uid + "", startTime + "", endTime + "").find(TB_v3_sport_data.class);
        if (sportDatas == null || sportDatas.size() <= 0) {
            KLog.d("no2855-->准备开始入新表:表数据为空>]?? ");
        } else {
            for (TB_v3_sport_data sportData : sportDatas) {
                Detail_data detailData = (Detail_data) JsonTool.fromJson(sportData.getDetail_data(), Detail_data.class);
                if (!(sportData.getStart_uxtime() == 0 || sportData.getEnd_uxtime() == 0)) {
                    int sportTime = (int) (sportData.getEnd_uxtime() - sportData.getStart_uxtime());
                    if (sportData.getSport_type() != 1 && (sportTime >= 300 || detailData.getDistance() >= 500.0f)) {
                        KLog.d("no2855-->准备开始入新表: " + sportData.getStart_uxtime());
                        int tType = DataUtil.getSportDataTYpe(sportData.getSport_type());
                        if (tType == 0) {
                            sport2Gps(sportData, detailData);
                        } else if (tType == 1) {
                            sport2Ball(sportData, detailData);
                        } else if (tType == 2) {
                            sport2Other(sportData, detailData);
                        }
                    }
                }
            }
        }
        this.dataImportView.sportToGpsOk();
    }

    public void downloadStatisticsSport(long uid) {
    }

    private void downloadCountGps(long uid) {
    }

    private void sportToGpsSegmentOld(long uid, long startTime, long endTime) {
        KLog.d("no2855-->准备开始入新表:启用阶段 ");
        List<TB_v3_sport_data> sportDatas = DataSupport.where("uid=? and start_uxtime>？and start_uxtime<?", uid + "", startTime + "", endTime + "").find(TB_v3_sport_data.class);
        if (sportDatas == null || sportDatas.size() <= 0) {
            KLog.d("no2855-->准备开始入新表:表数据为空>]?? ");
            return;
        }
        for (TB_v3_sport_data sportData : sportDatas) {
            Detail_data detailData = (Detail_data) JsonTool.fromJson(sportData.getDetail_data(), Detail_data.class);
            if (sportData.getSport_type() != 1 && (detailData.getActivity() >= 5 || detailData.getDistance() >= 500.0f)) {
                KLog.d("no2855-->准备开始入新表: " + sportData.getStart_uxtime());
                int tType = DataUtil.getSportDataTYpe(sportData.getSport_type());
                if (tType == 0) {
                    sport2Gps(sportData, detailData);
                } else if (tType == 1) {
                    sport2Ball(sportData, detailData);
                } else if (tType == 2) {
                    sport2Other(sportData, detailData);
                }
            }
        }
    }

    private void sportToGpsSegmentNew(SportDownCode resultCode) {
        List<SportData> content = resultCode.getContent();
        if (content != null && content.size() > 0) {
            for (SportData sportData : content) {
                if (sportData.getActivity() >= 5 || sportData.getDistance() >= 500.0f) {
                    KLog.d("no2855-->准备开始入新表: " + sportData.getStart_time());
                    int tType = DataUtil.getSportDataTYpe(sportData.getType());
                    if (tType == 0) {
                        sport2Gps(sportData);
                    } else if (tType == 1) {
                        sport2Ball(sportData);
                    } else if (tType == 2) {
                        sport2Other(sportData);
                    }
                }
            }
        }
    }

    public void uploadGpsSegment(long uid, long startTime, long endTime) {
        this.dataImportView.onProgress(0);
        final List<TB_sport_gps_segment> gpsSegments = DataSupport.where("uid=? and upload=0 and start_time>=? and start_time<=?", uid + "", startTime + "", endTime + "").find(TB_sport_gps_segment.class);
        if (gpsSegments == null || gpsSegments.size() == 0) {
            this.times = 0;
            KLog.d("no2855--> gps无数据需要更新的数据: ");
            uploadBallSegment(uid, startTime, endTime);
            this.dataImportView.uploadGpsSuccess(0);
            return;
        }
        SportGpsSend sportGpsSend = new SportGpsSend();
        sportGpsSend.setUid(uid);
        ArrayList arrayList = new ArrayList();
        LinkedList<Long> idList = new LinkedList<>();
        for (TB_sport_gps_segment gps_segment : gpsSegments) {
            KLog.e("no2855--> gps准备更新的数据: " + gps_segment.getStart_time());
            if (!"1".equals(gps_segment.getGps_url()) && !"1".equals(gps_segment.getHeart_url()) && !"1".equals(gps_segment.getR1_url())) {
                GpsSend gpsSend = new GpsSend();
                gpsSend.setData_from(gps_segment.getData_from());
                gpsSend.setSource_type(2);
                DateUtil dateUtil2 = new DateUtil(gps_segment.getStart_time(), true);
                DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
                gpsSend.setStart_time(dateUtil2.getYyyyMMdd_HHmmssDate());
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
                idList.add(Long.valueOf(gps_segment.getId()));
                arrayList.add(gpsSend);
            }
        }
        if (arrayList.size() > 0) {
            sportGpsSend.setData(arrayList);
            final long j = uid;
            final long j2 = startTime;
            final long j3 = endTime;
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode returnCode) {
                    if (returnCode != null && returnCode.getReturnCode() == 0) {
                        for (TB_sport_gps_segment gps_segment : gpsSegments) {
                            gps_segment.setUpload(1);
                            gps_segment.update(gps_segment.getId());
                            KLog.d("no2855--> gps更新的数据: " + gps_segment.getStart_time());
                        }
                        DataImportPresenter.this.dataImportView.uploadGpsSuccess(0);
                        DataImportPresenter.this.uploadBallSegment(j, j2, j3);
                    }
                }

                public void onFail(Throwable e) {
                    KLog.d("no2855--> gps更新失败: ");
                    DataImportPresenter.this.times = DataImportPresenter.this.times + 1;
                    if (DataImportPresenter.this.times > 3) {
                        DataImportPresenter.this.dataImportView.uploadGpsFail(0);
                        DataImportPresenter.this.uploadBallSegment(j, j2, j3);
                        DataImportPresenter.this.times = 0;
                        return;
                    }
                    DataImportPresenter.this.uploadGpsSegment(j, j2, j3);
                }
            }).upSportGpsSegment(uid, sportGpsSend);
            return;
        }
        this.dataImportView.uploadGpsSuccess(0);
        uploadBallSegment(uid, startTime, endTime);
    }

    public void uploadBallSegment(long uid, long startTime, long endTime) {
        this.dataImportView.onProgress(0);
        final List<TB_sport_ball> sport_balls = DataSupport.where("uid=? and upload_type=0 and start_time>=? and start_time<=? ", uid + "", startTime + "", endTime + "").find(TB_sport_ball.class);
        if (sport_balls == null || sport_balls.size() <= 0) {
            this.times = 0;
            KLog.d("no2855--> ball无数据需要更新的数据: ");
            this.dataImportView.uploadGpsSuccess(1);
            uploadOtherSegment(uid, startTime, endTime);
            return;
        }
        SportBallSend sportGpsSend = new SportBallSend();
        sportGpsSend.setUid(uid);
        ArrayList arrayList = new ArrayList();
        LinkedList<Long> idList = new LinkedList<>();
        for (TB_sport_ball gps_segment : sport_balls) {
            boolean canUp = !"1".equals(gps_segment.getHeart_url());
            KLog.e("no2855--> ball准备更新的数据: " + gps_segment.getStart_time() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + canUp);
            if (canUp) {
                BallSend gpsSend = new BallSend();
                gpsSend.setData_from(gps_segment.getData_from());
                DateUtil dateUtil2 = new DateUtil(gps_segment.getStart_time(), true);
                DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
                gpsSend.setStart_time(dateUtil2.getYyyyMMdd_HHmmssDate());
                gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
                gpsSend.setSport_type(gps_segment.getSport_type());
                gpsSend.setCalorie(gps_segment.getCalorie());
                gpsSend.setDuration(gps_segment.getDuration());
                gpsSend.setHr_data_url(gps_segment.getHeart_url());
                idList.add(Long.valueOf(gps_segment.getId()));
                arrayList.add(gpsSend);
            }
        }
        if (arrayList.size() > 0) {
            sportGpsSend.setData(arrayList);
            final long j = uid;
            final long j2 = startTime;
            final long j3 = endTime;
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode returnCode) {
                    if (returnCode == null) {
                        try {
                            KLog.d("no2855--> ball更新的数据:null ");
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    } else if (returnCode.getReturnCode() == 0) {
                        for (TB_sport_ball gps_segment : sport_balls) {
                            gps_segment.setUpload_type(1);
                            KLog.d("no2855--> ball更新的数据: " + gps_segment.getStart_time());
                            gps_segment.update(gps_segment.getId());
                        }
                        DataImportPresenter.this.dataImportView.uploadGpsSuccess(1);
                        DataImportPresenter.this.uploadOtherSegment(j, j2, j3);
                    }
                }

                public void onFail(Throwable e) {
                    KLog.d("no2855--> ball更新失败: ");
                    DataImportPresenter.this.times = DataImportPresenter.this.times + 1;
                    if (DataImportPresenter.this.times > 3) {
                        DataImportPresenter.this.dataImportView.uploadGpsFail(1);
                        DataImportPresenter.this.uploadOtherSegment(j, j2, j3);
                        DataImportPresenter.this.times = 0;
                        return;
                    }
                    DataImportPresenter.this.uploadBallSegment(j, j2, j3);
                }
            }).upSportBallSegment(uid, sportGpsSend);
            return;
        }
        this.times = 0;
        KLog.d("no2855--> ball111无数据需要更新的数据: ");
        this.dataImportView.uploadGpsSuccess(1);
        uploadOtherSegment(uid, startTime, endTime);
    }

    public void uploadOtherSegment(long uid, long startTime, long endTime) {
        this.dataImportView.onProgress(0);
        final List<TB_sport_other> sport_balls = DataSupport.where("uid=? and upload_type=0 and start_time>=? and start_time<=? ", uid + "", startTime + "", endTime + "").find(TB_sport_other.class);
        if (sport_balls == null || sport_balls.size() <= 0) {
            KLog.d("no2855--> other无数据需要更新的数据: ");
            this.dataImportView.uploadGpsSuccess(2);
            this.times = 0;
            return;
        }
        SportOtherSend sportGpsSend = new SportOtherSend();
        sportGpsSend.setUid(uid);
        ArrayList arrayList = new ArrayList();
        LinkedList<TB_sport_other> noUpGpsList = new LinkedList<>();
        LinkedList<Long> idList = new LinkedList<>();
        for (TB_sport_other gps_segment : sport_balls) {
            KLog.e("no2855--> other准备更新的数据: " + gps_segment.getStart_time());
            if (!"1".equals(gps_segment.getHeart_url())) {
                OtherSend gpsSend = new OtherSend();
                gpsSend.setData_from(gps_segment.getData_from());
                DateUtil dateUtil2 = new DateUtil(gps_segment.getStart_time(), true);
                DateUtil ed = new DateUtil(gps_segment.getEnd_time(), true);
                gpsSend.setStart_time(dateUtil2.getYyyyMMdd_HHmmssDate());
                gpsSend.setEnd_time(ed.getYyyyMMdd_HHmmssDate());
                gpsSend.setSport_type(gps_segment.getSport_type());
                gpsSend.setCalorie(gps_segment.getCalorie());
                gpsSend.setDuration(gps_segment.getDuration());
                gpsSend.setDone_times(gps_segment.getDone_times());
                gpsSend.setHr_data_url(gps_segment.getHeart_url());
                idList.add(Long.valueOf(gps_segment.getId()));
                arrayList.add(gpsSend);
            } else {
                noUpGpsList.add(gps_segment);
            }
        }
        if (arrayList.size() > 0) {
            sportGpsSend.setData(arrayList);
            final long j = uid;
            final long j2 = startTime;
            final long j3 = endTime;
            NetFactory.getInstance().getClient(new MyCallback<ReturnCode>() {
                public void onSuccess(ReturnCode returnCode) {
                    if (returnCode != null) {
                        try {
                            if (returnCode.getReturnCode() == 0) {
                                for (TB_sport_other gps_segment : sport_balls) {
                                    gps_segment.setUpload_type(1);
                                    KLog.d("no2855--> other更新的数据: " + gps_segment.getStart_time());
                                    gps_segment.update(gps_segment.getId());
                                }
                                DataImportPresenter.this.dataImportView.uploadGpsSuccess(2);
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }

                public void onFail(Throwable e) {
                    KLog.d("no2855--> other更新失败: ");
                    DataImportPresenter.this.times = DataImportPresenter.this.times + 1;
                    if (DataImportPresenter.this.times <= 3) {
                        DataImportPresenter.this.uploadOtherSegment(j, j2, j3);
                        return;
                    }
                    DataImportPresenter.this.dataImportView.uploadGpsFail(2);
                    DataImportPresenter.this.times = 0;
                }
            }).upSportOtherSegment(uid, sportGpsSend);
            return;
        }
        this.dataImportView.uploadGpsSuccess(2);
        this.times = 0;
    }

    private void sport2Gps(TB_v3_sport_data sportData, Detail_data detailData) {
        if (!DataSupport.isExist(TB_sport_gps_segment.class, "uid=? and start_time=? and data_from=?", sportData.getUid() + "", sportData.getStart_uxtime() + "", sportData.getData_from())) {
            TB_sport_gps_segment gpsSegment = new TB_sport_gps_segment();
            int sportType = sportData.getSport_type();
            int mType = 0;
            if (sportType == 7) {
                mType = 0;
            } else if (sportType == 136) {
                mType = 1;
            } else if (sportType == 5) {
                mType = 3;
            } else if (sportType == 147) {
                mType = 2;
            }
            gpsSegment.setData_from(sportData.getData_from());
            gpsSegment.setUpload(0);
            gpsSegment.setSource_type(2);
            gpsSegment.setUid(sportData.getUid());
            gpsSegment.setCalorie((float) sportData.getCalorie());
            gpsSegment.setDuration(detailData.getActivity() * 60);
            gpsSegment.setDistance(detailData.getDistance());
            gpsSegment.setStart_time(sportData.getStart_uxtime());
            gpsSegment.setEnd_time(sportData.getEnd_uxtime());
            gpsSegment.setStep(detailData.getStep());
            gpsSegment.setSport_type(mType);
            gpsSegment.setMtime(new DateUtil(gpsSegment.getStart_time(), true).getY_M_D_H_M_S());
            gpsSegment.save();
        }
    }

    private void sport2Gps(SportData sportData) {
        if (!DataSupport.isExist(TB_sport_gps_segment.class, "uid=? and start_time=? and data_from=?", sportData.getUid() + "", sportData.getStart_time() + "", sportData.getData_from())) {
            TB_sport_gps_segment gpsSegment = new TB_sport_gps_segment();
            int sportType = sportData.getType();
            int mType = 0;
            if (sportType == 7) {
                mType = 0;
            } else if (sportType == 136) {
                mType = 1;
            } else if (sportType == 5) {
                mType = 3;
            } else if (sportType == 147) {
                mType = 2;
            }
            gpsSegment.setData_from(sportData.getData_from());
            gpsSegment.setUpload(0);
            gpsSegment.setSource_type(2);
            gpsSegment.setUid(sportData.getUid());
            gpsSegment.setCalorie(sportData.getCalorie());
            gpsSegment.setDuration(sportData.getActivity() * 60);
            gpsSegment.setDistance(sportData.getDistance());
            gpsSegment.setStart_time(sportData.getStart_time());
            gpsSegment.setEnd_time(sportData.getEnd_time());
            gpsSegment.setStep(sportData.getSteps());
            gpsSegment.setSport_type(mType);
            gpsSegment.setMtime(new DateUtil(gpsSegment.getStart_time(), true).getY_M_D_H_M_S());
            gpsSegment.save();
        }
    }

    private void sport2Ball(TB_v3_sport_data sportData, Detail_data detailData) {
        if (!DataSupport.isExist(TB_sport_ball.class, "uid=? and start_time=? and data_from=?", sportData.getUid() + "", sportData.getStart_uxtime() + "", sportData.getData_from())) {
            TB_sport_ball sportBall = new TB_sport_ball();
            sportBall.setUpload_type(0);
            sportBall.setCalorie((float) sportData.getCalorie());
            sportBall.setData_from(sportData.getData_from());
            sportBall.setDuration(detailData.getActivity() * 60);
            sportBall.setEnd_time(sportData.getEnd_uxtime());
            sportBall.setSport_type(sportData.getSport_type());
            sportBall.setStart_time(sportData.getStart_uxtime());
            sportBall.setUid(sportData.getUid());
            sportBall.save();
        }
    }

    private void sport2Ball(SportData sportData) {
        if (!DataSupport.isExist(TB_sport_ball.class, "uid=? and start_time=? and data_from=?", sportData.getUid() + "", sportData.getStart_time() + "", sportData.getData_from())) {
            TB_sport_ball sportBall = new TB_sport_ball();
            sportBall.setUpload_type(0);
            sportBall.setCalorie(sportData.getCalorie());
            sportBall.setData_from(sportData.getData_from());
            sportBall.setDuration(sportData.getActivity() * 60);
            sportBall.setEnd_time(sportData.getEnd_time());
            sportBall.setSport_type(sportData.getType());
            sportBall.setStart_time(sportData.getStart_time());
            sportBall.setUid(sportData.getUid());
            sportBall.save();
        }
    }

    private void sport2Other(TB_v3_sport_data sportData, Detail_data detailData) {
        if (!DataSupport.isExist(TB_sport_other.class, "uid=? and start_time=? and data_from=?", sportData.getUid() + "", sportData.getStart_uxtime() + "", sportData.getData_from())) {
            TB_sport_other sportOther = new TB_sport_other();
            sportOther.setUpload_type(0);
            sportOther.setCalorie((float) sportData.getCalorie());
            sportOther.setData_from(sportData.getData_from());
            sportOther.setDuration(detailData.getActivity() * 60);
            sportOther.setEnd_time(sportData.getEnd_uxtime());
            sportOther.setSport_type(sportData.getSport_type());
            sportOther.setStart_time(sportData.getStart_uxtime());
            sportOther.setUid(sportData.getUid());
            sportOther.setDone_times(detailData.getCount());
            sportOther.save();
        }
    }

    private void sport2Other(SportData sportData) {
        if (!DataSupport.isExist(TB_sport_other.class, "uid=? and start_time=? and data_from=?", sportData.getUid() + "", sportData.getStart_time() + "", sportData.getData_from())) {
            TB_sport_other sportOther = new TB_sport_other();
            sportOther.setUpload_type(0);
            sportOther.setCalorie(sportData.getCalorie());
            sportOther.setData_from(sportData.getData_from());
            sportOther.setDuration(sportData.getActivity() * 60);
            sportOther.setEnd_time(sportData.getEnd_time());
            sportOther.setSport_type(sportData.getType());
            sportOther.setStart_time(sportData.getStart_time());
            sportOther.setUid(sportData.getUid());
            sportOther.setDone_times(sportData.getCount());
            sportOther.save();
        }
    }

    /* access modifiers changed from: private */
    public void downloadTBSport(SportDownCode resultCode) {
        List<SportData> content = resultCode.getContent();
        if (content.size() > 0) {
            for (SportData sportData : content) {
                if (!DataSupport.isExist(TB_v3_sport_data.class, "uid=? and start_uxtime=? and data_from=? and sport_type=?", sportData.getUid() + "", sportData.getStart_time() + "", sportData.getData_from() + "", sportData.getType() + "")) {
                    Detail_data detail_data = new Detail_data();
                    detail_data.setActivity(sportData.getActivity());
                    detail_data.setCount(sportData.getCount());
                    if (sportData.getDistance() <= 0.0f || ((float) sportData.getSteps()) / sportData.getDistance() < 1000.0f) {
                        detail_data.setDistance(sportData.getDistance());
                    } else {
                        detail_data.setDistance(sportData.getDistance() * 1000.0f);
                    }
                    detail_data.setStep(sportData.getSteps());
                    String deStr = JsonTool.toJson(detail_data);
                    TB_v3_sport_data tbData = new TB_v3_sport_data();
                    tbData.set_uploaded(1);
                    tbData.setStart_uxtime(sportData.getStart_time());
                    tbData.setEnd_uxtime(sportData.getEnd_time());
                    tbData.setCalorie((double) sportData.getCalorie());
                    tbData.setData_from(sportData.getData_from());
                    tbData.setUid(sportData.getUid());
                    if (sportData.getType() != 256) {
                        tbData.setSport_type(sportData.getType());
                    } else {
                        tbData.setSport_type(1);
                    }
                    tbData.setDetail_data(deStr);
                    DateUtil dateUtil2 = new DateUtil(sportData.getStart_time(), true);
                    tbData.setYear(dateUtil2.getYear());
                    tbData.setMonth(dateUtil2.getMonth());
                    tbData.setDay(dateUtil2.getDay());
                    tbData.setWeek(dateUtil2.getWeekOfYear());
                    tbData.setStart_time((dateUtil2.getHour() * 60) + dateUtil2.getMinute());
                    DateUtil dateUtil1End = new DateUtil(sportData.getEnd_time(), true);
                    tbData.setEnd_time((dateUtil1End.getHour() * 60) + dateUtil1End.getMinute());
                    tbData.save();
                }
            }
        }
    }

    public void getAllGpsTotal() {
    }
}
