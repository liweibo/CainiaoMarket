package com.huake.lymarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huake.lymarket.factory.FragmentFactory;

/*
 * @创建者     兰昱
 * @创建时间  2016/8/14 16:31
 * @描述	      
 */
public class CenterAdapter extends FragmentPagerAdapter {

    String[] titles;
    public CenterAdapter(FragmentManager fm, String[] mStringArray) {
        super(fm);
        titles=mStringArray;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getFragment(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
