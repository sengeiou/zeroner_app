package com.iwown.lib_common;

import android.app.Activity;
import android.os.Bundle;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.fatigueview.FatigueDataBean;
import com.iwown.lib_common.views.fatigueview.FatigueScrollView;
import com.iwown.lib_common.views.fatigueview2.FatigueDataBean2;
import com.iwown.lib_common.views.fatigueview2.FatigueLineView;
import com.iwown.lib_common.views.heartview.DHeartChartView;
import com.iwown.lib_common.views.heartview.DlineDataBean;
import com.iwown.lib_common.views.sleepview.DSleepChartView;
import com.iwown.lib_common.views.sleepview.DSleepChartView.SleepData;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewDemoActivity extends Activity {
    private DHeartChartView dcvHeart;
    private DSleepChartView dcvSleep;
    private FatigueLineView flvFatigueLine;
    private FatigueScrollView wvsFatigueChart;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_common_activity_view_demo);
        initView();
        initData();
    }

    private void initView() {
        this.dcvSleep = (DSleepChartView) findViewById(R.id.dcv_sleep);
        this.dcvHeart = (DHeartChartView) findViewById(R.id.dcv_heart);
        this.wvsFatigueChart = (FatigueScrollView) findViewById(R.id.wvs_fatigue_chart);
        this.flvFatigueLine = (FatigueLineView) findViewById(R.id.flv_fatigue_line);
    }

    private void initData() {
        DateUtil dateUtil = new DateUtil();
        dateUtil.setHour(22);
        long endTime = dateUtil.getTimestamp();
        Random random = new Random();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            long start_time = endTime;
            endTime = start_time + 1200000;
            int type = random.nextInt(4);
            if (type == 0) {
                type = 1;
            }
            arrayList.add(new SleepData(start_time, endTime, type));
        }
        ArrayList arrayList2 = arrayList;
        arrayList2.add(new SleepData(((SleepData) arrayList.get(0)).startTime, ((SleepData) arrayList.get(arrayList.size() - 1)).endTime, 1));
        this.dcvSleep.updateSleepDatas(arrayList);
        List<DlineDataBean> datas = new ArrayList<>();
        DateUtil dateUtil1 = new DateUtil();
        dateUtil1.setHour(0);
        dateUtil1.setMinute(0);
        dateUtil1.setSecond(0);
        long unixTimestamp = dateUtil1.getUnixTimestamp();
        for (int i2 = 0; i2 < 100; i2++) {
            DlineDataBean dlineDataBean = new DlineDataBean(((long) (i2 * ServiceErrorCode.YOU_AND_ME_IS_FRIEND)) + unixTimestamp, random.nextInt(100) + 50);
            datas.add(dlineDataBean);
        }
        this.dcvHeart.setDatas(datas, null);
        ArrayList arrayList3 = new ArrayList();
        long zeroTime = new DateUtil().getZeroTime();
        for (int i3 = 0; i3 < 10; i3++) {
            FatigueDataBean fatigueDataBean = new FatigueDataBean(random.nextInt(50) + 100, random.nextInt(50) + 20);
            arrayList3.add(fatigueDataBean);
        }
        this.wvsFatigueChart.setDatas(arrayList3);
        FatigueDataBean2 fatigueDataBean2 = new FatigueDataBean2(150, 0, dateUtil1.getMonth() + "/" + dateUtil1.getDay(), "1");
        FatigueDataBean2 fatigueDataBean22 = new FatigueDataBean2(100, 50, dateUtil1.getMonth() + "/" + dateUtil1.getDay(), "2");
        FatigueDataBean2 fatigueDataBean23 = new FatigueDataBean2(50, 10, dateUtil1.getMonth() + "/" + dateUtil1.getDay(), "3");
        fatigueDataBean2.left_data = fatigueDataBean22;
        fatigueDataBean2.right_data = fatigueDataBean23;
        this.flvFatigueLine.setFatigueDataBean(fatigueDataBean2);
    }
}
