package com.iwown.device_module.device_message_push.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.sql.TB_PUSH_SOFT;
import com.iwown.device_module.common.sql.TB_PushSoft;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_message_push.adapter.AppPushInfoAdapter;
import com.iwown.device_module.device_message_push.adapter.AppPushInfoAdapter.CheckChangeListener;
import com.iwown.device_module.device_message_push.bean.AppInfo;
import com.iwown.device_module.device_message_push.biz.V3_sport_pushsoft_biz;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.HashMap;
import org.litepal.crud.DataSupport;

public class PackageAllPushActivity extends DeviceModuleBaseActivity implements CheckChangeListener {
    private ListView apkList;
    ArrayList<AppInfo> appList = new ArrayList<>();
    private ArrayList<AppInfo> appListSort1 = new ArrayList<>();
    private ArrayList<AppInfo> appListSort2 = new ArrayList<>();
    private ArrayList<TB_PUSH_SOFT> cache1 = new ArrayList<>();
    private ArrayList<String> cache2 = new ArrayList<>();
    private HashMap<Integer, Boolean> isSelected = new HashMap<>();
    private AppPushInfoAdapter mAdapter;
    private Context mContext;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public int mType;
    private String[] packageNames;
    private String[] packageNamesTwo;
    private ProgressDialog progressDialog;
    private V3_sport_pushsoft_biz v3_sport_pushsoft_biz;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_packagename);
        this.mType = getIntent().getIntExtra("type", 1);
        this.mContext = this;
        initData();
        initView();
        this.v3_sport_pushsoft_biz = new V3_sport_pushsoft_biz();
    }

    private void initData() {
        this.apkList = (ListView) findViewById(R.id.package_name_list);
        this.mAdapter = new AppPushInfoAdapter(this.mContext, this.appList, this.isSelected, this.mType);
        this.apkList.setAdapter(this.mAdapter);
        this.mAdapter.setListener(this);
        prepareData();
    }

    private void prepareData() {
        addApp();
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(R.string.device_module_activity_msg_push);
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                if (PackageAllPushActivity.this.mType == AppPushInfoAdapter.FOR_OTHER_APP) {
                    PackageAllPushActivity.this.savePushData();
                }
                PackageAllPushActivity.this.setResult(-1);
                PackageAllPushActivity.this.finish();
            }
        });
        if (this.mType == AppPushInfoAdapter.FOR_SMS_APP) {
            this.apkList.setChoiceMode(1);
        } else {
            this.apkList.setChoiceMode(2);
        }
    }

    /* access modifiers changed from: private */
    public void savePushData() {
        KLog.e("applist_size", Integer.valueOf(this.appList.size()));
        for (int i = 0; i < this.appList.size(); i++) {
            if (((AppInfo) this.appList.get(i)).isCheck()) {
                KLog.d("licl", "选中推送软件" + ((AppInfo) this.appList.get(i)).getPackageName());
                if (DataSupport.where("packageName=?", ((AppInfo) this.appList.get(i)).getPackageName()).find(TB_PushSoft.class).size() <= 0) {
                    new TB_PushSoft(ContextUtil.getUID() + "", ((AppInfo) this.appList.get(i)).getPackageName(), ((AppInfo) this.appList.get(i)).getAppName()).save();
                }
            } else {
                DataSupport.deleteAll(TB_PushSoft.class, "packageName=?", ((AppInfo) this.appList.get(i)).getPackageName());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void addApp() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            Log.d("pin", "resolveInfo.activityInfo.packageName->" + resolveInfo.activityInfo.packageName);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.appName = resolveInfo.activityInfo.loadLabel(this.mContext.getPackageManager()).toString();
            tmpInfo.packageName = resolveInfo.activityInfo.packageName;
            tmpInfo.setResolveInfo(resolveInfo);
            tmpInfo.setCheck(false);
            this.appList.add(tmpInfo);
        }
        for (int j = 0; j < this.appList.size(); j++) {
            if (DataSupport.where("packageName=?", ((AppInfo) this.appList.get(j)).getPackageName()).find(TB_PushSoft.class).size() <= 0) {
                this.isSelected.put(Integer.valueOf(j), Boolean.valueOf(false));
                this.appListSort2.add(this.appList.get(j));
            } else {
                this.isSelected.put(Integer.valueOf(j), Boolean.valueOf(true));
                this.appListSort1.add(this.appList.get(j));
            }
        }
        this.appList.clear();
        this.appList.addAll(this.appListSort1);
        this.appList.addAll(this.appListSort2);
        for (int i = 0; i < this.appList.size(); i++) {
            if (i < this.appListSort1.size()) {
                ((AppInfo) this.appList.get(i)).setCheck(true);
                this.isSelected.put(Integer.valueOf(i), Boolean.valueOf(true));
            } else {
                ((AppInfo) this.appList.get(i)).setCheck(false);
                this.isSelected.put(Integer.valueOf(i), Boolean.valueOf(false));
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void oncheckChange(int position, boolean checked) {
        KLog.i("PackageAllpushActivity", ((AppInfo) this.appList.get(position)).getPackageName() + ((AppInfo) this.appList.get(position)).getAppName() + "position:" + position + "==" + checked);
        ((AppInfo) this.appList.get(position)).setCheck(checked);
        if (this.mType == AppPushInfoAdapter.FOR_SMS_APP) {
            for (int i = 0; i < this.appList.size(); i++) {
                if (i != position) {
                    ((AppInfo) this.appList.get(i)).setCheck(false);
                }
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }
}
