package com.huake.lymarket.adapter;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/12 17:51
 * @描述	      
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huake.lymarket.R;
import com.huake.lymarket.bean.HotBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//注意这里的泛型要跟viewholder匹配
public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {



    private Context mContext;
    private HotBean mHotBean;


    public HotAdapter(Context context,HotBean homeBeans) {
        mContext = context;
        mHotBean = homeBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        //区分左边和右边，创建viewholder
        return new ViewHolder(mInflater.inflate(R.layout.template_hot_wares, parent, false));
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        //处理数据
        HotBean.ListBean listBean = mHotBean.getList().get(position);
        Glide.with(mContext).load(listBean.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.mFragmentHotImg);
        holder.mFragmentHotTextPrice.setText(listBean.getPrice()+"");
        holder.mFragmentHotTextTitle.setText(listBean.getName());
    }


    @Override
    public int getItemCount() {
        return mHotBean.getList().size();
    }

    //必须添加viewHolder
    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.fragment_hot_img)
        ImageView mFragmentHotImg;
        @InjectView(R.id.fragment_hot_text_title)
        TextView mFragmentHotTextTitle;
        @InjectView(R.id.fragment_hot_text_price)
        TextView mFragmentHotTextPrice;
        @InjectView(R.id.fragment_hot_btn_add)
        Button mFragmentHotBtnAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

    }
    //增加数据
    public void addData(List<HotBean.ListBean> mdatas){
        mHotBean.getList().addAll(mdatas);
        notifyItemInserted(mHotBean.getList().size());
    }
    //删除数据
    public void clearData(){
        mHotBean.getList().clear();
        notifyItemRangeRemoved(0,mHotBean.getList().size());
    }



}
