package pub.devrel.easypermissions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.tencent.tinker.loader.hotplug.EnvConsts;

@RestrictTo({Scope.LIBRARY_GROUP})
public class AppSettingsDialogHolderActivity extends AppCompatActivity implements OnClickListener {
    private static final int APP_SETTINGS_RC = 7534;
    private AlertDialog mDialog;

    public static Intent createShowDialogIntent(Context context, AppSettingsDialog dialog) {
        return new Intent(context, AppSettingsDialogHolderActivity.class).putExtra("extra_app_settings", dialog);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDialog = AppSettingsDialog.fromIntent(getIntent(), this).showDialog(this, this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            startActivityForResult(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts(EnvConsts.PACKAGE_MANAGER_SRVNAME, getPackageName(), null)), APP_SETTINGS_RC);
        } else if (which == -2) {
            setResult(0);
            finish();
        } else {
            throw new IllegalStateException("Unknown button type: " + which);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode, data);
        finish();
    }
}
