package com.huake.lymarket.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huake.lymarket.R;
import com.huake.lymarket.adapter.CarAdapter;
import com.huake.lymarket.base.BaseFragment;
import com.huake.lymarket.bean.ShoppingCart;
import com.huake.lymarket.utils.CarProvider;
import com.huake.lymarket.views.MyToolbar;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CartFragment extends BaseFragment {


    @InjectView(R.id.toolbar_car_fragment)
    MyToolbar mToolbarCarFragmentCarFragment;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.checkbox_all)
    CheckBox mCheckboxAll;
    @InjectView(R.id.txt_total)
    TextView mTxtTotal;
    @InjectView(R.id.btn_order)
    Button mBtnOrder;
    @InjectView(R.id.btn_del)
    Button mBtnDel;
    private CarProvider mCarProvider;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_CAMPLATE = 2;

    @Override
    public View initView() {
        View inflate = View.inflate(getContext(), R.layout.fragment_cart, null);
        ButterKnife.inject(this, inflate);
        return inflate;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        if (mToolbarCarFragmentCarFragment != null) {
            mToolbarCarFragmentCarFragment.getToolbarSearchview().setVisibility(View.GONE);

            mToolbarCarFragmentCarFragment.setTextTitle("购物车");
            mToolbarCarFragmentCarFragment.setRightButtonText("编辑");
            mToolbarCarFragmentCarFragment.setButtonOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mToolbarCarFragmentCarFragment.getRightButton().setTag(ACTION_EDIT);
        }

    }

    @Override
    public void initData() {
        mCarProvider = new CarProvider(getContext());
        List<ShoppingCart> all = mCarProvider.getAll();
        if (all != null) {
            Toast.makeText(getContext(), "all.size():" + all.size(), Toast.LENGTH_SHORT).show();
            CarAdapter carAdapter = new CarAdapter(R.layout.template_cart,all);
            mRecyclerView.setAdapter(carAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            carAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
