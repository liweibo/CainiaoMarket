package com.huake.lymarket.activity;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/14 20:45
 * @描述	      
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.huake.lymarket.R;
import com.huake.lymarket.adapter.HottAdapter;
import com.huake.lymarket.bean.HotBean;
import com.huake.lymarket.config.Contants;
import com.huake.lymarket.http.OkHttpHelper;
import com.huake.lymarket.http.SpotsCallBack;
import com.huake.lymarket.views.MyToolbar;
import com.squareup.okhttp.Response;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WareListActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    @InjectView(R.id.toolbar_detaiList_activity)
    MyToolbar mToolbarDetaiListActivity;
    @InjectView(R.id.txt_detaiList_activity)
    TextView mTxtDetaiListActivity;
    @InjectView(R.id.recycerview_detaiList_activity)
    RecyclerView mRecycerviewDetaiListActivity;
    @InjectView(R.id.refresh_layout_detaiList_activity)
    MaterialRefreshLayout mRefreshLayoutDetaiListActivity;
    //tablayout专用
    public static final int TAG_DEFAULT = 0;
    public static final int TAG_SALE = 1;
    public static final int TAG_PRICE = 2;

    public static final int ACTION_LIST = 1;
    public static final int ACTION_GIRD = 2;
    @InjectView(R.id.tablayout_detaiList_activity)
    TabLayout mTablayoutDetaiListActivity;
    private int campaignId;
    private OkHttpHelper mInstance;
    private int curPage = 1;
    private int pageSize = 10;
    private int orderBy = 0;
    private int mTotalPage;
    private List<HotBean.ListBean> mHotBeans;

    public static final int NORMAL = 0;
    public static final int REFRESH = 1;
    public static final int LOADMORE = 2;
    private int state = NORMAL;
    private HottAdapter hotAdapter;
    private int mAction=ACTION_LIST;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_warelist);
        ButterKnife.inject(this);

        initToolBar();

        campaignId = getIntent().getIntExtra(Contants.COMPAINGAIN_ID, 1);
        //三个筛选项
        initTab();
        initListener();
        //拿取数据
        getDataFromNet();


    }

    private void initListener() {

        mRefreshLayoutDetaiListActivity.setLoadMore(true);//文档没有这句话，坑爹
        mRefreshLayoutDetaiListActivity.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state = REFRESH;
                curPage = 1;
                getDataFromNet();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                state = LOADMORE;
                // 结束上拉刷新...
                curPage++;
                if (curPage <= mTotalPage) {
                    getDataFromNet();
                } else {
                    materialRefreshLayout.finishRefreshLoadMore();
                }


            }
        });
    }

    private void getDataFromNet() {
        String url = Contants.API.WARES_CAMPAIN_LIST + "?curPage=" + curPage + "&pageSize=" + pageSize + "&campaignId=" + campaignId + "&orderBy=" + orderBy;
        mInstance = OkHttpHelper.getInstance();
        mInstance.get(url, new SpotsCallBack<HotBean>(this) {
            @Override
            public void onSuccess(Response response, HotBean hotBeen) {
                mHotBeans = hotBeen.getList();
                curPage = hotBeen.getCurrentPage();
                mTotalPage = hotBeen.getTotalPage();
                int totalCount = hotBeen.getTotalCount();
                mTxtDetaiListActivity.setText("总共有" + totalCount + "件商品");
                initRecycerView();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initRecycerView() {
        switch (state) {
            case NORMAL:
                hotAdapter = new HottAdapter(R.layout.template_hot_wares, mHotBeans);
                hotAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                //判断显示什么状态
                if (ACTION_LIST == mAction) {
                    mRecycerviewDetaiListActivity.setLayoutManager(new LinearLayoutManager(this));

                } else if (ACTION_GIRD == mAction) {
                    mRecycerviewDetaiListActivity.setLayoutManager(new GridLayoutManager(this, 2));
                }

                mRecycerviewDetaiListActivity.setAdapter(hotAdapter);
                break;
            case REFRESH:
                hotAdapter.setNewData(mHotBeans);
                mRefreshLayoutDetaiListActivity.finishRefresh();
                break;
            case LOADMORE:
                hotAdapter.addData(mHotBeans);
                mRefreshLayoutDetaiListActivity.finishRefreshLoadMore();
                break;
        }
        mRecycerviewDetaiListActivity.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotBean.ListBean item = hotAdapter.getItem(position);
                Intent intent = new Intent(WareListActivity.this, WareDetailActivity.class);
                intent.putExtra(Contants.WARE,item);
                startActivity(intent);
            }
        });
        state = NORMAL;
    }


    private void initTab() {
        //三种标签选项
        TabLayout.Tab tab = mTablayoutDetaiListActivity.newTab();
        tab.setText("默认");
        tab.setTag(TAG_DEFAULT);
        mTablayoutDetaiListActivity.addTab(tab);

        tab = mTablayoutDetaiListActivity.newTab();
        tab.setText("价格");
        tab.setTag(TAG_PRICE);
        mTablayoutDetaiListActivity.addTab(tab);

        tab = mTablayoutDetaiListActivity.newTab();
        tab.setText("销量");
        tab.setTag(TAG_SALE);

        mTablayoutDetaiListActivity.addTab(tab);
        //点击事件
        mTablayoutDetaiListActivity.addOnTabSelectedListener(this);
    }

    //设置toolbar的点击事件，按钮图标，绑定tag
    private void initToolBar() {

        mToolbarDetaiListActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareListActivity.this.finish();
            }
        });

        mToolbarDetaiListActivity.setButtonOnClickListener(this);
        mToolbarDetaiListActivity.setRightButtonDrawable(getResources().getDrawable(R.drawable.icon_grid_32));
        //绑定按钮做一个标识，便于区分同一个按钮的不同状态
        mToolbarDetaiListActivity.getToolbarRightButton().setTag(ACTION_LIST);
        mToolbarDetaiListActivity.setTextTitle("商品列表");


    }

    @Override
    public void onClick(View v) {

        if (ACTION_LIST == mAction) {

            mAction=ACTION_GIRD;
            mToolbarDetaiListActivity.setRightButtonDrawable(getResources().getDrawable(R.drawable.icon_list_32));
            hotAdapter.resetLayout(R.layout.template_grid_wares);
            hotAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            mRecycerviewDetaiListActivity.setLayoutManager(new GridLayoutManager(this, 2));

        } else if (ACTION_GIRD == mAction) {
            mAction=ACTION_LIST;
            mToolbarDetaiListActivity.setRightButtonDrawable(getResources().getDrawable(R.drawable.icon_grid_32));
            hotAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            hotAdapter.resetLayout(R.layout.template_hot_wares);
            mRecycerviewDetaiListActivity.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mType = (int) tab.getTag();
        orderBy = mType;
        getDataFromNet();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
