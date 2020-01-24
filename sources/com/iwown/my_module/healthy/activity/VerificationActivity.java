package com.iwown.my_module.healthy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.AppManger;
import com.iwown.data_link.consts.UserConst;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.HealthyConstants;
import com.socks.library.KLog;
import org.json.JSONObject;

public class VerificationActivity extends BaseActivity implements Callback {
    private static final int RETRY_INTERVAL = 60;
    EditText codeEdit;
    /* access modifiers changed from: private */
    public String codeString;
    TextView codeText1;
    TextView codeText2;
    TextView codeText3;
    TextView codeText4;
    private Drawable drawableDown;
    private Drawable drawableUp;
    private int enterType;
    /* access modifiers changed from: private */
    public boolean flag;
    InputMethodManager imm;
    private Context mContext;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private OnSendMessageHandler osmHandler;
    private String phone;
    TextView phoneTxt;
    TextView sendAgain;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().length() >= 4) {
                VerificationActivity.this.codeString = s.toString().substring(0, 4);
                VerificationActivity.this.checkText(4, s.toString().substring(0, 4));
                VerificationActivity.this.toSMSnz();
                return;
            }
            VerificationActivity.this.codeString = s.toString();
            VerificationActivity.this.checkText(s.toString().length(), s.toString());
        }
    };
    /* access modifiers changed from: private */
    public int time = 60;
    private boolean toashFlag;

    class CutClass implements Runnable {
        CutClass() {
        }

        public void run() {
            while (VerificationActivity.this.time > 0 && !VerificationActivity.this.flag) {
                VerificationActivity.this.time = VerificationActivity.this.time - 1;
                VerificationActivity.this.mHandler.post(new Runnable() {
                    public void run() {
                        if (VerificationActivity.this.isDestroyed()) {
                            VerificationActivity.this.flag = true;
                            return;
                        }
                        VerificationActivity.this.sendAgain.setEnabled(false);
                        VerificationActivity.this.sendAgain.setText("0:" + VerificationActivity.this.time);
                    }
                });
                try {
                    if (VerificationActivity.this.flag) {
                        break;
                    }
                    Thread.sleep(1000);
                    if (VerificationActivity.this.isDestroyed()) {
                        break;
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
            VerificationActivity.this.mHandler.post(new Runnable() {
                public void run() {
                    if (!VerificationActivity.this.isDestroyed()) {
                        VerificationActivity.this.sendAgain.setEnabled(true);
                        VerificationActivity.this.sendAgain.setText("重新发送");
                    }
                }
            });
            VerificationActivity.this.time = 60;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_main);
        this.mContext = this;
        this.mHandler = new Handler();
        setLeftBackTo();
        initView();
        this.enterType = getIntent().getIntExtra("enter", 1);
        if (this.enterType == 1) {
            setTitleText(R.string.activity_forgetpassword_title);
        } else {
            setTitleText(R.string.activity_register);
        }
        this.phone = getIntent().getStringExtra(UserConst.PHONE);
        this.codeEdit.addTextChangedListener(this.textWatcher);
        initSMSSDK();
        AppManger.getAppManager().addActivity(this);
    }

    private void initView() {
        this.codeEdit = (EditText) findViewById(R.id.verification_edit);
        this.codeText1 = (TextView) findViewById(R.id.code_text1);
        this.codeText2 = (TextView) findViewById(R.id.code_text2);
        this.codeText3 = (TextView) findViewById(R.id.code_text3);
        this.codeText4 = (TextView) findViewById(R.id.code_text4);
        this.phoneTxt = (TextView) findViewById(R.id.phone_num_txt);
        this.sendAgain = (TextView) findViewById(R.id.send_code_again);
        if (this.phone != null && this.phone.length() >= 11) {
            this.phoneTxt.setText(this.phone.substring(0, 3) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.phone.substring(3, 7) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.phone.substring(7, 11));
        }
        this.drawableDown = ContextCompat.getDrawable(this, R.drawable.line_bg_down_shape);
        this.drawableUp = ContextCompat.getDrawable(this, R.drawable.line_bg_shape);
        this.drawableDown.setBounds(0, 0, this.drawableDown.getMinimumWidth(), this.drawableDown.getMinimumHeight());
        this.drawableUp.setBounds(0, 0, this.drawableUp.getMinimumWidth(), this.drawableUp.getMinimumHeight());
        this.codeText1.setCompoundDrawables(null, null, null, this.drawableDown);
        this.codeEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VerificationActivity.this.codeEdit.setSelection(VerificationActivity.this.codeEdit.getText().length());
            }
        });
        this.sendAgain.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                KLog.e("no2855重新发送验证码");
                VerificationActivity.this.getSMSAgain();
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkText(int chose, String message) {
        this.codeText1.setCompoundDrawables(null, null, null, this.drawableUp);
        this.codeText2.setCompoundDrawables(null, null, null, this.drawableUp);
        this.codeText3.setCompoundDrawables(null, null, null, this.drawableUp);
        this.codeText4.setCompoundDrawables(null, null, null, this.drawableUp);
        switch (chose) {
            case 0:
                this.codeText1.setText("");
                this.codeText2.setText("");
                this.codeText3.setText("");
                this.codeText4.setText("");
                return;
            case 1:
                this.codeText1.setText(message.substring(0, 1));
                this.codeText2.setText("");
                this.codeText3.setText("");
                this.codeText4.setText("");
                this.codeText1.setCompoundDrawables(null, null, null, this.drawableDown);
                return;
            case 2:
                this.codeText1.setText(message.substring(0, 1));
                this.codeText2.setText(message.substring(1, 2));
                this.codeText3.setText("");
                this.codeText4.setText("");
                this.codeText2.setCompoundDrawables(null, null, null, this.drawableDown);
                return;
            case 3:
                this.codeText1.setText(message.substring(0, 1));
                this.codeText2.setText(message.substring(1, 2));
                this.codeText3.setText(message.substring(2, 3));
                this.codeText4.setText("");
                this.codeText3.setCompoundDrawables(null, null, null, this.drawableDown);
                return;
            case 4:
                this.codeText1.setText(message.substring(0, 1));
                this.codeText2.setText(message.substring(1, 2));
                this.codeText3.setText(message.substring(2, 3));
                this.codeText4.setText(message.substring(3, 4));
                this.codeText4.setCompoundDrawables(null, null, null, this.drawableDown);
                return;
            default:
                return;
        }
    }

    public void toSMSnz() {
        showInputMethod(false);
        this.toashFlag = false;
        if (!TextUtils.isEmpty(this.phone)) {
            SMSSDK.submitVerificationCode("86", this.phone, this.codeString);
        }
    }

    public void getSMSAgain() {
        if (TextUtils.isEmpty(this.phone)) {
            Toast.makeText(this, getString(R.string.no_phone_error), 0).show();
            Intent intent = new Intent(this, PhoneOrEmailActivity.class);
            intent.putExtra("enter", this.enterType);
            startActivity(intent);
            AppManger.getAppManager().finishAllActivity();
            return;
        }
        getValidateCode();
    }

    private void initSMSSDK() {
        this.imm = (InputMethodManager) getSystemService("input_method");
        SMSSDK.initSDK(this, HealthyConstants.APPKEY, HealthyConstants.APPSECRET);
        final Handler handler = new Handler(this);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        if (!TextUtils.isEmpty(this.phone)) {
            SMSSDK.registerEventHandler(eventHandler);
            getValidateCode();
        }
        showInputMethod(true);
    }

    private void getValidateCode() {
        SMSSDK.getVerificationCode("86", this.phone, this.osmHandler);
        this.flag = false;
        new Thread(new CutClass()).start();
    }

    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (result == -1) {
            if (event == 3) {
                KLog.e("no2855提交验证码成功");
                Intent intent = new Intent(this, PhoneOrPasswordActivity.class);
                intent.putExtra("enter", this.enterType);
                intent.putExtra(UserConst.PHONE, this.phone);
                startActivity(intent);
            } else if (event == 2) {
                KLog.e(" //no2855获取验证码成功" + this.phone);
            } else if (event == 1) {
            }
        } else if (result == 0) {
            try {
                Throwable throwable = (Throwable) data;
                ThrowableExtension.printStackTrace(throwable);
                int status = new JSONObject(throwable.getMessage()).optInt(NotificationCompat.CATEGORY_STATUS);
                if (!this.toashFlag) {
                    this.toashFlag = true;
                    if (status == 467) {
                        this.codeEdit.setText("");
                        Toast.makeText(this, getResources().getString(R.string.send_verification_again), 0).show();
                    } else if (status == 468) {
                        this.codeEdit.setText("");
                        Toast.makeText(this, getResources().getString(R.string.send_verification_code_is_error), 0).show();
                    }
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        return false;
    }

    private void showInputMethod(boolean isShow) {
        if (isShow) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    VerificationActivity.this.imm.toggleSoftInput(0, 2);
                }
            }, 300);
        } else {
            this.imm.hideSoftInputFromWindow(this.codeEdit.getWindowToken(), 0);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
