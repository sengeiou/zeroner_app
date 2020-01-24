package com.iwown.sport_module.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.heart.HeartData;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.sport_module.R;
import com.iwown.sport_module.contract.R1DataPresenter.R1DataImpl;
import com.iwown.sport_module.device_data.AmapLinearCheckBar;
import com.iwown.sport_module.device_data.AmapLinearCheckBar.OnCheckedChangeListener;
import com.iwown.sport_module.device_data.AmapScrollView;
import com.iwown.sport_module.device_data.AmapScrollView.OnScrollListener;
import com.iwown.sport_module.device_data.view.RunChartItem;
import com.iwown.sport_module.device_data.view.RunDiagramsItem;
import com.iwown.sport_module.device_data.view.RunGoogleMapItem;
import com.iwown.sport_module.device_data.view.RunHeartItem;
import com.iwown.sport_module.device_data.view.RunMapDataItem;
import com.iwown.sport_module.device_data.view.RunMapLayout;
import com.iwown.sport_module.device_data.view.RunMapViewItem;
import com.iwown.sport_module.device_data.view.RunR1SportItem;
import com.iwown.sport_module.gps.data.TB_location_down;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import com.iwown.sport_module.pojo.DataFragmentBean;
import com.iwown.sport_module.pojo.DevSupportAnalysisModuleInfo;
import com.iwown.sport_module.pojo.DiagramsData;
import com.iwown.sport_module.pojo.MapHealthyData;
import com.iwown.sport_module.pojo.R1DataBean;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.view.checkbar.AChecKBarAdapter;
import com.iwown.sport_module.view.run.DlineDataBean;
import com.socks.library.KLog;
import com.sweetzpot.stravazpot.StravaUtil;
import com.sweetzpot.stravazpot.StravaUtil.MyStravaCallback;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import org.litepal.crud.DataSupport;

public class MapRunActivity extends BaseRunActivity implements OnScrollListener, R1DataImpl {
    public static final int DEV_TYPE_EARPHONE = 3;
    public static final int DEV_TYPE_PHONE = 2;
    public static final int DEV_TYPE_WATCH = 1;
    public static final int DEV_TYPE_WRIST = 0;
    public static final int DEV_TYPE_WRIST_GPS = 4;
    static DecimalFormat decimalFormat;
    private String TAG = getClass().getSimpleName();
    private LinearLayout addLayout;
    private int age;
    CopySportGps copySportGps;
    /* access modifiers changed from: private */
    public String dataFrom;
    private String dataFrom2 = "";
    private String devModel = "";
    private int devType = 0;
    long end_time = 0;
    private int fromType = 0;
    LinkedList<Integer> groupNum = new LinkedList<>();
    private boolean hasSend = false;
    private int hrType;
    private boolean isAdd = false;
    private boolean isHealthy;
    private boolean isLoad6161 = false;
    private boolean isMetric = true;
    private int lastY = 0;
    /* access modifiers changed from: private */
    public TextView loadText;
    private ImageView mBackBtn;
    private List<Bean> mBeans;
    List<DataFragmentBean> mDataFragmentBeans;
    private DateUtil mDate;
    private DiagramsData mDiagramsData = null;
    private String mFileName61;
    private Handler mHandler;
    private HeartData mHeartData = null;
    private List<DevSupportAnalysisModuleInfo> mInfos;
    LoadingDialog mLoadingDialog = null;
    /* access modifiers changed from: private */
    public List<LongitudeAndLatitude> mLongitudeAndLatitudes;
    private MapHealthyData mMapHealthyData = null;
    private AmapLinearCheckBar mRunCheckBar;
    private String mSaveDirPath61;
    AmapScrollView mScrollView;
    /* access modifiers changed from: private */
    public int mSportType;
    private ConstraintLayout mTopContainer;
    private int mType;
    /* access modifiers changed from: private */
    public long mUid;
    private float mUserHeight;
    private int mapHeight = 0;
    /* access modifiers changed from: private */
    public int mapNum = 2;
    private R1DataBean r1DataBean;
    private RunMapLayout runMapViewItem;
    /* access modifiers changed from: private */
    public ArrayList<Integer> scrollList = new ArrayList<>();
    private Runnable scrollRunnable = new Runnable() {
        public void run() {
            MapRunActivity.this.scrollList.clear();
            Iterator it = MapRunActivity.this.groupNum.iterator();
            while (it.hasNext()) {
                Integer integer = (Integer) it.next();
                if (integer.intValue() == 2) {
                    MapRunActivity.this.scrollList.add(Integer.valueOf(0));
                } else {
                    MapRunActivity.this.scrollList.add(Integer.valueOf(((ViewGroup) MapRunActivity.this.viewGroup.get(integer)).getTop()));
                }
            }
            MapRunActivity.this.scrollSize = MapRunActivity.this.scrollList.size();
        }
    };
    /* access modifiers changed from: private */
    public int scrollSize = 1;
    private int scroollType = 0;
    private boolean showDiagrams = false;
    private boolean showHR = false;
    private boolean showMap = false;
    private boolean showPaceChart = false;
    private boolean showR1 = false;
    long start_time = 0;
    Map<Integer, ViewGroup> viewGroup = new HashMap();

