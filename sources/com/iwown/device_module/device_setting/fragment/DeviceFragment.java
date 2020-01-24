package com.iwown.device_module.device_setting.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothCallbackReceiver;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.UI;
import com.iwown.device_module.device_operation.type.DeviceListActivity;
import com.iwown.device_module.device_setting.adapter.ViewPagerAdapter;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.device_module.device_setting.view.HorizontalPickerView;
import com.iwown.device_module.device_setting.view.HorizontalPickerView.CurrentItemChangeListener;
import com.iwown.device_module.device_setting.view.HorizontalPickerView.OnItemClickListener;
import com.iwown.device_module.device_setting.wifi_scale.WifiScaleSettingFragment;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.fragment.SupportFragment;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DeviceFragment extends SupportFragment {
    ViewPagerAdapter adapter;
    LottieAnimationView animView;
    private ConstraintLayout constraintLayout;
    List<String> deviceTitle = new ArrayList();
    List<Fragment> fragmentList = new ArrayList();
    private ImageView imgDevice;
    private boolean isReload;
    private boolean isVisible;
    private Handler mHandler = new Handler(Looper.myLooper());
    Runnable preDialogDismiss = new Runnable() {
        public void run() {
        }
    };
    private DeviceReceiver receiver = new DeviceReceiver();
    /* access modifiers changed from: private */
    public HorizontalPickerView tablayout1;
    TextView toast_msg;
    View view;
    ViewPager viewPager;

    private class DeviceReceiver extends BluetoothCallbackReceiver {
        private DeviceReceiver() {
        }

        public void connectStatue(boolean isConnect) {
            super.connectStatue(isConnect);
            if (isConnect) {
                DeviceFragment.this.showDialog(false);
            }
        }

        public void onBluetoothInit() {
            super.onBluetoothInit();
            DeviceFragment.this.showDialog(false);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.device_module_fragment_tablayout, container, false);
        EventBus.getDefault().register(this);
        long s1 = System.currentTimeMillis();
        initView(this.view);
        initData();
        initEvent();
        KLog.e("no2855deviceFragment初始化耗时始: " + (System.currentTimeMillis() - s1));
        return this.view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(ContextUtil.app).registerReceiver(this.receiver, BaseActionUtils.getIntentFilter());
    }

    private void initEvent() {
        this.view.postDelayed(new Runnable() {
            public void run() {
                if (new DateUtil().getUnixTimestamp() > PrefUtil.getLong(ContextUtil.app, UserAction.User_Log_History_Time)) {
                    PrefUtil.save((Context) ContextUtil.app, UserAction.User_Log_History_Time, new DateUtil().getUnixTimestamp() + 86400);
                    DeviceFragment.this.tablayout1.scrollto(PrefUtil.getInt((Context) DeviceFragment.this._mActivity, UserAction.User_Page_Select, 0));
                }
            }
        }, 1000);
        try {
            this.imgDevice.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    UI.startActivity(DeviceFragment.this._mActivity, DeviceListActivity.class);
                }
            });
            this.adapter = new ViewPagerAdapter(ContextUtil.app, getChildFragmentManager(), this.fragmentList, this.deviceTitle);
            this.viewPager.setAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
            this.tablayout1.setData(this.deviceTitle);
            this.tablayout1.scrollto(PrefUtil.getInt((Context) this._mActivity, UserAction.User_Page_Select, 0));
            this.viewPager.setCurrentItem(PrefUtil.getInt((Context) this._mActivity, UserAction.User_Page_Select, 0));
            this.tablayout1.setOnItemClickListener(new OnItemClickListener() {
                public void onClick(View view, int position) {
                    DeviceFragment.this.viewPager.setCurrentItem(position);
                }
            });
            this.tablayout1.setCurrentItemChangeListener(new CurrentItemChangeListener() {
                public void onCurrentItemChanged(View view, int position) {
                    PrefUtil.save((Context) DeviceFragment.this._mActivity, UserAction.User_Page_Select, position);
                }

                public void onScrollChangedFinish(View view, int position) {
                    DeviceFragment.this.viewPager.setCurrentItem(position);
                }
            });
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static DeviceFragment newInstance() {
        Bundle args = new Bundle();
        DeviceFragment fragment = new DeviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onHiddenChanged(boolean hidden) {
        long s1 = System.currentTimeMillis();
        super.onHiddenChanged(hidden);
        if (BluetoothOperation.isBind() && !BluetoothOperation.isConnected() && !hidden) {
            showDialog(true);
            EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Device_Fragment_Visable));
        }
        if (!hidden && this.isVisible && BluetoothOperation.isBind() && this.isReload) {
            this.isReload = false;
            initData();
            initEvent();
        }
        KLog.e("no2855耗时k开始: " + (System.currentTimeMillis() - s1) + " - " + hidden);
    }

    private void initData() {
        this.fragmentList.clear();
        this.deviceTitle.clear();
        KLog.e("===BluetoothOperation.isBind()===" + BluetoothOperation.isBind());
        if (BluetoothOperation.isBind() || BluetoothOperation.isConnected()) {
            this.fragmentList.add(IvSettingFragment.newInstance());
            if (PrefUtil.getInt(this._mActivity, SharedPreferencesAction.User_Sdk_type) == 1 || PrefUtil.getInt(this._mActivity, SharedPreferencesAction.User_Sdk_type) == 3) {
                this.deviceTitle.add(getString(R.string.device_module_tab_title_1));
                KLog.i("add bracelet");
            } else if (PrefUtil.getInt(this._mActivity, SharedPreferencesAction.User_Sdk_type) == 2) {
                KLog.i(String.format("------------heasdset %d", new Object[]{Integer.valueOf(PrefUtil.getInt(this._mActivity, SharedPreferencesAction.EARPHONE))}));
                if (PrefUtil.getInt(this._mActivity, SharedPreferencesAction.EARPHONE) == 1) {
                    this.deviceTitle.add(getString(R.string.device_module_tab_title_4));
                } else if (PrefUtil.getInt(this._mActivity, SharedPreferencesAction.EARPHONE) == 2) {
                    this.deviceTitle.add(getString(R.string.device_module_tab_title_3));
                    KLog.i("add watch");
                } else {
                    this.deviceTitle.add(getString(R.string.device_module_tab_title_1));
                }
            } else if (PrefUtil.getInt(this._mActivity, SharedPreferencesAction.User_Sdk_type) == 4) {
                if (PrefUtil.getInt(this._mActivity, SharedPreferencesAction.PROTOBUF) == 2) {
                    this.deviceTitle.add(getString(R.string.device_module_tab_title_1));
                } else {
                    this.deviceTitle.add(getString(R.string.device_module_tab_title_3));
                }
            }
        } else {
            this.isReload = true;
            KLog.e("================设备页去空白页================");
        }
        if (S2WifiUtils.s2WifiConfigMacIsOK(ContextUtil.getLUID())) {
            this.fragmentList.add(WifiScaleSettingFragment.newInstance());
            this.deviceTitle.add(getString(R.string.device_module_tab_title_2));
        }
    }

    private void initView(View view2) {
        this.viewPager = (ViewPager) view2.findViewById(R.id.view_pager);
        this.imgDevice = (ImageView) view2.findViewById(R.id.add_device);
        this.constraintLayout = (ConstraintLayout) view2.findViewById(R.id.dialog_show);
        this.animView = (LottieAnimationView) view2.findViewById(R.id.pre_loading);
        this.toast_msg = (TextView) view2.findViewById(R.id.toast_msg);
        if (AppConfigUtil.isUpfit() || AppConfigUtil.isNewfit()) {
            this.imgDevice.setVisibility(8);
        }
        this.tablayout1 = (HorizontalPickerView) view2.findViewById(R.id.tab_layout_1);
    }

    /* access modifiers changed from: private */
    public void showDialog(boolean show) {
    }

    public void onSupportVisible() {
        super.onSupportVisible();
        this.isVisible = true;
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        LocalBroadcastManager.getInstance(ContextUtil.app).unregisterReceiver(this.receiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchPage(DevicePageSwitch devicePageSwitch) {
        if (devicePageSwitch.getAction().equals(DevicePageSwitch.Device_Top_Switch_To_Scale)) {
            initData();
            if (this.fragmentList.size() > 1) {
                PrefUtil.save((Context) this._mActivity, UserAction.User_Page_Select, 1);
            } else {
                PrefUtil.save((Context) this._mActivity, UserAction.User_Page_Select, 0);
            }
            initEvent();
        } else if (devicePageSwitch.getAction().equals(DevicePageSwitch.Device_Top_Switch_To_Setting)) {
            PrefUtil.save((Context) this._mActivity, UserAction.User_Page_Select, 0);
            initData();
            initEvent();
        } else if (devicePageSwitch.getAction().equals(DevicePageSwitch.Device_Top_Unbind_Device)) {
            PrefUtil.save((Context) this._mActivity, UserAction.User_Page_Select, 0);
            initData();
            initEvent();
        }
    }
}
