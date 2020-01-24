package com.iwown.sport_module;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.sport_module.Fragment.SportFragment;
import com.iwown.sport_module.util.WindowUtil;

@Route(path = "/sport/sport_mainactivty")
public class MainActivity extends AppCompatActivity {
    private FrameLayout container;
    private FragmentManager mFragmentManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.setTopWindows(getWindow());
        setContentView(R.layout.sport_module_activity_main);
        this.mFragmentManager = getSupportFragmentManager();
        initView();
    }

    private void initView() {
        this.container = (FrameLayout) findViewById(R.id.container);
        SportFragment sportFragment = new SportFragment();
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        transaction.add(R.id.container, sportFragment, "sport_fragment");
        transaction.show(sportFragment);
        transaction.commitAllowingStateLoss();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
