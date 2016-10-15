package com.huake.lymarket.adapter;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/14 17:20
 * @描述	      
 */

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huake.lymarket.R;
import com.huake.lymarket.bean.HotBean;

import java.util.List;

public class CategoryContentAdapter extends BaseQuickAdapter<HotBean.ListBean> {

    public CategoryContentAdapter(int layoutResId, List<HotBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotBean.ListBean item) {
        helper.setText(R.id.text_category_des,item.getName());
        helper.setText(R.id.text_category_price,item.getPrice()+"");
        Glide.with(mContext).load(item.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.img_category_pic));
    }
}
