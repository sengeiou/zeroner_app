package com.iwown.device_module.device_operation.type;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.adapter.ComViewHolder;
import com.iwown.device_module.common.adapter.ComViewHolder.OnItemClickListener;
import com.iwown.device_module.common.adapter.CommonRecyAdapter;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.Utils;
import com.iwown.device_module.common.view.TitleBar;
import com.iwown.device_module.common.view.WrapContentLinearLayoutManager;
import com.iwown.device_module.common.view.dialog.TipDialog;
import com.iwown.device_module.device_operation.bean.DeviceType;
import com.iwown.device_module.device_operation.bean.ModeTypes;
import com.iwown.device_module.device_operation.bean.ModeTypes.DataBean;
import com.iwown.device_module.device_operation.search.HealthScaleConnectActivity;
import com.iwown.device_module.device_operation.search.SearchActivity;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.lib_common.fragment.SupportFragment;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

@Route(path = "/device/DeviceListFragment")
public class DeviceListFragment extends SupportFragment {
    public static boolean leftBack;
    private Activity activity;
    private TitleBar deviceListTitle;
    /* access modifiers changed from: private */
    public List<DeviceType> list = new ArrayList();
    private RecyclerView lvDeviceType;
    MyAdapter myAdapter;
    /* access modifiers changed from: private */
    public TipDialog permDialog;
    /* access modifiers changed from: private */
    public TipDialog tipDialog;
    View view;

    class MyAdapter extends CommonRecyAdapter<DeviceType> {
        private Context context;

        class ViewHolder extends ComViewHolder {
            /* access modifiers changed from: private */
            public ImageView image2DeviceList;
            /* access modifiers changed from: private */
            public TextView text2DeviceList;

            public ViewHolder(View itemView) {
                super(itemView);
                this.image2DeviceList = (ImageView) itemView.findViewById(R.id.image_2_device_list);
                this.text2DeviceList = (TextView) itemView.findViewById(R.id.text_2_device_list);
            }
        }

        public MyAdapter(Context context2, List<DeviceType> dataList, int layoutId) {
            super(context2, dataList, layoutId);
            this.context = context2;
        }

        public int getItemCount() {
            return super.getItemCount();
        }

        /* access modifiers changed from: protected */
        public ComViewHolder setComViewHolder(View view, int viewType) {
            return new ViewHolder(view);
        }

        public void onBindItem(android.support.v7.widget.RecyclerView.ViewHolder holder, int position, DeviceType device) {
            super.onBindItem(holder, position, device);
            if (!(holder instanceof ViewHolder)) {
                return;
            }
            if (device.getClassid() == 1) {
                ((ViewHolder) holder).image2DeviceList.setImageResource(R.mipmap.bracel_2x_1);
                ((ViewHolder) holder).text2DeviceList.setText(DeviceListFragment.this.getString(R.string.device_module_tab_title_1));
            } else if (device.getClassid() == 2) {
                ((ViewHolder) holder).image2DeviceList.setImageResource(R.mipmap.watch_2x_1);
                ((ViewHolder) holder).text2DeviceList.setText(DeviceListFragment.this.getString(R.string.device_module_tab_title_3));
            } else if (device.getClassid() == 3) {
                ((ViewHolder) holder).image2DeviceList.setImageResource(R.mipmap.scale_3x_new);
                ((ViewHolder) holder).text2DeviceList.setText(DeviceListFragment.this.getString(R.string.device_module_tab_title_2));
            } else if (device.getClassid() == 4) {
                ((ViewHolder) holder).image2DeviceList.setImageResource(R.mipmap.earphone_2x_1);
                ((ViewHolder) holder).text2DeviceList.setText(DeviceListFragment.this.getString(R.string.device_module_tab_title_4));
            } else if (device.getClassid() == 6) {
                ((ViewHolder) holder).image2DeviceList.setImageResource(R.mipmap.health_scale_3x);
                ((ViewHolder) holder).text2DeviceList.setText(DeviceListFragment.this.getString(R.string.device_module_tab_title_health));
            }
        }
    }

    public void onAttach(Activity activity2) {
        super.onAttach(activity2);
        this.activity = activity2;
    }

    public static DeviceListFragment newInstance(boolean flag) {
        leftBack = flag;
        Bundle args = new Bundle();
        DeviceListFragment fragment = new DeviceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DeviceListFragment newInstance() {
        leftBack = false;
        Bundle args = new Bundle();
        DeviceListFragment fragment = new DeviceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.device_module_layout_device_list, container, false);
        initView(this.view);
        initData();
        return this.view;
    }

