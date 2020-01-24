package com.iwown.sport_module.Fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.consts.UserConst.DrViva;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.eventbus.EpoEvent;
import com.iwown.data_link.eventbus.ShouldGetWeatherEvent;
import com.iwown.data_link.eventbus.ToDeviceListFragmentEvent;
import com.iwown.data_link.sport_data.R1DataBean;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.lib_common.CommonAdapter;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.ViewHolder;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.lib_common.fragment.SupportFragment;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.iwown.sport_module.Fragment.data.SportHomeFragment;
import com.iwown.sport_module.Fragment.plan.SportPlanFragment;
import com.iwown.sport_module.R;
import com.iwown.sport_module.R1ConvertHandler;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.activity.ShowAdActivity;
import com.iwown.sport_module.contract.SportHomeContract.SportHomeView;
import com.iwown.sport_module.gps.activity.GpsTargetActivity;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.pojo.ErrorTipEvent;
import com.iwown.sport_module.pojo.Weather;
import com.iwown.sport_module.pojo.Weather24hBean;
import com.iwown.sport_module.service.IntentSendUtils;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.view.ErrorTipTextView2;
import com.iwown.sport_module.view.ErrorTipTextView2.OnDisplayEndListener;
import com.iwown.sport_module.view.ErrorTipTextView2.OnTipClickedListener;
import com.iwown.sport_module.view.UpgradeFailDialogRemind;
import com.iwown.sport_module.view.WithUnitText;
import com.iwown.sport_module.zxing.activity.CaptureActivity;
import com.socks.library.KLog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SportFragment extends SupportFragment implements SportHomeView {
    private static final int Bind_Device = 1;
    private static final int Gps_Move = 2;
    private static final int Scan = 0;
    /* access modifiers changed from: private */
    public String TAG = getClass().getSimpleName();
    private String cityName = "";
    private boolean frag_showing_now = false;
    /* access modifiers changed from: private */
    public ImageView mAdBtn;
    private ImageView mCloseMenuBtn;
    /* access modifiers changed from: private */
    public DrawerLayout mDrawerLayout;
    /* access modifiers changed from: private */
    public ErrorTipTextView2 mErrorTipTv;
    private List<Fragment> mFragments = new ArrayList();
    private TextView mGoogleCity;
    /* access modifiers changed from: private */
    public LinearLayout mGuide_active;
    /* access modifiers changed from: private */
    public RelativeLayout mGuide_refresh;
    /* access modifiers changed from: private */
    public ListView mItemList;
    private View mLayout;
    /* access modifiers changed from: private */
    public CommonAdapter<MenuItem> mMenuItemCommonAdapter;
    /* access modifiers changed from: private */
    public List<MenuItem> mMenuItems;
    private ImageView mOpenRightMenuBtn;
    private PreferenceUtility mPref;
    private ConstraintLayout mRightMenu;
    private ConstraintLayout mRootBg;
    private ViewPager mSportVp;
    private View mToActiveBtn;
    private View mTopBar;
    /* access modifiers changed from: private */
    public UpgradeFailDialogRemind mUpgradeFailDialogRemind = null;
    private ImageView mWeatherIcon;
    private WithUnitText mWeatherValue;
    private float now_temperature = -1.0f;
    private float now_temperature_F = -1.0f;

    private class MenuItem {
        public int icon_res;
        public int title_res;
        public int type = -1;

        public MenuItem(int icon_res2, int title_res2, int type2) {
            this.icon_res = icon_res2;
            this.title_res = title_res2;
            this.type = type2;
        }
    }

    public static SportFragment newInstance() {
        Bundle args = new Bundle();
        SportFragment fragment = new SportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mLayout = inflater.inflate(R.layout.sport_module_sport_fragment, null);
        this.mPref = new PreferenceUtility(getActivity());
        EventBus.getDefault().register(this);
        this.mUpgradeFailDialogRemind = new UpgradeFailDialogRemind(getActivity(), false);
        IntentSendUtils.sendDownloadWeight(getContext());
        IntentSendUtils.sendDownloadFatigue(getContext());
        IntentSendUtils.sendDownloadSleep(getContext());
        IntentSendUtils.sendDownloadHeart(getContext());
        IntentSendUtils.sendDownloadEcg(getContext());
        IntentSendUtils.sendDownloadBlood(getContext());
        initData();
        initView();
        initEvent();
        KLog.e(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + DateUtil.getGMTDate(new DateUtil().getUnixTimestamp()));
        return this.mLayout;
    }

    public void onResume() {
        super.onResume();
        KLog.e(this.TAG, "onResume...");
        UserConfig.getInstance().initInfoFromOtherModule();
        this.frag_showing_now = true;
        errorTipDismiss();
        if (!UserConfig.getInstance().isHasGuideHomeRefresh()) {
            this.mGuide_refresh.setVisibility(0);
        } else if (!UserConfig.getInstance().isHasGuideHome()) {
            this.mGuide_active.setVisibility(0);
        }
    }

    private void errorTipDismiss() {
        KLog.e(this.TAG, "errorTipDismiss: " + this.frag_showing_now + "/" + this.mErrorTipTv.isErrorLevelNow() + "/" + this.mErrorTipTv.isErrorTipAnimating());
        if (this.frag_showing_now && this.mErrorTipTv.isErrorLevelNow() && this.mErrorTipTv.isErrorTipAnimating()) {
            this.mErrorTipTv.outDelay(2000);
        }
    }

    public void onHiddenChanged(boolean hidden) {
        String string;
        super.onHiddenChanged(hidden);
        this.frag_showing_now = !hidden;
        KLog.e(this.TAG, "onHiddenChanged...");
        if (!hidden) {
            UserConfig.getInstance().initInfoFromOtherModule();
            errorTipDismiss();
            if (this.now_temperature != -1.0f) {
                if (UserConfig.getInstance().isCentigrade()) {
                    this.mWeatherValue.setNumTv(((int) this.now_temperature) + "");
                } else {
                    this.mWeatherValue.setNumTv(((int) this.now_temperature_F) + "");
                }
                WithUnitText withUnitText = this.mWeatherValue;
                if (UserConfig.getInstance().isCentigrade()) {
                    string = getString(R.string.sport_module_centigrade);
                } else {
                    string = getString(R.string.sport_module_fahrenheit);
                }
                withUnitText.setUnitTv(string);
                this.mGoogleCity.setText(UserConfig.getInstance().getGoogleCityName());
            }
        }
    }

    public void onStop() {
        super.onStop();
        KLog.e(this.TAG, "onStop...");
        this.frag_showing_now = false;
    }

    private void initEvent() {
        this.mGuide_refresh.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserConfig.getInstance().setHasGuideHomeRefresh(true);
                UserConfig.getInstance().save();
                SportFragment.this.mGuide_refresh.setVisibility(8);
                SportFragment.this.mGuide_active.setVisibility(0);
            }
        });
        this.mGuide_active.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserConfig.getInstance().setHasGuideHome(true);
                UserConfig.getInstance().save();
                SportFragment.this.mGuide_active.setVisibility(8);
            }
        });
        this.mToActiveBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserConfig.getInstance().setHasGuideHome(true);
                UserConfig.getInstance().save();
                SportFragment.this.mGuide_active.setVisibility(8);
                RouteUtils.startActiveActivity();
            }
        });
        this.mUpgradeFailDialogRemind.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface anInterface) {
                SportFragment.this.mUpgradeFailDialogRemind.setTitleMsg(SportFragment.this.getString(R.string.failed));
                SportFragment.this.mUpgradeFailDialogRemind.setContentMsg(SportFragment.this.getString(R.string.epo_up_fail_msg));
                SportFragment.this.mUpgradeFailDialogRemind.setBt_okText(SportFragment.this.getString(R.string.device_module_update_step_write_Retry));
            }
        });
        this.mUpgradeFailDialogRemind.setClickCallBack(new ClickCallBack() {
            public void onOk() {
                ModuleRouteDeviceInfoService.getInstance().startEpo();
                SportFragment.this.mUpgradeFailDialogRemind.dismiss();
            }

            public void onCancel() {
                SportFragment.this.mUpgradeFailDialogRemind.dismiss();
            }
        });
        this.mAdBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.i(SportFragment.this.TAG, "click gift button");
                SportFragment.this.startActivity(new Intent(SportFragment.this.getActivity(), ShowAdActivity.class));
                SportFragment.this.mAdBtn.setBackgroundResource(R.mipmap.advertising_3x);
            }
        });
        this.mErrorTipTv.setDisplayEndListener(new OnDisplayEndListener() {
            public void onDisplayEnd() {
                SportFragment.this.mDrawerLayout.setSystemUiVisibility(0);
            }
        });
        this.mItemList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                KLog.e(SportFragment.this.TAG, Integer.valueOf(i));
                SportFragment.this.mDrawerLayout.closeDrawer(5, false);
                switch (((MenuItem) SportFragment.this.mMenuItems.get(i)).type) {
                    case 0:
                        PermissionsUtils.handleCAMER(SportFragment.this.getActivity(), new PermissinCallBack() {
                            public void callBackOk() {
                                SportFragment.this.getActivity().startActivity(new Intent(SportFragment.this.getActivity(), CaptureActivity.class));
                            }

                            public void callBackFial() {
                            }
                        });
                        return;
                    case 1:
                        EventBus.getDefault().post(new ToDeviceListFragmentEvent());
                        return;
                    case 2:
                        SportFragment.this.getActivity().startActivity(new Intent(SportFragment.this.getActivity(), GpsTargetActivity.class));
                        return;
                    default:
                        return;
                }
            }
        });
        this.mCloseMenuBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SportFragment.this.mDrawerLayout.closeDrawer(5, true);
            }
        });
        this.mOpenRightMenuBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SportFragment.this.mDrawerLayout.openDrawer(5, true);
                SportFragment.this.mItemList.setVisibility(0);
                SportFragment.this.mMenuItemCommonAdapter.notifyDataSetChanged();
            }
        });
        this.mDrawerLayout.addDrawerListener(new DrawerListener() {
            private int open_count = 0;

            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            public void onDrawerOpened(View drawerView) {
            }

            public void onDrawerClosed(View drawerView) {
            }

            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void initView() {
        this.mTopBar = this.mLayout.findViewById(R.id.top_bar);
        this.mTopBar.setPadding(DensityUtil.dip2px(getContext(), 20.0f), 0, DensityUtil.dip2px(getContext(), 20.0f), 0);
        this.mTopBar.setTranslationY((float) WindowUtil.getStatusBarHeight());
        this.mWeatherValue = (WithUnitText) this.mTopBar.findViewById(R.id.weather_value);
        this.mWeatherIcon = (ImageView) this.mTopBar.findViewById(R.id.weather_icon);
        this.mOpenRightMenuBtn = (ImageView) this.mTopBar.findViewById(R.id.plus_icon);
        this.mGoogleCity = (TextView) this.mTopBar.findViewById(R.id.google_city_name);
        this.mWeatherValue.setVisibility(8);
        this.mWeatherIcon.setVisibility(8);
        this.mGoogleCity.setVisibility(8);
        this.mRootBg = (ConstraintLayout) this.mLayout.findViewById(R.id.root_bg);
        this.mRootBg.setBackground(new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.Sport_Home_Bg_Color));
        this.mSportVp = (ViewPager) this.mLayout.findViewById(R.id.sport_vp);
        this.mSportVp.setOffscreenPageLimit(2);
        this.mSportVp.setAdapter(new SportFragmentsPagerAdatper(getChildFragmentManager(), this.mFragments));
        PermissionsUtils.handleLOCATION(getActivity(), new PermissinCallBack() {
            public void callBackOk() {
            }

            public void callBackFial() {
            }
        });
        this.mDrawerLayout = (DrawerLayout) this.mLayout.findViewById(R.id.drawer_layout);
        this.mDrawerLayout.setScrimColor(0);
        this.mCloseMenuBtn = (ImageView) this.mLayout.findViewById(R.id.close_menu_btn);
        this.mItemList = (ListView) this.mLayout.findViewById(R.id.item_list);
        this.mMenuItemCommonAdapter = new CommonAdapter<MenuItem>(getActivity(), this.mMenuItems, R.layout.sport_module_home_right_menu_item) {
            public void convert(ViewHolder helper, int position, MenuItem item) {
                helper.setText(R.id.title, SportFragment.this.getString(item.title_res)).setImageResource(R.id.icon, item.icon_res);
                View view = helper.getConvertView();
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setDuration(200);
                animationSet.setStartOffset((long) (position * 200));
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, ((float) (-view.getTop())) * 2.5f, 0.0f);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f, 1, 0.5f, 1, 1.0f);
                animationSet.setInterpolator(new OvershootInterpolator(1.5f));
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translateAnimation);
                view.startAnimation(animationSet);
            }
        };
        this.mItemList.setAdapter(this.mMenuItemCommonAdapter);
        this.mGuide_refresh = (RelativeLayout) this.mLayout.findViewById(R.id.guide_cl);
        this.mGuide_active = (LinearLayout) this.mLayout.findViewById(R.id.guide_active_cl);
        this.mToActiveBtn = this.mLayout.findViewById(R.id.top2);
        this.mRightMenu = (ConstraintLayout) this.mLayout.findViewById(R.id.right_menu);
        this.mRightMenu.setBackground(new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.Sport_Home_Menu_Bg_Color));
        int statusBarHeight = WindowUtil.getStatusBarHeight();
        int screenWidth = WindowUtil.getScreenWidth(getActivity());
        this.mErrorTipTv = (ErrorTipTextView2) this.mLayout.findViewById(R.id.error_tip_tv);
        this.mErrorTipTv.post(new Runnable() {
            public void run() {
                SportFragment.this.mErrorTipTv.setTranslationY((float) (-SportFragment.this.mErrorTipTv.getHeight()));
            }
        });
        ((GradientDrawable) this.mErrorTipTv.getBackground()).setColor(RunActivitySkin.Sport_Home_ErrorTip_Bg);
        this.mErrorTipTv.setOnTipClickedListener(new OnTipClickedListener() {
            public void onTipClicked() {
                KLog.e(SportFragment.this.TAG, "onTipClicked...");
                if (SportFragment.this.mErrorTipTv.isErrorLevelNow()) {
                    SportFragment.this.mErrorTipTv.out();
                    SportFragment.this.mDrawerLayout.setSystemUiVisibility(0);
                }
            }
        });
        this.mAdBtn = (ImageView) this.mLayout.findViewById(R.id.ad_btn);
        if (AppConfigUtil.isZeronerHealthPro() || AppConfigUtil.isNanfei_TRAX_GPS() || AppConfigUtil.isHealthy() || AppConfigUtil.isUpfit() || AppConfigUtil.isNewfit()) {
            this.mAdBtn.setVisibility(8);
        }
        this.mAdBtn.setBackgroundResource(R.mipmap.advertising_3x);
        this.mDrawerLayout.setDrawerLockMode(1);
        if (AppConfigUtil.isIwownFitPro()) {
            getAd();
        } else if (AppConfigUtil.isDrviva()) {
            getAd_Drviva();
        }
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.mWeatherValue.getNumTv());
    }

    private void getAd() {
        double[] doubles = Util.getLocationInfo(getActivity());
        if (doubles == null) {
            KLog.e(this.TAG, "获取位置失败");
            L.file("ad--获取位置失败", 3);
            return;
        }
        NetFactory.getInstance().getClient(new MyCallback<Boolean>() {
            public void onSuccess(Boolean canShowAdRemindDot) {
                if (canShowAdRemindDot.booleanValue()) {
                    SportFragment.this.mAdBtn.setBackgroundResource(R.mipmap.advertising_with_dot3x);
                }
            }

            public void onFail(Throwable e) {
            }
        }).getAdvertise(doubles[0] + "", doubles[1] + "");
    }

    private void getAd_Drviva() {
        if (!TextUtils.isEmpty(this.mPref.fetchStrValueWithKey(DrViva.MarketUrl))) {
            this.mAdBtn.setBackgroundResource(R.mipmap.advertising_with_dot3x);
        }
    }

    private void initData() {
        SportHomeFragment dataFragment = new SportHomeFragment();
        SportPlanFragment planFragment = new SportPlanFragment();
        this.mFragments.add(dataFragment);
        this.mFragments.add(planFragment);
        this.mMenuItems = new ArrayList();
        this.mMenuItems.add(new MenuItem(R.mipmap.scan_2x, R.string.sport_module_Scan_it, 0));
        this.mMenuItems.add(new MenuItem(R.mipmap.bind_dev2x, R.string.sport_module_s2_mamul_bind_tag, 1));
        this.mMenuItems.add(new MenuItem(R.mipmap.gps_menu_2x, R.string.sport_module_GPS_movement, 2));
    }

    public void refreshWeatherView(Weather weather) {
        String string;
        boolean isCentigrade = UserConfig.getInstance().isCentigrade();
        if (weather == null) {
            KLog.i("weather", "fuck,weather is null");
            return;
        }
        L.file("weather-->code: " + weather.getError() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + weather.getType() + "/" + weather.getTemp(), 4);
        KLog.e("weather-->code: " + weather.getError() + "---------" + weather.getType() + "/" + weather.getTemp() + "===" + isAdded());
        try {
            if (weather.getError() != -1) {
                this.mWeatherValue.setVisibility(8);
                this.mWeatherIcon.setVisibility(8);
                this.mGoogleCity.setVisibility(8);
                this.now_temperature = -1.0f;
                this.now_temperature_F = (float) ((int) weather.getF_tmp());
                this.cityName = UserConfig.getInstance().getGoogleCityName();
                return;
            }
            this.mWeatherValue.setVisibility(8);
            this.mWeatherIcon.setVisibility(8);
            this.mGoogleCity.setVisibility(8);
            String tempStr = ((int) weather.getTemp()) + "";
            WithUnitText withUnitText = this.mWeatherValue;
            if (!isCentigrade) {
                tempStr = ((int) weather.getF_tmp()) + "";
            }
            withUnitText.setNumTv(tempStr);
            WithUnitText withUnitText2 = this.mWeatherValue;
            if (isCentigrade) {
                string = getString(R.string.sport_module_centigrade);
            } else {
                string = getString(R.string.sport_module_fahrenheit);
            }
            withUnitText2.setUnitTv(string);
            this.mGoogleCity.setText(UserConfig.getInstance().getGoogleCityName());
            this.now_temperature = (float) ((int) weather.getTemp());
            this.now_temperature_F = (float) ((int) weather.getF_tmp());
            this.mWeatherIcon.setBackgroundResource(weather.getIcon_res());
            ModuleRouteDeviceInfoService.getInstance().writeWeather((float) weather.getTemp(), Weather.weather_type_num, UserConfig.getInstance().getCountry(), UserConfig.getInstance().getPm25(), JsonTool.toJson(weather.getWeather24hBeans()));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void refreshTopPic(String file_path) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorTipEvent event) {
        KLog.d(this.TAG, "收到ErrorTipEvent:" + JsonTool.toJson(event));
        this.mDrawerLayout.setSystemUiVisibility(4);
        switch (event.getTip_level()) {
            case 0:
                this.mErrorTipTv.showOKTip(event.getTip_content());
                this.mDrawerLayout.postDelayed(new Runnable() {
                    public void run() {
                        SportFragment.this.mDrawerLayout.setSystemUiVisibility(0);
                    }
                }, 1000);
                this.mErrorTipTv.outDelay(1000);
                break;
            case 1:
                this.mErrorTipTv.showWarningTip(event.getTip_content());
                break;
            case 2:
                this.mErrorTipTv.showErrorTip(event.getTip_content());
                break;
        }
        if (!this.mErrorTipTv.isErrorTipAnimating()) {
            this.mErrorTipTv.cancelAnims();
            this.mErrorTipTv.in();
            this.mDrawerLayout.setSystemUiVisibility(4);
        }
        errorTipDismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EpoEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShouldGetWeatherEvent event) {
        if (event.getFlag() != 1 && event.getFlag() == 10) {
            if (new DateUtil().getUnixTimestamp() - UserConfig.getInstance().getWeatherGo() > 0) {
                UserConfig.getInstance().setWeatherGo(new DateUtil().getUnixTimestamp() + 1800);
                UserConfig.getInstance().save();
                return;
            }
            String weather24 = UserConfig.getInstance().getWeather24h();
            KLog.i("取缓存天气:" + weather24);
            if (!TextUtils.isEmpty(weather24)) {
                List<Weather24hBean> weather24hBeans = JsonTool.getListJson(weather24, Weather24hBean.class);
                Collections.sort(weather24hBeans, new Comparator<Weather24hBean>() {
                    public int compare(Weather24hBean bean, Weather24hBean t1) {
                        long diff = bean.getTime_stamp() - t1.getTime_stamp();
                        if (diff < 0) {
                            return -1;
                        }
                        if (diff == 0) {
                            return 0;
                        }
                        return 1;
                    }
                });
                KLog.e("找到本地缓存天气：" + JsonTool.toJson(weather24hBeans));
                Weather weather = null;
                int i = 0;
                while (true) {
                    if (i >= weather24hBeans.size()) {
                        break;
                    }
                    Weather24hBean weather24hBean = (Weather24hBean) weather24hBeans.get(i);
                    Weather24hBean next = (Weather24hBean) weather24hBeans.get(i + 1 >= weather24hBeans.size() ? i : i + 1);
                    long compare_time = System.currentTimeMillis();
                    if (compare_time < weather24hBean.getTime_stamp() * 1000 || compare_time > next.getTime_stamp() * 1000) {
                        i++;
                    } else {
                        KLog.e("找到一个缓存天气-->" + JsonTool.toJson(weather24hBean));
                        if (!weather24hBean.isCentigrade()) {
                            weather = new Weather((double) ((int) Util.F2C(weather24hBean.getTemperature())), "", weather24hBean.getPm_25() + "");
                            weather.setF_tmp(weather24hBean.getTemperature());
                        } else {
                            weather = new Weather(weather24hBean.getTemperature(), "", weather24hBean.getPm_25() + "");
                            weather.setF_tmp((double) ((int) Util.C2F(weather24hBean.getTemperature())));
                        }
                        weather.getWeather24hBeans().clear();
                        weather.getWeather24hBeans().addAll(weather24hBeans);
                        Weather.weather_type_num = weather24hBean.weather_type;
                        weather.setWeatherDrawAsFirmwareWeahterType();
                    }
                }
                if (weather != null) {
                    refreshWeatherView(weather);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventR1TableData(R1DataBean r1DataBean) {
        R1ConvertHandler.gpsAndR1Disp(r1DataBean);
        R1ConvertHandler.tb68ToConvertHistory(r1DataBean);
        r1dataProgress(r1DataBean);
    }

    private void r1dataProgress(final R1DataBean r1DataBean) {
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                R1ConvertHandler.gpsAndR1Disp(r1DataBean);
                R1ConvertHandler.tb68ToConvertHistory(r1DataBean);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).subscribe((Consumer<? super T>) new Consumer<String>() {
            public void accept(String s) throws Exception {
                KLog.e("yanxi", s);
            }
        });
    }
}
