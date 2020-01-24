package com.iwown.device_module.device_operation.type;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.device_module.R;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.lib_common.activity.SupportActivity;

@Route(path = "/device/DeviceListActivity")
public class DeviceListActivity extends SupportActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_device_list);
        WindowsUtil.setTopWindows(getWindow());
        initView();
    }

    private void initView() {
        loadRootFragment(R.id.container, DeviceListFragment.newInstance(true));
    }
}
