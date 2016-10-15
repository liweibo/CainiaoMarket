package com.huake.lymarket.adapter;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/14 15:17
 * @描述	      
 */

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huake.lymarket.R;
import com.huake.lymarket.bean.HomeBean;

import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBean> {

    public HomeAdapter(List<HomeBean> data) {
        super(data);
        addItemType(HomeBean.LEFT, R.layout.template_home_cardview_l);
        addItemType(HomeBean.RIGHT, R.layout.template_home_cardview_r);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeBean homeBean) {
        switch (holder.getItemViewType()) {
            case HomeBean.LEFT:
            case HomeBean.RIGHT:
                holder.setText(R.id.text_title, homeBean.getTitle());
                Glide.with(mContext).load(homeBean.getCpOne().getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.imgview_big));
                Glide.with(mContext).load(homeBean.getCpTwo().getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.imgview_small_top));
                Glide.with(mContext).load(homeBean.getCpThree().getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.imgview_small_bottom));
                break;
        }
    }
}
