package com.iwown.device_module;

import android.os.Bundle;
import com.iwown.device_module.device_setting.fragment.DeviceFragment;
import com.iwown.lib_common.activity.SupportActivity;
import com.iwown.lib_common.fragment.SupportFragment;

public class MainActivity extends SupportActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_main);
        initView();
    }

    private void initView() {
        if (((SupportFragment) findFragment(SupportFragment.class)) != null) {
            loadRootFragment(R.id.container, findFragment());
        } else {
            loadRootFragment(R.id.container, newFragment());
        }
    }

    private SupportFragment newFragment() {
        return DeviceFragment.newInstance();
    }

    private SupportFragment findFragment() {
        return DeviceFragment.newInstance();
    }
}
