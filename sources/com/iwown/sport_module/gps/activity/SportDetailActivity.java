package com.iwown.sport_module.gps.activity;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.data.CopySportAll;
import com.iwown.data_link.eventbus.GpsTotalEvent;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.Fragment.data.SportHomeFragment;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.gps.adapter.GpsInfoAdapter;
import com.iwown.sport_module.gps.adapter.GpsInfoAdapter.OnLoadClickListener;
import com.iwown.sport_module.gps.contract.SportDetailContract.SportDetailView;
import com.iwown.sport_module.gps.data.GpsFootItem;
import com.iwown.sport_module.gps.data.SportDetailItem;
import com.iwown.sport_module.gps.data.SportItemData;
import com.iwown.sport_module.gps.data.SportTotalItem;
import com.iwown.sport_module.gps.presenter.SportDetailPresenter;
import com.iwown.sport_module.gps.view.SlideVpIndicator;
import com.iwown.sport_module.gps.view.SportTypeSelectBar;
import com.iwown.sport_module.gps.view.SportTypeSelectBar.OnClickListener;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.view.MyTextView;
import com.iwown.sport_module.view.WithUnitText;
import com.socks.library.KLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

public class SportDetailActivity extends Activity implements SportDetailView {
    /* access modifiers changed from: private */
    public static float SPORT_IMG_FACTOR = 0.4f;
    /* access modifiers changed from: private */
    public String TAG;
    private int cardType;
    private CopySportAll copySportAll;
    /* access modifiers changed from: private */
    public List<View> dataViews = new ArrayList();
    private String[] dayShu = {"th", "st", "ed", "rd"};
    private TextView details_title;
    AppBarLayout gpsAppBar;
    ImageView gpsDetailBack;
    MyTextView gpsDetailDis;
    WithUnitText gpsDetailTime;
    private boolean isAddAll = false;
    private boolean isEnglish;
    private boolean isFirstLoad = true;
    public boolean isSee = false;
    private float itemClo = 0.0f;
    private int itemCount = 0;
    private float itemDisKm = 0.0f;
    private int itemDur = 0;
    private int lastAllTime;
    private float lastCalorie;
    private int lastCount;
    private float lastDistanceKm;
    private float lastDistanceMile;
    private long lastLoadTime;
    private int lastMonth = 0;
    private int lastSize = 0;
    private int lastYear = 0;
    /* access modifiers changed from: private */
    public GpsInfoAdapter mGpsInfoAdapter;
    private List<MultiItemEntity> mGps_datas;
    private View mRunDataView;
    private WithUnitText mRunTv;
    private WithUnitText mRunTv2;
    SportTypeSelectBar mSportBar;
    private SportDataPagerAdapter mSportDataPagerAdapter;
    ViewPager mSportDataVp;
    RecyclerView mSportRecycler;
    SlideVpIndicator mSportVpIndicator;
    /* access modifiers changed from: private */
    public int mSport_type_now;
    private SportDetailPresenter sportDetailPresenter;
    private SportItemData sportItemData;
    /* access modifiers changed from: private */
    public int sportType = 0;
    private TextView totalDurationTx;
    private long uid;

    private static class MyHandler extends Handler {
        private WeakReference<Activity> context;

        private MyHandler(Activity activity) {
            this.context = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SportDetailActivity activity = (SportDetailActivity) this.context.get();
            if (msg.what != 1) {
                activity.isSee = false;
                sendEmptyMessageDelayed(1, 1000);
            } else if (!activity.isSee) {
                if (!activity.isDestroyed()) {
                }
            } else {
                sendEmptyMessageDelayed(2, 1000);
            }
        }
    }

