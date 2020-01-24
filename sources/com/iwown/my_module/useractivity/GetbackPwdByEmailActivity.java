package com.iwown.my_module.useractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.ApiConst;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.dialog.DialogRemind;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.widget.MyEditTex;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetbackPwdByEmailActivity extends BaseActivity {
    private String mEmail;
    MyEditTex mEmailEdt;
    /* access modifiers changed from: private */
    public Handler mHandler;
    RelativeLayout mLayout;
    /* access modifiers changed from: private */
    public DialogRemind mNoticeDialog;
    TextView mOkBtn;
    private Retrofit mRetrofit;
    private UserService mUserService;
    /* access modifiers changed from: private */
    public int time = 60;
    private String user;

    class secondClass implements Runnable {
        secondClass() {
        }

        public void run() {
            while (GetbackPwdByEmailActivity.this.time > 0) {
                GetbackPwdByEmailActivity.this.mHandler.post(new Runnable() {
                    public void run() {
                        if (!GetbackPwdByEmailActivity.this.isDestroyed()) {
                            GetbackPwdByEmailActivity.this.mOkBtn.setText(GetbackPwdByEmailActivity.this.time + "s");
                            GetbackPwdByEmailActivity.this.mOkBtn.setEnabled(false);
                            GetbackPwdByEmailActivity.this.mOkBtn.setClickable(false);
                            GetbackPwdByEmailActivity.this.mOkBtn.setSelected(false);
                        }
                    }
                });
                GetbackPwdByEmailActivity.this.time = GetbackPwdByEmailActivity.this.time - 1;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
            GetbackPwdByEmailActivity.this.mHandler.post(new Runnable() {
                public void run() {
                    if (!GetbackPwdByEmailActivity.this.isDestroyed()) {
                        GetbackPwdByEmailActivity.this.mOkBtn.setText(R.string.sport_module_ok);
                        GetbackPwdByEmailActivity.this.mOkBtn.setEnabled(true);
                        GetbackPwdByEmailActivity.this.mOkBtn.setClickable(true);
                        GetbackPwdByEmailActivity.this.mOkBtn.setSelected(true);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_find_pwd_email);
        this.mEmailEdt = (MyEditTex) findViewById(R.id.email_edt);
        this.mOkBtn = (TextView) findViewById(R.id.ok_btn);
        this.mLayout = (RelativeLayout) findViewById(R.id.find_pwd_email_layout);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.user = getIntent().getStringExtra("email");
        if (this.user != null) {
            this.mEmailEdt.setText(this.user);
        }
        this.mRetrofit = new Builder().baseUrl(ApiConst.AMAZON_API_ADDRESS_PROD).addConverterFactory(GsonConverterFactory.create()).build();
        initView();
        initEvent();
    }

    private void initView() {
        this.mEmailEdt.setEdtHint(R.string.email_address);
        this.mEmailEdt.setEdtHintColor(Color.parseColor("#B4B4B4"));
        this.mEmailEdt.setUderLineColorWhenChg(R.color.find_pwd_edt_underline_color_yes, R.color.find_pwd_edt_underline_color_no);
        setTitleText(getString(R.string.findpwdactiviey_title));
        setLeftBackTo();
        this.mOkBtn.setSelected(true);
    }

    private void initEvent() {
        this.mOkBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GetbackPwdByEmailActivity.this.retrieveByEmail();
            }
        });
    }

    public void retrieveByEmail() {
        Call<ReturnCode> call;
        this.mEmail = this.mEmailEdt.getText().toString().trim();
        if (checkEmail()) {
            this.time = 60;
            new Thread(new secondClass()).start();
            this.mOkBtn.setClickable(false);
            this.mOkBtn.setSelected(false);
            if (this.mUserService == null) {
                this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
            }
            if (AppConfigUtil.isDrviva()) {
                call = this.mUserService.getbackPwdByEmailDrViva(this.mEmail);
            } else {
                call = this.mUserService.getbackPwdByEmail(this.mEmail);
            }
            showLoadingDialog();
            call.enqueue(new Callback<ReturnCode>() {
                public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                    GetbackPwdByEmailActivity.this.hideLoadingDialog();
                    if (response == null || response.body() == null) {
                        GetbackPwdByEmailActivity.this.raiseErrorNotice(GetbackPwdByEmailActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                        GetbackPwdByEmailActivity.this.setErrorUIStatus();
                        return;
                    }
                    switch (((ReturnCode) response.body()).getRetCode()) {
                        case 0:
                            GetbackPwdByEmailActivity.this.mNoticeDialog = new DialogRemind(GetbackPwdByEmailActivity.this);
                            GetbackPwdByEmailActivity.this.mNoticeDialog.setOnlyOneBT(true);
                            GetbackPwdByEmailActivity.this.mNoticeDialog.setClickCallBack(new ClickCallBack() {
                                public void onOk() {
                                    Intent intent = new Intent(GetbackPwdByEmailActivity.this, LoginActivity.class);
                                    intent.setFlags(67108864);
                                    GetbackPwdByEmailActivity.this.startActivity(intent);
                                }

                                public void onCancel() {
                                    GetbackPwdByEmailActivity.this.mNoticeDialog.dismiss();
                                }
                            });
                            GetbackPwdByEmailActivity.this.mNoticeDialog.show();
                            return;
                        case 10001:
                            GetbackPwdByEmailActivity.this.raiseErrorNotice(R.string.sql_error);
                            GetbackPwdByEmailActivity.this.setErrorUIStatus();
                            return;
                        case ApiConst.UserNotExist /*50012*/:
                        case ApiConst.UserAlreadyExistMoreThanOne /*80001*/:
                            GetbackPwdByEmailActivity.this.raiseErrorNotice(R.string.getback_pwd_email_nonexist);
                            GetbackPwdByEmailActivity.this.setErrorUIStatus();
                            return;
                        default:
                            GetbackPwdByEmailActivity.this.raiseErrorNotice(GetbackPwdByEmailActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((ReturnCode) response.body()).getRetCode())}));
                            GetbackPwdByEmailActivity.this.setErrorUIStatus();
                            return;
                    }
                }

                public void onFailure(Call<ReturnCode> call, Throwable t) {
                    GetbackPwdByEmailActivity.this.hideLoadingDialog();
                    GetbackPwdByEmailActivity.this.raiseErrorNotice(GetbackPwdByEmailActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    GetbackPwdByEmailActivity.this.setErrorUIStatus();
                }
            });
        }
    }

    private boolean checkEmail() {
        if (!TextUtils.isEmpty(this.mEmailEdt.getText().toString().trim())) {
            return true;
        }
        raiseErrorNotice(R.string.empty_username);
        setErrorUIStatus();
        return false;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mNoticeDialog != null) {
            this.mNoticeDialog.dismiss();
        }
    }

    public void setErrorUIStatus() {
        this.time = 0;
        this.mOkBtn.setClickable(true);
        this.mOkBtn.setSelected(true);
    }
}
