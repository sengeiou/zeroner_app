package com.iwown.sport_module.gps.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.response.UpSDFileCode;
import com.socks.library.KLog;
import java.io.File;
import org.litepal.crud.DataSupport;

public class BaseShowMapActivity extends Activity {
    private String dataFrom;
    Handler handler;
    /* access modifiers changed from: private */
    public ProgressDialog progressDialog;
    protected int target = 0;
    protected long time;
    protected long uid;

    /* access modifiers changed from: protected */
    public void testUpFileAndDetailData() {
        this.handler = new Handler();
        initPross();
        TB_location_history history = (TB_location_history) DataSupport.where("uid=? and time_id=?", this.uid + "", this.time + "").findFirst(TB_location_history.class);
        if (history != null && history.getIs_upload() != 1) {
            Log.e("httpAPI", "history is null-->  " + this.time);
            final String msd = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH;
            long startTime = history.getTime_id();
            long endTime = history.getEnd_time();
            final String outputName = this.uid + "_gps_" + startTime + "_Android.zip";
            File file = new File(msd + outputName);
            if (file.exists()) {
                upGpsFile(file, startTime, endTime);
                return;
            }
            final long j = startTime;
            final long j2 = endTime;
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    BaseShowMapActivity.this.upGpsFile(new File(msd + outputName), j, j2);
                }
            }, 3000);
        }
    }

    /* access modifiers changed from: private */
    public void upGpsFile(File file, final long startTime, long endTime) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.e("no2855--> detail onSuccess");
                L.file("上传一段运动成功: " + BaseShowMapActivity.this.uid + "/" + BaseShowMapActivity.this.time + "\r\n上传时间： " + System.currentTimeMillis(), 3);
                if (BaseShowMapActivity.this.progressDialog != null) {
                    BaseShowMapActivity.this.progressDialog.dismiss();
                }
                if (o instanceof UpSDFileCode) {
                    KLog.d("no2855--> " + ((UpSDFileCode) o).getUrl());
                    ModuleRouteSportService.getInstance().updateTBGpsSegmentUrl(((UpSDFileCode) o).getUrl(), 0, BaseShowMapActivity.this.uid, startTime, "Android%", 0, false);
                }
                BaseShowMapActivity.this.showMap();
            }

            public void onFail(Throwable e) {
                KLog.e("no2855--> detail onFail");
                L.file("上传一段运动失败: " + BaseShowMapActivity.this.uid + "/" + BaseShowMapActivity.this.time + "\r\n上传时间： " + System.currentTimeMillis() + "\r\n失败原因：" + e.getMessage() + "", 3);
                if (BaseShowMapActivity.this.progressDialog != null) {
                    BaseShowMapActivity.this.progressDialog.dismiss();
                }
                BaseShowMapActivity.this.showMap();
            }
        }).uploadGpsFile(this.uid, new DateUtil(startTime, true).getYyyyMMdd_HHmmssDate(), new DateUtil(endTime, true).getYyyyMMdd_HHmmssDate(), "Android", file);
    }

    /* access modifiers changed from: protected */
    public void showMap() {
    }

    private void initPross() {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setProgressStyle(0);
            this.progressDialog.setCancelable(true);
            this.progressDialog.setCanceledOnTouchOutside(false);
        }
        this.progressDialog.show();
    }
}
