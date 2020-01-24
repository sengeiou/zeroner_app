package com.iwown.my_module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.lib_common.activity.SupportActivity;
import com.iwown.my_module.enumtype.EHomeTabContentType;
import com.iwown.my_module.fragment.EmptyFragment;
import com.iwown.my_module.fragment.ProfileFragment;
import com.iwown.my_module.model.ThemeConfig;
import com.iwown.my_module.utility.flynstatusbar.Eyes;
import com.iwown.my_module.widget.TabBarImageBtn;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class ProfileMainActivity extends SupportActivity implements OnClickListener {
    private EHomeTabContentType mContentType;
    TabBarImageBtn mDataBtn;
    TabBarImageBtn mDeviceBtn;
    private Fragment mEmptyFragment1;
    private Fragment mEmptyFragment2;
    TabBarImageBtn mProfileBtn;
    private Fragment mProfileFragment;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.my_module_activity_profile_main);
        this.mDataBtn = (TabBarImageBtn) findViewById(R.id.bottom_data);
        this.mDeviceBtn = (TabBarImageBtn) findViewById(R.id.bottom_device);
        this.mProfileBtn = (TabBarImageBtn) findViewById(R.id.bottom_my);
        this.mDataBtn.setOnClickListener(this);
        this.mDeviceBtn.setOnClickListener(this);
        this.mProfileBtn.setOnClickListener(this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (this.mProfileFragment == null) {
            this.mProfileFragment = new ProfileFragment();
        }
        transaction.add(R.id.content_area, this.mProfileFragment).commit();
        this.mDeviceBtn.setImageIcon(R.mipmap.homepage_device_up);
        this.mDeviceBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
        this.mProfileBtn.setImageIcon(R.mipmap.homepage_profile_down);
        this.mProfileBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
        this.mDataBtn.setImageIcon(R.mipmap.homepage_data_up);
        this.mDataBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
        this.mContentType = EHomeTabContentType.Mine;
        int theme = new PreferenceUtility(this).fetchNumberValueWithKey(ThemeConfig.THEME_KEY);
        if (theme == 0 || theme > 2) {
            theme = 1;
        }
        if (theme == 1) {
            Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.dark_theme_title_background_color));
        } else {
            Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.light_theme_title_background_color));
        }
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bottom_data) {
            if (this.mContentType != EHomeTabContentType.Data) {
                this.mContentType = EHomeTabContentType.Data;
                setBottomTabUIState();
                if (this.mEmptyFragment1 == null) {
                    this.mEmptyFragment1 = new EmptyFragment();
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "Home");
                this.mEmptyFragment1.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_area, this.mEmptyFragment1).commit();
            }
        } else if (i == R.id.bottom_device) {
            if (this.mContentType != EHomeTabContentType.Device) {
                this.mContentType = EHomeTabContentType.Device;
                setBottomTabUIState();
                if (this.mEmptyFragment2 == null) {
                    this.mEmptyFragment2 = new EmptyFragment();
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("title", "Device");
                this.mEmptyFragment2.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_area, this.mEmptyFragment2).commit();
            }
        } else if (i == R.id.bottom_my && this.mContentType != EHomeTabContentType.Mine) {
            this.mContentType = EHomeTabContentType.Mine;
            setBottomTabUIState();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (this.mProfileFragment == null) {
                this.mProfileFragment = new ProfileFragment();
            }
            transaction.replace(R.id.content_area, this.mProfileFragment).commit();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setBottomTabUIState() {
        if (this.mContentType == EHomeTabContentType.Data) {
            this.mDataBtn.setImageIcon(R.mipmap.homepage_data_down);
            this.mDataBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
        } else {
            this.mDataBtn.setImageIcon(R.mipmap.homepage_data_up);
            this.mDataBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
        }
        if (this.mContentType == EHomeTabContentType.Device) {
            this.mDeviceBtn.setImageIcon(R.mipmap.homepage_device_down);
            this.mDeviceBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
        } else {
            this.mDeviceBtn.setImageIcon(R.mipmap.homepage_device_up);
            this.mDeviceBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
        }
        if (this.mContentType == EHomeTabContentType.Mine) {
            this.mProfileBtn.setImageIcon(R.mipmap.homepage_profile_down);
            this.mProfileBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
            return;
        }
        this.mProfileBtn.setImageIcon(R.mipmap.homepage_profile_up);
        this.mProfileBtn.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
    }

    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