    private class Bean {
        public int img_selector;
        public String text;
        public int text_selector;

        public Bean(int img_selector2, int text_selector2, String text2) {
            this.img_selector = img_selector2;
            this.text_selector = text_selector2;
            this.text = text2;
        }

        public Bean(int img_selector2, String text2) {
            this.img_selector = img_selector2;
            this.text = text2;
        }

        public Bean(int img_selector2) {
            this.img_selector = img_selector2;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_activity_run_amap_new);
        WindowUtil.setTopWindows(getWindow());
        this.mLoadingDialog = new LoadingDialog(this);
        this.isHealthy = AppConfigUtil.isHealthy(this);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mapHeight = DensityUtil.dip2px(this, 350.0f);
        findByView(savedInstanceState);
        decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                MapRunActivity.this.initData();
                MapRunActivity.this.initView();
                MapRunActivity.this.initEvent();
                MapRunActivity.this.initStrava();
            }
        }, 300);
    }

    /* access modifiers changed from: private */
    public void initStrava() {
        if (!this.isHealthy) {
            StravaUtil.getInstance(getApplicationContext()).setStravaCallback(new MyStravaCallback() {
                public void onResult(int code) {
                    if (code == 6) {
                        MapRunActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MapRunActivity.this, "Please reuthenticate with Strava", 1).show();
                            }
                        });
                        KLog.e("要去重新授权了");
                    }
                }
            });
        }
    }

    private void findByView(Bundle savedInstanceState) {
        this.mBackBtn = (ImageView) findViewById(R.id.back_btn);
        this.mRunCheckBar = (AmapLinearCheckBar) findViewById(R.id.run_check_bar);
        this.mTopContainer = (ConstraintLayout) findViewById(R.id.top_container);
        this.mTopContainer.setBackground(RunActivitySkin.RunActy_Top_BG);
        this.mTopContainer.setPadding(0, WindowUtil.getStatusBarHeight(), 0, 0);
        this.mScrollView = (AmapScrollView) findViewById(R.id.amap_scrollView);
        this.mScrollView.setFocusable(true);
        this.loadText = (TextView) findViewById(R.id.load_txt);
        this.mScrollView.setOnScrollListener(this);
        this.loadText.setVisibility(0);
        if (this.isHealthy) {
            this.runMapViewItem = new RunMapViewItem((Context) this, 1);
        } else {
            this.runMapViewItem = new RunGoogleMapItem((Context) this, 0);
        }
        this.runMapViewItem.initView(this, savedInstanceState);
    }

    /* access modifiers changed from: private */
    public void initData() {
        this.start_time = getIntent().getLongExtra("start_time", 0);
        this.end_time = getIntent().getLongExtra("end_time", 0);
        this.mType = getIntent().getIntExtra("type", 0);
        this.dataFrom = getIntent().getStringExtra("data_from");
        if (this.dataFrom != null) {
            this.dataFrom2 = this.dataFrom.toUpperCase(Locale.US);
        }
        this.isMetric = UserConfig.getInstance().isMertric();
        this.mUserHeight = (float) UserConfig.getInstance().getHeight();
        this.mUid = UserConfig.getInstance().getNewUID();
        this.age = UserConfig.getInstance().getAge();
        this.mSportType = getIntent().getIntExtra("sport_type", 0);
        this.copySportGps = ModuleRouteSportService.getInstance().getOneTbSport(this.mUid, this.start_time / 1000, this.mType, this.mSportType, this.dataFrom);
        KLog.e("no2855--> sport_type: " + this.mSportType + " - " + this.copySportGps.getStart_time() + " - " + this.dataFrom + " - " + this.mType);
        boolean isP1 = false;
        if (!this.dataFrom2.contains("ANDROID") && !this.dataFrom2.contains("IPHONE") && hasHeart(this.dataFrom)) {
            this.showHR = true;
        }
        if (ModuleRouteSportService.getInstance().isP1(this.dataFrom)) {
            isP1 = true;
        }
        if (this.mType == 0) {
            if (this.dataFrom2.contains("ANDROID") || this.dataFrom2.contains("IPHONE")) {
                this.devType = 2;
                this.showMap = true;
                this.showDiagrams = true;
                this.showPaceChart = true;
                if (this.dataFrom2.contains("VOICE")) {
                    this.showHR = true;
                    this.showR1 = true;
                    this.showDiagrams = false;
                    this.showPaceChart = true;
                }
            } else {
                if (!TextUtils.isEmpty(this.copySportGps.getR1_url())) {
                    this.showHR = true;
                    this.showR1 = true;
                }
                if (isP1) {
                    KLog.e(" no2855判定为 手表，显示地图-->");
                    this.devType = 1;
                    this.showMap = true;
                    this.showHR = true;
                    this.showPaceChart = true;
                    this.showDiagrams = true;
                } else if (!this.dataFrom2.contains("VOICE")) {
                    this.devType = 0;
                }
                if (this.dataFrom.contains("Band23") || this.dataFrom.contains("SW 650") || this.dataFrom.contains("P1MINI")) {
                    this.devType = 4;
                    this.showMap = true;
                    this.showPaceChart = true;
                    this.showDiagrams = true;
                }
                if (this.mSportType == 0) {
                    this.mSportType = 7;
                } else if (this.mSportType == 1) {
                    this.mSportType = Opcodes.FLOAT_TO_LONG;
                } else if (this.mSportType == 2) {
                    this.mSportType = Opcodes.DIV_INT;
                } else if (this.mSportType == 3) {
                    this.mSportType = 5;
                }
            }
            KLog.d("no2855--> 新的设备名: " + this.dataFrom2 + " - " + this.mSportType);
        } else if (isP1) {
            this.devType = 1;
            this.showHR = true;
        } else if (!this.dataFrom2.contains("VOICE")) {
            this.devType = 0;
            this.showHR = true;
        }
        if (TextUtils.isEmpty(this.copySportGps.getGps_url()) && TextUtils.isEmpty(this.copySportGps.getHeart_url()) && TextUtils.isEmpty(this.copySportGps.getR1_url()) && isP1 && !this.dataFrom.contains("P1MINI")) {
            this.isLoad6161 = true;
        }
        this.mDate = new DateUtil(this.start_time, false);
        this.mFileName61 = this.mUid + "_" + this.mDate.getSyyyyMMddDate() + "_" + this.dataFrom + ".txt";
        this.mSaveDirPath61 = LogPath.DATA61_PATH;
    }

    private void initLoad() {
        if (this.isLoad6161) {
            this.mPresenter.initCopySportGps(this.copySportGps, this.isMetric, this.isLoad6161);
            if (this.devType == 1) {
                this.mPresenter.getHealthyDataAboutWatch(this.mUid, this.start_time, this.end_time, this.dataFrom, this.mFileName61, this.mSaveDirPath61, this.mSportType, this.isMetric, this.age, this.devModel);
                this.mPresenter.getWatchTrackData(this.mUid, this.start_time, this.end_time, this.dataFrom, this.mUid + "_" + this.mDate.getSyyyyMMddDate() + "_" + this.dataFrom + ".txt", LogPath.DATA62_PATH);
                return;
            }
            this.mPresenter.getHealthyDataAboutWrist(this.mUid, this.start_time, this.end_time, this.dataFrom, this.age, this.devModel);
            return;
        }
        if (this.devType == 1) {
            if (this.showMap) {
                this.mPresenter.getDeviceLocation(this.mUid, this.dataFrom, this.start_time / 1000, this.end_time / 1000, this.copySportGps.getGps_url());
                this.mPresenter.initModel(this.copySportGps, this.mUid, this.start_time, this.end_time, this.dataFrom, this.mFileName61, this.mSaveDirPath61, this.mSportType, this.isMetric);
            }
        } else if (this.devType == 2) {
            this.mPresenter.initCopySportGps(this.copySportGps, this.isMetric, this.isLoad6161);
            this.mPresenter.getPhoneLocation(this.mUid, this.mSportType, this.dataFrom, this.start_time, this.end_time, this.copySportGps.getGps_url());
        } else if (this.devType == 4) {
            this.mPresenter.initCopySportGps(this.copySportGps, this.isMetric, this.isLoad6161);
            this.mPresenter.getDeviceLocation(this.mUid, this.dataFrom, this.start_time / 1000, this.end_time / 1000, this.copySportGps.getGps_url());
        }
        if (this.showHR) {
            this.mPresenter.getSportHeart(this.mUid, this.dataFrom, this.start_time, this.age, this.copySportGps.getHeart_url());
        }
    }

    /* access modifiers changed from: private */
    public void initEvent() {
        if (this.showR1) {
            this.mPresenter.getR1Data(this.mUid, this.dataFrom, this.start_time, this.copySportGps.getR1_url());
        }
        initLoad();
        this.mBackBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MapRunActivity.this.finish();
            }
        });
        this.mRunCheckBar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckChanged(int position, boolean isChecked) {
                if (isChecked && position >= 0 && position < MapRunActivity.this.scrollSize && MapRunActivity.this.scrollList != null && MapRunActivity.this.scrollList.size() > 0) {
                    MapRunActivity.this.mScrollView.smoothScrollTo(0, ((Integer) MapRunActivity.this.scrollList.get(position % MapRunActivity.this.scrollList.size())).intValue());
                }
            }
        });
        if (!this.showMap && !this.showHR) {
            this.loadText.setText(getString(R.string.sport_module_no_hr));
        }
    }

    private void addModuleItem() {
        this.addLayout = (LinearLayout) findViewById(R.id.sport_add_layout);
        this.mBeans = new ArrayList();
        if (this.showMap) {
            this.mBeans.add(new Bean(R.drawable.sport_module_run_activity_map_selector));
            this.addLayout.addView(this.runMapViewItem);
            RunMapDataItem runMapDataItem = new RunMapDataItem(this, this.mDate, this.isMetric, this.mSportType);
            runMapDataItem.adjustMapHealthyDataUi(this.devType);
            runMapDataItem.refreshMapDataViews(this.mMapHealthyData);
            this.viewGroup.put(Integer.valueOf(2), runMapDataItem);
            this.addLayout.addView((View) this.viewGroup.get(Integer.valueOf(2)));
            this.groupNum.add(Integer.valueOf(2));
        }
        if (this.showHR) {
            this.mBeans.add(new Bean(R.drawable.sport_module_run_activity_heart_rate_selector));
            RunHeartItem runHeartItem = new RunHeartItem(this);
            runHeartItem.refreshHeartDataViews(this.mHeartData);
            this.viewGroup.put(Integer.valueOf(3), runHeartItem);
            this.addLayout.addView((View) this.viewGroup.get(Integer.valueOf(3)));
            this.groupNum.add(Integer.valueOf(3));
        }
        if (this.showPaceChart) {
            this.mBeans.add(new Bean(R.drawable.sport_module_run_activity_chart_selector));
            RunChartItem runChartItem = new RunChartItem((Context) this, this.isMetric);
            runChartItem.refreshChartView(this.mDataFragmentBeans);
            this.viewGroup.put(Integer.valueOf(4), runChartItem);
            this.addLayout.addView((View) this.viewGroup.get(Integer.valueOf(4)));
            this.groupNum.add(Integer.valueOf(4));
        }
        if (this.showDiagrams) {
            this.mBeans.add(new Bean(R.drawable.sport_module_run_activity_diagrams_selector));
            RunDiagramsItem runDiagramsItem = new RunDiagramsItem((Context) this, this.isMetric, this.mSportType);
            runDiagramsItem.adjustDiagramUi(this.devType);
            runDiagramsItem.refreshDiagrams(this.mDiagramsData);
            if (this.mMapHealthyData != null) {
                runDiagramsItem.refreshPaceRate(this.mMapHealthyData.getPace(), this.mMapHealthyData.getRate() + "");
            }
            this.viewGroup.put(Integer.valueOf(5), runDiagramsItem);
            this.addLayout.addView((View) this.viewGroup.get(Integer.valueOf(5)));
            this.groupNum.add(Integer.valueOf(5));
        }
        if (this.showR1) {
            this.mBeans.add(new Bean(R.drawable.sport_module_run_activity_sport_selector));
            RunR1SportItem runR1SportItem = new RunR1SportItem(this);
            runR1SportItem.refreshR1SportView(this.r1DataBean);
            this.viewGroup.put(Integer.valueOf(6), runR1SportItem);
            this.addLayout.addView((View) this.viewGroup.get(Integer.valueOf(6)));
            this.groupNum.add(Integer.valueOf(6));
            if (this.viewGroup.get(Integer.valueOf(2)) != null) {
                if (this.r1DataBean != null) {
                    ((RunMapDataItem) this.viewGroup.get(Integer.valueOf(2))).refreshPace(this.r1DataBean.getSpeed_avg());
                }
                if (!this.showMap) {
                    ((RunMapDataItem) this.viewGroup.get(Integer.valueOf(2))).goneSomeView();
                }
            }
        }
    }

    private void addModuleImg() {
        if (this.showMap) {
            this.mScrollView.setMapHeight(this.mapHeight);
            if (this.runMapViewItem != null) {
                KLog.d("no2855---> 设备type:  " + this.devType + " == " + this.dataFrom);
                this.runMapViewItem.refreshDataView(this.devType, this.dataFrom);
                return;
            }
            return;
        }
        if (this.showHR) {
            this.loadText.setVisibility(8);
        }
        this.mapHeight = 0;
        this.mScrollView.setMapHeight(this.mapHeight);
    }

    /* access modifiers changed from: private */
    public void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        switch (this.devType) {
            case 0:
            case 1:
                int mti = Util.getSporyImgOrName(0, this.mSportType);
                if (mti == -1) {
                    title.setText(getString(R.string.sport_module_run));
                    break;
                } else {
                    title.setText(getString(mti));
                    break;
                }
            case 2:
                if (this.mSportType != 0) {
                    if (this.mSportType != 1) {
                        if (this.mSportType != 2) {
                            title.setText(R.string.sport_module_other);
                            break;
                        } else {
                            title.setText(R.string.sport_module_walking);
                            break;
                        }
                    } else {
                        title.setText(R.string.sport_module_sport_plan_cycling);
                        break;
                    }
                } else {
                    title.setText(R.string.sport_module_run);
                    break;
                }
        }
        addModuleImg();
        addModuleItem();
        this.mRunCheckBar.setAdapter(new AChecKBarAdapter<Bean>(this, R.layout.sport_module_active_data_type_check_item, this.mBeans, 0) {
            public void bindCheckRes(View itemView, Bean bean) {
                ((TextView) itemView.findViewById(R.id.data_type_text)).setVisibility(8);
                ((ImageView) itemView.findViewById(R.id.data_type_img)).setBackground(MapRunActivity.this.getResources().getDrawable(bean.img_selector));
            }
        });
    }

    public void onStart() {
        super.onStart();
        KLog.e("onStart");
        if (this.runMapViewItem != null) {
            this.runMapViewItem.onStart();
        }
    }

    public void onResume() {
        super.onResume();
        KLog.e("onResume");
        if (this.runMapViewItem != null) {
            this.runMapViewItem.onResume();
        }
    }

    public void onPause() {
        super.onPause();
        if (this.runMapViewItem != null) {
            this.runMapViewItem.onPause();
        }
    }

    public void onStop() {
        super.onStop();
        if (this.runMapViewItem != null) {
            this.runMapViewItem.onStop();
        }
    }

    public void refreshMapView(List<LongitudeAndLatitude> locations) {
        this.mLongitudeAndLatitudes = locations;
        refreshMap();
        if (this.devType == 2) {
            KLog.d("no2855:-> dev_type==DEV_TYPE_PHONE");
            this.mPresenter.getHealthyDataAboutPhone(this.mUid, this.start_time, this.end_time, this.mUserHeight, this.isMetric, this.mSportType, this.dataFrom);
        } else if (this.devType == 1) {
            Log.i(this.TAG, "------------startNewStrava DEV_TYPE_WATCH");
            uploadStrava();
        } else if (this.devType == 4) {
            this.mPresenter.getHealthyDataAboutPhone(this.mUid, this.start_time, this.end_time, this.mUserHeight, this.isMetric, this.mSportType, this.dataFrom);
            this.mPresenter.getWristGpsPace(this.mUid, this.dataFrom, this.start_time, this.end_time, true);
            Log.i(this.TAG, "------------startNewStrava DEV_TYPE_WRIST_GPS");
            uploadStrava();
        }
        refreshAllViewScrollY();
    }

    private void uploadStrava() {
        if (this.copySportGps == null || !"1".equals(this.copySportGps.getGps_url())) {
            StravaUtil.getInstance(this).startNewStrava(this.mUid, this.mSportType, this.start_time / 1000, this.end_time / 1000, this.dataFrom);
        } else {
            Executors.newFixedThreadPool(1).submit(new Runnable() {
                public void run() {
                    DataUtil.writeBlueOneGps2SD(MapRunActivity.this.mUid, MapRunActivity.this.start_time / 1000, MapRunActivity.this.end_time / 1000, MapRunActivity.this.dataFrom);
                    StravaUtil.getInstance(MapRunActivity.this).startNewStrava(MapRunActivity.this.mUid, MapRunActivity.this.mSportType, MapRunActivity.this.start_time / 1000, MapRunActivity.this.end_time / 1000, MapRunActivity.this.dataFrom);
                }
            });
        }
    }

    public void onMapHealthDataArrive(MapHealthyData healthyData) {
        this.mMapHealthyData = healthyData;
        this.mapNum--;
        if (this.mapNum <= 0) {
            this.loadText.post(new Runnable() {
                public void run() {
                    MapRunActivity.this.loadText.setVisibility(8);
                }
            });
        }
        if (this.viewGroup.get(Integer.valueOf(2)) != null && (this.viewGroup.get(Integer.valueOf(2)) instanceof RunMapDataItem)) {
            if (this.mMapHealthyData != null && this.mMapHealthyData.getHr() == 0 && this.mHeartData != null && this.mHeartData.getAvg() > 0) {
                this.mMapHealthyData.setHr(this.mHeartData.getAvg());
            }
            ((RunMapDataItem) this.viewGroup.get(Integer.valueOf(2))).refreshMapDataViews(this.mMapHealthyData);
            refreshAllViewScrollY();
        }
        if (this.mMapHealthyData != null && this.mMapHealthyData.getHr() > 0 && this.viewGroup.get(Integer.valueOf(3)) != null && (this.viewGroup.get(Integer.valueOf(3)) instanceof RunHeartItem)) {
            ((RunHeartItem) this.viewGroup.get(Integer.valueOf(3))).refreshAvgHeart(this.mMapHealthyData.getHr());
        }
    }

    public void onHeartDataArrive(HeartData heartData) {
        this.mHeartData = heartData;
        if (this.viewGroup.get(Integer.valueOf(3)) != null && (this.viewGroup.get(Integer.valueOf(3)) instanceof RunHeartItem)) {
            if (this.mHeartData != null && this.mHeartData.getMaxHeart() == 0) {
                this.mHeartData.setMaxHeart(220 - this.age);
            }
            if (this.mMapHealthyData != null && this.mMapHealthyData.getHr() > 0) {
                this.mHeartData.setAvg(this.mMapHealthyData.getHr());
            }
            ((RunHeartItem) this.viewGroup.get(Integer.valueOf(3))).refreshHeartDataViews(this.mHeartData);
            if ((this.mHeartData != null && this.mHeartData.getAvg() > 0 && this.mMapHealthyData != null && this.mMapHealthyData.getHr() == 0) && this.viewGroup.get(Integer.valueOf(2)) != null && (this.viewGroup.get(Integer.valueOf(2)) instanceof RunMapDataItem)) {
                ((RunMapDataItem) this.viewGroup.get(Integer.valueOf(2))).refreshHeart(this.mHeartData.getAvg());
            }
            refreshAllViewScrollY();
            if (this.devType != 4) {
                return;
            }
            if (this.mDiagramsData == null || (this.mDiagramsData != null && this.mDiagramsData.getRateDataBeans().size() == 0)) {
                this.mPresenter.getWristGpsPace(this.mUid, this.dataFrom, this.start_time, this.end_time, true);
            }
        }
    }

    public void onPaceChartBeansArrive(List<DataFragmentBean> beans) {
        if (this.viewGroup.get(Integer.valueOf(4)) != null && (this.viewGroup.get(Integer.valueOf(4)) instanceof RunChartItem)) {
            ((RunChartItem) this.viewGroup.get(Integer.valueOf(4))).refreshChartView(beans);
            refreshAllViewScrollY();
        }
    }

    public void onDiagramArrive(DiagramsData diagramsData) {
        this.mDiagramsData = diagramsData;
        if (this.devType == 1 && ((this.mDiagramsData == null || this.mDiagramsData.getPaceDataBeans().size() == 0) && !this.isAdd)) {
            this.isAdd = true;
            this.mPresenter.getWristGpsPace(this.mUid, this.dataFrom, this.start_time, this.end_time, false);
        }
        if (!(this.mDiagramsData.getPaceDataBeans() == null || this.mDiagramsData.getPaceDataBeans().size() == 0)) {
            this.mDiagramsData.getPaceDataBeans().add(0, new DlineDataBean(((DlineDataBean) this.mDiagramsData.getPaceDataBeans().get(0)).time - 60, 0.0f));
        }
        if (!(this.mDiagramsData.getRateDataBeans() == null || this.mDiagramsData.getRateDataBeans().size() == 0)) {
            this.mDiagramsData.getRateDataBeans().add(0, new DlineDataBean(((DlineDataBean) this.mDiagramsData.getRateDataBeans().get(0)).time - 60, 0.0f));
        }
        if (this.viewGroup.get(Integer.valueOf(5)) != null) {
            ((RunDiagramsItem) this.viewGroup.get(Integer.valueOf(5))).refreshDiagrams(this.mDiagramsData);
            if (this.mMapHealthyData != null) {
                ((RunDiagramsItem) this.viewGroup.get(Integer.valueOf(5))).refreshPaceRate(this.mMapHealthyData.getPace(), this.mMapHealthyData.getRate() + "");
            }
            refreshAllViewScrollY();
        }
        if (!this.hasSend) {
            this.hasSend = true;
            ModuleRouteSportService.getInstance().uploadNoUpGps(this.mUid);
        }
    }

    public void onR1Data(R1DataBean dataBean) {
        this.r1DataBean = dataBean;
        ((RunR1SportItem) this.viewGroup.get(Integer.valueOf(6))).refreshR1SportView(this.r1DataBean);
    }

    private void refreshMap() {
        KLog.e(this.TAG, "no2855-->refreshMap1111111");
        if (this.runMapViewItem != null) {
            this.runMapViewItem.post(new Runnable() {
                public void run() {
                    MapRunActivity.this.mapNum = MapRunActivity.this.mapNum - 1;
                    if (MapRunActivity.this.mapNum == 0) {
                        MapRunActivity.this.loadText.setVisibility(8);
                    }
                    if (MapRunActivity.this.mLongitudeAndLatitudes != null && MapRunActivity.this.mLongitudeAndLatitudes.size() != 0) {
                        MapRunActivity.this.drawAmapMap(MapRunActivity.this.mLongitudeAndLatitudes);
                    }
                }
            });
        }
    }

    public void onScroll(int scrollY) {
        if (scrollY - this.lastY > 3) {
            if (this.scroollType < this.scrollSize - 1 && Math.abs(scrollY - ((Integer) this.scrollList.get(this.scroollType + 1)).intValue()) < 100) {
                KLog.d("no2855滑动距离加 scrolly --> " + this.scroollType + " 差距: " + Math.abs(scrollY - ((Integer) this.scrollList.get(this.scroollType + 1)).intValue()));
                this.scroollType++;
                if (this.mRunCheckBar != null) {
                    this.mRunCheckBar.setCheck(this.scroollType);
                }
            }
            this.lastY = scrollY;
        } else if (scrollY - this.lastY < -3) {
            if (this.scroollType > 0 && Math.abs(scrollY - ((Integer) this.scrollList.get(this.scroollType - 1)).intValue()) < 100) {
                KLog.d("no2855滑动距离减 scrolly --> " + this.scroollType + " 差距: " + Math.abs(scrollY - ((Integer) this.scrollList.get(this.scroollType - 1)).intValue()));
                this.scroollType--;
                if (this.mRunCheckBar != null) {
                    this.mRunCheckBar.setCheck(this.scroollType);
                }
            }
            this.lastY = scrollY;
        }
    }

    public void showR1Data(R1DataBean r1DataBean2) {
        this.r1DataBean = r1DataBean2;
        if (this.viewGroup.get(Integer.valueOf(6)) != null) {
            ((RunR1SportItem) this.viewGroup.get(Integer.valueOf(6))).refreshR1SportView(r1DataBean2);
            if (this.viewGroup.get(Integer.valueOf(2)) != null && r1DataBean2 != null) {
                ((RunMapDataItem) this.viewGroup.get(Integer.valueOf(2))).refreshPace(r1DataBean2.getSpeed_avg());
            }
        }
    }

    public void showHrData(HeartData heartData) {
        this.mHeartData = heartData;
        if (this.viewGroup.get(Integer.valueOf(3)) != null) {
            ((RunHeartItem) this.viewGroup.get(Integer.valueOf(3))).refreshHeartDataViews(this.mHeartData);
            refreshAllViewScrollY();
        }
    }

    public void controlLoading(boolean shouldShow) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (!this.isHealthy) {
            StravaUtil.getInstance(this).setStravaCallback(null);
        }
        if (this.runMapViewItem != null) {
            this.runMapViewItem.onDestroy();
        }
        super.onDestroy();
    }

    public void drawAmapMap(List<LongitudeAndLatitude> longitudeAndLatitudes) {
        this.mapNum--;
        if (this.mapNum == 0) {
            this.loadText.setVisibility(8);
        }
        if (longitudeAndLatitudes.size() > 0) {
            if (this.showMap) {
                if (this.runMapViewItem != null) {
                    this.mapHeight = this.runMapViewItem.getMapHeight();
                }
                this.mScrollView.setMapHeight(this.mapHeight);
            }
            if (this.runMapViewItem != null) {
                if (this.devType == 2) {
                    TB_location_down down = (TB_location_down) DataSupport.where("uid=? and time_id=?", this.mUid + "", (this.start_time / 1000) + "").findFirst(TB_location_down.class);
                    if (down != null) {
                        this.fromType = down.getFrom();
                    }
                }
                this.runMapViewItem.drawAmapMap(longitudeAndLatitudes, this.devType, this.fromType);
            }
        }
    }

    public void refreshAllViewScrollY() {
        this.mHandler.removeCallbacks(this.scrollRunnable);
        this.mHandler.postDelayed(this.scrollRunnable, 1200);
    }

    public boolean hasHeart(String dataFrom3) {
        boolean noHr;
        if (TextUtils.isEmpty(dataFrom3)) {
            return false;
        }
        if (dataFrom3.contains("Bracel04") || dataFrom3.contains("Braceli5") || dataFrom3.contains("Bracel02") || dataFrom3.contains("Bracel15") || dataFrom3.contains("I6Dk")) {
            noHr = true;
        } else {
            noHr = false;
        }
        if (!noHr) {
            return true;
        }
        return false;
    }
}
