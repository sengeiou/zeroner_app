package com.iwown.my_module.settingactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.enumtype.EnumTemperature;
import com.iwown.data_link.eventbus.LogOutEventBus;
import com.iwown.data_link.googlefit.GoogleFitUtility;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.healthy.BuildConfig;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.my_module.MyInitUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.dialog.LogUploadConfirmDialog;
import com.iwown.my_module.healthy.HealthyLoginActivity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.HealthyUtil;
import com.iwown.my_module.useractivity.ChangePwdActivity;
import com.iwown.my_module.useractivity.LoginActivity;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.widget.SelectinfoView;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.litepal.util.Const.Config;

public class AppSettingActivity extends BaseActivity implements OnClickListener {
    public static String SDPATH = (Environment.getExternalStorageDirectory() + "/");
    SelectinfoView aboutApp;
    SelectinfoView appVersion;
    SelectinfoView backPermissionSettingBtn;
    SelectinfoView changePasswordPro;
    SelectinfoView dfuHelper;
    private boolean isHealthy = false;
    Button mBtnSignOut;
    Context mContext;
    LogUploadConfirmDialog mNoticeDialog;
    SelectinfoView temperatureSwitch;
    SelectinfoView unitMeasurementSwitch;
    SelectinfoView uploadBleBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_app_setting);
        this.changePasswordPro = (SelectinfoView) findViewById(R.id.change_password_pro);
        this.changePasswordPro.setOnClickListener(this);
        this.appVersion = (SelectinfoView) findViewById(R.id.app_version);
        this.appVersion.setOnClickListener(this);
        this.aboutApp = (SelectinfoView) findViewById(R.id.about_app);
        this.aboutApp.setOnClickListener(this);
        this.dfuHelper = (SelectinfoView) findViewById(R.id.dfu_helper);
        this.dfuHelper.setOnClickListener(this);
        this.unitMeasurementSwitch = (SelectinfoView) findViewById(R.id.unit_measurement);
        this.unitMeasurementSwitch.setOnClickListener(this);
        this.temperatureSwitch = (SelectinfoView) findViewById(R.id.temperature_switch);
        this.temperatureSwitch.setOnClickListener(this);
        this.mBtnSignOut = (Button) findViewById(R.id.btnSignOut);
        this.mBtnSignOut.setOnClickListener(this);
        this.uploadBleBtn = (SelectinfoView) findViewById(R.id.upload_ble);
        this.uploadBleBtn.setOnClickListener(this);
        this.backPermissionSettingBtn = (SelectinfoView) findViewById(R.id.background_permission_setting);
        this.backPermissionSettingBtn.setOnClickListener(this);
        this.temperatureSwitch.setVisibility(8);
        setLeftBackTo();
        setTitleText(getString(R.string.setting_title));
        this.mContext = this;
        this.isHealthy = AppConfigUtil.isHealthy(this);
        initView();
    }

    private void initData(int flag) {
    }

    private void initView() {
        this.appVersion.setMessageText(CommonUtility.getClientVersionName(this.mContext));
        if (this.isHealthy) {
            int loginType = new HealthySharedUtil(this).getLoginType();
            if (loginType == 3 || loginType == 4) {
                this.changePasswordPro.setVisibility(8);
            }
            this.unitMeasurementSwitch.setVisibility(8);
            this.temperatureSwitch.setVisibility(8);
        } else if (AppConfigUtil.isUpfit() || AppConfigUtil.isNewfit()) {
            this.appVersion.setVisibility(8);
            this.backPermissionSettingBtn.setVisibility(8);
        }
        if (GlobalUserDataFetcher.getPreferredMeasureUnit(this) == EnumMeasureUnit.Imperial) {
            this.unitMeasurementSwitch.setMessageText(getString(R.string.unit_imperial));
        } else {
            this.unitMeasurementSwitch.setMessageText(getString(R.string.unit_metric));
        }
        if (GlobalUserDataFetcher.getPreferredTemperature(this) == EnumTemperature.Fahrenheit) {
            this.temperatureSwitch.setMessageText(getString(R.string.i6_fahrenheit));
        } else {
            this.temperatureSwitch.setMessageText(getString(R.string.i6_centigrade));
        }
        if (AppConfigUtil.isDrviva()) {
            this.uploadBleBtn.setVisibility(8);
        }
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.change_password_pro) {
            startActivity(new Intent(this, ChangePwdActivity.class));
        } else if (i == R.id.app_version) {
            launchAppDetail(this);
        } else if (i == R.id.about_app) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (i == R.id.dfu_helper) {
            ARouter.getInstance().build(RouteUtils.Activity_Device_FirmwareUpgradeActivity).navigation();
        } else if (i == R.id.unit_measurement) {
            setUnitOfMeasurement();
        } else if (i == R.id.temperature_switch) {
            setUnitTemperature();
        } else if (i == R.id.btnSignOut) {
            GlobalDataUpdater.setLoginStatus(this, 0);
            GlobalDataUpdater.setUnitSetStatus(this, 0);
            GlobalDataUpdater.setPrevUid(this, GlobalUserDataFetcher.getCurrentUid(this).longValue());
            GoogleFitUtility.createInstance().disconnect();
            Editor editor = getSharedPreferences("bloodhistory", 0).edit();
            editor.clear();
            editor.commit();
            LogOutEventBus.userLogOut();
            if (this.isHealthy) {
                startActivity(new Intent(this, HealthyLoginActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        } else if (i == R.id.upload_ble) {
            this.mNoticeDialog = new LogUploadConfirmDialog(this);
            this.mNoticeDialog.setOnlyOneBT(true);
            this.mNoticeDialog.setClickCallBack(new ClickCallBack() {
                public void onOk() {
                    AppSettingActivity.copyDataBaseToSD();
                    ModuleRouteDeviceInfoService.getInstance().upTodayLogFile();
                    AppSettingActivity.this.mNoticeDialog.dismiss();
                }

                public void onCancel() {
                    AppSettingActivity.this.mNoticeDialog.dismiss();
                }
            });
            this.mNoticeDialog.show();
        } else if (i == R.id.background_permission_setting) {
            Intent intent = new Intent(this, CustomWebViewActivity.class);
            String url = "https://api4.iwown.com/setting/dist/index.html#/";
            String country = getResources().getConfiguration().locale.getCountry().toLowerCase();
            if (AppConfigUtil.isRussia(this)) {
                if (AppConfigUtil.isIwownFitPro()) {
                    if (country.equals("es")) {
                        url = "https://search.iwown.com/setting/es/dist/index.html#/";
                    } else {
                        url = "https://search.iwown.com/setting/dist/index.html#/";
                    }
                } else if (AppConfigUtil.isZeronerHealthPro()) {
                    url = "https://search.iwown.com/setting/dist/index.html#/zhp/app";
                }
            } else if (AppConfigUtil.isIwownFitPro()) {
                if (country.equals("es")) {
                    url = "https://api4.iwown.com/setting/es/dist/index.html#/";
                } else {
                    url = "https://api4.iwown.com/setting/dist/index.html#/";
                }
            } else if (AppConfigUtil.isZeronerHealthPro()) {
                url = "https://api4.iwown.com/setting/dist/index.html#/zhp/app";
            } else if (AppConfigUtil.isHealthy(this)) {
                url = "https://api2.iwown.com/setting/zhushou/index.html#/china/app";
            } else if (AppConfigUtil.isDrviva()) {
                url = "https://api4.iwown.com/setting/viva/index.html#/common/drviva";
            } else {
                url = "https://api4.iwown.com/setting/dist/index.html#/";
            }
            intent.putExtra("url", url);
            intent.putExtra("title", getString(R.string.my_module_background_permission));
            startActivity(intent);
        }
    }

    private void setUnitOfMeasurement() {
        if (GlobalUserDataFetcher.getPreferredMeasureUnit(this) == EnumMeasureUnit.Metric) {
            new PreferenceUtility(this).updateNumberValueWithKey(UserConst.MEASUREUNIT, EnumMeasureUnit.Imperial.ordinal());
            this.unitMeasurementSwitch.setMessageText(getString(R.string.unit_imperial));
        } else {
            new PreferenceUtility(this).updateNumberValueWithKey(UserConst.MEASUREUNIT, EnumMeasureUnit.Metric.ordinal());
            this.unitMeasurementSwitch.setMessageText(getString(R.string.unit_metric));
        }
        ModuleRouteDeviceInfoService.getInstance().updateMeasureUnit(GlobalUserDataFetcher.getPreferredMeasureUnit(this));
    }

    private void setUnitTemperature() {
        if (GlobalUserDataFetcher.getPreferredTemperature(this) == EnumTemperature.Centigrade) {
            new PreferenceUtility(this).updateNumberValueWithKey(UserConst.TEMPERATUREUNIT, EnumTemperature.Fahrenheit.ordinal());
            this.temperatureSwitch.setMessageText(getString(R.string.i6_fahrenheit));
            return;
        }
        new PreferenceUtility(this).updateNumberValueWithKey(UserConst.TEMPERATUREUNIT, EnumTemperature.Centigrade.ordinal());
        this.temperatureSwitch.setMessageText(getString(R.string.i6_centigrade));
    }

    public void launchAppDetail(Context context) {
        if (AppConfigUtil.isHealthy(this)) {
            HealthyUtil.gotoChinaMarket(context);
        } else if (!AppConfigUtil.isNanfei_TRAX_GPS() && !AppConfigUtil.isUpfit() && !AppConfigUtil.isNewfit()) {
            String packageName = BuildConfig.APPLICATION_ID;
            try {
                if (AppConfigUtil.isIwownFitPro()) {
                    packageName = "com.healthy.iwownfit_pro";
                } else if (AppConfigUtil.isDrviva()) {
                    packageName = "com.doctorviva.app";
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName));
                intent.setPackage("com.android.vending");
                intent.addFlags(268435456);
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, context.getString(R.string.no_google_play), 0).show();
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public static void copyDataBaseToSD() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try {
                File dbFile = new File(MyInitUtils.getInstance().getMyApplication().getDatabasePath(Constants.DB_Name) + Config.DB_NAME_SUFFIX);
                File file = new File(SDPATH + "Zeroner/zeroner/log/", "healthy_all_in_5.0.db");
                file.createNewFile();
                inChannel = new FileInputStream(dbFile).getChannel();
                outChannel = new FileOutputStream(file).getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                if (inChannel != null) {
                    try {
                        inChannel.close();
                    } catch (IOException e) {
                        KLog.e("file close error.");
                        ThrowableExtension.printStackTrace(e);
                        return;
                    }
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (Exception e2) {
                KLog.e("copy dataBase to SD error.");
                ThrowableExtension.printStackTrace(e2);
                if (inChannel != null) {
                    try {
                        inChannel.close();
                    } catch (IOException e3) {
                        KLog.e("file close error.");
                        ThrowableExtension.printStackTrace(e3);
                        return;
                    }
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (Throwable th) {
                if (inChannel != null) {
                    try {
                        inChannel.close();
                    } catch (IOException e4) {
                        KLog.e("file close error.");
                        ThrowableExtension.printStackTrace(e4);
                        throw th;
                    }
                }
                if (outChannel != null) {
                    outChannel.close();
                }
                throw th;
            }
        }
    }
}
