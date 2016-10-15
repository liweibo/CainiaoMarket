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
import com.huake.lymarket.bean.ShoppingCart;

import java.util.List;

public class CarAdapter extends BaseQuickAdapter<ShoppingCart> {

    private List<ShoppingCart> data;

    public CarAdapter(int layoutResId, List<ShoppingCart> data) {
        super(layoutResId, data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCart item) {
        helper.setText(R.id.text_title_car_fragment,item.getName());
        helper.setText(R.id.text_price_car_fragment,item.getPrice()+"");
        Glide.with(mContext).load(item.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.img_car_fragment));
    }

}
