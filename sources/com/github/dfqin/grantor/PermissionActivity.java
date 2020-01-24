package com.github.dfqin.grantor;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.github.dfqin.grantor.PermissionsUtil.TipInfo;
import java.io.Serializable;

public class PermissionActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 64;
    private final String defaultCancel = "取消";
    private final String defaultContent = "当前应用缺少必要权限。\n \n 请点击 \"设置\"-\"权限\"-打开所需权限。";
    private final String defaultEnsure = "设置";
    private final String defaultTitle = "帮助";
    private boolean isRequireCheck;
    private String key;
    private String[] permission;
    private boolean showTip;
    private TipInfo tipInfo;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra("permission")) {
            finish();
            return;
        }
        this.isRequireCheck = true;
        this.permission = getIntent().getStringArrayExtra("permission");
        this.key = getIntent().getStringExtra("key");
        this.showTip = getIntent().getBooleanExtra("showTip", true);
        Serializable ser = getIntent().getSerializableExtra("tip");
        if (ser == null) {
            this.tipInfo = new TipInfo("帮助", "当前应用缺少必要权限。\n \n 请点击 \"设置\"-\"权限\"-打开所需权限。", "取消", "设置");
        } else {
            this.tipInfo = (TipInfo) ser;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!this.isRequireCheck) {
            this.isRequireCheck = true;
        } else if (PermissionsUtil.hasPermission(this, this.permission)) {
            permissionsGranted();
        } else {
            requestPermissions(this.permission);
            this.isRequireCheck = false;
        }
    }

    private void requestPermissions(String[] permission2) {
        ActivityCompat.requestPermissions(this, permission2, 64);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 64 && PermissionsUtil.isGranted(grantResults) && PermissionsUtil.hasPermission(this, permissions)) {
            permissionsGranted();
        } else if (this.showTip) {
            showMissingPermissionDialog();
        } else {
            permissionsDenied();
        }
    }

    private void showMissingPermissionDialog() {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) TextUtils.isEmpty(this.tipInfo.title) ? "帮助" : this.tipInfo.title);
        builder.setMessage((CharSequence) TextUtils.isEmpty(this.tipInfo.content) ? "当前应用缺少必要权限。\n \n 请点击 \"设置\"-\"权限\"-打开所需权限。" : this.tipInfo.content);
        builder.setNegativeButton((CharSequence) TextUtils.isEmpty(this.tipInfo.cancel) ? "取消" : this.tipInfo.cancel, (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PermissionActivity.this.permissionsDenied();
            }
        });
        builder.setPositiveButton((CharSequence) TextUtils.isEmpty(this.tipInfo.ensure) ? "设置" : this.tipInfo.ensure, (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PermissionsUtil.gotoSetting(PermissionActivity.this);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /* access modifiers changed from: private */
    public void permissionsDenied() {
        PermissionListener listener = PermissionsUtil.fetchListener(this.key);
        if (listener != null) {
            listener.permissionDenied(this.permission);
        }
        finish();
    }

    private void permissionsGranted() {
        PermissionListener listener = PermissionsUtil.fetchListener(this.key);
        if (listener != null) {
            listener.permissionGranted(this.permission);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        PermissionsUtil.fetchListener(this.key);
        super.onDestroy();
    }
}
