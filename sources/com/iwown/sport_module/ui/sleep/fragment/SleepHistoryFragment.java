package com.iwown.sport_module.ui.sleep.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepHistoryData;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseFragment;
import com.iwown.sport_module.ui.repository.DataRepositoryHelper;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.iwown.sport_module.ui.repository.SleepDataRepository;
import com.iwown.sport_module.ui.skin_loader.SleepSkinHandler;
import com.iwown.sport_module.ui.sleep.DividerGridItemDecoration;
import com.iwown.sport_module.ui.sleep.adapter.SleepHistoryDetailsAdapter;
import com.iwown.sport_module.ui.utils.ScreenLongShareUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SleepHistoryFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int Size = 16;
    /* access modifiers changed from: private */
    public long currentTime = System.currentTimeMillis();
    private Gallery gy_charts;
    /* access modifiers changed from: private */
    public ArrayList<SleepHistoryData> historyCahrtBeans;
    /* access modifiers changed from: private */
    public boolean isToday;
    /* access modifiers changed from: private */
    public LoadingDialog loadingDialog;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    /* access modifiers changed from: private */
    public RecyclerView rcv_details;
    public Map<String, Long> recordDays = new HashMap();
    /* access modifiers changed from: private */
    public SleepHistoryGalleryAdapter sleepHistoryGalleryAdapter;
    /* access modifiers changed from: private */
    public TextView tv_score;
    private TextView tv_score_unit;

    public interface OnFragmentInteractionListener {
        View getTitleBar();
    }

    public static SleepHistoryFragment newInstance(String param1, String param2) {
        SleepHistoryFragment fragment = new SleepHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sport_module_fragment_sleep_history, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        this.gy_charts = (Gallery) view.findViewById(R.id.gy_charts);
        this.rcv_details = (RecyclerView) view.findViewById(R.id.rcv_details);
        this.tv_score = (TextView) view.findViewById(R.id.tv_score);
        this.tv_score_unit = (TextView) view.findViewById(R.id.tv_score_unit);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.tv_score);
        updateSkinColor();
        this.loadingDialog = new LoadingDialog(getContext());
        this.currentTime = System.currentTimeMillis();
        this.historyCahrtBeans = new ArrayList<>();
        this.recordDays.clear();
        getDatas(new DateUtil(this.currentTime, false));
        this.sleepHistoryGalleryAdapter = new SleepHistoryGalleryAdapter(this.historyCahrtBeans, getContext());
        this.gy_charts.setSpacing(50);
        this.gy_charts.setAdapter(this.sleepHistoryGalleryAdapter);
        this.gy_charts.setCallbackDuringFling(false);
        this.gy_charts.setOnItemSelectedListener(new OnItemSelectedListener() {
            public int selectndex;

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (this.selectndex != i) {
                    SleepHistoryFragment.this.sleepHistoryGalleryAdapter.showLightSelectBG(i);
                    KLog.e("--选中-- " + i);
                    this.selectndex = i;
                    if (i != 0) {
                        SleepHistoryFragment.this.tv_score.setText(((SleepHistoryData) SleepHistoryFragment.this.historyCahrtBeans.get(i)).score + "");
                        SleepHistoryFragment.this.showRcvDetailUI((SleepHistoryData) SleepHistoryFragment.this.historyCahrtBeans.get(i));
                        return;
                    }
                    SleepHistoryFragment.this.getDatas(new DateUtil(SleepHistoryFragment.this.currentTime, false));
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void getDatas(final DateUtil endDate) {
        final DateUtil startDate = new DateUtil(endDate.getTimestamp(), false);
        this.isToday = false;
        if (DateUtil.isSameDay(new Date(endDate.getTimestamp()), new Date())) {
            this.isToday = true;
        }
        startDate.addDay(-this.Size);
        KLog.e(" start Date " + startDate.getSyyyyMMddDate() + "  " + this.isToday + "   end Date " + endDate.getSyyyyMMddDate());
        Long aLong = (Long) this.recordDays.get(startDate.getSyyyyMMddDate());
        if (aLong == null || System.currentTimeMillis() - aLong.longValue() > 300000) {
            SleepDataRepository sleepDataRepository = DataRepositoryHelper.getSleepDataRepository(getContext());
            SleepDataDay sleepDataToday = new SleepDataDay();
            long timestamp = startDate.getTimestamp();
            sleepDataToday.date = DataTimeUtils.getyyyyMMddHHmmss(timestamp);
            sleepDataToday.uid = UserConfig.getInstance().getNewUID();
            sleepDataToday.data_from = UserConfig.getInstance().getDev_mac();
            sleepDataToday.time_unix = timestamp / 1000;
            sleepDataToday.size = this.Size;
            this.loadingDialog.show();
            sleepDataRepository.getRemoteSleepRepository().loadDayDataByTime(sleepDataToday, new DataCallBack<SleepDataDay>() {
                public void onResult(SleepDataDay sleepDataToday) {
                    SleepHistoryFragment.this.recordDays.put(startDate.getSyyyyMMddDate(), Long.valueOf(System.currentTimeMillis()));
                    List<SleepHistoryData> startEndSleeps = ModuleRouteSleepService.getInstance().getStartEndSleeps(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice(), startDate, endDate);
                    KLog.e("   " + startEndSleeps);
                    SleepHistoryFragment.this.loadingDialog.dismiss();
                    SleepHistoryFragment.this.updateChartViews(startEndSleeps, startDate, SleepHistoryFragment.this.isToday);
                    startDate.addDay(-1);
                    SleepHistoryFragment.this.currentTime = startDate.getTimestamp();
                }
            });
            return;
        }
        KLog.e("5分钟内不在请求");
    }

    /* access modifiers changed from: private */
    public void showRcvDetailUI(final SleepHistoryData sleepHistoryData) {
        this.rcv_details.postDelayed(new Runnable() {
            public void run() {
                try {
                    int screenHeight = DensityUtil.getScreenHeight(SleepHistoryFragment.this.getContext());
                    int numbers_height = (screenHeight - DensityUtil.dip2px(SleepHistoryFragment.this.getContext(), 330.0f)) - SleepHistoryFragment.this.mListener.getTitleBar().getHeight();
                    int number_height = numbers_height / 3;
                    KLog.e(screenHeight + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + number_height + " numbers_height " + numbers_height + "     " + DensityUtil.dip2px(SleepHistoryFragment.this.getContext(), 350.0f));
                    List<SleepHistoryDetailBean> details = new ArrayList<>();
                    SleepHistoryDetailBean sleepHistoryDetailBean = new SleepHistoryDetailBean();
                    KLog.e("   " + sleepHistoryData.startTime);
                    if (sleepHistoryData.startTime == 0) {
                        sleepHistoryDetailBean.valueStr = "00:00";
                    } else {
                        DateUtil dateUtil = new DateUtil(sleepHistoryData.startTime, true);
                        sleepHistoryDetailBean.valueStr = dateUtil.getHHmmDate();
                    }
                    sleepHistoryDetailBean.isHMFormat = false;
                    sleepHistoryDetailBean.title = SleepHistoryFragment.this.getString(R.string.sport_module_sleep_error_feedback_sleep_time);
                    SleepHistoryDetailBean sleepHistoryDetailBean1 = new SleepHistoryDetailBean();
                    if (sleepHistoryData.startTime == 0) {
                        sleepHistoryDetailBean1.valueStr = "00:00";
                    } else {
                        DateUtil dateUtil2 = new DateUtil(sleepHistoryData.endTime, true);
                        sleepHistoryDetailBean1.valueStr = dateUtil2.getHHmmDate();
                    }
                    sleepHistoryDetailBean1.isHMFormat = false;
                    sleepHistoryDetailBean1.title = SleepHistoryFragment.this.getString(R.string.sport_module_sleep_error_feedback_awake_time);
                    SleepHistoryDetailBean sleepHistoryDetailBean2 = new SleepHistoryDetailBean();
                    SleepHistoryFragment.this.min2Str(sleepHistoryDetailBean2, sleepHistoryData.totalTime);
                    sleepHistoryDetailBean2.title = SleepHistoryFragment.this.getResources().getString(R.string.sport_module_sleep_total_time);
                    SleepHistoryDetailBean sleepHistoryDetailBean3 = new SleepHistoryDetailBean();
                    SleepHistoryFragment.this.min2Str(sleepHistoryDetailBean3, sleepHistoryData.deepTime);
                    sleepHistoryDetailBean3.title = SleepHistoryFragment.this.getResources().getString(R.string.sport_module_sleep_deep_time);
                    SleepHistoryDetailBean sleepHistoryDetailBean4 = new SleepHistoryDetailBean();
                    SleepHistoryFragment.this.min2Str(sleepHistoryDetailBean4, sleepHistoryData.lightTime);
                    sleepHistoryDetailBean4.title = SleepHistoryFragment.this.getResources().getString(R.string.sport_module_sleep_light_time);
                    SleepHistoryDetailBean sleepHistoryDetailBean5 = new SleepHistoryDetailBean();
                    int wake_time = (sleepHistoryData.totalTime - sleepHistoryData.deepTime) - sleepHistoryData.lightTime;
                    if (wake_time < 0) {
                        wake_time = 0;
                    }
                    SleepHistoryFragment.this.min2Str(sleepHistoryDetailBean5, wake_time);
                    sleepHistoryDetailBean5.title = SleepHistoryFragment.this.getResources().getString(R.string.sport_module_sleep_awake_time);
                    details.add(sleepHistoryDetailBean);
                    details.add(sleepHistoryDetailBean1);
                    details.add(sleepHistoryDetailBean2);
                    details.add(sleepHistoryDetailBean3);
                    details.add(sleepHistoryDetailBean4);
                    details.add(sleepHistoryDetailBean5);
                    SleepHistoryDetailsAdapter adapter = new SleepHistoryDetailsAdapter(details, number_height);
                    SleepHistoryFragment.this.rcv_details.setLayoutManager(new GridLayoutManager(SleepHistoryFragment.this.getContext(), 2));
                    SleepHistoryFragment.this.rcv_details.addItemDecoration(new DividerGridItemDecoration(Color.parseColor("#3A496F")));
                    SleepHistoryFragment.this.rcv_details.setAdapter(adapter);
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }, 100);
    }

    /* access modifiers changed from: private */
    public void min2Str(SleepHistoryDetailBean sleepHistoryDetailBean, int totalTime) {
        sleepHistoryDetailBean.hour = totalTime / 60;
        sleepHistoryDetailBean.min = totalTime % 60;
    }

    /* access modifiers changed from: private */
    public void updateChartViews(List<SleepHistoryData> sleepDataToday, DateUtil startDate, boolean isToday2) {
        List<SleepHistoryData> temps = new ArrayList<>();
        Map<String, SleepHistoryData> maps = new LinkedHashMap<>();
        DateUtil tempData = new DateUtil(startDate.getTimestamp(), false);
        for (int i = 0; i <= this.Size; i++) {
            SleepHistoryData sleepHistoryData = new SleepHistoryData();
            sleepHistoryData.time_str = tempData.getMonth() + "/" + tempData.getDay();
            maps.put(sleepHistoryData.time_str, sleepHistoryData);
            tempData.addDay(1);
        }
        for (int i2 = 0; i2 < sleepDataToday.size(); i2++) {
            SleepHistoryData sleepHistoryData2 = (SleepHistoryData) sleepDataToday.get(i2);
            maps.put(sleepHistoryData2.time_str, sleepHistoryData2);
        }
        for (String key : maps.keySet()) {
            SleepHistoryData sleepHistoryData3 = (SleepHistoryData) maps.get(key);
            if (!this.historyCahrtBeans.contains(sleepHistoryData3)) {
                temps.add(sleepHistoryData3);
            }
        }
        this.historyCahrtBeans.addAll(0, temps);
        this.sleepHistoryGalleryAdapter.notifyDataSetChanged();
        if (isToday2) {
            this.gy_charts.setSelection(this.historyCahrtBeans.size() - 1);
            showRcvDetailUI((SleepHistoryData) this.historyCahrtBeans.get(this.historyCahrtBeans.size() - 1));
            return;
        }
        KLog.e(this.historyCahrtBeans.size() + "   " + temps.size());
        this.gy_charts.setSelection(temps.size());
        showRcvDetailUI((SleepHistoryData) this.historyCahrtBeans.get(temps.size()));
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void shareScreen() {
        ScreenLongShareUtils.shotActivityNoStatusBar(getContext(), getActivity());
    }

    public void updateSkinColor() {
        super.updateSkinColor();
        this.tv_score.setTextColor(SleepSkinHandler.getInstance().getSleepSkin_Type_History_Top_Score());
        this.tv_score_unit.setTextColor(SleepSkinHandler.getInstance().getSleepSkin_Type_History_Top_Score());
    }
}
