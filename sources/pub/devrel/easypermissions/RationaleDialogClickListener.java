package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.support.v4.app.Fragment;
import java.util.Arrays;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;
import pub.devrel.easypermissions.helper.PermissionHelper;

class RationaleDialogClickListener implements OnClickListener {
    private PermissionCallbacks mCallbacks;
    private RationaleDialogConfig mConfig;
    private Object mHost;

    RationaleDialogClickListener(RationaleDialogFragmentCompat compatDialogFragment, RationaleDialogConfig config, PermissionCallbacks callbacks) {
        Object activity;
        if (compatDialogFragment.getParentFragment() != null) {
            activity = compatDialogFragment.getParentFragment();
        } else {
            activity = compatDialogFragment.getActivity();
        }
        this.mHost = activity;
        this.mConfig = config;
        this.mCallbacks = callbacks;
    }

    RationaleDialogClickListener(RationaleDialogFragment dialogFragment, RationaleDialogConfig config, PermissionCallbacks callbacks) {
        Object activity;
        if (VERSION.SDK_INT >= 17) {
            if (dialogFragment.getParentFragment() != null) {
                activity = dialogFragment.getParentFragment();
            } else {
                activity = dialogFragment.getActivity();
            }
            this.mHost = activity;
        } else {
            this.mHost = dialogFragment.getActivity();
        }
        this.mConfig = config;
        this.mCallbacks = callbacks;
    }

    public void onClick(DialogInterface dialog, int which) {
        if (which != -1) {
            notifyPermissionDenied();
        } else if (this.mHost instanceof Fragment) {
            PermissionHelper.newInstance((Fragment) this.mHost).directRequestPermissions(this.mConfig.requestCode, this.mConfig.permissions);
        } else if (this.mHost instanceof android.app.Fragment) {
            PermissionHelper.newInstance((android.app.Fragment) this.mHost).directRequestPermissions(this.mConfig.requestCode, this.mConfig.permissions);
        } else if (this.mHost instanceof Activity) {
            PermissionHelper.newInstance((Activity) this.mHost).directRequestPermissions(this.mConfig.requestCode, this.mConfig.permissions);
        } else {
            throw new RuntimeException("Host must be an Activity or Fragment!");
        }
    }

    private void notifyPermissionDenied() {
        if (this.mCallbacks != null) {
            this.mCallbacks.onPermissionsDenied(this.mConfig.requestCode, Arrays.asList(this.mConfig.permissions));
        }
    }
}
