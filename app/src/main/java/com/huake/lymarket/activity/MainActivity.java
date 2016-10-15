package com.huake.lymarket.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huake.lymarket.R;
import com.huake.lymarket.adapter.CenterAdapter;
import com.huake.lymarket.views.MyViewPager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.activity_main_viewpager)
    MyViewPager mActivityMainViewpager;
    @InjectView(R.id.rb_home)
    RadioButton mRbHome;
    @InjectView(R.id.rb_hot)
    RadioButton mRbHot;
    @InjectView(R.id.rb_category)
    RadioButton mRbCategory;
    @InjectView(R.id.rb_car)
    RadioButton mRbCar;
    @InjectView(R.id.rb_mine)
    RadioButton mRbMine;
    @InjectView(R.id.rg_bottom_tag)
    RadioGroup mRgBottomTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //默认显示首页
        mRgBottomTag.check(R.id.rb_home);
        initData();
        initListener();
    }

    private void initListener() {
        mRgBottomTag.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position=0;
                switch (checkedId){
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_hot:
                        position = 1;
                        break;
                    case R.id.rb_category:
                        position = 2;
                        break;
                    case R.id.rb_car:
                        position = 3;
                        break;
                    case R.id.rb_mine:
                        position = 4;
                        break;
                }
                setFragment(position);
            }
        });
    }

    private void setFragment(int position) {
        mActivityMainViewpager.setCurrentItem(position);
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.main_titles);
        mActivityMainViewpager.setAdapter(new CenterAdapter(getSupportFragmentManager(),stringArray));
        mActivityMainViewpager.setScroll(false);
    }


}