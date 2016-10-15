package com.huake.lymarket.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.huake.lymarket.R;
import com.huake.lymarket.adapter.CategoryAdapter;
import com.huake.lymarket.adapter.CategoryContentAdapter;
import com.huake.lymarket.base.BaseFragment;
import com.huake.lymarket.bean.CategoryBean;
import com.huake.lymarket.bean.HeadPicBean;
import com.huake.lymarket.bean.HotBean;
import com.huake.lymarket.config.Contants;
import com.huake.lymarket.http.OkHttpHelper;
import com.huake.lymarket.http.SpotsCallBack;
import com.huake.lymarket.utils.CommonUtils;
import com.huake.lymarket.views.HeadView;
import com.squareup.okhttp.Response;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.huake.lymarket.fragment.HotFragment.LOADMORE;
import static com.huake.lymarket.fragment.HotFragment.REFRESH;


public class CategoryFragment extends BaseFragment {


    @InjectView(R.id.recycerview_left_category)
    RecyclerView mRecycerviewLeftCategory;
    @InjectView(R.id.recyclerview_right_category)
    RecyclerView mRecyclerviewRightCategory;
    @InjectView(R.id.refresh_layout)
    MaterialRefreshLayout mRefreshLayout;
    private OkHttpHelper mOkHttpHelper;
    private List<CategoryBean> mCategoryDatas;
    private List<HeadPicBean> mHeadPicBeans;
    private CategoryAdapter mCategoryAdapter;
    private List<HotBean.ListBean> mListContents;
    private int category_id;
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;
    private CategoryContentAdapter mCategoryContentAdapter;

    @Override
    public View initView() {
        View inflate = View.inflate(getContext(), R.layout.fragment_category, null);
        ButterKnife.inject(this, inflate);
        mOkHttpHelper = OkHttpHelper.getInstance();
        return inflate;
    }

    @Override
    public void initData() {
        getHeadViewFromNet();
        getCategotyFromNet();
    }

    private void getHeadViewFromNet() {
        String url = Contants.API.SLIDERURL + 1;
        mOkHttpHelper.get(url, new SpotsCallBack<List<HeadPicBean>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<HeadPicBean> headPicBeen) {
                mHeadPicBeans = headPicBeen;
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    private void getCategotyFromNet() {


        mOkHttpHelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<CategoryBean>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<CategoryBean> categoryBeen) {
                mCategoryDatas = categoryBeen;
                showCategory();
                if (mCategoryDatas != null && mCategoryDatas.size() > 0) {
                    category_id = mCategoryDatas.get(0).getId();
                    requestWares(category_id);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //显示左侧列表
    private void showCategory() {
        mCategoryAdapter = new CategoryAdapter(R.layout.template_single_text, mCategoryDatas);
        mRecycerviewLeftCategory.setAdapter(mCategoryAdapter);
        mRecycerviewLeftCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycerviewLeftCategory.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                category_id=mCategoryDatas.get(position).getId();
                currPage=1;
                state=STATE_NORMAL;
                requestWares(category_id);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void requestWares(long categoryId) {

        String url = Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" + pageSize;

        mOkHttpHelper.get(url, new SpotsCallBack<HotBean>(getContext()) {

            @Override
            public void onSuccess(Response response, HotBean hotBean) {
                currPage = hotBean.getCurrentPage();
                totalPage = hotBean.getTotalPage();
                mListContents = hotBean.getList();
                showContent(mListContents);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void showContent(List<HotBean.ListBean> listContents) {

        switch (state) {
            case STATE_NORMAL:

                mCategoryContentAdapter = new CategoryContentAdapter(R.layout.template_grid_wares, mListContents);
                HeadView headView = new HeadView(mHeadPicBeans,getContext());
                View mHviews = headView.getInflate();
                mHviews.setPadding(CommonUtils.dip2px(5,getContext()),0,0,0);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,CommonUtils.dip2px(120,getContext()));
                mHviews.setLayoutParams(params);
                mCategoryContentAdapter.addHeaderView(headView.getInflate());
                mCategoryContentAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                mRecyclerviewRightCategory.setLayoutManager(new GridLayoutManager(getContext(),2));
                mRecyclerviewRightCategory.setAdapter(mCategoryContentAdapter);
                break;
            case STATE_REFREH:
                mCategoryContentAdapter.setNewData(mListContents);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mCategoryContentAdapter.addData(mListContents);
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
        state = STATE_NORMAL;
    }

    @Override
    public void initListener() {
        super.initListener();
        mRefreshLayout.setLoadMore(true);//文档没有这句话，坑爹
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state=REFRESH;
                currPage=1;
                requestWares(category_id);

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                state=LOADMORE;
                // 结束上拉刷新...
                currPage++;
                if (currPage<=totalPage){
                    requestWares(category_id);
                }else {
                    materialRefreshLayout.finishRefreshLoadMore();
                }


            }
        });
    }
}


