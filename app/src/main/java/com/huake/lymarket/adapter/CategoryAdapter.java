package com.huake.lymarket.adapter;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/12 17:51
 * @描述	      
 */

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huake.lymarket.R;
import com.huake.lymarket.bean.CategoryBean;

import java.util.List;

//注意这里的泛型要跟viewholder匹配
public class CategoryAdapter extends BaseQuickAdapter<CategoryBean>{

    public CategoryAdapter(int layoutResId, List<CategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        helper.setText(R.id.fragment_categoty_textView,item.getName());
    }
}
