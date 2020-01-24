package com.iwown.my_module.googlefit;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.eventbus.GoogleFitEvent;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import org.greenrobot.eventbus.EventBus;

public class GoogleFitConnectionActivity extends BaseActivity {
    private static final String TAG = "googlefit";
    Button mBtnAuthorize;
    Button mBtnUnAuthorize;
    TextView mDesc1Tv;
    TextView mDesc2Tv;
    GoogleApiClient mGoogleClient = null;
    LinearLayout mStatusArea;
    ImageView mTargetPlatformImv;
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
        this.mTargetPlatformImv = (ImageView) findViewById(R.id.target_platform_img);
        this.mDesc1Tv = (TextView) findViewById(R.id.connected_desc_1);
        this.mDesc2Tv = (TextView) findViewById(R.id.connected_desc_2);
        this.mUIHandler = new Handler();
        setLeftBackTo();
        setTitleText(getString(R.string.my_module_googlefit));
        String str = "";
        if (AppConfigUtil.isIwownFitPro()) {
            String appName = "iWOWNFit Pro";
        } else if (AppConfigUtil.isZeronerHealthPro()) {
            String appName2 = "Zeroner Health Pro";
        } else if (AppConfigUtil.isNanfei_TRAX_GPS()) {
            String appName3 = "TRAX GPS";
        }
        this.mTextViewDesc.setText(String.format(getResources().getString(R.string.my_module_strava_desc), new Object[]{"Googlefit", AppConfigUtil.app_name, "Googlefit"}));
        this.mTargetPlatformImv.setImageResource(R.mipmap.googlefit_connection_x3);
        initView();
        initEvent();
    }

    private void initView() {
        if (GlobalUserDataFetcher.getGoogleFitConnectStatus(this) == 1) {
            refreshView(true);
        } else {
            refreshView(false);
        }
    }

    private void refreshView(boolean connected) {
        if (connected) {
            this.mBtnAuthorize.setVisibility(8);
            this.mTextViewDesc.setVisibility(8);
            this.mBtnUnAuthorize.setVisibility(0);
            this.mTextViewNotice1.setVisibility(0);
            this.mTextViewNotice2.setVisibility(0);
            this.mStatusArea.setVisibility(0);
            this.mDesc1Tv.setText(String.format(getResources().getString(R.string.my_module_strava_notice1), new Object[]{"Googlefit"}));
            return;
        }
        this.mBtnUnAuthorize.setVisibility(8);
        this.mTextViewNotice1.setVisibility(8);
        this.mTextViewNotice2.setVisibility(8);
        this.mStatusArea.setVisibility(8);
        this.mBtnAuthorize.setVisibility(0);
        this.mTextViewDesc.setVisibility(0);
    }

    private void initEvent() {
        this.mBtnAuthorize.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GoogleFitConnectionActivity.this.connect();
            }
        });
        this.mBtnUnAuthorize.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GoogleFitConnectionActivity.this.disconnect();
            }
        });
    }

    /* access modifiers changed from: private */
    public void connect() {
        Log.i(TAG, "connect");
        EventBus.getDefault().post(new GoogleFitEvent());
        GlobalDataUpdater.setGoogleFitConnectStatus(this, 1);
        refreshView(true);
    }

    /* access modifiers changed from: private */
    public void disconnect() {
        GlobalDataUpdater.setGoogleFitConnectStatus(this, 0);
        refreshView(false);
    }
}
