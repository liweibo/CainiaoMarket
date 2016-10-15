package com.huake.lymarket.factory;

import android.support.v4.util.SparseArrayCompat;

import com.huake.lymarket.base.BaseFragment;
import com.huake.lymarket.fragment.CartFragment;
import com.huake.lymarket.fragment.CategoryFragment;
import com.huake.lymarket.fragment.HomeFragment;
import com.huake.lymarket.fragment.HotFragment;
import com.huake.lymarket.fragment.MineFragment;

/*
 * @创建者     兰昱
 * @创建时间  2016/8/14 16:13
 * @描述	      
 */
public class FragmentFactory {

    public static final int home = 0;
    public static final int hot = 1;
    public static final int category = 2;
    public static final int cart = 3;
    public static final int mine = 4;
    //缓存fragment
    static SparseArrayCompat<BaseFragment> cacheFragment = new SparseArrayCompat<BaseFragment>();

    public static BaseFragment getFragment(int index) {

        BaseFragment fragment = null;
        fragment = cacheFragment.get(index);
        if (fragment != null) {
            return fragment;
        }
        switch (index) {
            case home:
                fragment = new HomeFragment();
                break;
            case hot:
                fragment = new HotFragment();
                break;
            case category:
                fragment = new CategoryFragment();
                break;
            case mine:
                fragment = new MineFragment();
                break;
            case cart:
                fragment = new CartFragment();
                break;
            default:
                break;
        }
        cacheFragment.put(index, fragment);
        return fragment;
    }
}
