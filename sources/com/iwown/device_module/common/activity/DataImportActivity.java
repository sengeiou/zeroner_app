package com.iwown.device_module.common.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.gms.common.ConnectionResult;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.device_module.R;
import com.iwown.device_module.common.contract.DataImportContract.DataImportView;
import com.iwown.device_module.common.contract.DataImportPresenter;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.utils.WindowsUtil;
import com.iwown.device_module.common.view.HorizontalProgressBar;
import com.iwown.device_module.common.view.HorizontalProgressBar.ProgressListener;
import com.iwown.lib_common.date.DateUtil;

@Route(path = "/device/DataImportActivity")
public class DataImportActivity extends Activity implements ProgressListener, DataImportView {
    private int count = 0;
    /* access modifiers changed from: private */
    public DataImportPresenter dataImportPresenter;
    /* access modifiers changed from: private */
    public TextView dataJump;
    /* access modifiers changed from: private */
    public DateUtil dateUtil;
    /* access modifiers changed from: private */
    public int errorNum = 0;
    /* access modifiers changed from: private */
    public Handler handler;
    HorizontalProgressBar horizontalProgressBar;
    private int progress = 0;
    Runnable timeout = new Runnable() {
        public void run() {
            DataImportActivity.this.tryAgain.setVisibility(0);
            DataImportActivity.this.errorNum = DataImportActivity.this.errorNum + 1;
            if (DataImportActivity.this.errorNum > 3) {
                DataImportActivity.this.dataJump.setVisibility(0);
            }
        }
    };
    /* access modifiers changed from: private */
    public TextView tryAgain;
    /* access modifiers changed from: private */
    public long uid;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowsUtil.setTopWindows(getWindow());
        setContentView(R.layout.device_module_data_import);
        this.horizontalProgressBar = (HorizontalProgressBar) findViewById(R.id.import_progressbar);
        this.tryAgain = (TextView) findViewById(R.id.data_again);
        this.dataJump = (TextView) findViewById(R.id.data_jump);
        this.horizontalProgressBar.setProgressListener(this);
        this.horizontalProgressBar.initProgress();
        this.uid = ModuleRouteUserInfoService.getInstance().getUserInfo(this).uid;
        this.handler = new Handler();
        initEvent();
    }

    private void masterTest() {
        ModuleRouteUserInfoService.getInstance().changeTBImport(this.uid);
        reshProgress(100);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                RouteUtils.startAPPMainActivitry();
            }
        }, 2000);
    }

    private void initEvent() {
        initData(30);
        this.tryAgain.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DataImportActivity.this.initData(ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
                DataImportActivity.this.tryAgain.setVisibility(8);
                DataImportActivity.this.dataJump.setVisibility(8);
            }
        });
        this.dataJump.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DataImportActivity.this.showJumpDialog();
            }
        });
    }

    /* access modifiers changed from: private */
    public void showJumpDialog() {
        Builder alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setTitle((CharSequence) getString(R.string.sport_module_prompt));
        alertDialogBuilder.setPositiveButton((CharSequence) getString(R.string.sport_module_ok), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                RouteUtils.startAPPMainActivitry();
            }
        });
        alertDialogBuilder.setNegativeButton((CharSequence) getString(R.string.sport_module_cancel), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.data_import_jump));
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    /* access modifiers changed from: private */
    public void initData(int delayTime) {
        this.dateUtil = new DateUtil(new DateUtil().getZeroTime(), true);
        this.dataImportPresenter = new DataImportPresenter(this, this.dateUtil);
        this.dataImportPresenter.initEvent();
        this.progress = 0;
        this.count = 0;
        this.horizontalProgressBar.setProgressWithAnimation(0.0f);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                DataImportActivity.this.horizontalProgressBar.initProgress();
                DataImportActivity.this.dataImportPresenter.downloadTSport(DataImportActivity.this.uid);
                DataImportActivity.this.handler.removeCallbacks(DataImportActivity.this.timeout);
                DataImportActivity.this.handler.postDelayed(DataImportActivity.this.timeout, 21000);
            }
        }, (long) delayTime);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: private */
    public void reshProgress(int add) {
        this.progress += add;
        this.horizontalProgressBar.setProgressWithAnimation((float) this.progress);
    }

    public void currentProgressListener(float currentProgress) {
    }

    public void loadSuccess() {
        new Handler().post(new Runnable() {
            public void run() {
                DataImportActivity.this.reshProgress(20);
                DataImportActivity.this.dataImportPresenter.sportToGpsSegment(DataImportActivity.this.uid, DataImportActivity.this.dateUtil.getUnixTimestamp(), DataImportActivity.this.dateUtil.getUnixTimestamp() + 2678400);
            }
        });
    }

    public void loadFail() {
        ToastUtil.showToast("导入失败，请检查您的网络，请稍后再试");
    }

    public void onProgress(int point) {
        this.handler.removeCallbacks(this.timeout);
        this.handler.postDelayed(this.timeout, 21000);
    }

    public void sportToGpsOk() {
        reshProgress(15);
        this.dataImportPresenter.uploadGpsSegment(this.uid, this.dateUtil.getUnixTimestamp(), this.dateUtil.getUnixTimestamp() + 2678400);
    }

    public void uploadGpsSuccess(int type) {
        reshProgress(5);
        if (type != 2) {
            return;
        }
        if (this.count == 0) {
            this.count++;
            this.dataImportPresenter.downloadTSport(this.uid);
            return;
        }
        this.handler.removeCallbacks(this.timeout);
        ModuleRouteUserInfoService.getInstance().changeTBImport(this.uid);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                RouteUtils.startAPPMainActivitry();
            }
        }, 2000);
    }

    public void uploadGpsFail(int type) {
        reshProgress(5);
    }
}
