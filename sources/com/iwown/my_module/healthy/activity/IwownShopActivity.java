package com.iwown.my_module.healthy.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.HealthyConstants;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.event.AbsAuthEvent;
import com.youzan.sdk.event.AbsChooserEvent;
import com.youzan.sdk.event.AbsShareEvent;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.Event;
import com.youzan.sdk.web.plugin.YouzanBrowser;

public class IwownShopActivity extends BaseActivity {
    private static final int CODE_REQUEST_LOGIN = 123;
    private Handler handler;
    private YouzanBrowser mView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.handler = new Handler();
        if (!YouzanSDK.READY) {
            YouzanSDK.init(this, HealthyConstants.YOUZAN_ID);
        }
        this.handler.postDelayed(new Runnable() {
            public void run() {
                IwownShopActivity.this.initView();
                IwownShopActivity.this.initListener();
            }
        }, 200);
    }

    /* access modifiers changed from: private */
    public void initView() {
        this.mView = new YouzanBrowser(this);
        this.mView.loadUrl("https://h5.youzan.com/v2/showcase/homepage?alias=uievbze9");
        setContentView((View) this.mView);
        setTitleText(R.string.iwown_shop);
        setLeftBackTo();
        setLeftTitle("关闭");
    }

    /* access modifiers changed from: private */
    public void initListener() {
        this.mView.subscribe((Event) new AbsAuthEvent() {
            public void call(View view, boolean needLogin) {
            }
        });
        this.mView.subscribe((Event) new AbsChooserEvent() {
            public void call(View view, Intent intent, int requestCode) throws ActivityNotFoundException {
                IwownShopActivity.this.startActivityForResult(intent, requestCode);
            }
        });
        this.mView.subscribe((Event) new AbsShareEvent() {
            public void call(View view, GoodsShareModel data) {
                String content = String.format("%s %s", new Object[]{data.getDesc(), data.getLink()});
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", content);
                sendIntent.putExtra("android.intent.extra.SUBJECT", data.getTitle());
                sendIntent.setFlags(268435456);
                sendIntent.setType("text/plain");
                IwownShopActivity.this.startActivity(sendIntent);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && 123 != requestCode) {
            this.mView.receiveFile(requestCode, data);
        }
    }

    public void onBackPressed() {
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != 4 || !this.mView.canGoBack()) {
            return super.dispatchKeyEvent(event);
        }
        this.mView.goBack();
        return true;
    }

    public void back() {
        super.back();
    }
}
