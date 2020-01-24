package com.iwown.my_module.healthy.register;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.consts.ApiConst;
import com.iwown.my_module.healthy.network.LoginFactory;
import com.iwown.my_module.healthy.network.request.PhoneFindPasswordSend;
import com.iwown.my_module.healthy.network.request.PhoneSend;
import com.iwown.my_module.healthy.network.response.RegisterCode;
import java.net.ConnectException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterManager {
    private LoginFactory loginFactory;
    private PasswordListener passwordListener;

    public interface PasswordListener {
        void onPasswordEnd(int i, long j);
    }

    public void setOnPasswordListener(PasswordListener passwordListener2) {
        this.passwordListener = passwordListener2;
        this.loginFactory = new LoginFactory();
    }

    /* access modifiers changed from: private */
    public void workEnd(int type, long uid) {
        if (this.passwordListener != null) {
            this.passwordListener.onPasswordEnd(type, uid);
        }
    }

    public void phoneResetPsw(String phone, String password) {
        PhoneFindPasswordSend find = new PhoneFindPasswordSend();
        find.setPhone(Long.parseLong(phone));
        find.setPassword(password);
        this.loginFactory.getLoginService().postPhoneFindPassword(find).enqueue(new Callback<RetCode>() {
            public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                if (response == null || response.body() == null) {
                    RegisterManager.this.workEnd(-1, 0);
                    return;
                }
                RegisterManager.this.workEnd(((RetCode) response.body()).getRetCode(), 0);
                call.cancel();
            }

            public void onFailure(Call<RetCode> call, Throwable t) {
                RegisterManager.this.workEnd(-1, 0);
                call.cancel();
            }
        });
    }

    public void phoneRegister(String phone, String password) {
        this.loginFactory.getLoginService().postRegister(new PhoneSend(phone, 1, password)).enqueue(new Callback<RegisterCode>() {
            public void onResponse(Call<RegisterCode> call, Response<RegisterCode> response) {
                if (response != null) {
                    try {
                        if (response.body() != null) {
                            RegisterCode resp = (RegisterCode) response.body();
                            if (((RegisterCode) response.body()).getRetCode() == 0) {
                                RegisterManager.this.workEnd(((RegisterCode) response.body()).getRetCode(), resp.getUid());
                                return;
                            } else {
                                RegisterManager.this.workEnd(((RegisterCode) response.body()).getRetCode(), 0);
                                return;
                            }
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                        return;
                    }
                }
                RegisterManager.this.workEnd(-1, 0);
            }

            public void onFailure(Call<RegisterCode> call, Throwable t) {
                for (Throwable throwable = t; throwable.getCause() != null; throwable = throwable.getCause()) {
                    t = throwable;
                }
                if (t instanceof ConnectException) {
                    RegisterManager.this.workEnd(ApiConst.NETWORK_ERROR, 0);
                } else {
                    RegisterManager.this.workEnd(-1, 0);
                }
                call.cancel();
            }
        });
    }
}
