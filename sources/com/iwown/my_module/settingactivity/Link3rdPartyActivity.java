package com.iwown.my_module.settingactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.toast.CustomToast;
import com.iwown.my_module.MyInitUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.googlefit.GoogleFitConnectionActivity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.event.WxBindEvent;
import com.iwown.my_module.healthy.presenter.BaseUiListener;
import com.iwown.my_module.strava.StravaConnectActivity;
import com.iwown.my_module.widget.NewSelectinfoView;
import com.kunekt.healthy.wxapi.WxQqAPI;
import com.kunekt.healthy.wxapi.WxQqEvent;
import com.tencent.mm.opensdk.modelbiz.JumpToBizProfile;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Req;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Link3rdPartyActivity extends BaseActivity implements OnClickListener {
    private BaseUiListener baseUiListener;
    LinearLayout chinaLayout;
    NewSelectinfoView mGoogleFitMenu;
    NewSelectinfoView mStravaMenu;
    LinearLayout overseasLayout;
    NewSelectinfoView qqLink;
    private String qrcode;
    NewSelectinfoView wxLink;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_link);
        this.mGoogleFitMenu = (NewSelectinfoView) findViewById(R.id.googlefit_link);
        this.mGoogleFitMenu.setOnClickListener(this);
        this.mStravaMenu = (NewSelectinfoView) findViewById(R.id.strava_link);
        this.mStravaMenu.setOnClickListener(this);
        EventBus.getDefault().register(this);
        if (AppConfigUtil.isUpfit() || AppConfigUtil.isNewfit()) {
            this.mStravaMenu.setVisibility(8);
        } else {
            this.mStravaMenu.setVisibility(0);
        }
        setTitleText(R.string.profile_link);
        setLeftBackTo();
        this.qqLink = (NewSelectinfoView) findViewById(R.id.qq_link);
        this.wxLink = (NewSelectinfoView) findViewById(R.id.wx_link);
        this.overseasLayout = (LinearLayout) findViewById(R.id.overseas_layout);
        this.chinaLayout = (LinearLayout) findViewById(R.id.china_layout);
        this.qqLink.setOnClickListener(this);
        this.wxLink.setOnClickListener(this);
        if (AppConfigUtil.isHealthy(this)) {
            this.overseasLayout.setVisibility(8);
            this.chinaLayout.setVisibility(0);
            return;
        }
        this.overseasLayout.setVisibility(0);
        this.chinaLayout.setVisibility(8);
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.googlefit_link) {
            startActivity(new Intent(this, GoogleFitConnectionActivity.class));
        } else if (i == R.id.strava_link) {
            startActivity(new Intent(this, StravaConnectActivity.class));
        } else if (i == R.id.qq_link) {
            bindQQSport();
        } else if (i == R.id.wx_link) {
            bindWechatSport();
        }
    }

    private void bindWechatSport() {
        new HealthySharedUtil(this).setWxLoginOrBind(2);
        Req req = new Req();
        req.scope = "snsapi_userinfo";
        req.state = "com.kunekt.healthy.zeroner_1";
        WxQqAPI.getIwxapi(getApplicationContext()).sendReq(req);
    }

    private void bindQQSport() {
        if (this.baseUiListener == null) {
            this.baseUiListener = new BaseUiListener(this);
        }
        WxQqAPI.getTencent(this).login((Activity) this, "all", (IUiListener) this.baseUiListener);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11101) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this.baseUiListener);
        }
        if (requestCode == 10100 && resultCode == 0) {
            Tencent.handleResultData(data, this.baseUiListener);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(WxQqEvent event) {
        if (event.getType() == 2) {
            JumpToBizProfile.Req req = new JumpToBizProfile.Req();
            req.toUserName = "gh_865deac93478";
            req.profileType = 1;
            req.extMsg = this.qrcode;
            WxQqAPI.getIwxapi(MyInitUtils.getInstance().getMyApplication()).sendReq(req);
            CustomToast.makeText(this, event.getMsg(), true);
        } else if (event.getType() == 1) {
            CustomToast.makeText(this, event.getMsg(), true);
        } else {
            CustomToast.makeText(this, event.getMsg(), false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxBind(WxBindEvent event) {
        this.qrcode = event.msg;
    }
}
