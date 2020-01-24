package com.iwown.device_module.device_setting.ble_scale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.OptionsPickerView.Builder;
import com.bigkoo.pickerview.OptionsPickerView.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.CustomListener;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.ScaleCleanWifi;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.UI;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.dialog.TipDialog;
import com.iwown.device_module.device_setting.wifi_scale.WifiScaleSettingContract.View;
import com.iwown.device_module.device_setting.wifi_scale.activity.QuestionActivity;
import com.iwown.device_module.device_setting.wifi_scale.activity.WeightDataNotBelongToActivity;
import com.iwown.device_module.device_setting.wifi_scale.activity.WifiUserListActivity;
import com.iwown.device_module.device_setting.wifi_scale.bean.WifiScaleSetting;
import com.iwown.device_module.device_setting.wifi_scale.data.WifiScaleData;
import com.iwown.device_module.device_setting.wifi_scale.eventbus.EventbusFinish;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.device_module.device_setting.wifi_scale.util.WifiScaleSettingPresenter;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.lib_common.fragment.SupportFragment;
import com.iwown.lib_common.network.NetworkUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LFScaleSettingFragment extends SupportFragment implements OnClickListener, View {
    TipDialog alert;
    private ItemView autoWeight;
    /* access modifiers changed from: private */
    public int changeUnit = 1;
    private List<String> items = new ArrayList();
    private LinearLayout l_wifi_scale_name;
    WifiScaleSettingPresenter presenter;
    /* access modifiers changed from: private */
    public OptionsPickerView pvCustomOptions;
    private TextView scaleMacMy;
    private TextView scaleNameMy;
    private ItemView toConfigWifi;
    private ItemView toHelpFaq;
    private ItemView toUnarchivedDataSetting;
    private ItemView toUnitSwitch;
    /* access modifiers changed from: private */
    public ItemView toUserList;
    private TextView wifiScaleUnbind;

    public static LFScaleSettingFragment newInstance() {
        Bundle args = new Bundle();
        LFScaleSettingFragment fragment = new LFScaleSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.device_module_scale_setting, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    private void initView(android.view.View view) {
        this.presenter = new WifiScaleSettingPresenter(this);
        this.scaleNameMy = (TextView) view.findViewById(R.id.scale_name_my);
        this.scaleMacMy = (TextView) view.findViewById(R.id.scale_mac_my);
        this.scaleMacMy.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.scale_ble_connect, 0, 0, 0);
        this.scaleMacMy.setCompoundDrawablePadding(10);
        this.scaleMacMy.setVisibility(8);
        this.toUnarchivedDataSetting = (ItemView) view.findViewById(R.id.to_unarchived_data_setting);
        this.toUserList = (ItemView) view.findViewById(R.id.to_user_list);
        this.toUnitSwitch = (ItemView) view.findViewById(R.id.to_unit_switch);
        this.toConfigWifi = (ItemView) view.findViewById(R.id.to_config_wifi);
        this.toConfigWifi.setVisibility(8);
        this.autoWeight = (ItemView) view.findViewById(R.id.auto_weight);
        this.toHelpFaq = (ItemView) view.findViewById(R.id.to_help_faq);
        this.toHelpFaq.setVisibility(8);
        this.wifiScaleUnbind = (TextView) view.findViewById(R.id.wifi_scale_unbind);
        this.l_wifi_scale_name = (LinearLayout) view.findViewById(R.id.wifi_scale_name);
        this.toUnarchivedDataSetting.setOnClickListener(this);
        this.toUserList.setOnClickListener(this);
        this.toUnitSwitch.setOnClickListener(this);
        this.toConfigWifi.setOnClickListener(this);
        this.toHelpFaq.setOnClickListener(this);
        this.autoWeight.setOnClickListener(this);
        this.wifiScaleUnbind.setOnClickListener(this);
        this.l_wifi_scale_name.setOnClickListener(this);
        initData();
    }

    public void onClick(android.view.View v) {
        boolean z = true;
        int id = v.getId();
        if (id == R.id.to_unarchived_data_setting) {
            UI.startActivity(this._mActivity, WeightDataNotBelongToActivity.class);
        } else if (id == R.id.to_user_list) {
            UI.startActivity(this._mActivity, WifiUserListActivity.class);
        } else if (id == R.id.to_help_faq) {
            UI.startActivity(this._mActivity, QuestionActivity.class);
        } else if (id == R.id.auto_weight) {
            WifiScaleSetting setting = this.presenter.wifiScaleSettingStatue();
            if (setting.isAutomaticArchive()) {
                z = false;
            }
            setting.setAutomaticArchive(z);
            this.presenter.saveWifiScaleStatue(setting);
        } else if (id == R.id.to_unit_switch) {
            if (!NetworkUtils.isNetworkAvailable()) {
                ToastUtil.showToast(getString(R.string.network_error));
                return;
            }
            this.pvCustomOptions = new Builder(this._mActivity, new OnOptionsSelectListener() {
                public void onOptionsSelect(int options1, int option2, int options3, android.view.View v) {
                    KLog.i("option1" + options1);
                    int unit = 0;
                    if (options1 == 0) {
                        unit = 2;
                    } else if (options1 == 1) {
                        unit = LFScaleSettingFragment.this.changeUnit;
                    } else if (options1 == 2) {
                        unit = 3;
                    }
                    WifiScaleSetting setting = LFScaleSettingFragment.this.presenter.wifiScaleSettingStatue();
                    setting.setUnitSwitch(unit);
                    LFScaleSettingFragment.this.presenter.saveWifiScaleStatue(setting);
                    NetFactory.getInstance().getClient(null).scaleSetUnit(unit, S2WifiUtils.wifiScaleMac(ContextUtil.getUID()));
                }
            }).setLayoutRes(R.layout.device_module_layout_picker_wheelview_option, new CustomListener() {
                public void customLayout(android.view.View v) {
                    TextView tvSubmit = (TextView) v.findViewById(R.id.ok_dialog_option);
                    ((TextView) v.findViewById(R.id.dialog_title_option)).setText(LFScaleSettingFragment.this.getString(R.string.device_module_scale_wifi_setting_5));
                    TextView ivCancel = (TextView) v.findViewById(R.id.cancel_dialog_option);
                    tvSubmit.setOnClickListener(new OnClickListener() {
                        public void onClick(android.view.View v) {
                            LFScaleSettingFragment.this.pvCustomOptions.returnData();
                            LFScaleSettingFragment.this.pvCustomOptions.dismiss();
                        }
                    });
                    ivCancel.setOnClickListener(new OnClickListener() {
                        public void onClick(android.view.View v) {
                            LFScaleSettingFragment.this.pvCustomOptions.dismiss();
                        }
                    });
                }
            }).setSelectOptions(this.presenter.wifiScaleSettingStatue().getUnitSwitch() == 2 ? 0 : 1).setOutSideCancelable(false).setTextColorOut(getResources().getColor(R.color.device_module_device_wifi_picker_un_select)).setLineSpacingMultiplier(2.0f).setDividerColor(getResources().getColor(R.color.device_module_common_line_color)).setTextColorCenter(getResources().getColor(R.color.device_module_white)).setBgColor(getResources().getColor(R.color.device_module_common_background_1)).setContentTextSize(22).isCenterLabel(true).isDialog(false).setLabels("", "", "").build();
            this.pvCustomOptions.setNPicker(this.items, null, null);
            this.pvCustomOptions.show();
        } else if (id == R.id.wifi_scale_unbind) {
            if (!NetworkUtils.isNetworkAvailable()) {
                ToastUtil.showToast(getString(R.string.network_error));
                return;
            }
            this.alert = new TipDialog(this._mActivity, false);
            this.alert.setClickCallBack(new ClickCallBack() {
                public void onOk() {
                    ScaleCleanWifi req = new ScaleCleanWifi();
                    req.setUid(ContextUtil.getLUID());
                    req.setScaleid(S2WifiUtils.wifiScaleMac(ContextUtil.getUID()));
                    NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                        public void onSuccess(Integer integer) {
                            LFScaleSettingFragment.this.alert.dismiss();
                            if (BluetoothOperation.isBind()) {
                                EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Setting));
                            } else {
                                EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Check_List));
                            }
                            EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Top_Unbind_Device));
                            HealthDataEventBus.updateHealthWeightEvent();
                        }

                        public void onFail(Throwable e) {
                            LFScaleSettingFragment.this.alert.dismiss();
                        }
                    }).unBindScale(req);
                }

                public void onCancel() {
                    LFScaleSettingFragment.this.alert.dismiss();
                }
            });
            this.alert.show();
            this.alert.setTitleMsg(getString(R.string.device_module_device_setting_tip_dialog_title));
            this.alert.setBt_okText(getString(R.string.device_module_common_cormfir_yes));
            this.alert.setBt_cancel(getString(R.string.device_module_common_cormfir_no));
            this.alert.setContentMsg(getString(R.string.device_module_scale_wifi_data_belong_to_unbind));
        } else if (id == R.id.to_config_wifi) {
            ARouter.getInstance().build(RouteUtils.Actvity_Sport_WifiInputAcitvity).navigation();
        }
    }

    private void initData() {
        this.items.clear();
        if (AppConfigUtil.isHealthy()) {
            this.items.add(getString(R.string.my_module_unit_kg));
            this.items.add(getString(R.string.my_module_unit_ng));
            this.changeUnit = 3;
        } else {
            this.items.add(getString(R.string.my_module_unit_kg));
            this.items.add(getString(R.string.unit_lbs));
            this.changeUnit = 1;
        }
        if (this.presenter.wifiScaleSettingStatue().isAutomaticArchive()) {
            this.autoWeight.setMessageText(getString(R.string.device_module_on));
        } else {
            this.autoWeight.setMessageText(getString(R.string.device_module_off));
        }
        if (this.presenter.wifiScaleSettingStatue().getUnitSwitch() == 2) {
            this.toUnitSwitch.setMessageText(getString(R.string.my_module_unit_kg));
        } else if (this.presenter.wifiScaleSettingStatue().getUnitSwitch() == 1) {
            this.toUnitSwitch.setMessageText(getString(R.string.unit_lbs));
        } else if (this.presenter.wifiScaleSettingStatue().getUnitSwitch() == 3) {
            this.toUnitSwitch.setMessageText(getString(R.string.my_module_unit_ng));
        }
        final int users = WifiScaleData.getInstance().userCount();
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                LFScaleSettingFragment.this.toUserList.setMessageText(String.valueOf(WifiScaleData.getInstance().userCount()));
            }

            public void onFail(Throwable e) {
                LFScaleSettingFragment.this.toUserList.setMessageText(String.valueOf(users));
            }
        }).getNoAccountList();
    }

    public void saveStatueSuccess() {
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadData(EventbusFinish eventbusFinish) {
        if (eventbusFinish.getAction() == 2) {
            initData();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
