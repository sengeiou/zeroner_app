package com.iwown.sport_module.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.data_link.consts.UserConst.DrViva;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.lib_common.BaseActivity2;
import com.iwown.sport_module.R;
import com.iwown.sport_module.sql.TB_ad_url;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class ShowAdActivity extends BaseActivity2 {
    private TextView adNoTxt;
    private RecyclerView adRecycler;
    private MessageAdapter mAdapter;
    /* access modifiers changed from: private */
    public Context mContext;
    private PreferenceUtility mPref;
    /* access modifiers changed from: private */
    public List<String> message;

    public class MessageAdapter extends Adapter<MyHolder> {

        class MyHolder extends ViewHolder {
            ImageView adImg;

            public MyHolder(View itemView) {
                super(itemView);
                this.adImg = (ImageView) itemView.findViewById(R.id.ad_main_img);
            }
        }

        public MessageAdapter() {
        }

        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(ShowAdActivity.this.mContext).inflate(R.layout.sport_module_ad_img_item, parent, false));
        }

        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.adImg.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ShowAdActivity.this.goClient((String) ShowAdActivity.this.message.get(position));
                }
            });
        }

        public int getItemCount() {
            return ShowAdActivity.this.message.size();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate in ShowAdActivity");
        setContentView(R.layout.sport_module_show_ad_main);
        setTitleText(R.string.sport_module_ad_message_title);
        setLeftBackTo();
        getTitleBar().setBackgroundColor(RunActivitySkin.Sport_Home_Bg_Color_Bottom);
        this.mContext = this;
        this.mPref = new PreferenceUtility(this);
        initData();
    }

    private void initData() {
        this.adNoTxt = (TextView) findViewById(R.id.ad_no_txt);
        if (AppConfigUtil.isDrviva()) {
            String url = this.mPref.fetchStrValueWithKey(DrViva.MarketUrl);
            if (TextUtils.isEmpty(url) || (!url.startsWith("http://") && !url.startsWith("https://"))) {
                this.adNoTxt.setVisibility(0);
                return;
            }
            this.adNoTxt.setVisibility(8);
            goClient(url);
            return;
        }
        this.message = new ArrayList();
        TB_ad_url ad_url = (TB_ad_url) DataSupport.findFirst(TB_ad_url.class);
        long nowTime = System.currentTimeMillis() / 1000;
        if (ad_url == null || ad_url.getStart_time() > nowTime || ad_url.getAdTime() < nowTime) {
            this.adNoTxt.setVisibility(0);
            return;
        }
        this.adNoTxt.setVisibility(8);
        goClient(ad_url.getAdOneUrl());
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public void back() {
        super.back();
    }

    /* access modifiers changed from: private */
    public void goClient(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        startActivity(intent);
        finish();
    }
}
