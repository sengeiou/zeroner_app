package com.iwown.my_module.useractivity.profile;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;

public class TestActivity extends BaseActivity implements OnClickListener {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_test);
    }

    public void onClick(View view) {
    }
}
