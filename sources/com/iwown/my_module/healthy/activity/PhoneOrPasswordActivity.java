package com.iwown.my_module.healthy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import com.iwown.data_link.AppManger;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.HealthyLoginActivity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.HealthyUtil;
import com.iwown.my_module.healthy.register.RegisterManager;
import com.iwown.my_module.healthy.register.RegisterManager.PasswordListener;
import com.iwown.my_module.useractivity.profile.NewProfileGenderActivity;
import com.tencent.tinker.android.dx.instruction.Opcodes;

public class PhoneOrPasswordActivity extends BaseActivity implements PasswordListener {
    View changeView;
    /* access modifiers changed from: private */
    public LoadingDialog dialog;
    /* access modifiers changed from: private */
    public int enterType;
    private Context mContext;
    private Handler mHandler;
    private String phone;
    CheckBox phoneEyes;
    Button phoneOk;
    EditText phonePswEdit;
    private RegisterManager registerManager;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() > 0) {
                PhoneOrPasswordActivity.this.changeView.setBackgroundColor(-1);
            } else {
                PhoneOrPasswordActivity.this.changeView.setBackgroundColor(-10394000);
            }
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_email_main);
        this.mContext = this;
        setLeftBackTo();
        this.enterType = getIntent().getIntExtra("enter", 1);
        this.phone = getIntent().getStringExtra(UserConst.PHONE);
        this.mHandler = new Handler();
        initView();
        this.registerManager = new RegisterManager();
        this.registerManager.setOnPasswordListener(this);
        AppManger.getAppManager().addActivity(this);
    }

    private void initView() {
        this.phonePswEdit = (EditText) findViewById(R.id.phone_email_edit);
        this.phoneEyes = (CheckBox) findViewById(R.id.phone_email_clear);
        this.phoneOk = (Button) findViewById(R.id.phone_email_ok);
        this.changeView = findViewById(R.id.change_view);
        this.phoneEyes.setVisibility(0);
        this.phonePswEdit.addTextChangedListener(this.textWatcher);
        this.phonePswEdit.setFocusable(true);
        this.phonePswEdit.setFocusableInTouchMode(true);
        this.phonePswEdit.requestFocus();
        this.phonePswEdit.setHint(R.string.password_hint);
        showInputMethod(true);
        if (this.enterType == 1) {
            setTitleText(R.string.activity_forgetpassword_title);
            this.phoneOk.setText(getString(R.string.home_weight_record_ok));
        } else {
            setTitleText(R.string.activity_register);
            this.phoneOk.setText(getString(R.string.activity_register));
        }
        this.phoneEyes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PhoneOrPasswordActivity.this.phonePswEdit.setInputType(Opcodes.ADD_INT);
                    PhoneOrPasswordActivity.this.phonePswEdit.setSelection(PhoneOrPasswordActivity.this.phonePswEdit.getText().toString().length());
                    return;
                }
                PhoneOrPasswordActivity.this.phonePswEdit.setInputType(Opcodes.SHR_INT_LIT8);
                PhoneOrPasswordActivity.this.phonePswEdit.setSelection(PhoneOrPasswordActivity.this.phonePswEdit.getText().toString().length());
            }
        });
        this.phoneOk.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhoneOrPasswordActivity.this.sendPassword();
            }
        });
    }

    private void showDialog() {
        if (this.dialog == null) {
            this.dialog = new LoadingDialog(this);
            this.dialog.setCanceledOnTouchOutside(false);
        }
        this.dialog.show();
    }

    private void hiddenDialog() {
        this.mHandler.post(new Runnable() {
            public void run() {
                if (!PhoneOrPasswordActivity.this.isDestroyed()) {
                    if (PhoneOrPasswordActivity.this.dialog != null) {
                        PhoneOrPasswordActivity.this.dialog.dismiss();
                    }
                    PhoneOrPasswordActivity.this.phoneOk.setEnabled(true);
                }
            }
        });
    }

    public void sendPassword() {
        if (TextUtils.isEmpty(this.phone)) {
            Toast.makeText(this, getString(R.string.no_phone_error), 0).show();
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(PhoneOrPasswordActivity.this, PhoneOrEmailActivity.class);
                    intent.putExtra("enter", PhoneOrPasswordActivity.this.enterType);
                    PhoneOrPasswordActivity.this.startActivity(intent);
                    PhoneOrPasswordActivity.this.finish();
                }
            }, 1000);
        } else if (this.phonePswEdit.getText().toString().trim().length() < 6 || this.phonePswEdit.getText().toString().trim().length() > 18) {
            Toast.makeText(this, getString(R.string.password_hint), 0).show();
        } else {
            showDialog();
            if (this.enterType == 1) {
                this.registerManager.phoneResetPsw(this.phone, this.phonePswEdit.getText().toString().trim());
            } else {
                this.registerManager.phoneRegister(this.phone, this.phonePswEdit.getText().toString().trim());
            }
        }
    }

    public void onPasswordEnd(int type, long uid) {
        hiddenDialog();
        if (type == 0) {
            if (this.enterType == 2) {
                HealthySharedUtil sharedUtil = new HealthySharedUtil(this);
                sharedUtil.setLoginType(1);
                sharedUtil.setUid(uid);
                GlobalDataUpdater.setLoginStatus(this.mContext, 1);
                AppManger.getAppManager().finishAllActivity();
                HealthyUtil.saveWxQqToTb(this.phone, 1, uid);
                Intent intent = new Intent(this, NewProfileGenderActivity.class);
                intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
                startActivity(intent);
                finish();
                return;
            }
            AppManger.getAppManager().finishAllActivity();
            startActivity(new Intent(this, HealthyLoginActivity.class));
            finish();
        } else if (type == 11000) {
            Toast.makeText(this, getString(R.string.network_unavailable), 0).show();
        } else {
            Toast.makeText(this, getString(R.string.submit_error), 0).show();
        }
    }

    public void back() {
        super.back();
    }

    private void showInputMethod(boolean isShow) {
        final InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        if (isShow) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    imm.toggleSoftInput(0, 2);
                }
            }, 300);
        } else {
            imm.hideSoftInputFromWindow(this.phonePswEdit.getWindowToken(), 0);
        }
    }
}
