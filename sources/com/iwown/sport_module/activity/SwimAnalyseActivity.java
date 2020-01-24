package com.iwown.sport_module.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.sport_module.R;
import com.iwown.sport_module.contract.SwimAnalyseContract.View;
import com.iwown.sport_module.device_data.view.RunSwimItem;
import com.iwown.sport_module.device_data.view.RunSwimRateItem;
import com.iwown.sport_module.pojo.SwimRateData;
import com.iwown.sport_module.pojo.data.SwimHealthyData;
import com.iwown.sport_module.presenter.SwimAnalysePresenter;
import com.iwown.sport_module.util.WindowUtil;
import com.socks.library.KLog;

public class SwimAnalyseActivity extends AppCompatActivity implements View {
    private LinearLayout addLayout;
    private String dataFrom;
    private long endTime;
    private boolean isMetric;
    private TextView loadText;
    private ImageView mBackBtn;
    private Handler mHandler;
    private ConstraintLayout mTopContainer;
    private int mType;
    private RunSwimItem runSwimItem;
    private RunSwimRateItem runSwimRateItem;
    private long startTime;
    private SwimAnalysePresenter swimAnalysePresenter;
    private int swimType;
    private long uid;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_activity_swim_analy);
        WindowUtil.setTopWindows(getWindow());
        this.mTopContainer = (ConstraintLayout) findViewById(R.id.top_container);
        this.mTopContainer.setBackground(RunActivitySkin.RunActy_Top_BG);
        this.mTopContainer.setPadding(0, WindowUtil.getStatusBarHeight(), 0, 0);
        this.mBackBtn = (ImageView) findViewById(R.id.back_btn);
        this.mBackBtn.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                SwimAnalyseActivity.this.finish();
            }
        });
        this.mHandler = new Handler(Looper.getMainLooper());
        this.isMetric = UserConfig.getInstance().isMertric();
        this.swimAnalysePresenter = new SwimAnalysePresenter(this, this.isMetric);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                SwimAnalyseActivity.this.initData();
                SwimAnalyseActivity.this.initView();
            }
        }, 300);
    }

    /* access modifiers changed from: private */
    public void initData() {
        this.uid = UserConfig.getInstance().getNewUID();
        this.startTime = getIntent().getLongExtra("start_time", 0) / 1000;
        this.endTime = getIntent().getLongExtra("end_time", 0) / 1000;
        this.mType = getIntent().getIntExtra("type", 0);
        this.dataFrom = getIntent().getStringExtra("data_from");
        int sportType = getIntent().getIntExtra("sport_type", 0);
        if (sportType == 2097) {
            this.swimType = 0;
        } else {
            this.swimType = 1;
        }
        KLog.d("no2855游泳: " + this.startTime + " == " + this.endTime + " == " + this.mType + " == " + this.dataFrom + " sport: " + sportType);
    }

    /* access modifiers changed from: private */
    public void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        if (this.swimType == 0) {
            title.setText(getString(R.string.sport_swimming_swim_pool));
        } else {
            title.setText(getString(R.string.sport_swimming_swim_free));
        }
        this.addLayout = (LinearLayout) findViewById(R.id.sport_add_layout);
        this.loadText = (TextView) findViewById(R.id.load_txt);
        this.loadText.setVisibility(8);
        this.runSwimItem = new RunSwimItem((Context) this, this.swimType, this.isMetric);
        this.addLayout.addView(this.runSwimItem);
        this.runSwimRateItem = new RunSwimRateItem((Context) this, true);
        this.addLayout.addView(this.runSwimRateItem);
        this.swimAnalysePresenter.getSwimBaseData(this.uid, this.dataFrom, this.startTime);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    public void onBaseDataArrive(SwimHealthyData swimHealthyData) {
        if (this.runSwimItem != null) {
            this.runSwimItem.refreshView(swimHealthyData);
        }
        if (this.runSwimRateItem != null) {
            this.runSwimRateItem.setAvgRate(swimHealthyData.getAvgRate());
        }
        this.swimAnalysePresenter.getSwimRateData(this.uid, this.dataFrom, this.startTime, this.endTime, swimHealthyData.getUrl());
    }

    public void onRateDataArrive(SwimRateData swimRateData) {
        if (this.runSwimRateItem != null) {
            this.runSwimRateItem.refreshView(swimRateData);
        }
        if (this.runSwimItem != null && this.swimType != 0 && swimRateData != null) {
            this.runSwimItem.refreshMaxRate(swimRateData.getMaxY_rate());
        }
    }
}
