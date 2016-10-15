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

public class HottAdapter extends BaseQuickAdapter<HotBean.ListBean> {

    private int layoutResId;
    private List<HotBean.ListBean> data;
    public HottAdapter(int layoutResId, List<HotBean.ListBean> data) {
        super(layoutResId, data);
        this.layoutResId=layoutResId;
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotBean.ListBean item) {
        helper.setText(R.id.fragment_hot_text_title,item.getName());
        helper.setText(R.id.fragment_hot_text_price,item.getPrice()+"");
        Glide.with(mContext).load(item.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.fragment_hot_img));
    }

    public void  resetLayout(int layoutId){

        this.layoutResId  = layoutId;
        notifyItemRangeChanged(0,data.size());


    }
}