    class SportDataPagerAdapter extends PagerAdapter {
        SportDataPagerAdapter() {
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) SportDetailActivity.this.dataViews.get(position));
        }

        public Object instantiateItem(ViewGroup container, int i) {
            container.addView((View) SportDetailActivity.this.dataViews.get(i));
            return SportDetailActivity.this.dataViews.get(i);
        }

        public int getCount() {
            return SportDetailActivity.this.dataViews.size();
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_activity_gps_detail);
        WindowUtil.setTopWindows(getWindow());
        UserConfig.getInstance().initInfoFromOtherModule();
        this.TAG = getClass().getSimpleName();
        this.uid = UserConfig.getInstance().getNewUID();
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        boolean z = true;
        this.sportItemData = new SportItemData();
        this.mSport_type_now = getIntent().getIntExtra("sport_type", 0);
        if (this.mSport_type_now < 4) {
            this.cardType = 0;
        } else if (this.mSport_type_now == 4) {
            this.cardType = 1;
        } else if (this.mSport_type_now == 6) {
            this.cardType = 3;
        } else {
            this.cardType = 2;
        }
        this.mGps_datas = new ArrayList();
        if (UserConfig.getInstance().isMertric()) {
            this.isEnglish = false;
        } else {
            this.isEnglish = true;
        }
        if (this.isEnglish) {
            z = false;
        }
        this.sportDetailPresenter = new SportDetailPresenter(this, z);
    }

    private void initEvent() {
        this.sportDetailPresenter.getDetailGpsLocal(this.uid, this.cardType, this.mSport_type_now);
        this.lastLoadTime = System.currentTimeMillis() / 1000;
        this.sportDetailPresenter.getDetailGpsServer(this.uid, this.lastLoadTime, 20, this.cardType, this.mSport_type_now);
        this.gpsAppBar.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                SportDetailActivity.this.isSee = true;
            }
        });
        this.mSportDataVp.addOnPageChangeListener(new OnPageChangeListener() {
            float lastPosOffset = 0.0f;
            int last_position = SportDetailActivity.this.mSportDataVp.getCurrentItem();

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float totalOffset = positionOffset + ((float) position);
                SportDetailActivity.this.mSportVpIndicator.setMoveOffset(totalOffset);
                if (totalOffset > this.lastPosOffset) {
                    this.last_position = (int) Math.floor((double) totalOffset);
                    SportDetailActivity.this.mSportBar.setViewScaleFromCenter(this.last_position, 1.0f - (SportDetailActivity.SPORT_IMG_FACTOR * (totalOffset - ((float) this.last_position))));
                    SportDetailActivity.this.mSportBar.setViewAlpha(this.last_position, 1.0f - (SportDetailActivity.SPORT_IMG_FACTOR * (totalOffset - ((float) this.last_position))));
                    SportDetailActivity.this.mSportBar.setViewScaleFromCenter(this.last_position + 1, (SportDetailActivity.SPORT_IMG_FACTOR * (totalOffset - ((float) this.last_position))) + 0.6f);
                    SportDetailActivity.this.mSportBar.setViewAlpha(this.last_position + 1, (SportDetailActivity.SPORT_IMG_FACTOR * (totalOffset - ((float) this.last_position))) + 0.6f);
                } else if (totalOffset < this.lastPosOffset) {
                    this.last_position = (int) Math.ceil((double) totalOffset);
                    SportDetailActivity.this.mSportBar.setViewScaleFromCenter(this.last_position, 1.0f - (SportDetailActivity.SPORT_IMG_FACTOR * (((float) this.last_position) - totalOffset)));
                    SportDetailActivity.this.mSportBar.setViewAlpha(this.last_position, 1.0f - (SportDetailActivity.SPORT_IMG_FACTOR * (((float) this.last_position) - totalOffset)));
                    SportDetailActivity.this.mSportBar.setViewScaleFromCenter(this.last_position - 1, (SportDetailActivity.SPORT_IMG_FACTOR * (((float) this.last_position) - totalOffset)) + 0.6f);
                    SportDetailActivity.this.mSportBar.setViewAlpha(this.last_position - 1, (SportDetailActivity.SPORT_IMG_FACTOR * (((float) this.last_position) - totalOffset)) + 0.6f);
                }
                this.lastPosOffset = totalOffset;
            }

            public void onPageSelected(int position) {
                SportDetailActivity.this.mSport_type_now = position;
                SportDetailActivity.this.sportType = position;
                if (SportDetailActivity.this.mGpsInfoAdapter != null) {
                    SportDetailActivity.this.mGpsInfoAdapter.setSport_type(SportDetailActivity.this.sportType);
                }
                KLog.e(SportDetailActivity.this.TAG, "onPageSelected: " + SportDetailActivity.this.sportType);
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        this.mSportRecycler.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                SportDetailActivity.this.isSee = true;
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == 0 && manager.findLastCompletelyVisibleItemPosition() == manager.getItemCount() - 1) {
                    SportDetailActivity.this.getData();
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                SportDetailActivity.this.isSee = true;
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        KLog.e("no2855--> 同步时间: " + SportHomeFragment.syncTime + " -- " + (System.currentTimeMillis() - SportHomeFragment.syncTime));
        if (System.currentTimeMillis() - SportHomeFragment.syncTime > 20000) {
            this.sportDetailPresenter.uploadNoUpSegment(this.uid);
        } else {
            EventBus.getDefault().post(new GpsTotalEvent(1));
        }
    }

    /* access modifiers changed from: private */
    public void getData() {
        if (!this.isAddAll) {
            if (this.mGpsInfoAdapter != null) {
                this.mGpsInfoAdapter.showLoadMore();
            }
            this.sportDetailPresenter.getDetailGpsServer(this.uid, this.lastLoadTime, 20, this.cardType, this.mSport_type_now);
        }
    }

    private void initView() {
        this.mRunDataView = View.inflate(this, R.layout.sport_module_gps_sport_page_layout, null);
        this.mRunTv = (WithUnitText) this.mRunDataView.findViewById(R.id.num_text);
        this.mRunTv2 = (WithUnitText) this.mRunDataView.findViewById(R.id.num_text2);
        this.gpsDetailTime = (WithUnitText) findViewById(R.id.gps_detail_time);
        this.totalDurationTx = (TextView) findViewById(R.id.total_duration_title);
        this.gpsDetailDis = (MyTextView) findViewById(R.id.gps_detail_dis);
        this.gpsDetailBack = (ImageView) findViewById(R.id.gps_detail_back);
        this.mSportDataVp = (ViewPager) findViewById(R.id.sport_data_vp);
        this.mSportBar = (SportTypeSelectBar) findViewById(R.id.sport_bar);
        this.mSportVpIndicator = (SlideVpIndicator) findViewById(R.id.sport_vp_indicator);
        this.mSportRecycler = (RecyclerView) findViewById(R.id.swipe_target);
        this.details_title = (TextView) findViewById(R.id.details_title);
        this.mSportRecycler.setBackgroundColor(RunActivitySkin.RunActy_Base_BG);
        this.gpsAppBar = (AppBarLayout) findViewById(R.id.gps_app_bar);
        this.gpsAppBar.setBackground(new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.GpsTargetActy_Default_Bg_Color));
        if (this.cardType == 0) {
            if (this.isEnglish) {
                this.mRunTv.setUnitTv(getString(R.string.sport_module_distance_unit_mi) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            } else {
                this.mRunTv.setUnitTv(getString(R.string.sport_module_distance_unit_km) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
            this.mRunTv2.setVisibility(8);
            this.gpsDetailTime.setUnitTv("");
            this.gpsDetailTime.setNumTv("00:00:00");
            this.totalDurationTx.setText(getString(R.string.sport_module_total_duration));
        } else {
            this.mRunTv.setNumTv("0 ");
            this.mRunTv.setUnitTv(getString(R.string.sport_module_de_h) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            this.mRunTv2.setVisibility(0);
            this.mRunTv2.setNumTv("0 ");
            this.mRunTv2.setUnitTv(getString(R.string.sport_module_de_min) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            this.gpsDetailTime.setUnitTv(getString(R.string.sport_module_calorie_unit_km));
            this.gpsDetailTime.setNumTv("0");
            this.totalDurationTx.setText(getString(R.string.sport_module_total_calories));
        }
        this.dataViews.add(this.mRunDataView);
        this.gpsDetailDis.setText(String.valueOf(0));
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.gpsDetailDis, this.gpsDetailTime.getNumTv());
        KLog.e("licl", "mSport_type_now:" + this.mSport_type_now);
        setMyTitle(this.mSport_type_now);
        this.mSportDataPagerAdapter = new SportDataPagerAdapter();
        this.mSportBar.addAllTabs();
        this.mSportBar.setImgRes(new int[]{R.mipmap.run_bule_on3x, R.mipmap.cycling_bule_on3x, R.mipmap.walk_bule_on3x, R.mipmap.r1_icon}, R.id.sport_img);
        this.mSportBar.setOnItemclickListener(new OnClickListener() {
            public void onClick(int pos) {
                if (SportDetailActivity.this.mSportDataVp != null && SportDetailActivity.this.mSportDataVp.getCurrentItem() != pos) {
                    SportDetailActivity.this.initSportBar();
                    SportDetailActivity.this.mSportDataVp.setCurrentItem(pos);
                }
            }
        });
        this.mSportDataVp.setAdapter(this.mSportDataPagerAdapter);
        this.mGpsInfoAdapter = new GpsInfoAdapter(this, this.mGps_datas, this.isEnglish, this.cardType);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(1);
        this.mSportRecycler.setLayoutManager(linearLayoutManager);
        this.mSportRecycler.setAdapter(this.mGpsInfoAdapter);
        this.mGpsInfoAdapter.notifyDataSetChanged();
        this.gpsDetailBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SportDetailActivity.this.finish();
            }
        });
        this.mGpsInfoAdapter.setOnLoadClickListener(new OnLoadClickListener() {
            public void onLoadClick() {
                SportDetailActivity.this.getData();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        initSportBar();
        this.mSportBar.setViewScaleFromCenter(this.mSport_type_now, 1.0f);
        this.mSportBar.setViewAlpha(this.mSport_type_now, 1.0f);
        UserConfig.getInstance().initInfoFromOtherModule();
        this.isEnglish = !UserConfig.getInstance().isMertric();
    }

    /* access modifiers changed from: private */
    public void initSportBar() {
        this.mSportBar.setViewScaleFromCenter(0, 0.6f);
        this.mSportBar.setViewAlpha(0, 0.6f);
        this.mSportBar.setViewScaleFromCenter(1, 0.6f);
        this.mSportBar.setViewAlpha(1, 0.6f);
        this.mSportBar.setViewScaleFromCenter(2, 0.6f);
        this.mSportBar.setViewAlpha(2, 0.6f);
        this.mSportBar.setViewScaleFromCenter(3, 0.6f);
        this.mSportBar.setViewAlpha(3, 0.6f);
    }

    private void addData2View(List<SportDetailItem> histories) {
        if (this.mGps_datas.size() > 0 && (this.mGps_datas.get(this.mGps_datas.size() - 1) instanceof GpsFootItem)) {
            this.mGps_datas.remove(this.mGps_datas.size() - 1);
        }
        if (histories == null || histories.size() <= 0) {
            this.mGps_datas.add(new GpsFootItem(2));
            return;
        }
        KLog.e("no2855--> 需要显示到界面的数据: " + JsonUtils.toJson(histories));
        int size = histories.size();
        for (int i = 0; i < size; i++) {
            if (!this.mGps_datas.contains(histories.get(i))) {
                this.itemCount++;
                this.itemClo = ((SportDetailItem) histories.get(i)).getCalorie() + this.itemClo;
                this.itemDur = ((SportDetailItem) histories.get(i)).getDuration() + this.itemDur;
                this.itemDisKm = ((SportDetailItem) histories.get(i)).getDistanceKm() + this.itemDisKm;
                if (((SportDetailItem) histories.get(i)).getUpload() == 1) {
                    this.sportItemData.setCalorie(((SportDetailItem) histories.get(i)).getCalorie() + this.sportItemData.getCalorie());
                    this.sportItemData.setCount(this.sportItemData.getCount() + 1);
                    this.sportItemData.setDistance(((SportDetailItem) histories.get(i)).getDistanceKm() + this.sportItemData.getDistance());
                    this.sportItemData.setTime(((SportDetailItem) histories.get(i)).getDuration() + this.sportItemData.getTime());
                }
                DateUtil dateUtil = new DateUtil(((SportDetailItem) histories.get(i)).getStartTime(), true);
                if (this.lastYear == dateUtil.getYear() || this.lastMonth == dateUtil.getMonth()) {
                    this.lastCalorie = ((SportDetailItem) histories.get(i)).getCalorie() + this.lastCalorie;
                    this.lastDistanceKm = ((SportDetailItem) histories.get(i)).getDistanceKm() + this.lastDistanceKm;
                    this.lastDistanceMile = ((SportDetailItem) histories.get(i)).getDistanceMile() + this.lastDistanceMile;
                    this.lastCount++;
                    this.lastAllTime = ((SportDetailItem) histories.get(i)).getDuration() + this.lastAllTime;
                    if (this.mGps_datas.size() > 0 && (this.mGps_datas.get(this.lastSize) instanceof SportTotalItem)) {
                        ((SportTotalItem) this.mGps_datas.get(this.lastSize)).setActivityCount(this.lastCount);
                        ((SportTotalItem) this.mGps_datas.get(this.lastSize)).setTotalDistanceKm(this.lastDistanceKm);
                        ((SportTotalItem) this.mGps_datas.get(this.lastSize)).setTotalDistanceMile(this.lastDistanceMile);
                        ((SportTotalItem) this.mGps_datas.get(this.lastSize)).setAllTime((long) this.lastAllTime);
                        ((SportTotalItem) this.mGps_datas.get(this.lastSize)).setCalorie(this.lastCalorie);
                    }
                } else {
                    this.lastYear = dateUtil.getYear();
                    this.lastMonth = dateUtil.getMonth();
                    this.lastCalorie = ((SportDetailItem) histories.get(i)).getCalorie();
                    this.lastDistanceKm = ((SportDetailItem) histories.get(i)).getDistanceKm();
                    this.lastDistanceMile = ((SportDetailItem) histories.get(i)).getDistanceMile();
                    this.lastCount = 1;
                    this.lastAllTime = ((SportDetailItem) histories.get(i)).getDuration();
                    SportTotalItem totalItem = new SportTotalItem();
                    totalItem.setYear(this.lastYear);
                    totalItem.setMonth(this.lastMonth);
                    totalItem.setActivityCount(this.lastCount);
                    totalItem.setTotalDistanceKm(this.lastDistanceKm);
                    totalItem.setTotalDistanceMile(this.lastDistanceMile);
                    totalItem.setAllTime((long) this.lastAllTime);
                    totalItem.setCalorie(this.lastCalorie);
                    totalItem.setData_type(this.cardType);
                    this.mGps_datas.add(totalItem);
                    this.lastSize = this.mGps_datas.size() - 1;
                }
                this.mGps_datas.add(histories.get(i));
            }
        }
        if (histories.size() < 20) {
            this.mGps_datas.add(new GpsFootItem(2));
            KLog.d("no2855--> 总数据11: " + this.itemCount + " - " + this.itemDur + " -- ");
            refreshTotalView(this.itemCount, this.itemDur, this.itemDisKm * 1000.0f, this.itemClo);
            if (!(this.copySportAll == null || this.copySportAll.getDoneTimes() == this.itemCount)) {
                KLog.d("no2855--> 不一样的数据11: " + this.itemCount + " - " + this.itemDur + " -- " + this.copySportAll.getDoneTimes());
                this.sportDetailPresenter.updataAllSportTb(this.uid, this.cardType, this.mSport_type_now, this.sportItemData);
            }
        } else {
            this.mGps_datas.add(new GpsFootItem(0));
        }
        this.mGpsInfoAdapter.notifyDataSetChanged();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                Log.d("testEvent11", "LockView is Down");
                break;
            case 1:
                Log.d("testEvent11", "LockView is up");
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setMyTitle(int sportType2) {
        switch (sportType2) {
            case 0:
                this.details_title.setText(getResources().getString(R.string.sport_module_running));
                return;
            case 1:
                this.details_title.setText(getResources().getString(R.string.sport_module_riding));
                return;
            case 2:
                this.details_title.setText(getResources().getString(R.string.sport_module_walking));
                return;
            case 3:
                this.details_title.setText(getResources().getString(R.string.sport_module_mountaineering));
                return;
            case 4:
                this.details_title.setText(getResources().getString(R.string.sport_module_balling));
                return;
            case 5:
                this.details_title.setText(getResources().getString(R.string.sport_module_sport_r1_other));
                return;
            case 6:
                this.details_title.setText(getResources().getString(R.string.sport_swimming_title));
                return;
            default:
                this.details_title.setText(getResources().getString(R.string.sport_module_sport_r1_other));
                return;
        }
    }

    public void loadPageDataSuccess(List<SportDetailItem> sportDetailItemList) {
        addData2View(sportDetailItemList);
    }

    public void loadPageServiceDataSuccess(int netNum, List<SportDetailItem> sportDetailItemList) {
        if (this.isFirstLoad) {
            this.lastYear = 0;
            this.lastMonth = 0;
            this.itemDisKm = 0.0f;
            this.itemDur = 0;
            this.itemClo = 0.0f;
            this.itemCount = 0;
            this.sportItemData.clear();
            this.mGps_datas.clear();
        }
        if (sportDetailItemList != null) {
            if (sportDetailItemList.size() > 0) {
                this.lastLoadTime = ((SportDetailItem) sportDetailItemList.get(sportDetailItemList.size() - 1)).getStartTime();
            }
            if (netNum < 20) {
                this.isAddAll = true;
            }
        } else {
            this.isAddAll = true;
        }
        addData2View(sportDetailItemList);
        if (this.isFirstLoad) {
            this.isFirstLoad = false;
            this.sportDetailPresenter.getTotalGps(this.uid, this.cardType, this.mSport_type_now);
        }
    }

    public void loadAllSportSuccess(CopySportAll copySportAll2) {
        refreshTotalData(copySportAll2);
    }

    public void loadAllSportFail() {
        if (this.sportDetailPresenter != null) {
        }
    }

    public void loadServiceAllSportSuccess(CopySportAll copySportAll2) {
        this.copySportAll = copySportAll2;
        refreshTotalData(copySportAll2);
        if (this.sportDetailPresenter != null) {
        }
    }

    public void loadPageDataFail() {
        KLog.e("no2855怎么会这样呢 onfail 呢");
        if (this.isFirstLoad) {
            this.sportDetailPresenter.getTotalGps(this.uid, this.cardType, this.mSport_type_now);
        }
        this.mGpsInfoAdapter.loadFail();
    }

    private void refreshTotalData(CopySportAll copySportAll2) {
        if (copySportAll2.getDoneTimes() >= 20 || this.itemCount <= 0 || copySportAll2.getDoneTimes() == this.itemCount) {
            KLog.d("no2855--> 总数据22: " + copySportAll2.getDoneTimes() + " - " + copySportAll2.getDuration());
            refreshTotalView(copySportAll2.getDoneTimes(), copySportAll2.getDuration(), copySportAll2.getDistance(), copySportAll2.getCalorie());
            return;
        }
        KLog.d("no2855--> 总数据11: " + this.itemCount + " - " + this.itemDur);
        refreshTotalView(this.itemCount, this.itemDur, this.itemDisKm * 1000.0f, this.itemClo);
        if (copySportAll2 != null && copySportAll2.getDoneTimes() != this.itemCount) {
            this.sportDetailPresenter.updataAllSportTb(this.uid, this.cardType, this.mSport_type_now, this.sportItemData);
        }
    }

    private void refreshTotalView(int count, int duration, float distance, float calorie) {
        if (this.cardType == 0) {
            if (this.isEnglish) {
                this.mRunTv.setNumTv(String.valueOf(Util.doubleToFloat(1, Util.meterToMile(((double) distance) / 1000.0d))) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            } else {
                this.mRunTv.setNumTv(String.valueOf(Util.doubleToFloat(1, ((double) distance) / 1000.0d)) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
            this.gpsDetailTime.setNumTv(String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(duration / 3600)}) + ":" + String.format(Locale.US, "%02d", new Object[]{Integer.valueOf((duration % 3600) / 60)}) + ":" + String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(duration % 60)}));
        } else {
            this.mRunTv.setNumTv((duration / 3600) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            this.mRunTv2.setNumTv(((duration % 3600) / 60) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            this.gpsDetailTime.setNumTv(String.valueOf(Util.doubleToFloat(1, (double) calorie)));
        }
        this.gpsDetailDis.setText(String.valueOf(count));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().post(new GpsTotalEvent(0));
        super.onDestroy();
    }
}
