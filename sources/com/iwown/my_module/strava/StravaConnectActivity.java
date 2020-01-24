package com.iwown.my_module.strava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.consts.StravaCredential;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.eventbus.StravaTokenGetEvent;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.dialog.DeleteContactsDialog;
import com.sweetzpot.stravazpot.StravaUtil;
import com.sweetzpot.stravazpot.StravaUtil.MyStravaCallback;
import com.sweetzpot.stravazpot.authenticaton.api.AccessScope;
import com.sweetzpot.stravazpot.authenticaton.api.ApprovalPrompt;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class StravaConnectActivity extends BaseActivity {
    private static final String REDIRECT_URI = "http://hwbetaapi.iwown.com";
    private static final int RQ_LOGIN = 1001;
    private static final int STATE_GET_CODE_FAIL = 0;
    private static final int STATE_GET_CODE_SUCCESS_AND_TO_GET_TOKEN = 1;
    private static final int STATE_GET_TOKEN_FAIL = 2;
    private static final int STATE_GET_TOKEN_SUCCESS = 3;
    /* access modifiers changed from: private */
    public static int now_state = -1;
    /* access modifiers changed from: private */
    public static DeleteContactsDialog tipDialog = null;
    /* access modifiers changed from: private */
    public int get_token_fail_time = 0;
    ImageView mAppConnectImg;
    Button mBtnAuthorize;
    Button mBtnUnAuthorize;
    Context mContext;
    TextView mDesc1Tv;
    LinearLayout mStatusArea;
    TextView mTextViewDesc;
    LinearLayout mTextViewNotice1;
    LinearLayout mTextViewNotice2;
    Handler mUIHandler;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_sportplatform_connection);
        this.mBtnAuthorize = (Button) findViewById(R.id.strava_authorize);
        this.mBtnUnAuthorize = (Button) findViewById(R.id.strava_unauthorize);
        this.mTextViewDesc = (TextView) findViewById(R.id.strava_desc1);
        this.mTextViewNotice1 = (LinearLayout) findViewById(R.id.strava_notice1);
        this.mTextViewNotice2 = (LinearLayout) findViewById(R.id.strava_notice2);
        this.mStatusArea = (LinearLayout) findViewById(R.id.top_area);
        this.mAppConnectImg = (ImageView) findViewById(R.id.app_connect_img);
        this.mDesc1Tv = (TextView) findViewById(R.id.connected_desc_1);
        this.mUIHandler = new Handler();
        setLeftBackTo();
        setTitleText(getString(R.string.strava));
        String str = "";
        if (AppConfigUtil.isIwownFitPro()) {
            String appName = "iWOWNFit Pro";
        } else if (AppConfigUtil.isZeronerHealthPro()) {
            String appName2 = "Zeroner Health Pro";
        } else if (AppConfigUtil.isNanfei_TRAX_GPS()) {
            String appName3 = "TRAX GPS";
        }
        this.mTextViewDesc.setText(String.format(getResources().getString(R.string.my_module_strava_desc), new Object[]{"Strava", AppConfigUtil.getApp_name(), "Strava"}));
        this.mContext = this;
        initView();
        initData(1);
        initEvent();
        StravaUtil.getInstance(this).setStravaCallback(new MyStravaCallback() {
            public void onResult(int code) {
                if (code == 15) {
                    StravaConnectActivity.this.get_token_fail_time = StravaConnectActivity.this.get_token_fail_time + 1;
                    if (StravaConnectActivity.this.get_token_fail_time >= 2) {
                        StravaConnectActivity.now_state = 2;
                        StravaConnectActivity.tipDialog.dismiss();
                        StravaConnectActivity.tipDialog.show();
                        StravaConnectActivity.this.get_token_fail_time = 0;
                        return;
                    }
                    StravaUtil.getInstance(StravaConnectActivity.this).getToken();
                } else if (code == 14) {
                    StravaConnectActivity.now_state = 3;
                    StravaConnectActivity.tipDialog.dismiss();
                    StravaConnectActivity.tipDialog.show();
                    StravaConnectActivity.this.get_token_fail_time = 0;
                }
            }
        });
    }

    private void initEvent() {
        this.mBtnAuthorize.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StravaConnectActivity.this.login();
            }
        });
        this.mBtnUnAuthorize.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StravaConnectActivity.this.unauthorize();
            }
        });
        tipDialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialog) {
                StravaConnectActivity.this.showTipDialog(StravaConnectActivity.now_state);
            }
        });
        EventBus.getDefault().register(this);
    }

    private void initData(int flag) {
    }

    private void initView() {
        String token = GlobalUserDataFetcher.getStravaToken(this);
        if (token == null || TextUtils.isEmpty(token)) {
            refreshView(false);
        } else {
            refreshView(true);
        }
        tipDialog = new DeleteContactsDialog(this, false);
    }

    /* access modifiers changed from: private */
    public void refreshView(boolean connected) {
        if (connected) {
            this.mBtnAuthorize.setVisibility(8);
            this.mTextViewDesc.setVisibility(8);
            this.mBtnUnAuthorize.setVisibility(0);
            this.mTextViewNotice1.setVisibility(0);
            this.mTextViewNotice2.setVisibility(0);
            this.mStatusArea.setVisibility(0);
            this.mDesc1Tv.setText(String.format(getResources().getString(R.string.my_module_strava_notice1), new Object[]{"Strava"}));
            return;
        }
        this.mBtnUnAuthorize.setVisibility(8);
        this.mTextViewNotice1.setVisibility(8);
        this.mTextViewNotice2.setVisibility(8);
        this.mStatusArea.setVisibility(8);
        this.mBtnAuthorize.setVisibility(0);
        this.mTextViewDesc.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void login() {
        startActivityForResult(StravaLogin.withContext(this).withClientID(StravaCredential.getClientId()).withRedirectURI(REDIRECT_URI).withApprovalPrompt(ApprovalPrompt.AUTO).withAccessScope(AccessScope.VIEW_PRIVATE_WRITE).makeIntent(), 1001);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1001) {
            return;
        }
        if (resultCode == -1 && data != null) {
            String code = data.getStringExtra("StravaLoginActivity.RESULT_CODE");
            Log.i("Strava authorize code", code);
            GlobalDataUpdater.setStravaCode(this, code);
            now_state = 1;
            tipDialog.dismiss();
            tipDialog.show();
            StravaUtil.getInstance(this).getToken();
        } else if (resultCode != -1) {
            now_state = 0;
            tipDialog.dismiss();
            tipDialog.show();
            Log.i("Strava result code", resultCode + "");
        } else if (data == null) {
            now_state = 0;
            tipDialog.dismiss();
            tipDialog.show();
        }
    }

    /* access modifiers changed from: private */
    public void showTipDialog(int whatState) {
        tipDialog.setOk(R.string.sport_module_ok);
        tipDialog.setCancel(R.string.sport_module_cancel);
        switch (whatState) {
            case 0:
                tipDialog.setTitle(R.string.failed);
                tipDialog.setContent(R.string.please_connect_again);
                tipDialog.changeCancelBtnVisible(true);
                this.mBtnAuthorize.setClickable(true);
                return;
            case 1:
                tipDialog.setTitle(R.string.waiting);
                tipDialog.setContent(R.string.please_wait);
                this.mBtnAuthorize.setClickable(false);
                tipDialog.changeCancelBtnVisible(true);
                return;
            case 2:
                tipDialog.setTitle(R.string.failed);
                tipDialog.setContent(R.string.try_again_later);
                this.mBtnAuthorize.setClickable(true);
                tipDialog.changeCancelBtnVisible(true);
                return;
            case 3:
                tipDialog.setTitle(R.string.succeed);
                tipDialog.setContent(R.string.get_access_token_succeed);
                this.mBtnAuthorize.setClickable(true);
                tipDialog.changeCancelBtnVisible(false);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(StravaTokenGetEvent event) {
        if (event.getStatus() == 1) {
            this.mUIHandler.post(new Runnable() {
                public void run() {
                    StravaConnectActivity.this.refreshView(true);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void unauthorize() {
        GlobalDataUpdater.setStravaCode(this, "");
        GlobalDataUpdater.setStravaToken(this, "");
        refreshView(false);
    }
}
