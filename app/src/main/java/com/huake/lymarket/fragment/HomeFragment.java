package com.huake.lymarket.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huake.lymarket.R;
import com.huake.lymarket.activity.WareListActivity;
import com.huake.lymarket.adapter.HomeAdapter;
import com.huake.lymarket.base.BaseFragment;
import com.huake.lymarket.bean.HeadPicBean;
import com.huake.lymarket.bean.HomeBean;
import com.huake.lymarket.config.Contants;
import com.huake.lymarket.http.OkHttpHelper;
import com.huake.lymarket.http.SpotsCallBack;
import com.huake.lymarket.views.HeadView;
import com.squareup.okhttp.Response;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragment extends BaseFragment {

    @InjectView(R.id.toolbar_home_fragment)
    Toolbar mToolbarHomeFragment;
    @InjectView(R.id.recyclerview_home_fragment)
    RecyclerView mRecyclerviewHomeFragment;
    private List<HeadPicBean> mHeads;
    private List<HomeBean> mHomeBeans;
    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance();


    private HeadView mHeadView;

    @Override
    public View initView() {
        View inflate = View.inflate(getContext(), R.layout.fragment_home, null);
        ButterKnife.inject(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        requestImages();
        requestRecycetView();

    }

    private void requestRecycetView() {
        String url = Contants.API.CAMPAIGN_HOME ;
        mOkHttpHelper.get(url, new SpotsCallBack<List<HomeBean>>(getContext()) {


            @Override
            public void onSuccess(Response response, List<HomeBean> homeBeen) {
                mHomeBeans=homeBeen;
                initRecycerView();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initRecycerView() {

        //设置左右界面布局类型
        for (int i = 0; i < mHomeBeans.size(); i++) {
            if (i%2==0){
                mHomeBeans.get(i).setItemType(HomeBean.LEFT);
            }else {
                mHomeBeans.get(i).setItemType(HomeBean.RIGHT);
            }
        }
        HomeAdapter homeAdapter = new HomeAdapter(mHomeBeans);
        homeAdapter.addHeaderView(mHeadView.getInflate());
        if (mRecyclerviewHomeFragment != null) {
            mRecyclerviewHomeFragment.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            mRecyclerviewHomeFragment.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(getActivity(), WareListActivity.class);
                    intent.putExtra(Contants.COMPAINGAIN_ID,mHomeBeans.get(position).getId());
                    startActivity(intent);
                }
            });
            mRecyclerviewHomeFragment.setAdapter(homeAdapter);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        if (mHeadView != null) {

            mHeadView.getHandler().removeMessages(0);
        }
    }

    private void requestImages() {

        String url = Contants.API.SLIDERURL + 1;

        mOkHttpHelper.get(url, new SpotsCallBack<List<HeadPicBean>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<HeadPicBean> headPicBeen) {
                mHeads = headPicBeen;
//                initHoldView();
                mHeadView = new HeadView(mHeads, getContext());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }


    @Override
    public void onPause() {
        if (mHeadView != null) {
            mHeadView.getHandler().removeMessages(0);
        }
        super.onPause();
    }
}