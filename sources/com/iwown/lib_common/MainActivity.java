package com.iwown.lib_common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.lib_common.views.weightview.WeightShowData;
import com.iwown.lib_common.views.weightview.WeightViewScrollView;
import com.iwown.lib_common.views.weightview.mini_weight.WeightViewMiniLayout;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {
    private Button btViewDemo;
    private Random random;
    private WeightViewMiniLayout wvmlChart;
    private WeightViewScrollView wvsFatigueChart;
    private WeightViewScrollView wvsWeightChart;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_common_activity_main);
        testKLog();
        initData();
        testAop1("456");
        initView();
    }

    private void initData() {
        KLog.i("test aop ");
    }

    private void testAop1(String text) {
        ToastUtils.showLongToast((CharSequence) "你妹的来个泡试一下");
    }

    private void testKLog() {
        KLog.i("何志远");
        KLog.i("no thread info and method info");
        KLog.e("hezhiyuan", "Custom tag for only one use");
        KLog.json("{ \"key\": 3, \"value\": something}");
        KLog.d(Arrays.asList(new String[]{"foo", "bar"}));
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key1", "value2");
        KLog.d(map);
        KLog.i("===============");
    }

    private void initView() {
        WindowManager manager = getWindowManager();
        manager.getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.wvmlChart = (WeightViewMiniLayout) findViewById(R.id.wvml_chart);
        this.wvsWeightChart = (WeightViewScrollView) findViewById(R.id.wvs_fatigue_chart);
        this.wvsWeightChart.setGoal(48.0d, "kg", 120.0f);
        this.random = new Random();
        List<WeightShowData> weightDatas = new ArrayList<>();
        long zeroTime1 = new DateUtil().getZeroTime();
        for (int i = 0; i < 30; i++) {
            int nextInt = this.random.nextInt(10);
            weightDatas.add(new WeightShowData(((long) (i * 60 * 10)) + zeroTime1, (float) (this.random.nextInt(50) + 50)));
        }
        this.wvsWeightChart.setDatas(weightDatas);
        this.wvmlChart.setGoal(48.0d, 120.0f, "kg");
        this.random = new Random();
        List<WeightShowData> weightDatas1 = new ArrayList<>();
        long zeroTime11 = new DateUtil().getZeroTime();
        for (int i2 = 0; i2 < 7; i2++) {
            weightDatas1.add(new WeightShowData(((long) (this.random.nextInt(10) * 60 * 10)) + zeroTime11, (float) (this.random.nextInt(50) + 50)));
        }
        this.wvmlChart.setDatas(weightDatas1);
        this.btViewDemo = (Button) findViewById(R.id.bt_view_demo);
        Button button = this.btViewDemo;
        AnonymousClass1 r0 = new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ViewDemoActivity.class));
            }
        };
        button.setOnClickListener(r0);
    }
}
