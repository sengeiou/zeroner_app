package com.iwown.sport_module.Fragment.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.af.ModuleRouterRRIService;
import com.iwown.data_link.consts.UserConst.DrViva;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService.DeviceStatusListener;
import com.iwown.data_link.ecg.EcgData;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.data_link.eventbus.AgpsEvent;
import com.iwown.data_link.eventbus.GpsTotalEvent;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.ShouldGetWeatherEvent;
import com.iwown.data_link.eventbus.StartSyncDataEvent;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.data_link.eventbus.ViewRefresh;
import com.iwown.data_link.fatigue.ModuleRouteFatigueService;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sport_data.Bp_data_sport;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.data_link.weight.ModuleRouteWeightService;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.WeakHandler;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.Fragment.data.mvp.HomeTraningContract.HTPresenter1;
import com.iwown.sport_module.Fragment.data.mvp.HomeTraningContract.HTView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.SportInitUtils;
import com.iwown.sport_module.activity.ActiveActivity;
import com.iwown.sport_module.activity.NotificationHintActivity;
import com.iwown.sport_module.gps.activity.SportDetailActivity;
import com.iwown.sport_module.model.ActiveTodayModelImpl;
import com.iwown.sport_module.pojo.ErrorTipEvent;
import com.iwown.sport_module.pojo.WristConnectStateEvent;
import com.iwown.sport_module.pojo.active.SportAllData;
import com.iwown.sport_module.pojo.data.BaseTraningItem;
import com.iwown.sport_module.pojo.data.HistoryRideTraningItem;
import com.iwown.sport_module.pojo.data.HistoryRunWalkTraningItem;
import com.iwown.sport_module.pojo.data.TodayAfItem;
import com.iwown.sport_module.pojo.data.TodayBloodItem;
import com.iwown.sport_module.pojo.data.TodayEcgItem;
import com.iwown.sport_module.pojo.data.TodayFatigueItem;
import com.iwown.sport_module.pojo.data.TodayHeartItem;
import com.iwown.sport_module.pojo.data.TodaySleepItem;
import com.iwown.sport_module.pojo.data.TodayWeightItem;
import com.iwown.sport_module.service.IntentSendUtils;
import com.iwown.sport_module.sql.SportSqlHelper;
import com.iwown.sport_module.ui.af.AfActivity;
import com.iwown.sport_module.ui.af.presenter.AfPresenter;
import com.iwown.sport_module.ui.blood.BloodShowActivity;
import com.iwown.sport_module.ui.ecg.EcgActivity1;
import com.iwown.sport_module.ui.fatigue.FatigueActivity;
import com.iwown.sport_module.ui.heart.HeartActivity;
import com.iwown.sport_module.ui.sleep.SleepShowActivity;
import com.iwown.sport_module.ui.weight.WeightShowActivity;
import com.iwown.sport_module.util.SPUtils;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.view.SportDataProgressLayout;
import com.iwown.sport_module.view.SportRefreshHeader;
import com.iwown.sport_module.view.SportRefreshHeader.ShowTipListener;
import com.iwown.sport_module.view.TrainningRcyItemDecoration;
import com.iwown.sport_module.view.scrolltextview.ScrollTextView;
import com.iwown.sport_module.view.scrolltextview.ScrollTextView.OnItemClickListener;
import com.iwown.sport_module.view.swipetoloadlayout.SwipeToLoadLayout;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SportHomeFragment extends Fragment implements HTView {
    public static long syncTime = 0;
    /* access modifiers changed from: private */
    public String TAG = getClass().getSimpleName();
    private long agpsTime = 0;
    /* access modifiers changed from: private */
    public String amazonTip;
    /* access modifiers changed from: private */
    public Context context;
    private String dayStr = " days)";
    private HelathAdapter healthAdapter;
    /* access modifiers changed from: private */
    public RecyclerView healthRcy;
    private Map<String, MultiItemEntity> homeMap = new HashMap();
    /* access modifiers changed from: private */
    public List<MultiItemEntity> home_items = new ArrayList();
    private int lastPro = 0;
    private ActiveTodayModelImpl mActiveTodayModel;
    private SportDataProgressLayout mCalProgressBar;
    private int mCal_finish;
    private float mCal_target;
    /* access modifiers changed from: private */
    public Context mContext;
    private RelativeLayout mDataRl;
    private LinearLayout mHeadLl;
    /* access modifiers changed from: private */
    public View mHealthRcyHead;
    private TrainningRcyItemDecoration mItemDecoration;
    private SyncDataEvent mLastEvent;
    private View mLayout;
    /* access modifiers changed from: private */
    public ImageView mLight;
    /* access modifiers changed from: private */
    public ImageView mManPic;
    private SportRefreshHeader mRefreshHead;
    private View mReminder;
    private SportAllData mSportAllData;
    private TextView mSport_title;
    private SportDataProgressLayout mStandProgressBar;
    private int mStand_hour;
    private SportDataProgressLayout mStepProgressBar;
    private int mStep_finish;
    private int mStep_target;
    private int mTarget_stand;
    /* access modifiers changed from: private */
    public TextView mText_sync;
    /* access modifiers changed from: private */
    public ImageView mTipCancelBtn;
    private ScrollTextView mTipView;
    private View mTopBar;
    private TraningAdapter mTraningAdapter;
    private RecyclerView mTraniningRcy;
    private WeakHandler mWeakHandler = new WeakHandler(Looper.getMainLooper());
    private int progress = 0;
    /* access modifiers changed from: private */
    public SwipeToLoadLayout refreshLayout;
    Runnable syncTimeOutRunnable = new Runnable() {
        public void run() {
            SportHomeFragment.this.refreshLayout.setRefreshEnabled(true);
            SportHomeFragment.this.refreshLayout.setEnabled(true);
            EventBus.getDefault().post(new ErrorTipEvent(SportHomeFragment.this.getString(R.string.sport_module_sync_fail), 2));
            SportHomeFragment.this.refreshLayout.setRefreshing(true);
            SportHomeFragment.this.refreshLayout.setRefreshEnabled(true);
            KLog.e(SportHomeFragment.this.TAG, "同步开始后60s都没收到一点进度....");
            SportHomeFragment.this.mText_sync.setText("同步开始后60s都没收到一点进度....");
        }
    };
    private int tKcal;
    private int tStep;
    /* access modifiers changed from: private */
    public int textPosition;
    /* access modifiers changed from: private */
    public ArrayList<String> tips = new ArrayList<>();
    DateUtil todayDate = new DateUtil();
    private TraningPresenter traningPresenter;
    /* access modifiers changed from: private */
    public List<MultiItemEntity> traning_items = new ArrayList();
    private TextView tv_healthy;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mLayout = inflater.inflate(R.layout.sport_module_sport_data_fragment, null);
        this.mContext = getActivity();
        EventBus.getDefault().register(this);
        this.traningPresenter = new TraningPresenter(this);
        this.mActiveTodayModel = new ActiveTodayModelImpl();
        ModuleRouteDeviceInfoService.getInstance().setDeviceStatusListener(new DeviceStatusListener() {
            public void bluetoothInit() {
                EventBus.getDefault().post(new WristConnectStateEvent(true));
                if (SportHomeFragment.this.isAdded()) {
                }
            }

            public void deviceConnectStatus(boolean isConnect) {
                KLog.e(SportHomeFragment.this.TAG, "收到连接状态信号：" + isConnect);
                KLog.e(SportHomeFragment.this.TAG, "是否正在同步数据：" + ModuleRouteDeviceInfoService.getInstance().isSyncDataInfo());
                SportHomeFragment.this.mText_sync.setText("收到连接状态信号：" + isConnect + "/是否正在同步数据：" + ModuleRouteDeviceInfoService.getInstance().isSyncDataInfo());
                if (!isConnect) {
                    KLog.e(SportHomeFragment.this.TAG, "正在同步数据的过程中，突然断连了");
                    SportHomeFragment.this.wakeReshAndRemove(true);
                    if (SportHomeFragment.this.isAdded()) {
                        EventBus.getDefault().post(new ErrorTipEvent(SportHomeFragment.this.getActivity().getApplicationContext().getString(R.string.device_module_ble_connect_statue_2), 2));
                        SportHomeFragment.this.refreshLayout.setRefreshing(false);
                        SportHomeFragment.this.refreshLayout.setRefreshEnabled(true);
                    }
                }
                EventBus.getDefault().post(new WristConnectStateEvent(isConnect));
                if (!isConnect) {
                    EventBus.getDefault().post(new WristConnectStateEvent(false));
                    if (SportHomeFragment.this.isAdded()) {
                    }
                }
            }
        });
        initData();
        initView();
        initEvent();
        SportSqlHelper.getInstance().upLoadWalkData();
        KLog.e(this.TAG, "onCreateView......");
        if (AppConfigUtil.isHealthy()) {
            this.dayStr = " 天)";
        }
        return this.mLayout;
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
        this.context = context2;
    }

    public void onResume() {
        super.onResume();
        KLog.e(this.TAG, "onResume......");
        refreshNotificationToastReminder(Util.isLocationEnabled(this.context));
        if (AppConfigUtil.isDrviva()) {
            refreshAmazon();
        }
        if (!refreshNotificationPermissionReminder() || ModuleRouteDeviceInfoService.getInstance().isNotificationServiceRun()) {
            refreshNotificationServiceReminder();
        } else {
            this.mLayout.postDelayed(new Runnable() {
                public void run() {
                    SportHomeFragment.this.refreshNotificationServiceReminder();
                }
            }, 10000);
        }
        UserConfig.getInstance().initInfoFromOtherModule();
        DateUtil dateUtil = new DateUtil();
        if (!dateUtil.isSameDay(UserConfig.getInstance().getLast_up_sport_time(), false)) {
            SportSqlHelper.getInstance().upLoadSportData();
        }
        this.mSportAllData = this.mActiveTodayModel.getAllData(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), UserConfig.getInstance().getDevice(), false);
        refreshSportPorgressBars();
    }

    private void refreshConnectStateReminder() {
        boolean z = true;
        boolean isBind = ModuleRouteDeviceInfoService.getInstance().isBind();
        boolean isConnect = ModuleRouteDeviceInfoService.getInstance().isWristConnected();
        KLog.e(this.TAG, "是否解绑， 是否连接： " + isBind + " / " + isConnect);
        String string = getActivity().getApplicationContext().getString(R.string.Please_connect_your_device);
        if (!isBind || !isConnect) {
            z = false;
        }
        refreshScrollText(string, z);
    }

    /* access modifiers changed from: private */
    public void refreshNotificationServiceReminder() {
        if (isAdded()) {
            refreshScrollText(getString(R.string.sport_module_message_service_not_run), ModuleRouteDeviceInfoService.getInstance().isNotificationServiceRun());
            KLog.e(this.TAG, "通知服务是否在运行" + ModuleRouteDeviceInfoService.getInstance().isNotificationServiceRun());
            return;
        }
        KLog.e(this.TAG, "has_not_added");
    }

    private boolean refreshNotificationPermissionReminder() {
        boolean isOpen = Util.isMsgNotificationEnabled(this.mContext);
        KLog.e(this.TAG, "通知权限是否开启：" + isOpen);
        refreshScrollText(getString(R.string.sport_module_no_notification_monitor_access), isOpen);
        return isOpen;
    }

    private void refreshNotificationToastReminder(boolean isMove) {
        refreshScrollText(getString(R.string.sport_module_message_toast), isMove);
    }

    private void refreshAmazon() {
        String note = new PreferenceUtility(this.mContext).fetchStrValueWithKey(DrViva.MarketNotice);
        if (!TextUtils.isEmpty(note)) {
            this.amazonTip = note;
            refreshScrollText(note, false);
        }
    }

    private void refreshAllReminder() {
        refreshNotificationPermissionReminder();
        refreshNotificationServiceReminder();
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            UserConfig.getInstance().initInfoFromOtherModule();
            if (!refreshNotificationPermissionReminder() || ModuleRouteDeviceInfoService.getInstance().isNotificationServiceRun()) {
                refreshNotificationServiceReminder();
            } else {
                this.mLayout.postDelayed(new Runnable() {
                    public void run() {
                        SportHomeFragment.this.refreshNotificationServiceReminder();
                    }
                }, 10000);
            }
        }
    }

    private void initEvent() {
        this.traningPresenter.downloadGpsSegment(UserConfig.getInstance().getNewUID());
        this.mSport_title.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.mRefreshHead.setShowTipListener(new ShowTipListener() {
            public void shouldShowTip() {
                KLog.e("下拉刷新，开始同步数据。。。");
                SportHomeFragment.this.syncBindData();
            }
        });
        this.mTipView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(int position) {
                String tip = (String) SportHomeFragment.this.tips.get(position);
                try {
                    if (tip.equals(SportHomeFragment.this.context.getResources().getString(R.string.sport_module_no_notification_monitor_access))) {
                        Util.openNotificationAccess(SportHomeFragment.this.mContext);
                    } else if (tip.equals(SportHomeFragment.this.context.getResources().getString(R.string.sport_module_message_service_not_run))) {
                        SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), NotificationHintActivity.class));
                    } else if (!tip.equals(SportHomeFragment.this.context.getResources().getString(R.string.Please_connect_your_device)) && tip.equalsIgnoreCase(SportHomeFragment.this.amazonTip)) {
                        String url = new PreferenceUtility(SportHomeFragment.this.mContext).fetchStrValueWithKey(DrViva.MarketUrl);
                        if (!TextUtils.isEmpty(url)) {
                            ARouter.getInstance().build(RouteUtils.Activity_my_app_background_Activity).withString("url", url).withString("title", "").navigation();
                        }
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }

            public void onShow(int position) {
                SportHomeFragment.this.textPosition = position;
                if (((String) SportHomeFragment.this.tips.get(position)).equals(SportHomeFragment.this.context.getResources().getString(R.string.sport_module_message_toast))) {
                    SportHomeFragment.this.mTipCancelBtn.setVisibility(0);
                } else {
                    SportHomeFragment.this.mTipCancelBtn.setVisibility(0);
                }
            }
        });
        this.healthRcy.addOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0 && !SportHomeFragment.this.healthRcy.canScrollVertically(-1)) {
                    SportHomeFragment.this.mLight.setVisibility(0);
                } else if (dy > 0) {
                    SportHomeFragment.this.mLight.setVisibility(4);
                }
            }
        });
        this.mTraningAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity multiItemEntity = (MultiItemEntity) SportHomeFragment.this.traning_items.get(position);
                if (multiItemEntity instanceof HistoryRunWalkTraningItem) {
                    HistoryRunWalkTraningItem item = (HistoryRunWalkTraningItem) multiItemEntity;
                    Intent intent = new Intent(SportHomeFragment.this.getActivity(), SportDetailActivity.class);
                    intent.putExtra("sport_type", item.getSport_type());
                    SportHomeFragment.this.getActivity().startActivity(intent);
                } else if (multiItemEntity instanceof HistoryRideTraningItem) {
                    HistoryRideTraningItem item2 = (HistoryRideTraningItem) multiItemEntity;
                    Intent intent2 = new Intent(SportHomeFragment.this.getActivity(), SportDetailActivity.class);
                    intent2.putExtra("sport_type", item2.getSport_type());
                    SportHomeFragment.this.getActivity().startActivity(intent2);
                }
            }
        });
        this.mDataRl.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SportHomeFragment.this.getActivity().startActivity(new Intent(SportHomeFragment.this.getActivity(), ActiveActivity.class));
            }
        });
        this.mTipCancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SportHomeFragment.this.refreshScrollText((String) SportHomeFragment.this.tips.get(SportHomeFragment.this.textPosition), true);
                SportInitUtils.has_reminder = false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void syncBindData() {
        EventBus.getDefault().post(new ShouldGetWeatherEvent(10));
        if (!ModuleRouteDeviceInfoService.getInstance().isWristConnected()) {
            KLog.d(this.TAG, "要开始同步数据，但手环是断连的");
            this.mText_sync.setText("要开始同步数据，但手环是断连的");
            this.refreshLayout.setRefreshing(false);
            this.refreshLayout.setRefreshEnabled(true);
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_fail) + "\r\n" + getString(R.string.Please_connect_your_device), 2, true));
            wakeReshAndRemove(true);
        } else if (ModuleRouteDeviceInfoService.getInstance().isSyncDataInfo()) {
            KLog.d(this.TAG, "已经在同步了");
            this.mText_sync.setText("已经在同步了");
            this.refreshLayout.setRefreshEnabled(false);
        } else {
            KLog.d(this.TAG, "按下同步按钮, 开始同步数据");
            if (System.currentTimeMillis() - this.agpsTime >= 20000 || !ModuleRouteDeviceInfoService.getInstance().isZg()) {
                this.mText_sync.setText("开始同步数据");
                this.mWeakHandler.postDelayed(this.syncTimeOutRunnable, 10000);
                ModuleRouteDeviceInfoService.getInstance().getPower();
                ModuleRouteDeviceInfoService.getInstance().syncDataInfo(true);
                return;
            }
            this.refreshLayout.setRefreshing(false);
            this.refreshLayout.setRefreshEnabled(true);
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_agps), 2, true));
            wakeReshAndRemove(false);
        }
    }

    /* access modifiers changed from: private */
    public void wakeReshAndRemove(boolean up) {
        this.refreshLayout.setRefreshing(false);
        this.refreshLayout.setRefreshEnabled(true);
        this.mWeakHandler.removeCallbacks(this.syncTimeOutRunnable);
        if (up) {
            KLog.d("no2855--> 刷新了数据wakeReshAndRemove");
            updateTraningData(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SyncDataEvent events) {
        this.mWeakHandler.removeCallbacks(this.syncTimeOutRunnable);
        this.mWeakHandler.postDelayed(this.syncTimeOutRunnable, 60000);
        if (!ModuleRouteDeviceInfoService.getInstance().isWristConnected() || isDetached()) {
            KLog.e(this.TAG, "收到数据但手环断连了");
            this.mText_sync.setText("收到数据但手环断连了");
            wakeReshAndRemove(true);
        } else if (ModuleRouteDeviceInfoService.getInstance().isZg()) {
            progressZg(events);
        } else {
            try {
                int progress2 = events.getProgress();
                this.progress = progress2;
                this.lastPro = progress2;
                if (this.mLastEvent == null || (progress2 == 0 && this.mLastEvent.isStop() != events.isStop())) {
                    KLog.e("同步进度 : " + progress2 + "   isStop : " + events.isStop(), this.TAG);
                }
                if (this.refreshLayout.isRefreshEnabled()) {
                    this.refreshLayout.setRefreshEnabled(false);
                }
                if (ModuleRouteDeviceInfoService.getInstance().isSupport08()) {
                    KLog.e("显示进度条" + progress2, this.TAG);
                    if (progress2 != 0) {
                        showPross(events.getmDay(), events.getTotalDay());
                    } else {
                        EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_data), 1));
                    }
                } else {
                    EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_data), 1));
                }
                if (ModuleRouteDeviceInfoService.getInstance().isProtoBuf()) {
                    if (progress2 != 0) {
                        showProgress(events.getType(), events.getmDay(), events.getTotalDay());
                    } else {
                        EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_data), 1));
                    }
                }
                this.mLastEvent = events;
                if (events.isStop()) {
                    syncTime = System.currentTimeMillis();
                    KLog.e("testf1shuju", "收到同步结束饿---");
                    this.mText_sync.setText("收到同步结束饿");
                    KLog.d("no2855--> 刷新了数据普通 同步结束");
                    wakeReshAndRemove(true);
                    EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_success), 0));
                    this.refreshLayout.setRefreshEnabled(true);
                    if (ModuleRouteDeviceInfoService.getInstance().isMtk()) {
                        ModuleRouterEcgService.getInstance().braceletToView(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice());
                    }
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    private void showPross(int mDay, int allDay) {
        if (ModuleRouteDeviceInfoService.getInstance().isMtk() || ModuleRouteDeviceInfoService.getInstance().isMTKHeadset() || ModuleRouteDeviceInfoService.getInstance().isProtoBuf()) {
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_progress, Integer.valueOf(this.progress)) + "(" + mDay + "/" + allDay + this.dayStr, 1));
            return;
        }
        EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_progress, Integer.valueOf(this.progress)), 1));
    }

    private void showProgress(String type, int mDay, int allDay) {
        if (!ModuleRouteDeviceInfoService.getInstance().isProtoBuf()) {
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_progress, Integer.valueOf(this.progress)), 1));
        } else if (allDay == 0) {
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_protobuf_progress, type, Integer.valueOf(this.progress)), 1));
        } else if (type.toUpperCase().contains("ECG")) {
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_protobuf_progress, type, Integer.valueOf(this.progress)) + "(" + mDay + "/" + allDay + ")", 1));
        } else {
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_protobuf_progress, type, Integer.valueOf(this.progress)) + "(" + mDay + "/" + allDay + this.dayStr, 1));
        }
    }

    private void progressZg(SyncDataEvent events) {
        if (events.isStop()) {
            syncTime = System.currentTimeMillis();
            KLog.e(this.TAG, "licl收到同步结束饿---");
            this.mText_sync.setText("zg 同步结束");
            KLog.d("no2855--> 刷新了数据zg 同步结束");
            wakeReshAndRemove(true);
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_success), 0));
            this.refreshLayout.setRefreshEnabled(true);
            return;
        }
        if (this.refreshLayout.isRefreshEnabled()) {
            this.refreshLayout.setRefreshEnabled(false);
        }
        KLog.e(this.TAG, "licl显示进度条" + this.progress, this.TAG);
        if (this.todayDate.isSameDay((long) events.getProgress(), true)) {
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_progress_2), 1));
        } else {
            EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_progress_3) + " (" + new DateUtil((long) events.getProgress(), true).getY_M_D() + ")", 1));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ViewRefresh event) {
        KLog.d(this.TAG, "ViewRefresh:" + event.getType() + "");
        DateUtil date = new DateUtil();
        if (event.getType() == 40) {
            this.mSportAllData = this.mActiveTodayModel.getAllData(UserConfig.getInstance().getNewUID(), date.getYear(), date.getMonth(), date.getDay(), UserConfig.getInstance().getDevice(), false);
            refreshSportPorgressBars();
        }
    }

    private void initData() {
        KLog.d("no2855--> initData");
        DateUtil dateUtil = new DateUtil();
        this.mSportAllData = this.mActiveTodayModel.getDataForShow(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), UserConfig.getInstance().getDevice(), false);
    }

    /* access modifiers changed from: private */
    public void updateTraningData(boolean b) {
        if (this.traningPresenter != null) {
            this.traning_items.clear();
            this.traning_items.addAll(this.traningPresenter.getHistoryItems(true));
            this.traningPresenter.loadData(new DateUtil(), b);
        }
    }

    private void updateHomeContentDetailData() {
        this.homeMap.clear();
        this.home_items.clear();
        TodaySleepItem todaySleepItem = new TodaySleepItem();
        KLog.e(UserConfig.getInstance().getDevice());
        KLog.e("YANXI", UserConfig.getInstance().getDevice().toUpperCase());
        if (!UserConfig.getInstance().getDevice().toUpperCase(Locale.US).contains("VOICE")) {
            this.homeMap.put(todaySleepItem.getItemType() + "", todaySleepItem);
        } else if (ModuleRouteSleepService.getInstance().isExitSleepData(UserConfig.getInstance().getNewUID())) {
            this.homeMap.put(todaySleepItem.getItemType() + "", todaySleepItem);
        }
        if (ModuleRouteHeartService.getInstance().isExist53DataByUid(UserConfig.getInstance().getNewUID()) || ModuleRouteDeviceInfoService.getInstance().isSupportHeart()) {
            TodayHeartItem todayHeartItem = new TodayHeartItem();
            this.homeMap.put(todayHeartItem.getItemType() + "", todayHeartItem);
        }
        TodayWeightItem weightItem = new TodayWeightItem();
        if (ModuleRouteWeightService.getInstance().getScaleMac(UserConfig.getInstance().getNewUID()) != null || (weightItem.getWeightDatas1() != null && weightItem.getWeightDatas1().size() > 0)) {
            this.homeMap.put(weightItem.getItemType() + "", weightItem);
        }
        TodayFatigueItem fatigueItem = new TodayFatigueItem();
        if (fatigueItem.getFatigue() != null || ModuleRouteFatigueService.getIsnatnce().isMTK() || DeviceSettingsBiz.getInstance().supportSomeSetting(28)) {
            KLog.e(Boolean.valueOf(DeviceSettingsBiz.getInstance().supportSomeSetting(28)));
            this.homeMap.put(fatigueItem.getItemType() + "", fatigueItem);
        }
        List<EcgViewDataBean> dataBeanList = ModuleRouterEcgService.getInstance().ecgViewDataFromDB(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice(), new DateUtil().getZeroTime());
        if (dataBeanList == null || dataBeanList.size() <= 0) {
            boolean flag = (ModuleRouteDeviceInfoService.getInstance().isMtk() || ModuleRouteDeviceInfoService.getInstance().isProtoBuf()) && !"P5J".equalsIgnoreCase(ModuleRouteDeviceInfoService.getInstance().getDeviceInfo().dev_modle);
            List<EcgViewDataBean> beans = ModuleRouterEcgService.getInstance().queryEcgDataByUid(UserConfig.getInstance().getNewUID());
            boolean flag1 = beans != null && beans.size() > 0;
            if (flag || flag1) {
                TodayEcgItem ecgItem = new TodayEcgItem();
                ecgItem.setEcgData(new EcgData(0));
                this.homeMap.put(ecgItem.getItemType() + "", ecgItem);
            }
        } else {
            TodayEcgItem ecgItem2 = new TodayEcgItem();
            ecgItem2.setEcgData(new EcgData(1));
            this.homeMap.put(ecgItem2.getItemType() + "", ecgItem2);
        }
        TodaySleepItem tSleep = (TodaySleepItem) this.homeMap.get(BaseTraningItem.UI_TYPE_TODAY_Sleep + "");
        if (tSleep != null) {
            this.home_items.add(tSleep);
            this.homeMap.remove(BaseTraningItem.UI_TYPE_TODAY_Sleep + "");
        }
        TodayHeartItem tHeart = (TodayHeartItem) this.homeMap.get(BaseTraningItem.UI_TYPE_TODAY_Heart + "");
        if (tHeart != null) {
            this.home_items.add(tHeart);
            this.homeMap.remove(BaseTraningItem.UI_TYPE_TODAY_Heart + "");
        }
        boolean isSupportAf = ModuleRouteDeviceInfoService.getInstance().supportSomeSetting(41);
        boolean hasData = ModuleRouterRRIService.getInstance().hasRRIData(UserConfig.getInstance().getNewUID());
        if (isSupportAf || hasData) {
            this.home_items.add(new TodayAfItem());
        }
        KLog.d("l808   BloodUIChange   0000000000000000000000");
        TodayBloodItem bloodItem = new TodayBloodItem();
        DateUtil dateUtil1 = new DateUtil();
        dateUtil1.setHour(0);
        dateUtil1.setMinute(0);
        dateUtil1.setSecond(0);
        if (ModuleRouteSportService.getInstance().getAllDataBlood(UserConfig.getInstance().getNewUID(), dateUtil1.getUnixTimestamp() - 2592000, dateUtil1.getUnixTimestamp() + 86400).size() > 0) {
            this.homeMap.put(bloodItem.getItemType() + "", bloodItem);
            KLog.e("l808   BloodUIChange   -----------  数据库有数据");
        } else if (DeviceSettingsBiz.getInstance().supportSomeSetting(38)) {
            this.homeMap.put(bloodItem.getItemType() + "", bloodItem);
            KLog.e("l808   BloodUIChange   -----------  连接血压手环");
        } else {
            KLog.e("l808   BloodUIChange   -----------  服务器下载数据");
        }
        for (Object obj : this.homeMap.keySet()) {
            this.home_items.add(this.homeMap.get(obj.toString()));
        }
        if (this.home_items.size() == 0) {
            this.tv_healthy.setVisibility(8);
        } else {
            this.tv_healthy.setVisibility(0);
        }
        if (this.healthAdapter != null) {
            this.healthAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void healthDataUpdate(HealthDataEventBus healthDataEventBus) {
        KLog.e(healthDataEventBus.type + " --- ");
        if (healthDataEventBus.type == 6 || healthDataEventBus.type == 5) {
            UserConfig.getInstance().initInfoFromOtherModule();
            if (ModuleRouteDeviceInfoService.getInstance().isMtk() || ModuleRouteDeviceInfoService.getInstance().isProtoBuf()) {
                ModuleRouterEcgService.getInstance().braceletToView(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice());
            }
            updateHomeContentDetailData();
            DateUtil dateUtil = new DateUtil();
            this.mSportAllData = this.mActiveTodayModel.getAllData(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), UserConfig.getInstance().getDevice(), false);
            refreshSportPorgressBars();
            SPUtils.save(getContext(), SPUtils.TODAY_UI_First_Update, System.currentTimeMillis());
            if (AppConfigUtil.isHealthy(getContext())) {
                ModuleRouteUserInfoService.getInstance().uploadWxQQStep(getContext());
                return;
            }
            return;
        }
        if (healthDataEventBus.type == 1) {
            this.home_items.set(0, new TodaySleepItem());
        }
        if (healthDataEventBus.type == 2) {
            TodayHeartItem todayHeartItem = new TodayHeartItem();
            if (this.home_items.size() > 1) {
                this.home_items.set(1, todayHeartItem);
            }
            if (AppConfigUtil.isHealthy(getContext())) {
                ModuleRouteUserInfoService.getInstance().uploadQQSleep(getContext());
            }
        }
        if (healthDataEventBus.type == 3 || healthDataEventBus.type == 4) {
            UserConfig.getInstance().initInfoFromOtherModule();
            weightFatigueUIChange();
        } else if (healthDataEventBus.type == 5) {
            IntentSendUtils.sendUploadAllData(getContext());
            if (AppConfigUtil.isHealthy(getContext())) {
                ModuleRouteUserInfoService.getInstance().uploadWxQQStep(getContext());
            }
        } else if (healthDataEventBus.type == 7) {
            ecgUIChange();
        } else if (healthDataEventBus.type == 8) {
            BloodUIChange();
        } else if (healthDataEventBus.type == 100) {
            KLog.d("no2855--> 刷新了数据100");
            updateTraningData(false);
            return;
        } else if (healthDataEventBus.type == 103) {
            KLog.d("no2855--> 刷新了数据103");
            updateTraningData(false);
            weightFatigueUIChange();
        } else if (healthDataEventBus.type == 9) {
            KLog.d("no2855--> 刷新了数据房颤结果");
            if (healthDataEventBus.date != null && !healthDataEventBus.date.equals("")) {
                new AfPresenter(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice()).updateAfResult(healthDataEventBus.date);
            }
        }
        this.healthAdapter.notifyDataSetChanged();
    }

    private void weightFatigueUIChange() {
        TodayWeightItem todayWeightItem = new TodayWeightItem();
        TodayFatigueItem todayFatigueItem = new TodayFatigueItem();
        Iterator it = this.home_items.iterator();
        int weightIndex = 99;
        int fatigueIndex = 99;
        for (int i = 0; i < this.home_items.size(); i++) {
            if (this.home_items.get(i) instanceof TodayWeightItem) {
                weightIndex = i;
            } else if (this.home_items.get(i) instanceof TodayFatigueItem) {
                fatigueIndex = i;
            }
        }
        if (ModuleRouteWeightService.getInstance().getScaleMac(UserConfig.getInstance().getNewUID()) != null || (todayWeightItem.getWeightDatas1() != null && todayWeightItem.getWeightDatas1().size() > 0)) {
            if (weightIndex == 99) {
                this.home_items.add(todayWeightItem);
            } else {
                this.home_items.set(weightIndex, todayWeightItem);
            }
        }
        if (todayFatigueItem.getFatigue() == null && !ModuleRouteFatigueService.getIsnatnce().isMTK()) {
            return;
        }
        if (fatigueIndex == 99) {
            this.home_items.add(todayFatigueItem);
        } else {
            this.home_items.set(fatigueIndex, todayFatigueItem);
        }
    }

    private void ecgUIChange() {
        int ecgIndex = 99;
        for (int i = 0; i < this.home_items.size(); i++) {
            if (this.home_items.get(i) instanceof TodayEcgItem) {
                ecgIndex = i;
            }
        }
        List<EcgViewDataBean> dataBeanList = ModuleRouterEcgService.getInstance().ecgViewDataFromDB(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice(), new DateUtil().getZeroTime());
        if (dataBeanList == null || dataBeanList.size() <= 0) {
            boolean flag = ModuleRouteDeviceInfoService.getInstance().isMtk() && !"P5J".equalsIgnoreCase(ModuleRouteDeviceInfoService.getInstance().getDeviceInfo().dev_modle);
            List<EcgViewDataBean> beans = ModuleRouterEcgService.getInstance().queryEcgDataByUid(UserConfig.getInstance().getNewUID());
            boolean flag1 = beans != null && beans.size() > 0;
            if (flag || flag1) {
                TodayEcgItem ecgItem = new TodayEcgItem();
                ecgItem.setEcgData(new EcgData(0));
                if (ecgIndex == 99) {
                    this.home_items.add(ecgItem);
                } else {
                    this.home_items.set(ecgIndex, ecgItem);
                }
            }
        } else {
            TodayEcgItem ecgItem2 = new TodayEcgItem();
            ecgItem2.setEcgData(new EcgData(1));
            if (ecgIndex == 99) {
                this.home_items.add(ecgItem2);
            } else {
                this.home_items.set(ecgIndex, ecgItem2);
            }
        }
        this.healthAdapter.notifyDataSetChanged();
    }

    private void BloodUIChange() {
        int bloodIndex = 99;
        for (int i = 0; i < this.home_items.size(); i++) {
            if (this.home_items.get(i) instanceof TodayEcgItem) {
                bloodIndex = i;
            }
        }
        DateUtil dateUtil1 = new DateUtil();
        dateUtil1.setHour(0);
        dateUtil1.setMinute(0);
        dateUtil1.setSecond(0);
        List<Bp_data_sport> bloodDeanList = ModuleRouteSportService.getInstance().getAllDataBlood(UserConfig.getInstance().getNewUID(), dateUtil1.getUnixTimestamp() - 2592000, dateUtil1.getUnixTimestamp() + 86400);
        if (bloodDeanList == null || bloodDeanList.size() <= 0) {
            KLog.e("l808   BloodUIChange   -----------  no");
            return;
        }
        TodayBloodItem bloodItem = new TodayBloodItem();
        if (bloodIndex == 99) {
            KLog.e("l808   BloodUIChange   -----------  yes");
            this.home_items.add(bloodItem);
            return;
        }
        KLog.e("l808   BloodUIChange   -----------  yes  11111");
        this.home_items.set(bloodIndex, bloodItem);
    }

    private void initView() {
        this.mHealthRcyHead = getActivity().getLayoutInflater().inflate(R.layout.sport_module_health_card_head_layout, null);
        this.mReminder = this.mHealthRcyHead.findViewById(R.id.reminder);
        this.mText_sync = (TextView) this.mHealthRcyHead.findViewById(R.id.test_sync_tv);
        this.mSport_title = (TextView) this.mHealthRcyHead.findViewById(R.id.sports_title);
        this.tv_healthy = (TextView) this.mHealthRcyHead.findViewById(R.id.tv_healthy);
        if (SportInitUtils.has_reminder) {
            this.mReminder.setVisibility(0);
        } else {
            this.mReminder.setVisibility(8);
        }
        this.mTipView = (ScrollTextView) this.mReminder.findViewById(R.id.scroll_tv);
        this.mTipCancelBtn = (ImageView) this.mReminder.findViewById(R.id.close_reminder);
        this.mTipView.setAnimTime(400);
        this.mTipView.setTextStillTime(8500);
        this.refreshLayout = (SwipeToLoadLayout) this.mLayout.findViewById(R.id.refresh_layout);
        this.mRefreshHead = (SportRefreshHeader) this.refreshLayout.findViewById(R.id.sport_module_swipe_refresh_header);
        this.healthRcy = (RecyclerView) this.mLayout.findViewById(R.id.sport_module_swipe_target);
        this.mLight = (ImageView) this.mLayout.findViewById(R.id.light);
        this.mHeadLl = (LinearLayout) this.mHealthRcyHead.findViewById(R.id.head_ll);
        this.mManPic = (ImageView) this.mHealthRcyHead.findViewById(R.id.man_pic);
        this.mStandProgressBar = (SportDataProgressLayout) this.mHealthRcyHead.findViewById(R.id.stand_progress_bar);
        this.mStepProgressBar = (SportDataProgressLayout) this.mHealthRcyHead.findViewById(R.id.step_progress_bar);
        this.mCalProgressBar = (SportDataProgressLayout) this.mHealthRcyHead.findViewById(R.id.cal_progress_bar);
        this.mDataRl = (RelativeLayout) this.mHealthRcyHead.findViewById(R.id.data_rl);
        this.healthAdapter = new HelathAdapter(getActivity(), this.home_items);
        this.healthAdapter.addHeaderView(this.mHealthRcyHead);
        this.healthRcy.setAdapter(this.healthAdapter);
        this.healthRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.healthRcy.setOverScrollMode(2);
        this.healthRcy.setPadding(DensityUtil.dip2px(getActivity(), 20.0f), WindowUtil.getStatusBarHeight() + DensityUtil.dip2px(getActivity(), 45.0f), DensityUtil.dip2px(getActivity(), 20.0f), 0);
        this.mRefreshHead.setTranslationY((float) (WindowUtil.getStatusBarHeight() + DensityUtil.dip2px(getActivity(), 45.0f)));
        this.healthAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity multiItemEntity = (MultiItemEntity) SportHomeFragment.this.home_items.get(position);
                if (multiItemEntity.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Heart) {
                    SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), HeartActivity.class));
                } else if (multiItemEntity.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Sleep) {
                    SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), SleepShowActivity.class));
                } else if (multiItemEntity.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Fatigue) {
                    SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), FatigueActivity.class));
                } else if (multiItemEntity.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Weight) {
                    SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), WeightShowActivity.class));
                } else if (multiItemEntity.getItemType() == BaseTraningItem.UI_TYPE_TODAY_ECG) {
                    SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), EcgActivity1.class));
                } else if (multiItemEntity.getItemType() == BaseTraningItem.UI_TYPE_TODAY_Blood) {
                    SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), BloodShowActivity.class));
                } else if (multiItemEntity.getItemType() == BaseTraningItem.UI_TYPE_TODAY_AF) {
                    SportHomeFragment.this.startActivity(new Intent(SportHomeFragment.this.getContext(), AfActivity.class));
                }
            }
        });
        this.mItemDecoration = new TrainningRcyItemDecoration(DensityUtil.dip2px(getActivity(), 10.0f));
        this.mTraniningRcy = (RecyclerView) this.mHealthRcyHead.findViewById(R.id.tranining_rcy);
        this.mTraniningRcy.addItemDecoration(this.mItemDecoration);
        this.mTraningAdapter = new TraningAdapter(getActivity(), this.traning_items);
        this.mTraniningRcy.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        this.mTraniningRcy.setAdapter(this.mTraningAdapter);
        this.mTraningAdapter.notifyDataSetChanged();
        this.mTraniningRcy.setOverScrollMode(2);
        this.mLight.post(new Runnable() {
            public void run() {
                Rect rect = new Rect();
                SportHomeFragment.this.mManPic.getGlobalVisibleRect(rect);
                int distance = SportHomeFragment.this.mLight.getHeight() - rect.bottom;
                if (distance > 0) {
                    SportHomeFragment.this.mHealthRcyHead.setPadding(0, distance, 0, 0);
                    return;
                }
                MarginLayoutParams layoutParams = (MarginLayoutParams) SportHomeFragment.this.healthRcy.getLayoutParams();
                layoutParams.topMargin += distance;
                SportHomeFragment.this.healthRcy.setLayoutParams(layoutParams);
            }
        });
        updateTraningData(false);
        if (this.mWeakHandler != null) {
            this.mWeakHandler.postDelayed(new Runnable() {
                public void run() {
                    SportHomeFragment.this.updateTraningData(true);
                }
            }, 1500);
        }
        updateHomeContentDetailData();
    }

    private void refreshSportPorgressBars() {
        this.mStand_hour = this.mSportAllData.getStand_hours();
        this.mTarget_stand = UserConfig.getInstance().getTarget_stand_hours();
        this.mStep_finish = this.mSportAllData.getSteps();
        this.mStep_target = UserConfig.getInstance().getTarget_step();
        this.mCal_finish = this.mSportAllData.getCalorie();
        this.mCal_target = UserConfig.getInstance().getTarget_cal();
        if (!(this.tStep == this.mStep_target && ((float) this.tKcal) == this.mCal_target)) {
            this.tStep = this.mStep_target;
            this.tKcal = (int) this.mCal_target;
            ModuleRouteDeviceInfoService.getInstance().updateTargetStep(this.mStep_target, this.mCal_target);
        }
        refreshTarget_Progress(this.mStand_hour, this.mTarget_stand, this.mStandProgressBar);
        refreshTarget_Progress(this.mStep_finish, this.mStep_target, this.mStepProgressBar);
        refreshTarget_Progress(this.mCal_finish, (int) this.mCal_target, this.mCalProgressBar);
    }

    public void refreshTarget_Progress(int finish, int target, SportDataProgressLayout dataProgressLayout) {
        dataProgressLayout.setComplete(target + "");
        dataProgressLayout.setTarget(finish + "");
        float progress2 = 0.0f;
        if (target != 0) {
            progress2 = finish >= target ? 1.0f : (float) ((((double) finish) * 1.0d) / ((double) target));
        }
        dataProgressLayout.startCircleAnim(progress2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRershCard(GpsTotalEvent event) {
        if (event.getCode() == 0) {
            KLog.d("no2855--> 刷新了数据GpsTotalEvent");
            updateTraningData(false);
        } else if (event.getCode() == 1) {
            this.mWeakHandler.postDelayed(new Runnable() {
                public void run() {
                    ModuleRouteSportService.getInstance().uploadNoUpGps(UserConfig.getInstance().getNewUID());
                }
            }, 60000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartSync(StartSyncDataEvent event) {
        KLog.d(this.TAG, "StartSyncDataEvent: 收到开始同步数据事件");
        this.refreshLayout.setRefreshing(false);
        this.refreshLayout.setRefreshEnabled(false);
        this.mWeakHandler.removeCallbacks(this.syncTimeOutRunnable);
        this.mWeakHandler.postDelayed(this.syncTimeOutRunnable, 60000);
        EventBus.getDefault().post(new ErrorTipEvent(getString(R.string.sport_module_sync_data), 1, true));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAgpsSync(AgpsEvent event) {
        if (event.getProgress() < 100) {
            this.agpsTime = System.currentTimeMillis();
        } else {
            this.agpsTime = 0;
        }
    }

    public void refreshScrollText(String tip, boolean is2move) {
        if (tip != null) {
            int index = this.tips.indexOf(tip);
            if ((index >= 0 || !is2move) && (index < 0 || is2move)) {
                if (index < 0 || !is2move) {
                    KLog.e(this.TAG, tip + "-->add");
                    this.mTipView.stopAutoScroll();
                    this.tips.add(tip);
                } else {
                    KLog.e(this.TAG, tip + "-->move");
                    this.mTipView.stopAutoScroll();
                    this.tips.remove(index);
                }
            }
            if (this.tips.size() == 0) {
                this.mReminder.setVisibility(8);
            } else if (SportInitUtils.has_reminder) {
                this.mReminder.setVisibility(0);
            }
            this.mTipView.setTextList(this.tips);
            this.mTipView.startAutoScroll();
        }
    }

    public void onDestroy() {
        this.mWeakHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
        ModuleRouteDeviceInfoService.getInstance().setDeviceStatusListener(null);
        super.onDestroy();
    }

    public void setPresenter(HTPresenter1 presenter) {
    }

    public void showTrandingDatas(List<MultiItemEntity> historyRunWalkTraningItems) {
        this.traning_items.clear();
        this.traning_items.addAll(historyRunWalkTraningItems);
        this.mTraningAdapter.notifyDataSetChanged();
    }
}
