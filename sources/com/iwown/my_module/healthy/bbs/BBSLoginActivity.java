package com.iwown.my_module.healthy.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.RegexUtils;
import com.iwown.my_module.healthy.contract.BbsAccountContract.BbsAccountView;
import com.iwown.my_module.healthy.data.DiscuzUser;
import com.iwown.my_module.healthy.network.request.BBSAccount;
import com.iwown.my_module.healthy.presenter.BbsAccountPresenter;
import org.greenrobot.eventbus.EventBus;

public class BBSLoginActivity extends BaseActivity implements BbsAccountView {
    private BbsAccountPresenter bbsAccountPresenter;
    Button btLogin;
    CheckBox cbPwdShow;
    EditText etPwd;
    EditText etUsername;
    TextView tvLossPwd;
    private long uid = 0;
    DiscuzUser user;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbslogin);
        setLeftBackTo();
        setTitleText("埃微论坛");
        initView();
        this.user = (DiscuzUser) getIntent().getParcelableExtra("user");
        if (this.user != null) {
            this.etUsername.setText(this.user.getUserName());
            this.etPwd.setText(this.user.getPassword());
        }
        this.bbsAccountPresenter = new BbsAccountPresenter(this);
        this.btLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BBSLoginActivity.this.saveBBSAccount();
            }
        });
        this.cbPwdShow.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    BBSLoginActivity.this.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    BBSLoginActivity.this.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void initView() {
        this.etUsername = (EditText) findViewById(R.id.bbs_user_edit);
        this.etPwd = (EditText) findViewById(R.id.bbs_pas_edit);
        this.btLogin = (Button) findViewById(R.id.bbs_login_ok);
        this.tvLossPwd = (TextView) findViewById(R.id.tv_loss_pwd);
        this.cbPwdShow = (CheckBox) findViewById(R.id.phone_email_clear);
        this.tvLossPwd.getPaint().setFlags(8);
        this.uid = new HealthySharedUtil(this).getUid();
    }

    /* access modifiers changed from: private */
    public void saveBBSAccount() {
        String bbsName = this.etUsername.getText().toString().trim();
        String bbs_pwd = this.etPwd.getText().toString().trim();
        if (RegexUtils.isEixtExChar(bbsName)) {
            ToastUtils.showShortToast((CharSequence) "用户名不能含有特殊字符");
        } else if (TextUtils.isEmpty(bbsName)) {
            ToastUtils.showShortToast((CharSequence) "用户名为空");
        } else if (this.etUsername.getText().toString().length() > 12) {
            ToastUtils.showShortToast((CharSequence) "名字长度不能超过12位");
        } else if (TextUtils.isEmpty(bbs_pwd)) {
            ToastUtils.showShortToast((CharSequence) "密码不能为空");
        } else {
            BBSAccount account = new BBSAccount();
            account.setUid(getUid());
            if (this.user != null) {
                account.setEmail(this.user.getEmail());
            }
            account.setPassword(this.etPwd.getText().toString().trim());
            account.setUserName(this.etUsername.getText().toString().trim());
            this.bbsAccountPresenter.loginBBs(account);
        }
    }

    private void bindAccount() {
        BBSAccount account = new BBSAccount();
        account.setUid(getUid());
        if (this.user != null) {
            account.setEmail(this.user.getEmail());
        }
        account.setPassword(this.etPwd.getText().toString().trim());
        account.setUserName(this.etUsername.getText().toString().trim());
        this.bbsAccountPresenter.bindBBs(account);
    }

    private long getUid() {
        if (this.uid == 0) {
            this.uid = new HealthySharedUtil(this).getUid();
        }
        return this.uid;
    }

    public void loginResult(int retCode) {
        if (retCode == 0) {
            bindAccount();
            return;
        }
        if (retCode == 70001) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_5));
        } else if (retCode == 70002) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_6));
        } else if (retCode == 70003) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_7));
        } else if (retCode == 70006) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_9));
        } else if (retCode == 70010) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_10));
        } else if (retCode == 70009) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_11));
        } else {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_8));
        }
        new HealthySharedUtil(this).setBBsBind(2);
    }

    public void bindAccountResult(int retCode, String account) {
        if (retCode == 0) {
            HealthySharedUtil sharedUtil = new HealthySharedUtil(this);
            sharedUtil.setBBsBind(1);
            sharedUtil.setBBsAccount(account);
            BroadcastUtils.sendFinishActivityBroadcast(this);
            startActivity(new Intent(this, BBSActivity.class));
            EventBus.getDefault().post(new BBSFinish());
            finish();
            return;
        }
        ToastUtils.showShortToast((CharSequence) "error " + retCode);
        new HealthySharedUtil(this).setBBsBind(2);
    }

    public void registerResult(int retCode) {
    }
}
