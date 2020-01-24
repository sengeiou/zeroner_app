package com.iwown.sport_module.ui.ecg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.OptionsPickerView.Builder;
import com.bigkoo.pickerview.OptionsPickerView.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.ecg.EcgHasDataNet.EcgHasData;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.service.IntentSendUtils;
import com.iwown.sport_module.ui.ecg.EcgContract.EcgDataView;
import com.iwown.sport_module.ui.ecg.EcgContract.EcgPresenter;
import com.iwown.sport_module.ui.ecg.ai.EcgAiAnalysisActivity;
import com.iwown.sport_module.ui.ecg.ai.EcgAiAnalysisResultActivity;
import com.iwown.sport_module.ui.ecg.bean.EcgAiResultEvent;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder.CallBack;
import com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag;
import com.iwown.sport_module.view.ecg.EcgChartView;
import com.iwown.sport_module.view.ecg.IVEcgViewAnim;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EcgActivity1 extends BaseActivity implements OnClickListener, EcgDataView, EcgOnclickListener {
    EcgViewItemAdapter adapter;
    private List<EcgViewDataBean> dataBeans = new ArrayList();
    ImageView dataFrom_ecg;
    TextView dateCenter;
    DateUtil dateUtil = new DateUtil();
    TextView ecgSpeed;
    TextView ecgTestTime;
    ImageView ecg_speed_pull_down;
    private EcgViewDataBean itemEcgData;
    List<String> items = new ArrayList();
    IVEcgViewAnim ivEcgView;
    EcgChartView ivEcgView1;
    private int month = new DateUtil().getMonth();
    TextView noData;
    private EcgPresenterImpl presenter;
    /* access modifiers changed from: private */
    public OptionsPickerView pvCustomOptions;
    RecyclerView recyclerView;
    /* access modifiers changed from: private */
    public ImageView toAiActivity;
    ImageView toAnother;
    DateUtil toDay;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_ecg_1);
        EventBus.getDefault().register(this);
        initView();
        initData(this.dateUtil);
    }

    private void initView() {
        this.items.clear();
        this.items.add("25mm/s");
        this.items.add("50mm/s");
        this.presenter = new EcgPresenterImpl(this);
        setLeftBackTo();
        setTitleBarBG(getResources().getColor(R.color.heart_top));
        setTitleTextID(R.string.sport_module_page_ecg_2);
        this.dateCenter = (TextView) findViewById(R.id.tv_date_center);
        this.toDay = new DateUtil();
        initCalendar();
        this.dateCenter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
                    EcgActivity1.this.initCalendar();
                }
                CalendarShowHanlder.getCalendarShowHanlder().showCalendar(EcgActivity1.this.dateCenter);
            }
        });
        this.recyclerView = (RecyclerView) findViewById(R.id.ecg_charts_items);
        this.dataFrom_ecg = (ImageView) findViewById(R.id.iv_data_from_ecg);
        this.ecgTestTime = (TextView) findViewById(R.id.ecg_time_test);
        this.noData = (TextView) findViewById(R.id.tv_no_data_ecg);
        this.toAnother = (ImageView) findViewById(R.id.to_another_activity);
        this.ivEcgView = (IVEcgViewAnim) findViewById(R.id.iv_ecg_view);
        this.ivEcgView1 = (EcgChartView) findViewById(R.id.iv_ecg_view_1);
        this.toAiActivity = (ImageView) findViewById(R.id.to_ai_analysis);
        this.ecgSpeed = (TextView) findViewById(R.id.ecg_speed_text);
        this.ecg_speed_pull_down = (ImageView) findViewById(R.id.ecg_speed_pull_down);
        this.toAnother.setOnClickListener(this);
        this.ecgSpeed.setOnClickListener(this);
        this.ecg_speed_pull_down.setOnClickListener(this);
        this.dataFrom_ecg.setOnClickListener(this);
        this.toAiActivity.setOnClickListener(this);
        this.toAiActivity.setVisibility(8);
        this.presenter.loadEcgHasData(UserConfig.getInstance().getNewUID(), this.dateUtil.getYear(), this.dateUtil.getMonth());
        this.presenter.loadEcgCalendarStatus(UserConfig.getInstance().getNewUID());
        if (UserConfig.getInstance().isEcg_Speed_Defaut()) {
            this.ecgSpeed.setText((CharSequence) this.items.get(1));
            this.ivEcgView1.setSpeed(4);
            return;
        }
        this.ecgSpeed.setText((CharSequence) this.items.get(0));
        this.ivEcgView1.setSpeed(2);
    }

    /* access modifiers changed from: private */
    public void initCalendar() {
        CalendarShowHanlder.init(this);
        CalendarShowHanlder.getCalendarShowHanlder().setRoundColor(Color.parseColor("#D4572E"));
        CalendarShowHanlder.getCalendarShowHanlder().setLeveTag(true);
        CalendarShowHanlder.getCalendarShowHanlder().setCallBack(new CallBack() {
            public void onResult(int year, int month, int day) {
                DateUtil dateUtil = new DateUtil(year, month, day);
                EcgActivity1.this.initData(dateUtil);
                EcgActivity1.this.toDay = dateUtil;
                EcgActivity1.this.dateCenter.setText(dateUtil.getY_M_D());
            }
        });
    }

    /* access modifiers changed from: private */
    public void initData(DateUtil dateUtil2) {
        if (this.month != dateUtil2.getMonth()) {
            this.presenter.loadEcgHasData(UserConfig.getInstance().getNewUID(), dateUtil2.getYear(), dateUtil2.getMonth());
            this.month = dateUtil2.getMonth();
        }
        this.presenter.braceletToView();
        if (DateUtil.isSameDay(new Date(), dateUtil2.toDate())) {
            this.dateCenter.setText(String.format(this.dateCenter.getContext().getResources().getString(R.string.sport_module_sleep_sync_time), new Object[]{""}));
        } else {
            this.dateCenter.setText(dateUtil2.getY_M_D());
        }
        this.dataBeans = this.presenter.loadEcgDataByTime(dateUtil2.getZeroTime());
        this.adapter = new EcgViewItemAdapter(this.dataBeans);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setListener(this);
        this.adapter.notifyDataSetChanged();
        if (this.dataBeans == null || this.dataBeans.size() <= 0) {
            this.ivEcgView1.setDataList(null);
            this.noData.setVisibility(0);
            this.ecgTestTime.setText("");
            this.presenter.downLoadEcgDataByDay(dateUtil2);
            this.toAiActivity.setVisibility(8);
            this.itemEcgData = null;
            return;
        }
        for (int i = 0; i < this.dataBeans.size(); i++) {
            ArrayList<Integer> list = JsonUtils.getListJson(((EcgViewDataBean) this.dataBeans.get(i)).getDataArray(), Integer.class);
            if ((list == null || list.size() <= 0) && ((EcgViewDataBean) this.dataBeans.get(i)).getUrl() != null) {
                NetFactory.getInstance().getClient(new MyCallback() {
                    public void onSuccess(Object o) {
                        EcgActivity1.this.toAiActivity.setVisibility(0);
                    }

                    public void onFail(Throwable e) {
                    }
                }).downLoadEcgFile(((EcgViewDataBean) this.dataBeans.get(i)).getUrl(), ((EcgViewDataBean) this.dataBeans.get(i)).getUid(), ((EcgViewDataBean) this.dataBeans.get(i)).getData_from(), ((EcgViewDataBean) this.dataBeans.get(i)).getDate(), (EcgViewDataBean) this.dataBeans.get(i));
            }
        }
    }

    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ecg_speed_text || view.getId() == R.id.ecg_speed_pull_down) {
            this.pvCustomOptions = new Builder(this, new OnOptionsSelectListener() {
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    KLog.i("option1" + options1);
                    if (options1 == 0) {
                        UserConfig.getInstance().setEcg_Speed_Defaut(false);
                    } else if (options1 == 1) {
                        UserConfig.getInstance().setEcg_Speed_Defaut(true);
                    }
                    UserConfig.getInstance().save();
                }
            }).setLayoutRes(R.layout.sport_module_layout_picker_wheelview_option, new CustomListener() {
                public void customLayout(View v) {
                    TextView tvSubmit = (TextView) v.findViewById(R.id.ok_dialog_option);
                    ((TextView) v.findViewById(R.id.dialog_title_option)).setText(EcgActivity1.this.getString(R.string.sport_module_page_ecg_42));
                    TextView ivCancel = (TextView) v.findViewById(R.id.cancel_dialog_option);
                    tvSubmit.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            EcgActivity1.this.pvCustomOptions.returnData();
                            EcgActivity1.this.pvCustomOptions.dismiss();
                            if (UserConfig.getInstance().isEcg_Speed_Defaut()) {
                                EcgActivity1.this.ivEcgView1.setSpeed(4);
                                EcgActivity1.this.ecgSpeed.setText((CharSequence) EcgActivity1.this.items.get(1));
                                return;
                            }
                            EcgActivity1.this.ivEcgView1.setSpeed(2);
                            EcgActivity1.this.ecgSpeed.setText((CharSequence) EcgActivity1.this.items.get(0));
                        }
                    });
                    ivCancel.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            EcgActivity1.this.pvCustomOptions.dismiss();
                        }
                    });
                }
            }).setSelectOptions(UserConfig.getInstance().isEcg_Speed_Defaut() ? 1 : 0).setOutSideCancelable(false).setTextColorOut(getResources().getColor(R.color.device_module_device_wifi_picker_un_select)).setLineSpacingMultiplier(2.0f).setDividerColor(getResources().getColor(R.color.device_module_common_line_color)).setTextColorCenter(getResources().getColor(R.color.device_module_white)).setBgColor(getResources().getColor(R.color.device_module_common_background_1)).setContentTextSize(22).isCenterLabel(true).isDialog(false).setLabels("", "", "").build();
            this.pvCustomOptions.setNPicker(this.items, null, null);
            this.pvCustomOptions.show();
        } else if (view.getId() == R.id.to_another_activity) {
            if (this.itemEcgData != null) {
                Intent intent = new Intent(this, EcgHorizontalScreenActivity.class);
                intent.putExtra("data", this.itemEcgData);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.to_ai_analysis) {
            IntentSendUtils.sendUploadEcg(this);
            if (this.itemEcgData != null) {
                KLog.i("-----------------" + this.itemEcgData.getAi_result());
                if (TextUtils.isEmpty(this.itemEcgData.getAi_result()) || "[]".equalsIgnoreCase(this.itemEcgData.getAi_result())) {
                    Intent intent2 = new Intent(this, EcgAiAnalysisActivity.class);
                    intent2.putExtra("data", this.itemEcgData);
                    startActivity(intent2);
                    return;
                }
                Intent intent3 = new Intent(this, EcgAiAnalysisResultActivity.class);
                intent3.putExtra("data", this.itemEcgData.getAi_result());
                startActivity(intent3);
            }
        }
    }

    public void showDataOver() {
        try {
            this.toAiActivity.setVisibility(0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void showLoading() {
    }

    public void dismissLoading() {
    }

    public Context getContext() {
        return null;
    }

    public void updateCalendar(Map<String, EcgHasData> dataMap) {
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            int color_good = getResources().getColor(R.color.base_text_color_black_1);
            Map<String, ShowLeveTag> showLeveTagList = new LinkedHashMap<>();
            for (String key : dataMap.keySet()) {
                EcgHasData bean = (EcgHasData) dataMap.get(key);
                ShowLeveTag showLeveTag = new ShowLeveTag();
                showLeveTag.color = color_good;
                showLeveTag.unix_time = bean.getUnixTime();
                showLeveTagList.put(key, showLeveTag);
            }
            calendarShowHanlder.updateSleepStatus(this, showLeveTagList);
        }
    }

    public void noEcgDataByDay() {
    }

    public void setPresenter(EcgPresenter presenter2) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EcgAiResultEvent event) {
        KLog.i("--更新ecg数据--");
        if (this.toDay != null) {
            initData(this.toDay);
            return;
        }
        this.toDay = new DateUtil();
        initData(this.toDay);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        CalendarShowHanlder calendarShowHanlder = CalendarShowHanlder.getCalendarShowHanlder();
        if (calendarShowHanlder != null) {
            calendarShowHanlder.destory();
            IntentSendUtils.sendUploadEcg(this);
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEcgClick(int positions) {
        EcgViewDataBean dataBean = (EcgViewDataBean) this.dataBeans.get(positions);
        this.itemEcgData = dataBean;
        String ecgPoint = dataBean.getDataArray();
        if (!TextUtils.isEmpty(ecgPoint)) {
            this.noData.setVisibility(8);
            ArrayList<Integer> pointData = JsonUtils.getListJson(ecgPoint, Integer.class);
            KLog.i(pointData.size() + "----" + JsonUtils.toJson(pointData));
            if (pointData.size() >= 2500) {
                this.ivEcgView1.setDataList(null);
                this.ivEcgView1.setDataList(pointData);
                this.ecgTestTime.setText(new DateUtil(dataBean.getUnixTime(), true).getHHmmDate());
                this.toAiActivity.setVisibility(0);
            } else {
                this.ivEcgView1.setDataList(null);
                this.noData.setVisibility(0);
                this.toAiActivity.setVisibility(8);
            }
        } else {
            this.ivEcgView1.setDataList(null);
            this.noData.setVisibility(0);
            this.toAiActivity.setVisibility(8);
        }
        showDateTimeUI(this.dateCenter, dataBean);
        this.dataFrom_ecg.setTag(R.id.first_id, dataBean.getData_from() + "");
    }

    @SuppressLint({"StringFormatInvalid"})
    private void showDateTimeUI(TextView tv_date_choose, EcgViewDataBean ecgViewDataBean) {
        if (ecgViewDataBean == null) {
            return;
        }
        if (DateUtil.isSameDay(new Date(), new DateUtil(ecgViewDataBean.getUnixTime(), true).toDate())) {
            tv_date_choose.setText(String.format(tv_date_choose.getContext().getResources().getString(R.string.sport_module_sleep_sync_time), new Object[]{""}));
            return;
        }
        tv_date_choose.setText(new DateUtil(ecgViewDataBean.getUnixTime(), true).getY_M_D());
    }
}
