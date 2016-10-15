package com.huake.lymarket.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.huake.lymarket.R;
import com.huake.lymarket.adapter.HottAdapter;
import com.huake.lymarket.base.BaseFragment;
import com.huake.lymarket.bean.HotBean;
import com.huake.lymarket.config.Contants;
import com.huake.lymarket.http.OkHttpHelper;
import com.huake.lymarket.http.SpotsCallBack;
import com.squareup.okhttp.Response;

import java.util.List;

import butterknife.ButterKnife;


public class HotFragment extends BaseFragment{


    private RecyclerView mFragmentHotRecyclerview;
    private MaterialRefreshLayout materialRefreshLayout;
    private List<HotBean.ListBean> mHotBeans;
    private OkHttpHelper mOkHttpHelper;
    private int curPage=1;
    private int pageSize=10;
    public static final int NORMAL=0;
    public static final int REFRESH=1;
    public static final int LOADMORE=2;
    private int state=NORMAL;
    private HottAdapter hotAdapter=null;
    private int mTotalPage;

    @Override
    public View initView() {
        View inflate = View.inflate(getContext(), R.layout.fragment_hot, null);
        ButterKnife.inject(inflate);
        materialRefreshLayout = (MaterialRefreshLayout)inflate.findViewById(R.id.fragment_hot_refresh_view);
        mFragmentHotRecyclerview = (RecyclerView) inflate.findViewById(R.id.fragment_hot_recyclerview);
        return inflate;
    }

    @Override
    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {

        mOkHttpHelper = OkHttpHelper.getInstance();
        String url = Contants.API.WARES_HOT + "?curPage="+curPage+"&pageSize="+pageSize;
        mOkHttpHelper.get(url, new SpotsCallBack<HotBean>(getContext()) {
            @Override
            public void onSuccess(Response response, HotBean hotBeen) {
                mHotBeans = hotBeen.getList();
                curPage=hotBeen.getCurrentPage();
                mTotalPage = hotBeen.getTotalPage();
                initRecycerView();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    @Override
    public void initListener() {
        super.initListener();
       materialRefreshLayout.setLoadMore(true);//文档没有这句话，坑爹
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state=REFRESH;
                curPage=1;
                getDataFromNet();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                state=LOADMORE;
                // 结束上拉刷新...
                curPage++;
                if (curPage<=mTotalPage){
                    getDataFromNet();
                }else {
                    materialRefreshLayout.finishRefreshLoadMore();
                }


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initRecycerView() {
        switch (state){
            case NORMAL:
                hotAdapter=new HottAdapter(R.layout.template_hot_wares,mHotBeans);
                mFragmentHotRecyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                mFragmentHotRecyclerview.setAdapter(hotAdapter);
                break;
            case REFRESH:
                hotAdapter.setNewData(mHotBeans);
                materialRefreshLayout.finishRefresh();
                break;
            case LOADMORE:
                hotAdapter.addData(mHotBeans);
                materialRefreshLayout.finishRefreshLoadMore();
                break;
        }
        state=NORMAL;

    }


}
