package com.iwown.my_module.healthy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.iwown.data_link.AppManger;
import com.iwown.data_link.consts.UserConst;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.HealthyLoginActivity;
import com.iwown.my_module.healthy.contract.CheckUserExist;
import com.iwown.my_module.healthy.contract.CheckUserExist.CheckListener;
import com.iwown.my_module.utility.TextValidator;
import com.socks.library.KLog;

public class PhoneOrEmailActivity extends BaseActivity implements CheckListener {
    /* access modifiers changed from: private */
    public View changeView;
    private CheckUserExist checkUserExist;
    private LoadingDialog dialog;
    private int enterType;
    InputMethodManager imm;
    private EditText phoneEdit;
    private Button phoneOk;
    private String phoneStr;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() > 0) {
                PhoneOrEmailActivity.this.changeView.setBackgroundColor(-1);
            } else {
                PhoneOrEmailActivity.this.changeView.setBackgroundColor(-10394000);
            }
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_email_main);
        this.enterType = getIntent().getIntExtra("enter", 1);
        this.phoneEdit = (EditText) findViewById(R.id.phone_email_edit);
        this.phoneOk = (Button) findViewById(R.id.phone_email_ok);
        this.changeView = findViewById(R.id.change_view);
        if (this.enterType == 1) {
            setTitleText(R.string.activity_forgetpassword_title);
            this.phoneEdit.setHint(R.string.register_edit_phone);
            this.phoneOk.setText(R.string.get_nianzhengma);
        } else if (this.enterType == 2) {
            setTitleText(R.string.activity_login_register);
            this.phoneEdit.setHint(R.string.register_edit_phone);
            this.phoneOk.setText(R.string.get_nianzhengma);
        } else {
            setTitleText(R.string.activity_forgetpassword_title);
            this.phoneEdit.setHint(R.string.edit_init_message);
            this.phoneOk.setText(R.string.home_weight_record_ok);
        }
        setLeftBackTo();
        this.phoneEdit.addTextChangedListener(this.textWatcher);
        if (this.enterType == 1 || this.enterType == 2) {
            this.phoneEdit.setInputType(2);
        } else {
            this.phoneEdit.setInputType(32);
        }
        this.checkUserExist = new CheckUserExist(this);
        this.imm = (InputMethodManager) getSystemService("input_method");
        AppManger.getAppManager().addActivity(this);
        this.phoneEdit.setFocusable(true);
        this.phoneEdit.setFocusableInTouchMode(true);
        this.phoneEdit.requestFocus();
        showInputMethod(true);
        this.phoneOk.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhoneOrEmailActivity.this.sendPassword();
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void showDialog() {
        this.phoneOk.setEnabled(false);
        if (this.dialog == null) {
            this.dialog = new LoadingDialog(this);
            this.dialog.setCanceledOnTouchOutside(false);
        }
        this.dialog.show();
    }

    private void hiddenDialog() {
        this.phoneOk.setEnabled(true);
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

    public void sendPassword() {
        showInputMethod(false);
        this.phoneStr = this.phoneEdit.getText().toString().trim();
        if (this.enterType == 1 || this.enterType == 2) {
            if (!TextValidator.isPhoneNumber(this.phoneStr)) {
                Toast.makeText(this, getResources().getString(R.string.phone_number_isnull), 0).show();
                return;
            }
            showDialog();
            this.checkUserExist.checkPhoneExist(this.phoneStr);
        } else if (!TextValidator.isEmail(this.phoneStr)) {
            Toast.makeText(this, getResources().getString(R.string.message_format_email_error), 0).show();
        } else {
            showDialog();
            this.checkUserExist.checkEmailExist(this.phoneStr);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public void onCheckUserEnd(int type) {
        if (type == 0) {
            if (this.enterType == 1) {
                hiddenDialog();
                KLog.e("no2855短时的暗示打d ");
                Intent intent = new Intent(this, VerificationActivity.class);
                intent.putExtra("enter", this.enterType);
                intent.putExtra(UserConst.PHONE, this.phoneStr);
                startActivity(intent);
            } else if (this.enterType == 2) {
                hiddenDialog();
                Toast.makeText(this, getResources().getString(R.string.healthy_activity_user_exist), 0).show();
            } else {
                this.checkUserExist.sendUserEmail(this.phoneStr);
            }
        } else if (type == 50012) {
            hiddenDialog();
            if (this.enterType == 2) {
                Intent intent2 = new Intent(this, VerificationActivity.class);
                intent2.putExtra("enter", this.enterType);
                intent2.putExtra(UserConst.PHONE, this.phoneStr);
                startActivity(intent2);
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.message_login_not_exists), 0).show();
        } else {
            hiddenDialog();
            Toast.makeText(this, getResources().getString(R.string.client_error), 0).show();
        }
    }

    public void onSendEmail(int type) {
        hiddenDialog();
        if (type == 0) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.msg_send_to_email));
            startActivity(new Intent(this, HealthyLoginActivity.class));
            AppManger.getAppManager().finishAllActivity();
            return;
        }
        ToastUtils.showShortToast((CharSequence) getString(R.string.msg_no_send__email));
    }

    private void showInputMethod(boolean isShow) {
        if (isShow) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    PhoneOrEmailActivity.this.imm.toggleSoftInput(0, 2);
                }
            }, 300);
        } else {
            this.imm.hideSoftInputFromWindow(this.phoneEdit.getWindowToken(), 0);
        }
    }
}
