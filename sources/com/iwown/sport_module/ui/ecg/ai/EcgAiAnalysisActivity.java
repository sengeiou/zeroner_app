package com.iwown.sport_module.ui.ecg.ai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.lib_common.toast.CustomToast;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.ecg.bean.EcgResultEventBus;
import com.iwown.sport_module.ui.ecg.bean.Filtering;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EcgAiAnalysisActivity extends BaseActivity implements OnClickListener {
    private TextView aiDes;
    /* access modifiers changed from: private */
    public TextView aiEcgCount;
    private TextView aiGoto;
    int count;
    private List<Integer> data_source;
    private String ecgArray;
    Filtering filtering = new Filtering();
    private EcgViewDataBean itemData;
    private LoadingDialog loadingDialog;
    MyHandler myHandler = new MyHandler();
    Runnable runnable = new Runnable() {
        public void run() {
            try {
                EcgAiAnalysisActivity.this.dismissDialog();
                EcgAiAnalysisActivity.this.count--;
                EcgAiAnalysisActivity.this.aiEcgCount.setText(EcgAiAnalysisActivity.this.getString(R.string.sport_module_page_ecg_8, new Object[]{Integer.valueOf(EcgAiAnalysisActivity.this.sumCount - EcgAiAnalysisActivity.this.count)}));
                UserConfig.getInstance().setEcgCount(EcgAiAnalysisActivity.this.count);
                UserConfig.getInstance().save();
                CustomToast.makeText(EcgAiAnalysisActivity.this, EcgAiAnalysisActivity.this.getString(R.string.sport_module_page_ecg_35));
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    };
    /* access modifiers changed from: private */
    public int sumCount = 15;

    private class MyHandler extends Handler {
        private MyHandler() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.sport_module_activity_ecg_ai_analysis, null);
        setContentLayout(R.layout.sport_module_activity_ecg_ai_analysis);
        EventBus.getDefault().register(this);
        setLeftBackTo();
        setTitleBarBG(getResources().getColor(R.color.heart_bg));
        setTitleTextID(R.string.sport_module_page_ecg_2);
        String month = new DateUtil().getMonth() + "";
        if (!month.equalsIgnoreCase(UserConfig.getInstance().getEcgMonth())) {
            UserConfig.getInstance().setEcgCount(0);
            UserConfig.getInstance().setEcgMonth(month);
            UserConfig.getInstance().save();
        }
        initView();
        this.itemData = (EcgViewDataBean) getIntent().getParcelableExtra("data");
        if (this.itemData != null) {
            this.ecgArray = this.itemData.getDataArray();
        }
    }

    private void initView() {
        this.aiDes = (TextView) findViewById(R.id.ecg_ai_des);
        this.aiEcgCount = (TextView) findViewById(R.id.ecg_ai_count);
        this.aiGoto = (TextView) findViewById(R.id.ecg_ai_goto);
        this.aiGoto.setOnClickListener(this);
        this.count = UserConfig.getInstance().getEcgCount();
        this.aiEcgCount.setText(getString(R.string.sport_module_page_ecg_8, new Object[]{Integer.valueOf(this.sumCount - this.count)}));
        if (this.count < this.sumCount) {
            this.aiGoto.setVisibility(0);
            return;
        }
        this.aiGoto.setVisibility(8);
        this.aiDes.setText(R.string.sport_module_page_ecg_36);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.ecg_ai_goto) {
            this.count++;
            UserConfig.getInstance().setEcgCount(this.count);
            UserConfig.getInstance().save();
            this.aiEcgCount.setText(getString(R.string.sport_module_page_ecg_8, new Object[]{Integer.valueOf(this.sumCount - this.count)}));
            if (this.sumCount - this.count <= 0) {
                this.aiGoto.setVisibility(8);
            }
            String csv = null;
            try {
                this.data_source = JsonUtils.getListJson(this.itemData.getDataArray(), Integer.class);
                List<Integer> lvList = lvbo(this.data_source);
                List<Float> ecgLvData = new ArrayList<>();
                if (lvList.size() <= 750) {
                    try {
                        dismissDialog();
                        this.count--;
                        this.aiEcgCount.setText(getString(R.string.sport_module_page_ecg_8, new Object[]{Integer.valueOf(this.sumCount - this.count)}));
                        UserConfig.getInstance().setEcgCount(this.count);
                        UserConfig.getInstance().save();
                        CustomToast.makeText(this, getString(R.string.sport_module_page_ecg_35));
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
                for (int i = 750; i < lvList.size(); i++) {
                    float f = (float) (((double) (2000.0f * (((float) ((Integer) lvList.get(i)).intValue()) / 2000.0f))) / 2000.0d);
                    if (i >= 6750) {
                        break;
                    }
                    ecgLvData.add(Float.valueOf(1.0f * f));
                }
                StringBuilder csvBuilder = new StringBuilder();
                for (Float floatValue : ecgLvData) {
                    csvBuilder.append(floatValue.floatValue() + "");
                    csvBuilder.append(",");
                }
                String csv2 = csvBuilder.toString();
                csv = csv2.substring(0, csv2.length() - 1);
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
            }
            AiAPI.aiEcgPost(csv, this.itemData);
            showDialog();
            this.myHandler.removeCallbacks(this.runnable);
            this.myHandler.postDelayed(this.runnable, 60000);
        }
    }

    private List<Integer> lvbo(List<Integer> dataList) {
        this.filtering.init();
        List<Integer> list = new ArrayList<>();
        long sum = 0;
        for (int i = 0; i < dataList.size(); i++) {
            sum += (long) ((Integer) dataList.get(i)).intValue();
        }
        float avg = (((float) sum) * 1.0f) / ((float) dataList.size());
        KLog.i("--avg--" + avg + "--sum--" + sum + "--dataList.size()----" + dataList.size());
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            list.add(Integer.valueOf(this.filtering.filteringMain((int) (((float) ((Integer) dataList.get(i2)).intValue()) - avg), true)));
        }
        return list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EcgResultEventBus event) {
        if (event != null) {
            this.myHandler.removeCallbacks(this.runnable);
            dismissDialog();
            String data = event.getStrData();
            finish();
            Intent intent = new Intent(this, EcgAiAnalysisResultActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }
    }

    private void showDialog() {
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
        this.loadingDialog.show();
    }

    /* access modifiers changed from: private */
    public void dismissDialog() {
        if (this.loadingDialog != null && this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