    private void initView(View view2) {
        String country = PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_class_model_country);
        NetFactory.getInstance().getClient(null).deviceClassId_1(AppConfigUtil.APP_TYPE, country);
        NetFactory.getInstance().getClient(null).deviceClassIdDetail(AppConfigUtil.APP_TYPE, country);
        this.deviceListTitle = (TitleBar) view2.findViewById(R.id.device_list_title);
        this.lvDeviceType = (RecyclerView) view2.findViewById(R.id.lv_device_type);
        this.deviceListTitle.setTitle(R.string.home_device);
        this.deviceListTitle.setImmersive(true);
        this.deviceListTitle.setTitleColor(-1);
        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this._mActivity);
        layoutManager.setOrientation(1);
        this.lvDeviceType.setLayoutManager(layoutManager);
        if (leftBack) {
            showLeftBack();
        }
    }

    public void showLeftBack() {
        this.deviceListTitle.setLeftImageResource(R.mipmap.back3x);
        this.deviceListTitle.setLeftClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (DeviceListFragment.this._mActivity instanceof DeviceListActivity) {
                    DeviceListFragment.this._mActivity.finish();
                }
            }
        });
    }

    private void initData() {
        this.list.clear();
        String modelClass = PrefUtil.getString(this._mActivity, SharedPreferencesAction.APP_SDK_UPDATE_Types);
        if (TextUtils.isEmpty(modelClass)) {
            modelClass = Utils.getFromAssets(this._mActivity, "modesdklist1default.txt");
            PrefUtil.save((Context) this._mActivity, SharedPreferencesAction.APP_SDK_UPDATE_Types, modelClass);
        }
        ModeTypes modeTypes = (ModeTypes) JsonUtils.fromJson(modelClass, ModeTypes.class);
        List<Integer> supportClassIDs = new ArrayList<>();
        if (AppConfigUtil.isUpfit()) {
            supportClassIDs.add(Integer.valueOf(1));
        } else if (AppConfigUtil.isNanfei_TRAX_GPS()) {
            supportClassIDs.add(Integer.valueOf(1));
            supportClassIDs.add(Integer.valueOf(2));
        } else if (AppConfigUtil.isNewfit()) {
            supportClassIDs.add(Integer.valueOf(1));
        } else {
            supportClassIDs.add(Integer.valueOf(1));
            supportClassIDs.add(Integer.valueOf(2));
            supportClassIDs.add(Integer.valueOf(3));
            supportClassIDs.add(Integer.valueOf(4));
            supportClassIDs.add(Integer.valueOf(6));
        }
        for (DataBean dataBean : modeTypes.getData()) {
            if (supportClassIDs.contains(Integer.valueOf(dataBean.getClassid()))) {
                this.list.add(new DeviceType(dataBean.getClassname(), dataBean.getClassid()));
            }
        }
        this.myAdapter = new MyAdapter(this._mActivity, this.list, R.layout.device_module_layout_device_list_item_view);
        this.lvDeviceType.setAdapter(this.myAdapter);
        this.myAdapter.notifyDataSetChanged();
        this.myAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final int position, View view) {
                ZGBaseUtils.DownloadBp();
                try {
                    if (((DeviceType) DeviceListFragment.this.list.get(position)).getClassid() == 3) {
                        PermissionsUtils.handleCAMER(DeviceListFragment.this._mActivity, new PermissinCallBack() {
                            public void callBackOk() {
                                if (S2WifiUtils.s2WifiConfigMacIsOK(ContextUtil.getLUID())) {
                                    DeviceListFragment.this.showTipDialog(((DeviceType) DeviceListFragment.this.list.get(position)).getClassid());
                                    return;
                                }
                                if (DeviceListFragment.this._mActivity instanceof DeviceListActivity) {
                                    DeviceListFragment.this._mActivity.finish();
                                }
                                ARouter.getInstance().build(RouteUtils.Actvity_Sport_CaptureActivity).navigation();
                            }

                            public void callBackFial() {
                            }
                        });
                        return;
                    }
                    if (((DeviceType) DeviceListFragment.this.list.get(position)).getClassid() == 1 || ((DeviceType) DeviceListFragment.this.list.get(position)).getClassid() == 2 || ((DeviceType) DeviceListFragment.this.list.get(position)).getClassid() == 4) {
                        if (BluetoothOperation.isBind()) {
                            DeviceListFragment.this.showTipDialog(((DeviceType) DeviceListFragment.this.list.get(position)).getClassid());
                            return;
                        }
                    } else if (((DeviceType) DeviceListFragment.this.list.get(position)).getClassid() == 6 && BluetoothOperation.isBind()) {
                        DeviceListFragment.this.showTipDialog(((DeviceType) DeviceListFragment.this.list.get(position)).getClassid());
                        return;
                    }
                    DeviceListFragment.this.requestLocPermission(position);
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void requestLocPermission(final int position) {
        Intent intent;
        if (PermissionsUtils.hasPermission(this._mActivity, "android.permission.ACCESS_FINE_LOCATION")) {
            if (this._mActivity instanceof DeviceListActivity) {
                this._mActivity.finish();
            }
            if (isLocationEnabled()) {
                if (((DeviceType) this.list.get(position)).getClassid() == 6) {
                    intent = new Intent(this._mActivity, HealthScaleConnectActivity.class);
                } else {
                    intent = new Intent(this._mActivity, SearchActivity.class);
                }
                intent.putExtra("classId", ((DeviceType) this.list.get(position)).getClassid());
                startActivity(intent);
                return;
            }
            openGPS();
            return;
        }
        if (this.permDialog == null) {
            this.permDialog = new TipDialog(this._mActivity, false);
            this.permDialog.setClickCallBack(new ClickCallBack() {
                public void onOk() {
                    DeviceListFragment.this.permDialog.dismiss();
                    PermissionsUtils.handleLOCATION(DeviceListFragment.this._mActivity, new PermissinCallBack() {
                        public void callBackOk() {
                            if (DeviceListFragment.this._mActivity instanceof DeviceListActivity) {
                                DeviceListFragment.this._mActivity.finish();
                            }
                            if (DeviceListFragment.this.isLocationEnabled()) {
                                Intent intent = new Intent(DeviceListFragment.this._mActivity, SearchActivity.class);
                                intent.putExtra("classId", ((DeviceType) DeviceListFragment.this.list.get(position)).getClassid());
                                DeviceListFragment.this.startActivity(intent);
                                return;
                            }
                            DeviceListFragment.this.openGPS();
                        }

                        public void callBackFial() {
                        }
                    });
                }

                public void onCancel() {
                    DeviceListFragment.this.permDialog.dismiss();
                }
            });
        }
        this.permDialog.show();
        this.permDialog.setTitleMsg(getString(R.string.device_module_location_permission_title));
        this.permDialog.setContentMsg(getString(R.string.device_module_location_permission_desc));
    }

    /* access modifiers changed from: private */
    public void showTipDialog(final int classId) {
        try {
            if (this.tipDialog == null) {
                this.tipDialog = new TipDialog(this._mActivity, false);
                this.tipDialog.setClickCallBack(new ClickCallBack() {
                    public void onOk() {
                        try {
                            if (classId == 3) {
                                if (DeviceListFragment.this._mActivity instanceof DeviceListActivity) {
                                    DeviceListFragment.this._mActivity.finish();
                                }
                                EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Top_Switch_To_Scale));
                            } else if (classId == 1 || classId == 2) {
                                if (DeviceListFragment.this._mActivity instanceof DeviceListActivity) {
                                    DeviceListFragment.this._mActivity.finish();
                                }
                                if (DeviceListFragment.this.tipDialog.isShowing()) {
                                    DeviceListFragment.this.tipDialog.dismiss();
                                }
                                EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Setting));
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }

                    public void onCancel() {
                        DeviceListFragment.this.tipDialog.dismiss();
                    }
                });
            }
            this.tipDialog.show();
            this.tipDialog.setTitleMsg(getString(R.string.device_module_device_setting_tip_dialog_title));
            this.tipDialog.setContentMsg(getString(R.string.device_module_device_setting_tip_dialog_message));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (this.permDialog != null) {
                this.permDialog.dismiss();
            }
            if (this.tipDialog != null) {
                this.tipDialog.dismiss();
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public boolean isLocationEnabled() {
        if (VERSION.SDK_INT >= 19) {
            try {
                if (Secure.getInt(this.activity.getContentResolver(), "location_mode") != 0) {
                    return true;
                }
                return false;
            } catch (SettingNotFoundException e) {
                ThrowableExtension.printStackTrace(e);
                return false;
            }
        } else if (TextUtils.isEmpty(Secure.getString(this.activity.getContentResolver(), "location_providers_allowed"))) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void openGPS() {
        if (this.permDialog == null) {
            this.permDialog = new TipDialog(this._mActivity, false);
            this.permDialog.setClickCallBack(new ClickCallBack() {
                public void onOk() {
                    DeviceListFragment.this.permDialog.dismiss();
                    DeviceListFragment.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 887);
                }

                public void onCancel() {
                    DeviceListFragment.this.permDialog.dismiss();
                }
            });
        }
        this.permDialog.show();
        this.permDialog.setTitleMsg(getString(R.string.device_module_location_permission_title_switch));
        this.permDialog.setContentMsg(getString(R.string.device_module_location_permission_desc_switch));
    }
}
