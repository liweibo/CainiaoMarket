package com.huake.lymarket.base;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/8 15:02
 * @描述	      
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView();

        initListener();
        initData();
        initToolBar();
        return view;

    }



    public void initListener() {
    }

    //初始化视图必须实现
    public abstract View initView() ;

    public abstract void initData();

    public void initToolBar() {

    }


}
