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
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.RegexUtils;
import com.iwown.my_module.healthy.contract.BbsAccountContract.BbsAccountView;
import com.iwown.my_module.healthy.data.DiscuzUser;
import com.iwown.my_module.healthy.network.request.BBSAccount;
import com.iwown.my_module.healthy.presenter.BbsAccountPresenter;
import com.iwown.my_module.healthy.view.ClearableEditText;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class BBSRegisterActivity extends BaseActivity implements OnClickListener, BbsAccountView {
    private BbsAccountPresenter bbsAccountPresenter;
    Button btRegister;
    CheckBox cbPwdShow;
    ClearableEditText etEmail;
    EditText etPwd;
    ClearableEditText etUsername;
    private String nickename = "";
    TextView tvHasAccount;
    private long uid = 0;
    private DiscuzUser user;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbsregister);
        setLeftBackTo();
        setTitleText("埃微论坛");
        initView();
        this.uid = new HealthySharedUtil(this).getUid();
        UserInfoEntity info = (UserInfoEntity) DataSupport.where("uid=?", this.uid + "").findFirst(UserInfoEntity.class);
        if (!(info == null || info.getNickname() == null)) {
            this.nickename = info.getNickname();
        }
        this.tvHasAccount.getPaint().setFlags(8);
        initData();
        initListener();
    }

    private void initView() {
        this.etUsername = (ClearableEditText) findViewById(R.id.et_username);
        this.etEmail = (ClearableEditText) findViewById(R.id.et_email);
        this.etPwd = (EditText) findViewById(R.id.et_pwd);
        this.btRegister = (Button) findViewById(R.id.bt_register);
        this.tvHasAccount = (TextView) findViewById(R.id.tv_has_account);
        this.cbPwdShow = (CheckBox) findViewById(R.id.cb_pwd_show);
    }

    private void initData() {
        this.etUsername.setText(this.nickename + "");
    }

    private void initListener() {
        this.bbsAccountPresenter = new BbsAccountPresenter(this);
        this.tvHasAccount.setOnClickListener(this);
        this.btRegister.setOnClickListener(this);
        this.cbPwdShow.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    BBSRegisterActivity.this.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    BBSRegisterActivity.this.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_has_account) {
            startActivity(new Intent(this, BBSLoginActivity.class));
        } else if (view.getId() == R.id.bt_register) {
            registerDizc();
        }
    }

    private void registerDizc() {
        this.user = new DiscuzUser();
        String bbsName = this.etUsername.getText().toString().trim();
        String bbs_pwd = this.etPwd.getText().toString().trim();
        String bbs_email = this.etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(bbsName)) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_4));
        } else if (this.etUsername.getText().toString().length() > 12) {
            ToastUtils.showShortToast((CharSequence) "名字长度不能超过12位");
        } else if (TextUtils.isEmpty(bbs_pwd)) {
            ToastUtils.showShortToast((CharSequence) "密码不能为空");
        } else if (bbs_pwd.length() < 6 || bbs_pwd.length() > 12) {
            ToastUtils.showShortToast((CharSequence) "密码长度必须6~12字符");
        } else if (TextUtils.isEmpty(bbs_email)) {
            ToastUtils.showShortToast((CharSequence) "邮箱不能为空");
        } else if (!RegexUtils.checkEmail(bbs_email)) {
            ToastUtils.showShortToast((CharSequence) "Email 不符合规则");
        } else {
            this.user.setPassword(bbs_pwd);
            this.user.setUserName(bbsName);
            this.user.setEmail(bbs_email);
            this.bbsAccountPresenter.registerBBs(this.user);
        }
    }

    private void saveBBSAccount() {
        if (this.user != null) {
            String bbsName = this.etUsername.getText().toString().trim();
            String bbs_pwd = this.etPwd.getText().toString().trim();
            if (RegexUtils.isEixtExChar(bbsName)) {
                ToastUtils.showShortToast((CharSequence) "用户名不能含有特殊字符");
            } else if (TextUtils.isEmpty(bbsName)) {
                ToastUtils.showShortToast((CharSequence) getString(R.string.activity_bbs_4));
            } else if (this.etUsername.getText().toString().length() > 12) {
                ToastUtils.showShortToast((CharSequence) "名字长度不能超过12位");
            } else if (TextUtils.isEmpty(bbs_pwd)) {
                ToastUtils.showShortToast((CharSequence) "密码不能为空");
            } else if (bbs_pwd.length() < 6 || bbs_pwd.length() > 12) {
                ToastUtils.showShortToast((CharSequence) "密码长度必须6~12字符");
            } else {
                BBSAccount account = new BBSAccount();
                account.setUid(UserConfig.getInstance().getNewUID());
                if (this.user != null) {
                    account.setEmail(this.user.getEmail());
                }
                account.setPassword(this.user.getPassword());
                account.setUserName(this.user.getUserName());
                this.bbsAccountPresenter.loginBBs(account);
            }
        }
    }

    private void bindAccount() {
        if (this.user != null) {
            BBSAccount account = new BBSAccount();
            account.setUid(this.uid);
            account.setEmail(this.user.getEmail());
            account.setPassword(this.user.getPassword());
            account.setUserName(this.user.getUserName());
            this.bbsAccountPresenter.bindBBs(account);
        }
    }

    public void onEventMainThread(BBSFinish bbsFinish) {
        finish();
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
        if (retCode == 0) {
            saveBBSAccount();
        } else if (retCode == 70001) {
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
    }
}
