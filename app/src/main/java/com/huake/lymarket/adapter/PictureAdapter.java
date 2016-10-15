package com.huake.lymarket.adapter;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/9 21:01
 * @描述	      
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huake.lymarket.bean.HeadPicBean;

import java.util.List;

public class PictureAdapter extends PagerAdapter{

    private List<HeadPicBean> mHeads;
    private Context context;
    public PictureAdapter(List<HeadPicBean> mheads, Context context) {
        this.mHeads = mheads;
        this.context=context;
    }

    @Override
    public int getCount() {

        if (mHeads != null) {
            return Integer.MAX_VALUE;
        }
        return 0;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //默认位置肯定是从0开始的，取余是防止超出界限引发空指针，如果想左右滑动，就需要先把位置调整到中间咯
        position = position % mHeads.size();
        Glide.with(context).load(mHeads.get(position).getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
