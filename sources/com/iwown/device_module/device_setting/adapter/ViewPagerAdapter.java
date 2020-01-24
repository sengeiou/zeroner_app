package com.iwown.device_module.device_setting.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.socks.library.KLog;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Fragment> fragments = null;
    private List<String> titles;

    public ViewPagerAdapter(Context context2, FragmentManager fm, List<Fragment> fragments2, List<String> titles2) {
        super(fm);
        this.context = context2;
        this.fragments = fragments2;
        this.titles = titles2;
        notifyDataSetChanged();
    }

    public ViewPagerAdapter(Context context2, FragmentManager fm) {
        super(fm);
        this.context = context2;
        notifyDataSetChanged();
    }

    public Fragment getItem(int arg0) {
        return (Fragment) this.fragments.get(arg0);
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public String getPageTitle(int position) {
        KLog.i(((String) this.titles.get(position)).toLowerCase());
        return (String) this.titles.get(position);
    }

    public int getCount() {
        return this.fragments.size();
    }
}
