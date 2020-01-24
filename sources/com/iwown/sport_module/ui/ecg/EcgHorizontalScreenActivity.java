package com.iwown.sport_module.ui.ecg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.ecg.bean.Filtering;
import com.iwown.sport_module.ui.utils.ScreenLongShareUtils;
import com.iwown.sport_module.view.EcgMvDialog;
import com.iwown.sport_module.view.EcgMvDialog.OnItemClickListener;
import com.iwown.sport_module.view.ecg.ECGHorizontalView;
import com.socks.library.KLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;

public class EcgHorizontalScreenActivity extends Activity implements OnClickListener {
    private ImageView backImg;
    /* access modifiers changed from: private */
    public Context context;
    private List<Integer> data_source = new ArrayList();
    private ArrayList<Integer> data_source_more = new ArrayList<>();
    String date;
    private ECGHorizontalView ecgView;
    private TextView ecg_info1;
    /* access modifiers changed from: private */
    public ImageView ecg_mv_x;
    private boolean isShort;
    /* access modifiers changed from: private */
    public EcgViewDataBean itemData;
    private ImageView saveImg;
    private RelativeLayout scroll_ecg_view;
    private TextView tvHr;
    private TextView tvTime;
    private TextView upload;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == 1) {
            setRequestedOrientation(0);
        }
        setContentView(R.layout.sport_module_activity_ecg_horizontal_screen);
        this.context = this;
        initView();
    }

    private void initView() {
        this.backImg = (ImageView) findViewById(R.id.ecg_back_1);
        this.saveImg = (ImageView) findViewById(R.id.ecg_save_1);
        this.tvHr = (TextView) findViewById(R.id.ecg_info_2);
        this.ecg_info1 = (TextView) findViewById(R.id.ecg_info_1);
        this.tvTime = (TextView) findViewById(R.id.ecg_info_3);
        this.ecg_mv_x = (ImageView) findViewById(R.id.ecg_mv_x);
        this.upload = (TextView) findViewById(R.id.upload_file);
        this.ecgView = (ECGHorizontalView) findViewById(R.id.ecg_horizontal);
        this.scroll_ecg_view = (RelativeLayout) findViewById(R.id.scroll_ecg_view);
        this.backImg.setOnClickListener(this);
        this.saveImg.setOnClickListener(this);
        this.ecg_mv_x.setOnClickListener(this);
        this.ecg_mv_x.setOnClickListener(this);
        this.ecg_info1.setOnClickListener(this);
        this.upload.setOnClickListener(this);
        this.saveImg.setVisibility(8);
        this.itemData = (EcgViewDataBean) getIntent().getParcelableExtra("data");
        if (this.itemData != null) {
            EcgViewDataBean bean = ModuleRouterEcgService.getInstance().ecgDataByTime(this.itemData.getUid(), this.itemData.getData_from(), this.itemData.getUnixTime());
            if (bean != null) {
                this.data_source = JsonUtils.getListJson(bean.getDataArray(), Integer.class);
                KLog.i(bean.getDataArray());
                if (this.data_source != null && this.data_source.size() >= 2400) {
                    for (int i = 0; i < this.data_source.size(); i++) {
                        this.data_source_more.add(this.data_source.get(i));
                    }
                }
            }
        }
        if (this.itemData != null) {
            this.tvHr.setText(getString(R.string.sport_module_page_ecg_5, new Object[]{Integer.valueOf(this.itemData.getHeartrate())}));
            this.tvTime.setText(new DateUtil(this.itemData.getUnixTime(), true).getY_M_D_H_M_S());
        }
        switchData(UserConfig.getInstance().getEcg_Mv_Default());
    }

    /* access modifiers changed from: private */
    public void switchData(int mvPosition) {
        if (mvPosition == 0) {
            this.ecgView.setData(this.data_source_more, 8000.0f);
            this.ecg_info1.setText(getString(R.string.sport_module_page_ecg_43));
        } else if (mvPosition == 1) {
            this.ecgView.setData(this.data_source_more, 4000.0f);
            this.ecg_info1.setText(getString(R.string.sport_module_page_ecg_44));
        } else if (mvPosition == 2) {
            this.ecgView.setData(this.data_source_more, 2000.0f);
            this.ecg_info1.setText(getString(R.string.sport_module_page_ecg_45));
        } else if (mvPosition == 3) {
            this.ecgView.setData(this.data_source_more, 1000.0f);
            this.ecg_info1.setText(getString(R.string.sport_module_page_ecg_46));
        } else if (mvPosition == 4) {
            this.ecgView.setData(this.data_source_more, 500.0f);
            this.ecg_info1.setText(getString(R.string.sport_module_page_ecg_47));
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.ecg_back_1) {
            finish();
        } else if (v.getId() == R.id.ecg_save_1) {
            if (!this.isShort) {
                this.ecgView.toInitPosition();
                this.isShort = true;
                ScreenLongShareUtils.bitmapScreenView(this, this.scroll_ecg_view);
                this.isShort = false;
            }
        } else if (v.getId() == R.id.ecg_mv_x || v.getId() == R.id.ecg_info_1) {
            this.ecg_mv_x.setImageDrawable(ContextCompat.getDrawable(this.context, R.mipmap.pull_up_3x));
            EcgMvDialog dialog = new EcgMvDialog(this, true);
            dialog.setPosition(UserConfig.getInstance().getEcg_Mv_Default());
            dialog.setOnItemClickListener(new OnItemClickListener() {
                public void item(int position) {
                    EcgHorizontalScreenActivity.this.ecg_mv_x.setImageDrawable(ContextCompat.getDrawable(EcgHorizontalScreenActivity.this.context, R.mipmap.ecg_pull_down));
                    UserConfig.getInstance().setEcg_Mv_Default(position);
                    UserConfig.getInstance().save();
                    EcgHorizontalScreenActivity.this.switchData(position);
                }
            });
            dialog.show();
        } else if (v.getId() == R.id.upload_file) {
            uploadEDFFile();
        }
    }

    private void uploadEDFFile() {
        setData(this.data_source_more, 2000.0f);
    }

    public void setData(ArrayList<Integer> dataList, float zengyi) {
        int seq;
        float[] data = new float[21750];
        List<Integer> dataValue = new ArrayList<>();
        try {
            List<Integer> ecgList = new ArrayList<>();
            if (dataList != null && dataList.size() > 750) {
                for (int i = 0; i < dataList.size(); i++) {
                    ecgList.add(dataList.get(i));
                }
                KLog.i("横屏滤波前:" + JsonUtils.toJson(ecgList));
                ArrayList arrayList = (ArrayList) lvbo(ecgList);
                if (arrayList == null || arrayList.size() <= 0) {
                    for (int i2 = 0; i2 < 21750; i2++) {
                        data[i2] = 0.0f;
                    }
                } else {
                    if (arrayList.size() >= 21750) {
                        seq = 21750;
                    } else {
                        seq = arrayList.size();
                    }
                    for (int i3 = 750; i3 < seq; i3++) {
                        dataValue.add(arrayList.get(i3));
                    }
                    for (int i4 = 0; i4 < dataValue.size(); i4++) {
                        float f = ((((float) ((Integer) dataValue.get(i4)).intValue()) / zengyi) * zengyi) / zengyi;
                        if (i4 >= 21750) {
                            break;
                        }
                        data[i4] = 1.0f * f;
                    }
                }
                KLog.i("横屏滤波后：" + JsonUtils.toJson(data));
                final List<Float> datas = new ArrayList<>();
                for (int i5 = 0; i5 < data.length; i5++) {
                    if (i5 < 15000 || data[i5] != 0.0f || data[i5 - 1] != 0.0f || data[i5 - 2] != 0.0f || data[i5 - 3] != 0.0f || data[i5 - 4] != 0.0f || data[i5 - 5] != 0.0f) {
                        datas.add(Float.valueOf(data[i5]));
                    }
                }
                try {
                    new Thread() {
                        public void run() {
                            super.run();
                            MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                            HashMap<String, String> map = new HashMap<>();
                            map.put("hardware", "ECGwatch");
                            map.put("hardwareid", ContextUtil.getDeviceNameCurr());
                            map.put("edf", JsonUtils.toJson(datas));
                            map.put("date", EcgHorizontalScreenActivity.this.itemData.getDate());
                            Gson gson = new Gson();
                            String gData = gson.toJson((Object) map);
                            try {
                                KLog.e("-----------" + gson.toJson((Object) new OkHttpClient().newCall(new Builder().url("https://api.vagus.co/v1/measurements").post(RequestBody.create(JSON, gData)).build()).execute().body()));
                            } catch (IOException e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                    }.start();
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                Toast.makeText(this, "success", 1).show();
            }
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    private List<Integer> lvbo(List<Integer> dataList) {
        Filtering filtering = new Filtering();
        List<Integer> list = new ArrayList<>();
        filtering.init();
        long sum = 0;
        for (int i = 0; i < dataList.size(); i++) {
            sum += (long) ((Integer) dataList.get(i)).intValue();
        }
        long avg = sum / ((long) dataList.size());
        KLog.i("--avg--" + avg + "--sum--" + sum + "--dataList.size()----" + dataList.size());
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            list.add(Integer.valueOf(filtering.filteringMain((int) (((long) ((Integer) dataList.get(i2)).intValue()) - avg), true)));
        }
        return list;
    }
}
