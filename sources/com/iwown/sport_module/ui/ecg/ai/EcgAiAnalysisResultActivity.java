package com.iwown.sport_module.ui.ecg.ai;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.HashMap;

public class EcgAiAnalysisResultActivity extends BaseActivity implements OnClickListener {
    private String data;
    private ConstraintLayout ecgAnotherLayout;
    private TextView ecgResult;
    private TextView ecgResult1;
    private TextView ecgResultDes;
    private TextView ecgResultDes1;
    private ImageView imgPullDown;
    private ImageView imgPullDown1;
    ArrayList<String> list;
    private HashMap<String, String> maps = new HashMap<>();
    private HashMap<String, String> mapsDes = new HashMap<>();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_ecg_ai_analysis_result);
        setLeftBackTo();
        setTitleBarBG(getResources().getColor(R.color.heart_top));
        setTitleTextID(R.string.sport_module_page_ecg_2);
        this.data = getIntent().getStringExtra("data");
        KLog.i("---------------" + this.data);
        try {
            if (!TextUtils.isEmpty(this.data)) {
                this.list = JsonUtils.getListJson(this.data, String.class);
                this.maps.put("SN", getString(R.string.sport_module_page_ecg_11));
                this.maps.put("N", getString(R.string.sport_module_page_ecg_15));
                this.maps.put("SNA", getString(R.string.sport_module_page_ecg_12));
                this.maps.put("SNT", getString(R.string.sport_module_page_ecg_16));
                this.maps.put("SNB", getString(R.string.sport_module_page_ecg_17));
                this.maps.put("AF", getString(R.string.sport_module_page_ecg_18));
                this.maps.put("AFL", getString(R.string.sport_module_page_ecg_19));
                this.maps.put("PVC", getString(R.string.sport_module_page_ecg_20));
                this.maps.put("PAC", getString(R.string.sport_module_page_ecg_21));
                this.maps.put("LBBB", getString(R.string.sport_module_page_ecg_22));
                this.maps.put("RBBB", getString(R.string.sport_module_page_ecg_23));
                this.maps.put("NO", getString(R.string.sport_module_page_ecg_38));
                this.mapsDes.put("SN", getString(R.string.sport_module_page_ecg_24));
                this.mapsDes.put("N", "");
                this.mapsDes.put("SNA", getString(R.string.sport_module_page_ecg_25));
                this.mapsDes.put("SNT", getString(R.string.sport_module_page_ecg_26));
                this.mapsDes.put("SNB", getString(R.string.sport_module_page_ecg_27));
                this.mapsDes.put("AF", getString(R.string.sport_module_page_ecg_28));
                this.mapsDes.put("AFL", getString(R.string.sport_module_page_ecg_29));
                this.mapsDes.put("PVC", getString(R.string.sport_module_page_ecg_31));
                this.mapsDes.put("PAC", getString(R.string.sport_module_page_ecg_30));
                this.mapsDes.put("LBBB", getString(R.string.sport_module_page_ecg_34));
                this.mapsDes.put("RBBB", getString(R.string.sport_module_page_ecg_33));
                this.mapsDes.put("NO", getString(R.string.sport_module_page_ecg_39));
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        initView();
    }

    private void initView() {
        this.ecgResult = (TextView) findViewById(R.id.ecg_ai_result);
        this.ecgResultDes = (TextView) findViewById(R.id.ecg_result_des);
        this.imgPullDown = (ImageView) findViewById(R.id.ecg_result_pull_down);
        this.ecgResult1 = (TextView) findViewById(R.id.ecg_ai_result_1);
        this.ecgResultDes1 = (TextView) findViewById(R.id.ecg_result_des_1);
        this.imgPullDown1 = (ImageView) findViewById(R.id.ecg_result_pull_down_1);
        this.ecgAnotherLayout = (ConstraintLayout) findViewById(R.id.another_layout);
        this.imgPullDown.setOnClickListener(this);
        this.ecgResult.setOnClickListener(this);
        this.imgPullDown1.setOnClickListener(this);
        this.ecgResult1.setOnClickListener(this);
        this.ecgResultDes.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (this.list == null || this.list.size() <= 0) {
            this.ecgResult.setText((CharSequence) this.maps.get("NO"));
            this.ecgResultDes.setText((CharSequence) this.mapsDes.get("NO"));
            this.ecgAnotherLayout.setVisibility(8);
            return;
        }
        try {
            if (this.list.size() <= 1) {
                if (this.maps.get(this.list.get(0)) == null) {
                    this.ecgResult.setText((CharSequence) this.maps.get("NO"));
                    this.ecgResultDes.setText((CharSequence) this.mapsDes.get("NO"));
                } else if (TextUtils.isEmpty((CharSequence) this.maps.get(this.list.get(0)))) {
                    this.ecgResult.setText((CharSequence) this.maps.get("NO"));
                    this.ecgResultDes.setText((CharSequence) this.mapsDes.get("NO"));
                } else {
                    this.ecgResult.setText((CharSequence) this.maps.get(this.list.get(0)));
                    this.ecgResultDes.setText((CharSequence) this.mapsDes.get(this.list.get(0)));
                }
                this.ecgAnotherLayout.setVisibility(8);
            }
            if (this.list.size() > 1) {
                if (this.maps.get(this.list.get(0)) == null) {
                    this.ecgResult.setText((CharSequence) this.maps.get("NO"));
                    this.ecgResultDes.setText((CharSequence) this.mapsDes.get("NO"));
                } else if (TextUtils.isEmpty((CharSequence) this.maps.get(this.list.get(0)))) {
                    this.ecgResult.setText((CharSequence) this.maps.get("NO"));
                    this.ecgResultDes.setText((CharSequence) this.mapsDes.get("NO"));
                } else {
                    this.ecgResult.setText((CharSequence) this.maps.get(this.list.get(0)));
                    this.ecgResultDes.setText((CharSequence) this.mapsDes.get(this.list.get(0)));
                }
                if (this.maps.get(this.list.get(1)) == null) {
                    this.ecgResult1.setText((CharSequence) this.maps.get("NO"));
                    this.ecgResultDes1.setText((CharSequence) this.mapsDes.get("NO"));
                } else if (TextUtils.isEmpty((CharSequence) this.maps.get(this.list.get(1)))) {
                    this.ecgResult1.setText((CharSequence) this.maps.get("NO"));
                    this.ecgResultDes1.setText((CharSequence) this.mapsDes.get("NO"));
                } else {
                    this.ecgResult1.setText((CharSequence) this.maps.get(this.list.get(1)));
                    this.ecgResultDes1.setText((CharSequence) this.mapsDes.get(this.list.get(1)));
                }
                this.ecgAnotherLayout.setVisibility(0);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.ecg_result_pull_down || v.getId() == R.id.ecg_ai_result) {
            if (this.ecgResultDes.getVisibility() == 0) {
                this.ecgResultDes.setVisibility(8);
                this.imgPullDown.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ecg_pull_down));
                return;
            }
            this.ecgResultDes.setVisibility(0);
            this.imgPullDown.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.pull_up_3x));
        } else if (v.getId() != R.id.ecg_result_pull_down_1 && v.getId() != R.id.ecg_ai_result_1) {
        } else {
            if (this.ecgResultDes1.getVisibility() == 0) {
                this.ecgResultDes1.setVisibility(8);
                this.imgPullDown1.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ecg_pull_down));
                return;
            }
            this.ecgResultDes1.setVisibility(0);
            this.imgPullDown1.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.pull_up_3x));
        }
    }
}
