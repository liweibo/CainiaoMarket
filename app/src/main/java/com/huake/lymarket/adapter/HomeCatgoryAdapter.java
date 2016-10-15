package com.huake.lymarket.adapter;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/12 17:51
 * @描述	      
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huake.lymarket.R;
import com.huake.lymarket.bean.HomeBean;

import java.util.List;

//注意这里的泛型要跟viewholder匹配
public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {


    private Context mContext;
    private List<HomeBean> mHomeBeans;
    private static final int VIEW_TYPE_LEFT = 0;
    private static final int VIEW_TYPE_RIGHT = 1;
    private static final int HEAD_VIEW = 1;
    private OnCampaignClickListener mOnCampaignClickListener;

    public void setOnCampaignClickListener(OnCampaignClickListener onCampaignClickListener) {
        mOnCampaignClickListener = onCampaignClickListener;
    }

    public HomeCatgoryAdapter(Context context, List<HomeBean> homeBeans) {
        mContext = context;
        mHomeBeans = homeBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater  mInflater = LayoutInflater.from(parent.getContext());
        //区分左边和右边，创建viewholder
        if (viewType == VIEW_TYPE_RIGHT) {
            return  new ViewHolder(mInflater.inflate(R.layout.template_home_cardview_r,parent,false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview_l,parent,false));
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        //处理数据
        HomeBean homeBean = mHomeBeans.get(position);
        holder.textTitle.setText(homeBean.getTitle());
        Glide.with(mContext).load(homeBean.getCpOne().getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imageViewBig);
        Glide.with(mContext).load(homeBean.getCpTwo().getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imageViewSmallTop);
        Glide.with(mContext).load(homeBean.getCpThree().getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imageViewSmallBottom);
    }

    @Override
    public int getItemViewType(int position) {

        if (position==0){
            return HEAD_VIEW;
        }else {
            if (position % 2 == 0) {
                return VIEW_TYPE_LEFT;
            }
            return VIEW_TYPE_RIGHT;
        }
    }

    @Override
    public int getItemCount() {
        int count = (mHomeBeans == null ? 0 : mHomeBeans.size());
        //添加了请求头
        return count+1;
    }

    //必须添加viewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;



        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //装逼一下，再添加一个旋转动画
            if (mOnCampaignClickListener != null) {
                amination(v);
            }

        }
        private void amination(final View v) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F);
            animator.setDuration(1000);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    HomeBean homeBean = mHomeBeans.get(getLayoutPosition());
                    switch (v.getId()) {

                        case R.id.imgview_big:
                            mOnCampaignClickListener.onClick(v, homeBean.getCpOne());
                            break;

                        case R.id.imgview_small_top:
                            mOnCampaignClickListener.onClick(v, homeBean.getCpTwo());
                            break;

                        case R.id.imgview_small_bottom:
                            mOnCampaignClickListener.onClick(v, homeBean.getCpThree());
                            break;

                    }

                }
            });
            animator.start();
        }
    }


    //自定义点击事件
    public  interface OnCampaignClickListener{
        void onClick(View view,HomeBean.Compaign compaign);
    }


}
