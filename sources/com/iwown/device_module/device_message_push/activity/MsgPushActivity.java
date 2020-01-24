package com.iwown.device_module.device_message_push.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.PushAppPackName;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.adapter.CommonAdapter;
import com.iwown.device_module.common.adapter.ViewHolder;
import com.iwown.device_module.common.sql.TB_PushSoft;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.SwitchItme;
import com.iwown.device_module.common.view.SwitchItme.OnSwitchChangedListener;
import com.iwown.device_module.common.view.TimeIntervalView;
import com.iwown.device_module.common.view.iosStyle.SwipeMenu;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuCreator;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuItem;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView.OnMenuItemClickListener;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView.OpenOrCloseListener;
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_message_push.bean.AppInfo;
import com.iwown.device_module.device_message_push.bean.MessagePushSwitchStatue;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.fragment.SettingPresenter;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class MsgPushActivity extends DeviceModuleBaseActivity implements View {
    private static final int OTHER_APP_REQUEST = 234;
    /* access modifiers changed from: private */
    public static int TYPE_OTHER = 1;
    /* access modifiers changed from: private */
    public CommonAdapter<AppInfo> addAppAdapter = null;
    private boolean isEnable = true;
    /* access modifiers changed from: private */
    public List<AppInfo> mAddedApps = new ArrayList();
    private List<AppInfo> mAppInfos = new ArrayList();
    private RelativeLayout mAppRl;
    private SwitchItme mCallIdNotify;
    private View mFoot;
    private View mHeader;
    SwipeMenuListView mListViewSwipeMenu;
    private LinearLayout mMainAppList;
    private List<TB_PushSoft> mPUSHSofts;
    private SwitchItme mPush;
    private String[] mTimeArr;
    private String[] mTimeArr2;
    private List<String> mainAppPackNames = new ArrayList();
    /* access modifiers changed from: private */
    public MsgPushPresenter presenter;
    /* access modifiers changed from: private */
    public SettingPresenter settingPresenter;
    /* access modifiers changed from: private */
    public SwitchItme smsNotify;
    /* access modifiers changed from: private */
    public TimeIntervalView time_interval_picker_for_call;
    /* access modifiers changed from: private */
    public TimeIntervalView time_interval_picker_for_notify;
    /* access modifiers changed from: private */
    public ItemView time_text_call;
    /* access modifiers changed from: private */
    public ItemView time_text_notify;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_msg_push);
        this.mTimeArr = getResources().getStringArray(R.array.device_module_time);
        this.mTimeArr2 = getResources().getStringArray(R.array.device_module_time_1_24);
        this.isEnable = NotificationAccessUtil.isEnabled(this);
        initData();
        initView();
        initEvent();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        boolean isEn = NotificationAccessUtil.isEnabled(this);
        if (!isEn) {
            showConfirmDialog();
        } else if (!this.isEnable) {
            this.mPush.setOn(true);
            this.smsNotify.setOn(true);
            DeviceSettingLocal localSettings = this.settingPresenter.localDeviceSetting();
            localSettings.setMsgEnable(true);
            this.settingPresenter.saveLocalDeviceSetting(localSettings);
            DeviceUtils.writeCommandToDevice(30);
            openAppStatue();
        }
        this.isEnable = isEn;
    }

    /* access modifiers changed from: private */
    public void openAppStatue() {
        try {
            if (this.mMainAppList != null && this.mMainAppList.getChildCount() != 0) {
                for (int i = 0; i < this.mMainAppList.getChildCount(); i++) {
                    ((SwitchItme) this.mMainAppList.getChildAt(i).findViewById(R.id.swtich_item)).setSwitchBtnCanChanged(true);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void closeAppStatue() {
        try {
            if (!(this.mMainAppList == null || this.mMainAppList.getChildCount() == 0)) {
                for (int i = 0; i < this.mMainAppList.getChildCount(); i++) {
                    SwitchItme switch_item = (SwitchItme) this.mMainAppList.getChildAt(i).findViewById(R.id.swtich_item);
                    switch_item.setOn(false);
                    switch_item.setSwitchBtnCanChanged(false);
                }
            }
            if (this.mainAppPackNames != null) {
                for (String packName : this.mainAppPackNames) {
                    this.presenter.switchMainAppState(packName, false);
                    saveOrDelete(false, packName, "");
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void setSwitchStatue(List<TB_PushSoft> tb_pushSofts) {
        try {
            boolean aBoolean = PrefUtil.getBoolean((Context) this, PushAppPackName.Push_Default, false);
            KLog.e("no2855--> 开始开关了--> " + aBoolean);
            if (!aBoolean && tb_pushSofts.size() == 0) {
                if (!(this.mMainAppList == null || this.mMainAppList.getChildCount() == 0)) {
                    for (int i = 0; i < this.mMainAppList.getChildCount(); i++) {
                        ((SwitchItme) this.mMainAppList.getChildAt(i).findViewById(R.id.swtich_item)).setOn(true);
                    }
                    for (AppInfo info : this.mAppInfos) {
                        this.presenter.switchMainAppState(info.getPackageName(), true);
                        saveOrDelete(true, info.getPackageName(), info.getAppName());
                    }
                }
                PrefUtil.save((Context) this, PushAppPackName.Push_Default, true);
            } else if (tb_pushSofts.size() != 0) {
                List<Integer> indexs = new ArrayList<>();
                for (TB_PushSoft soft : tb_pushSofts) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= this.mAppInfos.size()) {
                            break;
                        } else if (soft.getPackageName().equals(((AppInfo) this.mAppInfos.get(i2)).getPackageName())) {
                            indexs.add(Integer.valueOf(i2));
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                if (this.mMainAppList != null && this.mMainAppList.getChildCount() != 0 && this.mMainAppList.getChildCount() >= this.mAppInfos.size()) {
                    for (int i3 = 0; i3 < indexs.size(); i3++) {
                        ((SwitchItme) this.mMainAppList.getChildAt(((Integer) indexs.get(i3)).intValue()).findViewById(R.id.swtich_item)).setOn(true);
                    }
                    for (int i4 = 0; i4 < this.mMainAppList.getChildCount(); i4++) {
                        if (!indexs.contains(Integer.valueOf(i4))) {
                            ((SwitchItme) this.mMainAppList.getChildAt(i4).findViewById(R.id.swtich_item)).setOn(false);
                        }
                    }
                }
            } else if (this.mMainAppList != null && this.mMainAppList.getChildCount() != 0) {
                for (int i5 = 0; i5 < this.mMainAppList.getChildCount(); i5++) {
                    ((SwitchItme) this.mMainAppList.getChildAt(i5).findViewById(R.id.swtich_item)).setOn(false);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void showConfirmDialog() {
        new Builder(this).setMessage(getString(R.string.device_module_notification_push_open)).setTitle(getString(R.string.device_module_notification_push_title)).setIconAttribute(16843605).setCancelable(true).setPositiveButton(17039370, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MsgPushActivity.this.openNotificationAccess();
            }
        }).setNegativeButton(17039360, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MsgPushActivity.this.finish();
            }
        }).create().show();
    }

    /* access modifiers changed from: private */
    public void openNotificationAccess() {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    private void initEvent() {
        this.mListViewSwipeMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                AppInfo item = (AppInfo) MsgPushActivity.this.mAddedApps.get(position);
                switch (index) {
                    case 0:
                        DataSupport.deleteAll(TB_PushSoft.class, " packageName=? ", item.getPackageName());
                        MsgPushActivity.this.mAddedApps.remove(item);
                        MsgPushActivity.this.addAppAdapter.notifyDataSetChanged();
                        return;
                    default:
                        return;
                }
            }
        });
        this.mListViewSwipeMenu.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                return false;
            }
        });
        this.mListViewSwipeMenu.setOnOpenOrCloseListener(new OpenOrCloseListener() {
            public void isOpen(boolean isOpen) {
            }
        });
        this.mCallIdNotify.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                DeviceSettingLocal localSetting = MsgPushActivity.this.settingPresenter.localDeviceSetting();
                localSetting.setCallEnable(isOn);
                MsgPushActivity.this.settingPresenter.saveLocalDeviceSetting(localSetting);
                DeviceUtils.writeCommandToDevice(25);
                MsgPushActivity.this.time_text_call.setEnabled(isOn);
            }
        });
        this.smsNotify.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                MessagePushSwitchStatue status = MsgPushActivity.this.presenter.messageSwitchStatue();
                status.setSms(isOn);
                MsgPushActivity.this.presenter.saveMessageSwitchStatue(status);
            }
        });
        this.mAppRl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!NotificationAccessUtil.isEnabled(MsgPushActivity.this)) {
                    MsgPushActivity.this.showConfirmDialog();
                    return;
                }
                Intent intent = new Intent(MsgPushActivity.this, PackageAllPushActivity.class);
                intent.putExtra("type", MsgPushActivity.TYPE_OTHER);
                MsgPushActivity.this.startActivityForResult(intent, MsgPushActivity.OTHER_APP_REQUEST);
            }
        });
    }

    private void initData() {
        this.presenter = new MsgPushPresenter(this);
        this.settingPresenter = new SettingPresenter();
        this.mainAppPackNames = this.presenter.mainAppPackNames();
        this.mAppInfos = this.presenter.getMainAppList(this.mainAppPackNames);
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(R.string.device_module_activity_msg_push);
        this.mListViewSwipeMenu = (SwipeMenuListView) findViewById(R.id.listView_swipeMenu);
        this.mHeader = LayoutInflater.from(this).inflate(R.layout.device_module_msg_activity_head_layout, null);
        this.mCallIdNotify = (SwitchItme) this.mHeader.findViewById(R.id.call_id_notify);
        this.mCallIdNotify.setDivideLineVisible(false);
        this.smsNotify = (SwitchItme) this.mHeader.findViewById(R.id.msg_notify);
        this.smsNotify.setDivideLineVisible(false);
        this.mMainAppList = (LinearLayout) this.mHeader.findViewById(R.id.main_app_list);
        this.time_text_call = (ItemView) this.mHeader.findViewById(R.id.time_text_call);
        this.time_interval_picker_for_call = (TimeIntervalView) this.mHeader.findViewById(R.id.time_interval_picker_for_call);
        this.time_text_notify = (ItemView) this.mHeader.findViewById(R.id.time_text_notify);
        this.time_interval_picker_for_notify = (TimeIntervalView) this.mHeader.findViewById(R.id.time_interval_picker_for_notify);
        this.mPush = (SwitchItme) this.mHeader.findViewById(R.id.msg_push);
        this.mPush.setDivideLineVisible(false);
        this.mPush.setOn(this.settingPresenter.localDeviceSetting().isMsgEnable());
        this.mPush.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                DeviceSettingLocal localSettings = MsgPushActivity.this.settingPresenter.localDeviceSetting();
                localSettings.setMsgEnable(isOn);
                MsgPushActivity.this.settingPresenter.saveLocalDeviceSetting(localSettings);
                if (isOn) {
                    MsgPushActivity.this.openAppStatue();
                    MsgPushActivity.this.time_text_notify.setEnabled(true);
                    MsgPushActivity.this.smsNotify.setSwitchBtnCanChanged(true);
                } else {
                    MsgPushActivity.this.closeAppStatue();
                    MsgPushActivity.this.smsNotify.setOn(false);
                    MsgPushActivity.this.time_text_notify.setEnabled(false);
                    MsgPushActivity.this.smsNotify.setSwitchBtnCanChanged(false);
                    MessagePushSwitchStatue status = MsgPushActivity.this.presenter.messageSwitchStatue();
                    status.setSms(false);
                    MsgPushActivity.this.presenter.saveMessageSwitchStatue(status);
                }
                DeviceUtils.writeCommandToDevice(30);
            }
        });
        if (BluetoothOperation.isZg()) {
            this.time_text_call.setVisibility(0);
            this.time_text_notify.setVisibility(0);
            this.mCallIdNotify.setDivideLineVisible(true);
            this.mPush.setDivideLineVisible(true);
            this.smsNotify.setDivideLineVisible(true);
        } else {
            this.time_text_call.setVisibility(8);
            this.time_text_notify.setVisibility(8);
            this.mCallIdNotify.setDivideLineVisible(false);
            this.mPush.setDivideLineVisible(false);
            this.smsNotify.setDivideLineVisible(false);
        }
        this.mPush.setVisibility(0);
        try {
            DeviceSettingLocal localSettings = this.settingPresenter.localDeviceSetting();
            int start = localSettings.getCallStart();
            int end = localSettings.getCallEnd();
            this.time_text_call.setMessageText(this.mTimeArr[start] + HelpFormatter.DEFAULT_OPT_PREFIX + this.mTimeArr2[end]);
            this.time_interval_picker_for_call.setStartCurrPosition(start);
            this.time_interval_picker_for_call.setEndCurrPosition(end);
            int msgStart = localSettings.getMsgStart();
            int msgEnd = localSettings.getMsgEnd();
            this.time_text_notify.setMessageText(this.mTimeArr[msgStart] + HelpFormatter.DEFAULT_OPT_PREFIX + this.mTimeArr2[msgEnd]);
            this.time_interval_picker_for_notify.setStartCurrPosition(msgStart);
            this.time_interval_picker_for_notify.setEndCurrPosition(msgEnd);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        this.time_text_call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MsgPushActivity.this.time_interval_picker_for_call.getVisibility() == 0) {
                    int startTimeCurrPosition = MsgPushActivity.this.time_interval_picker_for_call.getStartTimeCurrPosition();
                    int endTimeCurrentPosition = MsgPushActivity.this.time_interval_picker_for_call.getEndTimeCurrentPosition();
                    if (endTimeCurrentPosition == 0 || startTimeCurrPosition < endTimeCurrentPosition) {
                        MsgPushActivity.this.showTextMessageTimeStr(MsgPushActivity.this.time_text_call, startTimeCurrPosition, endTimeCurrentPosition);
                        MsgPushActivity.this.time_interval_picker_for_call.setVisibility(8);
                        DeviceSettingLocal localSettings = MsgPushActivity.this.settingPresenter.localDeviceSetting();
                        localSettings.setCallStart(startTimeCurrPosition);
                        localSettings.setCallEnd(endTimeCurrentPosition);
                        MsgPushActivity.this.settingPresenter.saveLocalDeviceSetting(localSettings);
                        DeviceUtils.writeCommandToDevice(25);
                        return;
                    }
                    return;
                }
                MsgPushActivity.this.time_interval_picker_for_call.setVisibility(0);
            }
        });
        this.time_text_notify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MsgPushActivity.this.time_interval_picker_for_notify.getVisibility() == 0) {
                    int startTimeCurrPosition = MsgPushActivity.this.time_interval_picker_for_notify.getStartTimeCurrPosition();
                    int endTimeCurrentPosition = MsgPushActivity.this.time_interval_picker_for_notify.getEndTimeCurrentPosition();
                    if (endTimeCurrentPosition == 0 || startTimeCurrPosition < endTimeCurrentPosition) {
                        DeviceSettingLocal localSettings = MsgPushActivity.this.settingPresenter.localDeviceSetting();
                        localSettings.setMsgStart(startTimeCurrPosition);
                        localSettings.setMsgEnd(endTimeCurrentPosition);
                        MsgPushActivity.this.settingPresenter.saveLocalDeviceSetting(localSettings);
                        MsgPushActivity.this.showTextMessageTimeStr(MsgPushActivity.this.time_text_notify, startTimeCurrPosition, endTimeCurrentPosition);
                        MsgPushActivity.this.time_interval_picker_for_notify.setVisibility(8);
                        DeviceUtils.writeCommandToDevice(30);
                        return;
                    }
                    return;
                }
                MsgPushActivity.this.time_interval_picker_for_notify.setVisibility(0);
            }
        });
        this.mListViewSwipeMenu.addHeaderView(this.mHeader, null, false);
        this.mFoot = LayoutInflater.from(this).inflate(R.layout.device_module_msg_activity_foot_layout, null);
        this.mAppRl = (RelativeLayout) this.mFoot.findViewById(R.id.add_app_rl);
        this.mListViewSwipeMenu.addFooterView(this.mAppRl, null, false);
        this.mListViewSwipeMenu.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MsgPushActivity.this.getApplicationContext());
                deleteItem.setBackground((Drawable) new ColorDrawable(Color.rgb(240, 65, 66)));
                deleteItem.setWidth(Utils.dip2px(MsgPushActivity.this, 90.0f));
                deleteItem.setTitle(MsgPushActivity.this.getString(R.string.device_module_delete));
                deleteItem.setTitleSize(17);
                deleteItem.setTitleColor(-1);
                menu.addMenuItem(deleteItem);
            }
        });
        this.addAppAdapter = new CommonAdapter<AppInfo>(this, this.mAddedApps, R.layout.device_module_push_main_app_item_layout) {
            public void convert(ViewHolder helper, int position, AppInfo item) {
                SwitchItme switchItme = (SwitchItme) helper.getConvertView().findViewById(R.id.swtich_item).findViewById(R.id.swtich_item);
                AppInfo appInfo = (AppInfo) MsgPushActivity.this.mAddedApps.get(position);
                switchItme.setTitle(appInfo.getAppName());
                switchItme.setIconImagVisible(true);
                switchItme.setIconDrawble(appInfo.getAppIcon());
                switchItme.setSwitchVisible(false);
                switchItme.setSwitchBtnCanChanged(false);
            }
        };
        this.mListViewSwipeMenu.setAdapter((ListAdapter) this.addAppAdapter);
        this.addAppAdapter.notifyDataSetChanged();
        this.mPUSHSofts = DataSupport.findAll(TB_PushSoft.class, new long[0]);
        for (final AppInfo appInfo : this.mAppInfos) {
            View view = LayoutInflater.from(this).inflate(R.layout.device_module_push_main_app_item_layout, null);
            SwitchItme switchItme = (SwitchItme) view.findViewById(R.id.swtich_item);
            switchItme.setDivideLineVisible(true);
            switchItme.setTitle(appInfo.getAppName());
            switchItme.setIconImagVisible(true);
            switchItme.setIconDrawble(appInfo.getAppIcon());
            if (NotificationAccessUtil.isEnabled(this)) {
                KLog.d("no2855--> is check" + appInfo.isCheck());
                switchItme.setOn(appInfo.isCheck());
                if (!this.settingPresenter.localDeviceSetting().isMsgEnable()) {
                    closeAppStatue();
                }
            } else {
                switchItme.setOn(false);
                switchItme.setSwitchBtnCanChanged(false);
                this.presenter.switchMainAppState(appInfo.getPackageName(), true);
                saveOrDelete(true, appInfo.getPackageName(), appInfo.getAppName());
            }
            switchItme.setOnSwitchChangedListener(new OnSwitchChangedListener() {
                public void onSwitchChanged(boolean isOn) {
                    KLog.e("licl", "no28555isenable/ison " + NotificationAccessUtil.isEnabled(MsgPushActivity.this) + "/" + isOn + " == " + appInfo.getPackageName());
                    PrefUtil.save((Context) MsgPushActivity.this, PushAppPackName.Push_Default, true);
                    if (!NotificationAccessUtil.isEnabled(MsgPushActivity.this)) {
                        MsgPushActivity.this.presenter.switchMainAppState(appInfo.getPackageName(), false);
                        MsgPushActivity.this.saveOrDelete(false, appInfo.getPackageName(), appInfo.getAppName());
                        MsgPushActivity.this.showConfirmDialog();
                        return;
                    }
                    MsgPushActivity.this.presenter.switchMainAppState(appInfo.getPackageName(), isOn);
                    MsgPushActivity.this.saveOrDelete(isOn, appInfo.getPackageName(), appInfo.getAppName());
                }
            });
            this.mMainAppList.addView(view);
        }
        this.mCallIdNotify.setOn(this.settingPresenter.localDeviceSetting().isCallEnable());
        if (this.settingPresenter.localDeviceSetting().isMsgEnable()) {
            this.smsNotify.setSwitchBtnCanChanged(true);
        } else {
            closeAppStatue();
            this.smsNotify.setSwitchBtnCanChanged(false);
        }
        this.smsNotify.setOn(this.presenter.messageSwitchStatue().isSms());
        if (BluetoothOperation.isZg()) {
            if (!this.mCallIdNotify.isOn()) {
                this.time_text_call.setEnabled(false);
            } else {
                this.time_text_call.setEnabled(true);
            }
            if (!this.mPush.isOn()) {
                this.time_text_notify.setEnabled(false);
            } else {
                this.time_text_notify.setEnabled(true);
            }
        }
        refreshSwipMenuListUi();
    }

    /* access modifiers changed from: private */
    public void showTextMessageTimeStr(ItemView time_text_notify2, int startTimeCurrPosition, int endTimeCurrentPosition) {
        if (endTimeCurrentPosition == 24) {
            endTimeCurrentPosition = 0;
        }
        time_text_notify2.setMessageText(this.mTimeArr[startTimeCurrPosition] + HelpFormatter.DEFAULT_OPT_PREFIX + this.mTimeArr2[endTimeCurrentPosition]);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case OTHER_APP_REQUEST /*234*/:
                    refreshSwipMenuListUi();
                    return;
                default:
                    return;
            }
        }
    }

    private void refreshSwipMenuListUi() {
        if (this.mAddedApps != null) {
            this.mAddedApps.clear();
        }
        if (this.mPUSHSofts != null) {
            this.mPUSHSofts.clear();
        }
        this.mPUSHSofts = DataSupport.findAll(TB_PushSoft.class, new long[0]);
        if (this.mPUSHSofts != null) {
            setSwitchStatue(this.mPUSHSofts);
        }
        Iterator<TB_PushSoft> it = this.mPUSHSofts.iterator();
        while (it.hasNext()) {
            TB_PushSoft soft = (TB_PushSoft) it.next();
            if (TextUtils.isEmpty(soft.getPackageName()) || !notMainPushApp(soft.getPackageName())) {
                it.remove();
            }
        }
        if (!(this.mPUSHSofts == null || this.mPUSHSofts.size() == 0)) {
            for (int i = 0; i < this.mPUSHSofts.size(); i++) {
                AppInfo app = new AppInfo();
                try {
                    app.setAppIcon(getPackageManager().getApplicationIcon(((TB_PushSoft) this.mPUSHSofts.get(i)).getPackageName()));
                } catch (NameNotFoundException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                app.setAppName(((TB_PushSoft) this.mPUSHSofts.get(i)).getAppName());
                app.setPackageName(((TB_PushSoft) this.mPUSHSofts.get(i)).getPackageName());
                this.mAddedApps.add(app);
            }
        }
        this.addAppAdapter.notifyDataSetChanged();
    }

    private boolean notMainPushApp(String name) {
        if (name.equalsIgnoreCase("com.facebook.orca") || name.equalsIgnoreCase("com.twitter.android") || name.equalsIgnoreCase("com.whatsapp") || name.equalsIgnoreCase("com.skype.rover") || name.equalsIgnoreCase("com.skype.raider") || name.equalsIgnoreCase("jp.naver.line.android") || name.equalsIgnoreCase("com.kakao.talk") || name.equalsIgnoreCase("com.google.android.gm")) {
            return false;
        }
        if (!AppConfigUtil.isHealthy() || (!name.equalsIgnoreCase("com.tencent.mobileqq") && !name.equalsIgnoreCase("com.tencent.mm") && !name.equalsIgnoreCase(PushAppPackName.SINA))) {
            return true;
        }
        return false;
    }

    public void setPresenter(Presenter presenter2) {
    }

    /* access modifiers changed from: private */
    public void saveOrDelete(boolean isSave, String packageName, String appName) {
        if (isSave) {
            new TB_PushSoft(ContextUtil.getUID() + "", packageName, appName).saveOrUpdate("uid=? and packageName=? ", ContextUtil.getUID() + "", packageName);
            return;
        }
        DataSupport.deleteAll(TB_PushSoft.class, "uid=? and packageName=? ", ContextUtil.getUID() + "", packageName);
    }
}
