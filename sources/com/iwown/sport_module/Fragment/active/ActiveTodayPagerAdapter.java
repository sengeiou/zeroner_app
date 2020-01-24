package com.iwown.sport_module.Fragment.active;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.List;

public class ActiveTodayPagerAdapter extends FragmentPagerAdapter {
    private Context ctx;
    private List<Fragment> list;

    public ActiveTodayPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.list = fragmentList;
    }

    public Fragment getItem(int position) {
        return (Fragment) this.list.get(position);
    }

    public int getCount() {
        return this.list.size();
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
