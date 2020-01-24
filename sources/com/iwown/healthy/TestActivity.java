package com.iwown.healthy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.healthy.zeroner_pro.R;
import com.iwown.data_link.RouteUtils;
import com.iwown.healthy.dialog.TestDialogRemind;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.iwown.lib_common.toast.ToastUtils;

public class TestActivity extends Activity {
    private Button btTestNetwork;
    private Button btTestPermissions;
    private Button go2Active;
    private Button mBtGoToActive;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        initView();
    }

    private void initView() {
        this.btTestNetwork = (Button) findViewById(R.id.bt_test_network);
        this.btTestNetwork.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TestDialogRemind dialogRemindStyle = new TestDialogRemind(TestActivity.this);
                dialogRemindStyle.setOnlyOneBT(true);
                dialogRemindStyle.setClickCallBack(new ClickCallBack() {
                    public void onOk() {
                        ToastUtils.showShortToast((CharSequence) "ok");
                    }

                    public void onCancel() {
                        ToastUtils.showShortToast((CharSequence) "cancel");
                    }
                });
                dialogRemindStyle.show();
            }
        });
        this.btTestPermissions = (Button) findViewById(R.id.bt_test_permissions);
        this.btTestPermissions.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PermissionsUtils.handleCAMER(TestActivity.this, new PermissinCallBack() {
                    public void callBackOk() {
                    }

                    public void callBackFial() {
                    }
                });
            }
        });
        this.mBtGoToActive = (Button) findViewById(R.id.bt_go_to_active);
        this.mBtGoToActive.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RouteUtils.startActiveActivity();
            }
        });
    }
}
